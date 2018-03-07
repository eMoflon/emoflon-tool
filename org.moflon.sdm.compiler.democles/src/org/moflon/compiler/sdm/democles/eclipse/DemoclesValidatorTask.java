package org.moflon.compiler.sdm.democles.eclipse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.dfs.DFSGraph;
import org.moflon.core.dfs.DfsFactory;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowPackage;
import org.moflon.sdm.compiler.democles.validation.controlflow.InefficientBootstrappingBuilder;
import org.moflon.sdm.compiler.democles.validation.controlflow.SDMActivityGraphBuilder;
import org.moflon.sdm.compiler.democles.validation.controlflow.Validator;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

import SDMLanguage.activities.ActivitiesPackage;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.MoflonEOperation;

/**
 * This job calls during its {@link #run(IProgressMonitor)} the stored validator
 * method for all EClasses contained in its stored EPackage (see
 * {@link #DemoclesValidatorTask(ScopeValidator, EPackage)}.
 */
public class DemoclesValidatorTask implements ITask {
	private static final Logger logger = Logger.getLogger(DemoclesValidatorTask.class);

	public static final String TASK_NAME = "SDM validation";

	private final ScopeValidator scopeValidator;

	private final EPackage ePackage;

	private final ResourceSet resourceSet;

	private boolean loadIntoResourceSet; // Whether to load the validation result into resourceSet

	public DemoclesValidatorTask(final ScopeValidator scopeValidator, final EPackage ePackage) {
		this.scopeValidator = scopeValidator;
		this.ePackage = ePackage;
		this.resourceSet = ePackage.eResource().getResourceSet();
		this.loadIntoResourceSet = true;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		final MultiStatus validationStatus = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0,
				TASK_NAME + " failed", null);
		final List<EClass> eClasses = eMoflonEMFUtil.getEClasses(ePackage);
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, "Validating classes in package " + ePackage.getName(),
					eClasses.size());
			for (final EClass eClass : eClasses) {
				IStatus cancelStatus = validateEClass(eClass, validationStatus, subMon.split(1));
				if (cancelStatus.getSeverity() == Status.CANCEL)
					return cancelStatus;
			}

			return validationStatus.isOK() ? Status.OK_STATUS : validationStatus;
		} catch (final RuntimeException e) {
			LogUtils.error(logger, e, "Stacktrace of failed validation:\n%s",
					WorkspaceHelper.printStacktraceToString(e));
			return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR,
					"Internal exception occured (probably caused by a bug in the validation module): " + e
							+ " Please report the bug on " + WorkspaceHelper.ISSUE_TRACKER_URL
							+ ".\n\nDetailed information:\n" + WorkspaceHelper.printStacktraceToString(e),
					new DemoclesValidationException(e));
		}
	}

	/**
	 * Run validation on the given {@code eClass}, validation errors are stored in
	 * {@code validationStatus}, process errors (such as exceptions) are stored in
	 * {@code processStatus}
	 *
	 * @param eClass
	 * @param validationStatus
	 * @param monitor
	 * @return
	 */
	private IStatus validateEClass(final EClass eClass, final MultiStatus validationStatus,
			final IProgressMonitor monitor) {
		final List<EOperation> eOperations = eClass.getEOperations();
		final SubMonitor subMon = SubMonitor.convert(monitor, "Validating operations in class " + eClass.getName(),
				eOperations.size());
		for (final EOperation eOperation : eOperations) {
			final IStatus cancelStatus = validateEOperation(eOperation, validationStatus, subMon.split(1));
			if (cancelStatus.getSeverity() == IStatus.CANCEL)
				return cancelStatus;
		}
		return Status.OK_STATUS;
	}

	/**
	 * Validates a particular EOperation.
	 *
	 * For the description of the parameters, see
	 * {@link #validateEClass(EClass, MultiStatus, IProgressMonitor)}
	 *
	 * @param eOperation
	 * @param validationStatus
	 * @param monitor
	 * @return
	 */
	private IStatus validateEOperation(final EOperation eOperation, final MultiStatus validationStatus,
			final IProgressMonitor monitor) {
		final SubMonitor subMon = SubMonitor.convert(monitor,
				String.format("Validating %s::%s", eOperation.getEContainingClass().getName(), eOperation.getName()),
				1);

		// Lookup activity in EOperation
		final Activity activity = lookupRootActivity(eOperation);
		if (activity != null) {
			/*
			 * (1) Perform control flow validation
			 */
			final ValidationReport controlFlowValidationReport = performControlFlowValidation(eOperation, activity);

			final EObject scopeCandidate = controlFlowValidationReport.getResult();
			if (controlFlowValidationReport.getErrorMessages().size() == 0 && scopeCandidate == null) {
				throw new RuntimeException(
						"Control flow validation produced no result and reported no errors while analyzing "
								+ getReportableEOperationName(eOperation));
			}
			for (final ErrorMessage message : controlFlowValidationReport.getErrorMessages()) {
				validationStatus.add(ValidationStatus.createValidationStatus(message));
			}
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			/*
			 * (2) Perform scope validation
			 */
			if (isSuccessful(controlFlowValidationReport, Severity.ERROR) && scopeCandidate instanceof Scope) {
				final Scope scope = (Scope) scopeCandidate;
				// Assign unique identifiers to control flow nodes
				scope.accept(ControlflowPackage.eINSTANCE.getControlflowFactory().createIdentifierAllocator());

				// Perform scope validation
				final ValidationReport scopeValidationReport = ResultFactory.eINSTANCE.createValidationReport();
				scopeValidator.setValidationReport(scopeValidationReport);
				performScopeValidation(scopeValidator, eOperation, scope);

				for (final ErrorMessage message : scopeValidationReport.getErrorMessages()) {
					validationStatus.add(ValidationStatus.createValidationStatus(message));
				}

				if (subMon.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
			}
		}
		subMon.worked(1);
		return Status.OK_STATUS;
	}

	@Override
	public final String getTaskName() {
		return TASK_NAME;
	}

	public boolean isSuccessful(final ValidationReport report, final Severity limit) {
		for (final ErrorMessage errorMessage : report.getErrorMessages()) {
			if (errorMessage.getSeverity().compareTo(limit) >= 0) {
				return false;
			}
		}
		return true;
	}

	public Activity lookupRootActivity(final EOperation eOperation) {
		return lookupActivity(loadIntoResourceSet ? resourceSet : null, eOperation);
	}

	public static final Activity lookupActivity(final EOperation eOperation) {
		if (ActivitiesPackage.eINSTANCE.getMoflonEOperation().isInstance(eOperation)) {
			final Activity activity = ((MoflonEOperation) eOperation).getActivity();
			if (activity != null) {
				activity.setOwningOperation(eOperation);
				return activity;
			}
		}
		return null;
	}

	public static final Activity lookupActivity(final ResourceSet resourceSet, final EOperation eOperation) {
		if (ActivitiesPackage.eINSTANCE.getMoflonEOperation().isInstance(eOperation)) {
			final Activity activity = ((MoflonEOperation) eOperation).getActivity();
			if (activity != null) {
				activity.setOwningOperation(eOperation);
				return activity;
			}
		}
		final EAnnotation sdmAnnotation = eOperation.getEAnnotation("SDM");
		if (sdmAnnotation != null) {
			final AdapterResource sdmResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
					DemoclesMethodBodyHandler.SDM_FILE_EXTENSION);
			try {
				// Load SDM resource and handle activity
				final String sdmXMISpec = sdmAnnotation.getDetails().get("XMI");
				load(sdmResource, sdmXMISpec);
				// Handle activity
				final Activity activity = (Activity) sdmResource.getContents().get(0);
				activity.setOwningOperation(eOperation);
			} catch (final IOException exception) {
				// Do nothing
			}

			if (resourceSet != null && sdmResource != null && sdmResource.getResourceSet() == null) {
				resourceSet.getResources().add(sdmResource);
			}
			return (Activity) sdmResource.getContents().get(0);
		} else {
			return null;
		}
	}

	public ValidationReport performControlFlowValidation(final EOperation eOperation, final Activity activity) {
		return performControlFlowValidation(loadIntoResourceSet ? resourceSet : null, eOperation, activity);
	}

	public static final ValidationReport performControlFlowValidation(final ResourceSet resourceSet,
			final EOperation eOperation, final Activity activity) {
		// Initialize control flow validator
		final Validator validator = ControlflowFactory.eINSTANCE.createValidator();
		validator.setStopNodeInForEachComponentSeverity(Severity.WARNING);
		final InefficientBootstrappingBuilder inefficientBuilder = ControlflowFactory.eINSTANCE
				.createInefficientBootstrappingBuilder();
		final SDMActivityGraphBuilder builder = ControlflowFactory.eINSTANCE.createSDMActivityGraphBuilder();
		final DFSGraph graph = DfsFactory.eINSTANCE.createDFSGraph();
		validator.setGraph(graph);
		inefficientBuilder.setGraph(graph);
		builder.setGraph(graph);
		validator.getBuilders().add(builder);
		validator.getBuilders().add(inefficientBuilder);
		inefficientBuilder.setDelegate(builder);

		// Add the DFS graph to the resource set
		final AdapterResource dfsGraphResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
				DemoclesMethodBodyHandler.DFS_FILE_EXTENSION);
		if (resourceSet != null && dfsGraphResource.getResourceSet() == null) {
			resourceSet.getResources().add(dfsGraphResource);
		}
		dfsGraphResource.getContents().add(graph);
		validator.validate(activity, inefficientBuilder);
		return validator.getValidationReport();
	}

	public final void performScopeValidation(final ScopeValidator scopeValidator, final EOperation eOperation,
			final Scope scope) {
		final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
				DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
		if (loadIntoResourceSet && controlFlowResource.getResourceSet() == null) {
			resourceSet.getResources().add(controlFlowResource);
		}
		controlFlowResource.getContents().add(scope);

		// Add this object and method parameters to the (root) scope
		final CFVariable thisObject = DemoclesFactory.eINSTANCE.createCFVariable();
		scope.getVariables().add(thisObject);
		thisObject.setName("this");
		thisObject.setType(eOperation.getEContainingClass());
		thisObject.setLocal(false);
		for (final EParameter eParameter : eOperation.getEParameters()) {
			final CFVariable parameter = DemoclesFactory.eINSTANCE.createCFVariable();
			scope.getVariables().add(parameter);
			parameter.setName(eParameter.getName());
			parameter.setType(eParameter.getEType());
			parameter.setLocal(false);
		}

		// Perform scope validation
		scope.accept(scopeValidator);
	}

	// SDM loader methods
	private static final void load(final Resource resource, final String string) throws IOException {
		final InputStream in = new URIConverter.ReadableInputStream(string);
		resource.load(in, Collections.EMPTY_MAP);
		in.close();
	}

	private final String getReportableEOperationName(final EOperation eOperation) {
		StringBuilder builder = new StringBuilder();
		builder.append(eOperation.getEContainingClass().getName());
		builder.append(".");
		builder.append(eOperation.getName());
		builder.append("(");
		for (int i = 0; i < eOperation.getEParameters().size(); i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(eOperation.getEParameters().get(i).getEType().getName());
		}
		builder.append(")");
		return builder.toString();
	}
}

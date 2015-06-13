package org.moflon.compiler.sdm.democles.eclipse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
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
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.eclipse.job.IMonitoredJob;

import ControlFlow.CFVariable;
import ControlFlow.ControlFlowFactory;
import ControlFlow.Scope;
import DepthFirstSearchGraph.DFSGraph;
import DepthFirstSearchGraph.DepthFirstSearchGraphFactory;
import SDMLanguage.activities.ActivitiesPackage;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.MoflonEOperation;
import ScopeValidation.ScopeValidator;
import Sequencer.InefficientBootstrappingBuilder;
import Sequencer.SDMActivityGraphBuilder;
import Sequencer.SequencerFactory;
import Sequencer.SequencerPackage;
import Sequencer.Validator;
import ValidationResult.ErrorMessage;
import ValidationResult.Severity;
import ValidationResult.ValidationReport;
import ValidationResult.ValidationResultFactory;

public class DemoclesValidatorTask implements IMonitoredJob {
	public static final String TASK_NAME = "SDM validation";

	private final ScopeValidator scopeValidator;
	private final EPackage ePackage;
	private final ResourceSet resourceSet;
	private boolean loadIntoResourceSet;
	
	public DemoclesValidatorTask(final ScopeValidator scopeValidator, final EPackage ePackage) {
		this.scopeValidator = scopeValidator;
		this.ePackage = ePackage;
		this.resourceSet = ePackage.eResource().getResourceSet();
		this.loadIntoResourceSet = true;
	}

	@Override
   public IStatus run(final IProgressMonitor monitor) {
		final MultiStatus validationStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, TASK_NAME + " failed", null);
		final List<EClass> eClasses = CodeGeneratorPlugin.getEClasses(ePackage);
		try {
		   monitor.beginTask("Validating classes in package " + ePackage.getName(), eClasses.size());
			for (final EClass eClass : eClasses) {
				final List<EOperation> eOperations = eClass.getEOperations();
				SubProgressMonitor operationMonitor = new SubProgressMonitor(monitor, 1);
				operationMonitor.beginTask("Validating operations in class " + eClass.getName(), eOperations.size());
				try {
					for (final EOperation eOperation : eOperations) {
						// Lookup activity in EOperation
						final Activity activity = lookupRootActivity(eOperation);
						if (activity != null) {
							// Perform control flow validation
							// LOGGER.info("Validating " + eOperation.getName() + " in class " + eOperation.getEContainingClass().getName() + " in project " + project.getName());
							final ValidationReport controlFlowValidationReport = performControlFlowValidation(eOperation, activity);
							// LOGGER.info("Validation completed with: " + controlFlowValidationReport.getErrorMessages().size() + " messages.");

							final EObject scopeCandidate = controlFlowValidationReport.getResult();
							if (controlFlowValidationReport.getErrorMessages().size() == 0 && scopeCandidate == null) {
								throw new RuntimeException("Control flow validation produced no result and reported no errors while analyzing "
										+ getReportableEOperationName(eOperation));
							}
							for (final ErrorMessage message : controlFlowValidationReport.getErrorMessages()) {
								validationStatus.add(ValidationStatus.createValidationStatus(message));
							}
							if (operationMonitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}

							if (isSuccessful(controlFlowValidationReport, Severity.ERROR) && scopeCandidate instanceof Scope) {
								final Scope scope = (Scope) scopeCandidate;
								// Assign unique identifiers to control flow nodes
								scope.accept(SequencerPackage.eINSTANCE.getSequencerFactory().createIdentifierAllocator());

								// Perform scope validation
								final ValidationReport scopeValidationReport = ValidationResultFactory.eINSTANCE.createValidationReport();
								scopeValidator.setValidationReport(scopeValidationReport);
								performScopeValidation(scopeValidator, eOperation, scope);

								for (final ErrorMessage message : scopeValidationReport.getErrorMessages()) {
									validationStatus.add(ValidationStatus.createValidationStatus(message));
								}

								if (operationMonitor.isCanceled()) {
									return Status.CANCEL_STATUS;
								}
							}
						}
						operationMonitor.worked(1);
					}
				} finally {
					operationMonitor.done();
				}
			}
			return validationStatus.isOK() ? new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), TASK_NAME + " succeeded") : validationStatus;
		} catch (RuntimeException e) {
			return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR,
					"Internal exception occured (probably caused by a bug in the validation module): " + e.getMessage()
					+ " Please report the bug on https://github.com/eMoflon/emoflon-issue-tracking-system/issues", e);
		} finally {
			monitor.done();
		}
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
			final AdapterResource sdmResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.SDM_FILE_EXTENSION);
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
		final Validator validator = SequencerFactory.eINSTANCE.createValidator();
		validator.setStopNodeInForEachComponentSeverity(Severity.WARNING);
		final InefficientBootstrappingBuilder inefficientBuilder = SequencerFactory.eINSTANCE.createInefficientBootstrappingBuilder();
		final SDMActivityGraphBuilder builder = SequencerFactory.eINSTANCE.createSDMActivityGraphBuilder();
		final DFSGraph graph = DepthFirstSearchGraphFactory.eINSTANCE.createDFSGraph();
		validator.setGraph(graph);
		inefficientBuilder.setGraph(graph);
		builder.setGraph(graph);
		validator.getBuilders().add(builder);
		validator.getBuilders().add(inefficientBuilder);
		inefficientBuilder.setDelegate(builder);

		// Add the DFS graph to the resource set
		final AdapterResource dfsGraphResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.DFS_FILE_EXTENSION);
		if (resourceSet != null && dfsGraphResource.getResourceSet() == null) {
			resourceSet.getResources().add(dfsGraphResource);
		}
		dfsGraphResource.getContents().add(graph);
		validator.validate(activity, inefficientBuilder);
		return validator.getValidationReport();
	}

	public final void performScopeValidation(final ScopeValidator scopeValidator, final EOperation eOperation, final Scope scope) {
		final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
		if (loadIntoResourceSet && controlFlowResource.getResourceSet() == null) {
			resourceSet.getResources().add(controlFlowResource);
		}
		controlFlowResource.getContents().add(scope);

		// Add this object and method parameters to the (root) scope
		final CFVariable thisObject = ControlFlowFactory.eINSTANCE.createCFVariable();
		scope.getVariables().add(thisObject);
		thisObject.setName("this");
		thisObject.setType(eOperation.getEContainingClass());
		thisObject.setLocal(false);
		for (final EParameter eParameter : eOperation.getEParameters()) {
			final CFVariable parameter = ControlFlowFactory.eINSTANCE.createCFVariable();
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

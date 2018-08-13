package org.moflon.compiler.sdm.democles;

import java.util.Map.Entry;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.democles.codegen.ImportManager;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.TemplateInvocation;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.emf.codegen.AbstractMoflonClassGeneratorAdapter;
import org.moflon.emf.injection.ide.InjectionManager;
import org.moflon.sdm.runtime.democles.Scope;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class DemoclesClassGeneratorAdapter extends AbstractMoflonClassGeneratorAdapter {
	private ImportManager democlesImportManager = ImportManager.EMPTY_IMPORT_MANAGER;

	protected DemoclesClassGeneratorAdapter(final DemoclesGeneratorAdapterFactory generatorAdapterFactory) {
		super(generatorAdapterFactory);
	}

	public static DemoclesClassGeneratorAdapter createDemoclesClassGeneratorAdapter(
			final DemoclesGeneratorAdapterFactory generatorAdapterFactory) {
		return new DemoclesClassGeneratorAdapter(generatorAdapterFactory);
	}

	@Override
	public DemoclesGeneratorAdapterFactory getAdapterFactory() {
		return (DemoclesGeneratorAdapterFactory) adapterFactory;
	}

	@Override
	public boolean isAdapterForType(final Object type) {
		return type instanceof Class && ((Class<?>) type).isAssignableFrom(getClass());
	}

	@Override
	protected void generateClass(final GenClass genClass, final Monitor monitor) {
		if (!genClass.isInterface()) {
			// Resolve imports in a code generation dry-run
			democlesImportManager = new DemoclesToGenModelImportManager(genClass.getGenModel());
			for (GenOperation genOperation : genClass.getImplementedGenOperations()) {
				getGeneratedMethodBody(genOperation.getEcoreOperation());
			}
			getInjectedCode(true);
			handleImports(true);

			democlesImportManager = new GenModelToDemoclesImportManager(genClass.getGenModel());
		}
		super.generateClass(genClass, monitor);
	}

	@Override
	public boolean hasGeneratedMethodBody(final EOperation eOperation) {
		return super.hasGeneratedMethodBody(eOperation) || eOperation != null && EcoreUtil
				.getExistingAdapter(eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION) != null;
	}

	@Override
	public String getGeneratedMethodBody(final EOperation eOperation) {
		String generatedMethodBody = null;

		final InjectionManager injectionManager = getAdapterFactory().getInjectionManager();
		if (injectionManager != null && injectionManager.hasModelCode(eOperation)) {
			final String modelCode = injectionManager.getModelCode(eOperation);
			generatedMethodBody = modelCode;
		} else {
			final AdapterResource cfResource = (AdapterResource) EcoreUtil.getExistingAdapter(eOperation,
					DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
			if (cfResource != null && !cfResource.getContents().isEmpty()
					&& cfResource.getContents().get(0) instanceof Scope) {
				final Scope scope = (Scope) cfResource.getContents().get(0);

				final TemplateConfigurationProvider templateProvider = getAdapterFactory()
						.getTemplateConfigurationProvider();

				final STGroup group = templateProvider
						.getTemplateGroup(DefaultTemplateConfiguration.CONTROL_FLOW_GENERATOR);
				final ST template = group.getInstanceOf("/" + DefaultTemplateConfiguration.CONTROL_FLOW_GENERATOR + "/"
						+ scope.getClass().getSimpleName());
				template.add("scope", scope);
				template.add("importManager", democlesImportManager);
				generatedMethodBody = template.render();
			}
		}

		if (generatedMethodBody == null) {
			generatedMethodBody = MoflonUtil.DEFAULT_METHOD_BODY;
		}

		return generatedMethodBody;
	}

	/**
	 * This class produces the pattern matching code that is usually appended to a
	 * class implementation file
	 *
	 * @param isImplementation
	 *            whether the current class is the implementation of the interface.
	 *            The pattern matching code is only added if isImplementation is
	 *            true.
	 *
	 * @return the generated Java code
	 */
	@Override
	public String getInjectedCode(final boolean isImplementation) {
		final StringBuilder code = new StringBuilder();
		if (isImplementation) {
			final GenClass genClass = (GenClass) generatingObject;
			final TemplateConfigurationProvider templateProvider = getAdapterFactory()
					.getTemplateConfigurationProvider();

			final EList<Adapter> adapters = genClass.getEcoreClass().eAdapters();
			for (int i = 0; i < adapters.size(); i++) {
				final Adapter adapter = adapters.get(i);
				if (adapter.isAdapterForType(SearchPlanAdapter.class)) {
					final SearchPlanAdapter searchPlanAdapter = (SearchPlanAdapter) adapter;
					final String patternType = searchPlanAdapter.getPatternType();
					final OperationSequenceCompiler operationSequenceCompiler = templateProvider
							.getOperationSequenceCompiler(patternType);
					final TemplateInvocation template = searchPlanAdapter
							.prepareTemplateInvocation(operationSequenceCompiler, democlesImportManager);

					ST st = templateProvider.getTemplateGroup(patternType).getInstanceOf(template.getTemplateName());
					for (Entry<String, Object> entry : template.getAttributes().entrySet()) {
						st.add(entry.getKey(), entry.getValue());
					}
					code.append(st.render());
					code.append("\n\n");
				}
			}
		}
		code.append(super.getInjectedCode(isImplementation));
		return code.length() > 0 ? code.toString() : null;
	}
}

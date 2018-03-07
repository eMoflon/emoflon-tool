package org.moflon.compiler.sdm.democles.attributes.eclipse;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.attributes.AttributeConstraintCodeGeneratorConfig;
import org.moflon.compiler.sdm.democles.eclipse.DemoclesValidationProcess;

public class AttributeConstraintAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes") final Class adapterType) {
		if (adaptableObject instanceof MoflonCodeGenerator
				&& AttributeConstraintCodeGeneratorConfig.class == adapterType) {
			final MoflonCodeGenerator process = (MoflonCodeGenerator) adaptableObject;
			IProject project = process.getEcoreFile().getProject();
			final AttributeConstraintCodeGeneratorConfig defaultCodeGeneratorConfig = new AttributeConstraintCodeGeneratorConfig(
					process.getResourceSet(), project, process.getPreferencesStorage());
			return new DemoclesMethodBodyHandler(process.getResourceSet(), defaultCodeGeneratorConfig);
		} else if (adaptableObject instanceof DemoclesValidationProcess
				&& AttributeConstraintCodeGeneratorConfig.class == adapterType) {
			final DemoclesValidationProcess process = (DemoclesValidationProcess) adaptableObject;
			IProject project = process.getEcoreFile().getProject();
			return new AttributeConstraintCodeGeneratorConfig(process.getResourceSet(), project,
					process.getPreferencesStorage());
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { AttributeConstraintCodeGeneratorConfig.class };
	}
}

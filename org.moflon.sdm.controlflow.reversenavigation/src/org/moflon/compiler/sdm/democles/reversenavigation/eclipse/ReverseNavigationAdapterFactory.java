package org.moflon.compiler.sdm.democles.reversenavigation.eclipse;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.eclipse.DemoclesValidationProcess;
import org.moflon.compiler.sdm.democles.reversenavigation.ReverseNavigationCodeGeneratorConfig;
import org.moflon.emf.build.GenericMoflonProcess;

public class ReverseNavigationAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes") final Class adapterType) {
		if (adaptableObject instanceof MoflonCodeGenerator
				&& ReverseNavigationCodeGeneratorConfig.class == adapterType) {
			final GenericMoflonProcess process = (GenericMoflonProcess) adaptableObject;
			IProject project = process.getEcoreFile().getProject();
			final ReverseNavigationCodeGeneratorConfig defaultCodeGeneratorConfig = new ReverseNavigationCodeGeneratorConfig(
					process.getResourceSet(), project, process.getPreferencesStorage());
			return new DemoclesMethodBodyHandler(process.getResourceSet(), defaultCodeGeneratorConfig);
		} else if (adaptableObject instanceof DemoclesValidationProcess
				&& ReverseNavigationCodeGeneratorConfig.class == adapterType) {
			final GenericMoflonProcess process = (GenericMoflonProcess) adaptableObject;
			IProject project = process.getEcoreFile().getProject();
			return new ReverseNavigationCodeGeneratorConfig(process.getResourceSet(), project,
					process.getPreferencesStorage());
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { ReverseNavigationCodeGeneratorConfig.class };
	}

}

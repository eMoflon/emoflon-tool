package org.moflon.compiler.sdm.democles.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.compiler.sdm.democles.DefaultCodeGeneratorConfig;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;

public class DemoclesAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes") final Class adapterType) {
		if (adaptableObject instanceof DemoclesValidationProcess && DefaultCodeGeneratorConfig.class == adapterType) {
			final DemoclesValidationProcess process = (DemoclesValidationProcess) adaptableObject;
			return new DefaultCodeGeneratorConfig(process.getResourceSet(), process.getPreferencesStorage());
		} else if (adaptableObject instanceof MoflonCodeGenerator && (DemoclesMethodBodyHandler.class == adapterType
				|| DefaultCodeGeneratorConfig.class == adapterType)) {
			final MoflonCodeGenerator process = (MoflonCodeGenerator) adaptableObject;
			final DefaultCodeGeneratorConfig defaultCodeGeneratorConfig = new DefaultCodeGeneratorConfig(
					process.getResourceSet(), process.getPreferencesStorage());
			return new DemoclesMethodBodyHandler(process.getResourceSet(), defaultCodeGeneratorConfig);
		} else if (adaptableObject instanceof IFile && EclipseErrorReporter.class == adapterType) {
			return new EclipseErrorReporter((IFile) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { DemoclesMethodBodyHandler.class, DefaultCodeGeneratorConfig.class,
				EclipseErrorReporter.class };
	}
}

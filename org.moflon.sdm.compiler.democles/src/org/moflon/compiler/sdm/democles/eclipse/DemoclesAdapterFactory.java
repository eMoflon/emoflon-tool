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
			return new DefaultCodeGeneratorConfig(process.getResourceSet());
		} else if (adaptableObject instanceof MoflonCodeGenerator && 
				(DemoclesMethodBodyHandler.class == adapterType || DefaultCodeGeneratorConfig.class == adapterType)) {
			final MoflonCodeGenerator process = (MoflonCodeGenerator) adaptableObject;
			final DefaultCodeGeneratorConfig defaultCodeGeneratorConfig =
					new DefaultCodeGeneratorConfig(process.getResourceSet());
			return new DemoclesMethodBodyHandler(process.getResourceSet(), defaultCodeGeneratorConfig);
		} else if (adaptableObject instanceof IFile && MonitoredSDMValidator.class == adapterType) {
			return new MonitoredSDMValidator((IFile) adaptableObject);
		} else if (adaptableObject instanceof IFile && MonitoredSDMValidatorWithDumping.class == adapterType) {
		   return new MonitoredSDMValidatorWithDumping((IFile) adaptableObject);
		} else if (adaptableObject instanceof IFile && EclipseErrorReporter.class == adapterType) {
			return new EclipseErrorReporter((IFile) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { MonitoredSDMValidator.class, DemoclesMethodBodyHandler.class, 
				DefaultCodeGeneratorConfig.class, EclipseErrorReporter.class };
	}
}

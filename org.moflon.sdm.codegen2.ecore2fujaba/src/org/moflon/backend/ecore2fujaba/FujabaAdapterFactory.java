package org.moflon.backend.ecore2fujaba;

import org.eclipse.core.runtime.IAdapterFactory;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;

public final class FujabaAdapterFactory implements IAdapterFactory {

	@Override
	public final Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof MoflonCodeGenerator && FujabaMethodBodyHandler.class == adapterType) {
			MoflonCodeGenerator process = (MoflonCodeGenerator) adaptableObject;
			return new FujabaMethodBodyHandler(process.getResourceSet(), process.getAllResources(), process.getEcoreFile(), process.getMoflonProperties());
		}
		return null;
	}

	@Override
	public final Class<?>[] getAdapterList() {
		return new Class[] { FujabaMethodBodyHandler.class };
	}
}

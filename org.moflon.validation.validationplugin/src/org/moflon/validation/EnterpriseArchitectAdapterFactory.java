package org.moflon.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;

public class EnterpriseArchitectAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
   @Override
	public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes") final Class adapterType) {
		if (adaptableObject instanceof IProject && EnterpriseArchitectValidationHelper.class == adapterType) {
			return new EnterpriseArchitectValidationHelper((IProject) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { EnterpriseArchitectValidationHelper.class };
	}

}

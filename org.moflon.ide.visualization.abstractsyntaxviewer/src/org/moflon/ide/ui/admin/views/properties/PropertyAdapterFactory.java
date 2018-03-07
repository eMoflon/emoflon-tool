package org.moflon.ide.ui.admin.views.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.ui.views.properties.IPropertySource;

public class PropertyAdapterFactory implements IAdapterFactory {

	public PropertyAdapterFactory() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adapterType == IPropertySource.class) {
			if (adaptableObject instanceof EObject) {
				ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
						ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

				adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
				AdapterFactoryContentProvider afcp = new AdapterFactoryContentProvider(adapterFactory);
				return afcp.getPropertySource(adaptableObject);
			} else {
				throw new RuntimeException(
						"Unsupported type '" + (adaptableObject != null ? adaptableObject.getClass().getName() : null)
								+ "' for property source");
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}

package org.moflon.ide.ui.admin.views.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EObjectItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;

import DebugLanguage.DebugCorrespondence;
import DebugLanguage.DebugEObjectProxy;

public class DebugEObjectProxyItemProvider extends EObjectItemProvider// ReflectiveItemProvider
{

   public DebugEObjectProxyItemProvider(AdapterFactory adapterFactory)
   {
      super(adapterFactory);
   }

   /**
    * Returns the property image for the specified type. Implementations of {@link #getPropertyImage() getPropertyImage}
    * typically call this method.
    * 
    * This method is a copy.
    * 
    * @see WrapperItemProvider#getPropertyImage(Class<?> typeClass)
    */
   protected Object getPropertyImage(Class<?> typeClass)
   {
      if (typeClass == Boolean.TYPE || typeClass == Boolean.class)
      {
         return ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE;
      } else if (typeClass == Byte.TYPE || typeClass == Byte.class || typeClass == Integer.TYPE || typeClass == Integer.class || typeClass == Long.TYPE
            || typeClass == Long.class || typeClass == Short.TYPE || typeClass == Short.class)
      {
         return ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE;
      } else if (typeClass == Character.TYPE || typeClass == Character.class || typeClass == String.class)
      {
         return ItemPropertyDescriptor.TEXT_VALUE_IMAGE;
      } else if (typeClass == Double.TYPE || typeClass == Double.class || typeClass == Float.TYPE || typeClass == Float.class)
      {
         return ItemPropertyDescriptor.REAL_VALUE_IMAGE;
      }

      return ItemPropertyDescriptor.GENERIC_VALUE_IMAGE;
   }

   /**
    * Based on {@link ReflectiveItemProvider#getPropertyDescriptor(Object, Object)}.
    * 
    * @see org.eclipse.emf.ecore.provider.EObjectItemProvider#getPropertyDescriptors(java.lang.Object)
    */
   @Override
   public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
   {
      // if (itemPropertyDescriptors == null)
      {
         itemPropertyDescriptors = new ArrayList<IItemPropertyDescriptor>();

         if (object instanceof DebugCorrespondence)
         {
            DebugCorrespondence correspondence = (DebugCorrespondence) object;
            EStructuralFeature feature = correspondence.eClass().getEStructuralFeature("name");
            itemPropertyDescriptors.add(new ItemPropertyDescriptor(adapterFactory, "Type", null, feature, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
         } else if (object instanceof DebugEObjectProxy)
         {
            DebugEObjectProxy proxy = (DebugEObjectProxy) object;

            proxy.getAttributeProxy().forEach(
                  aproxy -> {
                     Object image = null;
                     if (aproxy.getValue() == null)
                     {
                        image = ItemPropertyDescriptor.GENERIC_VALUE_IMAGE;
                     } else
                     {
                        image = getPropertyImage(aproxy.getValue().getClass());
                     }

                     itemPropertyDescriptors.add(new DebugEobjectProxyPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                           aproxy.getName(), "DADA", null/* value */, false, image, aproxy));
                  });
         } else
         {
            throw new RuntimeException("PropertyDescriptor for object '" + object + "' +" + (object == null ? "" : "[" + object.getClass().getName() + "]")
                  + "+is not supported. Please implement or contact the eMoflon Team.");
         }
      }
      return itemPropertyDescriptors;

   }
}

package org.moflon.ide.ui.admin.views.properties;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

import DebugLanguage.AttributeProxy;

public class DebugEobjectProxyPropertyDescriptor extends ItemPropertyDescriptor
{
   AttributeProxy aproxy;

   public DebugEobjectProxyPropertyDescriptor(AdapterFactory adapterFactory, String name, String string, EStructuralFeature value, boolean b,
         Object genericValueImage, AttributeProxy aproxy)
   {
      super(adapterFactory, name, string, value, b, genericValueImage);
      this.itemDelegator = new DebugItemDelegator(adapterFactory);
      this.aproxy = aproxy;
   }

   protected class DebugItemDelegator extends ItemDelegator
   {

      public DebugItemDelegator(AdapterFactory adapterFactory)
      {
         super(adapterFactory);
      }

      @Override
      public String getText(Object object)
      {
         return aproxy.getValue() == null ? "null" : aproxy.getValue().toString();
      }

   }

}

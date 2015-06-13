package org.moflon.ide.debug.core.model;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.moflon.ide.debug.core.DebugCorePlugin;
import org.xml.sax.InputSource;

abstract public class MoflonDebugElement extends DebugElement
{
   public MoflonDebugElement(IDebugTarget target)
   {
      super(target);
   }

   @Override
   public String getModelIdentifier()
   {
      return DebugCorePlugin.ID_MOFLON_DEBUG_MODEL;
   }

   public static EObject convertToEObject(String xmlString) throws IOException
   {
      XMIResourceImpl resource = new XMIResourceImpl();
      resource.setEncoding("UTF-8");
      resource.load(new InputSource(new StringReader(xmlString)), null);

      return resource.getContents().get(0);
   }

}

package org.moflon.tgg.algorithm.synchronization;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

public class EmfXmlConverter
{

   public static String convertToXml(EObject eObject) throws IOException
   {
      XMLResourceImpl resource = new XMLResourceImpl();
      XMLProcessor processor = new XMLProcessor();
      resource.setEncoding("UTF-8");
      resource.getContents().add(eObject);
      return processor.saveToString(resource, null);
   }
   
}
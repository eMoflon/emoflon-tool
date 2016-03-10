package org.moflon.maave.tool.smt.smtlib;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;



public class SmtLibHelper
{
   private static final String BUILDIN_SMT_LIB_PROJECT="org.moflon.maave.tool.smt.smtlib";
   private static final URI BUILDIN_SMT_LIB_URI=URI.createPlatformPluginURI("/"+BUILDIN_SMT_LIB_PROJECT+"/lib/SMTLib.xmi",true);
   
   public static SMTLib getSMTLib()
   {
      Resource res=loadSMTLibResource(BUILDIN_SMT_LIB_URI,false);
      if(res==null){
         return null;
      }
      return (SMTLib) res.getContents().get(0);
   }
   
   
   private static Resource loadSMTLibResource(URI uri, boolean createOnDemand){
      ResourceSet rset= new ResourceSetImpl();
      rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
      rset.getPackageRegistry().put(SmtlibPackage.eNS_URI, SmtlibPackage.eINSTANCE);
      rset.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(System.getenv("CurrentWSLoc")+ "\\"));
      rset.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/"+BUILDIN_SMT_LIB_PROJECT+"/", true), URI.createPlatformResourceURI("/"+BUILDIN_SMT_LIB_PROJECT+"/", true));
      Resource res=null;

      res=rset.getResource(uri, true);

      return res;

   }
}

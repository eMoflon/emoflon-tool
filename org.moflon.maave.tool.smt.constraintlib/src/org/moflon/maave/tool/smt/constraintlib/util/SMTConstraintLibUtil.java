package org.moflon.maave.tool.smt.constraintlib.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.moflon.maave.tool.smt.constraintlib.ConstraintlibPackage;
import org.moflon.maave.tool.smt.constraintlib.SMTConstraintLibrary;




public class SMTConstraintLibUtil
{
   private static final URI BUILDIN_SMT_CONST_LIB_URI=URI.createPlatformPluginURI("/org.moflon.maave.tool.smt.constraintlib/lib/SMTConstraintLibrary.xmi",true);



   public static SMTConstraintLibrary getBuildInSMTConstraintLibrary(){

      Resource res=loadAttributeConstraintLibraryResource(BUILDIN_SMT_CONST_LIB_URI,false);
      if(res==null){
         return null;
      }
      return (SMTConstraintLibrary) res.getContents().get(0);



   }

   private static Resource loadAttributeConstraintLibraryResource(URI uri, boolean createOnDemand){
      ResourceSet rset= new ResourceSetImpl();
      rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
      rset.getPackageRegistry().put(ConstraintlibPackage.eNS_URI, ConstraintlibPackage.eINSTANCE);
      rset.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(System.getenv("CurrentWSLoc")+ "\\"));
      rset.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/org.moflon.maave.tool.smt.constraintlib/", true), URI.createPlatformResourceURI("/org.moflon.maave.tool.smt.constraintlib/", true));
      Resource res=null;

      res=rset.getResource(uri, true);

      return res;

   }

}

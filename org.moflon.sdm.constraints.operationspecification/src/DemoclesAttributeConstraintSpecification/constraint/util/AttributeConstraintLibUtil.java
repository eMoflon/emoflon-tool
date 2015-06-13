package DemoclesAttributeConstraintSpecification.constraint.util;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;

import DemoclesAttributeConstraintSpecification.AttributeConstraintLibrary;
import DemoclesAttributeConstraintSpecification.DemoclesAttributeConstraintSpecificationFactory;
import DemoclesAttributeConstraintSpecification.DemoclesAttributeConstraintSpecificationPackage;


public class AttributeConstraintLibUtil
{
   private static final URI BUILDIN_ATTR_CONST_LIB_URI=URI.createPlatformPluginURI("/DemoclesAttributeConstraintSpecification/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi",true);
   
   
   
   public static AttributeConstraintLibrary getBuildInAttributeConstraintLibrary(){

      Resource res=loadAttributeConstraintLibraryResource(BUILDIN_ATTR_CONST_LIB_URI,false);
      if(res==null){
         return null;
      }
      return (AttributeConstraintLibrary) res.getContents().get(0);
      


   }
   public static AttributeConstraintLibrary loadUserDefinedAttributeConstraintLibrary(IProject project){

      String filePath="/"+project.getName()+"/lib/"+project.getName()+"AttributeConstraintsLib.xmi";
      URI libFileURI=URI.createPlatformResourceURI(filePath, true);
      Resource res=loadAttributeConstraintLibraryResource(libFileURI, false);
      if(res==null){
         return null;
      }
      return (AttributeConstraintLibrary) res.getContents().get(0);
   }
   
   public static AttributeConstraintLibrary getUserDefinedAttributeConstraintLibrary(IProject project){
      String fileName=project.getName()+"AttributeConstraintsLib";
      String filePath="/"+project.getName()+"/lib/"+fileName+".xmi";
      URI libFileURI=URI.createPlatformResourceURI(filePath, true);
      Resource res=loadAttributeConstraintLibraryResource(libFileURI, true);
      AttributeConstraintLibrary attributeConstraintLibrary=null;
      if(res.getContents().size()>0){
         attributeConstraintLibrary=(AttributeConstraintLibrary) res.getContents().get(0);
      }
      if(attributeConstraintLibrary==null){
         attributeConstraintLibrary=DemoclesAttributeConstraintSpecificationFactory.eINSTANCE.createAttributeConstraintLibrary();
         attributeConstraintLibrary.setPrefix(fileName);
         res.getContents().add(attributeConstraintLibrary);
      }
      return attributeConstraintLibrary;
      
   }
   private static Resource loadAttributeConstraintLibraryResource(URI uri, boolean createOnDemand){
      ResourceSet rset= CodeGeneratorPlugin.createDefaultResourceSet();
      rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
      rset.getPackageRegistry().put(DemoclesAttributeConstraintSpecificationPackage.eNS_URI, DemoclesAttributeConstraintSpecificationPackage.eINSTANCE);
      Resource res=null;
      if (rset.getURIConverter().exists(uri, null)) {
         res=rset.getResource(uri, true);
      }else if(createOnDemand){
         res=rset.createResource(uri);
      }
      return res;

   }
   public static void saveAttributeConstraintLibrary(AttributeConstraintLibrary attributeConstraintLibrary){
      try
      {
         attributeConstraintLibrary.eResource().save(null);
      } catch (IOException e)
      {
           e.printStackTrace();
      }
   }
   
}

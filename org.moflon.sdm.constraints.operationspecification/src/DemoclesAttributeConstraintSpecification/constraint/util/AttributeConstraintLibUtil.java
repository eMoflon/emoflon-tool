package DemoclesAttributeConstraintSpecification.constraint.util;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.moflno.sdm.constraints.operationspecification.AttributeConstraintsOperationActivator;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

import DemoclesAttributeConstraintSpecification.AttributeConstraintLibrary;
import DemoclesAttributeConstraintSpecification.DemoclesAttributeConstraintSpecificationFactory;
import DemoclesAttributeConstraintSpecification.DemoclesAttributeConstraintSpecificationPackage;

/**
 * Utility class for loading and storing the built-in and user-defined attribute constraint libraries.
 */
public final class AttributeConstraintLibUtil
{
   private AttributeConstraintLibUtil(){
      throw new UtilityClassNotInstantiableException();
   }
   
   public static AttributeConstraintLibrary getBuildInAttributeConstraintLibrary()
   {

      Resource res = loadAttributeConstraintLibraryResource(getURIOfBuiltInLibrary(), false);
      if (res == null)
      {
         return null;
      }
      return (AttributeConstraintLibrary) res.getContents().get(0);

   }

   public static URI getURIOfBuiltInLibrary()
   {
      return URI.createPlatformPluginURI(
            "/" + AttributeConstraintsOperationActivator.getBundleId() + "/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi", true);
   }

   public static AttributeConstraintLibrary loadUserDefinedAttributeConstraintLibrary(final IProject project)
   {

      String filePath = "/" + project.getName() + "/lib/" + project.getName() + "AttributeConstraintsLib.xmi";
      URI libFileURI = URI.createPlatformResourceURI(filePath, true);
      Resource res = loadAttributeConstraintLibraryResource(libFileURI, false);
      if (res == null)
      {
         return null;
      }
      return (AttributeConstraintLibrary) res.getContents().get(0);
   }

   public static AttributeConstraintLibrary getUserDefinedAttributeConstraintLibrary(final IProject project)
   {
      String fileName = project.getName() + "AttributeConstraintsLib";
      URI libFileURI = URI.createPlatformResourceURI("/" + project.getName() + "/lib/" + fileName + ".xmi", true);
      Resource res = loadAttributeConstraintLibraryResource(libFileURI, true);
      AttributeConstraintLibrary attributeConstraintLibrary = null;
      if (res.getContents().size() > 0)
      {
         attributeConstraintLibrary = (AttributeConstraintLibrary) res.getContents().get(0);
      }
      if (attributeConstraintLibrary == null)
      {
         attributeConstraintLibrary = DemoclesAttributeConstraintSpecificationFactory.eINSTANCE.createAttributeConstraintLibrary();
         attributeConstraintLibrary.setPrefix(fileName);
         res.getContents().add(attributeConstraintLibrary);
      }
      return attributeConstraintLibrary;

   }

   private static Resource loadAttributeConstraintLibraryResource(final URI uri, final boolean createOnDemand)
   {
      ResourceSet rset = CodeGeneratorPlugin.createDefaultResourceSet();
      rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
      rset.getPackageRegistry().put(DemoclesAttributeConstraintSpecificationPackage.eNS_URI, DemoclesAttributeConstraintSpecificationPackage.eINSTANCE);
      Resource res = null;
      if (rset.getURIConverter().exists(uri, null))
      {
         res = rset.getResource(uri, true);
      } else if (createOnDemand)
      {
         res = rset.createResource(uri);
      }
      return res;

   }

   public static void saveAttributeConstraintLibrary(final AttributeConstraintLibrary attributeConstraintLibrary)
   {
      try
      {
         attributeConstraintLibrary.eResource().save(null);
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }

}

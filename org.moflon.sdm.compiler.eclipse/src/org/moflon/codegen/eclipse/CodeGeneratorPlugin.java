package org.moflon.codegen.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CodeGeneratorPlugin implements BundleActivator
{
   public static final String EMOFLON_JOB_NAME = "EMoflon Job";

   private static String BUNDLE_ID;

   //private static final EPackage.Registry DEFAULT_EMOFLON_EPACKAGE_REGISTRY = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);

   //   public static final void registerEMoflonDefaults()
   //   {
   //      DEFAULT_EMOFLON_EPACKAGE_REGISTRY.put("http://www.eclipse.org/emf/2002/GenModel", new PackageDescriptor("org.eclipse.emf.codegen.ecore",
   //            "org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage", "org.moflon.codegen.eclipse", "org.moflon.eclipse.genmodel.MoflonGenModelFactory"));
   //   }

   @Override
   public void start(final BundleContext context) throws Exception
   {
      BUNDLE_ID = context.getBundle().getSymbolicName();
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      BUNDLE_ID = null;
   }

   /**
    * @deprecated Use {@link eMoflonEMFUtil#createDefaultResourceSet()} directly
    */
   @Deprecated
   public static final ResourceSet createDefaultResourceSet()
   {
      return eMoflonEMFUtil.createDefaultResourceSet();
   }

   /**
    * @deprecated Use {@link WorkspaceHelper#getPluginId(Class)}
    */
   @Deprecated
   public static final String getModuleID()
   {
      if (BUNDLE_ID == null)
      {
         throw new NullPointerException();
      } else
      {
         return BUNDLE_ID;
      }
   }

   /**
    * @deprecated use {@link eMoflonEMFUtil#createPluginToResourceMapping(ResourceSet)}
    */
   @Deprecated
   public static void createPluginToResourceMapping(ResourceSet set) throws CoreException {
     eMoflonEMFUtil.createPluginToResourceMapping(set);
   }

   //TODO@rkluge: Remove, appears to be unused 2017-12-19
   //   public static final List<Resource> exploreDependentResources(final Resource initialResource)
   //   {
   //      final UniqueEList<Resource> result = new UniqueEList<Resource>();
   //      result.add(initialResource);
   //      for (int i = 0; i < result.size(); i++)
   //      {
   //         final Resource resource = result.get(i);
   //         for (final TreeIterator<EObject> j = resource.getAllContents(); j.hasNext();)
   //         {
   //            final EObject eObject = j.next();
   //            if (eObject instanceof EDataType)
   //            {
   //               j.prune();
   //            }
   //            for (final EObject eCrossReference : eObject.eCrossReferences())
   //            {
   //               if (eCrossReference instanceof EClass)
   //               {
   //                  final EPackage referencedEPackage = ((EClass) eCrossReference).getEPackage();
   //                  if (resource != referencedEPackage.eResource())
   //                  {
   //                     result.add(referencedEPackage.eResource());
   //                  }
   //               }
   //            }
   //         }
   //      }
   //      return result;
   //   }
}

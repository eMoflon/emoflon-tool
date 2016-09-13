package org.moflon.codegen.eclipse;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl.URIMap;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.eclipse.resource.GenModelResourceFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.SDMCodeGeneratorIds;

public class CodeGeneratorPlugin implements BundleActivator
{
   public static final int UNKNOWN = 0;

   public static final int DEPLOYED_PLUGIN = 1;

   public static final int WORKSPACE_PLUGIN_PROJECT = 2;

   public static final int WORKSPACE_PROJECT = 3;

   public static final int DEPENDENCY_TYPE_COUNT = 3;

   public static final String EMOFLON_JOB_NAME = "EMoflon Job";

   private static String BUNDLE_ID;

   private static final EPackage.Registry DEFAULT_EMOFLON_EPACKAGE_REGISTRY = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);

   public static final void registerEMoflonDefaults()
   {
      DEFAULT_EMOFLON_EPACKAGE_REGISTRY.put("http://www.eclipse.org/emf/2002/GenModel", new PackageDescriptor("org.eclipse.emf.codegen.ecore",
            "org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage", "org.moflon.codegen.eclipse", "org.moflon.eclipse.genmodel.MoflonGenModelFactory"));
   }

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

   public static final ResourceSet createDefaultResourceSet()
   {
      return eMoflonEMFUtil.createDefaultResourceSet();
   }

   public static final ResourceSet setResourceSetDefaultsForSDMCompilation(final ResourceSet set)
   {
      set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("genmodel", new GenModelResourceFactory());
      set.setPackageRegistry(new EPackageRegistryImpl(DEFAULT_EMOFLON_EPACKAGE_REGISTRY));
      return set;
   }

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

   static final IStatus validateResourceSet(final ResourceSet resourceSet, final String taskName, final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask("Checking errors in the " + taskName + " task", resourceSet.getResources().size());
         MultiStatus status = new MultiStatus(CodeGeneratorPlugin.getModuleID(), IStatus.OK, taskName + " failed", null);
         for (Resource resource : resourceSet.getResources())
         {
            for (Diagnostic diagnostic : resource.getErrors())
            {
               Exception exception = diagnostic instanceof Exception ? (Exception) diagnostic : null;
               status.add(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, diagnostic.getMessage(), exception));
            }
            monitor.worked(1);
         }
         return status;
      } finally
      {
         monitor.done();
      }
   }

   /**
    * Returns the code generator configured in moflon.properties.xmi
    */
   public static final String getMethodBodyHandler(final MoflonPropertiesContainer moflonProperties)
   {
      SDMCodeGeneratorIds handlerId = moflonProperties.getSdmCodegeneratorHandlerId().getValue();
      return handlerId.getLiteral();
   }

   public static final List<EClass> getEClasses(final EPackage ePackage)
   {
      final List<EClass> result = new LinkedList<EClass>();
      for (final TreeIterator<EObject> iterator = ePackage.eAllContents(); iterator.hasNext();)
      {
         final EObject eObject = iterator.next();
         if (EcorePackage.eINSTANCE.getEClass().isInstance(eObject))
         {
            result.add((EClass) eObject);
            iterator.prune();
         }
      }
      return result;
   }

   public static final URI getDefaultProjectRelativeEcoreFileURI(final IProject project)
   {
      final String ecoreFileName = MoflonUtil.lastCapitalizedSegmentOf(project.getName());
      return URI.createURI(WorkspaceHelper.MODEL_FOLDER + "/" + ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
   }

   public static final URI getDefaultEcoreFileURI(final IProject project)
   {
      return getDefaultProjectRelativeEcoreFileURI(project).resolve(URI.createPlatformResourceURI(project.getName() + "/", true));
   }

   public static final void createPluginToResourceMapping(final ResourceSet set, final IProject project) throws CoreException
   {
      if (project.isAccessible() && project.hasNature(WorkspaceHelper.PLUGIN_NATURE_ID))
      {
         final IPluginModelBase pluginModel = PluginRegistry.findModel(project);
         if (pluginModel != null)
         {
            // Plugin projects in the workspace
            final String pluginID = pluginModel.getBundleDescription().getSymbolicName();
            final URI pluginURI = URI.createPlatformPluginURI(pluginID + "/", true);
            final URI resourceURI = URI.createPlatformResourceURI(project.getName() + "/", true);
            set.getURIConverter().getURIMap().put(pluginURI, resourceURI);
         }
      }
   }

   public static final void createPluginToResourceMapping(final ResourceSet set, final IProgressMonitor monitor) throws CoreException {
	   final IProject[] workspaceProjects =
			   ResourcesPlugin.getWorkspace().getRoot().getProjects();
	   try {
		   monitor.beginTask("Register plugin to resource mapping", workspaceProjects.length);
		   for (final IProject project : workspaceProjects) {
			   createPluginToResourceMapping(set, project);
			   monitor.worked(1);
		   }
	   } finally {
		   monitor.done();
	   }
   }
   
   public static final void createPluginToResourceMapping(final ResourceSet set) throws CoreException {
	   createPluginToResourceMapping(set, new NullProgressMonitor());
   }
   
   public static final URI lookupProjectURI(final IProject project)
   {
      IPluginModelBase pluginModel = PluginRegistry.findModel(project);
      if (pluginModel != null)
      {
         // Plugin projects in the workspace
         String pluginID = pluginModel.getBundleDescription().getSymbolicName();
         return URI.createPlatformPluginURI(pluginID + "/", true);
      } else
      {
         // Regular projects in the workspace
         return URI.createPlatformResourceURI(project.getName() + "/", true);
      }
   }

   public static final URI getResolvedPlatformResourceURI(final URIMap uriMap, URI uri)
   {
      for (URI remappedURI = uriMap.getURI(uri); uri != remappedURI && !uri.isPlatformResource();)
      {
         uri = remappedURI;
         remappedURI = uriMap.getURI(uri);
      }
      return uri;
   }

   public static final IProject getWorkspaceProject(final URI namespaceURI)
   {
      assert namespaceURI.segmentCount() >= 2 && namespaceURI.isPlatformPlugin() || namespaceURI.isPlatformResource();
      if (namespaceURI.isPlatformResource() && namespaceURI.segmentCount() >= 2)
      {
         return ResourcesPlugin.getWorkspace().getRoot().getProject(namespaceURI.segment(1));
      }
      if (namespaceURI.isPlatformPlugin() && namespaceURI.segmentCount() >= 2)
      {
         for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects())
         {
            IPluginModelBase pluginModel = PluginRegistry.findModel(project);
            if (pluginModel != null && namespaceURI.segment(1).equals(pluginModel.getBundleDescription().getSymbolicName()))
            {
               return project;
            }
         }
      }
      return null;
   }

   public static final int getDependencyType(final URI namespaceURI)
   {
      if (namespaceURI.isPlatformResource() && namespaceURI.segmentCount() >= 2)
      {
         return WORKSPACE_PROJECT;
      }
      if (namespaceURI.isPlatformPlugin() && namespaceURI.segmentCount() >= 2)
      {
         return getWorkspaceProject(namespaceURI) != null ? WORKSPACE_PLUGIN_PROJECT : DEPLOYED_PLUGIN;
      }
      return UNKNOWN;
   }

   public static final List<Resource> exploreDependentResources(final Resource initialResource)
   {
      final UniqueEList<Resource> result = new UniqueEList<Resource>();
      result.add(initialResource);
      for (int i = 0; i < result.size(); i++)
      {
         final Resource resource = result.get(i);
         for (final TreeIterator<EObject> j = resource.getAllContents(); j.hasNext();)
         {
            final EObject eObject = j.next();
            if (eObject instanceof EDataType)
            {
               j.prune();
            }
            for (final EObject eCrossReference : eObject.eCrossReferences())
            {
               if (eCrossReference instanceof EClass)
               {
                  final EPackage referencedEPackage = ((EClass) eCrossReference).getEPackage();
                  if (resource != referencedEPackage.eResource())
                  {
                     result.add(referencedEPackage.eResource());
                  }
               }
            }
         }
      }
      return result;
   }
}

package org.moflon.ide.core.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.TGGLanguageActivator;
import org.moflon.core.moca.tree.MocaTreePlugin;
import org.moflon.core.mocatomoflon.impl.ExporterImpl;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResource;
import org.moflon.ide.core.CoreActivator;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.sdm.language.SDMLanguagePlugin;
import org.moflon.tgg.runtime.TGGRuntimePlugin;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.PluginProducerWorkspaceRunnable;
import org.moflon.util.plugins.manifest.PluginURIToResourceURIRemapper;

import MocaTree.Attribute;
import MocaTree.Node;
import MoflonPropertyContainer.Dependencies;
import MoflonPropertyContainer.MoflonPropertiesContainer;
import MoflonPropertyContainer.MoflonPropertyContainerFactory;
import MoflonPropertyContainer.PropertiesValue;

public class ResourceFillingMocaToMoflonTransformation extends ExporterImpl
{
   private Map<String, MetamodelProperties> propertiesMap;

   private static final Logger logger = Logger.getLogger(ResourceFillingMocaToMoflonTransformation.class);

   private static final String MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT = "TGG";

   private static final String MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT = "EPackage";

   private static final String MOCA_TREE_ATTRIBUTE_NS_URI = "Moflon::NsUri";

   private static final String MOCA_TREE_ATTRIBUTE_EXPORT = "Moflon::Export";

   private static final String MOFLON_TREE_ATTRIBUTE_NAME = "Moflon::Name";

   public static final URI MOFLON_PROPERTIES_URI = URI.createURI(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE);

   protected final ResourceSet set;

   private IProgressMonitor monitor;

   /**
    * 
    * @param monitor
    *           the monitor to be used during project construction. Every project to be generated should advance the
    *           monitor 1 tick
    */
   public ResourceFillingMocaToMoflonTransformation(final ResourceSet resourceSet, final Map<String, MetamodelProperties> propertiesMap,
         final IProgressMonitor monitor)
   {
      this.set = resourceSet;
      this.monitor = monitor;
      this.propertiesMap = propertiesMap;
   }

   @Override
   public void handleOutermostPackage(final Node node, final EPackage outermostPackage)
   {
      final IProgressMonitor subMonitor = WorkspaceHelper.createSubmonitorWith1Tick(this.monitor);
      try
      {
         final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
         final String projectName = getProjectName(node);
         final String exported = lookupAttribute(node, MOCA_TREE_ATTRIBUTE_EXPORT);
         subMonitor.beginTask("Handling node for project " + projectName, 32);

         // userDefinedNamespaceURI should point to the ecore file from which code was generated
         // E.g., platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore
         final URI namespaceURI = URI.createURI(lookupAttribute(node, MOCA_TREE_ATTRIBUTE_NS_URI));

         if (exported.equals("true"))
         {
            if (MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(node.getName()) || MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(node.getName()))
            {
               // Handling (creating/opening) projects in Eclipse workspace
               IProject workspaceProject = workspaceRoot.getProject(projectName);
               if (!workspaceProject.exists())
               {
                  handleOrReportMissingProject(node, workspaceProject);
               }
               assert workspaceProject != null && workspaceProject.exists();
               subMonitor.worked(10);

               if (!workspaceProject.isAccessible())
               {
                  handleOrReportClosedProject(node, workspaceProject);
               }
               assert workspaceProject.isAccessible();
               subMonitor.worked(10);

               PackageRemappingDependency dependency = new PackageRemappingDependency(namespaceURI, false, false);
               Resource resource = dependency.getResource(set, false, true);
               resource.getContents().add(outermostPackage);
               setEPackageURI(outermostPackage);
               subMonitor.worked(2);

               handleOpenProject(node, workspaceProject, resource);
               subMonitor.worked(10);
            } else
            {
               reportError("Project " + getProjectName(node) + " has unknown type " + node.getName());
               subMonitor.worked(32);
            }
         } else
         {
            try
            {
               if (!MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(node.getName()))
               {
                  reportError("Project " + getProjectName(node) + " must always be exported");
               }

               final IProject project = workspaceRoot.getProject(projectName);
               final PackageRemappingDependency resourceLoader = getResourceLoaderForNonExportedMetamodel(project, namespaceURI);
               final Resource resource = resourceLoader.getResource(set, true, isEcoreFileAccessible(project));

               final EPackage ePackage = (EPackage) resource.getContents().get(0);

               // Copy EPackage URIs from existing metamodel to the constructed metamodel
               copyEPackageURI(ePackage, outermostPackage);

               // Replace existing metamodel content with the constructed metamodel in resourceSet
               SDMEnhancedEcoreResource resourceReplacement = new SDMEnhancedEcoreResource(resource.getURI());
               resourceReplacement.getContents().add(outermostPackage);

               // Make sure that this resource is not saved
               resourceReplacement.getDefaultSaveOptions().put(SDMEnhancedEcoreResource.READ_ONLY, true);

               // If set contains resource remove it first
               if (set.getResources().contains(resource))
                  set.getResources().remove(resource);

               // Now add wrapper resource
               set.getResources().add(resourceReplacement);
               subMonitor.worked(32);
            } catch (RuntimeException e)
            {
               logger.debug("Ignoring " + getProjectName(node));
            }
         }
      } finally
      {
         subMonitor.done();
      }
   }

   private boolean isEcoreFileAccessible(final IProject project)
   {
      return project.isAccessible() && project.getFile(MoflonUtil.getDefaultPathToEcoreFileInProject(project.getName())).exists();
   }

   private final PackageRemappingDependency getResourceLoaderForNonExportedMetamodel(final IProject project, final URI namespaceURI)
   {
      if (project.isAccessible())
      {
         try
         {
            PluginURIToResourceURIRemapper.createPluginToResourceMap(set, project);
         } catch (IOException e)
         {
            e.printStackTrace();
         }
         return new PackageRemappingDependency(namespaceURI, false, false);
      } else
      {
         return new PackageRemappingDependency(namespaceURI, true, true);
      }
   }

   @Override
   public void postProcessing()
   {
      super.postProcessing();
      for (org.moflon.tgg.language.TripleGraphGrammar tgg : getTggexporter().getTripleGraphGrammar())
      {
         IProject workspaceProject = ResourcesPlugin.getWorkspace().getRoot().getProject(tgg.getName());
         if (!workspaceProject.exists())
         {
            reportError("Project " + workspaceProject.getName() + " is missing");
         }
         if (!workspaceProject.isAccessible())
         {
            reportError("Project " + workspaceProject.getName() + " is closed");
         }

         final URI projectURI = MoflonUtil.lookupProjectURI(workspaceProject);
         final URI tggURI = URI.createURI("model/" + MoflonUtil.lastCapitalizedSegmentOf(tgg.getName()) + WorkspaceHelper.PRE_TGG_FILE_EXTENSION)
               .resolve(projectURI);

         final PackageRemappingDependency resourceLoader = new PackageRemappingDependency(tggURI, false, false);
         Resource tggResource = resourceLoader.getResource(set, false);

         tggResource.getContents().add(tgg);
      }
   }

   protected String getProjectName(final Node node)
   {
      return lookupAttribute(node, MOFLON_TREE_ATTRIBUTE_NAME);
   }

   protected String getPathToEcoreFile(final Node node)
   {
      return MoflonUtil.getDefaultPathToEcoreFileInProject(lookupAttribute(node, MOFLON_TREE_ATTRIBUTE_NAME));
   }

   protected static final String lookupAttribute(final Node node, final String attributeName)
   {
      for (final Attribute attribute : node.getAttribute())
      {
         if (attributeName.equals(attribute.getName()))
         {
            return attribute.getValue();
         }
      }
      return null;
   }

   private final void handleOrReportMissingProject(final Node node, final IProject project)
   {
      handleMissingProject(node, project);
      if (!project.exists())
      {
         reportError("Project " + getProjectName(node) + " is missing");
      }
   }

   private final void handleOrReportClosedProject(final Node node, final IProject project)
   {
      handleClosedProject(node, project);
      if (!project.isAccessible())
      {
         reportError("Project " + project.getName() + " is closed");
      }
   }

   protected final void reportError(final String errorMessage)
   {
      throw new UncheckedCoreException(errorMessage, CoreActivator.getModuleID());
   }

   private final void setEPackageURI(final EPackage ePackage)
   {
      URI uri = EcoreUtil.getURI(ePackage);

      if (ePackage instanceof InternalEObject && ((InternalEObject) ePackage).eDirectResource() != null)
      {
         uri = uri.trimFragment();
      }

      ePackage.setNsURI(uri.toString());

      for (final EPackage subPackage : ePackage.getESubpackages())
      {
         setEPackageURI(subPackage);
      }

      setDefaultName(ePackage);
   }

   private void setDefaultName(final EPackage ePackage)
   {
      ePackage.setName(MoflonUtil.lastSegmentOf(ePackage.getName()));
   }

   private final void copyEPackageURI(final EPackage source, final EPackage target)
   {
      target.setNsURI(source.getNsURI());
      for (EPackage sourceSubpackage : source.getESubpackages())
      {
         boolean matchFound = false;
         for (EPackage targetSubpackage : target.getESubpackages())
         {
            if (sourceSubpackage.getName().equals(targetSubpackage.getName()))
            {
               copyEPackageURI(sourceSubpackage, targetSubpackage);
               matchFound = true;
               break;
            }
         }
         if (!matchFound)
         {
            logger.debug("Unable to find a match for subpackage: " + sourceSubpackage + " in " + target);
            logger.debug("Are you sure the versions of your exported metamodels are up to date?");
         }
      }
   }

   protected void reportError(final CoreException e)
   {
      throw new UncheckedCoreException(e);
   }

   protected void handleClosedProject(final Node node, final IProject project)
   {
      try
      {
         project.open(new NullProgressMonitor());
      } catch (final CoreException e)
      {
         throw new UncheckedCoreException(e);
      }
   }

   protected void handleOpenProject(final Node node, final IProject project, final Resource resource)
   {
      MetamodelProperties properties = propertiesMap.get(project.getName());

      try
      {
         ResourcesPlugin.getWorkspace().run(new PluginProducerWorkspaceRunnable(project, properties), project, 0, new NullProgressMonitor());
      } catch (CoreException e)
      {
         this.reportError(e);
      }

      try
      {
         MoflonProjectCreator.createFoldersIfNecessary(project, new NullProgressMonitor());
      } catch (final CoreException e)
      {
         logger.warn("Failed to create folders: " + e.getMessage());
      }

      MoflonPropertiesContainer moflonProps;

      try
      {
         moflonProps = createOrLoadMoflonProperties(project, properties.getMetamodelProjectName());
      } catch (Exception e)
      {
         logger.fatal("Unable to load moflon.properties.xmi from " + project.getName() + ". Reason: " + e.getMessage());
         e.printStackTrace();
         return;
      }

      WorkspaceHelper.moveProjectToWorkingSet(project, properties.get(MetamodelProperties.WORKING_SET_KEY));

      moflonProps.getDependencies().clear();
      properties.getDependenciesAsURIs().stream().forEach(dep -> addMetamodelDependency(moflonProps, dep));

      // These two metamodels are usually used directly or indirectly by most projects
      addMetamodelDependency(moflonProps, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.PLUGIN_ID_ECORE));

      // Additional standard dependencies for integration projects
      if (MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(node.getName()))
      {
         resource.setURI(URI.createURI(resource.getURI().toString().replace(WorkspaceHelper.ECORE_FILE_EXTENSION, WorkspaceHelper.PRE_ECORE_FILE_EXTENSION)));

         addMetamodelDependency(moflonProps, MoflonUtil.getDefaultURIToEcoreFileInPlugin(TGGRuntimePlugin.getDefault().getPluginId()));
         addMetamodelDependency(moflonProps, MoflonUtil.getDefaultURIToEcoreFileInPlugin(SDMLanguagePlugin.getDefault().getPluginId()));
         addMetamodelDependency(moflonProps, MoflonUtil.getDefaultURIToEcoreFileInPlugin(TGGLanguageActivator.getDefault().getPluginId()));
         addMetamodelDependency(moflonProps, MoflonUtil.getDefaultURIToEcoreFileInPlugin(MocaTreePlugin.getDefault().getPluginId()));
      }

      MoflonPropertiesContainerHelper.save(moflonProps, new NullProgressMonitor());

      CoreActivator.getDefault().setDirty(project, true);

   }

   protected void handleMissingProject(final Node node, final IProject project)
   {
      MetamodelProperties properties = propertiesMap.get(project.getName());

      try
      {
         final String projectName = properties.getProjectName();
         final MoflonProjectCreator createMoflonProject = new MoflonProjectCreator();
         createMoflonProject.setProjectName(projectName);
         createMoflonProject.setType(properties.getType());
         createMoflonProject.setMetaModelProjectName(properties.getMetamodelProjectName());

         ResourcesPlugin.getWorkspace().run(createMoflonProject, new NullProgressMonitor());
      } catch (final CoreException e)
      {
         this.reportError(e);
      }
   }

   private final MoflonPropertiesContainer createOrLoadMoflonProperties(final IProject project, final String metamodelProject)
   {
      IFile moflonProps = project.getFile(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE);

      if (moflonProps.exists())
      {
         MoflonPropertiesContainer container = MoflonPropertiesContainerHelper.load(project, new NullProgressMonitor());

         container.updateMetamodelProjectName(metamodelProject);
         return container;

      } else
      {
         return MoflonPropertiesContainerHelper.createDefaultPropertiesContainer(project.getName(), metamodelProject);
      }
   }

   public void addMetamodelDependency(final MoflonPropertiesContainer moflonProperties, final URI metamodelUri)
   {
      Dependencies dep = MoflonPropertyContainerFactory.eINSTANCE.createDependencies();
      dep.setValue(metamodelUri.toString());
      if (!alreadyContainsDependency(moflonProperties.getDependencies(), dep))
         moflonProperties.getDependencies().add(dep);
   }

   private boolean alreadyContainsDependency(final Collection<? extends PropertiesValue> dependencies, final PropertiesValue addDep)
   {
      return dependencies.stream().anyMatch(d -> d.getValue().equals(addDep.getValue()));
   }
}

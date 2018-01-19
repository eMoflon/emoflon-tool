package org.moflon.gt.ide;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.codegen.eclipse.MoflonCodeGeneratorPhase;

/**
 * This task weaves the control flow and pattern matching models stored in textual syntax with the plain metamodel
 *
 * The task is configured with the top-level {@link EPackage} of the metamodel.
 *
 * @author Roland Kluge - Initial implementation
 *
 * @see #run(IProgressMonitor)
 */
public class MOSLGTWeavingTask implements ITask, MoflonCodeGeneratorPhase
{
//   /**
//    * The top-level {@link EPackage} of the ongoing build process
//    */
//   private EPackage ePackage;

   //   /**
   //    * The common {@link ResourceSet} of the ongoing build process
   //    */
   //   private ResourceSet resourceSet;
   //
   //   private IProject project;

   //   private TransformationConfiguration transformationConfiguration;

   @Override
   public String getTaskName()
   {
      return "eMoflon-GT Transformation task";
   }

   @Override
   public IStatus run(final IProject project, Resource resource, MethodBodyHandler methodBodyHandler, IProgressMonitor monitor)
   {
      //      this.project = project;
      //      this.ePackage = (EPackage) resource.getContents().get(0);
      //      this.resourceSet = ePackage.eResource().getResourceSet();
      //      //      this.transformationConfiguration = new TransformationConfiguration();
      //      final Map<String, PatternMatcher> patternMatcherConfiguration = methodBodyHandler.getPatternMatcherConfiguration();
      //      //      this.transformationConfiguration.getPatternMatchingController().setSearchplanGenerators(patternMatcherConfiguration);
      //
      //      DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);
      return run(monitor);
   }

   /**
    * Entry point of this task
    *
    * It iterates through all {@link EClass}es in the metamodel and invokes
    * {@link #weaveEClass(EClass, MultiStatus, IProgressMonitor)} for each one.
    *
    * @param monitor
    *           the progress monitor
    * @return the status of the entire task
    */
   @Override
   public IStatus run(IProgressMonitor monitor)
   {
      //      final IStatus mgtLoadStatus = this.loadMgtFiles();
      //      if (mgtLoadStatus.matches(IStatus.ERROR))
      //         return mgtLoadStatus;
      //
      //      // This status collects all information about the weaving process for 'ePackage'
      //      final MultiStatus weavingMultiStatus = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0, getTaskName() + " failed", null);
      //      final List<EClass> eClasses = eMoflonEMFUtil.getEClasses(this.ePackage);
      //
      //      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName() + " in " + ePackage.getName(), eClasses.size());
      //      processMgtFiles(subMon);
      //      return weavingMultiStatus.isOK() ? Status.OK_STATUS : weavingMultiStatus;
      return Status.OK_STATUS;
   }

   //   /**
   //    * This routine identifies and loads all .mgt files in the current project.
   //    *
   //    * For each .mgt file, an appropriate resource is created in this generator's resource set ({@link #getResourceSet()}
   //    */
   //   private IStatus loadMgtFiles()
   //   {
   //      try
   //      {
   //         getProject().accept(new IResourceVisitor() {
   //
   //            @Override
   //            public boolean visit(IResource resource) throws CoreException
   //            {
   //               if (resource.getName().equals("bin"))
   //                  return false;
   //
   //               if (isMOSLGTFile(resource))
   //               {
   //                  final Resource schemaResource = (Resource) getResourceSet()
   //                        .createResource(URI.createPlatformResourceURI(resource.getAdapter(IFile.class).getFullPath().toString(), false));
   //                  try
   //                  {
   //                     schemaResource.load(null);
   //                  } catch (final IOException e)
   //                  {
   //                     throw new CoreException(
   //                           new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e));
   //                  }
   //               }
   //               return true;
   //            }
   //
   //            private boolean isMOSLGTFile(IResource resource)
   //            {
   //               final IFile file = resource.getAdapter(IFile.class);
   //               return resource != null && resource.exists() && file != null && WorkspaceHelper.EMOFLON_GT_EXTENSION.equals(file.getFileExtension());
   //            }
   //
   //         });
   //         EcoreUtil.resolveAll(this.getResourceSet());
   //      } catch (final CoreException e)
   //      {
   //         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
   //      }
   //
   //      return Status.OK_STATUS;
   //   }
   //
   //   private ResourceSet getResourceSet()
   //   {
   //      return this.resourceSet;
   //   }
   //
   //   private IProject getProject()
   //   {
   //      return project;
   //   }
   //
   //   private IStatus processMgtFiles(final IProgressMonitor monitor)
   //   {
      //      try
      //      {
      //         CodeadapterTrafo helper = this.transformationConfiguration.getCodeadapterTransformator();
      //
      //         final List<Resource> mgtResources = this.resourceSet.getResources().stream()
      //               .filter(resource -> resource.getURI().lastSegment().endsWith('.' + WorkspaceHelper.EMOFLON_GT_EXTENSION)).collect(Collectors.toList());
      //
      //         for (final Resource schemaResource : mgtResources)
      //         {
      //            processMgtResource(helper, schemaResource);
      //
      //         }
      //         EcoreUtil.resolveAll(this.resourceSet);
      //      } catch (final IOException e)
      //      {
      //         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e);
      //      }
   //      return Status.OK_STATUS;
   //   }

   //   private void processMgtResource(CodeadapterTrafo helper, final Resource schemaResource) throws IOException
   //   {
   //      final GraphTransformationFile gtf = GraphTransformationFile.class.cast(schemaResource.getContents().get(0));
   //      if (gtf.getImports().size() > 0)
   //      {
   //         // TODO@rkluge: Probably, we will have to translate the .mgt files "package-by-package" and load the
   //         // appropriate packages
   //
   //         // load context
   //         String contextEcorePath = gtf.getImports().get(0).getName().replaceFirst("platform:/resource", "").replaceFirst("platform:/plugin", "");
   //         Resource ecoreRes = this.resourceSet.getResource(URI.createPlatformResourceURI(contextEcorePath, false), true);
   //         ecoreRes.load(null);
   //         // TODO@rkluge: Extend to support multiple root packages
   //         final EPackage contextEPackage = EcoreUtil.copy((EPackage) ecoreRes.getContents().get(0));
   //
   //         // transformation
   //         Resource enrichedEcoreResource = ePackage.eResource(); // this.resourceSet.createResource(enrichedEcoreURI);
   //         String nsURI = ePackage.eResource().getURI().toString();
   //         enrichedEcoreResource.getContents().clear();
   //         enrichedEcoreResource.getContents().add(contextEPackage);
   //         contextEPackage.setNsURI(nsURI);
   //         enrichedEcoreResource.save(Collections.EMPTY_MAP);
   //         EcoreUtil.resolveAll(contextEPackage);
   //         final EPackage enrichedEPackage = helper.transform(contextEPackage, gtf, this.resourceSet);
   //
   //         // save context
   //         enrichedEcoreResource.getContents().clear();
   //         enrichedEcoreResource.getContents().add(enrichedEPackage);
   //         final Map<String, String> saveOptions = new HashMap<>();
   //         saveOptions.put(Resource.OPTION_LINE_DELIMITER, WorkspaceHelper.DEFAULT_RESOURCE_LINE_DELIMITER);
   //         enrichedEcoreResource.save(saveOptions);
   //
   //      }
   //   }

}

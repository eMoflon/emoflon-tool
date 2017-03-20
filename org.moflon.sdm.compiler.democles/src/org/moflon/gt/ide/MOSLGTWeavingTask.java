package org.moflon.gt.ide;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.codeadapter.MOSLGTUtil;
import org.moflon.gt.mosl.codeadapter.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;

/**
 * This task weaves the control flow and pattern matching models stored in textual syntax with the plain metamodel
 * 
 * The task is configured with the top-level {@link EPackage} of the metamodel.
 * 
 * @author Roland Kluge - Initial implementation
 * 
 * @see #run(IProgressMonitor)
 */
public class MOSLGTWeavingTask implements ITask
{
   /**
    * The top-level {@link EPackage} of the ongoing build process
    */
   private EPackage ePackage;

   /**
    * The common {@link ResourceSet} of the ongoing build process
    */
   private ResourceSet resourceSet;

   /**
    * Preconfigures this task with the top-level {@link EPackage} of the metamodel to be processed
    * 
    * @param ePackage
    */
   public MOSLGTWeavingTask(final EPackage ePackage)
   {
      this.ePackage = ePackage;
      this.resourceSet = ePackage.eResource().getResourceSet();
      DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);
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
      // This status collects all information about the weaving process for 'ePackage'
      final MultiStatus weavingMultiStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, getTaskName() + " failed", null);
      final List<EClass> eClasses = CodeGeneratorPlugin.getEClasses(this.ePackage);

      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName() + " in " + ePackage.getName(), eClasses.size());
      loadMGTFiles(subMon);
      return weavingMultiStatus.isOK() ? Status.OK_STATUS : weavingMultiStatus;
   }

   private IStatus loadMGTFiles(final IProgressMonitor monitor)
   {
      try
      {
         CodeadapterTrafo helper = CodeadapterTrafo.getInstance();
         for (final IFile moslGTFile : MOSLGTUtil.getInstance().getMGTFiles())
         {
            final IProject project = moslGTFile.getProject();
            final URI workspaceURI = URI.createPlatformResourceURI("/", true);
            final URI projectURI = URI.createURI(project.getName() + "/", true).resolve(workspaceURI);
            Resource schemaResource = (Resource) this.resourceSet.createResource(URI.createPlatformResourceURI(moslGTFile.getFullPath().toString(), false));
            schemaResource.load(null);
            final GraphTransformationFile gtf = GraphTransformationFile.class.cast(schemaResource.getContents().get(0));
            if (gtf.getImports().size() > 0)
            {
               String contextEcorePath = gtf.getImports().get(0).getName().replaceFirst("platform:/resource", "").replaceFirst("platform:/plugin", "");
               Resource ecoreRes = (Resource) this.resourceSet.createResource(URI.createPlatformResourceURI(contextEcorePath, false));
               ecoreRes.load(null);
               final EPackage contextEPackage = (EPackage) ecoreRes.getContents().get(0);
               EPackage enrichedEPackage = helper.transform(contextEPackage, gtf, DemoclesMethodBodyHandler::initResourceSetForDemocles);
               IFile enrichedEcoreFile = project.getFile(WorkspaceHelper.INSTANCES_FOLDER + "/debug" + WorkspaceHelper.ECORE_FILE_EXTENSION);
               URI enrichedEcoreURI = URI.createURI(enrichedEcoreFile.getProjectRelativePath().toString(), true).resolve(projectURI);
               Resource enrichedEcoreResource = this.resourceSet.createResource(enrichedEcoreURI);
               enrichedEcoreResource.getContents().clear();
               enrichedEcoreResource.getContents().add(enrichedEPackage);
               enrichedEcoreResource.save(Collections.EMPTY_MAP);
            }

         }
         EcoreUtil.resolveAll(this.resourceSet);
      } catch (IOException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e);
      }
      return Status.OK_STATUS;
   }

   @Override
   public String getTaskName()
   {
      return "MOSL-GT Weaving";
   }

}

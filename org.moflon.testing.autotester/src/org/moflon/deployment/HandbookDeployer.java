package org.moflon.deployment;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;

public class HandbookDeployer extends AbstractDeployer
{
   private static final Logger logger = Logger.getLogger(HandbookDeployer.class);

   public HandbookDeployer(final String deploymentPath)
   {
      super(deploymentPath);
   }

   @Override
   public void deploy(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Deploying handbook", 8);
         
         final IProject handbookCommonProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.00_common");
         final IProject introductionProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.00_introduction");
         final IProject installationProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.01_installation");
         final IProject leitnersLearningBoxProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.02_leitnersLearningBox");
         final IProject sdmProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.03_storyDiagrams");
         final IProject tggProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.04_tripleGraphTransformations");
         final IProject modelToTextProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.05_modelToTextTransformations");
         final IProject miscProject = WorkspaceHelper.getProjectByName("org.moflon.doc.handbook.06_miscellaneous");

         final List<IProject> handbookProjects = Arrays.asList(handbookCommonProject, installationProject, introductionProject, leitnersLearningBoxProject,
               miscProject, modelToTextProject, sdmProject, tggProject);

         if (WorkspaceHelper.allProjectsExist(handbookProjects))
         {
            logger.info("Copying eMoflon Handbook...");

            new File(getDeploymentPath()).mkdir();
            final File handbookPath = clearFlatFolder(getDeploymentPath());
            monitor.worked(1);

            copySingleHandbookDocument(introductionProject, "part0.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(installationProject, "part1.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(leitnersLearningBoxProject, "part2.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(sdmProject, "part3.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(tggProject, "part4.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(modelToTextProject, "part5.pdf", handbookPath);
            monitor.worked(1);
            copySingleHandbookDocument(miscProject, "part6.pdf", handbookPath);
            monitor.worked(1);

            logger.info("Copying eMoflon Handbook done.");
         } else
         {
            logger.warn("Skipping handbook because one or more handbook projects are missing from the workspace.");
            ++this.warningCount;
            monitor.worked(8);
         }
      } finally
      {
         monitor.done();
      }
   }

   private static void copySingleHandbookDocument(final IProject project, final String partName, final File handbookRoot) throws CoreException
   {
      IFile introPdfFile = getFirstPdfFile(project);
      if (introPdfFile != null)
      {
         copyFile(introPdfFile.getLocation().toFile(), new File(handbookRoot, partName));
      } else
      {
         logger.error("Missing PDF file for project " + project.getName());
      }
   }

   /**
    * Returns the first PDF file in the given project
    */
   private static IFile getFirstPdfFile(final IProject project) throws CoreException
   {
      for (final IResource resource : project.members())
      {
         if (WorkspaceHelper.isPdfFile(resource))
         {
            return (IFile) resource;
         }
      }
      return null;
   }

}

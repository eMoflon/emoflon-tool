package org.moflon.ide.core.runtime;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IJavaProject;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.util.plugins.MetamodelProperties;

import MoflonPropertyContainer.MoflonPropertiesContainer;
import MoflonPropertyContainer.SDMCodeGeneratorIds;

public class MoflonProjectCreator implements IWorkspaceRunnable
{
   private String projectName;

   private String type;

   private String metaModelProjectName;

   public void setProjectName(final String projectName)
   {
      this.projectName = projectName;
   }

   public void setType(final String type)
   {
      this.type = type;
   }

   public void setMetaModelProjectName(final String metaModelProjectName)
   {
      this.metaModelProjectName = metaModelProjectName;
   }

   @Override
   public void run(final IProgressMonitor monitor) throws CoreException
   {
      final IWorkspace workspace = ResourcesPlugin.getWorkspace();
      final IWorkspaceRoot workspaceRoot = workspace.getRoot();
      final IProject workspaceProject = workspaceRoot.getProject(projectName);
      if (workspaceProject.exists())
      {
         return;
      }
      try
      {
         monitor.beginTask("Creating project " + projectName, 12);
         final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(projectName);

         workspaceProject.create(description, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         workspaceProject.open(WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         createFoldersIfNecessary(workspaceProject, WorkspaceHelper.createSubMonitor(monitor, 4));
         addGitIgnoreFiles(workspaceProject, WorkspaceHelper.createSubMonitor(monitor, 2));

         addNatureAndBuilders(monitor, this.type, workspaceProject);

         IJavaProject javaProject = WorkspaceHelper.setUpAsJavaProject(workspaceProject, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         WorkspaceHelper.setAsSourceFolderInBuildpath(javaProject, new IFolder[] { WorkspaceHelper.getGenFolder(workspaceProject) },
               // see issue #718
               new IClasspathAttribute[] { /* JavaCore.newClasspathAttribute("ignore_optional_problems", "true") */ },
               WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         MoflonPropertiesContainer moflonProperties = MoflonPropertiesContainerHelper.createDefaultPropertiesContainer(workspaceProject.getName(),
               metaModelProjectName);
         setDefaultCodeGenerator(moflonProperties);
         monitor.worked(1);

         MoflonPropertiesContainerHelper.save(moflonProperties, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

      } finally
      {
         monitor.done();
      }
   }

   private static void addGitIgnoreFiles(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Creating .gitignore files", 2);
         IFile genGitIgnore = WorkspaceHelper.getGenFolder(project).getFile(".gitignore");
         if (!genGitIgnore.exists())
         {
            genGitIgnore.create(new ByteArrayInputStream("*".getBytes()), true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }

         IFile modelGitIgnore = WorkspaceHelper.getModelFolder(project).getFile(".gitignore");
         if (!modelGitIgnore.exists())
         {
            modelGitIgnore.create(new ByteArrayInputStream("*".getBytes()), true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }
      } finally
      {
         monitor.done();
      }
   }

   public static void createFoldersIfNecessary(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Creating folders within project", 5);
         WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getGenFolder(project), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getLibFolder(project), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getModelFolder(project), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getInstancesFolder(project), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getInjectionFolder(project), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
      } finally
      {
         monitor.done();
      }
   }

   private void setDefaultCodeGenerator(final MoflonPropertiesContainer moflonProps)
   {
      if (type.equals(MetamodelProperties.INTEGRATION_KEY))
         moflonProps.getSdmCodegeneratorHandlerId().setValue(SDMCodeGeneratorIds.DEMOCLES_REVERSE_NAVI);
      else
         moflonProps.getSdmCodegeneratorHandlerId().setValue(SDMCodeGeneratorIds.DEMOCLES);
   }

   private void addNatureAndBuilders(final IProgressMonitor monitor, final String type, final IProject newProjectHandle) throws CoreException
   {
      try
      {
         monitor.beginTask("Adding nature and builders", 1);
         if (type.equals(MetamodelProperties.REPOSITORY_KEY))
            WorkspaceHelper.addNature(newProjectHandle, WorkspaceHelper.REPOSITORY_NATURE_ID, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         else
            WorkspaceHelper.addNature(newProjectHandle, WorkspaceHelper.INTEGRATION_NATURE_ID, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
      } finally
      {
         monitor.done();
      }
   }

}

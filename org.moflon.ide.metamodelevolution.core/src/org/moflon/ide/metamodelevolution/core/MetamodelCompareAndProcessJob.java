package org.moflon.ide.metamodelevolution.core;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class MetamodelCompareAndProcessJob extends Job {

	private final Logger logger;
	private final List<IProject> projects;	
	protected IProject project;
	//private final IStatus OK_STATUS = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
 
	public MetamodelCompareAndProcessJob(final String name, final List <IProject> projects) {
		super(name);
		this.logger = Logger.getLogger(this.getClass());
		this.projects = projects;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {

        for (final IProject project : projects) {
         	try {
 				if (project.isAccessible() && (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))) {
 		    		EMFCompareMetamodelComparator comparator = new EMFCompareMetamodelComparator(project);
 		    		comparator.compare();
 				}
 			} catch (CoreException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			} 		
        }    
		return null;
	}
	
	public static final URI lookupProjectURI(final IProject project)  {
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
	
	protected IFile getEcoreFile() {
	   return getEcoreFile(project);
	}
	   
	private boolean doesEcoreFileExist() {
	   return getEcoreFile().exists();
	}
	
	protected IFile getEcoreFileAndHandleMissingFile() {
	   if (!doesEcoreFileExist())
	      createMarkersForMissingEcoreFile();
	   return getEcoreFile();
	}
	   
	private void createMarkersForMissingEcoreFile() {
	   IFile ecoreFile = getEcoreFile();
	   logger.error("Unable to generate code: " + ecoreFile + " does not exist in project!");
	}
	
	//TODO@settl : use WorkspaceHelper.getDefaultEcoreFile(project) [you need to update before!!!]
	public static IFile getEcoreFile(final IProject p) {
		String ecoreFileName = MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(p.getName());
		return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
	}	
	
	protected boolean shallBuildProject(final IProject project) {
	      return true;
	}
}

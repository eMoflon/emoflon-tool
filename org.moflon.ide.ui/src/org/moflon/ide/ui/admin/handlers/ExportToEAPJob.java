package org.moflon.ide.ui.admin.handlers;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.ecore2mocaxmi.utils.ConverterHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.ui.UIActivator;

import MocaTree.Node;

public class ExportToEAPJob extends Job {

	final static private String EA_TREE_FILE_EXTENSION = ".moca.xmi";
	final static private String CONVERTER_JOB_NAME = "Exporting Ecore-File to EAP";
	
	private Logger logger;
	
	private String filePath;
	private Node eaTree;
	private String ecoreFileName;
	
	public ExportToEAPJob(String filePath, String ecoreFileName, Node eaTree) {
		super(CONVERTER_JOB_NAME);
		this.filePath = filePath;
		this.eaTree = eaTree;
		this.ecoreFileName = ecoreFileName;
		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IProject eapProject = null;
		IFile eaTreeFile = null;
		IFile lockFile = null;
		if(filePath != null && eaTree != null){
			List<String> parts = Arrays.asList(filePath.split("/"));
			eapProject = ConverterHelper.getProject(parts);
			lockFile = ConverterHelper.getLockFile(eapProject, parts);
			if(lockFile != null && lockFile.exists())
				return new Status(IStatus.ERROR, UIActivator.getModuleID(), IStatus.ERROR, getLockedErrorMessage(lockFile).toString(), null);
			if(eapProject != null){
				IFolder tempFolder = eapProject.getFolder(".temp");
				if(tempFolder != null && !tempFolder.exists())
					try {
						tempFolder.create(true, true, monitor);
					} catch (CoreException e) {
						logger.error("Cannot create .temp Folder", e);
					}
				
				if(tempFolder != null && tempFolder.exists()){
					eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), eaTree, tempFolder.getLocation().toString() + "/" + ecoreFileName + EA_TREE_FILE_EXTENSION);
					eaTreeFile = tempFolder.getFile(ecoreFileName + EA_TREE_FILE_EXTENSION);
					EnterpriseArchitectHelper.importXMIFilesToEAP(parts.get(parts.size()-1),eapProject, eaTreeFile, monitor);
				}
			}
		}
		return new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
	}

	private static StringBuilder getLockedErrorMessage(IFile lockFile){
		StringBuilder message = new StringBuilder(100);
		message.append("Selected ").append(lockFile.getName().replace(lockFile.getFileExtension(), "")).append("eap File is open! Please close the File");
		return message;
		
	}
	
}

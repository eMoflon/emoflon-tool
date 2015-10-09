/**
 * 
 */
package org.moflon.ide.ui.admin.handlers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.codegen.eclipse.MonitoredMetamodelLoader;
import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiFactory;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;

import MocaTree.Node;
import MoflonPropertyContainer.MoflonPropertyContainerFactory;

/**
 * @author ah70bafa
 *
 */
public class ExportToEAPHandler extends AbstractCommandHandler {

	final static private String ECORE_FILE_EXSTENSION = "ecore";
	final static private String EA_TREE_FILE_EXTENSION = ".moca.xmi";
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return execute(HandlerUtil.getCurrentSelectionChecked(event), event);
	}
	
	
	private Object execute(ISelection selection, ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String filePath = null;
		IFile ecoreFile = null;
		String ecoreFileName = null;
		IFile eaTreeFile = null;
		IProject eapProject = null;
		Node eaTree = null;
		if (selection instanceof TreeSelection) {
			final TreeSelection treeSelection = (TreeSelection) selection;
			for (final Iterator<?> selectionIterator = treeSelection.iterator(); selectionIterator.hasNext();) {
				final Object element = selectionIterator.next();
				if (element != null && element instanceof IFile	&& IFile.class.cast(element).getName().endsWith("." + ECORE_FILE_EXSTENSION)){
					ecoreFile = IFile.class.cast(element);
					eaTree = getEATree(ecoreFile);
					ecoreFileName = ecoreFile.getName();
					break;
				}
			}
		}	
		try {
		filePath = getEAPFilePath(window.getShell(), ecoreFile.getParent().getLocation().toString()).replace('\\', '/');
		} catch (NullPointerException npe){
			return null;
		}
		if(filePath != null && eaTree != null){
			List<String> parts = Arrays.asList(filePath.split("/"));
			eapProject = getProject(parts);
			if(eapProject != null){
				IFolder tempFolder = eapProject.getFolder(".temp");
				if(tempFolder != null && tempFolder.exists()){
					eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), eaTree, tempFolder.getLocation().toString() + "/" + ecoreFileName + EA_TREE_FILE_EXTENSION);
					eaTreeFile = tempFolder.getFile(ecoreFileName + EA_TREE_FILE_EXTENSION);
					EnterpriseArchitectHelper.importXMIFilesToEAP(eapProject, eaTreeFile);
				}
			}
		}
		return null;
	}


	private String getEAPFilePath(final Shell shell, final String ecorePath){
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Select EAP File");
		String [] extensions = {"*.eap"};
		dialog.setFilterExtensions(extensions);
		dialog.setFilterPath(ecorePath);
		return dialog.open();
	}
	
	private Node getEATree(IFile file){
		Ecore2MocaXMIConverter converter = Ecore2mocaxmiFactory.eINSTANCE.createEcore2MocaXMIConverter();
		Node tree = null;
		MonitoredMetamodelLoader mmLoader = new MonitoredMetamodelLoader(new ResourceSetImpl(), file, MoflonPropertyContainerFactory.eINSTANCE.createMoflonPropertiesContainer());
		try{
			mmLoader.run(new NullProgressMonitor());
			Resource res = mmLoader.getEcoreResource();
			EObject eObject = res.getContents().get(0);
			if (eObject instanceof EPackage) {
			    EPackage p = (EPackage)eObject;
			    tree = converter.convert(p, file.getProject().getName());
		}
		}catch (Exception e){
			logger.error("A Problem has been caused", e);
			return null;
		}
		return tree;
	}

	private IProject getProject(List<String> parts){		
		List<IProject> projects = WorkspaceHelper.getAllProjectsInWorkspace();
		for(IProject project : projects){
			if(parts.contains(project.getName()))
				return project;
		}
		return null;
	}
}

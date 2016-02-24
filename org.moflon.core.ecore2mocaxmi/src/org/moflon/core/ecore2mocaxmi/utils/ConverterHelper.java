package org.moflon.core.ecore2mocaxmi.utils;


import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.moflon.codegen.eclipse.AbstractMonitoredMetamodelLoader;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiFactory;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.properties.MoflonPropertiesContainerHelper;

import MocaTree.Node;

public class ConverterHelper {
	protected static Logger logger;
	
	private final static String LOCK_FILE_EXTENSION=".ldb";
	
	public static void setLogger(final Logger log){
		logger = log;
	}
	
	public static String getEAPFilePath(final Shell shell, final String ecorePath){
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Select EAP File");
		String [] extensions = {"*.eap"};
		dialog.setFilterExtensions(extensions);
		dialog.setFilterPath(ecorePath);
		return dialog.open();
	}
	
	public static Node getEATree(final IFile file){
		return getEATree(file, false);
	}

	private static AbstractMonitoredMetamodelLoader createMetaModelLoader(final ResourceSet resourceSet, final IFile ecoreFile){
		return new AbstractMonitoredMetamodelLoader(resourceSet, ecoreFile, MoflonPropertiesContainerHelper.createEmptyContainer()) {			
			@Override
			protected void createResourcesForWorkspaceProjects(IProgressMonitor monitor) {
			      try
			      {
			         final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			         monitor.beginTask("Loading workspace projects", workspaceProjects.length);
			         for (IProject workspaceProject : workspaceProjects)
			         {
			            try
			            {
			               if (isAccessible(workspaceProject))
			               {
			                  final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(workspaceProject);
			                  final URI metamodelURI = CodeGeneratorPlugin.getDefaultProjectRelativeEcoreFileURI(workspaceProject).resolve(projectURI);
			                  new PackageRemappingDependency(metamodelURI, false, false).getResource(resourceSet, false, true);
			                  if(workspaceProject.equals(ecoreFile.getProject())){
			                	  new PackageRemappingDependency(URI.createURI(ecoreFile.getLocation().toOSString()), false, false).getResource(resourceSet, false, true);
			                  }
			               }
			            } catch (Exception e)
			            {
			               // Do nothing
			            }
			            monitor.worked(1);
			         }
			      } finally
			      {
			         monitor.done();
			      }
			   }

			@Override
			protected boolean isAccessible(IProject project) {
				return project.isAccessible();
			}
		};
	}
	
	public static Node getEATree(final IFile file, final boolean export){
		Ecore2MocaXMIConverter converter = Ecore2mocaxmiFactory.eINSTANCE.createEcore2MocaXMIConverter();
		Node tree = converter.createNewRootNode();
		AbstractMonitoredMetamodelLoader mmLoader = createMetaModelLoader(new ResourceSetImpl(), file);
		try{
			String fileName = file.getName().replace('.' + file.getFileExtension(), "");
			mmLoader.run(new NullProgressMonitor());
			Resource res = mmLoader.getEcoreResource();
			converter.clear();
			EPackage p = getEPackage(res, fileName);
			tree = converter.convert(p, "imported"+ fileName, export, tree);
			converter.resolve();
		}catch (Exception e){
			logger.error("A Problem has been caused", e);
			return null;
		}
		return tree;
	}
	
	private static EPackage getEPackage(Resource res, String fileName){
		if(res.getContents().size() > 1 && moreThanOneAreEPackeges(res.getContents()))
			return createNewSuperPackage(res.getContents(), fileName);
		else if(res.getContents().size() >= 1 && onlyOneEPackage(res.getContents()))
			return theSingleEPackage(res.getContents());
		else
			return null;
	}

	private static EPackage theSingleEPackage(EList<EObject> contents) {
		for(EObject object : contents){
			if(object instanceof EPackage)
				return EPackage.class.cast(object);
		}
		return null;
	}

	private static boolean onlyOneEPackage(EList<EObject> contents) {
		boolean theOneAndOnly = false;
		for(EObject object : contents){
			if(!theOneAndOnly && object instanceof EPackage)
				theOneAndOnly = true;
			else if(theOneAndOnly && object instanceof EPackage)
				return false;
		}
		return theOneAndOnly;
	}

	private static EPackage createNewSuperPackage(EList<EObject> contents, String fileName) {
		EPackage superPackage = EcoreFactory.eINSTANCE.createEPackage();
		superPackage.setName("org.emoflon.importedEcore."+fileName);
		superPackage.setNsPrefix("");
		superPackage.setNsURI("");
		for(EObject object : contents){
			if(object instanceof EPackage){
				EPackage ePackage = EPackage.class.cast(object);
				superPackage.getESubpackages().add(ePackage);
				if(ePackage.getNsPrefix() != null &&!superPackage.getNsPrefix().equals(ePackage.getNsPrefix()))
					superPackage.setNsPrefix(ePackage.getNsPrefix());
				
				if(ePackage.getNsURI() != null && !superPackage.getNsURI().equals(ePackage.getNsURI()))
					superPackage.setNsURI(ePackage.getNsURI());
			}
		}
		return superPackage;
	}

	private static boolean moreThanOneAreEPackeges(EList<EObject> contents) {
		boolean first = false;
		for (EObject object : contents) {
			if (first && object instanceof EPackage)
				return true;
			else if(object instanceof EPackage)
				first = true;
		}
		return false;
	}

	public static IProject getProject(final List<String> parts){		
		List<IProject> projects = WorkspaceHelper.getAllProjectsInWorkspace();
		for(IProject project : projects){
			if(parts.contains(project.getName()))
				return project;
		}
		return null;
	}
	
	public static IFile getLockFile(final IProject project, final List<String> parts){
		if(project !=null && parts!=null && parts.size() > 1){
			return project.getFile(parts.get(parts.size()-2) + LOCK_FILE_EXTENSION);
		}
		return null;
	}
}

package org.moflon.ide.metamodelevolution.core;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.ReferenceUtil;

public class EMFCompareMetamodelComparator implements MetamodelComparator {

	protected IProject project;
	protected ResourceSet resourceSetMMold; //TODO@settl: work with packages instead of resourcesets
	protected ResourceSet resourceSetMMnew;
	
	public EMFCompareMetamodelComparator(IProject project) {
		this.project = project;
	}
	
	public EMFCompareMetamodelDelta compare() {
		//TODO@settl: make fail-safe, return delta
		loadMetamodels();
		compare(resourceSetMMold, resourceSetMMnew);
		return null;
	}
	
	@Override
	public EMFCompareMetamodelDelta compare(EPackage oldMetaModel, EPackage newMetaModel) {
		
		IComparisonScope scope = new DefaultComparisonScope(oldMetaModel, newMetaModel, null);
		
		// custom diffprocessor that ignores renaming - for testing purposes
		IDiffProcessor ignoreNameDiffProcessor = new DiffBuilder() {
			@Override
			public void attributeChange(Match match, EAttribute attribute, Object value, DifferenceKind kind, DifferenceSource source) {
				if (attribute != EcorePackage.Literals.ENAMED_ELEMENT__NAME) {
					super.attributeChange(match, attribute, value, kind, source);
				}
			}
		};

		IDiffEngine diffEngine = new DefaultDiffEngine(ignoreNameDiffProcessor);
		// Uncomment this line to use your own custom Diffprocessor
		//EMFCompare metamodelComparator = EMFCompare.builder().setDiffEngine(diffEngine).build();
		
		EMFCompare metamodelComparator = EMFCompare.builder().build();
		Comparison comparison = metamodelComparator.compare(scope);
		
		printDiffs(comparison);
		return null;
	}
	
	public EMFCompareMetamodelDelta compare(ResourceSet oldMetaModel, ResourceSet newMetaModel) {
		
		IComparisonScope scope = new DefaultComparisonScope(oldMetaModel, newMetaModel, null);
		
		// custom diffprocessor that ignores renaming - for testing purposes
		IDiffProcessor ignoreNameDiffProcessor = new DiffBuilder() {
			@Override
			public void attributeChange(Match match, EAttribute attribute, Object value, DifferenceKind kind, DifferenceSource source) {
				if (attribute != EcorePackage.Literals.ENAMED_ELEMENT__NAME) {
					super.attributeChange(match, attribute, value, kind, source);
				}
			}
		};

		IDiffEngine diffEngine = new DefaultDiffEngine(ignoreNameDiffProcessor);
		// Uncomment this line to use your own custom Diffprocessor
		//EMFCompare metamodelComparator = EMFCompare.builder().setDiffEngine(diffEngine).build();
		
		EMFCompare metamodelComparator = EMFCompare.builder().build();
		Comparison comparison = metamodelComparator.compare(scope);
		
		printDiffs(comparison);
		return null;
	}
	
	protected void loadMetamodels() {

		URI projectURI = lookupProjectURI(project);
		URI metamodelURI = URI.createURI(getEcoreFileAndHandleMissingFile().getProjectRelativePath().toString()).resolve(projectURI);
         
        String oldMetamodelURITempString = metamodelURI.toString();
        oldMetamodelURITempString = oldMetamodelURITempString.substring(0, oldMetamodelURITempString.lastIndexOf(".")) + ".previous.ecore";
        URI previousMMURI = URI.createURI(oldMetamodelURITempString);
         
 		resourceSetMMold = new ResourceSetImpl(); 
 		resourceSetMMnew = new ResourceSetImpl();
 		
 		Resource oldMMResource = null;
 		Resource newMMResource = null;
 		try {
 			oldMMResource = resourceSetMMold.getResource(previousMMURI, true);
 			newMMResource = resourceSetMMnew.getResource(metamodelURI, true);     		

 		} catch (final Exception e) {
 			e.printStackTrace();
 		}
 		//TODO@settl: this returns always the same package, not the previous one, why?
 		
 		//EPackage oldMetamodelPackage = (EPackage)oldMMResource.getContents().get(0);
 		//EPackage newMetamodelPackage = (EPackage)newMMResource.getContents().get(0);
	}
	
	protected void printDiffs(Comparison comparison) {
	    
	    List<Diff> differences = comparison.getDifferences();

	    for(Diff diffElement: differences)
	    {
	    	Match match = diffElement.getMatch();
	        System.out.println("diffElement.getKind(): " + diffElement.getKind());
	        System.out.println("diffElement.getMatch(): " + diffElement.getMatch());
	        
	        if (diffElement.getKind() == DifferenceKind.CHANGE && diffElement instanceof AttributeChange) {
	            final EAttribute attribute = ((AttributeChange)diffElement).getAttribute();
	            final String newValue = ((AttributeChange)diffElement).getValue().toString();
	            final EObject containerLeft = diffElement.getMatch().getLeft();
	            final Object value = ReferenceUtil.safeEGet(containerLeft, attribute);
	            
	            System.out.println("newValue: " + newValue);
	            
	            final EObject containerRight;
	            if (diffElement.getSource() == DifferenceSource.LEFT) {
	              containerRight = (EClass)diffElement.getMatch().getRight();
	              	              
	              System.out.println("containerLeft: " + containerLeft);
	              System.out.println("containerRight: " + containerRight);
	            }
	        }
	        //System.out.println("State: " + diffElement.getState());
	    }

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
	   //logger.error("Unable to generate code: " + ecoreFile + " does not exist in project!");
	}
	
	public static IFile getEcoreFile(final IProject p) {
		String ecoreFileName = MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(p.getName());
		return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
	}
}

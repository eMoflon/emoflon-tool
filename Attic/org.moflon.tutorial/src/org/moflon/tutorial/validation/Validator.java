package org.moflon.tutorial.validation;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tutorial.Common;
import org.moflon.tutorial.helper.MyWorkspaceHelper;

import TGGLanguage.TripleGraphGrammar;
import TutorialValidationRules.Validation;
import TutorialValidationRules.ValidationResult;
import TutorialValidationRules.impl.TutorialValidationRulesFactoryImpl;

public class Validator {

   private static final Logger logger = Logger.getLogger(Validator.class);
   
	private static Validation validation = TutorialValidationRulesFactoryImpl.eINSTANCE.createValidation();
	private static IProject tutorialProject = ResourcesPlugin.getWorkspace().getRoot().getProject(Common.TUTORIAL_PROJECT_NAME);
	
	private String chapter;
	private int step;
	
	private EPackage ecoreModelPackage;
	private EPackage tggPackage;
	private TripleGraphGrammar tgg;
	
	public Validator(final String chapter, final int step) {
		super();
		this.chapter = chapter;
		this.step = step;
	}

	
	
	public void validate() {
		if (!additionalCheck())
			return;
		
		if (!loadPackages())
			return;
		
		
		ValidationResult validationResult = null;

		if (chapter.equalsIgnoreCase("chapter1")) {
			validationResult = validateChapter1();
		} 
		else if (chapter.equalsIgnoreCase("chapter2")) {
			validationResult = validateChapter2();
		} 
		else if (chapter.equalsIgnoreCase("chapter3")) {
			validationResult = validateChapter3();
		}
		
		if (validationResult == null || validationResult.getStep() > step) {
			logger.info("Everything is OK.");
		}
		else {
			if (validationResult.isOldChapter()) {
				logger.error( "The following error was found while checking code that was part of the first chapter! " +
						"In order to validate your work for this chapter you must have a correct version of the code written in chapter 1.");
			}
			logger.error( "Error in step " + 
				validationResult.getStep() + ": " + validationResult.getMessage());	
		}
		
		
		
	}
	
	
	
	private ValidationResult validateChapter1() {
		return validation.checkMetamodelStructure(ecoreModelPackage);
	}
	
	private ValidationResult validateChapter2() {
		ValidationResult result = validateChapter1();
		if (result != null) {
			result.setOldChapter(true);
			return result;
		}
		
		return validation.checkSDMs(ecoreModelPackage);
	}
	
	private ValidationResult validateChapter3() {
		ValidationResult result = validateChapter1();
		if (result != null) {
			result.setOldChapter(true);
			return result;
		}

		return validation.checkTGG(tggPackage, tgg);
	}
	
	
	private boolean loadPackages() {
		IFile ecoreModelPackageFile = MyWorkspaceHelper.getWorkspaceIFile(Common.GENERATED_PROJECT_NAME + "/model/" + Common.GENERATED_PROJECT_NAME + ".ecore");
		IFile tggPackageFile = MyWorkspaceHelper.getWorkspaceIFile(Common.GENERATED_TGG_PROJECT_NAME + "/model/" + Common.GENERATED_TGG_PROJECT_NAME + ".ecore");
		IFile tggFile = MyWorkspaceHelper.getWorkspaceIFile(Common.GENERATED_TGG_PROJECT_NAME + "/model/" + Common.GENERATED_TGG_PROJECT_NAME + ".tgg.xmi");
		
		try {
		
			if (ecoreModelPackageFile.exists()) {
				ecoreModelPackage = (EPackage)eMoflonEMFUtil.loadModel(ecoreModelPackageFile.getLocation().toString());
			} else {
				logger.error( "Cannot find the auto-generated model! Have you already built your project?");
				return false;
			}
			
			
			if (chapter.equalsIgnoreCase("chapter3")) {
				if (tggPackageFile.exists() && tggFile.exists()) {
					tggPackage = (EPackage)eMoflonEMFUtil.loadModel(tggPackageFile.getLocation().toString());
					tgg = (TripleGraphGrammar)eMoflonEMFUtil.loadModel(tggFile.getLocation().toString());
				} else {
					logger.error( "Cannot find the auto-generated TGG model! Have you built your project?");
					return false;
				}
			}
		
		} catch (Exception e) {
			logger.error( "An unexpected error occurred while trying to load the model file(s)!");
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * perform some extra checks that cannot be done via SDMs.
	 * @return true if all checks went OK
	 */
	private boolean additionalCheck() {
		if (!tutorialProject.exists()) {
			logger.error( "The project " + Common.TUTORIAL_PROJECT_NAME + " is missing. Please check for any spelling errors.");
			return false;
		}
		
		IFolder languagePackage = tutorialProject.getFolder(new Path("MOSL/"+Common.TUTORIAL_WORKING_SET + "/" + Common.GENERATED_PROJECT_NAME));
		if (!languagePackage.exists()) {
			logger.error( "The package " + Common.GENERATED_PROJECT_NAME + " is missing or not inside of " + Common.TUTORIAL_WORKING_SET + ". Please check for any spelling errors.");
			return false;
		}
		
		if (chapter.equalsIgnoreCase("chapter3")) {
			return additionalCheckForTGG();
		}
		
		return true;
	}
	
	private boolean additionalCheckForTGG() {
		IFolder tggPackage = tutorialProject.getFolder(new Path("MOSL/"+Common.TUTORIAL_WORKING_SET + "/" + Common.GENERATED_TGG_PROJECT_NAME));
		IFile importsFile = tutorialProject.getFile(new Path("MOSL/_imports.mconf"));
		if (!tggPackage.exists()) {
			logger.error( "The package " + Common.GENERATED_TGG_PROJECT_NAME + " is missing or not inside of " + Common.TUTORIAL_WORKING_SET + ". Please check for any spelling errors.");
			return false;			
		}
		//we only check for the existence of _imports.mconf. If the correct import statement is missing in that file,
		//hopefully an error marker is set while the project is parsed.
		if (!importsFile.exists()) {
			logger.error( "The file MOSL/_imports.mconf missing.");
			return false;
		}
		return true;
	}
	
}

package org.moflon.ide.metamodelevolution.core.processing;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;

public class JavaRefactorProcessor implements MetamodelDeltaProcessor
{
   private static final Logger logger = Logger.getLogger(MetamodelDeltaProcessor.class);

   private static final String IMPL_FILE = "Impl";
   
   private static final String IMPL_FOLDER = "/impl/";
   
   private static final String IMPL_EXTENSION = ".impl";

   private static final String UTIL_EXTENSION = ".util";
   
   @Override
   public void processDelta(IProject project, ChangeSequence delta)
   {
      for (EModelElementChange change : delta.getEModelElementChange())
      {
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renaming = (RenameChange) change;
            if (renaming.arePreviousAndCurrentValueDifferent())
            {
               logger.debug("Change detected for old value: " + renaming.getPreviousValue() + ". New value is: " + renaming.getCurrentValue());
               // todo@settl: Impl und factory method infos aus dem
               // genmodel laden
               // adapt java code
               // rename class and "Impl" class
               // TODO@settl: If possible, leave RenameClassRefactoring as
               // dumb as possible -> invoke it twice for both classes (I
               // guess this is what you already planned to do) (RK)
               
               
               // rename factory method
               // TODO@settl: Create a new MethodRenamingChange here:
               // "createOldClass -> createNewClass in class XYZFactory"
               /*GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project);
               // class name
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName();
               String pattern = genModel.getClassNamePattern();
               
               String genFolder = genModel.getModelDirectory(); // path to gen folder
               String factoryInterfaceName = genModel.getAllGenPackagesWithClassifiers().get(0).getFactoryInterfaceName();
               
               String factoryInterfacePCkgName = genModel.getAllGenPackagesWithClassifiers().get(0).getPackageInterfaceName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getSwitchClassName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getUtilitiesPackageName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getClassPackageName();
               EClass cls = EcoreFactory.eINSTANCE.createEClass(); // If
               // possible,
               // use
               // the
               // EClasses...
               cls.setName("Topology");*/
               // genModel.findGenClassifier(cls);

 
               if(renaming.getElement().equals(E_CLASS))
               {
            	   
            	  /*for (GenClass genClass : genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses())
            	  {
            		  if (genClass.getInterfaceName().equals(renaming.getPreviousValue()))
            		  {
            			// Interface
            			  String interfaceName = genClass.getInterfaceName();
                          RenameRefactoring interfaceProcessor = new RenameClassRefactoring(interfaceName, renaming.getCurrentValue());
                          interfaceProcessor.refactor(project, renaming);
                          
                          // Impl class
                          String className = genClass.getClassName();                                                   
                          RenameRefactoring classProcessor = new RenameClassRefactoring(className, renaming.getCurrentValue() + "Impl");
                          classProcessor.refactor(project, renaming);            			             			  
            		  }
            	  }*/
            	  long startTime = System.nanoTime();
                  RenameRefactoring processor = new RenameClassRefactoring(renaming.getPreviousValue(), renaming.getCurrentValue(), renaming.getPackageName(), true);
                  processor.refactor(project, renaming);   
                  
                  String implPackageName = renaming.getPackageName() + IMPL_FOLDER;
                  RenameRefactoring classProcessor = new RenameClassRefactoring(renaming.getPreviousValue() + IMPL_FILE, renaming.getCurrentValue() + IMPL_FILE, implPackageName, true);
                  classProcessor.refactor(project, renaming);  
                  
                  RenameRefactoring methodRenaming = new RenameMethodRefactoring();
                  methodRenaming.refactor(project, renaming);
                  
                  long elapsedTime = System.nanoTime() - startTime;
                  System.out.println("Elapsed time for coevolution: refactorings: " + elapsedTime/1000000000.0);
                  
                  processInjections(project);

               }
               else if(renaming.getElement().equals(TL_PACKAGE))
               { 
            	   /*RenameRefactoring tlpProcesser = new RenameProjectRefactoring();
            	   tlpProcesser.refactor(project, renaming);*/
               }
               else if(renaming.getElement().equals(E_PACKAGE))
               {                
            	  String oldValue = renaming.getPackageName().substring(0, renaming.getPackageName().lastIndexOf(".")) + "." + renaming.getPreviousValue();
            	  String newValue = renaming.getPackageName().substring(0, renaming.getPackageName().lastIndexOf(".")) + "." + renaming.getCurrentValue();
           	      
            	  // package
                  RenameRefactoring factoryProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Factory", 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "Factory", renaming.getPackageName(), false);
                  factoryProcessor.refactor(project, renaming); 
                  
                  RenameRefactoring packageProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Package", 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "Package", renaming.getPackageName(), false);
                  packageProcessor.refactor(project, renaming); 
                                   
                  RenameRefactoring processor = new RenamePackageRefactoring(oldValue, newValue);
                  processor.refactor(project, renaming);
                  
                  // .impl package                   
                  RenameRefactoring factoryImplProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Factory" + IMPL_FILE, 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "Factory" + IMPL_FILE, renaming.getPackageName() + IMPL_EXTENSION, false);
                  factoryImplProcessor.refactor(project, renaming);  
                                                     
                  RenameRefactoring packageImplProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Package" + IMPL_FILE, 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "Package" + IMPL_FILE, renaming.getPackageName() + IMPL_EXTENSION, false);
                  packageImplProcessor.refactor(project, renaming); 
                                 
                  RenameRefactoring implProcessor = new RenamePackageRefactoring(oldValue + IMPL_EXTENSION, newValue + IMPL_EXTENSION);
                  implProcessor.refactor(project, renaming);
                                   
                  // .util package
                  RenameRefactoring utilProcessor = new RenamePackageRefactoring(oldValue + UTIL_EXTENSION, newValue + UTIL_EXTENSION);
                  utilProcessor.refactor(project, renaming);
                  
                  RenameRefactoring factoryUtilProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Factory" + IMPL_FILE, 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "AdapterFactory", renaming.getPackageName() + UTIL_EXTENSION, false);
                  factoryUtilProcessor.refactor(project, renaming);  
                   
                  RenameRefactoring switchProcessor = new RenameClassRefactoring(StringUtils.capitalize(renaming.getPreviousValue()) + "Package" + IMPL_FILE, 
                		  StringUtils.capitalize(renaming.getCurrentValue()) + "Switch", renaming.getPackageName() + UTIL_EXTENSION, false);
                  switchProcessor.refactor(project, renaming);                 

               }

            }
         }
      }
   }
   /**
    * This method deletes and reextracts all injection files for a given project
    */
   private void processInjections(IProject project)
   {
      long startTime = System.nanoTime();
      try
      {
    	 IFolder injFolder = project.getFolder(WorkspaceHelper.INJECTION_FOLDER);
    	 if (injFolder.members().length == 0) {
    		 System.out.println("No Injections found");
    	     long elapsedTime = System.nanoTime() - startTime;
    	     System.out.println("Elapsed time for coevolution: processInjections: " + elapsedTime/1000000000.0);
    		 return;
    	 }
    	 
         JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();
         IFolder genFolder = project.getFolder(WorkspaceHelper.GEN_FOLDER);

         if (genFolder.members().length != 0)
         {
            genFolder.accept(new IResourceVisitor() {
               @Override
               public boolean visit(IResource resource) throws CoreException
               {
                  if (resource.getType() == (IResource.FILE))
                     extractor.extractInjectionNonInteractively((IFile) resource);
                  return true;
               }
            });
         }

      } catch (Exception e)
      {
         e.printStackTrace();
      }
      long elapsedTime = System.nanoTime() - startTime;
      System.out.println("Elapsed time for coevolution: processInjections: " + elapsedTime/1000000000.0);
   }
}

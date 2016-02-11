package org.moflon.ide.metamodelevolution.core.processing;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;

public class JavaRefactorProcessor implements MetamodelDeltaProcessor
{
   private static final Logger logger = Logger.getLogger(MetamodelDeltaProcessor.class);

   //TODO@settl: replace with this.getImplementationFileSuffix(genModel) (to be defined)
   private static final String IMPL_FILE = "Impl";

   private static final String IMPL_FOLDER = "/impl/";

   private static final String IMPL_EXTENSION = ".impl";

   private static final String UTIL_EXTENSION = ".util";

   private String implementationFileSuffix;

   @Override
   public void processDelta(IProject project, ChangeSequence delta)
   {
      final GenModel genModel = null;
      this.implementationFileSuffix = this.getImplementationFileSuffix(genModel);
      
      for (EModelElementChange change : delta.getEModelElementChange())
      {
         // TODO@settl: Refactor into several methods
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renameChange = (RenameChange) change;
            if (renameChange.arePreviousAndCurrentValueDifferent())
            {
               logger.debug("Change detected for old value: " + renameChange.getPreviousValue() + ". New value is: " + renameChange.getCurrentValue());
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
               /*
                * GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project); // class name
                * genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName(); String
                * pattern = genModel.getClassNamePattern();
                * 
                * String genFolder = genModel.getModelDirectory(); // path to gen folder String factoryInterfaceName =
                * genModel.getAllGenPackagesWithClassifiers().get(0).getFactoryInterfaceName();
                * 
                * String factoryInterfacePCkgName =
                * genModel.getAllGenPackagesWithClassifiers().get(0).getPackageInterfaceName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getSwitchClassName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getUtilitiesPackageName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getClassPackageName(); EClass cls =
                * EcoreFactory.eINSTANCE.createEClass(); // If // possible, // use // the // EClasses...
                * cls.setName("Topology");
                */
               // genModel.findGenClassifier(cls);

               if (renameChange.getElement().equals(E_CLASS))
               {

                  /*
                   * for (GenClass genClass : genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses()) { if
                   * (genClass.getInterfaceName().equals(renaming.getPreviousValue())) { // Interface String
                   * interfaceName = genClass.getInterfaceName(); RenameRefactoring interfaceProcessor = new
                   * RenameClassRefactoring(interfaceName, renaming.getCurrentValue());
                   * interfaceProcessor.refactor(project, renaming);
                   * 
                   * // Impl class String className = genClass.getClassName(); RenameRefactoring classProcessor = new
                   * RenameClassRefactoring(className, renaming.getCurrentValue() + "Impl");
                   * classProcessor.refactor(project, renaming); } }
                   */
                  long startTime = System.nanoTime();
                  RenameRefactoring processor = new RenameClassRefactoring(renameChange.getPreviousValue(), renameChange.getCurrentValue(), renameChange.getPackageName(),
                        true);
                  processor.refactor(project, renameChange);

                  String implPackageName = renameChange.getPackageName() + IMPL_FOLDER;
                  RenameRefactoring classProcessor = new RenameClassRefactoring(renameChange.getPreviousValue() + IMPL_FILE, renameChange.getCurrentValue() + IMPL_FILE,
                        implPackageName, true);
                  classProcessor.refactor(project, renameChange);

                  RenameRefactoring methodRenaming = new RenameMethodRefactoring();
                  methodRenaming.refactor(project, renameChange);

                  long elapsedTime = System.nanoTime() - startTime;
                  System.out.println("Elapsed time for coevolution: refactorings: " + elapsedTime / 1000000000.0);

                  processInjections(project);

               } else if (renameChange.getElement().equals(TL_PACKAGE))
               {
                  /*
                   * RenameRefactoring tlpProcesser = new RenameProjectRefactoring(); tlpProcesser.refactor(project,
                   * renaming);
                   */
               } else if (renameChange.getElement().equals(E_PACKAGE))
               {
                  String oldValue = renameChange.getPackageName().substring(0, renameChange.getPackageName().lastIndexOf(".")) + "." + renameChange.getPreviousValue();
                  String newValue = renameChange.getPackageName().substring(0, renameChange.getPackageName().lastIndexOf(".")) + "." + renameChange.getCurrentValue();

                  //TODO@settl: Encapsulate sequences of refactorings into a new class 'SequenceRefactoring'
                  
                  // package
                  RenameRefactoring factoryProcessor = new RenameClassRefactoring(StringUtils.capitalize(renameChange.getPreviousValue()) + "Factory",
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "Factory", renameChange.getPackageName(), false);
                  factoryProcessor.refactor(project, renameChange);

                  RenameRefactoring packageProcessor = new RenameClassRefactoring(StringUtils.capitalize(renameChange.getPreviousValue()) + "Package",
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "Package", renameChange.getPackageName(), false);
                  packageProcessor.refactor(project, renameChange);

                  RenameRefactoring processor = new RenamePackageRefactoring(oldValue, newValue);
                  processor.refactor(project, renameChange);

                  // .impl package
                  RenameRefactoring factoryImplProcessor = new RenameClassRefactoring(
                        StringUtils.capitalize(renameChange.getPreviousValue()) + "Factory" + IMPL_FILE,
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "Factory" + IMPL_FILE, renameChange.getPackageName() + IMPL_EXTENSION, false);
                  factoryImplProcessor.refactor(project, renameChange);

                  RenameRefactoring packageImplProcessor = new RenameClassRefactoring(
                        StringUtils.capitalize(renameChange.getPreviousValue()) + "Package" + IMPL_FILE,
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "Package" + IMPL_FILE, renameChange.getPackageName() + IMPL_EXTENSION, false);
                  packageImplProcessor.refactor(project, renameChange);

                  RenameRefactoring implProcessor = new RenamePackageRefactoring(oldValue + IMPL_EXTENSION, newValue + IMPL_EXTENSION);
                  implProcessor.refactor(project, renameChange);

                  // .util package
                  RenameRefactoring utilProcessor = new RenamePackageRefactoring(oldValue + UTIL_EXTENSION, newValue + UTIL_EXTENSION);
                  utilProcessor.refactor(project, renameChange);

                  RenameRefactoring factoryUtilProcessor = new RenameClassRefactoring(
                        StringUtils.capitalize(renameChange.getPreviousValue()) + "Factory" + IMPL_FILE,
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "AdapterFactory", renameChange.getPackageName() + UTIL_EXTENSION, false);
                  factoryUtilProcessor.refactor(project, renameChange);

                  RenameRefactoring switchProcessor = new RenameClassRefactoring(StringUtils.capitalize(renameChange.getPreviousValue()) + "Package" + IMPL_FILE,
                        StringUtils.capitalize(renameChange.getCurrentValue()) + "Switch", renameChange.getPackageName() + UTIL_EXTENSION, false);
                  switchProcessor.refactor(project, renameChange);

               }

            }
         }
      }
   }

   private String getImplementationFileSuffix(GenModel genModel)
   {
      //TODO@settl: Extract from genmodel
      return IMPL_FILE;
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
         if (injFolder.members().length == 0)
         {
            System.out.println("No Injections found");
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed time for coevolution: processInjections: " + elapsedTime / 1000000000.0);
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
      System.out.println("Elapsed time for coevolution: processInjections: " + elapsedTime / 1000000000.0);
   }
}

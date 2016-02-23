package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class SequenceRefactoring
{
   private final String previousName;

   private final String currentName;

   private final String packageName;

   public SequenceRefactoring(RenameChange renameChange)
   {
      previousName = renameChange.getPreviousValue();
      currentName = renameChange.getCurrentValue();
      packageName = renameChange.getPackageName();
   }

   /**
    * This method creates all refactorings that are required to handle a Rename EClass change operation.
    * 
    * @param project
    * @param implFileSuffix
    * @return
    */
   public IStatus createClassRefactorings(IProject project, String implFileSuffix)
   {
      RenameClassRefactoring interfaceRefactoring = new RenameClassRefactoring(previousName, currentName, packageName, true);
      IStatus status = interfaceRefactoring.refactor(project);

      String implPackageName = packageName + "/impl/";
      RenameRefactoring classRefactoring = new RenameClassRefactoring(previousName + implFileSuffix, currentName + implFileSuffix, implPackageName, true);
      classRefactoring.refactor(project);

      RenameRefactoring factoryMethodRenaming = new RenameMethodRefactoring(getFactoryMethodName(previousName), getFactoryMethodName(currentName), packageName,
            null, getFactoryInterfaceName(getLastPackageComponent(packageName)));
      factoryMethodRenaming.refactor(project);

      RenameRefactoring methodRenaming = new RenameMethodRefactoring(getPackageMethodName(previousName), getPackageMethodName(currentName), packageName,
            null, getPackageInterfaceName(getLastPackageComponent(packageName)));
      methodRenaming.refactor(project);

      return status;
   }

   private String getLastPackageComponent(String packageName)
   {
      return packageName.substring(packageName.lastIndexOf(".") + 1);
   }
   
   private String getFactoryInterfaceName(String name)
   {
      return StringUtils.capitalize(name + "Factory");
   }

   private String getPackageInterfaceName(String packageName)
   {
      return StringUtils.capitalize(packageName + "Package");
   }

   private String getFactoryMethodName(String methodName)
   {
      return "create" + methodName;
   }

   private String getPackageMethodName(String methodName)
   {
      return "get" + methodName;
   }

   /**
    * This method creates all refactorings that are required to handle a Rename EPackage change operation.
    * @param project
    * @param implementationFileSuffix
    * @return
    */
   public IStatus createPackageRefactorings(IProject project, String implementationFileSuffix)
   {
      
       String oldValue = packageName.substring(0, packageName.lastIndexOf(".")) +
       "." + previousName; String newValue = packageName.substring(0,
             packageName.lastIndexOf(".")) + "." + currentName;
       
       //final GenModel genmodel = eMoflonEMFUtil.extractGenModelFromProject(project); 
       /*TODO@settl: Encapsulate
       * sequences of refactorings into a new class 'SequenceRefactoring' String factoryInterfaceName =
       * renameChange.getCurrentValue() + "Factory"; List<GenPackage> genPackages =
       * genmodel.getAllGenPackagesWithClassifiers(); for (GenPackage pckg : genPackages) { String test =
       * pckg.getNSName(); if (pckg.getNSName().equals(renameChange.getPackageName())) { factoryInterfaceName =
       * pckg.getFactoryInterfaceName(); break; } }
       * */
       //RenameRefactoring factoryProcessor = new RenameClassRefactoring(getFactoryInterfaceName(previousName), getFactoryInterfaceName(currentName), packageName, false); 
       //factoryProcessor.refactor(project);
        /* RenameRefactoring packageProcessor = new
       * RenameClassRefactoring(StringUtils.capitalize(renameChange.getPreviousValue()) + "Package",
       * StringUtils.capitalize(renameChange.getCurrentValue()) + "Package", renameChange.getPackageName(), false);
       * packageProcessor.refactor(project, renameChange);
       * 
       * RenameRefactoring processor = new RenamePackageRefactoring(oldValue, newValue); processor.refactor(project,
       * renameChange);
       * 
       * // .impl package RenameRefactoring factoryImplProcessor = new RenameClassRefactoring(
       * StringUtils.capitalize(renameChange.getPreviousValue()) + "Factory" + IMPL_FILE,
       * StringUtils.capitalize(renameChange.getCurrentValue()) + "Factory" + IMPL_FILE, renameChange.getPackageName() +
       * IMPL_EXTENSION, false); factoryImplProcessor.refactor(project, renameChange);
       * 
       * RenameRefactoring packageImplProcessor = new RenameClassRefactoring(
       * StringUtils.capitalize(renameChange.getPreviousValue()) + "Package" + IMPL_FILE,
       * StringUtils.capitalize(renameChange.getCurrentValue()) + "Package" + IMPL_FILE, renameChange.getPackageName() +
       * IMPL_EXTENSION, false); packageImplProcessor.refactor(project, renameChange);
       * 
       * RenameRefactoring implProcessor = new RenamePackageRefactoring(oldValue + IMPL_EXTENSION, newValue +
       * IMPL_EXTENSION); implProcessor.refactor(project, renameChange);
       * 
       * // .util package RenameRefactoring utilProcessor = new RenamePackageRefactoring(oldValue + UTIL_EXTENSION,
       * newValue + UTIL_EXTENSION); utilProcessor.refactor(project, renameChange);
       * 
       * RenameRefactoring factoryUtilProcessor = new RenameClassRefactoring(
       * StringUtils.capitalize(renameChange.getPreviousValue()) + "Factory" + IMPL_FILE,
       * StringUtils.capitalize(renameChange.getCurrentValue()) + "AdapterFactory", renameChange.getPackageName() +
       * UTIL_EXTENSION, false); factoryUtilProcessor.refactor(project, renameChange);
       * 
       * RenameRefactoring switchProcessor = new
       * RenameClassRefactoring(StringUtils.capitalize(renameChange.getPreviousValue()) + "Package" + IMPL_FILE,
       * StringUtils.capitalize(renameChange.getCurrentValue()) + "Switch", renameChange.getPackageName() +
       * UTIL_EXTENSION, false); switchProcessor.refactor(project, renameChange);
       */
      return Status.OK_STATUS;
   }
}

package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class SequenceRefactoring
{
	private static final Logger logger = Logger.getLogger(SequenceRefactoring.class);
	
	private final String previousName;

	private final String currentName;

	private final String packageName;

	public SequenceRefactoring(RenameChange renameChange)
	{
		previousName = renameChange.getPreviousValue();
		currentName = renameChange.getCurrentValue();
		packageName = renameChange.getPackageName();
	}
   
	public SequenceRefactoring(String previousName, String currentName, String packageName){
		this.previousName = previousName;
		this.currentName = currentName;
		this.packageName = packageName;
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
		try
		{
			String implPackageName = packageName + "/impl/";
			RenameRefactoring classRefactoring = new RenameClassRefactoring(previousName + implFileSuffix, currentName + implFileSuffix, implPackageName, true);
			classRefactoring.refactor(project);
	
			Thread.sleep(10);
	      
			RenameRefactoring factoryMethodRenaming = new RenameMethodRefactoring(getFactoryMethodName(previousName), getFactoryMethodName(currentName), packageName,
					null, getFactoryInterfaceName(getLastPackageComponent(packageName)));
			factoryMethodRenaming.refactor(project);
	
			Thread.sleep(10);
	      
			RenameRefactoring methodRenaming = new RenameMethodRefactoring(getPackageMethodName(previousName), getPackageMethodName(currentName), packageName, null,
					getPackageInterfaceName(getLastPackageComponent(packageName)));
			methodRenaming.refactor(project);
	
			Thread.sleep(10);
		} catch (InterruptedException ie){
    	  logger.error("An Exception has been thrown", ie);
		}
      
		return status;
	}

   /**
    * This method creates all refactorings that are required to handle a Rename EPackage change operation.
    * 
    * @param project
    * @param implementationFileSuffix
    * @return
    */
   public IStatus createPackageRefactorings(IProject project, String implementationFileSuffix)
   {
      /*
       * Something goes wrong in the Eclipse LTK when calling that many refactorings one after another (concurrency problem?)
       * Solution 1: Proper concurrency handling e.g., wait until one refactoring is done before calling the next one
       * Solution 2: Only call refactorings that are propably rather used than others (might omit important elements though)
       */
      try
      {
         // PackageName*Factory
         RenameRefactoring factoryProcessor = new RenameClassRefactoring(getFactoryInterfaceName(previousName), getFactoryInterfaceName(currentName),
               getSubPackage(previousName), false);
         factoryProcessor.refactor(project);
         // just a quick fix for now - give the Eclipse LTK some time to process the refactoring
         Thread.sleep(1000);

         // PackageName*Package
         RenameRefactoring packageProcessor = new RenameClassRefactoring(getPackageInterfaceName(previousName), getPackageInterfaceName(currentName),
               getSubPackage(previousName), false);
         packageProcessor.refactor(project);

         Thread.sleep(1000);
         
         // PackageName*FactoryImpl
         RenameRefactoring factoryImplProcessor = new RenameClassRefactoring(getFactoryClassName(previousName), getFactoryClassName(currentName),
               getSubPackage(previousName) + getImplPackageExtension(), false);
         factoryImplProcessor.refactor(project);

         Thread.sleep(1000);
         
         // PackageName*PackageImpl
         RenameRefactoring packageImplProcessor = new RenameClassRefactoring(getPackageClassName(previousName), getPackageClassName(currentName),
               getSubPackage(previousName) + getImplPackageExtension(), false);
         packageImplProcessor.refactor(project);

         Thread.sleep(1000);
         
         // AdapterFactory
         RenameRefactoring factoryUtilProcessor = new RenameClassRefactoring(getAdapterFactoryInterfaceName(previousName),
               getAdapterFactoryInterfaceName(currentName), getSubPackage(previousName) + getUtilPackageExtension(), false);
         factoryUtilProcessor.refactor(project);

         Thread.sleep(2000);
         
         // Switch
         RenameRefactoring switchProcessor = new RenameClassRefactoring(getSwitchInterfaceName(previousName), getSwitchInterfaceName(currentName),
               getSubPackage(previousName) + getUtilPackageExtension(), false);
         switchProcessor.refactor(project);

         Thread.sleep(2000);
         
         // Package
         RenameRefactoring processor = new RenamePackageRefactoring(getSubPackage(previousName), getSubPackage(currentName));
         processor.refactor(project);

         Thread.sleep(2000);
         
         // .impl package
         RenameRefactoring implProcessor = new RenamePackageRefactoring(getSubPackage(previousName) + getImplPackageExtension(),
               getSubPackage(currentName) + getImplPackageExtension());
         implProcessor.refactor(project);

         Thread.sleep(2000);
         
         // .util package
         RenameRefactoring utilProcessor = new RenamePackageRefactoring(getSubPackage(previousName) + getUtilPackageExtension(),
               getSubPackage(currentName) + getUtilPackageExtension());
         utilProcessor.refactor(project);
      } catch (Exception e)
      {
        logger.error("An Exception has been thrown", e);
      }
      return Status.OK_STATUS;
   }

   private String getLastPackageComponent(String packageName)
   {
      return packageName.substring(packageName.lastIndexOf(".") + 1);
   }

   private String getSubPackage(String subPackageName)
   {
      return packageName.substring(0, packageName.lastIndexOf(".") + 1) + subPackageName;
   }

   private String getFactoryInterfaceName(String name)
   {
      return StringUtils.capitalize(name + "Factory");
   }

   private String getFactoryClassName(String name)
   {
      return StringUtils.capitalize(name + "FactoryImpl");
   }

   private String getAdapterFactoryInterfaceName(String name)
   {
      return StringUtils.capitalize(name + "AdapterFactory");
   }

   private String getPackageInterfaceName(String packageName)
   {
      return StringUtils.capitalize(packageName + "Package");
   }

   private String getPackageClassName(String packageName)
   {
      return StringUtils.capitalize(packageName + "PackageImpl");
   }

   private String getSwitchInterfaceName(String name)
   {
      return StringUtils.capitalize(name + "Switch");
   }

   private String getFactoryMethodName(String methodName)
   {
      return "create" + methodName;
   }

   private String getPackageMethodName(String methodName)
   {
      return "get" + methodName;
   }

   private String getImplPackageExtension()
   {
      return ".impl";
   }

   private String getUtilPackageExtension()
   {
      return ".util";
   }
}

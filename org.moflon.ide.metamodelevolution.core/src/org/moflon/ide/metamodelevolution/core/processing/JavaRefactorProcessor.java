package org.moflon.ide.metamodelevolution.core.processing;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;

public class JavaRefactorProcessor implements MetamodelDeltaProcessor
{
   private static final Logger logger = Logger.getLogger(MetamodelDeltaProcessor.class);

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
               
               if(renaming.getElement().equals(E_CLASS))
               {
                  RenameRefactoring processor = new RenameClassRefactoring();
                  processor.refactor(project, renaming);
               }
               else if(renaming.getElement().equals(E_PACKAGE))
               {
                  //RenameRefactoring processor = new RenamePackageRefactoring();
                  //processor.refactor(project, renaming);
               }
               
               // rename factory method
               // TODO@settl: Create a new MethodRenamingChange here:
               // "createOldClass -> createNewClass in class XYZFactory"
               GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project);
               // TODO@settl: This is how you can get the implementation
               // class name
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
               // TODO@settl: This is how you can get the interface name
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName();
               genModel.getModelDirectory(); // path to gen folder
               genModel.getAllGenPackagesWithClassifiers().get(0).getFactoryInterfaceName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getPackageInterfaceName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getSwitchClassName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getUtilitiesPackageName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getClassPackageName();
               EClass cls = EcoreFactory.eINSTANCE.createEClass(); // If
               // possible,
               // use
               // the
               // EClasses...
               cls.setName("Topology");
               // genModel.findGenClassifier(cls);

               RenameRefactoring methodRenaming = new RenameMethodRefactoring();
               methodRenaming.refactor(project, renaming);

            }
         }
      }
   }

}

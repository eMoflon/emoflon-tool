package org.moflon.testframework.tgg;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.moflon.core.utilities.EmfCompareUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import MocaTree.File;
import MocaTree.Folder;
import MocaTree.Node;
import TGGLanguage.TGGLanguagePackage;
import TGGLanguage.algorithm.ApplicationTypes;
import TGGLanguage.analysis.StaticAnalysis;

public class IntegratorTest extends TransformationTest
{

   public IntegratorTest(EPackage sourcePackage, EPackage corrPackage, EPackage targetPackage)
   {
      super(sourcePackage, corrPackage, targetPackage);
   }

   @Override
   public EObject performTransformation(ApplicationTypes direction) throws IOException
   {
      enableCachingIfMocaTree(direction);
      performIntegration(direction);

      EObject created = (direction == ApplicationTypes.FORWARD) ? helper.getTrg() : helper.getSrc();
      return created;
   }

   // MOCATREE caching methods
   protected void enableCachingIfMocaTree(ApplicationTypes direction)
   {

      EObject input = null;
      if (ApplicationTypes.FORWARD == direction)
         input = helper.getSrc();
      else if (ApplicationTypes.BACKWARD == direction)
         input = helper.getTrg();
      else
         return;

      if (input.eClass().getEPackage().getName().equals("MocaTree"))
      {
         if (input instanceof Node)
            ((Node) input).enableCachingForTree();
         else if (input instanceof File)
            ((File) input).getRootNode().enableCachingForTree();
         else if (input instanceof Folder)
            enableCachingInFolder((Folder) input);
      }
   }

   private void enableCachingInFolder(Folder input)
   {
      for (File file : input.getFile())
      {
         file.getRootNode().enableCachingForTree();
      }
      for (Folder subfolder : input.getSubFolder())
      {
         enableCachingInFolder(subfolder);
      }
   }

   /**
    * Empty default implementation
    */
   @Override
   public void wrapUp()
   {

   }

   @Override
   protected void saveOutput(String testCaseName, EObject created)
   {
      super.saveOutput(testCaseName, created);
      try
      {
         helper.saveCorr(getFullOutpath() + testCaseName + "_corr.xmi");
      } catch (Exception e)
      {

      }
      helper.saveSynchronizationProtocol(getOutPath() + "protocol_" + testCaseName + ".xmi");
   }

   /**
    * Default implementation using EMFCompare for comparison. Override in your test class for a problem-specific
    * comparison, which is usually much better!
    * 
    * @throws InterruptedException
    */
   @Override
   public void compare(EObject expected, EObject created) throws InterruptedException
   {
      List<Diff> differences = EmfCompareUtil.compareAndFilter(expected, created, true);
      Assert.assertEquals(0, differences.size());
   }

}

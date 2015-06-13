package org.moflon.testframework.tgg;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;
import org.moflon.tgg.algorithm.modelgenerator.controller.DefaultModelGenController;
import org.moflon.tgg.algorithm.modelgenerator.controller.LimitedRandomRuleSelector;
import org.moflon.tgg.algorithm.modelgenerator.controller.MaxRulePerformCounterController;
import org.moflon.tgg.algorithm.modelgenerator.controller.TimeoutController;

public class ModelgenTest
{

   private EPackage tggPackage;

   public ModelgenTest(EPackage tggPackage)
   {

      eMoflonEMFUtil.init(tggPackage);
      BasicConfigurator.configure();

      this.tggPackage = tggPackage;
   }

   public ModelGenerator createDefaultModelgenerator(String rootContextRuleName, int maxRulePerformCounter, int timeout)
   {

      AbstractModelGenerationController controller = new DefaultModelGenController();
      controller.addContinuationController(new MaxRulePerformCounterController(maxRulePerformCounter));
      controller.addContinuationController(new TimeoutController(timeout));
      controller.setRuleSelector(new LimitedRandomRuleSelector().addRuleLimit(rootContextRuleName, 1));

      ModelGenerator gen = new ModelGenerator(tggPackage, controller);
      ModelGenerator.writeLog = false;
      return gen;
   }

   public void runDefaultModelgeneratorTest(String rootContextRuleName, int maxRulePerformCounter, int timeout)
   {
      File dir = new File("instances");
      if (dir.isDirectory())
      {
         for (File file : dir.listFiles())
         {
            file.delete();
         }
         dir.delete();
      }

      ModelGenerator generator = createDefaultModelgenerator(rootContextRuleName, maxRulePerformCounter, timeout);
      Assert.assertTrue(generator.generate().getModelgenStats().getTotalRulePerformCount() > 0);

   }

   public void runDefaultModelgeneratorTest(String rootContextRuleName, int maxRulePerformCounter)
   {
      runDefaultModelgeneratorTest(rootContextRuleName, maxRulePerformCounter, 5000);
   }

}

package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardFinalSolutionVisual extends AbstractExampleWizard
{

   private static final String RES_PATH = "resources/handbook-examples/FinalSolution/Visual";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/Dictionary.zip", "Dictionary"), //
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"), //
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/LearningBoxLanguage.zip", "LearningBoxLanguage"), //
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"), //
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/LeitnersBox.zip", "LeitnersBox"), //
            new ProjectDescriptor(BUNDLE_ID, RES_PATH + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));
   }

}

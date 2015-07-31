package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardFinalSolutionVisual extends AbstractExampleWizard
{

   private static final String ROOT = "resources/handbook-examples/FinalSolution/Visual";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/Dictionary.zip", "Dictionary"), //
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"), //
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"), //
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"), //
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/LeitnersBox.zip", "LeitnersBox"), //
            new ProjectDescriptor(BUNDLE_ID, ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));
   }

}

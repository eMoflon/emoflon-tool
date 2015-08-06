package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardFinalSolutionVisual extends AbstractExampleWizard
{

   private static final String ROOT = "resources/handbook-examples/FinalSolution/Visual";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/Dictionary.zip", "Dictionary"), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LeitnersBox.zip", "LeitnersBox"), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));
   }

}

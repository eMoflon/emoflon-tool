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
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/Dictionary.zip", "Dictionary"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/DictionaryLanguage.zip", "DictionaryLanguage"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/LeitnersBox.zip", "LeitnersBox"), //
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));
   }

}

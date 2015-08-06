package org.moflon.handbook.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.moflon.tutorial.TutorialPlugin;

public class WizardFinalSolutionTextual extends AbstractExampleWizard
{

   private static final String ROOT = "resources/handbook-examples/FinalSolution/Textual";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(1);
      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/Dictionary.zip", "Dictionary"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/DictionaryLanguage.zip", "DictionaryLanguage"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LeitnersBox.zip", "LeitnersBox"));

      projects.add(new ProjectDescriptor(TutorialPlugin.getPluginId(), ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));

      return projects;
   }

}

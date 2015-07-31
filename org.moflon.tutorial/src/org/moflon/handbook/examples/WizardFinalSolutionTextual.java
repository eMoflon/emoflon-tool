package org.moflon.handbook.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WizardFinalSolutionTextual extends AbstractExampleWizard
{

   private static final String ROOT = "resources/handbook-examples/FinalSolution/Textual";

   private static final String BUNDLE = "org.moflon.CheatSheetTutorial";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(1);
      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/Dictionary.zip", "Dictionary"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/DictionaryLanguage.zip", "DictionaryLanguage"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/LeitnersBox.zip", "LeitnersBox"));

      projects.add(new ProjectDescriptor(BUNDLE, ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));

      return projects;
   }

}

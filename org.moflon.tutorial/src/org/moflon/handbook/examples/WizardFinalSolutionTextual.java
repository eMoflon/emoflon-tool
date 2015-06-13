package org.moflon.handbook.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WizardFinalSolutionTextual extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(1);
      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/Dictionary.zip", "Dictionary"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/DictionaryCodeAdapter.zip",
            "DictionaryCodeAdapter"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/DictionaryLanguage.zip",
            "DictionaryLanguage"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/LearningBoxLanguage.zip",
            "LearningBoxLanguage"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial",
            "resources/handbook-examples/FinalSolution/Textual/LearningBoxToDictionaryIntegration.zip", "LearningBoxToDictionaryIntegration"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/LeitnersBox.zip", "LeitnersBox"));

      projects.add(new ProjectDescriptor("org.moflon.CheatSheetTutorial", "resources/handbook-examples/FinalSolution/Textual/LeitnersLearningBox.zip",
            "LeitnersLearningBox"));

      return projects;
   }

}

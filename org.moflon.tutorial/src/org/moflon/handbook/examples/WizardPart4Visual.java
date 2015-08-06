package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart4Visual extends AbstractExampleWizard
{

   private static final String GUI_PROJECT_NAME = "LeitnersBox";

   private static final String ARCHIVE_OF_GUI = "resources/handbook-examples/LeitnersBoxGUI.zip";

   private static final String METAMODEL_PROJECT_NAME = "LeitnersLearningBox";

   private static final String ARCHIVE_OF_METAMODEL = "resources/handbook-examples/PartIV/visualLeitnersLearningBox.zip";

   private static final String REPO_PROJECT_NAME = "LearningBoxLanguage";

   private static final String ARCHIVE_OF_REPO_PROJECT = "resources/handbook-examples/PartIV/LearningBoxLanguage.zip";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_REPO_PROJECT, REPO_PROJECT_NAME), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_METAMODEL, METAMODEL_PROJECT_NAME), //
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_GUI, GUI_PROJECT_NAME));
   }

}

package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart4Textual extends AbstractExampleWizard
{

   private static final String PROJECT_NAME_OF_GENERATED_CODE = "LearningBoxLanguage";

   private static final String PROJECT_NAME_OF_METAMODEL = "LeitnersLearningBox";

   private static final String PROJECT_NAME_OF_GUI = "LeitnersBox";

   private static final String ARCHIVE_OF_GENERATED_CODE = "resources/handbook-examples/PartIV/LearningBoxLanguage.zip";

   private static final String ARCHIVE_OF_METAMODEL = "resources/handbook-examples/PartIV/textualLeitnersLearningBox.zip";

   private static final String ARCHIVE_OF_GUI = "resources/handbook-examples/LeitnersBoxGUI.zip";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_GENERATED_CODE, PROJECT_NAME_OF_GENERATED_CODE),//
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_METAMODEL, PROJECT_NAME_OF_METAMODEL),//
            new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE_OF_GUI, PROJECT_NAME_OF_GUI));
   }

}

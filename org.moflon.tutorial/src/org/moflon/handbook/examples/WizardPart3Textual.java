package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardPart3Textual extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(
            //
            new ProjectDescriptor(BUNDLE_ID, "resources/handbook-examples/PartIII/LearningBoxLanguage.zip", "LearningBoxLanguage"), //
            new ProjectDescriptor(BUNDLE_ID, "resources/handbook-examples/PartIII/textualLeitnersLearningBox.zip", "LeitnersLearningBox"), //
            new ProjectDescriptor(BUNDLE_ID, "resources/handbook-examples/LeitnersBoxGUI.zip", "LeitnersBox"));
   }

}

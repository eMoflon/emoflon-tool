package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart3Visual extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(//
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), "resources/handbook-examples/PartIII/LearningBoxLanguage.zip", "LearningBoxLanguage"),//
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), "resources/handbook-examples/PartIII/visualLeitnersLearningBox.zip", "LeitnersLearningBoxVisual"),//
            new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), "resources/handbook-examples/LeitnersBoxGUI.zip", "LeitnersBox"));
   }

}

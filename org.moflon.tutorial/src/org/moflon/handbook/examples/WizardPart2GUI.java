package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart2GUI extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(new ProjectDescriptor(TutorialPlugin.getPluginId(), "resources/handbook-examples/LeitnersBoxGUI.zip", "LeitnersBox"));
   }

}

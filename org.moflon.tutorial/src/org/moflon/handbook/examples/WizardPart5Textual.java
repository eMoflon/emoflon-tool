package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart5Textual extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(new ProjectDescriptor(TutorialPlugin.getDefault().getPluginId(), "resources/handbook-examples/PartV/textualDictionary.zip", "Dictionary"));
   }

}

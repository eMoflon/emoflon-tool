package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

import org.moflon.tutorial.TutorialPlugin;

public class WizardPart5Textual extends AbstractExampleWizard
{

   private static final String PROJECT_NAME = "Dictionary";

   private static final String ARCHIVE = "resources/handbook-examples/PartV/textualDictionary.zip";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(new ProjectDescriptor(TutorialPlugin.getPluginId(), ARCHIVE, PROJECT_NAME));
   }

}

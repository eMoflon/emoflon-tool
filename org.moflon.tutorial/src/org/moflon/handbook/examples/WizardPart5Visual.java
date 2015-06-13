package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardPart5Visual extends AbstractExampleWizard
{

   private static final String PROJECT_NAME = "Dictionary";

   private static final String ARCHIVE = "resources/handbook-examples/PartV/visualDictionary.zip";

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(new ProjectDescriptor(BUNDLE_ID, ARCHIVE, PROJECT_NAME));
   }

}

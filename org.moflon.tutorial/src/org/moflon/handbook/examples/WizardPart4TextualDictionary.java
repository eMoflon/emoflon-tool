package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardPart4TextualDictionary extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {

      return Arrays.asList(new ProjectDescriptor(BUNDLE_ID, "resources/handbook-examples/PartIV/textualDictionary.zip", "Dictionary"));
   }

}

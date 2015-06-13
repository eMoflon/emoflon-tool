package org.moflon.handbook.examples;

import java.util.Arrays;
import java.util.Collection;

public class WizardPart2GUI extends AbstractExampleWizard
{

   @Override
   protected Collection<ProjectDescriptor> getProjectDescriptors()
   {
      return Arrays.asList(new ProjectDescriptor(BUNDLE_ID, "resources/handbook-examples/LeitnersBoxGUI.zip", "LeitnersBox"));
   }

}

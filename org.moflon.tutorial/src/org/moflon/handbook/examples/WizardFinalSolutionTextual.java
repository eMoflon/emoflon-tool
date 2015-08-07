package org.moflon.handbook.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.moflon.tutorial.TutorialPlugin;

public class WizardFinalSolutionTextual extends AbstractExampleWizard {

	private static final String ROOT = "resources/handbook-examples/FinalSolution/Textual";

	@Override
	protected Collection<ProjectDescriptor> getProjectDescriptors() {
		List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(1);
		String pluginId = TutorialPlugin.getDefault().getPluginId();
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/Dictionary.zip", "Dictionary"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/DictionaryCodeAdapter.zip", "DictionaryCodeAdapter"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/DictionaryLanguage.zip", "DictionaryLanguage"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/LearningBoxLanguage.zip", "LearningBoxLanguage"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/LearningBoxToDictionaryIntegration.zip",
				"LearningBoxToDictionaryIntegration"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/LeitnersBox.zip", "LeitnersBox"));
		projects.add(new ProjectDescriptor(pluginId, ROOT + "/LeitnersLearningBox.zip", "LeitnersLearningBox"));

		return projects;
	}

}

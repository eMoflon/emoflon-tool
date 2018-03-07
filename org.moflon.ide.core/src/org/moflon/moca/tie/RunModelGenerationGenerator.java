package org.moflon.moca.tie;

import org.eclipse.core.resources.IProject;

public class RunModelGenerationGenerator extends RunIntegrationGeneratorBatch {

	public RunModelGenerationGenerator(IProject project) {
		super(project);
	}

	@Override
	protected String getTemplateName() {
		return "TGGMainModelGen";
	}

	protected String getClassName() {
		return getRootOfClassName() + "ModelGen";
	}
}

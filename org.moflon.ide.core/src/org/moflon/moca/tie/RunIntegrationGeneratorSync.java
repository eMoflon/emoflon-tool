package org.moflon.moca.tie;

import org.eclipse.core.resources.IProject;

public class RunIntegrationGeneratorSync extends RunIntegrationGeneratorBatch {
	public RunIntegrationGeneratorSync(IProject project) {
		super(project);
	}

	@Override
	protected String getTemplateName() {
		return "TGGMainSync";
	}

	@Override
	protected String getClassName() {
		return getRootOfClassName() + "Sync";
	}
}

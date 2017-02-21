package org.moflon.gt.mosl.codeadapter.rules.impl;

import org.moflon.gt.mosl.codeadapter.rules.Rule;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;

public class GraphTransformationFileToEPackageRule implements Rule<GraphTransformationFile> {

	@Override
	public <Output, Context> Output transform(GraphTransformationFile input, Context context,
			Class<? extends Output> outputClassContext, Class<? extends Context> contextClassContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends GraphTransformationFile> getInputClassContext() {
		return GraphTransformationFile.class;
	}

}

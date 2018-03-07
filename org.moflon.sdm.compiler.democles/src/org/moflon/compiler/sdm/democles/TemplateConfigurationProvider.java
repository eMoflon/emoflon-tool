package org.moflon.compiler.sdm.democles;

import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.stringtemplate.v4.STGroup;

public interface TemplateConfigurationProvider {
	public STGroup getTemplateGroup(final String patternType);

	public OperationSequenceCompiler getOperationSequenceCompiler(final String patternType);
}

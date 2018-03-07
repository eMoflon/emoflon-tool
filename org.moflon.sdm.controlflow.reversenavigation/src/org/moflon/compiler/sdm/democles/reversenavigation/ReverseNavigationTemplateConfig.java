package org.moflon.compiler.sdm.democles.reversenavigation;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.PatternInvocationConstraintTemplateProvider;
import org.gervarro.democles.relational.RelationalConstraintTemplateProvider;
import org.moflon.compiler.sdm.democles.DefaultCodeGeneratorConfig;
import org.moflon.compiler.sdm.democles.DefaultTemplateConfiguration;
import org.stringtemplate.v4.STGroup;

public class ReverseNavigationTemplateConfig extends DefaultTemplateConfiguration {

	public ReverseNavigationTemplateConfig(final GenModel genModel) {
		super(genModel);

		operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR,
				createBlackOperationSequenceCompiler());

		STGroup blackTemplateGroup = templates.get(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR);
		blackTemplateGroup.loadGroupFile("/reverseNavi/",
				"platform:/plugin/org.moflon.sdm.controlflow.reversenavigation/templates/stringtemplate/EMFOperation.stg");
	}

	@SuppressWarnings("unchecked")
	public static OperationSequenceCompiler createBlackOperationSequenceCompiler() {
		return new OperationSequenceCompiler(new PatternInvocationConstraintTemplateProvider(),
				new RelationalConstraintTemplateProvider(), new ReverseNavigationTemplateProvider());
	}
}

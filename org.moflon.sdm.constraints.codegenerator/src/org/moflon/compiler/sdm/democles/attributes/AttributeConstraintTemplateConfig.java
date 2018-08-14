package org.moflon.compiler.sdm.democles.attributes;

import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.PatternInvocationConstraintTemplateProvider;
import org.gervarro.democles.codegen.emf.EMFTemplateProvider;
import org.gervarro.democles.relational.RelationalConstraintTemplateProvider;
import org.moflon.compiler.sdm.democles.DefaultCodeGeneratorConfig;
import org.moflon.compiler.sdm.democles.DefaultTemplateConfiguration;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintsOperationActivator;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

public class AttributeConstraintTemplateConfig extends DefaultTemplateConfiguration {

	/**
	 * Initializes this template configuration with the given {@link GenModel} and a
	 * list of user-defined attribute constraint libraries
	 *
	 * @param genModel
	 *            the {@link GenModel}
	 * @param attributeConstraintLibs
	 *            the {@link AttributeConstraintLibrary}s
	 */
	public AttributeConstraintTemplateConfig(final GenModel genModel,
			final List<AttributeConstraintLibrary> attributeConstraintLibs) {
		super(genModel);
		addAttrConstTemplatesToBlackTemplates(attributeConstraintLibs);
		operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR,
				createBlackOperationSequenceCompiler());
	}

	@SuppressWarnings("unchecked")
	public static OperationSequenceCompiler createBlackOperationSequenceCompiler() {
		return new OperationSequenceCompiler(new PatternInvocationConstraintTemplateProvider(),
				new RelationalConstraintTemplateProvider(), new EMFTemplateProvider(),
				new AttributeConstraintsTemplateProvider());
	}

	/**
	 * Adds the templates for user-defined constraints to the template group for
	 * black patterns (i.e., patterns that represent preserved variables).
	 *
	 * @param attributeConstraintLibs
	 *            the library containing user-defined attribute constraints and
	 *            operations
	 */
	private void addAttrConstTemplatesToBlackTemplates(final List<AttributeConstraintLibrary> attributeConstraintLibs) {
		final STGroup group = getTemplateGroup(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR);
		for (final AttributeConstraintLibrary library : attributeConstraintLibs) {

			for (final OperationSpecificationGroup operationSpecificationGroup : library.getOperationSpecifications()) {

				if (!operationSpecificationGroup.isTemplateGroupGenerated()) {
					operationSpecificationGroup.gernerateTemplate();
				}

				final STGroup newGroup = new STGroupString("someName",
						operationSpecificationGroup.getTemplateGroupString(),
						AttributeConstraintsOperationActivator.OUTER_ST_DELIMITER,
						AttributeConstraintsOperationActivator.OUTER_ST_DELIMITER);

				for (final String templateName : newGroup.getTemplateNames()) {
					final ST template = newGroup.getInstanceOf(templateName);
					String fullyQualifiedTemplateName = "/" + library.getPrefix() + "/"
							+ operationSpecificationGroup.getOperationIdentifier() + template.getName();
					group.rawDefineTemplate(fullyQualifiedTemplateName, template.impl, null);
				}

			}

		}

	}

}

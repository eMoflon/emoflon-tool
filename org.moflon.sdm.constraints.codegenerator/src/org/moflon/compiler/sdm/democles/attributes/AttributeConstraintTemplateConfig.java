package org.moflon.compiler.sdm.democles.attributes;

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

public class AttributeConstraintTemplateConfig extends DefaultTemplateConfiguration
{

   public AttributeConstraintTemplateConfig(final GenModel genModel, final java.util.List<AttributeConstraintLibrary> attributeConstraintLibs)
   {
      super(genModel);
      addAttrConstTempleatesToBlackTemplates(attributeConstraintLibs);
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR, createBlackOperationSequenceCompiler());
   }

   @SuppressWarnings("unchecked")
   public static OperationSequenceCompiler createBlackOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new PatternInvocationConstraintTemplateProvider(), new RelationalConstraintTemplateProvider(),
            new EMFTemplateProvider(), new AttributeConstraintsTemplateProvider());
   }

   private void addAttrConstTempleatesToBlackTemplates(final java.util.List<AttributeConstraintLibrary> attributeConstraintLibs)
   {
      final STGroup group = getTemplateGroup(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR);
      for (AttributeConstraintLibrary library : attributeConstraintLibs)
      {

         for (OperationSpecificationGroup opSpecGroup : library.getOperationSpecifications())
         {
            if (!opSpecGroup.isTemplateGroupGenerated())
            {
               opSpecGroup.gernerateTemplate();

            }
            STGroup newGroup = new STGroupString("someName", opSpecGroup.getTemplateGroupString(), AttributeConstraintsOperationActivator.OUTER_ST_DELIMITER, AttributeConstraintsOperationActivator.OUTER_ST_DELIMITER);
            for (String templateName : newGroup.getTemplateNames())
            {
               ST template = newGroup.getInstanceOf(templateName);
               group.rawDefineTemplate("/" + library.getPrefix() + "/" + opSpecGroup.getOperationIdentifier() + template.getName(), template.impl, null);
            }

         }

      }

   }

}

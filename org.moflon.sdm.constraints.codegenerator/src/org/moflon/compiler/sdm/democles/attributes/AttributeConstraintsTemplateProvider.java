package org.moflon.compiler.sdm.democles.attributes;

import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.CodeGeneratorProvider;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;

public class AttributeConstraintsTemplateProvider implements CodeGeneratorProvider<Chain<TemplateController>>
{

   @Override
   public Chain<TemplateController> getTemplateController(final GeneratorOperation operation, final Chain<TemplateController> tail)
   {

      if (operation.getType() instanceof ConstraintSpecification)
      {
         ConstraintSpecification cType = (ConstraintSpecification) operation.getType();

         String prefix = cType.getAttributeConstraintLibrary().getPrefix();
         String operationIdentifier = cType.getOperationSpecificationGroup().getOperationIdentifier();
         String adornmentString = operation.getPrecondition().toString();

         String fullyQualifiedTemplateName = "/" + prefix + "/" + operationIdentifier + "/" + operationIdentifier + adornmentString;
         return new Chain<TemplateController>(new TemplateController(fullyQualifiedTemplateName, operation), tail);
      }
      throw new RuntimeException("Invalid operation");
   }

   @Override
   public boolean isResponsibleFor(final GeneratorOperation operation)
   {
      return operation != null && operation.getType() instanceof ConstraintSpecification;
   }

}

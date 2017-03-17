package org.moflon.gt.mosl.codeadapter.linkvariablerules;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class MatchingUnboundLVTransformingRule extends LVTransformerRule
{

   @Override
   protected boolean isTransformable(LinkVariablePattern linkVariable)
   {
      return isCheckOnly(linkVariable.getOp());
   }

   @Override
   protected void transformLinkVariable(LinkVariablePattern linkVariable, ObjectVariableDefinition ov, PatternBody patternBody)
   {
      Reference reference = EMFTypeFactory.eINSTANCE.createReference();
      reference.setEModelElement(linkVariable.getType());
      patternBody.getConstraints().add(reference);

      ConstraintParameter source = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(source);
      source.setReference(this.getVariableMonad(ov.getName()).get());

      this.handleTarget(reference, linkVariable);

   }

}

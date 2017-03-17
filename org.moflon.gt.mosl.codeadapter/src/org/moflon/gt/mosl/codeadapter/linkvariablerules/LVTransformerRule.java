package org.moflon.gt.mosl.codeadapter.linkvariablerules;

import java.util.Optional;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.moflon.gt.mosl.codeadapter.abstractutils.IOperatorRule;
import org.moflon.gt.mosl.codeadapter.codeadapter.LinkVariableBuilder;
import org.moflon.gt.mosl.codeadapter.codeadapter.PatternBuilder;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public abstract class LVTransformerRule implements IOperatorRule
{

   private Function<String, Optional<Variable>> getVariableMonadFun;

   public LVTransformerRule()
   {
      LinkVariableBuilder.getInstance().addTransformer(this);
   }

   public void transforming(LinkVariablePattern linkVariable, ObjectVariableDefinition ov, PatternBody patternBody)
   {
      if (isTransformable(linkVariable))
      {
         getVariableMonadFun = varName -> {
            return patternBody.getHeader().getSymbolicParameters().stream().filter(var -> var.getName().compareTo(varName) == 0).findFirst();
         };
         transformLinkVariable(linkVariable, ov, patternBody);
      }
   }

   protected Optional<Variable> getVariableMonad(String varName)
   {
      return getVariableMonadFun.apply(varName);
   }

   protected void handleTarget(Reference reference, LinkVariablePattern linkVariable)
   {
      ConstraintParameter target = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(target);
      String targetName = linkVariable.getTarget().getName();

      Optional<Variable> targetMonad = this.getVariableMonad(targetName);

      if (targetMonad.isPresent())
      {
         target.setReference(targetMonad.get());
      } else
      {
         PatternBuilder.getInstance().addUnfinishedLinkVaraible(targetName, var -> {
            target.setReference(var);
         });
      }

   }

   protected abstract boolean isTransformable(LinkVariablePattern linkVariable);

   protected abstract void transformLinkVariable(LinkVariablePattern linkVariable, ObjectVariableDefinition ov, PatternBody patternBody);
}

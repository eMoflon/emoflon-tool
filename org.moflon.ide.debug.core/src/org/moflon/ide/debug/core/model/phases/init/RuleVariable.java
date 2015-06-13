package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import TGGLanguage.analysis.Rule;

public class RuleVariable extends MoflonDebugElement implements IVariable
{
   public static final String SOURCE_RULES = "Source rules";

   public static final String TARGET_RULES = "Target rules";

   public static enum Mode {
      SOURCE, SOURCES, TARGET, TARGETS, RULE
   };

   Mode mode;

   IValue value;

   Rule rule;

   public RuleVariable(IDebugTarget target, Mode mode, EList<Rule> rules)
   {
      super(target);
      this.mode = mode;
      value = new RuleValue(getDebugTarget(), mode, rules);
   }

   public RuleVariable(IDebugTarget target, Mode mode, Rule rule)
   {
      super(target);
      this.value = new RuleValue(target, mode);
      this.rule = rule;
      this.mode = mode;
   }

   @Override
   public void setValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub
   }

   @Override
   public void setValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean supportsValueModification()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IValue getValue() throws DebugException
   {
      return value;
   }

   @Override
   public String getName() throws DebugException
   {
      switch (mode)
      {
      case SOURCES:
         return SOURCE_RULES;
      case TARGETS:
         return TARGET_RULES;
      case SOURCE:
      case TARGET:
         return rule.getRuleName();
      default:
         return null;
      }
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null;
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      return false;
   }

}

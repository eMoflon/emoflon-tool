package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable.Mode;

import TGGLanguage.analysis.Rule;

public class RuleValue implements IValue
{

   private EList<Rule> rules;

   private Rule rule;

   private IVariable[] variables;

   private Mode mode;

   public RuleValue(IDebugTarget debugTarget, Mode mode, EList<Rule> rules)
   {
      this.rules = rules;
      this.mode = mode;
      this.variables = new IVariable[rules.size()];
      for (int i = 0; i < variables.length; i++)
      {
         switch (mode)
         {
         case SOURCES:
            this.variables[i] = new RuleVariable(debugTarget, Mode.SOURCE, rules.get(i));
            break;

         case TARGETS:
            this.variables[i] = new RuleVariable(debugTarget, Mode.TARGET, rules.get(i));
            break;
         default:
            break;
         }
      }
   }

   public RuleValue(IDebugTarget target, Mode mode)
   {
      this.mode = mode;
   }

   public RuleValue(IDebugTarget target, Mode mode, Rule rule)
   {
      this.mode = mode;
      this.rule = rule;
   }

   @Override
   public String getModelIdentifier()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IDebugTarget getDebugTarget()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ILaunch getLaunch()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      switch (mode)
      {
      case SOURCES:
      case TARGETS:
         return "Count: " + rules.size();
      case SOURCE:
      case TARGET:
         return "";
      case RULE:
         return rule.getRuleName();
      default:
         return null;
      }
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return variables != null && variables.length > 0;
   }

}

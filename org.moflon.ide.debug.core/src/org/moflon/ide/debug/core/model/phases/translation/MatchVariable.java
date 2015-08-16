package org.moflon.ide.debug.core.model.phases.translation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.debug.core.IJavaModifiers;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import org.moflon.tgg.debug.language.DebugMatch;

public class MatchVariable extends MoflonDebugElement implements IVariable
{

   private IValue value;

   private DebugMatch match;

   public MatchVariable(DebugMatch value)
   {
      super(null);
      this.match = value;
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
      if (value == null)
      {
         value = new MatchValue(getDebugTarget(), match);
      }
      return value;
   }

   @Override
   public String getName() throws DebugException
   {
      return "Input Match " + match.getRuleName();
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == IJavaVariable.class || adapter == IJavaModifiers.class)
      {
         return this;
      } else if (adapter == EObject.class)
      {
         return match;
      }
      return super.getAdapter(adapter);
   }
}

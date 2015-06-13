package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.debug.core.IJavaModifiers;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import DebugLanguage.AbstractPhase;
import DebugLanguage.ChangeMode;
import DebugLanguage.DebugCorrespondence;
import DebugLanguage.DebugEObjectProxy;
import DebugLanguage.DebugTripleMatch;
import TGGRuntime.CorrespondenceModel;
import TGGRuntime.TGGRuntimeFactory;
import TGGRuntime.TripleMatch;

public class TripleMatchVariable extends MoflonDebugElement implements IVariable
{
   private DebugTripleMatch triplematch;

   private IValue value;

   private DebugEObjectProxy src;

   private DebugEObjectProxy trg;

   private CorrespondenceModel adapterCorr;

   public TripleMatchVariable(IDebugTarget target, DebugTripleMatch tripleMatch)
   {
      super(target);
      this.triplematch = tripleMatch;

      src = ((AbstractPhase) tripleMatch.eContainer().eContainer()).getSourceProxy();
      trg = ((AbstractPhase) tripleMatch.eContainer().eContainer()).getTargetProxy();

      value = new TripleMatchValue(getDebugTarget(), tripleMatch);
   }

   @Override
   public void setValue(String expression) throws DebugException
   {
      System.out.println("setValue: " + expression);

   }

   @Override
   public void setValue(IValue value) throws DebugException
   {
      System.out.println("setValue: " + value);
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
      return "Match " + triplematch.getRuleName();
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return value.getReferenceTypeName();
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      return triplematch.getChangeMode() != ChangeMode.NONE;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      System.err.println(adapter.getCanonicalName());
      if (adapter == IJavaVariable.class || adapter == IJavaModifiers.class)
      {
         return this;
      } else if (adapter == ChangeMode.class)
      {
         return triplematch.getChangeMode();
      } else if (adapter == EObject.class || adapter == TripleMatch.class)
      {
         return triplematch;
      } else if (adapter == CorrespondenceModel.class)
      {
         if (adapterCorr == null)
         {
            adapterCorr = TGGRuntimeFactory.eINSTANCE.createCorrespondenceModel();
            adapterCorr.setSource(src);
            adapterCorr.setTarget(trg);

            triplematch.getCorrespondenceElements().forEach(e -> {
               if (e instanceof DebugCorrespondence)
               {
                  adapterCorr.getCorrespondences().add(e);
               }
            });
         }
         return adapterCorr;
      }
      return super.getAdapter(adapter);
   }
}

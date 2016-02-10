package org.moflon.ide.metamodelevolution.core.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Equivalence;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.compare.utils.IEqualityHelper;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.ide.metamodelevolution.core.MetamodelDelta;

public class EMFCompareMetamodelDelta implements Comparison, MetamodelDelta
{

   @Override
   public EClass eClass()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Resource eResource()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EObject eContainer()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EStructuralFeature eContainingFeature()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EReference eContainmentFeature()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<EObject> eContents()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public TreeIterator<EObject> eAllContents()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean eIsProxy()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public EList<EObject> eCrossReferences()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Object eGet(EStructuralFeature feature)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Object eGet(EStructuralFeature feature, boolean resolve)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void eSet(EStructuralFeature feature, Object newValue)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean eIsSet(EStructuralFeature feature)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void eUnset(EStructuralFeature feature)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Adapter> eAdapters()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean eDeliver()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void eSetDeliver(boolean deliver)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void eNotify(Notification notification)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public EList<MatchResource> getMatchedResources()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Match> getMatches()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Conflict> getConflicts()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Equivalence> getEquivalences()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Diff> getDifferences()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public EList<Diff> getDifferences(EObject element)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Match getMatch(EObject element)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IEqualityHelper getEqualityHelper()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean isThreeWay()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void setThreeWay(boolean value)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public Diagnostic getDiagnostic()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void setDiagnostic(Diagnostic value)
   {
      // TODO Auto-generated method stub

   }

}

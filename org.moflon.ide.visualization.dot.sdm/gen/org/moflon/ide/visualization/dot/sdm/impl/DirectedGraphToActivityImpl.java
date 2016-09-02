/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import SDMLanguage.activities.Activity;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.moflon.ide.visualization.dot.language.DirectedGraph;

import org.moflon.ide.visualization.dot.sdm.DirectedGraphToActivity;
import org.moflon.ide.visualization.dot.sdm.SdmPackage;

import org.moflon.tgg.runtime.impl.AbstractCorrespondenceImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Directed Graph To Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.DirectedGraphToActivityImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.DirectedGraphToActivityImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DirectedGraphToActivityImpl extends AbstractCorrespondenceImpl implements DirectedGraphToActivity
{
   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected DirectedGraph source;

   /**
    * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTarget()
    * @generated
    * @ordered
    */
   protected Activity target;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected DirectedGraphToActivityImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return SdmPackage.Literals.DIRECTED_GRAPH_TO_ACTIVITY;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public DirectedGraph getSource()
   {
      if (source != null && source.eIsProxy())
      {
         InternalEObject oldSource = (InternalEObject) source;
         source = (DirectedGraph) eResolveProxy(oldSource);
         if (source != oldSource)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE, oldSource, source));
         }
      }
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public DirectedGraph basicGetSource()
   {
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setSource(DirectedGraph newSource)
   {
      DirectedGraph oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Activity getTarget()
   {
      if (target != null && target.eIsProxy())
      {
         InternalEObject oldTarget = (InternalEObject) target;
         target = (Activity) eResolveProxy(oldTarget);
         if (target != oldTarget)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET, oldTarget, target));
         }
      }
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Activity basicGetTarget()
   {
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setTarget(Activity newTarget)
   {
      Activity oldTarget = target;
      target = newTarget;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET, oldTarget, target));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE:
         if (resolve)
            return getSource();
         return basicGetSource();
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET:
         if (resolve)
            return getTarget();
         return basicGetTarget();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE:
         setSource((DirectedGraph) newValue);
         return;
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET:
         setTarget((Activity) newValue);
         return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID)
   {
      switch (featureID)
      {
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE:
         setSource((DirectedGraph) null);
         return;
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET:
         setTarget((Activity) null);
         return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID)
   {
      switch (featureID)
      {
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__SOURCE:
         return source != null;
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY__TARGET:
         return target != null;
      }
      return super.eIsSet(featureID);
   }
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //DirectedGraphToActivityImpl

/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import MocaTree.Node;

import SDMLanguage.activities.ActivityEdge;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.moflon.ide.visualization.dot.sdm.NodeToActivityEdge;
import org.moflon.ide.visualization.dot.sdm.SdmPackage;

import org.moflon.tgg.runtime.impl.AbstractCorrespondenceImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node To Activity Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.NodeToActivityEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.NodeToActivityEdgeImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeToActivityEdgeImpl extends AbstractCorrespondenceImpl implements NodeToActivityEdge
{
   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected Node source;

   /**
    * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTarget()
    * @generated
    * @ordered
    */
   protected ActivityEdge target;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected NodeToActivityEdgeImpl()
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
      return SdmPackage.Literals.NODE_TO_ACTIVITY_EDGE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node getSource()
   {
      if (source != null && source.eIsProxy())
      {
         InternalEObject oldSource = (InternalEObject) source;
         source = (Node) eResolveProxy(oldSource);
         if (source != oldSource)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE, oldSource, source));
         }
      }
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node basicGetSource()
   {
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setSource(Node newSource)
   {
      Node oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ActivityEdge getTarget()
   {
      if (target != null && target.eIsProxy())
      {
         InternalEObject oldTarget = (InternalEObject) target;
         target = (ActivityEdge) eResolveProxy(oldTarget);
         if (target != oldTarget)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET, oldTarget, target));
         }
      }
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ActivityEdge basicGetTarget()
   {
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setTarget(ActivityEdge newTarget)
   {
      ActivityEdge oldTarget = target;
      target = newTarget;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET, oldTarget, target));
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
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE:
         if (resolve)
            return getSource();
         return basicGetSource();
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET:
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
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE:
         setSource((Node) newValue);
         return;
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET:
         setTarget((ActivityEdge) newValue);
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
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE:
         setSource((Node) null);
         return;
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET:
         setTarget((ActivityEdge) null);
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
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__SOURCE:
         return source != null;
      case SdmPackage.NODE_TO_ACTIVITY_EDGE__TARGET:
         return target != null;
      }
      return super.eIsSet(featureID);
   }
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //NodeToActivityEdgeImpl

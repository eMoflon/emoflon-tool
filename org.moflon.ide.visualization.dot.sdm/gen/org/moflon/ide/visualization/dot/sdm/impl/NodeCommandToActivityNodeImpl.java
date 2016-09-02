/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import SDMLanguage.activities.ActivityNode;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.moflon.ide.visualization.dot.language.NodeCommand;

import org.moflon.ide.visualization.dot.sdm.NodeCommandToActivityNode;
import org.moflon.ide.visualization.dot.sdm.SdmPackage;

import org.moflon.tgg.runtime.impl.AbstractCorrespondenceImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Command To Activity Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.NodeCommandToActivityNodeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.NodeCommandToActivityNodeImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeCommandToActivityNodeImpl extends AbstractCorrespondenceImpl implements NodeCommandToActivityNode
{
   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected NodeCommand source;

   /**
    * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTarget()
    * @generated
    * @ordered
    */
   protected ActivityNode target;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected NodeCommandToActivityNodeImpl()
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
      return SdmPackage.Literals.NODE_COMMAND_TO_ACTIVITY_NODE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeCommand getSource()
   {
      if (source != null && source.eIsProxy())
      {
         InternalEObject oldSource = (InternalEObject) source;
         source = (NodeCommand) eResolveProxy(oldSource);
         if (source != oldSource)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE, oldSource, source));
         }
      }
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeCommand basicGetSource()
   {
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setSource(NodeCommand newSource)
   {
      NodeCommand oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ActivityNode getTarget()
   {
      if (target != null && target.eIsProxy())
      {
         InternalEObject oldTarget = (InternalEObject) target;
         target = (ActivityNode) eResolveProxy(oldTarget);
         if (target != oldTarget)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET, oldTarget, target));
         }
      }
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ActivityNode basicGetTarget()
   {
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setTarget(ActivityNode newTarget)
   {
      ActivityNode oldTarget = target;
      target = newTarget;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET, oldTarget, target));
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
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE:
         if (resolve)
            return getSource();
         return basicGetSource();
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET:
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
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE:
         setSource((NodeCommand) newValue);
         return;
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET:
         setTarget((ActivityNode) newValue);
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
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE:
         setSource((NodeCommand) null);
         return;
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET:
         setTarget((ActivityNode) null);
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
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE:
         return source != null;
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE__TARGET:
         return target != null;
      }
      return super.eIsSet(featureID);
   }
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //NodeCommandToActivityNodeImpl

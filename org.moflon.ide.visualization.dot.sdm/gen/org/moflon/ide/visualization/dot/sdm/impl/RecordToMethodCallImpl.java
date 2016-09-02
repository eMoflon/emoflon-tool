/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import SDMLanguage.calls.callExpressions.MethodCallExpression;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.moflon.ide.visualization.dot.language.RecordEntry;

import org.moflon.ide.visualization.dot.sdm.RecordToMethodCall;
import org.moflon.ide.visualization.dot.sdm.SdmPackage;

import org.moflon.tgg.runtime.impl.AbstractCorrespondenceImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Record To Method Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.RecordToMethodCallImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.sdm.impl.RecordToMethodCallImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RecordToMethodCallImpl extends AbstractCorrespondenceImpl implements RecordToMethodCall
{
   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected RecordEntry source;

   /**
    * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTarget()
    * @generated
    * @ordered
    */
   protected MethodCallExpression target;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RecordToMethodCallImpl()
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
      return SdmPackage.Literals.RECORD_TO_METHOD_CALL;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public RecordEntry getSource()
   {
      if (source != null && source.eIsProxy())
      {
         InternalEObject oldSource = (InternalEObject) source;
         source = (RecordEntry) eResolveProxy(oldSource);
         if (source != oldSource)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.RECORD_TO_METHOD_CALL__SOURCE, oldSource, source));
         }
      }
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public RecordEntry basicGetSource()
   {
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setSource(RecordEntry newSource)
   {
      RecordEntry oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.RECORD_TO_METHOD_CALL__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MethodCallExpression getTarget()
   {
      if (target != null && target.eIsProxy())
      {
         InternalEObject oldTarget = (InternalEObject) target;
         target = (MethodCallExpression) eResolveProxy(oldTarget);
         if (target != oldTarget)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SdmPackage.RECORD_TO_METHOD_CALL__TARGET, oldTarget, target));
         }
      }
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MethodCallExpression basicGetTarget()
   {
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setTarget(MethodCallExpression newTarget)
   {
      MethodCallExpression oldTarget = target;
      target = newTarget;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SdmPackage.RECORD_TO_METHOD_CALL__TARGET, oldTarget, target));
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
      case SdmPackage.RECORD_TO_METHOD_CALL__SOURCE:
         if (resolve)
            return getSource();
         return basicGetSource();
      case SdmPackage.RECORD_TO_METHOD_CALL__TARGET:
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
      case SdmPackage.RECORD_TO_METHOD_CALL__SOURCE:
         setSource((RecordEntry) newValue);
         return;
      case SdmPackage.RECORD_TO_METHOD_CALL__TARGET:
         setTarget((MethodCallExpression) newValue);
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
      case SdmPackage.RECORD_TO_METHOD_CALL__SOURCE:
         setSource((RecordEntry) null);
         return;
      case SdmPackage.RECORD_TO_METHOD_CALL__TARGET:
         setTarget((MethodCallExpression) null);
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
      case SdmPackage.RECORD_TO_METHOD_CALL__SOURCE:
         return source != null;
      case SdmPackage.RECORD_TO_METHOD_CALL__TARGET:
         return target != null;
      }
      return super.eIsSet(featureID);
   }
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //RecordToMethodCallImpl

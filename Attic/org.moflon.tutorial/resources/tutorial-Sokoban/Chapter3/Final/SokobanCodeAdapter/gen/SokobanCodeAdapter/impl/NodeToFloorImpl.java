/**
 */
package SokobanCodeAdapter.impl;

import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import SokobanCodeAdapter.NodeToBoard;
import SokobanCodeAdapter.NodeToFloor;
import SokobanCodeAdapter.SokobanCodeAdapterFactory;
import SokobanCodeAdapter.SokobanCodeAdapterPackage;

import SokobanLanguage.Admin;
import SokobanLanguage.Board;
import SokobanLanguage.Floor;
import SokobanLanguage.Goal;
import SokobanLanguage.Server;
import SokobanLanguage.SokobanLanguageFactory;
import SokobanLanguage.Wall;

import TGGLanguage.csp.*;

import TGGRuntime.Edge;
import TGGRuntime.IsApplicableMatch;
import TGGRuntime.IsApplicableRuleResult;
import TGGRuntime.IsAppropriateRuleResult;
import TGGRuntime.Match;
import TGGRuntime.PerformRuleResult;
import TGGRuntime.TGGRuntimeFactory;

import csp.constraints.*;

import de.upb.tools.sdm.*;

import java.util.*;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node To Floor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link SokobanCodeAdapter.impl.NodeToFloorImpl#getSource <em>Source</em>}</li>
 *   <li>{@link SokobanCodeAdapter.impl.NodeToFloorImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeToFloorImpl extends EObjectImpl implements NodeToFloor {
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
	protected Floor target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeToFloorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SokobanCodeAdapterPackage.Literals.NODE_TO_FLOOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (Node) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE,
							oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Node newSource) {
		Node oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE, oldSource,
					source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Floor getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (Floor) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET,
							oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Floor basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Floor newTarget) {
		Floor oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET, oldTarget,
					target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET:
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE:
			setSource((Node) newValue);
			return;
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET:
			setTarget((Floor) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE:
			setSource((Node) null);
			return;
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET:
			setTarget((Floor) null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__SOURCE:
			return source != null;
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}

} //NodeToFloorImpl

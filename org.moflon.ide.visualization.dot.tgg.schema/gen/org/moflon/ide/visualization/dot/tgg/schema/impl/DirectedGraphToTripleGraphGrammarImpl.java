/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.moflon.ide.visualization.dot.language.DirectedGraph;

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;
import org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage;

import org.moflon.tgg.language.TripleGraphGrammar;

import org.moflon.tgg.runtime.impl.AbstractCorrespondenceImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Directed Graph To Triple Graph Grammar</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DirectedGraphToTripleGraphGrammarImpl extends AbstractCorrespondenceImpl
		implements DirectedGraphToTripleGraphGrammar {
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
	protected TripleGraphGrammar target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DirectedGraphToTripleGraphGrammarImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectedGraph getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (DirectedGraph) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectedGraph basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(DirectedGraph newSource) {
		DirectedGraph oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TripleGraphGrammar getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (TripleGraphGrammar) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TripleGraphGrammar basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(TripleGraphGrammar newTarget) {
		TripleGraphGrammar oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET:
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
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE:
			setSource((DirectedGraph) newValue);
			return;
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET:
			setTarget((TripleGraphGrammar) newValue);
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
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE:
			setSource((DirectedGraph) null);
			return;
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET:
			setTarget((TripleGraphGrammar) null);
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
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE:
			return source != null;
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //DirectedGraphToTripleGraphGrammarImpl

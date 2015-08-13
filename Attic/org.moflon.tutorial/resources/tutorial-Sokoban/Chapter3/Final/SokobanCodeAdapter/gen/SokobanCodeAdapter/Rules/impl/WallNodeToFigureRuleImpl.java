/**
 */
package SokobanCodeAdapter.Rules.impl;

import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import SokobanCodeAdapter.NodeToBoard;
import SokobanCodeAdapter.NodeToFloor;

import SokobanCodeAdapter.Rules.RulesPackage;
import SokobanCodeAdapter.Rules.WallNodeToFigureRule;

import SokobanCodeAdapter.SokobanCodeAdapterFactory;

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

import TGGRuntime.impl.AbstractRuleImpl;

import csp.constraints.*;

import de.upb.tools.sdm.*;

import java.lang.reflect.InvocationTargetException;

import java.util.*;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wall Node To Figure Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class WallNodeToFigureRuleImpl extends AbstractRuleImpl implements
		WallNodeToFigureRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WallNodeToFigureRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.WALL_NODE_TO_FIGURE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		Node figureNode = null;
		Floor floor = null;
		Node floorNode = null;
		NodeToFloor nodeToFloor = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		Wall wall = null;
		PerformRuleResult ruleresult = null;
		Edge floorNode__children__figureNode = null;
		Edge floor__figure__wall = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("figureNode"));

			// ensure correct type and really bound of object figureNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			figureNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("floor"));

			// ensure correct type and really bound of object floor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			floor = (Floor) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("floorNode"));

			// ensure correct type and really bound of object floorNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			floorNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("nodeToFloor"));

			// ensure correct type and really bound of object nodeToFloor
			JavaSDM.ensure(_TmpObject instanceof NodeToFloor);
			nodeToFloor = (NodeToFloor) _TmpObject;
			// check object isApplicableMatch is really bound
			JavaSDM.ensure(isApplicableMatch != null);
			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// iterate to-many link attributeInfo from isApplicableMatch to csp
			fujaba__Success = false;

			fujaba__IterIsApplicableMatchToCsp = isApplicableMatch
					.getAttributeInfo().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterIsApplicableMatchToCsp.hasNext()) {
				try {
					_TmpObject = fujaba__IterIsApplicableMatchToCsp.next();

					// ensure correct type and really bound of object csp
					JavaSDM.ensure(_TmpObject instanceof CSP);
					csp = (CSP) _TmpObject;

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			if (!fujaba__Success) {
				fujaba__Success = true;
				csp = null;
			}
			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(), "#") == 0);

			// create object wall
			wall = SokobanLanguageFactory.eINSTANCE.createWall();

			// create link
			wall.setFloor(floor);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					wall, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					figureNode, "translatedElements");
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'bookkeeping for edges'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object floorNode is really bound
			JavaSDM.ensure(floorNode != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// check isomorphic binding between objects floor and figureNode 
			JavaSDM.ensure(!floor.equals(figureNode));

			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// check isomorphic binding between objects nodeToFloor and figureNode 
			JavaSDM.ensure(!nodeToFloor.equals(figureNode));

			// check isomorphic binding between objects wall and figureNode 
			JavaSDM.ensure(!wall.equals(figureNode));

			// check isomorphic binding between objects floorNode and floor 
			JavaSDM.ensure(!floorNode.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floor 
			JavaSDM.ensure(!nodeToFloor.equals(floor));

			// check isomorphic binding between objects wall and floor 
			JavaSDM.ensure(!wall.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floorNode 
			JavaSDM.ensure(!nodeToFloor.equals(floorNode));

			// check isomorphic binding between objects wall and floorNode 
			JavaSDM.ensure(!wall.equals(floorNode));

			// check isomorphic binding between objects wall and nodeToFloor 
			JavaSDM.ensure(!wall.equals(nodeToFloor));

			// create object floorNode__children__figureNode
			floorNode__children__figureNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object floor__figure__wall
			floor__figure__wall = TGGRuntimeFactory.eINSTANCE.createEdge();

			// assign attribute floorNode__children__figureNode
			floorNode__children__figureNode.setName("children");
			// assign attribute floor__figure__wall
			floor__figure__wall.setName("figure");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floorNode__children__figureNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor__figure__wall, "createdEdges");

			// create link
			floorNode__children__figureNode.setSrc(floorNode);

			// create link
			floorNode__children__figureNode.setTrg(figureNode);

			// create link
			floor__figure__wall.setSrc(floor);

			// create link
			floor__figure__wall.setTrg(wall);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// statement node 'perform postprocessing'
		// No post processing method found
		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, Node floorNode,
			Node figureNode) {
		boolean fujaba__Success = false;
		Edge floorNode__children__figureNode = null;

		// statement node 'Solve CSP'
		//Statement
		// statement node 'Check CSP'
		fujaba__Success = true;
		if (fujaba__Success) {
			// story node 'collect elements to be translated'
			try {
				fujaba__Success = false;

				// check object figureNode is really bound
				JavaSDM.ensure(figureNode != null);
				// check object floorNode is really bound
				JavaSDM.ensure(floorNode != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check isomorphic binding between objects floorNode and figureNode 
				JavaSDM.ensure(!floorNode.equals(figureNode));

				// create object floorNode__children__figureNode
				floorNode__children__figureNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute floorNode__children__figureNode
				floorNode__children__figureNode.setName("children");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floorNode__children__figureNode, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						figureNode, "toBeTranslatedElements");

				// create link
				floorNode__children__figureNode.setTrg(figureNode);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(
						floorNode__children__figureNode, floorNode, "src");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object floorNode is really bound
				JavaSDM.ensure(floorNode != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floorNode, "contextNodes");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("floorNode", floorNode);
			match.registerObject("figureNode", figureNode);

			return true;

		} else {
			return false;

		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass eClass = null;
		Iterator fujaba__IterEClassToPerformOperation = null;
		EOperation performOperation = null;
		IsApplicableRuleResult ruleresult = null;
		Node figureNode = null;
		Node floorNode = null;
		IsApplicableMatch isApplicableMatch = null;
		Floor floor = null;
		Iterator fujaba__IterFloorNodeToNodeToFloor = null;
		NodeToFloor nodeToFloor = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from eClass to performOperation
			fujaba__Success = false;

			fujaba__IterEClassToPerformOperation = eClass.getEOperations()
					.iterator();

			while (!(fujaba__Success)
					&& fujaba__IterEClassToPerformOperation.hasNext()) {
				try {
					performOperation = (EOperation) fujaba__IterEClassToPerformOperation
							.next();

					// check object performOperation is really bound
					JavaSDM.ensure(performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							performOperation.getName(), "perform_FWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsApplicableRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);
			// assign attribute ruleresult
			ruleresult.setRule("WallNodeToFigureRule");

			// create link
			ruleresult.setPerformOperation(performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'core match'
		try {
			fujaba__Success = false;

			_TmpObject = (match.getObject("figureNode"));

			// ensure correct type and really bound of object figureNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			figureNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("floorNode"));

			// ensure correct type and really bound of object floorNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			floorNode = (Node) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(), "#") == 0);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// check object floorNode is really bound
			JavaSDM.ensure(floorNode != null);
			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// check link children from figureNode to floorNode
			JavaSDM.ensure(floorNode.equals(figureNode.getParentNode()));

			// iterate to-many link source from floorNode to nodeToFloor
			fujaba__Success = false;

			fujaba__IterFloorNodeToNodeToFloor = new ArrayList(
					org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReference(
							floorNode, NodeToFloor.class, "source")).iterator();

			while (fujaba__IterFloorNodeToNodeToFloor.hasNext()) {
				try {
					nodeToFloor = (NodeToFloor) fujaba__IterFloorNodeToNodeToFloor
							.next();

					// check object nodeToFloor is really bound
					JavaSDM.ensure(nodeToFloor != null);
					// bind object
					floor = nodeToFloor.getTarget();

					// check object floor is really bound
					JavaSDM.ensure(floor != null);

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(),
							"#") == 0);

					// create object isApplicableMatch
					isApplicableMatch = TGGRuntimeFactory.eINSTANCE
							.createIsApplicableMatch();

					// statement node 'solve CSP'

					// Snapshot pattern match on which CSP is solved
					isApplicableMatch.registerObject("floorNode", floorNode);
					isApplicableMatch.registerObject("figureNode", figureNode);
					isApplicableMatch
							.registerObject("nodeToFloor", nodeToFloor);
					isApplicableMatch.registerObject("floor", floor);

					// statement node 'check CSP'
					fujaba__Success = true;
					if (fujaba__Success) {
						// story node 'add match to rule result'
						try {
							fujaba__Success = false;

							// check object isApplicableMatch is really bound
							JavaSDM.ensure(isApplicableMatch != null);
							// check object ruleresult is really bound
							JavaSDM.ensure(ruleresult != null);
							// assign attribute ruleresult
							ruleresult.setSuccess(true);

							// create link
							ruleresult.getIsApplicableMatch().add(
									isApplicableMatch);

							fujaba__Success = true;
						} catch (JavaSDMException fujaba__InternalException) {
							fujaba__Success = false;
						}

					} else {

					}

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		Floor floor = null;
		Node floorNode = null;
		NodeToFloor nodeToFloor = null;
		Wall wall = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		Node figureNode = null;
		PerformRuleResult ruleresult = null;
		Edge floor__figure__wall = null;
		Edge floorNode__children__figureNode = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("floor"));

			// ensure correct type and really bound of object floor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			floor = (Floor) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("floorNode"));

			// ensure correct type and really bound of object floorNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			floorNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("nodeToFloor"));

			// ensure correct type and really bound of object nodeToFloor
			JavaSDM.ensure(_TmpObject instanceof NodeToFloor);
			nodeToFloor = (NodeToFloor) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("wall"));

			// ensure correct type and really bound of object wall
			JavaSDM.ensure(_TmpObject instanceof Wall);
			wall = (Wall) _TmpObject;
			// check object isApplicableMatch is really bound
			JavaSDM.ensure(isApplicableMatch != null);
			// iterate to-many link attributeInfo from isApplicableMatch to csp
			fujaba__Success = false;

			fujaba__IterIsApplicableMatchToCsp = isApplicableMatch
					.getAttributeInfo().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterIsApplicableMatchToCsp.hasNext()) {
				try {
					_TmpObject = fujaba__IterIsApplicableMatchToCsp.next();

					// ensure correct type and really bound of object csp
					JavaSDM.ensure(_TmpObject instanceof CSP);
					csp = (CSP) _TmpObject;

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			if (!fujaba__Success) {
				fujaba__Success = true;
				csp = null;
			}
			// create object figureNode
			figureNode = MocaTreeFactory.eINSTANCE.createNode();

			// assign attribute figureNode
			figureNode.setName("#");

			// create link
			figureNode.setParentNode(floorNode);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					figureNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					wall, "translatedElements");
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'bookkeeping for edges'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object floorNode is really bound
			JavaSDM.ensure(floorNode != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// check isomorphic binding between objects floor and figureNode 
			JavaSDM.ensure(!floor.equals(figureNode));

			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// check isomorphic binding between objects nodeToFloor and figureNode 
			JavaSDM.ensure(!nodeToFloor.equals(figureNode));

			// check isomorphic binding between objects wall and figureNode 
			JavaSDM.ensure(!wall.equals(figureNode));

			// check isomorphic binding between objects floorNode and floor 
			JavaSDM.ensure(!floorNode.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floor 
			JavaSDM.ensure(!nodeToFloor.equals(floor));

			// check isomorphic binding between objects wall and floor 
			JavaSDM.ensure(!wall.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floorNode 
			JavaSDM.ensure(!nodeToFloor.equals(floorNode));

			// check isomorphic binding between objects wall and floorNode 
			JavaSDM.ensure(!wall.equals(floorNode));

			// check isomorphic binding between objects wall and nodeToFloor 
			JavaSDM.ensure(!wall.equals(nodeToFloor));

			// create object floor__figure__wall
			floor__figure__wall = TGGRuntimeFactory.eINSTANCE.createEdge();

			// create object floorNode__children__figureNode
			floorNode__children__figureNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// assign attribute floorNode__children__figureNode
			floorNode__children__figureNode.setName("children");
			// assign attribute floor__figure__wall
			floor__figure__wall.setName("figure");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor__figure__wall, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floorNode__children__figureNode, "createdEdges");

			// create link
			floorNode__children__figureNode.setSrc(floorNode);

			// create link
			floorNode__children__figureNode.setTrg(figureNode);

			// create link
			floor__figure__wall.setSrc(floor);

			// create link
			floor__figure__wall.setTrg(wall);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// statement node 'perform postprocessing'
		// No post processing method found
		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, Floor floor, Wall wall) {
		boolean fujaba__Success = false;
		Edge floor__figure__wall = null;

		// statement node 'Solve CSP'
		//Statement
		// statement node 'Check CSP'
		fujaba__Success = true;
		if (fujaba__Success) {
			// story node 'collect elements to be translated'
			try {
				fujaba__Success = false;

				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object wall is really bound
				JavaSDM.ensure(wall != null);
				// create object floor__figure__wall
				floor__figure__wall = TGGRuntimeFactory.eINSTANCE.createEdge();

				// assign attribute floor__figure__wall
				floor__figure__wall.setName("figure");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floor__figure__wall, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						wall, "toBeTranslatedElements");

				// create link
				floor__figure__wall.setTrg(wall);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(
						floor__figure__wall, floor, "src");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floor, "contextNodes");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("floor", floor);
			match.registerObject("wall", wall);

			return true;

		} else {
			return false;

		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass eClass = null;
		Iterator fujaba__IterEClassToPerformOperation = null;
		EOperation performOperation = null;
		IsApplicableRuleResult ruleresult = null;
		Floor floor = null;
		Wall wall = null;
		IsApplicableMatch isApplicableMatch = null;
		Node floorNode = null;
		Iterator fujaba__IterFloorToNodeToFloor = null;
		NodeToFloor nodeToFloor = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from eClass to performOperation
			fujaba__Success = false;

			fujaba__IterEClassToPerformOperation = eClass.getEOperations()
					.iterator();

			while (!(fujaba__Success)
					&& fujaba__IterEClassToPerformOperation.hasNext()) {
				try {
					performOperation = (EOperation) fujaba__IterEClassToPerformOperation
							.next();

					// check object performOperation is really bound
					JavaSDM.ensure(performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							performOperation.getName(), "perform_BWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsApplicableRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);
			// assign attribute ruleresult
			ruleresult.setRule("WallNodeToFigureRule");

			// create link
			ruleresult.setPerformOperation(performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'core match'
		try {
			fujaba__Success = false;

			_TmpObject = (match.getObject("floor"));

			// ensure correct type and really bound of object floor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			floor = (Floor) _TmpObject;
			_TmpObject = (match.getObject("wall"));

			// ensure correct type and really bound of object wall
			JavaSDM.ensure(_TmpObject instanceof Wall);
			wall = (Wall) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// check link figure from floor to wall
			JavaSDM.ensure(wall.equals(floor.getFigure()));

			// iterate to-many link target from floor to nodeToFloor
			fujaba__Success = false;

			fujaba__IterFloorToNodeToFloor = new ArrayList(
					org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReference(floor,
							NodeToFloor.class, "target")).iterator();

			while (fujaba__IterFloorToNodeToFloor.hasNext()) {
				try {
					nodeToFloor = (NodeToFloor) fujaba__IterFloorToNodeToFloor
							.next();

					// check object nodeToFloor is really bound
					JavaSDM.ensure(nodeToFloor != null);
					// bind object
					floorNode = nodeToFloor.getSource();

					// check object floorNode is really bound
					JavaSDM.ensure(floorNode != null);

					// create object isApplicableMatch
					isApplicableMatch = TGGRuntimeFactory.eINSTANCE
							.createIsApplicableMatch();

					// statement node 'solve CSP'

					// Snapshot pattern match on which CSP is solved
					isApplicableMatch.registerObject("floorNode", floorNode);
					isApplicableMatch
							.registerObject("nodeToFloor", nodeToFloor);
					isApplicableMatch.registerObject("floor", floor);
					isApplicableMatch.registerObject("wall", wall);

					// statement node 'check CSP'
					fujaba__Success = true;
					if (fujaba__Success) {
						// story node 'add match to rule result'
						try {
							fujaba__Success = false;

							// check object isApplicableMatch is really bound
							JavaSDM.ensure(isApplicableMatch != null);
							// check object ruleresult is really bound
							JavaSDM.ensure(ruleresult != null);
							// assign attribute ruleresult
							ruleresult.setSuccess(true);

							// create link
							ruleresult.getIsApplicableMatch().add(
									isApplicableMatch);

							fujaba__Success = true;
						} catch (JavaSDMException fujaba__InternalException) {
							fujaba__Success = false;
						}

					} else {

					}

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsAppropriateRuleResult isAppropriate_FWD_Node_24(Node floorNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterFloorNodeToFigureNode = null;
		Node figureNode = null;
		Iterator fujaba__IterRuleresultToMatch = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object __eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			__eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from __eClass to __performOperation
			fujaba__Success = false;

			fujaba__Iter__eClassTo__performOperation = __eClass
					.getEOperations().iterator();

			while (!(fujaba__Success)
					&& fujaba__Iter__eClassTo__performOperation.hasNext()) {
				try {
					__performOperation = (EOperation) fujaba__Iter__eClassTo__performOperation
							.next();

					// check object __performOperation is really bound
					JavaSDM.ensure(__performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							__performOperation.getName(), "isApplicable_FWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsAppropriateRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);

			// create link
			ruleresult.setPerformOperation(__performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'test core match kernel'
		try {
			fujaba__Success = false;

			// check object floorNode is really bound
			JavaSDM.ensure(floorNode != null);
			// iterate to-many link children from floorNode to figureNode
			fujaba__Success = false;

			fujaba__IterFloorNodeToFigureNode = new ArrayList(
					floorNode.getChildren("#")).iterator();

			while (fujaba__IterFloorNodeToFigureNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterFloorNodeToFigureNode.next();

					// ensure correct type and really bound of object figureNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					figureNode = (Node) _TmpObject;
					// check isomorphic binding between objects floorNode and figureNode 
					JavaSDM.ensure(!floorNode.equals(figureNode));

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(),
							"#") == 0);

					// story node 'test core match'
					try {
						fujaba__Success = false;

						// check object figureNode is really bound
						JavaSDM.ensure(figureNode != null);
						// check object floorNode is really bound
						JavaSDM.ensure(floorNode != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);
						// check isomorphic binding between objects floorNode and figureNode 
						JavaSDM.ensure(!floorNode.equals(figureNode));

						// check link children from figureNode to floorNode
						JavaSDM.ensure(floorNode.equals(figureNode
								.getParentNode()));

						// attribute condition
						JavaSDM.ensure(JavaSDM.stringCompare(
								figureNode.getName(), "#") == 0);

						// create object match
						match = TGGRuntimeFactory.eINSTANCE.createMatch();

						// statement node 'bookkeeping with generic isAppropriate method'
						fujaba__Success = this.isAppropriate_FWD(match,
								floorNode, figureNode);
						if (fujaba__Success) {
							// story node 'Add match to rule result'
							try {
								fujaba__Success = false;

								// check object match is really bound
								JavaSDM.ensure(match != null);
								// check object ruleresult is really bound
								JavaSDM.ensure(ruleresult != null);

								// create link
								match.setIsAppropriateRuleResult(ruleresult);

								fujaba__Success = true;
							} catch (JavaSDMException fujaba__InternalException) {
								fujaba__Success = false;
							}

						} else {

						}
						fujaba__Success = true;
					} catch (JavaSDMException fujaba__InternalException) {
						fujaba__Success = false;
					}

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'set success'
		try {
			fujaba__Success = false;

			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// iterate to-many link matches from ruleresult to match
			fujaba__Success = false;

			fujaba__IterRuleresultToMatch = ruleresult.getMatches().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterRuleresultToMatch.hasNext()) {
				try {
					match = (Match) fujaba__IterRuleresultToMatch.next();

					// check object match is really bound
					JavaSDM.ensure(match != null);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// assign attribute ruleresult
			ruleresult.setSuccess(true);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsAppropriateRuleResult isAppropriate_FWD_Node_25(Node figureNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Node floorNode = null;
		Iterator fujaba__IterRuleresultToMatch = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object __eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			__eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from __eClass to __performOperation
			fujaba__Success = false;

			fujaba__Iter__eClassTo__performOperation = __eClass
					.getEOperations().iterator();

			while (!(fujaba__Success)
					&& fujaba__Iter__eClassTo__performOperation.hasNext()) {
				try {
					__performOperation = (EOperation) fujaba__Iter__eClassTo__performOperation
							.next();

					// check object __performOperation is really bound
					JavaSDM.ensure(__performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							__performOperation.getName(), "isApplicable_FWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsAppropriateRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);

			// create link
			ruleresult.setPerformOperation(__performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'test core match kernel'
		try {
			fujaba__Success = false;

			// check object figureNode is really bound
			JavaSDM.ensure(figureNode != null);
			// bind object
			floorNode = figureNode.getParentNode();

			// check object floorNode is really bound
			JavaSDM.ensure(floorNode != null);

			// check isomorphic binding between objects floorNode and figureNode 
			JavaSDM.ensure(!floorNode.equals(figureNode));

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(), "#") == 0);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object figureNode is really bound
				JavaSDM.ensure(figureNode != null);
				// check object floorNode is really bound
				JavaSDM.ensure(floorNode != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check isomorphic binding between objects floorNode and figureNode 
				JavaSDM.ensure(!floorNode.equals(figureNode));

				// check link children from figureNode to floorNode
				JavaSDM.ensure(floorNode.equals(figureNode.getParentNode()));

				// attribute condition
				JavaSDM.ensure(JavaSDM.stringCompare(figureNode.getName(), "#") == 0);

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_FWD(match, floorNode,
						figureNode);
				if (fujaba__Success) {
					// story node 'Add match to rule result'
					try {
						fujaba__Success = false;

						// check object match is really bound
						JavaSDM.ensure(match != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);

						// create link
						match.setIsAppropriateRuleResult(ruleresult);

						fujaba__Success = true;
					} catch (JavaSDMException fujaba__InternalException) {
						fujaba__Success = false;
					}

				} else {

				}
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'set success'
		try {
			fujaba__Success = false;

			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// iterate to-many link matches from ruleresult to match
			fujaba__Success = false;

			fujaba__IterRuleresultToMatch = ruleresult.getMatches().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterRuleresultToMatch.hasNext()) {
				try {
					match = (Match) fujaba__IterRuleresultToMatch.next();

					// check object match is really bound
					JavaSDM.ensure(match != null);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// assign attribute ruleresult
			ruleresult.setSuccess(true);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsAppropriateRuleResult isAppropriate_BWD_Floor_11(Floor floor) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Wall wall = null;
		Iterator fujaba__IterRuleresultToMatch = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object __eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			__eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from __eClass to __performOperation
			fujaba__Success = false;

			fujaba__Iter__eClassTo__performOperation = __eClass
					.getEOperations().iterator();

			while (!(fujaba__Success)
					&& fujaba__Iter__eClassTo__performOperation.hasNext()) {
				try {
					__performOperation = (EOperation) fujaba__Iter__eClassTo__performOperation
							.next();

					// check object __performOperation is really bound
					JavaSDM.ensure(__performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							__performOperation.getName(), "isApplicable_BWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsAppropriateRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);

			// create link
			ruleresult.setPerformOperation(__performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'test core match kernel'
		try {
			fujaba__Success = false;

			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// bind object
			_TmpObject = floor.getFigure();

			// ensure correct type and really bound of object wall
			JavaSDM.ensure(_TmpObject instanceof Wall);
			wall = (Wall) _TmpObject;

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check object wall is really bound
				JavaSDM.ensure(wall != null);
				// check link figure from floor to wall
				JavaSDM.ensure(wall.equals(floor.getFigure()));

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_BWD(match, floor, wall);
				if (fujaba__Success) {
					// story node 'Add match to rule result'
					try {
						fujaba__Success = false;

						// check object match is really bound
						JavaSDM.ensure(match != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);

						// create link
						match.setIsAppropriateRuleResult(ruleresult);

						fujaba__Success = true;
					} catch (JavaSDMException fujaba__InternalException) {
						fujaba__Success = false;
					}

				} else {

				}
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'set success'
		try {
			fujaba__Success = false;

			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// iterate to-many link matches from ruleresult to match
			fujaba__Success = false;

			fujaba__IterRuleresultToMatch = ruleresult.getMatches().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterRuleresultToMatch.hasNext()) {
				try {
					match = (Match) fujaba__IterRuleresultToMatch.next();

					// check object match is really bound
					JavaSDM.ensure(match != null);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// assign attribute ruleresult
			ruleresult.setSuccess(true);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsAppropriateRuleResult isAppropriate_BWD_Wall_0(Wall wall) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Floor floor = null;
		Iterator fujaba__IterRuleresultToMatch = null;

		// story node 'prepare return value'
		try {
			fujaba__Success = false;

			_TmpObject = (this.eClass());

			// ensure correct type and really bound of object __eClass
			JavaSDM.ensure(_TmpObject instanceof EClass);
			__eClass = (EClass) _TmpObject;
			// iterate to-many link eOperations from __eClass to __performOperation
			fujaba__Success = false;

			fujaba__Iter__eClassTo__performOperation = __eClass
					.getEOperations().iterator();

			while (!(fujaba__Success)
					&& fujaba__Iter__eClassTo__performOperation.hasNext()) {
				try {
					__performOperation = (EOperation) fujaba__Iter__eClassTo__performOperation
							.next();

					// check object __performOperation is really bound
					JavaSDM.ensure(__performOperation != null);
					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(
							__performOperation.getName(), "isApplicable_BWD") == 0);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE
					.createIsAppropriateRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(false);

			// create link
			ruleresult.setPerformOperation(__performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'test core match kernel'
		try {
			fujaba__Success = false;

			// check object wall is really bound
			JavaSDM.ensure(wall != null);
			// bind object
			floor = wall.getFloor();

			// check object floor is really bound
			JavaSDM.ensure(floor != null);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check object wall is really bound
				JavaSDM.ensure(wall != null);
				// check link figure from floor to wall
				JavaSDM.ensure(wall.equals(floor.getFigure()));

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_BWD(match, floor, wall);
				if (fujaba__Success) {
					// story node 'Add match to rule result'
					try {
						fujaba__Success = false;

						// check object match is really bound
						JavaSDM.ensure(match != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);

						// create link
						match.setIsAppropriateRuleResult(ruleresult);

						fujaba__Success = true;
					} catch (JavaSDMException fujaba__InternalException) {
						fujaba__Success = false;
					}

				} else {

				}
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'set success'
		try {
			fujaba__Success = false;

			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// iterate to-many link matches from ruleresult to match
			fujaba__Success = false;

			fujaba__IterRuleresultToMatch = ruleresult.getMatches().iterator();

			while (!(fujaba__Success)
					&& fujaba__IterRuleresultToMatch.hasNext()) {
				try {
					match = (Match) fujaba__IterRuleresultToMatch.next();

					// check object match is really bound
					JavaSDM.ensure(match != null);

					fujaba__Success = true;
				} catch (JavaSDMException fujaba__InternalException) {
					fujaba__Success = false;
				}
			}
			JavaSDM.ensure(fujaba__Success);
			// assign attribute ruleresult
			ruleresult.setSuccess(true);
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments)
			throws InvocationTargetException {
		switch (operationID) {
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE:
			return isAppropriate_FWD((Match) arguments.get(0),
					(Node) arguments.get(1), (Node) arguments.get(2));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_WALL:
			return isAppropriate_BWD((Match) arguments.get(0),
					(Floor) arguments.get(1), (Wall) arguments.get(2));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_24__NODE:
			return isAppropriate_FWD_Node_24((Node) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_25__NODE:
			return isAppropriate_FWD_Node_25((Node) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_11__FLOOR:
			return isAppropriate_BWD_Floor_11((Floor) arguments.get(0));
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_WALL_0__WALL:
			return isAppropriate_BWD_Wall_0((Wall) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //WallNodeToFigureRuleImpl

/**
 */
package SokobanCodeAdapter.Rules.impl;

import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import SokobanCodeAdapter.NodeToBoard;
import SokobanCodeAdapter.NodeToFloor;

import SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule;
import SokobanCodeAdapter.Rules.RulesPackage;

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
 * An implementation of the model object '<em><b>Leftcolumn Node To Floor Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class LeftcolumnNodeToFloorRuleImpl extends AbstractRuleImpl implements
		LeftcolumnNodeToFloorRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LeftcolumnNodeToFloorRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.LEFTCOLUMN_NODE_TO_FLOOR_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		Board board = null;
		Node boardNode = null;
		Node columnNode = null;
		NodeToBoard nodeToBoard = null;
		Node rowNode = null;
		Floor topNeighbor = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		NodeToFloor nodeToFloor = null;
		Floor floor = null;
		PerformRuleResult ruleresult = null;
		Edge nodeToFloor__source__columnNode = null;
		Edge floor__top__topNeighbor = null;
		Edge nodeToFloor__target__floor = null;
		Edge board__floors__floor = null;
		Edge rowNode__children__columnNode = null;
		Edge boardNode__children__rowNode = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("board"));

			// ensure correct type and really bound of object board
			JavaSDM.ensure(_TmpObject instanceof Board);
			board = (Board) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("boardNode"));

			// ensure correct type and really bound of object boardNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("columnNode"));

			// ensure correct type and really bound of object columnNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			columnNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("nodeToBoard"));

			// ensure correct type and really bound of object nodeToBoard
			JavaSDM.ensure(_TmpObject instanceof NodeToBoard);
			nodeToBoard = (NodeToBoard) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("rowNode"));

			// ensure correct type and really bound of object rowNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("topNeighbor"));

			// ensure correct type and really bound of object topNeighbor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			topNeighbor = (Floor) _TmpObject;
			// check object isApplicableMatch is really bound
			JavaSDM.ensure(isApplicableMatch != null);
			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

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
			JavaSDM.ensure(topNeighbor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(columnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(columnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(), "ROW") == 0);

			// create object nodeToFloor
			nodeToFloor = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToFloor();

			// create object floor
			floor = SokobanLanguageFactory.eINSTANCE.createFloor();

			// assign attribute floor
			floor.setCol(0);
			// assign attribute floor
			floor.setRow(((Number) csp.getAttributeVariable("floor", "row")
					.getValue()).intValue());

			// create link
			nodeToFloor.setSource(columnNode);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToFloor,
					floor, "target");

			// create link
			floor.setBoard(board);

			// create link
			floor.setTop(topNeighbor);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					columnNode, "translatedElements");
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'bookkeeping for edges'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object topNeighbor is really bound
			JavaSDM.ensure(topNeighbor != null);
			// check isomorphic binding between objects boardNode and board 
			JavaSDM.ensure(!boardNode.equals(board));

			// check isomorphic binding between objects columnNode and board 
			JavaSDM.ensure(!columnNode.equals(board));

			// check isomorphic binding between objects floor and board 
			JavaSDM.ensure(!floor.equals(board));

			// check isomorphic binding between objects nodeToBoard and board 
			JavaSDM.ensure(!nodeToBoard.equals(board));

			// check isomorphic binding between objects nodeToFloor and board 
			JavaSDM.ensure(!nodeToFloor.equals(board));

			// check isomorphic binding between objects rowNode and board 
			JavaSDM.ensure(!rowNode.equals(board));

			// check isomorphic binding between objects topNeighbor and board 
			JavaSDM.ensure(!topNeighbor.equals(board));

			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects floor and boardNode 
			JavaSDM.ensure(!floor.equals(boardNode));

			// check isomorphic binding between objects nodeToBoard and boardNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardNode));

			// check isomorphic binding between objects nodeToFloor and boardNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// check isomorphic binding between objects topNeighbor and boardNode 
			JavaSDM.ensure(!topNeighbor.equals(boardNode));

			// check isomorphic binding between objects floor and columnNode 
			JavaSDM.ensure(!floor.equals(columnNode));

			// check isomorphic binding between objects nodeToBoard and columnNode 
			JavaSDM.ensure(!nodeToBoard.equals(columnNode));

			// check isomorphic binding between objects nodeToFloor and columnNode 
			JavaSDM.ensure(!nodeToFloor.equals(columnNode));

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// check isomorphic binding between objects topNeighbor and columnNode 
			JavaSDM.ensure(!topNeighbor.equals(columnNode));

			// check isomorphic binding between objects nodeToBoard and floor 
			JavaSDM.ensure(!nodeToBoard.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floor 
			JavaSDM.ensure(!nodeToFloor.equals(floor));

			// check isomorphic binding between objects rowNode and floor 
			JavaSDM.ensure(!rowNode.equals(floor));

			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// check isomorphic binding between objects nodeToFloor and nodeToBoard 
			JavaSDM.ensure(!nodeToFloor.equals(nodeToBoard));

			// check isomorphic binding between objects rowNode and nodeToBoard 
			JavaSDM.ensure(!rowNode.equals(nodeToBoard));

			// check isomorphic binding between objects topNeighbor and nodeToBoard 
			JavaSDM.ensure(!topNeighbor.equals(nodeToBoard));

			// check isomorphic binding between objects rowNode and nodeToFloor 
			JavaSDM.ensure(!rowNode.equals(nodeToFloor));

			// check isomorphic binding between objects topNeighbor and nodeToFloor 
			JavaSDM.ensure(!topNeighbor.equals(nodeToFloor));

			// check isomorphic binding between objects topNeighbor and rowNode 
			JavaSDM.ensure(!topNeighbor.equals(rowNode));

			// create object nodeToFloor__source__columnNode
			nodeToFloor__source__columnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object floor__top__topNeighbor
			floor__top__topNeighbor = TGGRuntimeFactory.eINSTANCE.createEdge();

			// create object nodeToFloor__target__floor
			nodeToFloor__target__floor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object board__floors__floor
			board__floors__floor = TGGRuntimeFactory.eINSTANCE.createEdge();

			// create object rowNode__children__columnNode
			rowNode__children__columnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardNode__children__rowNode
			boardNode__children__rowNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// assign attribute boardNode__children__rowNode
			boardNode__children__rowNode.setName("children");
			// assign attribute rowNode__children__columnNode
			rowNode__children__columnNode.setName("children");
			// assign attribute nodeToFloor__source__columnNode
			nodeToFloor__source__columnNode.setName("source");
			// assign attribute nodeToFloor__target__floor
			nodeToFloor__target__floor.setName("target");
			// assign attribute board__floors__floor
			board__floors__floor.setName("floors");
			// assign attribute floor__top__topNeighbor
			floor__top__topNeighbor.setName("top");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__source__columnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor__top__topNeighbor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__target__floor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board__floors__floor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowNode__children__columnNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode__children__rowNode, "translatedEdges");

			// create link
			boardNode__children__rowNode.setSrc(boardNode);

			// create link
			rowNode__children__columnNode.setSrc(rowNode);

			// create link
			boardNode__children__rowNode.setTrg(rowNode);

			// create link
			rowNode__children__columnNode.setTrg(columnNode);

			// create link
			nodeToFloor__source__columnNode.setTrg(columnNode);

			// create link
			nodeToFloor__source__columnNode.setSrc(nodeToFloor);

			// create link
			nodeToFloor__target__floor.setSrc(nodeToFloor);

			// create link
			board__floors__floor.setSrc(board);

			// create link
			floor__top__topNeighbor.setTrg(topNeighbor);

			// create link
			floor__top__topNeighbor.setSrc(floor);

			// create link
			nodeToFloor__target__floor.setTrg(floor);

			// create link
			board__floors__floor.setTrg(floor);

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
	public boolean isAppropriate_FWD(Match match, Node boardNode, Node rowNode,
			Node columnNode) {
		boolean fujaba__Success = false;
		Edge boardNode__children__rowNode = null;
		Edge rowNode__children__columnNode = null;

		// statement node 'Solve CSP'
		// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables

		// Create explicit parameters

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// statement node 'Check CSP'
		fujaba__Success = csp.check();
		if (fujaba__Success) {
			// story node 'collect elements to be translated'
			try {
				fujaba__Success = false;

				// check object boardNode is really bound
				JavaSDM.ensure(boardNode != null);
				// check object columnNode is really bound
				JavaSDM.ensure(columnNode != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object rowNode is really bound
				JavaSDM.ensure(rowNode != null);
				// check isomorphic binding between objects columnNode and boardNode 
				JavaSDM.ensure(!columnNode.equals(boardNode));

				// check isomorphic binding between objects rowNode and boardNode 
				JavaSDM.ensure(!rowNode.equals(boardNode));

				// check isomorphic binding between objects rowNode and columnNode 
				JavaSDM.ensure(!rowNode.equals(columnNode));

				// create object boardNode__children__rowNode
				boardNode__children__rowNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object rowNode__children__columnNode
				rowNode__children__columnNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute boardNode__children__rowNode
				boardNode__children__rowNode.setName("children");
				// assign attribute rowNode__children__columnNode
				rowNode__children__columnNode.setName("children");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardNode__children__rowNode, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						rowNode__children__columnNode, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						columnNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						rowNode, "toBeTranslatedElements");

				// create link
				boardNode__children__rowNode.setTrg(rowNode);

				// create link
				rowNode__children__columnNode.setSrc(rowNode);

				// create link
				rowNode__children__columnNode.setTrg(columnNode);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(
						boardNode__children__rowNode, boardNode, "src");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object boardNode is really bound
				JavaSDM.ensure(boardNode != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardNode, "contextNodes");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("boardNode", boardNode);
			match.registerObject("rowNode", rowNode);
			match.registerObject("columnNode", columnNode);

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
		Node boardNode = null;
		Node columnNode = null;
		Node rowNode = null;
		IsApplicableMatch isApplicableMatch = null;
		Iterator fujaba__IterBoardToTopNeighbor = null;
		Floor topNeighbor = null;
		Board board = null;
		Iterator fujaba__IterBoardNodeToNodeToBoard = null;
		NodeToBoard nodeToBoard = null;

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
			ruleresult.setRule("LeftcolumnNodeToFloorRule");

			// create link
			ruleresult.setPerformOperation(performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'core match'
		try {
			fujaba__Success = false;

			_TmpObject = (match.getObject("boardNode"));

			// ensure correct type and really bound of object boardNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("columnNode"));

			// ensure correct type and really bound of object columnNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			columnNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("rowNode"));

			// ensure correct type and really bound of object rowNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowNode = (Node) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// attribute condition
			JavaSDM.ensure(columnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(columnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(), "ROW") == 0);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// check link children from rowNode to boardNode
			JavaSDM.ensure(boardNode.equals(rowNode.getParentNode()));

			// check link children from columnNode to rowNode
			JavaSDM.ensure(rowNode.equals(columnNode.getParentNode()));

			// iterate to-many link source from boardNode to nodeToBoard
			fujaba__Success = false;

			fujaba__IterBoardNodeToNodeToBoard = new ArrayList(
					org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReference(
							boardNode, NodeToBoard.class, "source")).iterator();

			while (fujaba__IterBoardNodeToNodeToBoard.hasNext()) {
				try {
					nodeToBoard = (NodeToBoard) fujaba__IterBoardNodeToNodeToBoard
							.next();

					// check object nodeToBoard is really bound
					JavaSDM.ensure(nodeToBoard != null);
					// bind object
					board = nodeToBoard.getTarget();

					// check object board is really bound
					JavaSDM.ensure(board != null);

					// iterate to-many link floors from board to topNeighbor
					fujaba__Success = false;

					fujaba__IterBoardToTopNeighbor = new ArrayList(
							board.getFloors()).iterator();

					while (fujaba__IterBoardToTopNeighbor.hasNext()) {
						try {
							topNeighbor = (Floor) fujaba__IterBoardToTopNeighbor
									.next();

							// check object topNeighbor is really bound
							JavaSDM.ensure(topNeighbor != null);
							// attribute condition
							JavaSDM.ensure(topNeighbor.getCol() == 0);

							// attribute condition
							JavaSDM.ensure(columnNode.getIndex() == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									boardNode.getName(), "BOARD") == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									columnNode.getName(), "COLUMN") == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									rowNode.getName(), "ROW") == 0);

							// create object isApplicableMatch
							isApplicableMatch = TGGRuntimeFactory.eINSTANCE
									.createIsApplicableMatch();

							// statement node 'solve CSP'
							// Create CSP
							CSP csp = CspFactory.eINSTANCE.createCSP();
							isApplicableMatch.getAttributeInfo().add(csp);

							// Create literals
							Variable<Number> literal0 = CspFactory.eINSTANCE
									.createVariable("literal0", true, csp);
							literal0.setValue(1);

							// Create attribute variables
							Variable<Number> var_topNeighbor_row = CspFactory.eINSTANCE
									.createVariable("topNeighbor.row", true,
											csp);
							var_topNeighbor_row.setValue(topNeighbor.getRow());
							Variable<Number> var_rowNode_index = CspFactory.eINSTANCE
									.createVariable("rowNode.index", true, csp);
							var_rowNode_index.setValue(rowNode.getIndex());

							// Create explicit parameters

							// Create unbound variables
							Variable<Number> var_floor_row = CspFactory.eINSTANCE
									.createVariable("floor.row", csp);

							// Create constraints
							Sub sub = new Sub();
							Eq<Number> eq = new Eq<Number>();

							csp.getConstraints().add(sub);
							csp.getConstraints().add(eq);

							// Solve CSP
							sub.setRuleName("");
							sub.solve(var_floor_row, literal0,
									var_topNeighbor_row);
							eq.setRuleName("");
							eq.solve(var_floor_row, var_rowNode_index);

							// Snapshot pattern match on which CSP is solved
							isApplicableMatch.registerObject("boardNode",
									boardNode);
							isApplicableMatch
									.registerObject("rowNode", rowNode);
							isApplicableMatch.registerObject("columnNode",
									columnNode);
							isApplicableMatch.registerObject("nodeToBoard",
									nodeToBoard);
							isApplicableMatch.registerObject("board", board);
							isApplicableMatch.registerObject("topNeighbor",
									topNeighbor);

							// statement node 'check CSP'
							fujaba__Success = csp.check();
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
		Board board = null;
		Node boardNode = null;
		Floor floor = null;
		NodeToBoard nodeToBoard = null;
		Floor topNeighbor = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		Node rowNode = null;
		Node columnNode = null;
		NodeToFloor nodeToFloor = null;
		PerformRuleResult ruleresult = null;
		Edge board__floors__floor = null;
		Edge nodeToFloor__source__columnNode = null;
		Edge boardNode__children__rowNode = null;
		Edge rowNode__children__columnNode = null;
		Edge nodeToFloor__target__floor = null;
		Edge floor__top__topNeighbor = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("board"));

			// ensure correct type and really bound of object board
			JavaSDM.ensure(_TmpObject instanceof Board);
			board = (Board) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("boardNode"));

			// ensure correct type and really bound of object boardNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("floor"));

			// ensure correct type and really bound of object floor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			floor = (Floor) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("nodeToBoard"));

			// ensure correct type and really bound of object nodeToBoard
			JavaSDM.ensure(_TmpObject instanceof NodeToBoard);
			nodeToBoard = (NodeToBoard) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("topNeighbor"));

			// ensure correct type and really bound of object topNeighbor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			topNeighbor = (Floor) _TmpObject;
			// check object isApplicableMatch is really bound
			JavaSDM.ensure(isApplicableMatch != null);
			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

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
			JavaSDM.ensure(topNeighbor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(floor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// create object rowNode
			rowNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object columnNode
			columnNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object nodeToFloor
			nodeToFloor = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToFloor();

			// assign attribute rowNode
			rowNode.setName("ROW");
			// assign attribute rowNode
			rowNode.setIndex(((Number) csp.getAttributeVariable("rowNode",
					"index").getValue()).intValue());
			// assign attribute columnNode
			columnNode.setIndex(0);
			// assign attribute columnNode
			columnNode.setName("COLUMN");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToFloor,
					floor, "target");

			// create link
			rowNode.setParentNode(boardNode);

			// create link
			columnNode.setParentNode(rowNode);

			// create link
			nodeToFloor.setSource(columnNode);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					columnNode, "createdElements");
			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'bookkeeping for edges'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object topNeighbor is really bound
			JavaSDM.ensure(topNeighbor != null);
			// check isomorphic binding between objects boardNode and board 
			JavaSDM.ensure(!boardNode.equals(board));

			// check isomorphic binding between objects columnNode and board 
			JavaSDM.ensure(!columnNode.equals(board));

			// check isomorphic binding between objects floor and board 
			JavaSDM.ensure(!floor.equals(board));

			// check isomorphic binding between objects nodeToBoard and board 
			JavaSDM.ensure(!nodeToBoard.equals(board));

			// check isomorphic binding between objects nodeToFloor and board 
			JavaSDM.ensure(!nodeToFloor.equals(board));

			// check isomorphic binding between objects rowNode and board 
			JavaSDM.ensure(!rowNode.equals(board));

			// check isomorphic binding between objects topNeighbor and board 
			JavaSDM.ensure(!topNeighbor.equals(board));

			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects floor and boardNode 
			JavaSDM.ensure(!floor.equals(boardNode));

			// check isomorphic binding between objects nodeToBoard and boardNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardNode));

			// check isomorphic binding between objects nodeToFloor and boardNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// check isomorphic binding between objects topNeighbor and boardNode 
			JavaSDM.ensure(!topNeighbor.equals(boardNode));

			// check isomorphic binding between objects floor and columnNode 
			JavaSDM.ensure(!floor.equals(columnNode));

			// check isomorphic binding between objects nodeToBoard and columnNode 
			JavaSDM.ensure(!nodeToBoard.equals(columnNode));

			// check isomorphic binding between objects nodeToFloor and columnNode 
			JavaSDM.ensure(!nodeToFloor.equals(columnNode));

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// check isomorphic binding between objects topNeighbor and columnNode 
			JavaSDM.ensure(!topNeighbor.equals(columnNode));

			// check isomorphic binding between objects nodeToBoard and floor 
			JavaSDM.ensure(!nodeToBoard.equals(floor));

			// check isomorphic binding between objects nodeToFloor and floor 
			JavaSDM.ensure(!nodeToFloor.equals(floor));

			// check isomorphic binding between objects rowNode and floor 
			JavaSDM.ensure(!rowNode.equals(floor));

			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// check isomorphic binding between objects nodeToFloor and nodeToBoard 
			JavaSDM.ensure(!nodeToFloor.equals(nodeToBoard));

			// check isomorphic binding between objects rowNode and nodeToBoard 
			JavaSDM.ensure(!rowNode.equals(nodeToBoard));

			// check isomorphic binding between objects topNeighbor and nodeToBoard 
			JavaSDM.ensure(!topNeighbor.equals(nodeToBoard));

			// check isomorphic binding between objects rowNode and nodeToFloor 
			JavaSDM.ensure(!rowNode.equals(nodeToFloor));

			// check isomorphic binding between objects topNeighbor and nodeToFloor 
			JavaSDM.ensure(!topNeighbor.equals(nodeToFloor));

			// check isomorphic binding between objects topNeighbor and rowNode 
			JavaSDM.ensure(!topNeighbor.equals(rowNode));

			// create object board__floors__floor
			board__floors__floor = TGGRuntimeFactory.eINSTANCE.createEdge();

			// create object nodeToFloor__source__columnNode
			nodeToFloor__source__columnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardNode__children__rowNode
			boardNode__children__rowNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object rowNode__children__columnNode
			rowNode__children__columnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToFloor__target__floor
			nodeToFloor__target__floor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object floor__top__topNeighbor
			floor__top__topNeighbor = TGGRuntimeFactory.eINSTANCE.createEdge();

			// assign attribute boardNode__children__rowNode
			boardNode__children__rowNode.setName("children");
			// assign attribute rowNode__children__columnNode
			rowNode__children__columnNode.setName("children");
			// assign attribute nodeToFloor__source__columnNode
			nodeToFloor__source__columnNode.setName("source");
			// assign attribute nodeToFloor__target__floor
			nodeToFloor__target__floor.setName("target");
			// assign attribute board__floors__floor
			board__floors__floor.setName("floors");
			// assign attribute floor__top__topNeighbor
			floor__top__topNeighbor.setName("top");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board__floors__floor, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__source__columnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode__children__rowNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowNode__children__columnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__target__floor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					floor__top__topNeighbor, "translatedEdges");

			// create link
			boardNode__children__rowNode.setSrc(boardNode);

			// create link
			rowNode__children__columnNode.setSrc(rowNode);

			// create link
			boardNode__children__rowNode.setTrg(rowNode);

			// create link
			rowNode__children__columnNode.setTrg(columnNode);

			// create link
			nodeToFloor__source__columnNode.setTrg(columnNode);

			// create link
			nodeToFloor__target__floor.setSrc(nodeToFloor);

			// create link
			nodeToFloor__source__columnNode.setSrc(nodeToFloor);

			// create link
			board__floors__floor.setSrc(board);

			// create link
			floor__top__topNeighbor.setTrg(topNeighbor);

			// create link
			nodeToFloor__target__floor.setTrg(floor);

			// create link
			board__floors__floor.setTrg(floor);

			// create link
			floor__top__topNeighbor.setSrc(floor);

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
	public boolean isAppropriate_BWD(Match match, Board board,
			Floor topNeighbor, Floor floor) {
		boolean fujaba__Success = false;
		Edge board__floors__floor = null;
		Edge floor__top__topNeighbor = null;
		Edge board__floors__topNeighbor = null;

		// statement node 'Solve CSP'
		// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals
		Variable<Number> literal0 = CspFactory.eINSTANCE.createVariable(
				"literal0", true, csp);
		literal0.setValue(1);

		// Create attribute variables
		Variable<Number> var_floor_row = CspFactory.eINSTANCE.createVariable(
				"floor.row", true, csp);
		var_floor_row.setValue(floor.getRow());
		Variable<Number> var_topNeighbor_row = CspFactory.eINSTANCE
				.createVariable("topNeighbor.row", true, csp);
		var_topNeighbor_row.setValue(topNeighbor.getRow());

		// Create explicit parameters

		// Create unbound variables

		// Create constraints
		Sub sub = new Sub();

		csp.getConstraints().add(sub);

		// Solve CSP
		sub.setRuleName("");
		sub.solve(var_floor_row, literal0, var_topNeighbor_row);

		// statement node 'Check CSP'
		fujaba__Success = csp.check();
		if (fujaba__Success) {
			// story node 'collect elements to be translated'
			try {
				fujaba__Success = false;

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object topNeighbor is really bound
				JavaSDM.ensure(topNeighbor != null);
				// check isomorphic binding between objects topNeighbor and floor 
				JavaSDM.ensure(!topNeighbor.equals(floor));

				// create object board__floors__floor
				board__floors__floor = TGGRuntimeFactory.eINSTANCE.createEdge();

				// create object floor__top__topNeighbor
				floor__top__topNeighbor = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute board__floors__floor
				board__floors__floor.setName("floors");
				// assign attribute floor__top__topNeighbor
				floor__top__topNeighbor.setName("top");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						board__floors__floor, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floor, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						floor__top__topNeighbor, "toBeTranslatedEdges");

				// create link
				board__floors__floor.setTrg(floor);

				// create link
				floor__top__topNeighbor.setSrc(floor);

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(
						board__floors__floor, board, "src");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(
						floor__top__topNeighbor, topNeighbor, "trg");
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object topNeighbor is really bound
				JavaSDM.ensure(topNeighbor != null);
				// create object board__floors__topNeighbor
				board__floors__topNeighbor = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute board__floors__topNeighbor
				board__floors__topNeighbor.setName("floors");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						board, "contextNodes");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						topNeighbor, "contextNodes");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						board__floors__topNeighbor, "contextEdges");

				// create link
				board__floors__topNeighbor.setSrc(board);

				// create link
				board__floors__topNeighbor.setTrg(topNeighbor);

				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("board", board);
			match.registerObject("topNeighbor", topNeighbor);
			match.registerObject("floor", floor);

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
		Board board = null;
		Floor floor = null;
		Floor topNeighbor = null;
		IsApplicableMatch isApplicableMatch = null;
		Node boardNode = null;
		Iterator fujaba__IterBoardToNodeToBoard = null;
		NodeToBoard nodeToBoard = null;

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
			ruleresult.setRule("LeftcolumnNodeToFloorRule");

			// create link
			ruleresult.setPerformOperation(performOperation);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'core match'
		try {
			fujaba__Success = false;

			_TmpObject = (match.getObject("board"));

			// ensure correct type and really bound of object board
			JavaSDM.ensure(_TmpObject instanceof Board);
			board = (Board) _TmpObject;
			_TmpObject = (match.getObject("floor"));

			// ensure correct type and really bound of object floor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			floor = (Floor) _TmpObject;
			_TmpObject = (match.getObject("topNeighbor"));

			// ensure correct type and really bound of object topNeighbor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			topNeighbor = (Floor) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// attribute condition
			JavaSDM.ensure(topNeighbor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(floor.getCol() == 0);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object floor is really bound
			JavaSDM.ensure(floor != null);
			// check object topNeighbor is really bound
			JavaSDM.ensure(topNeighbor != null);
			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// check link bottom from floor to topNeighbor
			JavaSDM.ensure(topNeighbor.equals(floor.getTop()));

			// check link floors from floor to board
			JavaSDM.ensure(board.equals(floor.getBoard()));

			// check link floors from topNeighbor to board
			JavaSDM.ensure(board.equals(topNeighbor.getBoard()));

			// iterate to-many link target from board to nodeToBoard
			fujaba__Success = false;

			fujaba__IterBoardToNodeToBoard = new ArrayList(
					org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReference(board,
							NodeToBoard.class, "target")).iterator();

			while (fujaba__IterBoardToNodeToBoard.hasNext()) {
				try {
					nodeToBoard = (NodeToBoard) fujaba__IterBoardToNodeToBoard
							.next();

					// check object nodeToBoard is really bound
					JavaSDM.ensure(nodeToBoard != null);
					// bind object
					boardNode = nodeToBoard.getSource();

					// check object boardNode is really bound
					JavaSDM.ensure(boardNode != null);

					// attribute condition
					JavaSDM.ensure(floor.getCol() == 0);

					// attribute condition
					JavaSDM.ensure(topNeighbor.getCol() == 0);

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(),
							"BOARD") == 0);

					// create object isApplicableMatch
					isApplicableMatch = TGGRuntimeFactory.eINSTANCE
							.createIsApplicableMatch();

					// statement node 'solve CSP'
					// Create CSP
					CSP csp = CspFactory.eINSTANCE.createCSP();
					isApplicableMatch.getAttributeInfo().add(csp);

					// Create literals

					// Create attribute variables
					Variable<Number> var_floor_row = CspFactory.eINSTANCE
							.createVariable("floor.row", true, csp);
					var_floor_row.setValue(floor.getRow());

					// Create explicit parameters

					// Create unbound variables
					Variable<Number> var_rowNode_index = CspFactory.eINSTANCE
							.createVariable("rowNode.index", csp);

					// Create constraints
					Eq<Number> eq = new Eq<Number>();

					csp.getConstraints().add(eq);

					// Solve CSP
					eq.setRuleName("");
					eq.solve(var_floor_row, var_rowNode_index);

					// Snapshot pattern match on which CSP is solved
					isApplicableMatch.registerObject("boardNode", boardNode);
					isApplicableMatch
							.registerObject("nodeToBoard", nodeToBoard);
					isApplicableMatch.registerObject("board", board);
					isApplicableMatch
							.registerObject("topNeighbor", topNeighbor);
					isApplicableMatch.registerObject("floor", floor);

					// statement node 'check CSP'
					fujaba__Success = csp.check();
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_16(Node boardNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterRowNodeToColumnNode = null;
		Node columnNode = null;
		Iterator fujaba__IterBoardNodeToRowNode = null;
		Node rowNode = null;
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

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// iterate to-many link children from boardNode to rowNode
			fujaba__Success = false;

			fujaba__IterBoardNodeToRowNode = new ArrayList(
					boardNode.getChildren("ROW")).iterator();

			while (fujaba__IterBoardNodeToRowNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardNodeToRowNode.next();

					// ensure correct type and really bound of object rowNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					rowNode = (Node) _TmpObject;
					// check isomorphic binding between objects rowNode and boardNode 
					JavaSDM.ensure(!rowNode.equals(boardNode));

					// iterate to-many link children from rowNode to columnNode
					fujaba__Success = false;

					fujaba__IterRowNodeToColumnNode = new ArrayList(
							rowNode.getChildren("COLUMN", 0)).iterator();

					while (fujaba__IterRowNodeToColumnNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterRowNodeToColumnNode.next();

							// ensure correct type and really bound of object columnNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							columnNode = (Node) _TmpObject;
							// check isomorphic binding between objects columnNode and boardNode 
							JavaSDM.ensure(!columnNode.equals(boardNode));

							// check isomorphic binding between objects rowNode and columnNode 
							JavaSDM.ensure(!rowNode.equals(columnNode));

							// attribute condition
							JavaSDM.ensure(columnNode.getIndex() == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									boardNode.getName(), "BOARD") == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									columnNode.getName(), "COLUMN") == 0);

							// attribute condition
							JavaSDM.ensure(JavaSDM.stringCompare(
									rowNode.getName(), "ROW") == 0);

							// story node 'test core match'
							try {
								fujaba__Success = false;

								// check object boardNode is really bound
								JavaSDM.ensure(boardNode != null);
								// check object columnNode is really bound
								JavaSDM.ensure(columnNode != null);
								// check object rowNode is really bound
								JavaSDM.ensure(rowNode != null);
								// check object ruleresult is really bound
								JavaSDM.ensure(ruleresult != null);
								// check isomorphic binding between objects columnNode and boardNode 
								JavaSDM.ensure(!columnNode.equals(boardNode));

								// check isomorphic binding between objects rowNode and boardNode 
								JavaSDM.ensure(!rowNode.equals(boardNode));

								// check isomorphic binding between objects rowNode and columnNode 
								JavaSDM.ensure(!rowNode.equals(columnNode));

								// check link children from rowNode to boardNode
								JavaSDM.ensure(boardNode.equals(rowNode
										.getParentNode()));

								// check link children from columnNode to rowNode
								JavaSDM.ensure(rowNode.equals(columnNode
										.getParentNode()));

								// attribute condition
								JavaSDM.ensure(columnNode.getIndex() == 0);

								// attribute condition
								JavaSDM.ensure(JavaSDM.stringCompare(
										boardNode.getName(), "BOARD") == 0);

								// attribute condition
								JavaSDM.ensure(JavaSDM.stringCompare(
										columnNode.getName(), "COLUMN") == 0);

								// attribute condition
								JavaSDM.ensure(JavaSDM.stringCompare(
										rowNode.getName(), "ROW") == 0);

								// create object match
								match = TGGRuntimeFactory.eINSTANCE
										.createMatch();

								// statement node 'bookkeeping with generic isAppropriate method'
								fujaba__Success = this.isAppropriate_FWD(match,
										boardNode, rowNode, columnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_17(Node rowNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterRowNodeToColumnNode = null;
		Node columnNode = null;
		Node boardNode = null;
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

			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);
			// bind object
			boardNode = rowNode.getParentNode();

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// iterate to-many link children from rowNode to columnNode
			fujaba__Success = false;

			fujaba__IterRowNodeToColumnNode = new ArrayList(
					rowNode.getChildren("COLUMN", 0)).iterator();

			while (fujaba__IterRowNodeToColumnNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterRowNodeToColumnNode.next();

					// ensure correct type and really bound of object columnNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					columnNode = (Node) _TmpObject;
					// check isomorphic binding between objects columnNode and boardNode 
					JavaSDM.ensure(!columnNode.equals(boardNode));

					// check isomorphic binding between objects rowNode and columnNode 
					JavaSDM.ensure(!rowNode.equals(columnNode));

					// attribute condition
					JavaSDM.ensure(columnNode.getIndex() == 0);

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(),
							"BOARD") == 0);

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(columnNode.getName(),
							"COLUMN") == 0);

					// attribute condition
					JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(),
							"ROW") == 0);

					// story node 'test core match'
					try {
						fujaba__Success = false;

						// check object boardNode is really bound
						JavaSDM.ensure(boardNode != null);
						// check object columnNode is really bound
						JavaSDM.ensure(columnNode != null);
						// check object rowNode is really bound
						JavaSDM.ensure(rowNode != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);
						// check isomorphic binding between objects columnNode and boardNode 
						JavaSDM.ensure(!columnNode.equals(boardNode));

						// check isomorphic binding between objects rowNode and boardNode 
						JavaSDM.ensure(!rowNode.equals(boardNode));

						// check isomorphic binding between objects rowNode and columnNode 
						JavaSDM.ensure(!rowNode.equals(columnNode));

						// check link children from rowNode to boardNode
						JavaSDM.ensure(boardNode.equals(rowNode.getParentNode()));

						// check link children from columnNode to rowNode
						JavaSDM.ensure(rowNode.equals(columnNode
								.getParentNode()));

						// attribute condition
						JavaSDM.ensure(columnNode.getIndex() == 0);

						// attribute condition
						JavaSDM.ensure(JavaSDM.stringCompare(
								boardNode.getName(), "BOARD") == 0);

						// attribute condition
						JavaSDM.ensure(JavaSDM.stringCompare(
								columnNode.getName(), "COLUMN") == 0);

						// attribute condition
						JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(),
								"ROW") == 0);

						// create object match
						match = TGGRuntimeFactory.eINSTANCE.createMatch();

						// statement node 'bookkeeping with generic isAppropriate method'
						fujaba__Success = this.isAppropriate_FWD(match,
								boardNode, rowNode, columnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_18(Node columnNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Node boardNode = null;
		Node rowNode = null;
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

			// check object columnNode is really bound
			JavaSDM.ensure(columnNode != null);
			// bind object
			rowNode = columnNode.getParentNode();

			// check object rowNode is really bound
			JavaSDM.ensure(rowNode != null);

			// check isomorphic binding between objects rowNode and columnNode 
			JavaSDM.ensure(!rowNode.equals(columnNode));

			// bind object
			boardNode = rowNode.getParentNode();

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);

			// check isomorphic binding between objects columnNode and boardNode 
			JavaSDM.ensure(!columnNode.equals(boardNode));

			// check isomorphic binding between objects rowNode and boardNode 
			JavaSDM.ensure(!rowNode.equals(boardNode));

			// attribute condition
			JavaSDM.ensure(columnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(columnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(), "ROW") == 0);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object boardNode is really bound
				JavaSDM.ensure(boardNode != null);
				// check object columnNode is really bound
				JavaSDM.ensure(columnNode != null);
				// check object rowNode is really bound
				JavaSDM.ensure(rowNode != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check isomorphic binding between objects columnNode and boardNode 
				JavaSDM.ensure(!columnNode.equals(boardNode));

				// check isomorphic binding between objects rowNode and boardNode 
				JavaSDM.ensure(!rowNode.equals(boardNode));

				// check isomorphic binding between objects rowNode and columnNode 
				JavaSDM.ensure(!rowNode.equals(columnNode));

				// check link children from rowNode to boardNode
				JavaSDM.ensure(boardNode.equals(rowNode.getParentNode()));

				// check link children from columnNode to rowNode
				JavaSDM.ensure(rowNode.equals(columnNode.getParentNode()));

				// attribute condition
				JavaSDM.ensure(columnNode.getIndex() == 0);

				// attribute condition
				JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(),
						"BOARD") == 0);

				// attribute condition
				JavaSDM.ensure(JavaSDM.stringCompare(columnNode.getName(),
						"COLUMN") == 0);

				// attribute condition
				JavaSDM.ensure(JavaSDM.stringCompare(rowNode.getName(), "ROW") == 0);

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_FWD(match, boardNode,
						rowNode, columnNode);
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
	public IsAppropriateRuleResult isAppropriate_BWD_Board_2(Board board) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Floor floor = null;
		Iterator fujaba__IterBoardToTopNeighbor = null;
		Floor topNeighbor = null;
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

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// iterate to-many link floors from board to topNeighbor
			fujaba__Success = false;

			fujaba__IterBoardToTopNeighbor = new ArrayList(board.getFloors())
					.iterator();

			while (fujaba__IterBoardToTopNeighbor.hasNext()) {
				try {
					topNeighbor = (Floor) fujaba__IterBoardToTopNeighbor.next();

					// check object topNeighbor is really bound
					JavaSDM.ensure(topNeighbor != null);
					// bind object
					floor = topNeighbor.getBottom();

					// check object floor is really bound
					JavaSDM.ensure(floor != null);

					// check isomorphic binding between objects topNeighbor and floor 
					JavaSDM.ensure(!topNeighbor.equals(floor));

					// check link floors from floor to board
					JavaSDM.ensure(board.equals(floor.getBoard()));

					// attribute condition
					JavaSDM.ensure(floor.getCol() == 0);

					// attribute condition
					JavaSDM.ensure(topNeighbor.getCol() == 0);

					// story node 'test core match'
					try {
						fujaba__Success = false;

						// check object board is really bound
						JavaSDM.ensure(board != null);
						// check object floor is really bound
						JavaSDM.ensure(floor != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);
						// check object topNeighbor is really bound
						JavaSDM.ensure(topNeighbor != null);
						// check isomorphic binding between objects topNeighbor and floor 
						JavaSDM.ensure(!topNeighbor.equals(floor));

						// check link bottom from floor to topNeighbor
						JavaSDM.ensure(topNeighbor.equals(floor.getTop()));

						// check link floors from floor to board
						JavaSDM.ensure(board.equals(floor.getBoard()));

						// check link floors from topNeighbor to board
						JavaSDM.ensure(board.equals(topNeighbor.getBoard()));

						// attribute condition
						JavaSDM.ensure(floor.getCol() == 0);

						// attribute condition
						JavaSDM.ensure(topNeighbor.getCol() == 0);

						// create object match
						match = TGGRuntimeFactory.eINSTANCE.createMatch();

						// statement node 'bookkeeping with generic isAppropriate method'
						fujaba__Success = this.isAppropriate_BWD(match, board,
								topNeighbor, floor);
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
	public IsAppropriateRuleResult isAppropriate_BWD_Floor_6(Floor topNeighbor) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Board board = null;
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

			// check object topNeighbor is really bound
			JavaSDM.ensure(topNeighbor != null);
			// bind object
			floor = topNeighbor.getBottom();

			// check object floor is really bound
			JavaSDM.ensure(floor != null);

			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// bind object
			board = topNeighbor.getBoard();

			// check object board is really bound
			JavaSDM.ensure(board != null);

			// check link floors from floor to board
			JavaSDM.ensure(board.equals(floor.getBoard()));

			// attribute condition
			JavaSDM.ensure(topNeighbor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(floor.getCol() == 0);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check object topNeighbor is really bound
				JavaSDM.ensure(topNeighbor != null);
				// check isomorphic binding between objects topNeighbor and floor 
				JavaSDM.ensure(!topNeighbor.equals(floor));

				// check link bottom from floor to topNeighbor
				JavaSDM.ensure(topNeighbor.equals(floor.getTop()));

				// check link floors from floor to board
				JavaSDM.ensure(board.equals(floor.getBoard()));

				// check link floors from topNeighbor to board
				JavaSDM.ensure(board.equals(topNeighbor.getBoard()));

				// attribute condition
				JavaSDM.ensure(topNeighbor.getCol() == 0);

				// attribute condition
				JavaSDM.ensure(floor.getCol() == 0);

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_BWD(match, board,
						topNeighbor, floor);
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
	public IsAppropriateRuleResult isAppropriate_BWD_Floor_7(Floor floor) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Board board = null;
		Floor topNeighbor = null;
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
			topNeighbor = floor.getTop();

			// check object topNeighbor is really bound
			JavaSDM.ensure(topNeighbor != null);

			// check isomorphic binding between objects topNeighbor and floor 
			JavaSDM.ensure(!topNeighbor.equals(floor));

			// bind object
			board = floor.getBoard();

			// check object board is really bound
			JavaSDM.ensure(board != null);

			// check link floors from topNeighbor to board
			JavaSDM.ensure(board.equals(topNeighbor.getBoard()));

			// attribute condition
			JavaSDM.ensure(floor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(topNeighbor.getCol() == 0);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object floor is really bound
				JavaSDM.ensure(floor != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check object topNeighbor is really bound
				JavaSDM.ensure(topNeighbor != null);
				// check isomorphic binding between objects topNeighbor and floor 
				JavaSDM.ensure(!topNeighbor.equals(floor));

				// check link bottom from floor to topNeighbor
				JavaSDM.ensure(topNeighbor.equals(floor.getTop()));

				// check link floors from floor to board
				JavaSDM.ensure(board.equals(floor.getBoard()));

				// check link floors from topNeighbor to board
				JavaSDM.ensure(board.equals(topNeighbor.getBoard()));

				// attribute condition
				JavaSDM.ensure(topNeighbor.getCol() == 0);

				// attribute condition
				JavaSDM.ensure(floor.getCol() == 0);

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_BWD(match, board,
						topNeighbor, floor);
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
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE:
			return isAppropriate_FWD((Match) arguments.get(0),
					(Node) arguments.get(1), (Node) arguments.get(2),
					(Node) arguments.get(3));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR:
			return isAppropriate_BWD((Match) arguments.get(0),
					(Board) arguments.get(1), (Floor) arguments.get(2),
					(Floor) arguments.get(3));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_16__NODE:
			return isAppropriate_FWD_Node_16((Node) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_17__NODE:
			return isAppropriate_FWD_Node_17((Node) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_18__NODE:
			return isAppropriate_FWD_Node_18((Node) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_2__BOARD:
			return isAppropriate_BWD_Board_2((Board) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_6__FLOOR:
			return isAppropriate_BWD_Floor_6((Floor) arguments.get(0));
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_7__FLOOR:
			return isAppropriate_BWD_Floor_7((Floor) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //LeftcolumnNodeToFloorRuleImpl

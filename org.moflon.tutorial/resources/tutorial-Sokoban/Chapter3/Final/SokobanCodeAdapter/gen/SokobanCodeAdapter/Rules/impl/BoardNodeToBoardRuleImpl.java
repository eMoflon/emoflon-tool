/**
 */
package SokobanCodeAdapter.Rules.impl;

import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import SokobanCodeAdapter.NodeToBoard;
import SokobanCodeAdapter.NodeToFloor;

import SokobanCodeAdapter.Rules.BoardNodeToBoardRule;
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
 * An implementation of the model object '<em><b>Board Node To Board Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class BoardNodeToBoardRuleImpl extends AbstractRuleImpl implements
		BoardNodeToBoardRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoardNodeToBoardRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.BOARD_NODE_TO_BOARD_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		Node boardNode = null;
		Node boardSpecNode = null;
		Node colsNode = null;
		Node columnCountNode = null;
		Node dimensionNode = null;
		File file = null;
		Node rowCountNode = null;
		Node rowsNode = null;
		Node topRowLeftColumnNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		NodeToBoard nodeToBoard = null;
		NodeToFloor nodeToFloor = null;
		Board board = null;
		Floor topLeftFloor = null;
		PerformRuleResult ruleresult = null;
		Edge file__rootNode__boardSpecNode = null;
		Edge rowsNode__children__rowCountNode = null;
		Edge dimensionNode__children__rowsNode = null;
		Edge boardSpecNode__children__boardNode = null;
		Edge nodeToBoard__source__boardNode = null;
		Edge boardNode__children__topRowNode = null;
		Edge nodeToBoard__target__board = null;
		Edge board__floors__topLeftFloor = null;
		Edge nodeToFloor__source__topRowLeftColumnNode = null;
		Edge dimensionNode__children__colsNode = null;
		Edge nodeToFloor__target__topLeftFloor = null;
		Edge colsNode__children__columnCountNode = null;
		Edge topRowNode__children__topRowLeftColumnNode = null;
		Edge boardSpecNode__children__dimensionNode = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("boardNode"));

			// ensure correct type and really bound of object boardNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("boardSpecNode"));

			// ensure correct type and really bound of object boardSpecNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardSpecNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("colsNode"));

			// ensure correct type and really bound of object colsNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			colsNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("columnCountNode"));

			// ensure correct type and really bound of object columnCountNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			columnCountNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("dimensionNode"));

			// ensure correct type and really bound of object dimensionNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			dimensionNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("file"));

			// ensure correct type and really bound of object file
			JavaSDM.ensure(_TmpObject instanceof File);
			file = (File) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("rowCountNode"));

			// ensure correct type and really bound of object rowCountNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowCountNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("rowsNode"));

			// ensure correct type and really bound of object rowsNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowsNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("topRowLeftColumnNode"));

			// ensure correct type and really bound of object topRowLeftColumnNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			topRowLeftColumnNode = (Node) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("topRowNode"));

			// ensure correct type and really bound of object topRowNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			topRowNode = (Node) _TmpObject;
			// check object isApplicableMatch is really bound
			JavaSDM.ensure(isApplicableMatch != null);
			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

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
			JavaSDM.ensure(dimensionNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowsNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(columnCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowLeftColumnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(colsNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(boardNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardSpecNode.getName(),
					"BOARD_SPEC") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(colsNode.getName(), "COLS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(
					topRowLeftColumnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(dimensionNode.getName(),
					"DIMENSIONS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(topRowNode.getName(), "ROW") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowsNode.getName(), "ROWS") == 0);

			// create object nodeToBoard
			nodeToBoard = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToBoard();

			// create object nodeToFloor
			nodeToFloor = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToFloor();

			// create object board
			board = SokobanLanguageFactory.eINSTANCE.createBoard();

			// create object topLeftFloor
			topLeftFloor = SokobanLanguageFactory.eINSTANCE.createFloor();

			// assign attribute board
			board.setHeight(((Number) csp.getAttributeVariable("board",
					"height").getValue()).intValue());
			// assign attribute board
			board.setWidth(((Number) csp.getAttributeVariable("board", "width")
					.getValue()).intValue());
			// assign attribute topLeftFloor
			topLeftFloor.setRow(0);
			// assign attribute topLeftFloor
			topLeftFloor.setCol(0);

			// create link
			nodeToBoard.setSource(boardNode);

			// create link
			nodeToFloor.setSource(topRowLeftColumnNode);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToBoard,
					board, "target");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToFloor,
					topLeftFloor, "target");

			// create link
			topLeftFloor.setBoard(board);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// check object file is really bound
			JavaSDM.ensure(file != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowLeftColumnNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowsNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topLeftFloor, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowCountNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					columnCountNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					file, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					colsNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowNode, "translatedElements");
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
			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// check object file is really bound
			JavaSDM.ensure(file != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// check isomorphic binding between objects boardNode and board 
			JavaSDM.ensure(!boardNode.equals(board));

			// check isomorphic binding between objects boardSpecNode and board 
			JavaSDM.ensure(!boardSpecNode.equals(board));

			// check isomorphic binding between objects colsNode and board 
			JavaSDM.ensure(!colsNode.equals(board));

			// check isomorphic binding between objects columnCountNode and board 
			JavaSDM.ensure(!columnCountNode.equals(board));

			// check isomorphic binding between objects dimensionNode and board 
			JavaSDM.ensure(!dimensionNode.equals(board));

			// check isomorphic binding between objects file and board 
			JavaSDM.ensure(!file.equals(board));

			// check isomorphic binding between objects nodeToBoard and board 
			JavaSDM.ensure(!nodeToBoard.equals(board));

			// check isomorphic binding between objects nodeToFloor and board 
			JavaSDM.ensure(!nodeToFloor.equals(board));

			// check isomorphic binding between objects rowCountNode and board 
			JavaSDM.ensure(!rowCountNode.equals(board));

			// check isomorphic binding between objects rowsNode and board 
			JavaSDM.ensure(!rowsNode.equals(board));

			// check isomorphic binding between objects topLeftFloor and board 
			JavaSDM.ensure(!topLeftFloor.equals(board));

			// check isomorphic binding between objects topRowLeftColumnNode and board 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(board));

			// check isomorphic binding between objects topRowNode and board 
			JavaSDM.ensure(!topRowNode.equals(board));

			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects file and boardNode 
			JavaSDM.ensure(!file.equals(boardNode));

			// check isomorphic binding between objects nodeToBoard and boardNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardNode));

			// check isomorphic binding between objects nodeToFloor and boardNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topLeftFloor and boardNode 
			JavaSDM.ensure(!topLeftFloor.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects file and boardSpecNode 
			JavaSDM.ensure(!file.equals(boardSpecNode));

			// check isomorphic binding between objects nodeToBoard and boardSpecNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardSpecNode));

			// check isomorphic binding between objects nodeToFloor and boardSpecNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topLeftFloor and boardSpecNode 
			JavaSDM.ensure(!topLeftFloor.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects file and colsNode 
			JavaSDM.ensure(!file.equals(colsNode));

			// check isomorphic binding between objects nodeToBoard and colsNode 
			JavaSDM.ensure(!nodeToBoard.equals(colsNode));

			// check isomorphic binding between objects nodeToFloor and colsNode 
			JavaSDM.ensure(!nodeToFloor.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topLeftFloor and colsNode 
			JavaSDM.ensure(!topLeftFloor.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects file and columnCountNode 
			JavaSDM.ensure(!file.equals(columnCountNode));

			// check isomorphic binding between objects nodeToBoard and columnCountNode 
			JavaSDM.ensure(!nodeToBoard.equals(columnCountNode));

			// check isomorphic binding between objects nodeToFloor and columnCountNode 
			JavaSDM.ensure(!nodeToFloor.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topLeftFloor and columnCountNode 
			JavaSDM.ensure(!topLeftFloor.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects file and dimensionNode 
			JavaSDM.ensure(!file.equals(dimensionNode));

			// check isomorphic binding between objects nodeToBoard and dimensionNode 
			JavaSDM.ensure(!nodeToBoard.equals(dimensionNode));

			// check isomorphic binding between objects nodeToFloor and dimensionNode 
			JavaSDM.ensure(!nodeToFloor.equals(dimensionNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topLeftFloor and dimensionNode 
			JavaSDM.ensure(!topLeftFloor.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects nodeToBoard and file 
			JavaSDM.ensure(!nodeToBoard.equals(file));

			// check isomorphic binding between objects nodeToFloor and file 
			JavaSDM.ensure(!nodeToFloor.equals(file));

			// check isomorphic binding between objects rowCountNode and file 
			JavaSDM.ensure(!rowCountNode.equals(file));

			// check isomorphic binding between objects rowsNode and file 
			JavaSDM.ensure(!rowsNode.equals(file));

			// check isomorphic binding between objects topLeftFloor and file 
			JavaSDM.ensure(!topLeftFloor.equals(file));

			// check isomorphic binding between objects topRowLeftColumnNode and file 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(file));

			// check isomorphic binding between objects topRowNode and file 
			JavaSDM.ensure(!topRowNode.equals(file));

			// check isomorphic binding between objects nodeToFloor and nodeToBoard 
			JavaSDM.ensure(!nodeToFloor.equals(nodeToBoard));

			// check isomorphic binding between objects rowCountNode and nodeToBoard 
			JavaSDM.ensure(!rowCountNode.equals(nodeToBoard));

			// check isomorphic binding between objects rowsNode and nodeToBoard 
			JavaSDM.ensure(!rowsNode.equals(nodeToBoard));

			// check isomorphic binding between objects topLeftFloor and nodeToBoard 
			JavaSDM.ensure(!topLeftFloor.equals(nodeToBoard));

			// check isomorphic binding between objects topRowLeftColumnNode and nodeToBoard 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(nodeToBoard));

			// check isomorphic binding between objects topRowNode and nodeToBoard 
			JavaSDM.ensure(!topRowNode.equals(nodeToBoard));

			// check isomorphic binding between objects rowCountNode and nodeToFloor 
			JavaSDM.ensure(!rowCountNode.equals(nodeToFloor));

			// check isomorphic binding between objects rowsNode and nodeToFloor 
			JavaSDM.ensure(!rowsNode.equals(nodeToFloor));

			// check isomorphic binding between objects topLeftFloor and nodeToFloor 
			JavaSDM.ensure(!topLeftFloor.equals(nodeToFloor));

			// check isomorphic binding between objects topRowLeftColumnNode and nodeToFloor 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(nodeToFloor));

			// check isomorphic binding between objects topRowNode and nodeToFloor 
			JavaSDM.ensure(!topRowNode.equals(nodeToFloor));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topLeftFloor and rowCountNode 
			JavaSDM.ensure(!topLeftFloor.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topLeftFloor and rowsNode 
			JavaSDM.ensure(!topLeftFloor.equals(rowsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and topLeftFloor 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(topLeftFloor));

			// check isomorphic binding between objects topRowNode and topLeftFloor 
			JavaSDM.ensure(!topRowNode.equals(topLeftFloor));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// create object file__rootNode__boardSpecNode
			file__rootNode__boardSpecNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object rowsNode__children__rowCountNode
			rowsNode__children__rowCountNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object dimensionNode__children__rowsNode
			dimensionNode__children__rowsNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardSpecNode__children__boardNode
			boardSpecNode__children__boardNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToBoard__source__boardNode
			nodeToBoard__source__boardNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardNode__children__topRowNode
			boardNode__children__topRowNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToBoard__target__board
			nodeToBoard__target__board = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object board__floors__topLeftFloor
			board__floors__topLeftFloor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToFloor__source__topRowLeftColumnNode
			nodeToFloor__source__topRowLeftColumnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object dimensionNode__children__colsNode
			dimensionNode__children__colsNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToFloor__target__topLeftFloor
			nodeToFloor__target__topLeftFloor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object colsNode__children__columnCountNode
			colsNode__children__columnCountNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object topRowNode__children__topRowLeftColumnNode
			topRowNode__children__topRowLeftColumnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardSpecNode__children__dimensionNode
			boardSpecNode__children__dimensionNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// assign attribute file__rootNode__boardSpecNode
			file__rootNode__boardSpecNode.setName("rootNode");
			// assign attribute boardSpecNode__children__boardNode
			boardSpecNode__children__boardNode.setName("children");
			// assign attribute boardSpecNode__children__dimensionNode
			boardSpecNode__children__dimensionNode.setName("children");
			// assign attribute dimensionNode__children__rowsNode
			dimensionNode__children__rowsNode.setName("children");
			// assign attribute dimensionNode__children__colsNode
			dimensionNode__children__colsNode.setName("children");
			// assign attribute rowsNode__children__rowCountNode
			rowsNode__children__rowCountNode.setName("children");
			// assign attribute colsNode__children__columnCountNode
			colsNode__children__columnCountNode.setName("children");
			// assign attribute boardNode__children__topRowNode
			boardNode__children__topRowNode.setName("children");
			// assign attribute topRowNode__children__topRowLeftColumnNode
			topRowNode__children__topRowLeftColumnNode.setName("children");
			// assign attribute nodeToBoard__source__boardNode
			nodeToBoard__source__boardNode.setName("source");
			// assign attribute nodeToBoard__target__board
			nodeToBoard__target__board.setName("target");
			// assign attribute nodeToFloor__source__topRowLeftColumnNode
			nodeToFloor__source__topRowLeftColumnNode.setName("source");
			// assign attribute nodeToFloor__target__topLeftFloor
			nodeToFloor__target__topLeftFloor.setName("target");
			// assign attribute board__floors__topLeftFloor
			board__floors__topLeftFloor.setName("floors");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					file__rootNode__boardSpecNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowsNode__children__rowCountNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode__children__rowsNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode__children__boardNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard__source__boardNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode__children__topRowNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard__target__board, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board__floors__topLeftFloor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__source__topRowLeftColumnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode__children__colsNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__target__topLeftFloor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					colsNode__children__columnCountNode, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowNode__children__topRowLeftColumnNode,
					"translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode__children__dimensionNode, "translatedEdges");

			// create link
			file__rootNode__boardSpecNode.setSrc(file);

			// create link
			boardSpecNode__children__boardNode.setSrc(boardSpecNode);

			// create link
			boardSpecNode__children__dimensionNode.setSrc(boardSpecNode);

			// create link
			file__rootNode__boardSpecNode.setTrg(boardSpecNode);

			// create link
			boardSpecNode__children__dimensionNode.setTrg(dimensionNode);

			// create link
			dimensionNode__children__colsNode.setSrc(dimensionNode);

			// create link
			dimensionNode__children__rowsNode.setSrc(dimensionNode);

			// create link
			dimensionNode__children__rowsNode.setTrg(rowsNode);

			// create link
			rowsNode__children__rowCountNode.setSrc(rowsNode);

			// create link
			dimensionNode__children__colsNode.setTrg(colsNode);

			// create link
			colsNode__children__columnCountNode.setSrc(colsNode);

			// create link
			rowsNode__children__rowCountNode.setTrg(rowCountNode);

			// create link
			colsNode__children__columnCountNode.setTrg(columnCountNode);

			// create link
			boardNode__children__topRowNode.setSrc(boardNode);

			// create link
			boardSpecNode__children__boardNode.setTrg(boardNode);

			// create link
			nodeToBoard__source__boardNode.setTrg(boardNode);

			// create link
			topRowNode__children__topRowLeftColumnNode.setSrc(topRowNode);

			// create link
			boardNode__children__topRowNode.setTrg(topRowNode);

			// create link
			nodeToFloor__source__topRowLeftColumnNode
					.setTrg(topRowLeftColumnNode);

			// create link
			topRowNode__children__topRowLeftColumnNode
					.setTrg(topRowLeftColumnNode);

			// create link
			nodeToBoard__target__board.setSrc(nodeToBoard);

			// create link
			nodeToBoard__source__boardNode.setSrc(nodeToBoard);

			// create link
			nodeToFloor__source__topRowLeftColumnNode.setSrc(nodeToFloor);

			// create link
			nodeToFloor__target__topLeftFloor.setSrc(nodeToFloor);

			// create link
			board__floors__topLeftFloor.setSrc(board);

			// create link
			nodeToBoard__target__board.setTrg(board);

			// create link
			nodeToFloor__target__topLeftFloor.setTrg(topLeftFloor);

			// create link
			board__floors__topLeftFloor.setTrg(topLeftFloor);

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
	public boolean isAppropriate_FWD(Match match, File file,
			Node boardSpecNode, Node dimensionNode, Node rowsNode,
			Node colsNode, Node rowCountNode, Node columnCountNode,
			Node boardNode, Node topRowNode, Node topRowLeftColumnNode) {
		boolean fujaba__Success = false;
		Edge file__rootNode__boardSpecNode = null;
		Edge boardNode__children__topRowNode = null;
		Edge colsNode__children__columnCountNode = null;
		Edge boardSpecNode__children__boardNode = null;
		Edge dimensionNode__children__rowsNode = null;
		Edge dimensionNode__children__colsNode = null;
		Edge boardSpecNode__children__dimensionNode = null;
		Edge topRowNode__children__topRowLeftColumnNode = null;
		Edge rowsNode__children__rowCountNode = null;

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
				// check object boardSpecNode is really bound
				JavaSDM.ensure(boardSpecNode != null);
				// check object colsNode is really bound
				JavaSDM.ensure(colsNode != null);
				// check object columnCountNode is really bound
				JavaSDM.ensure(columnCountNode != null);
				// check object dimensionNode is really bound
				JavaSDM.ensure(dimensionNode != null);
				// check object file is really bound
				JavaSDM.ensure(file != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object rowCountNode is really bound
				JavaSDM.ensure(rowCountNode != null);
				// check object rowsNode is really bound
				JavaSDM.ensure(rowsNode != null);
				// check object topRowLeftColumnNode is really bound
				JavaSDM.ensure(topRowLeftColumnNode != null);
				// check object topRowNode is really bound
				JavaSDM.ensure(topRowNode != null);
				// check isomorphic binding between objects boardSpecNode and boardNode 
				JavaSDM.ensure(!boardSpecNode.equals(boardNode));

				// check isomorphic binding between objects colsNode and boardNode 
				JavaSDM.ensure(!colsNode.equals(boardNode));

				// check isomorphic binding between objects columnCountNode and boardNode 
				JavaSDM.ensure(!columnCountNode.equals(boardNode));

				// check isomorphic binding between objects dimensionNode and boardNode 
				JavaSDM.ensure(!dimensionNode.equals(boardNode));

				// check isomorphic binding between objects rowCountNode and boardNode 
				JavaSDM.ensure(!rowCountNode.equals(boardNode));

				// check isomorphic binding between objects rowsNode and boardNode 
				JavaSDM.ensure(!rowsNode.equals(boardNode));

				// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

				// check isomorphic binding between objects topRowNode and boardNode 
				JavaSDM.ensure(!topRowNode.equals(boardNode));

				// check isomorphic binding between objects colsNode and boardSpecNode 
				JavaSDM.ensure(!colsNode.equals(boardSpecNode));

				// check isomorphic binding between objects columnCountNode and boardSpecNode 
				JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

				// check isomorphic binding between objects dimensionNode and boardSpecNode 
				JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

				// check isomorphic binding between objects rowCountNode and boardSpecNode 
				JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

				// check isomorphic binding between objects rowsNode and boardSpecNode 
				JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

				// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

				// check isomorphic binding between objects topRowNode and boardSpecNode 
				JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

				// check isomorphic binding between objects columnCountNode and colsNode 
				JavaSDM.ensure(!columnCountNode.equals(colsNode));

				// check isomorphic binding between objects dimensionNode and colsNode 
				JavaSDM.ensure(!dimensionNode.equals(colsNode));

				// check isomorphic binding between objects rowCountNode and colsNode 
				JavaSDM.ensure(!rowCountNode.equals(colsNode));

				// check isomorphic binding between objects rowsNode and colsNode 
				JavaSDM.ensure(!rowsNode.equals(colsNode));

				// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

				// check isomorphic binding between objects topRowNode and colsNode 
				JavaSDM.ensure(!topRowNode.equals(colsNode));

				// check isomorphic binding between objects dimensionNode and columnCountNode 
				JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

				// check isomorphic binding between objects rowCountNode and columnCountNode 
				JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

				// check isomorphic binding between objects rowsNode and columnCountNode 
				JavaSDM.ensure(!rowsNode.equals(columnCountNode));

				// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

				// check isomorphic binding between objects topRowNode and columnCountNode 
				JavaSDM.ensure(!topRowNode.equals(columnCountNode));

				// check isomorphic binding between objects rowCountNode and dimensionNode 
				JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

				// check isomorphic binding between objects rowsNode and dimensionNode 
				JavaSDM.ensure(!rowsNode.equals(dimensionNode));

				// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

				// check isomorphic binding between objects topRowNode and dimensionNode 
				JavaSDM.ensure(!topRowNode.equals(dimensionNode));

				// check isomorphic binding between objects rowsNode and rowCountNode 
				JavaSDM.ensure(!rowsNode.equals(rowCountNode));

				// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

				// check isomorphic binding between objects topRowNode and rowCountNode 
				JavaSDM.ensure(!topRowNode.equals(rowCountNode));

				// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
				JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

				// check isomorphic binding between objects topRowNode and rowsNode 
				JavaSDM.ensure(!topRowNode.equals(rowsNode));

				// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
				JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

				// create object file__rootNode__boardSpecNode
				file__rootNode__boardSpecNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object boardNode__children__topRowNode
				boardNode__children__topRowNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object colsNode__children__columnCountNode
				colsNode__children__columnCountNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object boardSpecNode__children__boardNode
				boardSpecNode__children__boardNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object dimensionNode__children__rowsNode
				dimensionNode__children__rowsNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object dimensionNode__children__colsNode
				dimensionNode__children__colsNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object boardSpecNode__children__dimensionNode
				boardSpecNode__children__dimensionNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object topRowNode__children__topRowLeftColumnNode
				topRowNode__children__topRowLeftColumnNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// create object rowsNode__children__rowCountNode
				rowsNode__children__rowCountNode = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute file__rootNode__boardSpecNode
				file__rootNode__boardSpecNode.setName("rootNode");
				// assign attribute boardSpecNode__children__boardNode
				boardSpecNode__children__boardNode.setName("children");
				// assign attribute boardSpecNode__children__dimensionNode
				boardSpecNode__children__dimensionNode.setName("children");
				// assign attribute dimensionNode__children__rowsNode
				dimensionNode__children__rowsNode.setName("children");
				// assign attribute dimensionNode__children__colsNode
				dimensionNode__children__colsNode.setName("children");
				// assign attribute rowsNode__children__rowCountNode
				rowsNode__children__rowCountNode.setName("children");
				// assign attribute colsNode__children__columnCountNode
				colsNode__children__columnCountNode.setName("children");
				// assign attribute boardNode__children__topRowNode
				boardNode__children__topRowNode.setName("children");
				// assign attribute topRowNode__children__topRowLeftColumnNode
				topRowNode__children__topRowLeftColumnNode.setName("children");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						file__rootNode__boardSpecNode, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardNode__children__topRowNode, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						colsNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						colsNode__children__columnCountNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						rowCountNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardSpecNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						columnCountNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardSpecNode__children__boardNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						dimensionNode__children__rowsNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						rowsNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						topRowNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						topRowLeftColumnNode, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						dimensionNode__children__colsNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						boardSpecNode__children__dimensionNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						topRowNode__children__topRowLeftColumnNode,
						"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						file, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil
						.addOppositeReference(match,
								rowsNode__children__rowCountNode,
								"toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						dimensionNode, "toBeTranslatedElements");

				// create link
				file__rootNode__boardSpecNode.setSrc(file);

				// create link
				boardSpecNode__children__boardNode.setSrc(boardSpecNode);

				// create link
				boardSpecNode__children__dimensionNode.setSrc(boardSpecNode);

				// create link
				file__rootNode__boardSpecNode.setTrg(boardSpecNode);

				// create link
				dimensionNode__children__colsNode.setSrc(dimensionNode);

				// create link
				dimensionNode__children__rowsNode.setSrc(dimensionNode);

				// create link
				boardSpecNode__children__dimensionNode.setTrg(dimensionNode);

				// create link
				dimensionNode__children__rowsNode.setTrg(rowsNode);

				// create link
				rowsNode__children__rowCountNode.setSrc(rowsNode);

				// create link
				dimensionNode__children__colsNode.setTrg(colsNode);

				// create link
				colsNode__children__columnCountNode.setSrc(colsNode);

				// create link
				rowsNode__children__rowCountNode.setTrg(rowCountNode);

				// create link
				colsNode__children__columnCountNode.setTrg(columnCountNode);

				// create link
				boardSpecNode__children__boardNode.setTrg(boardNode);

				// create link
				boardNode__children__topRowNode.setSrc(boardNode);

				// create link
				topRowNode__children__topRowLeftColumnNode.setSrc(topRowNode);

				// create link
				boardNode__children__topRowNode.setTrg(topRowNode);

				// create link
				topRowNode__children__topRowLeftColumnNode
						.setTrg(topRowLeftColumnNode);

				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object match is really bound
				JavaSDM.ensure(match != null);
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("file", file);
			match.registerObject("boardSpecNode", boardSpecNode);
			match.registerObject("dimensionNode", dimensionNode);
			match.registerObject("rowsNode", rowsNode);
			match.registerObject("colsNode", colsNode);
			match.registerObject("rowCountNode", rowCountNode);
			match.registerObject("columnCountNode", columnCountNode);
			match.registerObject("boardNode", boardNode);
			match.registerObject("topRowNode", topRowNode);
			match.registerObject("topRowLeftColumnNode", topRowLeftColumnNode);

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
		Node boardSpecNode = null;
		Node colsNode = null;
		Node columnCountNode = null;
		Node dimensionNode = null;
		File file = null;
		Node rowCountNode = null;
		Node rowsNode = null;
		Node topRowLeftColumnNode = null;
		Node topRowNode = null;
		IsApplicableMatch isApplicableMatch = null;

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
			ruleresult.setRule("BoardNodeToBoardRule");

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
			_TmpObject = (match.getObject("boardSpecNode"));

			// ensure correct type and really bound of object boardSpecNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			boardSpecNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("colsNode"));

			// ensure correct type and really bound of object colsNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			colsNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("columnCountNode"));

			// ensure correct type and really bound of object columnCountNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			columnCountNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("dimensionNode"));

			// ensure correct type and really bound of object dimensionNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			dimensionNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("file"));

			// ensure correct type and really bound of object file
			JavaSDM.ensure(_TmpObject instanceof File);
			file = (File) _TmpObject;
			_TmpObject = (match.getObject("rowCountNode"));

			// ensure correct type and really bound of object rowCountNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowCountNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("rowsNode"));

			// ensure correct type and really bound of object rowsNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			rowsNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("topRowLeftColumnNode"));

			// ensure correct type and really bound of object topRowLeftColumnNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			topRowLeftColumnNode = (Node) _TmpObject;
			_TmpObject = (match.getObject("topRowNode"));

			// ensure correct type and really bound of object topRowNode
			JavaSDM.ensure(_TmpObject instanceof Node);
			topRowNode = (Node) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// attribute condition
			JavaSDM.ensure(dimensionNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowsNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(columnCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowLeftColumnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(colsNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(boardNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardSpecNode.getName(),
					"BOARD_SPEC") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(colsNode.getName(), "COLS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(
					topRowLeftColumnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(dimensionNode.getName(),
					"DIMENSIONS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(topRowNode.getName(), "ROW") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowsNode.getName(), "ROWS") == 0);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// check object file is really bound
			JavaSDM.ensure(file != null);
			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// check link children from topRowNode to boardNode
			JavaSDM.ensure(boardNode.equals(topRowNode.getParentNode()));

			// check link children from boardNode to boardSpecNode
			JavaSDM.ensure(boardSpecNode.equals(boardNode.getParentNode()));

			// check link children from dimensionNode to boardSpecNode
			JavaSDM.ensure(boardSpecNode.equals(dimensionNode.getParentNode()));

			// check link children from columnCountNode to colsNode
			JavaSDM.ensure(colsNode.equals(columnCountNode.getParentNode()));

			// check link children from colsNode to dimensionNode
			JavaSDM.ensure(dimensionNode.equals(colsNode.getParentNode()));

			// check link children from rowsNode to dimensionNode
			JavaSDM.ensure(dimensionNode.equals(rowsNode.getParentNode()));

			// check link children from rowCountNode to rowsNode
			JavaSDM.ensure(rowsNode.equals(rowCountNode.getParentNode()));

			// check link children from topRowLeftColumnNode to topRowNode
			JavaSDM.ensure(topRowNode.equals(topRowLeftColumnNode
					.getParentNode()));

			// check link rootNode from boardSpecNode to file
			JavaSDM.ensure(file.equals(boardSpecNode.getFile()));

			// attribute condition
			JavaSDM.ensure(columnCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowsNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(topRowLeftColumnNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(rowCountNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(dimensionNode.getIndex() == 0);

			// attribute condition
			JavaSDM.ensure(boardNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(colsNode.getIndex() == 1);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardNode.getName(), "BOARD") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(boardSpecNode.getName(),
					"BOARD_SPEC") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(colsNode.getName(), "COLS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(
					topRowLeftColumnNode.getName(), "COLUMN") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(dimensionNode.getName(),
					"DIMENSIONS") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(topRowNode.getName(), "ROW") == 0);

			// attribute condition
			JavaSDM.ensure(JavaSDM.stringCompare(rowsNode.getName(), "ROWS") == 0);

			// create object isApplicableMatch
			isApplicableMatch = TGGRuntimeFactory.eINSTANCE
					.createIsApplicableMatch();

			// statement node 'solve CSP'
			// Create CSP
			CSP csp = CspFactory.eINSTANCE.createCSP();
			isApplicableMatch.getAttributeInfo().add(csp);

			// Create literals

			// Create attribute variables
			Variable<String> var_rowCountNode_name = CspFactory.eINSTANCE
					.createVariable("rowCountNode.name", true, csp);
			var_rowCountNode_name.setValue(rowCountNode.getName());
			Variable<String> var_columnCountNode_name = CspFactory.eINSTANCE
					.createVariable("columnCountNode.name", true, csp);
			var_columnCountNode_name.setValue(columnCountNode.getName());

			// Create explicit parameters

			// Create unbound variables
			Variable<Number> var_board_height = CspFactory.eINSTANCE
					.createVariable("board.height", csp);
			Variable<Number> var_board_width = CspFactory.eINSTANCE
					.createVariable("board.width", csp);

			// Create constraints
			StringToNumber stringToNumber = new StringToNumber();
			StringToNumber stringToNumber_0 = new StringToNumber();

			csp.getConstraints().add(stringToNumber);
			csp.getConstraints().add(stringToNumber_0);

			// Solve CSP
			stringToNumber.setRuleName("");
			stringToNumber.solve(var_rowCountNode_name, var_board_height);
			stringToNumber_0.setRuleName("");
			stringToNumber_0.solve(var_columnCountNode_name, var_board_width);

			// Snapshot pattern match on which CSP is solved
			isApplicableMatch.registerObject("file", file);
			isApplicableMatch.registerObject("boardSpecNode", boardSpecNode);
			isApplicableMatch.registerObject("dimensionNode", dimensionNode);
			isApplicableMatch.registerObject("rowsNode", rowsNode);
			isApplicableMatch.registerObject("colsNode", colsNode);
			isApplicableMatch.registerObject("rowCountNode", rowCountNode);
			isApplicableMatch
					.registerObject("columnCountNode", columnCountNode);
			isApplicableMatch.registerObject("boardNode", boardNode);
			isApplicableMatch.registerObject("topRowNode", topRowNode);
			isApplicableMatch.registerObject("topRowLeftColumnNode",
					topRowLeftColumnNode);

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
					ruleresult.getIsApplicableMatch().add(isApplicableMatch);

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
		Floor topLeftFloor = null;
		Iterator fujaba__IterIsApplicableMatchToCsp = null;
		CSP csp = null;
		File file = null;
		Node boardSpecNode = null;
		Node boardNode = null;
		Node dimensionNode = null;
		Node colsNode = null;
		Node rowsNode = null;
		Node rowCountNode = null;
		Node columnCountNode = null;
		Node topRowNode = null;
		NodeToBoard nodeToBoard = null;
		Node topRowLeftColumnNode = null;
		NodeToFloor nodeToFloor = null;
		PerformRuleResult ruleresult = null;
		Edge colsNode__children__columnCountNode = null;
		Edge nodeToFloor__target__topLeftFloor = null;
		Edge topRowNode__children__topRowLeftColumnNode = null;
		Edge dimensionNode__children__colsNode = null;
		Edge nodeToBoard__source__boardNode = null;
		Edge dimensionNode__children__rowsNode = null;
		Edge nodeToBoard__target__board = null;
		Edge rowsNode__children__rowCountNode = null;
		Edge board__floors__topLeftFloor = null;
		Edge boardSpecNode__children__dimensionNode = null;
		Edge file__rootNode__boardSpecNode = null;
		Edge boardSpecNode__children__boardNode = null;
		Edge nodeToFloor__source__topRowLeftColumnNode = null;
		Edge boardNode__children__topRowNode = null;

		// story node 'perform transformation'
		try {
			fujaba__Success = false;

			_TmpObject = (isApplicableMatch.getObject("board"));

			// ensure correct type and really bound of object board
			JavaSDM.ensure(_TmpObject instanceof Board);
			board = (Board) _TmpObject;
			_TmpObject = (isApplicableMatch.getObject("topLeftFloor"));

			// ensure correct type and really bound of object topLeftFloor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			topLeftFloor = (Floor) _TmpObject;
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
			// attribute condition
			JavaSDM.ensure(topLeftFloor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getRow() == 0);

			// create object file
			file = MocaTreeFactory.eINSTANCE.createFile();

			// create object boardSpecNode
			boardSpecNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object boardNode
			boardNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object dimensionNode
			dimensionNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object colsNode
			colsNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object rowsNode
			rowsNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object rowCountNode
			rowCountNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object columnCountNode
			columnCountNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object topRowNode
			topRowNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object nodeToBoard
			nodeToBoard = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToBoard();

			// create object topRowLeftColumnNode
			topRowLeftColumnNode = MocaTreeFactory.eINSTANCE.createNode();

			// create object nodeToFloor
			nodeToFloor = SokobanCodeAdapterFactory.eINSTANCE
					.createNodeToFloor();

			// assign attribute boardSpecNode
			boardSpecNode.setName("BOARD_SPEC");
			// assign attribute dimensionNode
			dimensionNode.setName("DIMENSIONS");
			// assign attribute dimensionNode
			dimensionNode.setIndex(0);
			// assign attribute rowsNode
			rowsNode.setName("ROWS");
			// assign attribute rowsNode
			rowsNode.setIndex(0);
			// assign attribute colsNode
			colsNode.setName("COLS");
			// assign attribute colsNode
			colsNode.setIndex(1);
			// assign attribute rowCountNode
			rowCountNode.setIndex(0);
			// assign attribute rowCountNode
			rowCountNode.setName((java.lang.String) csp.getAttributeVariable(
					"rowCountNode", "name").getValue());
			// assign attribute columnCountNode
			columnCountNode.setIndex(0);
			// assign attribute columnCountNode
			columnCountNode
					.setName((java.lang.String) csp.getAttributeVariable(
							"columnCountNode", "name").getValue());
			// assign attribute boardNode
			boardNode.setName("BOARD");
			// assign attribute boardNode
			boardNode.setIndex(1);
			// assign attribute topRowNode
			topRowNode.setName("ROW");
			// assign attribute topRowNode
			topRowNode.setIndex(0);
			// assign attribute topRowLeftColumnNode
			topRowLeftColumnNode.setName("COLUMN");
			// assign attribute topRowLeftColumnNode
			topRowLeftColumnNode.setIndex(0);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToBoard,
					board, "target");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(nodeToFloor,
					topLeftFloor, "target");

			// create link
			boardSpecNode.setFile(file);

			// create link
			boardNode.setParentNode(boardSpecNode);

			// create link
			dimensionNode.setParentNode(boardSpecNode);

			// create link
			colsNode.setParentNode(dimensionNode);

			// create link
			rowsNode.setParentNode(dimensionNode);

			// create link
			rowCountNode.setParentNode(rowsNode);

			// create link
			columnCountNode.setParentNode(colsNode);

			// create link
			topRowNode.setParentNode(boardNode);

			// create link
			nodeToBoard.setSource(boardNode);

			// create link
			topRowLeftColumnNode.setParentNode(topRowNode);

			// create link
			nodeToFloor.setSource(topRowLeftColumnNode);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'collect translated elements'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);
			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// check object file is really bound
			JavaSDM.ensure(file != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// create object ruleresult
			ruleresult = TGGRuntimeFactory.eINSTANCE.createPerformRuleResult();

			// assign attribute ruleresult
			ruleresult.setSuccess(true);

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowCountNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topLeftFloor, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					columnCountNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowsNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					colsNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board, "translatedElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard, "createdLinkElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowLeftColumnNode, "createdElements");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					file, "createdElements");
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
			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// check object file is really bound
			JavaSDM.ensure(file != null);
			// check object nodeToBoard is really bound
			JavaSDM.ensure(nodeToBoard != null);
			// check object nodeToFloor is really bound
			JavaSDM.ensure(nodeToFloor != null);
			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// check object ruleresult is really bound
			JavaSDM.ensure(ruleresult != null);
			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// check isomorphic binding between objects boardNode and board 
			JavaSDM.ensure(!boardNode.equals(board));

			// check isomorphic binding between objects boardSpecNode and board 
			JavaSDM.ensure(!boardSpecNode.equals(board));

			// check isomorphic binding between objects colsNode and board 
			JavaSDM.ensure(!colsNode.equals(board));

			// check isomorphic binding between objects columnCountNode and board 
			JavaSDM.ensure(!columnCountNode.equals(board));

			// check isomorphic binding between objects dimensionNode and board 
			JavaSDM.ensure(!dimensionNode.equals(board));

			// check isomorphic binding between objects file and board 
			JavaSDM.ensure(!file.equals(board));

			// check isomorphic binding between objects nodeToBoard and board 
			JavaSDM.ensure(!nodeToBoard.equals(board));

			// check isomorphic binding between objects nodeToFloor and board 
			JavaSDM.ensure(!nodeToFloor.equals(board));

			// check isomorphic binding between objects rowCountNode and board 
			JavaSDM.ensure(!rowCountNode.equals(board));

			// check isomorphic binding between objects rowsNode and board 
			JavaSDM.ensure(!rowsNode.equals(board));

			// check isomorphic binding between objects topLeftFloor and board 
			JavaSDM.ensure(!topLeftFloor.equals(board));

			// check isomorphic binding between objects topRowLeftColumnNode and board 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(board));

			// check isomorphic binding between objects topRowNode and board 
			JavaSDM.ensure(!topRowNode.equals(board));

			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardNode 
			JavaSDM.ensure(!colsNode.equals(boardNode));

			// check isomorphic binding between objects columnCountNode and boardNode 
			JavaSDM.ensure(!columnCountNode.equals(boardNode));

			// check isomorphic binding between objects dimensionNode and boardNode 
			JavaSDM.ensure(!dimensionNode.equals(boardNode));

			// check isomorphic binding between objects file and boardNode 
			JavaSDM.ensure(!file.equals(boardNode));

			// check isomorphic binding between objects nodeToBoard and boardNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardNode));

			// check isomorphic binding between objects nodeToFloor and boardNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardNode));

			// check isomorphic binding between objects rowCountNode and boardNode 
			JavaSDM.ensure(!rowCountNode.equals(boardNode));

			// check isomorphic binding between objects rowsNode and boardNode 
			JavaSDM.ensure(!rowsNode.equals(boardNode));

			// check isomorphic binding between objects topLeftFloor and boardNode 
			JavaSDM.ensure(!topLeftFloor.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects file and boardSpecNode 
			JavaSDM.ensure(!file.equals(boardSpecNode));

			// check isomorphic binding between objects nodeToBoard and boardSpecNode 
			JavaSDM.ensure(!nodeToBoard.equals(boardSpecNode));

			// check isomorphic binding between objects nodeToFloor and boardSpecNode 
			JavaSDM.ensure(!nodeToFloor.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// check isomorphic binding between objects topLeftFloor and boardSpecNode 
			JavaSDM.ensure(!topLeftFloor.equals(boardSpecNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects file and colsNode 
			JavaSDM.ensure(!file.equals(colsNode));

			// check isomorphic binding between objects nodeToBoard and colsNode 
			JavaSDM.ensure(!nodeToBoard.equals(colsNode));

			// check isomorphic binding between objects nodeToFloor and colsNode 
			JavaSDM.ensure(!nodeToFloor.equals(colsNode));

			// check isomorphic binding between objects rowCountNode and colsNode 
			JavaSDM.ensure(!rowCountNode.equals(colsNode));

			// check isomorphic binding between objects rowsNode and colsNode 
			JavaSDM.ensure(!rowsNode.equals(colsNode));

			// check isomorphic binding between objects topLeftFloor and colsNode 
			JavaSDM.ensure(!topLeftFloor.equals(colsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(colsNode));

			// check isomorphic binding between objects topRowNode and colsNode 
			JavaSDM.ensure(!topRowNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// check isomorphic binding between objects file and columnCountNode 
			JavaSDM.ensure(!file.equals(columnCountNode));

			// check isomorphic binding between objects nodeToBoard and columnCountNode 
			JavaSDM.ensure(!nodeToBoard.equals(columnCountNode));

			// check isomorphic binding between objects nodeToFloor and columnCountNode 
			JavaSDM.ensure(!nodeToFloor.equals(columnCountNode));

			// check isomorphic binding between objects rowCountNode and columnCountNode 
			JavaSDM.ensure(!rowCountNode.equals(columnCountNode));

			// check isomorphic binding between objects rowsNode and columnCountNode 
			JavaSDM.ensure(!rowsNode.equals(columnCountNode));

			// check isomorphic binding between objects topLeftFloor and columnCountNode 
			JavaSDM.ensure(!topLeftFloor.equals(columnCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(columnCountNode));

			// check isomorphic binding between objects topRowNode and columnCountNode 
			JavaSDM.ensure(!topRowNode.equals(columnCountNode));

			// check isomorphic binding between objects file and dimensionNode 
			JavaSDM.ensure(!file.equals(dimensionNode));

			// check isomorphic binding between objects nodeToBoard and dimensionNode 
			JavaSDM.ensure(!nodeToBoard.equals(dimensionNode));

			// check isomorphic binding between objects nodeToFloor and dimensionNode 
			JavaSDM.ensure(!nodeToFloor.equals(dimensionNode));

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// check isomorphic binding between objects topLeftFloor and dimensionNode 
			JavaSDM.ensure(!topLeftFloor.equals(dimensionNode));

			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

			// check isomorphic binding between objects topRowNode and dimensionNode 
			JavaSDM.ensure(!topRowNode.equals(dimensionNode));

			// check isomorphic binding between objects nodeToBoard and file 
			JavaSDM.ensure(!nodeToBoard.equals(file));

			// check isomorphic binding between objects nodeToFloor and file 
			JavaSDM.ensure(!nodeToFloor.equals(file));

			// check isomorphic binding between objects rowCountNode and file 
			JavaSDM.ensure(!rowCountNode.equals(file));

			// check isomorphic binding between objects rowsNode and file 
			JavaSDM.ensure(!rowsNode.equals(file));

			// check isomorphic binding between objects topLeftFloor and file 
			JavaSDM.ensure(!topLeftFloor.equals(file));

			// check isomorphic binding between objects topRowLeftColumnNode and file 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(file));

			// check isomorphic binding between objects topRowNode and file 
			JavaSDM.ensure(!topRowNode.equals(file));

			// check isomorphic binding between objects nodeToFloor and nodeToBoard 
			JavaSDM.ensure(!nodeToFloor.equals(nodeToBoard));

			// check isomorphic binding between objects rowCountNode and nodeToBoard 
			JavaSDM.ensure(!rowCountNode.equals(nodeToBoard));

			// check isomorphic binding between objects rowsNode and nodeToBoard 
			JavaSDM.ensure(!rowsNode.equals(nodeToBoard));

			// check isomorphic binding between objects topLeftFloor and nodeToBoard 
			JavaSDM.ensure(!topLeftFloor.equals(nodeToBoard));

			// check isomorphic binding between objects topRowLeftColumnNode and nodeToBoard 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(nodeToBoard));

			// check isomorphic binding between objects topRowNode and nodeToBoard 
			JavaSDM.ensure(!topRowNode.equals(nodeToBoard));

			// check isomorphic binding between objects rowCountNode and nodeToFloor 
			JavaSDM.ensure(!rowCountNode.equals(nodeToFloor));

			// check isomorphic binding between objects rowsNode and nodeToFloor 
			JavaSDM.ensure(!rowsNode.equals(nodeToFloor));

			// check isomorphic binding between objects topLeftFloor and nodeToFloor 
			JavaSDM.ensure(!topLeftFloor.equals(nodeToFloor));

			// check isomorphic binding between objects topRowLeftColumnNode and nodeToFloor 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(nodeToFloor));

			// check isomorphic binding between objects topRowNode and nodeToFloor 
			JavaSDM.ensure(!topRowNode.equals(nodeToFloor));

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// check isomorphic binding between objects topLeftFloor and rowCountNode 
			JavaSDM.ensure(!topLeftFloor.equals(rowCountNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowCountNode));

			// check isomorphic binding between objects topRowNode and rowCountNode 
			JavaSDM.ensure(!topRowNode.equals(rowCountNode));

			// check isomorphic binding between objects topLeftFloor and rowsNode 
			JavaSDM.ensure(!topLeftFloor.equals(rowsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(rowsNode));

			// check isomorphic binding between objects topRowNode and rowsNode 
			JavaSDM.ensure(!topRowNode.equals(rowsNode));

			// check isomorphic binding between objects topRowLeftColumnNode and topLeftFloor 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(topLeftFloor));

			// check isomorphic binding between objects topRowNode and topLeftFloor 
			JavaSDM.ensure(!topRowNode.equals(topLeftFloor));

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// create object colsNode__children__columnCountNode
			colsNode__children__columnCountNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToFloor__target__topLeftFloor
			nodeToFloor__target__topLeftFloor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object topRowNode__children__topRowLeftColumnNode
			topRowNode__children__topRowLeftColumnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object dimensionNode__children__colsNode
			dimensionNode__children__colsNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToBoard__source__boardNode
			nodeToBoard__source__boardNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object dimensionNode__children__rowsNode
			dimensionNode__children__rowsNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToBoard__target__board
			nodeToBoard__target__board = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object rowsNode__children__rowCountNode
			rowsNode__children__rowCountNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object board__floors__topLeftFloor
			board__floors__topLeftFloor = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardSpecNode__children__dimensionNode
			boardSpecNode__children__dimensionNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object file__rootNode__boardSpecNode
			file__rootNode__boardSpecNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardSpecNode__children__boardNode
			boardSpecNode__children__boardNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object nodeToFloor__source__topRowLeftColumnNode
			nodeToFloor__source__topRowLeftColumnNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// create object boardNode__children__topRowNode
			boardNode__children__topRowNode = TGGRuntimeFactory.eINSTANCE
					.createEdge();

			// assign attribute file__rootNode__boardSpecNode
			file__rootNode__boardSpecNode.setName("rootNode");
			// assign attribute boardSpecNode__children__boardNode
			boardSpecNode__children__boardNode.setName("children");
			// assign attribute boardSpecNode__children__dimensionNode
			boardSpecNode__children__dimensionNode.setName("children");
			// assign attribute dimensionNode__children__rowsNode
			dimensionNode__children__rowsNode.setName("children");
			// assign attribute dimensionNode__children__colsNode
			dimensionNode__children__colsNode.setName("children");
			// assign attribute rowsNode__children__rowCountNode
			rowsNode__children__rowCountNode.setName("children");
			// assign attribute colsNode__children__columnCountNode
			colsNode__children__columnCountNode.setName("children");
			// assign attribute boardNode__children__topRowNode
			boardNode__children__topRowNode.setName("children");
			// assign attribute topRowNode__children__topRowLeftColumnNode
			topRowNode__children__topRowLeftColumnNode.setName("children");
			// assign attribute nodeToBoard__source__boardNode
			nodeToBoard__source__boardNode.setName("source");
			// assign attribute nodeToBoard__target__board
			nodeToBoard__target__board.setName("target");
			// assign attribute nodeToFloor__source__topRowLeftColumnNode
			nodeToFloor__source__topRowLeftColumnNode.setName("source");
			// assign attribute nodeToFloor__target__topLeftFloor
			nodeToFloor__target__topLeftFloor.setName("target");
			// assign attribute board__floors__topLeftFloor
			board__floors__topLeftFloor.setName("floors");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					colsNode__children__columnCountNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__target__topLeftFloor, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					topRowNode__children__topRowLeftColumnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode__children__colsNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard__source__boardNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					dimensionNode__children__rowsNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToBoard__target__board, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					rowsNode__children__rowCountNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					board__floors__topLeftFloor, "translatedEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode__children__dimensionNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					file__rootNode__boardSpecNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardSpecNode__children__boardNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					nodeToFloor__source__topRowLeftColumnNode, "createdEdges");

			// create link
			org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(ruleresult,
					boardNode__children__topRowNode, "createdEdges");

			// create link
			file__rootNode__boardSpecNode.setSrc(file);

			// create link
			boardSpecNode__children__dimensionNode.setSrc(boardSpecNode);

			// create link
			boardSpecNode__children__boardNode.setSrc(boardSpecNode);

			// create link
			file__rootNode__boardSpecNode.setTrg(boardSpecNode);

			// create link
			boardSpecNode__children__dimensionNode.setTrg(dimensionNode);

			// create link
			dimensionNode__children__colsNode.setSrc(dimensionNode);

			// create link
			dimensionNode__children__rowsNode.setSrc(dimensionNode);

			// create link
			dimensionNode__children__rowsNode.setTrg(rowsNode);

			// create link
			rowsNode__children__rowCountNode.setSrc(rowsNode);

			// create link
			colsNode__children__columnCountNode.setSrc(colsNode);

			// create link
			dimensionNode__children__colsNode.setTrg(colsNode);

			// create link
			rowsNode__children__rowCountNode.setTrg(rowCountNode);

			// create link
			colsNode__children__columnCountNode.setTrg(columnCountNode);

			// create link
			boardNode__children__topRowNode.setSrc(boardNode);

			// create link
			nodeToBoard__source__boardNode.setTrg(boardNode);

			// create link
			boardSpecNode__children__boardNode.setTrg(boardNode);

			// create link
			boardNode__children__topRowNode.setTrg(topRowNode);

			// create link
			topRowNode__children__topRowLeftColumnNode.setSrc(topRowNode);

			// create link
			topRowNode__children__topRowLeftColumnNode
					.setTrg(topRowLeftColumnNode);

			// create link
			nodeToFloor__source__topRowLeftColumnNode
					.setTrg(topRowLeftColumnNode);

			// create link
			nodeToBoard__target__board.setSrc(nodeToBoard);

			// create link
			nodeToBoard__source__boardNode.setSrc(nodeToBoard);

			// create link
			nodeToFloor__target__topLeftFloor.setSrc(nodeToFloor);

			// create link
			nodeToFloor__source__topRowLeftColumnNode.setSrc(nodeToFloor);

			// create link
			board__floors__topLeftFloor.setSrc(board);

			// create link
			nodeToBoard__target__board.setTrg(board);

			// create link
			nodeToFloor__target__topLeftFloor.setTrg(topLeftFloor);

			// create link
			board__floors__topLeftFloor.setTrg(topLeftFloor);

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
			Floor topLeftFloor) {
		boolean fujaba__Success = false;
		Edge board__floors__topLeftFloor = null;

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

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object match is really bound
				JavaSDM.ensure(match != null);
				// check object topLeftFloor is really bound
				JavaSDM.ensure(topLeftFloor != null);
				// create object board__floors__topLeftFloor
				board__floors__topLeftFloor = TGGRuntimeFactory.eINSTANCE
						.createEdge();

				// assign attribute board__floors__topLeftFloor
				board__floors__topLeftFloor.setName("floors");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						board, "toBeTranslatedElements");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						board__floors__topLeftFloor, "toBeTranslatedEdges");

				// create link
				org.moflon.core.utilities.eMoflonEMFUtil.addOppositeReference(match,
						topLeftFloor, "toBeTranslatedElements");

				// create link
				board__floors__topLeftFloor.setSrc(board);

				// create link
				board__floors__topLeftFloor.setTrg(topLeftFloor);

				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// story node 'collect context elements'
			try {
				fujaba__Success = false;

				// check object match is really bound
				JavaSDM.ensure(match != null);
				fujaba__Success = true;
			} catch (JavaSDMException fujaba__InternalException) {
				fujaba__Success = false;
			}

			// statement node 'register objects to match'
			match.registerObject("board", board);
			match.registerObject("topLeftFloor", topLeftFloor);

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
		Floor topLeftFloor = null;
		IsApplicableMatch isApplicableMatch = null;

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
			ruleresult.setRule("BoardNodeToBoardRule");

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
			_TmpObject = (match.getObject("topLeftFloor"));

			// ensure correct type and really bound of object topLeftFloor
			JavaSDM.ensure(_TmpObject instanceof Floor);
			topLeftFloor = (Floor) _TmpObject;
			// check object match is really bound
			JavaSDM.ensure(match != null);
			// attribute condition
			JavaSDM.ensure(topLeftFloor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getRow() == 0);

			fujaba__Success = true;
		} catch (JavaSDMException fujaba__InternalException) {
			fujaba__Success = false;
		}

		// story node 'find context'
		try {
			fujaba__Success = false;

			// check object board is really bound
			JavaSDM.ensure(board != null);
			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// check link floors from topLeftFloor to board
			JavaSDM.ensure(board.equals(topLeftFloor.getBoard()));

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getRow() == 0);

			// create object isApplicableMatch
			isApplicableMatch = TGGRuntimeFactory.eINSTANCE
					.createIsApplicableMatch();

			// statement node 'solve CSP'
			// Create CSP
			CSP csp = CspFactory.eINSTANCE.createCSP();
			isApplicableMatch.getAttributeInfo().add(csp);

			// Create literals

			// Create attribute variables
			Variable<Number> var_board_height = CspFactory.eINSTANCE
					.createVariable("board.height", true, csp);
			var_board_height.setValue(board.getHeight());
			Variable<Number> var_board_width = CspFactory.eINSTANCE
					.createVariable("board.width", true, csp);
			var_board_width.setValue(board.getWidth());

			// Create explicit parameters

			// Create unbound variables
			Variable<String> var_rowCountNode_name = CspFactory.eINSTANCE
					.createVariable("rowCountNode.name", csp);
			Variable<String> var_columnCountNode_name = CspFactory.eINSTANCE
					.createVariable("columnCountNode.name", csp);

			// Create constraints
			StringToNumber stringToNumber = new StringToNumber();
			StringToNumber stringToNumber_0 = new StringToNumber();

			csp.getConstraints().add(stringToNumber);
			csp.getConstraints().add(stringToNumber_0);

			// Solve CSP
			stringToNumber.setRuleName("");
			stringToNumber.solve(var_rowCountNode_name, var_board_height);
			stringToNumber_0.setRuleName("");
			stringToNumber_0.solve(var_columnCountNode_name, var_board_width);

			// Snapshot pattern match on which CSP is solved
			isApplicableMatch.registerObject("board", board);
			isApplicableMatch.registerObject("topLeftFloor", topLeftFloor);

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
					ruleresult.getIsApplicableMatch().add(isApplicableMatch);

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

		return ruleresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsAppropriateRuleResult isAppropriate_FWD_File_0(File file) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardSpecNodeToDimensionNode = null;
		Node dimensionNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		Node boardSpecNode = null;
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

			// check object file is really bound
			JavaSDM.ensure(file != null);
			// bind object
			boardSpecNode = file.getRootNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// iterate to-many link children from boardSpecNode to dimensionNode
							fujaba__Success = false;

							fujaba__IterBoardSpecNodeToDimensionNode = new ArrayList(
									boardSpecNode.getChildren("DIMENSIONS", 0))
									.iterator();

							while (fujaba__IterBoardSpecNodeToDimensionNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterBoardSpecNodeToDimensionNode
											.next();

									// ensure correct type and really bound of object dimensionNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									dimensionNode = (Node) _TmpObject;
									// check isomorphic binding between objects dimensionNode and boardNode 
									JavaSDM.ensure(!dimensionNode
											.equals(boardNode));

									// check isomorphic binding between objects dimensionNode and boardSpecNode 
									JavaSDM.ensure(!dimensionNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects topRowNode and dimensionNode 
									JavaSDM.ensure(!topRowNode
											.equals(dimensionNode));

									// iterate to-many link children from dimensionNode to colsNode
									fujaba__Success = false;

									fujaba__IterDimensionNodeToColsNode = new ArrayList(
											dimensionNode
													.getChildren("COLS", 1))
											.iterator();

									while (fujaba__IterDimensionNodeToColsNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterDimensionNodeToColsNode
													.next();

											// ensure correct type and really bound of object colsNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											colsNode = (Node) _TmpObject;
											// check isomorphic binding between objects colsNode and boardNode 
											JavaSDM.ensure(!colsNode
													.equals(boardNode));

											// check isomorphic binding between objects colsNode and boardSpecNode 
											JavaSDM.ensure(!colsNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects dimensionNode and colsNode 
											JavaSDM.ensure(!dimensionNode
													.equals(colsNode));

											// check isomorphic binding between objects topRowNode and colsNode 
											JavaSDM.ensure(!topRowNode
													.equals(colsNode));

											// iterate to-many link children from colsNode to columnCountNode
											fujaba__Success = false;

											fujaba__IterColsNodeToColumnCountNode = new ArrayList(
													colsNode.getChildren(0))
													.iterator();

											while (fujaba__IterColsNodeToColumnCountNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterColsNodeToColumnCountNode
															.next();

													// ensure correct type and really bound of object columnCountNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													columnCountNode = (Node) _TmpObject;
													// check isomorphic binding between objects columnCountNode and boardNode 
													JavaSDM.ensure(!columnCountNode
															.equals(boardNode));

													// check isomorphic binding between objects columnCountNode and boardSpecNode 
													JavaSDM.ensure(!columnCountNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects columnCountNode and colsNode 
													JavaSDM.ensure(!columnCountNode
															.equals(colsNode));

													// check isomorphic binding between objects dimensionNode and columnCountNode 
													JavaSDM.ensure(!dimensionNode
															.equals(columnCountNode));

													// check isomorphic binding between objects topRowNode and columnCountNode 
													JavaSDM.ensure(!topRowNode
															.equals(columnCountNode));

													// iterate to-many link children from dimensionNode to rowsNode
													fujaba__Success = false;

													fujaba__IterDimensionNodeToRowsNode = new ArrayList(
															dimensionNode
																	.getChildren(
																			"ROWS",
																			0))
															.iterator();

													while (fujaba__IterDimensionNodeToRowsNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterDimensionNodeToRowsNode
																	.next();

															// ensure correct type and really bound of object rowsNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															rowsNode = (Node) _TmpObject;
															// check isomorphic binding between objects rowsNode and boardNode 
															JavaSDM.ensure(!rowsNode
																	.equals(boardNode));

															// check isomorphic binding between objects rowsNode and boardSpecNode 
															JavaSDM.ensure(!rowsNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects rowsNode and colsNode 
															JavaSDM.ensure(!rowsNode
																	.equals(colsNode));

															// check isomorphic binding between objects rowsNode and columnCountNode 
															JavaSDM.ensure(!rowsNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects rowsNode and dimensionNode 
															JavaSDM.ensure(!rowsNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects topRowNode and rowsNode 
															JavaSDM.ensure(!topRowNode
																	.equals(rowsNode));

															// iterate to-many link children from rowsNode to rowCountNode
															fujaba__Success = false;

															fujaba__IterRowsNodeToRowCountNode = new ArrayList(
																	rowsNode.getChildren(0))
																	.iterator();

															while (fujaba__IterRowsNodeToRowCountNode
																	.hasNext()) {
																try {
																	_TmpObject = fujaba__IterRowsNodeToRowCountNode
																			.next();

																	// ensure correct type and really bound of object rowCountNode
																	JavaSDM.ensure(_TmpObject instanceof Node);
																	rowCountNode = (Node) _TmpObject;
																	// check isomorphic binding between objects rowCountNode and boardNode 
																	JavaSDM.ensure(!rowCountNode
																			.equals(boardNode));

																	// check isomorphic binding between objects rowCountNode and boardSpecNode 
																	JavaSDM.ensure(!rowCountNode
																			.equals(boardSpecNode));

																	// check isomorphic binding between objects rowCountNode and colsNode 
																	JavaSDM.ensure(!rowCountNode
																			.equals(colsNode));

																	// check isomorphic binding between objects rowCountNode and columnCountNode 
																	JavaSDM.ensure(!rowCountNode
																			.equals(columnCountNode));

																	// check isomorphic binding between objects rowCountNode and dimensionNode 
																	JavaSDM.ensure(!rowCountNode
																			.equals(dimensionNode));

																	// check isomorphic binding between objects rowsNode and rowCountNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(rowCountNode));

																	// check isomorphic binding between objects topRowNode and rowCountNode 
																	JavaSDM.ensure(!topRowNode
																			.equals(rowCountNode));

																	// iterate to-many link children from topRowNode to topRowLeftColumnNode
																	fujaba__Success = false;

																	fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
																			topRowNode
																					.getChildren(
																							"COLUMN",
																							0))
																			.iterator();

																	while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
																			.hasNext()) {
																		try {
																			_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
																					.next();

																			// ensure correct type and really bound of object topRowLeftColumnNode
																			JavaSDM.ensure(_TmpObject instanceof Node);
																			topRowLeftColumnNode = (Node) _TmpObject;
																			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(boardNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(boardSpecNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(colsNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(columnCountNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(dimensionNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(rowCountNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(rowsNode));

																			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																			JavaSDM.ensure(!topRowNode
																					.equals(topRowLeftColumnNode));

																			// attribute condition
																			JavaSDM.ensure(rowsNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(columnCountNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(dimensionNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(topRowLeftColumnNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(topRowNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(rowCountNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(boardNode
																					.getIndex() == 1);

																			// attribute condition
																			JavaSDM.ensure(colsNode
																					.getIndex() == 1);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							boardNode
																									.getName(),
																							"BOARD") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							boardSpecNode
																									.getName(),
																							"BOARD_SPEC") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							colsNode.getName(),
																							"COLS") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							topRowLeftColumnNode
																									.getName(),
																							"COLUMN") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							dimensionNode
																									.getName(),
																							"DIMENSIONS") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							topRowNode
																									.getName(),
																							"ROW") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							rowsNode.getName(),
																							"ROWS") == 0);

																			// story node 'test core match'
																			try {
																				fujaba__Success = false;

																				// check object boardNode is really bound
																				JavaSDM.ensure(boardNode != null);
																				// check object boardSpecNode is really bound
																				JavaSDM.ensure(boardSpecNode != null);
																				// check object colsNode is really bound
																				JavaSDM.ensure(colsNode != null);
																				// check object columnCountNode is really bound
																				JavaSDM.ensure(columnCountNode != null);
																				// check object dimensionNode is really bound
																				JavaSDM.ensure(dimensionNode != null);
																				// check object file is really bound
																				JavaSDM.ensure(file != null);
																				// check object rowCountNode is really bound
																				JavaSDM.ensure(rowCountNode != null);
																				// check object rowsNode is really bound
																				JavaSDM.ensure(rowsNode != null);
																				// check object ruleresult is really bound
																				JavaSDM.ensure(ruleresult != null);
																				// check object topRowLeftColumnNode is really bound
																				JavaSDM.ensure(topRowLeftColumnNode != null);
																				// check object topRowNode is really bound
																				JavaSDM.ensure(topRowNode != null);
																				// check isomorphic binding between objects boardSpecNode and boardNode 
																				JavaSDM.ensure(!boardSpecNode
																						.equals(boardNode));

																				// check isomorphic binding between objects colsNode and boardNode 
																				JavaSDM.ensure(!colsNode
																						.equals(boardNode));

																				// check isomorphic binding between objects columnCountNode and boardNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(boardNode));

																				// check isomorphic binding between objects dimensionNode and boardNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(boardNode));

																				// check isomorphic binding between objects rowCountNode and boardNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(boardNode));

																				// check isomorphic binding between objects rowsNode and boardNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(boardNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(boardNode));

																				// check isomorphic binding between objects topRowNode and boardNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(boardNode));

																				// check isomorphic binding between objects colsNode and boardSpecNode 
																				JavaSDM.ensure(!colsNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects columnCountNode and boardSpecNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects dimensionNode and boardSpecNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects rowCountNode and boardSpecNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects rowsNode and boardSpecNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects topRowNode and boardSpecNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects columnCountNode and colsNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(colsNode));

																				// check isomorphic binding between objects dimensionNode and colsNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(colsNode));

																				// check isomorphic binding between objects rowCountNode and colsNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(colsNode));

																				// check isomorphic binding between objects rowsNode and colsNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(colsNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(colsNode));

																				// check isomorphic binding between objects topRowNode and colsNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(colsNode));

																				// check isomorphic binding between objects dimensionNode and columnCountNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowCountNode and columnCountNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowsNode and columnCountNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects topRowNode and columnCountNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowCountNode and dimensionNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects rowsNode and dimensionNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects topRowNode and dimensionNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects rowsNode and rowCountNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowNode and rowCountNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(rowsNode));

																				// check isomorphic binding between objects topRowNode and rowsNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(rowsNode));

																				// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(topRowLeftColumnNode));

																				// check link children from topRowNode to boardNode
																				JavaSDM.ensure(boardNode
																						.equals(topRowNode
																								.getParentNode()));

																				// check link children from boardNode to boardSpecNode
																				JavaSDM.ensure(boardSpecNode
																						.equals(boardNode
																								.getParentNode()));

																				// check link children from dimensionNode to boardSpecNode
																				JavaSDM.ensure(boardSpecNode
																						.equals(dimensionNode
																								.getParentNode()));

																				// check link children from columnCountNode to colsNode
																				JavaSDM.ensure(colsNode
																						.equals(columnCountNode
																								.getParentNode()));

																				// check link children from colsNode to dimensionNode
																				JavaSDM.ensure(dimensionNode
																						.equals(colsNode
																								.getParentNode()));

																				// check link children from rowsNode to dimensionNode
																				JavaSDM.ensure(dimensionNode
																						.equals(rowsNode
																								.getParentNode()));

																				// check link children from rowCountNode to rowsNode
																				JavaSDM.ensure(rowsNode
																						.equals(rowCountNode
																								.getParentNode()));

																				// check link children from topRowLeftColumnNode to topRowNode
																				JavaSDM.ensure(topRowNode
																						.equals(topRowLeftColumnNode
																								.getParentNode()));

																				// check link rootNode from boardSpecNode to file
																				JavaSDM.ensure(file
																						.equals(boardSpecNode
																								.getFile()));

																				// attribute condition
																				JavaSDM.ensure(rowCountNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(dimensionNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(columnCountNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(topRowLeftColumnNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(topRowNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(rowsNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(colsNode
																						.getIndex() == 1);

																				// attribute condition
																				JavaSDM.ensure(boardNode
																						.getIndex() == 1);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								boardNode
																										.getName(),
																								"BOARD") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								boardSpecNode
																										.getName(),
																								"BOARD_SPEC") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								colsNode.getName(),
																								"COLS") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								topRowLeftColumnNode
																										.getName(),
																								"COLUMN") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								dimensionNode
																										.getName(),
																								"DIMENSIONS") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								topRowNode
																										.getName(),
																								"ROW") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								rowsNode.getName(),
																								"ROWS") == 0);

																				// create object match
																				match = TGGRuntimeFactory.eINSTANCE
																						.createMatch();

																				// statement node 'bookkeeping with generic isAppropriate method'
																				fujaba__Success = this
																						.isAppropriate_FWD(
																								match,
																								file,
																								boardSpecNode,
																								dimensionNode,
																								rowsNode,
																								colsNode,
																								rowCountNode,
																								columnCountNode,
																								boardNode,
																								topRowNode,
																								topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_5(Node boardSpecNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterBoardSpecNodeToDimensionNode = null;
		Node dimensionNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
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

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);
			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// iterate to-many link children from boardSpecNode to dimensionNode
							fujaba__Success = false;

							fujaba__IterBoardSpecNodeToDimensionNode = new ArrayList(
									boardSpecNode.getChildren("DIMENSIONS", 0))
									.iterator();

							while (fujaba__IterBoardSpecNodeToDimensionNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterBoardSpecNodeToDimensionNode
											.next();

									// ensure correct type and really bound of object dimensionNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									dimensionNode = (Node) _TmpObject;
									// check isomorphic binding between objects dimensionNode and boardNode 
									JavaSDM.ensure(!dimensionNode
											.equals(boardNode));

									// check isomorphic binding between objects dimensionNode and boardSpecNode 
									JavaSDM.ensure(!dimensionNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects topRowNode and dimensionNode 
									JavaSDM.ensure(!topRowNode
											.equals(dimensionNode));

									// iterate to-many link children from topRowNode to topRowLeftColumnNode
									fujaba__Success = false;

									fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
											topRowNode.getChildren("COLUMN", 0))
											.iterator();

									while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
													.next();

											// ensure correct type and really bound of object topRowLeftColumnNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											topRowLeftColumnNode = (Node) _TmpObject;
											// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
											JavaSDM.ensure(!topRowLeftColumnNode
													.equals(boardNode));

											// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
											JavaSDM.ensure(!topRowLeftColumnNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
											JavaSDM.ensure(!topRowLeftColumnNode
													.equals(dimensionNode));

											// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
											JavaSDM.ensure(!topRowNode
													.equals(topRowLeftColumnNode));

											// iterate to-many link children from dimensionNode to colsNode
											fujaba__Success = false;

											fujaba__IterDimensionNodeToColsNode = new ArrayList(
													dimensionNode.getChildren(
															"COLS", 1))
													.iterator();

											while (fujaba__IterDimensionNodeToColsNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterDimensionNodeToColsNode
															.next();

													// ensure correct type and really bound of object colsNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													colsNode = (Node) _TmpObject;
													// check isomorphic binding between objects colsNode and boardNode 
													JavaSDM.ensure(!colsNode
															.equals(boardNode));

													// check isomorphic binding between objects colsNode and boardSpecNode 
													JavaSDM.ensure(!colsNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects dimensionNode and colsNode 
													JavaSDM.ensure(!dimensionNode
															.equals(colsNode));

													// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(colsNode));

													// check isomorphic binding between objects topRowNode and colsNode 
													JavaSDM.ensure(!topRowNode
															.equals(colsNode));

													// iterate to-many link children from colsNode to columnCountNode
													fujaba__Success = false;

													fujaba__IterColsNodeToColumnCountNode = new ArrayList(
															colsNode.getChildren(0))
															.iterator();

													while (fujaba__IterColsNodeToColumnCountNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterColsNodeToColumnCountNode
																	.next();

															// ensure correct type and really bound of object columnCountNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															columnCountNode = (Node) _TmpObject;
															// check isomorphic binding between objects columnCountNode and boardNode 
															JavaSDM.ensure(!columnCountNode
																	.equals(boardNode));

															// check isomorphic binding between objects columnCountNode and boardSpecNode 
															JavaSDM.ensure(!columnCountNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects columnCountNode and colsNode 
															JavaSDM.ensure(!columnCountNode
																	.equals(colsNode));

															// check isomorphic binding between objects dimensionNode and columnCountNode 
															JavaSDM.ensure(!dimensionNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects topRowNode and columnCountNode 
															JavaSDM.ensure(!topRowNode
																	.equals(columnCountNode));

															// iterate to-many link children from dimensionNode to rowsNode
															fujaba__Success = false;

															fujaba__IterDimensionNodeToRowsNode = new ArrayList(
																	dimensionNode
																			.getChildren(
																					"ROWS",
																					0))
																	.iterator();

															while (fujaba__IterDimensionNodeToRowsNode
																	.hasNext()) {
																try {
																	_TmpObject = fujaba__IterDimensionNodeToRowsNode
																			.next();

																	// ensure correct type and really bound of object rowsNode
																	JavaSDM.ensure(_TmpObject instanceof Node);
																	rowsNode = (Node) _TmpObject;
																	// check isomorphic binding between objects rowsNode and boardNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(boardNode));

																	// check isomorphic binding between objects rowsNode and boardSpecNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(boardSpecNode));

																	// check isomorphic binding between objects rowsNode and colsNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(colsNode));

																	// check isomorphic binding between objects rowsNode and columnCountNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(columnCountNode));

																	// check isomorphic binding between objects rowsNode and dimensionNode 
																	JavaSDM.ensure(!rowsNode
																			.equals(dimensionNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(rowsNode));

																	// check isomorphic binding between objects topRowNode and rowsNode 
																	JavaSDM.ensure(!topRowNode
																			.equals(rowsNode));

																	// iterate to-many link children from rowsNode to rowCountNode
																	fujaba__Success = false;

																	fujaba__IterRowsNodeToRowCountNode = new ArrayList(
																			rowsNode.getChildren(0))
																			.iterator();

																	while (fujaba__IterRowsNodeToRowCountNode
																			.hasNext()) {
																		try {
																			_TmpObject = fujaba__IterRowsNodeToRowCountNode
																					.next();

																			// ensure correct type and really bound of object rowCountNode
																			JavaSDM.ensure(_TmpObject instanceof Node);
																			rowCountNode = (Node) _TmpObject;
																			// check isomorphic binding between objects rowCountNode and boardNode 
																			JavaSDM.ensure(!rowCountNode
																					.equals(boardNode));

																			// check isomorphic binding between objects rowCountNode and boardSpecNode 
																			JavaSDM.ensure(!rowCountNode
																					.equals(boardSpecNode));

																			// check isomorphic binding between objects rowCountNode and colsNode 
																			JavaSDM.ensure(!rowCountNode
																					.equals(colsNode));

																			// check isomorphic binding between objects rowCountNode and columnCountNode 
																			JavaSDM.ensure(!rowCountNode
																					.equals(columnCountNode));

																			// check isomorphic binding between objects rowCountNode and dimensionNode 
																			JavaSDM.ensure(!rowCountNode
																					.equals(dimensionNode));

																			// check isomorphic binding between objects rowsNode and rowCountNode 
																			JavaSDM.ensure(!rowsNode
																					.equals(rowCountNode));

																			// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																			JavaSDM.ensure(!topRowLeftColumnNode
																					.equals(rowCountNode));

																			// check isomorphic binding between objects topRowNode and rowCountNode 
																			JavaSDM.ensure(!topRowNode
																					.equals(rowCountNode));

																			// attribute condition
																			JavaSDM.ensure(rowsNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(rowCountNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(topRowNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(dimensionNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(columnCountNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(topRowLeftColumnNode
																					.getIndex() == 0);

																			// attribute condition
																			JavaSDM.ensure(colsNode
																					.getIndex() == 1);

																			// attribute condition
																			JavaSDM.ensure(boardNode
																					.getIndex() == 1);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							boardNode
																									.getName(),
																							"BOARD") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							boardSpecNode
																									.getName(),
																							"BOARD_SPEC") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							colsNode.getName(),
																							"COLS") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							topRowLeftColumnNode
																									.getName(),
																							"COLUMN") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							dimensionNode
																									.getName(),
																							"DIMENSIONS") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							topRowNode
																									.getName(),
																							"ROW") == 0);

																			// attribute condition
																			JavaSDM.ensure(JavaSDM
																					.stringCompare(
																							rowsNode.getName(),
																							"ROWS") == 0);

																			// story node 'test core match'
																			try {
																				fujaba__Success = false;

																				// check object boardNode is really bound
																				JavaSDM.ensure(boardNode != null);
																				// check object boardSpecNode is really bound
																				JavaSDM.ensure(boardSpecNode != null);
																				// check object colsNode is really bound
																				JavaSDM.ensure(colsNode != null);
																				// check object columnCountNode is really bound
																				JavaSDM.ensure(columnCountNode != null);
																				// check object dimensionNode is really bound
																				JavaSDM.ensure(dimensionNode != null);
																				// check object file is really bound
																				JavaSDM.ensure(file != null);
																				// check object rowCountNode is really bound
																				JavaSDM.ensure(rowCountNode != null);
																				// check object rowsNode is really bound
																				JavaSDM.ensure(rowsNode != null);
																				// check object ruleresult is really bound
																				JavaSDM.ensure(ruleresult != null);
																				// check object topRowLeftColumnNode is really bound
																				JavaSDM.ensure(topRowLeftColumnNode != null);
																				// check object topRowNode is really bound
																				JavaSDM.ensure(topRowNode != null);
																				// check isomorphic binding between objects boardSpecNode and boardNode 
																				JavaSDM.ensure(!boardSpecNode
																						.equals(boardNode));

																				// check isomorphic binding between objects colsNode and boardNode 
																				JavaSDM.ensure(!colsNode
																						.equals(boardNode));

																				// check isomorphic binding between objects columnCountNode and boardNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(boardNode));

																				// check isomorphic binding between objects dimensionNode and boardNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(boardNode));

																				// check isomorphic binding between objects rowCountNode and boardNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(boardNode));

																				// check isomorphic binding between objects rowsNode and boardNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(boardNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(boardNode));

																				// check isomorphic binding between objects topRowNode and boardNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(boardNode));

																				// check isomorphic binding between objects colsNode and boardSpecNode 
																				JavaSDM.ensure(!colsNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects columnCountNode and boardSpecNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects dimensionNode and boardSpecNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects rowCountNode and boardSpecNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects rowsNode and boardSpecNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects topRowNode and boardSpecNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(boardSpecNode));

																				// check isomorphic binding between objects columnCountNode and colsNode 
																				JavaSDM.ensure(!columnCountNode
																						.equals(colsNode));

																				// check isomorphic binding between objects dimensionNode and colsNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(colsNode));

																				// check isomorphic binding between objects rowCountNode and colsNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(colsNode));

																				// check isomorphic binding between objects rowsNode and colsNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(colsNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(colsNode));

																				// check isomorphic binding between objects topRowNode and colsNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(colsNode));

																				// check isomorphic binding between objects dimensionNode and columnCountNode 
																				JavaSDM.ensure(!dimensionNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowCountNode and columnCountNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowsNode and columnCountNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects topRowNode and columnCountNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(columnCountNode));

																				// check isomorphic binding between objects rowCountNode and dimensionNode 
																				JavaSDM.ensure(!rowCountNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects rowsNode and dimensionNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects topRowNode and dimensionNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(dimensionNode));

																				// check isomorphic binding between objects rowsNode and rowCountNode 
																				JavaSDM.ensure(!rowsNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowNode and rowCountNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(rowCountNode));

																				// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																				JavaSDM.ensure(!topRowLeftColumnNode
																						.equals(rowsNode));

																				// check isomorphic binding between objects topRowNode and rowsNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(rowsNode));

																				// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																				JavaSDM.ensure(!topRowNode
																						.equals(topRowLeftColumnNode));

																				// check link children from topRowNode to boardNode
																				JavaSDM.ensure(boardNode
																						.equals(topRowNode
																								.getParentNode()));

																				// check link children from boardNode to boardSpecNode
																				JavaSDM.ensure(boardSpecNode
																						.equals(boardNode
																								.getParentNode()));

																				// check link children from dimensionNode to boardSpecNode
																				JavaSDM.ensure(boardSpecNode
																						.equals(dimensionNode
																								.getParentNode()));

																				// check link children from columnCountNode to colsNode
																				JavaSDM.ensure(colsNode
																						.equals(columnCountNode
																								.getParentNode()));

																				// check link children from colsNode to dimensionNode
																				JavaSDM.ensure(dimensionNode
																						.equals(colsNode
																								.getParentNode()));

																				// check link children from rowsNode to dimensionNode
																				JavaSDM.ensure(dimensionNode
																						.equals(rowsNode
																								.getParentNode()));

																				// check link children from rowCountNode to rowsNode
																				JavaSDM.ensure(rowsNode
																						.equals(rowCountNode
																								.getParentNode()));

																				// check link children from topRowLeftColumnNode to topRowNode
																				JavaSDM.ensure(topRowNode
																						.equals(topRowLeftColumnNode
																								.getParentNode()));

																				// check link rootNode from boardSpecNode to file
																				JavaSDM.ensure(file
																						.equals(boardSpecNode
																								.getFile()));

																				// attribute condition
																				JavaSDM.ensure(topRowLeftColumnNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(columnCountNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(dimensionNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(rowsNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(topRowNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(rowCountNode
																						.getIndex() == 0);

																				// attribute condition
																				JavaSDM.ensure(boardNode
																						.getIndex() == 1);

																				// attribute condition
																				JavaSDM.ensure(colsNode
																						.getIndex() == 1);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								boardNode
																										.getName(),
																								"BOARD") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								boardSpecNode
																										.getName(),
																								"BOARD_SPEC") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								colsNode.getName(),
																								"COLS") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								topRowLeftColumnNode
																										.getName(),
																								"COLUMN") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								dimensionNode
																										.getName(),
																								"DIMENSIONS") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								topRowNode
																										.getName(),
																								"ROW") == 0);

																				// attribute condition
																				JavaSDM.ensure(JavaSDM
																						.stringCompare(
																								rowsNode.getName(),
																								"ROWS") == 0);

																				// create object match
																				match = TGGRuntimeFactory.eINSTANCE
																						.createMatch();

																				// statement node 'bookkeeping with generic isAppropriate method'
																				fujaba__Success = this
																						.isAppropriate_FWD(
																								match,
																								file,
																								boardSpecNode,
																								dimensionNode,
																								rowsNode,
																								colsNode,
																								rowCountNode,
																								columnCountNode,
																								boardNode,
																								topRowNode,
																								topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_6(Node dimensionNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
		Node boardSpecNode = null;
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

			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);
			// bind object
			boardSpecNode = dimensionNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// iterate to-many link children from dimensionNode to colsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToColsNode = new ArrayList(
									dimensionNode.getChildren("COLS", 1))
									.iterator();

							while (fujaba__IterDimensionNodeToColsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToColsNode
											.next();

									// ensure correct type and really bound of object colsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									colsNode = (Node) _TmpObject;
									// check isomorphic binding between objects colsNode and boardNode 
									JavaSDM.ensure(!colsNode.equals(boardNode));

									// check isomorphic binding between objects colsNode and boardSpecNode 
									JavaSDM.ensure(!colsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects dimensionNode and colsNode 
									JavaSDM.ensure(!dimensionNode
											.equals(colsNode));

									// check isomorphic binding between objects topRowNode and colsNode 
									JavaSDM.ensure(!topRowNode.equals(colsNode));

									// iterate to-many link children from colsNode to columnCountNode
									fujaba__Success = false;

									fujaba__IterColsNodeToColumnCountNode = new ArrayList(
											colsNode.getChildren(0)).iterator();

									while (fujaba__IterColsNodeToColumnCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterColsNodeToColumnCountNode
													.next();

											// ensure correct type and really bound of object columnCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											columnCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects columnCountNode and boardNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardNode));

											// check isomorphic binding between objects columnCountNode and boardSpecNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects columnCountNode and colsNode 
											JavaSDM.ensure(!columnCountNode
													.equals(colsNode));

											// check isomorphic binding between objects dimensionNode and columnCountNode 
											JavaSDM.ensure(!dimensionNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowNode and columnCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(columnCountNode));

											// iterate to-many link children from dimensionNode to rowsNode
											fujaba__Success = false;

											fujaba__IterDimensionNodeToRowsNode = new ArrayList(
													dimensionNode.getChildren(
															"ROWS", 0))
													.iterator();

											while (fujaba__IterDimensionNodeToRowsNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterDimensionNodeToRowsNode
															.next();

													// ensure correct type and really bound of object rowsNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowsNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowsNode and boardNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardNode));

													// check isomorphic binding between objects rowsNode and boardSpecNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowsNode and colsNode 
													JavaSDM.ensure(!rowsNode
															.equals(colsNode));

													// check isomorphic binding between objects rowsNode and columnCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowsNode and dimensionNode 
													JavaSDM.ensure(!rowsNode
															.equals(dimensionNode));

													// check isomorphic binding between objects topRowNode and rowsNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowsNode));

													// iterate to-many link children from rowsNode to rowCountNode
													fujaba__Success = false;

													fujaba__IterRowsNodeToRowCountNode = new ArrayList(
															rowsNode.getChildren(0))
															.iterator();

													while (fujaba__IterRowsNodeToRowCountNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterRowsNodeToRowCountNode
																	.next();

															// ensure correct type and really bound of object rowCountNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															rowCountNode = (Node) _TmpObject;
															// check isomorphic binding between objects rowCountNode and boardNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardNode));

															// check isomorphic binding between objects rowCountNode and boardSpecNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects rowCountNode and colsNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(colsNode));

															// check isomorphic binding between objects rowCountNode and columnCountNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects rowCountNode and dimensionNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects rowsNode and rowCountNode 
															JavaSDM.ensure(!rowsNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowNode and rowCountNode 
															JavaSDM.ensure(!topRowNode
																	.equals(rowCountNode));

															// iterate to-many link children from topRowNode to topRowLeftColumnNode
															fujaba__Success = false;

															fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
																	topRowNode
																			.getChildren(
																					"COLUMN",
																					0))
																	.iterator();

															while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
																	.hasNext()) {
																try {
																	_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
																			.next();

																	// ensure correct type and really bound of object topRowLeftColumnNode
																	JavaSDM.ensure(_TmpObject instanceof Node);
																	topRowLeftColumnNode = (Node) _TmpObject;
																	// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(boardNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(boardSpecNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(colsNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(columnCountNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(dimensionNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(rowCountNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(rowsNode));

																	// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																	JavaSDM.ensure(!topRowNode
																			.equals(topRowLeftColumnNode));

																	// attribute condition
																	JavaSDM.ensure(rowsNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(columnCountNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(topRowLeftColumnNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(dimensionNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(rowCountNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(topRowNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(colsNode
																			.getIndex() == 1);

																	// attribute condition
																	JavaSDM.ensure(boardNode
																			.getIndex() == 1);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					boardNode
																							.getName(),
																					"BOARD") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					boardSpecNode
																							.getName(),
																					"BOARD_SPEC") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					colsNode.getName(),
																					"COLS") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					topRowLeftColumnNode
																							.getName(),
																					"COLUMN") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					dimensionNode
																							.getName(),
																					"DIMENSIONS") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					topRowNode
																							.getName(),
																					"ROW") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					rowsNode.getName(),
																					"ROWS") == 0);

																	// story node 'test core match'
																	try {
																		fujaba__Success = false;

																		// check object boardNode is really bound
																		JavaSDM.ensure(boardNode != null);
																		// check object boardSpecNode is really bound
																		JavaSDM.ensure(boardSpecNode != null);
																		// check object colsNode is really bound
																		JavaSDM.ensure(colsNode != null);
																		// check object columnCountNode is really bound
																		JavaSDM.ensure(columnCountNode != null);
																		// check object dimensionNode is really bound
																		JavaSDM.ensure(dimensionNode != null);
																		// check object file is really bound
																		JavaSDM.ensure(file != null);
																		// check object rowCountNode is really bound
																		JavaSDM.ensure(rowCountNode != null);
																		// check object rowsNode is really bound
																		JavaSDM.ensure(rowsNode != null);
																		// check object ruleresult is really bound
																		JavaSDM.ensure(ruleresult != null);
																		// check object topRowLeftColumnNode is really bound
																		JavaSDM.ensure(topRowLeftColumnNode != null);
																		// check object topRowNode is really bound
																		JavaSDM.ensure(topRowNode != null);
																		// check isomorphic binding between objects boardSpecNode and boardNode 
																		JavaSDM.ensure(!boardSpecNode
																				.equals(boardNode));

																		// check isomorphic binding between objects colsNode and boardNode 
																		JavaSDM.ensure(!colsNode
																				.equals(boardNode));

																		// check isomorphic binding between objects columnCountNode and boardNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(boardNode));

																		// check isomorphic binding between objects dimensionNode and boardNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(boardNode));

																		// check isomorphic binding between objects rowCountNode and boardNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(boardNode));

																		// check isomorphic binding between objects rowsNode and boardNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(boardNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(boardNode));

																		// check isomorphic binding between objects topRowNode and boardNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(boardNode));

																		// check isomorphic binding between objects colsNode and boardSpecNode 
																		JavaSDM.ensure(!colsNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects columnCountNode and boardSpecNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects dimensionNode and boardSpecNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects rowCountNode and boardSpecNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects rowsNode and boardSpecNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects topRowNode and boardSpecNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects columnCountNode and colsNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(colsNode));

																		// check isomorphic binding between objects dimensionNode and colsNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(colsNode));

																		// check isomorphic binding between objects rowCountNode and colsNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(colsNode));

																		// check isomorphic binding between objects rowsNode and colsNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(colsNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(colsNode));

																		// check isomorphic binding between objects topRowNode and colsNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(colsNode));

																		// check isomorphic binding between objects dimensionNode and columnCountNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowCountNode and columnCountNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowsNode and columnCountNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects topRowNode and columnCountNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowCountNode and dimensionNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects rowsNode and dimensionNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects topRowNode and dimensionNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects rowsNode and rowCountNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowNode and rowCountNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(rowsNode));

																		// check isomorphic binding between objects topRowNode and rowsNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(rowsNode));

																		// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(topRowLeftColumnNode));

																		// check link children from topRowNode to boardNode
																		JavaSDM.ensure(boardNode
																				.equals(topRowNode
																						.getParentNode()));

																		// check link children from boardNode to boardSpecNode
																		JavaSDM.ensure(boardSpecNode
																				.equals(boardNode
																						.getParentNode()));

																		// check link children from dimensionNode to boardSpecNode
																		JavaSDM.ensure(boardSpecNode
																				.equals(dimensionNode
																						.getParentNode()));

																		// check link children from columnCountNode to colsNode
																		JavaSDM.ensure(colsNode
																				.equals(columnCountNode
																						.getParentNode()));

																		// check link children from colsNode to dimensionNode
																		JavaSDM.ensure(dimensionNode
																				.equals(colsNode
																						.getParentNode()));

																		// check link children from rowsNode to dimensionNode
																		JavaSDM.ensure(dimensionNode
																				.equals(rowsNode
																						.getParentNode()));

																		// check link children from rowCountNode to rowsNode
																		JavaSDM.ensure(rowsNode
																				.equals(rowCountNode
																						.getParentNode()));

																		// check link children from topRowLeftColumnNode to topRowNode
																		JavaSDM.ensure(topRowNode
																				.equals(topRowLeftColumnNode
																						.getParentNode()));

																		// check link rootNode from boardSpecNode to file
																		JavaSDM.ensure(file
																				.equals(boardSpecNode
																						.getFile()));

																		// attribute condition
																		JavaSDM.ensure(topRowLeftColumnNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(dimensionNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(rowsNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(columnCountNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(rowCountNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(topRowNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(colsNode
																				.getIndex() == 1);

																		// attribute condition
																		JavaSDM.ensure(boardNode
																				.getIndex() == 1);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						boardNode
																								.getName(),
																						"BOARD") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						boardSpecNode
																								.getName(),
																						"BOARD_SPEC") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						colsNode.getName(),
																						"COLS") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						topRowLeftColumnNode
																								.getName(),
																						"COLUMN") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						dimensionNode
																								.getName(),
																						"DIMENSIONS") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						topRowNode
																								.getName(),
																						"ROW") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						rowsNode.getName(),
																						"ROWS") == 0);

																		// create object match
																		match = TGGRuntimeFactory.eINSTANCE
																				.createMatch();

																		// statement node 'bookkeeping with generic isAppropriate method'
																		fujaba__Success = this
																				.isAppropriate_FWD(
																						match,
																						file,
																						boardSpecNode,
																						dimensionNode,
																						rowsNode,
																						colsNode,
																						rowCountNode,
																						columnCountNode,
																						boardNode,
																						topRowNode,
																						topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_7(Node rowsNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
		Node boardSpecNode = null;
		Node dimensionNode = null;
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

			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);
			// bind object
			dimensionNode = rowsNode.getParentNode();

			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// bind object
			boardSpecNode = dimensionNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// check isomorphic binding between objects rowsNode and boardNode 
					JavaSDM.ensure(!rowsNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// check isomorphic binding between objects topRowNode and rowsNode 
							JavaSDM.ensure(!topRowNode.equals(rowsNode));

							// iterate to-many link children from dimensionNode to colsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToColsNode = new ArrayList(
									dimensionNode.getChildren("COLS", 1))
									.iterator();

							while (fujaba__IterDimensionNodeToColsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToColsNode
											.next();

									// ensure correct type and really bound of object colsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									colsNode = (Node) _TmpObject;
									// check isomorphic binding between objects colsNode and boardNode 
									JavaSDM.ensure(!colsNode.equals(boardNode));

									// check isomorphic binding between objects colsNode and boardSpecNode 
									JavaSDM.ensure(!colsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects dimensionNode and colsNode 
									JavaSDM.ensure(!dimensionNode
											.equals(colsNode));

									// check isomorphic binding between objects rowsNode and colsNode 
									JavaSDM.ensure(!rowsNode.equals(colsNode));

									// check isomorphic binding between objects topRowNode and colsNode 
									JavaSDM.ensure(!topRowNode.equals(colsNode));

									// iterate to-many link children from colsNode to columnCountNode
									fujaba__Success = false;

									fujaba__IterColsNodeToColumnCountNode = new ArrayList(
											colsNode.getChildren(0)).iterator();

									while (fujaba__IterColsNodeToColumnCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterColsNodeToColumnCountNode
													.next();

											// ensure correct type and really bound of object columnCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											columnCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects columnCountNode and boardNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardNode));

											// check isomorphic binding between objects columnCountNode and boardSpecNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects columnCountNode and colsNode 
											JavaSDM.ensure(!columnCountNode
													.equals(colsNode));

											// check isomorphic binding between objects dimensionNode and columnCountNode 
											JavaSDM.ensure(!dimensionNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowsNode and columnCountNode 
											JavaSDM.ensure(!rowsNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowNode and columnCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(columnCountNode));

											// iterate to-many link children from rowsNode to rowCountNode
											fujaba__Success = false;

											fujaba__IterRowsNodeToRowCountNode = new ArrayList(
													rowsNode.getChildren(0))
													.iterator();

											while (fujaba__IterRowsNodeToRowCountNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterRowsNodeToRowCountNode
															.next();

													// ensure correct type and really bound of object rowCountNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowCountNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowCountNode and boardNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardNode));

													// check isomorphic binding between objects rowCountNode and boardSpecNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowCountNode and colsNode 
													JavaSDM.ensure(!rowCountNode
															.equals(colsNode));

													// check isomorphic binding between objects rowCountNode and columnCountNode 
													JavaSDM.ensure(!rowCountNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowCountNode and dimensionNode 
													JavaSDM.ensure(!rowCountNode
															.equals(dimensionNode));

													// check isomorphic binding between objects rowsNode and rowCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowNode and rowCountNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowCountNode));

													// iterate to-many link children from topRowNode to topRowLeftColumnNode
													fujaba__Success = false;

													fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
															topRowNode
																	.getChildren(
																			"COLUMN",
																			0))
															.iterator();

													while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
																	.next();

															// ensure correct type and really bound of object topRowLeftColumnNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															topRowLeftColumnNode = (Node) _TmpObject;
															// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(boardNode));

															// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(colsNode));

															// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(rowsNode));

															// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
															JavaSDM.ensure(!topRowNode
																	.equals(topRowLeftColumnNode));

															// attribute condition
															JavaSDM.ensure(rowsNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(dimensionNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(columnCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(rowCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowLeftColumnNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(colsNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(boardNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardNode
																					.getName(),
																			"BOARD") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardSpecNode
																					.getName(),
																			"BOARD_SPEC") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			colsNode.getName(),
																			"COLS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowLeftColumnNode
																					.getName(),
																			"COLUMN") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			dimensionNode
																					.getName(),
																			"DIMENSIONS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowNode
																					.getName(),
																			"ROW") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			rowsNode.getName(),
																			"ROWS") == 0);

															// story node 'test core match'
															try {
																fujaba__Success = false;

																// check object boardNode is really bound
																JavaSDM.ensure(boardNode != null);
																// check object boardSpecNode is really bound
																JavaSDM.ensure(boardSpecNode != null);
																// check object colsNode is really bound
																JavaSDM.ensure(colsNode != null);
																// check object columnCountNode is really bound
																JavaSDM.ensure(columnCountNode != null);
																// check object dimensionNode is really bound
																JavaSDM.ensure(dimensionNode != null);
																// check object file is really bound
																JavaSDM.ensure(file != null);
																// check object rowCountNode is really bound
																JavaSDM.ensure(rowCountNode != null);
																// check object rowsNode is really bound
																JavaSDM.ensure(rowsNode != null);
																// check object ruleresult is really bound
																JavaSDM.ensure(ruleresult != null);
																// check object topRowLeftColumnNode is really bound
																JavaSDM.ensure(topRowLeftColumnNode != null);
																// check object topRowNode is really bound
																JavaSDM.ensure(topRowNode != null);
																// check isomorphic binding between objects boardSpecNode and boardNode 
																JavaSDM.ensure(!boardSpecNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardNode));

																// check isomorphic binding between objects columnCountNode and boardNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects dimensionNode and boardNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowCountNode and boardNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowsNode and boardNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowNode and boardNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardSpecNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and boardSpecNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects dimensionNode and boardSpecNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowCountNode and boardSpecNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowsNode and boardSpecNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowNode and boardSpecNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and colsNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and colsNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowCountNode and colsNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowsNode and colsNode 
																JavaSDM.ensure(!rowsNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowNode and colsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and columnCountNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and columnCountNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowsNode and columnCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowNode and columnCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and dimensionNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and dimensionNode 
																JavaSDM.ensure(!rowsNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowNode and dimensionNode 
																JavaSDM.ensure(!topRowNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and rowCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowNode and rowCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and rowsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																JavaSDM.ensure(!topRowNode
																		.equals(topRowLeftColumnNode));

																// check link children from topRowNode to boardNode
																JavaSDM.ensure(boardNode
																		.equals(topRowNode
																				.getParentNode()));

																// check link children from boardNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(boardNode
																				.getParentNode()));

																// check link children from dimensionNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(dimensionNode
																				.getParentNode()));

																// check link children from columnCountNode to colsNode
																JavaSDM.ensure(colsNode
																		.equals(columnCountNode
																				.getParentNode()));

																// check link children from colsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(colsNode
																				.getParentNode()));

																// check link children from rowsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(rowsNode
																				.getParentNode()));

																// check link children from rowCountNode to rowsNode
																JavaSDM.ensure(rowsNode
																		.equals(rowCountNode
																				.getParentNode()));

																// check link children from topRowLeftColumnNode to topRowNode
																JavaSDM.ensure(topRowNode
																		.equals(topRowLeftColumnNode
																				.getParentNode()));

																// check link rootNode from boardSpecNode to file
																JavaSDM.ensure(file
																		.equals(boardSpecNode
																				.getFile()));

																// attribute condition
																JavaSDM.ensure(columnCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(dimensionNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(topRowLeftColumnNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowsNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(topRowNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(colsNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(boardNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardNode
																						.getName(),
																				"BOARD") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardSpecNode
																						.getName(),
																				"BOARD_SPEC") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				colsNode.getName(),
																				"COLS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowLeftColumnNode
																						.getName(),
																				"COLUMN") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				dimensionNode
																						.getName(),
																				"DIMENSIONS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowNode
																						.getName(),
																				"ROW") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				rowsNode.getName(),
																				"ROWS") == 0);

																// create object match
																match = TGGRuntimeFactory.eINSTANCE
																		.createMatch();

																// statement node 'bookkeeping with generic isAppropriate method'
																fujaba__Success = this
																		.isAppropriate_FWD(
																				match,
																				file,
																				boardSpecNode,
																				dimensionNode,
																				rowsNode,
																				colsNode,
																				rowCountNode,
																				columnCountNode,
																				boardNode,
																				topRowNode,
																				topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_8(Node colsNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
		Node boardSpecNode = null;
		Node dimensionNode = null;
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

			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);
			// bind object
			dimensionNode = colsNode.getParentNode();

			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// bind object
			boardSpecNode = dimensionNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// check isomorphic binding between objects colsNode and boardNode 
					JavaSDM.ensure(!colsNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and colsNode 
							JavaSDM.ensure(!topRowNode.equals(colsNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// iterate to-many link children from colsNode to columnCountNode
							fujaba__Success = false;

							fujaba__IterColsNodeToColumnCountNode = new ArrayList(
									colsNode.getChildren(0)).iterator();

							while (fujaba__IterColsNodeToColumnCountNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterColsNodeToColumnCountNode
											.next();

									// ensure correct type and really bound of object columnCountNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									columnCountNode = (Node) _TmpObject;
									// check isomorphic binding between objects columnCountNode and boardNode 
									JavaSDM.ensure(!columnCountNode
											.equals(boardNode));

									// check isomorphic binding between objects columnCountNode and boardSpecNode 
									JavaSDM.ensure(!columnCountNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects columnCountNode and colsNode 
									JavaSDM.ensure(!columnCountNode
											.equals(colsNode));

									// check isomorphic binding between objects dimensionNode and columnCountNode 
									JavaSDM.ensure(!dimensionNode
											.equals(columnCountNode));

									// check isomorphic binding between objects topRowNode and columnCountNode 
									JavaSDM.ensure(!topRowNode
											.equals(columnCountNode));

									// iterate to-many link children from dimensionNode to rowsNode
									fujaba__Success = false;

									fujaba__IterDimensionNodeToRowsNode = new ArrayList(
											dimensionNode
													.getChildren("ROWS", 0))
											.iterator();

									while (fujaba__IterDimensionNodeToRowsNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterDimensionNodeToRowsNode
													.next();

											// ensure correct type and really bound of object rowsNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											rowsNode = (Node) _TmpObject;
											// check isomorphic binding between objects rowsNode and boardNode 
											JavaSDM.ensure(!rowsNode
													.equals(boardNode));

											// check isomorphic binding between objects rowsNode and boardSpecNode 
											JavaSDM.ensure(!rowsNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects rowsNode and colsNode 
											JavaSDM.ensure(!rowsNode
													.equals(colsNode));

											// check isomorphic binding between objects rowsNode and columnCountNode 
											JavaSDM.ensure(!rowsNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowsNode and dimensionNode 
											JavaSDM.ensure(!rowsNode
													.equals(dimensionNode));

											// check isomorphic binding between objects topRowNode and rowsNode 
											JavaSDM.ensure(!topRowNode
													.equals(rowsNode));

											// iterate to-many link children from rowsNode to rowCountNode
											fujaba__Success = false;

											fujaba__IterRowsNodeToRowCountNode = new ArrayList(
													rowsNode.getChildren(0))
													.iterator();

											while (fujaba__IterRowsNodeToRowCountNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterRowsNodeToRowCountNode
															.next();

													// ensure correct type and really bound of object rowCountNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowCountNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowCountNode and boardNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardNode));

													// check isomorphic binding between objects rowCountNode and boardSpecNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowCountNode and colsNode 
													JavaSDM.ensure(!rowCountNode
															.equals(colsNode));

													// check isomorphic binding between objects rowCountNode and columnCountNode 
													JavaSDM.ensure(!rowCountNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowCountNode and dimensionNode 
													JavaSDM.ensure(!rowCountNode
															.equals(dimensionNode));

													// check isomorphic binding between objects rowsNode and rowCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowNode and rowCountNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowCountNode));

													// iterate to-many link children from topRowNode to topRowLeftColumnNode
													fujaba__Success = false;

													fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
															topRowNode
																	.getChildren(
																			"COLUMN",
																			0))
															.iterator();

													while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
																	.next();

															// ensure correct type and really bound of object topRowLeftColumnNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															topRowLeftColumnNode = (Node) _TmpObject;
															// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(boardNode));

															// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(colsNode));

															// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(rowsNode));

															// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
															JavaSDM.ensure(!topRowNode
																	.equals(topRowLeftColumnNode));

															// attribute condition
															JavaSDM.ensure(rowCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(dimensionNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowLeftColumnNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(rowsNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(columnCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(boardNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(colsNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardNode
																					.getName(),
																			"BOARD") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardSpecNode
																					.getName(),
																			"BOARD_SPEC") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			colsNode.getName(),
																			"COLS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowLeftColumnNode
																					.getName(),
																			"COLUMN") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			dimensionNode
																					.getName(),
																			"DIMENSIONS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowNode
																					.getName(),
																			"ROW") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			rowsNode.getName(),
																			"ROWS") == 0);

															// story node 'test core match'
															try {
																fujaba__Success = false;

																// check object boardNode is really bound
																JavaSDM.ensure(boardNode != null);
																// check object boardSpecNode is really bound
																JavaSDM.ensure(boardSpecNode != null);
																// check object colsNode is really bound
																JavaSDM.ensure(colsNode != null);
																// check object columnCountNode is really bound
																JavaSDM.ensure(columnCountNode != null);
																// check object dimensionNode is really bound
																JavaSDM.ensure(dimensionNode != null);
																// check object file is really bound
																JavaSDM.ensure(file != null);
																// check object rowCountNode is really bound
																JavaSDM.ensure(rowCountNode != null);
																// check object rowsNode is really bound
																JavaSDM.ensure(rowsNode != null);
																// check object ruleresult is really bound
																JavaSDM.ensure(ruleresult != null);
																// check object topRowLeftColumnNode is really bound
																JavaSDM.ensure(topRowLeftColumnNode != null);
																// check object topRowNode is really bound
																JavaSDM.ensure(topRowNode != null);
																// check isomorphic binding between objects boardSpecNode and boardNode 
																JavaSDM.ensure(!boardSpecNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardNode));

																// check isomorphic binding between objects columnCountNode and boardNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects dimensionNode and boardNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowCountNode and boardNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowsNode and boardNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowNode and boardNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardSpecNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and boardSpecNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects dimensionNode and boardSpecNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowCountNode and boardSpecNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowsNode and boardSpecNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowNode and boardSpecNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and colsNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and colsNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowCountNode and colsNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowsNode and colsNode 
																JavaSDM.ensure(!rowsNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowNode and colsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and columnCountNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and columnCountNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowsNode and columnCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowNode and columnCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and dimensionNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and dimensionNode 
																JavaSDM.ensure(!rowsNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowNode and dimensionNode 
																JavaSDM.ensure(!topRowNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and rowCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowNode and rowCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and rowsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																JavaSDM.ensure(!topRowNode
																		.equals(topRowLeftColumnNode));

																// check link children from topRowNode to boardNode
																JavaSDM.ensure(boardNode
																		.equals(topRowNode
																				.getParentNode()));

																// check link children from boardNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(boardNode
																				.getParentNode()));

																// check link children from dimensionNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(dimensionNode
																				.getParentNode()));

																// check link children from columnCountNode to colsNode
																JavaSDM.ensure(colsNode
																		.equals(columnCountNode
																				.getParentNode()));

																// check link children from colsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(colsNode
																				.getParentNode()));

																// check link children from rowsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(rowsNode
																				.getParentNode()));

																// check link children from rowCountNode to rowsNode
																JavaSDM.ensure(rowsNode
																		.equals(rowCountNode
																				.getParentNode()));

																// check link children from topRowLeftColumnNode to topRowNode
																JavaSDM.ensure(topRowNode
																		.equals(topRowLeftColumnNode
																				.getParentNode()));

																// check link rootNode from boardSpecNode to file
																JavaSDM.ensure(file
																		.equals(boardSpecNode
																				.getFile()));

																// attribute condition
																JavaSDM.ensure(dimensionNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(columnCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(topRowNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(topRowLeftColumnNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowsNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(colsNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(boardNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardNode
																						.getName(),
																				"BOARD") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardSpecNode
																						.getName(),
																				"BOARD_SPEC") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				colsNode.getName(),
																				"COLS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowLeftColumnNode
																						.getName(),
																				"COLUMN") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				dimensionNode
																						.getName(),
																				"DIMENSIONS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowNode
																						.getName(),
																				"ROW") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				rowsNode.getName(),
																				"ROWS") == 0);

																// create object match
																match = TGGRuntimeFactory.eINSTANCE
																		.createMatch();

																// statement node 'bookkeeping with generic isAppropriate method'
																fujaba__Success = this
																		.isAppropriate_FWD(
																				match,
																				file,
																				boardSpecNode,
																				dimensionNode,
																				rowsNode,
																				colsNode,
																				rowCountNode,
																				columnCountNode,
																				boardNode,
																				topRowNode,
																				topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_9(Node rowCountNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
		Node boardSpecNode = null;
		Node dimensionNode = null;
		Node rowsNode = null;
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

			// check object rowCountNode is really bound
			JavaSDM.ensure(rowCountNode != null);
			// bind object
			rowsNode = rowCountNode.getParentNode();

			// check object rowsNode is really bound
			JavaSDM.ensure(rowsNode != null);

			// check isomorphic binding between objects rowsNode and rowCountNode 
			JavaSDM.ensure(!rowsNode.equals(rowCountNode));

			// bind object
			dimensionNode = rowsNode.getParentNode();

			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);

			// check isomorphic binding between objects rowCountNode and dimensionNode 
			JavaSDM.ensure(!rowCountNode.equals(dimensionNode));

			// check isomorphic binding between objects rowsNode and dimensionNode 
			JavaSDM.ensure(!rowsNode.equals(dimensionNode));

			// bind object
			boardSpecNode = dimensionNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowCountNode and boardSpecNode 
			JavaSDM.ensure(!rowCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects rowsNode and boardSpecNode 
			JavaSDM.ensure(!rowsNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// check isomorphic binding between objects rowCountNode and boardNode 
					JavaSDM.ensure(!rowCountNode.equals(boardNode));

					// check isomorphic binding between objects rowsNode and boardNode 
					JavaSDM.ensure(!rowsNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// check isomorphic binding between objects topRowNode and rowCountNode 
							JavaSDM.ensure(!topRowNode.equals(rowCountNode));

							// check isomorphic binding between objects topRowNode and rowsNode 
							JavaSDM.ensure(!topRowNode.equals(rowsNode));

							// iterate to-many link children from dimensionNode to colsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToColsNode = new ArrayList(
									dimensionNode.getChildren("COLS", 1))
									.iterator();

							while (fujaba__IterDimensionNodeToColsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToColsNode
											.next();

									// ensure correct type and really bound of object colsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									colsNode = (Node) _TmpObject;
									// check isomorphic binding between objects colsNode and boardNode 
									JavaSDM.ensure(!colsNode.equals(boardNode));

									// check isomorphic binding between objects colsNode and boardSpecNode 
									JavaSDM.ensure(!colsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects dimensionNode and colsNode 
									JavaSDM.ensure(!dimensionNode
											.equals(colsNode));

									// check isomorphic binding between objects rowCountNode and colsNode 
									JavaSDM.ensure(!rowCountNode
											.equals(colsNode));

									// check isomorphic binding between objects rowsNode and colsNode 
									JavaSDM.ensure(!rowsNode.equals(colsNode));

									// check isomorphic binding between objects topRowNode and colsNode 
									JavaSDM.ensure(!topRowNode.equals(colsNode));

									// iterate to-many link children from colsNode to columnCountNode
									fujaba__Success = false;

									fujaba__IterColsNodeToColumnCountNode = new ArrayList(
											colsNode.getChildren(0)).iterator();

									while (fujaba__IterColsNodeToColumnCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterColsNodeToColumnCountNode
													.next();

											// ensure correct type and really bound of object columnCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											columnCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects columnCountNode and boardNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardNode));

											// check isomorphic binding between objects columnCountNode and boardSpecNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects columnCountNode and colsNode 
											JavaSDM.ensure(!columnCountNode
													.equals(colsNode));

											// check isomorphic binding between objects dimensionNode and columnCountNode 
											JavaSDM.ensure(!dimensionNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowCountNode and columnCountNode 
											JavaSDM.ensure(!rowCountNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowsNode and columnCountNode 
											JavaSDM.ensure(!rowsNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowNode and columnCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(columnCountNode));

											// iterate to-many link children from topRowNode to topRowLeftColumnNode
											fujaba__Success = false;

											fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
													topRowNode.getChildren(
															"COLUMN", 0))
													.iterator();

											while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
															.next();

													// ensure correct type and really bound of object topRowLeftColumnNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													topRowLeftColumnNode = (Node) _TmpObject;
													// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(boardNode));

													// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(colsNode));

													// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(columnCountNode));

													// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(dimensionNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowsNode));

													// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
													JavaSDM.ensure(!topRowNode
															.equals(topRowLeftColumnNode));

													// attribute condition
													JavaSDM.ensure(dimensionNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(rowsNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(columnCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowLeftColumnNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(rowCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(boardNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(colsNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardNode.getName(),
															"BOARD") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardSpecNode
																	.getName(),
															"BOARD_SPEC") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															colsNode.getName(),
															"COLS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowLeftColumnNode
																	.getName(),
															"COLUMN") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															dimensionNode
																	.getName(),
															"DIMENSIONS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowNode
																	.getName(),
															"ROW") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															rowsNode.getName(),
															"ROWS") == 0);

													// story node 'test core match'
													try {
														fujaba__Success = false;

														// check object boardNode is really bound
														JavaSDM.ensure(boardNode != null);
														// check object boardSpecNode is really bound
														JavaSDM.ensure(boardSpecNode != null);
														// check object colsNode is really bound
														JavaSDM.ensure(colsNode != null);
														// check object columnCountNode is really bound
														JavaSDM.ensure(columnCountNode != null);
														// check object dimensionNode is really bound
														JavaSDM.ensure(dimensionNode != null);
														// check object file is really bound
														JavaSDM.ensure(file != null);
														// check object rowCountNode is really bound
														JavaSDM.ensure(rowCountNode != null);
														// check object rowsNode is really bound
														JavaSDM.ensure(rowsNode != null);
														// check object ruleresult is really bound
														JavaSDM.ensure(ruleresult != null);
														// check object topRowLeftColumnNode is really bound
														JavaSDM.ensure(topRowLeftColumnNode != null);
														// check object topRowNode is really bound
														JavaSDM.ensure(topRowNode != null);
														// check isomorphic binding between objects boardSpecNode and boardNode 
														JavaSDM.ensure(!boardSpecNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardNode 
														JavaSDM.ensure(!colsNode
																.equals(boardNode));

														// check isomorphic binding between objects columnCountNode and boardNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardNode));

														// check isomorphic binding between objects dimensionNode and boardNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardNode));

														// check isomorphic binding between objects rowCountNode and boardNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardNode));

														// check isomorphic binding between objects rowsNode and boardNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowNode and boardNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardSpecNode 
														JavaSDM.ensure(!colsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and boardSpecNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects dimensionNode and boardSpecNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowCountNode and boardSpecNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowsNode and boardSpecNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowNode and boardSpecNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and colsNode 
														JavaSDM.ensure(!columnCountNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and colsNode 
														JavaSDM.ensure(!dimensionNode
																.equals(colsNode));

														// check isomorphic binding between objects rowCountNode and colsNode 
														JavaSDM.ensure(!rowCountNode
																.equals(colsNode));

														// check isomorphic binding between objects rowsNode and colsNode 
														JavaSDM.ensure(!rowsNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowNode and colsNode 
														JavaSDM.ensure(!topRowNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and columnCountNode 
														JavaSDM.ensure(!dimensionNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and columnCountNode 
														JavaSDM.ensure(!rowCountNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowsNode and columnCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowNode and columnCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and dimensionNode 
														JavaSDM.ensure(!rowCountNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and dimensionNode 
														JavaSDM.ensure(!rowsNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowNode and dimensionNode 
														JavaSDM.ensure(!topRowNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and rowCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowNode and rowCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and rowsNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
														JavaSDM.ensure(!topRowNode
																.equals(topRowLeftColumnNode));

														// check link children from topRowNode to boardNode
														JavaSDM.ensure(boardNode
																.equals(topRowNode
																		.getParentNode()));

														// check link children from boardNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(boardNode
																		.getParentNode()));

														// check link children from dimensionNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(dimensionNode
																		.getParentNode()));

														// check link children from columnCountNode to colsNode
														JavaSDM.ensure(colsNode
																.equals(columnCountNode
																		.getParentNode()));

														// check link children from colsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(colsNode
																		.getParentNode()));

														// check link children from rowsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(rowsNode
																		.getParentNode()));

														// check link children from rowCountNode to rowsNode
														JavaSDM.ensure(rowsNode
																.equals(rowCountNode
																		.getParentNode()));

														// check link children from topRowLeftColumnNode to topRowNode
														JavaSDM.ensure(topRowNode
																.equals(topRowLeftColumnNode
																		.getParentNode()));

														// check link rootNode from boardSpecNode to file
														JavaSDM.ensure(file
																.equals(boardSpecNode
																		.getFile()));

														// attribute condition
														JavaSDM.ensure(rowCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowLeftColumnNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(dimensionNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(rowsNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(columnCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(boardNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(colsNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardNode
																		.getName(),
																"BOARD") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardSpecNode
																		.getName(),
																"BOARD_SPEC") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																colsNode.getName(),
																"COLS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowLeftColumnNode
																		.getName(),
																"COLUMN") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																dimensionNode
																		.getName(),
																"DIMENSIONS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowNode
																		.getName(),
																"ROW") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																rowsNode.getName(),
																"ROWS") == 0);

														// create object match
														match = TGGRuntimeFactory.eINSTANCE
																.createMatch();

														// statement node 'bookkeeping with generic isAppropriate method'
														fujaba__Success = this
																.isAppropriate_FWD(
																		match,
																		file,
																		boardSpecNode,
																		dimensionNode,
																		rowsNode,
																		colsNode,
																		rowCountNode,
																		columnCountNode,
																		boardNode,
																		topRowNode,
																		topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_10(
			Node columnCountNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		Iterator fujaba__IterBoardSpecNodeToBoardNode = null;
		Node boardNode = null;
		File file = null;
		Node boardSpecNode = null;
		Node dimensionNode = null;
		Node colsNode = null;
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

			// check object columnCountNode is really bound
			JavaSDM.ensure(columnCountNode != null);
			// bind object
			colsNode = columnCountNode.getParentNode();

			// check object colsNode is really bound
			JavaSDM.ensure(colsNode != null);

			// check isomorphic binding between objects columnCountNode and colsNode 
			JavaSDM.ensure(!columnCountNode.equals(colsNode));

			// bind object
			dimensionNode = colsNode.getParentNode();

			// check object dimensionNode is really bound
			JavaSDM.ensure(dimensionNode != null);

			// check isomorphic binding between objects dimensionNode and colsNode 
			JavaSDM.ensure(!dimensionNode.equals(colsNode));

			// check isomorphic binding between objects dimensionNode and columnCountNode 
			JavaSDM.ensure(!dimensionNode.equals(columnCountNode));

			// bind object
			boardSpecNode = dimensionNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects colsNode and boardSpecNode 
			JavaSDM.ensure(!colsNode.equals(boardSpecNode));

			// check isomorphic binding between objects columnCountNode and boardSpecNode 
			JavaSDM.ensure(!columnCountNode.equals(boardSpecNode));

			// check isomorphic binding between objects dimensionNode and boardSpecNode 
			JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to boardNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToBoardNode = new ArrayList(
					boardSpecNode.getChildren("BOARD", 1)).iterator();

			while (fujaba__IterBoardSpecNodeToBoardNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToBoardNode.next();

					// ensure correct type and really bound of object boardNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					boardNode = (Node) _TmpObject;
					// check isomorphic binding between objects boardSpecNode and boardNode 
					JavaSDM.ensure(!boardSpecNode.equals(boardNode));

					// check isomorphic binding between objects colsNode and boardNode 
					JavaSDM.ensure(!colsNode.equals(boardNode));

					// check isomorphic binding between objects columnCountNode and boardNode 
					JavaSDM.ensure(!columnCountNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// iterate to-many link children from boardNode to topRowNode
					fujaba__Success = false;

					fujaba__IterBoardNodeToTopRowNode = new ArrayList(
							boardNode.getChildren("ROW", 0)).iterator();

					while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardNodeToTopRowNode
									.next();

							// ensure correct type and really bound of object topRowNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowNode and boardNode 
							JavaSDM.ensure(!topRowNode.equals(boardNode));

							// check isomorphic binding between objects topRowNode and boardSpecNode 
							JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and colsNode 
							JavaSDM.ensure(!topRowNode.equals(colsNode));

							// check isomorphic binding between objects topRowNode and columnCountNode 
							JavaSDM.ensure(!topRowNode.equals(columnCountNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// iterate to-many link children from dimensionNode to rowsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToRowsNode = new ArrayList(
									dimensionNode.getChildren("ROWS", 0))
									.iterator();

							while (fujaba__IterDimensionNodeToRowsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToRowsNode
											.next();

									// ensure correct type and really bound of object rowsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									rowsNode = (Node) _TmpObject;
									// check isomorphic binding between objects rowsNode and boardNode 
									JavaSDM.ensure(!rowsNode.equals(boardNode));

									// check isomorphic binding between objects rowsNode and boardSpecNode 
									JavaSDM.ensure(!rowsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects rowsNode and colsNode 
									JavaSDM.ensure(!rowsNode.equals(colsNode));

									// check isomorphic binding between objects rowsNode and columnCountNode 
									JavaSDM.ensure(!rowsNode
											.equals(columnCountNode));

									// check isomorphic binding between objects rowsNode and dimensionNode 
									JavaSDM.ensure(!rowsNode
											.equals(dimensionNode));

									// check isomorphic binding between objects topRowNode and rowsNode 
									JavaSDM.ensure(!topRowNode.equals(rowsNode));

									// iterate to-many link children from rowsNode to rowCountNode
									fujaba__Success = false;

									fujaba__IterRowsNodeToRowCountNode = new ArrayList(
											rowsNode.getChildren(0)).iterator();

									while (fujaba__IterRowsNodeToRowCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterRowsNodeToRowCountNode
													.next();

											// ensure correct type and really bound of object rowCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											rowCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects rowCountNode and boardNode 
											JavaSDM.ensure(!rowCountNode
													.equals(boardNode));

											// check isomorphic binding between objects rowCountNode and boardSpecNode 
											JavaSDM.ensure(!rowCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects rowCountNode and colsNode 
											JavaSDM.ensure(!rowCountNode
													.equals(colsNode));

											// check isomorphic binding between objects rowCountNode and columnCountNode 
											JavaSDM.ensure(!rowCountNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowCountNode and dimensionNode 
											JavaSDM.ensure(!rowCountNode
													.equals(dimensionNode));

											// check isomorphic binding between objects rowsNode and rowCountNode 
											JavaSDM.ensure(!rowsNode
													.equals(rowCountNode));

											// check isomorphic binding between objects topRowNode and rowCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(rowCountNode));

											// iterate to-many link children from topRowNode to topRowLeftColumnNode
											fujaba__Success = false;

											fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
													topRowNode.getChildren(
															"COLUMN", 0))
													.iterator();

											while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
															.next();

													// ensure correct type and really bound of object topRowLeftColumnNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													topRowLeftColumnNode = (Node) _TmpObject;
													// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(boardNode));

													// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(colsNode));

													// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(columnCountNode));

													// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(dimensionNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowsNode));

													// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
													JavaSDM.ensure(!topRowNode
															.equals(topRowLeftColumnNode));

													// attribute condition
													JavaSDM.ensure(rowCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowLeftColumnNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(columnCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(rowsNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(dimensionNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(boardNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(colsNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardNode.getName(),
															"BOARD") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardSpecNode
																	.getName(),
															"BOARD_SPEC") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															colsNode.getName(),
															"COLS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowLeftColumnNode
																	.getName(),
															"COLUMN") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															dimensionNode
																	.getName(),
															"DIMENSIONS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowNode
																	.getName(),
															"ROW") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															rowsNode.getName(),
															"ROWS") == 0);

													// story node 'test core match'
													try {
														fujaba__Success = false;

														// check object boardNode is really bound
														JavaSDM.ensure(boardNode != null);
														// check object boardSpecNode is really bound
														JavaSDM.ensure(boardSpecNode != null);
														// check object colsNode is really bound
														JavaSDM.ensure(colsNode != null);
														// check object columnCountNode is really bound
														JavaSDM.ensure(columnCountNode != null);
														// check object dimensionNode is really bound
														JavaSDM.ensure(dimensionNode != null);
														// check object file is really bound
														JavaSDM.ensure(file != null);
														// check object rowCountNode is really bound
														JavaSDM.ensure(rowCountNode != null);
														// check object rowsNode is really bound
														JavaSDM.ensure(rowsNode != null);
														// check object ruleresult is really bound
														JavaSDM.ensure(ruleresult != null);
														// check object topRowLeftColumnNode is really bound
														JavaSDM.ensure(topRowLeftColumnNode != null);
														// check object topRowNode is really bound
														JavaSDM.ensure(topRowNode != null);
														// check isomorphic binding between objects boardSpecNode and boardNode 
														JavaSDM.ensure(!boardSpecNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardNode 
														JavaSDM.ensure(!colsNode
																.equals(boardNode));

														// check isomorphic binding between objects columnCountNode and boardNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardNode));

														// check isomorphic binding between objects dimensionNode and boardNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardNode));

														// check isomorphic binding between objects rowCountNode and boardNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardNode));

														// check isomorphic binding between objects rowsNode and boardNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowNode and boardNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardSpecNode 
														JavaSDM.ensure(!colsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and boardSpecNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects dimensionNode and boardSpecNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowCountNode and boardSpecNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowsNode and boardSpecNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowNode and boardSpecNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and colsNode 
														JavaSDM.ensure(!columnCountNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and colsNode 
														JavaSDM.ensure(!dimensionNode
																.equals(colsNode));

														// check isomorphic binding between objects rowCountNode and colsNode 
														JavaSDM.ensure(!rowCountNode
																.equals(colsNode));

														// check isomorphic binding between objects rowsNode and colsNode 
														JavaSDM.ensure(!rowsNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowNode and colsNode 
														JavaSDM.ensure(!topRowNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and columnCountNode 
														JavaSDM.ensure(!dimensionNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and columnCountNode 
														JavaSDM.ensure(!rowCountNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowsNode and columnCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowNode and columnCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and dimensionNode 
														JavaSDM.ensure(!rowCountNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and dimensionNode 
														JavaSDM.ensure(!rowsNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowNode and dimensionNode 
														JavaSDM.ensure(!topRowNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and rowCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowNode and rowCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and rowsNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
														JavaSDM.ensure(!topRowNode
																.equals(topRowLeftColumnNode));

														// check link children from topRowNode to boardNode
														JavaSDM.ensure(boardNode
																.equals(topRowNode
																		.getParentNode()));

														// check link children from boardNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(boardNode
																		.getParentNode()));

														// check link children from dimensionNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(dimensionNode
																		.getParentNode()));

														// check link children from columnCountNode to colsNode
														JavaSDM.ensure(colsNode
																.equals(columnCountNode
																		.getParentNode()));

														// check link children from colsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(colsNode
																		.getParentNode()));

														// check link children from rowsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(rowsNode
																		.getParentNode()));

														// check link children from rowCountNode to rowsNode
														JavaSDM.ensure(rowsNode
																.equals(rowCountNode
																		.getParentNode()));

														// check link children from topRowLeftColumnNode to topRowNode
														JavaSDM.ensure(topRowNode
																.equals(topRowLeftColumnNode
																		.getParentNode()));

														// check link rootNode from boardSpecNode to file
														JavaSDM.ensure(file
																.equals(boardSpecNode
																		.getFile()));

														// attribute condition
														JavaSDM.ensure(dimensionNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(columnCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowLeftColumnNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(rowsNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(rowCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(boardNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(colsNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardNode
																		.getName(),
																"BOARD") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardSpecNode
																		.getName(),
																"BOARD_SPEC") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																colsNode.getName(),
																"COLS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowLeftColumnNode
																		.getName(),
																"COLUMN") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																dimensionNode
																		.getName(),
																"DIMENSIONS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowNode
																		.getName(),
																"ROW") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																rowsNode.getName(),
																"ROWS") == 0);

														// create object match
														match = TGGRuntimeFactory.eINSTANCE
																.createMatch();

														// statement node 'bookkeeping with generic isAppropriate method'
														fujaba__Success = this
																.isAppropriate_FWD(
																		match,
																		file,
																		boardSpecNode,
																		dimensionNode,
																		rowsNode,
																		colsNode,
																		rowCountNode,
																		columnCountNode,
																		boardNode,
																		topRowNode,
																		topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_11(Node boardNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardSpecNodeToDimensionNode = null;
		Node dimensionNode = null;
		Iterator fujaba__IterBoardNodeToTopRowNode = null;
		Node topRowNode = null;
		File file = null;
		Node boardSpecNode = null;
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
			// bind object
			boardSpecNode = boardNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardNode to topRowNode
			fujaba__Success = false;

			fujaba__IterBoardNodeToTopRowNode = new ArrayList(
					boardNode.getChildren("ROW", 0)).iterator();

			while (fujaba__IterBoardNodeToTopRowNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardNodeToTopRowNode.next();

					// ensure correct type and really bound of object topRowNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					topRowNode = (Node) _TmpObject;
					// check isomorphic binding between objects topRowNode and boardNode 
					JavaSDM.ensure(!topRowNode.equals(boardNode));

					// check isomorphic binding between objects topRowNode and boardSpecNode 
					JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

					// iterate to-many link children from boardSpecNode to dimensionNode
					fujaba__Success = false;

					fujaba__IterBoardSpecNodeToDimensionNode = new ArrayList(
							boardSpecNode.getChildren("DIMENSIONS", 0))
							.iterator();

					while (fujaba__IterBoardSpecNodeToDimensionNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterBoardSpecNodeToDimensionNode
									.next();

							// ensure correct type and really bound of object dimensionNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							dimensionNode = (Node) _TmpObject;
							// check isomorphic binding between objects dimensionNode and boardNode 
							JavaSDM.ensure(!dimensionNode.equals(boardNode));

							// check isomorphic binding between objects dimensionNode and boardSpecNode 
							JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

							// check isomorphic binding between objects topRowNode and dimensionNode 
							JavaSDM.ensure(!topRowNode.equals(dimensionNode));

							// iterate to-many link children from dimensionNode to colsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToColsNode = new ArrayList(
									dimensionNode.getChildren("COLS", 1))
									.iterator();

							while (fujaba__IterDimensionNodeToColsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToColsNode
											.next();

									// ensure correct type and really bound of object colsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									colsNode = (Node) _TmpObject;
									// check isomorphic binding between objects colsNode and boardNode 
									JavaSDM.ensure(!colsNode.equals(boardNode));

									// check isomorphic binding between objects colsNode and boardSpecNode 
									JavaSDM.ensure(!colsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects dimensionNode and colsNode 
									JavaSDM.ensure(!dimensionNode
											.equals(colsNode));

									// check isomorphic binding between objects topRowNode and colsNode 
									JavaSDM.ensure(!topRowNode.equals(colsNode));

									// iterate to-many link children from colsNode to columnCountNode
									fujaba__Success = false;

									fujaba__IterColsNodeToColumnCountNode = new ArrayList(
											colsNode.getChildren(0)).iterator();

									while (fujaba__IterColsNodeToColumnCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterColsNodeToColumnCountNode
													.next();

											// ensure correct type and really bound of object columnCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											columnCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects columnCountNode and boardNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardNode));

											// check isomorphic binding between objects columnCountNode and boardSpecNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects columnCountNode and colsNode 
											JavaSDM.ensure(!columnCountNode
													.equals(colsNode));

											// check isomorphic binding between objects dimensionNode and columnCountNode 
											JavaSDM.ensure(!dimensionNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowNode and columnCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(columnCountNode));

											// iterate to-many link children from dimensionNode to rowsNode
											fujaba__Success = false;

											fujaba__IterDimensionNodeToRowsNode = new ArrayList(
													dimensionNode.getChildren(
															"ROWS", 0))
													.iterator();

											while (fujaba__IterDimensionNodeToRowsNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterDimensionNodeToRowsNode
															.next();

													// ensure correct type and really bound of object rowsNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowsNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowsNode and boardNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardNode));

													// check isomorphic binding between objects rowsNode and boardSpecNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowsNode and colsNode 
													JavaSDM.ensure(!rowsNode
															.equals(colsNode));

													// check isomorphic binding between objects rowsNode and columnCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowsNode and dimensionNode 
													JavaSDM.ensure(!rowsNode
															.equals(dimensionNode));

													// check isomorphic binding between objects topRowNode and rowsNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowsNode));

													// iterate to-many link children from rowsNode to rowCountNode
													fujaba__Success = false;

													fujaba__IterRowsNodeToRowCountNode = new ArrayList(
															rowsNode.getChildren(0))
															.iterator();

													while (fujaba__IterRowsNodeToRowCountNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterRowsNodeToRowCountNode
																	.next();

															// ensure correct type and really bound of object rowCountNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															rowCountNode = (Node) _TmpObject;
															// check isomorphic binding between objects rowCountNode and boardNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardNode));

															// check isomorphic binding between objects rowCountNode and boardSpecNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects rowCountNode and colsNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(colsNode));

															// check isomorphic binding between objects rowCountNode and columnCountNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects rowCountNode and dimensionNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects rowsNode and rowCountNode 
															JavaSDM.ensure(!rowsNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowNode and rowCountNode 
															JavaSDM.ensure(!topRowNode
																	.equals(rowCountNode));

															// iterate to-many link children from topRowNode to topRowLeftColumnNode
															fujaba__Success = false;

															fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
																	topRowNode
																			.getChildren(
																					"COLUMN",
																					0))
																	.iterator();

															while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
																	.hasNext()) {
																try {
																	_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
																			.next();

																	// ensure correct type and really bound of object topRowLeftColumnNode
																	JavaSDM.ensure(_TmpObject instanceof Node);
																	topRowLeftColumnNode = (Node) _TmpObject;
																	// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(boardNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(boardSpecNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(colsNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(columnCountNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(dimensionNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(rowCountNode));

																	// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																	JavaSDM.ensure(!topRowLeftColumnNode
																			.equals(rowsNode));

																	// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																	JavaSDM.ensure(!topRowNode
																			.equals(topRowLeftColumnNode));

																	// attribute condition
																	JavaSDM.ensure(rowCountNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(rowsNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(columnCountNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(dimensionNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(topRowLeftColumnNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(topRowNode
																			.getIndex() == 0);

																	// attribute condition
																	JavaSDM.ensure(colsNode
																			.getIndex() == 1);

																	// attribute condition
																	JavaSDM.ensure(boardNode
																			.getIndex() == 1);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					boardNode
																							.getName(),
																					"BOARD") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					boardSpecNode
																							.getName(),
																					"BOARD_SPEC") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					colsNode.getName(),
																					"COLS") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					topRowLeftColumnNode
																							.getName(),
																					"COLUMN") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					dimensionNode
																							.getName(),
																					"DIMENSIONS") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					topRowNode
																							.getName(),
																					"ROW") == 0);

																	// attribute condition
																	JavaSDM.ensure(JavaSDM
																			.stringCompare(
																					rowsNode.getName(),
																					"ROWS") == 0);

																	// story node 'test core match'
																	try {
																		fujaba__Success = false;

																		// check object boardNode is really bound
																		JavaSDM.ensure(boardNode != null);
																		// check object boardSpecNode is really bound
																		JavaSDM.ensure(boardSpecNode != null);
																		// check object colsNode is really bound
																		JavaSDM.ensure(colsNode != null);
																		// check object columnCountNode is really bound
																		JavaSDM.ensure(columnCountNode != null);
																		// check object dimensionNode is really bound
																		JavaSDM.ensure(dimensionNode != null);
																		// check object file is really bound
																		JavaSDM.ensure(file != null);
																		// check object rowCountNode is really bound
																		JavaSDM.ensure(rowCountNode != null);
																		// check object rowsNode is really bound
																		JavaSDM.ensure(rowsNode != null);
																		// check object ruleresult is really bound
																		JavaSDM.ensure(ruleresult != null);
																		// check object topRowLeftColumnNode is really bound
																		JavaSDM.ensure(topRowLeftColumnNode != null);
																		// check object topRowNode is really bound
																		JavaSDM.ensure(topRowNode != null);
																		// check isomorphic binding between objects boardSpecNode and boardNode 
																		JavaSDM.ensure(!boardSpecNode
																				.equals(boardNode));

																		// check isomorphic binding between objects colsNode and boardNode 
																		JavaSDM.ensure(!colsNode
																				.equals(boardNode));

																		// check isomorphic binding between objects columnCountNode and boardNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(boardNode));

																		// check isomorphic binding between objects dimensionNode and boardNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(boardNode));

																		// check isomorphic binding between objects rowCountNode and boardNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(boardNode));

																		// check isomorphic binding between objects rowsNode and boardNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(boardNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(boardNode));

																		// check isomorphic binding between objects topRowNode and boardNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(boardNode));

																		// check isomorphic binding between objects colsNode and boardSpecNode 
																		JavaSDM.ensure(!colsNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects columnCountNode and boardSpecNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects dimensionNode and boardSpecNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects rowCountNode and boardSpecNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects rowsNode and boardSpecNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects topRowNode and boardSpecNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(boardSpecNode));

																		// check isomorphic binding between objects columnCountNode and colsNode 
																		JavaSDM.ensure(!columnCountNode
																				.equals(colsNode));

																		// check isomorphic binding between objects dimensionNode and colsNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(colsNode));

																		// check isomorphic binding between objects rowCountNode and colsNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(colsNode));

																		// check isomorphic binding between objects rowsNode and colsNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(colsNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(colsNode));

																		// check isomorphic binding between objects topRowNode and colsNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(colsNode));

																		// check isomorphic binding between objects dimensionNode and columnCountNode 
																		JavaSDM.ensure(!dimensionNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowCountNode and columnCountNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowsNode and columnCountNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects topRowNode and columnCountNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(columnCountNode));

																		// check isomorphic binding between objects rowCountNode and dimensionNode 
																		JavaSDM.ensure(!rowCountNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects rowsNode and dimensionNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects topRowNode and dimensionNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(dimensionNode));

																		// check isomorphic binding between objects rowsNode and rowCountNode 
																		JavaSDM.ensure(!rowsNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowNode and rowCountNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(rowCountNode));

																		// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																		JavaSDM.ensure(!topRowLeftColumnNode
																				.equals(rowsNode));

																		// check isomorphic binding between objects topRowNode and rowsNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(rowsNode));

																		// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																		JavaSDM.ensure(!topRowNode
																				.equals(topRowLeftColumnNode));

																		// check link children from topRowNode to boardNode
																		JavaSDM.ensure(boardNode
																				.equals(topRowNode
																						.getParentNode()));

																		// check link children from boardNode to boardSpecNode
																		JavaSDM.ensure(boardSpecNode
																				.equals(boardNode
																						.getParentNode()));

																		// check link children from dimensionNode to boardSpecNode
																		JavaSDM.ensure(boardSpecNode
																				.equals(dimensionNode
																						.getParentNode()));

																		// check link children from columnCountNode to colsNode
																		JavaSDM.ensure(colsNode
																				.equals(columnCountNode
																						.getParentNode()));

																		// check link children from colsNode to dimensionNode
																		JavaSDM.ensure(dimensionNode
																				.equals(colsNode
																						.getParentNode()));

																		// check link children from rowsNode to dimensionNode
																		JavaSDM.ensure(dimensionNode
																				.equals(rowsNode
																						.getParentNode()));

																		// check link children from rowCountNode to rowsNode
																		JavaSDM.ensure(rowsNode
																				.equals(rowCountNode
																						.getParentNode()));

																		// check link children from topRowLeftColumnNode to topRowNode
																		JavaSDM.ensure(topRowNode
																				.equals(topRowLeftColumnNode
																						.getParentNode()));

																		// check link rootNode from boardSpecNode to file
																		JavaSDM.ensure(file
																				.equals(boardSpecNode
																						.getFile()));

																		// attribute condition
																		JavaSDM.ensure(topRowNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(rowsNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(topRowLeftColumnNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(dimensionNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(rowCountNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(columnCountNode
																				.getIndex() == 0);

																		// attribute condition
																		JavaSDM.ensure(boardNode
																				.getIndex() == 1);

																		// attribute condition
																		JavaSDM.ensure(colsNode
																				.getIndex() == 1);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						boardNode
																								.getName(),
																						"BOARD") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						boardSpecNode
																								.getName(),
																						"BOARD_SPEC") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						colsNode.getName(),
																						"COLS") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						topRowLeftColumnNode
																								.getName(),
																						"COLUMN") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						dimensionNode
																								.getName(),
																						"DIMENSIONS") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						topRowNode
																								.getName(),
																						"ROW") == 0);

																		// attribute condition
																		JavaSDM.ensure(JavaSDM
																				.stringCompare(
																						rowsNode.getName(),
																						"ROWS") == 0);

																		// create object match
																		match = TGGRuntimeFactory.eINSTANCE
																				.createMatch();

																		// statement node 'bookkeeping with generic isAppropriate method'
																		fujaba__Success = this
																				.isAppropriate_FWD(
																						match,
																						file,
																						boardSpecNode,
																						dimensionNode,
																						rowsNode,
																						colsNode,
																						rowCountNode,
																						columnCountNode,
																						boardNode,
																						topRowNode,
																						topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_12(Node topRowNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterTopRowNodeToTopRowLeftColumnNode = null;
		Node topRowLeftColumnNode = null;
		Iterator fujaba__IterBoardSpecNodeToDimensionNode = null;
		Node dimensionNode = null;
		File file = null;
		Node boardSpecNode = null;
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

			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);
			// bind object
			boardNode = topRowNode.getParentNode();

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// bind object
			boardSpecNode = boardNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to dimensionNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToDimensionNode = new ArrayList(
					boardSpecNode.getChildren("DIMENSIONS", 0)).iterator();

			while (fujaba__IterBoardSpecNodeToDimensionNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToDimensionNode
							.next();

					// ensure correct type and really bound of object dimensionNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					dimensionNode = (Node) _TmpObject;
					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardSpecNode 
					JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

					// check isomorphic binding between objects topRowNode and dimensionNode 
					JavaSDM.ensure(!topRowNode.equals(dimensionNode));

					// iterate to-many link children from topRowNode to topRowLeftColumnNode
					fujaba__Success = false;

					fujaba__IterTopRowNodeToTopRowLeftColumnNode = new ArrayList(
							topRowNode.getChildren("COLUMN", 0)).iterator();

					while (fujaba__IterTopRowNodeToTopRowLeftColumnNode
							.hasNext()) {
						try {
							_TmpObject = fujaba__IterTopRowNodeToTopRowLeftColumnNode
									.next();

							// ensure correct type and really bound of object topRowLeftColumnNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							topRowLeftColumnNode = (Node) _TmpObject;
							// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
							JavaSDM.ensure(!topRowLeftColumnNode
									.equals(boardNode));

							// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
							JavaSDM.ensure(!topRowLeftColumnNode
									.equals(boardSpecNode));

							// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
							JavaSDM.ensure(!topRowLeftColumnNode
									.equals(dimensionNode));

							// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
							JavaSDM.ensure(!topRowNode
									.equals(topRowLeftColumnNode));

							// iterate to-many link children from dimensionNode to colsNode
							fujaba__Success = false;

							fujaba__IterDimensionNodeToColsNode = new ArrayList(
									dimensionNode.getChildren("COLS", 1))
									.iterator();

							while (fujaba__IterDimensionNodeToColsNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterDimensionNodeToColsNode
											.next();

									// ensure correct type and really bound of object colsNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									colsNode = (Node) _TmpObject;
									// check isomorphic binding between objects colsNode and boardNode 
									JavaSDM.ensure(!colsNode.equals(boardNode));

									// check isomorphic binding between objects colsNode and boardSpecNode 
									JavaSDM.ensure(!colsNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects dimensionNode and colsNode 
									JavaSDM.ensure(!dimensionNode
											.equals(colsNode));

									// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
									JavaSDM.ensure(!topRowLeftColumnNode
											.equals(colsNode));

									// check isomorphic binding between objects topRowNode and colsNode 
									JavaSDM.ensure(!topRowNode.equals(colsNode));

									// iterate to-many link children from colsNode to columnCountNode
									fujaba__Success = false;

									fujaba__IterColsNodeToColumnCountNode = new ArrayList(
											colsNode.getChildren(0)).iterator();

									while (fujaba__IterColsNodeToColumnCountNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterColsNodeToColumnCountNode
													.next();

											// ensure correct type and really bound of object columnCountNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											columnCountNode = (Node) _TmpObject;
											// check isomorphic binding between objects columnCountNode and boardNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardNode));

											// check isomorphic binding between objects columnCountNode and boardSpecNode 
											JavaSDM.ensure(!columnCountNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects columnCountNode and colsNode 
											JavaSDM.ensure(!columnCountNode
													.equals(colsNode));

											// check isomorphic binding between objects dimensionNode and columnCountNode 
											JavaSDM.ensure(!dimensionNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
											JavaSDM.ensure(!topRowLeftColumnNode
													.equals(columnCountNode));

											// check isomorphic binding between objects topRowNode and columnCountNode 
											JavaSDM.ensure(!topRowNode
													.equals(columnCountNode));

											// iterate to-many link children from dimensionNode to rowsNode
											fujaba__Success = false;

											fujaba__IterDimensionNodeToRowsNode = new ArrayList(
													dimensionNode.getChildren(
															"ROWS", 0))
													.iterator();

											while (fujaba__IterDimensionNodeToRowsNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterDimensionNodeToRowsNode
															.next();

													// ensure correct type and really bound of object rowsNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowsNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowsNode and boardNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardNode));

													// check isomorphic binding between objects rowsNode and boardSpecNode 
													JavaSDM.ensure(!rowsNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowsNode and colsNode 
													JavaSDM.ensure(!rowsNode
															.equals(colsNode));

													// check isomorphic binding between objects rowsNode and columnCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowsNode and dimensionNode 
													JavaSDM.ensure(!rowsNode
															.equals(dimensionNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowsNode));

													// check isomorphic binding between objects topRowNode and rowsNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowsNode));

													// iterate to-many link children from rowsNode to rowCountNode
													fujaba__Success = false;

													fujaba__IterRowsNodeToRowCountNode = new ArrayList(
															rowsNode.getChildren(0))
															.iterator();

													while (fujaba__IterRowsNodeToRowCountNode
															.hasNext()) {
														try {
															_TmpObject = fujaba__IterRowsNodeToRowCountNode
																	.next();

															// ensure correct type and really bound of object rowCountNode
															JavaSDM.ensure(_TmpObject instanceof Node);
															rowCountNode = (Node) _TmpObject;
															// check isomorphic binding between objects rowCountNode and boardNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardNode));

															// check isomorphic binding between objects rowCountNode and boardSpecNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(boardSpecNode));

															// check isomorphic binding between objects rowCountNode and colsNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(colsNode));

															// check isomorphic binding between objects rowCountNode and columnCountNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(columnCountNode));

															// check isomorphic binding between objects rowCountNode and dimensionNode 
															JavaSDM.ensure(!rowCountNode
																	.equals(dimensionNode));

															// check isomorphic binding between objects rowsNode and rowCountNode 
															JavaSDM.ensure(!rowsNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
															JavaSDM.ensure(!topRowLeftColumnNode
																	.equals(rowCountNode));

															// check isomorphic binding between objects topRowNode and rowCountNode 
															JavaSDM.ensure(!topRowNode
																	.equals(rowCountNode));

															// attribute condition
															JavaSDM.ensure(columnCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(dimensionNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(rowCountNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(rowsNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(topRowLeftColumnNode
																	.getIndex() == 0);

															// attribute condition
															JavaSDM.ensure(boardNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(colsNode
																	.getIndex() == 1);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardNode
																					.getName(),
																			"BOARD") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			boardSpecNode
																					.getName(),
																			"BOARD_SPEC") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			colsNode.getName(),
																			"COLS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowLeftColumnNode
																					.getName(),
																			"COLUMN") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			dimensionNode
																					.getName(),
																			"DIMENSIONS") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			topRowNode
																					.getName(),
																			"ROW") == 0);

															// attribute condition
															JavaSDM.ensure(JavaSDM
																	.stringCompare(
																			rowsNode.getName(),
																			"ROWS") == 0);

															// story node 'test core match'
															try {
																fujaba__Success = false;

																// check object boardNode is really bound
																JavaSDM.ensure(boardNode != null);
																// check object boardSpecNode is really bound
																JavaSDM.ensure(boardSpecNode != null);
																// check object colsNode is really bound
																JavaSDM.ensure(colsNode != null);
																// check object columnCountNode is really bound
																JavaSDM.ensure(columnCountNode != null);
																// check object dimensionNode is really bound
																JavaSDM.ensure(dimensionNode != null);
																// check object file is really bound
																JavaSDM.ensure(file != null);
																// check object rowCountNode is really bound
																JavaSDM.ensure(rowCountNode != null);
																// check object rowsNode is really bound
																JavaSDM.ensure(rowsNode != null);
																// check object ruleresult is really bound
																JavaSDM.ensure(ruleresult != null);
																// check object topRowLeftColumnNode is really bound
																JavaSDM.ensure(topRowLeftColumnNode != null);
																// check object topRowNode is really bound
																JavaSDM.ensure(topRowNode != null);
																// check isomorphic binding between objects boardSpecNode and boardNode 
																JavaSDM.ensure(!boardSpecNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardNode));

																// check isomorphic binding between objects columnCountNode and boardNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects dimensionNode and boardNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowCountNode and boardNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardNode));

																// check isomorphic binding between objects rowsNode and boardNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardNode));

																// check isomorphic binding between objects topRowNode and boardNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardNode));

																// check isomorphic binding between objects colsNode and boardSpecNode 
																JavaSDM.ensure(!colsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and boardSpecNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects dimensionNode and boardSpecNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowCountNode and boardSpecNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects rowsNode and boardSpecNode 
																JavaSDM.ensure(!rowsNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects topRowNode and boardSpecNode 
																JavaSDM.ensure(!topRowNode
																		.equals(boardSpecNode));

																// check isomorphic binding between objects columnCountNode and colsNode 
																JavaSDM.ensure(!columnCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and colsNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowCountNode and colsNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(colsNode));

																// check isomorphic binding between objects rowsNode and colsNode 
																JavaSDM.ensure(!rowsNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(colsNode));

																// check isomorphic binding between objects topRowNode and colsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(colsNode));

																// check isomorphic binding between objects dimensionNode and columnCountNode 
																JavaSDM.ensure(!dimensionNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and columnCountNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowsNode and columnCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects topRowNode and columnCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(columnCountNode));

																// check isomorphic binding between objects rowCountNode and dimensionNode 
																JavaSDM.ensure(!rowCountNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and dimensionNode 
																JavaSDM.ensure(!rowsNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects topRowNode and dimensionNode 
																JavaSDM.ensure(!topRowNode
																		.equals(dimensionNode));

																// check isomorphic binding between objects rowsNode and rowCountNode 
																JavaSDM.ensure(!rowsNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowNode and rowCountNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowCountNode));

																// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
																JavaSDM.ensure(!topRowLeftColumnNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and rowsNode 
																JavaSDM.ensure(!topRowNode
																		.equals(rowsNode));

																// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
																JavaSDM.ensure(!topRowNode
																		.equals(topRowLeftColumnNode));

																// check link children from topRowNode to boardNode
																JavaSDM.ensure(boardNode
																		.equals(topRowNode
																				.getParentNode()));

																// check link children from boardNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(boardNode
																				.getParentNode()));

																// check link children from dimensionNode to boardSpecNode
																JavaSDM.ensure(boardSpecNode
																		.equals(dimensionNode
																				.getParentNode()));

																// check link children from columnCountNode to colsNode
																JavaSDM.ensure(colsNode
																		.equals(columnCountNode
																				.getParentNode()));

																// check link children from colsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(colsNode
																				.getParentNode()));

																// check link children from rowsNode to dimensionNode
																JavaSDM.ensure(dimensionNode
																		.equals(rowsNode
																				.getParentNode()));

																// check link children from rowCountNode to rowsNode
																JavaSDM.ensure(rowsNode
																		.equals(rowCountNode
																				.getParentNode()));

																// check link children from topRowLeftColumnNode to topRowNode
																JavaSDM.ensure(topRowNode
																		.equals(topRowLeftColumnNode
																				.getParentNode()));

																// check link rootNode from boardSpecNode to file
																JavaSDM.ensure(file
																		.equals(boardSpecNode
																				.getFile()));

																// attribute condition
																JavaSDM.ensure(topRowLeftColumnNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowsNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(topRowNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(rowCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(dimensionNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(columnCountNode
																		.getIndex() == 0);

																// attribute condition
																JavaSDM.ensure(boardNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(colsNode
																		.getIndex() == 1);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardNode
																						.getName(),
																				"BOARD") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				boardSpecNode
																						.getName(),
																				"BOARD_SPEC") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				colsNode.getName(),
																				"COLS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowLeftColumnNode
																						.getName(),
																				"COLUMN") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				dimensionNode
																						.getName(),
																				"DIMENSIONS") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				topRowNode
																						.getName(),
																				"ROW") == 0);

																// attribute condition
																JavaSDM.ensure(JavaSDM
																		.stringCompare(
																				rowsNode.getName(),
																				"ROWS") == 0);

																// create object match
																match = TGGRuntimeFactory.eINSTANCE
																		.createMatch();

																// statement node 'bookkeeping with generic isAppropriate method'
																fujaba__Success = this
																		.isAppropriate_FWD(
																				match,
																				file,
																				boardSpecNode,
																				dimensionNode,
																				rowsNode,
																				colsNode,
																				rowCountNode,
																				columnCountNode,
																				boardNode,
																				topRowNode,
																				topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_FWD_Node_13(
			Node topRowLeftColumnNode) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterRowsNodeToRowCountNode = null;
		Node rowCountNode = null;
		Iterator fujaba__IterDimensionNodeToRowsNode = null;
		Node rowsNode = null;
		Iterator fujaba__IterColsNodeToColumnCountNode = null;
		Node columnCountNode = null;
		Iterator fujaba__IterDimensionNodeToColsNode = null;
		Node colsNode = null;
		Iterator fujaba__IterBoardSpecNodeToDimensionNode = null;
		Node dimensionNode = null;
		File file = null;
		Node boardSpecNode = null;
		Node boardNode = null;
		Node topRowNode = null;
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

			// check object topRowLeftColumnNode is really bound
			JavaSDM.ensure(topRowLeftColumnNode != null);
			// bind object
			topRowNode = topRowLeftColumnNode.getParentNode();

			// check object topRowNode is really bound
			JavaSDM.ensure(topRowNode != null);

			// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
			JavaSDM.ensure(!topRowNode.equals(topRowLeftColumnNode));

			// bind object
			boardNode = topRowNode.getParentNode();

			// check object boardNode is really bound
			JavaSDM.ensure(boardNode != null);

			// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardNode));

			// check isomorphic binding between objects topRowNode and boardNode 
			JavaSDM.ensure(!topRowNode.equals(boardNode));

			// bind object
			boardSpecNode = boardNode.getParentNode();

			// check object boardSpecNode is really bound
			JavaSDM.ensure(boardSpecNode != null);

			// check isomorphic binding between objects boardSpecNode and boardNode 
			JavaSDM.ensure(!boardSpecNode.equals(boardNode));

			// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
			JavaSDM.ensure(!topRowLeftColumnNode.equals(boardSpecNode));

			// check isomorphic binding between objects topRowNode and boardSpecNode 
			JavaSDM.ensure(!topRowNode.equals(boardSpecNode));

			// bind object
			file = boardSpecNode.getFile();

			// check object file is really bound
			JavaSDM.ensure(file != null);

			// iterate to-many link children from boardSpecNode to dimensionNode
			fujaba__Success = false;

			fujaba__IterBoardSpecNodeToDimensionNode = new ArrayList(
					boardSpecNode.getChildren("DIMENSIONS", 0)).iterator();

			while (fujaba__IterBoardSpecNodeToDimensionNode.hasNext()) {
				try {
					_TmpObject = fujaba__IterBoardSpecNodeToDimensionNode
							.next();

					// ensure correct type and really bound of object dimensionNode
					JavaSDM.ensure(_TmpObject instanceof Node);
					dimensionNode = (Node) _TmpObject;
					// check isomorphic binding between objects dimensionNode and boardNode 
					JavaSDM.ensure(!dimensionNode.equals(boardNode));

					// check isomorphic binding between objects dimensionNode and boardSpecNode 
					JavaSDM.ensure(!dimensionNode.equals(boardSpecNode));

					// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
					JavaSDM.ensure(!topRowLeftColumnNode.equals(dimensionNode));

					// check isomorphic binding between objects topRowNode and dimensionNode 
					JavaSDM.ensure(!topRowNode.equals(dimensionNode));

					// iterate to-many link children from dimensionNode to colsNode
					fujaba__Success = false;

					fujaba__IterDimensionNodeToColsNode = new ArrayList(
							dimensionNode.getChildren("COLS", 1)).iterator();

					while (fujaba__IterDimensionNodeToColsNode.hasNext()) {
						try {
							_TmpObject = fujaba__IterDimensionNodeToColsNode
									.next();

							// ensure correct type and really bound of object colsNode
							JavaSDM.ensure(_TmpObject instanceof Node);
							colsNode = (Node) _TmpObject;
							// check isomorphic binding between objects colsNode and boardNode 
							JavaSDM.ensure(!colsNode.equals(boardNode));

							// check isomorphic binding between objects colsNode and boardSpecNode 
							JavaSDM.ensure(!colsNode.equals(boardSpecNode));

							// check isomorphic binding between objects dimensionNode and colsNode 
							JavaSDM.ensure(!dimensionNode.equals(colsNode));

							// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
							JavaSDM.ensure(!topRowLeftColumnNode
									.equals(colsNode));

							// check isomorphic binding between objects topRowNode and colsNode 
							JavaSDM.ensure(!topRowNode.equals(colsNode));

							// iterate to-many link children from colsNode to columnCountNode
							fujaba__Success = false;

							fujaba__IterColsNodeToColumnCountNode = new ArrayList(
									colsNode.getChildren(0)).iterator();

							while (fujaba__IterColsNodeToColumnCountNode
									.hasNext()) {
								try {
									_TmpObject = fujaba__IterColsNodeToColumnCountNode
											.next();

									// ensure correct type and really bound of object columnCountNode
									JavaSDM.ensure(_TmpObject instanceof Node);
									columnCountNode = (Node) _TmpObject;
									// check isomorphic binding between objects columnCountNode and boardNode 
									JavaSDM.ensure(!columnCountNode
											.equals(boardNode));

									// check isomorphic binding between objects columnCountNode and boardSpecNode 
									JavaSDM.ensure(!columnCountNode
											.equals(boardSpecNode));

									// check isomorphic binding between objects columnCountNode and colsNode 
									JavaSDM.ensure(!columnCountNode
											.equals(colsNode));

									// check isomorphic binding between objects dimensionNode and columnCountNode 
									JavaSDM.ensure(!dimensionNode
											.equals(columnCountNode));

									// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
									JavaSDM.ensure(!topRowLeftColumnNode
											.equals(columnCountNode));

									// check isomorphic binding between objects topRowNode and columnCountNode 
									JavaSDM.ensure(!topRowNode
											.equals(columnCountNode));

									// iterate to-many link children from dimensionNode to rowsNode
									fujaba__Success = false;

									fujaba__IterDimensionNodeToRowsNode = new ArrayList(
											dimensionNode
													.getChildren("ROWS", 0))
											.iterator();

									while (fujaba__IterDimensionNodeToRowsNode
											.hasNext()) {
										try {
											_TmpObject = fujaba__IterDimensionNodeToRowsNode
													.next();

											// ensure correct type and really bound of object rowsNode
											JavaSDM.ensure(_TmpObject instanceof Node);
											rowsNode = (Node) _TmpObject;
											// check isomorphic binding between objects rowsNode and boardNode 
											JavaSDM.ensure(!rowsNode
													.equals(boardNode));

											// check isomorphic binding between objects rowsNode and boardSpecNode 
											JavaSDM.ensure(!rowsNode
													.equals(boardSpecNode));

											// check isomorphic binding between objects rowsNode and colsNode 
											JavaSDM.ensure(!rowsNode
													.equals(colsNode));

											// check isomorphic binding between objects rowsNode and columnCountNode 
											JavaSDM.ensure(!rowsNode
													.equals(columnCountNode));

											// check isomorphic binding between objects rowsNode and dimensionNode 
											JavaSDM.ensure(!rowsNode
													.equals(dimensionNode));

											// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
											JavaSDM.ensure(!topRowLeftColumnNode
													.equals(rowsNode));

											// check isomorphic binding between objects topRowNode and rowsNode 
											JavaSDM.ensure(!topRowNode
													.equals(rowsNode));

											// iterate to-many link children from rowsNode to rowCountNode
											fujaba__Success = false;

											fujaba__IterRowsNodeToRowCountNode = new ArrayList(
													rowsNode.getChildren(0))
													.iterator();

											while (fujaba__IterRowsNodeToRowCountNode
													.hasNext()) {
												try {
													_TmpObject = fujaba__IterRowsNodeToRowCountNode
															.next();

													// ensure correct type and really bound of object rowCountNode
													JavaSDM.ensure(_TmpObject instanceof Node);
													rowCountNode = (Node) _TmpObject;
													// check isomorphic binding between objects rowCountNode and boardNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardNode));

													// check isomorphic binding between objects rowCountNode and boardSpecNode 
													JavaSDM.ensure(!rowCountNode
															.equals(boardSpecNode));

													// check isomorphic binding between objects rowCountNode and colsNode 
													JavaSDM.ensure(!rowCountNode
															.equals(colsNode));

													// check isomorphic binding between objects rowCountNode and columnCountNode 
													JavaSDM.ensure(!rowCountNode
															.equals(columnCountNode));

													// check isomorphic binding between objects rowCountNode and dimensionNode 
													JavaSDM.ensure(!rowCountNode
															.equals(dimensionNode));

													// check isomorphic binding between objects rowsNode and rowCountNode 
													JavaSDM.ensure(!rowsNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
													JavaSDM.ensure(!topRowLeftColumnNode
															.equals(rowCountNode));

													// check isomorphic binding between objects topRowNode and rowCountNode 
													JavaSDM.ensure(!topRowNode
															.equals(rowCountNode));

													// attribute condition
													JavaSDM.ensure(dimensionNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(topRowLeftColumnNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(rowsNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(rowCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(columnCountNode
															.getIndex() == 0);

													// attribute condition
													JavaSDM.ensure(colsNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(boardNode
															.getIndex() == 1);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardNode.getName(),
															"BOARD") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															boardSpecNode
																	.getName(),
															"BOARD_SPEC") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															colsNode.getName(),
															"COLS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowLeftColumnNode
																	.getName(),
															"COLUMN") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															dimensionNode
																	.getName(),
															"DIMENSIONS") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															topRowNode
																	.getName(),
															"ROW") == 0);

													// attribute condition
													JavaSDM.ensure(JavaSDM.stringCompare(
															rowsNode.getName(),
															"ROWS") == 0);

													// story node 'test core match'
													try {
														fujaba__Success = false;

														// check object boardNode is really bound
														JavaSDM.ensure(boardNode != null);
														// check object boardSpecNode is really bound
														JavaSDM.ensure(boardSpecNode != null);
														// check object colsNode is really bound
														JavaSDM.ensure(colsNode != null);
														// check object columnCountNode is really bound
														JavaSDM.ensure(columnCountNode != null);
														// check object dimensionNode is really bound
														JavaSDM.ensure(dimensionNode != null);
														// check object file is really bound
														JavaSDM.ensure(file != null);
														// check object rowCountNode is really bound
														JavaSDM.ensure(rowCountNode != null);
														// check object rowsNode is really bound
														JavaSDM.ensure(rowsNode != null);
														// check object ruleresult is really bound
														JavaSDM.ensure(ruleresult != null);
														// check object topRowLeftColumnNode is really bound
														JavaSDM.ensure(topRowLeftColumnNode != null);
														// check object topRowNode is really bound
														JavaSDM.ensure(topRowNode != null);
														// check isomorphic binding between objects boardSpecNode and boardNode 
														JavaSDM.ensure(!boardSpecNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardNode 
														JavaSDM.ensure(!colsNode
																.equals(boardNode));

														// check isomorphic binding between objects columnCountNode and boardNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardNode));

														// check isomorphic binding between objects dimensionNode and boardNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardNode));

														// check isomorphic binding between objects rowCountNode and boardNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardNode));

														// check isomorphic binding between objects rowsNode and boardNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardNode));

														// check isomorphic binding between objects topRowNode and boardNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardNode));

														// check isomorphic binding between objects colsNode and boardSpecNode 
														JavaSDM.ensure(!colsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and boardSpecNode 
														JavaSDM.ensure(!columnCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects dimensionNode and boardSpecNode 
														JavaSDM.ensure(!dimensionNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowCountNode and boardSpecNode 
														JavaSDM.ensure(!rowCountNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects rowsNode and boardSpecNode 
														JavaSDM.ensure(!rowsNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowLeftColumnNode and boardSpecNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects topRowNode and boardSpecNode 
														JavaSDM.ensure(!topRowNode
																.equals(boardSpecNode));

														// check isomorphic binding between objects columnCountNode and colsNode 
														JavaSDM.ensure(!columnCountNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and colsNode 
														JavaSDM.ensure(!dimensionNode
																.equals(colsNode));

														// check isomorphic binding between objects rowCountNode and colsNode 
														JavaSDM.ensure(!rowCountNode
																.equals(colsNode));

														// check isomorphic binding between objects rowsNode and colsNode 
														JavaSDM.ensure(!rowsNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowLeftColumnNode and colsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(colsNode));

														// check isomorphic binding between objects topRowNode and colsNode 
														JavaSDM.ensure(!topRowNode
																.equals(colsNode));

														// check isomorphic binding between objects dimensionNode and columnCountNode 
														JavaSDM.ensure(!dimensionNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and columnCountNode 
														JavaSDM.ensure(!rowCountNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowsNode and columnCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and columnCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(columnCountNode));

														// check isomorphic binding between objects topRowNode and columnCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(columnCountNode));

														// check isomorphic binding between objects rowCountNode and dimensionNode 
														JavaSDM.ensure(!rowCountNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and dimensionNode 
														JavaSDM.ensure(!rowsNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowLeftColumnNode and dimensionNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(dimensionNode));

														// check isomorphic binding between objects topRowNode and dimensionNode 
														JavaSDM.ensure(!topRowNode
																.equals(dimensionNode));

														// check isomorphic binding between objects rowsNode and rowCountNode 
														JavaSDM.ensure(!rowsNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowCountNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowNode and rowCountNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowCountNode));

														// check isomorphic binding between objects topRowLeftColumnNode and rowsNode 
														JavaSDM.ensure(!topRowLeftColumnNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and rowsNode 
														JavaSDM.ensure(!topRowNode
																.equals(rowsNode));

														// check isomorphic binding between objects topRowNode and topRowLeftColumnNode 
														JavaSDM.ensure(!topRowNode
																.equals(topRowLeftColumnNode));

														// check link children from topRowNode to boardNode
														JavaSDM.ensure(boardNode
																.equals(topRowNode
																		.getParentNode()));

														// check link children from boardNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(boardNode
																		.getParentNode()));

														// check link children from dimensionNode to boardSpecNode
														JavaSDM.ensure(boardSpecNode
																.equals(dimensionNode
																		.getParentNode()));

														// check link children from columnCountNode to colsNode
														JavaSDM.ensure(colsNode
																.equals(columnCountNode
																		.getParentNode()));

														// check link children from colsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(colsNode
																		.getParentNode()));

														// check link children from rowsNode to dimensionNode
														JavaSDM.ensure(dimensionNode
																.equals(rowsNode
																		.getParentNode()));

														// check link children from rowCountNode to rowsNode
														JavaSDM.ensure(rowsNode
																.equals(rowCountNode
																		.getParentNode()));

														// check link children from topRowLeftColumnNode to topRowNode
														JavaSDM.ensure(topRowNode
																.equals(topRowLeftColumnNode
																		.getParentNode()));

														// check link rootNode from boardSpecNode to file
														JavaSDM.ensure(file
																.equals(boardSpecNode
																		.getFile()));

														// attribute condition
														JavaSDM.ensure(dimensionNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowLeftColumnNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(rowsNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(topRowNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(rowCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(columnCountNode
																.getIndex() == 0);

														// attribute condition
														JavaSDM.ensure(boardNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(colsNode
																.getIndex() == 1);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardNode
																		.getName(),
																"BOARD") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																boardSpecNode
																		.getName(),
																"BOARD_SPEC") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																colsNode.getName(),
																"COLS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowLeftColumnNode
																		.getName(),
																"COLUMN") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																dimensionNode
																		.getName(),
																"DIMENSIONS") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																topRowNode
																		.getName(),
																"ROW") == 0);

														// attribute condition
														JavaSDM.ensure(JavaSDM.stringCompare(
																rowsNode.getName(),
																"ROWS") == 0);

														// create object match
														match = TGGRuntimeFactory.eINSTANCE
																.createMatch();

														// statement node 'bookkeeping with generic isAppropriate method'
														fujaba__Success = this
																.isAppropriate_FWD(
																		match,
																		file,
																		boardSpecNode,
																		dimensionNode,
																		rowsNode,
																		colsNode,
																		rowCountNode,
																		columnCountNode,
																		boardNode,
																		topRowNode,
																		topRowLeftColumnNode);
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
	public IsAppropriateRuleResult isAppropriate_BWD_Board_1(Board board) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Iterator fujaba__IterBoardToTopLeftFloor = null;
		Floor topLeftFloor = null;
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
			// iterate to-many link floors from board to topLeftFloor
			fujaba__Success = false;

			fujaba__IterBoardToTopLeftFloor = new ArrayList(board.getFloors())
					.iterator();

			while (fujaba__IterBoardToTopLeftFloor.hasNext()) {
				try {
					topLeftFloor = (Floor) fujaba__IterBoardToTopLeftFloor
							.next();

					// check object topLeftFloor is really bound
					JavaSDM.ensure(topLeftFloor != null);
					// attribute condition
					JavaSDM.ensure(topLeftFloor.getCol() == 0);

					// attribute condition
					JavaSDM.ensure(topLeftFloor.getRow() == 0);

					// story node 'test core match'
					try {
						fujaba__Success = false;

						// check object board is really bound
						JavaSDM.ensure(board != null);
						// check object ruleresult is really bound
						JavaSDM.ensure(ruleresult != null);
						// check object topLeftFloor is really bound
						JavaSDM.ensure(topLeftFloor != null);
						// check link floors from topLeftFloor to board
						JavaSDM.ensure(board.equals(topLeftFloor.getBoard()));

						// attribute condition
						JavaSDM.ensure(topLeftFloor.getCol() == 0);

						// attribute condition
						JavaSDM.ensure(topLeftFloor.getRow() == 0);

						// create object match
						match = TGGRuntimeFactory.eINSTANCE.createMatch();

						// statement node 'bookkeeping with generic isAppropriate method'
						fujaba__Success = this.isAppropriate_BWD(match, board,
								topLeftFloor);
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
	public IsAppropriateRuleResult isAppropriate_BWD_Floor_4(Floor topLeftFloor) {
		boolean fujaba__Success = false;
		Object _TmpObject = null;
		EClass __eClass = null;
		Iterator fujaba__Iter__eClassTo__performOperation = null;
		EOperation __performOperation = null;
		IsAppropriateRuleResult ruleresult = null;
		Match match = null;
		Board board = null;
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

			// check object topLeftFloor is really bound
			JavaSDM.ensure(topLeftFloor != null);
			// bind object
			board = topLeftFloor.getBoard();

			// check object board is really bound
			JavaSDM.ensure(board != null);

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getCol() == 0);

			// attribute condition
			JavaSDM.ensure(topLeftFloor.getRow() == 0);

			// story node 'test core match'
			try {
				fujaba__Success = false;

				// check object board is really bound
				JavaSDM.ensure(board != null);
				// check object ruleresult is really bound
				JavaSDM.ensure(ruleresult != null);
				// check object topLeftFloor is really bound
				JavaSDM.ensure(topLeftFloor != null);
				// check link floors from topLeftFloor to board
				JavaSDM.ensure(board.equals(topLeftFloor.getBoard()));

				// attribute condition
				JavaSDM.ensure(topLeftFloor.getCol() == 0);

				// attribute condition
				JavaSDM.ensure(topLeftFloor.getRow() == 0);

				// create object match
				match = TGGRuntimeFactory.eINSTANCE.createMatch();

				// statement node 'bookkeeping with generic isAppropriate method'
				fujaba__Success = this.isAppropriate_BWD(match, board,
						topLeftFloor);
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
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD__MATCH_FILE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE:
			return isAppropriate_FWD((Match) arguments.get(0),
					(File) arguments.get(1), (Node) arguments.get(2),
					(Node) arguments.get(3), (Node) arguments.get(4),
					(Node) arguments.get(5), (Node) arguments.get(6),
					(Node) arguments.get(7), (Node) arguments.get(8),
					(Node) arguments.get(9), (Node) arguments.get(10));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR:
			return isAppropriate_BWD((Match) arguments.get(0),
					(Board) arguments.get(1), (Floor) arguments.get(2));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_FILE_0__FILE:
			return isAppropriate_FWD_File_0((File) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_5__NODE:
			return isAppropriate_FWD_Node_5((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_6__NODE:
			return isAppropriate_FWD_Node_6((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_7__NODE:
			return isAppropriate_FWD_Node_7((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_8__NODE:
			return isAppropriate_FWD_Node_8((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_9__NODE:
			return isAppropriate_FWD_Node_9((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_10__NODE:
			return isAppropriate_FWD_Node_10((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_11__NODE:
			return isAppropriate_FWD_Node_11((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_12__NODE:
			return isAppropriate_FWD_Node_12((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_13__NODE:
			return isAppropriate_FWD_Node_13((Node) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_BOARD_1__BOARD:
			return isAppropriate_BWD_Board_1((Board) arguments.get(0));
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_FLOOR_4__FLOOR:
			return isAppropriate_BWD_Floor_4((Floor) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //BoardNodeToBoardRuleImpl

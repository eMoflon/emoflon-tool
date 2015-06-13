package org.moflon.mosl.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.mosl.MOSLUtils;

import ControlFlow.CFNode;
import ControlFlow.CompoundNode;
import ControlFlow.ForEach;
import ControlFlow.HeadControlledLoop;
import ControlFlow.IfStatement;
import ControlFlow.Scope;
import ControlFlow.TailControlledLoop;
import MocaToMoflonTransformation.Exporter;
import MocaToMoflonTransformation.MocaToMoflonTransformationFactory;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.StatementNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.calls.ParameterBinding;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.calls.callExpressions.ParameterExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.expressions.LiteralExpression;
import SDMLanguage.expressions.TextualExpression;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import SDMLanguage.patterns.patternExpressions.ObjectVariableExpression;
import ValidationResult.ValidationReport;

public class ValidatorUntransformer {

	private final Map<String, Node> moslGrammarTreeMap;
	private ResourceSet set;

	protected ValidatorUntransformer(){
		moslGrammarTreeMap = new HashMap<String, Node>();
	}

	public static ValidatorUntransformer init(){
		return new ValidatorUntransformer();
	}

	public void initValidator(final Node tree) {
		this.set = tree.eResource().getResourceSet();
		DemoclesMethodBodyHandler.initResourceSetForDemocles(set);
		
		final Exporter exporter = MocaToMoflonTransformationFactory.eINSTANCE.createExporter();
		exporter.mocaToEcore(tree);

		// Fill resource set
		for (final EPackage outermostPackage : exporter.getEpackages()) {
			// Create resource with URI from package
			final Resource resource = set.createResource(URI.createURI(outermostPackage.getNsURI()));

			// Add package to resource
			resource.getContents().add(outermostPackage);
		}

		//Map<String, Scope> dfsList = new HashMap<String, Scope>();
		for (final EPackage ePackage : exporter.getEpackages()) {
			convertEPackages2MOSLGrammarTree(ePackage);
		}

		//System.err.println(dfsList);
	}

	public Node getTransformedEClassPattern(final String OperationName) {
		Node body = moslGrammarTreeMap.get(OperationName);
		if(body == null){
			body = MocaTreeFactory.eINSTANCE.createNode();
			body.setName("body");
			body.setIndex(0);
		}
		return body;
	}

	private void convertEPackages2MOSLGrammarTree(final EPackage ePackage) {
		for (final EPackage subPackage : ePackage.getESubpackages()) {
			convertEPackages2MOSLGrammarTree(subPackage);
		}

		for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				final EClass eClass = (EClass) eClassifier;
				for (final EOperation eOperation : eClass.getEAllOperations()) {
					final Scope rootScope = getScope(eOperation);
					if(rootScope != null) {
						moslGrammarTreeMap.put(eOperation.getName(), scope2Statements(rootScope));
					}
				}
			}
		}
	}

	private Node scope2Statements(final Scope rootScope){
		final Node bodyNode = MocaTreeFactory.eINSTANCE.createNode();
		bodyNode.setName("body");
		evaluateScope(rootScope, bodyNode);
		return bodyNode;
	}


	private void evaluateScope(final Scope scope, final Node node){
		final Node stmtList = createNode("stmt_list", 0, node);
		if (scope.getContents() != null && scope.getContents().size()>=1) {
			evaluateStatement(scope.getContents().get(0), stmtList);
		}
	}

	private void evaluateStatement(CFNode statement, final Node node){
		int id = 0;
		do {
			final Node stmt = createNode("stmt", id, node);
			id++;
			if (statement instanceof IfStatement) {
				ifStatement((IfStatement) statement, stmt);
			} else if (statement instanceof ForEach) {
				forEach((ForEach) statement, stmt);
			} else if (statement instanceof HeadControlledLoop){
				whileLoop((HeadControlledLoop)statement, stmt);
			} else if (statement instanceof TailControlledLoop) {
				doWhileLoop((TailControlledLoop)statement, stmt);
			} else {
				simpleStatement(statement, stmt);
			}
			statement=statement.getNext();
		} while(statement != null);
	}

	private void simpleStatement(final CFNode statement, final Node node){
		final Node simpleStmt = createNode("simple_stmt", 0, node);
		if (statement.getOrigin() instanceof StopNode) {
			returnStatement((StopNode) statement.getOrigin(), simpleStmt);
		} else { 
			callOrPatternStatement(statement, simpleStmt, 0);
		}
	}

	private void callOrPatternStatement(final CFNode statement, final Node node, final int index){
		if (statement.getOrigin() instanceof StatementNode) {
			callStatement((StatementNode) statement.getOrigin(), node);
		} else {
			patternStatement(statement.getOrigin(), node, index);
		}
	}

	private void ifStatement(final IfStatement statement, final Node node){
		// create if statement node
		final Node ifStmt = createNode("if_stmt", 0, node);

		// pattern
		callOrPatternStatement(statement, ifStmt, 0);

		// Success part of a SDM
		internalCompound(statement, ifStmt, "if", 0, 1);

		if (statement.getScopes()!=null && statement.getScopes().get(1)!=null && statement.getScopes().get(1).getContents()!= null && statement.getScopes().get(1).getContents().size()>=1) {
			// failure part of a SDM
			internalCompound(statement, ifStmt, "else", 1, 2);
		}
	}

	private void forEach(final ForEach statement, final Node node){
		// create for each statement node
		final Node forEachNode = createNode("foreach_stmt", 0, node);

		// pattern
		patternStatement(statement.getOrigin(), forEachNode, 0);

		// internal part of a ForEach loop
		if(statement.getScopes() != null && statement.getScopes().get(0).getContents().size()>0) {
			internalCompound(statement, forEachNode, "eachTime", 0, 1);
		}
	}

	private void doWhileLoop(final TailControlledLoop statement, final Node node){
		// create do statement node
		final Node doNode = createNode("do_stmt", 0, node);

		// internal part of a do loop
		if (statement.getScopes() != null && statement.getScopes().get(0).getContents().size()>0) {
			internalCompound(statement, doNode, "do_loop", 0, 0);
		}

		//pattern
		patternStatement(statement.getOrigin(), doNode, 1);
	}

	private void whileLoop(final HeadControlledLoop statement, final Node node){
		// create while statement node
		final Node whileNode = createNode("while_stmt", 0, node);

		//pattern
		patternStatement(statement.getOrigin(), whileNode, 0);

		//internal part of a while loop
		if (statement.getScopes() != null && statement.getScopes().get(0).getContents().size()>0) {
			internalCompound(statement, whileNode, "while_loop", 0, 1);
		}
	}

	private void patternStatement(final ActivityNode pattern, final Node node, final int index){
		final Node patternStmt = createNode("pattern_stmt", index, node);
		createNode(MOSLUtils.strip(pattern.getName()), 0, patternStmt);
	}

	private void callStatement(final StatementNode pattern, final Node node){
		final Node callStmt = createNode("call_stmt", 0, node);
		evaluateExpression(pattern.getStatementExpression(), callStmt, 0); 
	}

	private void returnStatement(final StopNode stopNode, final Node node){
		final Node returnStmt = createNode("return_stmt", 0, node);
		if (stopNode.getReturnValue() == null) {
			createNode(" ", 0, returnStmt);
		} else { 
			evaluateExpression(stopNode.getReturnValue(), returnStmt, 0);
		}
	}

	private void evaluateExpression(final Expression expression, final Node node, final int index){
		if(expression instanceof LiteralExpression){
			final LiteralExpression literalExpression = (LiteralExpression)expression;
			final Node litExp = createNode("LiteralExpression", index, node);
			createNode(literalExpression.getValue(), 0, litExp);
		}
		else if (expression instanceof AttributeValueExpression){
			final AttributeValueExpression attributeValueExpression = (AttributeValueExpression) expression;
			final Node attExp =  createNode("AttributeExpression", index, node);
			createNode(attributeValueExpression.getObject().getName(), 0, attExp);
			createNode(getNameFromUriEObject(attributeValueExpression.getAttribute()), 1, attExp); 
		}
		else if (expression instanceof ParameterExpression){
			final ParameterExpression parameterExpression = (ParameterExpression) expression;
			final Node parExp =  createNode("ParameterExpression", index, node);
			createNode(getNameFromUriEObject(parameterExpression.getParameter()), 0, parExp);
		}
		else if (expression instanceof MethodCallExpression){
			final Node mce = createNode("MethodCallExpression", index, node);
			final MethodCallExpression methodCallExpression = (MethodCallExpression) expression;
			evaluateExpression(methodCallExpression.getTarget(), mce, 0);
			createNode(getNameFromUriEObject(methodCallExpression.getCallee()), 1, mce);
			int i=2;
			for (final ParameterBinding param : methodCallExpression.getOwnedParameterBindings()){	            
				evaluateExpression(param.getValueExpression(), mce, i);
				i++;
			}

		}
		else if (expression instanceof ObjectVariableExpression){
			final ObjectVariableExpression objectVariableExpression = (ObjectVariableExpression) expression;
			final Node ove = createNode("ObjectVariableExpression", index, node);
			createNode(objectVariableExpression.getObject().getName(), 0, ove);
		}
		else if (expression instanceof TextualExpression){
			final TextualExpression textualExpression = (TextualExpression) expression;
			final Node te = createNode("TextualExpression", index, node);
			createNode(textualExpression.getExpressionText(), 0, te);
		}
	}

	private void internalCompound(final CompoundNode statement, final Node node, final String nodeName, final int indexOfScope, final int indexOfNode){
		final Scope internal = statement.getScopes().get(indexOfScope);
		final Node internalNode = createNode(nodeName, indexOfNode, node);
		if (internal != null) {
			evaluateScope(internal, internalNode);
		}
	}   

	private Node createNode(final String name, final int index, final Node parent){
		final Node node = MocaTreeFactory.eINSTANCE.createNode();
		node.setName(name);
		node.setIndex(index);
		node.setParentNode(parent);
		return node;
	}

	private Scope getScope(final EOperation eOperation) {
		final Activity sdm = DemoclesMethodBodyHandler.lookupActivity(set, eOperation);
		if (sdm != null) {
			final ValidationReport validationReport =
					DemoclesMethodBodyHandler.performControlFlowValidation(set, eOperation, sdm);
			final EObject result = validationReport.getResult();
			if (validationReport.getErrorMessages().isEmpty() && result instanceof Scope) {
				return (Scope) result;
			}
		}
		return null;
	}

	private String getNameFromUriEObject(final EObject eObject){
		final String uri=eMoflonEMFUtil.getName(eObject);
		final String [] parts = uri.split("/");
		return parts[parts.length-1];
	}
}

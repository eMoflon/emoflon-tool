package org.moflon.maave.tool.smt.solverutil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;

public class OldPredicateTransformer extends PredicateSymbolSwitch implements IPredicateTransformer{
	protected static final String SUBTRACTION_FUN="sub";
	protected static final String ADDITION_FUN="add";
	protected static final String LARGER_EQUAL_FUN="largerEqual";
	protected static final String LARGER_FUN="Larger";
	protected static final String SMALLER_EQUAL_FUN="smallerEqual";
	protected static final String SMALLER_FUN="smaller";
	protected static final String EQUAL_FUN="equal";
	protected static final String CONST_TRUE_FUN="true";
	protected static final String CONST_FALSE_FUN="false";
	protected static final String SUBTRACTION_CH_FUN="isSub";
	protected static final String ADDITION_CH_FUN="isAdd";
	protected static final String LARGER_EQUAL_CH_FUN="isLargerEqual";
	protected static final String LARGER_CH_FUN="isLarger";
	protected static final String SMALLER_EQUAL_CH_FUN="isSmallerEqual";
	protected static final String SMALLER_CH_FUN="isSmaller";
	protected static final String EQUAL_CH_FUN="isEqual";
	


	private HashMap<String, String>funcDefinitions=new HashMap<String, String>();
	private HashSet<String>variableDeclarations=new HashSet<String>();
	private STGroup stg;

	public OldPredicateTransformer() {
		stg = new STGroup('<', '>');
		stg.loadGroupFile("/","file:/"+System.getenv("CurrentWSLoc")+"/SMTSolverUtils/templates/predicates.stg");
	}

	public Collection<String> getFunctionDefinitions(){
		return funcDefinitions.values();		
	}
	public Collection<String> getVariableDeclarations(){
		return variableDeclarations.stream().collect(Collectors.toList());	
	}

	public String transformPredicate(Predicate predicate,Mapping<LabelNode> labelNodeSubstMap){
		String functionSymb =doSwitch(predicate);
		String operator =predicate.getSymbol();
		List<String> paramTypes=new LinkedList<String>();
		List<String> paramNames=new LinkedList<String>();
		List<String> variableNames=new LinkedList<String>();
		
		for (int i=0; i< predicate.getParameters().size(); i++) {
			Parameter param=predicate.getParameters().get(i);
			String paramType=ParameterTypeTransformer.getSMTLibDatatypeString(param.getType());
			paramTypes.add(paramType);
			paramNames.add("x!"+i);
			if(param instanceof LabelNode){
				String label;
				LabelNode labelNode=(LabelNode) param;
				label=labelNodeSubstMap.imageOf(labelNode).getLabel();
				addVariableDecl(label,paramType);
				variableNames.add(label);
			}else{
				Constant constant = (Constant) param;
				variableNames.add(constant.getInterpretation());
			}
			
				
			
		}
		addFunctionDef(functionSymb, operator, paramNames, paramTypes);
		for (int i = 0; i < variableNames.size(); i++) {
			
		}
		return getFuncInvoc(functionSymb, variableNames);
	}


		
	
	private String getFuncInvoc(String functionName,List<String> parameterNames){
		ST st = stg.getInstanceOf("funcInvoc");
		st.add("functionName", functionName);
		st.add("parameterNames", parameterNames);
		return st.render();
	}
	
	private void addVariableDecl(String varName, String varType){
		ST st = stg.getInstanceOf("varDecl");
		st.add("varName", varName);
		st.add("varType", varType);
		String result=st.render();
		variableDeclarations.add(result);
	}
	private void addFunctionDef(String functionName,String operator,List<String> paramNames,List<String>paramTypes ){

		String declaration=paramTypes.stream().reduce(functionName , (a,b)->a+b);

		if(!funcDefinitions.containsKey(declaration)){
			ST st=null;
			switch (paramNames.size()) {
			case 0:{}break;
			case 1:{}break;
			case 2:{st = stg.getInstanceOf("twoValPred");}break;
			default:{st = stg.getInstanceOf("multiValPred");}break;
			}
			if(st!=null){
				st.add("symbol", functionName);
				st.add("operator", operator);
				st.add("paramTypes", paramTypes);
				st.add("params", paramNames);
				String definition= st.render();
				funcDefinitions.put(declaration, definition);
			}
		}
	}

	@Override
	protected String caseSub(Predicate predicate) {
		if(predicate.getParameters().size()>=3){
			return SUBTRACTION_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Subtraction needs at least 3 parameters");
		}
	}

	@Override
	protected String caseAdd(Predicate predicate) {
		if(predicate.getParameters().size()>=3){

			return ADDITION_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Addition needs at least 3 parameters");
		}
	}

	@Override
	protected String caseLargerEqual(Predicate predicate) {
		if(predicate.getParameters().size()==2){
			return LARGER_EQUAL_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; LargerEqual requires exact 2 parameters");
		}
	}

	@Override
	protected String caseSmallerEqual(Predicate predicate) {
		if(predicate.getParameters().size()==2){
			return SMALLER_EQUAL_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; SmallerEqual requires exact 2 parameters");
		}
	}

	@Override
	protected String caseLarger(Predicate predicate) {
		if(predicate.getParameters().size()==2){
			return LARGER_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Larger requires exact 2 parameters");
		}
	}

	@Override
	protected String caseSmaller(Predicate predicate) {
		if(predicate.getParameters().size()==2){
			return SMALLER_FUN ;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Smaller requires exact 2 parameters");
		}
	}



	@Override
	protected String caseEqual(Predicate predicate) {
		if(predicate.getParameters().size()==2){
			return EQUAL_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Equal requires exact 2 parameters");
		}
	}


	@Override
	protected String caseConstFalse(Predicate predicate) {
		if(predicate.getParameters().size()==0){
			return CONST_FALSE_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Constant FALSE requires exact 0 parameters");
		}
	}

	@Override
	protected String caseConstTrue(Predicate predicate) {
		if(predicate.getParameters().size()==0){
			return CONST_TRUE_FUN;
		}else{
			throw new UnsupportedOperationException("Malformed Predicate "+predicate+"; Constant TRUE requires exact 0 parameters");
		}
	}

	@Override
	protected String defaultCase(Predicate predicate) {
		throw new UnsupportedOperationException("Unsupported Predicate. The "+predicate+" is currently not supported");

	}

	@Override
	protected String caseImplChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseSubChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseAddChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseLargerEqualChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseSmallerEqualChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseLargerChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseSmallerChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String caseEqualChFun(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

   @Override
   public Collection<String> getSortDeclarations()
   {
      // TODO Auto-generated method stub
      return null;
   }
}

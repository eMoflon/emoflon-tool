package org.moflon.maave.tool.smt.solverutil;

import java.util.List;
import java.util.stream.Collectors;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.IdentityMapping;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;

public class SymbFormulaToSMTLibTransformer {

	private IPredicateTransformer predicateTransformer =new PredicateTransformer();
	private STGroup stg;
	
	public SymbFormulaToSMTLibTransformer() {
		stg = new STGroup('<', '>');
		stg.loadGroupFile("/","file:/"+System.getenv("CurrentWSLoc")+"/org.moflon.maave.tool.smt.solverutil/templates/logic.stg");
	}
	
public String transformBiImplication(Disjunction term1, Disjunction term2, Mapping<LabelNode> term2VariableSubstMap) {
      
      
      ST st;
      st = stg.getInstanceOf("checkUnsat");
      st.add("term", internalTransformBiImplication(term1,term2,term2VariableSubstMap));
      st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
      st.add("varDecls", predicateTransformer.getVariableDeclarations());
      return st.render();
   }
	
	public String transformImplication(Disjunction premise, Disjunction conclusion, Mapping<LabelNode> conclusionVariableSubstMap) {
		
		
		ST st;
		st = stg.getInstanceOf("checkUnsat");
		st.add("term", internalTransformImplication(premise,conclusion,conclusionVariableSubstMap));
		st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
		st.add("varDecls", predicateTransformer.getVariableDeclarations());
//		System.out.println("SymbFormulaToSMTLibTransformer/tranformImplication");
//		System.out.println(st.render());
		return st.render();
	}
	private String internalTransformBiImplication(Disjunction term1, Disjunction term2, Mapping<LabelNode> term2VariableSubstMap){
      ST st;
      st = stg.getInstanceOf("biImplication");
      
      st.add("term1", internalTransformDisjunction(term1,new IdentityMapping<LabelNode>()));
      st.add("term2", internalTransformDisjunction(term2,term2VariableSubstMap));
      return st.render();
   }
	
	private String internalTransformImplication(Disjunction premise, Disjunction conclusion, Mapping<LabelNode> labelNodeSubstMap){
		ST st;
		st = stg.getInstanceOf("implication");
		st.add("premise", internalTransformDisjunction(premise,new IdentityMapping<LabelNode>()));
		st.add("conclusion", internalTransformDisjunction(conclusion,labelNodeSubstMap));
		return st.render();
	}
	public String transformDisjunction(Disjunction disjunction){
	   ST st;
      st = stg.getInstanceOf("checkSat");
      st.add("term", internalTransformDisjunction(disjunction, new IdentityMapping<LabelNode>()));
      st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
      st.add("varDecls", predicateTransformer.getVariableDeclarations());
      return st.render();
	}
	private String internalTransformDisjunction(Disjunction disjunction,Mapping<LabelNode> labelNodeSubstMap){
		
	   
	   List<String> conjunctions=disjunction.getOf().stream().map(conj->transformConjunction(conj,labelNodeSubstMap)).collect(Collectors.toList());
		ST st;
		if(disjunction.getQuantifier()!=null){
		   
		   List<String>exVarDefs=disjunction.getQuantifier().getLabelNodes().stream().map(ln->"("+ln.getLabel()+" "+ParameterTypeTransformer.getSMTLibDatatypeString(ln.getType())+")").collect(Collectors.toList());
		   st = stg.getInstanceOf("disjunctionExists");
		   st.add("varDefs", exVarDefs);
	     
      }else{
         
         st = stg.getInstanceOf("disjunction");
      }
		st.add("conjunctions", conjunctions);
		return st.render();
	}
	private String transformConjunction(Conjunction conjunction,Mapping<LabelNode> labelNodeSubstMap){
		List<String> predicates;
		predicates=conjunction.getOf().stream().map(pred->predicateTransformer.transformPredicate(pred,labelNodeSubstMap)).collect(Collectors.toList());
			
		ST st;
		st = stg.getInstanceOf("conjunction");
		st.add("predicates", predicates);
		return st.render();
	}

	
	
}

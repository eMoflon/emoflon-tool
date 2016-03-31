package org.moflon.maave.tool.smt.solverutil;

import java.util.List;
import java.util.stream.Collectors;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.IdentityMapping;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class FormulaToSMTLibTransformer implements IFormulaTransformer {

	private IPredicateTransformer predicateTransformer;
	private STGroup stg;

	private static final String IMPLICATION="=>";
	private static final String BI_IMPLICATION="=";
	
	public FormulaToSMTLibTransformer() {
		this.stg = new STGroup('<', '>');
		this.stg.loadGroupFile("/","file:/"+System.getenv("CurrentWSLoc")+"/org.moflon.maave.tool.smt.solverutil/templates/logic.stg");
		
		this.predicateTransformer=new PredicateTransformer2();
	}
	
/* (non-Javadoc)
 * @see org.moflon.maave.tool.smt.solverutil.IFormulaTransformer#transformBiImplication(org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction, org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction, org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping)
 */
@Override
public String transformBiImplication(Disjunction term1, Disjunction term2, Mapping<LabelNode> term2VariableSubstMap) {
      ST st;
      st = stg.getInstanceOf("checkUnsat");
      st.add("term", internalTransformBiImplication(term1,term2,term2VariableSubstMap));
      st.add("sortDecl", predicateTransformer.getSortDeclarations());
      st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
      st.add("varDecls", predicateTransformer.getVariableDeclarations());
      return st.render();
   }
@Override
public String transformDisjunctionSat(Disjunction disjunction){
   ST st;
   st = stg.getInstanceOf("checkSat");
   st.add("term", internalTransformDisjunction(disjunction, new IdentityMapping<LabelNode>()));
   st.add("sortDecl", predicateTransformer.getSortDeclarations());
   st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
   st.add("varDecls", predicateTransformer.getVariableDeclarations());
   return st.render();
}
/* (non-Javadoc)
 * @see org.moflon.maave.tool.smt.solverutil.IFormulaTransformer#transformDisjunctionUnsat(org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction)
 */
@Override
public String transformDisjunctionUnsat(Disjunction disjunction){
   ST st;
   st = stg.getInstanceOf("checkUnsat");
   st.add("term", internalTransformDisjunction(disjunction, new IdentityMapping<LabelNode>()));
   st.add("sortDecl", predicateTransformer.getSortDeclarations());
   st.add("funcDefs", predicateTransformer.getFunctionDefinitions());
   st.add("varDecls", predicateTransformer.getVariableDeclarations());
   return st.render();
}
	
	/* (non-Javadoc)
    * @see org.moflon.maave.tool.smt.solverutil.IFormulaTransformer#transformImplication(org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction, org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction, org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping)
    */
	@Override
   public String transformImplication(Disjunction premise, Disjunction conclusion, Mapping<LabelNode> conclusionVariableSubstMap) {
		
		
		ST st;
		st = stg.getInstanceOf("checkUnsat");
		st.add("term", internalTransformImplication(premise,conclusion,conclusionVariableSubstMap));
		st.add("sortDecl", predicateTransformer.getSortDeclarations());
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
	/* (non-Javadoc)
    * @see org.moflon.maave.tool.smt.solverutil.IFormulaTransformer#transformDisjunctionSat(org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction)
    */
	
	
	
	private String internalTransformDisjunction(Disjunction disjunction,Mapping<LabelNode> labelNodeSubstMap){
		
	   
	   List<String> conjunctions=disjunction.getOf().stream().map(conj->transformConjunction(conj,labelNodeSubstMap)).collect(Collectors.toList());
		ST st;
		if(disjunction.getQuantifier()!=null){
		   List<String> exVarDefs = predicateTransformer.getQuantifiedVarDef(disjunction);
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

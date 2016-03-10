package org.moflon.maave.tool.smt.solverutil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import org.moflon.maave.tool.smt.constraintlib.PredicateSpecification;
import org.moflon.maave.tool.smt.constraintlib.SMTConstraintLibrary;
import org.moflon.maave.tool.smt.constraintlib.SMTLibPredicateSpecification;
import org.moflon.maave.tool.smt.constraintlib.util.SMTConstraintLibUtil;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Quantifier;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

public class PredicateTransformer implements IPredicateTransformer {

   private HashMap<String, String>funcDefinitions=new HashMap<String, String>();
   private HashSet<String>variableDeclarations=new HashSet<String>();
   private STGroup stg;
   private SMTConstraintLibrary constraintLibrary;


   public PredicateTransformer() {
      stg = new STGroup('<', '>');
      stg.loadGroupFile("/","file:/"+System.getenv("CurrentWSLoc")+"/org.moflon.maave.tool.smt.solverutil/templates/predicates.stg");
      loadAndInitSMTConstraintLib();
   }
   private void loadAndInitSMTConstraintLib(){
      constraintLibrary =SMTConstraintLibUtil.getBuildInSMTConstraintLibrary();

   }
   public Collection<String> getFunctionDefinitions(){
      return funcDefinitions.values();		
   }
   public Collection<String> getVariableDeclarations(){
      return variableDeclarations.stream().collect(Collectors.toList());	
   }

   public String transformPredicate(Predicate predicate,Mapping<LabelNode> labelNodeSubstMap){
      PredicateSpecification predicateSpecification=constraintLibrary.lookupPredicateSpecification(predicate);
      if(predicateSpecification==null){
         throw new RuntimeException("Missing predicate specification for predicate with symbol \""+predicate.getSymbol()+
               "\" and parameters"+predicate.getParameters().stream().map(x->x.getType().getName()).reduce(" ",(a,b)->a+", "+b));

      }

      List<String> variableNames=new LinkedList<String>();

      for (int i=0; i< predicate.getParameters().size(); i++) {
         Parameter param=predicate.getParameters().get(i);
         String paramType=ParameterTypeTransformer.getSMTLibDatatypeString(param.getType());
         if(param instanceof LabelNode){
            String label;
            LabelNode labelNode=(LabelNode) param;
            if(labelNode.eContainer() instanceof SymbolicGraph){
               label=labelNodeSubstMap.imageOf(labelNode).getLabel();
               LabelNode imageLabelNode=labelNodeSubstMap.imageOf(labelNode);
               label=label+((SymbolicGraph)imageLabelNode.eContainer()).getLabelNodes().indexOf(imageLabelNode);
               addVariableDecl(label,paramType);
               variableNames.add(label);
               
            }else if(labelNode.eContainer() instanceof Quantifier){
               label=labelNode.getLabel();
               
               label=label+"!"+((Quantifier)labelNode.eContainer()).getLabelNodes().indexOf(labelNode);
               variableNames.add(label);
            }
            
         }else{
            Constant constant = (Constant) param;
            variableNames.add(constant.getInterpretation());
         }



      }
      addFunctionDef(predicateSpecification.getSmtLibPredicateSpecification());
      return getFuncInvoc(predicateSpecification.getSmtLibPredicateSpecification(), variableNames);
   }




   private String getFuncInvoc(SMTLibPredicateSpecification spec,List<String> parameterNames){

      //      System.out.println("/PredicateTransformer/getFuncInvoc:");
      //      System.out.println(spec.getIdentifier()); 
      ST declST = new ST(spec.getIdentifier(), '$', '$');
      for (int i = 0; i < parameterNames.size(); i++)
      {
         declST.add("x"+i, parameterNames.get(i));
      }

      String template =declST.render();
      //    System.out.println(template); 
      return template;
   }

   private void addVariableDecl(String varName, String varType){
      ST st = stg.getInstanceOf("varDecl");
      st.add("varName", varName);
      st.add("varType", varType);
      String result=st.render();
      variableDeclarations.add(result);
   }
   private void addFunctionDef(SMTLibPredicateSpecification spec){

      if(!funcDefinitions.containsKey(spec.getIdentifier())){
         funcDefinitions.put(spec.getIdentifier(), spec.getSmtLibString());
      }
   }
   @Override
   public Collection<String> getSortDeclarations()
   {
      // TODO Auto-generated method stub
      return null;
   }


}

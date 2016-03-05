package org.moflon.maave.tool.symbolicgraphs.secondorder.util;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.maave.tool.smt.solverutil.IAttribSolver;
import org.moflon.maave.tool.smt.solverutil.Z3AttribSolver;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MappingUtil;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.FormulaMode;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MorMappingMode;
import org.moflon.maave.wsconfig.WsInfo;

public class MorphismClassUtil {
	
	
	public static <T> boolean checkMappingMode(Mapping<T> mapping,Collection<T>dom,Collection<T>coDom,MorMappingMode mode) {
		
	   if(MappingUtil.isTotal(mapping, dom)==false)
	   {
	      return false;
	   }
	   
		switch (mode) {
		case INJ: return MappingUtil.isInjective(mapping);	
		case BIJ: return MappingUtil.isBijective(mapping, coDom); 
		case SURJ: return MappingUtil.isSurjective(mapping, coDom);
		case ARBIT: return true;
		default: return false;
			
		}
	}

	public static boolean checkFormulaMode(SymbolicGraphMorphism morphism, FormulaMode formulaMode) {
		switch (formulaMode) {
		case IMPL: return checkImplication(morphism);	
		case BIIMPL: return checkBiImplication(morphism); 
		case PROJ: return checkProjection(morphism);
		case ARBIT: return true;
		default: return false;
			
		}
	}
	
	private static boolean checkBiImplication(SymbolicGraphMorphism morphism) {
		IAttribSolver solver = new Z3AttribSolver();
		return solver.hasEquivalentFormulas(morphism);
	}

	private static boolean checkImplication(SymbolicGraphMorphism morphism) {
		IAttribSolver solver = new Z3AttribSolver();
		return solver.checkImplication(morphism);
		
	}
	
	private static boolean checkProjection(SymbolicGraphMorphism morC_B) {
      
	   //   D----------morD_B---|
	   //   |                   |
	   // morD_C                |
	   //   !                   !
	   //   C------morC_B------>B
	   CategoryUtil catUtil=MatchingUtilsFactory.eINSTANCE.createCategoryUtil();
	   SymbolicGraphMorphism morD_C=catUtil.copyGraph(morC_B.getDom());
	   SymbolicGraphMorphism morD_B=morD_C.composeWith(morC_B);
	   morD_B.getDom().setFormula(FormulaUtil.createFalseFormula());
	   FormulaUtil.disjunctDomFormulawithCodomFormula(morD_B);
	   return checkBiImplication(morD_C);
      
   }

	private static void reportSimilarURIs(EObject typeA, EObject typeB) {
		String uriA = EcoreUtil.getURI(typeA).toString();
		String uriB = EcoreUtil.getURI(typeB).toString();
		if (uriA.equals(uriB)) {
			throw new RuntimeException("Different EClasses with same URI: " + uriA + "\n" + uriB);
		}

	}
	public static boolean isCorrectlyTyped(SymbolicGraphMorphism morphism) {
		
		for (GraphEdge ge : morphism.getDom().getGraphEdges()) {
			if (!(ge.getType() == morphism.imageOf(ge).getType())) {
				if (WsInfo.getVerboseLevel()==6) {
					reportSimilarURIs(ge.getType(), morphism.imageOf(ge).getType());
				}
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(
							" type(" + ge.getDebugId() + ")!=type(f_GE(" + morphism.imageOf(ge).getDebugId() + "))");
				}
				return false;
			}
			if (!(morphism.imageOf(ge.getSource()) == morphism.imageOf(ge).getSource())) {
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(" f_GN s_GE (" + ge.getDebugId() + ")="
							+ morphism.imageOf(ge.getSource()).getDebugId() + " not equal to s_GE f_GE ("
							+ ge.getDebugId() + ")=" + morphism.imageOf(ge).getSource().getDebugId());
				}
				return false;
			}
			if (!(morphism.imageOf(ge.getTarget()) == morphism.imageOf(ge).getTarget())) {
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(" f_GN t_GE (" + ge.getDebugId() + ")="
							+ morphism.imageOf(ge.getTarget()).getDebugId() + " not equal to t_GE f_GE ("
							+ ge.getDebugId() + ")=" + morphism.imageOf(ge).getTarget().getDebugId());
				}
				return false;
			}

		}
		for (LabelEdge le : morphism.getDom().getLabelEdges()) {
			if (!(le.getType() == morphism.imageOf(le).getType())) {

				if (WsInfo.getVerboseLevel()==3) {
					reportSimilarURIs(le.getType(), morphism.imageOf(le).getType());
				}
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(
							" type(" + le.getDebugId() + ")!=type(f_LE(" + morphism.imageOf(le).getDebugId() + "))");
				}
				return false;
			}
			if (!(morphism.imageOf(le.getSource()) == morphism.imageOf(le).getSource())) {
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(" f_GN s_LE (" + le.getDebugId() + ")="
							+ morphism.imageOf(le.getSource()).getDebugId() + " not equal to s_LE f_LE ("
							+ le.getDebugId() + ")=" + morphism.imageOf(le).getSource().getDebugId());
				}
				return false;
			}
			if (!(morphism.imageOf(le.getTarget()) == morphism.imageOf(le).getTarget())) {
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(" f_GN t_LE (" + le.getDebugId() + ")="
							+ morphism.imageOf(le.getTarget()).getLabel() + " not equal to s_LE f_LE ("
							+ le.getDebugId() + ")=" + morphism.imageOf(le).getTarget().getLabel());
				}
				return false;
			}
		}
		for (GraphNode gn : morphism.getDom().getGraphNodes()) {
			if (!(gn.getType() == morphism.imageOf(gn).getType())) {
				if (WsInfo.getVerboseLevel()==3) {
					reportSimilarURIs(gn.getType(), morphism.imageOf(gn).getType());
				}
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(
							" type(" + gn.getDebugId() + ")!=type(f_GN(" + morphism.imageOf(gn).getDebugId() + "))");
				}
				return false;
			}

		}

		for (LabelNode ln : morphism.getDom().getLabelNodes()) {
			if (!(ln.getType() == morphism.imageOf(ln).getType())) {
				if (WsInfo.getVerboseLevel()==3) {
					reportSimilarURIs(ln.getType(), morphism.imageOf(ln).getType());
				}
				if (WsInfo.getVerboseLevel()==6) {
					System.out.println(
							" type(" + ln.getLabel() + ")!=type(f_LN(" + morphism.imageOf(ln).getLabel() + "))");
				}
				return false;
			}

		}
		return true;
	}

	
	
}

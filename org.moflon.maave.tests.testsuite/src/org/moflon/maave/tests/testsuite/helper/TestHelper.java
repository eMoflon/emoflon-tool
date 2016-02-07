package org.moflon.maave.tests.testsuite.helper;



import org.eclipse.emf.ecore.EAttribute;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;



public class TestHelper {

	public static boolean checkMorphismsInRule(SymbGTRule rule){
		CategoryUtil categoryUtil= CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
	   
	   SymbolicGraphMorphism left=rule.getLeft();
		SymbolicGraphMorphism right=rule.getRight();
		SymbolicGraph K=left.getDom();
		assert categoryUtil.isOfClassM(left);
		assert categoryUtil.isOfClassM(right);
		boolean result=true; 
		for (GraphNode gn : K.getGraphNodes()) {
			result=result && (left.imageOf(gn)!=null)&&
					(left.imageOf(gn).getDebugId().equals(gn.getDebugId()))&&
					(right.imageOf(gn)!=null)&&
					(right.imageOf(gn).getDebugId().equals(gn.getDebugId()));
		}
		for (GraphEdge ge : K.getGraphEdges()) {
			result=result && (left.imageOf(ge)!=null)&&
					(left.imageOf(ge).getDebugId().equals(ge.getDebugId()))&&
					(right.imageOf(ge)!=null)&&
					(right.imageOf(ge).getDebugId().equals(ge.getDebugId()));
		}
		for (LabelNode ln: K.getLabelNodes()) {
			result=result && (left.imageOf(ln)!=null)&&
				(left.imageOf(ln).getLabel().equals(ln.getLabel()))&&
				(right.imageOf(ln)!=null)&&
				(right.imageOf(ln).getLabel().equals(ln.getLabel()));
		}
		for (LabelEdge le: K.getLabelEdges()) {
			result=result && (left.imageOf(le)!=null)&&
				(left.imageOf(le).getDebugId().equals(le.getDebugId()))&&
				(right.imageOf(le)!=null)&&
				(right.imageOf(le).getDebugId().equals(le.getDebugId()));
		}
		return result;
	}
	
	public static boolean checkMorphism(SymbolicGraphMorphism mor){
		 
		CategoryUtil categoryHelper= CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
		
		boolean result= categoryHelper.isValidEGraphMorphism(mor);
		
		for (GraphNode gn : mor.getDom().getGraphNodes()) {
			result=result &&(mor.imageOf(gn)!=null)&&
				(mor.imageOf(gn).getDebugId().equals(gn.getDebugId()));
			
		}
		for (GraphEdge ge : mor.getDom().getGraphEdges()) {
			result=result &&(mor.imageOf(ge)!=null)&&
				(mor.imageOf(ge).getDebugId().equals(ge.getDebugId()));
			
		}
		for (LabelNode ln: mor.getDom().getLabelNodes()) {
			result=result &&(mor.imageOf(ln)!=null)&&
				(mor.imageOf(ln).getLabel().equals(ln.getLabel()));
			
		}
		for (LabelEdge le: mor.getDom().getLabelEdges()) {
			result=result &&(mor.imageOf(le)!=null)&&
				(mor.imageOf(le).getDebugId().equals(le.getDebugId()));
			
		}
		return result;
	}
	
	public static void printSpan(SymbGTRule rule){
		System.out.println("########## TestHelper/printSpan("+rule+") ###########");
		SymbolicGraphMorphism left=rule.getLeft();
		SymbolicGraphMorphism right=rule.getRight();
		SymbolicGraph K=left.getDom();
		
		System.out.println("GraphNodes:");
		for (GraphNode gn : K.getGraphNodes()) {
			System.out.println(left.imageOf(gn).getDebugId()+" <------- "+gn.getDebugId()+" ------> "+right.imageOf(gn).getDebugId());
		}
		System.out.println("GraphEdges:");
		for (GraphEdge ge : K.getGraphEdges()) {
			System.out.println(left.imageOf(ge).getDebugId()+" <------- "+ge.getDebugId()+" ------> "+right.imageOf(ge).getDebugId());
		}
		System.out.println("LabelNodes:");
		for (LabelNode ln: K.getLabelNodes()) {
			System.out.println(left.imageOf(ln).getLabel()+" <------- "+ln.getLabel()+" ------> "+right.imageOf(ln).getLabel());
		}
		System.out.println("LabelEdges:");
		for (LabelEdge le: K.getLabelEdges()) {
			System.out.println(left.imageOf(le).getDebugId()+" <------- "+le.getDebugId()+" ------> "+right.imageOf(le).getDebugId());
		}
	}

	public static void printMorphism(SymbolicGraphMorphism ix) {
		System.out.println("########## TestHelper/printMorphism("+ix+") ##########");
		SymbolicGraphMorphism right=ix;
		SymbolicGraph K=right.getDom();
		
		for (GraphNode gn : K.getGraphNodes()) {
			System.out.println(gn.getDebugId()+"\t ------> \t "+right.imageOf(gn).getDebugId());
		}
		for (GraphEdge ge : K.getGraphEdges()) {
			System.out.println(ge.getDebugId()+"\t ------> \t "+right.imageOf(ge).getDebugId());
		}
		for (LabelNode ln: K.getLabelNodes()) {
			System.out.println(ln.getLabel()+"\t ------> \t "+ right.imageOf(ln).getLabel());
		}
		for (LabelEdge le: K.getLabelEdges()) {
			System.out.println(le.getDebugId()+"\t ------> \t "+right.imageOf(le).getDebugId());
		}
		
	}
	public static boolean areLabelNodesAndEdgesCorrect(SymbGTRule rule){
		CategoryUtil util=CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
		SymbolicGraph graphK=rule.getLeft().getDom();
		for (GraphNode gn : graphK.getGraphNodes()) {
			for (EAttribute attrib : gn.getType().getEAllAttributes()) {
				if(!( (gn.getLabelEdge().stream().filter(le->le.getType()==attrib).count()<=1) &&
				gn.getLabelEdge().stream().filter(le->le.getTarget().getType()==attrib.getEAttributeType()).count()<=1)){
					return false;
				}
			}
		}
		if((!util.isInjectiveMorphism(rule.getLeft()))||(!util.isInjectiveMorphism(rule.getRight()))){
			return false;
		}
		if((!util.isValidEGraphMorphism(rule.getLeft()))||(!util.isValidEGraphMorphism(rule.getRight()))){
			return false;
		}
		if((!haveAllLabelEdges(rule.getLeft().getCodom()))||(!haveAllLabelEdges(rule.getRight().getCodom()))){
			return false;
		}
		return true;
	}
	private static boolean haveAllLabelEdges(SymbolicGraph graph){
		
		for (GraphNode gn : graph.getGraphNodes()) {
			for (EAttribute attrib : gn.getType().getEAllAttributes()) {
				if(!( (gn.getLabelEdge().stream().filter(le->le.getType()==attrib).count()==1) &&
				gn.getLabelEdge().stream().filter(le->le.getTarget().getType()==attrib.getEAttributeType()).count()==1)){
					return false;
				}
			}
		}
		return true;
		
	}
}

package org.moflon.ide.visualization.dot.tgg.delta.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.moflon.ide.visualization.dot.language.EdgeCommand;
import org.moflon.ide.visualization.dot.language.NodeCommand;
import org.moflon.ide.visualization.dot.tgg.delta.EdgeCommandToEMoflonEdge;
import org.moflon.ide.visualization.dot.tgg.delta.NodeCommandToEMoflonEdge;
import org.moflon.ide.visualization.dot.tgg.delta.NodeCommandToNode;
import org.moflon.tgg.runtime.EMoflonEdge;

public class DeltaPostProcessingHelper {
	private static DeltaPostProcessingHelper helper;
	
	private static Map<EClass,String> generatedNames = new HashMap<>();
	
	private DeltaPostProcessingHelper(){
		clear();
	}
	
	public static DeltaPostProcessingHelper getHelper(){
		if(helper == null){
			helper = new DeltaPostProcessingHelper();
		}
		return helper;
	}
	
	public void postProcess(EObject corr){
		if(corr instanceof NodeCommandToNode){
			postProcessNode(NodeCommandToNode.class.cast(corr));
		}
		
		if (corr instanceof NodeCommandToEMoflonEdge){
			createLabel(NodeCommandToEMoflonEdge.class.cast(corr));
		}
		
		if(corr instanceof EdgeCommandToEMoflonEdge){
			createLabel(EdgeCommandToEMoflonEdge.class.cast(corr));
		}
	}
	
	private void createLabel(EdgeCommandToEMoflonEdge ec2ee){
		EdgeCommand ec = ec2ee.getSource();
		NodeCommand srcNode = ec.getSource();
		NodeCommand trgNode = ec.getTarget();
		
		EMoflonEdge ee = ec2ee.getTarget();
		EObject srcEObject = ee.getSrc();
		EObject trgEObject = ee.getTrg();
		
		srcNode.setLabel(createGoodNameToIdentify(srcEObject));
		trgNode.setLabel(createGoodNameToIdentify(trgEObject));
		
	}
	
	private String createGoodNameToIdentify(EObject eObject){
		String candidate = null;
		EClass eClass = eObject.eClass();
		for(EAttribute eAttribute : eClass.getEAttributes()){
			if(eAttribute != null && eAttribute.getEType() != null && eAttribute.getEType().getName() != null && eAttribute.getEType().getName().compareTo("EString")==0){
				if(eAttribute.getName().compareTo("name")==0){
					candidate = eObject.eGet(eAttribute).toString();
					break;
				}
				if(candidate == null)
					candidate = eObject.eGet(eAttribute).toString();
			}
		}
		
		return candidate == null ? generateName(eClass) : (candidate.replace("\"", "") + " : " + eClass.getName());
	}
	
	private String generateName(EClass eClass){
		String name = generatedNames.get(eClass);
		String eClassName= eClass.getName();
		if(name == null){
			name = eClassName.toLowerCase().charAt(0) + eClassName.substring(1) + "#0";
			generatedNames.put(eClass, name);
		}else{
			int counter = Integer.parseInt(name.substring(name.indexOf("#"+1)));
			counter++;
			name = eClassName.toLowerCase().charAt(0) + eClassName.substring(1) + "#"+counter;
			generatedNames.put(eClass, name);
		}		
		
		return name;
	}
	
	public static void clear(){
		generatedNames.clear();
	}
	
	private void createLabel(NodeCommandToEMoflonEdge nc2ee){
		NodeCommand node = nc2ee.getSource();
		EMoflonEdge ee = nc2ee.getTarget();
		EClass eClass = ee.getTrg().eClass();
		node.setLabel(ee.getName() + " : " + eClass.getName());
	}
	
	private void postProcessNode(NodeCommandToNode n2N){
		NodeCommand srcNode = n2N.getSource();
		EObject trgNode = n2N.getTarget();
		EClass eClass = trgNode.eClass();		
		srcNode.setLabel(eClass.getName());		
	}
}

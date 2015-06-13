package org.moflon.mosl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.util.EcoreUtil;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class GuidHandler {
	private Map<String,String> types;
	private Map<String,Node> eReferences;
	private Map<String,String> pathes;
	private static GuidHandler handler= new GuidHandler();
	
	protected GuidHandler(){
		types = new HashMap<String, String>();
		eReferences = new HashMap<String, Node>();
		pathes = new HashMap<String, String>();
		
	}
	
	public static GuidHandler init(){
		return handler;
	}
		
	public void refresh(){
	   types.clear();
	   eReferences.clear();
	   pathes.clear();
	}
	
	private Attribute getAttribute(Node node, String name){
		Collection<Attribute> attributes = node.getAttribute(name);
		for(Attribute attribute : attributes){
			if(attribute.getName().compareTo(name)==0)
				return attribute;
		}
		return null;
	}
	
	public void initGuid (Node node){
		if(node.getName().equals("EReference"))
			initEReferences(node);
		
		for(Text child : node.getChildren()){
			if(child instanceof Node)
				initGuid((Node) child);
		}
		
		Attribute guid=getAttribute(node, "guid") , name=getAttribute(node, "name");
		Node ePackage = null;
		Attribute ePackageName = null;
		if(node.getName().compareTo("EClass") == 0){
			ePackage = node.getParentNode().getParentNode();
			ePackageName=getAttribute(ePackage, "name");
		}
		if(guid != null && name !=null)
			addType(guid.getValue(), name.getValue(), ePackageName);
		
		for(Attribute attribute : node.getAttribute()){
			if(attribute.getValue() != null && attribute.getValue().contains("'"))
				attribute.setValue(attribute.getValue().replace("'", "\""));
		}
	}   	
	
	private void addType(String guid, String name, Attribute ePackageName){
		if(types.values().contains(name) && ePackageName != null)
			types.put(guid, "/"+ePackageName.getValue()+"/"+name);
		else
			types.put(guid, name);
	}
	
	private void initEReferences(Node eRef){
		for(Attribute attribute : eRef.getAttribute()){
			if(attribute.getName().equals("guid"))
				eReferences.put(attribute.getValue(), EcoreUtil.copy(eRef));
		}
	}
	
	public String getName4Guid(String guid){
	   String shorten = guid.replace(" ", "");
	   return types.get(shorten);
	}
	
	public Node getEReferenceNode4Guid(String guid){
		return eReferences.get(guid);
	}

	public void addType(String guid, String name){
	   types.put(guid, name);
	}
	
	public void addPath(String name, String path){
		pathes.put(name, path);
	}
	
	public String getPath4Name(String name){
		return pathes.get(name);
	}
	
	public String getGuid4Name(String name){
		List<String> tmp = getGuids4Name(name);
		if(tmp != null && tmp.size()>0)
			return tmp.get(0);
		else 
			return null;
	}
	
	public List<String> getGuids4Name(String name){
		List<String> collectedGuids = null;
		if(types.containsValue(name)){
		   collectedGuids = new ArrayList<String>();
	      for(Entry<String, String> entry : types.entrySet()){
	         if(entry.getValue().compareTo(name)==0){
	            collectedGuids.add(entry.getKey());
	         }
	      }	      
	   }
	  return collectedGuids;
	}
}

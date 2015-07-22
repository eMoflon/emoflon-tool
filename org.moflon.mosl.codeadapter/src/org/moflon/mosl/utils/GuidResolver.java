package org.moflon.mosl.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import MOSLCodeAdapter.moslPlus.MoslCategory;
import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class GuidResolver extends AbstractResolver{

	private Map<Attribute, Attribute> searchGuidCache;
	private static final String [] signalWords = {"EInt", "void", "EString", "EDouble", "EBoolean"};
	private List<String> blackList;
	
	public GuidResolver (){
		searchGuidCache = new HashMap<Attribute, Attribute>();
		blackList = new LinkedList<String>(Arrays.asList(signalWords));
	}
	
	public void addSearchAttribute(Attribute category, Attribute searchAttribute){
		searchGuidCache.put(category, searchAttribute);
	}
	
	public void resolveGuids(){
		for(Attribute searchCategory : searchGuidCache.keySet()){
			if(getCategory(searchCategory,"")==MoslCategory.TYPE && blackList.contains(searchCategory.getNode().getAttribute().get(searchCategory.getNode().getAttribute().indexOf(searchCategory) + 1).getValue())){
				searchGuidCache.get(searchCategory).setValue("");
			}
		}
	}
	
	public void findMetamodel(Node domains, Attribute type, Node target){
		for(Text domainsChild : domains.getChildren()){
			Node domain = Node.class.cast(domainsChild);
			Attribute guid = getAttribute(domain, "metamodelGuid");
			if(type.getValue().contains(guid.getValue())){
				Attribute metamodelGuid = EcoreUtil.copy(guid);
				metamodelGuid.setIndex(7);
				metamodelGuid.setNode(target);
			}
		}

	}

	@Override
	protected String getPathForNode(Node node) {
		return getPathForNode(node.getName(), node.getParentNode());
	}
	
	private String getPathForNode(String path, Node node){
		if(node.getParentNode() == null)
			return path;
		else return getPathForNode(node.getName() + "/" + path, node.getParentNode());
	}
	
	
}

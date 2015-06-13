package org.moflon.mosl.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import MOSLCodeAdapter.moslPlus.Category;
import MocaTree.Attribute;

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
			if(getCategory(searchCategory)==Category.TYPE && blackList.contains(searchCategory.getNode().getAttribute().get(searchCategory.getNode().getAttribute().indexOf(searchCategory) + 1).getValue())){
				searchGuidCache.get(searchCategory).setValue("");
			}
		}
	}
	
	
}

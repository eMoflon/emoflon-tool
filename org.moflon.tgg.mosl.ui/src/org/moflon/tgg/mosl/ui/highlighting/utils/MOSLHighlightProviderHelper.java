package org.moflon.tgg.mosl.ui.highlighting.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.moflon.tgg.mosl.ui.highlighting.MOSLHighlightFactory;
import org.moflon.tgg.mosl.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.tgg.mosl.ui.highlighting.rules.AbstractHighlightingRule;

public class MOSLHighlightProviderHelper {

	private static Map<String, AbstractHighlightingRule<?>> rules = new HashMap<>();
	
	public static void init(){
		rules.clear();
		MOSLHighlightFactory.createAllInstances();
	}
	
	public static void addHighlightRule(AbstractHighlightingRule<?> rule) throws IDAlreadyExistException{
		if(rules.containsKey(rule.getID()))
			throw new IDAlreadyExistException();
		else{
			rules.put(rule.getID(), rule);
		}
	}
	
	public static Collection<AbstractHighlightingRule<?>> getHighlightRules(){
		return rules.values();
	}
}

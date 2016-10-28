package org.moflon.ide.mosl.core.scoping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

public class ScopeProviderHelper <E extends EObject> {
	private Map<URI, E> existingScopingRoots;
	private Map<String, List<EObject>> oldCandidates;
	private ResourceSet resourceSet;
	
	public ScopeProviderHelper(ResourceSet resSet) {
		init();
		resourceSet = resSet;
	}
	
	public ScopeProviderHelper() {
		init();
		resourceSet = new ResourceSetImpl();
	}
	
	private void init(){
		existingScopingRoots = new HashMap<>();
		oldCandidates = new HashMap<>();
	}
	
	public ResourceSet getResourceSet(){
		return resourceSet;
	}
	
	private E getScopingObject(URI uri, Class<E> clazz){
		if(existingScopingRoots.containsKey(uri)){
			return existingScopingRoots.get(uri);
		}else{
			Resource res=resourceSet.getResource(uri, true);
			E scopingRoot = clazz.cast(res.getContents().get(0));
			existingScopingRoots.put(uri, scopingRoot);
			return scopingRoot;
		}
	}	
	
	public IScope createScope(List<URI> uris, Class<E> clazz, Class<? extends EObject> type){
		List<EObject> candidates=null;
		if(oldCandidates.containsKey(type.toGenericString())){
			candidates=oldCandidates.get(type.toGenericString());
		}
		else {		
			candidates = new ArrayList<>();
			
			for(URI uri : uris){
				E scopingObject=getScopingObject(uri, clazz);
				List<EObject> tmpCandidates = new ArrayList<EObject>(scopingObject.eContents());
				//tmpCandidates.removeIf(c -> (!c.getClass().isAssignableFrom(clazz)));
				candidates.addAll(tmpCandidates.stream().filter(isAssignable(type)).collect(Collectors.<EObject>toList()));
			}
			oldCandidates.put(type.toGenericString(), candidates);
		}
		return Scopes.scopeFor(candidates);
	}
	
	
	private Predicate<? super EObject> isAssignable(Class<? extends EObject> type){
		return c -> type.isAssignableFrom(c.getClass());
	}
}

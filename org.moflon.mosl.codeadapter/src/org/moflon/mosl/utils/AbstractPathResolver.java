package org.moflon.mosl.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

import MOSLCodeAdapter.moslPlus.MoslCategory;
import MocaTree.Node;

public abstract class AbstractPathResolver extends AbstractResolver {

	protected AbstractPathResolver() {
		categorizedPathTable = new HashMap<>();
		moslCache = new HashMap<>();
	}
	/**
	 * saves all pathes. category -> path -> name
	 */
	protected Map<MoslCategory, Map<String, String>> categorizedPathTable;
	protected Map<String, Node> moslCache;

	protected void init(){
		categorizedPathTable.clear();
		moslCache.clear();
	}
	
	public class Pair<F, S>{
	      private  F first;
	      private  S second;
	      
	      public Pair(F k, S v){
	         first = k;
	         second =v;
	      }
	      
	      public F getFirst()
	      {
	         return first;
	      }
	      public void setFirst(F first)
	      {
	         this.first = first;
	      }
	      public S getSecond()
	      {
	         return second;
	      }
	      public void setSecond(S second)
	      {
	         this.second = second;
	      }

	      
	      public String toString(){
	    	  return "(" + first.toString() + "; " + second.toString() +")";
	      }

	   }
	
	public void addPath(String path, String name, Node node){
		moslCache.put(path, node);
		MoslCategory cat = getCategory(node);
		Map<String, String> pathTable=categorizedPathTable.get(cat);
		if(pathTable==null){
			pathTable=new HashMap<>();
		}
		pathTable.put(path, name);
		categorizedPathTable.put(cat, pathTable);
	}

	
	
	public String getPath(String referencePath, String nameReference, MoslCategory cat) {
		Map<String,String> pathTable = categorizedPathTable.get(cat);
		LinkedList<String> nameReferenceParts = new LinkedList<>(Arrays.asList(nameReference.split("/")));
		LinkedList<String> referencePathParts = new LinkedList<>(Arrays.asList(referencePath.split("/")));
		String name=nameReferenceParts.pollLast();
		if(name.equalsIgnoreCase("void"))
			return "void";
		else
			return getPath(referencePathParts, nameReferenceParts, name, nameReference, pathTable);
	}
	
	private String getPath(List<String> referencePathParts, List<String> nameReferenceParts, String name, String nameReference, Map<String, String> pathTable){
		List<String> candidates = getCandidates(name, pathTable);
		int candiateSize = candidates.size();
		if(candiateSize == 0)
			throw new CanNotResolvePathException("For the name "+ name + " cannot find any path!");
		else if(candiateSize == 1)
			return candidates.get(0);
		else
			return findBestCandidate(referencePathParts, nameReferenceParts, nameReference, candidates);
	}
	
	private List<String> getCandidates(String name, Map<String, String> pathTable){
		List<String> candidates = new LinkedList<>();
		for(Entry<String,String> entry : pathTable.entrySet()){
			String value=entry.getValue();
			if( value != null && value.compareTo(name)==0){
				candidates.add(entry.getKey());
			}
		}
		return candidates;
	}
	
	private String findBestCandidate(List<String> referencePathParts, List<String> nameReferenceParts, String nameReference, List<String> candidates) {
		findBestCandidateFromRight(0, nameReferenceParts, nameReference, candidates);
		findBestCandidateFromLeft(0, referencePathParts, nameReference, candidates);
		return candidates.get(0);
	}
	
	private void findBestCandidateFromRight(int position, List<String> nameReferenceParts, String nameReference, List<String> candidates) {
		if(candidates.size()==0)
			throw new CanNotResolvePathException("The Reference to " + nameReference + " is wrong");
		else if(candidates.size()==1)
			return;
		else if(position < nameReferenceParts.size()){
			List<String> cpyCandidates = new LinkedList<String>(candidates);
			for(String path : cpyCandidates){
				String[] pathParts = path.split("/");
				if(pathParts.length-1 > position && nameReferenceParts.get(nameReferenceParts.size()-1-position).compareTo(pathParts[pathParts.length-2-position])!=0)
					candidates.remove(path);
			}
			findBestCandidateFromRight(position + 1, nameReferenceParts, nameReference, candidates);
		}
	}
	
	private void findBestCandidateFromLeft(int position, List<String> referencePathParts, String nameReference, List<String> candidates) {
		if(candidates.size()==0)
			throw new CanNotResolvePathException("The Reference to " + nameReference + " is too imprecise");
		else if(candidates.size()==1)
			return;
		else if(position < referencePathParts.size()){
			List<String> cpyCandidates = new LinkedList<String>(candidates);
			for(String path : cpyCandidates){
				String[] pathParts = path.split("/");
				if(position < pathParts.length && referencePathParts.get(position).compareTo(pathParts[position])!=0)
					candidates.remove(path);
			}
			findBestCandidateFromLeft(position + 1, referencePathParts, nameReference, candidates);
		}
		else
			throw new CanNotResolvePathException("The Reference to " + nameReference + " is too imprecise");
	}
	
	public void removeNodeFromCache(String path){
		moslCache.remove(path);
	}
	
	public Node getNodeFromCache(String path){
		return moslCache.get(path);
	}
	
}

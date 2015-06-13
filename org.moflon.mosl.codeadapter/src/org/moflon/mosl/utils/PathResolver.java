package org.moflon.mosl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

import MOSLCodeAdapter.moslPlus.Category;
import MOSLCodeAdapter.moslPlus.MOSLConverterAdapter;
import MOSLCodeAdapter.moslPlus.MOSLToMOSLPlusConverter;
import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class PathResolver extends AbstractResolver {
	
	private final static String MOSL_CONFIGURATION_FILE="_MOSLConfiguration.mconf";
	private final static Category[] inheritTypes = {Category.OPERATION, Category.ATTRIBUTE, Category.PATTERN, Category.REFERENCE};
	private final static Category[] forbiddenCategories = {Category.TYPE, Category.PATTERN_FILE, Category.PATTERN, Category.TYPED_EXPRESSION};
	
	
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
	/**
	 * saves all pathes. category -> path -> name
	 */
	private Map<Category, Map<String, String>> categorizedPathTable;
	private Map<String, Node> moslCache;
	private Map<Category, List<Pair<String, Attribute>>> searchCache;
	private MOSLToMOSLPlusConverter converter;
	private List<Category> inheritTypesList;
	private List<Category> blackList;
	
	public PathResolver(MOSLConverterAdapter adapter){
		categorizedPathTable = new HashMap<>();
		moslCache = new HashMap<>();
		searchCache = new HashMap<>();
		if(adapter instanceof MOSLToMOSLPlusConverter){
			converter=MOSLToMOSLPlusConverter.class.cast(adapter);
		}
		inheritTypesList = new LinkedList<Category>(Arrays.asList(inheritTypes));
		blackList = new LinkedList<Category>(Arrays.asList(forbiddenCategories));
	}
	
	public void init(){
		categorizedPathTable.clear();
		moslCache.clear();
		searchCache.clear();
	}
	
	public void addSearchPair(String path, Attribute searchCategory, Attribute attribute){
		Category cat=getSearchCategory(searchCategory);
		List<Pair<String, Attribute>> tripleList = searchCache.get(cat);
		if(tripleList == null){
			tripleList=new ArrayList<>();
		}
		tripleList.add(new Pair<String, Attribute>(path, attribute));
		searchCache.put(cat, tripleList);
	}
	
	public void resolveCSPs(){
		Node declarations=getCSPDeclaration();
		
		Map<String, String> rulesPathes = categorizedPathTable.get(Category.RULE);
		if(rulesPathes != null){
			for(String rulePath : rulesPathes.keySet()){
				Node ruleCSP = getChild(moslCache.get(rulePath), "cspSpec");
				if(hasChild(ruleCSP, "ROOT")){
					getChild(ruleCSP, "ROOT").getChildren().add(EcoreUtil.copy(declarations));
				}
			}		
		}
	}
	
	public void resolveOpposites(){
		fixEReferenceOpposites();
		fixLinksReferences();
	}
	
	public void copyPatterns(){
	      if (searchCache.containsKey(Category.PATTERN_FILE))
	      {
	         List<Pair<String, Attribute>> pairs = searchCache.get(Category.PATTERN_FILE);
	         for (Pair<String, Attribute> pair : pairs)
	         {
	            String name = pair.second.getValue();
	            Node destination = pair.second.getNode();
	            getPattern(destination, name, pair.first);
	         }
	      }
	   }

	private String getSimpleStatementType(Node expression){
		if(expression.getChildren("ObjectVariableExpression").size()>0){
			Node ove = getChild(expression, "ObjectVariableExpression");
			Attribute oveNamePath = getAttribute(ove, "objectVariable");
			Node objectVariable = moslCache.get(oveNamePath.getValue());
			return getAttribute(objectVariable, "type").getValue();//findMyTypePath(oveNamePath.getValue());
		}else if(expression.getChildren("ParameterExpression").size()>0){
			Node pe = getChild(expression, "ParameterExpression");
			Attribute peNamePath = getAttribute(pe, "parameterName");
			return findMyTypePath(peNamePath.getValue());
		}
		throw new CanNotResolvePathException("Unknown Expression:" + expression.getName());
	}
	
	private void resolveTypedExpressions(){
		Map<String,String> pathes=categorizedPathTable.get(Category.TYPED_EXPRESSION);
		if(pathes != null){
			for(String path : pathes.keySet()){
				String typePath = null;// findMyTypePath(path);
				Node expression = moslCache.get(path);
				if("AttributeValueExpression".compareTo(expression.getName())==0){
					Attribute objectVariableReference = getAttribute(expression, "objectVariable");
					Node objectVariable = moslCache.get(objectVariableReference.getValue());
					typePath = getAttribute(objectVariable, "type").getValue();
					Attribute attribute = getAttribute(expression, "attribute");
					attribute.setValue(tryToGetPath(typePath, attribute.getValue(), Category.ATTRIBUTE));
					
				} else if("MethodCallExpression".compareTo(expression.getName())==0){
					Attribute methodName = getAttribute(expression, "methodGuid");
					typePath = getSimpleStatementType(getChild(expression, "Expression"));
					methodName.setValue(tryToGetPath(typePath, methodName.getValue(), Category.OPERATION));
				}else{
					throw new CanNotResolvePathException("Expression is no typedExpression: " + expression.getName(), path, expression.getName(), Category.TYPED_EXPRESSION);
				}
			}
		}
	}
	
	private void resolveTypes(){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = null;
		pairs = searchCache.get(Category.TYPE);
		if(pairs != null){
			for(Pair<String, Attribute> pair : pairs){
				attribute = pair.second;
				referencePath = pair.first;
				attribute.setValue(getPath(referencePath, attribute.getValue(), Category.TYPE));
			}
			searchCache.remove(Category.TYPE);
		}
	}
	
	public void resolveAllPathes(){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = null;
		resolveTypes();
		this.copyPatterns();
		resolveTypes();
		for(Category cat : searchCache.keySet()){
			if(!blackList.contains(cat)){
				pairs = searchCache.get(cat);
				for(Pair<String, Attribute> pair : pairs){
					 attribute = pair.second;
					 referencePath = pair.first;
					 attribute.setValue(tryToGetPath(referencePath, attribute.getValue(), cat));
				}
			}
		}
		resolveTypedExpressions();
	}

	private void fixEReferenceOpposites(){
		Map<String, String> oppositePathes = categorizedPathTable.get(Category.OPPOSITE);
		if(oppositePathes != null){
			for(String path : oppositePathes.keySet()){
				Node opposite = moslCache.get(path);
				String leftReferencePath=getAttribute(opposite, "nameLeft").getValue();
				String rightReferencePath=getAttribute(opposite, "nameRight").getValue();
				Node leftReference = moslCache.get(leftReferencePath);
				Node rightReference = moslCache.get(rightReferencePath);
				if(getAttribute(rightReference,"containment").getValue().equals("true")){
					createAttribute("role", "Supplier", rightReference.getAttribute().size()).setNode(rightReference);					
					createAttribute("role", "Client", leftReference.getAttribute().size()).setNode(leftReference);
				}
				else{
					createAttribute("role", "Client", rightReference.getAttribute().size()).setNode(rightReference);					
					createAttribute("role", "Supplier", leftReference.getAttribute().size()).setNode(leftReference);					
				}
				createAttribute("oppositeGuid", leftReferencePath, rightReference.getAttribute().size()).setNode(rightReference);
				createAttribute("oppositeGuid", rightReferencePath, leftReference.getAttribute().size()).setNode(leftReference);
			}		
		}
	}
	
	private void fixLinksReferences() {
		Map<String,String> linkPathes = categorizedPathTable.get(Category.LINK);
		if(linkPathes != null){
			for(String linkPath : linkPathes.keySet()){
				Node linkNode = moslCache.get(linkPath);
				Node belongingEReference = moslCache.get(getAttribute(linkNode, "guid").getValue());
				if(hasAttribute(belongingEReference, "role")){
					if(getAttribute(belongingEReference, "role").getValue().equals("Supplier")){
						createAttribute("sourceName", getAttribute(belongingEReference, "name").getValue(), linkNode.getAttribute().size()).setNode(linkNode);
					} else {
						Node oppositeEReference = moslCache.get(getAttribute(belongingEReference, "oppositeGuid").getValue());
						createAttribute("sourceName", getAttribute(oppositeEReference, "name").getValue(), linkNode.getAttribute().size()).setNode(linkNode);
					}
				}
				else {
					createAttribute("sourceName", getAttribute(belongingEReference, "name").getValue(), linkNode.getAttribute().size()).setNode(linkNode);
				}
				if(hasAttribute(belongingEReference, "oppositeGuid")){
					createAttribute("direction", "bi", linkNode.getAttribute().size()).setNode(linkNode);
				}else {
					createAttribute("direction", "right", linkNode.getAttribute().size()).setNode(linkNode);
				}
			}
		}
	}
	
	private String findMyTypePath(String path){
		Map<String, String> typePathes = categorizedPathTable.get(Category.TYPE);
		if(typePathes != null){
			for(String typePath : typePathes.keySet()){
				if(path.contains(typePath))
					return typePath;
			}
		}
		throw new CanNotResolvePathException("path is unknown");
	}
	
	private Node getCSPDeclaration(){
		Node adornmentDeclarations = getChild(getChild(getChild(getChild(getAdornment(), "Configuration"), "UserDefinedConstraints"), "ROOT"),"DECLARATIONS");
		Node mconfFileNode=getMoslConfigurationFileNode();
		
		if(mconfFileNode==null){
			return adornmentDeclarations;			
		}
		Node targetUDCNode=getChild(getChild(mconfFileNode, "Configuration"), "UserDefinedConstraints");
		if(hasChild(targetUDCNode, "ROOT")){
			Node targetROOT = getChild(targetUDCNode, "ROOT");
			if(hasChild(targetROOT, "DECLARATIONS")){
				Node declarations=getChild(targetROOT, "DECLARATIONS");
				for(Text child : adornmentDeclarations.getChildren()){
					declarations.getChildren().add(EcoreUtil.copy(child));
				}
				return declarations;
			}else
				return adornmentDeclarations;
		}else
			return adornmentDeclarations;
		
		
	}
	
//	private void checkAllPackagesForConfiguration(){
//		checkCategoryForConfiguration(MODEL_CONFIGURATION_FILE, Category.PACKAGE);
//	}
//	
//	private void checkAllWorkingSetsForConfiguration(){
//		checkCategoryForConfiguration(MOSL_CONFIGURATION_FILE, Category.WORKING_SET);
//	}
//	
//	private void checkCategoryForConfiguration(String config, Category cat){
//		Map<String, String> allWorkingSetPathes = categorizedPathTable.get(cat);
//		for(String path:allWorkingSetPathes.keySet()){
//			Node configuration = null;
//			Node workingSetNode = moslCache.get(path);
//			if(!hasChild(workingSetNode, config)){
//				configuration = createNodeForCSPs(config, path);
//				workingSetNode.getChildren().add(configuration);
//			}
//		}
//	}
	
	private Node getMoslConfigurationFileNode(){
		try{
			return moslCache.get(getPath("", MOSL_CONFIGURATION_FILE, Category.MCONF_FILE));
		}catch (Throwable e){
			return null;
		}
	}
	
	private Node getAdornment(){
		return moslCache.get(getPath("", "adornment.mconf", Category.MCONF_FILE));
	}
	
	private String createNewReferencePath(String oldReferencePath, String typePath, String baseClassPath){
		return baseClassPath + oldReferencePath.replaceFirst(typePath, "");
	}
	
	private String tryToGetPath(String referencePath, String name, Category cat){
		String path = null;
		try{
			path = getPath(referencePath, name, cat);
		}catch (Throwable e){
			if(inheritTypesList.contains(cat)){
				String myTypePath = findMyTypePath(referencePath);
				Node myType = moslCache.get(myTypePath);
				if(hasChild(myType, "BaseClasses")){
					for(Text child : getChild(myType, "BaseClasses").getChildren()){
						path = tryToGetPath(createNewReferencePath(referencePath, myTypePath, getAttribute(Node.class.cast(child), "baseClass").getValue()), name, cat);
						if(path != null)
							break;
					}
					if(path==null)
						throw new CanNotResolvePathException(e.getMessage(), e, referencePath, name, cat);
				}
			}
			if(path==null)
				throw new CanNotResolvePathException(e.getMessage(), e, referencePath, name, cat);
		}
		
		if(path==null)
			throw new CanNotResolvePathException(name, referencePath, name, cat);
		return path;
	}
	
	private void getPattern(Node destination, String name, String path){
		String[] pathParts=path.split("/");
		String relativeName=pathParts[pathParts.length-2]+"/"+pathParts[pathParts.length-1]+"/"+name+".pattern";
		
		Node cpyPattern = Node.class.cast(EcoreUtil.copy(moslCache.get(tryToGetPath(path, relativeName, Category.PATTERN_FILE)).getChildren().get(0)));
		cpyPattern.setParentNode(destination);
		converter.traverseTree(path, cpyPattern);
	}
	
	public Category getSearchCategory(Attribute searchCategory){
		return getCategory(searchCategory.getValue());
	}
	
	public void removeNodeFromCache(String path){
		moslCache.remove(path);
	}
	
	public Node getNodeFromCache(String path){
		return moslCache.get(path);
	}
		
	public void addPath(String path, String name, Node node){
		moslCache.put(path, node);
		Category cat = getCategory(node);
		Map<String, String> pathTable=categorizedPathTable.get(cat);
		if(pathTable==null){
			pathTable=new HashMap<>();
		}
		pathTable.put(path, name);
		categorizedPathTable.put(cat, pathTable);
	}
	
	// FIXME Categories Operation and Attributes are problematic because they can reference to SuperClasses
	public String getPath(String referencePath, String nameReference, Category cat) {
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
}

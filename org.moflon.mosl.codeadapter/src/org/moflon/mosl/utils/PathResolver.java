package org.moflon.mosl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

import java.util.Collections;
import java.util.Comparator;

import MOSLCodeAdapter.moslPlus.Category;
import MOSLCodeAdapter.moslPlus.MOSLConverterAdapter;
import MOSLCodeAdapter.moslPlus.MOSLToMOSLPlusConverter;
import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class PathResolver extends AbstractPathResolver {
	
	private final static String MOSL_CONFIGURATION_FILE="_MOSLConfiguration.mconf";
	private final static Category[] inheritTypes = {Category.OPERATION, Category.ATTRIBUTE, Category.PATTERN, Category.REFERENCE};
	private final static Category[] forbiddenCategories = {Category.TYPE, Category.PATTERN_FILE, Category.PATTERN, Category.TYPED_EXPRESSION, Category.CORRESPONDENCE};
	
	

	private Map<Category, List<Pair<String, Attribute>>> searchCache;
	private MOSLToMOSLPlusConverter converter;
	private List<Category> inheritTypesList;
	private List<Category> blackList;
	
	public PathResolver(MOSLConverterAdapter adapter){
		super();
		searchCache = new HashMap<>();
		if(adapter instanceof MOSLToMOSLPlusConverter){
			converter=MOSLToMOSLPlusConverter.class.cast(adapter);
		}
		inheritTypesList = new LinkedList<Category>(Arrays.asList(inheritTypes));
		blackList = new LinkedList<Category>(Arrays.asList(forbiddenCategories));
	}
	
	public void init(){
		super.init();
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
		fixLinksReferences(Category.LINK);
		fixLinksReferences(Category.TGG_LINK);
	}
	
	public void copyPatterns(){
	      if (searchCache.containsKey(Category.PATTERN_FILE))
	      {
	         List<Pair<String, Attribute>> pairs = searchCache.get(Category.PATTERN_FILE);
	         for (Pair<String, Attribute> pair : pairs)
	         {
	            String name = pair.getSecond().getValue();
	            Node destination = pair.getSecond().getNode();
	            getPattern(destination, name, pair.getFirst());
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
					Attribute methodGuid = getAttribute(expression, "methodGuid");
					typePath = getSimpleStatementType(getChild(expression, "Expression"));
					methodGuid.setValue(tryToGetPath(typePath, methodGuid.getValue(), Category.OPERATION));
					resolvingArguments(expression, moslCache.get(methodGuid.getValue()), path, methodGuid.getValue());
				}else{
					throw new CanNotResolvePathException("Expression is no typedExpression: " + expression.getName(), path, expression.getName(), Category.TYPED_EXPRESSION);
				}
			}
		}
	}
	
	private void  resolvingArguments(Node mce, Node method, String path, String methodPath){
		if(method != null){
			Node parametersNode=getChild(method, "parameters");
			List<Text> arguments= new ArrayList<>(mce.getChildren("ownedParameterBinding"));
			if(parametersNode==null && arguments.size()==0)
				return;
			
			List<Text> parameters = new ArrayList<>(parametersNode.getChildren());
			
			if(parameters.size() == arguments.size()){
				Comparator<Text> idComparator = new Comparator<Text>() {
					@Override
					public int compare(Text o1, Text o2) {
						Attribute id1 = null;
						Attribute id2 = null;
						Node node1 = Node.class.cast(o1);
						Node node2 = Node.class.cast(o2);
							id1 = getAttribute(node1, "nummberID");
							id2 = getAttribute(node2, "nummberID");

						return Integer.signum(Integer.parseInt(id1.getValue()) - Integer.parseInt(id2.getValue()));
					}
				};
				
				Collections.sort(parameters, idComparator);
				Collections.sort(arguments, idComparator);
				
				for(int i=0; i<parameters.size(); i++){
					Attribute parameterGuid = createAttribute("parameterGuid", methodPath + "/" +getAttribute(Node.class.cast(parameters.get(i)), "name").getValue(), 0);
					
					Attribute parameterType = EcoreUtil.copy(getAttribute(Node.class.cast(parameters.get(i)), "search"));
					parameterType.setName("parameterType");
					
					Node argument = Node.class.cast(arguments.get(i));
					
					parameterGuid.setNode(argument);
					parameterType.setNode(argument);
				}
				
			}else if(parameters.size() > arguments.size())
				throw new CanNotResolvePathException("Too less arguments for " + mce, path, mce.getName(), Category.TYPED_EXPRESSION);
			else
				throw new CanNotResolvePathException("Too many arguments for " + mce, path, mce.getName(), Category.TYPED_EXPRESSION);
		}
		else
			throw new CanNotResolvePathException("MethodCallExpression: "+mce+" has no guid", path, mce.getName(), Category.TYPED_EXPRESSION);
	}
	
	private void resolveTypes(){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = null;
		pairs = searchCache.get(Category.TYPE);
		if(pairs != null){
			for(Pair<String, Attribute> pair : pairs){
				attribute = pair.getSecond();
				referencePath = pair.getFirst();
				String path = getPath(referencePath, attribute.getValue(), Category.TYPE);
				attribute.setValue(path);
				setDependency(referencePath, path);
			}
			searchCache.remove(Category.TYPE);
		}
	}
	
	
	
	private void setDependency(String from, String to){
		List<String> fromPackages = getPathesForPathReferncesAndCategory(from, Category.PACKAGE);
		List<String> toPackages = getPathesForPathReferncesAndCategory(to, Category.PACKAGE);
		for(String fromPackagePath : fromPackages){
			if(!toPackages.contains(fromPackagePath)){
				Node ePackage = moslCache.get(fromPackagePath);
				Node dependenciesNode = null;
				if(hasChild(ePackage, "dependencies"))
				 dependenciesNode = getChild(ePackage, "dependencies");
				else{
					dependenciesNode=createNode("dependencies", ePackage.getChildren().size());
					dependenciesNode.setParentNode(ePackage);
				}
				for(String toPackagePath : toPackages){
					Node dependencyPackage = moslCache.get(toPackagePath);
					Attribute dependencyPackageName = getAttribute(dependencyPackage, "name");
					if("Ecore".compareTo(dependencyPackageName.getValue())!=0 && !hasChild(dependenciesNode, dependencyPackageName.getValue())){
						Node newDependency = createNode(dependencyPackageName.getValue(), dependenciesNode.getChildren().size());
						newDependency.setParentNode(dependenciesNode);
					}
				}
			}
		}
	}
	
	private List<String> getPathesForPathReferncesAndCategory(String refPath, Category cat){
		Map<String, String> pathTable = categorizedPathTable.get(cat);
		List<String> elements = new ArrayList<>();
		for(String path : pathTable.keySet()){
			if(refPath.contains(path))
				elements.add(path);
		}		
		return elements;
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
	
	private void fixLinksReferences(Category cat) {
		Map<String,String> linkPathes = categorizedPathTable.get(cat);
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
	
	private void resolveCategory(Category cat){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = searchCache.get(cat);
		if(pairs != null){
			for(Pair<String, Attribute> pair : pairs){
				attribute = pair.getSecond();
				referencePath = pair.getFirst();
				String path = tryToGetPath(referencePath, attribute.getValue(), cat);
				attribute.setValue(path);
			}
		}
	}
	
	public void resolveAllPathes(){
		resolveTypes();
		this.copyPatterns();
		resolveTypes();
		for(Category cat : searchCache.keySet()){
			if(!blackList.contains(cat)){
				resolveCategory(cat);
			}
		}
		resolveCategory(Category.CORRESPONDENCE);
		resolveTypedExpressions();
	}
	

		

}

package org.moflon.mosl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

import MOSLCodeAdapter.moslPlus.MOSLConverterAdapter;
import MOSLCodeAdapter.moslPlus.MOSLToMOSLPlusConverter;
import MOSLCodeAdapter.moslPlus.MoslCategory;
import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;
import MocaTree.TreeElement;

public class PathResolver extends AbstractPathResolver {
	
	private final static String MOSL_CONFIGURATION_FILE="_MOSLConfiguration.mconf";
	private final static MoslCategory[] inheritTypes = {MoslCategory.OPERATION, MoslCategory.ATTRIBUTE, MoslCategory.PATTERN, MoslCategory.REFERENCE};
	private final static MoslCategory[] forbiddenCategories = {MoslCategory.TYPE, MoslCategory.PATTERN_FILE, MoslCategory.PATTERN, MoslCategory.TYPED_EXPRESSION, MoslCategory.CORRESPONDENCE};
	
	

	private static Map<MoslCategory, List<Pair<String, Attribute>>> searchCache;
	private MOSLToMOSLPlusConverter converter;
	private List<MoslCategory> inheritTypesList;
	private List<MoslCategory> blackList;
	
	public PathResolver(final MOSLConverterAdapter adapter){
		super();
		searchCache = new HashMap<>();
		if(adapter instanceof MOSLToMOSLPlusConverter){
			converter=MOSLToMOSLPlusConverter.class.cast(adapter);
		}
		inheritTypesList = new LinkedList<MoslCategory>(Arrays.asList(inheritTypes));
		blackList = new LinkedList<MoslCategory>(Arrays.asList(forbiddenCategories));
	}
	
	@Override
	public void init(){
		super.init();
		searchCache.clear();
	}
	
	
	
	public void addSearchPair(final String path, final Attribute searchCategory, final Attribute attribute){
		MoslCategory cat=getSearchCategory(searchCategory,path);
		List<Pair<String, Attribute>> tripleList = searchCache.get(cat);
		if(tripleList == null){
			tripleList=new ArrayList<>();
		}
		tripleList.add(new Pair<String, Attribute>(path, attribute));
		searchCache.put(cat, tripleList);
	}
	
	public void resolveCSPs(){
		Node declarations=getCSPDeclaration();
		
		Map<String, String> schemaPathes = categorizedPathTable.get(MoslCategory.SCHEMA_FILE);
		if(schemaPathes != null){
			for(String schemaPath : schemaPathes.keySet()){
				Node constraints = getChild(getChild(moslCache.get(schemaPath), "TGG"), "constraints");				
				constraints.getChildren().add(EcoreUtil.copy(declarations));				
			}		
		}
	}
	
	public void resolveOpposites(){
		fixEReferenceOpposites();
		fixLinksReferences(MoslCategory.LINK);
		fixLinksReferences(MoslCategory.TGG_LINK);
	}
	
	public void copyPatterns(){
	      if (searchCache.containsKey(MoslCategory.PATTERN_FILE))
	      {
	         List<Pair<String, Attribute>> pairs = searchCache.get(MoslCategory.PATTERN_FILE);
	         for (Pair<String, Attribute> pair : pairs)
	         {
	            String name = pair.getSecond().getValue();
	            Node destination = pair.getSecond().getNode();
	            getPattern(destination, name, pair.getFirst(), destination);
	         }
	      }
	   }

	private String getSimpleStatementType(final Node expression){
		if(expression.getChildren("ObjectVariableExpression").size()>0){
			Node ove = getChild(expression, "ObjectVariableExpression");
			Attribute oveNamePath = getAttribute(ove, "objectVariable");
			Node objectVariable = moslCache.get(oveNamePath.getValue());
			return getAttribute(objectVariable, "type").getValue();//findMyTypePath(oveNamePath.getValue());
		}else if(expression.getChildren("ParameterExpression").size()>0){
			Node pe = getChild(expression, "ParameterExpression");
			Attribute peNamePath = getAttribute(pe, "parameterName");
			return findMyTypePath(peNamePath.getValue(), peNamePath);
		}
		throw new CanNotResolvePathException("Unknown Expression:" + expression.getName(), "", expression, MoslCategory.TYPED_EXPRESSION);
	}
	
	private void resolveTypedExpressions(){
		Map<String,String> pathes=categorizedPathTable.get(MoslCategory.TYPED_EXPRESSION);
		if(pathes != null){
			for(String path : pathes.keySet()){
				String typePath = null;// findMyTypePath(path);
				Node expression = moslCache.get(path);
				if("AttributeValueExpression".compareTo(expression.getName())==0){
					Attribute objectVariableReference = getAttribute(expression, "objectVariable");
					Node objectVariable = moslCache.get(objectVariableReference.getValue());
					typePath = getAttribute(objectVariable, "type").getValue();
					Attribute attribute = getAttribute(expression, "attribute");
					attribute.setValue(tryToGetPath(typePath, attribute.getValue(), MoslCategory.ATTRIBUTE, attribute));
					
				} else if("MethodCallExpression".compareTo(expression.getName())==0){
					Attribute methodGuid = getAttribute(expression, "methodGuid");
					typePath = getSimpleStatementType(getChild(expression, "Expression"));
					methodGuid.setValue(tryToGetPath(typePath, methodGuid.getValue(), MoslCategory.OPERATION, methodGuid));
					resolvingArguments(expression, moslCache.get(methodGuid.getValue()), path, methodGuid.getValue());
				}else{
					throw new CanNotResolvePathException("Expression is no typedExpression: " + expression.getName(), path, expression ,expression.getName(), MoslCategory.TYPED_EXPRESSION);
				}
			}
		}
	}
	
	private void  resolvingArguments(final Node mce, final Node method, final String path, final String methodPath){
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
				throw new CanNotResolvePathException("Too less arguments for " + mce.getName(), path, mce, mce.getName(), MoslCategory.TYPED_EXPRESSION);
			else
				throw new CanNotResolvePathException("Too many arguments for " + mce.getName(), path, mce, mce.getName(), MoslCategory.TYPED_EXPRESSION);
		}
		else
			throw new CanNotResolvePathException("MethodCallExpression: "+mce.getName()+" has no guid", path, mce, mce.getName(), MoslCategory.TYPED_EXPRESSION);
	}
	
	private void resolveTypes(){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = null;
		pairs = searchCache.get(MoslCategory.TYPE);
		if(pairs != null){
			for(Pair<String, Attribute> pair : pairs){
				attribute = pair.getSecond();
				referencePath = pair.getFirst();
				String path = getPath(referencePath, attribute.getValue(), MoslCategory.TYPE, attribute);
				attribute.setValue(path);
				setDependency(referencePath, path);
			}
			searchCache.remove(MoslCategory.TYPE);
		}
	}
	
	
	
	private void setDependency(final String from, final String to){
		List<String> fromPackages = getPathesForPathReferncesAndCategory(from, MoslCategory.PACKAGE);
		List<String> toPackages = getPathesForPathReferncesAndCategory(to, MoslCategory.PACKAGE);
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
	
	private List<String> getPathesForPathReferncesAndCategory(final String refPath, final MoslCategory cat){
		Map<String, String> pathTable = categorizedPathTable.get(cat);
		List<String> elements = new ArrayList<>();
		for(String path : pathTable.keySet()){
			if(refPath.contains(path))
				elements.add(path);
		}		
		return elements;
	}
	
	
	private void fixEReferenceOpposites(){
		Map<String, String> oppositePathes = categorizedPathTable.get(MoslCategory.OPPOSITE);
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
	
	private void fixLinksReferences(final MoslCategory cat) {
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
	
	private String findMyTypePath(final String path, final TreeElement reference){
		Map<String, String> typePathes = categorizedPathTable.get(MoslCategory.TYPE);
		if(typePathes != null){
			for(String typePath : typePathes.keySet()){
				if(path.contains(typePath))
					return typePath;
			}
		}
		throw new CanNotResolvePathException("Cannot find any " + getStringOfCategory(MoslCategory.TYPE) + " for " +reference.getName(), path, reference, MoslCategory.TYPE);
	}
	
	
	private void repairImportedAdornments(final Node adornmentDeclarations){
		for(Text adChild : adornmentDeclarations.getChildren()){
			Attribute userDefined = getAttribute(Node.class.cast(adChild), "USER_DEFINED");
			userDefined.setValue("false");
		}
	}
	
	private Node getCSPDeclaration(){
		Node adornmentDeclarations = getChild(getChild(getChild(getChild(getAdornment(), "Configuration"), "UserDefinedConstraints"), "ROOT"),"DECLARATIONS");
		repairImportedAdornments(adornmentDeclarations);
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
	
	private Node getMoslConfigurationFileNode(){
		try{
			return moslCache.get(getPath("", MOSL_CONFIGURATION_FILE, MoslCategory.MCONF_FILE, null));
		}catch (Throwable e){
			return null;
		}
	}
	
	private Node getAdornment(){
		return moslCache.get(getPath("", "adornment.mconf", MoslCategory.MCONF_FILE, null));
	}
	
	private String createNewReferencePath(final String oldReferencePath,final String typePath, final String baseClassPath){
		return baseClassPath + oldReferencePath.replaceFirst(typePath, "");
	}
	
	private String tryToGetPath(String referencePath, String name, MoslCategory cat, TreeElement referenceAttribute){
		String path = null;
		try{
			path = getPath(referencePath, name, cat, referenceAttribute);
		}catch (Throwable e){
			if(inheritTypesList.contains(cat)){
				String myTypePath = findMyTypePath(referencePath, referenceAttribute);
				Node myType = moslCache.get(myTypePath);
				if(hasChild(myType, "BaseClasses")){
					for(Text child : getChild(myType, "BaseClasses").getChildren()){
						path = tryToGetPath(createNewReferencePath(referencePath, myTypePath, getAttribute(Node.class.cast(child), "baseClass").getValue()), name, cat, referenceAttribute);
						if(path != null)
							return path;
					}
					if(path==null)
						throw new CanNotResolvePathException(e.getMessage(), e, referencePath, referenceAttribute, name, cat);
				}
			}
			if(path==null)
				throw new CanNotResolvePathException(e.getMessage(), e, referencePath, referenceAttribute, name, cat);
		}
		
		if(path==null)
			throw new CanNotResolvePathException(name, referencePath, referenceAttribute, name, cat);
		return path;
	}
	
	private void getPattern(final Node destination, final String name, final String path, final TreeElement referenceElement){
		String[] pathParts=path.split("/");
		String relativeName=pathParts[pathParts.length-2]+"/"+pathParts[pathParts.length-1]+"/"+name+".pattern";
		
		Node cpyPattern = Node.class.cast(EcoreUtil.copy(moslCache.get(tryToGetPath(path, relativeName, MoslCategory.PATTERN_FILE, referenceElement)).getChildren().get(0)));
		cpyPattern.setParentNode(destination);
		converter.traverseTree(path, cpyPattern);
	}
	
	private void resolveCategory(final MoslCategory cat){
		Attribute attribute = null;
		String referencePath = null;
		List<Pair<String, Attribute>> pairs = searchCache.get(cat);
		if(pairs != null){
			for(Pair<String, Attribute> pair : pairs){
				attribute = pair.getSecond();
				referencePath = pair.getFirst();
				String path = tryToGetPath(referencePath, attribute.getValue(), cat, attribute);
				attribute.setValue(path);
			}
		}
	}
	
	public void resolveAllPathes(){
		resolveTypes();
		this.copyPatterns();
		resolveTypes();
		for(MoslCategory cat : searchCache.keySet()){
			if(!blackList.contains(cat)){
				resolveCategory(cat);
			}
		}
		resolveCategory(MoslCategory.CORRESPONDENCE);
		resolveTypedExpressions();
	}
	

		

}

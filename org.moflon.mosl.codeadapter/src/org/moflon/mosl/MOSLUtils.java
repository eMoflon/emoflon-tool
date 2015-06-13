package org.moflon.mosl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.Vector;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.stringtemplate.StringTemplate;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.mosl.eclass.parser.EclassParserAdapter;
import org.moflon.mosl.eclass.unparser.EclassUnparserAdapter;
import org.moflon.mosl.mconf.parser.MconfParserAdapter;
import org.moflon.mosl.mconf.unparser.MconfUnparserAdapter;
import org.moflon.mosl.pattern.parser.PatternParserAdapter;
import org.moflon.mosl.pattern.unparser.PatternUnparserAdapter;
import org.moflon.mosl.sch.parser.SchParserAdapter;
import org.moflon.mosl.sch.unparser.SchUnparserAdapter;
import org.moflon.mosl.tgg.parser.TggParserAdapter;
import org.moflon.mosl.tgg.unparser.TggUnparserAdapter;

import Moca.CodeAdapter;
import Moca.MocaFactory;
import Moca.Problem;
import MocaTree.Attribute;
import MocaTree.File;
import MocaTree.Folder;
import MocaTree.MocaTreePackage;
import MocaTree.Node;
import MocaTree.Text;
import MocaTree.TreeElement;


public class MOSLUtils {
	
	private static Map<String, String> types = new HashMap<String, String>();
	private static Map<String, Map<String,String>> references = new HashMap<String, Map<String,String>>();
	
//   public static void filterErrors(MOSLTransformer transformer) {
//      Iterator<LanguageError> it = transformer.getErrors().iterator();
//      while (it.hasNext()) {
//         LanguageError e = it.next();
//         if ("AddType".equals(e.getErrorType()) && e.getClassName().startsWith("E")) {
//            it.remove();
//         }
//      }
//   }
   
	private static long generatedIndex = 0L;
	
	public static long getIndex(){
		if(generatedIndex == Long.MAX_VALUE){
			generatedIndex = 0L;
		}		
		return generatedIndex++;
	}
	
	
	
	
	public static<T extends TreeElement> T loadTree(URL fileURL, Class<T> clazz) {
		T loadedTree = null;
		eMoflonEMFUtil.init(MocaTreePackage.eINSTANCE);
		EObject obj;
      try
      {
         obj = eMoflonEMFUtil.loadModelWithDependenciesAndCrossReferencer(org.eclipse.emf.common.util.URI.createURI(fileURL.toURI().toString()), null);
      } catch (URISyntaxException e)
      {
         throw new IllegalArgumentException(e);
      }
		
		if (clazz.isAssignableFrom(obj.getClass())) {
			loadedTree = clazz.cast(obj);
		}
		
		return loadedTree;
	}
	
	
	
   public static CommonTree concat(int type, List<Object> list)
   {
      StringBuilder sb = new StringBuilder();
      for (Object o : list)
      {
         if (o instanceof Token)
         {
        	 Token token = (Token) o;
            sb.append(token.getText());
         }
      }
      CommonToken token = new CommonToken(type, sb.toString());
      return new CommonTree(token);
   }
	
	public static String findTypedef(Stack<Map<String, String>> typedefs, String typedef) {
	   for (Map<String, String> m : typedefs) {
	      if (m.containsKey(typedef))
	         return m.get(typedef);
	   }
	   
	   return null;
	}
	
	public static String resolveType(Stack<Map<String, String>> typedefs, String path, String relativePart) {
	   String typedef = relativePart;
	   int dotIndex = relativePart.indexOf("/");
	   if (dotIndex > -1) {
	      typedef = relativePart.substring(0, dotIndex);
	   }
	   String def = findTypedef(typedefs, typedef);
	   if (def != null && def.trim().length() > 0) {
	      relativePart = def + (dotIndex > -1 ? relativePart.substring(dotIndex) : "");
	   }
	   
		List<String> relativeParts = Arrays.asList(relativePart.split("/"));
		
		List<String> retPath = new Vector<String>();
		if (!relativePart.startsWith("/")) {
		   retPath.addAll(Arrays.asList(path.split("\\.")));
		} else {
		   retPath.add("root");
		}
		
		for (String p : relativeParts) {
		   if (p.length() > 0) {
   			if ("..".equals(p)) {
   				retPath.remove(retPath.size()-1);
   			} else {
   				retPath.add(p);
   			}
		   }
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < retPath.size(); i++) {
			if (i > 0) {
				sb.append(".");
			}
			sb.append(retPath.get(i));
		}
		
		return sb.toString();
	}
	
	public static String trBound(CommonTree bound) {
		if ("-1".equals(bound.getText())) {
			return "*";
		}
		return bound.getText();
	}
	
	public static String decode(CommonTree node, String... values) {
		for (int i = 0; i < values.length; i += 2) {
			if (values[i].equals(node.getText())) {
				return values[i+1];
			}
		}
		throw new IllegalArgumentException("Cannot decode '" + node.getText() + "' with " + Arrays.asList(values));
	}
	
	public static String decide(CommonTree node, String thenValue) {
		return decide(node, thenValue, null);
	}
	
	public static String decide(CommonTree node, String thenValue, String elseValue) {
		if ("true".equals(node.getText())) {
			return thenValue;
		} else {
			return elseValue;
		}
	}

    public static boolean checkEqual(String str, Object o) {
    	boolean check = str.equals(((CommonTree)o).getText());
        return check;
    }
	
	public static Problem createProblem(CodeAdapter codeAdapter, String file, AntlrParserError e) {
		Problem p = MocaFactory.eINSTANCE.createProblem();
		p.setMessage(e.getMessage());
		p.setLine(e.getLine());
		p.setCharacterPositionStart(e.getPositionInLine());
		p.setSource(file);
		p.setCodeAdapter(codeAdapter);
		return p;
	}

   public static URL getImport(String name)
   {
      String identifier = name;
      
      // Try to resolve the import from the given internal tree
      try {
         URL url = new URL("platform:/plugin/org.moflon.MOSLCodeAdapter/resources/trees/" + identifier + ".moca.xmi");

         if (canBeRead(url)) {
            return url;
         }
      } catch (Exception e) {
         throw new IllegalArgumentException("Error while resolving internal import '" + identifier + "': " + e.getMessage(), e);
      }

      try {
         // Try to resolve the import via a local project
         IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(identifier);
         if (project != null) {
            IResource resource = project.findMember(".temp/" + identifier + ".moca.xmi");
            
            if (resource != null && resource.exists()) {
               return new URL("file://" + resource.getRawLocation().toPortableString().replaceAll("\\s", "%20"));
            }
         }
      } catch (MalformedURLException mue) {
         throw new IllegalArgumentException(mue);
      }
         
      throw new IllegalArgumentException("Cannot resolve import '" + identifier + "'.");
   }
   
   private static boolean canBeRead(URL u) {
      InputStream is = null;
      try {
         is = u.openStream();
         
         if (is == null) {
            return false;
         } else {
            return true;
         }
      } catch (IOException ioe) {
         return false;
      } finally {
         try {
            if (is != null)
               is.close();
         } catch (IOException e) {}
      }
   }
   
   public static CodeAdapter createCodeAdapter() {
      return createCodeAdapter(null);
   }

   public static CodeAdapter createCodeAdapter(String templateDir)
   {
      CodeAdapter codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
      
      codeAdapter.getParser().add(new EclassParserAdapter());
      codeAdapter.getParser().add(new PatternParserAdapter());
      codeAdapter.getParser().add(new MconfParserAdapter());
      codeAdapter.getParser().add(new TggParserAdapter());
      codeAdapter.getParser().add(new SchParserAdapter());
      
      codeAdapter.getUnparser().add(new EclassUnparserAdapter(templateDir));
      codeAdapter.getUnparser().add(new PatternUnparserAdapter(templateDir));
      codeAdapter.getUnparser().add(new MconfUnparserAdapter(templateDir));
      codeAdapter.getUnparser().add(new TggUnparserAdapter(templateDir));
      codeAdapter.getUnparser().add(new SchUnparserAdapter(templateDir));
      
      return codeAdapter;
   }

   public static String resolvePattern(String path, String className, String operationName, String activityName)
   {
      String resolved = path + "/" + activityName + ".pattern";
      // System.err.format("resolvePattern(%s, %s, %s, %s) = %s\n", path, className, operationName, activityName, resolved);
      return resolved; 
   }

   public static String strip(String value)
   {
      return value.replaceAll("[^0-9a-zA-Z_]", "");
   }
   
   public static CommonTree strip(StringTemplate st)
   {		  
	  
	   CommonTree tmp = new CommonTree();
	   String name=strip(st.toString());
	   tmp.token=new CommonToken(1, name);
	   //tmp.token.setText(strip(st.toString()));
      return tmp;
   }
   
	/**
	 * Trims the current token in the given Lexer
	 * 
	 * @param lexer
	 * @param leftMargin
	 *            amount of characters that will be removed from the beginning
	 *            of the token
	 * @param rightMargin
	 *            amount of characters that will be removed from the end of the
	 *            token
	 */
	public static void trim(Lexer lexer, int leftMargin, int rightMargin)
	{
		String text = trim(lexer.getText());
		text = text.substring(leftMargin, text.length() - rightMargin);
		lexer.setText(text);
	}
   
   public static String trim(String value) {
      if (value == null)
         return null;
      String[] tmp = value.split("\n");
      StringBuffer sb = new StringBuffer();
      for(String str : tmp){
    	  if(str.contains("//")){
    		  sb.append(str.replace(str.substring(str.indexOf("//")), ""));
    	  }
    	  else{
    		  sb.append(str);
    	  }    		 
      }      
      return sb.toString().trim();
   }
   
   private static String checkForNumbers(String value){
	   try{
		   Integer.valueOf(value);
		   return value;
	   }catch(Exception e){
		   return "'"+ value +"'";
	   }
   }
   
   public static String getValueForLiteralExpression(String value){
	   switch(value){
	   case "true":
		   return value;
	   case "false":
		   return value;
	   default: 
		   return checkForNumbers(value);
	   }
   }
   
   public static String getTypeByGUID(String guid){
	   String type = types.get(cleanGuid(guid));
	   return type;
   }
   
   public static String getOppositeReference(String guid){
	if(guid.contains("Client") && references.get(cleanGuid(guid))!=null){
		List<String> output = new ArrayList<String>();
		for(Entry<String,String> refs : references.get(cleanGuid(guid)).entrySet()){			
			output.add(refs.getKey() + " : " + refs.getValue());			
		}
		if(output.size() == 2){
			return output.get(0) + " <-> " + output.get(1)+"\n";
		}
	}   
		   return null;
   }
   
   private static String cleanGuid (String guid){
	   return guid.replace("Supplier", "").replace("Client", "");
   }
   
   private static Map<String, String> getGuidAndNameFromNode(Node node){
		 Map<String, String> guidNameMap = new HashMap<String, String>();
		 for (Attribute att : node.getAttribute()){
				 if(att.getName().equals("guid")){
					 guidNameMap.put("guid", cleanGuid(att.getValue()));
				 }
				 else if (att.getName().equals("name")){
					 guidNameMap.put("name", att.getValue());
				 }
		 }				
		 if(guidNameMap.size() <= 2 && guidNameMap.get("guid") != null && guidNameMap.get("name") != null)
			 return guidNameMap;
		 else
			 return null;
   }
   
   
   private static void initReferences(Node eClassNode, String className){
	   for(Text eClassChildren : eClassNode.getChildren()){
		   if (eClassChildren instanceof Node && eClassChildren.getName().equals("references")){
			   Node referencesNode = (Node) eClassChildren;
			   for(Text eReferences : referencesNode.getChildren()){
				   if(eReferences instanceof Node && eReferences.getName().equals("EReference")){
					   Node eRefNode=(Node) eReferences;
					   Map<String, String> guidNameMap = getGuidAndNameFromNode(eRefNode);
					   if(guidNameMap != null){
						   if(references.get(guidNameMap.get("guid")) == null){
							   Map<String,String> names = new HashMap<String,String>();
							   names.put(guidNameMap.get("name"),className);
							   references.put(guidNameMap.get("guid"), names);
						   }
						   else{
							   references.get(guidNameMap.get("guid")).put(guidNameMap.get("name"),className);
						   }
					   }
					   else{
						   //TODO throw Exception
					   }
				   }
			   }
		   }
	   }
   }
   
   
   public static void initTypes(Folder folder){
	 for(Folder subFolder :  folder.getSubFolder()){
		 for(File file : subFolder.getFile()){
			 if(file.getName().endsWith(".eclass")){
				 Node root = file.getRootNode();
				 Node eClassNode = (Node) root.getChildren().get(0);
				 Map<String, String> guidNameMap = getGuidAndNameFromNode(eClassNode);
				 if(guidNameMap != null){
					 types.put(guidNameMap.get("guid"), guidNameMap.get("name"));
				 }
				 else{
					 //TODO throw Exception
				 }
				 initReferences(eClassNode, guidNameMap.get("name"));
			 }
		 }
	 }
   }
   
   private static String substituteCloseBrace(String str, int n){
	   if(n>=str.length()-2)
		   return str;
	   else{
		   if(str.charAt(n)==')'){
			   str=str.substring(0, n+1)+"\n"+str.substring(n+1);
			   n+=2;
		   }else
			   n++;
		   
		   return substituteCloseBrace(str, n);
	   }
   }
   
   public static String fixCsp(String csp){
	   
	   return csp==null ? null : substituteCloseBrace(csp, 0);
   }
}

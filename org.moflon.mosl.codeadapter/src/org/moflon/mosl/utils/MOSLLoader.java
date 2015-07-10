package org.moflon.mosl.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.mosl.MOSLUtils;
import org.moflon.mosl.utils.exceptions.MoslLoadException;

import Moca.CodeAdapter;
import MocaTree.Attribute;
import MocaTree.Folder;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import MocaTree.Text;

public class MOSLLoader {
	
	private CodeAdapter codeAdapter;
	
	public MOSLLoader () {
	      codeAdapter = MOSLUtils.createCodeAdapter();
	}
	
	   public Folder getImport(Node importNode)
	   {
	     Folder imports = MocaTreeFactory.eINSTANCE.createFolder();
	     imports.setName("imports");
	     
	      // load implicit imports
	      try {
	         URL url = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/templates/ImplicitImports/", "MOSLCodeAdapter");
	         File file=new File(url.getFile());
	         Folder implicitImports=codeAdapter.parse(file);
	         imports.getSubFolder().add(implicitImports);
	         
	      } catch (Exception e) {
	    	 e.printStackTrace();
	         throw new MoslLoadException("Cannot load implicit Imports", e);
	      }

//	      Folder external = MocaTreeFactory.eINSTANCE.createFolder();
//	      external.setName("ExternalImports");
	      
	      //FIXME after Untransforming import from Local
//	      try {
//	         // Try to resolve the import via a local project
//	    	  
//	    	  
//	    	  
//	         IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(identifier);
//	         if (project != null) {
//	            IResource resource = project.findMember(".temp/" + identifier + ".moca.xmi");
//	            
//	            if (resource != null && resource.exists()) {
//	               return new URL("file://" + resource.getRawLocation().toPortableString().replaceAll("\\s", "%20"));
//	            }
//	         }
//	      } catch (MalformedURLException mue) {
//	         throw new IllegalArgumentException(mue);
//	      }
//	         
//	      throw new IllegalArgumentException("Cannot resolve import '" + identifier + "'.");
//	      imports.getSubFolder().add(external);
	      
	      
	      return imports;
	   }
	   
	   public static Node loadTrees(Node external){
		   Node importedTrees=null;
		   try{
			   URL urlToImplicitImports = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/trees/CoreLangugae.coca.xmi", "MOSLCodeAdapter");
			   importedTrees=MOSLUtils.loadTree(urlToImplicitImports, Node.class);
			   if(external!=null){
				   for(Text text : external.getChildren()){
					   Node child = Node.class.cast(text);
					   Node externalTree = null;
					   Attribute name=new ArrayList<Attribute>(child.getAttribute("name")).get(0);
					   IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name.getValue());
				         if (project != null) {
				            IResource resource = project.findMember(".temp/" + name.getValue() + ".moca.xmi");
				            
				            if (resource != null && resource.exists()) {
				               externalTree = MOSLUtils.loadTree(new URL("file://" + resource.getRawLocation().toPortableString().replaceAll("\\s", "%20")), Node.class);
				               mergeEATrees(importedTrees, externalTree);
				            }
				         }
				   }
			   }
		   }catch (Exception e) {
		         throw new MoslLoadException("Cannot load imported Trees", e);
		      }
		   
		   
		   return importedTrees;
	   }
	   
	   private static void mergeEATrees(Node importedTree, Node externalTree) throws Exception{
		   Node importedExportedTree = Node.class.cast(new ArrayList<>(importedTree.getChildren("exportedTree")).get(0));
		   Node externalExportedTree = Node.class.cast(new ArrayList<>(externalTree.getChildren("exportedTree")).get(0));
		   for(Text text : externalExportedTree.getChildren()){
			   Node child = Node.class.cast(text);
			   child.setParentNode(importedExportedTree);
		   }		   
	   }

	   private MocaTree.File getFileFromFolder(Folder folder, String fileName){
		   for(MocaTree.File file : folder.getFile()){
			   if(file.getName().compareTo(fileName)==0){
				   return file;
			   }
		   }
		   return null;
	   }
}

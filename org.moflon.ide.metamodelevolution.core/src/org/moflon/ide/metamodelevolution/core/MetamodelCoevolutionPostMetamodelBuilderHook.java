package org.moflon.ide.metamodelevolution.core;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;

public class MetamodelCoevolutionPostMetamodelBuilderHook implements PostMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelBuilder.class);
   
   @Override
   public IStatus run(final PostMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      logger.debug("Performing post-build step for meta-model co-evolution support");
      
      /*IFile mocaFile = WorkspaceHelper.getChangesMocaTree(project);

      if (mocaFile.exists())
      {
         // Create and initialize resource set
         ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(set);
         
         // Load Moca tree in read-only mode
         URI mocaFileURI = URI.createPlatformResourceURI(mocaFile.getFullPath().toString(), true);
         Resource mocaTreeResource = set.getResource(mocaFileURI, true);
         Node mocaTree = (Node) mocaTreeResource.getContents().get(0);
         
         Map<String, String> delta = new HashMap<String, String>();
         delta = parseChangesTree(mocaTree, delta);
         System.out.println(delta);
         processDelta(delta, project);
      }*/
      
      return Status.OK_STATUS;
   }
   
   /*private Map<String, String> parseChangesTree(final Node tree, Map<String, String> delta)
   {
	   if (tree.getName() != null && tree.getName().equals("EClass"))
	   {
		   EList<Attribute> attributes2 = tree.getAttribute();
		   for (Attribute attr : attributes2)
		   {
			   if (!(attr.getName().equals("baseClasses")))				   
				   delta.put(attr.getName(), attr.getValue());
		   }
		   return delta;
	   }	
	   else 
	   {
		   final EList<Text> children = tree.getChildren();
		   for (Text text : children)
		   {
			   Node node = (Node)text;		   			   
			   parseChangesTree(node, delta);
		   }
		   return delta;
	   }
	}
   
   private void processDelta(Map<String, String> delta, IProject project)
   {
	      // adapt java code
		   RenameRefactoring processor = new RenameClassRefactoring();
		   processor.refactor(project, delta.get("previousName"), delta.get("name"), delta.get("packageName"));
		   
		   RenameRefactoring implProcessor = new RenameClassRefactoring();
		   implProcessor.refactor(project, "/impl/" + delta.get("previousName") + "Impl", delta.get("name") + "Impl", delta.get("packageName"));
		   
		   // process injections
		   try {
			   
			   JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();

			   WorkspaceHelper.clearFolder(project, WorkspaceHelper.INJECTION_FOLDER, new NullProgressMonitor());
			   
			   IFolder genFolder = WorkspaceHelper.addFolder(project, WorkspaceHelper.GEN_FOLDER, new NullProgressMonitor());
			   genFolder.accept(new IResourceVisitor()
				{
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource.getType() == (IResource.FILE))
							extractor.extractInjectionNonInteractively((IFile) resource);
						return true;
					}				
				}
				);
				
			   } catch (CoreException | URISyntaxException | IOException e) {
				e.printStackTrace();
			}
   }*/
}

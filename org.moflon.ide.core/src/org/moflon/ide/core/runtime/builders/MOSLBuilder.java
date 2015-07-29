package org.moflon.ide.core.runtime.builders;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.CoreActivator;
import org.moflon.mosl.MOSLUtils;
import org.moflon.mosl.utils.AbstractResolver;
import org.moflon.mosl.utils.GuidGenerator;
import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

import MOSLCodeAdapter.eaTreeTransformation.EaTreeTransformationFactory;
import MOSLCodeAdapter.eaTreeTransformation.EaTreeTransformator;
import MOSLCodeAdapter.moslPlus.MOSLToMOSLPlusConverter;
import MOSLCodeAdapter.moslPlus.MoslErrorMessage;
import MOSLCodeAdapter.moslPlus.MoslPlusFactory;
import Moca.CodeAdapter;
import MocaTree.Node;

public class MOSLBuilder extends AbstractBuilder
{
   private static final Logger logger = Logger.getLogger(MOSLBuilder.class);

   @Override
   public boolean visit(final IResource resource) throws CoreException
   {
      return false;
   }

   @Override
   public boolean visit(final IResourceDelta delta) throws CoreException
   {
      // Get changes and call visit on *.sch and *.tgg files
      IResourceDelta[] changes = delta.getAffectedChildren();
      for (int i = 0; i < changes.length; i++)
      {
         IResource resource = changes[i].getResource();
         if ("MOSL".equals(resource.getName()) && (changes[i].getKind() == IResourceDelta.CHANGED || changes[i].getKind() == IResourceDelta.ADDED))
         {
            final IProgressMonitor progressMonitorForIncrementalChanges = this.getProgressMonitorForIncrementalChanges();
            processMOSL(WorkspaceHelper.createSubMonitor(progressMonitorForIncrementalChanges, 100), false);
         }
         visit(changes[i]);
      }

      return false;
   }

   @Override
   protected boolean processResource(final IProgressMonitor monitor) throws CoreException
   {
      processMOSL(monitor, true);
      return false;
   }

   protected boolean processMOSL(final IProgressMonitor monitor, final boolean triggerFullBuild) throws CoreException
   {
      IProject project = getProject();
      IFolder moslFolder = project.getFolder("MOSL");
      try
      {
         monitor.beginTask("Processing MOSL", 8);
         logger.debug("Building project: " + getProject().getName() + " (fullBuild: " + triggerFullBuild + ")");

         CodeAdapter codeAdapter = MOSLUtils.createCodeAdapter();

         GuidGenerator.init(getProject().getName());
         CoreActivator.getDefault().setDirty(project, false);

         project.deleteMarkers(WorkspaceHelper.MOSL_PROBLEM_MARKER_ID, false, IResource.DEPTH_INFINITE);
         monitor.worked(1);

         File dir = moslFolder.getLocation().toFile();

         // Perform text to tree
         MocaTree.Folder moslTree = codeAdapter.parse(dir);
         if (codeAdapter.getProblems().size() > 0)
         {
            codeAdapter.getProblems().stream().distinct().forEach(p -> 
              {
                try {
                   IResource resource;
                   resource = findResource(moslFolder, p.getSource());
                   createMarker(resource != null ? resource : moslFolder, p.getLine(), p.getCharacterPositionStart(), p.getCharacterPositionEnd(), p.getMessage(), "Parser", null);
                 } catch (Exception e) {
                    logger.debug("Can't create marker");
                 }
              });

            return false;
         }
         monitor.worked(1);

         /* Perform tree to model */
         MOSLToMOSLPlusConverter converter = MoslPlusFactory.eINSTANCE.createMOSLToMOSLPlusConverter();
         EaTreeTransformator transformer = EaTreeTransformationFactory.eINSTANCE.createEaTreeTransformator();

         IFolder temp = project.getFolder(".temp");
         if (temp.exists())
            temp.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         else
            monitor.worked(1);

         temp.create(true, true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), moslTree,
               temp.getLocation().toFile() + WorkspaceHelper.PATH_SEPARATOR + project.getName() + ".mosl.xmi");
         monitor.worked(1);

         Node moslPlusTree = converter.convertMOSLFolder(moslTree);
         eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), moslPlusTree, temp.getLocation().toFile() + WorkspaceHelper.PATH_SEPARATOR
               + project.getName() + ".mosl.plus.xmi");
         monitor.worked(1);

         if (converter.getErrorHandler().size() == 0)
         {
            Node eaTree = transformer.transform(moslPlusTree);
            eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), eaTree,
                  temp.getLocation().toFile() + WorkspaceHelper.PATH_SEPARATOR + project.getName() + ".moca.xmi");
            monitor.worked(1);
         } else
            createErrorMarker(converter.getErrorHandler(), moslFolder);
         temp.refreshLocal(IResource.DEPTH_INFINITE, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
      } catch (Exception e)
      {
         if (e instanceof CanNotResolvePathException)
         {
            CanNotResolvePathException cnrpe = (CanNotResolvePathException) e;

            int lastSlash = cnrpe.getReferencePath().lastIndexOf('/');
            String pathToEClassWithMissingPattern = cnrpe.getReferencePath().substring(0, lastSlash);
            IResource resource = moslFolder.getFile(pathToEClassWithMissingPattern + ".eclass");

            // TODO [szander] Improve for cases where the resource is not an eclass - maybe check the category?
            if (!resource.exists())
               resource = moslFolder;

            int lastSlashBeforePattern = cnrpe.getName().lastIndexOf('/');
            String patternName = cnrpe.getName().substring(lastSlashBeforePattern + 1, cnrpe.getName().length());

            String message = "Cannot find: '" + patternName + "' for path '" + cnrpe.getReferencePath() + "'";
            createMarker(resource, -1, -1, -1, message, CanNotResolvePathException.class.getSimpleName(), ((CanNotResolvePathException) e).getPath());
         } else
         {
            createMarker(moslFolder, -1, -1, -1, "I'm unable to handle this MOSL specification due to some errors", e.getClass().getSimpleName(), "");
            logger.debug("Unable to handle MOSL spec: " + e.getMessage());
         }

         return false;
      } finally
      {
         monitor.done();
      }

      return true;
   }

   private void createErrorMarker(List<MoslErrorMessage> errors, IFolder moslFolder) throws CoreException{
	   for(MoslErrorMessage errorMessage : errors){	
		   String filePath = errorMessage.getFileName();
		   IResource resource = null;
		   if(filePath !=null && filePath.compareTo("")!=0)
			   resource = moslFolder.getFile(filePath);
	       if(resource==null || !resource.exists())
	    	   resource = moslFolder;
	       //createMarker(resource,errorMessage.getErrorNode().getStartLineIndex() , errorMessage.getErrorNode().getStartIndex(), errorMessage.getErrorNode().getStopIndex(), errorMessage.getMessage(), "SemanticError");
	       if(errorMessage.getCategory()!=null)
	    	   createMarker(resource, -1, -1, -1, errorMessage.getMessage(), "SemanticError:" + AbstractResolver.getStringOfCategory(errorMessage.getCategory()), errorMessage.getPath());
	       else
	    	   createMarker(moslFolder, -1, -1, -1, errorMessage.getMessage(), "SemanticError", errorMessage.getPath());
	   }
   }
   
   @SuppressWarnings("unused")
   private IResource findResourceForError(final IFolder moslFolder, final String packageName, final String className) throws CoreException
   {
      StringBuilder sb = new StringBuilder();

      if (!StringUtils.isBlank(packageName))
      {
         sb.append(packageName.replace(".", WorkspaceHelper.PATH_SEPARATOR));
      }
      if (!StringUtils.isBlank(className))
      {
         sb.append(className + ".eclass");
      }

      IResource resource = findResource(moslFolder, sb.toString());

      if (resource != null)
         return resource;

      return moslFolder;
   }

   private IResource findResource(final IContainer container, final String name) throws CoreException
   {
      // Try to find the member directly
      String containerPath = container.getRawLocation().toOSString();
      if (name.startsWith(containerPath))
      {
         IResource resource = container.findMember(name.substring(containerPath.length()));
         if (resource != null)
            return resource;
      } else
      {
         IResource resource = container.findMember(name);
         if (resource != null)
            return resource;
      }

      // Else search for a valid match
      for (IResource r : container.members())
      {
         if (r instanceof IContainer)
         {
            IResource ret = findResource((IContainer) r, name);
            if (ret != null)
               return ret;
         }
         if (r.getName().equals(name))
            return r;
      }
      return null;
   }

   // FIXME: This code is almost exactly contained in MarkerHelper (TextEditorFramework) and should be moved somewhere else.
   private void createMarker(final IResource resource, final int lineNumber, final int characterStart, final int characterEnd, final String message,
         final String errorType, final String referencePath) throws CoreException
   {
      logger.debug("Creating marker on resource: " + resource);
      IMarker m = resource.createMarker(WorkspaceHelper.MOSL_PROBLEM_MARKER_ID);
      m.setAttribute(IMarker.CHAR_START, -1);
      m.setAttribute(IMarker.CHAR_END, -1);
      if (lineNumber > 0)
         m.setAttribute(IMarker.LINE_NUMBER, lineNumber);

      m.setAttribute(IMarker.MESSAGE, message);
      m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
      m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      m.setAttribute("errorType", errorType == null ? "Unspecified" : errorType);
      m.setAttribute("referencePath", referencePath == null ? "" : referencePath);

      UIJob uiJob = new UIJob("Correct Marker") {
         @Override
         public IStatus runInUIThread(IProgressMonitor monitor)
         {
            try
            {
               correctLocationOfMarker(lineNumber, characterStart, characterEnd, m);
            } catch (Exception e)
            {
               return Status.CANCEL_STATUS;
            }

            return Status.OK_STATUS;
         }

         private void correctLocationOfMarker(final int lineNumber, final int characterStart, final int characterEnd, IMarker m) throws CoreException
         {
            IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
            if (editor instanceof AbstractTextEditor)
            {
               IDocumentProvider provider = AbstractTextEditor.class.cast(editor).getDocumentProvider();
               IDocument document = provider.getDocument(editor.getEditorInput());

               if (document != null)
               {
                  try
                  {
                     int posStart = characterStart + document.getLineOffset(lineNumber - 1);
                     int posEnd = characterEnd + document.getLineOffset(lineNumber - 1);

                     if (posStart < 0)
                     {
                        posStart = 0;
                        posEnd = 1;
                     }

                     m.setAttribute(IMarker.CHAR_START, posStart);
                     m.setAttribute(IMarker.CHAR_END, posEnd);
                  } catch (BadLocationException e)
                  {
                     logger.debug("Can't set position of marker");
                  }
               }
            }
         };
      };

      uiJob.setSystem(true);
      uiJob.schedule();
   }

   public static void convertEAPProjectToMOSL(final IProject project)
   {
      IFolder tempFolder = project.getFolder(".temp");
      IFolder moslFolder = project.getFolder("MOSL");

      if (moslFolder.exists())
         try
         {
            moslFolder.delete(true, null);
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      if (tempFolder.exists() && !moslFolder.exists())
      {

         IFile mocaFile = tempFolder.getFile(project.getName() + ".moca.xmi");

         if (mocaFile.exists())
         {
            logger.info("Converting project '" + project.getName() + "'");
            logger.info("Loading tree");
            ResourceSet set = new ResourceSetImpl();
            set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

            // URI mocaTreeURI = URI.createPlatformResourceURI(mocaFile.getFullPath().toString(), true);
            // Resource mocaTreeResource = set.getResource(mocaTreeURI, true);
            eMoflonEMFUtil.installCrossReferencers(set);
            // Node tree = (Node) mocaTreeResource.getContents().get(0);

            // Transform tree
            // logger.info(" - Transforming tree");
            // MOSLUntransformer untransformer = UntransformerFactory.eINSTANCE.createMOSLUntransformer();
            // Folder folder = untransformer.untransformEATree(tree);

            // URI debugURI = URI.createPlatformResourceURI("/" + project.getName() + "/debug/debug.xmi", true);
            // Resource debugResource = set.createResource(debugURI);
            // debugResource.getContents().add(folder);
            // try {
            // debugResource.save(null);
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            //
            // // Perform tree-to-text (using initial tree)
            // logger.info(" - Unparsing tree");
            // CodeAdapter codeAdapter = MOSLUtils.createCodeAdapter();
            // codeAdapter.unparse(ResourcesPlugin.getWorkspace().getRoot().getLocation().append(outputFolder.getParent().getFullPath()).toString(),
            // folder);
            //
            // if (codeAdapter.getProblems().size() > 0) {
            // for (Problem p : codeAdapter.getProblems()) {
            // logger.error("ERROR: " + p.getSource() + ":" + p.getLine() + ":" + p.getCharacterPositionStart() + " : "
            // + p.getMessage());
            // }
            // }
            //
            // logger.info(" - done");
         }
      }
   }

   @Override
   protected void cleanResource(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Cleaning", 2);
         IFolder f = getProject().getFolder(WorkspaceHelper.TEMP_FOLDER);
         
         if (f != null && f.exists())
         {
            f.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            f.create(true, true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }
      } finally
      {
         monitor.done();
      }
   }
}

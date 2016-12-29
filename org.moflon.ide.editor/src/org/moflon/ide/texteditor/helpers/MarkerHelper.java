package org.moflon.ide.texteditor.helpers;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;


public class MarkerHelper
{
   private static final Logger logger = Logger.getLogger(MarkerHelper.class);

   IResource resource;

   /**
    * returns the number of Markers for the given resource. all marker types are counted.
    * 
    * @param resource
    * @return
    */
   public static int getNumberOfMarkers(IResource resource)
   {
      IMarker[] markers = null;
      try
      {
         markers = resource.findMarkers(null, true, IResource.DEPTH_INFINITE);
      } catch (CoreException e)
      {
         LogUtils.error(logger, e);
      }
      return markers.length;
   }

   /**
    * removes all markers from given resource
    * 
    * @param resource
    */
   public static void removeMarkers(IResource resource)
   {
      try
      {
         if (resource.exists())
            resource.deleteMarkers(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, true, IResource.DEPTH_INFINITE);
      } catch (CoreException e)
      {
         LogUtils.error(logger, e);
      }

   }

   private static IDocument getDocument(ITextEditor textEditor)
   {
      IDocumentProvider provider = textEditor.getDocumentProvider();
      IDocument document = provider.getDocument(textEditor.getEditorInput());
      return document;
   }

   public static void reportError(IResource resource, Exception exception)
   {
      IMarker m;
      try
      {
         m = resource.createMarker(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID);
         m.setAttribute(IMarker.MESSAGE, exception.getMessage());
         m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
         m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      } catch (CoreException e)
      {
         LogUtils.error(logger, e);
      }
   }

   /**
    * creates an marker for the given error and binds it to resource
    * 
    * @param resource
    * @param error
    */
   public static void reportError(IResource resource, Problem problem, ITextEditor editor)
   {
      if (resource.getType() == IResource.FILE)
      {
         int line = problem.getLine();
         if (line == 0)
            line++;
         int posStart = 0;
         int posEnd = 0;

         try
         {
            IDocument document = getDocument(editor);
            if (document != null)
            {
               posStart = problem.getCharacterPositionStart() + document.getLineOffset(line - 1);
               posEnd = problem.getCharacterPositionEnd() + document.getLineOffset(line - 1);
               if (posStart < 0)
               {
                  posStart = 0;
                  posEnd = 1;
               }
            }
         } catch (BadLocationException e)
         {
            LogUtils.error(logger, e);
         }

         try
         {
            IMarker m = resource.createMarker(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID);
            m.setAttribute(IMarker.LINE_NUMBER, line);
            m.setAttribute(IMarker.CHAR_START, posStart);
            m.setAttribute(IMarker.CHAR_END, posEnd);
            m.setAttribute(IMarker.MESSAGE, (String) problem.getMessage());
            m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
            switch (problem.getType())
            {
            case INFO:
               m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
               break;
            case ERROR:
               m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
               break;
            case WARNING:
               m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
               break;
            default:
               break;
            }

         } catch (CoreException e)
         {
            LogUtils.error(logger, e);
         }
      }
   }


}

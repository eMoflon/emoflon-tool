package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.LogUtils;

public class RemoveXTextMarkersHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
      final List<IResource> resources = new ArrayList<>();
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();)
         {
            Object element = selectionIterator.next();
            if (element instanceof IResource) {
               resources.add((IResource) element);
            } else if (element instanceof IJavaProject)
            {
               resources.add(((IJavaProject)element).getProject());
            }
         }
      } else if (selection instanceof ITextSelection)
      {
         final IFile file = getEditedFile(event);
         resources.add(file);
      }
      
      resources.forEach(resource -> removeXtextMarkers(resource));
      
      return null;
   }

   // Hack related to Issue #781 (see https://github.com/eMoflon/emoflon-issues/issues/781) (rkluge)
   private final void removeXtextMarkers(IResource resource) {
      try {
         // Avoid binary dependency to XText by using the String ID of the error marker
         resource.deleteMarkers("org.eclipse.xtext.ui.check.fast", true, IResource.DEPTH_INFINITE);
      } catch (final CoreException e) {
         LogUtils.error(logger, e);
      }
   }
}

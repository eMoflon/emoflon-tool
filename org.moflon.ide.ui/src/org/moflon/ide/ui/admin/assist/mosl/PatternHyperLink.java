package org.moflon.ide.ui.admin.assist.mosl;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class PatternHyperLink implements IHyperlink
{

   private final IRegion region;

   private IFile other;

   public PatternHyperLink(final IResource resource, final IRegion region, final String text)
   {
      super();
      this.region = region;

      String name = resource.getName();
      if (name.endsWith(".eclass"))
      {
         String className = name.substring(0, name.length() - 7);
         IContainer container = resource.getParent();
         IContainer patternsFolder = (IContainer) container.findMember("_patterns");
         patternsFolder = (IContainer) patternsFolder.findMember(className);

         other = findPattern(patternsFolder, text);
      }
   }

   private IFile findPattern(final IContainer container, final String name)
   {
      try
      {
         for (IResource r : container.members())
         {
            if (r instanceof IFile && r.getName().equals(name + ".pattern"))
            {
               return (IFile) r;
            }
            if (r instanceof IContainer)
            {
               IFile f = findPattern((IContainer) r, name);
               if (f != null)
                  return f;
            }
         }
      } catch (CoreException ce)
      {
         ce.printStackTrace();
      }
      return null;
   }

   @Override
   public IRegion getHyperlinkRegion()
   {
      return region;
   }

   @Override
   public String getTypeLabel()
   {
      return null;
   }

   @Override
   public String getHyperlinkText()
   {

      return null;
   }

   @Override
   public void open()
   {
      if (other == null)
         return;

      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      try
      {
         IDE.openEditor(page, other);
      } catch (PartInitException e)
      {
         e.printStackTrace();
      }
   }

}

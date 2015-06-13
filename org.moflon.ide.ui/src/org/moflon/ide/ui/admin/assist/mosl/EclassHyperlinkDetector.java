package org.moflon.ide.ui.admin.assist.mosl;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

public class EclassHyperlinkDetector implements IHyperlinkDetector
{

   private final IResource resource;
   
   public EclassHyperlinkDetector(IResource resource)
   {
      this.resource = resource;
   }

   @Override
   public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks)
   {
      IRegion lineInfo;
      String line;

      try
      {
         IDocument document = textViewer.getDocument();

         lineInfo = document.getLineInformationOfOffset(region.getOffset());
         line = document.get(lineInfo.getOffset(), lineInfo.getLength());
      } catch (BadLocationException ex)
      {
         return null;
      }

      int begin = line.indexOf("[");
      int end = line.indexOf("]");

      if (end < 0 || begin < 0 || end == begin + 1)
         return null;

      String text = line.substring(begin + 1, end);
      IRegion newRegion = new Region(lineInfo.getOffset() + begin + 1, text.length());

      return new IHyperlink[] { new PatternHyperLink(resource, newRegion, text) };
   }
}

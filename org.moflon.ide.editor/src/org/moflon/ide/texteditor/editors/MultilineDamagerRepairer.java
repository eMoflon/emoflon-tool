package org.moflon.ide.texteditor.editors;

import org.apache.log4j.Logger;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.moflon.core.utilities.LogUtils;

public class MultilineDamagerRepairer extends DefaultDamagerRepairer
{
   private static final Logger logger = Logger.getLogger(MultilineDamagerRepairer.class);

   private int[] userRegionScope = new int[2];

   private int[] completeRegionScope = { 1, -1 };

   private boolean firstTimeOpening = true;

   public MultilineDamagerRepairer(ITokenScanner scanner, int[] regionScope)
   {
      super(scanner);
      this.userRegionScope = regionScope;
   }

   @Override
   public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent event, boolean documentPartitioningChanged)
   {
      int[] scopeToUse = userRegionScope;
      if (MoflonTextEditor.IsSaving || firstTimeOpening)
      {
         scopeToUse = completeRegionScope;
         firstTimeOpening = false;
      }

      if (documentPartitioningChanged || scopeToUse[0] > scopeToUse[1])
         return partition;

      int lowerBoundOffset = 0;
      int length = 0;

      IRegion desiredIRegion;
      try
      {

         IRegion changedLineRegion = event.fDocument.getLineInformationOfOffset(event.getOffset());
         lowerBoundOffset = changedLineRegion.getOffset();
         length = length + changedLineRegion.getLength();

         int lineIndex = event.fDocument.getLineOfOffset(event.getOffset());

         // compute lowerbound offset and length of alle lines before the current line if possible
         for (int i = -1; i >= scopeToUse[0]; i--)
         {
            int currentLineIndex = lineIndex + i;
            if (currentLineIndex >= 0)
            {
               IRegion currentLineRegion = event.fDocument.getLineInformation(currentLineIndex);
               lowerBoundOffset = currentLineRegion.getOffset();
               length = length + currentLineRegion.getLength() + 2;
            }
         }

         // compute length of all lines after the current line if possible
         for (int i = 1; i <= scopeToUse[1]; i++)
         {
            int currentLineIndex = lineIndex + i;
            if (currentLineIndex >= 0 && currentLineIndex < event.fDocument.getNumberOfLines())
            {
               length = length + event.fDocument.getLineInformation(currentLineIndex).getLength() + 2;
            }
         }
      } catch (BadLocationException e)
      {
         LogUtils.error(logger, e);
      }
      desiredIRegion = new Region(lowerBoundOffset, length);

      return desiredIRegion;
   }

}

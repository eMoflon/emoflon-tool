package org.moflon.ide.texteditor.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;

public class MultilineDamagerRepairer extends DefaultDamagerRepairer
{
	private int[] userRegionScope = new int[2];
	private int[] completeRegionScope = {1,-1};
	
	private boolean firstTimeOpening = true;
	
	public MultilineDamagerRepairer(ITokenScanner scanner, int[] regionScope)
	{
		super(scanner);
		this.userRegionScope = regionScope;
	}

	@Override
	public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent e, boolean documentPartitioningChanged)
	{
		int[] scopeToUse = userRegionScope;
		if(MoflonTextEditor.IsSaving || firstTimeOpening){
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

			IRegion changedLineRegion = e.fDocument.getLineInformationOfOffset(e.getOffset());
			lowerBoundOffset = changedLineRegion.getOffset();
			length = length + changedLineRegion.getLength();

			int lineIndex = e.fDocument.getLineOfOffset(e.getOffset());

			//compute lowerbound offset and length of alle lines before the current line if possible
			for (int i = -1; i >= scopeToUse[0]; i--)
			{
				int currentLineIndex = lineIndex + i;
				if (currentLineIndex >= 0)
				{
					IRegion currentLineRegion = e.fDocument.getLineInformation(currentLineIndex);
					lowerBoundOffset = currentLineRegion.getOffset();
					length = length + currentLineRegion.getLength() + 2;
				}
			}
			
			//compute length of all lines after the current line if possible
			for (int i = 1; i <= scopeToUse[1]; i++)
			{
				int currentLineIndex = lineIndex + i;
				if (currentLineIndex >= 0 && currentLineIndex < e.fDocument.getNumberOfLines())
				{
					length = length + e.fDocument.getLineInformation(currentLineIndex).getLength() + 2;
				}
			}
		}
		catch (BadLocationException e1)
		{
			e1.printStackTrace();
		}
		desiredIRegion = new Region(lowerBoundOffset, length);

		return desiredIRegion;
	}

}

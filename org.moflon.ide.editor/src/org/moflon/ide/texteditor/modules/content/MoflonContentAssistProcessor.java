package org.moflon.ide.texteditor.modules.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.Template;
import org.moflon.ide.texteditor.config.MoflonTextEditorConfigIntern;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;

public class MoflonContentAssistProcessor implements IContentAssistProcessor
{
   
   protected MoflonTextCompletionProcessor templateCompletionProcessor;
   
   private Boolean cursorBasedPositioning = false;
   
   private int cursorOffset;
   
   private MoflonTextEditorConfigIntern config;
   
   public MoflonContentAssistProcessor(MoflonTextEditorConfigIntern config)
   {
      this.config = config;
      this.templateCompletionProcessor = new MoflonTextCompletionProcessor();
      
   }
   
   @Override
   public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset)
   {
      setCursorOffset(offset);
      setCursorBasedPositioning(true);
      Collection<MoflonEditorTemplate> moflonTemplatesUnsorted = config.getTemplates();
      setCursorBasedPositioning(false);
      ArrayList<MoflonEditorTemplate> priorityQueue = new ArrayList<MoflonEditorTemplate>();
      for (MoflonEditorTemplate moflonTemplate : moflonTemplatesUnsorted)
      {
         priorityQueue.add(moflonTemplate);
      }
      Collections.sort(priorityQueue);
      Template[] templates = priorityQueue.toArray(new Template[priorityQueue.size()]);
      templateCompletionProcessor.setTemplates(templates);
      return templateCompletionProcessor.computeCompletionProposals(viewer, offset);

   }

   @Override
   public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset)
   {
      return templateCompletionProcessor.computeContextInformation(viewer, offset);
   }

   @Override
   public char[] getCompletionProposalAutoActivationCharacters()
   {

      return templateCompletionProcessor.getCompletionProposalAutoActivationCharacters();
   }

   @Override
   public char[] getContextInformationAutoActivationCharacters()
   {
      return templateCompletionProcessor.getContextInformationAutoActivationCharacters();
   }

   @Override
   public String getErrorMessage()
   {
      return templateCompletionProcessor.getErrorMessage();
   }

   @Override
   public IContextInformationValidator getContextInformationValidator()
   {
      return templateCompletionProcessor.getContextInformationValidator();
   }

   public Boolean getCursorBasedPositioning()
   {
      return cursorBasedPositioning;
   }

   public void setCursorBasedPositioning(Boolean cursorBasedPositioning)
   {
      this.cursorBasedPositioning = cursorBasedPositioning;
   }

   public int getCursorOffset()
   {
      return cursorOffset;
   }

   public void setCursorOffset(int cursorOffset)
   {
      this.cursorOffset = cursorOffset;
   }

}

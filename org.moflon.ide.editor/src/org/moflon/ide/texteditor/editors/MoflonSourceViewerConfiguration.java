package org.moflon.ide.texteditor.editors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.ContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.moflon.core.utilities.LogUtils;
import org.moflon.ide.texteditor.builders.TextEditorBuilder;
import org.moflon.ide.texteditor.builders.TextEditorBuilderHelper;
import org.moflon.ide.texteditor.config.MoflonTextEditorConfigIntern;

public class MoflonSourceViewerConfiguration extends SourceViewerConfiguration
{
   private static final Logger logger = Logger.getLogger(MoflonSourceViewerConfiguration.class);

   private Object loadedMoflonTextEditorConfiguration;

   public MoflonSourceViewerConfiguration(final IProject selectedProject, final IResource resource, final MoflonTextEditorConfigIntern moflonTextEditorConfiguration,
         final MoflonTextEditor editor)
   {
      loadedMoflonTextEditorConfiguration = moflonTextEditorConfiguration;
      configureTextEditorBuilder(selectedProject, resource, editor);
   }

   private void configureTextEditorBuilder(final IProject selectedProject, final IResource resource, final MoflonTextEditor editor)
   {
      TextEditorBuilderHelper m2tSynch = (TextEditorBuilderHelper) loadedMoflonTextEditorConfiguration;
      TextEditorBuilder.activeEditors.add(m2tSynch);

      MoflonTextEditorConfigIntern config = (MoflonTextEditorConfigIntern) loadedMoflonTextEditorConfiguration;

      config.setProject(selectedProject);
      config.setResource(resource);
      config.setEditor(editor);

      this.associateTextEditorBuilderWhenNecessary(selectedProject);
   }

   public Object getMoflonTextEditorConfiguration()
   {
      return loadedMoflonTextEditorConfiguration;
   }

   @Override
   public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer)
   {
      PresentationReconciler reconciler = new PresentationReconciler();

      ITokenScanner tokenScanner = (ITokenScanner) loadedMoflonTextEditorConfiguration;
      MultilineDamagerRepairer dr = new MultilineDamagerRepairer(tokenScanner,
            ((MoflonTextEditorConfigIntern) (this.loadedMoflonTextEditorConfiguration)).getRefreshScope());
      reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
      reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

      return reconciler;
   }

   @Override
   public IContentAssistant getContentAssistant(final ISourceViewer sourceViewer)
   {
      ContentAssistant assistant = new ContentAssistant();
      assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

      IContentAssistProcessor processor = ((MoflonTextEditorConfigIntern) loadedMoflonTextEditorConfiguration).getContentAssisstProcessor();
      assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);

      assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
      assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

      return assistant;
   }

   private void associateTextEditorBuilderWhenNecessary(final IProject project)
   {
      final String BUILDER_ID = "org.moflon.ide.texteditor.editors.TextEditorBuilder";
      IProjectDescription desc = null;
      try
      {
         desc = project.getDescription();
      } catch (CoreException e)
      {
         LogUtils.error(logger, e);
      }
      ICommand[] commands = desc.getBuildSpec();
      boolean found = false;

      for (int i = 0; i < commands.length; ++i)
      {
         if (commands[i].getBuilderName().equals(BUILDER_ID))
         {
            found = true;
            break;
         }
      }
      if (!found)
      {
         // add builder to project
         ICommand command = desc.newCommand();
         command.setBuilderName(BUILDER_ID);
         ICommand[] newCommands = new ICommand[commands.length + 1];

         // Add it before other builders.
         System.arraycopy(commands, 0, newCommands, 1, commands.length);
         newCommands[0] = command;
         desc.setBuildSpec(newCommands);
         try
         {
            project.setDescription(desc, null);
         } catch (CoreException e)
         {
            LogUtils.error(logger, e);
         }
      }
   }

   @Override
   public IAnnotationHover getAnnotationHover(final ISourceViewer sourceViewer)
   {
      return new DefaultAnnotationHover();
   }

   @Override
   public IHyperlinkDetector[] getHyperlinkDetectors(final ISourceViewer sourceViewer)
   {
      MoflonTextEditorConfigIntern config = (MoflonTextEditorConfigIntern) loadedMoflonTextEditorConfiguration;
      return config.getHyperlinkDetectors();
   }

   @Override
   public IAutoEditStrategy[] getAutoEditStrategies(final ISourceViewer sourceViewer, final String contentType)
   {
      MoflonTextEditorConfigIntern config = (MoflonTextEditorConfigIntern) loadedMoflonTextEditorConfiguration;
      
      List<IAutoEditStrategy> s = new ArrayList<IAutoEditStrategy>();
      IAutoEditStrategy[] strategies = super.getAutoEditStrategies(sourceViewer, contentType);
      for (IAutoEditStrategy autoEditStrategy : strategies)
      {
         s.add(autoEditStrategy);
      }
      s.add(new DefaultIndentLineAutoEditStrategy());
      if(config.getAutoEditStrategy() != null){
         s.add(config.getAutoEditStrategy());         
      }
      IAutoEditStrategy[] res = s.toArray(new IAutoEditStrategy[0]);
      return res;
   }
   
   @Override
   public IContentFormatter getContentFormatter(final ISourceViewer sourceViewer)
   {
      ContentFormatter formatter = new ContentFormatter();
      
      IFormattingStrategy strategy = (IFormattingStrategy) loadedMoflonTextEditorConfiguration;
      
      formatter.setFormattingStrategy(strategy, IDocument.DEFAULT_CONTENT_TYPE);
      
      return formatter;
   }
}

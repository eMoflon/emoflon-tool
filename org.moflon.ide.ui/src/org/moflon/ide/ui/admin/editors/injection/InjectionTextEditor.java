package org.moflon.ide.ui.admin.editors.injection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.moflon.ide.texteditor.editors.MoflonSourceViewerConfiguration;
import org.moflon.ide.texteditor.editors.MoflonTextEditor;

public class InjectionTextEditor extends MoflonTextEditor
{
   @Override
   protected void configure(final IProject projectOfOpenedFile, final IFile openedFile)
   {
      final SourceViewerConfiguration sourceViewConfig = new MoflonSourceViewerConfiguration(projectOfOpenedFile, openedFile,
            new InjectionTextEditorConfiguration(), this);
      this.setSourceViewerConfiguration(sourceViewConfig);
   }

}

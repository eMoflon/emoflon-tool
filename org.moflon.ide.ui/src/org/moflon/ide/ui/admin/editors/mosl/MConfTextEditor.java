package org.moflon.ide.ui.admin.editors.mosl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.moflon.ide.texteditor.editors.MoflonSourceViewerConfiguration;
import org.moflon.ide.texteditor.editors.MoflonTextEditor;

public class MConfTextEditor extends MoflonTextEditor
{
   @Override
   protected void configure(IProject projectOfOpenedFile, IFile openedFile)
   {
      SourceViewerConfiguration sourceViewConfig = new MoflonSourceViewerConfiguration(projectOfOpenedFile, openedFile, new MConfTextEditorConfiguration(), this);
      this.setSourceViewerConfiguration(sourceViewConfig);
   }
}

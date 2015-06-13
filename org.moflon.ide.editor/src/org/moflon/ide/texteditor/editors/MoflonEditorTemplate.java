package org.moflon.ide.texteditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;

public class MoflonEditorTemplate extends Template implements Comparable<MoflonEditorTemplate>
{

   private int priority;

   public MoflonEditorTemplate(String name, String description, String pattern, int priority)
   {
      super(name, description, IDocument.DEFAULT_CONTENT_TYPE, pattern, true);
      this.priority = priority;
   }

   public int compareTo(MoflonEditorTemplate obj)
   {
      return obj.priority - priority;
   }
}
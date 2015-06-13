package org.moflon.ide.ui.admin.editors.mosl;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.moflon.ide.texteditor.config.MoflonTextEditorConfigExtern;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;

public abstract class AbstractMOSLTextEditorConfiguration extends MoflonTextEditorConfigExtern
{
   private static final List<String> ECORE_DATATYPES = Arrays.asList("EString", "EInt", "EBoolean");

   protected int sortCounter = 0;

   /**
    * Here, the delimiters, which specifies a boundary between words in a char sequence without whitespace, should be
    * defined. For example, in an expression like "int a=5;" the chars '=' and ';' are delimiters and shape tokens to be
    * handled separately despite the missing whitespaces. This method is called once for the initialization of a text
    * editor.
    * 
    */
   @Override
   public char[] getDelimiters()
   {
      char[] delimiters = {};
      delimiters = new char[] { ',', ';', '{', '}', ':', '@', '.', '!', ')', '(' };
      return delimiters;
   }

   protected void addECoreDatatypes(final Vector<? super MoflonEditorTemplate> templates)
   {
      for (String datatype : ECORE_DATATYPES)
      {
         templates.add(new MoflonEditorTemplate(datatype, "http://www.eclipse.org/ecore/" + datatype, datatype, sortCounter++));
      }
   }

  
   protected String findCurrentBoxType()
   {
      // Find current type
      String currentType = null;
      for (int i = -1; i > -500; i--)
      {
         if ("{".equals(getWord(i)))
         {
            currentType = getWord(i - 1);
            break;
         }
      }
      return currentType;
   }

   @Override
   public void setKeyWords()
   {
      addKeyWord("true").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("false").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("null").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("this").as(COLORS.VIOLET, BOLD, 0);
   }

   protected void linkHighlighting()
   {
      String correspondenceBackward = getWord(-4);
      String before = getWord(-3);
      String preprevious = getWord(-2);
      String previous = getWord(-1);
      String word = getWord(0);
      String next = getWord(1);

      if ("-".equals(word) || ("->".equals(word) && !"<-".equals(correspondenceBackward)))
      {
         if ("--".equals(previous) || "--".equals(before) || "--".equals(preprevious))
            highlight(COLORS.RED);
         else if ("++".equals(previous) || "++".equals(before) || "++".equals(preprevious))
            highlight(COLORS.GREEN);
         else
            highlight(COLORS.DARKBLUE);

      }
      if ("-".equals(previous) && ("->".equals(next) || word.endsWith("->")))
      {
         if ("++".equals(preprevious))
            highlight(COLORS.GREEN);
         else if ("--".equals(preprevious))
            highlight(COLORS.RED);
         else
            highlight(COLORS.DARKBLUE);
      } else if (word.startsWith("-") && ((word.endsWith("->") && word.charAt(1) != '>') || "->".equals(next)))
      {
         if ("++".equals(previous))
            highlight(COLORS.GREEN);
         else if ("--".equals(previous))
            highlight(COLORS.RED);
         else
            highlight(COLORS.DARKBLUE);
      }
   }

   protected void semanticHighlighting()
   {
      String before = getWord(-3);
      String previous = getWord(-1);
      String word = getWord(0);
      String next = getWord(1);
      String after = getWord(3);

      if ("@".equals(previous))
      {
         if (!"this".equals(word))
            highlight(COLORS.BLACK, BOLD);
      } else if ("!".equals(previous))
         highlight(COLORS.BLACK, ITALIC);
      else if ("{".equals(next) || "{".equals(after))
      {
         if ("++".equals(previous) || "++".equals(before))
            highlight(COLORS.GREEN);
         else if ("--".equals(previous) || "--".equals(before))
            highlight(COLORS.RED);
      } else if (".".equals(previous))
      {
         highlight(COLORS.ORANGE);
      }
   }

   protected void commentHighlighting()
   {
      highlightRange(COLORS.GREEN).startsWith("//").endsWith("\n");
   }

   protected void stringHighlighting()
   {
      highlightRange(COLORS.BLUE).startsWith("\"").endsWith("\"");
      highlightRange(COLORS.BLUE).startsWith("\'").endsWith("\'");
   }

}

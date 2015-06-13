package org.moflon.ide.ui.admin.editors.antlr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

import org.antlr.grammar.v3.ANTLRv3Lexer;
import org.antlr.grammar.v3.ANTLRv3Parser;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.ide.texteditor.config.MoflonTextEditorConfigExtern;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;
import org.moflon.moca.MocaUtil;

import MocaTree.Node;

/**
 * This class provides project specific editor configuration for text highlighting and templates. Its generated methods
 * are to be filled and invoked during runtime by MOFLON Eclipse plugin in order to achieve a custom text editor
 * functionality.
 */
public class AntlrTextEditorConfiguration extends MoflonTextEditorConfigExtern
{
   private static final Pattern LOWERCASE = Pattern.compile("[a-z][a-z_0-9]*");

   private static final Pattern UPPERCASE = Pattern.compile("[A-Z][A-Z_0-9]*");

   /**
    * Here, the project specific keywords and their colors should be defined. This method is called once for the
    * initialization of a text editor.
    */
   @Override
   public void setKeyWords()
   {
      addKeyWord("lexer").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("parser").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("grammar").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("tokens").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("header").as(COLORS.VIOLET, BOLD, 0);
   }

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
      delimiters = new char[] { '{', '}', '(', ')', '#', '"', '/', ':', ' ', '\n', ';', '=', '[', ']' };
      return delimiters;
   }

   /**
    * This method is called each time when the color of a word should be determined by Eclipse. With getWord(int) the
    * context of the position can be accessed, at which Eclipse computes currently the highlighting color. The
    * highlight(COLORS) method from the super type should also be called in order to define a (context-dependent)
    * highlighting color.
    */
   @Override
   public void highlightWord()
   {
      String word = getWord(0);
      String next = getWord(1);

      String firstLetter = word.equals("")? "" : word.charAt(0) + "";
      
      if (LOWERCASE.matcher(firstLetter).matches()) 
      {
         if (next.equals("="))
         {
            highlight(COLORS.DARKBLUE);
         } else
         {
            highlight(COLORS.BLUE);
         }
      } else if (UPPERCASE.matcher(firstLetter).matches())
      {
         highlight(COLORS.VIOLET);
      } else if (word.startsWith("$"))
      {
         highlight(COLORS.DARKBLUE);
      }
   }

   /**
    * Here the scope for syntax highlighting can be defined. {-4,4} means four lines before the text change and four
    * lines after the text change. Default is the complete dokument which is {1,-1}.
    */
   @Override
   public int[] getRefreshScope()
   {
      return new int[] { -4, 4 };
   }

   @Override
   public Node getUnderlyingTree(final String filepath)
   {
      try
      {
         CharStream input = new ANTLRFileStream(filepath);
         // BUILD AST
         ANTLRv3Lexer lex = new ANTLRv3Lexer(input);
         CommonTokenStream tokens = new CommonTokenStream(lex);
         ANTLRv3Parser g = new ANTLRv3Parser(tokens);
         ANTLRv3Parser.grammarDef_return r = g.grammarDef();
         CommonTree t = r.getTree();
         return MocaUtil.commonTreeToMocaTree(t);
      } catch (Exception e)
      {
         return null;
      }
   }

   /**
    * Here, start and end strings of special text ranges defined which are to be highlighted completely. For example, a
    * javadoc starts with "/*" and ends with "*"+"/" and is highlighted completely with blue color.
    */
   @Override
   public void highlightSequence()
   {
      highlightRange(COLORS.GREEN).startsWith("//").endsWith("\n");
      highlightRange(COLORS.GREEN).startsWith("/*").endsWith("*/");
      highlightRange(COLORS.BLUE).startsWith("'").endsWith("'");
      highlightRange(COLORS.BLUE).startsWith("\"").endsWith("\"");
      highlightRange(COLORS.GRAY).startsWith("@header {").endsWith("}");
      highlightRange(COLORS.GRAY).startsWith("@members {").endsWith("}");
   }

   /**
    * This method returns a collection of templates when user presses ctrl+space in a text editor. With the help of
    * getWord(int) method, the context from the cursor position can be accessed.
    */
   @Override
   public Collection<MoflonEditorTemplate> getTemplates()
   {
      Vector<MoflonEditorTemplate> templates = new Vector<MoflonEditorTemplate>();
      // String previous = getWord(-1);
      // if (previous.equals("void")) {
      // MoflonEditorTemplate methodSignatureTemplate = new
      // MoflonEditorTemplate(
      // "method signature", "method signature template",
      // "${methodName} (${parameters})", 0);
      // templates.add(methodSignatureTemplate);
      // }
      return templates;
   }

   /**
    * User defines with this method an one-to-one mapping between model files (.xmi) and text files (opened with text
    * editor). The absolute paths of the files should be put into returned HashMap. You can use abs(path) function to
    * translate a project relative path to an absolute path
    */
   @Override
   public HashMap<String, String> getModelPathsToTextPaths()
   {
      HashMap<String, String> m2tPathes = new HashMap<String, String>();
      // m2tPathes.put(abs("/instances/out/test.xmi"),
      // abs("/instances/in/test.txt"));
      return m2tPathes;
   }

   /**
    * This method is called when a text file has been changed of which absolute path is a contained value in HashMap
    * returned by getModelPathesToTextPathes(). The argument is the absolute path of the changed text file. With the
    * help of calling getModelPath(textFilePath)) the path of the corresponding model file can also be accessed. Please
    * don't use any project relative path in this method. (use abs(projectRelativePath))
    */
   @Override
   public void onSave(final String textFilePath)
   {
      super.onSave(textFilePath);
   }

   /**
    * This method is called when a model file has changed of which absolute path is a contained key in the HashMap
    * returned by getModelPathesToTextPathes(). The argument is the absolute path of the changed model file. Please
    * don't use any project relative path in this method. (use abs(projectRelativePath))
    */
   @Override
   public void syncText(final String modelFilePath)
   {
      // helper.setInputModel(MocaTreePackage.eINSTANCE, modelFilePath);
      // your model2text transformation code here
   }

   /**
    * This method is called, after textToModel transformation All Problems which where collected during onSave operation
    * can be reported as markers in the TextEditor. Problems are accessible via the codeAdapter:
    * codeAdapter.getProblems() A maker can be created, using AddMarker(problem,pathToTextfile);
    */
   @Override
   public void getProblems()
   {
      super.getProblems();
   }

   @Override
   public boolean foldNode(final Node node)
   {
      if (node.getName().equals("@"))
         return true;
      return super.foldNode(node);
   }
}

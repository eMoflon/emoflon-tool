package org.moflon.ide.ui.admin.editors.injection;


import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.moflon.ide.texteditor.config.MoflonTextEditorConfigExtern;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;
import org.moflon.moca.inject.parser.InjectImplParserAdapter;
import org.moflon.moca.inject.parser.InjectParserAdapter;

import MocaTree.Node;

/**
 * This class provides project specific editor configuration for text highlighting and templates. Its generated methods
 * are to be filled and invoked during runtime by MOFLON Eclipse plugin in order to achieve a custom text editor
 * functionality.
 */
public class InjectionTextEditorConfiguration extends MoflonTextEditorConfigExtern
{
   

   /**
    * Here, the project specific keywords and their colors should be defined. This method is called once for the
    * initialization of a text editor.
    */
   public void setKeyWords()
   {
      // choose one of the following calls:
      addKeyWord("partial").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("class").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("return").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("public").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("import").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("protected").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("private").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("@Override").as(COLORS.GRAY, ITALIC, 0);
      addKeyWord("throws").as(COLORS.VIOLET, BOLD, 0);
      
      
      addKeyWord("@members").as(COLORS.BLUE);
      addKeyWord("@model").as(COLORS.BLUE);
      addKeyWord("<--").as(COLORS.GRAY, BOLD, 0);
      addKeyWord("-->").as(COLORS.GRAY, BOLD, 0);
      
      // addKeyWord("public").as(COLORS.VIOLET,BOLD,UNDERLINED);
      // addKeyWord("public").as(COLORS.VIOLET, COLORS.GRAY, String "Arial", 12, BOLD, UNDERLINED)
   }

   /**
    * Here, the delimiters, which specifies a boundary between words in a char sequence without whitespace, should be
    * defined. For example, in an expression like "int a=5;" the chars '=' and ';' are delimiters and shape tokens to be
    * handled separately despite the missing whitespaces. This method is called once for the initialization of a text
    * editor.
    * 
    */
   public char[] getDelimiters()
   {
      char[] delimiters = {};
      // delimiters = new char[]{',', ';', '{', '}', ':'};
      return delimiters;
   }

   /**
    * Here the scope for syntax highlighting can be defined. {-4,4} means four lines before the text
    * change and four lines after the text change. Default is the complete document which is {1,-1}.
    */
   @Override
	public int[] getRefreshScope()
	{
	   return super.getRefreshScope();
	}
   
   @Override
   public void addParserAdapters()
   {
      addParser(new InjectImplParserAdapter());
      addParser(new InjectParserAdapter());
   }
   /**
    * This method is called each time when the color of a word should be determined by Eclipse. With getWord(int) the
    * context of the position can be accessed, at which Eclipse computes currently the highlighting color. The
    * highlight(COLORS) method from the super type should also be called in order to define a (context-dependent)
    * highlighting color.
    */
   public void highlightWord()
   {
      // String previous = getWord(-1);
      // String word = getWord(0);
      // String next = getWord(1);

      // if (previous.equals("=") && next.equals(";"))
      // highlight(COLORS.BLUE, BOLD | ITALIC);

      // if (previous.equals(") && next.equals("))
      // choose one of the following calls:
      // highlight(COLORS.BLUE);
      // highlight(COLORS.BLUE, BOLD | ITALIC);
      // highlight(COLORS.BLUE, COLORS.GRAY, "Arial", 14, BOLD, UNDERLINED);

   }

   /**
    * Here, start and end strings of special text ranges defined which are to be highlighted completely. For example, a
    * javadoc starts with "/*" and ends with "*"+"/" and is highlighted completely with blue color.
    */
   public void highlightSequence()
   {
      // choose one of the following calls:
      // highlightRange(COLORS.GREEN).startsWith("/*").endsWith("*/");
      // highlightRange(COLORS.GRAY, ITALIC).startsWith("<--").endsWith("-->");
      // highlightRange(COLORS.GREEN, COLORS.GRAY, "Arial", 10, BOLD | ITALIC,
      // UNDERLINED).startsWith("/*").endsWith("*/");
   }

   /**
    * This method returns a collection of templates when user presses ctrl+space in a text editor. With the help of
    * getWord(int) method, the context from the cursor position can be accessed.
    */
   public Collection<MoflonEditorTemplate> getTemplates()
   {
      Vector<MoflonEditorTemplate> templates = new Vector<MoflonEditorTemplate>();
      String previous = getWord(-1);

      if (previous.equals("@"))
      {
         MoflonEditorTemplate membersTemplate = new MoflonEditorTemplate("Inject member",
               "Arbitrary code can be injected in the members section (there should only be one members section!).", "members <--   -->", 1);
         templates.add(membersTemplate);

         MoflonEditorTemplate modelTemplate = new MoflonEditorTemplate("Inject method in model",
               "An implementation for a method defined in the model can be injected in a model section (there should be one model section per method!).",
               "model ${nameOfMethod}(${TypeOfArg_nameOfArg})  <--   -->", 0);
         templates.add(modelTemplate);
      }

      return templates;
   }

   /**
    * User defines with this method an one-to-one mapping between model files (.xmi) and text files (opened with text
    * editor). The absolute paths of the files should be put into returned HashMap. You can use abs(path) function to
    * translate a project relative path to an absolute path
    */
   public HashMap<String, String> getModelPathsToTextPaths()
   {
      HashMap<String, String> m2tPaths = new HashMap<String, String>();
      // m2tPathes.put(abs("/instances/out/test.xmi"), abs("/instances/in/test.txt"));
      return m2tPaths;
   }

   

   /**
    * This method is called when a model file has changed of which absolute path is a contained key in the HashMap
    * returned by getModelPathesToTextPathes(). The argument is the absolute path of the changed model file. Please
    * don't use any project relative path in this method. (use abs(projectRelativePath))
    */
   public void syncText(String modelFilePath)
   {
      // helper.setInputModel(MocaTreePackage.eINSTANCE, modelFilePath);
      // your model2text transformation code here
   }

   @Override
   public boolean foldNode(Node node)
   {
      if(node.getName().equals("METHOD"))
      {
         return true;
      }
      return super.foldNode(node);
   }
   
   @Override
   public String getOutlineLabel(Node node)
   {
      if(node.getName().equals("METHOD"))
      {
         return "METHOD " + node.getChildren().get(0).getName();
      }
      return super.getOutlineLabel(node);
   }
   
   @Override
   public Boolean showInOutline(Node node)
   {

      if(node.getName().equals("MODEL"))
         return false;
      return super.showInOutline(node);
   }
   
}

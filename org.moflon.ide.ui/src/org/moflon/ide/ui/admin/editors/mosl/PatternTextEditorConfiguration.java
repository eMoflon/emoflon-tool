package org.moflon.ide.ui.admin.editors.mosl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;
//import org.moflon.mosl.editor.context.ContextStore;
import org.moflon.mosl.pattern.parser.PatternParserAdapter;

//import MOSLCodeAdapter.context.AttributeInfo;
//import MOSLCodeAdapter.context.ClassInfo;
//import MOSLCodeAdapter.context.ContextInformation;
import MocaTree.Attribute;
import MocaTree.Node;

/**
 * This class provides project specific editor configuration for text highlighting and templates. Its generated methods
 * are to be filled and invoked during runtime by MOFLON Eclipse plugin in order to achieve a custom text editor
 * functionality.
 */
public class PatternTextEditorConfiguration extends AbstractMOSLTextEditorConfiguration
{
   /**
    * Here, the project specific keywords and their colors should be defined. This method is called once for the
    * initialization of a text editor.
    */
   @Override
   public void setKeyWords()
   {
      super.setKeyWords();
      addKeyWord("pattern").as(COLORS.VIOLET, BOLD, 0);
      // choose one of the following calls:
      // addKeyWord("public").as(COLORS.VIOLET);
      // addKeyWord("public").as(COLORS.VIOLET, COLORS.GRAY, String "Arial", 12, BOLD, UNDERLINED)
      addKeyWord("++").as(COLORS.GREEN, 0, 0);
      addKeyWord("--").as(COLORS.RED, 0, 0);
      // addKeyWord("->").as(COLORS.BROWN, 0, 0);
   }

   /**
    * Here the scope for syntax highlighting can be defined. {-4,4} means four lines before the text change and four
    * lines after the text change. Default is the complete dokument which is {1,-1}.
    */
   @Override
   public int[] getRefreshScope()
   {
      return super.getRefreshScope();
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
      linkHighlighting();
      semanticHighlighting();

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
   @Override
   public void highlightSequence()
   {
      commentHighlighting();
      stringHighlighting();
      // highlightRange(COLORS.BROWN).startsWith("-").endsWith("->");
      // choose one of the following calls:
      // highlightRange(COLORS.GREEN).startsWith("/*").endsWith("*/");
      // highlightRange(COLORS.GREEN,BOLD).startsWith("/*").endsWith("*/");
      // highlightRange(COLORS.GREEN, COLORS.GRAY, "Arial", 10, BOLD | ITALIC,
      // UNDERLINED).startsWith("/*").endsWith("*/");
   }

   /**
    * This method returns a collection of templates when user presses ctrl+space in a text editor. With the help of
    * getWord(int) method, the context from the cursor position can be accessed.
    */
   @Override
   public Collection<MoflonEditorTemplate> getTemplates()
   {
      Vector<MoflonEditorTemplate> templates = new Vector<MoflonEditorTemplate>();
      sortCounter = 0;

      // Add basic templates
      templates.add(new MoflonEditorTemplate("link", "ObjectVariable link", "${modifier} -${name}-> ${object}", sortCounter++));
      templates.add(new MoflonEditorTemplate("objectvariable", "ObjectVariable", "${variable} : ${type}", sortCounter++));

      // ContextInformation info = ContextStore.getInstance().getContextInformation(getProject());

      // String previous = getWord(-1);

      // if (info != null)
      // {
      // if (":".equals(previous))
      // {
      // for (ClassInfo ci : info.getClasses())
      // {
      // templates.add(createClassInfoTemplate(ci));
      // }
      // addECoreDatatypes(templates);
      // }
      //
      // if ("->".equals(previous))
      // {
      // addBoxLinkTemplates(templates, info);
      // }
      //
      // if (".".equals(previous)) {
      // addAttributeTemplates(templates, info);
      // }
      // }

      return templates;
   }

   // protected void addAttributeTemplates(Vector<MoflonEditorTemplate> templates, ContextInformation info)
   // {
   // String currentType = findCurrentBoxType();
   //
   // for (AttributeInfo ai : info.getAttributes()) {
   // if (ai.getClassName().equals(currentType)) {
   // templates.add(createAttributeInfoTemplate(ai));
   // }
   // }
   //
   //
   // }

   /**
    * User defines with this method an one-to-one mapping between model files (.xmi) and text files (opened with text
    * editor). The absolute paths of the files should be put into returned HashMap. You can use abs(path) function to
    * translate a project relative path to an absolute path
    */
   @Override
   public HashMap<String, String> getModelPathsToTextPaths()
   {
      HashMap<String, String> m2tPathes = new HashMap<String, String>();
      // m2tPathes.put(abs("/instances/out/test.xmi"), abs("/instances/in/test.txt"));
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
   public void addParserAdapters()
   {
      addParser(new PatternParserAdapter());
   }

   private String getAttributeValue(final Node node, final String name)
   {
      for (Attribute a : node.getAttribute())
      {
         if (name.equals(a.getName()))
         {
            return a.getValue();
         }
      }
      return "<unknown>";
   }

   @Override
   public String getOutlineLabel(final Node node)
   {
      return getAttributeValue(node, "name");
   }

   @Override
   public Boolean showInOutline(final Node node)
   {
      return "ObjectVariable".equals(node.getName());
   }

}

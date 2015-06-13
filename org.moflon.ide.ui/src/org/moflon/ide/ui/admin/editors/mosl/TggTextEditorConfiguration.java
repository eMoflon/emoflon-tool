package org.moflon.ide.ui.admin.editors.mosl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;
//import org.moflon.mosl.editor.context.ContextStore;
import org.moflon.mosl.tgg.parser.TggParserAdapter;

//import MOSLCodeAdapter.context.ClassInfo;
//import MOSLCodeAdapter.context.ContextInformation;

/**
 * This class provides project specific editor configuration for text highlighting and templates. Its generated methods
 * are to be filled and invoked during runtime by MOFLON Eclipse plugin in order to achieve a custom text editor
 * functionality.
 */
public class TggTextEditorConfiguration extends AbstractMOSLTextEditorConfiguration
{
   /**
    * Here, the project specific keywords and their colors should be defined. This method is called once for the
    * initialization of a text editor.
    */
   public void setKeyWords()
   {
	  super.setKeyWords();
      addKeyWord("rule").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("refines").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("source").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("target").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("correspondence").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("constraints").as(COLORS.VIOLET, BOLD, 0);
      addKeyWord("operations").as(COLORS.VIOLET, BOLD, 0);

      addKeyWord("++").as(COLORS.GREEN, 0, 0);
      addKeyWord("--").as(COLORS.RED, 0, 0);
   }

   /**
    * Here the scope for syntax highlighting can be defined. {-4,4} means four lines before the text
    * change and four lines after the text change. Default is the complete dokument which is {1,-1}.
    */
   @Override
	public int[] getRefreshScope()
	{
	   return super.getRefreshScope();
	}
   

	private void semanticColor(String word) {
		if ("++".equals(word))
			highlight(COLORS.GREEN);
		else if ("--".equals(word)) {
			highlight(COLORS.RED);
		} else {
			highlight(COLORS.DARKBLUE);
		}
	}
   
   /**
    * This method is called each time when the color of a word should be determined by Eclipse. With getWord(int) the
    * context of the position can be accessed, at which Eclipse computes currently the highlighting color. The
    * highlight(COLORS) method from the super type should also be called in order to define a (context-dependent)
    * highlighting color.
    */
   public void highlightWord()
   {
     linkHighlighting();
     semanticHighlighting();
     
	  String previous = getWord(-1);
      String next = getWord(1);
      if("<-".equals(next))
    	  semanticColor(previous);
      else
	      for(int i=0;i<7;i++){
	    	  if("<-".equals(getWord(-i))){
	    		  semanticColor(getWord(-i-2));
	    		  return;
	    	  }
	      }     
   }

   /**
    * Here, start and end strings of special text ranges defined which are to be highlighted completely. For example, a
    * javadoc starts with "/*" and ends with "*"+"/" and is highlighted completely with blue color.
    */
   public void highlightSequence()
   {
	      commentHighlighting();
	      stringHighlighting();
   }

   /**
    * This method returns a collection of templates when user presses ctrl+space in a text editor. With the help of
    * getWord(int) method, the context from the cursor position can be accessed.
    */
   public Collection<MoflonEditorTemplate> getTemplates()
   {
      Vector<MoflonEditorTemplate> templates = new Vector<MoflonEditorTemplate>();
      sortCounter = 0;
      
      templates.add(new MoflonEditorTemplate("constraints", "Constraints block", "constraints {[\n]}", sortCounter++));
      templates.add(new MoflonEditorTemplate("operations", "Operations block", "operations {\n}", sortCounter++));
      
      templates.add(new MoflonEditorTemplate("correspondence", "Correspondence link", "${left} <- ${name} : ${type} -> ${right}", sortCounter++));
      templates.add(new MoflonEditorTemplate("link", "ObjectVariable link", "${modifier} - ${name} -> ${object}", sortCounter++));
      templates.add(new MoflonEditorTemplate("objectvariable", "ObjectVariable", "${variable} : ${type}", sortCounter++));

//      ContextInformation info = ContextStore.getInstance().getContextInformation(getProject());
//      
//      String previous = getWord(-1);
//      
//      if (info != null) {
//         if (":".equals(previous)) {
//           for (ClassInfo ci : info.getClasses()) {
//              templates.add(createClassInfoTemplate(ci, false));
//           }
//             addECoreDatatypes(templates);
//         }
//         
//         if ("->".equals(previous)) {
//            addBoxLinkTemplates(templates, info);
//         }
//      }
      
      return templates;
   }

   /**
    * User defines with this method an one-to-one mapping between model files (.xmi) and text files (opened with text
    * editor). The absolute paths of the files should be put into returned HashMap. You can use abs(path) function to
    * translate a project relative path to an absolute path
    */
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
   public void onSave(String textFilePath)
   {
      // MocaTree.File tree = codeAdapter.parseFile(new File(textFilePath), null);
      // Your textToModel transformation code here...
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

   /**
    * This method is called, after textToModel transformation All Problems which where collected during onSave operation
    * can be reported as markers in the TextEditor. Problems are accessible via the codeAdapter:
    * codeAdapter.getProblems() A maker can be created, using AddMarker(problem,pathToTextfile);
    */
   public void getProblems()
   {
      // for (Problem problem : codeAdapter.getProblems()) {
      // AddMarker(problem,"instances/in");
      // }
   }
   
   @Override
   public void addParserAdapters()
   {
      addParser(new TggParserAdapter());
   }
   
}

package org.moflon.ide.ui.admin.editors.misc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.moflon.ide.texteditor.config.MoflonTextEditorConfigExtern;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.colors.COLORS;

/**
 * This class provides project specific editor configuration for text
 * highlighting and templates. Its generated methods are to be filled and
 * invoked during runtime by MOFLON Eclipse plugin in order to achieve a custom
 * text editor functionality.
 */
public class VelocityTextEditorConfiguration extends MoflonTextEditorConfigExtern
{
	/**
	 * Here, the project specific keywords and their colors should be defined.
	 * This method is called once for the initialization of a text editor.
	 */
	public void setKeyWords()
	{
		addKeyWord("set").as(COLORS.BLUE);
		addKeyWord("if").as(COLORS.BLUE);
		addKeyWord("else").as(COLORS.BLUE);
		addKeyWord("elseif").as(COLORS.BLUE);
		addKeyWord("end").as(COLORS.BLUE);
		addKeyWord("parse").as(COLORS.BLUE);
		addKeyWord("foreach").as(COLORS.BLUE);
	}

	/**
	 * Here, the delimiters, which specifies a boundary between words in a char
	 * sequence without whitespace, should be defined. For example, in an
	 * expression like "int a=5;" the chars '=' and ';' are delimiters and shape
	 * tokens to be handled separately despite the missing whitespaces. This
	 * method is called once for the initialization of a text editor.
	 * 
	 */
	public char[] getDelimiters()
	{
		char[] delimiters = {};
		delimiters = new char[]
		{ '{', '}', '(', ')', '#', '"', '/', ':', ' ', '\n' };
		return delimiters;
	}

	/**
	 * Here the scope for syntax highlighting can be defined. {-4,4} means four
	 * lines before the text change and four lines after the text change.
	 * Default is the complete dokument which is {1,-1}.
	 */
	@Override
	public int[] getRefreshScope()
	{
		return super.getRefreshScope();
	}

	/**
	 * This method is called each time when the color of a word should be
	 * determined by Eclipse. With getWord(int) the context of the position can
	 * be accessed, at which Eclipse computes currently the highlighting color.
	 * The highlight(COLORS) method from the super type should also be called in
	 * order to define a (context-dependent) highlighting color.
	 */
	public void highlightWord()
	{
		// String previous = getWord(-1);
		// String word = getWord(0);
		// String next = getWord(1);

		// if (previous.equals("=") && next.equals(";"))
		// highlight(COLORS.BLUE);
	}

	/**
	 * Here, start and end strings of special text ranges defined which are to
	 * be highlighted completely. For example, a javadoc starts with "/*" and
	 * ends with "*"+"/" and is highlighted completely with blue color.
	 */
	public void highlightSequence()
	{
		highlightRange(COLORS.GREEN).startsWith("#*").endsWith("*#");
		highlightRange(COLORS.RED).startsWith("${").endsWith("}");
		highlightRange(COLORS.RED).startsWith("$!{").endsWith("}");
		highlightRange(COLORS.RED).startsWith("$").endsWith(" ");
		highlightRange(COLORS.RED).startsWith("$!").endsWith(" ");
	}

	/**
	 * This method returns a collection of templates when user presses
	 * ctrl+space in a text editor. With the help of getWord(int) method, the
	 * context from the cursor position can be accessed.
	 */
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
	 * User defines with this method an one-to-one mapping between model files
	 * (.xmi) and text files (opened with text editor). The absolute paths of
	 * the files should be put into returned HashMap. You can use abs(path)
	 * function to translate a project relative path to an absolute path
	 */
	public HashMap<String, String> getModelPathsToTextPaths()
	{
		HashMap<String, String> m2tPathes = new HashMap<String, String>();
		// m2tPathes.put(abs("/instances/out/test.xmi"),
		// abs("/instances/in/test.txt"));
		return m2tPathes;
	}

	/**
	 * This method is called when a text file has been changed of which absolute
	 * path is a contained value in HashMap returned by
	 * getModelPathesToTextPathes(). The argument is the absolute path of the
	 * changed text file. With the help of calling getModelPath(textFilePath))
	 * the path of the corresponding model file can also be accessed. Please
	 * don't use any project relative path in this method. (use
	 * abs(projectRelativePath))
	 */
	public void onSave(String textFilePath)
	{
		// MocaTree.File tree = codeAdapter.parseFile(new File(textFilePath),
		// null);
		// Your textToModel transformation code here...
	}

	/**
	 * This method is called when a model file has changed of which absolute
	 * path is a contained key in the HashMap returned by
	 * getModelPathesToTextPathes(). The argument is the absolute path of the
	 * changed model file. Please don't use any project relative path in this
	 * method. (use abs(projectRelativePath))
	 */
	public void syncText(String modelFilePath)
	{
		// helper.setInputModel(MocaTreePackage.eINSTANCE, modelFilePath);
		// your model2text transformation code here
	}

	/**
	 * This method is called, after textToModel transformation All Problems
	 * which where collected during onSave operation can be reported as markers
	 * in the TextEditor. Problems are accessible via the codeAdapter:
	 * codeAdapter.getProblems() A maker can be created, using
	 * AddMarker(problem,pathToTextfile);
	 */
	public void getProblems()
	{
		// for (Problem problem : codeAdapter.getProblems()) {
		// AddMarker(problem,"instances/in");
		// }
	}

}

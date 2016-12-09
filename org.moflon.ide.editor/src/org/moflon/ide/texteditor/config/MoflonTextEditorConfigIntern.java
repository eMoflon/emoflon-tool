package org.moflon.ide.texteditor.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.texteditor.ITextEditor;
import org.moflon.core.moca.processing.CodeAdapter;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.ide.texteditor.builders.TextEditorBuilderHelper;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.editors.MoflonTextEditor;
import org.moflon.ide.texteditor.editors.colors.COLORS;
import org.moflon.ide.texteditor.editors.colors.ColorManager;
import org.moflon.ide.texteditor.helpers.MarkerHelper;
import org.moflon.ide.texteditor.helpers.TextAttributeHelper;
import org.moflon.ide.texteditor.modules.autoedit.MoflonAutoEditStrategy;
import org.moflon.ide.texteditor.modules.content.MoflonContentAssistProcessor;
import org.moflon.ide.texteditor.modules.folding.IMoflonFoldingProvider;
import org.moflon.ide.texteditor.modules.outline.IMoflonOutlineProvider;
import org.moflon.ide.texteditor.modules.rules.MoflonKeywordRuleCreator;
import org.moflon.ide.texteditor.modules.rules.MoflonWordPatternRuleCreator;
import org.moflon.ide.texteditor.modules.rules.WhitespaceDetector;
import org.moflon.ide.texteditor.modules.rules.WordDetector;

import MocaTree.Node;

public abstract class MoflonTextEditorConfigIntern implements ITokenScanner, TextEditorBuilderHelper, IMoflonFoldingProvider, IMoflonOutlineProvider, IFormattingStrategy
{
   // text highlighting variables
   protected RuleBasedScanner ruleBasedScanner;

   private MoflonContentAssistProcessor contentAssisstProcessor;

   protected MoflonKeywordRuleCreator keywordRuleCreator;

   protected MoflonWordPatternRuleCreator wordPatternRuleCreator;

   protected Vector<IRule> defaultRules;

   protected Vector<IRule> contextDependentRules;

   protected IDocument document;

   protected WhitespaceDetector whitespaceDetector;

   protected char[] delimiters = new char[] {};

   public final static int BOLD = 1 << 0;

   public final static int ITALIC = 1 << 1;

   public final static int UNDERLINED = 1 << 30;

   public final static int STRIKETHROUGH = 1 << 29;

   // template variables
   public CodeAdapter codeAdapter;

   protected IProject project;

   protected IResource resource;

   protected ITextEditor editor;

   public MoflonTextEditorConfigIntern()
   {
      configureTextHighlighting();
      this.codeAdapter = ProcessingFactory.eINSTANCE.createCodeAdapter();
      this.setContentAssisstProcessor(new MoflonContentAssistProcessor(this));
      addParserAdapters();
   }

   // abstract config methods
   abstract public void setKeyWords();

   abstract public void highlightWord();

   abstract public void highlightSequence();

   abstract public char[] getDelimiters();

   public abstract Node getUnderlyingTree(String filepath);

   public abstract void addParserAdapters();

   public abstract int[] getRefreshScope();

   @Override
   public abstract void onSave(String textFilePath);

   abstract public Collection<MoflonEditorTemplate> getTemplates();

   /**
    * This method is called, after textToModel transformation All Problems which where collected during onSave operation
    * can be reported as markers in the TextEditor. Problems are accessible via the codeAdapter:
    * codeAdapter.getProblems() A maker can be created, using AddMarker(problem,pathToTextfile);
    */
   @Override
   public abstract void getProblems();

   /**
    * This method is called when a text file has been changed of which absolute path is a contained value in HashMap
    * returned by getModelPathesToTextPathes(). The argument is the absolute path of the changed text file. With the
    * help of calling getModelPath(textFilePath)) the path of the corresponding model file can also be accessed. Please
    * don't use any project relative path in this method. (use abs(projectRelativePath))
    */

   /**
    * Adds a Marker in the TextEditor for a given Problem. The Marker will be bound to the Resource, given in the
    * Problem.
    * 
    * @param problem
    *           The problem, the Marker will report.
    * @param pathToTextfiles
    *           The path, where the file is located that caused the problem (e.g. "instances/in").
    */
   protected final void AddMarker(final Problem problem, final String pathToTextfiles)
   {
      MarkerHelper.reportError(getResource(), problem, editor);
   }

   /**
    * Adds a Marker in the TextEditor for a given Exception. As no Resource is given, the Marker will be bound to the
    * current Project.
    * 
    * @param exception
    *           The exception, the Marker will report.
    */
   protected final void AddMarker(final Exception exception)
   {
      MarkerHelper.reportError(project, exception);
   }

   protected final MoflonKeywordRuleCreator addKeyWord(final String keyword)
   {
      keywordRuleCreator.setKeyword(keyword);
      return keywordRuleCreator;
   }

   /**
    * highlights a range
    * 
    * @param color
    *           defines the color of the text.
    * @return
    */
   protected final MoflonWordPatternRuleCreator highlightRange(final COLORS color)
   {
      wordPatternRuleCreator.setTextAttribute(TextAttributeHelper.createTextAttribute(color, null, null, 0, 0, 0));
      return wordPatternRuleCreator;
   }

   /**
    * highlights a range
    * 
    * @param color
    *           defines the color of the text.
    * @param style
    *           defines text style attributes. Values: BOLD, ITALIC. If style is 0, no style will be applied.
    * @return
    */
   protected final MoflonWordPatternRuleCreator highlightRange(final COLORS color, final int style)
   {
      wordPatternRuleCreator.setTextAttribute(TextAttributeHelper.createTextAttribute(color, null, null, 0, style, 0));
      return wordPatternRuleCreator;
   }

   /**
    * highlights a range
    * 
    * @param color
    *           defines the color of the text.
    * @param bgColor
    *           defines the color of the background. If bgColor is null, color wont be changed.
    * @param fontName
    *           defines the font. If fontName is null, font wont be changed.
    * @param fontHeight
    *           defines the size of the text. If fontHeight is 0, size wont be changed.
    * @param style
    *           defines text style attributes. Values: BOLD, ITALIC. If style is 0, no style will be applied.
    * @param lineStyle
    *           defines line style of text. Values: UNDERLINED, STRIKETHROUGH. If lineStyle is 0, no lines will be
    *           applied.
    * @return
    */
   protected final MoflonWordPatternRuleCreator highlightRange(final COLORS color, final COLORS bgColor, final String fontName, final int fontHeight, final int style, final int lineStyle)
   {
      wordPatternRuleCreator.setTextAttribute(TextAttributeHelper.createTextAttribute(color, bgColor, fontName, fontHeight, style, lineStyle));
      return wordPatternRuleCreator;
   }

   protected final String getWord(final int index)
   {

      try
      {
         if (index >= 0)
         {
            return getWordAhead(index);
         } else
         {
            return getWordBack(index);
         }
      }

      catch (Exception e)
      {
         return "";
      }

   }

   /**
    * Highlights a given Token
    * 
    * @param color
    *           defines the color of the text.
    * @param bgColor
    *           defines the color of the background. If bgColor is null, color wont be changed.
    * @param fontName
    *           defines the font. If fontName is null, font wont be changed.
    * @param fontHeight
    *           defines the size of the text. If fontHeight is 0, size wont be changed.
    * @param style
    *           defines text style attributes. Values: BOLD, ITALIC. If style is 0, no style will be applied.
    * @param lineStyle
    *           defines line style of text. Values: UNDERLINED, STRIKETHROUGH. If lineStyle is 0, no lines will be
    *           applied.
    */
   protected final void highlight(final COLORS color, final COLORS bgColor, final String fontName, final int fontHeight, final int style, final int lineStyle)
   {
      TextAttribute textAttribute = TextAttributeHelper.createTextAttribute(color, bgColor, fontName, fontHeight, style, lineStyle);
      IToken token = new Token(textAttribute);
      IRule rule = new WordRule(new WordDetector(delimiters), token);
      contextDependentRules.add(rule);
   }

   /**
    * Highlights a given Token
    * 
    * @param color
    *           defines the color of the text.
    * @param style
    *           defines text style attributes. Values: BOLD, ITALIC. If style is 0, no style will be applied.
    */
   protected final void highlight(final COLORS color, final int style)
   {
      highlight(color, null, null, 0, style, 0);
   }

   /**
    * Highlights a given Token
    * 
    * @param color
    *           defines the color of the text.
    */
   protected final void highlight(final COLORS color)
   {
      highlight(color, null, null, 0, 0, 0);
   }

   protected final String getWordAhead(final int index) throws Exception
   {
      int wordOffset;
      if (this.getContentAssisstProcessor().getCursorBasedPositioning())
         wordOffset = contentAssisstProcessor.getCursorOffset();
      else
         wordOffset = ruleBasedScanner.getTokenOffset() + ruleBasedScanner.getTokenLength();

      String result = "";
      for (int i = 0; i <= index; i++)
      {
         result = "";
         char c = document.getChar(wordOffset);

         while (whitespaceDetector.isWhitespace(c))
         {
            c = document.getChar(++wordOffset);
         }

         if (isDelimiter(c))
         {
            result += c;
            wordOffset++;
            continue;
         }

         while (!whitespaceDetector.isWhitespace(c) && !isDelimiter(c) && c != ICharacterScanner.EOF)
         {
            result = result + c;
            c = document.getChar(++wordOffset);
         }
         while (whitespaceDetector.isWhitespace(c))
         {
            try
            {
               c = document.getChar(++wordOffset);

            } catch (Exception e)
            {
               break;
            }
         }
      }
      return result;
   }

   protected final String getWordBack(final int index) throws Exception
   {
      int wordOffset;
      if (this.getContentAssisstProcessor().getCursorBasedPositioning())
         wordOffset = contentAssisstProcessor.getCursorOffset() - 1;
      else
         wordOffset = ruleBasedScanner.getTokenOffset() + ruleBasedScanner.getTokenLength() - 1;

      String result = "";
      for (int i = 0; i > index; i--)
      {
         result = "";
         char c = document.getChar(wordOffset);

         while (whitespaceDetector.isWhitespace(c))
         {
            c = document.getChar(--wordOffset);
         }

         if (isDelimiter(c))
         {
            result += c;
            wordOffset--;
            continue;
         }

         while (!whitespaceDetector.isWhitespace(c) && !isDelimiter(c))
         {
            result = c + result;
            try
            {
               c = document.getChar(--wordOffset);
            } catch (Exception e)
            {
               break;
            }

         }

      }
      return result;
   }

   public final IResource getResource()
   {
      return this.resource;
   }

   public final void setResource(final IResource resource)
   {
      this.resource = resource;
   }

   public final void setEditor(final MoflonTextEditor editor)
   {
      this.editor = editor;
   }

   public final IProject getProject()
   {
      return this.project;
   }

   public final void setProject(final IProject project)
   {
      this.project = project;
   }

   @Override
   public final void setRange(final IDocument document, final int offset, final int length)
   {
      this.document = document;
      ruleBasedScanner.setRange(document, offset, length);
   }

   @Override
   public final IToken nextToken()
   {
      highlightWord();
      defaultRules.addAll(defaultRules.size() - 2, contextDependentRules);
      ruleBasedScanner.setRules(defaultRules.toArray(new IRule[defaultRules.size()]));
      IToken token = ruleBasedScanner.nextToken();
      defaultRules.removeAll(contextDependentRules);
      contextDependentRules.removeAllElements();
      return token;
   }

   @Override
   public final int getTokenOffset()
   {
      return ruleBasedScanner.getTokenOffset();
   }

   @Override
   public final int getTokenLength()
   {
      return ruleBasedScanner.getTokenLength();
   }

   protected final void configureTextHighlighting()
   {
      ruleBasedScanner = new RuleBasedScanner();
      defaultRules = new Vector<IRule>();
      contextDependentRules = new Vector<IRule>();
      whitespaceDetector = new WhitespaceDetector();
      delimiters = getDelimiters();

      // create rules
      WhitespaceRule whiteSpaceRule = new WhitespaceRule(whitespaceDetector, Token.WHITESPACE);
      defaultRules.add(whiteSpaceRule);

      wordPatternRuleCreator = new MoflonWordPatternRuleCreator();
      highlightSequence();
      defaultRules.addAll(wordPatternRuleCreator.getRules());

      keywordRuleCreator = new MoflonKeywordRuleCreator(new WordDetector(delimiters));
      setKeyWords();
      defaultRules.addAll(keywordRuleCreator.getRules());

      IToken defaultToken = new Token(new TextAttribute(ColorManager.getInstance().getColor(new RGB(0, 0, 0))));
      IRule rule = new WordRule(new WordDetector(delimiters), defaultToken);
      defaultRules.add(rule);

      ruleBasedScanner.setRules(defaultRules.toArray(new IRule[defaultRules.size()]));
   }

   protected final boolean isDelimiter(final char c)
   {
      for (int i = 0; i < delimiters.length; i++)
      {
         if (c == delimiters[i])
            return true;
      }
      return false;
   }

   protected final String getModelPath(final String textPath)
   {
      HashMap<String, String> modelToTextPathes = this.getModelPathsToTextPaths();
      for (String modelPath : modelToTextPathes.keySet())
      {
         if (modelToTextPathes.get(modelPath).equals(textPath))
            return modelPath;
      }
      return null;
   }

   protected final String getTextPath(final String modelPath)
   {
      HashMap<String, String> modelToTextPathes = this.getModelPathsToTextPaths();
      if (modelToTextPathes.containsKey(modelPath))
      {
         return modelToTextPathes.get(modelPath);
      }
      return null;
   }

   public final String abs(String projectRelativePath)
   {
      if (projectRelativePath.startsWith("./"))
         projectRelativePath = projectRelativePath.substring(1);
      if (!projectRelativePath.startsWith("/"))
         projectRelativePath = "/" + projectRelativePath;
      String result = project.getLocation().toString() + projectRelativePath;
      return result;
   }

   public final MoflonContentAssistProcessor getContentAssisstProcessor()
   {
      return contentAssisstProcessor;
   }

   public final void setContentAssisstProcessor(final MoflonContentAssistProcessor contentAssisstProcessor)
   {
      this.contentAssisstProcessor = contentAssisstProcessor;
   }

   public IHyperlinkDetector[] getHyperlinkDetectors()
   {
      return null;
   }

   public IAutoEditStrategy getAutoEditStrategy()
   {
      return new MoflonAutoEditStrategy();
   }

   public void formatterStarts(String initialIndentation){
      
   }

   public String format(String content, boolean isLineStart, String indentation, int[] positions){
      return content;
   }

   public void formatterStops(){
      
   }
}

package org.moflon.ide.texteditor.config;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.parser.Parser;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.texteditor.editors.MoflonEditorTemplate;
import org.moflon.ide.texteditor.helpers.MarkerHelper;

import MocaTree.Node;

public abstract class MoflonTextEditorConfigExtern extends MoflonTextEditorConfigIntern
{

   public MoflonTextEditorConfigExtern()
   {
      super();
   }

   @Override
   public Node getUnderlyingTree(final String filepath)
   {
      return null;
   }

   @Override
   public int[] getRefreshScope()
   {
      int[] scope = { 1, -1 };
      return scope;
   }

   @Override
   public void getProblems()
   {
      MarkerHelper.removeMarkers(getResource());

      if (codeAdapter != null)
         for (Problem problem : codeAdapter.getProblems())
            AddMarker(problem, getResource().getProjectRelativePath().removeLastSegments(1).toString());
   }

   @Override
   public void addParserAdapters()
   {

   }

   public void addParser(final Parser parser)
   {
      this.codeAdapter.getParser().add(parser);
   }

   @Override
   public void onSave(final String textFilePath)
   {

      String resourceFilePath = getResource().getLocation().toString();

      if (resourceFilePath.equals(textFilePath))
      {
         // For some reason the problems are not always cleared on new parse actions.
         codeAdapter.getProblems().clear();

         File file = new File(textFilePath);
         MocaTree.File mocaFile = codeAdapter.parseFile(file, null);

         if (mocaFile != null && getModelPath(textFilePath) != null)
         {
            eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), mocaFile, getModelPath(textFilePath));
         }
      }
   }

   @Override
   public boolean foldNode(final Node node)
   {
      return false;
   }

   @Override
   public String getOutlineImagePath(final Node node)
   {
      return "";
   }

   @Override
   public String getOutlineLabel(final Node node)
   {
      String name = node.getName().replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "").trim();
      return name;
   }

   @Override
   public Boolean showInOutline(final Node node)
   {
      return true;
   }

   @Override
   public HashMap<String, String> getModelPathsToTextPaths()
   {
      HashMap<String, String> m2tPathes = new HashMap<String, String>();
      return m2tPathes;
   }

   @Override
   public void syncText(final String modelFilePath)
   {

   }

   @Override
   public Collection<MoflonEditorTemplate> getTemplates()
   {
      return new Vector<MoflonEditorTemplate>();
   }

   @Override
   public void highlightWord()
   {

   }

   @Override
   public void highlightSequence()
   {

   }

   @Override
   public char[] getDelimiters()
   {
      char[] delimiters = {};
      return delimiters;
   }
}

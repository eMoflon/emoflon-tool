package org.moflon.mosl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.moflon.moca.CustomMismatchedTreeNodeException;

public abstract class MOSLTreeGrammar extends TreeParser
{

   public MOSLTreeGrammar(TreeNodeStream input)
   {
      super(input);
   }
   
   public MOSLTreeGrammar(TreeNodeStream input, RecognizerSharedState state)
   {
      super(input, state);
   }

   private List<RecognitionException> errors = new ArrayList<RecognitionException>();

   public List<RecognitionException> getErrors()
   {
      return errors;
   }

   @Override
   public void reportError(RecognitionException e)
   {
      errors.add(e);
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException
   {
      TreeNodeStream tns = (TreeNodeStream) input;
      throw new CustomMismatchedTreeNodeException(ttype, tns);
   }

   public void setTemplateLib(StringTemplateGroup templates)
   {
      // TODO Auto-generated method stub

   }

   public abstract TreeRuleReturnScope main() throws RecognitionException;
}

package org.moflon.mosl.tgg.parser;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.mosl.AbstractMOSLParserAdapter;
import org.moflon.mosl.MOSLLexer;
import org.moflon.mosl.MOSLParser;

public class TggParserAdapter extends AbstractMOSLParserAdapter
{

   @Override
   public String getExtension()
   {
      return ".tgg";
   }
   
   @Override
   public MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream)
   {
      return new TggLexer(antlrReaderStream);
   }
   
   @Override
   public MOSLParser createParser(CommonTokenStream tokens)
   {
      return new TggParser(tokens);
   }
   
   @Override
   public CommonTree getTree(MOSLParser parser) throws RecognitionException
   {
      return (CommonTree) ((TggParser)parser).main().tree;
   }
}
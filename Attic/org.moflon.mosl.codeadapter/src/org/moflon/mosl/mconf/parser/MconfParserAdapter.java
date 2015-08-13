package org.moflon.mosl.mconf.parser;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.mosl.AbstractMOSLParserAdapter;
import org.moflon.mosl.MOSLLexer;
import org.moflon.mosl.MOSLParser;

public class MconfParserAdapter extends AbstractMOSLParserAdapter
{
   @Override
   public String getExtension()
   {
      return ".mconf";
   }
   
   @Override
   public MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream)
   {
      return new MconfLexer(antlrReaderStream);
   }
   
   @Override
   public MOSLParser createParser(CommonTokenStream tokens)
   {
      return new MconfParser(tokens);
   }
   
   @Override
   public CommonTree getTree(MOSLParser parser) throws RecognitionException
   {
      return (CommonTree) ((MconfParser)parser).main().tree;
   }
}
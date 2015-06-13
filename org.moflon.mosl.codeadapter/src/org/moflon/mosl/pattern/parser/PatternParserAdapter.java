package org.moflon.mosl.pattern.parser;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.mosl.AbstractMOSLParserAdapter;
import org.moflon.mosl.MOSLLexer;
import org.moflon.mosl.MOSLParser;

public class PatternParserAdapter extends AbstractMOSLParserAdapter {
   @Override
   public String getExtension()
   {
      return ".pattern";
   }
   
   @Override
   public MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream)
   {
      return new PatternLexer(antlrReaderStream);
   }
   
   @Override
   public MOSLParser createParser(CommonTokenStream tokens)
   {
      return new PatternParser(tokens);
   }
   
   @Override
   public CommonTree getTree(MOSLParser parser) throws RecognitionException
   {
      return (CommonTree) ((PatternParser)parser).main().tree;
   }
}
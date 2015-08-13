package org.moflon.mosl.sch.parser;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.mosl.AbstractMOSLParserAdapter;
import org.moflon.mosl.MOSLLexer;
import org.moflon.mosl.MOSLParser;

public class SchParserAdapter extends AbstractMOSLParserAdapter
{
   @Override
   public String getExtension()
   {
      return ".sch";
   }
   
   @Override
   public MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream)
   {
      return new SchLexer(antlrReaderStream);
   }
   
   @Override
   public MOSLParser createParser(CommonTokenStream tokens)
   {
      return new SchParser(tokens);
   }
   
   @Override
   public CommonTree getTree(MOSLParser parser) throws RecognitionException
   {
      return (CommonTree) ((SchParser)parser).main().tree;
   }
}
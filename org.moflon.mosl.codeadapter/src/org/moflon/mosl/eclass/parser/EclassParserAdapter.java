package org.moflon.mosl.eclass.parser;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.mosl.AbstractMOSLParserAdapter;
import org.moflon.mosl.MOSLLexer;
import org.moflon.mosl.MOSLParser;

public class EclassParserAdapter extends AbstractMOSLParserAdapter {
   @Override
   public String getExtension()
   {
      return ".eclass";
   }
   
   @Override
   public MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream)
   {
      return new EclassLexer(antlrReaderStream);
   }
   
   @Override
   public MOSLParser createParser(CommonTokenStream tokens)
   {
      return new EclassParser(tokens);
   }
   
   @Override
   public CommonTree getTree(MOSLParser parser) throws RecognitionException
   {
      return (CommonTree) ((EclassParser)parser).main().tree;
   }
}
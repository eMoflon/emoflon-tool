package org.moflon.moca.inject.parser;

import java.io.IOException;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.parser.impl.ParserImpl;
import org.moflon.core.utilities.LogUtils;
import org.moflon.moca.MocaUtil;

import MocaTree.Node;

public class InjectImplParserAdapter extends ParserImpl
{

   private static final Logger logger = Logger.getLogger(InjectImplParserAdapter.class);

   private String filename;

   @Override
   public boolean canParseFile(final String fileName)
   {
      this.filename = fileName;
      return fileName.endsWith("Impl.inject");
   }

   @Override
   public Node parse(final Reader reader)
   {
      try
      {
         InjectLexer lexer = new InjectLexer(new ANTLRReaderStream(reader));
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         InjectParser parser = new InjectParser(tokens);
         CommonTree result = (CommonTree) parser.implMain().tree;

         // Log Lexer Errors
         for (Problem problem : lexer.problems)
         {
            problem.setCodeAdapter(getCodeAdapter());
            problem.setSource(filename);
            getCodeAdapter().getProblems().add(problem);
         }

         // Log Parser Errors
         for (Problem problem : parser.problems)
         {
            problem.setCodeAdapter(getCodeAdapter());
            problem.setSource(filename);
            getCodeAdapter().getProblems().add(problem);
         }

         return MocaUtil.commonTreeToMocaTree(result);
      } catch (IOException | RecognitionException e)
      {
         LogUtils.error(logger, e);
      }

      return null;
   }
}
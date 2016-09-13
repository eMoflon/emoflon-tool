package org.moflon.moca.inject.parser;

import java.io.IOException;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.parser.impl.ParserImpl;
import org.moflon.moca.MocaUtil;

import MocaTree.Node;

public class InjectParserAdapter extends ParserImpl
{

   private String filename;

   @Override
   public boolean canParseFile(String fileName)
   {
      this.filename = fileName;
      return fileName.matches(".*(?<!Impl).inject");
   }

   @Override
   public Node parse(Reader reader)
   {
      try
      {
         InjectLexer lexer = new InjectLexer(new ANTLRReaderStream(reader));
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         InjectParser parser = new InjectParser(tokens);
         CommonTree result = (CommonTree) parser.nonImplMain().tree;

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
      } catch (IOException e)
      {
         e.printStackTrace();
      } catch (RecognitionException e)
      {
         e.printStackTrace();
      }

      return null;
   }
}
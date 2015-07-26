package org.moflon.mosl;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.moflon.moca.MocaUtil;

import Moca.parser.impl.ParserImpl;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

public abstract class AbstractMOSLParserAdapter extends ParserImpl
{
   private String currentFilename;
   private final Logger log = Logger.getLogger(this.getClass());
   @Override
   public final boolean canParseFile(String fileName) {
      if (fileName.endsWith(getExtension())) {
         log.debug("parse " + fileName);
         currentFilename = fileName;
         return true;
      }
      return false;
   }
   
   public String getCurrentFilename()
   {
      return currentFilename;
   }
   
   @Override
   public final Node parse(File file, Reader reader)
   {
      try
      {
         MOSLLexer lexer = createLexer(new ANTLRReaderStream(reader));
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         MOSLParser parser = createParser(tokens);
         CommonTree result = getTree(parser);
         if (result == null || parser.getErrors().size() > 0)
         {
            for (AntlrParserError e : parser.getErrors())
            {
               MOSLUtils.createProblem(getCodeAdapter(), file.getAbsolutePath(), e);
            }
            return MocaTreeFactory.eINSTANCE.createNode();
         }

         return MocaUtil.commonTreeToMocaTree(result);
      } catch (IOException e)
      {
         e.printStackTrace();
      } catch (RecognitionException e)
      {
         MOSLUtils.createProblem(getCodeAdapter(), file.getAbsolutePath(), new AntlrParserError(e));
         System.err.format("ParserError: [%s]: %s", getCurrentFilename(), e.toString());
      }
      return null;
   }


   public abstract String getExtension();
   public abstract MOSLLexer createLexer(ANTLRReaderStream antlrReaderStream);
   public abstract MOSLParser createParser(CommonTokenStream tokens);
   public abstract CommonTree getTree(MOSLParser parser) throws RecognitionException;


}

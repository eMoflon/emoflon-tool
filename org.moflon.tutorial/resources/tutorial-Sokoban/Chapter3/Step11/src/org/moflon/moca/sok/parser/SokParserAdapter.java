package org.moflon.moca.sok.parser;
import java.io.IOException;
import java.io.Reader;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.moflon.moca.MocaUtil;
import Moca.parser.impl.ParserImpl;
import MocaTree.Node;
import Moca.Problem;

public class SokParserAdapter extends ParserImpl 
{
  String filename;

  @Override
  public boolean canParseFile(String fileName) 
  {
    this.filename = fileName;
    return fileName.endsWith(".sok");
  }

  @Override
  public Node parse(Reader reader) 
  {
    try 
    {
      SokLexer lexer = new SokLexer(new ANTLRReaderStream(reader));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      SokParser parser = new SokParser(tokens);
      CommonTree result = (CommonTree) parser.main().tree; 
      
	  // Log Lexer Errors
	  for (Problem problem : lexer.problems) {
	    problem.setCodeAdapter(getCodeAdapter());
		problem.setSource(filename);	  
		getCodeAdapter().getProblems().add(problem);
	  }

      // Log Parser Errors
	  for (Problem problem : parser.problems) {
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
package org.moflon.moca.AttributeConstraint.parser;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.moca.MocaUtil;

import Moca.Problem;
import Moca.parser.impl.ParserImpl;
import MocaTree.Node;
import SDMLanguage.patterns.StoryPattern;
import ValidationResult.ErrorMessage;
import ValidationResult.Severity;
import ValidationResult.ValidationReport;
import ValidationResult.ValidationResultFactory;

public class AttributeConstraintParserAdapter extends ParserImpl 
{
  
   private String inputString;
   private StoryPattern sourceStoryPattern;
   private ValidationReport parseReport;
   @Override
   public boolean canParseFile(String fileName) 
   {
      
      return false;
   }

   public ValidationReport parse(String input, StoryPattern storyPattern){
      
      inputString=input;
      sourceStoryPattern=storyPattern;
      parseReport=ValidationResultFactory.eINSTANCE.createValidationReport();
      parseReport.setResult(parse(new StringReader(input)));
      return parseReport;
   }

   @Override
   public Node parse(Reader reader) 
   {
      try 
      {
         AttributeConstraintLexer lexer = new AttributeConstraintLexer(new ANTLRReaderStream(reader));
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         AttributeConstraintParser parser = new AttributeConstraintParser(tokens);
         CommonTree result = (CommonTree) parser.main().tree; 

         // Log Lexer Errors
         
         for (Problem problem : lexer.problems) {
           parseReport.getErrorMessages().add(createErrorMessage(problem));               
         }

         // Log Parser Errors
         for (Problem problem : parser.problems) {
            parseReport.getErrorMessages().add(createErrorMessage(problem));
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
   
   private ErrorMessage createErrorMessage(Problem problem){
      StringBuilder msg = new StringBuilder();
      msg.append("Problems parsing attribute constraint expression:\n ");
      msg.append("\t"+inputString.replace("\n", "\n\t"));
      msg.append("\n at line ");
      msg.append(problem.getLine() + ":" + problem.getCharacterPositionStart());
      msg.append(" " + problem.getMessage());
      
      ErrorMessage errMsg= ValidationResultFactory.eINSTANCE.createErrorMessage();
      errMsg.setId(msg.toString());
      errMsg.setSeverity(Severity.ERROR);
      errMsg.getLocation().add(sourceStoryPattern);
      return errMsg;
    }
}
// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g 2014-02-27 12:02:56

package org.moflon.moca.sok.parser; 
import java.util.Collection;
import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SokParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE"
    };
    public static final int EOF=-1;
    public static final int RULE=4;

    // delegates
    // delegators


        public SokParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SokParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SokParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g"; }


      public Collection<Problem> problems = new ArrayList<Problem>();

          public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
              Problem problem = MocaFactory.eINSTANCE.createProblem();
              int line  = e.line;
              int charPos = e.charPositionInLine;
              int tokenLenght = 1;
              Token token = e.token;
              if(token != null) 
                tokenLenght = token.getText().length();
                      
              
              problem.setType(ProblemType.ERROR);
              problem.setLine(line); 
              problem.setCharacterPositionStart(charPos); 
              problem.setCharacterPositionEnd(charPos+tokenLenght);
              problem.setMessage("Parser: " + getErrorMessage(e, tokenNames));
              
              problems.add(problem);
              super.displayRecognitionError(tokenNames, e);
        }


    public static class main_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "main"
    // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:48:1: main : ;
    public final SokParser.main_return main() throws RecognitionException {
        SokParser.main_return retval = new SokParser.main_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        try {
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:48:5: ()
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:48:6: 
            {
            root_0 = (Object)adaptor.nil();

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "main"

    // Delegated rules


 

}
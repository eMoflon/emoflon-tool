// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g 2014-02-27 12:02:56

package org.moflon.moca.sok.parser;
import java.util.Collection;
import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SokLexer extends Lexer {
    public static final int EOF=-1;
    public static final int RULE=4;

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
              problem.setMessage("Lexer: " + getErrorMessage(e, tokenNames)); 
              
              problems.add(problem);
              super.displayRecognitionError(tokenNames, e);
        }


    // delegates
    // delegators

    public SokLexer() {;} 
    public SokLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SokLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g"; }

    // $ANTLR start "RULE"
    public final void mRULE() throws RecognitionException {
        try {
            int _type = RULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:5: ()
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:6: 
            {
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:8: ( RULE )
        // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:10: RULE
        {
        mRULE(); 

        }


    }


 

}
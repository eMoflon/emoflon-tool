// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g 2014-02-28 11:10:51

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
    public static final int WS=4;
    public static final int NUM=5;
    public static final int NEWLINE=6;
    public static final int EMPTY=7;
    public static final int WALL=8;
    public static final int GOAL=9;
    public static final int ADMIN=10;
    public static final int SERVER=11;

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
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g"; }

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:3: ( ( '\\t' | '\\r' | '/*' ( . )* '*/' ) )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:5: ( '\\t' | '\\r' | '/*' ( . )* '*/' )
            {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:5: ( '\\t' | '\\r' | '/*' ( . )* '*/' )
            int alt2=3;
            switch ( input.LA(1) ) {
            case '\t':
                {
                alt2=1;
                }
                break;
            case '\r':
                {
                alt2=2;
                }
                break;
            case '/':
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:7: '\\t'
                    {
                    match('\t'); 

                    }
                    break;
                case 2 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:14: '\\r'
                    {
                    match('\r'); 

                    }
                    break;
                case 3 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:21: '/*' ( . )* '*/'
                    {
                    match("/*"); 

                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:26: ( . )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0=='*') ) {
                            int LA1_1 = input.LA(2);

                            if ( (LA1_1=='/') ) {
                                alt1=2;
                            }
                            else if ( ((LA1_1>='\u0000' && LA1_1<='.')||(LA1_1>='0' && LA1_1<='\uFFFF')) ) {
                                alt1=1;
                            }


                        }
                        else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:37:26: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    match("*/"); 


                    }
                    break;

            }

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:39:4: ( ( '0' .. '9' )+ )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:39:6: ( '0' .. '9' )+
            {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:39:6: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:39:6: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:41:8: ( '\\r \\n' | '\\n' | '\\r' )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\r') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==' ') ) {
                    alt4=1;
                }
                else {
                    alt4=3;}
            }
            else if ( (LA4_0=='\n') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:41:10: '\\r \\n'
                    {
                    match("\r \n"); 


                    }
                    break;
                case 2 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:41:20: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 3 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:41:27: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "EMPTY"
    public final void mEMPTY() throws RecognitionException {
        try {
            int _type = EMPTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:43:7: ( ' ' )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:43:9: ' '
            {
            match(' '); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EMPTY"

    // $ANTLR start "WALL"
    public final void mWALL() throws RecognitionException {
        try {
            int _type = WALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:46:6: ( '#' )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:46:8: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WALL"

    // $ANTLR start "GOAL"
    public final void mGOAL() throws RecognitionException {
        try {
            int _type = GOAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:48:6: ( '.' )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:48:8: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GOAL"

    // $ANTLR start "ADMIN"
    public final void mADMIN() throws RecognitionException {
        try {
            int _type = ADMIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:50:7: ( '@' )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:50:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ADMIN"

    // $ANTLR start "SERVER"
    public final void mSERVER() throws RecognitionException {
        try {
            int _type = SERVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:52:8: ( '$' )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:52:10: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SERVER"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:8: ( WS | NUM | NEWLINE | EMPTY | WALL | GOAL | ADMIN | SERVER )
        int alt5=8;
        alt5 = dfa5.predict(input);
        switch (alt5) {
            case 1 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:10: WS
                {
                mWS(); 

                }
                break;
            case 2 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:13: NUM
                {
                mNUM(); 

                }
                break;
            case 3 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:17: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 4 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:25: EMPTY
                {
                mEMPTY(); 

                }
                break;
            case 5 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:31: WALL
                {
                mWALL(); 

                }
                break;
            case 6 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:36: GOAL
                {
                mGOAL(); 

                }
                break;
            case 7 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:41: ADMIN
                {
                mADMIN(); 

                }
                break;
            case 8 :
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokLexer.g:1:47: SERVER
                {
                mSERVER(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\2\uffff\1\1\7\uffff";
    static final String DFA5_eofS =
        "\12\uffff";
    static final String DFA5_minS =
        "\1\11\1\uffff\1\40\7\uffff";
    static final String DFA5_maxS =
        "\1\100\1\uffff\1\40\7\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10";
    static final String DFA5_specialS =
        "\12\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\1\4\2\uffff\1\2\22\uffff\1\5\2\uffff\1\6\1\11\11\uffff"+
            "\1\7\1\1\12\3\6\uffff\1\10",
            "",
            "\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WS | NUM | NEWLINE | EMPTY | WALL | GOAL | ADMIN | SERVER );";
        }
    }
 

}
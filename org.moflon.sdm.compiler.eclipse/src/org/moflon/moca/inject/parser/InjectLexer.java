// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g 2013-06-29 00:25:36

package org.moflon.moca.inject.parser;
import java.util.ArrayList;
import java.util.Collection;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.moflon.moca.MocaUtil;

import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;

public class InjectLexer extends Lexer {
    public static final int EOF=-1;
    public static final int IMPORT_KEYWORD=4;
    public static final int CLASS_KEYWORD=5;
    public static final int PARTIAL_KEYWORD=6;
    public static final int CLASS_BEGIN=7;
    public static final int MODEL_KEYWORD=8;
    public static final int MEMBERS_KEYWORD=9;
    public static final int BLOCK_BEGIN=10;
    public static final int BLOCK_END=11;
    public static final int CODE_BLOCK=12;
    public static final int CLASS_END=13;
    public static final int DOT=14;
    public static final int SEMICOLON=15;
    public static final int WS=16;
    public static final int PARAMETER_BEGIN=17;
    public static final int PARAMETER_END=18;
    public static final int COMMA=19;
    public static final int STRING=20;

      public Collection<Problem> problems = new ArrayList<Problem>();

          @Override
		public void displayRecognitionError(final String[] tokenNames,
                                            final RecognitionException e) {
              Problem problem = MocaFactory.eINSTANCE.createProblem();
              int line  = e.line;
              int charPos = e.charPositionInLine;
              int tokenLength = 1;
              Token token = e.token;
              if(token != null) 
                tokenLength = token.getText().length();
                      
              
              problem.setType(ProblemType.ERROR);
              problem.setLine(line); 
              problem.setCharacterPositionStart(charPos); 
              problem.setCharacterPositionEnd(charPos+tokenLength);
              problem.setMessage("Lexer: " + getErrorMessage(e, tokenNames)); 
              
              problems.add(problem);
              super.displayRecognitionError(tokenNames, e);
        }


    // delegates
    // delegators

    public InjectLexer() {;} 
    public InjectLexer(final CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InjectLexer(final CharStream input, final RecognizerSharedState state) {
        super(input,state);

    }
    @Override
	public String getGrammarFileName() { return "C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g"; }

    // $ANTLR start "IMPORT_KEYWORD"
    public final void mIMPORT_KEYWORD() throws RecognitionException {
        try {
            int _type = IMPORT_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:41:15: ( 'import' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:41:17: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMPORT_KEYWORD"

    // $ANTLR start "CLASS_KEYWORD"
    public final void mCLASS_KEYWORD() throws RecognitionException {
        try {
            int _type = CLASS_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:43:14: ( 'class' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:43:16: 'class'
            {
            match("class"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLASS_KEYWORD"

    // $ANTLR start "PARTIAL_KEYWORD"
    public final void mPARTIAL_KEYWORD() throws RecognitionException {
        try {
            int _type = PARTIAL_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:45:16: ( 'partial' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:45:18: 'partial'
            {
            match("partial"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARTIAL_KEYWORD"

    // $ANTLR start "CLASS_BEGIN"
    public final void mCLASS_BEGIN() throws RecognitionException {
        try {
            int _type = CLASS_BEGIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:47:12: ( '{' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:47:14: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLASS_BEGIN"

    // $ANTLR start "MODEL_KEYWORD"
    public final void mMODEL_KEYWORD() throws RecognitionException {
        try {
            int _type = MODEL_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:49:14: ( '@model' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:49:16: '@model'
            {
            match("@model"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODEL_KEYWORD"

    // $ANTLR start "MEMBERS_KEYWORD"
    public final void mMEMBERS_KEYWORD() throws RecognitionException {
        try {
            int _type = MEMBERS_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:51:16: ( '@members' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:51:18: '@members'
            {
            match("@members"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MEMBERS_KEYWORD"

    // $ANTLR start "BLOCK_BEGIN"
    public final void mBLOCK_BEGIN() throws RecognitionException {
        try {
            int _type = BLOCK_BEGIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:53:12: ( '<--' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:53:14: '<--'
            {
            match("<--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BLOCK_BEGIN"

    // $ANTLR start "BLOCK_END"
    public final void mBLOCK_END() throws RecognitionException {
        try {
            int _type = BLOCK_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:55:10: ( '-->' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:55:12: '-->'
            {
            match("-->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BLOCK_END"

    // $ANTLR start "CODE_BLOCK"
    public final void mCODE_BLOCK() throws RecognitionException {
        try {
            int _type = CODE_BLOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:57:11: ( BLOCK_BEGIN ( . )* BLOCK_END )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:57:13: BLOCK_BEGIN ( . )* BLOCK_END
            {
            mBLOCK_BEGIN(); 
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:57:25: ( . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='-') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='-') ) {
                        int LA1_3 = input.LA(3);

                        if ( (LA1_3=='>') ) {
                            alt1=2;
                        }
                        else if ( ((LA1_3>='\u0000' && LA1_3<='=')||(LA1_3>='?' && LA1_3<='\uFFFF')) ) {
                            alt1=1;
                        }


                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<=',')||(LA1_1>='.' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=',')||(LA1_0>='.' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:57:25: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            mBLOCK_END(); 
            MocaUtil.trim(this, 3, 3);

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CODE_BLOCK"

    // $ANTLR start "CLASS_END"
    public final void mCLASS_END() throws RecognitionException {
        try {
            int _type = CLASS_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:59:10: ( '}' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:59:12: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLASS_END"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:61:4: ( '.' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:61:6: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:63:10: ( ';' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:63:12: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:65:4: ( ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )+ )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:65:6: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )+
            {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:65:6: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\t' && LA2_0<='\n')||(LA2_0>='\f' && LA2_0<='\r')||LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "PARAMETER_BEGIN"
    public final void mPARAMETER_BEGIN() throws RecognitionException {
        try {
            int _type = PARAMETER_BEGIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:67:16: ( '(' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:67:18: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARAMETER_BEGIN"

    // $ANTLR start "PARAMETER_END"
    public final void mPARAMETER_END() throws RecognitionException {
        try {
            int _type = PARAMETER_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:69:14: ( ')' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:69:16: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARAMETER_END"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:71:6: ( ',' )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:71:8: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:7: ( ( '_' | ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | DOT | '*' )+ )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:9: ( '_' | ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | DOT | '*' )+
            {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:9: ( '_' | ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | DOT | '*' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=7;
                switch ( input.LA(1) ) {
                case '_':
                    {
                    alt3=1;
                    }
                    break;
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt3=2;
                    }
                    break;
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    {
                    alt3=3;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt3=4;
                    }
                    break;
                case '.':
                    {
                    alt3=5;
                    }
                    break;
                case '*':
                    {
                    alt3=6;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:10: '_'
            	    {
            	    match('_'); 

            	    }
            	    break;
            	case 2 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:16: ( 'a' .. 'z' )
            	    {
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:16: ( 'a' .. 'z' )
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:17: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

            	    }


            	    }
            	    break;
            	case 3 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:29: ( 'A' .. 'Z' )
            	    {
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:29: ( 'A' .. 'Z' )
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:30: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

            	    }


            	    }
            	    break;
            	case 4 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:42: ( '0' .. '9' )
            	    {
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:42: ( '0' .. '9' )
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:43: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }


            	    }
            	    break;
            	case 5 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:55: DOT
            	    {
            	    mDOT(); 

            	    }
            	    break;
            	case 6 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:74:61: '*'
            	    {
            	    match('*'); 

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
    // $ANTLR end "STRING"

    @Override
	public void mTokens() throws RecognitionException {
        // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:8: ( IMPORT_KEYWORD | CLASS_KEYWORD | PARTIAL_KEYWORD | CLASS_BEGIN | MODEL_KEYWORD | MEMBERS_KEYWORD | BLOCK_BEGIN | BLOCK_END | CODE_BLOCK | CLASS_END | DOT | SEMICOLON | WS | PARAMETER_BEGIN | PARAMETER_END | COMMA | STRING )
        int alt4=17;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:10: IMPORT_KEYWORD
                {
                mIMPORT_KEYWORD(); 

                }
                break;
            case 2 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:25: CLASS_KEYWORD
                {
                mCLASS_KEYWORD(); 

                }
                break;
            case 3 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:39: PARTIAL_KEYWORD
                {
                mPARTIAL_KEYWORD(); 

                }
                break;
            case 4 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:55: CLASS_BEGIN
                {
                mCLASS_BEGIN(); 

                }
                break;
            case 5 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:67: MODEL_KEYWORD
                {
                mMODEL_KEYWORD(); 

                }
                break;
            case 6 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:81: MEMBERS_KEYWORD
                {
                mMEMBERS_KEYWORD(); 

                }
                break;
            case 7 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:97: BLOCK_BEGIN
                {
                mBLOCK_BEGIN(); 

                }
                break;
            case 8 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:109: BLOCK_END
                {
                mBLOCK_END(); 

                }
                break;
            case 9 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:119: CODE_BLOCK
                {
                mCODE_BLOCK(); 

                }
                break;
            case 10 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:130: CLASS_END
                {
                mCLASS_END(); 

                }
                break;
            case 11 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:140: DOT
                {
                mDOT(); 

                }
                break;
            case 12 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:144: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 13 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:154: WS
                {
                mWS(); 

                }
                break;
            case 14 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:157: PARAMETER_BEGIN
                {
                mPARAMETER_BEGIN(); 

                }
                break;
            case 15 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:173: PARAMETER_END
                {
                mPARAMETER_END(); 

                }
                break;
            case 16 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:187: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 17 :
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectLexer.g:1:193: STRING
                {
                mSTRING(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\3\17\5\uffff\1\25\6\uffff\3\17\3\uffff\3\17\2\uffff\1"+
        "\37\3\17\2\uffff\1\17\1\45\1\17\1\47\1\uffff\1\17\1\uffff\1\51\1"+
        "\uffff";
    static final String DFA4_eofS =
        "\52\uffff";
    static final String DFA4_minS =
        "\1\11\1\155\1\154\1\141\1\uffff\1\155\1\55\2\uffff\1\52\6\uffff"+
        "\1\160\1\141\1\162\1\145\1\55\1\uffff\1\157\1\163\1\164\2\uffff"+
        "\1\0\1\162\1\163\1\151\2\uffff\1\164\1\52\1\141\1\52\1\uffff\1\154"+
        "\1\uffff\1\52\1\uffff";
    static final String DFA4_maxS =
        "\1\175\1\155\1\154\1\141\1\uffff\1\155\1\55\2\uffff\1\172\6\uffff"+
        "\1\160\1\141\1\162\1\157\1\55\1\uffff\1\157\1\163\1\164\2\uffff"+
        "\1\uffff\1\162\1\163\1\151\2\uffff\1\164\1\172\1\141\1\172\1\uffff"+
        "\1\154\1\uffff\1\172\1\uffff";
    static final String DFA4_acceptS =
        "\4\uffff\1\4\2\uffff\1\10\1\12\1\uffff\1\14\1\15\1\16\1\17\1\20"+
        "\1\21\5\uffff\1\13\3\uffff\1\5\1\6\4\uffff\1\7\1\11\4\uffff\1\2"+
        "\1\uffff\1\1\1\uffff\1\3";
    static final String DFA4_specialS =
        "\33\uffff\1\0\16\uffff}>";
    static final String[] DFA4_transitionS = {
            "\2\13\1\uffff\2\13\22\uffff\1\13\7\uffff\1\14\1\15\1\17\1\uffff"+
            "\1\16\1\7\1\11\1\uffff\12\17\1\uffff\1\12\1\6\3\uffff\1\5\32"+
            "\17\4\uffff\1\17\1\uffff\2\17\1\2\5\17\1\1\6\17\1\3\12\17\1"+
            "\4\1\uffff\1\10",
            "\1\20",
            "\1\21",
            "\1\22",
            "",
            "\1\23",
            "\1\24",
            "",
            "",
            "\1\17\3\uffff\1\17\1\uffff\12\17\7\uffff\32\17\4\uffff\1\17"+
            "\1\uffff\32\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\32\11\uffff\1\31",
            "\1\33",
            "",
            "\1\34",
            "\1\35",
            "\1\36",
            "",
            "",
            "\0\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "",
            "",
            "\1\44",
            "\1\17\3\uffff\1\17\1\uffff\12\17\7\uffff\32\17\4\uffff\1\17"+
            "\1\uffff\32\17",
            "\1\46",
            "\1\17\3\uffff\1\17\1\uffff\12\17\7\uffff\32\17\4\uffff\1\17"+
            "\1\uffff\32\17",
            "",
            "\1\50",
            "",
            "\1\17\3\uffff\1\17\1\uffff\12\17\7\uffff\32\17\4\uffff\1\17"+
            "\1\uffff\32\17",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(final BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        @Override
		public String getDescription() {
            return "1:1: Tokens : ( IMPORT_KEYWORD | CLASS_KEYWORD | PARTIAL_KEYWORD | CLASS_BEGIN | MODEL_KEYWORD | MEMBERS_KEYWORD | BLOCK_BEGIN | BLOCK_END | CODE_BLOCK | CLASS_END | DOT | SEMICOLON | WS | PARAMETER_BEGIN | PARAMETER_END | COMMA | STRING );";
        }
        @Override
		public int specialStateTransition(int s, final IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_27 = input.LA(1);

                        s = -1;
                        if ( ((LA4_27>='\u0000' && LA4_27<='\uFFFF')) ) {s = 32;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 4, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}
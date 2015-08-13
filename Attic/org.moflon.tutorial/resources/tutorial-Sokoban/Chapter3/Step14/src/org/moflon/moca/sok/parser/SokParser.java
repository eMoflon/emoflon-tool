// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g 2014-02-25 17:35:28

package org.moflon.moca.sok.parser; 
import java.util.Collection;
import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;
import org.moflon.moca.MocaTokenFactory;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SokParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WS", "NUM", "NEWLINE", "EMPTY", "WALL", "GOAL", "ADMIN", "SERVER", "ROW", "COLUMN", "ROWS", "COLS", "BOARD_SPEC", "DIMENSIONS", "BOARD", "STARTWALL"
    };
    public static final int EOF=-1;
    public static final int WS=4;
    public static final int NUM=5;
    public static final int NEWLINE=6;
    public static final int EMPTY=7;
    public static final int WALL=8;
    public static final int GOAL=9;
    public static final int ADMIN=10;
    public static final int SERVER=11;
    public static final int ROW=12;
    public static final int COLUMN=13;
    public static final int ROWS=14;
    public static final int COLS=15;
    public static final int BOARD_SPEC=16;
    public static final int DIMENSIONS=17;
    public static final int BOARD=18;
    public static final int STARTWALL=19;

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
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g"; }



      private int cols = 0;
      private int rows = 0;
      
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
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:60:1: main : (r+= row )+ -> ^( BOARD_SPEC ^( DIMENSIONS ^( ROWS ) ^( COLS ) ) ^( BOARD ( $r)* ) ) ;
    public final SokParser.main_return main() throws RecognitionException {
        SokParser.main_return retval = new SokParser.main_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        List list_r=null;
        RuleReturnScope r = null;
        RewriteRuleSubtreeStream stream_row=new RewriteRuleSubtreeStream(adaptor,"rule row");
        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:60:5: ( (r+= row )+ -> ^( BOARD_SPEC ^( DIMENSIONS ^( ROWS ) ^( COLS ) ) ^( BOARD ( $r)* ) ) )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:60:7: (r+= row )+
            {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:60:7: (r+= row )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=EMPTY && LA1_0<=SERVER)||LA1_0==STARTWALL) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:60:8: r+= row
            	    {
            	    pushFollow(FOLLOW_row_in_main95);
            	    r=row();

            	    state._fsp--;

            	    stream_row.add(r.getTree());
            	    if (list_r==null) list_r=new ArrayList();
            	    list_r.add(r.getTree());


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);



            // AST REWRITE
            // elements: r
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: r
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"token r",list_r);
            root_0 = (Object)adaptor.nil();
            // 61:2: -> ^( BOARD_SPEC ^( DIMENSIONS ^( ROWS ) ^( COLS ) ) ^( BOARD ( $r)* ) )
            {
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:61:5: ^( BOARD_SPEC ^( DIMENSIONS ^( ROWS ) ^( COLS ) ) ^( BOARD ( $r)* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BOARD_SPEC, "BOARD_SPEC"), root_1);

                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:61:18: ^( DIMENSIONS ^( ROWS ) ^( COLS ) )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(DIMENSIONS, "DIMENSIONS"), root_2);

                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:61:31: ^( ROWS )
                {
                Object root_3 = (Object)adaptor.nil();
                root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(ROWS, "ROWS"), root_3);

                adaptor.addChild(root_3, MocaTokenFactory.createCommonTree("NUM", rows + "", tokenNames));

                adaptor.addChild(root_2, root_3);
                }
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:62:8: ^( COLS )
                {
                Object root_3 = (Object)adaptor.nil();
                root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLS, "COLS"), root_3);

                adaptor.addChild(root_3, MocaTokenFactory.createCommonTree("NUM", cols/rows + "", tokenNames));

                adaptor.addChild(root_2, root_3);
                }

                adaptor.addChild(root_1, root_2);
                }
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:63:25: ^( BOARD ( $r)* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BOARD, "BOARD"), root_2);

                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:63:33: ( $r)*
                while ( stream_r.hasNext() ) {
                    adaptor.addChild(root_2, stream_r.nextTree());

                }
                stream_r.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "main"

    public static class row_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "row"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:1: row : (c+= column )+ ( NEWLINE | EOF ) -> ^( ROW ( $c)* ) ;
    public final SokParser.row_return row() throws RecognitionException {
        SokParser.row_return retval = new SokParser.row_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE1=null;
        Token EOF2=null;
        List list_c=null;
        RuleReturnScope c = null;
        Object NEWLINE1_tree=null;
        Object EOF2_tree=null;
        RewriteRuleTokenStream stream_NEWLINE=new RewriteRuleTokenStream(adaptor,"token NEWLINE");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_column=new RewriteRuleSubtreeStream(adaptor,"rule column");
        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:4: ( (c+= column )+ ( NEWLINE | EOF ) -> ^( ROW ( $c)* ) )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:6: (c+= column )+ ( NEWLINE | EOF )
            {
            rows++;
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:16: (c+= column )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=EMPTY && LA2_0<=SERVER)||LA2_0==STARTWALL) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:17: c+= column
            	    {
            	    pushFollow(FOLLOW_column_in_row172);
            	    c=column();

            	    state._fsp--;

            	    stream_column.add(c.getTree());
            	    if (list_c==null) list_c=new ArrayList();
            	    list_c.add(c.getTree());


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

            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:29: ( NEWLINE | EOF )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==NEWLINE) ) {
                alt3=1;
            }
            else if ( (LA3_0==EOF) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:30: NEWLINE
                    {
                    NEWLINE1=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_row177);  
                    stream_NEWLINE.add(NEWLINE1);


                    }
                    break;
                case 2 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:40: EOF
                    {
                    EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_row181);  
                    stream_EOF.add(EOF2);


                    }
                    break;

            }



            // AST REWRITE
            // elements: c
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: c
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_c=new RewriteRuleSubtreeStream(adaptor,"token c",list_c);
            root_0 = (Object)adaptor.nil();
            // 65:45: -> ^( ROW ( $c)* )
            {
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:48: ^( ROW ( $c)* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ROW, "ROW"), root_1);

                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:65:54: ( $c)*
                while ( stream_c.hasNext() ) {
                    adaptor.addChild(root_1, stream_c.nextTree());

                }
                stream_c.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "row"

    public static class column_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:1: column : ( (f= figure ) -> ^( COLUMN $f) | EMPTY -> COLUMN );
    public final SokParser.column_return column() throws RecognitionException {
        SokParser.column_return retval = new SokParser.column_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EMPTY3=null;
        SokParser.figure_return f = null;


        Object EMPTY3_tree=null;
        RewriteRuleTokenStream stream_EMPTY=new RewriteRuleTokenStream(adaptor,"token EMPTY");
        RewriteRuleSubtreeStream stream_figure=new RewriteRuleSubtreeStream(adaptor,"rule figure");
        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:7: ( (f= figure ) -> ^( COLUMN $f) | EMPTY -> COLUMN )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=WALL && LA4_0<=SERVER)||LA4_0==STARTWALL) ) {
                alt4=1;
            }
            else if ( (LA4_0==EMPTY) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:9: (f= figure )
                    {
                    cols++;
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:19: (f= figure )
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:20: f= figure
                    {
                    pushFollow(FOLLOW_figure_in_column204);
                    f=figure();

                    state._fsp--;

                    stream_figure.add(f.getTree());

                    }



                    // AST REWRITE
                    // elements: f
                    // token labels: 
                    // rule labels: f, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_f=new RewriteRuleSubtreeStream(adaptor,"rule f",f!=null?f.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 67:30: -> ^( COLUMN $f)
                    {
                        // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:33: ^( COLUMN $f)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN, "COLUMN"), root_1);

                        adaptor.addChild(root_1, stream_f.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:67:48: EMPTY
                    {
                    cols++;
                    EMPTY3=(Token)match(input,EMPTY,FOLLOW_EMPTY_in_column220);  
                    stream_EMPTY.add(EMPTY3);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 67:64: -> COLUMN
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(COLUMN, "COLUMN"));

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column"

    public static class figure_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "figure"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:69:1: figure : ( WALL | SERVER | GOAL | ADMIN | STARTWALL ) ;
    public final SokParser.figure_return figure() throws RecognitionException {
        SokParser.figure_return retval = new SokParser.figure_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set4=null;

        Object set4_tree=null;

        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:69:7: ( ( WALL | SERVER | GOAL | ADMIN | STARTWALL ) )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\parser\\SokParser.g:69:9: ( WALL | SERVER | GOAL | ADMIN | STARTWALL )
            {
            root_0 = (Object)adaptor.nil();

            set4=(Token)input.LT(1);
            if ( (input.LA(1)>=WALL && input.LA(1)<=SERVER)||input.LA(1)==STARTWALL ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set4));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "figure"

    // Delegated rules


 

    public static final BitSet FOLLOW_row_in_main95 = new BitSet(new long[]{0x0000000000080F82L});
    public static final BitSet FOLLOW_column_in_row172 = new BitSet(new long[]{0x0000000000080FC0L});
    public static final BitSet FOLLOW_NEWLINE_in_row177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_row181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_figure_in_column204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EMPTY_in_column220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_figure231 = new BitSet(new long[]{0x0000000000000002L});

}
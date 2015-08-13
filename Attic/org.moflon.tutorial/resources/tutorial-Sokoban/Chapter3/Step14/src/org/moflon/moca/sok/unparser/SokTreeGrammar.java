// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g 2014-02-25 17:31:14

package org.moflon.moca.sok.unparser;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.HashMap;
public class SokTreeGrammar extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "STRING", "ATTRIBUTE", "'BOARD_SPEC'", "'BOARD'", "'DIMENSIONS'", "'ROWS'", "'COLS'", "'ROW'", "'COLUMN'"
    };
    public static final int EOF=-1;
    public static final int T__7=7;
    public static final int T__8=8;
    public static final int T__9=9;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int ID=4;
    public static final int STRING=5;
    public static final int ATTRIBUTE=6;

    // delegates
    // delegators


        public SokTreeGrammar(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public SokTreeGrammar(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected StringTemplateGroup templateLib =
      new StringTemplateGroup("SokTreeGrammarTemplates", AngleBracketTemplateLexer.class);

    public void setTemplateLib(StringTemplateGroup templateLib) {
      this.templateLib = templateLib;
    }
    public StringTemplateGroup getTemplateLib() {
      return templateLib;
    }
    /** allows convenient multi-value initialization:
     *  "new STAttrMap().put(...).put(...)"
     */
    public static class STAttrMap extends HashMap {
      public STAttrMap put(String attrName, Object value) {
        super.put(attrName, value);
        return this;
      }
      public STAttrMap put(String attrName, int value) {
        super.put(attrName, new Integer(value));
        return this;
      }
    }

    public String[] getTokenNames() { return SokTreeGrammar.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g"; }


    	public Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)	throws RecognitionException  {
    		try {
            		return super.recoverFromMismatchedToken(input, ttype, follow);
                } catch(java.util.NoSuchElementException e){
                    throw new IllegalArgumentException("Your tree does not comply with your tree grammar!\n"
                    		+ " Problems encountered at: [" + "..." + getTreeNodeStream().LT(-1) + " " 
                    		+ getTreeNodeStream().LT(1) + "..." + "] in tree.");
    		}
        }


    public static class main_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "main"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:1: main : ^( 'BOARD_SPEC' ( dimensions )? ^( 'BOARD' (rows+= row )+ ) ) -> board(rows=$rows);
    public final SokTreeGrammar.main_return main() throws RecognitionException {
        SokTreeGrammar.main_return retval = new SokTreeGrammar.main_return();
        retval.start = input.LT(1);

        List list_rows=null;
        RuleReturnScope rows = null;
        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:5: ( ^( 'BOARD_SPEC' ( dimensions )? ^( 'BOARD' (rows+= row )+ ) ) -> board(rows=$rows))
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:7: ^( 'BOARD_SPEC' ( dimensions )? ^( 'BOARD' (rows+= row )+ ) )
            {
            match(input,7,FOLLOW_7_in_main84); 

            match(input, Token.DOWN, null); 
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:22: ( dimensions )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==9) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:22: dimensions
                    {
                    pushFollow(FOLLOW_dimensions_in_main86);
                    dimensions();

                    state._fsp--;


                    }
                    break;

            }

            match(input,8,FOLLOW_8_in_main90); 

            match(input, Token.DOWN, null); 
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:48: (rows+= row )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==12) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:32:48: rows+= row
            	    {
            	    pushFollow(FOLLOW_row_in_main94);
            	    rows=row();

            	    state._fsp--;

            	    if (list_rows==null) list_rows=new ArrayList();
            	    list_rows.add(rows.getTemplate());


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


            match(input, Token.UP, null); 

            match(input, Token.UP, null); 


            // TEMPLATE REWRITE
            // 32:57: -> board(rows=$rows)
            {
                retval.st = templateLib.getInstanceOf("board",
              new STAttrMap().put("rows", list_rows));
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "main"

    public static class dimensions_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "dimensions"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:1: dimensions : ^( 'DIMENSIONS' ^( 'ROWS' STRING ) ^( 'COLS' STRING ) ) ;
    public final SokTreeGrammar.dimensions_return dimensions() throws RecognitionException {
        SokTreeGrammar.dimensions_return retval = new SokTreeGrammar.dimensions_return();
        retval.start = input.LT(1);

        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:11: ( ^( 'DIMENSIONS' ^( 'ROWS' STRING ) ^( 'COLS' STRING ) ) )
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:13: ^( 'DIMENSIONS' ^( 'ROWS' STRING ) ^( 'COLS' STRING ) )
            {
            match(input,9,FOLLOW_9_in_dimensions114); 

            match(input, Token.DOWN, null); 
            match(input,10,FOLLOW_10_in_dimensions117); 

            match(input, Token.DOWN, null); 
            match(input,STRING,FOLLOW_STRING_in_dimensions119); 

            match(input, Token.UP, null); 
            match(input,11,FOLLOW_11_in_dimensions123); 

            match(input, Token.DOWN, null); 
            match(input,STRING,FOLLOW_STRING_in_dimensions125); 

            match(input, Token.UP, null); 

            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dimensions"

    public static class row_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "row"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:36:1: row : ^( 'ROW' (columns+= column )+ ) -> row(columns=$columns);
    public final SokTreeGrammar.row_return row() throws RecognitionException {
        SokTreeGrammar.row_return retval = new SokTreeGrammar.row_return();
        retval.start = input.LT(1);

        List list_columns=null;
        RuleReturnScope columns = null;
        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:36:5: ( ^( 'ROW' (columns+= column )+ ) -> row(columns=$columns))
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:36:7: ^( 'ROW' (columns+= column )+ )
            {
            match(input,12,FOLLOW_12_in_row136); 

            match(input, Token.DOWN, null); 
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:36:22: (columns+= column )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==13) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:36:22: columns+= column
            	    {
            	    pushFollow(FOLLOW_column_in_row140);
            	    columns=column();

            	    state._fsp--;

            	    if (list_columns==null) list_columns=new ArrayList();
            	    list_columns.add(columns.getTemplate());


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


            match(input, Token.UP, null); 


            // TEMPLATE REWRITE
            // 36:33: -> row(columns=$columns)
            {
                retval.st = templateLib.getInstanceOf("row",
              new STAttrMap().put("columns", list_columns));
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "row"

    public static class column_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "column"
    // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:38:1: column : ^( 'COLUMN' (t+= STRING )? ) -> column(type=$t);
    public final SokTreeGrammar.column_return column() throws RecognitionException {
        SokTreeGrammar.column_return retval = new SokTreeGrammar.column_return();
        retval.start = input.LT(1);

        CommonTree t=null;
        List list_t=null;

        try {
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:38:8: ( ^( 'COLUMN' (t+= STRING )? ) -> column(type=$t))
            // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:38:9: ^( 'COLUMN' (t+= STRING )? )
            {
            match(input,13,FOLLOW_13_in_column159); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:38:21: (t+= STRING )?
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==STRING) ) {
                    alt4=1;
                }
                switch (alt4) {
                    case 1 :
                        // C:\\Users\\eburdon\\workspace\\CheatSheetTutorial\\resources\\tutorial-Sokoban\\Chapter3\\Final\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:38:21: t+= STRING
                        {
                        t=(CommonTree)match(input,STRING,FOLLOW_STRING_in_column163); 
                        if (list_t==null) list_t=new ArrayList();
                        list_t.add(t);


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }


            // TEMPLATE REWRITE
            // 38:32: -> column(type=$t)
            {
                retval.st = templateLib.getInstanceOf("column",
              new STAttrMap().put("type", list_t));
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column"

    // Delegated rules


 

    public static final BitSet FOLLOW_7_in_main84 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_dimensions_in_main86 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_main90 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_row_in_main94 = new BitSet(new long[]{0x0000000000001008L});
    public static final BitSet FOLLOW_9_in_dimensions114 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_10_in_dimensions117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_dimensions119 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_11_in_dimensions123 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_dimensions125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_12_in_row136 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_column_in_row140 = new BitSet(new long[]{0x0000000000002008L});
    public static final BitSet FOLLOW_13_in_column159 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_column163 = new BitSet(new long[]{0x0000000000000008L});

}
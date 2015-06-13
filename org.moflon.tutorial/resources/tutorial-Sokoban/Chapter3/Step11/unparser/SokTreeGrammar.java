// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g 2014-02-27 12:02:56

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "STRING", "ATTRIBUTE"
    };
    public static final int EOF=-1;
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
    public String getGrammarFileName() { return "C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g"; }


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
    // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:1: main : ;
    public final SokTreeGrammar.main_return main() throws RecognitionException {
        SokTreeGrammar.main_return retval = new SokTreeGrammar.main_return();
        retval.start = input.LT(1);

        try {
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:5: ()
            // C:\\Users\\eburdon\\workspace_temp\\SokobanCodeAdapter\\src\\org\\moflon\\moca\\sok\\unparser\\SokTreeGrammar.g:34:6: 
            {
            }

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "main"

    // Delegated rules


 

}
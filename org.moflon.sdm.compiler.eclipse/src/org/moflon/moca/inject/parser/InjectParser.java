// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g 2013-06-29 00:25:37

package org.moflon.moca.inject.parser; 
import java.util.ArrayList;
import java.util.Collection;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;

public class InjectParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IMPORT_KEYWORD", "CLASS_KEYWORD", "PARTIAL_KEYWORD", "CLASS_BEGIN", "MODEL_KEYWORD", "MEMBERS_KEYWORD", "BLOCK_BEGIN", "BLOCK_END", "CODE_BLOCK", "CLASS_END", "DOT", "SEMICOLON", "WS", "PARAMETER_BEGIN", "PARAMETER_END", "COMMA", "STRING", "ROOT", "IMPORTS", "MEMBERS", "MODEL", "METHOD"
    };
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
    public static final int ROOT=21;
    public static final int IMPORTS=22;
    public static final int MEMBERS=23;
    public static final int MODEL=24;
    public static final int METHOD=25;

    // delegates
    // delegators


        public InjectParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InjectParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return InjectParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g"; }


      public Collection<Problem> problems = new ArrayList<Problem>();

          public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
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
              problem.setMessage("Parser: " + getErrorMessage(e, tokenNames));
              
              problems.add(problem);
              super.displayRecognitionError(tokenNames, e);
        }


    public static class implMain_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "implMain"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:53:1: implMain : imports classHead CLASS_BEGIN implBody CLASS_END -> ^( ROOT imports implBody ) ;
    public final InjectParser.implMain_return implMain() throws RecognitionException {
        InjectParser.implMain_return retval = new InjectParser.implMain_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CLASS_BEGIN3=null;
        Token CLASS_END5=null;
        InjectParser.imports_return imports1 = null;

        InjectParser.classHead_return classHead2 = null;

        InjectParser.implBody_return implBody4 = null;


        Object CLASS_BEGIN3_tree=null;
        Object CLASS_END5_tree=null;
        RewriteRuleTokenStream stream_CLASS_BEGIN=new RewriteRuleTokenStream(adaptor,"token CLASS_BEGIN");
        RewriteRuleTokenStream stream_CLASS_END=new RewriteRuleTokenStream(adaptor,"token CLASS_END");
        RewriteRuleSubtreeStream stream_classHead=new RewriteRuleSubtreeStream(adaptor,"rule classHead");
        RewriteRuleSubtreeStream stream_imports=new RewriteRuleSubtreeStream(adaptor,"rule imports");
        RewriteRuleSubtreeStream stream_implBody=new RewriteRuleSubtreeStream(adaptor,"rule implBody");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:53:9: ( imports classHead CLASS_BEGIN implBody CLASS_END -> ^( ROOT imports implBody ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:53:11: imports classHead CLASS_BEGIN implBody CLASS_END
            {
            pushFollow(FOLLOW_imports_in_implMain89);
            imports1=imports();

            state._fsp--;

            stream_imports.add(imports1.getTree());
            pushFollow(FOLLOW_classHead_in_implMain91);
            classHead2=classHead();

            state._fsp--;

            stream_classHead.add(classHead2.getTree());
            CLASS_BEGIN3=(Token)match(input,CLASS_BEGIN,FOLLOW_CLASS_BEGIN_in_implMain93);  
            stream_CLASS_BEGIN.add(CLASS_BEGIN3);

            pushFollow(FOLLOW_implBody_in_implMain95);
            implBody4=implBody();

            state._fsp--;

            stream_implBody.add(implBody4.getTree());
            CLASS_END5=(Token)match(input,CLASS_END,FOLLOW_CLASS_END_in_implMain97);  
            stream_CLASS_END.add(CLASS_END5);



            // AST REWRITE
            // elements: implBody, imports
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 53:60: -> ^( ROOT imports implBody )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:53:63: ^( ROOT imports implBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ROOT, "ROOT"), root_1);

                adaptor.addChild(root_1, stream_imports.nextTree());
                adaptor.addChild(root_1, stream_implBody.nextTree());

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
    // $ANTLR end "implMain"

    public static class nonImplMain_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nonImplMain"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:55:1: nonImplMain : imports classHead CLASS_BEGIN nonImplBody CLASS_END -> ^( ROOT imports nonImplBody ) ;
    public final InjectParser.nonImplMain_return nonImplMain() throws RecognitionException {
        InjectParser.nonImplMain_return retval = new InjectParser.nonImplMain_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CLASS_BEGIN8=null;
        Token CLASS_END10=null;
        InjectParser.imports_return imports6 = null;

        InjectParser.classHead_return classHead7 = null;

        InjectParser.nonImplBody_return nonImplBody9 = null;


        Object CLASS_BEGIN8_tree=null;
        Object CLASS_END10_tree=null;
        RewriteRuleTokenStream stream_CLASS_BEGIN=new RewriteRuleTokenStream(adaptor,"token CLASS_BEGIN");
        RewriteRuleTokenStream stream_CLASS_END=new RewriteRuleTokenStream(adaptor,"token CLASS_END");
        RewriteRuleSubtreeStream stream_nonImplBody=new RewriteRuleSubtreeStream(adaptor,"rule nonImplBody");
        RewriteRuleSubtreeStream stream_classHead=new RewriteRuleSubtreeStream(adaptor,"rule classHead");
        RewriteRuleSubtreeStream stream_imports=new RewriteRuleSubtreeStream(adaptor,"rule imports");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:55:12: ( imports classHead CLASS_BEGIN nonImplBody CLASS_END -> ^( ROOT imports nonImplBody ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:55:14: imports classHead CLASS_BEGIN nonImplBody CLASS_END
            {
            pushFollow(FOLLOW_imports_in_nonImplMain114);
            imports6=imports();

            state._fsp--;

            stream_imports.add(imports6.getTree());
            pushFollow(FOLLOW_classHead_in_nonImplMain116);
            classHead7=classHead();

            state._fsp--;

            stream_classHead.add(classHead7.getTree());
            CLASS_BEGIN8=(Token)match(input,CLASS_BEGIN,FOLLOW_CLASS_BEGIN_in_nonImplMain118);  
            stream_CLASS_BEGIN.add(CLASS_BEGIN8);

            pushFollow(FOLLOW_nonImplBody_in_nonImplMain120);
            nonImplBody9=nonImplBody();

            state._fsp--;

            stream_nonImplBody.add(nonImplBody9.getTree());
            CLASS_END10=(Token)match(input,CLASS_END,FOLLOW_CLASS_END_in_nonImplMain122);  
            stream_CLASS_END.add(CLASS_END10);



            // AST REWRITE
            // elements: nonImplBody, imports
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 55:66: -> ^( ROOT imports nonImplBody )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:55:69: ^( ROOT imports nonImplBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ROOT, "ROOT"), root_1);

                adaptor.addChild(root_1, stream_imports.nextTree());
                adaptor.addChild(root_1, stream_nonImplBody.nextTree());

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
    // $ANTLR end "nonImplMain"

    public static class classHead_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "classHead"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:57:1: classHead : PARTIAL_KEYWORD CLASS_KEYWORD STRING ;
    public final InjectParser.classHead_return classHead() throws RecognitionException {
        InjectParser.classHead_return retval = new InjectParser.classHead_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PARTIAL_KEYWORD11=null;
        Token CLASS_KEYWORD12=null;
        Token STRING13=null;

        Object PARTIAL_KEYWORD11_tree=null;
        Object CLASS_KEYWORD12_tree=null;
        Object STRING13_tree=null;

        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:57:10: ( PARTIAL_KEYWORD CLASS_KEYWORD STRING )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:57:12: PARTIAL_KEYWORD CLASS_KEYWORD STRING
            {
            root_0 = (Object)adaptor.nil();

            PARTIAL_KEYWORD11=(Token)match(input,PARTIAL_KEYWORD,FOLLOW_PARTIAL_KEYWORD_in_classHead139); 
            PARTIAL_KEYWORD11_tree = (Object)adaptor.create(PARTIAL_KEYWORD11);
            adaptor.addChild(root_0, PARTIAL_KEYWORD11_tree);

            CLASS_KEYWORD12=(Token)match(input,CLASS_KEYWORD,FOLLOW_CLASS_KEYWORD_in_classHead141); 
            CLASS_KEYWORD12_tree = (Object)adaptor.create(CLASS_KEYWORD12);
            adaptor.addChild(root_0, CLASS_KEYWORD12_tree);

            STRING13=(Token)match(input,STRING,FOLLOW_STRING_in_classHead143); 
            STRING13_tree = (Object)adaptor.create(STRING13);
            adaptor.addChild(root_0, STRING13_tree);


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
    // $ANTLR end "classHead"

    public static class imports_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "imports"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:1: imports : ( importStatement )* -> ^( IMPORTS ( importStatement )* ) ;
    public final InjectParser.imports_return imports() throws RecognitionException {
        InjectParser.imports_return retval = new InjectParser.imports_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        InjectParser.importStatement_return importStatement14 = null;


        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:8: ( ( importStatement )* -> ^( IMPORTS ( importStatement )* ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:10: ( importStatement )*
            {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:10: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IMPORT_KEYWORD) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:11: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_imports151);
            	    importStatement14=importStatement();

            	    state._fsp--;

            	    stream_importStatement.add(importStatement14.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);



            // AST REWRITE
            // elements: importStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:29: -> ^( IMPORTS ( importStatement )* )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:32: ^( IMPORTS ( importStatement )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(IMPORTS, "IMPORTS"), root_1);

                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:59:42: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();

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
    // $ANTLR end "imports"

    public static class importStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "importStatement"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:61:1: importStatement : IMPORT_KEYWORD STRING SEMICOLON -> STRING ;
    public final InjectParser.importStatement_return importStatement() throws RecognitionException {
        InjectParser.importStatement_return retval = new InjectParser.importStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IMPORT_KEYWORD15=null;
        Token STRING16=null;
        Token SEMICOLON17=null;

        Object IMPORT_KEYWORD15_tree=null;
        Object STRING16_tree=null;
        Object SEMICOLON17_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_IMPORT_KEYWORD=new RewriteRuleTokenStream(adaptor,"token IMPORT_KEYWORD");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:61:16: ( IMPORT_KEYWORD STRING SEMICOLON -> STRING )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:61:18: IMPORT_KEYWORD STRING SEMICOLON
            {
            IMPORT_KEYWORD15=(Token)match(input,IMPORT_KEYWORD,FOLLOW_IMPORT_KEYWORD_in_importStatement169);  
            stream_IMPORT_KEYWORD.add(IMPORT_KEYWORD15);

            STRING16=(Token)match(input,STRING,FOLLOW_STRING_in_importStatement171);  
            stream_STRING.add(STRING16);

            SEMICOLON17=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_importStatement173);  
            stream_SEMICOLON.add(SEMICOLON17);



            // AST REWRITE
            // elements: STRING
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 61:50: -> STRING
            {
                adaptor.addChild(root_0, stream_STRING.nextNode());

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
    // $ANTLR end "importStatement"

    public static class implBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "implBody"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:1: implBody : ( members )? ( model )* -> ^( MEMBERS ( members )? ) ^( MODEL ( model )* ) ;
    public final InjectParser.implBody_return implBody() throws RecognitionException {
        InjectParser.implBody_return retval = new InjectParser.implBody_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        InjectParser.members_return members18 = null;

        InjectParser.model_return model19 = null;


        RewriteRuleSubtreeStream stream_model=new RewriteRuleSubtreeStream(adaptor,"rule model");
        RewriteRuleSubtreeStream stream_members=new RewriteRuleSubtreeStream(adaptor,"rule members");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:9: ( ( members )? ( model )* -> ^( MEMBERS ( members )? ) ^( MODEL ( model )* ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:11: ( members )? ( model )*
            {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:11: ( members )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==MEMBERS_KEYWORD) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:11: members
                    {
                    pushFollow(FOLLOW_members_in_implBody184);
                    members18=members();

                    state._fsp--;

                    stream_members.add(members18.getTree());

                    }
                    break;

            }

            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:20: ( model )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==MODEL_KEYWORD) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:20: model
            	    {
            	    pushFollow(FOLLOW_model_in_implBody187);
            	    model19=model();

            	    state._fsp--;

            	    stream_model.add(model19.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



            // AST REWRITE
            // elements: model, members
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 63:27: -> ^( MEMBERS ( members )? ) ^( MODEL ( model )* )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:30: ^( MEMBERS ( members )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MEMBERS, "MEMBERS"), root_1);

                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:40: ( members )?
                if ( stream_members.hasNext() ) {
                    adaptor.addChild(root_1, stream_members.nextTree());

                }
                stream_members.reset();

                adaptor.addChild(root_0, root_1);
                }
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:50: ^( MODEL ( model )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MODEL, "MODEL"), root_1);

                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:63:58: ( model )*
                while ( stream_model.hasNext() ) {
                    adaptor.addChild(root_1, stream_model.nextTree());

                }
                stream_model.reset();

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
    // $ANTLR end "implBody"

    public static class nonImplBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nonImplBody"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:1: nonImplBody : ( members )? -> ^( MEMBERS ( members )? ) ^( MODEL ) ;
    public final InjectParser.nonImplBody_return nonImplBody() throws RecognitionException {
        InjectParser.nonImplBody_return retval = new InjectParser.nonImplBody_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        InjectParser.members_return members20 = null;


        RewriteRuleSubtreeStream stream_members=new RewriteRuleSubtreeStream(adaptor,"rule members");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:12: ( ( members )? -> ^( MEMBERS ( members )? ) ^( MODEL ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:14: ( members )?
            {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:14: ( members )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==MEMBERS_KEYWORD) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:14: members
                    {
                    pushFollow(FOLLOW_members_in_nonImplBody211);
                    members20=members();

                    state._fsp--;

                    stream_members.add(members20.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: members
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 65:23: -> ^( MEMBERS ( members )? ) ^( MODEL )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:26: ^( MEMBERS ( members )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MEMBERS, "MEMBERS"), root_1);

                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:36: ( members )?
                if ( stream_members.hasNext() ) {
                    adaptor.addChild(root_1, stream_members.nextTree());

                }
                stream_members.reset();

                adaptor.addChild(root_0, root_1);
                }
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:65:46: ^( MODEL )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MODEL, "MODEL"), root_1);

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
    // $ANTLR end "nonImplBody"

    public static class members_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "members"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:67:1: members : MEMBERS_KEYWORD CODE_BLOCK -> CODE_BLOCK ;
    public final InjectParser.members_return members() throws RecognitionException {
        InjectParser.members_return retval = new InjectParser.members_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MEMBERS_KEYWORD21=null;
        Token CODE_BLOCK22=null;

        Object MEMBERS_KEYWORD21_tree=null;
        Object CODE_BLOCK22_tree=null;
        RewriteRuleTokenStream stream_CODE_BLOCK=new RewriteRuleTokenStream(adaptor,"token CODE_BLOCK");
        RewriteRuleTokenStream stream_MEMBERS_KEYWORD=new RewriteRuleTokenStream(adaptor,"token MEMBERS_KEYWORD");

        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:67:8: ( MEMBERS_KEYWORD CODE_BLOCK -> CODE_BLOCK )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:67:10: MEMBERS_KEYWORD CODE_BLOCK
            {
            MEMBERS_KEYWORD21=(Token)match(input,MEMBERS_KEYWORD,FOLLOW_MEMBERS_KEYWORD_in_members232);  
            stream_MEMBERS_KEYWORD.add(MEMBERS_KEYWORD21);

            CODE_BLOCK22=(Token)match(input,CODE_BLOCK,FOLLOW_CODE_BLOCK_in_members234);  
            stream_CODE_BLOCK.add(CODE_BLOCK22);



            // AST REWRITE
            // elements: CODE_BLOCK
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 67:37: -> CODE_BLOCK
            {
                adaptor.addChild(root_0, stream_CODE_BLOCK.nextNode());

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
    // $ANTLR end "members"

    public static class model_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "model"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:69:1: model : MODEL_KEYWORD signature CODE_BLOCK -> ^( METHOD signature CODE_BLOCK ) ;
    public final InjectParser.model_return model() throws RecognitionException {
        InjectParser.model_return retval = new InjectParser.model_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODEL_KEYWORD23=null;
        Token CODE_BLOCK25=null;
        InjectParser.signature_return signature24 = null;


        Object MODEL_KEYWORD23_tree=null;
        Object CODE_BLOCK25_tree=null;
        RewriteRuleTokenStream stream_CODE_BLOCK=new RewriteRuleTokenStream(adaptor,"token CODE_BLOCK");
        RewriteRuleTokenStream stream_MODEL_KEYWORD=new RewriteRuleTokenStream(adaptor,"token MODEL_KEYWORD");
        RewriteRuleSubtreeStream stream_signature=new RewriteRuleSubtreeStream(adaptor,"rule signature");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:69:6: ( MODEL_KEYWORD signature CODE_BLOCK -> ^( METHOD signature CODE_BLOCK ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:69:9: MODEL_KEYWORD signature CODE_BLOCK
            {
            MODEL_KEYWORD23=(Token)match(input,MODEL_KEYWORD,FOLLOW_MODEL_KEYWORD_in_model247);  
            stream_MODEL_KEYWORD.add(MODEL_KEYWORD23);

            pushFollow(FOLLOW_signature_in_model249);
            signature24=signature();

            state._fsp--;

            stream_signature.add(signature24.getTree());
            CODE_BLOCK25=(Token)match(input,CODE_BLOCK,FOLLOW_CODE_BLOCK_in_model251);  
            stream_CODE_BLOCK.add(CODE_BLOCK25);



            // AST REWRITE
            // elements: CODE_BLOCK, signature
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 69:44: -> ^( METHOD signature CODE_BLOCK )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:69:47: ^( METHOD signature CODE_BLOCK )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(METHOD, "METHOD"), root_1);

                adaptor.addChild(root_1, stream_signature.nextTree());
                adaptor.addChild(root_1, stream_CODE_BLOCK.nextNode());

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
    // $ANTLR end "model"

    public static class signature_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "signature"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:1: signature : name= STRING PARAMETER_BEGIN ( singleParam ( COMMA singleParam )* )? PARAMETER_END -> ^( $name ( singleParam )* ) ;
    public final InjectParser.signature_return signature() throws RecognitionException {
        InjectParser.signature_return retval = new InjectParser.signature_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token name=null;
        Token PARAMETER_BEGIN26=null;
        Token COMMA28=null;
        Token PARAMETER_END30=null;
        InjectParser.singleParam_return singleParam27 = null;

        InjectParser.singleParam_return singleParam29 = null;


        Object name_tree=null;
        Object PARAMETER_BEGIN26_tree=null;
        Object COMMA28_tree=null;
        Object PARAMETER_END30_tree=null;
        RewriteRuleTokenStream stream_PARAMETER_END=new RewriteRuleTokenStream(adaptor,"token PARAMETER_END");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_PARAMETER_BEGIN=new RewriteRuleTokenStream(adaptor,"token PARAMETER_BEGIN");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_singleParam=new RewriteRuleSubtreeStream(adaptor,"rule singleParam");
        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:10: (name= STRING PARAMETER_BEGIN ( singleParam ( COMMA singleParam )* )? PARAMETER_END -> ^( $name ( singleParam )* ) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:12: name= STRING PARAMETER_BEGIN ( singleParam ( COMMA singleParam )* )? PARAMETER_END
            {
            name=(Token)match(input,STRING,FOLLOW_STRING_in_signature270);  
            stream_STRING.add(name);

            PARAMETER_BEGIN26=(Token)match(input,PARAMETER_BEGIN,FOLLOW_PARAMETER_BEGIN_in_signature272);  
            stream_PARAMETER_BEGIN.add(PARAMETER_BEGIN26);

            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:40: ( singleParam ( COMMA singleParam )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==STRING) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:41: singleParam ( COMMA singleParam )*
                    {
                    pushFollow(FOLLOW_singleParam_in_signature275);
                    singleParam27=singleParam();

                    state._fsp--;

                    stream_singleParam.add(singleParam27.getTree());
                    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:53: ( COMMA singleParam )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==COMMA) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:54: COMMA singleParam
                    	    {
                    	    COMMA28=(Token)match(input,COMMA,FOLLOW_COMMA_in_signature278);  
                    	    stream_COMMA.add(COMMA28);

                    	    pushFollow(FOLLOW_singleParam_in_signature280);
                    	    singleParam29=singleParam();

                    	    state._fsp--;

                    	    stream_singleParam.add(singleParam29.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            PARAMETER_END30=(Token)match(input,PARAMETER_END,FOLLOW_PARAMETER_END_in_signature286);  
            stream_PARAMETER_END.add(PARAMETER_END30);



            // AST REWRITE
            // elements: singleParam, name
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 71:90: -> ^( $name ( singleParam )* )
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:93: ^( $name ( singleParam )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:71:101: ( singleParam )*
                while ( stream_singleParam.hasNext() ) {
                    adaptor.addChild(root_1, stream_singleParam.nextTree());

                }
                stream_singleParam.reset();

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
    // $ANTLR end "signature"

    public static class singleParam_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "singleParam"
    // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:73:1: singleParam : type= STRING name= STRING -> ^( $name $type) ;
    public final InjectParser.singleParam_return singleParam() throws RecognitionException {
        InjectParser.singleParam_return retval = new InjectParser.singleParam_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token type=null;
        Token name=null;

        Object type_tree=null;
        Object name_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:73:12: (type= STRING name= STRING -> ^( $name $type) )
            // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:73:14: type= STRING name= STRING
            {
            type=(Token)match(input,STRING,FOLLOW_STRING_in_singleParam305);  
            stream_STRING.add(type);

            name=(Token)match(input,STRING,FOLLOW_STRING_in_singleParam309);  
            stream_STRING.add(name);



            // AST REWRITE
            // elements: type, name
            // token labels: name, type
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleTokenStream stream_type=new RewriteRuleTokenStream(adaptor,"token type",type);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 73:38: -> ^( $name $type)
            {
                // C:\\Users\\anjorin\\Dropbox\\Home\\dev\\workspaces\\workspace_emoflon\\MoflonIdeCore\\src\\org\\moflon\\moca\\inject\\parser\\InjectParser.g:73:41: ^( $name $type)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                adaptor.addChild(root_1, stream_type.nextNode());

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
    // $ANTLR end "singleParam"

    // Delegated rules


 

    public static final BitSet FOLLOW_imports_in_implMain89 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_classHead_in_implMain91 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLASS_BEGIN_in_implMain93 = new BitSet(new long[]{0x0000000000002300L});
    public static final BitSet FOLLOW_implBody_in_implMain95 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLASS_END_in_implMain97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_imports_in_nonImplMain114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_classHead_in_nonImplMain116 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CLASS_BEGIN_in_nonImplMain118 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_nonImplBody_in_nonImplMain120 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLASS_END_in_nonImplMain122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARTIAL_KEYWORD_in_classHead139 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_KEYWORD_in_classHead141 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_STRING_in_classHead143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_importStatement_in_imports151 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_IMPORT_KEYWORD_in_importStatement169 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_STRING_in_importStatement171 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_SEMICOLON_in_importStatement173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_members_in_implBody184 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_model_in_implBody187 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_members_in_nonImplBody211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEMBERS_KEYWORD_in_members232 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CODE_BLOCK_in_members234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODEL_KEYWORD_in_model247 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_signature_in_model249 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CODE_BLOCK_in_model251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_signature270 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PARAMETER_BEGIN_in_signature272 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_singleParam_in_signature275 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_COMMA_in_signature278 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_singleParam_in_signature280 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_PARAMETER_END_in_signature286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_singleParam305 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_STRING_in_singleParam309 = new BitSet(new long[]{0x0000000000000002L});

}
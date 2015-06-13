package org.moflon.moca;
import java.io.File;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.mosl.eclass.parser.EclassParserAdapter;
import org.moflon.mosl.mconf.parser.MconfParserAdapter;
import org.moflon.mosl.pattern.parser.PatternParserAdapter;
import org.moflon.mosl.sch.parser.SchParserAdapter;
import org.moflon.mosl.tgg.parser.TggParserAdapter;

import Moca.CodeAdapter;
import Moca.MocaFactory;
import Moca.Problem;
import MocaTree.Folder;
import MocaTree.MocaTreePackage;

public class MOSLMain 
{

  private static CodeAdapter codeAdapter;

  public static void main(final String[] args) 
  {
    BasicConfigurator.configure();
    
    System.out.println(Calendar.getInstance().getTime());
    
    eMoflonEMFUtil.init(MocaTreePackage.eINSTANCE);
    
    // Test code
    // MOSLUtils.loadTree("../CoreLanguages/.temp/CoreLanguages.moca.xmi");

    // Register parsers and unparsers
    codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
    codeAdapter.getParser().add(new EclassParserAdapter());
    codeAdapter.getParser().add(new PatternParserAdapter());
    codeAdapter.getParser().add(new MconfParserAdapter());
    codeAdapter.getParser().add(new SchParserAdapter());
    codeAdapter.getParser().add(new TggParserAdapter());
//    codeAdapter.getUnparser().add(new EclassUnparserAdapter());
//    codeAdapter.getUnparser().add(new PatternUnparserAdapter());
//    codeAdapter.getUnparser().add(new MconfUnparserAdapter());

    System.out.println("Perform text-to-tree");
    Folder tree = codeAdapter.parse(new File("instances/in/MOSL/"));
    
    if (codeAdapter.getProblems().size() > 0) {
	    for (Problem p : codeAdapter.getProblems()) {
	    	System.err.println("ERROR: " + p.getSource() + ":" + p.getLine() + ":" + p.getCharacterPositionStart() + " : " + p.getMessage());
	    }
	    return;
    }
    
    System.out.println("Save tree to file");
    eMoflonEMFUtil.saveModel(tree, "instances/out/MOSL2Tree.xmi");
    /*
    // Perform tree-to-text (using initial tree)
    if (DO_UNPARSING) {
       System.out.println("Unparsing");
       codeAdapter.unparse("instances/out", tree); 
       
       if (codeAdapter.getProblems().size() > 0) {
          for (Problem p : codeAdapter.getProblems()) {
            System.err.println("ERROR: " + p.getSource() + ":" + p.getLine() + ":" + p.getCharacterPositionStart() + " : " + p.getMessage());
          }
          return;
       }
    }
    
    try {
    	System.out.println("Transform the tree into an EA compliant way");
	    MOSLTransformer transformer = MOSLCodeAdapterFactory.eINSTANCE.createMOSLTransformer();
	    Node eaTree = transformer.transformMOSLToEA(tree);

	    if (transformer.getErrors().size() > 0) {
		    for (MOSLCodeAdapter.LanguageError e : transformer.getErrors()) {
		    	System.err.println("ERROR: " + e.getMessage());
		    }
		    // return;
	    }
	    
	    System.out.println("Save eaTree to file");
	    eMoflonEMFUtil.saveModel(eaTree, "instances/eaTree.xmi");

	    /*
       System.out.println("Property list:");
       StringBuffer deps = new StringBuffer();
       for (Property p : transformer.getProperties()) {
          if (p.getKey() == "dependency") {
             deps.append(p.getValue()).append(",");
          }
       }
       
       for (Property p : transformer.getProperties()) {
          if (!"dependency".equals(p.getKey())) {
             String value = p.getValue();
             if (p.getKey().endsWith(".dependencies") && (p.getValue() == null || p.getValue().trim().length() == 0)) {
                value = deps.toString();
             }
             System.out.format("%s=%s\n", p.getKey(), value);
          }
       }
       */
	    /*
    } catch (Exception e) {
    	System.err.println("Transforming to EA failed.");
    	e.printStackTrace();
    }*/
    
    System.out.println("Done");
  }
}
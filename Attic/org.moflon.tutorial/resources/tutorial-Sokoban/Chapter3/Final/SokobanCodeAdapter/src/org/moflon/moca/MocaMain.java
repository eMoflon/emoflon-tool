package org.moflon.moca;
import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.moflon.moca.sok.parser.SokParserAdapter;
import org.moflon.moca.sok.unparser.SokUnparserAdapter;
import org.moflon.core.utilities.eMoflonEMFUtil;
import Moca.CodeAdapter;
import Moca.MocaFactory;
import MocaTree.Folder;
import MocaTree.MocaTreePackage;

public class MocaMain 
{

  private static CodeAdapter codeAdapter;

  public static void main(String[] args) 
  {
	  BasicConfigurator.configure();

    // Perform text-to-tree
    Folder tree = getCodeAdapter().parse(new File("instances/in/"));
    
    // Save tree to file
    eMoflonEMFUtil.saveModel(tree, "instances/tree.xmi");

    // Perform tree-to-text (using initial tree)
    getCodeAdapter().unparse("instances/out", tree);
	  
  }
  
  public static CodeAdapter getCodeAdapter(){
	  if (codeAdapter == null) {
		// Register parsers and unparsers
		  codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
		  codeAdapter.getParser().add(new SokParserAdapter());
		  codeAdapter.getUnparser().add(new SokUnparserAdapter());
	  }
	  return codeAdapter;
  }
}
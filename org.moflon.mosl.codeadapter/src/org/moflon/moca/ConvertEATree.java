package org.moflon.moca;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.moflon.mosl.MOSLUtils;
import org.moflon.mosl.eclass.unparser.EclassUnparserAdapter;
import org.moflon.mosl.mconf.unparser.MconfUnparserAdapter;
import org.moflon.mosl.pattern.unparser.PatternUnparserAdapter;
import org.moflon.mosl.sch.unparser.SchUnparserAdapter;
import org.moflon.mosl.tgg.unparser.TggUnparserAdapter;

import Moca.CodeAdapter;
import Moca.MocaFactory;
import Moca.Problem;
import MocaTree.Folder;

public class ConvertEATree
{

   private static CodeAdapter codeAdapter;

   public static void main(String[] args) throws MalformedURLException
   {
      BasicConfigurator.configure();

      System.out.println(Calendar.getInstance().getTime());

      // Test code
//      Node tree = MOSLUtils.loadTree(new URL("file:instances/untransformer/in/Demo4.xmi"));
      
      // Register parsers and unparsers
      codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
//      codeAdapter.getParser().add(new EclassParserAdapter());
//      codeAdapter.getParser().add(new PatternParserAdapter());
//      codeAdapter.getParser().add(new MconfParserAdapter());
//      codeAdapter.getParser().add(new TggParserAdapter());
//      codeAdapter.getParser().add(new SchParserAdapter());
      
      codeAdapter.getUnparser().add(new EclassUnparserAdapter());
      codeAdapter.getUnparser().add(new PatternUnparserAdapter());
      codeAdapter.getUnparser().add(new MconfUnparserAdapter());
      codeAdapter.getUnparser().add(new TggUnparserAdapter());
      codeAdapter.getUnparser().add(new SchUnparserAdapter());
      // Transform tree
//      MOSLUntransformer untransformer = UntransformerFactory.eINSTANCE.createMOSLUntransformer();

      System.out.println("Untransforming");
//      Folder folder = untransformer.untransformEATree(tree);
      //MOSLUtils.initTypes(folder);
      
//      eMoflonEMFUtil.saveModel(folder, "instances/untransformer/out/untransformed.xmi");
      Folder folder = MOSLUtils.loadTree(new URL("file:instances/out/MOSL2Tree.xmi"), Folder.class);
      // Perform tree-to-text (using initial tree)
      System.out.println("Unparsing");
      codeAdapter.unparse("instances/in/MOSL/", folder);

      if (codeAdapter.getProblems().size() > 0)
      {
         for (Problem p : codeAdapter.getProblems())
         {
            System.err.println("ERROR: " + p.getSource() + ":" + p.getLine() + ":" + p.getCharacterPositionStart() + " : " + p.getMessage());
         }
         return;
      }
      
      System.out.println("Done");
   }
}
package org.moflon.mosl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.moflon.moca.SafeCommonTreeNodeStream;

import Moca.unparser.impl.TemplateUnparserImpl;

public abstract class AbstractMOSLUnparserAdapter extends TemplateUnparserImpl
{
   private String currentFilename;
   protected final String templateDir;
   private final Logger log = Logger.getLogger(this.getClass());

   public AbstractMOSLUnparserAdapter(String templateDir)
   {
      super();
      this.templateDir = templateDir;
   }

   @Override
   public boolean canUnparseFile(String fileName) {
      if (fileName.endsWith(getExtension())) {
         log.debug("unparse " + fileName);
         currentFilename = fileName;
         return true;
      }
      return false;
   }
   
   @Override
   protected String callMainRule(CommonTreeNodeStream tree, StringTemplateGroup templates) throws RecognitionException {
      MOSLTreeGrammar eclassTreeGrammar = createTreeGrammar(new SafeCommonTreeNodeStream(tree.getTreeSource()));
      eclassTreeGrammar.setTemplateLib(templates);
      StringTemplate st = (StringTemplate) eclassTreeGrammar.main().getTemplate();
      
      if (st == null || eclassTreeGrammar.getErrors().size() > 0) {
         for (RecognitionException e : eclassTreeGrammar.getErrors()) {
            MOSLUtils.createProblem(getCodeAdapter(), currentFilename, new AntlrParserError(e, eclassTreeGrammar.getTokenNames()));
         }
         return "";
      }
      
      return st.toString();
   }
   
   @Override
   protected StringTemplateGroup getStringTemplateGroup() throws FileNotFoundException {
      if (templateDir != null) {
         return new StringTemplateGroup(new FileReader(new File(templateDir, getTemplateFile())));
      }
      return loadStringTemplateGroup(getTemplateFile());
   }
   
   public static StringTemplateGroup loadStringTemplateGroup(String path) {
      try {
         InputStreamReader reader = new InputStreamReader(getTemplateFileURL(path).openStream());
         return new StringTemplateGroup(reader);
      } catch (IOException e) {
         System.err.println("unable to load template file: " + path);
      }
      return null;
   }
   
   public static URL getTemplateFileURL(String path) throws IOException {
     return FileLocator.resolve(Platform.getBundle("org.moflon.MOSLCodeAdapter").getEntry(path));
   }


  
   protected abstract MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream);
   protected abstract String getTemplateFile();
   public abstract String getExtension();
}
/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.tools.generators;

import java.io.*;
import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.basic.*;
import de.upb.tools.fca.FHashMap;


/**
 * Class for generating HTML documentation of assocs.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class AssocsDocGenerator
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (AssocsDocGenerator.class);

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private TemplateFile assocsTemplate;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private TemplateFile htmlTemplate;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FHashMap parameter;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String PATH = "doc" + File.separator + "assocs" + File.separator;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String INDEX_FILENAME = "Index.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String SHORTCUTS_FILENAME = "Shortcuts.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String MAIN_FILENAME = "Main.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String ONE_FILENAME = "One.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String MANY_FILENAME = "Many.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String ATTR_QUAL_ONE_FILENAME = "Attr_Qual_One.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String QUAL_ONE_FILENAME = "Qual_One.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String ATTR_QUAL_MANY_FILENAME = "Attr_Qual_Many.html";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String QUAL_MANY_FILENAME = "Qual_Many.html";


   /**
    * Constructor for class AssocsDocGenerator
    */
   public AssocsDocGenerator()
   {
      assocsTemplate = TemplateManager.get().getTemplate ("Templates/AssocTemplateFCA.tpl");
      htmlTemplate = TemplateManager.get().getTemplate ("Templates/AssocsDocumentation.tpl");

      initParameter();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void initParameter()
   {
      parameter = new FHashMap();

      parameter.put ("$RIGHTROLE$", "rightRole");
      parameter.put ("$RIGHTROLES$", "rightRoles");
      parameter.put ("$RIGHTROLES_SUFFIX$", "RightRoles");
      parameter.put ("$RIGHTCLASS$", "RightClass");
      parameter.put ("$KEYTYPE$", "KeyType");
      parameter.put ("$GETKEY$", "getKey");
      parameter.put ("$INSERT$", "&lt;method to insert&gt;");
      parameter.put ("$REMOVE$", "&lt;method to remove&gt;");
      parameter.put ("$CONTAINER$", "FHashSet");
      parameter.put ("$LINKLISTCODE$", " [&& !hasInRightRoles (value)]");

      parameter.put ("$ONE_FILENAME$", ONE_FILENAME);
      parameter.put ("$MANY_FILENAME$", MANY_FILENAME);
      parameter.put ("$AQO_FILENAME$", ATTR_QUAL_ONE_FILENAME);
      parameter.put ("$QO_FILENAME$", QUAL_ONE_FILENAME);
      parameter.put ("$AQM_FILENAME$", ATTR_QUAL_MANY_FILENAME);
      parameter.put ("$QM_FILENAME$", QUAL_MANY_FILENAME);
      parameter.put ("$SHORTCUTS_FILENAME$", SHORTCUTS_FILENAME);
      parameter.put ("$MAIN_FILENAME$", MAIN_FILENAME);

      TemplateCodeBlock template = assocsTemplate.getFromCodeBlocks ("assoc-set-v1");
      parameter.put ("$assoc-set-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-get-v1");
      parameter.put ("$assoc-get-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v1");
      parameter.put ("$assoc-hasIn-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v1A");
      parameter.put ("$assoc-hasIn-v1A$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v2");
      parameter.put ("$assoc-hasIn-v2$", template.getSourceCode (parameter));

//      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v3");
//      parameter.put ("$assoc-hasIn-v3$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v4");
      parameter.put ("$assoc-hasIn-v4$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasIn-v5");
      parameter.put ("$assoc-hasIn-v5$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-hasKeyIn-v1");
      parameter.put ("$assoc-hasKeyIn-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-iteratorOf-v1");
      parameter.put ("$assoc-iteratorOf-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-iteratorOf-v2");
      parameter.put ("$assoc-iteratorOf-v2$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-iteratorOf-v3");
      parameter.put ("$assoc-iteratorOf-v3$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-keysOf-v1");
      parameter.put ("$assoc-keysOf-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-entriesOf-v1");
      parameter.put ("$assoc-entriesOf-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-sizeOf-v1");
      parameter.put ("$assoc-sizeOf-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-sizeOf-v2");
      parameter.put ("$assoc-sizeOf-v2$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v1");
      parameter.put ("$assoc-addTo-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v2");
      parameter.put ("$assoc-addTo-v2$", template.getSourceCode (parameter));

//      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v3");
//      parameter.put ("$assoc-addTo-v3$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v4");
      parameter.put ("$assoc-addTo-v4$", template.getSourceCode (parameter));

//      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v5");
//      parameter.put ("$assoc-addTo-v5$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-addTo-v6");
      parameter.put ("$assoc-addTo-v6$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-getFrom-v1");
      parameter.put ("$assoc-getFrom-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeFrom-v1");
      parameter.put ("$assoc-removeFrom-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeFrom-v2");
      parameter.put ("$assoc-removeFrom-v2$", template.getSourceCode (parameter));

//      template = assocsTemplate.getFromCodeBlocks ("assoc-removeFrom-v3");
//      parameter.put ("$assoc-removeFrom-v3$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeFrom-v4");
      parameter.put ("$assoc-removeFrom-v4$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeFrom-v5");
      parameter.put ("$assoc-removeFrom-v5$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeKeyFrom-v1");
      parameter.put ("$assoc-removeKeyFrom-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeKeyFrom-v2");
      parameter.put ("$assoc-removeKeyFrom-v2$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeAllFrom-v1");
      parameter.put ("$assoc-removeAllFrom-v1$", template.getSourceCode (parameter));

      template = assocsTemplate.getFromCodeBlocks ("assoc-removeAllFrom-v2");
      parameter.put ("$assoc-removeAllFrom-v2$", template.getSourceCode (parameter));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void generateHtmlFiles()
   {
      System.out.print ("Generating Fujaba association documentation...");

      TemplateCodeBlock htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Index");
      writeFile (PATH + INDEX_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Shortcuts");
      writeFile (PATH + SHORTCUTS_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Main");
      writeFile (PATH + MAIN_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("One");
      writeFile (PATH + ONE_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Many");
      writeFile (PATH + MANY_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("One-Qualified-Intern");
      writeFile (PATH + ATTR_QUAL_ONE_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Many-Qualified-Intern");
      writeFile (PATH + ATTR_QUAL_MANY_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("One-Qualified-Extern");
      writeFile (PATH + QUAL_ONE_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.print (".");

      htmlCodeBlock = htmlTemplate.getFromCodeBlocks ("Many-Qualified-Extern");
      writeFile (PATH + QUAL_MANY_FILENAME, htmlCodeBlock.getSourceCode (parameter));

      System.out.println ("finished.");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param fileName  No description provided
    * @param content   No description provided
    */
   public void writeFile (String fileName, String content)
   {
      try
      {
         File file = new File (fileName);
         FileWriter fileWriter = new FileWriter (file);
         fileWriter.write (content);
         fileWriter.flush();
         fileWriter.close();
      }
      catch (IOException e)
      {
         log.error ("Error writing file " + fileName);
      }
   }


   /**
    * The main program for the AssocsDocGenerator class
    *
    * @param args  The command line arguments
    */
   public static void main (String[] args)
   {
      if (args.length > 0)
      {
         PATH = args[0];
      }
      AssocsDocGenerator assocsDocGenerator = new AssocsDocGenerator();
      assocsDocGenerator.generateHtmlFiles();
   }

}

/*
 * $Log$
 * Revision 1.19  2007/01/25 17:01:28  travkin
 * Fixed Assocs generator.
 *
 */

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
package de.uni_paderborn.fujaba.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.app.FujabaApp;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap;


/**
 * <h2>Associations</h2> <pre>
 *                 +----------+ 1        file        1
 * TemplateManager | filename +------------------------ TemplateFile
 *                 +----------+ manager      templates
 *
 *              +----------+ 1                   1
 * TemplateFile | name     +----------------------- TemplateCodeBlock
 *              +----------+ template      codeBlocks
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class TemplateFile
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (TemplateFile.class);


   /**
    * Constructor for class TemplateFile
    *
    * @param filename  No description provided
    */
   public TemplateFile (String filename)
   {
      this.setFilename (filename);
   }

   // Attributes

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String filename = "";


   /**
    * Sets the filename attribute of the TemplateFile object
    *
    * @param filename  The new filename value
    */
   public void setFilename (String filename)
   {
      if ( (this.filename == null) || !this.filename.equals (filename))
      {
         // adjust position in symbol tables
         TemplateManager oldManager = this.getManager();
         this.setManager (null);

         // change key
         this.filename = filename;

         // reestablish assoc
         this.setManager (oldManager);
      }
   }


   /**
    * Get the filename attribute of the TemplateFile object
    *
    * @return   The filename value
    */
   public String getFilename()
   {
      return filename;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private long modifiedTag = 0;


   /**
    * Sets the modifiedTag attribute of the TemplateFile object
    *
    * @param tag  The new modifiedTag value
    */
   public void setModifiedTag (long tag)
   {
      this.modifiedTag = tag;
   }


   /**
    * Get the modifiedTag attribute of the TemplateFile object
    *
    * @return   The modifiedTag value
    */
   public long getModifiedTag()
   {
      return modifiedTag;
   }


   // Assocs

   /**
    * <pre>
    *                 +----------+ 1        file        1
    * TemplateManager | filename +------------------------ TemplateFile
    *                 +----------+ manager      templates
    * </pre>
    */
   private TemplateManager manager;


   /**
    * Sets the manager attribute of the TemplateFile object
    *
    * @param obj  The new manager value
    * @return     No description provided
    */
   public boolean setManager (TemplateManager obj)
   {
      boolean changed = false;

      if (this.manager != obj)
      {
         if (this.manager != null)
         {
            TemplateManager oldValue = this.manager;
            this.manager = null;
            oldValue.removeFromTemplates (this);
         }
         this.manager = obj;
         if (obj != null)
         {
            obj.addToTemplates (this);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * Get the manager attribute of the TemplateFile object
    *
    * @return   The manager value
    */
   public TemplateManager getManager()
   {
      return this.manager;
   }


   /**
    * <pre>
    *              +----------+ 1                   1
    * TemplateFile | name     +----------------------- TemplateCodeBlock
    *              +----------+ template      codeBlocks
    * </pre>
    */
   private FHashMap codeBlocks;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public boolean hasInCodeBlocks (TemplateCodeBlock obj)
   {
      return  ( (this.codeBlocks != null) &&
          (obj != null) &&  (obj.getName() != null) &&
          (this.codeBlocks.get (obj.getName()) == obj));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public boolean hasKeyInCodeBlocks (String key)
   {
      return  ( (this.codeBlocks != null) &&
          (key != null) &&
         this.codeBlocks.containsKey (key));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfCodeBlocks()
   {
      return  ( (this.codeBlocks == null)
         ? FEmptyIterator.get()
         : this.codeBlocks.values().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator keysOfCodeBlocks()
   {
      return  ( (this.codeBlocks == null)
         ? FEmptyIterator.get()
         : this.codeBlocks.keySet().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator entriesOfCodeBlocks()
   {
      return  ( (this.codeBlocks == null)
         ? FEmptyIterator.get()
         : this.codeBlocks.entrySet().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfCodeBlocks()
   {
      return  ( (this.codeBlocks == null)
         ? 0
         : this.codeBlocks.size());
   }


   /**
    * Get the fromCodeBlocks attribute of the TemplateFile object
    *
    * @param key  No description provided
    * @return     The fromCodeBlocks value
    */
   public TemplateCodeBlock getFromCodeBlocks (String key)
   {
      return  ( ( (this.codeBlocks == null) ||  (key == null))
         ? null
         : (TemplateCodeBlock) this.codeBlocks.get (key));
   }


   /**
    * Access method for an one to n association.
    *
    * @param obj  The object added.
    * @return     No description provided
    */
   public boolean addToCodeBlocks (TemplateCodeBlock obj)
   {
      boolean changed = false;

      if ( (obj != null) &&  (obj.getName() != null))
      {
         if (this.codeBlocks == null)
         {
            this.codeBlocks = new FHashMap();
         }
         TemplateCodeBlock oldValue = (TemplateCodeBlock) this.codeBlocks.put (obj.getName(), obj);
         if (oldValue != obj)
         {
            if (oldValue != null)
            {
               oldValue.setTemplate (null);
            }
            obj.setTemplate (this);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public boolean removeFromCodeBlocks (TemplateCodeBlock obj)
   {
      boolean changed = false;

      if ( (this.codeBlocks != null) &&  (obj != null) &&  (obj.getName() != null))
      {
         TemplateCodeBlock oldValue = (TemplateCodeBlock) this.codeBlocks.get (obj.getName());
         if (oldValue == obj)
         {
            this.codeBlocks.remove (obj.getName());
            obj.setTemplate (null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public boolean removeKeyFromCodeBlocks (String key)
   {
      boolean changed = false;

      if ( (this.codeBlocks != null) &&  (key != null))
      {
         TemplateCodeBlock tmpObj = (TemplateCodeBlock) this.codeBlocks.get (key);
         if (tmpObj != null)
         {
            this.codeBlocks.remove (key);
            tmpObj.setTemplate (null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromCodeBlocks()
   {
      TemplateCodeBlock tmpObj;
      Iterator iter = this.iteratorOfCodeBlocks();

      while (iter.hasNext())
      {
         tmpObj = (TemplateCodeBlock) iter.next();
         this.removeFromCodeBlocks (tmpObj);
      }
   }

   // Methods

   /**
    * Get the modified attribute of the TemplateFile object
    *
    * @return   The modified value
    */
   public boolean isModified()
   {
      File file;
      boolean modified = false;

      file = new File (getFilename());

      if (file.exists())
      {
         if (file.lastModified() > getModifiedTag())
         {
            modified = true;
         }
      }

      return modified;
   }


   /**
    * Tries to read the template file specified in the filename attribute. It first searches
    * in the current directory of Fujaba. If this fails, it searches in the class path, i.e.
    * it will then find also template files in JAR files. If the file is retrieved from the
    * current directory, the modification date of the file will be checked whenever the template
    * is used. This is not the case when the template is retrieved from the class path.
    *
    * @return              a buffered reader of the template file
    * @throws IOException  Exception description not provided
    */
   private BufferedReader open() throws IOException
   {
      BufferedReader in = null;

      String filename = getFilename();
      try
      {
         // FIXME !!! Remove FujabaApp reference or move this class to Fujaba stand-alone module
         // try to read from local file
         File file = new File (FujabaApp.FUJABA_BASE.resolve (filename));
         setModifiedTag (file.lastModified());

         in = new BufferedReader (new FileReader (file));
      }
      catch (FileNotFoundException e)
      {
         in = openUsingClassLoader();
      }
      catch (IllegalArgumentException e)
      {
         // the URI given to the File constructor is illegal
         in = openUsingClassLoader();
      }

      return in;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return              No description provided
    * @throws IOException  Exception description not provided
    */
   protected BufferedReader openUsingClassLoader() throws IOException
   {
      // reading from file does not work, look into the class path
      URL url = TemplateFile.class.getResource ("/" + getFilename());
      if (url == null)
      {
         throw new FileNotFoundException (getFilename());
      }
      return new BufferedReader (new InputStreamReader (url.openStream()));
   }


   /**
    * Parse should only be called by the TemplateManager (once, or after alteration of the
    * file)
    */
   protected void parse()
   {
      this.removeAllFromCodeBlocks();

      try
      {
         String str;
         BufferedReader in = open();

         while ( (str = in.readLine()) != null)
         {
            processLine (str, in);
         }

         in.close();
      }
      catch (IOException e)
      {
         log.info ("Failed loading assoc template file! Code generation for assocs disabled!");
         e.printStackTrace();
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param line  No description provided
    * @param in    No description provided
    */
   protected void processLine (String line, BufferedReader in)
   {
      String str = line.trim();

      if ( (str != null) && !str.equals (""))
      {
         if (str.charAt (0) == '#')
         {
            if (str.startsWith ("BeginCodeBlock", 1))
            {
               String codeBlockName = this.retrieveCodeBlockName (str);
               if ( (codeBlockName != null) && !codeBlockName.equals (""))
               {
                  TemplateCodeBlock block = new TemplateCodeBlock (codeBlockName);
                  block.parse (in);
                  this.addToCodeBlocks (block);
               }
               else
               {
                  log.info ("Error in template: No name for code block.");
               }
            }
         }
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param line  No description provided
    * @return      No description provided
    */
   protected String retrieveCodeBlockName (String line)
   {
      String codeBlockName = null;

      int index = line.indexOf ('=');
      if ( (index > -1) &&  (index < line.length()))
      {
         codeBlockName = line.substring (index + 1, line.length());
         codeBlockName = codeBlockName.trim();
      }

      return codeBlockName;
   }
}

/*
 * $Log$
 * Revision 1.23  2006/05/02 08:31:56  lowende
 * Unused imports removed.
 *
 */

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
 *      Copyright (C) 1997-2004 Fujaba Development Group
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
 * Contact adress:
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

import de.upb.tools.fca.*;


/**
 * <h2>Associations</h2> <pre>
 *              +----------+ 1      blocks       1
 * TemplateFile | name     +----------------------- TemplateCodeBlock
 *              +----------+ template   codeBlocks
 *
 *                    1       tokens {ordered}  n
 * TemplateCodeBlock ----------------------------- TemplateToken
 *                    codeBlock            tokens
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class TemplateCodeBlock
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (TemplateCodeBlock.class);


   /**
    * Constructor for class TemplateCodeBlock
    *
    * @param name  No description provided
    */
   public TemplateCodeBlock (String name)
   {
      this.setName (name);
   }

   // Attributes
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name = "";


   /**
    * Sets the name attribute of the TemplateCodeBlock object
    *
    * @param name  The new name value
    */
   public void setName (String name)
   {
      if ( (this.name == null) || !this.name.equals (name))
      {
         this.name = name;
      }
   }


   /**
    * Get the name attribute of the TemplateCodeBlock object
    *
    * @return   The name value
    */
   public String getName()
   {
      return this.name;
   }


   // Assocs

   /**
    * <pre>
    *              +----------+ 1      blocks       1
    * TemplateFile | name     +----------------------- TemplateCodeBlock
    *              +----------+ template   codeBlocks
    * </pre>
    */
   private TemplateFile template;


   /**
    * Sets the template attribute of the TemplateCodeBlock object
    *
    * @param obj  The new template value
    * @return     No description provided
    */
   public boolean setTemplate (TemplateFile obj)
   {
      boolean changed = false;

      if (this.template != obj)
      {
         if (this.template != null)
         {
            TemplateFile oldValue = this.template;
            this.template = null;
            oldValue.removeFromCodeBlocks (this);
         }
         this.template = obj;
         if (obj != null)
         {
            obj.addToCodeBlocks (this);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * Get the template attribute of the TemplateCodeBlock object
    *
    * @return   The template value
    */
   public TemplateFile getTemplate()
   {
      return this.template;
   }


   /**
    * <pre>
    *                    1       tokens {ordered}  n
    * TemplateCodeBlock ----------------------------- TemplateToken
    *                    codeBlock            tokens
    * </pre>
    */
   private FLinkedList tokens;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public boolean hasInTokens (TemplateToken obj)
   {
      return  ( (this.tokens != null) &&
          (obj != null) &&
         this.tokens.contains (obj));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfTokens()
   {
      return  ( (this.tokens == null)
         ? FEmptyIterator.get()
         : this.tokens.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfTokens()
   {
      return  ( (this.tokens == null)
         ? 0
         : this.tokens.size());
   }


   /**
    * Access method for an one to n association.
    *
    * @param obj  The object added.
    * @return     No description provided
    */
   public boolean addToTokens (TemplateToken obj)
   {
      boolean changed = false;

      if ( (obj != null) && !hasInTokens (obj))
      {
         if (this.tokens == null)
         {
            this.tokens = new FLinkedList();
         }
         tokens.add (obj);
         obj.setCodeBlock (this);
         changed = true;
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public boolean removeFromTokens (TemplateToken obj)
   {
      boolean changed = false;

      if ( (this.tokens != null) &&  (obj != null))
      {
         changed = this.tokens.remove (obj);
         obj.setCodeBlock (null);
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromTokens()
   {
      TemplateToken tmpObj;
      Iterator iter = this.iteratorOfTokens();

      while (iter.hasNext())
      {
         tmpObj = (TemplateToken) iter.next();
         this.removeFromTokens (tmpObj);
      }
      tokens = null;
   }


   // Methods

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param in  No description provided
    */
   protected void parse (BufferedReader in)
   {
      boolean endBlockReached = false;
      String line;

      try
      {
         while ( ( (line = in.readLine()) != null) && !endBlockReached)
         {
            String str = line.trim();
            if ( (str != null) && !str.equals (""))
            {
               if (str.charAt (0) == '#')
               {
                  if (str.startsWith ("EndCodeBlock", 1))
                  {
                     endBlockReached = true;
                  }
               }
            }

            if (!endBlockReached)
            {
               processLine (line, in);
            }
         }
      }
      catch (IOException e)
      {
         log.error ("Error during parsing a template block.");
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
      int startCodeIndex = 0;
      int endCodeIndex = line.length();
      int indexBeginPH = line.indexOf ('$', startCodeIndex);
      int indexEndPH = line.indexOf ('$', indexBeginPH + 1);
      boolean placeholderFound = false;

      while (indexBeginPH > -1)
      {
         placeholderFound = true;
         String code = line.substring (startCodeIndex, indexBeginPH);
         this.addCodeToken (code);

         String placeholder = line.substring (indexBeginPH, indexEndPH + 1);
         this.addPlaceholderToken (placeholder);

         startCodeIndex = indexEndPH + 1;
         indexBeginPH = line.indexOf ('$', startCodeIndex);
         indexEndPH = line.indexOf ('$', indexBeginPH + 1);
      }

      if (startCodeIndex < endCodeIndex)
      {
         String code = line.substring (startCodeIndex, endCodeIndex) + "\n";
         this.addCodeToken (code);
      }
      else if (placeholderFound)
      {
         String code = "\n";
         this.addCodeToken (code);
      }
   }


   /**
    * Access method for an one to n association.
    *
    * @param code  The object added.
    */
   protected void addCodeToken (String code)
   {
      SourceCodeToken codeToken = new SourceCodeToken (code);
      this.addToTokens (codeToken);
   }


   /**
    * Access method for an one to n association.
    *
    * @param placeholder  The object added.
    */
   protected void addPlaceholderToken (String placeholder)
   {
      PlaceHolderToken placeholderToken = new PlaceHolderToken (placeholder);
      this.addToTokens (placeholderToken);
   }


   /**
    * Get the sourceCode attribute of the TemplateCodeBlock object
    *
    * @param parameter  No description provided
    * @return           The sourceCode value
    */
   public String getSourceCode (FHashMap parameter)
   {
      StringBuffer codeBuffer = new StringBuffer();
      Iterator iter = iteratorOfTokens();
      while (iter.hasNext())
      {
         Object next = iter.next();
         if (next instanceof TemplateToken)
         {
            TemplateToken token = (TemplateToken) next;
            codeBuffer.append (token.getSourceCode (parameter));
         }
         else
         {
            System.out.println ("Token type: " +  (next == null ? "null" : next.getClass().getName()));
            System.out.println ("Token text: " + next);
         }
      }

      return codeBuffer.toString();
   }


   /**
    * Get the sourceCodeLines attribute of the TemplateCodeBlock object
    *
    * @param parameter  No description provided
    * @return           The sourceCodeLines value
    */
   public Iterator getSourceCodeLines (FHashMap parameter)
   {
      String lineSeparator = System.getProperty ("line.separator");
      ArrayList list = new ArrayList();
      StringBuffer codeBuffer = new StringBuffer();

      Iterator iter = iteratorOfTokens();
      while (iter.hasNext())
      {
         TemplateToken token = (TemplateToken) iter.next();
         String sourceCode = token.getSourceCode (parameter);
         codeBuffer.append (sourceCode);

         if (sourceCode.endsWith (lineSeparator))
         {
            list.add (codeBuffer);
            codeBuffer = new StringBuffer();
         }
      }

      if (codeBuffer.length() != 0)
      {
         list.add (codeBuffer.toString());
      }

      return list.iterator();
   }

}

/*
 * $Log$
 * Revision 1.14  2004/10/20 17:49:29  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

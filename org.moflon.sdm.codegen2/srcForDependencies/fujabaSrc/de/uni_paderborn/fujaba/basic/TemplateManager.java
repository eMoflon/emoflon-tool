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

import java.util.Iterator;

import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap;


/**
 * <h2>Associations</h2> <pre>
 *                 +----------+ 1        file        1
 * TemplateManager | filename +------------------------ TemplateFile
 *                 +----------+ manager      templates
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class TemplateManager
{
   // Singleton implementation
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static TemplateManager manager;


   /**
    * To allow a plugin to replace the default TemplateManager <br>
    * Attention: When several plugin use the machanism the behaviour might depend on the loading
    * order (when plugins are written unsafe)
    *
    * @param manager  the plugin specific TemplateManager
    */
   public static void set (TemplateManager manager)
   {
      TemplateManager.manager = manager;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public static TemplateManager get()
   {
      if (manager == null)
      {
         manager = new TemplateManager();
      }
      return  (manager);
   }

   // Assocs

   /**
    * <pre>
    *                 +----------+ 1        file        1
    * TemplateManager | filename +------------------------ TemplateFile
    *                 +----------+ manager      templates
    * </pre>
    */

   private FHashMap templates;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public boolean hasInTemplates (TemplateFile obj)
   {
      return  ( (this.templates != null) &&
          (obj != null) &&  (obj.getFilename() != null) &&
          (this.templates.get (obj.getFilename()) == obj));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public boolean hasKeyInTemplates (String key)
   {
      return  ( (this.templates != null) &&
          (key != null) &&
         this.templates.containsKey (key));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfTemplates()
   {
      return  ( (this.templates == null)
         ? FEmptyIterator.get()
         : this.templates.values().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator keysOfTemplates()
   {
      return  ( (this.templates == null)
         ? FEmptyIterator.get()
         : this.templates.keySet().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator entriesOfTemplates()
   {
      return  ( (this.templates == null)
         ? FEmptyIterator.get()
         : this.templates.entrySet().iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfTemplates()
   {
      return  ( (this.templates == null)
         ? 0
         : this.templates.size());
   }


   /**
    * Get the fromTemplates attribute of the TemplateManager object
    *
    * @param key  No description provided
    * @return     The fromTemplates value
    */
   public TemplateFile getFromTemplates (String key)
   {
      return  ( ( (this.templates == null) ||  (key == null))
         ? null
         : (TemplateFile) this.templates.get (key));
   }


   /**
    * Access method for an one to n association.
    *
    * @param obj  The object added.
    * @return     No description provided
    */
   public boolean addToTemplates (TemplateFile obj)
   {
      boolean changed = false;

      if ( (obj != null) &&  (obj.getFilename() != null))
      {
         if (this.templates == null)
         {
            this.templates = new FHashMap();
         }
         TemplateFile oldValue = (TemplateFile) this.templates.put (obj.getFilename(), obj);
         if (oldValue != obj)
         {
            if (oldValue != null)
            {
               oldValue.setManager (null);
            }
            obj.setManager (this);
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
   public boolean removeFromTemplates (TemplateFile obj)
   {
      boolean changed = false;

      if ( (this.templates != null) &&  (obj != null) &&  (obj.getFilename() != null))
      {
         TemplateFile oldValue = (TemplateFile) this.templates.get (obj.getFilename());
         if (oldValue == obj)
         {
            this.templates.remove (obj.getFilename());
            obj.setManager (null);
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
   public boolean removeKeyFromTemplates (String key)
   {
      boolean changed = false;

      if ( (this.templates != null) &&  (key != null))
      {
         TemplateFile tmpObj = (TemplateFile) this.templates.get (key);
         if (tmpObj != null)
         {
            this.templates.remove (key);
            tmpObj.setManager (null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromTemplates()
   {
      TemplateFile tmpObj;
      Iterator iter = this.iteratorOfTemplates();

      while (iter.hasNext())
      {
         tmpObj = (TemplateFile) iter.next();
         this.removeFromTemplates (tmpObj);
      }
   }


   // Methods

   /**
    * Get the template attribute of the TemplateManager object
    *
    * @param filename  No description provided
    * @return          The template value
    */
   public TemplateFile getTemplate (String filename)
   {
      TemplateFile template;

      if (hasKeyInTemplates (filename))
      {
         template = getFromTemplates (filename);

         if (template.isModified())
         {
            template.parse();
         }
      }
      else
      {
         template = new TemplateFile (filename);
         template.parse();
         this.addToTemplates (template);
      }

      return template;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void resetTemplates()
   {
      removeAllFromTemplates();

      if (FujabaDebug.SETATTRSTONULL)
      {
         templates = null;
      }
   }

}

/*
 * $Log$
 * Revision 1.14  2004/10/20 17:49:29  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

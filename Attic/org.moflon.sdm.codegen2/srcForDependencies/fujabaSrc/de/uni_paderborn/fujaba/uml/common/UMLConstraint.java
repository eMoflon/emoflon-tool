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
package de.uni_paderborn.fujaba.uml.common;

import java.util.Iterator;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *                0..n     constraints     0..n
 * UMLConstraint ------------------------------- UMLIncrement
 *                constraints    revConstraints
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLConstraint extends UMLDiagramItem implements FConstraint, FParsedElement
{
   /**
    * Constructor for class UMLConstraint
    *
    * @param project
    * @param persistent
    */
   protected UMLConstraint (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String text;


   /**
    * Get the text attribute of the UMLConstraint object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      return text;
   }


   /**
    * Sets the text attribute of the UMLConstraint object
    *
    * @param text  The new text value
    */
   public void setText (String text)
   {
      String oldValue = this.text;
      if (text != null)
      {
         this.text = text.trim();
      }
      else
      {
         this.text = null;
      }
      firePropertyChange (TEXT_PROPERTY, oldValue, this.text);
   }


   /**
    * <pre>
    *                0..n     constraints     0..n
    * UMLConstraint ------------------------------- UMLIncrement
    *                constraints        increments
    * </pre>
    */
   private FPropHashSet increments;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInIncrements (FIncrement value)
   {
      return  ( (this.increments != null) &&
          (value != null) &&
         this.increments.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfIncrements()
   {
      return  ( (this.increments == null)
         ? FEmptyIterator.get()
         : this.increments.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfIncrements()
   {
      return  ( (this.increments == null)
         ? 0
         : this.increments.size());
   }


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToIncrements (FIncrement value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.increments == null)
         {
            this.increments = new FPropHashSet (this, INCREMENTS_PROPERTY);
         }
         changed = this.increments.add (value);
         if (changed)
         {
            value.addToConstraints (this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromIncrements (FIncrement value)
   {
      boolean changed = false;
      if ( (this.increments != null) &&  (value != null))
      {
         changed = this.increments.remove (value);
         if (changed)
         {
            value.removeFromConstraints (this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromIncrements()
   {
      UMLIncrement tmpValue;
      Iterator iter = this.iteratorOfIncrements();
      while (iter.hasNext())
      {
         tmpValue = (UMLIncrement) iter.next();
         this.removeFromIncrements (tmpValue);
      }
   }


   /**
    * <pre>
    *                0..n     constraints     0..n
    * UMLConstraint ------------------------------- UMLDiagram
    *                constraints    revConstraints
    * </pre>
    */
   private FPropHashSet revConstraint;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInRevConstraint (FDiagram value)
   {
      return  ( (this.revConstraint != null) &&
          (value != null) &&
         this.revConstraint.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfRevConstraint()
   {
      return  ( (this.revConstraint == null)
         ? FEmptyIterator.get()
         : this.revConstraint.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevConstraint()
   {
      return  ( (this.revConstraint == null)
         ? 0
         : this.revConstraint.size());
   }


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToRevConstraint (FDiagram value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revConstraint == null)
         {
            this.revConstraint = new FPropHashSet (this, REV_CONSTRAINT_PROPERTY);
         }
         changed = this.revConstraint.add (value);
         if (changed)
         {
             ((UMLDiagram) value).addToConstraints (this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromRevConstraint (FDiagram value)
   {
      boolean changed = false;
      if ( (this.revConstraint != null) &&  (value != null))
      {
         changed = this.revConstraint.remove (value);
         if (changed)
         {
             ((UMLDiagram) value).removeFromConstraints (this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevConstraint()
   {
      UMLDiagram tmpValue;
      Iterator iter = this.iteratorOfRevConstraint();
      while (iter.hasNext())
      {
         tmpValue = (UMLDiagram) iter.next();
         this.removeFromRevConstraint (tmpValue);
      }
   }

   @Override
   public FDiagram getParentElement ()
   {
      if (sizeOfRevConstraint() == 1 && sizeOfDiagrams()==0)
      {
         return (FDiagram) iteratorOfRevConstraint().next();
      }
      if (sizeOfDiagrams() == 1 && sizeOfRevConstraint()==0)
      {
         return getFirstFromDiagrams();
      }
      if (sizeOfDiagrams() == 1 && sizeOfRevConstraint()==1)
      {
         FDiagram revConstraint = (FDiagram) iteratorOfRevConstraint().next();
         FDiagram diag = getFirstFromDiagrams();
         if (diag == revConstraint)
         {
            return diag;
         }
      }
      throw new UnsupportedOperationException(
         getClass()
         + " does not support to query the parent element if present in multiple/no diagrams!");
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Override
   public String toString()
   {
      return getText();
   } // toString


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      removeAllFromIncrements();

      removeAllFromRevConstraint();
      
      super.removeYou();
   } // removeYou

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "text";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getText();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setText(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'text' field for the type UMLConstraint.
    */
   private TextNode parsetree;
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsetree()
    */
   public TextNode getParsetree()
   {
      return this.parsetree;
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsetree(de.fujaba.text.nodes.UMLTextNode)
    */
   public void setParsetree(TextNode node)
   {
      this.parsetree = node;
   }

}

/*
 * $Log$
 * Revision 1.8  2007/03/22 13:59:49  creckord
 * - moved code to read/guess settings directory to PreferencesProperties
 * - project workspace is created with mkdirs instead of mkdir
 * - tests run with temporary settings directory using just default settings
 *
 * Revision 1.7  2006/06/21 10:35:54  koenigs
 * Renamed removeAllIncrements and removeAllRevConstraints to removeAllFromIncrements and removeAllFromRevConstraint since Fujaba would have generated them this way. [ak]
 *
 * Revision 1.6  2006/04/06 12:06:01  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.5  2006/03/29 09:51:13  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.4  2006/03/01 12:22:51  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

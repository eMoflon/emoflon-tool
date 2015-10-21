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
package de.uni_paderborn.fujaba.uml.behavior;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStatement extends UMLDiagramItem implements FParsedElement
{
   public static final String ACTIVITY_PROPERTY = "activity";


   /**
    * Constructor for class UMLStatement
    *
    * @param project
    * @param persistent
    */
   protected UMLStatement (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String statement;


   /**
    * Get the statement attribute of the UMLStatement object
    *
    * @return   The statement value
    */
   public String getStatement()
   {
      return statement;
   }


   /**
    * Sets the statement attribute of the UMLStatement object
    *
    * @param statement  The new statement value
    */
   public void setStatement (String statement)
   {
      // change only, if necessary
      if (! (statement != null && statement.equals (this.statement)))
      {
         //avoid an empty statement, because this looks ugly in diagrams
         if (statement == null || statement.trim().length() == 0)
         {
            statement = "//Statement";
         }

         String oldValue = this.statement;
         this.statement = statement;
         firePropertyChange ("statement", oldValue, statement);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param statement  No description provided
    */
   public void append (String statement)
   {
      if (this.statement == null)
      {
         setStatement (statement);
      }
      else
      {
         setStatement (this.statement + statement);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=ACTIVITY_PROPERTY, partner=UMLStatementActivity.STATE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private UMLStatementActivity activity;


   /**
    * Get the activity attribute of the UMLStatement object
    *
    * @return   The activity value
    */
   public UMLStatementActivity getActivity()
   {
      return activity;
   }


   /**
    * Sets the activity attribute of the UMLStatement object
    *
    * @param activity  The new activity value
    */
   public void setActivity (UMLStatementActivity activity)
   {
      if (this.activity != activity)
      {
         // new partner
         UMLStatementActivity oldActivity = this.activity;
         if (this.activity != null)
         {
            // inform old partner
            this.activity = null;
            oldActivity.setState (null);
         }
         this.activity = activity;
         if (activity != null)
         {
            // inform new partner
            activity.setState (this);
         }
         firePropertyChange (ACTIVITY_PROPERTY, oldActivity, activity);
      }
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      setActivity (null);

      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      return getActivity();
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "statement";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getStatement();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setStatement(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'statement' field for the type UMLStatement.
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
 * Revision 1.5  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.4  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.3  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

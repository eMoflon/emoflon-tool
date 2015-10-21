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
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLTransitionGuard extends UMLDiagramItem implements FParsedElement
{
   public static final String REV_GUARD_PROPERTY = "revGuard";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int NONE = 0;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int SUCCESS = 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int FAILURE = 2;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int EVERYTIMES = 3; // FIXME: rename it to EACH_TIME

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int TERMINATION = 4;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int ELSE = 5;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int BOOL = 6;

   /**
    * Guard type for an 'exception' transition. If this type is assigned to a transition's guard,
    * the activity that this transition is directed towards becomes a 'catch' block in
    * the source code. Not supported by the old sequencer!
    *
    * @see de.uni_paderborn.fujaba.gui.PEActTransition#GuardException
    */
   public static final int EXCEPTION = 7;

   /**
    * Guard type for a 'finally' transition. If this type is assigned to a transition's guard,
    * the activity that this transition is directed towards becomes a 'finally' block in
    * the source code. Not supported by the old sequencer!
    *
    * @see de.uni_paderborn.fujaba.gui.PEActTransition#GuardFinally
    */
   public static final int FINALLY = 8;

   public static int getGuardType (UMLTransition transition)
   {
      final UMLTransitionGuard guard = transition.getGuard();
      return  (guard == null ? NONE : guard.getType());
   }


   /**
    * Constructor for class UMLTransitionGuard
    *
    * @param project
    * @param persistent
    */
   protected UMLTransitionGuard (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int type = NONE;


   /**
    * Get the type attribute of the UMLTransitionGuard object
    *
    * @return   The type value
    */
   public int getType()
   {
      return type;
   }


   /**
    * Sets the type attribute of the UMLTransitionGuard object
    *
    * @param type  The new type value
    */
   public void setType (int type)
   {
      if ( (NONE <= type && type <= FINALLY) &&
          (this.type != type))
      {
         int oldValue = this.type;
         this.type = type;
         firePropertyChange ("type", oldValue, type);
         setBoolExpr(getBoolExprString(type, getBoolExpr()));
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String boolExpr = null;


   /**
    * Get the boolExpr attribute of the UMLTransitionGuard object
    *
    * @param type      No description provided
    * @param boolExpr
    * @return          The boolExpr value
    */
   private static String getBoolExprString (int type, String boolExpr)
   {
      String expr;
      switch (type)
      {
         case NONE:
            expr = null;
            break;
         case SUCCESS:
            expr = "success";
            break;
         case FAILURE:
            expr = "failure";
            break;
         case EVERYTIMES:
            expr = "each time";
            break;
         case TERMINATION:
            expr = "end";
            break;
         case ELSE:
            expr = "else";
            break;
         case FINALLY:
            expr = "finally";
            break;
         default:
            expr = boolExpr;
            break;
      }
      return expr;
   }

   public void setBoolExprString (String expr)
   {
   	if (expr == null || "".equals(expr.trim()))
   	{
   		this.setType(UMLTransitionGuard.NONE);
   	}
   	else if (expr.equals("success"))
   	{
   		this.setType(UMLTransitionGuard.SUCCESS);
   	}
   	else if (expr.equals("failure"))
   	{
   		this.setType(UMLTransitionGuard.FAILURE);
   	}
   	else if (expr.equals("each time"))
   	{
   		this.setType(UMLTransitionGuard.EVERYTIMES);
   	}
   	else if (expr.equals("end"))
   	{
   		this.setType(UMLTransitionGuard.TERMINATION);
   	}
   	else if (expr.equals("else"))
   	{
   		this.setType(UMLTransitionGuard.ELSE);
   	}
   	else if (expr.equals("finally"))
   	{
   	    this.setType(UMLTransitionGuard.FINALLY);
   	}
   	else
   	{
   		this.setType(UMLTransitionGuard.BOOL);
   		this.setBoolExpr(expr);
   	}
   }
   
   /**
    * Get the boolExpr attribute of the UMLTransitionGuard object
    *
    * @return   The boolExpr value
    */
   public String getBoolExpr()
   {
      return getBoolExprString (getType(), this.boolExpr);
   }

   /**
    * Sets the boolExpr attribute of the UMLTransitionGuard object
    *
    * @param boolExpr  The new boolExpr value
    */
   public void setBoolExpr (String boolExpr)
   {
      if ( (this.boolExpr == null && boolExpr != null) ||
          (this.boolExpr != null && !this.boolExpr.equals (boolExpr)))
      {
         String oldValue = this.boolExpr;
         this.boolExpr = boolExpr;
         firePropertyChange ("boolExpr", oldValue, boolExpr);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=REV_GUARD_PROPERTY, partner=UMLTransition.GUARD_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private UMLTransition revGuard = null; // reverse UMLTransiton guard


   /**
    * Get the revGuard attribute of the UMLTransitionGuard object
    *
    * @return   The revGuard value
    */
   public UMLTransition getRevGuard()
   {
      return revGuard;
   }


   /**
    * Sets the revGuard attribute of the UMLTransitionGuard object
    *
    * @param revGuard  The new revGuard value
    */
   public void setRevGuard (UMLTransition revGuard)
   {
      if ( (this.revGuard == null && revGuard != null) ||
          (this.revGuard != null && !this.revGuard.equals (revGuard)))
      { // new partner

         UMLTransition oldRevGuard = this.revGuard;
         if (this.revGuard != null)
         { // revInform old partner

            this.revGuard = null;
            oldRevGuard.setGuard (null);
         }
         this.revGuard = revGuard;
         if (revGuard != null)
         { // revInform new partner

            revGuard.setGuard (this);
         }
         firePropertyChange (REV_GUARD_PROPERTY, oldRevGuard, revGuard);
      }
   }


   /**
    * Same functionality as equals() there are maybe problems with jgl sets work if this method
    * is called equals()
    *
    * @param curTransitionGuard  No description provided
    * @return                    No description provided
    */
   public boolean looksLike (UMLTransitionGuard curTransitionGuard)
   {
      // only type is compared, boolean expresions may differ
      return  (this.getType() == curTransitionGuard.getType());
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      setRevGuard (null);

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
      return getRevGuard();
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "boolExpr";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getBoolExpr();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setBoolExpr(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. 'the boolExpr' field for the type UMLTransitionGuard.
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
 * Revision 1.11  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.10  2006/08/22 22:51:34  zuendorf
 * Added FMM.create(proj, class) to shorten the creation of metamodel objects.
 * Linkeditor selects objects in correct order now.
 * Added some flags guiding the code generation for Story Patterns and objects.
 * Added some convinience to UMLTransition.
 *
 * Revision 1.9  2006/06/07 12:21:19  creckord
 * - UMLTransitionGuard can be null instead of UMLTransitionGuard.NONE
 * - old "repair assoc" code removed (access methods are not kept, so repairing them is no longer needed)
 * - loop bends for assocs should be removed when loop is removed (not triggered yet :( )
 *
 * Revision 1.8  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.7  2006/04/04 15:57:24  rotschke
 * Rolled back unintended check-in [tr].
 *
 * Revision 1.5  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.4  2006/03/24 13:04:15  l3_g5
 * bugfix for ant task; added exceptions in story diags
 *
 * Revision 1.3  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

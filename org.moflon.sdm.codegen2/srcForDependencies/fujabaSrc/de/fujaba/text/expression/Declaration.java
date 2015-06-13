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
package de.fujaba.text.expression;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.FTextReference;
import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;

/**
 * Text node representation of a variable declaration.
 * 
 * note: the expression is put at the beginning of the
 * list of children in order to get it computed before
 * the declaration itself. This way, the types of the
 * expression and the declared variables can be compared.
 * 
 * @author Patrick Oppermann, patrick.oppermann@cs.uni-kassel.de
 */
public class Declaration extends TextNode implements FTextReference
{

   /**
    * Default constructor for class Declaration with mandatory parsed element.
    * 
    * @param parsedElement
    */
   public Declaration(FParsedElement parsedElement)
   {
      super(parsedElement);
      this.textRefUtil = new FTextReferenceUtility(this);
   }


   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Some convenient access methods.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   /**
    * Get a list of the identifier names declared by this declaration.
    * 
    * @return a non-null list of Strings
    * @throws RuntimeException
    */
   public List<String> getIdentifierNames()
   {
      final LinkedList<String> names = new LinkedList<String>();

      if(this.targets != null && !this.targets.isEmpty())
      {
         // add the names of all the declaration's
         // assign targets to the list
         for(final TextNode tn : this.targets)
         {
            if(tn instanceof Identifier)
            {
               final String name = ((Identifier) tn).getName();
               names.add(name);
            }
            else if(tn instanceof Assignment)
            {
               final TextNode target = ((Assignment) tn).getAssignTarget();
               if(target instanceof Identifier)
               {
                  final String name = ((Identifier) target).getName();
                  names.add(name);
               }
               else
               {
                  throw new RuntimeException("Declaration must only have identifiers as assign targets.");
               }
            }
         }
      }

      if(names.isEmpty())
      {
         throw new RuntimeException("Names of the target identifier nodes could not be obtained.");
      }

      return names;
   }


   /**
    * Get a list of the Identifier TextNodes that are declared by this declaration.
    * 
    * @return a non-null list of TextNode instances which can be either Identifier or Assignment
    * @throws RuntimeException
    */
   public List<Identifier> getIdentifiers()
   {
      final LinkedList<Identifier> identifiers = new LinkedList<Identifier>();

      if(this.targets != null && !this.targets.isEmpty())
      {
         // add the names of all the declaration's
         // assign targets to the list
         for(final TextNode tn : this.targets)
         {
            if(tn instanceof Identifier)
            {
               identifiers.add((Identifier) tn);
            }
            else if(tn instanceof Assignment)
            {
               final TextNode target = ((Assignment) tn).getAssignTarget();
               if(target instanceof Identifier)
               {
                  identifiers.add((Identifier) target);
               }
               else
               {
                  throw new RuntimeException("Declaration must only have identifiers as assign targets.");
               }
            }
         }
      }

      if(identifiers.isEmpty())
      {
         throw new RuntimeException("Assign target identifier nodes could not be obtained.");
      }

      return identifiers;
   }


   /**
    * Get the name of this declaration's first assign target.
    * 
    * @return a non-null, non-empty String
    * @throws RuntimeException
    */
   public String getFirstAssignTargetName()
   {
      String name = null;

      if(this.targets != null && !this.targets.isEmpty())
      {
         final TextNode firstTarget = this.targets.get(0);

         if(firstTarget == null)
         {
            throw new RuntimeException();
         }

         if(firstTarget instanceof Identifier)
         {
            name = ((Identifier) firstTarget).getName();
         }
         else if(firstTarget instanceof Assignment)
         {
            final TextNode t = ((Assignment) firstTarget).getAssignTarget();

            if(t == null)
            {
               throw new RuntimeException("Assignment has no target.");
            }

            if(t instanceof Identifier)
            {
               name = ((Identifier) t).getName();
            }
         }
         else
         {
            throw new RuntimeException("Declaration contains unsupported assign target."
                  + " An assign target must be of type \"Identifier\" or \"Assignment\"");
         }
      }

      if(name == null || name.length() == 0)
      {
         throw new RuntimeException("Assign target of declaration could not be obtained.");
      }

      return name;
   }


   /**
    * Indicates if this declaration is tagged as "final".
    */
   private Boolean isFinal = Boolean.FALSE;


   /**
    * Returns true if this declaration is marked as "final".
    */
   public Boolean isFinal()
   {
      return this.isFinal;
   }


   /**
    * Set a new value for the "final" tag of this declaration.
    * 
    * @param value the new value
    */
   public void setFinal(final Boolean value)
   {
      if(value == null)
      {
         throw new IllegalArgumentException("Parameter \"isFinal\" must not be null.");
      }

      if(this.isFinal != value)
      {
         this.isFinal = value;
      }
   }


   /**
    * Representation of the type of the declared variables.
    */
   private TypeExpression typeExpression;


   /**
    * Get the subtree representing the declared variables' type.
    * @return the type expression syntax tree.
    */
   public TypeExpression getTypeExpression()
   {
      return typeExpression;
   }


   /**
    * Set the subtree representing the declared variables' type.
    * 
    * @param value the new value for the type expression.
    * @return true if the field was changed.
    */
   public boolean setTypeExpression(TypeExpression value)
   {
      boolean changed = false;

      if(this.typeExpression != value)
      {
         TextNode oldValue = this.typeExpression;
         this.typeExpression = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }


   /**
    * The variable / assignments (variable-value pairs)
    * declared / initialized by this declaration.
    */
   private List<TextNode> targets;


   /**
    * 
    * @param value
    */
   public void addToTargets(TextNode value)
   {
      if(value == null)
      {
         return;
      }

      if(!(value instanceof Identifier) && !(value instanceof Assignment))
      {
         throw new IllegalArgumentException("Declaration targets must be of type \"Identifier\" or \"Assignment\".");
      }

      if(targets == null)
      {
         targets = new LinkedList<TextNode>();
      }
      targets.add(value);

      // sync children link
      this.addToChildren(value);
   }


   /**
    * 
    * @return 
    */
   public Iterator<TextNode> iteratorOfTargets()
   {
      if(targets == null)
      {
         targets = new LinkedList<TextNode>();
      }
      return targets.iterator();
   }


   /**
    * Implementation of the 'referencedElement' association between FTextReference and
    * UMLTextNode. Since this association must be implemented by numerous classes, all
    * operations are forwarded to the FTextReferenceUtility class.
    * 
    * <pre>
    *           0..1     referencedElement     0..n
    * FTextReference ------------------------- UMLTextNode
    *      referencedElement               textUsages
    * </pre>
    * 
    * @see de.fujaba.text.FTextReference
    * @see de.fujaba.text.FTextReferenceUtility
    */
   private FTextReferenceUtility textRefUtil;


   /**
    * Accessor for textRefUtil field featuring lazy instantiation.
    * 
    * @return the FTextReferenceUtility instance for this instance.
    */
   private FTextReferenceUtility getTextRefUtil()
   {
      if(this.textRefUtil == null)
      {
         this.textRefUtil = new FTextReferenceUtility(this);
      }
      return this.textRefUtil;
   }


   /**
    * Adds the given UMLTextNode instance to the set of
    * text usages. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean addToTextUsages(TextNode value)
   {
      return getTextRefUtil().addToTextUsages(value);
   }


   /**
    * Determines if this instance's set of text usages contains
    * the given UMLTextNode instance. (Forwarded to FTextReferenceUtility)
    */
   public boolean hasInTextUsages(TextNode value)
   {
      return getTextRefUtil().hasInTextUsages(value);
   }


   /**
    * @return an Iterator of this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public Iterator<TextNode> iteratorOfTextUsages()
   {
      return getTextRefUtil().iteratorOfTextUsages();
   }


   /**
    * Removes all elements from this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public void removeAllFromTextUsages()
   {
      getTextRefUtil().removeAllFromTextUsages();
   }


   /**
    * Removes the given element from this instance's set of text usages
    * if it is in there. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean removeFromTextUsages(TextNode value)
   {
      return getTextRefUtil().removeFromTextUsages(value);
   }


   /**
    * @return the number of UMLTextNode instances in this instance's set
    * of text usages. (Forwarded to FTextReferenceUtility)
    */
   public int sizeOfTextUsages()
   {
      return getTextRefUtil().sizeOfTextUsages();
   }


   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * removeYou() implementation that fires an event to indicate that the
    * removal of this instance is about to begin.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   @Override
   public void removeYou()
   {
      this.getPropertyChangeSupport().firePropertyChange(TextNode.REMOVE_YOU_STARTED, this, this);

      if(this.getPropertyChangeSupport() != null)
      {
         firePropertyChange(TextNode.REMOVE_YOU_STARTED, this, this);
      }

      if(this.getTypeExpression() != null)
      {
         this.getTypeExpression().removeYou();
         this.setTypeExpression(null);
      }

      for(Iterator<TextNode> iter = this.iteratorOfTargets(); iter.hasNext();)
      {
         iter.next().removeYou();
      }
      this.targets.clear();
      this.targets = null;

      if(this.getTextRefUtil() != null)
      {
         this.getTextRefUtil().removeAllFromTextUsages();
         this.textRefUtil = null;
      }

      super.removeYou();
   }


   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Visitable implementation.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   public void accept(NoArgVoidVisitor v)
   {
      v.visit(this);
   }


   public <R, A> R accept(ArgVisitor<R, A> v, A argu)
   {
      return v.visit(this, argu);
   }


   public <R> R accept(NoArgVisitor<R> v)
   {
      return v.visit(this);
   }


   public <A> void accept(ArgVoidVisitor<A> v, A argu)
   {
      v.visit(this, argu);
   }

}

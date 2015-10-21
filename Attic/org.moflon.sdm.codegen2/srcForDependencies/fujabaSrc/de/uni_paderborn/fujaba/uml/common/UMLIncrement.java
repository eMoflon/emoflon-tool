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
import java.util.Map;

import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FCommentary;
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.common.FTag;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashMap;
import de.upb.tools.fca.FPropHashSet;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *                0..n     constraints     0..n
 * UMLConstraint ------------------------------- UMLIncrement
 *                constraints        increments
 * 
 *              +-----------+ 0..n                      1
 * UMLIncrement | getName() |----------------------------- UMLStereotype
 *              +-----------+ increments      stereotypes
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLIncrement extends ASGElement implements
      FIncrement
{

   protected UMLIncrement(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   protected UMLIncrement(FProject project, boolean persistent,
         String factoryKey)
   {
      super(project, persistent, factoryKey);
   }

   /**
    * <pre>
    *                0..n     constraints     0..n
    * UMLConstraint ------------------------------- UMLIncrement
    *                constraints        increments
    * </pre>
    */
   private FPropHashSet<FConstraint> constraints;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#addToConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean addToConstraints(FConstraint value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.constraints == null)
         {
            this.constraints = new FPropHashSet<FConstraint>(this, CONSTRAINTS_PROPERTY);
         }
         changed = this.constraints.add(value);
         if (changed)
         {
            value.addToIncrements(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#hasInConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean hasInConstraints(FConstraint value)
   {
      return ((this.constraints != null) && this.constraints.contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#iteratorOfConstraints()
    */
   public Iterator<FConstraint> iteratorOfConstraints()
   {
      return ((this.constraints == null) ? FEmptyIterator.<FConstraint>get()
            : this.constraints.iterator());
   }


   public int sizeOfConstraints()
   {
      return ((this.constraints == null) ? 0 : this.constraints.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#removeFromConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean removeFromConstraints(FConstraint value)
   {
      boolean changed = false;
      if ((this.constraints != null) && (value != null))
      {
         changed = this.constraints.remove(value);
         if (changed)
         {
            value.removeFromIncrements(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#removeAllFromConstraints()
    */
   public void removeAllFromConstraints()
   {
      Iterator<FConstraint> iter = this.iteratorOfConstraints();
      while (iter.hasNext())
      {
         this.removeFromConstraints(iter.next());
      }
   }


   private UMLCommentary comment = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#getComment()
    */
   public UMLCommentary getComment()
   {
      return comment;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#setComment(de.uni_paderborn.fujaba.metamodel.common.FCommentary)
    */
   public void setComment(FCommentary comment)
   {
      if (this.comment != comment)
      {
         UMLCommentary oldComment = this.comment;
         // newPartner
         if (this.comment != null)
         {
            // inform old partner
            this.comment = null;
            oldComment.setRevComment(null);
         }
         this.comment = (UMLCommentary) comment;
         if (comment != null)
         {
            // inform new partner
            comment.setRevComment(this);
         }
         firePropertyChange(COMMENT_PROPERTY, oldComment, comment);
      }
   }


   private boolean assertInUnitTest = false;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#setAssertInUnitTest(boolean)
    */
   public void setAssertInUnitTest(boolean value)
   {
      if (assertInUnitTest != value)
      {
         boolean oldValue = assertInUnitTest;
         assertInUnitTest = value;
         firePropertyChange(ASSERT_IN_UNIT_TEST_PROPERTY, oldValue, value);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#isAssertInUnitTest()
    */
   public boolean isAssertInUnitTest()
   {
      return assertInUnitTest;
   }


   /**
    * <pre>
    *              +-----------+ 0..n                      1
    * UMLIncrement | getName() |----------------------------- UMLStereotype
    *              +-----------+ increments      stereotypes
    * </pre>
    */
   private FPropHashMap<String,FStereotype> stereotypes;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#addToStereotypes(de.uni_paderborn.fujaba.metamodel.common.FStereotype)
    */
   public boolean addToStereotypes(FStereotype stereotype)
   {
      if (stereotype != null && !(stereotype instanceof UMLStereotype))
      {
         throw new IllegalArgumentException(
               "Argument must be instanceof UMLStereotype!");
      }

      boolean changed = false;

      if ((stereotype != null) && (stereotype.getName() != null)
            && (!hasInStereotypes(stereotype)))
      {
         if (this.stereotypes == null)
         {
            this.stereotypes = new FPropHashMap<String,FStereotype>(this, STEREOTYPES_PROPERTY);
         }

         this.stereotypes.put(stereotype.getName(), stereotype);

         stereotype.addToIncrements(this);
         changed = true;
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#hasInStereotypes(de.uni_paderborn.fujaba.metamodel.common.FStereotype)
    */
   public boolean hasInStereotypes(FStereotype stereotype)
   {
      return ((this.stereotypes != null) && (stereotype != null)
            && (stereotype.getName() != null) && (this.stereotypes
            .get(stereotype.getName()) == stereotype));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#hasKeyInStereotypes(java.lang.String)
    */
   public boolean hasKeyInStereotypes(String key)
   {
      return ((this.stereotypes != null) && (key != null) && this.stereotypes
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#iteratorOfStereotypes()
    */
   public Iterator<? extends FStereotype> iteratorOfStereotypes()
   {
      return ((this.stereotypes == null) ? FEmptyIterator.<FStereotype>get()
            : this.stereotypes.values().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#keysOfStereotypes()
    */
   public Iterator<String> keysOfStereotypes()
   {
      return ((this.stereotypes == null) ? FEmptyIterator.<String>get()
            : this.stereotypes.keySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#entriesOfStereotypes()
    */
   @SuppressWarnings("unchecked")
   public Iterator<Map.Entry<String,? extends FStereotype>> entriesOfStereotypes()
   {
      return (Iterator<Map.Entry<String,? extends FStereotype>>)
            ((this.stereotypes == null) ? FEmptyIterator.<Map.Entry<String, ? extends FStereotype>>get()
            : this.stereotypes.entrySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#sizeOfStereotypes()
    */
   public int sizeOfStereotypes()
   {
      return ((this.stereotypes == null) ? 0 : this.stereotypes.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#getFromStereotypes(java.lang.String)
    */
   public UMLStereotype getFromStereotypes(String key)
   {
      return (((this.stereotypes == null) || (key == null)) ? null
            : (UMLStereotype) this.stereotypes.get(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#removeFromStereotypes(de.uni_paderborn.fujaba.metamodel.common.FStereotype)
    */
   public boolean removeFromStereotypes(FStereotype stereotype)
   {
      boolean changed = false;
      if (stereotype != null)
      {
         changed = removeKeyFromStereotypes(stereotype.getName());
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#removeKeyFromStereotypes(java.lang.String)
    */
   public boolean removeKeyFromStereotypes(String key)
   {
      boolean changed = false;

      if (hasKeyInStereotypes(key))
      {
         FStereotype tmpObj = getFromStereotypes(key);
         this.stereotypes.remove(key);
         tmpObj.removeFromIncrements(this);
         changed = true;
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FIncrement#removeAllFromStereotypes()
    */
   public void removeAllFromStereotypes()
   {
      Iterator<? extends FStereotype> iter = this.iteratorOfStereotypes();
      while (iter.hasNext())
      {
         this.removeFromStereotypes(iter.next());
      }
   }


   /**
    * <pre>
    *           0..1     tags     0..*
    * FIncrement ------------------------- FTag
    *           revTags               tags
    * </pre>
    */
   public static final String PROPERTY_TAGS = "tags";

   private FPropHashSet<FTag> tags;

   public boolean addToTags (FTag value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.tags == null)
         {
            this.tags = new FPropHashSet<FTag>(this, PROPERTY_TAGS);

         }
      
         changed = this.tags.add (value);
         if (changed)
         {
            value.setRevTags (this);
         }
      
      }
      return changed;
   }

   public boolean removeFromTags (FTag value)
   {
      boolean changed = false;

      if ((this.tags != null) && (value != null))
      {
      
         changed = this.tags.remove (value);
         if (changed)
         {
            value.setRevTags (null);
         }
      
      }
      return changed;
   }

   public void removeAllFromTags ()
   {
      Iterator<FTag> iter = this.iteratorOfTags ();
      while (iter.hasNext ())
      {
         this.removeFromTags (iter.next ());
      }
   
   }

   public boolean hasInTags (FTag value)
   {
      return ((this.tags != null) &&
              (value != null) &&
              this.tags.contains (value));
   }

   public Iterator<FTag> iteratorOfTags ()
   {
      return ((this.tags == null)
              ? FEmptyIterator.<FTag>get ()
              : this.tags.iterator ());
   }

   public int sizeOfTags ()
   {
      return ((this.tags == null)
              ? 0
              : this.tags.size ());
   }

   
   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.removeAllFromConstraints();
      this.removeAllFromStereotypes();

      UMLCommentary comment = this.getComment();
      if (comment != null)
      {
         comment.removeYou();
      }

      removeAllFromTags();

      super.removeYou();
   }

}

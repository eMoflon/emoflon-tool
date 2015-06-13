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

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.versioning.Versioning;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStoryActivity extends UMLActivity
{

   public static final String STORY_PATTERN_PROPERTY = "storyPattern";
   
   public static final String FOR_EACH_PROPERTY = "forEach";

   /**
    * Constructor for class UMLStoryActivity
    *
    * @param project
    * @param persistent
    */
   protected UMLStoryActivity (final FProject project, final boolean persistent)
   {
      super (project, persistent);
      
      addPropertyChangeListener(FOR_EACH_PROPERTY, new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
         if ( !Versioning.get().isInOperationalization(UMLStoryActivity.this) )
         {
            if (UMLStoryActivity.this.sizeOfExit() == 1)
				{
					UMLTransition trans = (UMLTransition) UMLStoryActivity.this.iteratorOfExit().next();
					UMLTransitionGuard guard = trans.getGuard();
					if (guard == null)
					{
						guard = project.getFromFactories(UMLTransitionGuard.class).create(persistent);						
					}
					if (evt.getNewValue().equals(Boolean.TRUE) && guard.getType() == UMLTransitionGuard.NONE)
					{
						guard.setType(UMLTransitionGuard.TERMINATION);						
					} else if (evt.getNewValue().equals(Boolean.FALSE) && guard.getType() == UMLTransitionGuard.TERMINATION)
					{
						guard.setType(UMLTransitionGuard.NONE);						
					}
					trans.setGuard(guard);
				}
         }
      }
    	  
      });
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String textComment;


   /**
    * Get the textComment attribute of the UMLStoryActivity object
    *
    * @return   The textComment value
    */
   public String getTextComment()
   {
      return textComment;
   }


   /**
    * Sets the textComment attribute of the UMLStoryActivity object
    *
    * @param textComment  The new textComment value
    */
   public void setTextComment (String textComment)
   {
      if ( (this.textComment == null) ||  (this.textComment != null && !this.textComment.equals (textComment)))
      {
         String oldComment = this.textComment;
         this.textComment = textComment;
         firePropertyChange ("textComment", oldComment, textComment);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean forEach = false;


   /**
    * Get the forEach attribute of the UMLStoryActivity object
    *
    * @return   The forEach value
    */
   public boolean isForEach()
   {
      return forEach;
   }


   /**
    * Sets the forEach attribute of the UMLStoryActivity object
    *
    * @param forEach  The new forEach value
    */
   public void setForEach (boolean forEach)
   {
      boolean oldValue = this.forEach;
      this.forEach = forEach;
      firePropertyChange (FOR_EACH_PROPERTY, oldValue, forEach);
   }

   // ######################################################################

   @Property( name = STORY_PATTERN_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLStoryPattern.REV_STORY_PATTERN_PROPERTY, adornment = ReferenceHandler.Adornment.COMPOSITION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLStoryPattern storyPattern;


   /**
    * Get the storyPattern attribute of the UMLStoryActivity object
    *
    * @return   The storyPattern value
    */
   public UMLStoryPattern getStoryPattern()
   {
      return storyPattern;
   }


   /**
    * Sets the storyPattern attribute of the UMLStoryActivity object
    *
    * @param storyPattern  The new storyPattern value
    */
   public void setStoryPattern (UMLStoryPattern storyPattern)
   {
      if (this.storyPattern != storyPattern)
      {
         UMLStoryPattern oldValue = this.storyPattern;
         if (this.storyPattern != null)
         {
            this.storyPattern = null;
            oldValue.setRevStoryPattern (null);
         }
         this.storyPattern = storyPattern;
         if (storyPattern != null)
         {
            storyPattern.setRevStoryPattern (this);
         }
         firePropertyChange (STORY_PATTERN_PROPERTY, oldValue, storyPattern);
      }
   }

   /**
    * *
    * 
    * <pre>    *
    * 0..1     iteration     0..1    * UMLStoryActivity ------------------------- UMLIteration    *           
    * 
    *    revIteration               iteration    *
    * </pre>
    */
   public static final String PROPERTY_ITERATION = "iteration";
   @Property(name = PROPERTY_ITERATION, partner = UMLIteration.PROPERTY_REV_ITERATION, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   private UMLIteration iteration;

   @Property(name = PROPERTY_ITERATION)
   public boolean setIteration ( UMLIteration value )
   {
      boolean changed = false;
      if (this.iteration != value)
      {
         UMLIteration oldValue =

         this.iteration;
         UMLStoryActivity source = this;
         if (this.iteration != null)
         {
            this.iteration = null;

            oldValue.setRevIteration(null);
         }
         this.iteration = value;
         if (value != null)
         {
            value.setRevIteration(this);
         }
         changed = true;
         getPropertyChangeSupport().firePropertyChange (PROPERTY_ITERATION, oldValue, value);
      }
      return changed;
   }

   @Property(name = PROPERTY_ITERATION)
   public UMLIteration getIteration ()
   {
      return this.iteration;
   }

   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      //----- remove the story-pattern of this activity
      UMLStoryPattern storyPattern = getStoryPattern();
      if (storyPattern != null)
      {
         storyPattern.removeYou();
      }

      setRevStory (null);
      setIteration(null);

      super.removeYou();
   }


   /**
    * Get the text attribute of the UMLStoryActivity object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      if (getStoryPattern() != null)
      {
         return getStoryPattern().getName();
      }
      return "STORY";
   }



   /**
    * @return   short string representation of current object
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("UMLStoryActivity[name=");
      result.append (getName());
      result.append (",pattern=");
      result.append (getStoryPattern());
      result.append ("]");

      return result.toString();
   }

}

/*
 * $Log$
 * Revision 1.5  2007/04/03 11:32:13  l3_g5
 * fixed getParentElement() for stories in statecharts
 *
 * Revision 1.4  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.3  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.2  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

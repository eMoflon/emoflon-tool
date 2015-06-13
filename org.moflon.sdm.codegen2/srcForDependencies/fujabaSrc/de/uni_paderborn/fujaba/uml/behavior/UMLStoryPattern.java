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
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.upb.tools.fca.*;
import de.upb.tools.sdm.JavaSDM;

import java.util.*;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStoryPattern extends UMLObjectDiagram
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int STORYPATTERN = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int STORYBOARD = 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SHOWSTORYPATTERN = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SHOWSEQUENCEBAR = 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SHOWBOTH = 2;

   // the values of the constants are the same for UMLObject and UMLLink
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int NONE = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int DELETE = NONE + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int CREATE = DELETE + 1;
   public static final String REV_STORY_PATTERN_PROPERTY = "revStoryPattern";
   public static final String REV_MASTER_COLLAB_STAT_PROPERTY = "revMasterCollabStat";


   /**
    * Constructor for class UMLStoryPattern
    *
    * @param project
    * @param persistent
    */
   protected UMLStoryPattern (FProject project, boolean persistent)
   {
      super (project, persistent);
   } // UMLStoryPattern


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int type = 0;


   /**
    * Get the type attribute of the UMLStoryPattern object
    *
    * @return   The type value
    */
   public int getType()
   {
      return type;
   } // getType


   /**
    * Sets the type attribute of the UMLStoryPattern object
    *
    * @param type  The new type value
    */
   public void setType (int type)
   {
      if (STORYPATTERN <= type && type <= STORYBOARD)
      {
         int oldValue = this.type;
         this.type = type;
         firePropertyChange ("type", oldValue, type);
      }
   } // setType


   @Property( name = REV_STORY_PATTERN_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLStoryActivity.STORY_PATTERN_PROPERTY, adornment = ReferenceHandler.Adornment.PARENT,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLStoryActivity revStoryPattern;


   /**
    * Get the revStoryPattern attribute of the UMLStoryPattern object
    *
    * @return   The revStoryPattern value
    */
   public UMLStoryActivity getRevStoryPattern()
   {
      return revStoryPattern;
   } // getRevStoryPattern


   /**
    * Sets the revStoryPattern attribute of the UMLStoryPattern object
    *
    * @param revStoryPattern  The new revStoryPattern value
    */
   public void setRevStoryPattern (UMLStoryActivity revStoryPattern)
   {
      if (this.revStoryPattern != revStoryPattern)
      {
         // new partner
         UMLStoryActivity oldRevStoryPattern = this.revStoryPattern;
         if (this.revStoryPattern != null)
         {
            // inform old partner
            this.revStoryPattern = null;
            oldRevStoryPattern.setStoryPattern (null);
         }
         this.revStoryPattern = revStoryPattern;
         if (revStoryPattern != null)
         {
            // inform new partner
            revStoryPattern.setStoryPattern (this);
         }

         firePropertyChange (REV_STORY_PATTERN_PROPERTY, oldRevStoryPattern, revStoryPattern);
      }
   } // setRevStoryPattern

   /**
    * @return iterator through all UMLObjects in this story pattern
    */
   @NoProperty
   public Iterator<UMLObject> iteratorOfObjects()
   {
      Collection<UMLObject> objects = new LinkedList<UMLObject>();
      Iterator<? extends FElement> iter = iteratorOfElements();
      while (iter.hasNext())
      {
         ASGElement tmpElement = (ASGElement) iter.next();
         if (tmpElement instanceof UMLObject)
         {
            objects.add ((UMLObject) tmpElement);
         }
      }
      return objects.iterator();
   } // elementsOfObjects

   @NoProperty
   public UMLObject getFromObjects(String objectName)
   {
   	UMLObject result = null;
   	Iterator<UMLObject> iter = this.iteratorOfObjects();
   	while (iter.hasNext())
   	{
   		result = iter.next();
   		if (JavaSDM.stringEquals(objectName, result.getObjectName()))
   		{
   			return result;
   		}
   	}
   	return null;
   }
   
   /**
    * Access method for an one to n association.
    *
    * @param object  The object added.
    */
   @NoProperty
   public void addToVariables (UMLObject object)
   {
      UMLActivityDiagram activityDiagram = getRevStoryPattern().getActivityDiagram();
      if (activityDiagram != null)
      {
         // this storyPattern is in a normal Activity Diagram
         // Won't be needed any more
      }
      else
      {
         // this storyPattern is in a stateChart
         getRevStoryPattern().getRevStory().addToDeclaredVariables (object);
      }
   }


   /**
    * Check if the may clauses include the objects passed as parameter
    *
    * @param nameO1  No description provided
    * @param nameO2  No description provided
    * @return        No description provided
    */
   private boolean mustGenerateIsomorphicBinding (String nameO1, String nameO2)
   {
      // just look if there are any maybe constraints
      if (mayBeConstraints == null)
      {
         return true;
      }

      String posibility1 = nameO1 + "==" + nameO2;
      String posibility2 = nameO2 + "==" + nameO1;

      return  (mayBeConstraints.indexOf (posibility1) == -1 &&
         mayBeConstraints.indexOf (posibility2) == -1);
   } // mustGenerateIsomorphicBinding


   /**
    * Check if there must be generated any isomorphic binding condition
    *
    * @param objectName  No description provided
    * @param container   No description provided
    * @return            No description provided
    */
   public FHashSet filterIsomorphicBindings (String objectName, FHashSet container)
   {
      FHashSet bindings = new FHashSet();

      // just look if there are any maybe constraints
      if (mayBeConstraints != null)
      {
         Iterator iter = container.iterator();
         while (iter.hasNext())
         {
            UMLObject tmpObject = (UMLObject) iter.next();

            if (mustGenerateIsomorphicBinding (objectName, tmpObject.getObjectName()))
            {
               bindings.add (tmpObject);
            }
         }
      }
      else
      {
         bindings = container;
      }

      return bindings;
   } // filterIsomorphicBindings

   private transient String mayBeConstraints = null;

   private int showType = SHOWSTORYPATTERN;


   /**
    * Sets the showType attribute of the UMLStoryPattern object
    *
    * @param showType  The new showType value
    */
   public void setShowType (int showType)
   {
      int oldValue = this.showType;
      this.showType = showType;
      firePropertyChange ("showType", oldValue, showType);
   }

   /**
    * Get the showType attribute of the UMLStoryPattern object
    *
    * @return   The showType value
    */
   public int getShowType()
   {
      return this.showType;
   }


   /**
    * Get the value of mayBeConstraints.
    *
    * @return   Value of mayBeConstraints.
    */
   public String getMayBeConstraints()
   {
      return this.mayBeConstraints;
   }


   /**
    * Set the value of mayBeConstraints.
    *
    * @param mayBeConstraints  Value to assign to mayBeConstraints.
    */
   public void setMayBeConstraints (String mayBeConstraints)
   {
      this.mayBeConstraints = mayBeConstraints;
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      Set<UMLDiagramItem> itemsContainer = new HashSet<UMLDiagramItem>();

      Iterator iter = iteratorOfElements();
      while (iter.hasNext())
      {
         ASGElement tmpElement = (ASGElement) iter.next();
         if (tmpElement instanceof UMLDiagramItem)
         {
            UMLDiagramItem tmpDiagramItem = (UMLDiagramItem) tmpElement;
            removeFromElements(tmpDiagramItem);
            itemsContainer.add (tmpDiagramItem);
         }
      }

      iter = itemsContainer.iterator();
      while (iter.hasNext())
      {
         ASGElement tmpElement = (ASGElement) iter.next();
         tmpElement.removeYou();
      }

      UMLIncrement incr = getRevStoryPattern();
      if (incr != null)
      {
         setRevStoryPattern (null);
         incr.removeYou();
      }

      setRevMasterCollabStat(null);

      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public UMLStoryActivity getParentElement()
   {
      return getRevStoryPattern();
   }


   /**
    * <pre>
    *                  1   masterCollabStat   1
    * UMLStoryPattern -------------------------- UMLCollabStat
    *                  + myPattern           +
    * </pre>
    */
   @Property( name = REV_MASTER_COLLAB_STAT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLCollabStat.MY_PATTERN_PROPERTY, adornment = ReferenceHandler.Adornment.COMPOSITION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private transient UMLCollabStat revMasterCollabStat; // reverse attribute myPattern


   /**
    * Get the revMasterCollabStat attribute of the UMLStoryPattern object
    *
    * @return   The revMasterCollabStat value
    */
   public UMLCollabStat getRevMasterCollabStat()
   {
      return revMasterCollabStat;
   } // getRevMasterCollabStat


   /**
    * Sets the revMasterCollabStat attribute of the UMLStoryPattern object
    *
    * @param revMasterCollabStat  The new revMasterCollabStat value
    */
   public void setRevMasterCollabStat (UMLCollabStat revMasterCollabStat)
   {
      if (this.revMasterCollabStat != revMasterCollabStat)
      {
         // newPartner
         UMLCollabStat oldRevMasterCollabStat = this.revMasterCollabStat;
         if (this.revMasterCollabStat != null)
         {
            // inform old partner
            this.revMasterCollabStat = null;

            oldRevMasterCollabStat.setMyPattern (null);
         }
         this.revMasterCollabStat = revMasterCollabStat;
         if (revMasterCollabStat != null)
         {
            // inform new partner
            revMasterCollabStat.setMyPattern (this);
         }

         firePropertyChange (REV_MASTER_COLLAB_STAT_PROPERTY, oldRevMasterCollabStat, revMasterCollabStat);
      }
   } // setRevMasterCollabStat


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public int renumberCollabStats()
   {
      UMLCollabStat masterCollabStat = this.getRevMasterCollabStat();

      if (masterCollabStat == null)
      {
         masterCollabStat = getProject().getFromFactories (UMLCollabStat.class).create (isPersistent());
         this.setRevMasterCollabStat (masterCollabStat);
      }

      return masterCollabStat.renumberCollabStats();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Override
   public String toString()
   {
      StringBuffer name = new StringBuffer ("StoryPattern");
      if (getName() != null)
      {
         name.append (" " + getName());
      }

      if (getRevStoryPattern() != null && getRevStoryPattern().getName() != null)
      {
         name.append (" in Activity " + getRevStoryPattern().getName());
      }

      return name.toString();
   }


   // recursive search, when found succesor connect it to the paramameter multiLink
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param multiLink  No description provided
    * @param queue      No description provided
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   private void searchNextMultiLink (UMLMultiLink multiLink, FQueue queue)
   {
      boolean found = false;
      UMLMultiLink nextMultiLink;

      for (int j = 0; j < queue.size() && !found; j++)
      {

         nextMultiLink = (UMLMultiLink) queue.get();

         if (multiLink.getTargetObject() == nextMultiLink.getSourceObject())
         {
            found = true;
            // connect the two MultiLinks
            multiLink.setNextMultiLink (nextMultiLink);
            nextMultiLink.setPreviousMultiLink (multiLink);
            this.searchNextMultiLink (nextMultiLink, queue);
         }
         else
         {
            queue.put (nextMultiLink);
         }
      }
   }


   // connect the Multilinks
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param queue  No description provided
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   public void connectMultiLinks (FQueue queue)
   {
      UMLMultiLink currentMultiLink;
      for (int j = 0; j < queue.size(); j++)
      {
         currentMultiLink = (UMLMultiLink) queue.get();
         this.searchNextMultiLink (currentMultiLink, queue);
         queue.put (currentMultiLink);
      }
   }


   private boolean noTryCatch;
   

	public void setNoTryCatch(boolean noTryCatch)
	{
		boolean oldValue = this.noTryCatch;
		this.noTryCatch = noTryCatch;
      firePropertyChange ("noTryCatch", oldValue, noTryCatch);
	}


	public boolean isNoTryCatch()
	{
		return noTryCatch;
	}

}

/*
 * $Log$
 * Revision 1.10  2007/03/21 12:47:47  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.9  2006/09/19 17:52:40  fklar
 * + removed unused imports
 *
 * Revision 1.8  2006/09/16 20:35:42  zuendorf
 * just a quick save
 *
 * Revision 1.7  2006/08/22 22:51:34  zuendorf
 * Added FMM.create(proj, class) to shorten the creation of metamodel objects.
 * Linkeditor selects objects in correct order now.
 * Added some flags guiding the code generation for Story Patterns and objects.
 * Added some convinience to UMLTransition.
 *
 * Revision 1.6  2006/05/03 13:01:51  lowende
 * UMLObjects enhanced by construction expressions.
 * These expressions will be used for creating objects in generated code.
 * Can also be used to create objects with factories (e.g. Fujaba Metamodel objects).
 *
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

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
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import org.apache.log4j.Logger;

import java.util.HashMap;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLMultiLink extends UMLConnection implements Traversable
{
   /**
    * log4j logging
    */
   private static final Logger log = Logger.getLogger (UMLMultiLink.class);

   // Priorities
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_CHECK = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_TO_ONE = P_CHECK + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_CHECK_TO_MANY = P_TO_ONE + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_CHECK = P_CHECK_TO_MANY + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_FIRST = P_MULTILINK_CHECK + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_LAST = P_MULTILINK_FIRST + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_BOUND_TO_UNBOUND = P_MULTILINK_LAST + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_UNBOUND_TO_BOUND = P_MULTILINK_BOUND_TO_UNBOUND + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_ENTRY = P_MULTILINK_UNBOUND_TO_BOUND + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_PATH = P_MULTILINK_ENTRY + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_TO_MANY = P_MULTILINK_PATH + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_ENTRY_OPTIONAL = P_TO_MANY + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_MULTILINK_PATH_OPTIONAL = P_MULTILINK_ENTRY_OPTIONAL + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_OPTIONAL = 20;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_OPTIONAL_CHECK = P_OPTIONAL;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_OPTIONAL_TO_ONE = P_OPTIONAL_CHECK + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_OPTIONAL_TO_MANY = P_OPTIONAL_TO_ONE + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_SET = 40;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_NEGATIVE = 60;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int P_NONE = 100;

   // Type of the MultiLink
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int FIRST = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int LAST = 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int DIRECT = 2;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int INDEX = 3;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int INDIRECT = 4;

   // Type of UMLObject
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int NORMAL = UMLObject.NORM;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int OPTIONAL = UMLObject.OPTIONAL;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int SET = UMLObject.SET;

   // Type String Representation of MultiLink
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static final String[] STRTYPES =
      {"->| ", " |->", " --> ", " []-> ", " ...> "};

   // generateJavaOptions
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int MULTILINK_SEARCH = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int MULTILINK_CREATE = 1;

   // insert new Object Context
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_NORM = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_OPTIONAL = 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_SET = 2;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_CREATE_OBJECT = 3;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_CREATE_LINK = 4;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_LEFT_NULL = 5;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_RIGHT_NORM = 6;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_RIGHT_OPTIONAL = 7;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int C_RIGHT_SET = 8;

   // Private Members
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property( name = SOURCE_LINK_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLLink.REV_SOURCE_LINK_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLLink sourceLink;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property( name = TARGET_LINK_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLLink.REV_TARGET_LINK_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLLink targetLink;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int type;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int index;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name = "";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property( name = CONTAINER_OBJECT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.REV_CONTAINER_OBJECT_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject containerObject = null;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLMultiLink nextMultiLink = null;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLMultiLink previousMultiLink = null;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLObject sourceObjectBuffer = null;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLObject targetObjectBuffer = null;

   // bindOptionalAndSet is set true if all normal objects are bounded in the multiLink-path
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean bindOptionalAndSet = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean path = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean entry = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean negative;
   public static final String CONTAINER_OBJECT_PROPERTY = "containerObject";
   public static final String SOURCE_LINK_PROPERTY = "sourceLink";
   public static final String TARGET_LINK_PROPERTY = "targetLink";

   // constructors
   /**
    * Constructor for class UMLMultiLink
    *
    * @param project
    * @param persistent
    */
   protected UMLMultiLink (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Sets the sourceConnector attribute of the UMLMultiLink object
    *
    * @param item  The new sourceConnector value
    * @return      No description provided
    */
   @Override
   public boolean setSourceConnector (UMLDiagramItem item)
   {
      if (! (item instanceof UMLLink))
      {
         throw new IllegalArgumentException ("Argument is no UMLLink");
      }
      return setSourceLink ((UMLLink) item);
   }


   /**
    * Get the sourceConnector attribute of the UMLMultiLink object
    *
    * @return   The sourceConnector value
    */
   @Override
   public UMLDiagramItem getSourceConnector()
   {
      return getSourceLink();
   }


   /**
    * Sets the targetConnector attribute of the UMLMultiLink object
    *
    * @param item  The new targetConnector value
    * @return      No description provided
    */
   @Override
   public boolean setTargetConnector (UMLDiagramItem item)
   {
      if (! (item instanceof UMLLink))
      {
         throw new IllegalArgumentException ("Argument is no UMLLink");
      }
      return setTargetLink ((UMLLink) item);
   }


   /**
    * Get the targetConnector attribute of the UMLMultiLink object
    *
    * @return   The targetConnector value
    */
   @Override
   public UMLDiagramItem getTargetConnector()
   {
      return getTargetLink();
   }


   /**
    * TODO: this caching stuff should be removed
    */
   public void update()
   {
      this.sourceObjectBuffer = null;
      this.targetObjectBuffer = null;
      this.getSourceObject();
      this.getTargetObject();
      this.setBindOptionalAndSet (false);
      this.setPath (false);

      boolean sourceIsSet =  (getSourceObject().getType() == SET);
      boolean targetIsSet =  (getTargetObject().getType() == SET);

      if (sourceIsSet || targetIsSet)
      {
         setType (INDIRECT);
      }

      this.setName (generateName());
   }


   /**
    * Sets the sourceLink attribute of the UMLMultiLink object
    *
    * @param sourceLink  The new sourceLink value
    * @return            No description provided
    */
   public boolean setSourceLink (UMLLink sourceLink)
   {
      if (this.sourceLink != sourceLink)
      {
         // new partner
         UMLLink oldSourceLink = this.sourceLink;
         if (this.sourceLink != null)
         {
            // inform old partner
            this.sourceLink = null;
            oldSourceLink.setRevSourceLink (null);
         }
         this.sourceLink = sourceLink;
         if (sourceLink != null)
         {
            // inform new partner
            sourceLink.setRevSourceLink (this);
         }
         firePropertyChange (SOURCE_LINK_PROPERTY, oldSourceLink, sourceLink);
         return true;
      }
      return false;
   }


   /**
    * Get the sourceLink attribute of the UMLMultiLink object
    *
    * @return   The sourceLink value
    */
   public UMLLink getSourceLink()
   {
      UMLLink result;

      if (getType() == FIRST)
      {
         // dirty hack
         result = getTargetLink();
      }
      else
      {
         result = this.sourceLink;
      }

      return result;
   }


   /**
    * Sets the targetLink attribute of the UMLMultiLink object
    *
    * @param targetLink  The new targetLink value
    * @return            No description provided
    */
   public boolean setTargetLink (UMLLink targetLink)
   {
      if (this.targetLink != targetLink)
      {
         // new partner
         UMLLink oldTargetLink = this.targetLink;
         if (this.targetLink != null)
         {
            // inform old partner
            this.targetLink = null;
            oldTargetLink.setRevTargetLink (null);
         }
         this.targetLink = targetLink;
         if (targetLink != null)
         {
            // inform new partner
            targetLink.setRevTargetLink (this);
         }
         firePropertyChange (TARGET_LINK_PROPERTY, oldTargetLink, targetLink);
         return true;
      }
      return false;
   }


   /**
    * Get the targetLink attribute of the UMLMultiLink object
    *
    * @return   The targetLink value
    */
   public UMLLink getTargetLink()
   {
      UMLLink result;

      if (getType() == LAST)
      {
         result = getSourceLink();
      }
      else
      {
         result = this.targetLink;
      }

      return result;
   }


   /**
    * Sets the name attribute of the UMLMultiLink object
    *
    * @param value  The new name value
    */
   @Override
   public void setName (String value)
   {
      if (!this.name.equals (value))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange ("name", oldValue, value);
      }
   }


   /**
    * Get the name attribute of the UMLMultiLink object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return this.name;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public String updateName()
   {
      this.setName (this.generateName());
      return getName();
   }


   /**
    * @return   short string representation of current object
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("UMLMultiLink[");
      result.append (name);
      result.append ("]");

      return result.toString();
   }


   /**
    * Sets the bindOptionalAndSet attribute of the UMLMultiLink object
    *
    * @param value  The new bindOptionalAndSet value
    */
   public void setBindOptionalAndSet (boolean value)
   {
      this.bindOptionalAndSet = value;
   }


   /**
    * Get the bindOptionalAndSet attribute of the UMLMultiLink object
    *
    * @return   The bindOptionalAndSet value
    */
   public boolean getBindOptionalAndSet()
   {
      return this.bindOptionalAndSet;
   }


   /**
    * Sets the path attribute of the UMLMultiLink object
    *
    * @param value  The new path value
    */
   public void setPath (boolean value)
   {
      this.path = value;
   }


   /**
    * Get the path attribute of the UMLMultiLink object
    *
    * @return   The path value
    */
   public boolean isPath()
   {
      return this.path;
   }


   /**
    * Sets the entry attribute of the UMLMultiLink object
    *
    * @param value  The new entry value
    */
   public void setEntry (boolean value)
   {
      this.entry = value;
   }


   /**
    * Get the entry attribute of the UMLMultiLink object
    *
    * @return   The entry value
    */
   public boolean isEntry()
   {
      return this.entry;
   }


   /**
    * Get the negative attribute of the UMLMultiLink object
    *
    * @return   The negative value
    */
   public boolean isNegative()
   {
      return this.negative;
   }


   /**
    * Sets the negative attribute of the UMLMultiLink object
    *
    * @param negative  The new negative value
    */
   public void setNegative (boolean negative)
   {
      this.negative = negative;
   }


   /**
    * Sets the type attribute of the UMLMultiLink object
    *
    * @param type  The new type value
    */
   public void setType (int type)
   {
      if (FIRST <= type && type <= INDIRECT)
      {
         int oldValue = this.type;
         this.type = type;
         this.updateName();
         firePropertyChange ("type", oldValue, type);
      }
   }


   /**
    * Get the type attribute of the UMLMultiLink object
    *
    * @return   The type value
    */
   public int getType()
   {
      return type;
   }


   /**
    * Get the containerObject attribute of the UMLMultiLink object
    *
    * @return   The containerObject value
    */
   public UMLObject getContainerObject()
   {
      return containerObject;
   } //getContainerObject


   /**
    * Sets the containerObject attribute of the UMLMultiLink object
    *
    * @param containerObject  The new containerObject value
    */
   public void setContainerObject (UMLObject containerObject)
   {
      if (this.containerObject != containerObject)
      {
         // new partner
         UMLObject oldContainerObject = this.containerObject;
         if (this.containerObject != null)
         {
            // inform old partner
            this.containerObject = null;
            oldContainerObject.removeFromRevContainerObject (this);
         }
         this.containerObject = containerObject;
         if (containerObject != null)
         {
            // inform new partner
            containerObject.addToRevContainerObject (this);
         }
         firePropertyChange (CONTAINER_OBJECT_PROPERTY, oldContainerObject, containerObject);
      }
   } //setContainerObject


   /**
    * Get the sourceObject attribute of the UMLMultiLink object
    *
    * @return   The sourceObject value
    */
   public UMLObject getSourceObject()
   {
      UMLObject result;

      if (sourceObjectBuffer != null)
      {
         result = this.sourceObjectBuffer;
      }
      else
      {
         if (getSourceLink().getTarget() != getContainerObject())
         {
            result = getSourceLink().getTarget();
         }
         else
         {
            result = getSourceLink().getSource();
         }
         this.sourceObjectBuffer = result;
      }

      return result;
   }


   /**
    * Get the targetObject attribute of the UMLMultiLink object
    *
    * @return   The targetObject value
    */
   public UMLObject getTargetObject()
   {
      UMLObject result;

      if (targetObjectBuffer != null)
      {
         result = this.targetObjectBuffer;
      }
      else
      {
         if (getTargetLink().getTarget() != getContainerObject())
         {
            result = getTargetLink().getTarget();
         }
         else
         {
            result = getTargetLink().getSource();
         }
         this.targetObjectBuffer = result;
      }
      return result;
   }


   /**
    * Get the nextMultiLink attribute of the UMLMultiLink object
    *
    * @return   The nextMultiLink value
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   @NoProperty
   public UMLMultiLink getNextMultiLink()
   {
      UMLMultiLink result;
      if (this.nextMultiLink != null)
      {
         result = this.nextMultiLink;
      }
      else
      {
         result = null;
      }
      return result;
   } // getNextMultiLink


   /**
    * Sets the nextMultiLink attribute of the UMLMultiLink object
    *
    * @param aMultiLink  The new nextMultiLink value
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   @NoProperty
   public void setNextMultiLink (UMLMultiLink aMultiLink)
   {
      if (aMultiLink != this.nextMultiLink)
      {
         this.nextMultiLink = aMultiLink;
      }
   } // setNextMultiLink


   /**
    * Get the previousMultiLink attribute of the UMLMultiLink object
    *
    * @return   The previousMultiLink value
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   @NoProperty
   public UMLMultiLink getPreviousMultiLink()
   {
      UMLMultiLink result;
      if (this.previousMultiLink != null)
      {
         result = this.previousMultiLink;
      }
      else
      {
         result = null;
      }
      return result;
   } // getPreviousMultiLink


   /**
    * Sets the previousMultiLink attribute of the UMLMultiLink object
    *
    * @param aMultiLink  The new previousMultiLink value
    * @deprecated this is part of the old codegen and should be removed from the model
    */
   public void setPreviousMultiLink (UMLMultiLink aMultiLink)
   {
      if (aMultiLink != this.previousMultiLink)
      {
         this.previousMultiLink = aMultiLink;
      }
   } // setPreviousMultiLink


   /**
    * Get the index attribute of the UMLMultiLink object
    *
    * @return   The index value
    */
   public int getIndex()
   {
      return this.index;
   } // getIndex


   /**
    * Sets the index attribute of the UMLMultiLink object
    *
    * @param value  The new index value
    */
   public void setIndex (int value)
   {
      if (value != this.index)
      {
         int oldValue = this.index;
         this.index = value;
         this.updateName();
         firePropertyChange ("index", oldValue, value);
      }
   } // setIndex


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Override
   public void removeYou()
   {
      UMLLink link = getSourceLink();
      if ( (type == FIRST || type == LAST) &&  (link != null))
      {
         FAssoc assoc = link.getInstanceOf();
         if (assoc != null)
         {
            link.setName (assoc.getName());
         }
      }

      this.setSourceLink (null);
      this.setTargetLink (null);
      this.setContainerObject (null);
      super.removeYou();
   } // removeYou


   /**
    * Get the containerName attribute of the UMLMultiLink object
    *
    * @return   The containerName value
    */
   public String getContainerName()
   {
      FRole tmpRole = null;

      if (this.getSourceLink().getTarget() != this.getContainerObject())
      {
         tmpRole = this.getSourceLink().getInstanceOf().getRightRole();
      }
      else
      {
         tmpRole = this.getSourceLink().getInstanceOf().getLeftRole();
      }

      return tmpRole.getAttrName();
   } // getContainerName


   /**
    * Get the priority attribute of the UMLMultiLink object
    *
    * @param boundObjects        No description provided
    * @param isomorphicBindings  No description provided
    * @return                    The priority value
    */
   public int getPriority (HashMap boundObjects, HashMap isomorphicBindings)
   {
      int priority = P_NONE;

      UMLObject sourceObject = this.getSourceObject();
      UMLObject targetObject = this.getTargetObject();

      //sourceBound is true if source is bound
      boolean sourceBound = boundObjects.get (sourceObject.getID()) != null;
      //targetBound is true if target is bound
      boolean targetBound = boundObjects.get (targetObject.getID()) != null;

      if (path == true && bindOptionalAndSet == false)
      {
         priority = P_MULTILINK_PATH;
      }
      else if (path == false && bindOptionalAndSet == true)
      {
         priority = P_MULTILINK_ENTRY_OPTIONAL;
      }
      else if (path == true && bindOptionalAndSet == true)
      {
         priority = P_MULTILINK_PATH_OPTIONAL;
      }
      else if ( (getType() == FIRST) && targetBound)
      {
         priority = P_MULTILINK_CHECK;
      }
      else if ( (getType() == LAST) && sourceBound)
      {
         priority = P_MULTILINK_CHECK;
      }
      else if (getType() == FIRST)
      {
         priority = P_MULTILINK_FIRST;
      }
      else if (getType() == LAST)
      {
         priority = P_MULTILINK_LAST;
      }
      else if (getPreviousMultiLink() == null && getType() != FIRST)
      {
         priority = P_MULTILINK_ENTRY;
         entry = true;
      }
      else if (getPreviousMultiLink().getType() == FIRST)
      {
         priority = P_MULTILINK_ENTRY;
         entry = true;
      }
      else if (sourceBound && targetBound)
      {
         priority = P_MULTILINK_CHECK;
      }
      else if (sourceBound && !targetBound &&  (sourceObject.getType() == NORMAL) &&
          (targetObject.getType() == NORMAL) &&  (this.getType() != INDIRECT))
      {
         priority = P_MULTILINK_BOUND_TO_UNBOUND;
      }
      else if (!sourceBound && targetBound &&  (sourceObject.getType() == NORMAL) &&
          (targetObject.getType() == NORMAL) &&  (this.getType() != INDIRECT))
      {
         priority = P_MULTILINK_UNBOUND_TO_BOUND;
      }
      else
      {
         priority = P_NONE;
      }
      if ( (sourceObject.getType() == UMLObject.NEGATIVE) ||  (targetObject.getType() == UMLObject.NEGATIVE) || isNegative())
      {
         priority += P_NEGATIVE;
      }
      return priority;
   } //getPriority


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param object  No description provided
    * @return        No description provided
    */
   public String findLowerBound (UMLObject object)
   {
      String result = null;

      UMLObject tmpObject;
      UMLMultiLink tmpMultiLink;

      boolean found = false;

      boolean
         isBound = false;

      boolean
         hasCreateModifier = false;
      int type = -1;

      if (object == getSourceObject())
      {
         tmpMultiLink = this.getPreviousMultiLink();
         tmpObject =  (tmpMultiLink != null) ? tmpMultiLink.getSourceObject() : null;
      }
      else
      {
         tmpMultiLink = this;
         tmpObject = tmpMultiLink.getSourceObject();
      }

      if (tmpObject != null)
      {
         type = tmpObject.getType();
         isBound = tmpObject.isBound();
         hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getSourceLink().getModifier() == UMLLink.CREATE);
      }

      while (!found &&  (tmpMultiLink != null) &&  (tmpMultiLink.getType() != FIRST))
      {
         if (!hasCreateModifier &&  (type == NORMAL))
         {
            result = tmpObject.getObjectName();
            found = true;
         }
         else if (isBound && !hasCreateModifier &&  (type == SET))
         {
            result = tmpObject.getObjectName() + ".last()";
            found = true;
         }
         tmpMultiLink = tmpMultiLink.getPreviousMultiLink();
         if (tmpMultiLink != null)
         {
            tmpObject = tmpMultiLink.getSourceObject();
            type = tmpObject.getType();
            isBound = tmpObject.isBound();
            hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getSourceLink().getModifier() == UMLLink.CREATE);
         }
      }
      return result;
   } // findLowerBound


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param object  No description provided
    * @return        No description provided
    */
   public String findNextNormalLowerBound (UMLObject object)
   {
      String result = null;

      UMLObject tmpObject;
      UMLMultiLink tmpMultiLink;

      boolean found = false;

      boolean
         isBound = false;

      boolean
         hasCreateModifier = false;
      int type = -1;

      if (object == getSourceObject())
      {
         tmpMultiLink = this.getPreviousMultiLink();
         tmpObject =  (tmpMultiLink != null) ? tmpMultiLink.getSourceObject() : null;
      }
      else
      {
         tmpMultiLink = this;
         tmpObject = tmpMultiLink.getSourceObject();
      }

      if (tmpObject != null)
      {
         type = tmpObject.getType();
         isBound = tmpObject.isBound();
         hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getSourceLink().getModifier() == UMLLink.CREATE);
      }

      while (!found &&  (tmpMultiLink != null) &&  (tmpMultiLink.getType() != FIRST))
      {
         if (!hasCreateModifier &&  (type == NORMAL))
         {
            result = tmpObject.getObjectName();
            found = true;
         }
         else if (!hasCreateModifier && isBound &&  (type == SET))
         {
            result = tmpObject.getObjectName() + ".last()";
            found = true;
         }
         else if (!hasCreateModifier)
         {
            result = null;
            found = true;
         }

         tmpMultiLink = tmpMultiLink.getPreviousMultiLink();
         if (tmpMultiLink != null)
         {
            tmpObject = tmpMultiLink.getSourceObject();
            type = tmpObject.getType();
            isBound = tmpObject.isBound();
            hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpObject.getModifier() == UMLLink.CREATE);
         }
      }
      return result;
   } // findNextNormalLowerBound


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param object        No description provided
    * @param boundObjects  No description provided
    * @return              No description provided
    */
   public String findUpperBoundForNormal (UMLObject object, HashMap boundObjects)
   {
      String result = null;

      UMLMultiLink tmpMultiLink;
      UMLObject tmpObject;

      boolean found = false;

      boolean
         isBound = false;

      boolean
         hasCreateModifier = false;
      int type = -1;

      if (object == getTargetObject())
      {
         tmpMultiLink = this.getNextMultiLink();
         tmpObject =  (tmpMultiLink != null) ? tmpMultiLink.getTargetObject() : null;
      }
      else
      {
         tmpMultiLink = this;
         tmpObject = tmpMultiLink.getTargetObject();
      }

      if (tmpObject != null)
      {
         type = tmpObject.getType();
         isBound = tmpObject.isBound() ||  (boundObjects.get (tmpObject.getID()) != null);
         hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getTargetLink().getModifier() == UMLLink.CREATE);
      }

      while (!found &&  (tmpMultiLink != null) &&  (tmpMultiLink.getType() != LAST))
      {
         if (isBound && !hasCreateModifier &&  (type == NORMAL))
         {
            result = tmpObject.getObjectName();
            found = true;
         }
         else if (isBound && !hasCreateModifier &&  (type == SET))
         {
            result = tmpObject.getObjectName() + ".first()";
            found = true;
         }

         tmpMultiLink = tmpMultiLink.getNextMultiLink();
         if (tmpMultiLink != null)
         {
            tmpObject = tmpMultiLink.getTargetObject();
            type = tmpObject.getType();
            isBound = tmpObject.isBound() ||  (boundObjects.get (tmpObject.getID()) != null);
            hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getTargetLink().getModifier() == UMLLink.CREATE);
         }
      }
      return result;
   } // findUpperBoundForNormal


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param object  No description provided
    * @return        No description provided
    */
   public boolean hasAnyLowerBound (UMLObject object)
   {
      UMLMultiLink tmpMultiLink;
      UMLObject tmpObject;
      boolean found = false;
      boolean hasCreateModifier = false;

      if (object == getSourceObject())
      {
         tmpMultiLink = this.getPreviousMultiLink();
         tmpObject =  (tmpMultiLink != null) ? tmpMultiLink.getSourceObject() : null;
      }
      else
      {
         tmpMultiLink = this;
         tmpObject = tmpMultiLink.getSourceObject();
      }

      if (tmpObject != null)
      {
         type = tmpObject.getType();
         hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getSourceLink().getModifier() == UMLLink.CREATE);
      }

      while (!found &&  (tmpMultiLink != null) &&  (tmpMultiLink.getType() != FIRST))
      {
         if (!hasCreateModifier)
         {
            found = true;
         }

         tmpMultiLink = tmpMultiLink.getPreviousMultiLink();
         if (tmpMultiLink != null)
         {
            tmpObject = tmpMultiLink.getSourceObject();
            hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getSourceLink().getModifier() == UMLLink.CREATE);
         }
      }
      return found;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param object        No description provided
    * @param boundObjects  No description provided
    * @return              No description provided
    */
   public String findUpperBoundForOptional (UMLObject object, HashMap boundObjects)
   {
      String result = null;

      UMLMultiLink tmpMultiLink;
      UMLObject tmpObject;

      boolean found = false;

      boolean
         isBound = false;

      boolean
         hasCreateModifier = false;
      int type = -1;

      if (object == getTargetObject())
      {
         tmpMultiLink = this.getNextMultiLink();
         tmpObject =  (tmpMultiLink != null) ? tmpMultiLink.getTargetObject() : null;
      }
      else
      {
         tmpMultiLink = this;
         tmpObject = tmpMultiLink.getTargetObject();
      }

      if (tmpObject != null)
      {
         type = tmpObject.getType();
         isBound =  (boundObjects.get (tmpObject.getID()) != null);
         hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getTargetLink().getModifier() == UMLLink.CREATE);
      }

      while (!found &&  (tmpMultiLink != null) &&  (tmpMultiLink.getType() != LAST))
      {
         if (!hasCreateModifier &&  (type == NORMAL))
         {
            result = tmpObject.getObjectName();
            found = true;
         }

         else if (isBound && !hasCreateModifier &&  (type == SET))
         {
            result = tmpObject.getObjectName() + ".first()";
            found = true;
         }

         tmpMultiLink = tmpMultiLink.getNextMultiLink();
         if (tmpMultiLink != null)
         {
            tmpObject = tmpMultiLink.getTargetObject();
            type = tmpObject.getType();
            isBound =  (boundObjects.get (tmpObject.getID()) != null);
            hasCreateModifier =  (tmpObject.getModifier() == UMLObject.CREATE) ||  (tmpMultiLink.getTargetLink().getModifier() == UMLLink.CREATE);
         }
      }

      return result;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   private String generateName()
   {
      StringBuffer result = new StringBuffer();
      boolean debug = false;

      if (!debug)
      {
         if (getType() == DIRECT)
         {
            result.append ("{next}");
         }
         else if (getType() == INDIRECT)
         {
            result.append ("{...}");
         }
         else if (getType() == INDEX)
         {
            result.append ("{next[").append (getIndex()).append ("]}");
         }
         else if (getType() == FIRST)
         {
            result.append ("{first}");
         }
         else if (getType() == LAST)
         {
            result.append ("{last}");
         }
      }
      else
      {
         if ( (getType() != FIRST) &&  (getType() != LAST))
         {
            result.append (getSourceObject()).append (STRTYPES[getType()]).append (getTargetObject());
         }
         else if (getType() == FIRST)
         {
            result.append (STRTYPES[FIRST]).append (getTargetObject());
         }
         else if (getType() == LAST)
         {
            result.append (STRTYPES[LAST]).append (getSourceObject());
         }
      }
      return result.toString();
   }


   /**
    * Get the context attribute of the UMLMultiLink object
    *
    * @param leftObject   No description provided
    * @param rightObject  No description provided
    * @param leftLink     No description provided
    * @param rightLink    No description provided
    * @return             The context value
    */
   public int getContext (UMLObject leftObject, UMLObject rightObject, UMLLink leftLink, UMLLink rightLink)
   {
      int result;

      int leftType =  (leftObject != null) ? leftObject.getType() : -1;
      int rightType =  (rightObject != null) ? rightObject.getType() : -1;

      int leftObjectModifier =  (leftObject != null) ? leftObject.getModifier() : -1;
      int rightObjectModifier =  (rightObject != null) ? rightObject.getModifier() : -1;

      int leftLinkModifier =  (leftLink != null) ? leftLink.getModifier() : -1;
      int rightLinkModifier =  (rightLink != null) ? rightLink.getModifier() : -1;

      if ( (leftType == UMLObject.NORM) &&  (leftObjectModifier != UMLObject.CREATE) &&  (leftLinkModifier != UMLLink.CREATE))
      {
         result = C_LEFT_NORM;
      }
      else if ( (rightType == UMLObject.NORM) &&  (rightObjectModifier != UMLObject.CREATE) &&  (rightLinkModifier != UMLLink.CREATE))
      {
         result = C_RIGHT_NORM;
      }
      else if ( (leftType == UMLObject.OPTIONAL) &&  (leftObjectModifier != UMLObject.CREATE) &&  (leftLinkModifier != UMLLink.CREATE))
      {
         result = C_LEFT_OPTIONAL;
      }
      else if ( (rightType == UMLObject.OPTIONAL) &&  (rightObjectModifier != UMLObject.CREATE) &&  (rightLinkModifier != UMLLink.CREATE))
      {
         result = C_RIGHT_OPTIONAL;
      }

      else if ( (leftType == UMLObject.SET) &&  (leftObjectModifier != UMLObject.CREATE) &&  (leftLinkModifier != UMLLink.CREATE))
      {
         result = C_LEFT_SET;
      }
      else if ( (rightType == UMLObject.SET) &&  (rightObjectModifier != UMLObject.CREATE) &&  (rightLinkModifier != UMLLink.CREATE))
      {
         result = C_RIGHT_SET;
      }
      else if (leftObjectModifier == UMLObject.CREATE)
      {
         result = C_LEFT_CREATE_OBJECT;
      }
      else if (leftLinkModifier == UMLLink.CREATE)
      {
         result = C_LEFT_CREATE_LINK;
      }
      else if (leftObject == null)
      {
         result = C_LEFT_NULL;
      }
      else
      {
         log.error ("UMLMultiLink.getContext(): error");
         result = -1;
      }

      return result;
   }


   /**
    * Get the correspondingRole attribute of the UMLMultiLink object
    *
    * @param object  No description provided
    * @return        The correspondingRole value
    */
   public FRole getCorrespondingRole (UMLObject object)
   {
      FRole tmpRole = null;
      UMLLink umlLink = getSourceLink() != null ? getSourceLink() : getTargetLink();
      FAssoc myAssoc = umlLink.getInstanceOf();
      FClass targetClass = null;

      if (myAssoc != null)
      {
         if (object == umlLink.getSource())
         {
            tmpRole = myAssoc.getLeftRole();
         }
         else
         {
            tmpRole = myAssoc.getRightRole();
         }
         targetClass = tmpRole.getTarget();
      }

      FClass tmpClass = object.getInstanceOf();

      if ( (tmpClass != null)
         &&  (! (targetClass == tmpClass || tmpClass.isChildOf (targetClass))))
      {
         if (object == umlLink.getSource())
         {
            tmpRole = myAssoc.getRightRole();
         }
         else
         {
            tmpRole = myAssoc.getLeftRole();
         }
      }

      return tmpRole;
   }

}

/*
 * $Log$
 * Revision 1.12  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.11  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.10  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.9  2006/03/01 12:22:48  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

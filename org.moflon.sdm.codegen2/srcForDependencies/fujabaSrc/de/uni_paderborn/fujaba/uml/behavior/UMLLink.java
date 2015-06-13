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
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.behavior.FInstanceElement;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.*;
import de.uni_paderborn.fujaba.metamodel.structure.util.FRoleUtility;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;

import java.util.HashMap;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *          0..n    source    0..1
 * UMLLink ------------------------ UMLObject
 *          revSource       source
 *
 *          0..n    target    0..1
 * UMLLink ------------------------ UMLObject
 *          revTarget       target
 *
 *          0..n    instanceOf    0..1
 * UMLLink ---------------------------- UMLAssoc
 *          instances       instanceOf
 *
 *          0..1     sourceLink     0..1
 * UMLLink ------------------------------ UMLMultiLink
 *          revSourceLink     sourceLink
 *
 *          0..1     targetLink     0..1
 * UMLLink ------------------------------ UMLMultiLink
 *          targetLink     revTargetLink
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLLink extends UMLConnection implements Traversable, FInstanceElement
{
   /**
    * type
    * 
    * @see #setType
    * @see #getType
    */
   public final static int NULL = 0;

   /**
    * type
    * 
    * @see #setType
    * @see #getType
    */
   public final static int NEGATIVE = 1;

   /**
    * type
    * 
    * @see #setType
    * @see #getType
    */
   public final static int OPTIONAL = 2;

   /**
    * modifier
    * 
    * @see #setModifier
    * @see #getModifier
    */
   public final static int NONE = UMLStoryPattern.NONE;

   /**
    * modifier
    * 
    * @see #setModifier
    * @see #getModifier
    */
   public final static int CREATE = UMLStoryPattern.CREATE;

   /**
    * modifier
    * 
    * @see #setModifier
    * @see #getModifier
    */
   public final static int DELETE = UMLStoryPattern.DELETE;

   public final static int P_CHECK = 0;

   public final static int P_TO_ONE = P_CHECK + 1;

   public final static int P_CHECK_TO_MANY = P_TO_ONE + 1;

   public final static int P_MULTILINK_CHECK = P_CHECK_TO_MANY + 1;

   public final static int P_MULTILINK_FIRST = P_MULTILINK_CHECK + 1;

   public final static int P_MULTILINK_LAST = P_MULTILINK_FIRST + 1;

   public final static int P_MULTILINK_BOUND_TO_UNBOUND = P_MULTILINK_LAST + 1;

   public final static int P_MULTILINK_UNBOUND_TO_BOUND = P_MULTILINK_BOUND_TO_UNBOUND + 1;

   public final static int P_MULTILINK_ENTRY = P_MULTILINK_UNBOUND_TO_BOUND + 1;

   public final static int P_MULTILINK_PATH = P_MULTILINK_ENTRY + 1;

   public final static int P_TO_MANY = P_MULTILINK_PATH + 1;

   public final static int P_MULTILINK_ENTRY_OPTIONAL = P_TO_MANY + 1;

   public final static int P_MULTILINK_PATH_OPTIONAL = P_MULTILINK_ENTRY_OPTIONAL + 1;

   public final static int P_OPTIONAL = 20;

   public final static int P_OPTIONAL_CHECK = P_OPTIONAL;

   public final static int P_OPTIONAL_TO_ONE = P_OPTIONAL_CHECK + 1;

   public final static int P_OPTIONAL_TO_MANY = P_OPTIONAL_TO_ONE + 1;

   public final static int P_SET = 40;

   public final static int P_NEGATIVE = 60;

   public final static int P_NONE = -1;

   public final static int SEARCH = 0;

   public final static int MODIFY = SEARCH + 1;

   public final static String VIA_ASGELEMENTREF_SUFFIX = " (via ASGElementRef)";
   public static final String SOURCE_ROLE_PROPERTY = "sourceRole";
   public static final String TARGET_ROLE_PROPERTY = "targetRole";
   public static final String SOURCE_PROPERTY = "source";
   public static final String TARGET_PROPERTY = "target";
   public static final String REV_TARGET_LINK_PROPERTY = "revTargetLink";
   public static final String REV_SOURCE_LINK_PROPERTY = "revSourceLink";
   public static final String INSTANCE_OF_PROPERTY = "instanceOf";
   public static final String MODIFIER_PROPERTY = "modifier";
   public static final String TYPE_PROPERTY = "type";


   protected UMLLink(FProject project, boolean persistent)
   {
      super(project, persistent);
   }

   // --------------------------------- Attributes ------------------------------------

   /**
    * This attribute is needed to hide via ASGElementRef links is object diagrams. status: current
    * implementation ... DO NOT REMOVE ... (JPW)
    */
   private boolean isViaASGElementRef = false;


   public boolean getIsViaASGElementRef()
   {
      return isViaASGElementRef;
   }


   public void setIsViaASGElementRef(boolean value)
   {
      isViaASGElementRef = value;
   }


   private String name;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      if (name == null && this.instanceOf != null)
      {
         return this.instanceOf.getName();
      }
      return name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
      if (name == null || !name.equals(getName()))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange("name", oldValue, name);
      }
   }


   private int type = NULL;


   public int getType()
   {
      return type;
   }


   public void setType(int type)
   {
      if (NULL <= type && type <= OPTIONAL)
      {
         int oldValue = this.type;
         this.type = type;
         firePropertyChange(TYPE_PROPERTY, oldValue, type);
      }
   }


   private int modifier = NONE;


   public int getModifier()
   {
      return modifier;
   }


   public void setModifier(int modifier)
   {
      int oldValue = this.modifier;
      this.modifier = modifier;
      firePropertyChange(MODIFIER_PROPERTY, oldValue, modifier);
   }


   private boolean totality = false;


   public boolean getTotality()
   {
      return totality;
   }


   public void setTotality(boolean totality)
   {
      boolean oldValue = this.totality;
      this.totality = totality;
      firePropertyChange("totality", oldValue, totality);
   }


   private String range;


   public String getRange()
   {
      return range;
   }


   public void setRange(String range)
   {
      String oldValue = this.range;
      this.range = range;
      firePropertyChange("range", oldValue, range);
   }

   
   private boolean reflective;

   
   public boolean isReflective ()
   {
      return reflective;
   }


   public void setReflective ( boolean reflective )
   {
      if (reflective != this.reflective)
      {
         this.reflective = reflective;
         firePropertyChange("reflective", !reflective, reflective);
      }
   }   
  
   
   // ------------------------------- Associations ----------------------------------

   /**
    * <pre>
    *          0..n    source    0..1
    * UMLLink ------------------------ UMLObject
    *          revSource       source
    * </pre>
    */
   @Property( name = SOURCE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.REV_SOURCE_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject source;


   public UMLObject getSource()
   {
      return source;
   }


   public boolean setSource(UMLObject source)
   {
      boolean changed = false;

      if ((this.source == null && source != null)
            || (this.source != null && !this.source.equals(source)))
      {
         // new partner
         UMLObject oldSource = this.source;
         FRole oldRole = getSourceRole();
         if (oldSource != null)
         {
            // inform old partner
            this.source = null;
            oldSource.removeFromRevSource(this);
         }
         this.source = source;
         if (source != null)
         {
            // inform new partner
            source.addToRevSource(this);
         }
         firePropertyChange(SOURCE_PROPERTY, oldSource, source);
         FRole newRole = getSourceRole();
         if (newRole != oldRole)
         {
            firePropertyChange(SOURCE_ROLE_PROPERTY, oldRole, newRole);
         }
         changed = true;
      }

      return changed;
   }


   /**
    * <pre>
    *          0..n    target    0..1
    * UMLLink ------------------------ UMLObject
    *          revTarget       target
    * </pre>
    */
   @Property( name = TARGET_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.REV_TARGET_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject target;


   public UMLObject getTarget()
   {
      return target;
   }


   public boolean setTarget(UMLObject target)
   {
      boolean changed = false;

      if ((this.target == null && target != null)
            || (this.target != null && !this.target.equals(target)))
      {
         // new partner
         UMLObject oldTarget = this.target;
         FRole oldRole = getTargetRole();
         if (this.target != null)
         {
            // inform old partner
            this.target = null;
            oldTarget.removeFromRevTarget(this);
         }
         this.target = target;
         if (target != null)
         {
            // inform new partner
            target.addToRevTarget(this);
         }
         firePropertyChange(TARGET_PROPERTY, oldTarget, target);
         FRole newRole = getTargetRole();
         if (newRole != oldRole)
         {
            firePropertyChange(TARGET_ROLE_PROPERTY, oldRole, newRole);
         }
         changed = true;
      }

      return changed;
   }


   /**
    * <pre>
    *          0..n    instanceOf    0..1
    * UMLLink ---------------------------- UMLAssoc
    *          instances       instanceOf
    * </pre>
    */
   @Property( name = INSTANCE_OF_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = FAssoc.INSTANCES_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FAssoc instanceOf;


   public void setInstanceOf(FAssoc instanceOf)
   {
      if (this.instanceOf != instanceOf)
      {
         // new partner
         FAssoc oldInstanceOf = this.instanceOf;
         if (this.instanceOf != null)
         {
            // inform old partner
            this.instanceOf = null;
            oldInstanceOf.removeFromInstances(this);
         }
         this.instanceOf = instanceOf;
         if (instanceOf != null)
         {
            // inform new partner
            instanceOf.addToInstances(this);
         }
         firePropertyChange(INSTANCE_OF_PROPERTY, oldInstanceOf, instanceOf);
         if (instanceOf != null)
         {
            setName(null);
         }
      }
   }


   public FAssoc getInstanceOf()
   {
      return instanceOf;
   }


   /**
    * <pre>
    *          0..1     sourceLink     0..1
    * UMLLink ------------------------------ UMLMultiLink
    *          revSourceLink     sourceLink
    * </pre>
    */
   @Property( name = REV_SOURCE_LINK_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLMultiLink.SOURCE_LINK_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLMultiLink revSourceLink;


   public void setRevSourceLink(UMLMultiLink revSourceLink)
   {
      if (this.revSourceLink != revSourceLink)
      {
         // new partner
         UMLMultiLink oldRevSourceLink = this.revSourceLink;
         if (this.revSourceLink != null)
         {
            // inform old partner
            this.revSourceLink = null;
            oldRevSourceLink.setSourceLink(null);
         }
         this.revSourceLink = revSourceLink;
         if (revSourceLink != null)
         {
            // inform new partner
            revSourceLink.setSourceLink(this);
         }
         firePropertyChange(REV_SOURCE_LINK_PROPERTY, oldRevSourceLink, revSourceLink);
      }
   }


   public UMLMultiLink getRevSourceLink()
   {
      return this.revSourceLink;
   }


   /**
    * <pre>
    *          0..1     targetLink     0..1
    * UMLLink ------------------------------ UMLMultiLink
    *          targetLink     revTargetLink
    * </pre>
    */
   @Property( name = REV_TARGET_LINK_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLMultiLink.TARGET_LINK_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLMultiLink revTargetLink;


   public void setRevTargetLink(UMLMultiLink revTargetLink)
   {
      if (this.revTargetLink != revTargetLink)
      {
         // new partner
         UMLMultiLink oldRevTargetLink = this.revTargetLink;
         if (this.revTargetLink != null)
         {
            // inform old partner
            this.revTargetLink = null;
            oldRevTargetLink.setTargetLink(null);
         }
         this.revTargetLink = revTargetLink;
         if (revTargetLink != null)
         {
            // inform new partner
            revTargetLink.setTargetLink(this);
         }
         firePropertyChange(REV_TARGET_LINK_PROPERTY, oldRevTargetLink, revTargetLink);
      }
   }


   public UMLMultiLink getRevTargetLink()
   {
      return this.revTargetLink;
   }


   // ------------------------------- Other Methods ---------------------------------------

   public UMLLink getSuccessorLink(HashMap objectsMap)
   {
      UMLLink succLink = null;
      UMLObject source = (UMLObject) objectsMap.get(getSource());
      UMLObject target = (UMLObject) objectsMap.get(getTarget());

      if (source != null && target != null && getModifier() != DELETE)
      {
         succLink = getProject().getFromFactories(UMLLink.class).create(
               isPersistent());
         succLink.setName(getName());
         succLink.setType(getType());
         succLink.setModifier(NONE);
         succLink.setRange(getRange());
         succLink.setSource(source);
         succLink.setTarget(target);
         succLink.setInstanceOf(getInstanceOf());
      }

      return succLink;
   }


   public boolean isReference()
   {
      FAssoc instOf = getInstanceOf();

      if (instOf != null)
      {
         return ((instOf.getLeftRole() != null && instOf.getLeftRole()
               .getAdornment() == FRole.REFERENCE) || (instOf.getRightRole() != null && instOf
               .getRightRole().getAdornment() == FRole.REFERENCE));
      }
      else
      {
         return false;
      }
   }


   public boolean isNavigable(UMLObject source)
   {
      return getCorrespondingRole(source).getAdornment() != FRole.REFERENCE;
   }


   private int getTargetsUpperBound(UMLObject target)
   {
      FClass objectInstance = null;
      if (target != null)
      {
         objectInstance = target.getInstanceOf();
      }

      if (objectInstance == null)
      {
         throw new RuntimeExceptionWithContext("UMLObject has no UMLClass: "
               + target, target);
      }

      FAssoc linkInstance = getInstanceOf();

      if (linkInstance == null)
      {
         throw new RuntimeExceptionWithContext("UMLLink has no UMLAssoc: "
               + getName(), this);
      }

      FRole role = getCorrespondingRole(target);
      int card = 1;

      if ((role != null) && (role.getCard() != null))
      {
         FCardinality cardinality = role.getCard();
         card = cardinality.getUpperBound();
      }
      return card;
   }


   public int getPriority(HashMap boundObjects, HashMap isomorphicBindings)
   {
      UMLObject sourceObject = (boundObjects.get(getSource().getID()) == getSource()) ? getSource()
            : getTarget();
      UMLObject targetObject = (getTarget() == sourceObject) ? getSource()
            : getTarget();
      int priority = P_NONE;
      int type = getType();

      if (boundObjects.get(sourceObject.getID()) != sourceObject)
      {
         throw new RuntimeExceptionWithContext(
               "The source object is not bound", sourceObject);
      }

      // check if the reference can be traversed
      if (isReference() && getSource() == targetObject
            && boundObjects.get(targetObject.getID()) != targetObject)
      {
         return P_NONE;
      }

      boolean typeCastNeeded = !targetObject.getInstanceOf().getName().equals(
            getCorrespondingRole(targetObject).getTarget().getName());
      boolean targetNegative = (targetObject.getType() == UMLObject.NEGATIVE);
      boolean targetBound = boundObjects.get(targetObject.getID()) != null;
      boolean boundSourceSet = (sourceObject.getType() == UMLObject.SET);
      int targetLinks = (targetObject.sizeOfRevSource() + targetObject
            .sizeOfRevTarget());
      boolean targetObjectHasOutGoingLinks = (targetLinks > 1);

      FClass targetClass = targetObject.getInstanceOf();
      String targetClassName = targetClass.getFullClassName();
      boolean targetIsoCheck = (!targetBound && (isomorphicBindings
            .get(targetClassName) != null));

      if (type == NEGATIVE
            || (targetNegative && !targetBound
                  && !(targetObject.getType() == UMLObject.SET)
                  && !targetObjectHasOutGoingLinks && !targetIsoCheck))
      {
         if (getRevSourceLink() != null || getRevTargetLink() != null)
         {
            return priority = P_NONE;
         }

         if (!targetBound && targetObjectHasOutGoingLinks && !targetNegative)
         {
            if (!isReference())
            {
               return priority = P_NONE;
            }
         }
         if ((!targetBound && !FRoleUtility.isToOneAccess(getCorrespondingRole(targetObject)) && (targetObject
               .iteratorOfAttrs().hasNext() || typeCastNeeded))
               || boundSourceSet || (boundSourceSet && targetBound))
         {
            if (targetNegative)
            {
               return priority = P_CHECK_TO_MANY + P_OPTIONAL;
            }
            else
            {
               return priority = P_CHECK_TO_MANY;
            }
         }
         return priority = P_CHECK;
      }

      if (((getModifier() == CREATE && getType() != OPTIONAL) || (targetObject
            .getModifier() == UMLObject.CREATE)
            && targetObject.getType() != UMLObject.OPTIONAL)
            && (priority != P_CHECK))
      {
         return P_NONE;
      }

      priority = P_CHECK;

      if (!boundObjects.containsValue(targetObject))
      {
         if (getTargetsUpperBound(targetObject) == 1)
         {
            if (getCorrespondingRole(sourceObject).getQualifier() != null
                  && (getRange() == null || getRange().trim().equals("")))
            {
               priority += P_TO_MANY;
            }
            else
            {
               if (!(targetObject.getType() == UMLObject.SET))
               {
                  priority += P_TO_ONE;
               }
               else
               {
                  priority += P_TO_MANY;
               }
            }
         }
         else
         {
            priority += P_TO_MANY;
         }
      }

      if (targetObject.isOptional() || getType() == OPTIONAL)
      {
         priority += P_OPTIONAL;
      }

      if ((targetObject.getType() == UMLObject.SET)
            || (sourceObject.getType() == UMLObject.SET))
      {
         priority += P_SET;
      }

      return priority;
   }


   public UMLObject getUnboundObject(HashMap boundObjects)
   {
      UMLObject source = (UMLObject) boundObjects.get(getSource().getID());
      UMLObject target = (UMLObject) boundObjects.get(getTarget().getID());

      if (source == null && target == null)
      {
         throw new RuntimeExceptionWithContext(
               "Neither source nor target object is bound", this);
      }

      if (source != null)
      {
         return getTarget();
      }
      else
      {
         if (target != null)
         {
            return getSource();
         }
         else
         {
            throw new RuntimeExceptionWithContext("This link is a checklink",
                  this);
         }
      }
   }


   /**
    * Wrapper method for getName.
    * 
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return getName();
   }


   public int getAbsoluteModifier()
   {
      int result = getModifier();
      result |= (getSource() == null ? 0 : getSource().getModifier());
      result |= (getTarget() == null ? 0 : getTarget().getModifier());

      if ((result != CREATE) && (result != DELETE) && (result != NONE))
      {
         throw new RuntimeExceptionWithContext("Error: Link " + this
               + " connects to a create and to a delete object", this);
      }

      return result;
   }


   /**
    * Wrapper method for setSource.
    * 
    * @throws IllegalArgumentException if argument is not instance of UMLObject
    * @see #setSource
    */
   @Override
   public boolean setSourceConnector(UMLDiagramItem incr)
   {
      if (!(incr instanceof UMLObject))
      {
         throw new IllegalArgumentException(
               "Argument is not instance of UMLObject.");
      }

      return setSource((UMLObject) incr);
   }


   /**
    * Wrapper method for getSource.
    * 
    * @see #getSource
    */
   @Override
   @NoProperty
   public UMLDiagramItem getSourceConnector()
   {
      return getSource();
   }


   /**
    * Wrapper method for setTarget.
    * 
    * @see #setTarget
    */
   @Override
   @NoProperty
   public boolean setTargetConnector(UMLDiagramItem incr)
   {
      if (!(incr instanceof UMLObject))
      {
         throw new IllegalArgumentException("Argument is no UMLObject");
      }
      return setTarget((UMLObject) incr);
   } // setTargetConnector


   /**
    * Wrapper method for getTarget.
    * 
    * @see #getTarget
    */
   @Override
   @NoProperty
   public UMLDiagramItem getTargetConnector()
   {
      return getTarget();
   }


   /**
    * Checks whether this link connects the two objects given by their names.
    */
   public boolean isLinkBetween(String firstObject, String secondObject)
   {
      return ((getSource().getObjectName().equals(firstObject) && getTarget()
            .getObjectName().equals(secondObject)) || (getSource()
            .getObjectName().equals(secondObject) && getTarget()
            .getObjectName().equals(firstObject)));
   }


   public FRole getCorrespondingRole(UMLObject object)
   {
      if (object == null || getSource() == null || getTarget() == null
            || getInstanceOf() == null)
      {
         return null;
      }
      FRole correspondingRole = null;
      FAssoc myAssoc = getInstanceOf();

      if (object == getSource())
      {
         if (myAssoc.getDirection() == FAssoc.LEFTRIGHT)
         {
            correspondingRole = myAssoc.getLeftRole();
         }
         else
         {
            correspondingRole = myAssoc.getRightRole();
         }
      }
      else if (object == getTarget())
      {
         if (myAssoc != null)
         {
            if (myAssoc.getDirection() == FAssoc.LEFTRIGHT)
            {
               correspondingRole = myAssoc.getRightRole();
            }
            else
            {
               correspondingRole = myAssoc.getLeftRole();
            }
         }
         else
         {
            String name = getName();
            if (name == null || name.indexOf("!broken!") < 0)
            {
               setName("!broken! (" + name + ")");
            }
            throw new RuntimeExceptionWithContext(
                  "UMLLink '"
                        + name
                        + "' has no association! The link is broken and should be removed. Broken links have a red highlighted name.",
                  this);
         }
      }
      else
      {
         throw new RuntimeExceptionWithContext("UMLLink: UMLObject '"
               + object.getObjectName() + "' does not belong to UMLLink '"
               + getName() + "'", this);
      }

      return correspondingRole;
   }


   public FRole getSourceRole()
   {
      if (getSource() == null)
      {
         return null;
      }

      FRole role = null;
      try
      {
         role = getCorrespondingRole(getSource());
      }
      catch (UnsupportedOperationException e)
      {
      }

      return role;
   }


   public FRole getTargetRole()
   {
      if (getTarget() == null)
      {
         return null;
      }

      FRole role = null;
      try
      {
         role = getCorrespondingRole(getTarget());
      }
      catch (UnsupportedOperationException e)
      {
      }

      return role;
   }


   public boolean accessable(UMLObject object)
   {
      return !((isReference()) && (getTarget() != object));
   }


   public boolean checkNegativeError(UMLObject sourceObject,
         UMLObject targetObject, int priority)
   {
      boolean sourceBound = sourceObject.isBound();
      boolean targetBound = targetObject.isBound();
      boolean targetOptional = targetObject.isOptional();
      boolean sourceNegative = (sourceObject.getType() == UMLObject.NEGATIVE);
      boolean targetNegative = (targetObject.getType() == UMLObject.NEGATIVE);
      boolean linkNegative = getType() == NEGATIVE;
      boolean sourceHasUMLAttrExprPair = sourceObject.iteratorOfAttrs()
            .hasNext();
      boolean targetHasUMLAttrExprPair = targetObject.iteratorOfAttrs()
            .hasNext();

      boolean result = false;

      switch (priority)
      {
         case P_CHECK:
         case P_CHECK_TO_MANY:
         {
            if ((sourceBound && sourceNegative && !sourceHasUMLAttrExprPair)
                  || (targetBound && targetNegative && !targetHasUMLAttrExprPair)
                  || (linkNegative && targetNegative && !targetBound)
                  || (targetOptional && targetNegative && !targetHasUMLAttrExprPair))
            {
               result = true;
            }
         }
            break;
         case P_TO_ONE:
         case P_TO_MANY:
         {
            if ((sourceBound && sourceNegative && !sourceHasUMLAttrExprPair)
                  || (targetBound && targetNegative && !targetHasUMLAttrExprPair)
                  || (linkNegative))
            {
               result = true;
            }
         }
            break;
      }

      return result;
   }

   @Override
   public FCodeStyle getInheritedCodeStyle ()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FElement instanceOf = getInstanceOf();
      if (instanceOf != null)
      {
         return instanceOf.getInheritedCodeStyle();
      }
      return null;
   }

   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLLink[name=");
      result.append(getName());
      result.append(",type=");
      result.append(getType());
      result.append(",modifier=");
      result.append(getModifier());
      result.append(",sourceObject=");
      result.append(getSource());
      result.append(",targetObject=");
      result.append(getTarget());
      result.append(",instanceof=");
      result.append(getInstanceOf());
      result.append("]");

      return result.toString();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      setInstanceOf(null);
      setSource(null);
      setTarget(null);

      if (getRevSourceLink() != null)
      {
         getRevSourceLink().removeYou();
      }
      if (getRevTargetLink() != null)
      {
         getRevTargetLink().removeYou();
      }

      setRevSourceLink(null);
      setRevTargetLink(null);

      super.removeYou();
   }


   @Override
   public String getQualifiedDisplayName()
   {
      FElement parentElement = this;
      try
      {
         while ( parentElement != null && !(parentElement instanceof FMethod) )
         {
            parentElement = parentElement.getParentElement();
         }
      } catch (UnsupportedOperationException e)
      {
         parentElement = null;
      }
      if ( parentElement != null )
      {
         return getName() + " from " + parentElement.getQualifiedDisplayName();
      }
      else
      {
         return getName() + " in " + getProject().getName();
      }
   }
}

/*
 * $Log$
 * Revision 1.18  2007/03/21 12:47:47  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.17  2007/03/02 14:10:56  fklar
 * added documentation for types and modifiers
 *
 * Revision 1.16  2006/08/22 22:51:34  zuendorf
 * Added FMM.create(proj, class) to shorten the creation of metamodel objects.
 * Linkeditor selects objects in correct order now.
 * Added some flags guiding the code generation for Story Patterns and objects.
 * Added some convinience to UMLTransition.
 *
 * Revision 1.15  2006/05/05 08:58:06  lowende
 * Removed deprecated calls to UMLObject.getObjectType and setObjectType.
 *
 * Revision 1.14  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.13  2006/03/06 14:49:41  lowende
 * AGElement.setCutCopyPasteParent removed.
 * FProject.findClass removed again.
 * FProject.elementsOfClasses removed.
 * Some enumerationOf... removed ->use iteratorOf...
 * Some unused methods in UMLAssoc removed.
 *
 */
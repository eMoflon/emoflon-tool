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


import java.util.Collection;
import java.util.Iterator;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.FTextReference;
import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.behavior.FInstanceElement;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;
import de.upb.tools.sdm.JavaSDM;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *            0..1     returnParam      0..1
 * UMLObject -------------------------------- UMLStopActivity
 *            returnParam     revReturnParam
 *
 *            0..n     instanceOf     0..1
 * UMLObject ------------------------------ UMLClass
 *            instances         instanceOf
 *
 *            0..1     source     0..n
 * UMLObject -------------------------- UMLLink
 *            source         revSource
 *
 *            0..1     target     0..n
 * UMLObject -------------------------- UMLLink
 *            target         revTarget
 *
 *            0..1     toSetObject     0..n
 * UMLObject ------------------------------- UMLLinkSet
 *            toSetObject    revToSetObject
 *
 *            0..1     attrs     0..n
 * UMLObject ------------------------- UMLAttrExprPair
 *            revAttrs          attrs
 *
 *            0..1    callTarget     0..n
 * UMLObject ----------------------------- UMLCollabStat
 *            callTarget      collabStats
 *
 *            0..1     callSource   0..n      
 * UMLObject ---------------------------- UMLCollabStat 
 *            callSource      collabStat
 *
 *            0..n    boundTo    0..1
 * UMLObject ------------------------- UMLObject
 *            bindings        boundTo
 *
 *            0..1       containerObject       0..n
 * UMLObject --------------------------------------- UMLMultiLink
 *            containerObject    revContainerObject
 *
 * </pre>
 *
 * Valid combinations of modifier and type (X=valid/O=invalid):
 *
 * <pre>
 * modifier\type  |  normal  |  optional  |  negative  |  set
 * -------------------------------------------------------------
 * none           |    X     |      X     |      X     |   X
 * -------------------------------------------------------------
 * create         |    X     |      X     |      O     |   O
 * -------------------------------------------------------------
 * delete         |    X     |      X     |      O     |   X
 * </pre>
 *
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLObject extends UMLDiagramItem implements FInstanceElement, FTextReference, FParsedElement
{
   /**
    * Type
    *
    * @see #setType
    * @see #getType
    */
   public static final int NORM = 0;

   /**
    * Type
    *
    * @see #setType
    * @see #getType
    */
   public static final int OPTIONAL = 1;

   /**
    * Type
    *
    * @see #setType
    * @see #getType
    */
   public static final int NEGATIVE = 2;

   /**
    * Type
    *
    * @see #setType
    * @see #getType
    */
   public static final int SET = 3;

   /**
    * Modifier
    *
    * @see #setModifier
    * @see #getModifier
    */
   public static final int NONE = UMLStoryPattern.NONE;

   /**
    * Modifier
    *
    * @see #setModifier
    * @see #getModifier
    */
   public static final int CREATE = UMLStoryPattern.CREATE;

   /**
    * Modifier
    *
    * @see #setModifier
    * @see #getModifier
    */
   public static final int DELETE = UMLStoryPattern.DELETE;
   public static final String INSTANCE_OF_PROPERTY = "instanceOf";
   public static final String REV_SOURCE_PROPERTY = "revSource";
   public static final String REV_TARGET_PROPERTY = "revTarget";
   public static final String REV_TO_SET_OBJECT_PROPERTY = "revToSetObject";
   public static final String ATTRS_PROPERTY = "attrs";
   public static final String COLLAB_STATS_PROPERTY = "collabStats";
   public static final String CALL_SOURCE_COLLAB_STATS_PROPERTY = "callSourceCollabStats";
   public static final String BOUND_TO_PROPERTY = "boundTo";
   public static final String BINDINGS_PROPERTY = "bindings";
   public static final String REV_CONTAINER_OBJECT_PROPERTY = "revContainerObject";
   public static final String MODIFIER_PROPERTY = "modifier";
   public static final String TYPE_PROPERTY = "type";


   protected UMLObject(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   protected String objectName = "";


   public String getObjectName()
   {
      return objectName;
   }


   public void setObjectName(String objectName)
   {
      if ((this.objectName == null && objectName != null)
            || (this.objectName != null && !this.objectName.equals(objectName)))
      {
         String oldValue = this.objectName;

         this.objectName = objectName;

         firePropertyChange("objectName", oldValue, this.objectName);
      }
   }

   private String reflectiveTypeExpr;
   
   
   public String getReflectiveTypeExpr ()
   {
      return reflectiveTypeExpr;
   }


   public void setReflectiveTypeExpr ( String reflectiveTypeExpr )
   {
      if ((this.reflectiveTypeExpr == null && reflectiveTypeExpr != null)
            || (this.reflectiveTypeExpr != null && !this.reflectiveTypeExpr.equals(reflectiveTypeExpr)))
      {
         String oldValue = this.reflectiveTypeExpr;
         
         this.reflectiveTypeExpr = reflectiveTypeExpr;
         
         firePropertyChange("reflectiveTypeExpr", oldValue, this.reflectiveTypeExpr);
      }
   }


   private String currentIcon;


   public String getCurrentIcon()
   {
      if ((this.currentIcon == null) && (this.getInstanceOf() != null))
      {
         return this.getInstanceOf().getDefaultIcon();
      }
      else
      {
         return this.currentIcon;
      }
   }


   public void setCurrentIcon(String newVal)
   {
      if ( !JavaSDM.stringEquals(this.currentIcon, newVal) )
      {
         String oldVal = this.currentIcon;
         this.currentIcon = newVal;
         firePropertyChange("currentIcon", oldVal, newVal);
      }
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLObject[");
      result.append(getObjectName());
      if ( getInstanceOf() != null ) {
         result.append(": ");
         result.append(getInstanceOf().getName());
      }
      result.append(", instanceOf=");
      result.append(getInstanceOf());
      result.append("]");

      return result.toString();
   }


   /**
    * This method returns the full qualified ObjectName. If the UMLObject is bound this method
    * returns only the objectName, otherwise it will return the objectName + : + objectType.
    */
   public String getFullQualifiedName()
   {
      String newText = getObjectName();

      if (!isBound())
      {
      	if (getInstanceOf() != null)
      	{
      		newText += ": " + getInstanceOf().getName();
      	}
      	else
      	{
      		newText += ": no class";
      	}
      }

      return newText;
   }


   private int type = NORM;


   public int getType()
   {
      return type;
   }


   public void setType(int type)
   {
      if (NORM <= type && type <= SET && this.type != type)
      {
         int oldValue = this.type;
         this.type = type;
         firePropertyChange(TYPE_PROPERTY, oldValue, type);
      }
   }


   public boolean isOptional()
   {
      return (getType() == OPTIONAL || getType() == SET);
   }


   public boolean isOptionalAtPost()
   {
      return (isOptional() && !(getModifier() == CREATE));
   }


   public boolean isSet()
   {
      return (getType() == SET);
   }


   private boolean bound = false;


   public boolean isBound()
   {
      return bound;
   }


   public void setBound(boolean bound)
   {
      if (this.bound != bound)
      {
         boolean oldValue = this.bound;
         this.bound = bound;
         firePropertyChange("bound", oldValue, bound);
      }
   }


   /**
    * The boolean value is used only for bound objects for type casting before executing the story
    * pattern.
    */
   private boolean checkTypeCast = false;


   public boolean isCheckTypeCast()
   {
      return checkTypeCast;
   }


   public void setCheckTypeCast(boolean newValue)
   {
      if (this.checkTypeCast != newValue)
      {
         boolean oldValue = this.checkTypeCast;
         this.checkTypeCast = newValue;
         firePropertyChange("checkTypeCast", oldValue, newValue);
      }
   }


   private String typeCastSource;


   public void setTypeCastSource(String newValue)
   {
      if (this.typeCastSource != newValue)
      {
         String oldValue = this.typeCastSource;
         this.typeCastSource = newValue;
         firePropertyChange("typeCastSource", oldValue, newValue);
      }
   }


   public String getTypeCastSource()
   {
      return this.typeCastSource;
   }


   private int modifier = NONE;


   /**
    * @return one of NONE, CREATE, DELETE
    */
   public int getModifier()
   {
      return modifier;
   }


   /**
    * @param modifier one of NONE, CREATE, DELETE
    */
   public void setModifier(int modifier)
   {
      if (this.modifier != modifier)
      {
         int oldValue = this.modifier;
         this.modifier = modifier;
         firePropertyChange(MODIFIER_PROPERTY, oldValue, modifier);
      }
   }


   private String constructionExpression;


   /**
    * This expression will be used in code generation for construct the instance of this UMLObject.
    */
   public void setConstructionExpression(String value)
   {
      String oldValue = this.constructionExpression;
      this.constructionExpression = value;
      firePropertyChange("constructionExpression", oldValue, value);
   }


   public String getConstructionExpression()
   {
      return this.constructionExpression;
   }


   /**
    * <pre>
    *            0..n     instanceOf     0..1
    * UMLObject ------------------------------ UMLClass
    *            instances         instanceOf
    * </pre>
    */
   @Property( name = INSTANCE_OF_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = FClass.INSTANCES_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FClass instanceOf;


   public FClass getInstanceOf()
   {
      return instanceOf;
   }


   public void setInstanceOf(FClass instanceOf)
   {
      if (this.instanceOf != instanceOf)
      {
         // new partner
         FClass oldInstanceOf = this.instanceOf;
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
      }
   }


   /**
    * <pre>
    *            0..1     source     0..n
    * UMLObject -------------------------- UMLLink
    *            source         revSource
    * </pre>
    */
   @Property( name = REV_SOURCE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLLink.SOURCE_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FPropHashSet revSource;


   public boolean hasInRevSource(UMLLink value)
   {
      return ((this.revSource != null) && (value != null) && this.revSource
            .contains(value));
   }


   public Iterator iteratorOfRevSource()
   {
      return ((this.revSource == null) ? FEmptyIterator.get() : this.revSource
            .iterator());
   }


   public int sizeOfRevSource()
   {
      return ((this.revSource == null) ? 0 : this.revSource.size());
   }


   public boolean addToRevSource(UMLLink value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revSource == null)
         {
            this.revSource = new FPropHashSet(this, REV_SOURCE_PROPERTY);
         }
         changed = this.revSource.add(value);
         if (changed)
         {
            value.setSource(this);
         }
      }
      return changed;
   }


   public boolean removeFromRevSource(UMLLink value)
   {
      boolean changed = false;
      if ((this.revSource != null) && (value != null))
      {
         changed = this.revSource.remove(value);
         if (changed)
         {
            value.setSource(null);
         }
      }
      return changed;
   }


   public void removeAllFromRevSource()
   {
      UMLLink tmpValue;
      Iterator iter = this.iteratorOfRevSource();
      while (iter.hasNext())
      {
         tmpValue = (UMLLink) iter.next();
         tmpValue.removeYou();
      }
   }


   /**
    * <pre>
    *            0..1     target     0..n
    * UMLObject -------------------------- UMLLink
    *            target         revTarget
    * </pre>
    */
   @Property( name = REV_TARGET_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLLink.TARGET_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FPropHashSet revTarget;


   public boolean hasInRevTarget(UMLLink value)
   {
      return ((this.revTarget != null) && (value != null) && this.revTarget
            .contains(value));
   }


   public Iterator iteratorOfRevTarget()
   {
      return ((this.revTarget == null) ? FEmptyIterator.get() : this.revTarget
            .iterator());
   }


   public int sizeOfRevTarget()
   {
      return ((this.revTarget == null) ? 0 : this.revTarget.size());
   }


   public boolean addToRevTarget(UMLLink value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revTarget == null)
         {
            this.revTarget = new FPropHashSet(this, REV_TARGET_PROPERTY);
         }
         changed = this.revTarget.add(value);
         if (changed)
         {
            value.setTarget(this);
         }
      }
      return changed;
   }


   public boolean removeFromRevTarget(UMLLink value)
   {
      boolean changed = false;
      if ((this.revTarget != null) && (value != null))
      {
         changed = this.revTarget.remove(value);
         if (changed)
         {
            value.setTarget(null);
         }
      }
      return changed;
   }


   public void removeAllFromRevTarget()
   {
      UMLLink tmpValue;
      Iterator iter = this.iteratorOfRevTarget();
      while (iter.hasNext())
      {
         tmpValue = (UMLLink) iter.next();
         tmpValue.removeYou();
      }
   }


   /**
    * <pre>
    *            0..1     toSetObject     0..n
    * UMLObject ------------------------------- UMLLinkSet
    *            toSetObject    revToSetObject
    * </pre>
    */

   @Property( name = REV_TO_SET_OBJECT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLLinkSet.TO_SET_OBJECT_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FPropHashSet revToSetObject;


   public boolean hasInRevToSetObject(UMLLinkSet value)
   {
      return ((this.revToSetObject != null) && (value != null) && this.revToSetObject
            .contains(value));
   }


   public Iterator iteratorOfRevToSetObject()
   {
      return ((this.revToSetObject == null) ? FEmptyIterator.get()
            : this.revToSetObject.iterator());
   }


   public int sizeOfRevToSetObject()
   {
      return ((this.revToSetObject == null) ? 0 : this.revToSetObject.size());
   }


   public boolean addToRevToSetObject(UMLLinkSet value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revToSetObject == null)
         {
            this.revToSetObject = new FPropHashSet(this, REV_TO_SET_OBJECT_PROPERTY);
         }
         changed = this.revToSetObject.add(value);
         if (changed)
         {
            value.setToSetObject(this);
         }
      }
      return changed;
   }


   public boolean removeFromRevToSetObject(UMLLinkSet value)
   {
      boolean changed = false;
      if ((this.revToSetObject != null) && (value != null))
      {
         changed = this.revToSetObject.remove(value);
         if (changed)
         {
            value.setToSetObject(null);
         }
      }
      return changed;
   }


   public void removeAllFromRevToSetObject()
   {
      UMLLinkSet tmpValue;
      Iterator iter = this.iteratorOfRevToSetObject();
      while (iter.hasNext())
      {
         tmpValue = (UMLLinkSet) iter.next();
         tmpValue.removeYou();
      }
   }


   /**
    * <pre>
    *            0..1     attrs     0..n
    * UMLObject ------------------------- UMLAttrExprPair
    *            revAttrs          attrs
    * </pre>
    */
   @Property( name = ATTRS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLAttrExprPair.REV_ATTRS_PROPERTY, adornment = ReferenceHandler.Adornment.COMPOSITION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FPropHashSet attrs;


   public boolean hasInAttrs(UMLAttrExprPair value)
   {
      return ((this.attrs != null) && (value != null) && this.attrs
            .contains(value));
   }


   public Iterator iteratorOfAttrs()
   {
      return ((this.attrs == null) ? FEmptyIterator.get() : this.attrs
            .iterator());
   }


   public int sizeOfAttrs()
   {
      return ((this.attrs == null) ? 0 : this.attrs.size());
   }


   public boolean addToAttrs(UMLAttrExprPair value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.attrs == null)
         {
            this.attrs = new FPropHashSet(this, ATTRS_PROPERTY);
         }
         changed = this.attrs.add(value);
         if (changed)
         {
            value.setRevAttrs(this);
         }
      }
      return changed;
   }


   public boolean removeFromAttrs(UMLAttrExprPair value)
   {
      boolean changed = false;
      if ((this.attrs != null) && (value != null))
      {
         changed = this.attrs.remove(value);
         if (changed)
         {
            value.setRevAttrs(null);
         }
      }
      return changed;
   }


   public void removeAllFromAttrs()
   {
      UMLAttrExprPair tmpValue;
      Iterator iter = this.iteratorOfAttrs();
      while (iter.hasNext())
      {
         tmpValue = (UMLAttrExprPair) iter.next();
         tmpValue.removeYou();
      }
   }


   /**
    * <pre>
    *            0..1    callTarget     0..n
    * UMLObject ----------------------------- UMLCollabStat
    *            callTarget      collabStats
    * </pre>
    */
   @Property( name = COLLAB_STATS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLCollabStat.CALL_TARGET_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private transient FPropHashSet collabStats;


   public boolean hasInCollabStats(UMLCollabStat value)
   {
      return ((this.collabStats != null) && (value != null) && this.collabStats
            .contains(value));
   }


   public Iterator iteratorOfCollabStats()
   {
      return ((this.collabStats == null) ? FEmptyIterator.get()
            : this.collabStats.iterator());
   }


   public int sizeOfRightRoles()
   {
      return ((this.collabStats == null) ? 0 : this.collabStats.size());
   }


   public boolean addToCollabStats(UMLCollabStat value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.collabStats == null)
         {
            this.collabStats = new FPropHashSet(this, COLLAB_STATS_PROPERTY);
         }
         changed = this.collabStats.add(value);
         if (changed)
         {
            value.setCallTarget(this);
         }
      }
      return changed;
   }


   public boolean removeFromCollabStats(UMLCollabStat value)
   {
      boolean changed = false;
      if ((this.collabStats != null) && (value != null))
      {
         changed = this.collabStats.remove(value);
         if (changed)
         {
            value.setCallTarget(null);
         }
      }
      return changed;
   }


   public void removeAllFromCollabStats()
   {
      UMLCollabStat tmpValue;
      Iterator iter = this.iteratorOfCollabStats();
      while (iter.hasNext())
      {
         tmpValue = (UMLCollabStat) iter.next();
         this.removeFromCollabStats(tmpValue);
      }
   }


   /**
    * <pre>
    *            0..1     callSource   0..n      
    * UMLObject ---------------------------- UMLCollabStat 
    *            callSource      collabStat
    * </pre>
    */
   @Property( name = CALL_SOURCE_COLLAB_STATS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLCollabStat.CALL_SOURCE_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private transient FPropHashSet callSourceCollabStats;


   public boolean hasInCallSourceCollabStats(UMLCollabStat value)
   {
      return ((this.callSourceCollabStats != null) && (value != null) && this.callSourceCollabStats
            .contains(value));
   }


   public Iterator iteratorOfCallSourceCollabStats()
   {
      return ((this.callSourceCollabStats == null) ? FEmptyIterator.get()
            : this.callSourceCollabStats.iterator());
   }


   public int sizeOfCallSourceCollabStats()
   {
      return ((this.callSourceCollabStats == null) ? 0
            : this.callSourceCollabStats.size());
   }


   public boolean addToCallSourceCollabStats(UMLCollabStat value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.callSourceCollabStats == null)
         {
            this.callSourceCollabStats = new FPropHashSet(this,
                  CALL_SOURCE_COLLAB_STATS_PROPERTY);

         }
         changed = this.callSourceCollabStats.add(value);
         if (changed)
         {
            value.setCallSource(this);
         }
      }
      return changed;
   }


   public boolean removeFromCallSourceCollabStats(UMLCollabStat value)
   {
      boolean changed = false;
      if ((this.callSourceCollabStats != null) && (value != null))
      {
         changed = this.callSourceCollabStats.remove(value);
         if (changed)
         {
            value.setCallSource(null);
         }
      }
      return changed;
   }


   public void removeAllFromCallSourceCollabStats()
   {
      UMLCollabStat tmpValue;
      Iterator iter = this.iteratorOfCallSourceCollabStats();
      while (iter.hasNext())
      {
         tmpValue = (UMLCollabStat) iter.next();
         this.removeFromCallSourceCollabStats(tmpValue);
      }

   }


   /**
    * <pre>
    *            0..1       containerObject       0..n
    * UMLObject --------------------------------------- UMLMultiLink
    *            containerObject    revContainerObject
    * </pre>
    */
   @Property( name = REV_CONTAINER_OBJECT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLMultiLink.CONTAINER_OBJECT_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FPropHashSet revContainerObject = null;


   public boolean addToRevContainerObject(UMLMultiLink value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revContainerObject == null)
         {
            this.revContainerObject = new FPropHashSet(this,
                  REV_CONTAINER_OBJECT_PROPERTY);
         }
         changed = this.revContainerObject.add(value);
         if (changed)
         {
            value.setContainerObject(this);
         }
      }
      return changed;
   }


   public boolean removeFromRevContainerObject(UMLMultiLink value)
   {
      boolean changed = false;
      if ((this.revContainerObject != null) && (value != null))
      {
         changed = this.revContainerObject.remove(value);
         if (changed)
         {
            value.setContainerObject(null);
         }
      }
      return changed;
   }


   public void removeAllFromRevContainerObject()
   {
      UMLMultiLink tmpValue;
      Iterator iter = this.iteratorOfRevContainerObject();
      while (iter.hasNext())
      {
         tmpValue = (UMLMultiLink) iter.next();
         tmpValue.removeYou();
      }
   }


   public Iterator iteratorOfRevContainerObject()
   {
      return ((this.revContainerObject == null) ? FEmptyIterator.get()
            : this.revContainerObject.iterator());
   }


   public boolean hasInRevContainerObject(UMLMultiLink value)
   {
      return ((this.revContainerObject != null) && (value != null) && this.revContainerObject
            .contains(value));
   }


   public int sizeOfRevContainerObject()
   {
      return ((this.revContainerObject == null) ? 0 : this.revContainerObject
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String newName)
   {
      setObjectName(newName);
   }

   @Override
   public String getName()
   {
      return getObjectName();
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return getObjectName();
   }

   private boolean noNotNullCheck;


	public void setNoNotNullCheck(boolean noTryCatch)
	{
		boolean oldValue = this.noNotNullCheck;
		this.noNotNullCheck = noTryCatch;
      firePropertyChange ("noNotNullCheck", oldValue, noTryCatch);
	}


	public boolean isNoNotNullCheck()
	{
		return noNotNullCheck;
	}



   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      removeAllFromAttrs();
      removeAllFromRevSource();
      removeAllFromRevTarget();
      removeAllFromRevToSetObject();
      removeAllFromCollabStats();
      removeAllFromRevContainerObject();
      removeAllFromTextUsages();

      setInstanceOf(null);

      super.removeYou();
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getName();
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
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      if(getModifier() == CREATE)
      {
         return "constructionExpression";
      }
      else
      {
         return "typeCastSource";
      }
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      if(getModifier() == CREATE)
      {
         return getConstructionExpression();
      }
      else
      {
         return getTypeCastSource();
      }
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      if(getModifier() == CREATE)
      {
         setConstructionExpression(value);
      }
      else
      {
         setTypeCastSource(value);
      }
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'expression' field for the type UMLAttrExprPair.
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

   @Override
   public UMLObjectDiagram getParentElement()
   {
      return (UMLObjectDiagram) super.getParentElement();
   }
   
   @Override
   public FCodeStyle getInheritedCodeStyle ()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FClass instanceOf = getInstanceOf();
      if (instanceOf != null)
      {
         return instanceOf.getInheritedCodeStyle();
      }
      return null;
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
         return getFullQualifiedName() + " from " + parentElement.getQualifiedDisplayName();
      }
      else
      {
         return getFullQualifiedName() + " in " + getProject().getName();
      }
   }
}

/*
 * $Log$
 * Revision 1.24  2007/03/21 12:47:47  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.23  2007/03/01 15:34:59  fklar
 * Optional Create enabled in GUI and Unparser (patch from Version_4_Maintainance-branch; see: creckord - 16 Feb 2006 09:25:41 +0000)
 *
 * Revision 1.22  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.21  2007/01/09 09:31:36  cschneid
 * cut/copy/paste dummys, requiredPlugins saved correctly with ctr
 *
 * Revision 1.20  2006/12/01 13:02:11  l3_g5
 * improved support for non persistent object creation
 *
 * Revision 1.19  2006/09/16 20:35:42  zuendorf
 * just a quick save
 *
 * Revision 1.18  2006/08/22 22:51:34  zuendorf
 * Added FMM.create(proj, class) to shorten the creation of metamodel objects.
 * Linkeditor selects objects in correct order now.
 * Added some flags guiding the code generation for Story Patterns and objects.
 * Added some convinience to UMLTransition.
 *
 * Revision 1.17  2006/05/05 08:58:06  lowende
 * Removed deprecated calls to UMLObject.getObjectType and setObjectType.
 *
 * Revision 1.16  2006/05/03 13:01:51  lowende
 * UMLObjects enhanced by construction expressions.
 * These expressions will be used for creating objects in generated code.
 * Can also be used to create objects with factories (e.g. Fujaba Metamodel objects).
 * Revision 1.15 2006/04/25 11:58:25 cschneid added deprecation expiration note, work on
 * versioning
 * 
 * Revision 1.14 2006/03/06 14:49:41 lowende AGElement.setCutCopyPasteParent removed.
 * FProject.findClass removed again. FProject.elementsOfClasses removed. Some enumerationOf...
 * removed ->use iteratorOf... Some unused methods in UMLAssoc removed.
 * 
 */

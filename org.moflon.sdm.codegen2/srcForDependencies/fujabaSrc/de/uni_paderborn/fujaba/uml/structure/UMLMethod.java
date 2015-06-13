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
package de.uni_paderborn.fujaba.uml.structure;


import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.asg.ASTRootNode;
import de.uni_paderborn.fujaba.basic.FujabaDebug;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.metamodel.structure.util.FMethodUtility;
import de.uni_paderborn.fujaba.parser.Parser;
import de.uni_paderborn.fujaba.parser.ParserManager;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FPropHashMap;
import de.upb.tools.fca.FPropHashSet;
import de.upb.tools.fca.FPropLinkedList;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * <h2>Associatons</h2>
 * 
 * <pre>
 *            1                    1 +---------------------+
 * UMLMethod ------------------------+ getFullMethodName() | UMLClass
 *            methods         parent +---------------------+
 * 
 *          +-----------+ 1                          1
 * UMLClass | getName() +------------------------------ UMLMethod
 *          +-----------+ declares    declaredInMethod
 * 
 *           0..1                           0..*
 * UMLAttr -------------------------------------- UMLMethod
 *          accessedAttribute      accessMethods
 * 
 *            0..1    astRootNode     0..1
 * UMLMethod ------------------------------ ASTRootNode
 *            uMLMethod        aSTRootNode
 * 
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLMethod extends UMLDeclaration implements FMethod
{

   protected UMLMethod(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   private String name = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
      if (this.name == null || !this.name.equals(name))
      {
         String oldValue = this.name;
         this.name = name;

         firePropertyChange(NAME_PROPERTY, oldValue, name);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return FMethodUtility.getText(this);
   }


   @Property(name = FMethod.STATIC_PROPERTY)
   private boolean umlStatic;


   /**
    * @deprecated use {@link #isStatic()} instead (gets deleted in 5.1)
    */
   @NoProperty
   public boolean isUmlStatic()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#isStatic()
    */
   public boolean isStatic()
   {
      return umlStatic;
   }


   /**
    * @deprecated use {@link #setStatic(boolean)} instead (gets deleted in 5.1)
    */
   @NoProperty
   public void setUmlStatic(boolean umlStatic)
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setStatic(boolean)
    */
   public void setStatic(boolean newValue)
   {
      boolean oldValue = this.umlStatic;
      this.umlStatic = newValue;
      firePropertyChange(FMethod.STATIC_PROPERTY, oldValue, newValue);
   }


   @Property(name = FMethod.ABSTRACT_PROPERTY)
   private boolean umlAbstract;


   /**
    * @deprecated use {@link #isAbstract()} instead (gets deleted in 5.1)
    */
   @NoProperty
   public boolean isUmlAbstract()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#isAbstract()
    */
   public boolean isAbstract()
   {
      return this.umlAbstract;
   }


   /**
    * @deprecated use {@link #setAbstract(boolean)} instead (gets deleted in 5.1)
    */
   @NoProperty
   public void setUmlAbstract(boolean umlAbstract)
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setAbstract(boolean)
    */
   public void setAbstract(boolean newValue)
   {
      boolean oldValue = this.umlAbstract;
      this.umlAbstract = newValue;

      if (this.umlAbstract && getParent() != null)
      {
         getParent().setAbstract(true);
      }

      firePropertyChange(ABSTRACT_PROPERTY, oldValue, newValue);
   }


   /**
    * <pre>
    *            1                    1 +---------------------+
    * UMLMethod ------------------------+ getFullMethodName() | UMLClass
    *            methods         parent +---------------------+
    * </pre>
    */
   private UMLClass parent;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getParent()
    */
   public UMLClass getParent()
   {
      return this.parent;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setParent(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public void setParent(FClass parent)
   {
      if (this.parent != parent)
      {
         UMLClass oldParent = this.parent;
         if (this.parent != null)
         {
            this.parent = null;
            oldParent.removeFromMethods(this);
         }
         this.parent = (UMLClass) parent;
         if (parent != null)
         {
            parent.addToMethods(this);
         }

         // side effects
         firePropertyChange(PARENT_PROPERTY, oldParent, parent);
      }
   }


   private UMLType resultType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getResultType()
    */
   public UMLType getResultType()
   {
      return resultType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setResultType(de.uni_paderborn.fujaba.metamodel.structure.FType)
    */
   public void setResultType(FType value)
   {
      if (this.resultType != value)
      {
         UMLType oldResultType = this.resultType;
         if (this.resultType != null)
         {
            this.resultType = null;
            oldResultType.removeFromRevResultType(this);
         }
         this.resultType = (UMLType) value;
         if (value != null)
         {
            ((UMLType) value).addToRevResultType(this);
         }

         firePropertyChange(RESULT_TYPE_PROPERTY, oldResultType, value);
      }
   }


   /**
    * <pre>
    *          +-----------+ 1                          1
    * UMLClass | getName() +------------------------------ UMLMethod
    *          +-----------+ declares    declaredInMethod
    * </pre>
    */
   private FPropHashMap declares;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#hasInDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean hasInDeclares(FClass obj)
   {
      return ((this.declares != null) && (obj != null)
            && (obj.getName() != null) && (this.declares.get(obj.getName()) == obj));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#hasKeyInDeclares(java.lang.String)
    */
   public boolean hasKeyInDeclares(String key)
   {
      return ((this.declares != null) && (key != null) && this.declares
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#iteratorOfDeclares()
    */
   public Iterator iteratorOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .values().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#keysOfDeclares()
    */
   public Iterator keysOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .keySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#entriesOfDeclares()
    */
   public Iterator entriesOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .entrySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#sizeOfDeclares()
    */
   public int sizeOfDeclares()
   {
      return ((this.declares == null) ? 0 : this.declares.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getFromDeclares(java.lang.String)
    */
   public UMLClass getFromDeclares(String key)
   {
      return (((this.declares == null) || (key == null)) ? null
            : (UMLClass) this.declares.get(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#addToDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean addToDeclares(FClass obj)
   {
      boolean changed = false;

      if ((obj != null) && (obj.getName() != null))
      {
         if (this.declares == null)
         {
            this.declares = new FPropHashMap(this, DECLARES_PROPERTY);
         }
         UMLClass oldValue = (UMLClass) this.declares.put(obj.getName(), obj);
         if (oldValue != obj)
         {
            if (oldValue != null)
            {
               oldValue.setDeclaredInMethod(null);
            }
            obj.setDeclaredInMethod(this);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeFromDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean removeFromDeclares(FClass obj)
   {
      boolean changed = false;

      if ((this.declares != null) && (obj != null) && (obj.getName() != null))
      {
         UMLClass oldValue = (UMLClass) this.declares.get(obj.getName());
         if (oldValue == obj)
         {
            this.declares.remove(obj.getName());
            obj.setDeclaredInMethod(null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeKeyFromDeclares(java.lang.String)
    */
   public boolean removeKeyFromDeclares(String key)
   {
      boolean changed = false;

      if ((this.declares != null) && (key != null))
      {
         UMLClass tmpObj = (UMLClass) this.declares.get(key);
         if (tmpObj != null)
         {
            this.declares.remove(key);
            tmpObj.setDeclaredInMethod(null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeAllFromDeclares()
    */
   public void removeAllFromDeclares()
   {
      UMLClass tmpObj;
      Iterator iter = this.iteratorOfDeclares();

      while (iter.hasNext())
      {
         tmpObj = (UMLClass) iter.next();
         this.removeFromDeclares(tmpObj);
      }
   }


   /**
    * Reverse UMLMethod revParam
    */
   private List<UMLParam> param = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#hasInParam(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean hasInParam(FParam newParam)
   {
      if (param == null)
      {
         return false;
      }
      return param.contains(newParam);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#sizeOfParam()
    */
   public int sizeOfParam()
   {
      if (param == null)
      {
         return 0;
      }
      return param.size();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#iteratorOfParam()
    */
   public Iterator<UMLParam> iteratorOfParam()
   {
      if (this.param == null)
      {
         return EmptyIterator.get();
      }
      return this.param.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#addToParam(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public void addToParam(FParam newParam)
   {
      if (newParam != null && !hasInParam(newParam))
      {
         if (param == null)
         {
            param = new FPropLinkedList(this, PARAM_PROPERTY);
         }
         param.add((UMLParam) newParam);
         newParam.setRevParam(this);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#addToParam(int,
    *      de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public void addToParam(int index, FParam newParam)
   {
      if (newParam != null && !hasInParam(newParam))
      {
         if (param == null)
         {
            param = new FPropLinkedList(this, PARAM_PROPERTY);
         }
         param.add(index, (UMLParam) newParam);
         newParam.setRevParam(this);
      }
   }


   /**
    * Moves the parameter to the given index.
    * 
    * @param index the parameter's target index
    * @param param the parameter to be moved
    * @return <code>true</code> if the operation was successful, <code>false</code> otherwise.
    */
   public boolean moveParamToIndex(int index, UMLParam param)
   {
      if (param != null && hasInParam(param) && index >= 0)
      {
         int oldIndex = this.param.indexOf(param);
         if (oldIndex != index && index <= this.param.size())
         {
            this.removeFromParam(param);
            this.addToParam(index, param);

            return true;
         }
      }
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeFromParam(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public void removeFromParam(FParam newParam)
   {
      if (newParam != null && hasInParam(newParam))
      {
         if (param.remove(newParam))
         {
            newParam.setRevParam(null);
         }
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeAllFromParam()
    */
   public final void removeAllFromParam()
   {
      Iterator<UMLParam> iter = iteratorOfParam();

      while (iter.hasNext())
      {
         UMLParam item = iter.next();
         removeFromParam(item);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getRevSpec()
    * @deprecated (gets deleted in 5.1)
    */
   @NoProperty
   public UMLStartActivity getRevSpec()
   {
      if (getStoryDiag() != null)
      {
         return ((UMLActivityDiagram) getStoryDiag()).getStartActivity();
      }
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setRevSpec(de.uni_paderborn.fujaba.metamodel.common.FElement)
    * @deprecated (gets deleted in 5.1)
    */
   @NoProperty
   public void setRevSpec(FElement revSpec)
   {
      UMLStartActivity activity = (UMLStartActivity) revSpec;
      setStoryDiag(activity.getActivityDiagram());
   }


   /**
    * reverse UMLMethod spec
    */
   @Property(name = STORY_DIAG_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
   // does not work as it returns FDiagram: partner=UMLActivityDiagram.STORY_METHOD_PROPERTY,
   adornment = ReferenceHandler.Adornment.COMPOSITION)
   private UMLActivityDiagram storyDiag;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getStoryDiag()
    */
   public FDiagram getStoryDiag()
   {
      return storyDiag;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setStoryDiag(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   public void setStoryDiag(FDiagram storyDiag)
   {
      if (this.storyDiag != storyDiag)
      { // new partner

         UMLActivityDiagram oldStoryDiag = this.storyDiag;
         if (this.storyDiag != null)
         { // inform old partner

            this.storyDiag = null;
            oldStoryDiag.setStoryMethod(null);
         }
         this.storyDiag = (UMLActivityDiagram) storyDiag;
         if (storyDiag != null)
         { // inform new partner

            this.storyDiag.setStoryMethod(this);
         }

         firePropertyChange(STORY_DIAG_PROPERTY, oldStoryDiag, storyDiag);
      }
   }


   private void removeStoryDiagram()
   {
      FDiagram diag = getStoryDiag();
      if (diag != null)
      {
         diag.removeYou();
      }
   }


   private String methodBody;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setMethodBody(java.lang.String)
    */
   @Property(name = "methodBody", kind = ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setMethodBody(String methodBody)
   {
      setMethodBody(methodBody, false);
   }


   /**
    * @param methodBody the new methodBody as text
    * @param createActivityDiagram true, if an activity diagram should be generated from the method
    *           body
    */
   public void setMethodBody(String methodBody, boolean createActivityDiagram)
   {
      this.methodBody = methodBody;

      if (this.methodBody != null && createActivityDiagram)
      {
         FMethodUtility.createActivityDiagram(this, this.getMethodBody(),
                this.isPersistent());
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getMethodBody()
    */
   public String getMethodBody()
   {
      return this.methodBody;
   }


   /**
    * <pre>
    *                   astRootNode     0..1
    * UMLMethod -----------------------------> ASTRootNode
    *                            astRootNode
    * </pre>
    */
   private transient SoftReference<ASTRootNode> astRootNodeReference;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getASTRootNode()
    */
   public ASTRootNode getASTRootNode()
   {
      ASTRootNode astRootNode = null;

      if ((astRootNodeReference == null || astRootNodeReference.get() == null)
            && getMethodBody() != null)
      {
         Parser parser = ParserManager.get().getCurrentParser();
         if (parser != null)
         {
            astRootNode = parser.parseMethodBody(this);
            setASTRootNode(astRootNode);
         }
      }
      else if (astRootNodeReference != null)
      {
         astRootNode = astRootNodeReference.get();
      }

      return astRootNode;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setASTRootNode(de.uni_paderborn.fujaba.asg.ASTRootNode)
    */
   public void setASTRootNode(ASTRootNode astRootNode)
   {
      if (astRootNode != null
            && (astRootNodeReference == null || astRootNodeReference.get() != astRootNode))
      {
         astRootNodeReference = new SoftReference<ASTRootNode>(astRootNode);
         astRootNode.setMethod(this);
      }
      else if (astRootNode == null)
      {
         astRootNodeReference = null;
      }
   }


   /**
    * <pre>
    *          n      throwsTypes  0..1
    * UMLType ------------------------- UMLMethod
    *          throws      throwMethod
    * </pre>
    */
   private FHashSet throwsTypes;


   public boolean hasInThrowsTypes(FType value)
   {
      return ((this.throwsTypes != null) && (value != null) && this.throwsTypes
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#iteratorOfThrowsTypes()
    */
   public Iterator iteratorOfThrowsTypes()
   {
      return ((this.throwsTypes == null) ? FEmptyIterator.get()
            : this.throwsTypes.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#sizeOfThrowsTypes()
    */
   public int sizeOfThrowsTypes()
   {
      return ((this.throwsTypes == null) ? 0 : this.throwsTypes.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#addToThrowsTypes(de.uni_paderborn.fujaba.metamodel.structure.FType)
    */
   public boolean addToThrowsTypes(FType value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.throwsTypes == null)
         {
            this.throwsTypes = new FPropHashSet(this, "throwsTypes");
         }
         changed = this.throwsTypes.add(value);
         if (changed)
         {
            // value.setThrowMethod (this);
         }
      }
      return changed;
   }


   public boolean removeFromThrowsTypes(FType value)
   {
      boolean changed = false;
      if ((this.throwsTypes != null) && (value != null))
      {
         changed = this.throwsTypes.remove(value);
         if (changed)
         {
            // value.setThrowMethod (null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#removeAllFromThrowsTypes()
    */
   public void removeAllFromThrowsTypes()
   {
      FType tmpValue;
      Iterator iter = this.iteratorOfThrowsTypes();
      while (iter.hasNext())
      {
         tmpValue = (FType) iter.next();
         this.removeFromThrowsTypes(tmpValue);
      }
   }


   /**
    * <pre>
    *          0..1                            0..*
    * UMLAttr -------------------------------------- UMLMethod
    *          accessedAttribute      accessMethods
    * </pre>
    */
   private UMLAttr accessedAttribute;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#setAccessedAttribute(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    * @deprecated (gets deleted in 5.1)
    */
   public boolean setAccessedAttribute(FAttr value)
   {
      boolean changed = false;

      if (this.accessedAttribute != value)
      {
         if (this.accessedAttribute != null)
         {
            UMLAttr oldValue = this.accessedAttribute;
            this.accessedAttribute = null;
            oldValue.removeFromAccessMethods(this);
         }

         this.accessedAttribute = (UMLAttr) value;

         if (value != null)
         {
            ((UMLAttr) value).addToAccessMethods(this);
         }

         changed = true;
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getAccessedAttribute()
    * @deprecated (gets deleted in 5.1)
    */
   public UMLAttr getAccessedAttribute()
   {
      return this.accessedAttribute;
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FClass getParentElement()
   {
      return getParent();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getFullMethodName()
    */
   public String getFullMethodName()
   {
      return FMethodUtility.getFullMethodName(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getMethodDecl()
    */
   public String getMethodDecl()
   {
      return FMethodUtility.getMethodDecl(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#getQualifiedMethodDecl()
    */
   public String getQualifiedMethodDecl()
   {
      return FMethodUtility.getQualifiedMethodDecl(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#iteratorOfOverriddenMethods()
    */
   public Iterator<? extends FMethod> iteratorOfOverriddenMethods()
   {
      return FMethodUtility.iteratorOfOverriddenMethods(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FMethod#iteratorOfOverridingMethods()
    */
   public Iterator<? extends FMethod> iteratorOfOverridingMethods()
   {
      return FMethodUtility.iteratorOfOverridingMethods(this);
   }


   public UMLActivityDiagram createStoryDiagram()
   {
      return FMethodUtility.createStoryDiagram(this);
   }


   /**
    * @deprecated (gets deleted in 5.1) For loading old project files only.
    */
   @SuppressWarnings("unused")
   private void setFinal(boolean javaFinal)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (javaFinal)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.FINAL));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.FINAL));
      }
      if (changed)
      {
         firePropertyChange("final", !javaFinal, javaFinal);
      }
   }


   /**
    * @deprecated (gets deleted in 5.1) For loading old project files only.
    */
   @SuppressWarnings("unused")
   private boolean isSignal()
   {
      return hasKeyInStereotypes(FStereotype.SIGNAL);
   }


   /**
    * @deprecated (gets deleted in 5.1) For loading old project files only.
    */
   @SuppressWarnings("unused")
   private void setSignal(boolean signal)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (signal)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.SIGNAL));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.SIGNAL));
      }
      if (changed)
      {
         firePropertyChange("signal", !signal, signal);
      }
   }


   /**
    * @deprecated (gets deleted in 5.1) For loading old project files only.
    */
   @SuppressWarnings("unused")
   private void setUmlNative(boolean umlNative)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      if (umlNative)
      {
         addToStereotypes(stereotypeFactory.getFromProducts(FStereotype.NATIVE));
      }
      else
      {
         removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.NATIVE));
      }
   }


   /**
    * @deprecated (gets deleted in 5.1) For loading old project files only.
    */
   @SuppressWarnings("unused")
   private void setResultTypeIsPointer(boolean pointer)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (pointer)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.POINTER));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.POINTER));
      }
      if (changed)
      {
         firePropertyChange("resultTypeIsPointer", !pointer, pointer);
      }
   }


   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      if (context != null && context.contains(getParent()))
      {
         getMethodDecl();
      }
      return getQualifiedMethodDecl();
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLMethod[");
      result.append(getFullMethodName());
      result.append("]");

      return result.toString();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.setParent(null);

      this.setResultType(null);
      this.removeAllFromDeclares();
      Iterator<UMLParam> paramIter = this.iteratorOfParam();
      while (paramIter.hasNext())
      {
         UMLParam param = paramIter.next();
         param.removeYou();
      }

      this.removeAllFromTextUsages();
      this.removeStoryDiagram();

      if (FujabaDebug.SETATTRSTONULL)
      {
         this.declares = null;
         this.param = null;
      }

      removeAllFromThrowsTypes();

      UMLAttr tmpAccessedAttribute = getAccessedAttribute();
      if (tmpAccessedAttribute != null)
      {
         setAccessedAttribute(null);
      }

      super.removeYou();
   }

   /**
    * Implementation of the 'referencedElement' association between FTextReference and UMLTextNode.
    * Since this association must be implemented by numerous classes, all operations are forwarded
    * to the FTextReferenceUtility class.
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
      if (this.textRefUtil == null)
      {
         this.textRefUtil = new FTextReferenceUtility(this);
      }
      return this.textRefUtil;
   }


   /**
    * Adds the given UMLTextNode instance to the set of text usages. (Forwarded to
    * FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean addToTextUsages(TextNode value)
   {
      return getTextRefUtil().addToTextUsages(value);
   }


   /**
    * Determines if this instance's set of text usages contains the given UMLTextNode instance.
    * (Forwarded to FTextReferenceUtility)
    */
   public boolean hasInTextUsages(TextNode value)
   {
      return getTextRefUtil().hasInTextUsages(value);
   }


   /**
    * @return an Iterator of this instance's set of text usages. (Forwarded to
    *         FTextReferenceUtility)
    */
   public Iterator<TextNode> iteratorOfTextUsages()
   {
      return getTextRefUtil().iteratorOfTextUsages();
   }


   /**
    * Removes all elements from this instance's set of text usages. (Forwarded to
    * FTextReferenceUtility)
    */
   public void removeAllFromTextUsages()
   {
      getTextRefUtil().removeAllFromTextUsages();
   }


   /**
    * Removes the given element from this instance's set of text usages if it is in there.
    * (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean removeFromTextUsages(TextNode value)
   {
      return getTextRefUtil().removeFromTextUsages(value);
   }


   /**
    * @return the number of UMLTextNode instances in this instance's set of text usages. (Forwarded
    *         to FTextReferenceUtility)
    */
   public int sizeOfTextUsages()
   {
      return getTextRefUtil().sizeOfTextUsages();
   }


   @Override
   public String getQualifiedDisplayName()
   {
      return (getParent() != null ? getParent().getName() + "." : "")
            + this.getName() + "(..)" + " in " + getProject().getName();
   }
}

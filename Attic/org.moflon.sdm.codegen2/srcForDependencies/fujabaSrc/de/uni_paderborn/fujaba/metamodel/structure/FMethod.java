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
package de.uni_paderborn.fujaba.metamodel.structure;


import de.fujaba.text.FTextReference;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.asg.ASTRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;

import java.util.Iterator;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface FMethod extends FDeclaration, FTextReference
{

   // --- Property static ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String STATIC_PROPERTY = "static";


   /**
    * Get the static attribute of the FMethod object
    * 
    * 
    * @return The static value
    */
   @Property(name=STATIC_PROPERTY)
   public boolean isStatic();


   /**
    * Sets the static attribute of the FMethod object
    * 
    * 
    * @param fStatic The new static value
    */
   @Property(name=STATIC_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setStatic(boolean fStatic);


   // --- Property ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ABSTRACT_PROPERTY = "abstract";


   /**
    * Sets the attribute of the FMethod object
    * 
    * 
    * @param fAbstract The new abstract value
    */
   @Property(name=ABSTRACT_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setAbstract(boolean fAbstract);


   /**
    * Get the attribute of the FMethod object
    * 
    * 
    * @return The value
    */
   @Property(name=ABSTRACT_PROPERTY)
   public boolean isAbstract();


   // --- Property parent ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String PARENT_PROPERTY = "parent";


   /**
    * Get the parent attribute of the FMethod object
    * 
    * 
    * @return The parent value
    */
   @Property(name=PARENT_PROPERTY)
   public FClass getParent();


   /**
    * Sets the parent attribute of the FMethod object
    * 
    * 
    * @param parent The new parent value
    */
   @Property( name = PARENT_PROPERTY, partner = FClass.METHODS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public void setParent(FClass parent);


   // --- Property resultType ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String RESULT_TYPE_PROPERTY = "resultType";


   /**
    * Sets the resultType attribute of the FMethod object
    * 
    * 
    * @param value The new resultType value
    */
   @Property(name=RESULT_TYPE_PROPERTY, partner=FType.REV_RESULT_TYPE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   public void setResultType(FType value);


   /**
    * Get the resultType attribute of the FMethod object
    * 
    * 
    * @return The resultType value
    */
   @Property(name=RESULT_TYPE_PROPERTY)
   public FType getResultType();


   // --- Property declares ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String DECLARES_PROPERTY = "declares";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param obj No description provided
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean hasInDeclares(FClass obj);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param key No description provided
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean hasKeyInDeclares(String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator<? extends FClass> iteratorOfDeclares();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator keysOfDeclares();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator entriesOfDeclares();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public int sizeOfDeclares();


   /**
    * Get the fromDeclares attribute of the FMethod object
    * 
    * 
    * @param key No description provided
    * @return The fromDeclares value
    */
   @Property(name=DECLARES_PROPERTY)
   public FClass getFromDeclares(String key);


   /**
    * Access method for an one to n association.
    * 
    * 
    * @param obj The object added.
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean addToDeclares(FClass obj);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param obj No description provided
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY, partner=FClass.DECLARED_IN_METHOD_PROPERTY, kind=ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromDeclares(FClass obj);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param key No description provided
    * @return No description provided
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean removeKeyFromDeclares(String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=DECLARES_PROPERTY)
   public void removeAllFromDeclares();


   // --- Property param ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String PARAM_PROPERTY = "param";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param newParam No description provided
    * @return No description provided
    */
   @Property(name=PARAM_PROPERTY)
   public boolean hasInParam(FParam newParam);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=PARAM_PROPERTY)
   public int sizeOfParam();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   @Property(name=PARAM_PROPERTY)
   public Iterator<? extends FParam> iteratorOfParam();


   /**
    * Access method for an one to n association.
    * 
    * 
    * @param newParam The object added.
    */
   @Property(name=PARAM_PROPERTY, partner=FParam.REV_PARAM_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public void addToParam(FParam newParam);


   /**
    * Access method for an one to n association.
    * 
    * 
    * @param index The object added.
    * @param newParam The object added.
    */
   @Property(name=PARAM_PROPERTY)
   public void addToParam(int index, FParam newParam);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @param newParam No description provided
    */
   @Property(name=PARAM_PROPERTY)
   public void removeFromParam(FParam newParam);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=PARAM_PROPERTY)
   public void removeAllFromParam();


   // --- Derived properties ---
   
   /**
    * @deprecated (gets deleted in 5.1)
    */
   public static final String REV_SPEC_PROPERTY = "revSpec";
   
   /**
    * Get the revSpec attribute of the FMethod object TODO: Make return type a start activity
    * 
    * 
    * @return The revSpec value
    * @deprecated use {@link #getStoryDiag} instead (gets deleted in 5.1)
    */
   @NoProperty()
   public FElement getRevSpec();


   /**
    * @param startActivity The new revSpec value
    * @deprecated use {@link #setStoryDiag} instead (gets deleted in 5.1)
    */
   @NoProperty()
   public void setRevSpec(FElement startActivity);


   // --- Property methodBody ---
   public final static String METHOD_BODY_PROPERTY = "methodBody";

   
   /**
    * Sets the methodBody of the UMLMethod
    * 
    * @param methodBody the new methodBody as text
    */
   @Property(name=METHOD_BODY_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setMethodBody(String methodBody);


   /**
    * Sets the methodBody of the UMLMethod
    * 
    * @param methodBody the new methodBody as text
    * @param generated true, if the method was generated
    */
   @Property(name=METHOD_BODY_PROPERTY)
   public void setMethodBody(String methodBody, boolean createActivityDiagram);

   
   /**
    * Get the methodBody of the UMLMethod
    * 
    * @return The methodBody
    */
   @Property(name=METHOD_BODY_PROPERTY)
   public String getMethodBody();


   public final static String ASTROOT_NODE_PROPERTY = "aSTRootNode";


   /**
    * Returns the abstract syntax tree (AST) root node of the method If the method body isn't parsed
    * yet, the method body will be parsed.
    * 
    * @return The AST root node
    */
   @Property(name=ASTROOT_NODE_PROPERTY)
   public ASTRootNode getASTRootNode();


   /**
    * Sets the abstract syntax tree (AST) root node of the method
    * 
    * @param astRootNode The new AST root node
    */
   @Property(name=ASTROOT_NODE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.NONE)
   public void setASTRootNode(ASTRootNode astRootNode);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   public Iterator<? extends FType> iteratorOfThrowsTypes();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * 
    * @return No description provided
    */
   public int sizeOfThrowsTypes();


   public static final String ACCESSED_ATTRIBUTE_PROPERTY = "accessedAttribute";

   /**
    * Get the accessedAttribute attribute of the FMethod object
    * 
    * 
    * @return The accessedAttribute value
    * @deprecated (gets deleted in 5.1)
    */
   @Property(name=ACCESSED_ATTRIBUTE_PROPERTY)
   public FAttr getAccessedAttribute();


   /**
    * Set the accessedAttribute attribute of the FMethod object
    * 
    * 
    * @param attr The new accessedAttribute value
    * @return No description provided
    * @deprecated (gets deleted in 5.1)
    */
   @Property(name=ACCESSED_ATTRIBUTE_PROPERTY, partner=FAttr.ACCESS_METHODS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   public boolean setAccessedAttribute(FAttr attr);


   // --- Property declares ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String STORY_DIAG_PROPERTY = "storyDiag";


   /**
    * Sets the storyDiag attribute of the FMethod object
    * 
    * 
    * @param diagram The new storyDiag value
    */
   @Property(name=STORY_DIAG_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE,
         adornment=ReferenceHandler.Adornment.COMPOSITION)
   public void setStoryDiag(FDiagram diagram);


   /**
    * Get the storyDiag attribute of the FMethod object
    * 
    * 
    * @return The storyDiag value
    */
   @Property(name=STORY_DIAG_PROPERTY)
   public FDiagram getStoryDiag();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromThrowsTypes();


   /**
    * Access method for a To N-association.
    * 
    * 
    * @param realType The object added.
    * @return No description provided
    */
   public boolean addToThrowsTypes(FType realType);


   /**
    * Get the fullMethodName attribute of the FMethod object
    * 
    * 
    * @return The fullMethodName value
    */
   @Property(name="fullMethodName", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getFullMethodName();


   /**
    * Get the methodDecl attribute of the FMethod object
    * 
    * 
    * @return The methodDecl value
    */
   @Property(name="methodDecl", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getMethodDecl();


   /**
    * Get the qualifiedMethodDecl attribute of the FMethod object
    * 
    * 
    * @return The qualifiedMethodDecl value
    */
   @Property(name="qualifiedMethodDecl", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getQualifiedMethodDecl();


   /**
    * Find all methods in superclasses and -interfaces that have the same signature as getClient()
    * method.
    * 
    * @return iterator through overwritten/implemented methods
    */
   public Iterator<? extends FMethod> iteratorOfOverriddenMethods();


   /**
    * Find all methods in subclasses and -interfaces that have the same signature as getClient()
    * method.
    * 
    * @return iterator through overwriting/implementing methods
    */
   public Iterator<? extends FMethod> iteratorOfOverridingMethods();

   FClass getParentElement();
}

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
package de.uni_paderborn.fujaba.metamodel.common;


import java.util.Iterator;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.properties.InspectionAware;
import de.uni_kassel.util.PropertyChangeSource;
import de.uni_paderborn.fujaba.basic.UniqueIdentifier;
import de.upb.tools.pcs.PropertyChangeClient;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface FElement extends FStyledElement, UniqueIdentifier,
      PropertyChangeClient, InspectionAware, PropertyChangeSource
{

   public final static String NAME_PROPERTY = "name";


   /**
    * Get the name attribute of the ASGElement object
    * 
    * @return The name value
    */
   @Property(name=NAME_PROPERTY)
   public abstract String getName();


   /**
    * Sets the name attribute of the ASGElement object
    * 
    * @param newName The new name value
    */
   @Property(name=NAME_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public abstract void setName(String newName);

   // --- Property text ---
   public final static String TEXT_PROPERTY = "text";

   /**
    * Get the text attribute of the ASGElement object
    * 
    * @return The text value
    */
   @Property(name=TEXT_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public abstract String getText();


   // --- Property diagrams property ---
   public final static String DIAGRAMS_PROPERTY = "diagrams";


   @Property(name=DIAGRAMS_PROPERTY)
   public abstract int sizeOfDiagrams();


   @Property(name=DIAGRAMS_PROPERTY)
   public abstract boolean hasInDiagrams(FDiagram diagram);


   @Property(name=DIAGRAMS_PROPERTY)
   public abstract Iterator<? extends FDiagram> iteratorOfDiagrams();


   @Property(name=DIAGRAMS_PROPERTY, partner=FDiagram.ELEMENTS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.PARENT)
   public abstract void addToDiagrams(FDiagram diagram);


   @Property(name=DIAGRAMS_PROPERTY)
   public abstract void removeFromDiagrams(FDiagram diagram);


   @Property(name=DIAGRAMS_PROPERTY)
   public abstract void removeAllFromDiagrams();


   // --- Property elementReferences ---
   public final static String ELEMENT_REFERENCES_PROPERTY = "elementReferences";


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean hasInElementReferences(FElementRef value);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean hasInElementReferences(String key, FElementRef value);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean hasKeyInElementReferences(String key);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract Iterator<? extends FElementRef> iteratorOfElementReferences();


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract Iterator keysOfElementReferences();


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract Iterator entriesOfElementReferences();


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract int sizeOfElementReferences();


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract FElementRef getFromElementReferences(String key);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean addToElementReferences(String key, FElementRef value);


   public abstract boolean addToElementReferences(java.util.Map.Entry entry);


   @Property(name=ELEMENT_REFERENCES_PROPERTY, kind=ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE, adornment=ReferenceHandler.Adornment.NONE)
   public abstract boolean removeFromElementReferences(FElementRef value);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean removeFromElementReferences(String key,
		FElementRef value);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract boolean removeKeyFromElementReferences(String key);


   @Property(name=ELEMENT_REFERENCES_PROPERTY)
   public abstract void removeAllFromElementReferences();

   // --- Property annotations ---
   public final static String ANNOTATIONS_PROPERTY = "annotations";


   public abstract boolean addToAnnotations(String key, FAnnotation value);


   public abstract Iterator<? extends FAnnotation> iteratorOfAnnotations();


   public abstract boolean hasInAnnotations(FAnnotation value);


   public abstract int sizeOfAnnotations();


   public abstract boolean removeFromAnnotations(String key, FAnnotation value);


   public abstract void removeAllFromAnnotations();


   public static final String REMOVE_YOU_PROPERTY = "removeYou";


   public abstract void removeYou();


   /**
    * Query if this object is stored or just a temporary one.
    * 
    * @return true if the object is stored (default for most objects)
    */
   @Property(name="persistent", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public abstract boolean isPersistent();


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object). This
    * method allows to navigate in direction of the model root (project) from any element within a
    * project.
    * 
    * @return the logical parent of this element, may not return null unless this is the top level
    *         node (project) or is not contained in any parent yet
    */
   @Property(name="parentElement", kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FElement getParentElement();


   /**
    * property change event name for property attribute
    */
   public final static String PROJECT_PROPERTY = "project";


   /**
    * @return The project the model element belongs to.
    */
   @Property(name=PROJECT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE)
   public FProject getProject();


   /**
    * Query key for creation of this object by a factory. The factory key is a flyweight key that is
    * required for creating/restoring an element (e.g., "Integer" or "Reference"). It must not
    * change during runtime! The implementation of this method should be set to final.
    * 
    * @return factory key, null if not applicable
    */
   @Property(name="factoryKey", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public abstract String getFactoryKey();


   /**
    * Holds the key for this element. The key may change during runtime!
    * 
    * Concrete elements may override this method to specify their key. E.g., the implementation of a
    * FClass will return a key that is composed of the full packagename and its own name.
    * 
    * An elementKey may be used, e.g., by an implementation of interface FFactory to implement the
    * methods 'getFromProducts(String key)' and 'hasKeyInProducts (String key)'.
    * 
    * @return Dynamic key of this element, null if not applicable.
    */
   @Property(name="elementKey", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public abstract String getElementKey();

   /**
    * An example for a qualified name would be the full qualified classname and the project it resides in.
    * @return a name of this object which enables the <u>user</u> to identify the object within the workspace
    */
   String getQualifiedDisplayName();
}

/*
 * $Log$
 * Revision 1.8  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.7  2007/03/21 12:47:48  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.6  2006/05/19 14:50:25  cschneid
 * versioning: commit message and update info
 *
 * Revision 1.5  2006/05/18 18:21:48  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.4  2006/03/27 15:06:12  lowende
 * Removed some of deprecated ProjectManager.getCurrentProject and FProject.getCurrentModelRootNode calls.
 *
 */

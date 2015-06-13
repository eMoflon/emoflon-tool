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
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * Represents a class (e.g. UML class).
 * 
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public interface FClass extends FDeclaration, FType, FAccessedElement, FTextReference
{

   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String ABSTRACT_PROPERTY = "abstract";


   /**
    * Change the 'abstract' flag of a class.
    * 
    * @param value true to make a class abstract
    * @return true if value was changed, false if it already had the specified value
    */
   @Property(name=ABSTRACT_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public boolean setAbstract(boolean value);


   /**
    * Read the 'abstract' flag of the class.
    * 
    * @return true if this is an class
    */
   @Property(name=ABSTRACT_PROPERTY)
   public boolean isAbstract();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String FILE_PROPERTY = "file";


   /**
    * Sets the file this class is defined in.
    * 
    * @param file file this class is defined in
    * @return true if value was changed, false if it already had the specified value
    */
   @Property(name=FILE_PROPERTY, partner=FFile.CONTAINS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.NONE) //, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean setFile(FFile file);


   /**
    * Get the file this class is defined in.
    * 
    * @return the file
    */
   @Property(name=FILE_PROPERTY)
   public FFile getFile();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_IMPORTED_CLASSES_PROPERTY = "revImportedClasses";


   /**
    * @param file file that possibly includes this class
    * @return true if this class is imported in the specified file
    * @see #iteratorOfRevImportedClasses()
    */
   @Property(name=REV_IMPORTED_CLASSES_PROPERTY)
   public boolean hasInRevImportedClasses(FFile file);


   /**
    * The revImportedClasses property is the conterpart of FFile.importedClasses. It lists all files
    * that import this class (e.g. in their Java codes import clause).
    * 
    * @return iterator through files that import this class
    * @see FFile#iteratorOfImportedClasses()
    */
   @Property(name=REV_IMPORTED_CLASSES_PROPERTY)
   public Iterator<? extends FFile> iteratorOfRevImportedClasses();


   /**
    * @param file file that imports this class now
    * @see #iteratorOfRevImportedClasses()
    */
   @Property(name=REV_IMPORTED_CLASSES_PROPERTY, partner=FFile.IMPORTED_CLASSES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public void addToRevImportedClasses(FFile file);


   /**
    * @param file that does no longer import this class
    * @see #iteratorOfRevImportedClasses()
    */
   @Property(name=REV_IMPORTED_CLASSES_PROPERTY)
   public void removeFromRevImportedClasses(FFile file);


   public final static String PARSED_MEMBERS_PROPERTY = "parsedMembers";

   /**
    * Access method for a To N-association.
    * 
    * @param value The object added.
    * @return No description provided
    */
   @Property(name=PARSED_MEMBERS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public boolean addToParsedMembers(FDeclaration value);


   @Property(name=PARSED_MEMBERS_PROPERTY)
   public boolean hasInParsedMembers(FDeclaration value);


   @Property(name=PARSED_MEMBERS_PROPERTY)
   public Iterator<? extends FDeclaration> iteratorOfParsedMembers();


   @Property(name=PARSED_MEMBERS_PROPERTY)
   public void removeAllFromParsedMembers();


   @Property(name=PARSED_MEMBERS_PROPERTY)
   public boolean removeFromParsedMembers(FDeclaration value);


   @Property(name=PARSED_MEMBERS_PROPERTY)
   public int sizeOfParsedMembers();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String ATTRS_PROPERTY = "attrs";


   /**
    * @param attr attribute that is member of this class now (is declared by this class)
    * @return true if value was added, false is already linked
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public boolean hasInAttrs(FAttr attr);


   /**
    * @param key name of an attribute
    * @return true if this class declares an attribute with the specified name
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public boolean hasKeyInAttrs(String key);


   /**
    * The attrs property of FClass contains all attributes ({@link FAttr}s) that are declared in
    * this class. The reverse property is FAttr.parent.
    * 
    * @return iterator through declared {@link FAttr}s
    * @see FAttr#getParent()
    */
   @Property(name=ATTRS_PROPERTY)
   public Iterator<? extends FAttr> iteratorOfAttrs();


   /**
    * @return iterator through attribute names (Strings)
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public Iterator<String> keysOfAttrs();


   /**
    * @return number of attributes this class declares
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public int sizeOfAttrs();


   /**
    * @param attrName name of the attribute to return
    * @return the attribute with the specified name, declared in this class, null if no attribute of
    *         this name is declared by this class
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public FAttr getFromAttrs(String attrName);


   /**
    * @param attr attribute that is declared by this class now
    * @return true if added, false if was already added
    * @see #iteratorOfAttrs()
    */
   public boolean addToAttrs(FAttr attr);


   /**
    * @param attr attribute that is no longer declared by this class
    * @return true if removed, false if was already removed (not contained)
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY, partner=FAttr.PARENT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromAttrs(FAttr attr);


   /**
    * @param attrName name of the attribute to be removed
    * @return true if removed, false if was already removed (not contained)
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public boolean removeKeyFromAttrs(String attrName);


   /**
    * Remove all declared attributes from this class.
    * 
    * @see #iteratorOfAttrs()
    */
   @Property(name=ATTRS_PROPERTY)
   public void removeAllFromAttrs();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String ROLES_PROPERTY = "roles";


   /**
    * @return number of roles this class has
    * @see #iteratorOfRoles()
    */
   @Property(name=ROLES_PROPERTY)
   public int sizeOfRoles();


   /**
    * @param role potential role this class has
    * @return true if class has the specified role
    * @see #iteratorOfRoles()
    */
   @Property(name=ROLES_PROPERTY)
   public boolean hasInRoles(FRole role);


   /**
    * The roles property lists all roles this class has in associations. A role is part of an
    * association.
    * 
    * @return iterator through all roles this class has in associations
    */
   @Property(name=ROLES_PROPERTY)
   public Iterator<? extends FRole> iteratorOfRoles();


   /**
    * @param role new role this class has now
    * @see #iteratorOfRoles()
    */
   @Property(name=ROLES_PROPERTY, partner=FRole.TARGET_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public void addToRoles(FRole role);


   /**
    * @param elem role this class this class has no more
    * @see #iteratorOfRoles()
    */
   @Property(name=ROLES_PROPERTY)
   public void removeFromRoles(FRole elem);


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_ROLES_PROPERTY = "revRoles";


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String METHODS_PROPERTY = "methods";


   /**
    * @param method potential method in this class
    * @return true if method is declared in this class
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public boolean hasInMethods(FMethod method);


   /**
    * @param signature signature of a method
    * @return true if this class declares a method with this signature
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public boolean hasKeyInMethods(String signature);


   /**
    * The methods property contains all methods this class declares.
    * 
    * @return iterator through declared methods
    * @see FMethod#getParent()
    */
   @Property(name=METHODS_PROPERTY)
   public Iterator<? extends FMethod> iteratorOfMethods();


   /**
    * @return number of mthods in this class
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public int sizeOfMethods();


   /**
    * @param signature possible signature
    * @return the method declared in this class with the specified signature, null if no such method
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public FMethod getFromMethods(String signature);


   /**
    * @param method method that is declared in this class now
    * @return true if added, false if was already added
    * @see #iteratorOfMethods()
    */
   public boolean addToMethods(FMethod method);


   /**
    * @param method method that is no longer declared in this class
    * @return true if removed, false if was already removed (not contained)
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY, partner=FMethod.PARENT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromMethods(FMethod method);


   /**
    * @param signature signature of the method to be removed from declared methods
    * @return true if removed, false if was already removed (not contained)
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public boolean removeKeyFromMethods(String signature);


   /**
    * Remove all methods declared in this class.
    * 
    * @see #iteratorOfMethods()
    */
   @Property(name=METHODS_PROPERTY)
   public void removeAllFromMethods();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String DECLARED_IN_PACKAGE_PROPERTY = "declaredInPackage";


   /**
    * @param pkg the package this class is declared in now
    * @return true if the class was declared in another package before
    * @see #getDeclaredInPackage()
    */
   @Property( name = DECLARED_IN_PACKAGE_PROPERTY, partner = FPackage.DECLARES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setDeclaredInPackage(FPackage pkg);


   /**
    * The declaredInPackage property denotes the package this class is declared in, null if this is
    * an inner or anonymous class.
    * 
    * @return the package this class is declared in, null if not applicable
    * @see FPackage#iteratorOfDeclares()
    * @see #getDeclaredInClass()
    * @see #getDeclaredInMethod()
    */
   @Property(name=DECLARED_IN_PACKAGE_PROPERTY)
   public FPackage getDeclaredInPackage();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String DECLARED_IN_METHOD_PROPERTY = "declaredInMethod";


   /**
    * @param method method where this class is declared in now
    * @return true if value was changed
    * @see #getDeclaredInMethod()
    */
   @Property( name = DECLARED_IN_METHOD_PROPERTY, partner = FMethod.DECLARES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setDeclaredInMethod(FMethod method);


   /**
    * The declaredInMethod property denotes the method this class is declared in if it is an
    * anonymous class, null if this is a top level or inner class.
    * 
    * @return the method this anonymous class is declared in, null if not applicable
    * @see FMethod#iteratorOfDeclares()
    * @see #getDeclaredInClass()
    * @see #getDeclaredInMethod()
    */
   @Property(name=DECLARED_IN_METHOD_PROPERTY)
   public FMethod getDeclaredInMethod();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String DECLARED_IN_CLASS_PROPERTY = "declaredInClass";


   /**
    * @param cls class where this class is declared in now
    * @return true if value was changed
    * @see #getDeclaredInClass()
    */
   @Property( name = DECLARED_IN_CLASS_PROPERTY, partner = FClass.DECLARES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setDeclaredInClass(FClass cls);


   /**
    * The declaredInClass property denotes the class this inner class is declared in, null if this
    * is a top level or anonymous class.
    * 
    * @return the class this inner class is declared in, null if not applicable
    * @see FClass#iteratorOfDeclares()
    * @see #getDeclaredInPackage()
    * @see #getDeclaredInMethod()
    */
   @Property(name=DECLARED_IN_CLASS_PROPERTY)
   public FClass getDeclaredInClass();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String DECLARES_PROPERTY = "declares";


   /**
    * @param cls possible inner class
    * @return true if cls is an inner class of this class
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean hasInDeclares(FClass cls);


   /**
    * @param classname unqualified classname
    * @return true if this class has an inner class with specified name
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean hasKeyInDeclares(String classname);


   /**
    * The declares property holds all inner classes of this class.
    * 
    * @return an iterator through inner classes
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator<? extends FClass> iteratorOfDeclares();


   /**
    * @return iterator through the unqualified names of inner classes (Strings)
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator keysOfDeclares();


   /**
    * @return iterator through inner class entries (String name, FClass class)
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator entriesOfDeclares();


   /**
    * @return number of inner classes
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public int sizeOfDeclares();


   /**
    * @param classname unqualified classname
    * @return the inner class declared in this class with the specified name, null if no inner class
    *         of that name
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public FClass getFromDeclares(String classname);


   /**
    * @param cls the class that is inner class of this class now
    * @return true if the value was added, false if it was already an inner class of this class
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean addToDeclares(FClass cls);


   /**
    * @param cls class that is no longer an inner class of this class
    * @return true if class was removed, false if it was no inner class of this class
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY, partner=FClass.DECLARED_IN_CLASS_PROPERTY, kind=ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromDeclares(FClass cls);


   /**
    * @param classname unqualified classname of an inner class
    * @return true if the class with specified name was removed, false if no inner class with this
    *         name
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean removeKeyFromDeclares(String classname);


   /**
    * Remove all inner classes (delete link, classes remain intact).
    * 
    * @see #iteratorOfDeclares()
    */
   @Property(name=DECLARES_PROPERTY)
   public void removeAllFromDeclares();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_SUBCLASS_PROPERTY = "revSubclass";


   /**
    * @param generalization possible generalization that has this class as subclass
    * @return true if this is the subclass in the generalization
    * @see #iteratorOfRevSubclass()
    */
   @Property(name=REV_SUBCLASS_PROPERTY)
   public boolean hasInRevSubclass(FGeneralization generalization);


   /**
    * @return number of superclasses/interfaces
    * @see #iteratorOfRevSubclass()
    */
   @Property(name=REV_SUBCLASS_PROPERTY)
   public int sizeOfRevSubclass();


   /**
    * The revSubclass property contains alls generalizations where this class is the subclass. This
    * means all superclasses can be found this way.
    * 
    * @return iterator through generalizations
    */
   @Property(name=REV_SUBCLASS_PROPERTY)
   public Iterator<? extends FGeneralization> iteratorOfRevSubclass();


   /**
    * @param elem generalization where this class is the subclass now
    * @see #iteratorOfRevSubclass()
    */
   @Property(name=REV_SUBCLASS_PROPERTY, partner=FGeneralization.SUBCLASS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public void addToRevSubclass(FGeneralization elem);


   /**
    * @param elem generalization where this class is no longer the subclass
    * @see #iteratorOfRevSubclass()
    */
   @Property(name=REV_SUBCLASS_PROPERTY)
   public void removeFromRevSubclass(FGeneralization elem);


   /**
    * Remove all generalizations where this class is the subclass.
    * 
    * @see #iteratorOfRevSubclass()
    */
   @Property(name=REV_SUBCLASS_PROPERTY)
   public void removeAllFromRevSubclass();


   /**
    * @return iterator through all superclasses and interfaces
    */
   public Iterator<? extends FClass> iteratorOfSuperClasses();

   /**
    * @return iterator through all subclasses and interfaces
    */
   public Iterator<? extends FClass> iteratorOfSubClasses();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_SUPERCLASS_PROPERTY = "revSuperclass";


   /**
    * @param elem generalization
    * @return true if this class is the superclass in the generalization
    * @see #iteratorOfRevSuperclass()
    */
   @Property(name=REV_SUPERCLASS_PROPERTY)
   public boolean hasInRevSuperclass(FGeneralization elem);


   /**
    * The revSuperclass property contains all generalization where this class is the superclass. You
    * can find subclasses on this way.
    * 
    * @return iterator through generalizations where this class is the superclass
    */
   @Property(name=REV_SUPERCLASS_PROPERTY)
   public Iterator<? extends FGeneralization> iteratorOfRevSuperclass();


   /**
    * @param elem generalization where this class is the superclass now
    * @see #iteratorOfRevSuperclass()
    */
   @Property(name=REV_SUPERCLASS_PROPERTY, partner=FGeneralization.SUPERCLASS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public void addToRevSuperclass(FGeneralization elem);


   /**
    * @param elem generalization where this class is no longer the superclass
    * @see #iteratorOfRevSuperclass()
    */
   @Property(name=REV_SUPERCLASS_PROPERTY)
   public void removeFromRevSuperclass(FGeneralization elem);


   /**
    * Remove all generalizations where this class is the superclass.
    * 
    * @see #iteratorOfRevSuperclass()
    */
   @Property(name=REV_SUPERCLASS_PROPERTY)
   public void removeAllFromRevSuperclass();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String INSTANCES_PROPERTY = "instances";


   @Property(name=INSTANCES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY,
         adornment=ReferenceHandler.Adornment.NONE)
   public void addToInstances(FElement object);


   @Property(name=INSTANCES_PROPERTY)
   public Iterator<? extends FElement> iteratorOfInstances();


   @Property(name=INSTANCES_PROPERTY)
   public void removeFromInstances(FElement object);


   /**
    * @return The superClass value
    */
   @Property(name="superClass", kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FClass getSuperClass();


   public final static String STATECHART_PROPERTY = "statechart";
 
   /**
    * @return the statechart for this class, null if not applicable
    */
   @Property(name=STATECHART_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FDiagram getStatechart();


   public static final String DEFAULT_ICON_PROPERTY = "defaultIcon";


   @Property(name=DEFAULT_ICON_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getDefaultIcon();


   public FMethod getFromMethodsByShortNameIncludingInherited(
         String key);


   public FClass findClass(String name);

   
   public FPackage findPackage();


   @NoProperty
   public Set<? extends FAssoc> getAllAssocs();


   @NoProperty
   public Collection<? extends FRole> getAllUsedRoles();


   public FAttr getFromAllAttrs(String key);


   public FRole getFromAllPartnerRoles(String key);


   public boolean isChildOf(FClass clazz);


   @Property(name="fullClassName", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getFullClassName();


   public Iterator<? extends FRole> iteratorOfAllRoles();


   public Set<? extends FRole> getAllRoles();


   public Set<? extends FAttr> getAllAttrs();


   public Set<? extends FMethod> findMethodsWithSignatureInSuperclasses(
         String signature);


   public Set<? extends FMethod> findMethodsWithSignatureInSubclasses(
         String signature);


   public FMethod getFromMethodsByShortName(String string);


   public Iterator<? extends FAttr> iteratorOfAllAttrs();


   public Iterator<? extends FMethod> iteratorOfAllAccessibleMethods();


   public FMethod getFromAllMethods(String string);

   int sizeOfRevSuperclass();
}

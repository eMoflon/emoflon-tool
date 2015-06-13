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

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FClassDiagram;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;

import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLClassDiagram extends UMLDiagram implements FClassDiagram
{

   protected UMLClassDiagram (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getName();
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    * 
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      super.removeYou();
   }

   /**
    * @deprecated only for testing
    */
   public void addAllObjectSuperClass()
   {
      FProject project = getProject();
      FClass objectClass = FProjectUtility.findClass(project, "java.lang.Object");

      for (Iterator<? extends FGeneralization> it = objectClass.iteratorOfRevSuperclass(); it.hasNext();)
      {
         FGeneralization generalization = it.next();
         if ( generalization.getSubclass() == null )
         {
            generalization.removeYou();
         }
      }

      addAllObjectSuperClass(project, project, objectClass);
   }

   private void addAllObjectSuperClass(FProject target, FProject project, FClass objectClass)
   {
      for (Iterator<FClass> it = project.getFromFactories(FClass.class).iteratorOfProducts(); it.hasNext();)
      {
         FClass cls = it.next();
         if (cls != objectClass)
         {
            if (cls.sizeOfRevSubclass() == 0)
            {
               FGeneralization gen = target.getFromFactories(FGeneralization.class).create(true);
               gen.setSuperclass(objectClass);
               gen.setSubclass(cls);
            }
            if (cls.sizeOfRevSubclass() == 1)
            {
               FGeneralization gen = cls.iteratorOfRevSubclass().next();
               if ( gen.getSuperclass() == null )
               {
                  gen.setSuperclass(objectClass);
               }
            }
         }
      }
      for (Iterator<? extends FProject> it = project.iteratorOfRequires(); it.hasNext();)
      {
         FProject requiredProject = it.next();
         addAllObjectSuperClass(target, requiredProject, objectClass);
      }
   }

   @Property(name=ELEMENTS_PROPERTY, partner=FElement.DIAGRAMS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.AGGREGATION)
   @Override
   public Iterator<? extends FElement> iteratorOfElements()
   {
      return super.iteratorOfElements();
   }
}

/*
 * $Log$
 * Revision 1.11  2006/05/31 18:19:55  fklar
 * removed unnecessary 'implements'-relation to 'FModelRootNode'
 *
 * Revision 1.10  2006/03/22 13:28:54  lowende
 * Parameter type of UMLDiagram.addToElements corrected, so that it overrides inherited method.
 *
 */

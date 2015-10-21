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
package de.uni_paderborn.fujaba.uml.common;


import java.util.Iterator;
import java.util.Set;

import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.asg.ASGDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FModelDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.packagediagrams.DiagramUsage;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FPropHashSet;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLDiagram extends ASGDiagram implements FModelDiagram
{
   
   protected UMLDiagram(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   private String name;


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
      if ((this.name == null && name != null)
            || (this.name != null && !this.name.equals(name)))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange(FElement.NAME_PROPERTY, oldValue, name);

         Iterator usages = this.iteratorOfUsages();

         while (usages.hasNext())
         {
            DiagramUsage currentUsage = (DiagramUsage) usages.next();

            if (currentUsage != null
                  && !currentUsage.getName().equals(this.name))
            {
               currentUsage.setName(this.name);
            }
         }
      }
   }

   private Set<UMLConstraint> constraints;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#hasInConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean hasInConstraints(FConstraint value)
   {
      return ((this.constraints != null) && this.constraints.contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#iteratorOfConstraints()
    */
   public Iterator<UMLConstraint> iteratorOfConstraints()
   {
      if (this.constraints == null)
      {
         return EmptyIterator.get();
      } else
      {
         return this.constraints.iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#sizeOfConstraint()
    */
   public int sizeOfConstraint()
   {
      return ((this.constraints == null) ? 0 : this.constraints.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#addToConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean addToConstraints(FConstraint value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.constraints == null)
         {
            //noinspection unchecked
            this.constraints = new FPropHashSet<UMLConstraint>(this, CONSTRAINTS_PROPERTY);
         }
         changed = this.constraints.add((UMLConstraint) value);
         if (changed)
         {
            value.addToRevConstraint(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#removeFromConstraints(de.uni_paderborn.fujaba.metamodel.common.FConstraint)
    */
   public boolean removeFromConstraints(FConstraint value)
   {
      boolean changed = false;
      if ((this.constraints != null) && (value != null))
      {
         changed = this.constraints.remove(value);
         if (changed)
         {
            value.removeFromRevConstraint(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FModelDiagram#removeAllFromConstraints()
    */
   public void removeAllFromConstraints()
   {
      Iterator<UMLConstraint> iter = this.iteratorOfConstraints();
      while (iter.hasNext())
      {
         UMLConstraint tmpValue = iter.next();
         this.removeFromConstraints(tmpValue);
      }
   }


   /**
    * Checks for the possibility of creating cyclic DiagramUsage's BEFORE they are created, by
    * recursively comparing DiagramUsage candidate with those contained in UMLDiagram this. Doesn't
    * work.
    * 
    * @param candidate usage which could be cyclic
    * @return true if cyclic
    */
   public boolean isCyclic(DiagramUsage candidate)
   {
      boolean cyclic = false;

      Iterator<? extends FElement> iterator = this.iteratorOfElements();
      while (iterator.hasNext() && !cyclic)
      {
         FElement item = iterator.next();
         if (item instanceof DiagramUsage)
         {
            DiagramUsage usage = (DiagramUsage) item;
            if (usage.equals(candidate))
            {
               cyclic = true;
            }
            else
            {
               cyclic = usage.getDiagram().isCyclic(candidate);
            }
         }
      }

      return cyclic;
   }


   /**
    * <pre>
    *               n    usages    0..1
    * DiagramUsage --------------------- UMLDiagram
    *               usages      diagram
    * </pre>
    */
   private Set<DiagramUsage> usages;


   public boolean hasInUsages(DiagramUsage value)
   {
      return ((this.usages != null) && (value != null) && this.usages
            .contains(value));
   }


   public Iterator iteratorOfUsages()
   {
      return ((this.usages == null) ? FEmptyIterator.get() : this.usages
            .iterator());
   }


   public int sizeOfUsages()
   {
      return ((this.usages == null) ? 0 : this.usages.size());
   }


   public boolean addToUsages(DiagramUsage value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.usages == null)
         {
            this.usages = new FHashSet<DiagramUsage>();
         }
         changed = this.usages.add(value);
         if (changed)
         {
            value.setDiagram(this);
         }
      }
      return changed;
   }


   public boolean removeFromUsages(DiagramUsage value)
   {
      boolean changed = false;
      if ((this.usages != null) && (value != null))
      {
         changed = this.usages.remove(value);
         if (changed)
         {
            value.setDiagram(null);
         }
      }
      return changed;
   }


   public void removeAllFromUsages()
   {
      Iterator iter = this.iteratorOfUsages();
      while (iter.hasNext())
      {
         DiagramUsage tmpValue = (DiagramUsage) iter.next();
         this.removeFromUsages(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      removeAllContraints();
      removeAllFromUsages();
      super.removeYou();
   }

   private void removeAllContraints()
   {
      for (Iterator<UMLConstraint> it = iteratorOfConstraints(); it.hasNext();)
      {
         UMLConstraint constraint = it.next();
         constraint.removeYou();
      }
   }

}

/*
 * $Log$
 * Revision 1.9  2007/01/31 10:41:26  l3_g5
 * prevent classcast exception
 *
 * Revision 1.8  2006/03/22 13:28:54  lowende
 * Parameter type of UMLDiagram.addToElements corrected, so that it overrides inherited method.
 *
 */

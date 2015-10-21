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


import java.util.Iterator;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.upb.tools.sdm.FComplexState;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *                   0..1   startOfStateChart   0..1
 * UMLStartActivity --------------------------------- UMLClass
 *                   +                            +
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLStartActivity extends UMLActivity
{
   protected UMLStartActivity(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#addToDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   @Override
   public void addToDiagrams(FDiagram diagram)
   {
      // ensure that the start activity is only in one activity diagram
      if (hasInDiagrams(diagram))
      {
         return;
      }

      if (diagram != null)
      {
         Iterator diagIter = iteratorOfDiagrams();
         while (diagIter.hasNext())
         {
            UMLDiagram oldDiagram = (UMLDiagram) diagIter.next();
            if ((oldDiagram != null) && (oldDiagram != diagram))
            {
               // the item is contained in more than one diagram
               throw new RuntimeExceptionWithContext(
                     "Error: An start activity can only be contained in one activity diagram",
                     this);
            }
         }
      }
      super.addToDiagrams(diagram);
   }


   public void setEntry(UMLTransition elem)
   {
      throw new RuntimeExceptionWithContext(
            "A start activity must not have any entry transitions", this);
   }


   /**
    * @deprecated (gets deleted in 5.1)
    */
   @Property(name="spec", kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FMethod getSpec()
   {
      return getActivityDiagram() != null ? getActivityDiagram().getStoryMethod() : null;
   }


   /**
    * @deprecated (gets deleted in 5.1)
    */
   public void setSpec(FMethod spec)
   {
      UMLActivityDiagram activityDiagram = getActivityDiagram();
      if ( activityDiagram != null ) {
         activityDiagram.setStoryMethod(spec);
      }
   }


   /**
    * Has one of the following values: FComplexState.HISTORY_NONE, FComplexState.HISTORY_SHALLOW,
    * FComplexState.HISTORY_DEEP
    */
   private int historyKind;


   public void setHistoryKind(int historyKind)
   {
      if (((historyKind == FComplexState.HISTORY_NONE)
            || (historyKind == FComplexState.HISTORY_DEEP) || (historyKind == FComplexState.HISTORY_SHALLOW))
            && this.historyKind != historyKind)
      {
         int oldValue = this.historyKind;
         this.historyKind = historyKind;
         firePropertyChange("historyKind", oldValue, historyKind);
      }
   }


   /**
    * @return One of the following values: FComplexState.HISTORY_NONE,
    *         FComplexState.HISTORY_SHALLOW, FComplexState.HISTORY_DEEP
    */
   public int getHistoryKind()
   {
      return historyKind;
   }


   /**
    * @return True, if kind of history is FComplexState.HISTORY_SHALLOW or
    *         FComplexState.HISTORY_DEEP
    */
   public boolean isHistory()
   {
      return ((historyKind == FComplexState.HISTORY_DEEP) || (historyKind == FComplexState.HISTORY_SHALLOW));
   }


   /**
    * @deprecated  (gets deleted in 5.1)
    */
   @SuppressWarnings({"deprecation"})
   public FClass getSpecClass()
   {
      FClass result = this.getRevStartOfStateChart();

      if (result == null)
      {
         FMethod myMethod = this.getSpec();

         if (myMethod == null)
         {
            return null;
         }

         result = myMethod.getParent();
      }

      return result;
   }


   public String getStartText()
   {
      FMethod myMethod = this.getActivityDiagram() != null ? this.getActivityDiagram().getStoryMethod() : null;
      if (myMethod != null)
      {
         return myMethod.getQualifiedMethodDecl();
      }

      UMLClass myClass = this.getRevStartOfStateChart();
      if (myClass != null)
      {
         return "Statechart for " + myClass.getName();
      }
      else
      {
         return storyName;
      }
   }


   private String storyName = "";


   public String getStoryName()
   {
      return this.storyName;
   }


   public void setStoryName(String newStoryName)
   {
      if ((newStoryName != null) && !newStoryName.equals(this.storyName))
      {
         // actually a new story name
         String oldValue = this.storyName;
         this.storyName = newStoryName;
         firePropertyChange("storyName", oldValue, newStoryName);
      }
   }

   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return isHistory() ? "History" : "Start";
   }


   /**
    * <pre>
    *                   0..1   startOfStateChart   0..1
    * UMLStartActivity --------------------------------- UMLClass
    *                   +                            +
    * </pre>
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   private transient UMLClass revStartOfStateChart;


   /**
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   public UMLClass getRevStartOfStateChart()
   {
      return revStartOfStateChart;
   }

   /**
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   public void setRevStartOfStateChart(UMLClass revStartOfStateChart)
   {
      if ((this.revStartOfStateChart == null && revStartOfStateChart != null)
            || (this.revStartOfStateChart != null && !this.revStartOfStateChart
                  .equals(revStartOfStateChart)))
      {
         // newPartner
         UMLClass oldRevStartOfStateChart = this.revStartOfStateChart;
         if (this.revStartOfStateChart != null)
         {
            // inform old partner
            this.revStartOfStateChart = null;
            oldRevStartOfStateChart.setStartOfStateChart(null);
         }

         this.revStartOfStateChart = revStartOfStateChart;
         if (revStartOfStateChart != null)
         {
            // inform new partner
            revStartOfStateChart.setStartOfStateChart(this);
         }
         firePropertyChange("revStartOfStateChart", oldRevStartOfStateChart,
               revStartOfStateChart);
      }
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getStoryName();
   }
   

   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      setRevStartOfStateChart(null);

      super.removeYou();
   }

}

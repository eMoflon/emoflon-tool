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
package de.uni_kassel.fujaba.codegen.sequencer;

import de.uni_kassel.fujaba.codegen.engine.TokenMutatorTemplateEngine;
import de.uni_kassel.fujaba.codegen.rules.engine.StoryPatternDependencyAnalysisEngine;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class FlowActivity extends Flow
{
   /**
    *Constructor for class FlowActivity
    *
    * @param act  No description provided
    */
   public FlowActivity (UMLActivity act)
   {
      setElement (act);
      umlActivity = act;
      //init();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param tmtengine
    */
   public void init (TokenMutatorTemplateEngine tmtengine)
   {
      if (umlActivity instanceof UMLStoryActivity)
      {
         UMLStoryPattern pattern =  ((UMLStoryActivity) umlActivity).getStoryPattern();
         if ( pattern == null )
         {
            throw new IllegalStateException("The activity '" + umlActivity.getName() + "' has no story pattern. " +
                  "It is broken and should be removed.");
         }
         StoryPatternDependencyAnalysisEngine engine = (StoryPatternDependencyAnalysisEngine) tmtengine.getEngineFor (pattern);
         engine.createToken (pattern, this);
      }
   }


   /**
    * 0..1 0..1 FlowActivity ----------------------------------> UMLActivity + flowActivity
    * + umlActivity
    */
   private UMLActivity umlActivity;


   /**
    * Get the uMLActivity attribute of the FlowActivity object
    *
    * @return   The uMLActivity value
    */
   public UMLActivity getUMLActivity()
   {
      return umlActivity;
   } // getUMLActivity


   /**
    * ForEachSeq
    */
   private Seq forEachSeq;


   /**
    * ForEachSeq
    *
    * @param forEachSeq  The new forEachSeq value
    */
   public void setForEachSeq (Seq forEachSeq)
   {
      this.forEachSeq = forEachSeq;
   }


   /**
    * Get the forEachSeq attribute of the FlowActivity object
    *
    * @return   The forEachSeq value
    */
   public Seq getForEachSeq()
   {
      return forEachSeq;
   }


   /**
    * Get the forEach attribute of the FlowActivity object
    *
    * @return   The forEach value
    */
   public boolean isForEach()
   {
      if (umlActivity instanceof UMLStoryActivity)
      {
         return  ((UMLStoryActivity) umlActivity).isForEach();
      }
      else
      {
         return false;
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int level;


   /**
    * level used by checkForEachStructure
    *
    * @param level  The new level value
    */
   public void setLevel (int level)
   {
      this.level = level;
   } // setLevel


   /**
    * Get the level attribute of the FlowActivity object
    *
    * @return   The level value
    */
   public int getLevel()
   {
      return level;
   } // getLevel


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int grayTime;


   /**
    * Time when FlowActivity was first visited by DFS
    *
    * @param grayTime  The new grayTime value
    */
   public void setGrayTime (int grayTime)
   {
      this.grayTime = grayTime;
   } //setGrayTime


   /**
    * Get the grayTime attribute of the FlowActivity object
    *
    * @return   The grayTime value
    */
   public int getGrayTime()
   {
      return grayTime;
   } // getGrayTime


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int blackTime;


   /**
    * Time when FlowActivity was last visited by DFS
    *
    * @param blackTime  The new blackTime value
    */
   public void setBlackTime (int blackTime)
   {
      this.blackTime = blackTime;
   } //setBlackTime


   /**
    * Get the blackTime attribute of the FlowActivity object
    *
    * @return   The blackTime value
    */
   public int getBlackTime()
   {
      return blackTime;
   } // getBlackTime


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeYou()
   {
      //setUMLActivity (null);
      setForEachSeq (null);
      //----- flow has no removeYou
      super.removeYou();
   } // removeYou


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String context = "children";


   /**
    * Get the context attribute of the Seq object
    *
    * @return   The context value
    */
   public String getContext()
   {
      return context;
   }


   /**
    * Sets the context attribute of the Seq object
    *
    * @param context  The new context value
    */
   public void setContext (String context)
   {
      this.context = context;
   }
}

/*
 * $Log$
 * Revision 1.7  2006/08/29 14:20:20  l3_g5
 * refactoring
 *
 */

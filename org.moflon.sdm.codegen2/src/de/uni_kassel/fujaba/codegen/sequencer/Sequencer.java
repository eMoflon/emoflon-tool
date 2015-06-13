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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import de.uni_kassel.fujaba.codegen.classdiag.ASGElementToken;
import de.uni_kassel.fujaba.codegen.engine.TokenCreationEngine;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLNopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatechart;
import de.uni_paderborn.fujaba.uml.behavior.UMLStopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.upb.tools.fca.FHashMap;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FLinkedList;

/**
 * Used in code generation of activity diagrams for exploring the control flow.
 *
 * @author $Author$
 * @version $Revision$
 */
public class Sequencer extends TokenCreationEngine
{
   private static final String FOR_EACH_STRUCTURE_ERROR_PREFIX = "'for each' structure error: ";

   /**
    * exploreCFG
    *
    * @param startFlowActivity
    *           The start activity of the method
    * @return a sequence for the CFG (note: the flow assocs of the activities in the diagram have to be set before and
    *         removed afterwards)
    */
   public final Seq exploreCFG(final FlowActivity startFlowActivity)
   {
      // start deep-force-search with time=1
      // assumes that 'blackTime' and 'grayTime' are 0 in FlowActivities
      time = 1;
      dfs(startFlowActivity);

      // check the ForEach structure and collect all 'each times' transitions
      level = 1;
      startFlowActivity.setLevel(level);
      level++;

      FLinkedList<UMLTransition> transitionList = new FLinkedList<>();
      checkForEachStructure(startFlowActivity, transitionList);

      // generate sequences for ForEach
      buildForEachStructure(transitionList);

      // start exploration of the Control Flow Graph
      // - all edges (transitions) in ForEach structures are marked now
      Seq seq = new Seq();
      explore(seq, startFlowActivity, null);
      return seq;
   } // exploreCFG

   /**
    * buildForEachStructure()
    *
    * @param transitionList
    *           List of transitions which are collected during checkForEachStructure phase For each 'every times'
    *           transition in transitionList, a flow will be created using explore(). This flow will be stored in the
    *           concerning flowActivity
    */
   private final void buildForEachStructure(final FLinkedList transitionList)
   {
      Iterator transitionsIter = transitionList.iterator();
      UMLTransition currentTransition;
      Seq seq;

      while (transitionsIter.hasNext())
      {
         currentTransition = (UMLTransition) transitionsIter.next();
         currentTransition.setIsMarked(true);
         seq = new Seq();
         explore(seq, getFromFlowActivities(currentTransition.getRevEntry()), getFromFlowActivities(currentTransition.getRevExit()));
         seq.removeLastFlowActivity(getFromFlowActivities(currentTransition.getRevExit()));
         getFromFlowActivities(currentTransition.getRevExit()).setForEachSeq(seq);
      }
   }

   /**
    * level: counter for checkForEachStructure() 0 : unexplored 1 : normal flow 2..n : ForEach level
    */
   static int level;

   /**
    * Checks the 'ForEach' structure and collects all 'each time' transitions in a list
    *
    * @param flowActivity
    *           the actual activity which will be checked
    * @param transitionList
    *           a list which stores all 'each time' transitions start createFlowActivities before if there was no error,
    *           items in transitionList will be used to create flows, collapse them and store them in the concerning
    *           flowActivities
    */
   private final void checkForEachStructure(final FlowActivity flowActivity, final FLinkedList<UMLTransition> transitionList)
   {
      boolean hasEverytimesTransition = false;
      boolean hasTerminationTransition = false;
      boolean hasOtherTransitions = false;

      Iterator<?> iter = flowActivity.getUMLActivity().iteratorOfExit();
      while (iter.hasNext())
      {
         UMLTransition currentUMLTransition = (UMLTransition) iter.next();
         FlowActivity nextFlowActivity = getFromFlowActivities(currentUMLTransition.getRevEntry());
         final int guard = UMLTransitionGuard.getGuardType(currentUMLTransition);
         switch (guard)
         {
         case UMLTransitionGuard.EVERYTIMES:
            if (hasEverytimesTransition)
            {
               throw new SDMParseException("Activity has more than one 'each time' transition.", currentUMLTransition);
            }

            hasEverytimesTransition = true;
            // nextActivity has to be unexplored (level=0)
            if (nextFlowActivity.getLevel() != 0)
            {
               throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX, currentUMLTransition);
            } else
            {
               nextFlowActivity.setLevel(level);
               level++;
               // Store transition for later use
               transitionList.add(currentUMLTransition);
               checkForEachStructure(nextFlowActivity, transitionList);
            }
            break;
         case UMLTransitionGuard.TERMINATION:
            if (hasTerminationTransition)
            {
               throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + "Story activity has more than one 'end' transition.", currentUMLTransition);
            }
            hasTerminationTransition = true;
            // go on ...

         default:

            if (guard != UMLTransitionGuard.TERMINATION)
            {
               hasOtherTransitions = true;
            }

            // next activity has to be unexplored or with same level
            if (nextFlowActivity.getLevel() == 0)
            {
               // next activity is unexplored ...
               // go on
               nextFlowActivity.setLevel(flowActivity.getLevel());
               checkForEachStructure(nextFlowActivity, transitionList);
            } else
            {
               // next activity is explored ...
               // if same level don't care, otherwise check for errors
               if (nextFlowActivity.getLevel() != flowActivity.getLevel())
               {
                  // first, get the corresponding ForEach transition if exists ...
                  Iterator<?> iterExit = nextFlowActivity.getUMLActivity().iteratorOfExit();
                  UMLTransition tmpUMLTransition;
                  FlowActivity tmpFlowActivity = null;

                  while (iterExit.hasNext() && tmpFlowActivity == null)
                  {
                     tmpUMLTransition = (UMLTransition) iterExit.next();
                     if (UMLTransitionGuard.getGuardType(tmpUMLTransition) == UMLTransitionGuard.EVERYTIMES)
                     {
                        tmpFlowActivity = getFromFlowActivities(tmpUMLTransition.getRevEntry());
                     }
                  }
                  // ... and then compare the levels
                  if (tmpFlowActivity != null)
                  {
                     // the next activity is a ForEach activity

                     // Important: This isn't a backward edge anymore
                     // nessesary for explore()
                     currentUMLTransition.setIsForwardEdge(true);

                     if (tmpFlowActivity.getLevel() != flowActivity.getLevel())
                     {
                        throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + "This transition can't end here.", currentUMLTransition);
                     }
                  } else
                  {
                     throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + "The 'for each' flow is not independent.", currentUMLTransition);
                  }
               }
            }

         } // switch

      } // while

      // at least some basic checks
      final UMLActivity umlActivity = flowActivity.getUMLActivity();
      final String activityName = umlActivity != null ? umlActivity.getName() : "unknown activity";
      final String activityNamePrefix = "[Activity: " + activityName + "] ";
      if (flowActivity.isForEach())
      {
         if (hasOtherTransitions)
         {
            throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX
                  + activityNamePrefix + "A 'for each' activity can only have 'each time' and 'termination' transitions.", umlActivity);
         }
         if (!hasTerminationTransition)
         {
            throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + activityNamePrefix + "Story activity is set 'for each' but has no 'termination' transition.", umlActivity);
         }
      } else
      {
         if (hasEverytimesTransition)
         {
            throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + activityNamePrefix + "Story activity has an 'each time' transition, but is not set 'for each'",
                  umlActivity);
         }
         if (hasTerminationTransition)
         {
            throw new SDMParseException(FOR_EACH_STRUCTURE_ERROR_PREFIX + activityNamePrefix + "Story activity has an 'termination' transition, but is not set 'for each'",
                  umlActivity);
         }

      }
   } // checkForEachStructure

   /**
    * time: counter for dfs() will be incremented with each new node
    */
   static int time;

   /**
    * Depth First Search.
    *
    * @param currentFlowActivity
    *           No description provided
    */
   private final void dfs(final FlowActivity currentFlowActivity)
   {
      currentFlowActivity.setGrayTime(time);
      time++;

      UMLTransition currentUMLTransition;
      FlowActivity nextFlowActivity;

      Iterator<?> iter = currentFlowActivity.getUMLActivity().iteratorOfExit();
      while (iter.hasNext())
      {
         currentUMLTransition = (UMLTransition) iter.next();
         currentUMLTransition.setIsMarked(false);
         nextFlowActivity = getFromFlowActivities(currentUMLTransition.getRevEntry());

         if (nextFlowActivity == null)
         {
            UMLActivityDiagram diagram = currentFlowActivity.getUMLActivity().getActivityDiagram();
            UMLActivity activity = currentUMLTransition.getRevEntry();
            throw new SDMParseException("UMLActivity '" + activity.getName() + "' has no FlowActivity:\nmaybe it wasn't added to UMLActivityDiagram '"
                  + diagram.getFullName() + "'", activity);
         }

         // If there is a NullPointerException here, there might be a
         // UMLActivity that was not added to the activity diagram.
         if (nextFlowActivity.getBlackTime() > 0)
         {
            // 1. Black vertex, forward edge, do not visit
            currentUMLTransition.setIsForwardEdge(true);
         } else
         {
            if (nextFlowActivity.getGrayTime() > 0)
            {
               // 2. Gray vertex, backward edge, do not visit
               currentUMLTransition.setIsForwardEdge(false);
            } else
            {
               // 3. White vertex, forward edge, visit it
               currentUMLTransition.setIsForwardEdge(true);
               dfs(nextFlowActivity);
            }
         }
      }

      currentFlowActivity.setBlackTime(time);
      time++;
   } // dfs

   /**
    * @param curActivity
    *           No description provided
    * @param curTransition
    *           No description provided
    * @return true, if curTransition is the only transition which is leaving from curActivity.
    */
   private final boolean isTheOnlyTransition(final UMLActivity curActivity, final UMLTransition curTransition)
   {
      Iterator<?> iter = curActivity.iteratorOfExit();
      while (iter.hasNext())
      {
         if (((UMLTransition) iter.next()) != curTransition)
         {
            return false;
         }
      }
      return true;
   }

   /**
    * @param intervalList
    *           No description provided
    * @param newInterval
    *           No description provided
    * @return false if it is impossible to place the interval in the list or returns true and places the interval in the
    *         list, so that the smallest intervall is on first position.
    */
   private final boolean isIntervalNested(final FLinkedList<FLinkedList<?>> intervalList, final FLinkedList newInterval)
   {
      if (intervalList.isEmpty())
      {
         intervalList.add(newInterval);
         return true;
      } else
      {
         FLinkedList<?> curInterval;
         // has somebody a better idea
         int curIntegerLeft;
         // has somebody a better idea
         int curIntegerRight;
         int newIntegerLeft = ((Integer) newInterval.get(2)).intValue();
         int newIntegerRight = ((Integer) newInterval.get(3)).intValue();

         ListIterator<FLinkedList<?>> iterator = intervalList.listIterator();
         while (iterator.hasNext())
         {
            curInterval = iterator.next();
            curIntegerLeft = ((Integer) curInterval.get(2)).intValue();
            curIntegerRight = ((Integer) curInterval.get(3)).intValue();

            if ((curIntegerLeft < newIntegerLeft) && (newIntegerRight < curIntegerRight))
            {
               // insert before curInterval
               // intervalList.insert (iterator, newInterval);
               intervalList.add(iterator, newInterval);
               return true;
            } else if ((newIntegerLeft >= curIntegerLeft) || (curIntegerRight >= newIntegerRight))
            {
               // is not nested
               return false;
            }
         } // while
           // OK all intervals in the List are nested

         intervalList.add(newInterval);
      }
      return true;
   } // intervalIsNested

   /**
    * Run dfs and checkForEachStructure before in exactly this order!
    *
    * @param seq
    *           Seqence to be filled
    * @param startFlowActivity
    *           A flowActivity where to start from
    * @param endFlowActivity
    *           A flowActivity up to which CFG will be explored (may be null for open end)
    */
   private final void explore(final Seq seq, final FlowActivity startFlowActivity, final FlowActivity endFlowActivity)
   {
      // Check if recursion terminates here
      if ((startFlowActivity.getUMLActivity() instanceof UMLStopActivity) || (startFlowActivity == endFlowActivity))
      {
         seq.add(startFlowActivity);
         return;
      }

      UMLTransition currentUMLTransition = null;

      // Check incoming backward edges

      UMLTransition backUMLTransition = null;
      FLinkedList interval;
      FLinkedList<FLinkedList<?>> intervalList = new FLinkedList<>();
      @SuppressWarnings("unchecked")
      Iterator<UMLTransition> iter = startFlowActivity.getUMLActivity().iteratorOfEntry();
      while (iter.hasNext())
      {
         currentUMLTransition = iter.next();
         if (!currentUMLTransition.getIsMarked() && !currentUMLTransition.getIsForwardEdge())
         {
            // found an unmarked backward edge
            if (isTheOnlyTransition(currentUMLTransition.getRevExit(), currentUMLTransition))
            {
               // backward transition of a head controlled repetition
               // only one allowed
               if (backUMLTransition == null)
               {
                  backUMLTransition = currentUMLTransition;
               } else
               {
                  throw new SDMParseException("Repetition error: Misplaced backward edges (1).", currentUMLTransition);
               }
            } else
            {
               // backward transition of a foot controlled repetition
               // n allowed but intervals (greyTime, blackTime) have to be nested
               interval = new FLinkedList<Object>();
               interval.add(0, null);
               interval.add(1, currentUMLTransition);
               interval.add(2, new Integer(getFromFlowActivities(currentUMLTransition.getRevExit()).getGrayTime()));
               interval.add(3, new Integer(getFromFlowActivities(currentUMLTransition.getRevExit()).getBlackTime()));
               if (!isIntervalNested(intervalList, interval))
               {
                  throw new SDMParseException("Repetition error: Misplaced backward edges (2).", currentUMLTransition);
               }
            }
         }
      }

      // Now choose the backward transition of the repetition which will be explored next
      // at first those in the list
      if (!intervalList.isEmpty())
      {
         backUMLTransition = ((UMLTransition) ((FLinkedList) intervalList.getFirst()).get(1));
      }

      if (backUMLTransition != null)
      {
         // found a repetition
         backUMLTransition.setIsMarked(true);
         exploreRepetition(seq, backUMLTransition, startFlowActivity, endFlowActivity);
      } else
      {
         // No unmarked incoming backward edges exist
         // Analyse unmarked outgoing forward edges (but no Everytimes Edges)
         FLinkedList list = new FLinkedList();
         Iterator iterExit = startFlowActivity.getUMLActivity().iteratorOfExit();
         while (iterExit.hasNext())
         {
            currentUMLTransition = (UMLTransition) iterExit.next();

            if (!currentUMLTransition.getIsMarked() && currentUMLTransition.getIsForwardEdge()
                  && !(UMLTransitionGuard.getGuardType(currentUMLTransition) == UMLTransitionGuard.EVERYTIMES))
            {
               list.add(currentUMLTransition);
            }
         }

         Iterator iterUMLTransitions;
         switch (list.size())
         { // # of unmarked forward edges

         case 0:
            // there is no next activity
            seq.add(startFlowActivity);
            throw new SDMParseException("Abnormal termination in control flow", startFlowActivity.getUMLActivity());
         case 1:
            // Case B: Sequence (one outgoing transition)
            iterUMLTransitions = list.iterator();
            currentUMLTransition = (UMLTransition) iterUMLTransitions.next();
            //
            seq.add(startFlowActivity);
            if (startFlowActivity != endFlowActivity)
            {
               currentUMLTransition.setIsMarked(true);
               explore(seq, getFromFlowActivities(currentUMLTransition.getRevEntry()), endFlowActivity);
            }
            break;
         case 2:
            // Case C: Selection (two outgoing transitions)
            iterUMLTransitions = list.iterator();
            UMLTransition firstUMLTransition = (UMLTransition) iterUMLTransitions.next();
            UMLTransition secondUMLTransition = (UMLTransition) iterUMLTransitions.next();

            exploreSelection(seq, firstUMLTransition, secondUMLTransition, startFlowActivity, endFlowActivity);
            break;
         default:
            // Case D: Switch (three or more outgoing transitions)
            throw new SDMParseException("One Activity has 3 or more outgoing transitions. (Case structure not yet supported)",
                  startFlowActivity.getUMLActivity());
         } // switch

      } // else

   } // explore

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param set
    *           No description provided
    * @param start
    *           No description provided
    * @param end
    *           No description provided
    */
   private final void exploreBackwardAndCollect(final FHashSet set, final FlowActivity start, final FlowActivity end)
   {
      if (start != end)
      {
         UMLTransition transition;
         Iterator iter = start.getUMLActivity().iteratorOfEntry();
         while (iter.hasNext())
         {
            transition = (UMLTransition) iter.next();
            if (!transition.getIsMarked())
            {
               if (set.add(getFromFlowActivities(transition.getRevExit())))
               { // put returns null is element was not in the list

                  exploreBackwardAndCollect(set, getFromFlowActivities(transition.getRevExit()), end);
               }
            }
         }
      }
   } // exploreBackwardAndCollect

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param seq
    *           No description provided
    * @param backUMLTransition
    *           No description provided
    * @param start
    *           No description provided
    * @param end
    *           No description provided
    */
   private final void exploreRepetition(final Seq seq, final UMLTransition backUMLTransition, final FlowActivity start, final FlowActivity end)
   {
      UMLTransition leavingUMLTransition = null;
      UMLTransition nextUMLTransition = null;
      UMLTransition currentUMLTransition;
      FHashSet nodesAfterRepetition;
      FHashSet nodesInRepetition;
      FlowActivity middle = getFromFlowActivities(backUMLTransition.getRevExit());

      Iterator iter = middle.getUMLActivity().iteratorOfExit();
      while (iter.hasNext())
      {
         currentUMLTransition = (UMLTransition) iter.next();
         if (!currentUMLTransition.getIsMarked())
         {
            if (!currentUMLTransition.getIsForwardEdge())
            {
               throw new SDMParseException("Repetition error: Last activity has more than one backward edge.", currentUMLTransition);
            }
            if (leavingUMLTransition == null)
            {
               leavingUMLTransition = currentUMLTransition;
            } else
            {
               throw new SDMParseException("Repetition error: Last activity has more than one leaving transition", currentUMLTransition);
            }
         }
      } // while

      // Now leavingUMLTransition is null or is the only leaving transition out of a foot controlled repetition
      if (leavingUMLTransition == null)
      {
         nodesInRepetition = new FHashSet();
         nodesInRepetition.add(middle);
         exploreBackwardAndCollect(nodesInRepetition, middle, start);
         iter = start.getUMLActivity().iteratorOfExit();
         while (iter.hasNext())
         {
            currentUMLTransition = (UMLTransition) iter.next();
            if (!nodesInRepetition.contains(getFromFlowActivities(currentUMLTransition.getRevEntry())))
            {
               // transition leaves the repetition
               if (leavingUMLTransition == null)
               {
                  leavingUMLTransition = currentUMLTransition;
               } else
               {
                  throw new SDMParseException("Repetition error: Activity has more than one leaving transition", currentUMLTransition);
               }
            } else
            {
               // transition goes into repetition
               if (nextUMLTransition == null)
               {
                  nextUMLTransition = currentUMLTransition;
               } else
               {
                  throw new SDMParseException("Repetition error: Activity has more than one next transition", currentUMLTransition);
               }
            }
         } // while

         if (leavingUMLTransition == null)
         {
            throw new SDMParseException("Repetition error: Repetition has no leaving transition", start.getUMLActivity());
         }
         if (nextUMLTransition == null)
         {
            throw new SDMParseException("Repetition error: Repetition has no next transition", start.getUMLActivity());
         }

         nodesInRepetition.clear();

         // now check if the body and the rest is disjoint
         nodesAfterRepetition = new FHashSet();
         nodesInRepetition.add(getFromFlowActivities(nextUMLTransition.getRevEntry()));
         exploreAndCollect(nodesInRepetition, getFromFlowActivities(nextUMLTransition.getRevEntry()), middle);
         exploreAndCompare(nodesInRepetition, nodesAfterRepetition, getFromFlowActivities(leavingUMLTransition.getRevEntry()), end);

         // if (nodesInRepetition.intersection (nodesAfterRepetition).size () != 0)
         if (intersection(nodesInRepetition, nodesAfterRepetition).size() != 0)
         {
            throw new SDMParseException("Repetition error: Error in structure", start.getUMLActivity());
         } else
         {
            // Objects can be removed from memory
            nodesInRepetition = null;
            nodesAfterRepetition = null;

            // OK, all checks done, lets make a head controlled repetition
            checkTransitionGuards(leavingUMLTransition.getGuard(), nextUMLTransition.getGuard());
            Seq bodySeq = new Seq();

            // start is NOP and not used for code generation but for layout the activity diagram
            bodySeq.add(start);

            leavingUMLTransition.setIsMarked(true);
            nextUMLTransition.setIsMarked(true);
            // jump over next activity (start), it is Nop
            explore(bodySeq, getFromFlowActivities(nextUMLTransition.getRevEntry()), middle);

            Rep rep = new Rep(bodySeq, nextUMLTransition, leavingUMLTransition, Rep.HEAD_CONTROLLED);

            if (!(start.getUMLActivity() instanceof UMLNopActivity))
            {
               rep.setHeadedByActivity(true);
               start.setContext("firstActivity");
            }

            seq.add(rep);
            explore(seq, getFromFlowActivities(leavingUMLTransition.getRevEntry()), end);
         }
      } else
      {
         // now check if the body and the rest is disjoint
         nodesAfterRepetition = new FHashSet();
         nodesInRepetition = new FHashSet();
         exploreAndCollect(nodesInRepetition, start, middle);
         exploreAndCompare(nodesInRepetition, nodesAfterRepetition, getFromFlowActivities(leavingUMLTransition.getRevEntry()), end);

         if (intersection(nodesInRepetition, nodesAfterRepetition).size() != 0)
         {
            throw new SDMParseException("Repetition error: Error in structure", start.getUMLActivity());
         } else
         {
            // OK, all checks done, lets make a foot controlled repetition
            checkTransitionGuards(leavingUMLTransition.getGuard(), backUMLTransition.getGuard());
            Seq bodySeq = new Seq();
            leavingUMLTransition.setIsMarked(true);
            explore(bodySeq, start, middle);
            Rep rep = new Rep(bodySeq, backUMLTransition, leavingUMLTransition, Rep.FOOT_CONTROLLED);
            seq.add(rep);
            explore(seq, getFromFlowActivities(leavingUMLTransition.getRevEntry()), end);
         }
      } // if

   } // exploreRepetition

   /**
    * Walk through the tree and collect nodes until a node was foud which is in a given set.
    *
    * @param leftSet
    *           The given set with nodes to compare
    * @param rightSet
    *           The set where to store the collected (visited) nodes in
    * @param start
    *           The node where to start from
    * @param end
    *           The deepest node to go
    */
   private final void exploreAndCompare(final FHashSet leftSet, final FHashSet rightSet, final FlowActivity start, final FlowActivity end)
   {
      boolean visited = !rightSet.add(start);
      boolean found = leftSet.contains(start);
      // put returns null if element was not in the list

      if (!visited && !found && start != end)
      {
         UMLTransition transition;
         Iterator iter = start.getUMLActivity().iteratorOfExit();
         while (iter.hasNext())
         {
            transition = (UMLTransition) iter.next();
            if (!transition.getIsMarked())
            {
               exploreAndCompare(leftSet, rightSet, getFromFlowActivities(transition.getRevEntry()), end);
            }
         }
      }
   } // exploreAndCompare

   /**
    * Walk through the tree and collect nodes.
    *
    * @param set
    *           The set where to store the collected (visited) nodes in
    * @param start
    *           The node where to start from
    * @param end
    *           The deepest node to go
    */
   private final void exploreAndCollect(final FHashSet set, final FlowActivity start, final FlowActivity end)
   {
      if (start != end)
      {
         UMLTransition transition;
         Iterator iter = start.getUMLActivity().iteratorOfExit();
         while (iter.hasNext())
         {
            transition = (UMLTransition) iter.next();
            if (!transition.getIsMarked())
            {
               if (set.add(getFromFlowActivities(transition.getRevEntry())))
               { // put returns null if element was not in the list

                  exploreAndCollect(set, getFromFlowActivities(transition.getRevEntry()), end);
               }
            }
         }
      }
   } // exploreAndCollect

   /**
    * Checks if the two guards are compatible.
    *
    * @param leftGuard
    *           Type of UMLTransitionGuard
    * @param rightGuard
    *           Type of UMLTransitionGuard
    */
   private final void checkTransitionGuards(final UMLTransitionGuard leftGuard, final UMLTransitionGuard rightGuard)
   {
      final int leftType = (leftGuard == null ? UMLTransitionGuard.NONE : leftGuard.getType());
      final int rightType = (rightGuard == null ? UMLTransitionGuard.NONE : rightGuard.getType());
      switch (leftType)
      {
      case UMLTransitionGuard.SUCCESS:
         if (rightType == UMLTransitionGuard.FAILURE)
         {
            return;
         }
         if (rightType == UMLTransitionGuard.ELSE)
         {
            rightGuard.setType(UMLTransitionGuard.FAILURE);
            return;
         }
         break;
      case UMLTransitionGuard.FAILURE:
         if (rightType == UMLTransitionGuard.SUCCESS)
         {
            return;
         }
         if (rightType == UMLTransitionGuard.ELSE)
         {
            rightGuard.setType(UMLTransitionGuard.SUCCESS);
            return;
         }
         break;
      case UMLTransitionGuard.BOOL:
         if (rightType == UMLTransitionGuard.ELSE)
         {
            return;
         }
         break;
      case UMLTransitionGuard.ELSE:
         if (rightType == UMLTransitionGuard.BOOL)
         {
            return;
         }
         break;
      case UMLTransitionGuard.EXCEPTION:
         if (rightType == UMLTransitionGuard.NONE)
         {
            return;
         }
         break;
      case UMLTransitionGuard.NONE:
         if (rightType == UMLTransitionGuard.EXCEPTION)
         {
            return;
         }
         break;
      } // switch

      // throw an exception to notify the user
      throw new SDMParseException("Transition guards " + rightGuard.getBoolExpr() + " and " + leftGuard.getBoolExpr() + " are incompatible.", leftGuard);
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param seq
    *           No description provided
    * @param firstUMLTransition
    *           No description provided
    * @param secondUMLTransition
    *           No description provided
    * @param start
    *           No description provided
    * @param end
    *           No description provided
    */
   private final void exploreSelection(final Seq seq, final UMLTransition firstUMLTransition, final UMLTransition secondUMLTransition,
         final FlowActivity start, final FlowActivity end)
   {
      checkTransitionGuards(firstUMLTransition.getGuard(), secondUMLTransition.getGuard());

      FHashSet leftSet = new FHashSet();
      FHashSet rightSet = new FHashSet();
      Seq firstSeq;
      Seq secondSeq;
      Flow sel;

      // first, fill leftSet with FlowActivities ...
      leftSet.add(getFromFlowActivities(firstUMLTransition.getRevEntry()));
      exploreAndCollect(leftSet, getFromFlowActivities(firstUMLTransition.getRevEntry()), end);

      // then, fill rightSet until an activity is in both sets
      exploreAndCompare(leftSet, rightSet, getFromFlowActivities(secondUMLTransition.getRevEntry()), end);

      FHashSet interSet = intersection(leftSet, rightSet);

      // size()=0 means disjoint
      // size()=1 means one common Activity found (further check required)
      // size()>=2 means error
      switch (interSet.size())
      {
      case 0:
         // disjoint
         firstSeq = new Seq();
         secondSeq = new Seq();
         firstUMLTransition.setIsMarked(true);
         secondUMLTransition.setIsMarked(true);
         explore(firstSeq, getFromFlowActivities(firstUMLTransition.getRevEntry()), end);
         explore(secondSeq, getFromFlowActivities(secondUMLTransition.getRevEntry()), end);

         if (end == null)
         {
            // If one transition guard is ELSE it will be in the else flow
            // and SUCCESS will be allways in the then flow (looks nicer)
            // another possibility is to look which flow has mor activities, this one is the common flow
            // the other is the then flow (more activities = check distance between gray and black)
            if ((UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.ELSE)
                  || (UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.FAILURE))
            {
               sel = new Sel(start, secondSeq, secondUMLTransition, firstSeq, firstUMLTransition);
            } else if (UMLTransitionGuard.getGuardType(secondUMLTransition) == UMLTransitionGuard.NONE
                  && UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.EXCEPTION)
            {
               sel = new ExceptionFlow(start, secondSeq, firstSeq, firstUMLTransition.getGuard());
            } else if (UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.NONE
                  && UMLTransitionGuard.getGuardType(secondUMLTransition) == UMLTransitionGuard.EXCEPTION)
            {
               sel = new ExceptionFlow(start, firstSeq, secondSeq, secondUMLTransition.getGuard());
            } else
            {
               sel = new Sel(start, firstSeq, firstUMLTransition, secondSeq, secondUMLTransition);
            }
            seq.add(sel);
         } else
         {
            // end != null we are in a Rep or Sel
            // so the flow which reaches end will be a common flow, the other one a then flow and no else flow
            if (!leftSet.contains(end))
            {
               // end is in the right set
               sel = new Sel(start, firstSeq, firstUMLTransition, null, secondUMLTransition);
               seq.add(sel);
               seq.add(secondSeq);
            } else
            {
               // end is in the left set
               sel = new Sel(start, secondSeq, secondUMLTransition, null, firstUMLTransition);
               seq.add(sel);
               seq.add(firstSeq);
            }
         }
         break;
      case 1:
         // There exist one common element in the flow
         // further checks nessesary (then, else and common disjoint ?)
         FlowActivity firstCommonFlowActivity = (FlowActivity) interSet.iterator().next();
         interSet.clear();
         leftSet.clear();
         rightSet.clear();

         leftSet.add(getFromFlowActivities(firstUMLTransition.getRevEntry()));
         exploreAndCollect(leftSet, getFromFlowActivities(firstUMLTransition.getRevEntry()), firstCommonFlowActivity);
         leftSet.remove(firstCommonFlowActivity);

         rightSet.add(getFromFlowActivities(secondUMLTransition.getRevEntry()));
         exploreAndCollect(rightSet, getFromFlowActivities(secondUMLTransition.getRevEntry()), firstCommonFlowActivity);
         rightSet.remove(firstCommonFlowActivity);

         // union leftSet and rightSet and check if there are no other transitions to a node under firstCommonActivity

         // leftSet.union (rightSet); did nothing

         exploreAndCompare(leftSet, interSet, firstCommonFlowActivity, end);
         if (intersection(leftSet, interSet).size() != 0)
         {
            String signature = getUmlActivityFullPath(firstCommonFlowActivity.getUMLActivity());

            throw new SDMParseException("Error in selection structure: " + signature, firstCommonFlowActivity.getUMLActivity());
         }

         // these objects can be collected now
         interSet = null;
         leftSet = null;
         rightSet = null;

         UMLTransition currentUMLTransition;
         Iterator iter = firstCommonFlowActivity.getUMLActivity().iteratorOfExit();
         while (iter.hasNext())
         {
            currentUMLTransition = (UMLTransition) iter.next();
            if (!currentUMLTransition.getIsMarked() && !currentUMLTransition.getIsForwardEdge())
            {
               throw new SDMParseException("Repetition overlaps selection", currentUMLTransition);
            }
         } // while

         // OK Checks performed Let's do it
         firstSeq = new Seq();
         secondSeq = new Seq();
         firstUMLTransition.setIsMarked(true);
         secondUMLTransition.setIsMarked(true);

         explore(firstSeq, getFromFlowActivities(firstUMLTransition.getRevEntry()), firstCommonFlowActivity);
         firstSeq.removeLastFlowActivity(firstCommonFlowActivity);

         explore(secondSeq, getFromFlowActivities(secondUMLTransition.getRevEntry()), firstCommonFlowActivity);
         secondSeq.removeLastFlowActivity(firstCommonFlowActivity);

         // If one transition guard is ELSE it will be in the else flow
         // and SUCCESS will be allways in the then flow (looks nicer)
         if ((UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.ELSE)
               || (UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.FAILURE))
         {
            sel = new Sel(start, secondSeq, secondUMLTransition, firstSeq, firstUMLTransition);
         } else if (UMLTransitionGuard.getGuardType(secondUMLTransition) == UMLTransitionGuard.NONE
               && UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.EXCEPTION)
         {
            sel = new ExceptionFlow(start, secondSeq, firstSeq, firstUMLTransition.getGuard());
         } else if (UMLTransitionGuard.getGuardType(firstUMLTransition) == UMLTransitionGuard.NONE
               && UMLTransitionGuard.getGuardType(secondUMLTransition) == UMLTransitionGuard.EXCEPTION)
         {
            sel = new ExceptionFlow(start, firstSeq, secondSeq, secondUMLTransition.getGuard());
         } else
         {
            sel = new Sel(start, firstSeq, firstUMLTransition, secondSeq, secondUMLTransition);
         }

         seq.add(sel);
         // and explore the rest
         explore(seq, firstCommonFlowActivity, end);
         break;
      default:
      {
         String signature = getUmlActivityFullPath(secondUMLTransition.getSource());
         throw new SDMParseException("Error in selection structure: " + signature, secondUMLTransition.getRevEntry());
      }
      }// switch

   } // exploreSelection

   private String getUmlActivityFullPath(final UMLActivity umlActivity)
   {
      FMethod storyOperation = umlActivity.getActivityDiagram().getStoryMethod();
      String signature = storyOperation.getName() + "/" + umlActivity.getName();
      FElement parent = storyOperation.getParent();
      while (parent != null)
      {
         if (!parent.getName().equals("") && !parent.getName().equals("generatedFujabaProject"))
         {
            signature = parent.getName() + "/" + signature;
         }
         parent = parent.getParentElement();
      }
      return signature;
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param setOne
    *           No description provided
    * @param setTwo
    *           No description provided
    * @return No description provided
    */
   public FHashSet intersection(final FHashSet setOne, final FHashSet setTwo)
   {
      FHashSet tmpSet = new FHashSet();

      Iterator iter = setTwo.iterator();
      Object obj;
      while (iter.hasNext())
      {
         obj = iter.next();
         if (setOne.contains(obj))
         {
            tmpSet.add(obj);
         }
      }
      return tmpSet;
   }

   /**
    * Creates a correspondent FlowActivity for each activity in this diagram. Necessary for code generation of the
    * related method.
    *
    * @param diag
    *           No description provided
    */
   public void createFlowAssociations(final UMLDiagram diag)
   {
      Iterator iter = diag.iteratorOfElements();
      while (iter.hasNext())
      {
         ASGElement tmpItem = (ASGElement) iter.next();
         if (tmpItem instanceof UMLActivity)
         {
            FlowActivity tmpFlowActivity = new FlowActivity((UMLActivity) tmpItem);
            flowActs.put(tmpItem, tmpFlowActivity);
         }
      }
   } // createFlowAssociations

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeFlowAssociations()
   {
      flowActs.clear();
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FHashMap flowActs = new FHashMap();

   /**
    * Get the fromFlowActivities attribute of the Sequencer object
    *
    * @param act
    *           No description provided
    * @return The fromFlowActivities value
    */
   private FlowActivity getFromFlowActivities(final UMLActivity act)
   {
      return (FlowActivity) flowActs.get(act);
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param diag
    *           No description provided
    * @return No description provided
    */
   public Seq exploreCFG(final UMLActivityDiagram diag)
   {
      createFlowAssociations(diag);
      FlowActivity startActivity = getFromFlowActivities(diag.getStartActivity());
      Seq seq = exploreCFG(startActivity);
      Iterator iter = flowActs.entrySet().iterator();
      while (iter.hasNext())
      {
         Object obj = ((Map.Entry) iter.next()).getValue();
         if (obj instanceof FlowActivity)
         {
            FlowActivity flow = (FlowActivity) obj;
            flow.init(getEngine());
         }
      }
      removeFlowAssociations();
      return seq;
   }

   @Override
   public Token createToken(final FElement element)
   {
      UMLActivityDiagram actDiag = (UMLActivityDiagram) element;
      ASGElementToken token = new ASGElementToken();
      token.setElement(actDiag);
      token.addToChildren(exploreCFG(actDiag));
      return token;
   }

   @Override
   public boolean isResponsible(final FElement element)
   {
      return (element instanceof UMLActivityDiagram) && !(element instanceof UMLStatechart);
   }
}

/*
 * $Log$ Revision 1.9 2007/03/22 16:35:48 l3_g5 Sequencer is not responsible for statecharts
 */

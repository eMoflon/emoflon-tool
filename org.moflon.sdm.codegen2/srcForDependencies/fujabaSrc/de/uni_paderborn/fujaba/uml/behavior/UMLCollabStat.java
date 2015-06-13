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


import de.fujaba.text.FParsedElement;
import de.fujaba.text.FTextReference;
import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FLinkedList;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *                0..n    callTarget    0..1
 * UMLCollabStat ---------------------------- UMLObject
 *                collabStats     callTarget
 *
 *                0..n    callSource    0..1
 * UMLCollabStat ---------------------------- UMLObject
 *                collabStat      callSource
 *
 *                0..1    masterCollabStat    0..1
 * UMLCollabStat ---------------------------------- UMLStoryPattern
 *                revMasterCollabStat    myPattern
 *
 *                0..n    subStats    0..1
 * UMLCollabStat -------------------------- UMLCollabStat
 *                subStats      fatherStat
 *
 *             0..1   masterCollabStat   0..1
 * UMLDiagram -------------------------------- UMLCollabStat
 *             diag                collabStat
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLCollabStat extends UMLDiagramItem implements FTextReference, FParsedElement
{
   
   // example: 3a.1b.1.2.3
   private static final Pattern PATH_PATTERN = Pattern
         .compile("(\\d+)([_a-zA-Z]\\w*)?" + // first part (3a)
               "(?:\\.(" + // rest of path - ignore dot in group
               "\\d+(?:[_a-zA-Z]\\w*)?" + // next part in path (1b)
               "(?:\\.\\d+(?:[_a-zA-Z]\\w*)?)*" + // remainder (.1.2.3)
               "))?");// close groups

   public final static int TYPE_CALL = 0;

   public final static int TYPE_COND = 1;

   public final static int TYPE_LOOP = 2;
   
   public static final String CALL_TARGET_PROPERTY = "callTarget";
   public static final String MY_PATTERN_PROPERTY = "myPattern";
   private static final String ASSIGN_TGT_TYPE_PROPERTY = "assignTgtType";
   public static final String FATHER_STAT_PROPERTY = "fatherStat";
   public static final String SUB_STATS_PROPERTY = "subStats";


   protected UMLCollabStat(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * If callOnElementsOfSet is true, the Collaboration Statement is executed on every element of
    * the set instead of executing it on the set itself. callOnElementsOfSet should do nothing if
    * the set is delete type. The Collaboration Editor should also allow to set this flag only if
    * the UMLObject is a set and of create/none type. The flag is used in JavaFactory to generate
    * code to iterate through the set and call the method.
    */
   private boolean callOnElementsOfSet = false;


   public boolean isCallOnElementsOfSet()
   {
      return this.callOnElementsOfSet;
   }


   public void setCallOnElementsOfSet(boolean flag)
   {
      if (this.callOnElementsOfSet != flag)
      {
         boolean oldFlag = this.callOnElementsOfSet;
         this.callOnElementsOfSet = flag;
         firePropertyChange("callOnElementsOfSet", Boolean.valueOf(oldFlag),
               Boolean.valueOf(flag));
      }
   }

 
 private boolean reflective;

   
   public boolean isReflective ()
   {
      return reflective;
   }

   /**
    * Used for reflective method calls. method call specifications like
    * $methodNameAsString(arg1, arg2)
    * have to be converted to something like
    * ...call(methodname, arg1, arg2)
    * 
    * @return expression for reflective method calls as parameter list
    */
   public String getReflectiveExpr ()
   {
      String callText = getCallText();
      if (callText == null)
      {
         return null;
      }
      int startPos = callText.indexOf('(');
      int endPos = callText.lastIndexOf(')');
      if (startPos == -1 || endPos == -1)
      {
         return callText;
      } else
      {
         String betweenBraces = callText.substring(startPos + 1, endPos).trim();
         if (betweenBraces.length() == 0)
         {
            return callText.substring(0, startPos);
         }
         else
         {
            return callText.substring(0, startPos) + ", " + betweenBraces;
         }       
      }      
   }
  
   public void setReflective ( boolean reflective )
   {
      if (reflective != this.reflective)
      {
         this.reflective = reflective;
         firePropertyChange("reflective", !reflective, reflective);
      }
   }
   
   private int number = 0;


   public int getNumber()
   {
      return this.number;
   }


   public void setNumber(int number)
   {
      if (this.number != number)
      {
         int oldNumber = this.number;
         this.number = number;
         firePropertyChange("number", oldNumber, number);
         createNoText();
      }
   }

   private transient String noText = null;


   public String getNoText()
   {
      if (noText == null)
      {
         createNoText();
      }
      return noText;
   } // getNoText


   protected void createNoText()
   {
      String oldNoText = this.noText;
      String newNoText = null;
      if (this.fatherStat == null)
      {
         // rootStat has no text
      }
      else
      {
         String fatherText = this.fatherStat.getNoText();
         if (fatherText != null && fatherText.length() > 0)
         {
            StringBuffer buffer = new StringBuffer(fatherText);
            buffer.append('.');
            buffer.append(this.number);
            newNoText = buffer.toString();
         }
         else
         {
            newNoText = Integer.toString(this.number);
         }
      }

      if ((oldNoText == null && newNoText != null)
            || (oldNoText != null && !oldNoText.equals(newNoText)))
      {
         if (this.noText == null && this.text == null)
         {
            // avoid endless loop if (full) text is not computed yet
            this.noText = "null";
         }
         String oldFullText = getText();
         this.noText = newNoText;
         fireTextChange("noText", oldNoText, newNoText, oldFullText);
         for (Iterator subStats = this.iteratorOfSubStats(); subStats.hasNext();)
         {
            UMLCollabStat subStat = (UMLCollabStat) subStats.next();
            subStat.createNoText();
         }
      }
   }


   /**
    * @deprecated (gets deleted in 5.1) Use setNumber and setFatherStat instead.
    */
   public String setNoText(String noText) throws NumberFormatException
   {
      if (noText != null)
      {
         int lastDot = noText.lastIndexOf('.');
         String parentPath = null;
         if (lastDot > -1)
         {
            parentPath = noText.substring(0, lastDot);
            noText = noText.substring(lastDot + 1);
         }
         int number;
         if ( noText.length() > 0 )
         {
            number = Integer.parseInt(noText);
         }
         else
         {
            number = 1;
         }
         setNumber(number);

         // don't create the path up to this collab stat
         // while loading, because it's done by setting the
         // parent
         if (!Versioning.get().isInOperationalization(this) && lastDot > -1)
         {
            UMLCollabStat master = getRootFatherStat();
            if (master != this)
            {
               UMLCollabStat parent = master.findSubStat(parentPath);
               if (parent == null)
               {
                  parent = master.createSubStat(parentPath);
               }
               parent.addToSubStats(this);
            }
         }
      }
      else
      {
         setNumber(0);
      }

      return this.getNoText();
   } // setNoText


   @NoProperty
   public int getNoDepth()
   {
      return getNoDepth(0);
   }


   private int getNoDepth(int i)
   {
      UMLCollabStat father = getFatherStat();
      if (father == null)
      {
         return i;
      }
      else
      {
         return father.getNoDepth(++i);
      }
   }


   private String callText = "";


   public String getCallText()
   {
      return (callText == null) ? "" : callText;
   }


   public String setCallText(String callText)
   {
      if ((this.callText == null)
            || (this.callText != null && !this.callText.equals(callText)))
      {
         String oldFullText = getText();
         String oldValue = this.getText();
         this.callText = callText;
         fireTextChange("callText", oldValue, callText, oldFullText);
      }

      return this.callText;
   }


   private String assignTgtText = "";


   public String getAssignTgtText()
   {
      return (assignTgtText == null) ? "" : assignTgtText;
   }


   public String setAssignTgtText(String assignTgtText)
   {
      if ((this.assignTgtText == null)
            || (this.assignTgtText != null && !this.assignTgtText
                  .equals(assignTgtText)))
      {
         String oldFullText = getText();
         String oldValue = this.assignTgtText;
         this.assignTgtText = assignTgtText;
         fireTextChange("assignTgtText", oldValue, assignTgtText, oldFullText);
      }

      return this.assignTgtText;
   }


   @Property( name = ASSIGN_TGT_TYPE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FType assignTgtType;


   public FType getAssignTgtType()
   {
      return assignTgtType;
   }


   public FType setAssignTgtType(FType assignTgtType)
   {
      if ((this.assignTgtType == null)
            || (this.assignTgtType != null && !this.assignTgtType
                  .equals(assignTgtType)))
      {
         String oldFullText = getText();
         FType oldValue = this.assignTgtType;
         this.assignTgtType = assignTgtType;
         fireTextChange(ASSIGN_TGT_TYPE_PROPERTY, oldValue, assignTgtType, oldFullText);
      }

      return this.assignTgtType;
   }


   private String ifCondText = "";


   public String getIfCondText()
   {
      return (ifCondText == null) ? "" : ifCondText;
   }


   public String setIfCondText(String ifCondText)
   {
      if ((this.ifCondText == null)
            || (this.ifCondText != null && !this.ifCondText.equals(ifCondText)))
      {
         String oldFullText = getText();
         String oldValue = this.ifCondText;
         this.ifCondText = ifCondText;
         fireTextChange("ifCondText", oldValue, ifCondText, oldFullText);
      }
      this.setStatType(TYPE_COND);
      return this.ifCondText;
   }


   private String loopVarName = "";


   public String getLoopVarName()
   {
      return (loopVarName == null) ? "" : loopVarName;
   }


   public String setLoopVarName(String loopVarName)
   {
      if ((this.loopVarName == null)
            || (this.loopVarName != null && !this.loopVarName
                  .equals(loopVarName)))
      {
         String oldFullText = getText();
         String oldValue = this.loopVarName;
         this.loopVarName = loopVarName;
         fireTextChange("loopVarName", oldValue, loopVarName, oldFullText);
      }
      this.setStatType(TYPE_LOOP);
      return this.loopVarName;
   }


   private String loopStartVal = "";


   public String getLoopStartVal()
   {
      return (loopStartVal == null) ? "" : loopStartVal;
   }


   public String setLoopStartVal(String loopStartVal)
   {
      if ((this.loopStartVal == null)
            || (this.loopStartVal != null && !this.loopStartVal
                  .equals(loopStartVal)))
      {
         String oldFullText = getText();
         String oldValue = this.loopStartVal;
         this.loopStartVal = loopStartVal;
         fireTextChange("loopStartVal", oldValue, loopStartVal, oldFullText);
      }
      this.setStatType(TYPE_LOOP);
      return this.loopStartVal;
   }


   private String loopStopVal = "";


   public String getLoopStopVal()
   {
      return (loopStopVal == null) ? "" : loopStopVal;
   }


   public String setLoopStopVal(String loopStopVal)
   {
      if ((this.loopStopVal == null)
            || (this.loopStopVal != null && !this.loopStopVal
                  .equals(loopStopVal)))
      {
         String oldFullText = getText();
         String oldValue = this.loopStopVal;
         this.loopStopVal = loopStopVal;
         fireTextChange("loopStopVal", oldValue, loopStopVal, oldFullText);
      }

      return this.loopStopVal;
   }


   private String whileLoopText = "";


   public String getWhileLoopText()
   {
      return (whileLoopText == null) ? "" : whileLoopText;
   }


   public String setWhileLoopText(String whileLoopText)
   {
      if ((this.whileLoopText == null)
            || (this.whileLoopText != null && !this.whileLoopText
                  .equals(whileLoopText)))
      {
         String oldFullText = getText();
         String oldValue = this.whileLoopText;
         this.whileLoopText = whileLoopText;
         fireTextChange("whileLoopText", oldValue, whileLoopText, oldFullText);
      }
      this.setStatType(TYPE_LOOP);
      return this.whileLoopText;
   }

   private String threadId = "";


   public String getThreadId()
   {
      return (threadId == null) ? "" : threadId;
   }


   public String setThreadId(String threadId)
   {
      if ((this.threadId == null)
            || (this.threadId != null && !this.threadId.equals(threadId)))
      {
         String oldFullText = getText();
         String oldValue = this.threadId;
         this.threadId = threadId;
         fireTextChange("threadId", oldValue, threadId, oldFullText);
      }

      return this.threadId;
   }


   /**
    * <pre>
    *                0..n    callTarget    0..1
    * UMLCollabStat ---------------------------- UMLObject
    *                collabStats     callTarget
    * </pre>
    */
   @Property( name = CALL_TARGET_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.COLLAB_STATS_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject callTarget;


   public UMLObject getCallTarget()
   {
      return callTarget;
   }


   public void setCallTarget(UMLObject callTarget)
   {
      if (this.callTarget != callTarget)
      {
         UMLObject oldCallTarget = this.callTarget;
         if (this.callTarget != null)
         {
            this.callTarget = null;
            oldCallTarget.removeFromCollabStats(this);
         }
         this.callTarget = callTarget;
         if (callTarget != null)
         {
            callTarget.addToCollabStats(this);
         }
         firePropertyChange(CALL_TARGET_PROPERTY, oldCallTarget, callTarget);
      }
   }


   public static final String CALL_SOURCE_PROPERTY = "callSource";

   /**
    * <pre>
    *                0..n    callSource    0..1
    * UMLCollabStat ---------------------------- UMLObject
    *                collabStat      callSource
    * </pre>
    */
   @Property( name = CALL_SOURCE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.CALL_SOURCE_COLLAB_STATS_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject callSource;


   public boolean setCallSource(UMLObject value)
   {
      boolean changed = false;
      if (this.callSource != value)
      {
         UMLObject oldValue = this.callSource;
         if (oldValue != null)
         {
            this.callSource = null;
            oldValue.removeFromCallSourceCollabStats(this);
         }
         this.callSource = value;
         firePropertyChange(CALL_SOURCE_PROPERTY, oldValue, value );
         if (value != null)
         {
            value.addToCallSourceCollabStats(this);
         }
         changed = true;
      }
      return changed;
   }


   public UMLObject getCallSource()
   {
      return this.callSource;
   }


   /**
    * <pre>
    *                0..1    masterCollabStat    0..1
    * UMLCollabStat ---------------------------------- UMLStoryPattern
    *                revMasterCollabStat    myPattern
    * </pre>
    */
   @Property( name = MY_PATTERN_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLStoryPattern.REV_MASTER_COLLAB_STAT_PROPERTY, adornment = ReferenceHandler.Adornment.PARENT,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLStoryPattern myPattern;


   public UMLStoryPattern getMyPattern()
   {
      return myPattern;
   }


   public void setMyPattern(UMLStoryPattern myPattern)
   {
      if (this.myPattern != myPattern)
      {
         UMLStoryPattern oldMyPattern = this.myPattern;
         if (this.myPattern != null)
         {
            this.myPattern = null;
            oldMyPattern.setRevMasterCollabStat(null);
         }
         this.myPattern = myPattern;
         firePropertyChange(MY_PATTERN_PROPERTY, oldMyPattern, myPattern);
         if (myPattern != null)
         {
            myPattern.setRevMasterCollabStat(this);
         }
      }
   }


   /**
    * <pre>
    *                0..n    subStats    0..1
    * UMLCollabStat -------------------------- UMLCollabStat
    *                subStats      fatherStat
    * </pre>
    */
   @Property( name = FATHER_STAT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = SUB_STATS_PROPERTY, adornment = ReferenceHandler.Adornment.PARENT,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLCollabStat fatherStat;


   public UMLCollabStat getFatherStat()
   {
      return fatherStat;
   }


   public void setFatherStat(UMLCollabStat fatherStat)
   {
      if (this.fatherStat != fatherStat)
      {
         UMLCollabStat oldFatherStat = this.fatherStat;
         if (this.fatherStat != null)
         {
            this.fatherStat = null;
            oldFatherStat.removeFromSubStats(this);
         }
         this.fatherStat = fatherStat;
         firePropertyChange(FATHER_STAT_PROPERTY, oldFatherStat, fatherStat);
         if (fatherStat != null)
         {
            fatherStat.addToSubStats(this);
         }
         createNoText();
      }
   }


   public Iterator iteratorOfOrderedSubStats()
   {
      LinkedList<UMLCollabStat> stats = new LinkedList(this.subStats);
      Collections.sort(stats, new CollabStatLessThan());
      return stats.iterator();
   }
   
   /**
    * <pre>
    *                0..n    subStats    0..1
    * UMLCollabStat -------------------------- UMLCollabStat
    *                subStats      fatherStat
    * </pre>
    */
   @Property( name = SUB_STATS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = FATHER_STAT_PROPERTY, adornment = ReferenceHandler.Adornment.COMPOSITION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private transient FLinkedList subStats = new FLinkedList();


   public boolean hasInSubStats(UMLCollabStat elem)
   {
      return this.subStats.contains(elem);
   }


   public Iterator iteratorOfSubStats()
   {
      return this.subStats.iterator();
   }


   public int sizeOfSubStats()
   {
      return (this.subStats.size());
   }


   public UMLCollabStat getLastOfSubStats()
   {
      return ((this.subStats.isEmpty() == true) ? null
            : (UMLCollabStat) this.subStats.getLast());
   }


   public void addToSubStats(UMLCollabStat elem)
   {
      if (elem != null && !this.hasInSubStats(elem))
      {
         this.subStats.add(elem);
         elem.setFatherStat(this);
         firePropertyChange(CollectionChangeEvent.get(this, SUB_STATS_PROPERTY,
               this.subStats, null, elem, subStats.size() - 1,
               CollectionChangeEvent.ADDED));
      }
   }


   public void addToSubStats(int index, UMLCollabStat elem)
   {
      if (elem != null && !this.hasInSubStats(elem))
      {
         this.subStats.add(index, elem);
         elem.setFatherStat(this);
         firePropertyChange(CollectionChangeEvent.get(this, SUB_STATS_PROPERTY,
               this.subStats, null, elem, index,
               CollectionChangeEvent.ADDED));
      }
   }

   
   public void addFirstToSubStats(UMLCollabStat elem)
   {
      if (elem != null && !hasInSubStats(elem))
      {
         this.subStats.addFirst(elem);
         elem.setFatherStat(this);
         firePropertyChange(CollectionChangeEvent.get(this, SUB_STATS_PROPERTY,
               this.subStats, null, elem, 0, CollectionChangeEvent.ADDED));
      }
   }


   public int indexOfSubStats(UMLCollabStat elem)
   {
      return this.subStats.indexOf(elem);
   }


   public void removeFromSubStats(UMLCollabStat elem)
   {
      int index = this.subStats.indexOf(elem);
      if (index > -1)
      {
         this.subStats.remove(index);
         elem.setFatherStat(null);
         firePropertyChange(CollectionChangeEvent.get(this, SUB_STATS_PROPERTY,
               this.subStats, elem, null, index, CollectionChangeEvent.REMOVED));
      }
   }


   public void removeAllFromSubStats()
   {
      UMLCollabStat item;
      Iterator iter = iteratorOfSubStats();

      while (iter.hasNext())
      {
         item = (UMLCollabStat) iter.next();
         item.setFatherStat(null);
         firePropertyChange(CollectionChangeEvent.get(this, SUB_STATS_PROPERTY,
               this.subStats, item, null, 0, CollectionChangeEvent.REMOVED));
      }
   }


   public UMLCollabStat getPrevCollabStat()
   {
      UMLCollabStat prevStat = null;
      if (fatherStat != null && this != fatherStat.subStats.getFirst())
      {
         int position = fatherStat.subStats.indexOf(this);
         prevStat = (UMLCollabStat) fatherStat.subStats.get(position - 1);
      }
      return prevStat;
   }

   public UMLCollabStat getNextCollabStat()
   {
      UMLCollabStat nextStat = null;
      if (fatherStat != null && this != fatherStat.subStats.getLast())
      {
         int position = fatherStat.subStats.indexOf(this);
         nextStat = (UMLCollabStat) fatherStat.subStats.get(position + 1);
      }
      return nextStat;
   }


   public UMLCollabStat createSubStat(String path)
   {
      path = path.trim();
      Matcher matcher = PATH_PATTERN.matcher(path);
      if (matcher.matches())
      {
         int number = Integer.parseInt(matcher.group(1));
         String thread = matcher.group(2);
         if (thread != null && thread.length() == 0)
         {
            thread = null;
         }
         String subPath = matcher.group(3);
         if (subPath != null && subPath.length() > 0)
         {
            UMLCollabStat subStat = findSubStat(number, thread);
            if (subStat == null)
            {
               subStat = createSubStat(number, thread);
            }
            return subStat.createSubStat(subPath);
         }
         else
         {
            return createSubStat(number, thread);
         }
      }
      else
      {
         throw new IllegalArgumentException(
               "Malformed sequence path expression: " + path);
      }
   }


   protected UMLCollabStat createSubStat(int number, String thread)
   {
      UMLCollabStat newStat = getProject()
            .getFromFactories(UMLCollabStat.class).create(isPersistent());
      newStat.setNumber(number);
      if (thread != null)
      {
         newStat.setThreadId(thread);
      }
      this.addToSubStats(newStat);
      return newStat;
   }


   public UMLCollabStat findSubStat(String path)
   {
      if (sizeOfSubStats() > 0)
      {
         path = path.trim();
         Matcher matcher = PATH_PATTERN.matcher(path);
         if (matcher.matches())
         {
            int number = Integer.parseInt(matcher.group(1));
            String thread = matcher.group(2);
            if (thread != null && thread.length() == 0)
            {
               thread = null;
            }
            String subPath = matcher.group(3);
            UMLCollabStat subStat = findSubStat(number, thread);
            if (subPath != null && subPath.length() > 0 && subStat != null)
            {
               subStat = subStat.findSubStat(subPath);
            }
            return subStat;
         }
         else
         {
            throw new IllegalArgumentException(
                  "Malformed sequence path expression: " + path);
         }
      }
      return null;
   }


   public UMLCollabStat findSubStat(int number, String thread)
   {
      Iterator iter = iteratorOfSubStats();
      while (iter.hasNext())
      {
         UMLCollabStat collabStat = (UMLCollabStat) iter.next();
         if (collabStat.getNumber() == number
               && ((thread == null && collabStat.getThreadId().length() == 0) || (thread != null && thread
                     .equals(collabStat.getThreadId()))))
         {
            return collabStat;
         }
      }
      return null;
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      if (getMyPattern() != null)
      {
         return getMyPattern();
      }
      else
      {
         return getFatherStat();
      }
   }


   private transient String text;

   private transient boolean parsing = false;


   private void fireTextChange(String name, Object oldValue, Object newValue,
         String oldFullText)
   {
      if (!parsing)
      {
         this.text = null;
         firePropertyChange(name, oldValue, newValue);
         firePropertyChange("text", oldFullText, getText());
      }
   }


   public void setText(String text)
   {
      if (Versioning.get().isInOperationalization(this))
      {
         // Parsing is not necessary for loading since all
         // involved attributes are saved explicitly.
         // Since parsing is also extremely buggy, skip it here
         return;
      }
      // FIXME: Parsing cannot handle texts properly that contain [, ] or : in condition/loop/call
      // parts
      String oldtext = this.getText();
      if ((oldtext == null && text != null)
            || (oldtext != null && !oldtext.equals(text)))
      {
         parsing = true;
         try
         {

            String oldNoText = getNoText();
            String oldIfCondText = getIfCondText();
            String oldWhileLoopText = getWhileLoopText();
            String oldLoopVarName = getLoopVarName();
            FType oldAssignTgtType = getAssignTgtType();
            String oldAssignTgtText = getAssignTgtText();
            String oldCallText = getCallText();

            if (text.startsWith(":"))
            {
               text = "1" + text;
            }
            // this method tries to parse a collab stat and to derive its parts.
            // just a test. Parsing is not yet complete. Albert
            StringTokenizer tokens = new StringTokenizer(text, "[");
            String word = null;

            this.setIfCondText("");
            this.setWhileLoopText("");
            this.setLoopVarName("");
            this.setAssignTgtType(null);

            int i = tokens.countTokens();
            if (i > 1)
            { // yes a square brace exists

               if (tokens.hasMoreElements())
               {
                  word = tokens.nextToken();
                  this.setNoText(word.trim());
               }
               if (tokens.hasMoreElements())
               {
                  word = tokens.nextToken("]");

                  // skip "["
                  word = word.substring(1);

                  // is it a while loop?
                  if (word.startsWith("while "))
                  {
                     this.setWhileLoopText(word.substring(6).trim());
                     this.setStatType(TYPE_LOOP);
                  }
                  else
                  {
                     this.setIfCondText(word.trim());
                     this.setStatType(TYPE_COND);
                  }
               }

               // skip ":"
               if (tokens.hasMoreElements())
               {
                  tokens.nextToken(":");
               }
            }
            else
            {
               // no square brackets, up to ":" belongs to number
               if (tokens.hasMoreElements())
               {
                  word = tokens.nextToken(":");
                  this.setNoText(word.trim());
               }
            }
            if (tokens.hasMoreElements())
            {
               word = tokens.nextToken("=").substring(1);
               if (word.endsWith(":"))
               {
                  word = word.substring(0, word.length() - 1);
               }
            }
            i = tokens.countTokens();
            if (i > 0)
            {
               // an assignment "x = m1 ()" exists
               // get the assign target variable
               String assignTxt = word.trim(); // .substring (1);
               int index = assignTxt.indexOf(" ");
               if (index > 0)
               {
                  this.setAssignTgtType(getProject().getFromFactories(
                        FBaseType.class).getFromProducts(
                        assignTxt.substring(0, index)));
                  if (this.getAssignTgtType() != null)
                  {
                     assignTxt = assignTxt.substring(index + 1);
                  }
               }
               this.setAssignTgtText(assignTxt);

               // now look up the call text
               if (tokens.hasMoreElements())
               {
                  word = tokens.nextToken();
                  this.setCallText(word.trim());
               }
            }
            else
            {
               this.setAssignTgtText("");
               this.setCallText(word.substring(1).trim());
            }
            this.text = null;

            firePropertyChange("noText", oldNoText, getNoText());
            firePropertyChange("ifCondText", oldIfCondText, getIfCondText());
            firePropertyChange("whileLoopText", oldWhileLoopText,
                  getWhileLoopText());
            firePropertyChange("loopVarName", oldLoopVarName, getLoopVarName());
            firePropertyChange(ASSIGN_TGT_TYPE_PROPERTY, oldAssignTgtType,
                  getAssignTgtType());
            firePropertyChange("assignTgtText", oldAssignTgtText,
                  getAssignTgtText());
            firePropertyChange("callText", oldCallText, getCallText());

            String tmpNewText = this.getText();
            firePropertyChange("text", oldtext, tmpNewText);
         }
         catch (Exception e2)
         {
            e2.printStackTrace();
         }
         finally
         {
            parsing = false;
         }
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      if (this.text == null)
      {
         StringBuffer buffer = new StringBuffer();
         String tmpString;

         buffer.append(getNoText());
         buffer.append(getThreadId());

         tmpString = getIfCondText();
         if ((tmpString != null) && (tmpString.length() != 0))
         {
            buffer.append(" [");
            buffer.append(tmpString);
            buffer.append("]");
         }

         tmpString = getWhileLoopText();
         if ((tmpString != null) && (tmpString.length() != 0))
         {
            buffer.append(" [while ");
            buffer.append(tmpString);
            buffer.append("]");
         }

         tmpString = getLoopVarName();
         // take loopVarName as an indicator for the existance of a loop statement
         if ((tmpString != null) && (tmpString.length() != 0))
         {
            buffer.append(" [");
            buffer.append(tmpString);
            buffer.append(":=");
            buffer.append(getLoopStartVal());
            buffer.append("..");
            buffer.append(getLoopStopVal());
            buffer.append("]");
         }

         buffer.append(": ");

         tmpString = getAssignTgtText();
         if ((tmpString != null) && (tmpString.length() != 0))
         {
            FType tmpType = getAssignTgtType();
            if (tmpType != null)
            {
               buffer.append(tmpType.getName());
               buffer.append(" ");
            }
            buffer.append(tmpString);
            buffer.append(" := ");
         }
         buffer.append(getCallText());

         this.text = buffer.toString();
      }

      return this.text;
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getText();
   }


   public UMLStoryPattern findStoryPattern()
   {
      UMLCollabStat fatherStat = this;
      UMLCollabStat lastFatherStat = this;

      do
      {
         lastFatherStat = fatherStat;
         fatherStat = fatherStat.getFatherStat();
      }
      while (fatherStat != null);

      return lastFatherStat.getMyPattern();
   }


   public UMLCollabStat getRootFatherStat()
   {
      if (this.fatherStat == null)
      {
         return this;
      }
      else
      {
         return fatherStat.getRootFatherStat();
      }
   }


   @Property( name = "method", kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FMethod method;


   public void setMethod(FMethod value)
   {
      if (this.method != value)
      {
         FMethod oldValue = this.method;
         this.method = value;
         firePropertyChange("method", oldValue, value);
      }
   }


   public FMethod getMethod()
   {
      return this.method;
   }

   
   private boolean expanded = false;


   public void setExpanded(boolean value)
   {
      expanded = value;
   }


   public boolean isExpanded()
   {
      return expanded;
   }


   private int statType = TYPE_CALL;


   public void setStatType(int value)
   {
      if (this.statType != value)
      {
         int oldValue = this.statType;
         this.statType = value;
         firePropertyChange("statType", oldValue, value);
      }
   }


   public int getStatType()
   {
      return statType;
   }


   public void autoNumber()
   {
      autoNumber(1);
   }


   private void autoNumber(int number)
   {
      int kidNo = 0;
      setNumber(number);
      Iterator iter = this.iteratorOfSubStats();
      while (iter.hasNext())
      {
         UMLCollabStat kidStat = (UMLCollabStat) iter.next();
         kidNo++;
         kidStat.autoNumber(kidNo);
      }
   }


   public int renumberCollabStats()
   {
      LinkedList<UMLCollabStat> stats = new LinkedList(this.subStats);

      Collections.sort(stats, new CollabStatLessThan());

      //if (!stats.equals(this.subStats)) {
      //removeAllFromSubStats();
      int lastNumber = 0;
      int oldNumberOfPrevious = -1;
      String threadOfPrevious = null;
      int currentPos = 0;

      for (UMLCollabStat aCollabStat : stats)
      {
         String thread = aCollabStat.getThreadId();
         if (thread != null && thread.length() > 0)
         {
            int number = aCollabStat.getNumber();
            if (threadOfPrevious == null || oldNumberOfPrevious != number)
            {
               threadOfPrevious = thread;
               oldNumberOfPrevious = number;
               aCollabStat.setNumber(++lastNumber);
            }
            else
            {
               threadOfPrevious = thread;
               aCollabStat.setNumber(lastNumber);
            }
         }
         else
         {
            threadOfPrevious = null;
            aCollabStat.setNumber(++lastNumber);
         }
         int oldPos = this.subStats.indexOf(aCollabStat);
         if (currentPos != oldPos)
         {
            removeFromSubStats(aCollabStat);
            addToSubStats(currentPos, aCollabStat);
            aCollabStat.renumberCollabStats();
         }
         currentPos++;
      }
      return currentPos;
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      setCallSource(null);
      setCallTarget(null);
      setMyPattern(null);
      setFatherStat(null);
      setMethod(null);
      removeAllFromSubStats();

      super.removeYou();
   }


   private static class CollabStatLessThan implements Comparator<UMLCollabStat>
   {

      public CollabStatLessThan()
      {
      }


      /**
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      public int compare(UMLCollabStat stat1, UMLCollabStat stat2)
      {
         if (stat1 == stat2)
         {
            return 0;
         }
         else if (stat1 == null)
         {
            return Integer.MIN_VALUE;
         }
         else if (stat2 == null)
         {
            return Integer.MAX_VALUE;
         }

         int depth1 = stat1.getNoDepth();
         int depth2 = stat2.getNoDepth();
         UMLCollabStat ancestor1 = stat1;
         UMLCollabStat ancestor2 = stat2;
         for (int i = 0; i < depth1 - depth2; i++)
         {
            ancestor1 = ancestor1.getFatherStat();
         }
         for (int i = 0; i < depth2 - depth1; i++)
         {
            ancestor2 = ancestor2.getFatherStat();
         }

         if (ancestor1 == ancestor2)
         {
            return depth1 - depth2;// one is substat of other
         }

         // seek common father stat
         UMLCollabStat last1 = ancestor1;
         UMLCollabStat last2 = ancestor2;
         while (ancestor1 != ancestor2)
         {
            last1 = ancestor1;
            last2 = ancestor2;
            ancestor1 = ancestor1.getFatherStat();
            ancestor2 = ancestor2.getFatherStat();
         }

         int result = last1.getNumber() - last2.getNumber();// compare by number
         if (result == 0)// now compare by thread
         {
            result = compareStr(last1.getThreadId(), last2.getThreadId());
            if (result == 0)// fallback
            {
               if (ancestor1 != null)
               {
                  int pos1 = ancestor1.indexOfSubStats(last1);
                  int pos2 = ancestor1.indexOfSubStats(last2);
                  result = pos1 - pos2;
               }
               else
               // ultimate fallback ;P
               {
                  result = compareStr(stat1.getText(), stat2.getText());
               }
            }
         }
         return result;
      } // compare


      private final int compareStr(final String str1, final String str2)
      {
         if (str1 == str2)
         {
            return 0;
         }
         else if (str1 == null)
         {
            return Integer.MIN_VALUE;
         }
         else if (str2 == null)
         {
            return Integer.MAX_VALUE;
         }
         return str1.compareTo(str2);
      }
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "callText";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getCallText();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setCallText(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'callText' field for the type UMLCollabStat.
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
   
}

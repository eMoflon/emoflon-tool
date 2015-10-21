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
import de.fujaba.text.TextNode;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStopActivity extends UMLActivity implements FParsedElement
{
   /**
    * Constructor for class UMLStopActivity
    *
    * @param project
    * @param persistent
    */
   protected UMLStopActivity (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Sets the exit attribute of the UMLStopActivity object
    *
    * @param elem  The new exit value
    */
   public void setExit (UMLTransition elem)
   {
      throw new RuntimeExceptionWithContext ("A stop activity must not have any exit transitions", this);
   }


   /**
    * Get the name attribute of the UMLStopActivity object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return "stop";
   }


   /*
    *  flag if this activity should produce source code or not
    *  in some cases a stop activity is needed in the diagram, but not in the source code
    */
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean generateCode;


   /**
    * Get the generateCode attribute of the UMLStopActivity object
    *
    * @return   The generateCode value
    */
   public boolean isGenerateCode()
   {
      return generateCode;
   }


   /**
    * Sets the generateCode attribute of the UMLStopActivity object
    *
    * @param generateCode  The new generateCode value
    */
   public void setGenerateCode (boolean generateCode)
   {
      if (this.generateCode != generateCode)
      {
         boolean oldValue = this.generateCode;
         this.generateCode = generateCode;
         firePropertyChange ("generateCode", oldValue, generateCode);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String returnValue = null;


   /**
    * Get the returnValue attribute of the UMLStopActivity object
    *
    * @return   The returnValue value
    */
   public String getReturnValue()
   {
      return this.returnValue;
   }


   /**
    * Sets the returnValue attribute of the UMLStopActivity object
    *
    * @param value  The new returnValue value
    */
   public void setReturnValue (String value)
   {
      if ( (this.returnValue == null && value != null) ||
          (this.returnValue != null && !this.returnValue.equals (value)))
      {
         String oldValue = this.returnValue;
         this.returnValue = value;
         firePropertyChange ("returnValue", oldValue, value);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean returnsVariable()
   {
      boolean returnsVariable = true;
      UMLDiagram diag = getFirstFromDiagrams();
      if (diag != null && diag instanceof UMLActivityDiagram)
      {
         UMLStartActivity startActivity =
             ((UMLActivityDiagram) diag).getStartActivity();
         if (startActivity != null)
         {
            FMethod methodDecl = startActivity.getSpec();
            if (methodDecl == null)
            { // maybe its start of statechart

               returnsVariable = false;
            }
            else
            {
               if ( (methodDecl.getResultType() != null) &&
                   ( (methodDecl.getResultType().getName().equals (FBaseType.VOID)) ||
                   (methodDecl.getResultType().getName().equals (FBaseType.CONSTRUCTOR))))
               {
                  returnsVariable = false;
               }
            }
         }
      }
      else
      {
         System.out.println ("Error in UMLStopActivity: getFirstFromDiagrams() does not return an UMLActivityDiagram");
         returnsVariable = false;
      }
      return returnsVariable;
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      super.removeYou();
   }


   /**
    * Get the text attribute of the UMLStopActivity object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      return "STOP";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "returnValue";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getReturnValue();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setReturnValue(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'returnValue' field for the type UMLStopActivity.
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

}

/*
 * $Log$
 * Revision 1.10  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.9  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.8  2006/03/08 14:57:44  lowende
 * Removed FClass.elementsOfRevSubclasses and FClass.elementsOfRevSuperclasses - use iteratorOf... instead.
 * Moved constants from UMLType to FType.
 * Added FClass.findClass and FClassUtility.findClass.
 * Added some tests and a test suite.
 *
 */

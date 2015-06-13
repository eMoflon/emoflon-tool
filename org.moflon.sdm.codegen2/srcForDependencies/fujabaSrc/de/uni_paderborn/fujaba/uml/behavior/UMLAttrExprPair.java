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
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.behavior.FInstanceElement;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.uni_paderborn.fujaba.versioning.Versioning;


/**
 * <h2>Associations</h2> <pre>
 *                  0..n    instanceOf    0..1
 * UMLAttrExprPair ---------------------------- UMLAttr
 *                  instances       instanceOf
 * <p/>
 *                  0..n    attrs    0..1
 * UMLAttrExprPair ----------------------- UMLObject
 *                  attrs        revAttrs
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLAttrExprPair extends UMLDiagramItem implements FInstanceElement, FParsedElement
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int NONE = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int PRE = NONE + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int POST = PRE + 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int EQUAL = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int NOTEQUAL = EQUAL + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int LESS = NOTEQUAL + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GREATER = LESS + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int LESSEQUAL = GREATER + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GREATEREQUAL = LESSEQUAL + 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REG_EXPRESSION = GREATEREQUAL + 1;
   public static final String REV_ATTRS_PROPERTY = "revAttrs";
   public static final String INSTANCE_OF_PROPERTY = "instanceOf";


   /**
    * Constructor for class UMLAttrExprPair
    *
    * @param project
    * @param persistent
    */
   protected UMLAttrExprPair (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name = "";


   /**
    * Get the name attribute of the UMLAttrExprPair object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * Set the value of name.
    *
    * @param name  Value to assign to name.
    */
   @Override
   public void setName (String name)
   {
      if ( (this.name == null && name != null) ||
          (this.name != null && !this.name.equals (name)))
      {
         String oldName = this.name;

         this.name = name;

         firePropertyChange ("name", oldName, this.name);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int qualifier = NONE;


   /**
    * Get the qualifier attribute of the UMLAttrExprPair object
    *
    * @return   The qualifier value
    */
   public int getQualifier()
   {
      return qualifier;
   }


   /**
    * Sets the qualifier attribute of the UMLAttrExprPair object
    *
    * @param qualifier  The new qualifier value
    */
   public void setQualifier (int qualifier)
   {
      if (this.qualifier != qualifier)
      {
         int oldQualifier = this.qualifier;
         String oldAttrOperationAsText = getAttrOperationAsText();

         this.qualifier = qualifier;

         firePropertyChange ("qualifier", oldQualifier, this.qualifier);

         firePropertyChange ("attrOperationAsText", oldAttrOperationAsText, getAttrOperationAsText());
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int operation = EQUAL;


   /**
    * Get the operation attribute of the UMLAttrExprPair object
    *
    * @return   The operation value
    */
   public int getOperation()
   {
      return operation;
   }


   /**
    * Sets the operation attribute of the UMLAttrExprPair object
    *
    * @param operation  The new operation value
    */
   public void setOperation (int operation)
   {
      if (this.operation != operation)
      {
         int oldOperation = this.operation;
         String oldAttrOperationAsText = getAttrOperationAsText();

         this.operation = operation;

         firePropertyChange ("operation", oldOperation, this.operation);

         firePropertyChange ("attrOperationAsText", oldAttrOperationAsText, getAttrOperationAsText());
      }
   }


   /**
    * Get the operationText attribute of the UMLAttrExprPair object
    *
    * @return   The operationText value
    */
   public String getOperationText()
   {
      switch (getOperation())
      {
         case EQUAL:
            return  ("=");
         case NOTEQUAL:
            return  ("!=");
         case LESS:
            return  ("<");
         case GREATER:
            return  (">");
         case LESSEQUAL:
            return  ("<=");
         case GREATEREQUAL:
            return  (">=");
         case REG_EXPRESSION:
            return  ("== RegExp:");
         default:
            // should not happen, otherwise model is incorrect
            return  ("");
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String expression = "";


   /**
    * Get the expression attribute of the UMLAttrExprPair object
    *
    * @return   The expression value
    */
   public String getExpression()
   {
      return expression;
   }


   /**
    * Set the value of expression.
    *
    * @param expression  Value to assign to expression.
    */
   public void setExpression (String expression)
   {
      if ( (this.expression == null && expression != null) ||
          (this.expression != null && !this.expression.equals (expression)))
      {
         String oldExpression = this.expression;
         this.expression = expression;
         firePropertyChange ("expression", oldExpression, this.expression);
      }
   }

   private boolean reflective;

   
   public boolean isReflective ()
   {
      return reflective;
   }


   public void setReflective ( boolean reflective )
   {
      if (reflective != this.reflective)
      {
         this.reflective = reflective;
         firePropertyChange("reflective", !reflective, reflective);
      }
   }


   /**
    * <pre>
    *                  0..n    instanceOf    0..1
    * UMLAttrExprPair ---------------------------- UMLAttr
    *                  instances       instanceOf
    * </pre>
    */
   @Property( name = INSTANCE_OF_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = FAttr.INSTANCES_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FAttr instanceOf;


   /**
    * Get the instanceOf attribute of the UMLAttrExprPair object
    *
    * @return   The instanceOf value
    */
   public FAttr getInstanceOf()
   {
      return instanceOf;
   }


   /**
    * Sets the instanceOf attribute of the UMLAttrExprPair object
    *
    * @param instanceOf  The new instanceOf value
    */
   public void setInstanceOf (FAttr instanceOf)
   {
      if (this.instanceOf != instanceOf)
      {
         // new partner
         FAttr oldInstanceOf = this.instanceOf;
         if (this.instanceOf != null)
         {
            // inform old partner
            this.instanceOf = null;
            oldInstanceOf.removeFromInstances (this);
         }
         this.instanceOf = instanceOf;
         firePropertyChange (INSTANCE_OF_PROPERTY, oldInstanceOf, instanceOf);
         if (instanceOf != null)
         {
            // inform new partner
            instanceOf.addToInstances (this);
         }
      }
   }


   /**
    * <pre>
    *                  0..n    attrs    0..1
    * UMLAttrExprPair ----------------------- UMLObject
    *                  attrs        revAttrs
    * </pre>
    */
   @Property( name = REV_ATTRS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.ATTRS_PROPERTY, adornment = ReferenceHandler.Adornment.PARENT,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject revAttrs;


   /**
    * Get the revAttrs attribute of the UMLAttrExprPair object
    *
    * @return   The revAttrs value
    */
   public UMLObject getRevAttrs()
   {
      return revAttrs;
   }


   /**
    * Sets the revAttrs attribute of the UMLAttrExprPair object
    *
    * @param revAttrs  The new revAttrs value
    */
   public void setRevAttrs (UMLObject revAttrs)
   {
      if (this.revAttrs != revAttrs)
      { // new partner

         UMLObject oldRevAttrs = this.revAttrs;
         if (this.revAttrs != null)
         { // inform old partner

            this.revAttrs = null;
            oldRevAttrs.removeFromAttrs (this);
         }
         this.revAttrs = revAttrs;
         firePropertyChange (REV_ATTRS_PROPERTY, oldRevAttrs, revAttrs);
         if (revAttrs != null)
         { // inform new partner

            revAttrs.addToAttrs (this);
         }
      }
   }


   /**
    * Get the text attribute of the UMLAttrExprPair object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      StringBuffer newText = new StringBuffer();

      newText.append (getName()).append(" ");

      switch (getQualifier())
      {
         case PRE:
            if (getOperation() == EQUAL)
            {
               newText.append ("=");
            }
            break;
         case POST:
            newText.append (":");
            break;
         default:
            newText.append (" ");
            break;
      }
      newText.append (getOperationText()).append (" ").append (getExpression());

      return newText.toString();
   }


   /**
    * Get the attrOperationAsText attribute of the UMLAttrExprPair object
    *
    * @return   The attrOperationAsText value
    */
   public String getAttrOperationAsText()
   {
      StringBuffer newText = new StringBuffer();

      switch (getQualifier())
      {
         case PRE:
            if (getOperation() == EQUAL)
            {
               newText.append ("=");
            }
            break;
         case POST:
            if (getOperation() == EQUAL)
            {
               newText.append (":");
            }
            break;
         default:
            break;
      }
      newText.append (getOperationText());

      return newText.toString();
   }


   /**
    * Sets the attrOperationAsText attribute of the UMLAttrExprPair object
    *
    * @param newText  The new attrOperationAsText value
    */
   public void setAttrOperationAsText (String newText)
   {
      if (newText.equals (":="))
      {
         setOperation (EQUAL);
         setQualifier (POST);
      }
      else if (newText.equals ("=="))
      {
         setOperation (EQUAL);
         setQualifier (PRE);
      }
      else if (newText.equals ("!="))
      {
         setOperation (NOTEQUAL);
         setQualifier (NONE);
      }
      else if (newText.equals ("<"))
      {
         setOperation (LESS);
         setQualifier (NONE);
      }
      else if (newText.equals (">"))
      {
         setOperation (GREATER);
         setQualifier (NONE);
      }
      else if (newText.equals ("<="))
      {
         setOperation (LESSEQUAL);
         setQualifier (NONE);
      }
      else if (newText.equals (">="))
      {
         setOperation (GREATEREQUAL);
         setQualifier (NONE);
      }
      else if (newText.equals ("== RegExp:"))
      {
         setOperation (REG_EXPRESSION);
         setQualifier (NONE);
      }
      else
      {
         { // this can happen while undoing some changes while the qualifier is not set
            if (!Versioning.get().isInOperationalization (this))
            {
               throw new RuntimeExceptionWithContext ("newText \"" + newText + "\" should not happen !", this);
            }
         }
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String[] allAttrOperationsAsText = null;


   /**
    * Get the allAttrOperationsAsText attribute of the UMLAttrExprPair class
    *
    * @return   The allAttrOperationsAsText value
    */
   public static String[] getAllAttrOperationsAsText()
   {
      if (allAttrOperationsAsText == null)
      {
         allAttrOperationsAsText = new String[]
            {
            ":=",
            "==",
            "!=",
            "<",
            ">",
            "<=",
            ">=",
            "== RegExp:"
            };
      }

      return allAttrOperationsAsText;
   } // getAllAttrOperationsAsText


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      setRevAttrs (null);
      setInstanceOf (null);

      super.removeYou();
   }

   @Override
   public FCodeStyle getInheritedCodeStyle ()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FElement instanceOf = getInstanceOf();
      if (instanceOf != null)
      {
         return instanceOf.getInheritedCodeStyle();
      }
      return null;
   }

   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public UMLObject getParentElement()
   {
      return getRevAttrs();
   }


   /**
    * @return   short string representation of current object
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("UMLAttrExprPair[name=");
      result.append (getName());
      result.append (",operation=");
      result.append (getOperationText());
      result.append (",expression=");
      result.append (getExpression());
      result.append (",instanceOf=");
      result.append (getInstanceOf());
      result.append ("]");

      return result.toString();
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return "expression";
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getExpression();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setExpression(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'expression' field for the type UMLAttrExprPair.
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
 * Revision 1.9  2007/03/21 12:47:47  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.8  2006/11/10 15:58:56  rotschke
 * Improved expression parser [tr].
 *
 * Revision 1.7  2006/10/24 12:42:08  rotschke
 * Added some luxury to assertions:
 *
 * - The value is entered through a combobox rather than a mere textfield.
 * - The combobox offers suitable attribute values of all objects in the current story pattern.
 * - Additionally, available literals can be selected for boolean- and enumeration-typed attributes.
 * - UMLAttrExpr has a new derived attribute "javaExpression", which adds access methods or enumeration names to allow for compacter rules.
 * - Thus, rather than "a == b.getC() + 1", you may write "a == b.c + 1", or "direction == OUT" instead of "direction == DirectionKindEnum.OUT", though there are limitations (no full-featured expression parser).
 * - The javaExpression is shown in the status field of the dialog.
 * - Arbitrary expressions may still be entered.
 * [tr].
 *
 * Revision 1.6  2006/05/03 13:01:51  lowende
 * UMLObjects enhanced by construction expressions.
 * These expressions will be used for creating objects in generated code.
 * Can also be used to create objects with factories (e.g. Fujaba Metamodel objects).
 *
 * Revision 1.5  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.4  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.3  2006/03/01 12:22:47  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

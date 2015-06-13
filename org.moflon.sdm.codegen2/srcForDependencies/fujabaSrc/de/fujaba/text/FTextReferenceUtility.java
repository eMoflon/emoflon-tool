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
package de.fujaba.text;

import java.util.Iterator;
import java.util.Set;

import de.upb.tools.fca.FHashSet;

/**
 * Implementation of the 'referencedElement' association between FTextReference and
 * UMLTextNode. Since this association must be implemented by numerous classes, all
 * operations are forwarded to this class.
 * 
 * <pre>
 *           0..1     referencedElement     0..n
 * FTextReference ------------------------- UMLTextNode
 *      referencedElement               textUsages
 * </pre>
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class FTextReferenceUtility
{

   /**
    * The FTextReference instance which this FTextReferenceUtility instance serves.
    */
   private final FTextReference reference;

   /**
    * The set of UMLTextNode text usages.
    */
   private Set<TextNode> textUsages;

   /**
    * @param reference
    *           Mandatory parameter; the FTextReference instance that this instance is
    *           destined to serve.
    */
   public FTextReferenceUtility(final FTextReference reference)
   {
      if(reference == null)
      {
         final String message = "FTextReferenceUtility needs an FTextReference instance as parameter."; //$NON-NLS-1$
         throw new IllegalArgumentException(message);
      }
      this.reference = reference;
   }

   /**
    * Add the given UMLTextNode instance to the given FTextReference instance's set of
    * text usages.
    * 
    * @param value
    *           the UMLTextNode instance to be added
    * 
    * @return true if the given FTextReference's set was changed.
    */
   public boolean addToTextUsages(final TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(this.textUsages == null)
         {
            this.textUsages = new FHashSet<TextNode>();
         }

         changed = this.textUsages.add(value);
         if(changed)
         {
            value.setReferencedElement(this.reference);
         }
      }
      return changed;
   }

   /**
    * Determines if the given FTextReference instance has the given UMLTextNode in its set
    * of text usages.
    * 
    * @param value
    *           the UMLTextNode instance to be checked for existence in the set
    * 
    * @return true if the given FTextReference contains the given UMLTextNode
    */
   public boolean hasInTextUsages(final TextNode value)
   {
      return((this.textUsages != null) && (value != null) && this.textUsages.contains(value));
   }

   /**
    * @return an Iterator of this instance's set of text usages.
    */
   public Iterator<TextNode> iteratorOfTextUsages()
   {
      return((this.textUsages == null) ? new FHashSet<TextNode>().iterator() : this.textUsages.iterator());
   }

   /**
    * Remove all UMLTextNode instances from the given FTextReference instance's set of
    * text usages.
    */
   public void removeAllFromTextUsages()
   {
      TextNode tmpValue;
      final Iterator iter = this.iteratorOfTextUsages();

      while(iter.hasNext())
      {
         tmpValue = (TextNode) iter.next();
         this.removeFromTextUsages(tmpValue);
      }
   }

   /**
    * Remove the given UMLTextNode instance from the given FTextReference instance's set
    * of text usages.
    * 
    * @param value
    *           the UMLTextNode instance to be removed
    * 
    * @return true if the given FTextReference's set was changed.
    */
   public boolean removeFromTextUsages(final TextNode value)
   {
      boolean changed = false;

      if((this.textUsages != null) && (value != null))
      {
         changed = this.textUsages.remove(value);
         if(changed)
         {
            value.setReferencedElement(null);
         }
      }
      return changed;
   }

   /**
    * @return the number of UMLTextNode instances in the given FTextReference instance's
    *         set of text usages.
    */
   public int sizeOfTextUsages()
   {
      return((this.textUsages == null) ? 0 : this.textUsages.size());
   }

}

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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FTag;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashMap;
import de.upb.tools.sdm.JavaSDM;


public class UMLTag extends UMLIncrement implements FTag
{

   protected UMLTag(FProject project, boolean persistent)
   {
      super(project, persistent);
   }

   public static final String PROPERTY_NAME = "name";

   @Property( name = PROPERTY_NAME, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   private String name;

   @Override
   @Property( name = PROPERTY_NAME )
   public void setName (String value)
   {
      if ( ! JavaSDM.stringEquals (this.name, value))
      {
         String oldValue = this.name;
         this.name = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_NAME, oldValue, value);
      }
   }

   @Override
   @Property( name = PROPERTY_NAME )
   public String getName ()
   {
      return this.name;
   }

   /**
    * <pre>
    *           0..*     tags     0..1
    * FTag ------------------------- FIncrement
    *           tags               revTags
    * </pre>
    */
   public static final String PROPERTY_REV_TAGS = "revTags";

   @Property( name = PROPERTY_REV_TAGS, partner = FIncrement.PROPERTY_TAGS, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   private FIncrement revTags;

   @Property( name = PROPERTY_REV_TAGS )
   public boolean setRevTags (FIncrement value)
   {
      boolean changed = false;

      if (this.revTags != value)
      {
      
         FIncrement oldValue = this.revTags;
         UMLTag source = this;
         if (this.revTags != null)
         {
            this.revTags = null;
            oldValue.removeFromTags (this);
         }
         this.revTags = value;

         if (value != null)
         {
            value.addToTags (this);
         }
            getPropertyChangeSupport().firePropertyChange(PROPERTY_REV_TAGS, oldValue, value);
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_REV_TAGS )
   public FIncrement getRevTags ()
   {
      return this.revTags;
   }

   /**
    * <pre>
    *           0..1     values     0..1
    * FTag ------------------------> String
    *           fTag               values
    * </pre>
    */
   public static final String PROPERTY_VALUES = "values";

   @Property( name = PROPERTY_VALUES, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private FPropHashMap values;

   @Property( name = PROPERTY_VALUES )
   public boolean removeFromValues (String value)
   {
      boolean changed = false;

      if (this.values != null)
      {
         Iterator iter = this.entriesOfValues ();
         Map.Entry entry;
         while (iter.hasNext ())
         {
            entry = (Map.Entry) iter.next ();
            if (entry.getValue () == value)
            {
            
               if (this.removeFromValues ((String) entry.getKey (), value))
               {
                  changed = true;
               }
            
            }
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_VALUES )
   public void removeAllFromValues ()
   {
      if (this.values != null && this.values.size () > 0)
      {
      
         try
         {
            this.values.clear();
         } catch (Exception e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      
      }
   }

   @Property( name = PROPERTY_VALUES )
   public boolean hasInValues (String value)
   {
      return ((this.values != null) &&
              this.values.containsValue (value));
   }

   @Property( name = PROPERTY_VALUES )
   public Iterator iteratorOfValues ()
   {
      return ((this.values == null)
              ? FEmptyIterator.get ()
              : this.values.values ().iterator ());
   }

   @Property( name = PROPERTY_VALUES )
   public int sizeOfValues ()
   {
      return ((this.values == null)
              ? 0
              : this.values.size ());
   }

   @Property( name = PROPERTY_VALUES )
   public boolean hasInValues (String key, String value)
   {
      return ((this.values != null) &&
              (value != null || this.values.containsKey (key)) && 
              (this.values.get (key) == value));
   }

   @Property( name = PROPERTY_VALUES )
   public boolean hasKeyInValues (String key)
   {
      return ((this.values != null) &&
              this.values.containsKey (key));
   }

   @Property( name = PROPERTY_VALUES )
   public Iterator keysOfValues ()
   {
      return ((this.values == null)
              ? FEmptyIterator.get ()
              : this.values.keySet ().iterator ());
   }

   @Property( name = PROPERTY_VALUES )
   public Iterator entriesOfValues ()
   {
      return ((this.values == null)
              ? FEmptyIterator.get ()
              : this.values.entrySet ().iterator ());
   }

   @Property( name = PROPERTY_VALUES )
   public boolean addToValues (String key, String value)
   {
      boolean changed = false;

      if (this.values == null)
      {
         this.values = new FPropHashMap (this, PROPERTY_VALUES);
      }
   
      String oldValue = (String) this.values.put (key, value);
      if (oldValue != value)
      {
         changed = true;
      }
   
      return changed;
   }

   @Property( name = PROPERTY_VALUES )
   public boolean addToValues (Map.Entry entry)
   {
      return addToValues ((String) entry.getKey (), (String) entry.getValue ());
   }

   @Property( name = PROPERTY_VALUES )
   public boolean removeFromValues (String key, String value)
   {
      boolean changed = false;

      if (this.values != null)
      {
         String oldValue = (String) this.values.get (key);
         if (oldValue == value && 
             (oldValue != null || this.values.containsKey (key)))
         {
         
            this.values.remove (key);
            changed = true;
         
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_VALUES )
   public boolean removeKeyFromValues (String key)
   {
      boolean changed = false;

      if (this.values != null)
      {
         changed = this.values.containsKey (key);
         if (changed)
         {
         
            String tmpValue = (String) this.values.remove (key);
         
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_VALUES )
   public String getFromValues (String key)
   {
      return ((this.values == null)
              ? null
              : (String) this.values.get (key));
   }

   @Override
   @Property(name="text", derived=true)
   public String getText()
   {
      StringBuilder builder = new StringBuilder();

      builder.append("@");
      builder.append(name);
      Iterator iter = keysOfValues();
      if (iter.hasNext())
      {
         builder.append("(");
         while (iter.hasNext())
         {
            String key = (String) iter.next();
            builder.append(key);
            builder.append("=\"");
            builder.append(getFromValues(key));
            builder.append("\"");
            if (iter.hasNext())
            {
               builder.append(",");
            }
         }
         builder.append(")");         
      }
      return builder.toString();
   }

   @Property(name="text", derived=true)
   public void setText (String text)
   {
      String oldText = getText();
      if (!text.startsWith("@")) throw new RuntimeException ("Text does not start with '@' char.");
      text = text.substring(1).trim();
      int index = text.indexOf("(");
      if (index == -1)
      {
         setName (text);
         removeAllFromValues();
      }
      else
      {
         if (!text.endsWith(")")) throw new RuntimeException ("Text does not end with ')' char.");
         setName (text.substring(0,index));
         removeAllFromValues();
         text = text.substring(index + 1, text.length() - 1).trim();
         String keyValue = "([^=,]+)(?:=[\\s]*\"([^\"]*)\")?";
         Pattern p1 = Pattern.compile (keyValue);
         Matcher m = p1.matcher(text);
         int start = 0;
         int length = text.length();
         while (start < length && m.find(start))
         {
            String key = m.group(1).trim();
            String value = m.group(2);
            if (value == null) value = "";
            int newStart = m.start();
            if (start > 0 && (start == newStart || !",".equals(text.substring(start, newStart).trim())))
            {
               throw new RuntimeException ("Tag can not be parsed.");
            }
            addToValues(key, value);
            start = m.end();
         }
         if (start != length)
         {
            throw new RuntimeException ("Tag can not be parsed completely.");
         }
      }
      text = getText();
      if (!oldText.equals(text))
      {
         firePropertyChange("text", oldText, text);
      }
   }

   @Property(name="displayParent", derived=true)
   public FElement getDisplayParent()
   {
      if ((revTags instanceof FAttr) || (revTags instanceof FMethod))
      {
         return revTags.getParentElement();
      }
      return revTags;
   }

   @Property(name="displayParent", derived=true)
   public void setDisplayParent(FElement elem)
   {
      throw new UnsupportedOperationException("Nope");
   }

   @Override
   public FElement getParentElement()
   {
      return getRevTags();
   }

   @Override
   public String toString()
   {
      return getText();
   } // toString

   @Override
   public void removeYou()
   {
      super.removeYou();
      this.setRevTags (null);
      this.removeAllFromValues ();
   }
}
///////////////////////////////////////////////////////////////////////////
//                                                                       //
// The FUJABA environment:                                               //
//                                                                       //
//   FUJABA (From Uml to Java And Back Again) aims to provide an         //
//   environment for round-trip engineering with                         //
//   Design Patterns, UML, Story Driven Modeling, and Java.              //
//                                                                       //
//      Copyright (C) 1998 Fujaba Development Group                      //
//                                                                       //
//   This program is free software; you can redistribute it and/or       //
//   modify it under the terms of the GNU Library General Public         //
//   License as published by the Free Software Foundation; either        //
//   version 2 of the License, or (at your option) any later version.    //
//                                                                       //
//   You should have received a copy of the GNU Library General Public   //
//   License along with this library; if not, write to the Free          //
//   Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  //
//                                                                       //
// WARRANTY:                                                             //
//                                                                       //
//   This library is distributed in the hope that it will be useful,     //
//   but WITHOUT ANY WARRANTY; without even the implied warranty of      //
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    //
//   Library General Public License for more details.                    //
//                                                                       //
// Contact Adresses:                                                     //
//                                                                       //
//   Albert Zuendorf                                                     //
//   AG Softwaretechnik                                                  //
//   Universitaet Paderborn                                              //
//   Warburgerstr. 100                                                   //
//   D-33098 Paderborn                                                   //
//   Germany                                                             //
//                                                                       //
// Email to:                                                             //
//                                                                       //
//   fujaba@uni-paderborn.de                                             //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

// Title       : Fujaba source file
// Copyright   : Copyright (c) 1998-2000
// Author      : Student research group Fujaba
// Organisation: University of Paderborn
// Description : Template for code generation of assocs

// $Id$

///////////////////////////////////////////////////////////////////////////
//
// This template file contains implementation code for the access methods
// of association. See Assocs.html for further information.
//
///////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////
//
// public boolean setPartner ($VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////
// v1:  One
#BeginCodeBlock = assoc-set-v1
boolean changed = false;

if (this.$FIELDNAME$ != value)
{
   $CHANGE_PREHOOK$
   this.$FIELDNAME$ = value;
   changed = true;
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock

#BeginCodeBlock = preHook-propertyChange
$VALUETYPE$ oldValue = this.$FIELDNAME$;
#EndCodeBlock

#BeginCodeBlock = postHook-propertyChange
firePropertyChange ( "$FIELDNAME$" , oldValue , value) ;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public $VALUETYPE$ getPartner ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One
#BeginCodeBlock = assoc-get-v1
return this.$FIELDNAME$;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean hasInPartners ($VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many
#BeginCodeBlock = assoc-hasIn-v1
return ((this.$FIELDNAME$ != null) &&
        (value != null) &&
        this.$FIELDNAME$.contains (value));
#EndCodeBlock


// v1A:  One-Quali-Ext, Many-Qual-Ext
#BeginCodeBlock = assoc-hasIn-v1A
return ((this.$FIELDNAME$ != null) &&
        this.$FIELDNAME$.containsValue (value));
#EndCodeBlock


// v2: One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-hasIn-v2
return (this.hasIn$METHOD_SUFFIX$ (this.getKeyFor$METHOD_SUFFIX$ (value), value));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean hasInPartners ($KEYTYPE$ key, $VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

// v4: One-Quali-Ext
#BeginCodeBlock = assoc-hasIn-v4
return ((this.$FIELDNAME$ != null) &&
        (value != null || this.$FIELDNAME$.containsKey (key)) &&
        (key != null) &&
        (this.$FIELDNAME$.get (key) == value));
#EndCodeBlock

// v5: Many-Quali-Ext
#BeginCodeBlock = assoc-hasIn-v5
return ((this.$FIELDNAME$ != null) &&
        (key != null) &&
        this.$FIELDNAME$.containsEntry (key, value));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean hasKeyInPartners ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1:  One-Quali-Ext, Many-Quali-Ext, One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-hasKeyIn-v1
return ((this.$FIELDNAME$ != null) &&
        (key != null) &&
        this.$FIELDNAME$.containsKey (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator iteratorOfPartners ()
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many
#BeginCodeBlock = assoc-iteratorOf-v1
return ((this.$FIELDNAME$ == null)
        ? FEmptyIterator.get ()
        : this.$FIELDNAME$.iterator ());
#EndCodeBlock


// v2: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-iteratorOf-v2
return ((this.$FIELDNAME$ == null)
        ? FEmptyIterator.get ()
        : this.$FIELDNAME$.values ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator iteratorOfPartners ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v3: Many-Quali-Ext, Many-Quali-Int
#BeginCodeBlock = assoc-iteratorOf-v3
return ((this.$FIELDNAME$ == null)
        ? FEmptyIterator.get ()
        : FCollections.iterator (this.$FIELDNAME$.values (key)));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator keysOfPartners ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-keysOf-v1
return ((this.$FIELDNAME$ == null)
        ? FEmptyIterator.get ()
        : this.$FIELDNAME$.keySet ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator entriesOfPartners ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-entriesOf-v1
return ((this.$FIELDNAME$ == null)
        ? FEmptyIterator.get ()
        : this.$FIELDNAME$.entrySet ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public int sizeOfPartners ()
//
///////////////////////////////////////////////////////////////////////////

// v1: Many, One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-sizeOf-v1
return ((this.$FIELDNAME$ == null)
        ? 0
        : this.$FIELDNAME$.size ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public int sizeOfPartners ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v2: Many-Quali-Int, Many-Quali-Ext
#BeginCodeBlock = assoc-sizeOf-v2
return ((this.$FIELDNAME$ == null)
        ? 0
        : this.$FIELDNAME$.size (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public $VALUETYPE$ getFromPartners ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, One-Quali-Ext
#BeginCodeBlock = assoc-getFrom-v1
return (((this.$FIELDNAME$ == null) || (key == null))
        ? null
        : ($VALUETYPE$) this.$FIELDNAME$.get (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean addToPartners ($VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1: Many
#BeginCodeBlock = assoc-addTo-v1
boolean changed = false;

if (value != null$LINKLISTCODE$)
{
   if (this.$FIELDNAME$ == null)
   {
      this.$FIELDNAME$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeSet () or FLinkedList ()

   }
   $CHANGE_PREHOOK$
   changed = this.$FIELDNAME$.add (value);
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock

// v2: One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-addTo-v2
return this.addTo$METHOD_SUFFIX$ ($PARTNER_KEY$getKeyFor$METHOD_SUFFIX$ (value), value);
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean addToPartners ($KEYTYPE$ key, RightClass value)
//
///////////////////////////////////////////////////////////////////////////

// v4: One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-addTo-v4
boolean changed = false;

if (key != null)
{
   if (this.$FIELDNAME$ == null)
   {
      this.$FIELDNAME$ = new $CONTAINER$ ($CONSTRUCTORPARAM$);
   }
   $CHANGE_PREHOOK$
   $VALUETYPE$ oldValue = ($VALUETYPE$) this.$FIELDNAME$.put (key, value);
   if (oldValue != value)
   {
      changed = true;
   }
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean addToPartners (Map.Entry entry)
//
///////////////////////////////////////////////////////////////////////////

// v6: One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-addTo-v6
return addTo$METHOD_SUFFIX$ ($PARTNER_KEY$($KEYTYPE$) entry.getKey (), ($VALUETYPE$) entry.getValue ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromPartners ($VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many
#BeginCodeBlock = assoc-removeFrom-v1
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (value != null))
{
   $CHANGE_PREHOOK$
   changed = this.$FIELDNAME$.remove (value);
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock


// v2: One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-removeFrom-v2
return removeFrom$METHOD_SUFFIX$ (getKeyFor$METHOD_SUFFIX$ (value), value);
#EndCodeBlock


// v4: One-Quali-Ext, Many-Quali-Ext
#BeginCodeBlock = assoc-removeFrom-v4
boolean changed = false;

if (this.$FIELDNAME$ != null)
{
   Iterator iter = this.entriesOf$METHOD_SUFFIX$ ();
   Map.Entry entry;
   while (iter.hasNext ())
   {
      entry = (Map.Entry) iter.next ();
      if (entry.getValue () == value)
      {
         $CHANGE_PREHOOK$
         if (this.removeFrom$METHOD_SUFFIX$ (($KEYTYPE$) entry.getKey (), value))
         {
            changed = true;
         }
         $CHANGE_POSTHOOK$
      }
   }
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromPartners ($KEYTYPE$ key, $VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

// v5: One-Quali-Ext
#BeginCodeBlock = assoc-removeFrom-v5
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (key != null))
{
   $VALUETYPE$ oldValue = ($VALUETYPE$) this.$FIELDNAME$.get (key);
   if (oldValue == value &&
       (oldValue != null || this.$FIELDNAME$.containsKey (key)))
   {
      $CHANGE_PREHOOK$
      this.$FIELDNAME$.remove (key);
      changed = true;
      $CHANGE_POSTHOOK$
   }
}
return changed;
#EndCodeBlock


// v6: Many-Quali-Ext
#BeginCodeBlock = assoc-removeFrom-v6
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (key != null) &&
    (value != null || this.$FIELDNAME$.containsEntry (key, value)))
{
   $CHANGE_PREHOOK$
   $VALUETYPE$ oldValue  = ($VALUETYPE$) this.$FIELDNAME$.remove (key, value);
   if (oldValue == value)
   {
     changed = true;
   }
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeKeyFromPartners ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1:  One-Quali-Int, One-Quali-Ext
#BeginCodeBlock = assoc-removeKeyFrom-v1
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (key != null))
{
   changed = this.$FIELDNAME$.containsKey (key);
   if (changed)
   {
      $CHANGE_PREHOOK$
      $VALUETYPE$ tmpValue = ($VALUETYPE$) this.$FIELDNAME$.remove (key);
      $CHANGE_POSTHOOK$
   }
}
return changed;
#EndCodeBlock


// v2: Many-Quali-Int, Many-Quali-Ext
#BeginCodeBlock = assoc-removeKeyFrom-v2
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (key != null))
{
   $CHANGE_PREHOOK$
   Collection tmpCol = (Collection) this.$FIELDNAME$.values (key);
   if (tmpCol != null)
   {
      $VALUETYPE$ tmpValue;
      Iterator iter = tmpCol.iterator ();
      while (iter.hasNext ())
      {
         tmpValue = ($VALUETYPE$) iter.next ();
         this.removeFrom$METHOD_SUFFIX$ (key, tmpValue);
      }
      changed = true;
   }
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public void removeAllFromPartners ()
//
///////////////////////////////////////////////////////////////////////////

// v1: Many
//$VALUETYPE$ tmpValue;
//Iterator iter = this.iteratorOf$METHOD_SUFFIX$ ();
//
//while (iter.hasNext ())
//{
//   tmpValue = ($VALUETYPE$) iter.next ();
//   this.removeFrom$METHOD_SUFFIX$ (tmpValue);
//}
#BeginCodeBlock = assoc-removeAllFrom-v1
if (this.$FIELDNAME$ != null && this.$FIELDNAME$.size () > 0)
{
   $CHANGE_PREHOOK$
   this.$FIELDNAME$.clear();
   $CHANGE_POSTHOOK$
}
#EndCodeBlock


// v2: One-Quali-Ext, Many-Quali-Ext, One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-removeAllFrom-v2
$CHANGE_PREHOOK$
Iterator iter = entriesOf$METHOD_SUFFIX$ ();
Map.Entry entry;
while (iter.hasNext ())
{
   entry = (Map.Entry) iter.next ();
   removeFrom$METHOD_SUFFIX$ (($KEYTYPE$) entry.getKey (), ($VALUETYPE$) entry.getValue ());
}
$CHANGE_POSTHOOK$
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public $KEYTYPE$ getKeyForPartners ($VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////
// One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-getKeyFor
return (value == null ? null : value.$GETKEY$ ());
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public void keyChangedInPartners ($KEYTYPE$ oldKey, $VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////
// One-Quali-Int
#BeginCodeBlock = assoc-keyChangedIn-one
if ((this.$FIELDNAME$ != null) &&
    (oldKey != getKeyFor$METHOD_SUFFIX$ (value)))
{
   $VALUETYPE$ oldValue = ($VALUETYPE$) this.$FIELDNAME$.get (oldKey);
   if (oldValue == value)
   {
      this.$FIELDNAME$.remove (oldKey);
      this.$FIELDNAME$.put (this.getKeyFor$METHOD_SUFFIX$ (value), value);
   }
}
#EndCodeBlock

// Many-Quali-Int
#BeginCodeBlock = assoc-keyChangedIn-many
if ((this.$FIELDNAME$ != null) &&
    (oldKey != getKeyFor$METHOD_SUFFIX$ (value)))
{
   $VALUETYPE$ oldValue  = ($VALUETYPE$) this.$FIELDNAME$.remove (oldKey, value);
   if (oldValue != null)
   {
      this.$FIELDNAME$.put (getKeyFor$METHOD_SUFFIX$ (value), value);
   }
}
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public $VALUETYPE$ getPartnersAt ( int index )
// public $VALUETYPE$ getFromPartners ( int index )
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many
#BeginCodeBlock = assoc-getAt
if (index >= 0 && index < sizeOf$METHOD_SUFFIX$ ())
{
	return ($VALUETYPE$) this.$FIELDNAME$.get (index);
}
else
{
	throw new IllegalArgumentException ("get$METHOD_SUFFIX$At(" + index + ")" );
}
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public int indexOfPartners ( $VALUETYPE$ value )
//
///////////////////////////////////////////////////////////////////////////

// v1:
#BeginCodeBlock = assoc-indexOf
return ((this.$FIELDNAME$ == null)
	? -1
	: this.$FIELDNAME$.indexOf (value));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public int indexOfPartners ( $VALUETYPE$ value, int index )
//
///////////////////////////////////////////////////////////////////////////

// v1:
#BeginCodeBlock = assoc-indexOf-Index
return ((this.$FIELDNAME$ == null)
	? -1
	: this.$FIELDNAME$.indexOf (value, index));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public int lastIndexOfPartners ( $VALUETYPE$ value )
//
///////////////////////////////////////////////////////////////////////////

// v1:
#BeginCodeBlock = assoc-lastIndexOf
return ((this.$FIELDNAME$ == null)
	? -1
	: this.$FIELDNAME$.lastIndexOf (value));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public int lastIndexOfPartners ( $VALUETYPE$ value, int index )
//
///////////////////////////////////////////////////////////////////////////

// v1:
#BeginCodeBlock = assoc-lastIndexOf-Index
return ((this.$FIELDNAME$ == null)
	? -1
	: this.$FIELDNAME$.lastIndexOf (value, index));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean isBeforeOfPartner (leftObject, rightObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-isBeforeOf
if ($FIELDNAME$ == null)
{
   return false;
}
else
{
   return $FIELDNAME$.isBefore (leftObject, rightObject);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public booelan isAfterOfPartner (leftObject, rightObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-isAfterOf
if ($FIELDNAME$ == null)
{
   return false;
}
else
{
   return $FIELDNAME$.isAfter (leftObject, rightObject);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getFirstOfPartner ()
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getFirstOf
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   if ($FIELDNAME$.size() == 0)
   {
      return null;
   }
   return ($VALUETYPE$) $FIELDNAME$.getFirst ();
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getLastOfPartner ()
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getLastOf
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   if ($FIELDNAME$.size() == 0)
   {
      return null;
   }
   return ($VALUETYPE$) $FIELDNAME$.getLast ();
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getNextOfPartner (object)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getNextOf
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   return ($VALUETYPE$) $FIELDNAME$.getNextOf (object);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getNextOfPartner (object, index)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getNextOf-Index
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   return ($VALUETYPE$) $FIELDNAME$.getNextOf (object, index);
}
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public object getPreviousOfPartner (object)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getPreviousOf
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   return ($VALUETYPE$) $FIELDNAME$.getPreviousOf (object);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getPreviousIndexOfPartner (object, index)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getPreviousOf-Index
if ($FIELDNAME$ == null)
{
   return null;
}
else
{
   return ($VALUETYPE$) $FIELDNAME$.getPreviousIndexOf (object, index);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public boolean addAfterOfPartner (refObject, value)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-addAfterOf
boolean changed;
if ($FIELDNAME$ == null)
{
   changed = false;
}
else
{
   int index = $FIELDNAME$.indexOf (refObject);
   changed = addTo$METHOD_SUFFIX$ ($PARTNER_KEY$index+1, value);
}
return changed;
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public boolean addBeforeOfPartner (refObject, value)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-addBeforeOf
boolean changed = false;
if ($FIELDNAME$ != null)
{
   int index = $FIELDNAME$.indexOf (refObject);
   changed = addTo$METHOD_SUFFIX$ ($PARTNER_KEY$index, value);
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean addToPartner (int index, value)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-addTo-Index
boolean changed = false;

if (value != null$LINKLISTCODE$)
{
   if (this.$FIELDNAME$ == null)
   {
      this.$FIELDNAME$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeSet () or FLinkedList ()
   }
   try
   {
      $CHANGE_PREHOOK$
      $FIELDNAME$.add (index, value);
      changed = true;
      $CHANGE_POSTHOOK$
   }
   catch (IndexOutOfBoundsException ex)
   {
      return false;
   }
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean setInPartners (int index, value)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-setIn
boolean changed = false;

if (value != null$LINKLISTCODE$)
{
   if (this.$FIELDNAME$ == null)
   {
      this.$FIELDNAME$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeSet () or FLinkedList ()
   }
   try
   {
      $CHANGE_PREHOOK$
      $VALUETYPE$ oldValue = ($VALUETYPE$)this.$FIELDNAME$.set (index, value);
      if (oldValue != value)
      {
         changed = true;
      }
      $CHANGE_POSTHOOK$
   }
   catch (IndexOutOfBoundsException ex)
   {
      return false;
   }
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromPartners (int index)
//
///////////////////////////////////////////////////////////////////////////

// v1
#BeginCodeBlock = assoc-removeFrom-List-v1
boolean changed = false;

if (this.$FIELDNAME$ != null && (index >= 0 && index < this.$FIELDNAME$.size ()))
{
   $CHANGE_PREHOOK$
   $VALUETYPE$ tmpValue = ($VALUETYPE$) this.$FIELDNAME$.remove (index);
   if (tmpValue != null)
   {
      changed = true;
   }
   $CHANGE_POSTHOOK$
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromPartners (int index, $VALUETYPE$ value)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-removeFrom-List-v2
boolean changed = false;

if ((this.$FIELDNAME$ != null) && (value != null) &&
    (index >= 0 && index < this.$FIELDNAME$.size ()))
{
   $VALUETYPE$ oldValue = ($VALUETYPE$) this.$FIELDNAME$.get (index);
   if (oldValue == value)
   {
      $CHANGE_PREHOOK$
      changed = this.removeFrom$METHOD_SUFFIX$ (index);
      $CHANGE_POSTHOOK$
   }
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public ListIterator iteratorOfContainerName (lowerBound)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-iteratorOf-Ml-List

ListIterator result = FEmptyListIterator.get ();

if ($FIELDNAME$ != null && lowerBound != null)
{
   int index = $FIELDNAME$.indexOf (lowerBound) + 1;
   result = $FIELDNAME$.listIterator (index);
}
else if ($FIELDNAME$ != null && lowerBound == null)
{
   result = $FIELDNAME$.listIterator (0);
}

return result;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public ListIterator iteratorOfPartners ()
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-iteratorOf-v1-List
return ((this.$FIELDNAME$ == null)
        ? FEmptyListIterator.get ()
        : this.$FIELDNAME$.listIterator ());
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public ListIterator iteratorOfPartners (int index)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-iteratorOf-Index-List
return ((this.$FIELDNAME$ == null)
        ? FEmptyListIterator.get ()
        : this.$FIELDNAME$.listIterator (Math.max(0,Math.min(index,this.$FIELDNAME$.size ()))));
#EndCodeBlock

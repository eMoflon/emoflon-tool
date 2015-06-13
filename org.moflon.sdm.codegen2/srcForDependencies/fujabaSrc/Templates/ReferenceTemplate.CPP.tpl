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
// public boolean setRightRole ($RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////
		
// v1:  One
#BeginCodeBlock = assoc-set-v1
boolean changed = false;

if ((this.$RIGHTROLE$ == null && value != null) ||
    (this.$RIGHTROLE$ != null && !this.$RIGHTROLE$.equals(value)))
{
   this.$RIGHTROLE$ = value;
   changed = true;
}
return changed;
#EndCodeBlock

// v1:  One , insert firePropertyChange statement
#BeginCodeBlock = assoc-set-v1-fpc
boolean changed = false;

if ((this.$RIGHTROLE$ == null && value != null) ||
    (this.$RIGHTROLE$ != null && !this.$RIGHTROLE$.equals(value)))
{
   $RIGHTCLASS$ oldValue = this.$RIGHTROLE$;

   this.$RIGHTROLE$ = value;

   firePropertyChange ( "$RIGHTROLE$" , oldValue , this.$RIGHTROLE$ ) ;
   changed = true;
}
return changed;
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public $RIGHTCLASS$ getRightRole ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One
#BeginCodeBlock = assoc-get-v1
return this.$RIGHTROLE$;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean hasInRightRoles ($RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many 
#BeginCodeBlock = assoc-hasIn-v1
return ((this.$RIGHTROLES$ != null) &&
        (value != null) &&
        this.$RIGHTROLES$.contains (value));
#EndCodeBlock


// v1A:  One-Quali-Ext, Many-Qual-Ext
#BeginCodeBlock = assoc-hasIn-v1A
return ((this.$RIGHTROLES$ != null) &&
        (value != null) &&
        this.$RIGHTROLES$.containsValue (value));
#EndCodeBlock


// v2: One-Quali-Int 
#BeginCodeBlock = assoc-hasIn-v2
return ((this.$RIGHTROLES$ != null) &&
        (value != null) && (value.$GETKEY$ () != null) &&
        (this.$RIGHTROLES$.get (value.$GETKEY$ ()) == value));
#EndCodeBlock


// v3: Many-Quali-Int 
#BeginCodeBlock = assoc-hasIn-v3
return ((this.$RIGHTROLES$ != null) &&
        (value != null) && (value.$GETKEY$ () != null) &&
        this.$RIGHTROLES$.containsEntry (value.$GETKEY$ (), value));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean hasInRightRoles ($KEYTYPE$ key, $RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////

// v4: One-Quali-Ext 
#BeginCodeBlock = assoc-hasIn-v4
return ((this.$RIGHTROLES$ != null) &&
        (value != null) && (key != null) &&
        (this.$RIGHTROLES$.get (key) == value));
#EndCodeBlock


// v5: Many-Quali-Ext 
#BeginCodeBlock = assoc-hasIn-v5
return ((this.$RIGHTROLES$ != null) &&
        (value != null) && (key != null) &&
        this.$RIGHTROLES$.containsEntry (key, value));
#EndCodeBlock

///////////////////////////////////////////////////////////////////////////
//
// public boolean hasKeyInRightRoles ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1:  One-Quali-Ext, Many-Quali-Ext, One-Quali-Int, Many-Quali-Int
#BeginCodeBlock = assoc-hasKeyIn-v1
return ((this.$RIGHTROLES$ != null) &&
        (key != null) &&
        this.$RIGHTROLES$.containsKey (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator iteratorOfRightRoles ()
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many 
#BeginCodeBlock = assoc-iteratorOf-v1
return ((this.$RIGHTROLES$ == null)
        ? FEmptyIterator.get ()
        : this.$RIGHTROLES$.iterator ());
#EndCodeBlock


// v2: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-iteratorOf-v2
return ((this.$RIGHTROLES$ == null)
        ? FEmptyIterator.get ()
        : this.$RIGHTROLES$.values ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator iteratorOfRightRoles ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v3: Many-Quali-Ext, Many-Quali-Int
#BeginCodeBlock = assoc-iteratorOf-v3
return ((this.$RIGHTROLES$ == null)
        ? FEmptyIterator.get ()
        : FCollections.iterator (this.$RIGHTROLES$.values (key)));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator keysOfRightRoles ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-keysOf-v1
return ((this.$RIGHTROLES$ == null)
        ? FEmptyIterator.get ()
        : this.$RIGHTROLES$.keySet ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public Iterator entriesOfRightRoles ()
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-entriesOf-v1
return ((this.$RIGHTROLES$ == null)
        ? FEmptyIterator.get ()
        : this.$RIGHTROLES$.entrySet ().iterator ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public int sizeOfRightRoles ()
//
///////////////////////////////////////////////////////////////////////////

// v1: Many, One-Quali-Int, Many-Quali-Int, One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-sizeOf-v1
return ((this.$RIGHTROLES$ == null)
        ? 0
        : this.$RIGHTROLES$.size ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public int sizeOfRightRoles ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v2: Many-Quali-Int, Many-Quali-Ext
#BeginCodeBlock = assoc-sizeOf-v2
return ((this.$RIGHTROLES$ == null)
        ? 0
        : this.$RIGHTROLES$.size (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public $RIGHTCLASS$ getFromRightRoles ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1: One-Quali-Int, One-Quali-Ext
#BeginCodeBlock = assoc-getFrom-v1
return (((this.$RIGHTROLES$ == null) || (key == null))
        ? null
        : ($RIGHTCLASS$) this.$RIGHTROLES$.get (key));
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean addToRightRoles ($RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1: Many 
#BeginCodeBlock = assoc-addTo-v1
boolean changed = false;

if (value != null$LINKLISTCODE$)
{
   if (this.$RIGHTROLES$ == null)
   {
      this.$RIGHTROLES$ = new $CONTAINER$ ( $CONSTRUCTORPARAM$ ); // or FTreeSet () or FLinkedList ()

   }
   changed = this.$RIGHTROLES$.add (value);
}
return changed;
#EndCodeBlock

// v2: One-Quali-Int 
#BeginCodeBlock = assoc-addTo-v2
boolean changed = false;

if ((value != null) && (value.$GETKEY$ () != null))
{
   if (this.$RIGHTROLES$ == null)
   {
      this.$RIGHTROLES$ = new $CONTAINER$ ( $CONSTRUCTORPARAM$ ) ; // or FTreeMap () 
   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (value.$GETKEY$ (), value);
   if (oldValue != value)
   {
      changed = true;
   }
}
return changed;
#EndCodeBlock

// v3: Many-Quali-Int 
#BeginCodeBlock = assoc-addTo-v3
boolean changed = false;

if ((value != null) && (value.$GETKEY$ () != null))
{
   if (this.$RIGHTROLES$ == null)
   {
       this.$RIGHTROLES$ = new $CONTAINER$ ( $CONSTRUCTORPARAM$ ) ; // or FDuplicatedTreeMap ()
   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (value.$GETKEY$ (), value);
   if (oldValue == null)
   {
      changed = true;
   }
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean addToRightRoles ($KEYTYPE$ key, RightClass value)
//
///////////////////////////////////////////////////////////////////////////

// v4: One-Quali-Ext 
#BeginCodeBlock = assoc-addTo-v4
boolean changed = false;

if ((value != null) && (key != null))
{
   if (this.$RIGHTROLES$ == null)
   {
      this.$RIGHTROLES$ = new $CONTAINER$ ( $CONSTRUCTORPARAM$ ) ; // or FTreeMap 

   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (key, value);
   if (oldValue != value)
   {
      changed = true;
   }
}
return changed;
#EndCodeBlock


//  v5: Many-Quali-Ext 
#BeginCodeBlock = assoc-addTo-v5
boolean changed = false;

if ((value != null) && (key != null))
{
   if (this.$RIGHTROLES$ == null)
   {
      this.$RIGHTROLES$ = new $CONTAINER$ ( $CONSTRUCTORPARAM$ ) ; // or FDuplicatedTreeMap
   }
   $RIGHTCLASS$ oldValue  = ($RIGHTCLASS$) this.$RIGHTROLES$.put (key, value);
   changed = true;
}
return false;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean addToRightRoles (Map.Entry entry)
//
///////////////////////////////////////////////////////////////////////////

// v6: One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-addTo-v6
return addTo$RIGHTROLES_SUFFIX$ (($KEYTYPE$) entry.getKey (), ($RIGHTCLASS$) entry.getValue ());
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromRightRoles ($RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many 
#BeginCodeBlock = assoc-removeFrom-v1
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null))
{
   changed = this.$RIGHTROLES$.remove (value);
}
return changed;
#EndCodeBlock


// v2: One-Quali-Int 
#BeginCodeBlock = assoc-removeFrom-v2
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null) && (value.$GETKEY$ () != null))
{
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.get (value.$GETKEY$ ());
   if (oldValue == value)
   {
      this.$RIGHTROLES$.remove (value.$GETKEY$ ());
      changed = true;
   }
}
return changed;
#EndCodeBlock


// v3: Many-Quali-Int 
#BeginCodeBlock = assoc-removeFrom-v3
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null) && (value.$GETKEY$ () != null))
{
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.remove (value.$GETKEY$ (), value);
   if (oldValue != null)
   {
      changed = true;
   }
}
return changed;
#EndCodeBlock


// v4: One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-removeFrom-v4
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null))
{
   Iterator iter = this.entriesOf$RIGHTROLES_SUFFIX$ ();
   Map.Entry entry;
   while (iter.hasNext ())
   {
      entry = (Map.Entry) iter.next ();
      if (entry.getValue () == value)
      {
         changed = changed || this.removeFrom$RIGHTROLES_SUFFIX$ (($KEYTYPE$) entry.getKey (), value);
      }
   }
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeFromRightRoles ($KEYTYPE$ key, $RIGHTCLASS$ value)
//
///////////////////////////////////////////////////////////////////////////

// v5: One-Quali-Ext
#BeginCodeBlock = assoc-removeFrom-v5
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null) && (key != null))
{
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.get (key);
   if (oldValue == value)
   {
      this.$RIGHTROLES$.remove (key);
      changed = true;
   }
}
return changed;
#EndCodeBlock


// v6: Many-Quali-Ext 
#BeginCodeBlock = assoc-removeFrom-v6
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (value != null) && (key != null))
{
   
   $RIGHTCLASS$ oldValue  = ($RIGHTCLASS$) this.$RIGHTROLES$.remove (key, value);
   changed = true;
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public boolean removeKeyFromRightRoles ($KEYTYPE$ key)
//
///////////////////////////////////////////////////////////////////////////

// v1:  One-Quali-Int, One-Quali-Ext 
#BeginCodeBlock = assoc-removeKeyFrom-v1
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (key != null))
{
   $RIGHTCLASS$ tmpValue = ($RIGHTCLASS$) this.$RIGHTROLES$.get (key);
   if (tmpValue != null)
   {
      this.$RIGHTROLES$.remove (key);
      changed = true;
   }
}
return changed;
#EndCodeBlock


// v2: Many-Quali-Int, Many-Quali-Ext 
#BeginCodeBlock = assoc-removeKeyFrom-v2
boolean changed = false;

if ((this.$RIGHTROLES$ != null) && (key != null))
{
   Collection tmpCol = (Collection) this.$RIGHTROLES$.remove (key);
   if (tmpCol != null)
   {
      changed = true;
   }
}
return changed;
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public void removeAllFromRightRoles ()
//
///////////////////////////////////////////////////////////////////////////

// v1: Many, One-Quali-Int, Many-Quali-Int 
#BeginCodeBlock = assoc-removeAllFrom-v1
$RIGHTCLASS$ tmpValue;
Iterator iter = this.iteratorOf$RIGHTROLES_SUFFIX$ ();

while (iter.hasNext ())
{
   tmpValue = ($RIGHTCLASS$) iter.next ();
   this.removeFrom$RIGHTROLES_SUFFIX$ (tmpValue);
}
#EndCodeBlock


// v2: One-Quali-Ext, Many-Quali-Ext 
#BeginCodeBlock = assoc-removeAllFrom-v2
Iterator iter = entriesOf$RIGHTROLES_SUFFIX$ ();
Map.Entry entry;
while (iter.hasNext ())
{
   entry = (Map.Entry) iter.next ();
   removeFrom$RIGHTROLES_SUFFIX$ (($KEYTYPE$) entry.getKey (), ($RIGHTCLASS$) entry.getValue ());
}
#EndCodeBlock



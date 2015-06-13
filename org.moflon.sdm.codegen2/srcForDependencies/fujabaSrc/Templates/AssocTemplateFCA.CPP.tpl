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

if (this.$RIGHTROLE$ != value)
{
   if (this.$RIGHTROLE$ != null)
   {
      $RIGHTCLASS$ oldValue = this.$RIGHTROLE$;
      this.$RIGHTROLE$ = null;
      oldValue.$REMOVE$;
   }
   this.$RIGHTROLE$ = value;
   if (value != null)
   {
      value.$INSERT$;
   }
   changed = true;
}
return changed;
#EndCodeBlock

// v1:  One , insert firePropertyChange statement
#BeginCodeBlock = assoc-set-v1-fpc
boolean changed = false;

if (this.$RIGHTROLE$ != value)
{
   $RIGHTCLASS$ oldValue = this.$RIGHTROLE$;

   if (this.$RIGHTROLE$ != null)
   {
      this.$RIGHTROLE$ = null;
      oldValue.$REMOVE$;
   }
   this.$RIGHTROLE$ = value;

   if (value != null)
   {
      value.$INSERT$;
   }

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
      this.$RIGHTROLES$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeSet () or FLinkedList ()

   }
   changed = this.$RIGHTROLES$.addToRightToles (value);
   if (changed)
   {
      value.$INSERT$;
   }
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
      this.$RIGHTROLES$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeMap () 
   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (value.$GETKEY$ (), value);
   if (oldValue != value)
   {
      if (oldValue != null)
      {
         oldValue.$REMOVE$;
      }
      value.$INSERT$;
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
       this.$RIGHTROLES$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FDuplicatedTreeMap ()
   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (value.$GETKEY$ (), value);
   if (oldValue == null)
   {
      value.$INSERT$;
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
      this.$RIGHTROLES$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FTreeMap 

   }
   $RIGHTCLASS$ oldValue = ($RIGHTCLASS$) this.$RIGHTROLES$.put (key, value);
   if (oldValue != value)
   {
      if (oldValue != null)
      {
         oldValue.$REMOVE$;
      }
      value.$INSERT$;
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
      this.$RIGHTROLES$ = new $CONTAINER$ ($CONSTRUCTORPARAM$); // or FDuplicatedTreeMap
   }
   $RIGHTCLASS$ oldValue  = ($RIGHTCLASS$) this.$RIGHTROLES$.put (key, value);
   if (oldValue != null)
   {
      oldValue.$REMOVE$;
   }
   value.$INSERT$;
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
   if (changed)
   {
      value.$REMOVE$;
   }
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
      value.$REMOVE$;
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
      value.$REMOVE$;
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
      value.$REMOVE$;
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
   if (oldValue != null)
   {
     value.$REMOVE$;
   }
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
      tmpValue.$REMOVE$;
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
      $RIGHTCLASS$ tmpValue;
      Iterator iter = tmpCol.iterator ();
      while (iter.hasNext ())
      {
         tmpValue = ($RIGHTCLASS$) iter.next ();
         tmpValue.$REMOVE$;
      }
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

///////////////////////////////////////////////////////////////////////////
//
// public $RIGHTCLASS$ getRightRolesAt ( int index ) 
//
///////////////////////////////////////////////////////////////////////////

// v1:  Many 
#BeginCodeBlock = assoc-getAt-v1
if (index >= 0 && index < sizeOf$RIGHTROLES_SUFFIX$ ())
{
	return ($RIGHTCLASS$) this.$RIGHTROLES$.get (index);
}
else
{
	throw new IllegalArgumentException ("get$RIGHTROLES_SUFFIX$At(" + index + ")" );
}
#EndCodeBlock


///////////////////////////////////////////////////////////////////////////
//
// public int indexOfRightRoles ( $RIGHTCLASS$ elem ) 
//
///////////////////////////////////////////////////////////////////////////

// v1: 
#BeginCodeBlock = assoc-indexOf-v1
return ((this.$RIGHTROLES$ == null)
	? -1
	: this.$RIGHTROLES$.indexOf (elem));
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public int lastIndexOfRightRoles ( $RIGHTCLASS$ elem ) 
//
///////////////////////////////////////////////////////////////////////////

// v1:
#BeginCodeBlock = assoc-lastIndexOf-v1
return ((this.$RIGHTROLES$ == null)
	? -1
	: this.$RIGHTROLES$.lastIndexOf (elem));
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public booelan isBeforeOfAssocName (leftObject, rightObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-isBeforeOf
if ($CONTAINERNAME$ == null)
{
   return false;
}
else
{
   return $CONTAINERNAME$.isBefore (leftObject, rightObject);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public booelan isAfterOfAssocName (leftObject, rightObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-isAfterOf
if ($CONTAINERNAME$ == null)
{
   return false;
}
else
{
   return $CONTAINERNAME$.isAfter (leftObject, rightObject);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getFirstOfAssocName ()
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getFirstOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getFirst ();
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getLastOfAssocName ()
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getLastOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getLast ();
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getNextOfAssocName (object)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getNextOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getNextOf (object);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getNextIndexOfAssocName (object, index)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getNextIndexOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getNextIndexOf (object, index);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getPreviousOfAssocName (object)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getPreviousOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getPreviousOf (object);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public object getPreviousIndexOfAssocName (object, index)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-getPreviousIndexOf
if ($CONTAINERNAME$ == null)
{
   return null;
}
else
{
   return ($RETURNTYPE$) $CONTAINERNAME$.getPreviousIndexOf (object, index);
}
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public boolean addAfterOfAssocName (refObject, newObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-addAfterOf
boolean changed;
if ($CONTAINERNAME$ == null)
{
   changed = false;
}
else
{
   int index = $CONTAINERNAME$.indexOf (refObject);
   try
   {
      $CONTAINERNAME$.add (index++, newObject);
      changed = true;
   }
   catch (IndexOutOfBoundsException e)
   {
      changed = false
   } 
}
return changed;
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public boolean addBeforeOfAssocName (refObject, newObject)
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-addBeforeOf
if ($CONTAINERNAME$ == null)
{
   changed = false;
}
else
{
   int index = $CONTAINERNAME$.indexOf (refObject);
   try
   {
      $CONTAINERNAME$.add (index, newObject);
      changed = true;
   }
   catch (IndexOutOfBoundsException e)
   {
      changed = false
   } 
}
return changed;
#EndCodeBlock



///////////////////////////////////////////////////////////////////////////
//
// public Iterator iteratorOfContainerName (lowerBound)  
//
///////////////////////////////////////////////////////////////////////////

#BeginCodeBlock = assoc-iteratorOf-Ml

Iterator result = FEmptyIterator.get ();

if ($CONTAINERNAME$ == null)
{		
   result = FEmptyIterator.get ();
}	
else if ($CONTAINERNAME$ != null && lowerBound != null)
{
   int index = $CONTAINERNAME$.indexOf (lowerBound) + 1;
   result = $CONTAINERNAME$.listIterator (index);
}
else if ($CONTAINERNAME$ != null && lowerBound == null)
{
   result = $CONTAINERNAME$.listIterator (0);
}

return result;
#EndCodeBlock



/*
 * $Log$
 * Revision 1.6  2006/03/03 17:30:10  mm
 * moved Templates folder into source folder
 *
 * Revision 1.4  2004/10/20 17:49:21  schneider
 * Introduction of interfaces for class diagram classes
 *
 * Revision 1.1.1.1  2004/09/20 09:12:50  cschneid
 * initial version
 *
 * Revision 1.3  2002/07/07 23:30:28  creckord
 * Small update to RuntimeTools, work on unparsing for Story Patterns, bugfixes, new selection/highlighting, Popups for Lines
 *
 * Revision 1.2  2002/04/10 13:40:40  mksoft
 * add UMLNGStatementActivity(uses a list of OOStatement instead of String state.statement), update copyright statement
 *
 * Revision 1.1  2001/11/20 19:19:14  mksoft
 * cp AssocTemplate.tpl AssocTemplate.(CPP|Eiffel).tpl \n cp ReferenceTemplate.tpl Refeerence.(CPP|Eiffel).tpl
 *
 * Revision 1.19  2001/08/27 15:55:33  hung
 * fix code generation for iteratorOfXXX(..)
 *
 * Revision 1.18  2001/08/02 17:25:37  kanswa
 * minor changes
 *
 * Revision 1.17  2001/07/12 12:22:02  kanswa
 * change isBeforeOf to isBefore...
 *
 * Revision 1.16  2001/07/06 16:05:51  lowende
 * Some minor changes.
 *
 * Revision 1.15  2001/07/05 09:25:39  kanswa
 * add templates for multilink
 *
 * Revision 1.14  2001/07/04 12:49:33  mksoft
 * add missing $
 *
 * Revision 1.13  2001/07/04 10:28:15  mksoft
 * bugfix
 *
 * Revision 1.12  2001/07/03 15:06:03  mksoft
 * add lastIndexOf,indexOf,getElementAt (for ordered 1-n assocs) code-templates
 *
 * Revision 1.11  2000/12/13 11:11:59  mksoft
 * add propertyChangeSupport to assoc-code-generation if
 * OptionsJavaGenerator.GEN_FIRE_PROPERTY_CHANGE_CODE = true
 *
 * toOne : add firePropertyChange statement
 * toMany : use FProp<Collection> instead of F<Collection>
 *
 * Revision 1.10  2000/09/29 15:34:49  wag25
 * Template updated for linked list.
 *
 * Revision 1.9  2000/08/18 12:28:44  wag25
 * Template supports ordered assocs.
 *
 * Revision 1.8  2000/07/31 17:39:42  elrond
 * remove Iterator from sdm package and add a singelton to the FEmptyIterator and FEmptyListIterator
 *
 * Revision 1.7  2000/07/11 12:28:15  wag25
 * File removed.
 *
 * Revision 1.6  2000/06/07 16:06:12  lowende
 * Assocs documentation tool added.
 *
 * Revision 1.5  2000/05/26 16:18:39  wag25
 * Changed code generation of One-Qual-Int assocs.
 *
 * Revision 1.4  2000/05/16 16:03:46  lowende
 * INSERT and REMOVE in set method swapped.
 *
 * Revision 1.3  2000/05/12 15:43:19  lowende
 * Indent bug removed.
 *
 * Revision 1.2  2000/05/11 17:48:18  wag25
 * Indent problem with template file removed.
 *
 * Revision 1.1  2000/05/11 17:09:04  wag25
 * Changed template file ending to *.tpl
 *
 * Revision 1.1  2000/05/11 16:59:27  wag25
 * Standard assoc is generated from template file.
 *
 */


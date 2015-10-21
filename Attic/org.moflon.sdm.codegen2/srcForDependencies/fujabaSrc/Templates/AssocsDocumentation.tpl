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
// Copyright   : Copyright (c) 1998-2001
// Author      : Student research group Fujaba
// Organisation: University of Paderborn
// Description : Template for HTML documentation of assocs

// $Id$

// Index
#BeginCodeBlock = Index
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Fujaba Associations Specification</title>
</head>

   <frameset cols="250,*">
      <frame src="$SHORTCUTS_FILENAME$" name="Content">
      <frame src="$MAIN_FILENAME$" name="Main">
   </frameset>

</html>
#EndCodeBlock



// Shortcuts
#BeginCodeBlock = Shortcuts
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">

<center><h2>Fujaba<sup><font size="-2">TM</font></sup> Associations Specification</h2></center>

<a href="$MAIN_FILENAME$" target="Main">Main</a>

<h3>Associations</h3>

<a href="$ONE_FILENAME$" target="Main">"To One"</a>
<br><a href="$MANY_FILENAME$" target="Main">"To Many"</a>
<br><a href="$AQO_FILENAME$" target="Main">Attributed qualified "To One"</a>
<br><a href="$AQM_FILENAME$" target="Main">Attributed qualified "To Many"</a>
<br><a href="$QO_FILENAME$" target="Main">Qualified "To One"</a>
<br><a href="$QM_FILENAME$" target="Main">Qualified "To Many"</a>

</body>
</html>
#EndCodeBlock



// Main
#BeginCodeBlock = Main
<html>
  
<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Main</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000EE" vlink="#551A8B" alink="#FF0000">

   <center><h1>Fujaba<sup><font size="-2">TM</font></sup> Associations Specification</h1></center>

<h2>Unterscheidungen der Assoziationen</h2> 
Assoziationen werden durch die folgenden f&uuml;nf Attribute
n&auml;her beschrieben.

<ol compact>
  <li>Anzahl der beteiligten Klassen
  <li>Name der Assoziation
  <li>Namen der jeweiligen Rollen
  <li>Kardinalit&auml;t der jeweiligen Rollen
  <li>Qualifizierte Rollen
</ol>

Im folgenden werden nur Assoziationen mit zwei beteiligten Klassen
betrachtet. Bei einer n&auml;heren Untersuchen der jeweiligen
Attribute f&auml;llt auf, da&szlig; die beiden letzten Eigenschaften eine
besondere Auswirkung auf das jeweilig zu implementierende Interface und
deren Umsetzung im Quelltext haben.
<p>
In der folgenden Tabelle werden die sechs m&ouml;glichen Assoziationen aufgeschl&uuml;sselt.
<p>

<center>
<table border >

   <tr>
      <th>Association</th> 
      <th>Cardinality</th>
      <th>Qualifier</th>
      <th>Key Cardinality</th>
   </tr>

   <tr align=center>
      <td><a href="$ONE_FILENAME$">"To One"</a></td>
      <td>1</td>
      <td>-</td>
      <td>-</td>
   </tr>

   <tr align=center>
      <td><a href="$MANY_FILENAME$">"To Many"</a></td>
      <td>n</td>
      <td>-</td>
      <td>-</td>
   </tr>

   <tr align=center>
      <td><a href="$AQO_FILENAME$">Attributed qualified "To One"</a></td>
      <td>n</td>
      <td>attribute</td>
      <td>1</td>
   </tr>

   <tr align=center>
      <td><a href="$AQM_FILENAME$">Attributed qualified "To Many"</a></td>
      <td>n</td>
      <td>attribute</td>
      <td>n</td>
   </tr>

   <tr align=center>
      <td><a href="$QO_FILENAME$">Qualified "To One"</a></td>
      <td>n</td>
      <td>any</td>
      <td>1</td>
   </tr>

   <tr align=center>
      <td><a href="$QM_FILENAME$">Qualified "To Many"</a></td>
      <td>n</td>
      <td>any</td>
      <td>n</td>
  </tr>

</table>
</center>

<h2>Implementierungsanweisungen</h2>

<ol compact>
  <li> Im Kommentar vor einer Klasse sollten <b>alle</b> Assoziationen
  der Klasse dokumentiert sein!

  <li> Vor jedem Attribut, das zu einer Assoziation geh&ouml;rt, mu&szlig; die
  Assoziation dokumentiert werden.

  <li> Die Zugriffsmethoden f&uuml;r die jeweiligen Assoziationen m&uuml;ssen
  wie in diesem Dokument beschrieben implementiert werden. Sollten
  einige Randbedingungen eine andere Implementierung oder
  zus&auml;tzliche Methoden erforden, so sind diese ausf&uuml;hrlich
  in dem Kommentar zur Assoziation und in bzw. &uuml;ber der jeweiligen
  Methode zu dokumentieren.

  <li>Methoden, die in den Zugriffsmethoden benutzt werden, werden
  hinter der letzten Zugriffsmethode im Quelltext positioniert.

  <li> Vor und nach jedem Block, der die Zugriffsmethoden
  incl. Kommentaren und anderen ben&ouml;tigten Methoden beinhaltet,
  werden Einzeilenkommentare eingef&uuml;gt, so da&szlig; die Bl&ouml;cke
  besser zu erkennen sind.
</ol>

<h2><a name="Container">Auswahl eines Containers</a></h2>
Zur Auswahl stehen folgende Container:

   <ul>
      <li>FHashSet
      <li>FHashMap
      <li>FDuplicatedHashMap
      <p>
      <li>FLinkedList
      <li>FTreeSet
      <li>FTreeMap
      <li>FDuplicatedTreeMap
   </ul>
     
Von Au&szlig;en unterscheiden sich die Hashtabellen und B&auml;ume nur wenig.
Der wesentliche Unterschied liegt in der Reihenfolge, in der die Elemente
durch einen Iterator ausgegeben werden. Bei Hashtabellen werden die Elemente
in Einf&uuml;gefolge und bei B&auml;umen in Sortierfolge ausgegeben.
<p>
Bei der Verwendung von B&auml;umen m&uuml;ssen die Elemente das Interface
Comparable implementieren. Ein Standard-Comparator wird zur Verf&uuml;gung
gestellt, es kann aber auch ein spezieller Comparator angegeben werden.
<p>
Zu beachten ist eine korrekte Implementierung der Funktion hashCode () bei
Verwendung der Hashtabellen!

<h2>Darstellung einer Assoziation im Kommentar der Klasse</h2>

<pre><tt>
/**
 * &lt;h2&gt;Associations&lt;/h2&gt;
 *
 * &lt;pre&gt;
 *            1                       1
 * LeftClass --------------------------- RightClass
 *            leftRole        rightRole
 * &lt;/pre&gt;
 */
public LeftClass
{
</tt></pre>

</body>
</html>
#EndCodeBlock



// One
#BeginCodeBlock = One
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>"To One"-Association</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
<center><h1>"To One"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *                                   1
 * LeftClass -------------------------- RightClass
 *                           rightrole
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private RightClass rightrole;</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#set"><b>setRightRole</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public RightClass</code></td>
      <td><code><a href="#get"><b>getRightRole</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="set">public boolean <b>setRightRole</b> (RightClass value)</a>
{<blockquote>$assoc-set-v1$</blockquote>}</code></pre>
   </tr>

   <tr>
      <td><pre><code>
<a name="get">public RightClass <b>getRightRole</b> ()</a>
{<blockquote>$assoc-get-v1$</blockquote>}</code></pre></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Comment</b></font></td>
   </tr>

   <tr>
      <td>
   Using a "To One" association in conjunction with a Qualified "To One" or "To Many" association would lead to the following signature:
<code><p>public boolean <b>setRightRole</b> (KeyType key, RightClass value)<br>
<pre>
&lt;method to remove&gt; = removeFromLeftRoles (key, this);
&lt;method to insert&gt; = addToLeftRoles (key, this);
</pre></code>
      </td>
   </tr>

</table>


</body>
</html>
#EndCodeBlock
   


// Many
#BeginCodeBlock = Many
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Many</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
<center><h1>"To Many"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *                                   n
 * LeftClass -------------------------- RightClass
 *                          rightRoles
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private FHashSet rightRoles;</code>
          <p>
          or
          <p>
          <code>private FTreeSet rightRoles;</code>
          <p>
          or
          <p>
          <code>private FLinkedList rightRoles;</code>
      </td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#hasIn"><b>hasInRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOf"><b>iteratorOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOf"><b>sizeOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addTo"><b>addToRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFrom"><b>removeFromRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeAllFrom"><b>removeAllFromRightRoles</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="hasIn">public boolean <b>hasInRightRoles</b> (RightClass value)</a>
{<blockquote>$assoc-hasIn-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="iteratorOf">public Iterator <b>iteratorOfRightRoles</b> ()</a>
{<blockquote>$assoc-iteratorOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="sizeOf">public int <b>sizeOfRightRoles</b> ()</a>
{<blockquote>$assoc-sizeOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="addTo">public boolean <b>addToRightRoles</b> (RightClass value)</a>
{<blockquote>$assoc-addTo-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="removeFrom">public boolean <b>removeFromRightRoles</b> (RightClass value)</a>
{<blockquote>$assoc-removeFrom-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
<a name="removeAllFrom">public void <b>removeAllFromRightRoles</b> ()</a>
{<blockquote>$assoc-removeAllFrom-v1$</blockquote>}</code></pre></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Comment</b></font></td>
   </tr>

   <tr>
      <td>
   Using a "To Many" association in conjunction with a Qualified "To One" or "To Many" association would lead to the following signatures:
<code><p>public boolean <b>addToRightRoles</b> (KeyType key, RightClass value)<br>
   public boolean <b>removeFromRightRoles</b> (KeyType key, RightClass value)<br>
<pre>
&lt;method to remove&gt; = removeFromLeftRoles (key, this);
&lt;method to insert&gt; = addToLeftRoles (key, this);
</pre></code>
      </td>
   </tr>

</table>

</body>
</html>
#EndCodeBlock



// One-Qualified-Intern
#BeginCodeBlock = One-Qualified-Intern
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Attributed qualified "To One"-Association</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
<center><h1>Attributed qualified "To One"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *           +----------+                 1
 * LeftClass | getKey() +------------------- RightClass
 *           +----------+        rightRoles
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private FHashMap rightRoles;</code>
          <p>
          or
          <p>
          <code>private FTreeMap rightRoles;</code>
      </td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#hasIn"><b>hasInRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOf"><b>iteratorOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#keysOf"><b>keysOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#entriesOf"><b>entriesOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOf"><b>sizeOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public RightClass</code></td>
      <td><code><a href="#getFrom"><b>getFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addTo"><b>addToRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFrom"><b>removeFromRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public void</code></td>
      <td><code><a href="#removeAllFrom"><b>removeAllFromRightRoles</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasIn"><b>hasInRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-hasIn-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-hasKeyIn-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOf"><b>iteratorOfRightRoles</b></a> ()
{<blockquote>$assoc-iteratorOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="keysOf"><b>keysOfRightRoles</b></a> ()
{<blockquote>$assoc-keysOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="entriesOf"><b>entriesOfRightRoles</b></a> ()
{<blockquote>$assoc-entriesOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOf"><b>sizeOfRightRoles</b></a> ()
{<blockquote>$assoc-sizeOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public RightClass <a name="getFrom"><b>getFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-getFrom-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addTo"><b>addToRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-addTo-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFrom"><b>removeFromRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-removeFrom-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-removeKeyFrom-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public void <a name="removeAllFrom"><b>removeAllFromRightRoles</b></a> ()
{<blockquote>$assoc-removeAllFrom-v1$</blockquote>}</code></pre></td>
   </tr>

</table>

</body>
</html>
#EndCodeBlock



// Many-Qualified-Intern
#BeginCodeBlock = Many-Qualified-Intern
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Attributed qualified "To Many"-Association</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
   <center><h1>Attributed qualified "To Many"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *           +----------+                 n
 * LeftClass | getKey() +------------------- RightClass
 *           +----------+        rightRoles
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private FDuplicatedHashMap rightRoles;</code>
          <p>
          or
          <p>
          <code>private FDuplicatedTreeMap rightRoles;</code>
      </td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#hasIn"><b>hasInRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOfV1"><b>iteratorOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOfV2"><b>iteratorOfRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#keysOf"><b>keysOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#entriesOf"><b>entriesOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOfV1"><b>sizeOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOfV2"><b>sizeOfRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addTo"><b>addToRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFrom"><b>removeFromRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public void</code></td>
      <td><code><a href="#removeAllFrom"><b>removeAllFromRightRoles</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">
   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasIn"><b>hasInRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-hasIn-v3$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-hasKeyIn-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOfV1"><b>iteratorOfRightRoles</b></a> ()
{<blockquote>$assoc-iteratorOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOfV2"><b>iteratorOfRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-iteratorOf-v3$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="keysOf"><b>keysOfRightRoles</b></a> ()
{<blockquote>$assoc-keysOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="entriesOf"><b>entriesOfRightRoles</b></a> ()
{<blockquote>$assoc-entriesOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOfV1"><b>sizeOfRightRoles</b></a> ()
{<blockquote>$assoc-sizeOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOfV2"><b>sizeOfRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-sizeOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addTo"><b>addToRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-addTo-v3$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFrom"><b>removeFromRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-removeFrom-v3$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-removeKeyFrom-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public void <a name="removeAllFrom"><b>removeAllFromRightRoles</b></a> ()
{<blockquote>$assoc-removeAllFrom-v1$</blockquote>}</code></pre></td>
   </tr>

</table>

</body>
</html>
#EndCodeBlock



// One-Qualified-Extern
#BeginCodeBlock = One-Qualified-Extern
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Qualified "To One"-Association</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
   <center><h1>Qualified "To One"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *           +-----+                 1
 * LeftClass | key +------------------- RightClass
 *           +-----+         rightrole
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private FHashMap rightRoles;</code>
          <p>
          or
          <p>
          <code>private FTreeMap rightRoles;</code>
      </td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#hasInV1"><b>hasInRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasInV2"><b>hasInRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOf"><b>iteratorOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#keysOf"><b>keysOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#entriesOf"><b>entriesOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOf"><b>sizeOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public RightClass</code></td>
      <td><code><a href="#getFrom"><b>getFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addToV1"><b>addToRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addToV2"><b>addToRightRoles</b></a> (Map.Entry entry)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFromV1"><b>removeFromRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFromV2"><b>removeFromRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public void</code></td>
      <td><code><a href="#removeAllFrom"><b>removeAllFromRightRoles</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasInV1"><b>hasInRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-hasIn-v1A$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasInV2"><b>hasInRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-hasIn-v4$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-hasKeyIn-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOf"><b>iteratorOfRightRoles</b></a> ()
{<blockquote>$assoc-iteratorOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="keysOf"><b>keysOfRightRoles</b></a> ()
{<blockquote>$assoc-keysOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="entriesOf"><b>entriesOfRightRoles</b></a> ()
{<blockquote>$assoc-entriesOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOf"><b>sizeOfRightRoles</b></a> ()
{<blockquote>$assoc-sizeOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public RightClass <a name="getFrom"><b>getFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-getFrom-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addToV1"><b>addToRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-addTo-v4$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addToV2"><b>addToRightRoles</b></a> (Map.Entry entry)
{<blockquote>$assoc-addTo-v6$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFromV1"><b>removeFromRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-removeFrom-v4$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFromV2"><b>removeFromRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-removeFrom-v5$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-removeKeyFrom-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public void <a name="removeAllFrom"><b>removeAllFromRightRoles</b></a> ()
{<blockquote>$assoc-removeAllFrom-v2$</blockquote>}</code></pre></td>
   </tr>

</table>

</body>
</html>
#EndCodeBlock



// Many-Qualified-Extern
#BeginCodeBlock = Many-Qualified-Extern
<html>

<head>
   <meta name="Author" content="Fujaba Development Group">
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <title>Qualified "To Many"-Association</title>
</head>

<body text="#000000" bgcolor="#FFFFFF" link="#0000FF" vlink="#990066" alink="#FF0000">
   
   <center><h1>Qualified "To Many"-Association</h1></center>

<pre><tt>
/**
 * &lt;pre&gt;
 *           +-----+                 n
 * LeftClass | key +------------------- RightClass
 *           +-----+        rightRoles
 * &lt;/pre&gt;
 */
</tt></pre>

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Attribute</b></font></td>
   </tr>

   <tr>
      <td><code>private FDuplicatedHashMap rightRoles;</code>
          <p>
          or
          <p>
          <code>private FDuplicatedTreeMap rightRoles;</code>
      </td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td colspan="2"><font size="+2"><b>Access Methods Summary</b></font></td>
   </tr>

   <tr>
      <td width="2%"><code>public boolean</code></td>
      <td><code><a href="#hasInV1"><b>hasInRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasInV2"><b>hasInRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOfV1"><b>iteratorOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#iteratorOfV2"><b>iteratorOfRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#keysOf"><b>keysOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public Iterator</code></td>
      <td><code><a href="#entriesOf"><b>entriesOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOfV1"><b>sizeOfRightRoles</b></a> ()</code></td>
   </tr>

   <tr>
      <td><code>public int</code></td>
      <td><code><a href="#sizeOfV2"><b>sizeOfRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addToV1"><b>addToRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#addToV2"><b>addToRightRoles</b></a> (Map.Entry entry)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFromV1"><b>removeFromRightRoles</b></a> (RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeFromV2"><b>removeFromRightRoles</b></a> (KeyType key, RightClass value)</code></td>
   </tr>

   <tr>
      <td><code>public boolean</code></td>
      <td><code><a href="#removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)</code></td>
   </tr>

   <tr>
      <td><code>public void</code></td>
      <td><code><a href="#removeAllFrom"><b>removeAllFromRightRoles</b></a> ()</code></td>
   </tr>

</table>

&nbsp;

<table border="1" cellpadding="3" cellspacing="0" width="100%">

   <tr bgcolor="#CCCCFF">
      <td><font size="+2"><b>Access Methods</b></font></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasInV1"><b>hasInRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-hasIn-v1A$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasInV2"><b>hasInRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-hasIn-v5$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="hasKeyIn"><b>hasKeyInRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-hasKeyIn-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOfV1"><b>iteratorOfRightRoles</b></a> ()
{<blockquote>$assoc-iteratorOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="iteratorOfV2"><b>iteratorOfRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-iteratorOf-v3$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="keysOf"><b>keysOfRightRoles</b></a> ()
{<blockquote>$assoc-keysOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public Iterator <a name="entriesOf"><b>entriesOfRightRoles</b></a> ()
{<blockquote>$assoc-entriesOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOfV1"><b>sizeOfRightRoles</b></a> ()
{<blockquote>$assoc-sizeOf-v1$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public int <a name="sizeOfV2"><b>sizeOfRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-sizeOf-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addToV1"><b>addToRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-addTo-v5$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="addToV2"><b>addToRightRoles</b></a> (Map.Entry entry)
{<blockquote>$assoc-addTo-v6$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFromV1"><b>removeFromRightRoles</b></a> (RightClass value)
{<blockquote>$assoc-removeFrom-v4$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeFromV2"><b>removeFromRightRoles</b></a> (KeyType key, RightClass value)
{<blockquote>$assoc-removeFrom-v5$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public boolean <a name="removeKeyFrom"><b>removeKeyFromRightRoles</b></a> (KeyType key)
{<blockquote>$assoc-removeKeyFrom-v2$</blockquote>}</code></pre></td>
   </tr>

   <tr>
      <td><pre><code>
public void <a name="removeAllFrom"><b>removeAllFromRightRoles</b></a> ()
{<blockquote>$assoc-removeAllFrom-v2$</blockquote>}</code></pre></td>
   </tr>

</table>

</body>
</html>
#EndCodeBlock


/*
 * $Log$
 * Revision 1.11  2006/03/03 17:30:10  mm
 * moved Templates folder into source folder
 *
 * Revision 1.9  2004/10/20 17:49:22  schneider
 * Introduction of interfaces for class diagram classes
 *
 * Revision 1.1.1.1  2004/09/20 09:12:50  cschneid
 * initial version
 *
 * Revision 1.8  2002/11/06 19:10:42  zuendorf
 * added some new buttons for convinience. Using HashSets instead of TreeSets as default now, in order to handle non comparable items. Anoying mouse-up-picture-down stopped. Some bug fixes to get rid of old access methods for associations and attributes. UMLObjects are no longer resizable / do no longer collabse on touch. XProM demo compiles and runs fine, now.
 *
 * Revision 1.7  2001/05/22 12:25:11  lowende
 * Documentation corrected.
 *
 * Revision 1.6  2000/08/18 12:28:44  wag25
 * Template supports ordered assocs.
 *
 * Revision 1.5  2000/07/11 12:28:15  wag25
 * File removed.
 *
 * Revision 1.4  2000/07/06 09:18:33  lowende
 * Some little changes.
 *
 * Revision 1.3  2000/06/09 12:07:25  lowende
 * Some little changes in assocs documentation.
 *
 * Revision 1.2  2000/06/08 16:55:14  lowende
 * Association documentation enhanced.
 *
 * Revision 1.1  2000/06/07 16:06:12  lowende
 * Assocs documentation tool added.
 *
 */


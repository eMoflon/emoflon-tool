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
package de.uni_paderborn.fujaba.basic;

import de.uni_paderborn.fujaba.metamodel.common.FElement;


/**
 * A runtime exception that has an element that can be shown to the user to display the error context.
 *
 * @author    christian.schneider@uni-kassel.de
 * @version   $Revision$ $Date$
 */
public class RuntimeExceptionWithContext extends RuntimeException
{
   /**
    * 
    */
   private static final long serialVersionUID = -5762323939997799733L;


   /**
    * Constructs a new runtime exception with <code>null</code> as its
    * detail message.  The cause is not initialized, and may subsequently be
    * initialized by a call to initCause().
    *
    * @param context  an element that can be shown to the user to display the error context
    */
   public RuntimeExceptionWithContext (FElement context)
   {
      this.context = context;
   }


   /**
    * Constructs a new runtime exception with the specified cause and a
    * detail message of <tt>(cause==null ? null : cause.toString())</tt>
    * (which typically contains the class and detail message of
    * <tt>cause</tt>).  This constructor is useful for runtime exceptions
    * that are little more than wrappers for other throwables.
    *
    * @param cause    the cause (which is saved for later retrieval by the
    *              {@link #getCause()} method).  (A <tt>null</tt> value is
    *              permitted, and indicates that the cause is nonexistent or
    *              unknown.)
    * @param context  an element that can be shown to the user to display the error context
    * @since          1.4
    */
   public RuntimeExceptionWithContext (Throwable cause, FElement context)
   {
      super (cause);
      this.context = context;
   }


   /**
    * Constructs a new runtime exception with the specified detail message.
    * The cause is not initialized, and may subsequently be initialized by a
    * call to initCause().
    *
    * @param message  the detail message. The detail message is saved for
    *                later retrieval by the {@link #getMessage()} method.
    * @param context  an element that can be shown to the user to display the error context
    */
   public RuntimeExceptionWithContext (String message, FElement context)
   {
      super (message);
      this.context = context;
   }


   /**
    * Constructs a new runtime exception with the specified detail message and
    * cause.  <p>Note that the detail message associated with
    * <code>cause</code> is <i>not</i> automatically incorporated in
    * this runtime exception's detail message.
    *
    * @param message  the detail message (which is saved for later retrieval
    *                by the {@link #getMessage()} method).
    * @param cause    the cause (which is saved for later retrieval by the
    *                {@link #getCause()} method).  (A <tt>null</tt> value is
    *                permitted, and indicates that the cause is nonexistent or
    *                unknown.)
    * @param context  an element that can be shown to the user to display the error context
    * @since          1.4
    */
   public RuntimeExceptionWithContext (String message, Throwable cause, FElement context)
   {
      super (message, cause);
      this.context = context;
   }


   /**
    * the element that can be shown to the user to display the error context
    */
   FElement context;


   /**
    * @return   the element that can be shown to the user to display the error context
    */
   public FElement getContext()
   {
      return context;
   }
}

/*
 * $Log$
 * Revision 1.3  2005/08/24 09:39:36  koenigs
 * Introduced new structure package for all Class Diagram related F-interfaces.
 * Introduced new common package for all remaining F-interfaces. [ak]
 *
 */

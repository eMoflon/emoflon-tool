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
 */
package de.fujaba.codegen;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;

public abstract class CodeGeneration
{
   private static CodeGeneration instance;

   /**
    * @return the last argument to {@link #set(CodeGeneration)}
    * @throws IllegalStateException if no code generation was registered, yet
    */
   public static CodeGeneration get() throws IllegalStateException
   {
      if (instance == null)
      {
         throw new IllegalStateException("No code generation was registered, yet!");
      }
      return instance;
   }

   protected CodeGeneration()
   {
   }

   public static void set(CodeGeneration codeGeneration)
   {
      Logger.getLogger(CodeGeneration.class).info("Changing code generation strategy to " + codeGeneration);
      instance = codeGeneration;
   }

   public StringBuffer generateFElement(FElement element)
   {
      return generateFElement(element, false);
   }

   public StringBuffer generateFElement(FElement elem, boolean save)
   {
      Map<String, Boolean> names = FujabaPreferencesManager
            .getSelectedCodeGenTargetNames(elem.getProject());
      if (names.isEmpty())
      {
         return new StringBuffer();
      }
      String targetName = names.keySet().iterator().next();
      return generateFElement(elem, save, targetName);
   }

   /**
    * Generates sourcecode of a given target for a given <code>FElement</code> with the possibility of saving it to
    * a file.
    *
    * @param element The model element for which source code will be generated.
    * @param save Defines whether the generated sourcecode has to be saved or not.
    * @param targetName name of the target (from preferences)
    * @return the generated sourcecode
    */
   public abstract StringBuffer generateFElement(FElement element, boolean save, String targetName);
   
   /**
    * @return Iterator of String keys identifying target languages registered by the codegen
    */
   public abstract Iterator<String> getCodegenTargets();
}

/*
 * $Log$
 */


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
package de.uni_paderborn.fujaba.parser;


import java.util.HashSet;

import de.uni_paderborn.fujaba.asg.ASTRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClassDiagram;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;


/**
 * Abstract super class for parsers connected to Fujaba
 *
 * <h2>Associations</h2>
 *
 * <pre>
 *         0..1      currentParser      0..1
 * Parser <---------------------------------- ParserManager
 *         currentParser      parserManager
 *
 *         0..n     parsers      0..1
 * Parser ---------------------------- ParserManager
 *         parsers      parserManager
 * </pre>
 *
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class Parser
{
   /**
    * Parses the given files.
    *
    *
    * @param project                 The project the ASG should be added to
    * @param files                   The files to be parsed
    * @param diagramPerPackage       true if a diagram should be created for each package parsed
    * @param incrementalParsing      true if the method bodies are to be parsed on demand
    * @param createActivityDiagrams  true, if activity diagrams should be created for all the parsed
    *           methods
    * @param classDiagram            a class diagram must be provided if diagramPerPackage is false
    */
   public abstract void parseFiles (FProject project, HashSet files,
                                    boolean diagramPerPackage, boolean incrementalParsing,
                                    boolean createActivityDiagrams, FClassDiagram classDiagram);


   /**
    * Parses the compilation unit and returns the abstract syntax graph (ASG) with an FFile as root.
    *
    *
    * @param project                 The project the ASG should be added to
    * @param filename                The name of the file to be parsed
    * @param diagramPerPackage       true if a diagram should be created for each package parsed
    * @param incrementalParsing      true if the method bodies are to be parsed on demand
    * @param createActivityDiagrams  true, if activity diagrams should be created for all the parsed
    *           methods
    * @param classDiagram            a class diagram must be provided if diagramPerPackage is false
    * @param reader                  An input reader
    * @return                        An FFile with the ASG of the parsed compilation unit
    */
   public abstract FFile parseCompilationUnit (FProject project,
                                               String filename, boolean diagramPerPackage,
                                               boolean incrementalParsing, boolean createActivityDiagrams,
                                               FClassDiagram classDiagram, java.io.Reader reader);


   /**
    * Parses the given method body and returns the root node of the abstract syntax tree.
    *
    *
    * @param fMethod  The method/constructor to be parsed
    * @return         The ASTRootNode
    */
   public abstract ASTRootNode parseMethodBody (FMethod fMethod);


   public abstract void addParserPostProcessor (
                                                ParserPostProcessor parserPostProcessor);


   public abstract void removeParserPostProcessor (
                                                   ParserPostProcessor parserPostProcessor);


   /**
    * <pre>
    *                0..1     parsers      0..n
    * ParserManager ---------------------------- Parser
    *                parserManager      parsers
    * </pre>
    */
   private ParserManager parserManager;


   public ParserManager getParserManager()
   {
      return this.parserManager;
   }


   public boolean setParserManager (ParserManager value)
   {
      boolean changed = false;
      if (this.parserManager != value)
      {
         if (this.parserManager != null)
         {
            ParserManager oldValue = this.parserManager;
            this.parserManager = null;
            oldValue.removeFromParsers (this);
         }
         this.parserManager = value;
         if (value != null)
         {
            value.addToParsers (this);
         }
         changed = true;
      }
      return changed;
   }


   public void removeYou()
   {
      ParserManager tmpParserManager = getParserManager();
      if (tmpParserManager != null)
      {
         setParserManager (null);
      }
   }

}

/*
 * $Log$
 * Revision 1.21  2006/10/30 14:21:06  lowende
 * Parser interface changed.
 * New method added to FMethod/UMLMethod to speed up parsing.
 * Version incremented.
 *
 */

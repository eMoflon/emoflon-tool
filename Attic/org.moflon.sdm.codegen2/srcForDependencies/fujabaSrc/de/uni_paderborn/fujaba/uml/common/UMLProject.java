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


import java.io.File;

import de.uni_kassel.coobra.Repository;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.asg.ASGProject;
import de.uni_paderborn.fujaba.asg.ASGUnparseInformation;
import de.uni_paderborn.fujaba.basic.TemplateManager;
import de.uni_paderborn.fujaba.metamodel.common.CodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FCommentary;
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FTag;
import de.uni_paderborn.fujaba.metamodel.structure.AccessStyle;
import de.uni_paderborn.fujaba.metamodel.structure.FAccessStyle;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.packagediagrams.PackagediagramsPackage;
import de.uni_paderborn.fujaba.uml.behavior.BehaviorPackage;
import de.uni_paderborn.fujaba.uml.factories.UMLGenericFactory;
import de.uni_paderborn.fujaba.uml.structure.StructurePackage;
import de.uni_paderborn.fujaba.uml.structure.UMLPackage;
import de.uni_paderborn.fujaba.versioning.Versioning;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLProject extends ASGProject
{

   protected UMLProject(Repository repository, boolean persistent)
   {
      super(repository, persistent);
   }


   /**
    * Create a UMLProject while loading. Needed for restore.
    *
    * @param repository that is loading the project.
    */
   protected UMLProject(Repository repository)
   {
      this(repository, true);
   }


   @Override
   protected void initOwnFactories()
   {
      super.initOwnFactories();

      // init own factories
      addToFactories(new UMLStereotypeFactory(this));
      addToFactories (new UMLGenericFactory<FCommentary>(this, FCommentary.class, UMLCommentary.class));
      addToFactories (new UMLGenericFactory<FTag>(this, FTag.class, UMLTag.class));
      addToFactories (new UMLGenericFactory<FConstraint>(this, FConstraint.class, UMLConstraint.class));
      addToFactories (new UMLGenericFactory<FFile>(this, FFile.class, UMLFile.class));
      StructurePackage.registerFactories(this);
      BehaviorPackage.registerFactories(this);
      PackagediagramsPackage.registerFactories(this);

      addToFactories(new UMLGenericFactory<ASGInformation>(this,
            ASGInformation.class, ASGInformation.class));
      addToFactories(new UMLGenericFactory<ASGUnparseInformation>(this,
            ASGUnparseInformation.class, ASGUnparseInformation.class));
      addToFactories(new UMLGenericFactory<FCodeStyle>(this,
            FCodeStyle.class, CodeStyle.class));
      addToFactories(new UMLGenericFactory<FAccessStyle>(this,
            FAccessStyle.class, AccessStyle.class));
   }
   

   /**
    * The used java-packages are saved in a package-tree. The root of this tree is the variable
    * this.rootPackage. The parameter fullName is the full name of the package, with dots as
    * separators. Attention: The name of a package is the simple name, the full name of a package is
    * build by searching the package-tree. So if you want to add a package, e.g. newPackge, with a
    * full-name, e.g. fullName, you have to do two things: first, leafe the name of the package
    * empty second, call the function this.addToPackages( newPackge,fullName ))
    *
    * <pre>
    *            -/-                     1
    * UMLProject <-----------------------> UMLPackage
    *            -/-           rootPackage
    * </pre>
    *
    * @see #getRootPackage
    */
   private UMLPackage rootPackage = null;


   public void setRootPackage(FPackage rootPackage)
   {
      UMLPackage oldValue = this.rootPackage;
      if ( rootPackage != oldValue ) {
         if (oldValue != null &&
               ( !Versioning.get().isInOperationalization( this )
                     || oldValue.sizeOfPackages() > 0
                     || oldValue.sizeOfDeclares() > 0) )
         {
            throw new IllegalStateException(
                  "The root package must not be changed!");
         }
         else if (rootPackage != null && !(rootPackage instanceof UMLPackage))
         {
            throw new IllegalArgumentException(
                  "The argument must be instance of UMLPackage!");
         }
         else
         {
            this.rootPackage = (UMLPackage) rootPackage;
            firePropertyChange(FProject.ROOT_PACKAGE_PROPERTY, null, rootPackage);
         }
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getRootPackage()
    */
   public UMLPackage getRootPackage()
   {
      return this.rootPackage;
   }


   // Only used for internal handling, don't save it.
   private transient File file = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getFile()
    */
   public File getFile()
   {
      return file;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#setFile(java.io.File)
    */
   public void setFile(File file)
   {
      if ((this.file == null && file != null)
            || (this.file != null && !this.file.equals(file)))
      {
         File oldValue = this.file;
         this.file = file;
         firePropertyChange(FILE_PROPERTY, oldValue, file);
      }
   }


   /**
    * Name of this project.
    *
    * <pre>
    *
    * Note:
    * Initially the name of this element is not set,
    * to give property listeners the chance to be notified,
    * if the name is set the first time.
    *
    * To give this project a name, the creator of this project
    * should ask the user via a gui, or assign a standard name,
    * after the project has been created.
    * </pre>
    */
   private String name = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
      if ((this.name == null && name != null)
            || (this.name != null && !this.name.equals(name)))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange(NAME_PROPERTY, oldValue, name);
      }
   }

   @Override
   public String toString()
   {
	   return this.getName();
   }

   private transient boolean saved = true;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#isSaved()
    */
   public boolean isSaved()
   {
      return saved;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#setSaved(boolean)
    */
   public void setSaved(boolean saved)
   {
      if (this.saved != saved)
      {
         boolean oldValue = this.saved;
         this.saved = saved;
         firePropertyChange(SAVED_PROPERTY, oldValue, saved);
      }
   }

   
   /**
    * Isolates the object so the garbage collector can remove it.
    *
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      if (this.rootPackage != null)
      {
         this.rootPackage.removeYou();
         this.rootPackage = null;
      }

      // FIXME: FPRCommon.resetClassInfos();
      // FIXME: FPRLoader.resetFieldWriteMethods();

      TemplateManager.get().resetTemplates();

      super.removeYou();
   }


   @Override
   protected boolean allowReuseOfFeatureAccessModule()
   {
      return true;
   }
}

/*
 * $Log$
 * Revision 1.51  2007/03/26 10:56:34  fklar
 * + activated template mechanism for automatic initialization of foreign factories and creation of initial products in ASGProject + automatic registration of new ASGProject implementation at ProjectManager
 *
 * Revision 1.50  2007/03/23 12:45:07  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.49  2007/03/21 17:47:09  fklar
 * + prepared template mechanism for automatic initialization of foreign factories and creation of initial products in ASGProject
 * + projects are added to the ProjectManager from within the project constructor instead of the project factory (may be moved to ASGProject)
 * + moved initialization of factories for a project and creation of initial products from project factories to a project's constructor
 * + project initializers may now initialize projects of type 'JarProject'
 * + UMLProject is not set to 'saved' in its constructor any longer
 *
 * Revision 1.48  2007/03/21 12:47:51  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.47  2007/03/15 16:07:09  weisemoeller
 * project specific export folder in the preferences dialog
 *
 * Revision 1.46  2007/03/12 09:26:39  cschneid
 * findByContextIdentifier gets type passed
 *
 * Revision 1.45  2007/02/20 09:24:34  cschneid
 * rolled back erroneously changed flag
 *
 * Revision 1.44  2007/02/16 14:30:07  cschneid
 * corrected fixes for EmptyFactory
 *
 * Revision 1.43  2007/02/15 10:00:30  cschneid
 * do not return null in gerFromFactories, load projects with same FeatureAccessModule to speed up loading many projects, new libs
 *
 * Revision 1.42  2007/02/09 15:29:30  cschneid
 * allow loading multiple projects in filechooser, prepend project dependencies instead of appending
 *
 * Revision 1.41  2007/01/09 09:31:35  cschneid
 * cut/copy/paste dummys, requiredPlugins saved correctly with ctr
 *
 * Revision 1.40  2006/08/17 08:57:59  zuendorf
 * Some enhancement of the Link Editor ported from Fujaba 4
 * Some utilities added for class retrival
 * Some virtual access methods added for convinience
 *
 * Revision 1.39  2006/05/24 08:44:44  cschneid
 * use Project factories in Versioning, selection manager can select not-displayed elements, Project tree displays packages, more dummy code for access sytles
 *
 * Revision 1.38  2006/05/09 13:18:56  cschneid
 * several versioning fixes
 *
 * Revision 1.37  2006/05/04 13:01:23  cschneid
 * dummy implementation for access style
 *
 * Revision 1.36  2006/04/25 11:58:25  cschneid
 * added deprecation expiration note, work on versioning
 *
 * Revision 1.35  2006/04/12 13:52:24  lowende
 * Project factories introduced.
 * Interface for plugins introduced to add factories to newly-created project factories.
 * Revision 1.34 2006/03/29 10:52:02 fklar initial name of project must not be set during
 * initialization, to give property listeners (like TreeNodeAdapters) the chance to react on the
 * first name-property-change: initial project name is now shown in the tree view
 * 
 * Revision 1.33 2006/03/28 18:52:51 fklar introduced constant 'ROOT_PACKAGE_PROPERTY:String' in
 * interface 'FProject'
 * 
 * Revision 1.32 2006/03/28 15:20:47 cschneid several fixes for storing/restoring
 * 
 * Revision 1.31 2006/03/24 19:09:57 rotschke Made all FUtilities static as discuss during FDD06
 * [tr].
 * 
 * Revision 1.30 2006/03/09 10:53:14 lowende Removed FProject.findType.
 * 
 */
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
package de.uni_paderborn.fujaba.project;


import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.beans.PropertyEditor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.ClassHandlerFactory;
import de.uni_kassel.features.CollectionFieldHandler;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.InvocationException;
import de.uni_kassel.features.PrimitiveClassHandler;
import de.uni_kassel.features.QualifiedFieldHandler;
import de.uni_kassel.features.reflect.DefaultClassHandlerFactory;
import de.uni_paderborn.fujaba.app.Version;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.basic.BasicIncrement;
import de.uni_paderborn.fujaba.basic.FD;
import de.uni_paderborn.fujaba.basic.KeyValuePair;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FClassDiagram;
import de.uni_paderborn.fujaba.metamodel.structure.FDeclaration;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.factories.UMLFlyweightFactory;
import de.uni_paderborn.fujaba.versioning.PathTranslator;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.uni_paderborn.lib.classloader.UPBClassLoader;
import de.upb.tools.fca.FCollections;
import de.upb.tools.fca.FDuplicatedTreeMap;
import de.upb.tools.fca.FHashMap;


/**
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 */
public class FPRLoader extends ProjectLoader
{

   private ClassHandler classHandlerFElement;

   private ClassHandler classHandlerFType;

   private ClassHandler classHandlerString;

   private ClassHandler classHandlerDimension;

   private ClassHandler classHandlerInsets;

   private ClassHandler classHandlerPropertyEditor;

   ClassHandler classHandlerUMLProject;

   ClassHandler classHandlerFProject;

   private ClassHandler classHandlerMethod;

   private int fileVersion;


   private void init()
   {
      DefaultClassHandlerFactory defaultClassHandlerFactory = new DefaultClassHandlerFactory(
            this.reflect);
      defaultClassHandlerFactory.setClassLoader(UPBClassLoader.get());
      this.reflect.setDefaultClassHandlerFactory(defaultClassHandlerFactory);
      try
      {
         this.classHandlerFElement = this.reflect
               .getClassHandler(FElement.class.getName());

         // todo: following handler are used for instanceof checks and should be replaced by
         // strategies/feature access
         this.classHandlerFType = this.reflect.getClassHandler(FType.class
               .getName());
         this.classHandlerString = this.reflect.getClassHandler(String.class
               .getName());
         this.classHandlerDimension = this.reflect
               .getClassHandler(Dimension.class.getName());
         this.classHandlerInsets = this.reflect.getClassHandler(Insets.class
               .getName());
         this.classHandlerPropertyEditor = this.reflect
               .getClassHandler(PropertyEditor.class.getName());
         this.classHandlerUMLProject = this.reflect
               .getClassHandler(UMLProject.class.getName());
         this.classHandlerFProject = this.reflect
               .getClassHandler(FProject.class.getName());
         this.classHandlerMethod = this.reflect
               .getClassHandler("java.lang.reflect.Method");
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException("ClassLoader problems?", e);
      }
   }

   final static transient Logger log = Logger.getLogger(FPRLoader.class);

   private int inconsistencyErrors = 0;

   private Map<String, ObjectInfo> objects = null;


   /**
    * @see de.uni_paderborn.fujaba.project.ProjectLoader#getTotalWork()
    */
   @Override
   public int getTotalWork()
   {
      return 100;
   }


   /**
    * Used to compute the progress.
    */
   private int totalWork;


   /**
    * Used to compute the progress.
    */
   private int currentProgress;


   /**
    * @see de.uni_paderborn.fujaba.project.ProjectLoader#load(java.io.InputStream, java.io.File,
    *      de.uni_paderborn.fujaba.project.ProgressHandler)
    */
   @Override
   protected final FProject load(InputStream stream, File file,
         ProgressHandler progress) throws IOException
   {
      this.erroneousFields.clear();

      ProjectManager projectManager = ProjectManager.get();
      setLoadingProject(projectManager
            .getFromProjectFactories(UMLProject.class).create());
      reflectFor(getLoadingProject());
      
      InputStream secondStream = null;
      try
      {
         secondStream = createNewInputStreamFromFile(file);
      }
      catch (Exception e1)
      {
         //do nothing
      }
      
      if(secondStream != null) //don't do this check, if we don't get another stream from the file
      {
         String projectId = getProjectIdFromInputStream(stream);
         if (projectId != null && isAlreadyLoaded(projectId))
         {
            FProject project = getLoadedProjectById(projectId);
            if(project.getFile() != null && file.compareTo(project.getFile()) == 0)
            {
               return project;
            }
            else if(project.getFile() == null)
            {
               // a project in memory that most probably is not persistent/has no repository
               // ignore this one
            }
            else
            {
               throw new LoadProjectException("Found two different versions of the project in " + file.toString() +
                     " \nand " + project.getFile().toString() + ". " +
                     "\nPlease close the opened project \""+ project.getName() + 
                     " \" first, before loading this project.", null, project.getName());
            }
         }
      }
      stream = secondStream;
      

      BufferedReader reader = new BufferedReader(new InputStreamReader(stream),
            32768);

      try
      {
         ObjectInfo currentInfo = null;
         String line = null;
         String className = null;
         int objectHashTableLength = 0;
         int lineNum = 0;

         Map<String, PluginData> plugins = new Hashtable<String, PluginData>();

         // Changes to the project after loading it should only be applied
         // to the user project, use this switch for this.
         StringTokenizer lineTok;
         boolean notYetWarned = true;

         String maxUniqueId = "0";

         if (log.isDebugEnabled())
         {
            log.debug("loading project from file " + file);
         }

         try
         {
            // check, if it is the correct FileFormat
            line = reader.readLine();
            lineNum++;
            if (!line.startsWith("# Fujaba-Project-File"))
            {
               throw new Exception("This is not a Fujaba project file");
            }

            // Now parse the rest of the file
            while (reader.ready())
            {
               lineNum++;
               line = reader.readLine();
               if (line == null)
               {
                  break;
               }

               lineTok = createLineTokenizer(line);

               // for temporary use only
               // the plug-in entries with old file format (line=$pluginName?pluginID#Major%Minor)
               // the plug-in entries with new file format
               // (line=$;counterID;pluginName;pluginID;Major;Minor)

               switch (line.charAt(0))
               {
                  case '#': // documentation
                     break;
                  case '$': // plug-ins
                     StringTokenizer token = createLineTokenizer(line);
                     String name = token.nextToken();
                     String pluginID = token.nextToken();
                     String majorV = token.nextToken();
                     String minorV = token.nextToken();
                     String revision = "0";

                     if (!UPBClassLoader.DEFAULT_CLASSLOADER.equals(pluginID))
                     {
                        if (token.hasMoreTokens())
                        {
                           revision = token.nextToken();
                        }
                        PluginData plugin = new PluginData(pluginID, name,
                              Integer.parseInt(majorV), Integer
                                    .parseInt(minorV), Integer
                                    .parseInt(revision));
                        plugin
                              .setClassHandlerFactory(createClassHandlerFactory(pluginID));
                        plugins.put(pluginID, plugin);
                     }
                     else
                     {
                        // which fujaba core...
                        Version version = Version.get();
                        int neededMajor = Integer.parseInt(majorV);
                        int neededMinor = Integer.parseInt(minorV);
                        int neededRev = 0;
                        if (token.hasMoreTokens())
                        {
                           neededRev = Integer.parseInt(token.nextToken());
                        }

                        this.fileVersion = neededMajor;
                        if (neededMajor > version.getMajor())
                        {
                           throw new RuntimeException("\nThe project file \""
                                 + file + "\" needs Fujaba core version: "
                                 + neededMajor + "." + neededMinor + "."
                                 + neededRev + "\n"
                                 + "Available Fujaba core version: "
                                 + version.getVersion() + ".\n");
                        }
                        else if (neededMajor == version.getMajor())
                        {
                           if (neededMinor > version.getMinor())
                           {
                              throw new RuntimeException(
                                    "\nThe project file \"" + file
                                          + "\" needs Fujaba core version: "
                                          + neededMajor + "." + neededMinor
                                          + "." + neededRev + "\n"
                                          + "Available Fujaba core version: "
                                          + version.getVersion() + ".\n");
                           }
                           else
                           {
                              // fine
                           }
                        }
                        else
                        {
                           // needMajor < version.getMajor()
                           if (neededMajor < 4)
                           {
                              throw new RuntimeException(
                                    "\nThe project file \""
                                          + file
                                          + "\" was written with Fujaba 3 or earlier."
                                          + "No import for this version is available.\n");
                           }
                           else
                           {
                              // importing from Fujaba 4, that's ok
                           }
                        }
                     }
                     break;
                  case '-': // file specific variables
                     String variable = lineTok.nextToken();
                     if (variable.compareTo("FileVersion") == 0)
                     {
                        Integer.parseInt(lineTok.nextToken());
                     }
                     if (variable.compareTo("HashTableLength") == 0)
                     {
                        objectHashTableLength = Integer.parseInt(lineTok
                              .nextToken());
                        this.objects = new FHashMap<String, ObjectInfo>(
                              objectHashTableLength * 2);

                        this.totalWork = objectHashTableLength * 6;
                        this.currentProgress = 0;

                        if (log.isDebugEnabled())
                        {
                           log.debug("Hashtable created with length = "
                                 + String.valueOf(objectHashTableLength * 2));
                        }
                     }
                     break;
                  case '+': // hashtable entry
                     if (this.objects == null)
                     {
                        throw new Exception(
                              "Hashtable entry found, but there is no hashtable created yet!");
                     }

                     // get id and compare to maxUniqueId
                     String id = lineTok.nextToken();
                     if (!id.endsWith("]") && lessUniqueId(maxUniqueId, id))
                     {
                        maxUniqueId = id;
                     }

                     // get class name
                     className = lineTok.nextToken();

                     String loaderID = null;
                     if (lineTok.hasMoreTokens())
                     {
                        loaderID = lineTok.nextToken();
                     }
                     else // if no class loader is defined use the default class loader
                     {
                        loaderID = UPBClassLoader.DEFAULT_CLASSLOADER;
                     }
                     
                     String factoryKey;
                     if (lineTok.hasMoreTokens())
                     {
                        factoryKey = lineTok.nextToken();
                     }
                     else
                     {
                        factoryKey = null;
                     }
                     try
                     {
                        ObjectInfo info;
                        if (UPBClassLoader.DEFAULT_CLASSLOADER.equals(loaderID))
                        {
                           final ClassHandler cls = this.reflect
                                 .getClassHandler(className);

                           info = new ObjectInfo(id, cls, factoryKey);

                        }
                        else
                        {
                           PluginData pluginData = plugins.get(loaderID);
                           pluginData.checkPluginVersion();
                           ClassHandler cls = pluginData
                                 .getClassHandlerFactory().getClassHandler(
                                       className);
                           this.reflect.setClassHandler(className, cls);
                           info = new ObjectInfo(id, cls, factoryKey);
                        }
                        this.objects.put(id, info);
                     }
                     catch (ClassNotFoundException e)
                     {
                        // ignore type list for import
                        if (!"de.uni_paderborn.fujaba.uml.UMLTypeList"
                              .equals(className))
                        {
                           // TODO: more sophisticated error handling
                           e.printStackTrace();
                        }
                     }
                     finally
                     {
                        this.currentProgress += 2;
                        computeProgress(progress);
                     }
                     break;
                  case '*': // object reference
                     // is this the very first time?

                     if (notYetWarned)
                     {
                        if (log.isDebugEnabled())
                        {
                           log.debug("Objects created, start filling: ... ");
                        }

                        // Adjust the uniqueId counter to avoid that already used
                        // numbers are not used even more.
                        if ( !maxUniqueId.contains("#") )
                        FPRCommon.setUniqueId(new StringBuffer(maxUniqueId));

                        notYetWarned = false;
                     }

                     currentInfo = getObjectInfo(lineTok.nextToken());
                     this.currentProgress += 1;
                     computeProgress(progress);
                     break;
                  case '~': // attribute reference

                     if (currentInfo == null)
                     {
                        // caused by a prior error - ignore line
                        // new Exception ("Attribute reference found in " + line + ",\nbut no
                        // current
                        // increment is set!").printStackTrace();
                     }
                     else
                     {
                        currentInfo.getAttributes().put(lineTok.nextToken(),
                              lineTok);
                     }

                     break;
                  case '?': //dependency to other project
                     handleDependency(line, file);
                     break;
               } // switch

            } // while fpr file has more lines

            // mark and refresh all increments
            if (log.isDebugEnabled())
            {
               log.debug("mark and refresh all increments");
            }

            //process project object first, otherwise dependencies cannot be correctly resolved
            ObjectInfo projectInfo = null;
            for(ObjectInfo info : this.objects.values())
            {
               if(info.getObject() instanceof FProject)
               {
                  readAttributes(info);
                  projectInfo = info;
                  break;
               }
            }
            
            this.inconsistencyErrors = 0;
            for (ObjectInfo info : this.objects.values())
            {
               this.currentProgress += 3;
               computeProgress(progress);
               if(info != projectInfo)
               {
                  readAttributes(info);
               }
            }

            for (ObjectInfo info : this.objects.values())
            {
               FElement object = info.getObject();
               if (object instanceof ASGElement)
               {
                  ASGElement tmpItem = (ASGElement) object;

                  if ((tmpItem instanceof FFile))
                  {
                     FFile tmpFile = (FFile) tmpItem;
                     if (tmpFile.sizeOfContains() == 0)
                     {
                        inconsistencyRemoveObject(tmpFile);
                     }
                  }
                  else if (tmpItem instanceof FParam)
                  {
                     FParam tmpParam = (FParam) tmpItem;
                     if ((tmpParam.getRevParam() == null))
                     {
                        inconsistencyRemoveObject(tmpParam);
                     }
                  }
                  /*
                   * Here check if there are irregular diagram elements and delete them. This should
                   * be replaced by an real consistency checker mechanism.
                   */
                  if ((tmpItem instanceof UMLObject)
                        || (tmpItem instanceof UMLLink)
                        || (tmpItem instanceof UMLTransition))
                  {
                     if (tmpItem.sizeOfDiagrams() == 0)
                     {
                        inconsistencyRemoveObject(tmpItem);
                     }

                     if (tmpItem instanceof UMLLink)
                     {
                        UMLLink tmpLink = (UMLLink) tmpItem;
                        if (tmpLink.getSource() == null
                              || tmpLink.getTarget() == null)
                        {
                           inconsistencyRemoveObject(tmpLink);
                        }
                     }
                  }
                  else if (tmpItem instanceof FGeneralization)
                  {
                     FGeneralization tmpGeneralisation = (FGeneralization) tmpItem;
                     if ((tmpGeneralisation.getSuperclass() == null)
                           || (tmpGeneralisation.getSubclass() == null))
                     {
                        inconsistencyRemoveObject(tmpGeneralisation);
                     }
                  }
                  else if (tmpItem instanceof FAttr)
                  {
                     FAttr tmpAttr = (FAttr) tmpItem;
                     if (tmpAttr.getParent() == null)
                     {
                        inconsistencyRemoveObject(tmpAttr);
                     }
                  }
                  else if (tmpItem instanceof FMethod)
                  {
                     FMethod tmpMethod = (FMethod) tmpItem;
                     if ((tmpMethod.getParent() == null))
                     {
                        inconsistencyRemoveObject(tmpMethod);
                     }
                  }
                  else if (tmpItem instanceof FClassDiagram)
                  {
                     FClassDiagram tmpDiagram = (FClassDiagram) tmpItem;
                     if (tmpDiagram.getProject() == null)
                     {
                        inconsistencyRemoveObject(tmpDiagram);
                     }
                  }
                  else if (tmpItem instanceof UMLStoryPattern)
                  {
                     UMLStoryPattern tmpPattern = (UMLStoryPattern) tmpItem;
                     if (tmpPattern.getRevStoryPattern() == null)
                     {
                        inconsistencyRemoveObject(tmpPattern);
                     }
                  }
               } // end of if ()
            }

            if (this.inconsistencyErrors > 0)
            {
               if (log.isDebugEnabled())
               {
                  log.debug(this.inconsistencyErrors
                        + " inconsistency errors found");
               }
            }

            if (log.isDebugEnabled())
            {
               log.debug("Loading done.");
            }
         }
         catch (Exception e)
         {
         	// catch all exceptions except of RequiredRepositoryMissingException
            if (e instanceof RequiredRepositoryMissingException)
            {
               throw (RequiredRepositoryMissingException) e;
            }
            else
            {
               e.printStackTrace();
               throw new RuntimeException("An error occured in line "
                     + Integer.toString(lineNum) + "=" + line
                     + "\nwhile reading the file " + file + "\nMessage: " + e.getMessage(), e);
            }
         }
         finally
         {
            try
            {
               reader.close();
            }
            catch (IOException e)
            {
               System.out.println("ERROR in line " + lineNum
                     + ": Perhaps class " + className
                     + " has no standard (parameterless) constructor?");
               e.printStackTrace();
               throw new RuntimeException(e.getMessage(), e);
            }
         }
         
         
         if (this.fileVersion == 4) // import from Fujaba 4
         {
            // correct changed orientation value
            for (ObjectInfo info : this.objects.values())
            {
               FElement element = info.getObject();
               if (element instanceof ASGInformation)
               {
                  ASGInformation information = (ASGInformation) element;
                  String orientation = information
                        .getFromInformation("orientation");
                  if (orientation != null)
                  {
                     orientation = String
                           .valueOf(Integer.parseInt(orientation) + 1);
                     information.addToInformation("orientation",
                           orientation);
                  }
               }
               // correct class visibility
               if (element instanceof FClass)
               {
                  // somehow classes get private when importing from Fujaba 4 - just set them to
                  // public
                  FClass cls = (FClass) element;
                  cls.setVisibility(FDeclaration.PUBLIC);
               }
            }
         }
         getLoadingProject().setSaved(Versioning.get().isEnabled());
         return getLoadingProject();
      }
      finally
      {
         this.objects = null;
      }
   } // loadProject


   @Override
   public long estimateProjectLoadSize(File file)
   {
      return file.length();
   }


   private void computeProgress(ProgressHandler progressHandler)
   {
      if (progressHandler != null)
      {
         final int progress = this.totalWork > 0 ? (this.currentProgress * 100 / this.totalWork)
               : 100;
         final int workUnits = progress - progressHandler.getWorked();
         if (workUnits > 0)
         {
            progressHandler.worked(workUnits);
         }
      }
   }


   private ClassHandlerFactory createClassHandlerFactory(String pluginID)
   {
      PersistencySupport persistencySupport = ProjectLoader
            .getPersistencySupport();
      ClassLoader classLoader = persistencySupport.getClassLoader(pluginID);
      DefaultClassHandlerFactory pluginFactory = new DefaultClassHandlerFactory(
            this.reflect);
      pluginFactory.setClassLoader(classLoader);
      return pluginFactory;
   }


   private StringTokenizer createLineTokenizer(String line)
   {
      StringTokenizer st = new StringTokenizer(line, ";");

      st.nextToken();

      return st;
   }


   private boolean lessUniqueId(String first, String second)
   {
      if (first.length() < second.length())
      {
         return true;
      }

      if (first.length() > second.length())
      {
         return false;
      }

      return first.compareTo(second) < 0;
   }


   private void inconsistencyRemoveObject(FElement incr)
   {
      incr.removeYou();
      this.inconsistencyErrors++;
   }

   private FeatureAccessModule reflect;


   private void reflectFor(FProject project)
   {
      if (project.getRepository() != null)
      {
         this.reflect = project.getRepository().getFeatureAccessModule();
      }
      else
      {
    	 if (reflect == null)
    	 {
           this.reflect = Versioning.get().createFeatureAccessModule();
    	 }
      }
      init();
   }

   private Set<String> erroneousFields = new HashSet<String>();


   /**
    * read attributes for current class instance from a fpr-file
    */
   public void readAttributes(ObjectInfo info)
   {
      FElement basicIncrement = info.getObject();
      if (basicIncrement != null)
      {
         FDuplicatedTreeMap attributes = info.getAttributes();
         ClassHandler cls;
         try
         {
            cls = info.getClassHandler().getFeatureAccessModule()
                  .getClassHandler(basicIncrement);
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException("Classloader problems?", e);
         }
         // log.info( "classFIelds: " + classFields);

         // now store the fields into the string buffer


         for (Iterator it = attributes.keySet().iterator(); it.hasNext();)
         {
            String qualifiedFieldName = (String) it.next();
            int indexOfColons = qualifiedFieldName.indexOf("::");
            String unqualifiedFieldName = indexOfColons >= 0 ? qualifiedFieldName
                  .substring(indexOfColons + 2, qualifiedFieldName.length())
                  : qualifiedFieldName;
            try
            {
               FieldHandler currentField = null;
               try
               {
                  if (unqualifiedFieldName.startsWith("uml"))
                  {
                     String unqualifiedFieldNameWOUml = Character
                           .toLowerCase(unqualifiedFieldName.charAt(3))
                           + unqualifiedFieldName.substring(4);
                     currentField = cls.getField(unqualifiedFieldNameWOUml);
                  }
                  else if (unqualifiedFieldName.startsWith("java"))
                  {
                     String unqualifiedFieldNameWOJava = Character
                           .toLowerCase(unqualifiedFieldName.charAt(4))
                           + unqualifiedFieldName.substring(5);
                     currentField = cls.getField(unqualifiedFieldNameWOJava);
                  }
                  else
                  {
                     currentField = cls.getField(unqualifiedFieldName);
                  }
               }
               catch (NoSuchFieldException e)
               {
                  currentField = cls.getField(unqualifiedFieldName);
               }
               if (!currentField.isReadOnly())
               {
                  callReadFromStringBuffer(basicIncrement, currentField,
                        attributes, qualifiedFieldName);
               }
               else if (!alreadyErroneousField(qualifiedFieldName))
               {
                  log.warn("Field is read only: " + qualifiedFieldName + " ("
                        + unqualifiedFieldName + ")");
               }
            }
            catch (NoSuchFieldException e)
            {
               if (!alreadyErroneousField(qualifiedFieldName))
               {
                  log.warn("Field not found while loading: "
                        + qualifiedFieldName + " (" + unqualifiedFieldName
                        + ")", e);
               }
            }
            catch (SecurityException accessExcept)
            {
               log.info("Security Exception at writing attributes in "
                     + basicIncrement.getClass().getName()
                     + ". Please check Security Manager.", accessExcept);
               accessExcept.printStackTrace();
            }
            catch (NullPointerException nullExcept)
            {
               // ignore these (null) attributes and skip to the next.
               nullExcept.printStackTrace();
            }
            catch (Exception e)
            {
               if (log.isEnabledFor(Level.WARN)
                     && !alreadyErroneousField(qualifiedFieldName))
               {
                  log.warn("Failed to set/read value for field "
                        + qualifiedFieldName, e);
               }
            }
         }
         if (basicIncrement instanceof FAttr)
         {
            FAttr attr = (FAttr) basicIncrement;
            final FType attrType = attr.getAttrType();
            attr.setAttrType((FType) read(FAttr.ATTR_TYPE_PROPERTY, attrType, attributes));
         }
         else if (basicIncrement instanceof FPackage)
         {
            // BUGFIX: there are old fpr-files which have saved the packages
            // in the wrong order
            FPackage pack = (FPackage) basicIncrement;
            Object obj = read("parent", null, attributes);
            if (obj != null)
            {
               pack.setParent((FPackage) obj);
            }
            
            //No package but the root should have no parent package.
            //There are older files with unnamed packages that cannot be seen 
            //as long as they are not somewhere under the root.
            if(pack.getParent() == null && pack != pack.getProject().getRootPackage())
            {
               pack.setParent(pack.getProject().getRootPackage());
            }
         }
      }
   }


   private boolean alreadyErroneousField(String qualifiedFieldName)
   {
      return !this.erroneousFields.add(qualifiedFieldName);
   }


   private void callReadFromStringBuffer(FElement basicIncrement,
         FieldHandler currentField, FDuplicatedTreeMap attributes,
         String fieldKey)
   {
      // let's take care of what type of Object we have got.
      if (currentField instanceof CollectionFieldHandler
            || currentField instanceof QualifiedFieldHandler)
      {
         // we have to handle a container
         putElements(basicIncrement, currentField, attributes, fieldKey);
      }
      else
      {
         try
         {
            // we only have to set a single value
            setValue(basicIncrement, currentField, attributes, fieldKey);
         }
         catch (InvocationException e)
         {
            if (!alreadyErroneousField(fieldKey))
            {
               log.warn("Setting field threw exception: " + currentField, e);
               if (!(e.getCause() instanceof UnsupportedOperationException))
               {
                  e.getCause().printStackTrace();
               }
            }
         }
         catch (Exception e)
         {
            if (log.isEnabledFor(Level.WARN))
            {
               if (!alreadyErroneousField(fieldKey))
               {
                  log.warn(
                        "<FElement::callReadFromStringBuffer> Error reading/setting field: "
                              + currentField, e);
               }
            }
         }

      }
   }


   private void setValue(FElement basicIncrement, FieldHandler currentField,
         FDuplicatedTreeMap attributes, String fieldKey)
   {
      Object fieldValue = null;

      // retrieve the data which is to be set, we need the isAssignableFrom
      // to take care of the null-Fields and the instanceof to take
      // care of the interface-fields.
      if (this.classHandlerFElement.isAssignableFrom(currentField.getType())
            || this.classHandlerFType.isAssignableFrom(currentField.getType()))
      {
         fieldValue = read(fieldKey, null, attributes);
      }
      else if (this.classHandlerString.isAssignableFrom(currentField.getType()))
      {
         fieldValue = readString(fieldKey, null, attributes);
      }
      else if (currentField.getType() instanceof PrimitiveClassHandler)
      {
         StringTokenizer data = (StringTokenizer) attributes.get(fieldKey);
         if (data.hasMoreTokens())
         {
            fieldValue = currentField.getType().deserialize(data.nextToken());
         }
         else
         {
            fieldValue = currentField.getType().deserialize("0");
         }
      }
      else if (this.classHandlerDimension.isAssignableFrom(currentField
            .getType()))
      {
         int sizeW = read(fieldKey + "Width", 0, attributes);
         int sizeH = read(fieldKey + "Height", 0, attributes);
         fieldValue = new Dimension(sizeW, sizeH);
         if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
         {
            log.debug("newDimension=" + fieldValue);
         }
      }
      else if (this.classHandlerInsets.isAssignableFrom(currentField.getType()))
      {
         int top = read(fieldKey + "Top", 0, attributes);
         int bottom = read(fieldKey + "Bottom", 0, attributes);
         int left = read(fieldKey + "Left", 0, attributes);
         int right = read(fieldKey + "Right", 0, attributes);

         fieldValue = new Insets(top, left, bottom, right);
      }
      // try to read PropertyEditor Interface
      else if (this.classHandlerPropertyEditor.isAssignableFrom(currentField
            .getType()))
      {
         // found PropertyEditor Interface, try to read value and class
         String value = readString(fieldKey + "Value", "", attributes);
         String theClazzName = readString(fieldKey + "Class", null, attributes);
         PropertyEditor newEditor;
         ClassHandler theClazz;

         if (theClazzName != null)
         {
            try
            {
               theClazz = currentField.getClassHandler()
                     .getFeatureAccessModule().getClassHandler(theClazzName);
               newEditor = (PropertyEditor) theClazz.newInstance();
            }
            catch (Exception exception)
            {
               if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
               {
                  log.debug(basicIncrement
                        + " not able to create an instance of " + theClazzName);
                  log.debug(exception.getMessage());
                  log.debug(exception.toString());
               }
               newEditor = null;
            }

            if (newEditor != null && value != null)
            {
               newEditor.setAsText(value);
            }

            if (newEditor != null)
            {
               fieldValue = newEditor;
               if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
               {
                  log.debug("PropertyEditor.reading " + fieldValue);
               }
            }
         }
      }

      //
      // read java.lang.reflect.Method
      //
      else if (this.classHandlerMethod.isAssignableFrom(currentField.getType()))
      {
         throw new UnsupportedOperationException(
               "loading java.reflect.Method is not supported any more");
      }
      else
      {
         if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
         {
            log.debug("Attrib not assigned: " + fieldKey + " ... "
                  + basicIncrement.getClass());
         }
      }

      currentField.alter(basicIncrement, null, null, fieldValue);
   }


   private Object callread(StringTokenizer myToken, FieldHandler currentField)
   {
      if (currentField instanceof CollectionFieldHandler)
      {
         return (readCollection(myToken));
      }
      else if (currentField instanceof QualifiedFieldHandler)

      {
         KeyValuePair keyValuePair = readMap(myToken);
         if (keyValuePair != null)
         {
            return keyValuePair;
         }
      }

      return null;
   }


   /**
    * insert an element from the tokenizer in the given fieldObject
    * 
    * @param basicIncrement
    * @param currentField No description provided
    * @param attributes No description provided
    * @param fieldKey
    */
   private void putElements(FElement basicIncrement, FieldHandler currentField,
         FDuplicatedTreeMap attributes, String fieldKey)
   {
      Iterator iter = FCollections.iterator(attributes.values(fieldKey));

      while (iter.hasNext())
      {
         Object fieldValue = callread((StringTokenizer) iter.next(),
               currentField);
         if (fieldValue instanceof Map.Entry)
         {
            Map.Entry entry = (Map.Entry) fieldValue;
            currentField.alter(basicIncrement, entry.getKey(), null, entry
                  .getValue());
         }
         else
         {
            currentField.alter(basicIncrement, null, null, fieldValue);
         }
      }
   }


   private String restoreEscapeSequences(String strg)
   {
      return strg.replace('\1', '\n').replace('\2', ';');
   }


   private String readString(String decl, String defaultValue,
         FDuplicatedTreeMap attributes)
   {
      StringTokenizer lineTok = (StringTokenizer) attributes.get(decl);
      if (lineTok != null)
      {
         String strg = "";
         if (lineTok.hasMoreTokens())
         {
            strg += lineTok.nextToken();
         }
         return restoreEscapeSequences(strg); // trinet
      }
      return defaultValue;
   }


   private int read(String decl, int defaultValue, FDuplicatedTreeMap attributes)
   {
      StringTokenizer lineTok = (StringTokenizer) attributes.get(decl);
      if (lineTok != null)
      {
         return Integer.parseInt(lineTok.nextToken());
      }
      return defaultValue;
   }


   private FElement read(String decl, FElement defaultValue,
         FDuplicatedTreeMap attributes)
   {
      StringTokenizer lineTok = (StringTokenizer) attributes.get(decl);
      if (lineTok != null && lineTok.hasMoreElements())
      {
         String strg = lineTok.nextToken();
         if (strg.compareTo("null") == 0)
         {
            return null;
         }
         else
         {
            return getObjectFromID(strg);
         }
      }
      return defaultValue;
   }

   private class ObjectInfo
   {
      private final String id;

      private final ClassHandler classHandler;

      private String factoryKey;

      private FElement object;

      private FDuplicatedTreeMap attributes = new FDuplicatedTreeMap();


      ObjectInfo(String id, ClassHandler cls)
      {
         if (cls == null)
         {
            throw new NullPointerException();
         }
         this.id = id;
         this.classHandler = cls;
      }


      ObjectInfo(String id, ClassHandler cls, String factoryKey)
      {
         if (cls == null)
         {
            throw new NullPointerException();
         }
         this.id = id;
         this.classHandler = cls;
         this.factoryKey = factoryKey;
      }


      @SuppressWarnings( { "deprecation" })
      public ObjectInfo(FElement object)
      {
         if (object == null)
         {
            throw new NullPointerException();
         }
         this.id = object.getID();
         this.classHandler = null;
         this.object = object;
      }


      public FDuplicatedTreeMap getAttributes()
      {
         return this.attributes;
      }


      public FElement getObject()
      {
         if (this.object == null)
         {
            if (FPRLoader.this.classHandlerFProject
                  .isAssignableFrom(this.classHandler))
            {
               if (!FPRLoader.this.classHandlerUMLProject
                     .isAssignableFrom(this.classHandler))
               {
                  throw new UnsupportedOperationException(
                        "Loading of non-UML Project is not supported by FPR import!");
               }
               this.object = getLoadingProject();
            }
            else
            {
               try
               {

                  FFactory factory = getLoadingProject().getFromFactories(
                        this.classHandler.getName());
                  if (factory != null)
                  {
                     if (this.factoryKey != null)
                     {
                        this.object = factory.getFromProducts(this.factoryKey);
                     }
                     else
                     {
                        if (!(factory instanceof UMLFlyweightFactory))
                        {
                           this.object = factory.create();
                        }
                        else
                        {
                           // for conversion of old projects
                           String key = guessAndRemoveFactoryKeyAttribute();
                           if (key == null && log.isEnabledFor(Level.WARN))
                           {
                              log.warn("creating object of type "
                                    + this.classHandler.getName()
                                    + " with null key!");
                           }
                           this.object = factory.getFromProducts(key);
                        }
                     }
                  }
                  else
                  {
                     if (log.isDebugEnabled())
                     {
                        log
                              .debug("no factory was found to create object of type "
                                    + this.classHandler.getName() + ".");
                     }
                     this.object = (FElement) this.classHandler.newInstance();
                  }
               }
               catch (InstantiationException e)
               {
                  throw new RuntimeException("Failed to created instance of "
                        + this.classHandler, e);
               }
               catch (IllegalAccessException e)
               {
                  throw new RuntimeException("Failed to created instance of "
                        + this.classHandler, e);
               }
               catch (NoSuchMethodException e)
               {
                  throw new RuntimeException("Failed to created instance of "
                        + this.classHandler, e);
               }
            }
            if (this.object instanceof BasicIncrement)
            {
               FPRCommon.setID((BasicIncrement) this.object, this.id);
            }
         }
         return this.object;
      }


      private String guessAndRemoveFactoryKeyAttribute()
      {
         for (Iterator it = this.attributes.keySet().iterator(); it.hasNext();)
         {
            String attr = (String) it.next();
            if (attr.endsWith("::name") || attr.endsWith("::cardString")
                  || attr.endsWith("::text"))
            {
               Object value = this.attributes.remove(attr);
               if (value != null)
               {
                  StringTokenizer tokens = (StringTokenizer) ((List) value)
                        .get(0);
                  return tokens.hasMoreTokens() ? tokens.nextToken() : null;
               }
               else
               {
                  return null;
               }
            }
         }
         return null;
      }


      protected ClassHandler getClassHandler()
      {
         return this.classHandler;
      }

   }


   private ObjectInfo getObjectInfo(String id)
   {
      return this.objects.get(id);
   }


   private FElement getObjectFromID(String id)
   {
      if (id != null)
      {
         FPRLoader.ObjectInfo objectInfo = getObjectInfo(id);
         if(objectInfo == null)
         {
            BasicIncrement o = FPRCommon.getObject(id);
            if(o instanceof FElement)
            {
               return (FElement) o;
            }
            else
            {
               return null;
            }
         }
         else
         {
            return objectInfo.getObject();
         }
      }
      else
      {
         return null;
      }
   }


   private Object readCollection(StringTokenizer lineTok)
   {
      if (lineTok != null)
      {
         String strg = "null";
         Object value;

         try
         {
            if (lineTok.hasMoreElements())
            {
               strg = lineTok.nextToken();
            }
         }
         catch (NoSuchElementException e)
         {
            // Don't know why this exception is sometimes thrown, but it seems
            // that it can be ignored.
            e.printStackTrace();
            return null;
         }

         if (strg.equals("null"))
         {
            return null;
         }

         // if a map changed to a set, the second value is the object-value
         if (lineTok.hasMoreElements())
         {
            strg = lineTok.nextToken();
         }

         if (strg.startsWith("java.lang.String"))
         {
            value = strg.substring(17);
            return value;
         }
         else if (strg.startsWith("java.lang.Character"))
         {
            value = Character.valueOf(strg.substring(20).charAt(0));
            return value;
         }
         else if (strg.startsWith("java.lang.Double"))
         {
            value = Double.valueOf(strg.substring(17));
            return value;
         }
         else if (strg.startsWith("java.lang.Float"))
         {
            value = Float.valueOf(strg.substring(16));
            return value;
         }
         else if (strg.startsWith("java.lang.Long"))
         {
            value = Long.valueOf(strg.substring(15));
            return value;
         }
         else if (strg.startsWith("java.lang.Integer"))
         {
            value = Integer.valueOf(strg.substring(18));
            return value;
         }
         else if (strg.startsWith("java.lang.Short"))
         {
            value = Short.valueOf(strg.substring(16));
            return value;
         }
         else if (strg.startsWith("java.lang.Byte"))
         {
            value = Byte.valueOf(strg.substring(15));
            return value;
         }
         else if (strg.startsWith("java.lang.Boolean"))
         {
            value = Boolean.valueOf(strg.substring(18));
            return value;
         }
         else if (strg.startsWith("java.awt.geom.Point2D$Double"))
         {
            int first = strg.indexOf("[");
            int last = strg.lastIndexOf("]");
            Double xPoint = new Double(strg.substring(first + 1, strg
                  .indexOf(",")));
            Double yPoint = new Double(strg.substring(strg.indexOf(",") + 2,
                  last));

            return new Point2D.Double(xPoint, yPoint);
         }
         else if (strg.startsWith("java.util.Vector[Point2D.Double["))
         {
            Vector vec = new Vector();
            int pos = strg.indexOf("e[");
            while ((pos = strg.indexOf("[", pos)) != -1)
            {
               pos++;
               Double xPoint = new Double(strg.substring(pos, strg
                     .indexOf(",", pos)));
               Double yPoint = new Double(strg.substring(strg.indexOf(
                     ",", pos) + 2, strg.indexOf("]", pos)));
               vec.add(new Point2D.Double(xPoint, yPoint));
            } // while

            return vec;
         }
         else
         {
            return getObjectFromID(strg);
         }
      }
      return null;
   }


   private KeyValuePair readMap(StringTokenizer lineTok)
   {
      if (lineTok != null)
      {
         String key = "null";
         String value;

         if (lineTok.hasMoreTokens())
         {
            key = lineTok.nextToken();
         }
         else
         {
            log.info("key is missing. Set to null ");
         }

         if (lineTok.hasMoreTokens())
         {
            value = lineTok.nextToken();
         }
         else
         {
            value = key;
            key = null;
         }

         if ((key != null && key.equals("null")) || value.equals("null"))
         {
            return null;
         }
         if (getObjectFromID(key) != null)
         {
            return new KeyValuePair(getObjectFromID(key),
                  getObjectFromID(value));
         }
         else
         {
            if (getObjectFromID(value) != null)
            {
               return new KeyValuePair(key, getObjectFromID(value));
            }
            else if (value.startsWith("point"))
            {
               int commaIndex = value.indexOf(',');
               if (commaIndex >= 0)
               {
                  String xString = value.substring(5, commaIndex);
                  String yString = value.substring(commaIndex + 1, value
                        .length());
                  Point point = new Point(Integer.parseInt(xString), Integer
                        .parseInt(yString));
                  return new KeyValuePair(key, point);
               }
            }
            else if (value.startsWith("string"))
            {
               String string = value.substring(6, value.length());
               return new KeyValuePair(key, restoreEscapeSequences(string));
            }
            else if (value.startsWith("double"))
            {
               String string = value.substring(6, value.length());
               return new KeyValuePair(key, Double.parseDouble(string));
            }
            return new KeyValuePair(key, null);
         }
      }
      return null;
   }
   
   private void handleDependency(String line, File currentlyLoadingProjectFile)
   {
     String[] values = line.split(";");
     String projectId = values[1];
     String fileName = values[2];
     String projectName = values[3];
     
     if(isAlreadyLoaded(projectId))
     {
        //do not load projects, which are already loaded
        return;
     }
     
     FProject currentLoading = getLoadingProject(); //gets overwritten during loading of dependencies
     File baseFile = currentlyLoadingProjectFile.getParentFile();
     String absoluteFilePath = PathTranslator.translateToAbsolutePath(baseFile.toString(), fileName);
     File fileToRequiredProject = new File(absoluteFilePath);     
     if(fileToRequiredProject.exists())
     {
        getPersistencySupport().loadRequiredProjectOnDemand(projectName, absoluteFilePath, null);
     }
     else
     {
      // try it by eclipse workspace relative path 
        try
      {
         getPersistencySupport().loadRequiredProjectOnDemand(projectName, fileName, null);
      }
      catch (RequiredRepositoryMissingException e)
      {
         try
         {
            //try it in the same folder as the dependent project
            String nonRelativeFileName = new File(fileName).getName();
            String sameFolderAsDependentProject = PathTranslator.translateToAbsolutePath(baseFile.toString(), nonRelativeFileName);
            getPersistencySupport().loadRequiredProjectOnDemand(projectName, sameFolderAsDependentProject, null);
         }
         catch (RequiredRepositoryMissingException e2)
         {
            // throw the original exception again, since we couldn't fix the problem
            throw e;
         }
      }
     }
     
     ProjectManager.get().setLoading(true); //have to tell the manager that we are still loading 
     setLoadingProject(currentLoading);
   }
   
   private boolean isAlreadyLoaded(String id)
   {
      return getLoadedProjectById(id) != null;
   }
   
   /**
    * Searches in the context of already loaded project for the given id.
    * @param id
    * @return the corresponding project or null 
    */
   private FProject getLoadedProjectById(String id)
   {
      Iterator<? extends FProject> i = ProjectManager.get().iteratorOfProjects();
      while(i.hasNext())
      {
         FProject p = i.next();
         if(id.equals(p.getID()))
         {
            return p;
         }
      }
      
      return null;
   }
   
   /**
    * Iterates over the input stream and searches for a UMLProject id. <br>
    * Make sure, you do not reuse the given input stream e.g. to load the project.
    * Create a new one. 
    * @param input
    * @return the id of a UMLProject
    * @throws IOException
    */
   private String getProjectIdFromInputStream(InputStream input) throws IOException
   {
      InputStream inputStream;
      try
      {
         inputStream = new GZIPInputStream (input);
      }
      catch (IOException e)
      {
         inputStream = input;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 32768);
      String projectId = null, line = null;
      String projectType = "de.uni_paderborn.fujaba.uml.common.UMLProject";
      while (projectId == null && reader.ready())
      {
         line = reader.readLine();
         if (line.charAt(0) == '+')
         {
            String[] values = line.split(";");
            String id = values[1];
            String type = values[2];
            if (projectType.equals(type))
            {
               //found the project id
               projectId = id;
               break;
            }
         }
      }
      reader.close();
      return projectId;
   }

}

package de.uni_paderborn.fujaba.versioning;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.adapter.ChangeRecorder;
import de.uni_kassel.coobra.identifiers.DependencyHandler;
import de.uni_kassel.coobra.identifiers.IDManager;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.coobra.persistency.CachedPersistencyModule;
import de.uni_kassel.coobra.persistency.ConcatenatingPersistencyModule;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.server.ServerModule;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.scm.SCMServerModule;
import de.uni_kassel.coobra.server.scm.UnexpectedFileContentException;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.ClassHandlerFactory;
import de.uni_kassel.features.DefaultFactoryModule;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.ResolvingFactoryModule;
import de.uni_kassel.features.accessor.AccessorClassHandler;
import de.uni_kassel.features.accessor.DefaultPlainFieldHandler;
import de.uni_kassel.features.reflect.DefaultClassHandlerFactory;
import de.uni_kassel.util.ArrayIterator;
import de.uni_kassel.util.PropertyChangeSourceImpl;
import de.uni_paderborn.fujaba.app.FujabaApp;
import de.uni_paderborn.fujaba.app.Version;
import de.uni_paderborn.fujaba.app.action.OpenProjectAction;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGProject;
import de.uni_paderborn.fujaba.asg.ASTNode;
import de.uni_paderborn.fujaba.asg.ASTRootNode;
import de.uni_paderborn.fujaba.basic.UnifiedClassLoader;
import de.uni_paderborn.fujaba.metamodel.common.CodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.factories.FProjectFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.project.PersistencySupport;
import de.uni_paderborn.fujaba.project.ProjectLoader;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.uml.UMLPlugin;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.uni_paderborn.fujaba.uml.behavior.UMLCollabStat;
import de.uni_paderborn.fujaba.uml.behavior.UMLComplexState;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLLinkSet;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLNopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLObjectDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLPath;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatechart;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatement;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatementActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.uml.common.UMLCommentary;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLConstraint;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.uni_paderborn.fujaba.uml.common.UMLFile;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.common.UMLStereotype;
import de.uni_paderborn.fujaba.uml.structure.UMLArray;
import de.uni_paderborn.fujaba.uml.structure.UMLAssoc;
import de.uni_paderborn.fujaba.uml.structure.UMLAttr;
import de.uni_paderborn.fujaba.uml.structure.UMLBaseType;
import de.uni_paderborn.fujaba.uml.structure.UMLCardinality;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.uni_paderborn.fujaba.uml.structure.UMLClassDiagram;
import de.uni_paderborn.fujaba.uml.structure.UMLDeclaration;
import de.uni_paderborn.fujaba.uml.structure.UMLGeneralization;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;
import de.uni_paderborn.fujaba.uml.structure.UMLPackage;
import de.uni_paderborn.fujaba.uml.structure.UMLParam;
import de.uni_paderborn.fujaba.uml.structure.UMLQualifier;
import de.uni_paderborn.fujaba.uml.structure.UMLRole;
import de.uni_paderborn.fujaba.uml.structure.UMLType;
import de.upb.lib.plugins.PluginInterface;
import de.upb.lib.plugins.PluginManager;
import de.upb.lib.plugins.PluginProperty;
import de.upb.lib.userinterface.UserInterfaceManager;


/**
 * Singleton to handle all versioning matters.
 * 
 * @author christian.schneider@uni-kassel.de
 * @author Last Editor: $Author$
 * @version $Revision$ $Date$
 */
public class Versioning extends PropertyChangeSourceImpl
{
   public static final String PROJECT_ELEMENT_NAME = "project";

   private static final String PROJECTS_SUBFOLDER = "projects.workspace";

   private HashMap<String, ClassLoader> f4EClassLoaders = new HashMap<String, ClassLoader>();

   /**
    * logger.
    */
   final static transient Logger log = Logger.getLogger(Versioning.class);

   /**
    * only instance of this class
    */
   private static Versioning instance;

   /**
    * property name for firing events.
    */
   public static final String PROPERTY_NAME_LAST_TRANSACTION = "lastTransaction";

   /**
    * property name for firing events.
    */
   public static final String PROPERTY_NAME_NEXT_TRANSACTION = "nextTransaction";

   private static final String BACKUP_PREFIX = "backup of ";

   private static final String ORDER_FILE_NAME = "order";

   private static final String CLOSED_FILE_NAME = "closed";

   private FeatureAccessModule featureAccessModule;

   public static final String FUJABA_MODEL_NAME = "Fujaba"; // this must not be adapted to app name!

   static final int UNDO_CACHE_LENGTH = 10000;

   private static boolean checkedWorkspaceNotInUse;

   private static final String WORKSPACE_FILE_EXTENSION = UMLPlugin.CTR_FILE_FORMAT;


   /**
    * Singleton - hide ctor.
    */
   private Versioning()
   {
      checkWorkspaceNotInUse(null);
   }


   /**
    * Initialize Versioning with a custom projects workspace directory.
    * 
    * @param projectsDirectory where to put workspace data
    */
   public static void initialize(File projectsDirectory)
   {
      if (instance != null || Versioning.projectsDirectory != null)
      {
         throw new IllegalStateException("Versioning already initialized!");
      }
      try
      {
         Versioning.projectsDirectory = projectsDirectory.getCanonicalFile();
         get();
      }
      catch (RuntimeException e)
      {
         Versioning.projectsDirectory = null;
         throw e;
      }
      catch (IOException e)
      {
         Versioning.projectsDirectory = null;
         throw new RuntimeException(e);
      }
   }


   /**
    * @return only instance of Versioning (Singleton)
    */
   public static Versioning get()
   {
      if (instance == null)
      {
         instance = new Versioning();
      }
      return instance;
   }


   public FeatureAccessModule createFeatureAccessModule()
   {
      return createFeatureAccessModule(null);
   }


   public FeatureAccessModule createFeatureAccessModule(ClassLoader loader)
   {
      FeatureAccessModule module = new FujabaFeatureAccessModule();

      if (this.f4EClassLoaders != null)
      {
         registerAdditionalClassLoaders(module, f4EClassLoaders);
      }

      module.setDebuggingEnabled(log.isDebugEnabled());

      DefaultClassHandlerFactory classHandlerFactory = new DefaultClassHandlerFactory(
            module);
      if (loader != null)
      {
         classHandlerFactory.setClassLoader(loader);
      }
      else
      {
         classHandlerFactory.setClassLoader(UnifiedClassLoader.get());
      }
      module.setDefaultClassHandlerFactory(classHandlerFactory);

      try
      {
         // conversion from Fujaba 4
         module.setClassHandler("de.uni_paderborn.fujaba.uml.ASTNode",
               getHandler(module, ASTNode.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.ASTRootNode",
               getHandler(module, ASTRootNode.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLActivity",
               getHandler(module, UMLActivity.class));
         module.setClassHandler(
               "de.uni_paderborn.fujaba.uml.UMLActivityDiagram", getHandler(
                     module, UMLActivityDiagram.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLArray",
               getHandler(module, UMLArray.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLAssoc",
               getHandler(module, UMLAssoc.class));
         module.setClassHandler(
               "de.uni_paderborn.fujaba.uml.UMLAssocStereotype", getHandler(
                     module, UMLStereotype.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLAttr",
               getHandler(module, UMLAttr.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLAttrExprPair",
               getHandler(module, UMLAttrExprPair.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLBaseTypes",
               getHandler(module, UMLBaseType.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLCardinality",
               getHandler(module, UMLCardinality.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLClass",
               getHandler(module, UMLClass.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLClassDiagram",
               getHandler(module, UMLClassDiagram.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLCollabStat",
               getHandler(module, UMLCollabStat.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLCommentary",
               getHandler(module, UMLCommentary.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLComplexState",
               getHandler(module, UMLComplexState.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLConnection",
               getHandler(module, UMLConnection.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLConstraint",
               getHandler(module, UMLConstraint.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLDeclaration",
               getHandler(module, UMLDeclaration.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLDiagram",
               getHandler(module, UMLDiagram.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLDiagramItem",
               getHandler(module, UMLDiagramItem.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLFile",
               getHandler(module, UMLFile.class));
         module.setClassHandler(
               "de.uni_paderborn.fujaba.uml.UMLGeneralization", getHandler(
                     module, UMLGeneralization.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLIncrement",
               getHandler(module, UMLIncrement.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLLink",
               getHandler(module, UMLLink.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLLinkSet",
               getHandler(module, UMLLinkSet.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLMethod",
               getHandler(module, UMLMethod.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLMultiLink",
               getHandler(module, UMLMultiLink.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLNopActivity",
               getHandler(module, UMLNopActivity.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLObject",
               getHandler(module, UMLObject.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLObjectDiagram",
               getHandler(module, UMLObjectDiagram.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLPackage",
               getHandler(module, UMLPackage.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLParam",
               getHandler(module, UMLParam.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLPath",
               getHandler(module, UMLPath.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLProject",
               getHandler(module, UMLProject.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLQualifier",
               getHandler(module, UMLQualifier.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLRole",
               getHandler(module, UMLRole.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStartActivity",
               getHandler(module, UMLStartActivity.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStatechart",
               getHandler(module, UMLStatechart.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStatement",
               getHandler(module, UMLStatement.class));
         module.setClassHandler(
               "de.uni_paderborn.fujaba.uml.UMLStatementActivity", getHandler(
                     module, UMLStatementActivity.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStereotype",
               getHandler(module, UMLStereotype.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStopActivity",
               getHandler(module, UMLStopActivity.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStoryActivity",
               getHandler(module, UMLStoryActivity.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLStoryPattern",
               getHandler(module, UMLStoryPattern.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLTransition",
               getHandler(module, UMLTransition.class));
         module.setClassHandler(
               "de.uni_paderborn.fujaba.uml.UMLTransitionGuard", getHandler(
                     module, UMLTransitionGuard.class));
         module.setClassHandler("de.uni_paderborn.fujaba.uml.UMLType",
               getHandler(module, UMLType.class));

         // specify some transient / non-transient fields
         ClassHandler umlClassHandler = getHandler(module, UMLClass.class);
         // umlClassHandler.getField("umlAbstract").setTransient(true);
         umlClassHandler.getField("abstract").setTransient(false);

         ClassHandler umlMethodHandler = getHandler(module, UMLMethod.class);
         // umlMethodHandler.getField("umlAbstract").setTransient(true);
         umlMethodHandler.getField("abstract").setTransient(false);

         // umlMethodHandler.getField("umlStatic").setTransient(true);
         umlMethodHandler.getField("static").setTransient(false);

         ClassHandler umlRoleHandler = getHandler(module, UMLRole.class);
         // umlRoleHandler.getField("umlVisibility").setTransient(true);

         // create plain field handler for 'methodBody' attribute to avoid being detected as
         // qualified
         DefaultPlainFieldHandler methodBodyHandler = new DefaultPlainFieldHandler(
               (AccessorClassHandler) umlMethodHandler, "methodBody", null);
         methodBodyHandler.setTransient(false);
         umlMethodHandler.setFieldHandler("methodBody", methodBodyHandler);

         markFieldAsTransient(module, UMLQualifier.class,
               FQualifier.QUALIFIED_ROLE_PROPERTY);
         markFieldAsTransient(module, UMLRole.class, FRole.ASSOC_PROPERTY);

         // map accessStyle -> codeStyle
         ClassHandler asgElementHandler = getHandler(module, ASGElement.class);
         FieldHandler codeStyleHandler = asgElementHandler
               .getField("codeStyle");
         asgElementHandler.setFieldHandler("accessStyle", codeStyleHandler);
         umlClassHandler.setFieldHandler("accessStyle", codeStyleHandler);
         ClassHandler umlAttrHandler = getHandler(module, UMLAttr.class);
         umlAttrHandler.setFieldHandler("accessStyle", codeStyleHandler);
         ClassHandler umlPackageHandler = getHandler(module, UMLPackage.class);
         umlPackageHandler.setFieldHandler("accessStyle", codeStyleHandler);
         umlRoleHandler.setFieldHandler("accessStyle", codeStyleHandler);
         ClassHandler codeStyleClassHandler = getHandler(module,
               CodeStyle.class);
         module.setClassHandler(
               "de.uni_paderborn.fujaba.metamodel.structure.AccessStyle",
               codeStyleClassHandler);

         getHandler(module, UMLCollabStat.class).setFieldHandler(
               "diag",
               getHandler(module, FElement.class).getField(
                     FElement.DIAGRAMS_PROPERTY));

         getHandler(module, UMLComplexState.class).setFieldHandler(
               "story",
               getHandler(module, UMLComplexState.class).getField(
               		UMLComplexState.ACTIVITY_PROPERTY));
         try
         {
            module.setClassHandler(PluginProperty.class.getName(),
                  new PluginPropertyHandler(module));
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException("class loader problems?", e);
         }
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException("model changed?", e);
      }

      // FIXME !!! initialize the FeatureAbstractionAwarePlugin in a UI component, but not here!
      PluginManager pluginManager = FujabaApp.getPluginManager();
      if (pluginManager != null)
      {
         Iterator iter = pluginManager.iteratorOfProperties();
         while (iter.hasNext())
         {
            PluginProperty property = (PluginProperty) iter.next();
            PluginInterface plugin = property.getPlugin();
            if (plugin instanceof FeatureAbstractionAwarePlugin)
            {
               FeatureAbstractionAwarePlugin awarePlugin = (FeatureAbstractionAwarePlugin) plugin;
               awarePlugin.initializeFeatureAbstraction(module);
            }
         }
      }

      return module;
   }


   static void registerAdditionalClassLoaders(FeatureAccessModule module,
         Map<String, ClassLoader> classLoaders)
   {
      DefaultFactoryModule additionalClassLoadersModule = new FujabaFactoryModule();
      for (Map.Entry<String, ClassLoader> entry : classLoaders.entrySet())
      {
         DefaultClassHandlerFactory factory = new DefaultClassHandlerFactory(
               module);
         factory.setClassLoader(entry.getValue());
         additionalClassLoadersModule.setClassHandlerFactory(entry.getKey(),
               factory);
      }
      module.setDefaultFactoryModule(additionalClassLoadersModule);
   }


   public static ClassHandler getHandler(FeatureAccessModule module,
         Class<?> cls)
   {
      try
      {
         return module.getClassHandler(cls.getName());
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException("class loader problems?", e);
      }
   }


   private void markFieldAsTransient(FeatureAccessModule module, Class<?> cls,
         String field)
   {
      try
      {
         getHandler(module, cls).getField(field).setTransient(true);
      }
      catch (NoSuchFieldException e)
      {
         System.err
               .println("failed to set field to transient - field was not found:");
         e.printStackTrace();
      }
   }

   private static File projectsDirectory;


   public Repository setupRepositoryForNewProject(boolean useSCM,
         boolean reuseFeatureAccessModule)
   {
      if (isEnabled())
      {
         try
         {
            final File workspaceFile = createWorkspaceFile();
            final Repository repository = setupRepository(
                  reuseFeatureAccessModule, null);

            FilePersistencyModule filePersistencyModule = new FilePersistencyModule(
                  workspaceFile.getAbsolutePath());
            repository.setPersistencyModule(new CachedPersistencyModule(
                  filePersistencyModule, UNDO_CACHE_LENGTH));
            repository.getPersistencyModule().open(false);

            if (useSCM)
            {
               repository.setServerModule(new SCMServerModule(
                     filePersistencyModule));
            }

            VersioningWriter.writeHeader(filePersistencyModule, repository,
                  null);

            if (useSCM)
            {
               repository.setName(IdentifierModule.generateRandomPrefix(128));
            }

            return repository;
         }
         catch (IOException e)
         {
            throw new RuntimeException(
                  "Error creating project file in workspace", e);
         }
      }
      else
      {
         return null;
      }
   }


   Repository setupRepositoryForImport(final Repository importInto)
   {
      final ClassHandler asgProjectClassHandler = getHandler(importInto
            .getFeatureAccessModule(), ASGProject.class);
      Repository repository = new FujabaRepository()
      {
         @Override
         protected Object createObject(ClassHandler classHandler, Object key)
               throws PersistencyException
         {
            if (asgProjectClassHandler.isAssignableFrom(classHandler))
            {
               try
               {
                  return classHandler
                        .getConstructor(Repository.class.getName())
                        .newInstance(importInto);
               }
               catch (NoSuchMethodException e)
               {
                  throw new PersistencyException(e);
               }
            }
            else
            {
               return super.createObject(classHandler, key);
            }
         }
      };
      repository.setIdentifierModule(new IdentifierModule(repository,
            getIDManager(), IdentifierModule.generateRandomPrefix(30), false));
      return repository;
   }


   /**
    * @return new repository with configured modules (except persistency)
    * @param reuseFeatureAccessModule true to allow, false to create a new one
    */
   Repository setupRepository(boolean reuseFeatureAccessModule,
         PersistencySupport persistencySupport)
   {
      final Repository repository = new FujabaRepository();
      if (persistencySupport != null)
      {
         initRepository(repository, reuseFeatureAccessModule,
               persistencySupport.getClassLoaders());
      }
      else
      {
         initRepository(repository, reuseFeatureAccessModule, null);
      }
      return repository;
   }


   private void initRepository(Repository repository,
         boolean reuseFeatureAccessModule,
         HashMap<String, ClassLoader> additionalClassLoaders)
   {
      this.f4EClassLoaders = additionalClassLoaders;
      FeatureAccessModule accessModule;
      if (reuseFeatureAccessModule)
      {
         accessModule = getDefaultFeatureAccessModule();
      }
      else
      {
         accessModule = createFeatureAccessModule(null);
      }
      repository.setErrorHandlerModule(new VersioningErrorHandlerModule(
            repository));
      repository.setFeatureAccessModule(accessModule);
      repository.setDefaultChangeRecorder(new FujabaPropertyChangeRecorder(
            repository));
      repository.setIdentifierModule(new IdentifierModule(repository,
            getIDManager(), IdentifierModule.generateRandomPrefix(30), true));
   }


   public FeatureAccessModule getDefaultFeatureAccessModule()
   {
      FeatureAccessModule accessModule = featureAccessModule;
      if (accessModule == null)
      {
         accessModule = createFeatureAccessModule();
         featureAccessModule = accessModule;
      }
      return accessModule;
   }


   private IDManager getIDManager()
   {
      if (idManager == null)
      {
         idManager = new IDManager();
         idManager.setDependencyHandler(new DependencyHandler()
         {
            public Result introduceDependency(Repository dependentRepository,
                  Repository reqiredRepository)
            {
               final FProject dependentProject = (FProject) dependentRepository
                     .getIdentifierModule()
                     .getNamedObject(PROJECT_ELEMENT_NAME);
               final FProject reqiredProject = (FProject) reqiredRepository
                     .getIdentifierModule()
                     .getNamedObject(PROJECT_ELEMENT_NAME);
               if (dependentProject.hasInRequires(reqiredProject))
               {
                  return Result.ACCEPT;
               }
               else if (dependentProject.hasInRequiredBy(reqiredProject))
               {
                  return Result.IGNORE;
               }
               else
               {
                  return Result.ERROR;
               }
            }
         });
      }
      return idManager;
   }

   private static IDManager idManager;


   File createWorkspaceFile() throws IOException
   {
      final File projects = getProjectsDirectory();
      if (!projects.isDirectory())
      {
         if (!projects.mkdirs())
         {
            throw new RuntimeException("Failed to create projects folder: "
                  + projects.getAbsolutePath());
         }
      }
      File file = null;
      int i = 0;
      while (file == null || file.exists())
      {
         file = new File(projects, PROJECT_ELEMENT_NAME
               + (System.currentTimeMillis() + i) + "."
               + WORKSPACE_FILE_EXTENSION);
      }
      return file;
   }


   /**
    * List all project files that are in the projects workspace folder.
    * 
    * @return iterator through project files
    */
   public Iterator<File> iteratorOfWorkspaceFiles()
   {
      processPendingRename();
      final List<String> namesOrdered = new ArrayList<String>();
      File orderFile = new File(getProjectsDirectory(), ORDER_FILE_NAME);
      if (orderFile.exists())
      {
         try
         {
            BufferedReader in = new BufferedReader(new FileReader(orderFile));
            String line;
            while ((line = in.readLine()) != null)
            {
               namesOrdered.add(line);
            }
            in.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      File[] list = getProjectsDirectory().listFiles(new FilenameFilter()
      {
         public boolean accept(File dir, String name)
         {
            return name.startsWith(PROJECT_ELEMENT_NAME)
                  && name.endsWith("." + WORKSPACE_FILE_EXTENSION)
                  && !queuedRenamedFileNames.contains(name);
         }
      });

      // sort order, by time if not known
      if (namesOrdered.size() > 1)
      {
         Arrays.sort(list, new Comparator<File>()
         {
            public int compare(File o1, File o2)
            {
               int index1 = namesOrdered.indexOf(o1.getName());
               int index2 = namesOrdered.indexOf(o2.getName());
               if (index1 != index2 && index1 != -1 && index2 != -1)
                  return index1 - index2;
               if (index1 == -1 && index2 != -1)
                  return 1;
               if (index1 != -1 && index2 == -1)
                  return -1;
               int lenDiff = o1.getName().length() - o2.getName().length();
               if (lenDiff != 0)
                  return lenDiff;
               else
                  return o1.getName().compareTo(o2.getName());
            }
         });
      }
      return new ArrayIterator<File>(list);
   }


   /**
    * List all backup files that are in the projects workspace folder.
    * 
    * @return iterator through project files
    */
   public Iterator<File> iteratorOfWorkspaceBackupFiles()
   {
      processPendingRename();
      File[] list = getProjectsDirectory().listFiles(new FilenameFilter()
      {
         public boolean accept(File dir, String name)
         {
            return name.startsWith(BACKUP_PREFIX + PROJECT_ELEMENT_NAME)
                  && name.endsWith("." + WORKSPACE_FILE_EXTENSION);
         }
      });
      return new ArrayIterator<File>(list);
   }


   public void registerPersistentObject(FProject project, Object element,
         String factoryKey)
   {
      if (element == null)
      {
         throw new IllegalArgumentException("Element may not be null!");
      }
      if (isEnabled())
      {

         // exclude DOBS Objects from changes recognition
         if (!element.getClass().getName().startsWith("de.uni_paderborn.dobs"))
         {
            final FProject projectElement = element instanceof FProject ? (FProject) element
                  : null;

            if (project == null)
            {
               throw new IllegalArgumentException(
                     "Element must be part of a project!");
            }
            final Repository repository = project.getRepository();
            if (repository != null)
            {
               checkPluginUsage(project, element);
               if (!repository.isInOperationalization())
               {
                  ActionTransactionListener transactionListener = ActionTransactionListener
                        .get();
                  transactionListener.checkActiveTransaction(repository);
               }
               repository.getDefaultChangeRecorder().registerNewObject(element,
                     factoryKey);
               if (projectElement != null)
               {
                  final Object namedObject = repository.getIdentifierModule()
                        .getNamedObject(PROJECT_ELEMENT_NAME);
                  if (namedObject == null)
                  {
                     repository.getIdentifierModule().putNamedObject(
                           PROJECT_ELEMENT_NAME, projectElement);
                  }
                  else if (namedObject != projectElement)
                  {
                     throw new IllegalStateException(
                           "Only one project per repository allowed!");
                  }
               }
            }
            else
            {
               if (!(element instanceof FProject))
               {
                  // unversioned project
                  throw new IllegalStateException(
                        "No repository associated with project though repository should be activated!");
               }
            }
         }
      }
   }


   public void checkPluginUsage(FProject project, Object element)
   {

      ClassLoader classLoader = element.getClass().getClassLoader();

      if (ProjectLoader.getPersistencySupport()
            .isPluginClassLoader(classLoader))
      {
         // a plug-in is responsible for this object
         String loaderID = ProjectLoader.getPersistencySupport()
               .getClassLoaderKey(classLoader);
         PluginProperty plugin = ProjectLoader.getPersistencySupport()
               .getPluginProperty(loaderID);
         if (plugin != null)
         {
            project.addToRequiredPlugins(plugin);
         }
      }
   }


   /**
    * Query undo/redo/loading state for an element.
    * 
    * @param element FElement of interest
    * @return true if an undo, redo or loading operation is currently in progress regarding given
    *         element
    */
   public boolean isInOperationalization(FElement element)
   {
      if (ProjectManager.get().isLoading())
      {
         return true;
      }

      final Repository repository = element.getProject().getRepository();
      if (repository != null)
      {
         return repository.isInOperationalization();
      }
      else
      {
         return false;
      }
   }


   /**
    * Close a project.
    * 
    * @param project what to close.
    * @param renameWorkspaceFile true to rename workspace files (to disable automatic reopening of
    *           projects)
    */
   public void close(FProject project, boolean renameWorkspaceFile)
   {
      final Repository repository = project.getRepository();
      close(repository, renameWorkspaceFile);
   }


   /**
    * Close a repository that does not necessarily belong to a project.
    * 
    * @param repository what to close.
    * @param renameWorkspaceFile true to rename workspace files (to disable automatic reopening of
    *           projects)
    */
   public void close(Repository repository, boolean renameWorkspaceFile)
   {
      if (repository != null)
      {
         if (repository.getPersistencyModule() != null)
         {
            repository.getPersistencyModule().close();
         }
         if (renameWorkspaceFile)
         {
            File file;
            if ((file = getWorkspaceFile(repository)) != null)
            {
               renameWorkspaceFileToBackupFile(file);
            }
            else
            {
               log
                     .warn("unable to rename workspacefile: workspacefile not found");
            }
         }
         final ChangeRecorder propertyChangeRecorder = repository
               .getDefaultChangeRecorder();
         if (propertyChangeRecorder instanceof FujabaPropertyChangeRecorder)
         {
            ((FujabaPropertyChangeRecorder) propertyChangeRecorder).disable();
         }
         repository.getIdentifierModule().delete();
      }
   }


   File getOriginalFile(Repository repository)
   {
      if (repository != null)
      {
         PersistencyModule persistencyModule = repository
               .getPersistencyModule();
         if (persistencyModule != null)
         {
            if (persistencyModule instanceof ConcatenatingPersistencyModule)
            {
               ConcatenatingPersistencyModule concatenatingPersistencyModule = (ConcatenatingPersistencyModule) persistencyModule;
               PersistencyModule firstPersistencyModule = concatenatingPersistencyModule
                     .getFirstPersistencyModule();
               if (firstPersistencyModule instanceof FilePersistencyModule)
               {
                  FilePersistencyModule filePersistencyModule = (FilePersistencyModule) firstPersistencyModule;
                  if (!isInWorkspace(filePersistencyModule.getFile()))
                  {
                     return filePersistencyModule.getFile();
                  }
               }
            }
         }
      }
      return null;
   }


   File getWorkspaceFile(Repository repository)
   {
      if (repository == null)
      {
         return null;
      }
      final PersistencyModule persistencyModule = repository
            .getPersistencyModule();
      if (persistencyModule != null)
      {
         // TODO: tidy this up :/
         final FilePersistencyModule filePersistencyModule;
         if (persistencyModule instanceof FilePersistencyModule)
         {
            filePersistencyModule = (FilePersistencyModule) persistencyModule;
         }
         else if (persistencyModule instanceof ConcatenatingPersistencyModule)
         {
            PersistencyModule lastPersistencyModule = ((ConcatenatingPersistencyModule) persistencyModule)
                  .getLastPersistencyModule();
            if (lastPersistencyModule instanceof FilePersistencyModule)
            {
               filePersistencyModule = (FilePersistencyModule) lastPersistencyModule;
            }
            else if (lastPersistencyModule instanceof CachedPersistencyModule)
            {
               PersistencyModule delegate = ((CachedPersistencyModule) lastPersistencyModule)
                     .getDelegate();
               if (delegate instanceof FilePersistencyModule)
               {
                  filePersistencyModule = (FilePersistencyModule) delegate;
               }
               else
               {
                  filePersistencyModule = null;
               }
            }
            else
            {
               filePersistencyModule = null;
            }
         }
         else if (persistencyModule instanceof CachedPersistencyModule)
         {
            PersistencyModule delegate = ((CachedPersistencyModule) persistencyModule)
                  .getDelegate();
            if (delegate instanceof FilePersistencyModule)
            {
               filePersistencyModule = (FilePersistencyModule) delegate;
            }
            else
            {
               filePersistencyModule = null;
            }
         }
         else
         {
            filePersistencyModule = null;
         }
         if (filePersistencyModule != null)
         {
            return filePersistencyModule.getFile();
         }
      }
      return null;
   }


   public boolean renameWorkspaceFileToBackupFile(File file)
   {
      if (file.exists())
      {
         if (!file.renameTo(new File(file.getParentFile(), BACKUP_PREFIX
               + file.getName())))
         {
            queueRename(file);
            return false;
         }
      }
      return true;
   }

   private Set<String> queuedRenamedFileNames = new HashSet<String>();


   private synchronized void queueRename(File file)
   {
      if (queuedRenamedFileNames.add(file.getName()))
      {
         processPendingRename();
      }
   }


   private void writeQueuedRenamedFileNames()
   {
      File infoFile = new File(getProjectsDirectory(), CLOSED_FILE_NAME);
      try
      {
         FileWriter writer = new FileWriter(infoFile);
         try
         {
            for (String name : queuedRenamedFileNames)
            {
               writer.append(name).append("\n");
            }
         }
         finally
         {
            writer.close();
         }
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }


   private void processPendingRename()
   {
      File infoFile = new File(getProjectsDirectory(), CLOSED_FILE_NAME);
      if (infoFile.exists())
      {
         try
         {
            BufferedReader in = new BufferedReader(new FileReader(infoFile));
            String line;
            while ((line = in.readLine()) != null)
            {
               queuedRenamedFileNames.add(line);
            }
            in.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      for (Iterator<String> it = queuedRenamedFileNames.iterator(); it
            .hasNext();)
      {
         String name = it.next();
         if (renameWorkspaceFileToBackupFile(new File(getProjectsDirectory(),
               name)))
         {
            it.remove();
         }
      }
      if (queuedRenamedFileNames.size() > 0)
      {
         writeQueuedRenamedFileNames();
      }
      else
      {
         infoFile.delete();
      }
   }


   /**
    * @param repository repository of interest
    * @return true if the repository is stored in a file in the workspace directory
    */
   public boolean isInWorkspace(Repository repository)
   {
      final PersistencyModule persistencyModule = repository
            .getPersistencyModule();
      if (persistencyModule instanceof FilePersistencyModule)
      {
         final FilePersistencyModule filePersistencyModule = ((FilePersistencyModule) persistencyModule);
         final File file = filePersistencyModule.getFile();
         return isInWorkspace(file);
      }
      else
      {
         return false;
      }
   }


   /**
    * @param file file of inetrest
    * @return true if the file is in the workspace directory
    */
   public boolean isInWorkspace(final File file)
   {
      try
      {
         return file.getParentFile() != null
               && getProjectsDirectory().equals(
                     file.getParentFile().getCanonicalFile().getAbsoluteFile());
      }
      catch (IOException e)
      {
         return false;
      }
   }


   /**
    * @return directory for projects in workspace
    */
   public File getProjectsDirectory()
   {
      return locateProjectsDirectory();
   }


   private static File locateProjectsDirectory()
   {
      if (projectsDirectory == null)
      {
         // FIXME !!! Introduce an interface that determines the destination for backup files created by Versioning
         File workspace = new File(FujabaPreferencesManager.getPropertyDir());
         if (!workspace.isDirectory())
         {
            if (!workspace.mkdirs())
            {
               throw new RuntimeException("Failed to create workspace folder: "
                     + workspace.getAbsolutePath());
            }
         }
         File dir = new File(workspace, PROJECTS_SUBFOLDER);
         try
         {
            if (!dir.isDirectory())
               dir.mkdir();
            projectsDirectory = dir.getCanonicalFile();
         }
         catch (IOException e)
         {
            projectsDirectory = dir;
         }
      }
      return projectsDirectory;
   }


   /**
    * Start a composite transaction that has transactions for all open repositories.
    * 
    * @param name name of the new transaction(s)
    * @return the new composite transaction
    */
   public CompositeTransaction startTransaction(String name)
   {
      ActionTransactionListener.get().preActionNotify(name);
      return ActionTransactionListener.get().getActiveTransaction();
   }


   /**
    * getter for field lastTransaction
    * 
    * @return current value of field lastTransaction
    */
   public CompositeTransaction getLastTransaction()
   {
      return this.lastTransaction;
   }

   /**
    * store the value for field lastTransaction
    */
   private CompositeTransaction lastTransaction;


   /**
    * setter for field lastTransaction
    * 
    * @param value new value
    */
   void setLastTransaction(final CompositeTransaction value)
   {
      final CompositeTransaction oldValue = this.lastTransaction;
      if (oldValue != value)
      {
         this.lastTransaction = value;
         CompositeTransaction oldValueNext = this.nextTransaction;
         final CompositeTransaction newValueNext = value != null ? value
               .getNext() : oldValue;
         this.nextTransaction = newValueNext;
         if (newValueNext != null && newValueNext.getPrevious() != value)
         {
            throw new IllegalStateException(
                  "Inconsistent transaction list - setLastTransaction(null) should be called for undo only!");
         }
         firePropertyChange(PROPERTY_NAME_LAST_TRANSACTION, oldValue, value);
         firePropertyChange(PROPERTY_NAME_NEXT_TRANSACTION, oldValueNext,
               newValueNext);
      }
   }


   /**
    * getter for field nextTransaction
    * 
    * @return current value of field nextTransaction
    */
   public CompositeTransaction getNextTransaction()
   {
      return this.nextTransaction;
   }

   /**
    * store the value for field nextTransaction
    */
   private CompositeTransaction nextTransaction;


   /**
    * initialize versioning (only called once per Fujaba instance).
    */
   public void init()
   {
      UserInterfaceManager.get().addExecutionListener(
            ActionTransactionListener.get());
   }


   /**
    * Rollback all changes done on the current action and start inplace editing (no action).
    */
   public void abortCurrentAction()
   {
      ActionTransactionListener.get().postActionNotify(null, null);
      ActionTransactionListener.get().preActionNotify((ActionEvent) null);
   }


   public void startInplaceEditing()
   {
      if (ActionTransactionListener.get().getActiveTransaction() == null)
      {
         ActionTransactionListener.get().startInplaceEditing();
      }
   }


   public static FProject update(Repository repository)
   {
      ProjectManager.get().setLoading(true);
      try
      {
         repository.getServerModule().update();
      }
      finally
      {
         ProjectManager.get().setLoading(false);
      }
      FProject updatedProject = (FProject) repository.getIdentifierModule()
            .getNamedObject(PROJECT_ELEMENT_NAME);
      return updatedProject;
   }


   /**
    * @return true iff Versioning is enabled (projects have a {@link Repository})
    */
   public boolean isEnabled()
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
            FujabaCorePreferenceKeys.REPOSITORY_ACTIVATED);
   }


   /**
    * Called by the application when it is (re)activated (one of it's windows is focused). All
    * loaded project files should be reopened.
    * 
    * @see #applicationDeactivated()
    */
   public void applicationActivated()
   {
      for (Iterator<? extends FProject> it = ProjectManager.get()
            .iteratorOfProjects(); it.hasNext();)
      {
         FProject project = it.next();
         Repository repository = project.getRepository();

         if (repository != null)
         {
            ServerModule serverModule = repository.getServerModule();
            if (project.isSaved() && serverModule instanceof SCMServerModule)
            {
               try
               {
                  serverModule.update();
               }
               catch (UnexpectedFileContentException e)
               {
                  reload(project);
               }
               catch (PersistencyException e)
               {
                  e.printStackTrace();
               }
               catch (RemoteException e)
               {
                  e.printStackTrace();
               }
            }
         }
      }
   }


   private void reload(FProject project)
   {
      ConcatenatingPersistencyModule persistencyModule = (ConcatenatingPersistencyModule) project
            .getRepository().getPersistencyModule();
      FilePersistencyModule lastPersistencyModule = (FilePersistencyModule) persistencyModule
            .getLastPersistencyModule();
      persistencyModule.close();
      File workspaceFile = lastPersistencyModule.getFile();
      ProjectManager.get().closeProject(project, false);
      
      // FIXME !!! Remove dependency to OpenProjectAction
      new OpenProjectAction().open(workspaceFile);
   }


   /**
    * Called by the application when it is deactivated (all of it's windows are unfocused). Calling
    * this method causes all unmodified projects that are not associated to a repository server to
    * close their project files to allow file-based versioning (CVS or SVN).
    * 
    * @see #applicationActivated()
    */
   public void applicationDeactivated()
   {
      for (Iterator<? extends FProject> it = ProjectManager.get()
            .iteratorOfProjects(); it.hasNext();)
      {
         FProject project = it.next();
         Repository repository = project.getRepository();

         if (repository != null && !repository.isInOperationalization())
         {
            ServerModule serverModule = repository.getServerModule();
            if (project.isSaved() && serverModule instanceof SCMServerModule)
            {
               serverModule.checkin();
            }
         }
      }
   }


   public Repository createBareRepository(FProject project)
   {
      FujabaRepository repository = new FujabaRepository(project);
      if (project.getRepository() != null)
      {
         repository.setFeatureAccessModule(project.getRepository()
               .getFeatureAccessModule());
      }
      else
      {
         repository.setFeatureAccessModule(this.createFeatureAccessModule());
      }
      repository.setErrorHandlerModule(new VersioningErrorHandlerModule(
            repository));
      return repository;
   }


   public void changeWorkspaceFileLoadOrder(List<FProject> sortedProjects)
   {
      if (!isEnabled())
         return;
      File file = new File(getProjectsDirectory(), ORDER_FILE_NAME);
      try
      {
         FileWriter out = new FileWriter(file);
         for (FProject project : sortedProjects)
         {
            File workspaceFile = getWorkspaceFile(project.getRepository());
            if (workspaceFile != null)
            {
               out.write(workspaceFile.getName());
               out.write("\n");
            }
         }
         out.close();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }


   public boolean usingDefaultModule(Repository repository)
   {
      FProject project = getProject(repository);
      if (project != null)
      {
         return project.allowsReuseOfFeatureAccessModule();
      }
      return repository.getFeatureAccessModule() == featureAccessModule;
   }


   public FProject getProject(Repository repository)
   {
      FProject project;
      if (repository instanceof FujabaRepository)
      {
         FujabaRepository fujabaRepository = (FujabaRepository) repository;
         project = fujabaRepository.getProject();
      }
      else
      {
         project = null;
      }
      return project;
   }


   /**
    * Check that the workspace is not used by another Fujaba instance.
    * 
    * @param parentComponent parent component for error dialog, null to throw an exception instead
    *           of dialog and System.exit
    */
   public static void checkWorkspaceNotInUse(Component parentComponent)
   {
      if (checkedWorkspaceNotInUse)
         return;
      String appName = Version.get().getAppName();
      File projectsDirectory = locateProjectsDirectory();
      File workspaceLockFile = new File(projectsDirectory, "lock");
      try
      {
         RandomAccessFile access = new RandomAccessFile(workspaceLockFile, "rw");
         FileLock fileLock = access.getChannel().tryLock(0, Long.MAX_VALUE,
               false);
         if (fileLock == null)
         {
            String message = "The " + appName
                  + " workspace folder still seems to be in use. \n"
                  + "Please close any " + appName
                  + " instances using the workspace "
                  + projectsDirectory.getAbsolutePath();
            if (parentComponent != null)
            {
               JOptionPane.showMessageDialog(parentComponent, message,
                     "Launch " + appName, JOptionPane.ERROR_MESSAGE);
               System.exit(-1);
            }
            else
            {
               throw new IllegalStateException(message);
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         String message = appName + " failed to access it's workspace for an "
               + "unexpected reason. \nThe file "
               + workspaceLockFile.getAbsolutePath() + " could not be opened: "
               + e + " \n" + "Please contact the " + appName
               + " developers if you cannot resolve this problem yourself.";
         if (parentComponent != null)
         {
            JOptionPane.showMessageDialog(parentComponent, message, "Launch "
                  + appName, JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
         }
         else
         {
            throw new RuntimeException(message, e);
         }
      }
      checkedWorkspaceNotInUse = true;
   }

   private static class FujabaRepository extends Repository
   {

      public static final String MANAGEMENT_KEY_REQUIRED_REPOSITORY_FILE = "REQUIRED_REPOSITORY_FILE";

      private ManagementDataHandler requiredFileHandler = new ManagementDataHandler()
      {
         /**
          * @see de.uni_kassel.coobra.Repository.ManagementDataHandler#handleRedo(de.uni_kassel.coobra.Change)
          */
         @Override
         public void handleRedo(Change change)
         {
            if (change.getNewValue() != null)
            {
               FProject p = ProjectManager.get().getFromProjects(
                     change.getOldValue().toString());
               if (p == null)
               {
                  String parentFolderPath = Versioning.get().getOriginalFile(FujabaRepository.this).getParentFile().toString();
                  String absolutePath = PathTranslator.translateToAbsolutePath(parentFolderPath, change.getNewValue().toString());
                  
                  if(new File(absolutePath).exists())
                  {
                     ProjectLoader.getPersistencySupport()
                     .loadRequiredProjectOnDemand(
                           change.getOldValue().toString(),
                           absolutePath, null);
                  }
                  else
                  {
                     // try it the old way with a workspace relative path
                     ProjectLoader.getPersistencySupport()
                     .loadRequiredProjectOnDemand(
                           change.getOldValue().toString(),
                           change.getNewValue().toString(), null);
                  }
               }

               p = ProjectManager.get().getFromProjects(
                     change.getOldValue().toString());

               if (p != null)
               {
                  addToRequiredRepositories(p.getRepository());
               }
            }
         }
      };


      public FujabaRepository()
      {
         this(null); // loaded later on
      }


      public FujabaRepository(FProject project)
      {
         this.project = project;
         this.putManagementDataHandler(MANAGEMENT_KEY_REQUIRED_REPOSITORY_FILE,
               requiredFileHandler);
      }


      /**
       * Create a new instance of an object for redo
       * {@link de.uni_kassel.coobra.Change.Kind#CREATE_OBJECT}.
       * 
       * @param classHandler type of object to be created
       * @param key key for recreating/finding the object
       * @return the newly created/found object (not null!)
       * @throws de.uni_kassel.coobra.persistency.PersistencyException if creation/search fails
       */
      @Override
      protected Object createObject(ClassHandler classHandler, Object key)
            throws PersistencyException
      {
         FProject project = getProject();
         if (project != null)
         {
            FFactory factory = project.getFromFactories(classHandler.getName());
            if (factory != null)
            {
               if (key != null)
               {
                  return factory.getFromProducts((String) key);
               }
               else
               {
                  return factory.create();
               }
            }
         }
         else
         {
            FProjectFactory<? extends FProject> factory = ProjectManager.get()
                  .getFromProjectFactories(classHandler.getName());
            if (factory != null)
            {
               project = factory.create(this, true);
               if (project instanceof UMLPackage)
               {
                  //make sure root package is set, as its creation is no longer a side effect
                  //of UMLProject's constructor
                  UMLPackage root = project.getFromFactories(UMLPackage.class).create();
                  project.setRootPackage(root);
               }
               return project;
            }
         }
         Object object = super.createObject(classHandler, key);
         log.debug("no factory was found to create object of type '"
               + classHandler.getName() + "'");
         if (project == null)
         {
            if (!(object instanceof FProject))
            {
               log.warn("creating an object of type " + classHandler.getName()
                     + " while no project loaded yet.");
            }
         }
         return object;
      }

      private FProject project;


      public FProject getProject()
      {
         if (project == null)
         {
            project = (FProject) getIdentifierModule().getNamedObject(
                  PROJECT_ELEMENT_NAME);
         }
         return project;
      }


      /**
       * @see de.uni_kassel.coobra.Repository#findRequiredRepository(java.lang.String,
       *      java.lang.Object)
       */
      @Override
      protected void findRequiredRepository(String repositoryName,
            Object description) throws RequiredRepositoryMissingException
      {
         try
         {
            super.findRequiredRepository(repositoryName, description);
         }
         catch (RequiredRepositoryMissingException e)
         {
            File originalFile = Versioning.get().getOriginalFile(this);
            if (description == null && repositoryName.endsWith(".jar"))
            {
               description = repositoryName; // TODO hotfix for old project files
            }

            // try loading required jar projets here, use another handler for all other project
            // types
            if (((String) description).endsWith(".jar"))
            {
               ProjectLoader.getPersistencySupport()
                     .loadRequiredProjectOnDemand((String) description,
                           getProject().getName(), originalFile);

               // retry searching for the repository
               super.findRequiredRepository(repositoryName, description);
            }
         }
      }


      /**
       * @see de.uni_kassel.coobra.Repository#addToRequiredRepositories(de.uni_kassel.coobra.Repository)
       */
      @Override
      public boolean addToRequiredRepositories(Repository value)
      {
         boolean change = super.addToRequiredRepositories(value);

         // additionally save the path to the required repository file
         if (change)
         {
            if (value instanceof FujabaRepository)
            {
               FujabaRepository fValue = (FujabaRepository) value;

               if (fValue.getProject() != null
                     && fValue.getProject().getFile() != null
                     && fValue.getProject().getFile().getAbsolutePath() != null
                     && Versioning.get().getOriginalFile(FujabaRepository.this) != null)
               {
                  String absPath = fValue.getProject().getFile()
                        .getAbsolutePath();
                  String basePath = Versioning.get().getOriginalFile(FujabaRepository.this).getParentFile().toString();
                  String relativePath = PathTranslator
                        .translateToRelativePath(basePath, absPath);

                  Change newChange = getChangeFactory().changeManagement(null,
                        MANAGEMENT_KEY_REQUIRED_REPOSITORY_FILE,
                        fValue.getProject().getName(), relativePath, false);
                  acknowledgeChange(newChange);
               }
            }
         }
         return change;
      }
   }

   private static class FujabaFactoryModule extends ResolvingFactoryModule
   {
      @Override
      protected ClassHandlerFactory handleNoSpecificFactoryFound(
            String className, Object instance)
      {
         Collection<ClassHandlerFactory> classHandlerFactories = getClassHandlerFactories()
               .values();
         for (ClassHandlerFactory factory : classHandlerFactories)
         {
            try
            {
               if (instance != null)
               {
                  factory.getClassHandler(instance);
               }
               else
               {
                  factory.getClassHandler(className);
               }
               return factory;
            }
            catch (ClassNotFoundException ex)
            {
               // current class handler factory could not load the class so try the next
               // factory
            }
         }
         return super.handleNoSpecificFactoryFound(className, instance);
      }
   }

   private static class FujabaFeatureAccessModule extends FeatureAccessModule
   {

      private Map<ClassLoader, ClassHandlerFactory> classHandlerFactories = new HashMap<ClassLoader, ClassHandlerFactory>();


      public FujabaFeatureAccessModule()
      {
      }


      public FujabaFeatureAccessModule(
            Map<String, ClassLoader> additionalClassLoaders)
      {
         registerAdditionalClassLoaders(this, additionalClassLoaders);
      }


      private ClassHandlerFactory getClassHandlerFactory(ClassLoader classLoader)
      {
         ClassHandlerFactory pluginFactory = this.classHandlerFactories
               .get(classLoader);

         if (pluginFactory == null)
         {
            DefaultClassHandlerFactory defaultClassHandlerFactory = new DefaultClassHandlerFactory(
                  this);
            defaultClassHandlerFactory.setClassLoader(classLoader);

            this.classHandlerFactories.put(classLoader,
                  defaultClassHandlerFactory);
            pluginFactory = defaultClassHandlerFactory;
         }

         return pluginFactory;
      }


      @Override
      public ClassHandler getClassHandler(Object instance)
            throws ClassNotFoundException
      {
         try
         {
            return super.getClassHandler(instance);
         }
         catch (ClassNotFoundException e)
         {
            // if plugin classloader is needed add it for this instance
            ClassLoader classLoader = instance.getClass().getClassLoader();
            ClassHandler classHandler = getClassHandlerFactory(classLoader)
                  .getClassHandler(instance);
            setClassHandler(instance.getClass().getName(), classHandler);
            return classHandler;
         }
      }

   }
}

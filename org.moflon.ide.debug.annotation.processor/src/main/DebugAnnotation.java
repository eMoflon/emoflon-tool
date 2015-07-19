package main;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.example.processor.internal.CodeAnalyzerProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.debug.core.Activator;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;
import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint;
import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint.Phase;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.algorithm.synchronization.Synchronizer;

import DebugLanguage.AbstractPhase;
import DebugLanguage.AdditionPhase;
import DebugLanguage.Breakpoint;
import DebugLanguage.DebugLanguageFactory;
import DebugLanguage.DebugLanguagePackage;
import DebugLanguage.DebugModel;
import DebugLanguage.DeletionPhase;
import DebugLanguage.InitializationPhase;
import DebugLanguage.TranslationBreakpoint;
import DebugLanguage.TranslationPhase;

import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.util.Pair;

public class DebugAnnotation
{
   public static final Logger log = Logger.getLogger(DebugAnnotation.class);
   {
      if (!log.getAllAppenders().hasMoreElements())
      {
         // Init Logging
         ConsoleAppender appender = new ConsoleAppender();
         appender.setName("Console");
         appender.setWriter(new PrintWriter(System.out));
         appender.setLayout(new SimpleLayout());
         log.addAppender(appender);
         log.setLevel(Level.INFO);
      }
   }

   private URL baseLocation;

   private IWorkspaceRoot root;

   public void setBaseLocation(URL baseLocation)
   {
      this.baseLocation = baseLocation;
   }

   public void setWorkspaceRoot(IWorkspaceRoot root)
   {
      this.root = root;
   }

   private File computeSourceFile(Class<?> clazz)
   {
      if (baseLocation == null)
      {
         baseLocation = clazz.getProtectionDomain().getCodeSource().getLocation();
      }
      String basePath = baseLocation.getPath();
      if (!basePath.endsWith("/"))
      {
         basePath = basePath + "/";
      }
      File file = new File(basePath.replace("%20", " ").replace("/bin/", "/src/") + clazz.getCanonicalName().replace(".", "/") + ".java");
      log.info("Add source file to list of inspected files: " + file);
      return file;
   }

   /**
    * https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html
    * 
    * <i>Hint</i> ToolProvider.getSystemJavaCompiler() is not used for class loading issues in rcp environment
    * 
    * @throws Exception
    */
   @Test
   public void computeDebugAnnotations() throws Exception
   {
      // http://stackoverflow.com/questions/15513330/toolprovider-getsystemjavacompiler-returns-null-usable-with-only-jre-install
      log.info("JAVA_HOME:" + System.getenv("JAVA_HOME"));
      log.info("java.home:" + System.getProperty("java.home"));

      // Get an instance of java compiler
      JavaCompiler compiler = JavacTool.create();

      log.info("compiler: " + compiler);
      Assert.assertNotNull("No Java Compiler was found. Please ensure that you are using a JDK. "
            + "Actions: Run this class with a JDK. Therefore, e.g. configure the JDK in your"
            + " Eclipse and check the Launch Configuration (see JRE tab) to be configured with a valid JDK.", compiler);

      // Get a new instance of the standard file manager implementation
      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

      // Get the list of java file objects
      Vector<String> optionList = new Vector<String>();
      if (Platform.isRunning())
      {
         ArrayList<String> dependencies = new ArrayList<String>();
         dependencies.add(FileLocator.getBundleFile(Platform.getBundle("org.apache.log4j")).toString());
         dependencies.add(FileLocator.getBundleFile(Platform.getBundle("org.eclipse.emf.common")).toString());
         dependencies.add(FileLocator.getBundleFile(Platform.getBundle("org.eclipse.emf.ecore")).toString());
         dependencies.add(root.getProject("org.moflon.core.utilities").getLocation().toPortableString() + "/bin");
         dependencies.add(root.getProject("TGGLanguage").getLocation().toPortableString() + "/bin");
         dependencies.add(root.getProject("TGGRuntime").getLocation().toPortableString() + "/bin");
         optionList.addElement("-classpath");
         optionList.addElement(StringUtils.join(dependencies, ";"));
         System.err.println(optionList);
      }

      Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjects(computeSourceFile(SynchronizationHelper.class),
            computeSourceFile(Synchronizer.class));

      StringWriter out = new StringWriter();
      CompilationTask task = compiler.getTask(out, fileManager, null, optionList, null, compilationUnits1);

      // Create a list to hold annotation processors
      LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();

      // Add an annotation processor to the list
      processors.add(new CodeAnalyzerProcessor());

      // Set the annotation processor to the compiler task
      task.setProcessors(processors);

      // Perform the compilation task.
      boolean result = task.call();
      if (!result)
      {
         log.error(out.toString());
      }
      Assert.assertTrue("Compilation failed for some reason.", result);

      // Process the result
      Map<Phase, Pair<String, Long>> phaseBreakpoints = ((CodeAnalyzerProcessor) processors.get(0)).getPhaseBreakpoints();

      DebugModel dm = DebugLanguageFactory.eINSTANCE.createDebugModel();
      phaseBreakpoints.forEach((phase, pair) -> {
         switch (phase)
         {
         case INIT:
            InitializationPhase ip = DebugLanguageFactory.eINSTANCE.createInitializationPhase();
            Breakpoint bp = DebugLanguageFactory.eINSTANCE.createBreakpoint();
            bp.setClassname(pair.fst);
            bp.setLine(pair.snd);
            ip.getBreakpoints().add(bp);
            dm.getPhases().add(ip);
            break;
         case ADD:
            AdditionPhase ap = DebugLanguageFactory.eINSTANCE.createAdditionPhase();
            bp = DebugLanguageFactory.eINSTANCE.createBreakpoint();
            bp.setClassname(pair.fst);
            bp.setLine(pair.snd);
            ap.getBreakpoints().add(bp);
            dm.getPhases().add(ap);
            break;
         case DELETE:
         case DELETE_BEFORE:
            Optional<AbstractPhase> findPhase = dm.getPhases().stream().filter(p -> p instanceof DeletionPhase).findFirst();
            DeletionPhase dp = null;
            if (findPhase.isPresent())
            {
               dp = (DeletionPhase) findPhase.get();
            } else
            {
               dp = DebugLanguageFactory.eINSTANCE.createDeletionPhase();
            }
            if (phase.equals(DebugBreakpoint.Phase.DELETE))
            {
               bp = DebugLanguageFactory.eINSTANCE.createBreakpoint();
            } else
            { // Phase.DELETE_BEFORE
               bp = DebugLanguageFactory.eINSTANCE.createDeletionBreakpoint();
            }
            bp.setClassname(pair.fst);
            bp.setLine(pair.snd);
            dp.getBreakpoints().add(bp);
            dm.getPhases().add(dp);
            break;
         case TRANSLATION_START:
         case TRANSLATION_STEP:
         case TRANSLATION_END:
            findPhase = dm.getPhases().stream().filter(p -> p instanceof TranslationPhase).findFirst();
            TranslationPhase tp = null;
            if (findPhase.isPresent())
            {
               tp = (TranslationPhase) findPhase.get();
            } else
            {
               tp = DebugLanguageFactory.eINSTANCE.createTranslationPhase();
            }
            bp = DebugLanguageFactory.eINSTANCE.createTranslationBreakpoint();
            bp.setClassname(pair.fst);
            bp.setLine(pair.snd);
            ((TranslationBreakpoint) bp).setSubPhase(phase.name());
            tp.getBreakpoints().add(bp);
            dm.getPhases().add(tp);
            break;
         default:
            throw new RuntimeException("The breakpoint type " + phase + " is currently not supported. Please remove it or implement it's handling.");
         }
      });

      // Bundle version:
      Activator a = new Activator();
      String pluginpath = null;
      if (root == null)
      {
         pluginpath = a.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("/bin", "/");
      } else
      {
         pluginpath = root.findMember(a.getClass().getPackage().getName()).getLocation().toPortableString() + "/";
      }

      log.info("Save breakpoints to: " + pluginpath + MoflonDebugTarget.DEBUG_INIT_XMI);
      eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), dm, pluginpath + MoflonDebugTarget.DEBUG_INIT_XMI);
   }

   @Test
   public void readDebugAnnotation()
   {
      Activator a = new Activator();
      String pluginpath = a.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("/bin", "/");

      DebugLanguagePackage.eINSTANCE.getClass();
      EObject eobj = eMoflonEMFUtil.loadModel(pluginpath + MoflonDebugTarget.DEBUG_INIT_XMI);
      DebugModel dm = (DebugModel) eobj;
      System.out.println("Read Debug Model:");
      dm.getPhases().forEach(p -> {
         System.out.print(p.getClass().getSimpleName());
         p.getBreakpoints().forEach(b -> {
            System.out.print("-> " + b.getLine() + "\n");
         });
      });
      System.out.println("End Read Debug Model.");
   }

}

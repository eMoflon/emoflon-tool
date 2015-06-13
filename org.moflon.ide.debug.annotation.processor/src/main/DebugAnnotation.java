package main;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.eclipse.emf.ecore.EObject;
import org.example.processor.internal.CodeAnalyzerProcessor;
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

import com.sun.tools.javac.util.Pair;

public class DebugAnnotation
{
   private File computeSourceFile(Class<?> clazz)
   {
      URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
      return new File(location.getPath().replace("/bin/", "/src/") + clazz.getCanonicalName().replace(".", "/") + ".java");
   }

   /**
    * https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html
    * 
    * @throws Exception
    */
   @Test
   public void computeDebugAnnotations() throws Exception
   {
      // http://stackoverflow.com/questions/15513330/toolprovider-getsystemjavacompiler-returns-null-usable-with-only-jre-install
      System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_40");

      // Get an instance of java compiler
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      System.err.println(compiler.getClass().getClassLoader());
      System.err.println(this.getClass().getClassLoader());
      // Get a new instance of the standard file manager implementation
      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

      // Get the list of java file objects, in this case we have only
      // one file, TestClass.java
      // Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjects(new File(
      // "C:\\dev\\TGGLanguage\\src\\org\\moflon\\tgg\\algorithm\\synchronization\\SynchronizationHelper.java"));

      // TODO improve for plugin environment
      Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjects(computeSourceFile(SynchronizationHelper.class),
            computeSourceFile(Synchronizer.class));// , computeSourceFile(DebugSynchronizationHelper.class));

      CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits1);

      // Create a list to hold annotation processors
      LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();

      // Add an annotation processor to the list
      processors.add(new CodeAnalyzerProcessor());

      // Set the annotation processor to the compiler task
      task.setProcessors(processors);

      // Perform the compilation task.
      task.call();

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
      // MoflonUtil.
      // String file = MoflonUtil.get
      // ResourcesPlugin.getWorkspace().getRoot().getProject(Activator.PLUGIN_ID).getFile(MoflonDebugTarget.DEBUG_INIT_XMI).getRawLocation().toFile().getAbsolutePath();
      // Bundle version:
      Activator a = new Activator();
      String pluginpath = a.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("/bin", "/");
      // ResourcesPlugin.getWorkspace().getRoot().getProjects()
      eMoflonEMFUtil.saveModel(dm, pluginpath + MoflonDebugTarget.DEBUG_INIT_XMI);
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

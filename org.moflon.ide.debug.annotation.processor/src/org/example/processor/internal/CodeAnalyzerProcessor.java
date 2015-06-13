package org.example.processor.internal;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint;

import com.sun.source.util.Trees;
import com.sun.tools.javac.util.Pair;

/**
 * The annotation processor class which processes java annotations in the supplied source file(s). This processor
 * supports v1.6 of java language and can processes all annotation types.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 * @author Marco Ballhausen (modified)
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor
{
   // private JavacTrees trees;
   private Trees trees;

   private static CodeAnalyzerTreeVisitor visitor;

   @Override
   public void init(ProcessingEnvironment pe)
   {
      super.init(pe);
      // trees = JavacTrees.instance((JavacProcessingEnvironment) pe);//
      System.err.println(pe.getClass().getClassLoader());
      System.err.println(this.getClass().getClassLoader());
      trees = Trees.instance(pe);

      // Scanner class to scan through various component elements
      if (visitor == null)
      {
         visitor = new CodeAnalyzerTreeVisitor();
      }
   }

   public Map<DebugBreakpoint.Phase, Pair<String, Long>> getPhaseBreakpoints()
   {
      return visitor.getPhaseBreakpoints();
   }

   /**
    * Processes the annotation types defined for this processor.
    * 
    * @param annotations
    *           the annotation types requested to be processed
    * @param roundEnvironment
    *           environment to get information about the current and prior round
    * @return whether or not the set of annotations are claimed by this processor
    */
   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment)
   {
      // invoke the scanner for all root elements
      roundEnvironment.getRootElements().forEach(root -> visitor.scan(trees.getPath(root), trees));

      return true;
   }
}

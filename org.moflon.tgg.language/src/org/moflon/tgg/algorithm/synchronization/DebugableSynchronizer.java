package org.moflon.tgg.algorithm.synchronization;

import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint.Phase;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.CorrespondenceModel;

/**
 * This class defines ...
 * 
 * <b>Note:</b> If you change this class you must update <i>debug.init.xmi</i> in order to keep the debugger working.
 * 
 * A debugging developer should checkout the <i>org.moflon.ide.debug.annotation.processor</i> project. The class
 * {@link org.moflon.ide.debug.annotation.processor.DebugAnnotation} can be executed as JUnit test and will update
 * <i>debug.init.xmi</i> automatically.
 *
 * @author mBallhausen
 * @version 2015
 */
public abstract class DebugableSynchronizer extends Synchronizer
{

   public DebugableSynchronizer(CorrespondenceModel graphTriple, Delta delta, SynchronizationProtocol protocol, Configurator configurator,
         StaticAnalysis rules, TempOutputContainer tempOutputContainer)
   {
      super(graphTriple, delta, protocol, configurator, rules, tempOutputContainer);
   }

   @Override
   protected void suspendInitializationPhase()
   {
//      new @DebugBreakpoint(phase = Phase.INIT) String();
   }

   @Override
   protected void suspendDeletionPhase()
   {
      new @DebugBreakpoint(phase = Phase.DELETE) String();
   }

   @Override
   protected void suspendDeletionPhaseBefore()
   {
      new @DebugBreakpoint(phase = Phase.DELETE_BEFORE) String();
   }

   @Override
   protected void suspendAdditionPhase()
   {
      new @DebugBreakpoint(phase = Phase.ADD) String();
   }

   @Override
   protected void suspendTranslationPhaseStart()
   {
      new @DebugBreakpoint(phase = Phase.TRANSLATION_START) String();
   }

   @Override
   protected void suspendTranslationPhaseEnd()
   {
      new @DebugBreakpoint(phase = Phase.TRANSLATION_END) String();
   }

   @Override
   protected void suspendTranslationPhaseStep()
   {
      new @DebugBreakpoint(phase = Phase.TRANSLATION_STEP) String();
   }

}

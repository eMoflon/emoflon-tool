package org.moflon.ide.metamodelevolution.core.processing;

import org.apache.log4j.Logger;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

public abstract class MetamodelDeltaProcessor_ImplBase implements MetamodelDeltaProcessor
{
   protected final Logger logger;
   protected final GenModel genModel;

   public MetamodelDeltaProcessor_ImplBase(final GenModel genModel)
   {
      this.genModel = genModel;
      this.logger = Logger.getLogger(this.getClass());
   }
}

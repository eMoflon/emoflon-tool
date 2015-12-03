package org.moflon.ide.metamodelevolution.core.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.ide.metamodelevolution.core.EMFCompareMetamodelComparator;
import org.moflon.ide.metamodelevolution.core.EMFCompareMetamodelDeltaProcessor;
import org.moflon.ide.ui.UIActivator;

public class MetamodelEvolutionJob extends Job
{

   private final Logger logger;

   private final List<IProject> projects;

   private final IStatus OK_STATUS = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);

   public MetamodelEvolutionJob(final String name, final List<IProject> projects)
   {
      super(name);
      this.logger = Logger.getLogger(this.getClass());
      this.projects = projects;
   }

   @Override
   protected IStatus run(IProgressMonitor monitor)
   {
      IStatus status = OK_STATUS;
      // TODO@settl: implement status monitor
      for (final IProject project : projects)
      {
         EMFCompareMetamodelComparator comparator = new EMFCompareMetamodelComparator(project);
         Map<String, String> delta = comparator.compare();
         if (delta != null)
         {
            EMFCompareMetamodelDeltaProcessor deltaProcessor = new EMFCompareMetamodelDeltaProcessor();
            deltaProcessor.processDelta(project, delta);
         }
      }
      return status;
   }

}

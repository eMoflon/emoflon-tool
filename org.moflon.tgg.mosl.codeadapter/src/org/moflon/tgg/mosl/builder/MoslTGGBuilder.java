package org.moflon.tgg.mosl.builder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.ProjectDependencyAnalyzer;
import org.moflon.ide.core.runtime.builders.AbstractVisitorBuilder;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

@SuppressWarnings("unused")
public class MoslTGGBuilder extends AbstractVisitorBuilder {


   public static final Logger logger = Logger.getLogger(MoslTGGBuilder.class);
   
   public static final String BUILDER_ID = "org.moflon.tgg.mosl.codeadapter.mosltggbuilder";
   
   /**
    * Specification of files whose changes will trigger in invocation of this builder
    */
   private static final String[] PROJECT_INTERNAL_TRIGGERS = new String[] { "src/org/moflon/tgg/mosl/*.tgg", "src/org/moflon/tgg/mosl/**/*.tgg" };

	public MoslTGGBuilder() {
		super(new AntPatternCondition(PROJECT_INTERNAL_TRIGGERS));
	}

	/**
	 * Specifies changes in *other* projects that should trigger a build of this project
	 */
	@Override
	protected AntPatternCondition getTriggerCondition(IProject project) {
		try {
			if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) ||
					project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID)) {
				return new AntPatternCondition(new String[] { "gen/**" });
			}
		} catch (final CoreException e) {
			// Do nothing
		}
		return new AntPatternCondition(new String[0]);
	}
	
	@Override
	protected void postprocess(final RelevantElementCollector buildVisitor, int originalKind,
			final Map<String, String> args, final IProgressMonitor monitor) {
		
	   final int kind = correctBuildTrigger(originalKind);
      final SubMonitor subMonitor = SubMonitor.convert(monitor, triggerProjects.size() + 1);
		
		if (getCommand().isBuilding(kind)) {
			final IFolder moslFolder = getProject().getFolder(new Path("src/org/moflon/tgg/mosl"));
			if ((isAutoOrIncrementalBuild(kind) && hasRelevantDeltas(buildVisitor)) || (isFullBuild(kind) && hasRelevantResources(buildVisitor))) {
					processResource(moslFolder, kind, args, subMonitor.split(1));
			}
		}
		
		if (!hasRelevantDeltas(buildVisitor) && this.isAutoOrIncrementalBuild(kind)) {
			try {
				for (final IProject project : triggerProjects) {
					
				   final RelevantElementCollector relevantElementCollector = new RelevantElementCollector(project, getTriggerCondition(project)) {
					   @Override
						public boolean handleResourceDelta(final IResourceDelta delta) {
							final int deltaKind = delta.getKind();
							if (deltaKind == IResourceDelta.ADDED || deltaKind == IResourceDelta.CHANGED) {
								super.handleResourceDelta(delta);
							}
							return false;
						}
					};
					
					final IResourceDelta delta = getDelta(project);
					if (delta != null) {

						delta.accept(relevantElementCollector, IResource.NONE);
						if (hasRelevantDeltas(relevantElementCollector)) {
							// Perform a full build if a triggering project changed
							build(FULL_BUILD, args, subMonitor.split(1));
							return;
						} else {
							subMonitor.worked(1);
						}
					} else {
						subMonitor.worked(1);
					}
					
				}
			} catch (final CoreException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

   private boolean hasRelevantResources(final RelevantElementCollector buildVisitor)
   {
      return !buildVisitor.getRelevantResources().isEmpty();
   }

   private boolean hasRelevantDeltas(final RelevantElementCollector buildVisitor)
   {
      return !buildVisitor.getRelevantDeltas().isEmpty();
   }

	@Override
	protected void processResource(IResource resource, int kind, Map<String, String> args, IProgressMonitor monitor) {
		try {
			
		   final Resource ecoreResource = new MOSLTGGConversionHelper().generateTGGModel(resource);
			
			if (ecoreResource != null && !ecoreResource.getContents().isEmpty() && ecoreResource.getContents().get(0) instanceof EPackage) {
				
			   final EPackage ePackage = (EPackage) ecoreResource.getContents().get(0);
				
            final ProjectDependencyAnalyzer projectDependencyAnalyzer =
						new ProjectDependencyAnalyzer(this, getProject(), getProject(), ePackage);
				
				final Set<IProject> interestingProjects =
						new TreeSet<>(MetamodelBuilder.PROJECT_COMPARATOR);
				interestingProjects.addAll(Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()));
				projectDependencyAnalyzer.setInterestingProjects(interestingProjects);
				
				final WorkspaceJob job = new ProjectDependencyAnalyzerWorkspaceJob(projectDependencyAnalyzer);
				job.setRule(ResourcesPlugin.getWorkspace().getRoot());
				job.schedule();
				
			} else {
			   
				processProblemStatus(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
						"Unable to construct the correspondence metamodel from the Xtext specification in " + resource, null),
				      resource);
				
			}
		} catch (CoreException e) {
			LogUtils.error(logger, e, "Unable to update created projects: " + e.getMessage());
		}
	}
}

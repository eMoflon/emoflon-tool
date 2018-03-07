package org.moflon.ide.ui;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.properties.MetamodelProjectUtil;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;
import org.moflon.ide.core.runtime.builders.RepositoryBuilder;
import org.moflon.ide.core.runtime.natures.MetamodelNature;
import org.moflon.ide.ui.decorators.MoflonProjectDecorator;
import org.osgi.framework.BundleContext;

/**
 * The Activator controls the plug-in life cycle and contains state and
 * functionality that can be used throughout the plugin. Constants used in
 * various places in the plugin should also be defined in the Activator.
 *
 */
public class UIActivator extends AbstractUIPlugin {
	private static Logger logger = Logger.getLogger(UIActivator.class);

	// IDs used in plugin (have to be synchronized with values in plugin.xml)
	public static final String NEW_METAMODEL_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.metamodel.NewMetamodelWizard";

	public static final String ADD_PARSER_AND_UNPARSER_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.moca.AddParserAndUnparserWizard";

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);

		registerDecoratorListeners();
		registerListenerForMetaModelProjectRenaming();
	}

	/**
	 * Registers a listener that identifies EAP projects that are outdated
	 */
	private void registerDecoratorListeners() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

			@Override
			public void resourceChanged(final IResourceChangeEvent event) {
				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {

						@Override
						public boolean visit(final IResourceDelta delta) throws CoreException {
							IResource resource = delta.getResource();
							if (resource instanceof IProject) {
								final IProject project = (IProject) resource;
								if (MetamodelNature.isMetamodelProjectNoThrow(project)) {
									final IFile eapFile = WorkspaceHelper.getEapFileFromMetamodelProject(project);
									final IFile xmiTree = MetamodelProjectUtil.getExportedMocaTree(project);
									// EA writes to an EAP file immediately after exporting it.
									// Without this timeout, 'needRebuild' would always be true.
									final long outdatedXmiTreeToleranceInMillis = 5000;
									final boolean needsRebuild = !xmiTree.exists()
											|| (xmiTree.exists() && xmiTree.getLocalTimeStamp()
													+ outdatedXmiTreeToleranceInMillis < eapFile.getLocalTimeStamp());

									Display.getDefault().asyncExec(new Runnable() {
										@Override
										public void run() {
											final MoflonProjectDecorator decorator = (MoflonProjectDecorator) PlatformUI
													.getWorkbench().getDecoratorManager()
													.getBaseLabelProvider(MoflonProjectDecorator.DECORATOR_ID);
											if (decorator != null) {
												decorator.setMetamodelProjectRequiresRebuild(project, needsRebuild);
											}
										}
									});
								}

								return false;
							} else {
								return true;
							}
						}
					});

					event.getDelta().accept(new IResourceDeltaVisitor() {

						@Override
						public boolean visit(IResourceDelta delta) throws CoreException {
							final IResource resource = delta.getResource();
							final IProject project;
							if (resource instanceof IProject) {
								project = (IProject) resource;
							} else if (resource instanceof IJavaProject) {
								project = ((IJavaProject) resource).getProject();
							} else {
								project = null;
							}

							if (project != null && project.isAccessible()) {
								final ICommand[] buildSpec = project.getDescription().getBuildSpec();
								for (final ICommand builder : buildSpec) {
									if (isRepositoryBuilder(builder) || isIntegrationBuilder(builder)) {
										final boolean autobuildEnabled = builder
												.isBuilding(IncrementalProjectBuilder.AUTO_BUILD);
										Display.getDefault().asyncExec(new Runnable() {
											@Override
											public void run() {
												final MoflonProjectDecorator decorator = (MoflonProjectDecorator) PlatformUI
														.getWorkbench().getDecoratorManager()
														.getBaseLabelProvider(MoflonProjectDecorator.DECORATOR_ID);
												if (decorator != null) {
													decorator.setAutobuildEnabled(project, autobuildEnabled);
												}
											}
										});
									}
								}

								return false;
							}

							return true;
						}

						private boolean isIntegrationBuilder(final ICommand builder) {
							return IntegrationBuilder.getId().equals(builder.getBuilderName());
						}

						private boolean isRepositoryBuilder(final ICommand builder) {
							return RepositoryBuilder.getId().equals(builder.getBuilderName());
						}
					});
				} catch (final CoreException e) {
					LogUtils.error(logger, e);
				}
			}
		}, IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * Registers a {@link IResourceChangeListener} for detecting renamings of
	 * meta-model projects. According to our convention, the name of the EAP file of
	 * a meta-model project equals the project name plus the suffix ".eap".
	 */
	private void registerListenerForMetaModelProjectRenaming() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {

				IResourceDelta[] children = event.getDelta().getAffectedChildren();
				if (children.length == 2) {
					final IResource firstResource = children[0].getResource();
					final IResource secondResource = children[1].getResource();
					if (firstResource instanceof IProject && secondResource instanceof IProject) {
						final IProject oldProject;
						final IProject newProject;
						if ((children[0].getFlags() & IResourceDelta.MOVED_TO) != 0) {
							oldProject = (IProject) firstResource;
							newProject = (IProject) secondResource;
						} else if ((children[1].getFlags() & IResourceDelta.MOVED_TO) != 0) {
							oldProject = (IProject) secondResource;
							newProject = (IProject) firstResource;
						} else {
							oldProject = null;
							newProject = null;
						}
						if (oldProject != null && MetamodelNature.isMetamodelProjectNoThrow(newProject)) {
							IFile eapFile = newProject.getFile(oldProject.getName() + ".eap");
							if (eapFile.exists()) {
								WorkspaceJob job = new WorkspaceJob("Renaming EAP file") {

									@Override
									public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
										try {
											eapFile.move(new Path(newProject.getName() + ".eap"), true, null);
											return Status.OK_STATUS;
										} catch (final CoreException e) {
											return new Status(IStatus.ERROR,
													WorkspaceHelper.getPluginId(UIActivator.class),
													"Failed to move EAP file " + eapFile, e);
										}
									}
								};
								job.schedule();

							}
						}
					}
				}
			}
		}, IResourceChangeEvent.POST_CHANGE);
	}
}

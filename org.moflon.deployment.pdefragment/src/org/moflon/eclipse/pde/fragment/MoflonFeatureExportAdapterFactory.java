package org.moflon.eclipse.pde.fragment;

import java.net.URI;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.ui.Policy;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetLocation;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.pde.core.target.LoadTargetDefinitionJob;
import org.eclipse.pde.internal.core.FeatureModelManager;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.WorkspaceFeatureModelManager;
import org.eclipse.pde.internal.core.exports.FeatureExportInfo;
import org.eclipse.pde.internal.core.exports.FeatureExportOperation;
import org.eclipse.pde.internal.core.exports.SiteBuildOperation;
import org.eclipse.pde.internal.core.ifeature.IFeatureModel;
import org.eclipse.pde.internal.core.isite.ISiteFeature;
import org.eclipse.pde.internal.core.isite.ISiteModel;
import org.eclipse.pde.internal.core.site.WorkspaceSiteModel;
import org.eclipse.pde.internal.core.target.IUBundleContainer;
import org.eclipse.pde.internal.core.target.P2TargetUtils;

public class MoflonFeatureExportAdapterFactory implements IAdapterFactory {
	private static final String TARGET_DEFINITION_FILE_CONTENT_TYPE_ID =
			"org.eclipse.pde.targetFile";
	private static final String UPDATE_SITE_NATURE =
			"org.eclipse.pde.UpdateSiteNature";

	@SuppressWarnings("unchecked")
   @Override
	public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes") final Class adapterType) {
		if (adaptableObject instanceof String && adapterType == FeatureExportOperation.class) {
			FeatureModelManager featureModelManager = PDECore.getDefault().getFeatureModelManager();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("org.moflon.deployer");
			if (WorkspaceFeatureModelManager.isFeatureProject(project)) {
				final IFeatureModel workspaceFeatureModel =
						featureModelManager.getFeatureModel(project);
				final FeatureExportInfo info = new FeatureExportInfo();
				info.allowBinaryCycles = true;
				// info.categoryDefinition = categoryDefinitionFile.getLocationURI().toString();
				info.destinationDirectory = (String) adaptableObject;
				// info.destinationDirectory = project.getLocation().toOSString();
				info.exportMetadata = true;
				info.exportSource = true;
				info.exportSourceBundle = false;
				info.items = new Object[] { workspaceFeatureModel };
				info.jnlpInfo = null;
				info.qualifier = null; // QualifierReplacer.getDateQualifier();
				info.signingInfo = null;
				info.targets = null;
				info.toDirectory = true;
				info.useJarFormat = true;
				info.useWorkspaceCompiledClasses = false;
				info.zipFileName = null;
				return new FeatureExportOperation(info, "Export Features");
			}
		} else if (adaptableObject instanceof IProject && adapterType == SiteBuildOperation.class) {
			IProject project = (IProject) adaptableObject;
			try {
				if (project.isAccessible() && project.hasNature(UPDATE_SITE_NATURE)) {
					IFile siteXmlFile = project.getFile("site.xml");
					ISiteModel buildSiteModel = new WorkspaceSiteModel(siteXmlFile);
					buildSiteModel.load();

					final FeatureModelManager featureModelManager = PDECore.getDefault().getFeatureModelManager();
					final ISiteFeature[] siteFeatures = buildSiteModel.getSite().getFeatures();
					final IFeatureModel[] featureModels = new IFeatureModel[siteFeatures.length];
					for (int j = 0; j < siteFeatures.length; j++) {
						featureModels[j] = featureModelManager.findFeatureModelRelaxed(siteFeatures[j].getId(), siteFeatures[j].getVersion());
					}
					
//					final FeatureExportInfo info = new FeatureExportInfo();
//					info.allowBinaryCycles = true;
//					info.categoryDefinition = null;
//					info.destinationDirectory = project.getLocation().toOSString();
//					info.exportMetadata = false;
//					info.exportSource = false;
//					info.exportSourceBundle = false;
//					info.items = featureModels;
//					info.jnlpInfo = null;
//					info.qualifier = QualifierReplacer.getDateQualifier();
//					info.signingInfo = null;
//					info.targets = null;
//					info.toDirectory = true;
//					info.useJarFormat = true;
//					info.useWorkspaceCompiledClasses = false;
//					info.zipFileName = null;
					
					Job job = new SiteBuildOperation(featureModels, buildSiteModel, "Build Moflon Update Site");
					job.setUser(true);
					return job;
				}
			} catch (CoreException e) {
				// Do nothing
			}
		} else if (adaptableObject instanceof IFile && adapterType == ITargetDefinition.class) {
			return createTargetDefinition((IFile) adaptableObject);
		} else if (adaptableObject instanceof IFile && adapterType == LoadTargetDefinitionJob.class) {
			final IFile file = (IFile) adaptableObject;
			final ITargetDefinition definition = createTargetDefinition(file);
			return definition != null ? createTargetDefinitionLoaderJob(definition) : null;
		} else if (adaptableObject instanceof ITargetDefinition && adapterType == LoadTargetDefinitionJob.class) {
			return createTargetDefinitionLoaderJob((ITargetDefinition) adaptableObject);
		} else if (adaptableObject instanceof ITargetDefinition && adapterType == UpdateSiteReloadJob.class) {
			try {
				final ITargetDefinition definition = (ITargetDefinition) adaptableObject;
				final ProvisioningUI ui = createProvisioningUI(definition);
				return new UpdateSiteReloadJob(ui);
			} catch (CoreException e) {
				// Do nothing
			}
		} else if (adaptableObject instanceof ITargetDefinition && adapterType == ProvisioningUI.class) {
			try {
				final ITargetDefinition definition = (ITargetDefinition) adaptableObject;
				final ProvisioningUI ui = createProvisioningUI(definition);
				final RepositoryTracker repoTracker = ui.getRepositoryTracker();
				final ITargetLocation[] targetLocations = definition.getTargetLocations();
				for (int i = 0; i < targetLocations.length; i++) {
					if (targetLocations[i] instanceof IUBundleContainer) {
						final IUBundleContainer bundleContainer =
								(IUBundleContainer) targetLocations[i];
						final URI[] repositories = repoTracker.getKnownRepositories(ui.getSession());
						final URI[] uris = bundleContainer.getRepositories();
						for (URI uri : uris) {
							if (Arrays.binarySearch(repositories, uri) < 0) {
								repoTracker.addRepository(uri, "", ui.getSession());
							}
						}
					}
				}
				return ui;
			} catch (CoreException e) {
				// Do nothing
			}
		} else if (adaptableObject instanceof ProvisioningUI && adapterType == UpdateSiteReloadJob.class) {
			return new UpdateSiteReloadJob((ProvisioningUI) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] {
				FeatureExportOperation.class,
				LoadTargetDefinitionJob.class,
				SiteBuildOperation.class,
				ProvisioningUI.class,
				ITargetDefinition.class,
				UpdateSiteReloadJob.class
		};
	}
	
	private final ITargetDefinition createTargetDefinition(final IFile file) {
		try {
			final IContentDescription description = file.getContentDescription();
			if (description != null) {
				final IContentType contentType = description.getContentType();
				if (contentType != null && TARGET_DEFINITION_FILE_CONTENT_TYPE_ID.equals(contentType.getId())) {
					final ITargetPlatformService service =
							(ITargetPlatformService) PDECore.getDefault().acquireService(ITargetPlatformService.class.getName());
					return service.getTarget(file).getTargetDefinition();
				}
			}
		} catch (CoreException e) {
			// Do nothing
		}
		return null;
	}
	
	private final ProvisioningUI createProvisioningUI(final ITargetDefinition definition) throws CoreException {
		final ProvisioningSession session = new ProvisioningSession(P2TargetUtils.getAgent());
		return new ProvisioningUI(session, P2TargetUtils.getProfileId(definition), new Policy());
	}
	
	private final LoadTargetDefinitionJob createTargetDefinitionLoaderJob(final ITargetDefinition definition) {
		final LoadTargetDefinitionJob job = new LoadTargetDefinitionJob(definition);
		job.setUser(true);
		return job;
	}
}

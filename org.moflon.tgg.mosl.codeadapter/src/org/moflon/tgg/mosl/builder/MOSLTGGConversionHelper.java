package org.moflon.tgg.mosl.builder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.core.plugins.manifest.PluginURIToResourceURIRemapper;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.emf.codegen.MoflonGenModelBuilder;
import org.moflon.ide.core.MoslTggConstants;
import org.moflon.tgg.algorithm.configuration.PGSavingConfigurator;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterPostProcessBackwardHelper;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterPostProcessForwardHelper;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterTrafo;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.tgg.tggproject.TGGProject;
import org.moflon.tgg.tggproject.TggprojectFactory;

public class MOSLTGGConversionHelper extends AbstractHandler {
	private static Logger logger = Logger.getLogger(MOSLTGGConversionHelper.class);

	/**
	 * The purpose of this {@link URIHandlerImpl} is to handle "pre.ecore" files in
	 * the same way as "ecore" files.
	 */
	public class PreEcoreHidingURIHandler extends URIHandlerImpl {
		@Override
		public URI deresolve(URI uri) {
			if (hasLastButOnePrefix(uri, "pre")) {
				// Trim ".pre .ecore" and add ".ecore"
				return uri.trimFileExtension().trimFileExtension().appendFileExtension("ecore");
			}

			return uri;
		}

		/**
		 * Returns true if the last but one suffix equals lastButOneSuffix
		 *
		 * @param uri
		 *            the URI to check
		 * @param lastButOneSuffix
		 *            the expected suffix
		 * @return
		 */
		private boolean hasLastButOnePrefix(URI uri, String lastButOneSuffix) {
			return uri.trimFileExtension().fileExtension() != null
					&& uri.trimFileExtension().fileExtension().equals(lastButOneSuffix);
		}
	}

	public Resource generateTGGModel(final IResource resource) throws CoreException {

		final IProject project = resource.getProject();

		// add default attribute conditions
		AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

		final IFolder moslFolder = IFolder.class.cast(resource);
		final XtextResourceSet resourceSet = new XtextResourceSet();

		Collection<TripleGraphGrammarFile> tggFiles = new HashSet<>();
		collectTGGFiles(resourceSet, moslFolder, tggFiles);

		TripleGraphGrammarFile tggRoot = tggFiles.stream().filter(f -> f.getSchema() != null).findAny().get();
		tggFiles.stream().forEach(f -> tggRoot.getRules().addAll(f.getRules()));
		addAttrCondDefLibraryReferencesToSchema(tggRoot);

		EcoreUtil.resolveAll(resourceSet);

		// Save intermediate result of XText parsing
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_URI_HANDLER, new PreEcoreHidingURIHandler());

		// Invoke TGG forward transformation to produce TGG model
		String pathToThisPlugin = WorkspaceHelper.getPathRelToPlugIn("/", WorkspaceHelper.getPluginId(getClass()))
				.getFile();

		CodeadapterTrafo helper = new CodeadapterTrafo(pathToThisPlugin);
		helper.getResourceSet().getResources().add(tggRoot.eResource());
		helper.setSrc(tggRoot);
		helper.setVerbose(false);
		helper.integrateForward();

		CodeadapterPostProcessForwardHelper postProcessHelper = new CodeadapterPostProcessForwardHelper();
		postProcessHelper.postProcessForward(helper);

		TGGProject tggProject = (TGGProject) helper.getTrg();
		if (tggProject != null) {
			return saveInternalTGGModelToXMI(tggProject, resourceSet, options, project.getName());
		}

		return null;
	}

	private void addAttrCondDefLibraryReferencesToSchema(TripleGraphGrammarFile tggRoot) {
		EList<AttrCondDef> usedAttrCondDefs = new BasicEList<AttrCondDef>();
		for (Rule rule : tggRoot.getRules()) {
			for (AttrCond attrCond : rule.getAttrConditions()) {
				if (!usedAttrCondDefs.contains(attrCond.getName()) && !attrCond.getName().isUserDefined()) {
					usedAttrCondDefs.add(attrCond.getName());
				}
			}
		}
		tggRoot.getSchema().getAttributeCondDefs().addAll(usedAttrCondDefs);
	}

	/**
	 * Loads a TGG grammar from the given folder.
	 *
	 * @param resourceSet
	 * @param moslFolder
	 * @return
	 * @throws IOException
	 * @throws CoreException
	 */
	private void collectTGGFiles(XtextResourceSet resourceSet, IFolder moslFolder,
			Collection<TripleGraphGrammarFile> tggFiles) throws CoreException {
		if (!moslFolder.exists())
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Required folder for MOSL-TGG specification is missing: " + moslFolder));

		for (final IResource resource : moslFolder.members()) {
			if (resource instanceof IFile) {
				final IFile file = IFile.class.cast(resource);
				if (file.getFileExtension().equals(MoslTggConstants.MOSL_TGG_EXTENSION)) {
					tggFiles.add(loadTggFromFile(resourceSet, file));
				}
			} else if (resource instanceof IFolder)
				collectTGGFiles(resourceSet, IFolder.class.cast(resource), tggFiles);
		}
	}

	private TripleGraphGrammarFile loadTggFromFile(XtextResourceSet resourceSet, IFile schemaFile)
			throws CoreException {
		XtextResource tggResource = (XtextResource) resourceSet
				.createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
		try {
			tggResource.load(null);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Problem while loading TGG file from '" + tggResource + "'. Reason: " + e.getMessage(), e));
		}
		TripleGraphGrammarFile tggFile = (TripleGraphGrammarFile) tggResource.getContents().get(0);
		return tggFile;
	}

	/**
	 * Tries to convert the first selected item from a "tgg.xmi" file to a MOSL-TGG
	 * specification
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);

			if (selection instanceof IStructuredSelection) {
				Object file = ((IStructuredSelection) selection).getFirstElement();
				if (file instanceof IFile) {
					IFile tggFile = (IFile) file;

					ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
					PluginURIToResourceURIRemapper.createPluginToResourceMap(resourceSet);
					TGGProject tggProject = createTGGProject(tggFile, resourceSet);
					resourceSet.getResources().forEach(r -> {
						try {
							r.load(null);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					EcoreUtil.resolveAll(resourceSet);

					String pathToThisPlugin = WorkspaceHelper
							.getPathRelToPlugIn("/", WorkspaceHelper.getPluginId(getClass())).getFile();
					CodeadapterTrafo helper = new CodeadapterTrafo(pathToThisPlugin, resourceSet);

					helper.setVerbose(true);
					helper.setConfigurator(new PGSavingConfigurator(helper,
							tggFile.getProject().getLocation().toString() + "/instances/PG.xmi"));
					helper.setTrg(tggProject);
					helper.integrateBackward();

					CodeadapterPostProcessBackwardHelper postProcessHelper = new CodeadapterPostProcessBackwardHelper();
					postProcessHelper.postProcessBackward(helper);

					TripleGraphGrammarFile tggModel = (TripleGraphGrammarFile) helper.getSrc();
					String projectPath = tggFile.getProject().getFullPath().toString();
					String projectName = tggFile.getProject().getName().replaceAll(Pattern.quote("."), "/");

					Resource resource = resourceSet.createResource(URI.createPlatformResourceURI(
							projectPath + "/src/" + projectName + "/org/moflon/tgg/mosl" + projectPath + ".xmi", true));
					resource.getContents().add(tggModel);
					resource.save(null);

					saveXtextTGGModelToTGGFile(tggModel, tggFile.getProject(),
							"/src/" + projectName + "/org/moflon/tgg/mosl" + projectPath + ".tgg");
				}
			}
		} catch (Exception e) {
			LogUtils.error(logger, e);
			throw new ExecutionException("MOSL-TGG conversion failed", e);
		}

		return null;
	}

	private Resource saveInternalTGGModelToXMI(TGGProject tggProject, XtextResourceSet resourceSet,
			Map<Object, Object> options, String saveTargetName) throws CoreException {
		TripleGraphGrammar tgg = tggProject.getTgg();
		EPackage corrPackage = tggProject.getCorrPackage();

		String file = StringUtils.substringAfterLast(saveTargetName, ".");

		if (file.isEmpty()) {
			file = StringUtils.capitalize(saveTargetName);
		} else {
			file = StringUtils.capitalize(file);
		}

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(saveTargetName);
		eMoflonEMFUtil.createPluginToResourceMapping(resourceSet, project);
		URI relativePreEcoreXmiURI = URI.createURI(MoflonConventions.getDefaultPathToFileInProject(file, ".pre.ecore"));
		URI projectURI = MoflonGenModelBuilder.determineProjectUriBasedOnPreferences(project);
		URI preEcoreXmiURI = relativePreEcoreXmiURI.resolve(projectURI);
		Resource preEcoreResource = resourceSet.createResource(preEcoreXmiURI);
		preEcoreResource.getContents().add(corrPackage);
		final String prefix = MoflonUtil.allSegmentsButLast(corrPackage.getNsPrefix());
		if (prefix != null && prefix.length() > 0) {
			EcoreUtil.setAnnotation(corrPackage, "http://www.eclipse.org/emf/2002/GenModel", "basePackage", prefix);
		}

		try {
			preEcoreResource.save(options);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Problem while saving PreEcore resource '" + preEcoreResource + "'. Reason: " + e.getMessage(), e));
		}

		URI pretggXmiURI = URI.createPlatformResourceURI(
				saveTargetName + "/" + MoflonConventions.getDefaultPathToFileInProject(file, ".pre.tgg.xmi"), false);
		Resource pretggXmiResource = resourceSet.createResource(pretggXmiURI);
		pretggXmiResource.getContents().add(tgg);
		try {
			pretggXmiResource.save(options);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Problem while saving Pre-TGG XMI resource '" + pretggXmiResource + "'. Reason: " + e.getMessage(),
					e));
		}
		return preEcoreResource;
	}

	private void saveXtextTGGModelToTGGFile(TripleGraphGrammarFile tggModel, IProject project, String filePath)
			throws IOException, CoreException {
		URI tggFileURI = URI.createPlatformResourceURI(project.getFullPath().toString() + filePath, true);

		XtextResourceSet xtextResourceSet = new XtextResourceSet();
		XtextResource xtextResource = (XtextResource) xtextResourceSet.createResource(tggFileURI);
		AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

		xtextResource.getContents().add(tggModel);
		EcoreUtil.resolveAll(xtextResource);

		SaveOptions.Builder options = SaveOptions.newBuilder();
		options.format();
		xtextResource.save(options.getOptions().toOptionsMap());
	}

	private TGGProject createTGGProject(IFile tggFile, ResourceSet resourceSet) throws CoreException {
		String tggFilePath = tggFile.getFullPath().toString();
		Resource tggEcoreResource = resourceSet
				.createResource(URI.createPlatformResourceURI(tggFilePath.replace(".tgg.xmi", ".ecore"), true));
		try {
			tggEcoreResource.load(null);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Problem while loading TGG Ecore resource '" + tggEcoreResource + "'. Reason: " + e.getMessage(),
					e));
		}
		Resource tggModelResource = resourceSet.createResource(URI.createPlatformResourceURI(tggFilePath, true));
		try {
			tggModelResource.load(null);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Problem while loading TGG model resource '" + tggModelResource + "'. Reason: " + e.getMessage(),
					e));
		}
		EcoreUtil.resolveAll(resourceSet);

		TGGProject tggProject = TggprojectFactory.eINSTANCE.createTGGProject();
		tggProject.setCorrPackage((EPackage) tggEcoreResource.getContents().get(0));
		tggProject.setTgg((TripleGraphGrammar) tggModelResource.getContents().get(0));
		Resource tggProjectResource = resourceSet.createResource(URI.createURI("TGGProject"));
		tggProjectResource.getContents().add(tggProject);

		return tggProject;
	}
}

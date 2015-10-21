package org.moflon.backend.ecore2fujaba;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.compiler.sdm.MethodVisitor;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.properties.MoflonPropertiesContainerHelper;

import MoflonPropertyContainer.MoflonPropertiesContainer;
import de.fujaba.preferences.StandaloneProjectPreferenceStoreBuilder;
import de.fujaba.preferences.WorkspacePreferenceStore;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.persistency.AbstractStreamPersistencyModule;
import de.uni_kassel.coobra.persistency.CachedPersistencyModule;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.MemoryPersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.persistency.VoidPersistencyModule;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionModule;
import de.uni_kassel.fujaba.codegen.CodeGenPlugin;
import de.uni_kassel.fujaba.codegen.Utility;
import de.uni_kassel.fujaba.codegen.engine.CodeGeneration;
import de.uni_kassel.fujaba.codegen.engine.CodeWritingEngine;
import de.uni_kassel.fujaba.codegen.engine.Engine;
import de.uni_kassel.fujaba.codegen.engine.JavaTokenMutatorTemplateEngine;
import de.uni_paderborn.fujaba.app.SimpleFujabaPersistencySupport;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.preferences.ProjectPreferenceStore;
import de.uni_paderborn.fujaba.project.ProjectLoader;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.project.ProjectWriter;
import de.uni_paderborn.fujaba.uml.common.UMLProjectFactory;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.uni_paderborn.fujaba.versioning.VersioningLoader;
import de.uni_paderborn.fujaba.versioning.VersioningWriter;

/**
 * Is responsible for initializing and starting CodeGen2, and coordinating all
 * necessary steps. Delegates the Ecore->Fujaba transformation to
 * {@link Ecore2Fujaba}.
 * 
 * 
 * @author anjorin
 * @author (last editor) $Author: anjorin $
 * @version $Revision: 1418 $ $Date: 2012-11-30 13:33:29 +0100 (Fr, 30 Nov 2012)
 *          $
 */
public class CodeGen2Adapter {
	private static final Logger logger = Logger
			.getLogger(CodeGen2Adapter.class);

	private static final String DEBUG_FILE_EXTENSION = "ctr";
	private static final String DEFAULT_NAME_FOR_TEMP_FUJABA_PROJECT = "generatedFujabaProject";

	private boolean debugMode;

	private boolean genTraceInformation;

	private IFolder debugFolder;

	private static Map<String, String> importMappings = new HashMap<String, String>();

	private static Map<String, String> generatedCode;

	private String generatedCodeRaw;

	// Used to keep mapping of eOperations to UMLMethod for assigning imports
	// from CodeGen2
	private static Map<EOperation, UMLMethod> eOperationToUMLMethod;

	// Used to create, delete and manipulate Fujaba projects
	private ProjectManager projectManager;

	// The Fujaba project that is populated and fed to CodeGen2
	private FProject project;

	private FCodeStyle emfCodeStyle;

	// Helper for transformation
	private Ecore2Fujaba ecore2fujaba;

	private IProject ecoreProject;

	public CodeGen2Adapter(final IResource ecoreFile, final List<Resource> resources,
			final Resource ecoreResource, final MoflonPropertiesContainer properties) {
		// set data members
		debugMode = false;
		genTraceInformation = false;
		
		ecoreProject = ecoreFile.getProject();
		debugFolder = ecoreFile.getProject().getFolder("/debug");
		generatedCode = new HashMap<String, String>();

		// Get import mappings from user properties
		MoflonPropertiesContainer userProperties;
		userProperties = MoflonPropertiesContainerHelper.load(ecoreProject, new NullProgressMonitor());
		
		importMappings = MoflonPropertiesContainerHelper.mappingsToMap(userProperties.getImportMappings());

		/* Initialize fujaba and codegen related stuff */
		logger.debug("Initializing fujaba and codegen related stuff");

		// Get ProjectManager
		projectManager = ProjectManager.get();

		// Add versioning support
		VersioningLoader versioningLoader = new VersioningLoader(false);
		VersioningWriter versioningWriter = new VersioningWriter(false);
		projectManager.registerProjectLoader(DEBUG_FILE_EXTENSION,
				versioningLoader);
		projectManager.registerProjectWriter(DEBUG_FILE_EXTENSION,
				versioningWriter);

		// Initialize core preferences
		FujabaPreferencesManager.setPreferenceStore(
				FujabaPreferencesManager.FUJABA_CORE_PREFERENCES,
				WorkspacePreferenceStore.getInstance());
		FujabaPreferencesManager
				.setProjectPreferenceStoreBuilder(new StandaloneProjectPreferenceStoreBuilder());

		// Add persistency support
		ProjectLoader
				.setPersistencySupport(new SimpleFujabaPersistencySupport());

		// Initialize codegen
		new CodeGenPlugin().initialize();
		logger.debug("Started codegen plugin");

		// Create empty fujaba project
		UMLProjectFactory projectFactory = new UMLProjectFactory();

		if (!debugMode) {
			// prevent the use of FilePersistencyModule
			Repository repository = Versioning.get()
					.setupRepositoryForNewProject(true, true);
			PersistencyModule persistencyModule = repository
					.getPersistencyModule();
			if (persistencyModule != null
					&& persistencyModule instanceof CachedPersistencyModule) {
				CachedPersistencyModule cpm = (CachedPersistencyModule) persistencyModule;
				PersistencyModule delegate = cpm.getDelegate();
				if (delegate != null
						&& delegate instanceof AbstractStreamPersistencyModule) {
					((AbstractStreamPersistencyModule) delegate)
							.setAutoFlushInterval(0);
				}
			}
			repository.setPersistencyModule(new VoidPersistencyModule());
			project = projectFactory.create(repository, false);
		} else {
			project = projectFactory.create(null, true);
		}
		project.setName(DEFAULT_NAME_FOR_TEMP_FUJABA_PROJECT);
		logger.debug("Created temporary fujaba project");

		// set some project specific properties
		initFujabaAndCodegenPreferences();
		logger.debug("Initialized Fujaba/CodeGen Properties");

		// Setup emf code style
		FFactory<FCodeStyle> codeStyleFactory = project
				.getFromFactories(FCodeStyle.class);
		emfCodeStyle = codeStyleFactory.getFromProducts("emf");

		if (emfCodeStyle == null) {
			emfCodeStyle = codeStyleFactory.create(true);
			emfCodeStyle.setName("emf");
		}

		project.setCodeStyle(emfCodeStyle);

		// Initialize helpers
		ecore2fujaba = new Ecore2Fujaba(project, emfCodeStyle, resources, ecoreResource,
				properties);

		logger.debug("Completed initialization");
	}

	private void initFujabaAndCodegenPreferences() {
		// init global preferences
		// eventually SystemPreferenceStore gets polled - it uses properties
		// stored in "System" => deposit settings there
		if (System.getProperty(FujabaCorePreferenceKeys.PROJECT_ID) == null) {
			System.setProperty(FujabaCorePreferenceKeys.PROJECT_ID, "temp"); // this
																				// is
																				// only
																				// cosmetic
																				// to
																				// prevent
																				// (log4j)
																				// WARN-messages
																				// from
																				// occurring
		}
		final String tempPropKey = FujabaCorePreferenceKeys.PROPERTY_PREFIX
				+ DEFAULT_NAME_FOR_TEMP_FUJABA_PROJECT;
		if (System.getProperty(tempPropKey) == null) {
			System.setProperty(tempPropKey, ""); // this is only cosmetic to
													// prevent (log4j)
													// WARN-messages from
													// occurring
		}
		if (System.getProperty("de.fujaba.general.FileHistory") == null) {
			System.setProperty("de.fujaba.general.FileHistory", ""); // this is
																		// only
																		// cosmetic
																		// to
																		// prevent
																		// (log4j)
																		// WARN-messages
																		// from
																		// occurring
		}

		// init settings specific for the temporal Fujaba project
		// setting the name of the project to the ProjectPreferenceStore
		ProjectPreferenceStore projectPreferenceStore = FujabaPreferencesManager
				.getProjectPreferenceStore(project);
		projectPreferenceStore.setValue(FujabaCorePreferenceKeys.PROJECT_ID,
				DEFAULT_NAME_FOR_TEMP_FUJABA_PROJECT);
	}

	/**
	 * Generates code for SDMs and stores the result.
	 * 
	 * @param genModel
	 * 
	 * @throws Exception
	 */
	public Map<String, String> generateCode(final GenModel genModel) throws Exception {
		logger.debug("Starting to generate SDM code");

		Utility.get().reset(); // init the utility to its defaults

		/* 0. Retrieve user settings for util */
		MoflonPropertiesContainer userProperties = MoflonPropertiesContainerHelper
				.load(ecoreProject, new NullProgressMonitor());
		Map<String, String> importMappings = MoflonPropertiesContainerHelper
				.mappingsToMap(userProperties.getImportMappings());
		Utility.get().addImportMappings(importMappings);

		/* 1. Build up fujaba project afresh */
		ecore2fujaba.createFProject();

		if (debugMode) {
			logger.debug("Saving generated fujaba project");
			try {
				// Save fujaba project
				ProjectWriter writer = projectManager
						.getProjectWriter(DEBUG_FILE_EXTENSION);

				if (!debugFolder.exists())
					debugFolder.create(true, false, null);

				IFile ctrFile = debugFolder
						.getFile("generatedFujabaProject.ctr");

				if (!ctrFile.exists())
					ctrFile.create(new ByteArrayInputStream("".getBytes()),
							true, null);

				writer.saveProject(project, new File(ctrFile.getLocationURI()),
						null);
				debugFolder.refreshLocal(IResource.DEPTH_INFINITE, null);

				// Save SDMs
				for (String sdmFilename : ecore2fujaba.getSDMs().keySet()) {
					IFile sdmFile = debugFolder.getFile(sdmFilename);
					ByteArrayInputStream sdmContents = null;
					try {
						sdmContents = new ByteArrayInputStream(ecore2fujaba
								.getSDMs().get(sdmFilename).toString()
								.getBytes());

						if (sdmFile.exists())
							sdmFile.setContents(sdmContents, IResource.FORCE,
									null);
						else
							sdmFile.create(sdmContents, IResource.FORCE, null);
					} catch (Exception e) {
						throw e;
					} finally {
						sdmContents.close();
					}
				}

			} catch (Exception e) {
				logger.error("Unable to save fujaba project for debugging purposes.");
				e.printStackTrace();
			}
		}

		/* 2. Prepare code generation */

		// Get template directory
		URL templateDir = MoflonUtilitiesActivator.getPathRelToPlugIn("/templates",
				"de.uni_kassel.fujaba.codegen");

		// Correct template directory for all code writers in responsible engine
		Engine engine = CodeGeneration.get().getEngineFor(project, "java");

		JavaTokenMutatorTemplateEngine javaTemplateEngine = (JavaTokenMutatorTemplateEngine) engine;

		for (CodeWritingEngine cw : javaTemplateEngine.getCodeWriter()) {
			cw.getTemplateLoader().getLoader().addToContext(templateDir);
			cw.setGlobalTemplate(cw.getTemplateLoader().loadTemplate(
					"java/global.vm"));
			cw.addToDirs(templateDir);
		}

		// activate tracing, if required
		if (genTraceInformation) {
			Utility.get().enableSdmTracing();

			// add a mapping from UMLMethod to the EOperation's ID (used for
			// lookup during trace-code-generation)
			Map<UMLMethod, Integer> toBeAdded = new HashMap<UMLMethod, Integer>();
			for (EOperation eOperation : ecore2fujaba
					.geteOperationToUMLMethod().keySet()) {
				int operationID = eOperation.getEContainingClass()
						.getOperationID(eOperation);
				toBeAdded
						.put(ecore2fujaba.geteOperationToUMLMethod().get(
								eOperation), operationID);
			}
			Utility.get().addAllUMLMethodToEOperationIds(toBeAdded);
		} else if (Utility.get().isSdmTracingActivated()) {
			Utility.get().disableSdmTracing();
		}

		// activate list shuffling, if required
		if (Utility.get().isShuffleLists()) {
			Utility.get().disableListShuffling();
		}

		/* 3. Generate code and save debug info */
		generateCodeForSDMs(ecore2fujaba.geteOperationToUMLMethod());
		logger.debug("Generated SDM code");

		if (debugMode) {
			logger.debug("Saving generated code");
			try {
				// Save generated code
				IFile sdmDebug = debugFolder.getFile("sdmCodeFromCodeGen2");
				ByteArrayInputStream contents = new ByteArrayInputStream(
						generatedCode.toString().getBytes());

				IFile sdmDebugRaw = debugFolder
						.getFile("sdmCodeFromCodeGen2Raw");
				ByteArrayInputStream contentsRaw = new ByteArrayInputStream(
						generatedCodeRaw.toString().getBytes());

				if (sdmDebug.exists())
					sdmDebug.setContents(contents, IResource.FORCE, null);
				else
					sdmDebug.create(contents, IResource.FORCE, null);

				if (sdmDebugRaw.exists())
					sdmDebugRaw.setContents(contentsRaw, IResource.FORCE, null);
				else
					sdmDebugRaw.create(contentsRaw, IResource.FORCE, null);

				contents.close();
				contentsRaw.close();

				logger.debug("Completed saving generated fujaba project");
			} catch (Exception e) {
				logger.error("Unable to save generated code for debugging purposes.");
				e.printStackTrace();
			}
		}

		return generatedCode;
	}

	public void release() {
		if (!debugMode) {
			CodeGeneration.get().removeYou();

			Repository repository = project.getRepository();
			if (repository != null) {
				Transaction activeTransaction = repository
						.getActiveTransaction();
				if (activeTransaction != null) {
					activeTransaction.commit();
				}
				repository.gc();
				projectManager.closeProject(project, false);
				projectManager.closeAllProjects(false);
				projectManager.removeAllFromProjects();

				IdentifierModule identifierModule = repository
						.getIdentifierModule();
				if (identifierModule != null) {
					identifierModule.gc();
					identifierModule.delete();
				}

				PersistencyModule persistencyModule = repository
						.getPersistencyModule();
				if (persistencyModule != null) {
					if (persistencyModule instanceof MemoryPersistencyModule) {
						MemoryPersistencyModule mpm = (MemoryPersistencyModule) persistencyModule;
						mpm.clear();
						if (mpm instanceof CachedPersistencyModule) {
							CachedPersistencyModule cpm = (CachedPersistencyModule) mpm;
							PersistencyModule delegateModule = cpm
									.getDelegate();
							cpm.clear();
							cpm.delete();
							if (delegateModule != null) {
								if (delegateModule instanceof FilePersistencyModule) {
									FilePersistencyModule fpm = (FilePersistencyModule) delegateModule;
									fpm.sendEOF();
									fpm.flush();
								}
								delegateModule.delete();
							}
						}
					}
					persistencyModule.delete();
				}

				TransactionModule transactionModule = repository
						.getTransactionModule();
				if (transactionModule != null) {
					transactionModule.delete();
				}
			} else {
				projectManager.closeProject(project, true);
			}
		} else {
			projectManager.closeProject(project, true);
		}
		logger.debug("Closed Fujaba Project");
	}

	/**
	 * Used by EMF codegenerator to collect imports for ClassImpl containing
	 * eOperation
	 * 
	 * @param genModel
	 *            Imports are added as Strings to the genModel via
	 *            genModel.addImport(...)
	 * @param eOperation
	 *            Current operation used to retrieve corresponding imports
	 */
	public void addImportsToGenModel(final GenModel genModel, final EOperation eOperation) {
		// Go from eOperation to UMLMethod
		UMLMethod method = getMethodForEOperation(eOperation);

		// Handle imports for TGG rules
		if (eOperation.getEContainingClass().getEAnnotation("TGGRule") != null) {
			addImport(genModel, "TGGLanguage.csp.*");
			addImport(genModel, "csp.constraints.*");
			addImport(genModel, "org.moflon.csp.CSPFactoryHelper");
		}

		// Add import for the trace util
		if (Utility.get().isSdmTracingActivated()) {
			addImport(genModel, "java.lang.reflect.Method");
			addImport(genModel, "org.eclipse.emf.ecore.EOperation");
		}

		// Get imports from Utility
		if (Utility.methodToImports.containsKey(method))
			for (String anImport : Utility.methodToImports.get(method)) {
				// Only add legal imports and perform transformations if
				// necessary
				addImport(genModel, anImport);
			}
	}

	private UMLMethod getMethodForEOperation(final EOperation eOperation) {
		UMLMethod m = eOperationToUMLMethod.get(eOperation);

		if (m == null) {
			String eOperationID = MethodVisitor.signatureFor(eOperation);
			for (EOperation eop : eOperationToUMLMethod.keySet()) {
				if (MethodVisitor.signatureFor(eop).equals(eOperationID))
					return eOperationToUMLMethod.get(eop);
			}

			logger.error("Unable to retrieve UMLMethod for eOperation: "
					+ eOperation.getName());
		}

		return m;
	}

	/**
	 * Helper method to retrieve the required imports from CodeGen for a given
	 * EOperation.
	 * 
	 * @param eOperation
	 *            The method for which the imports shall be retrieved.
	 * @return A set of string representations of required imports for the given
	 *         method. This set is, by convention, a copy of the actual data and
	 *         thus modifiable by the user.
	 */
	public Set<String> getMethodImports(final EOperation eOperation) {
		// Go from eOperation to UMLMethod
		UMLMethod method = eOperationToUMLMethod.get(eOperation);

		// Get imports from Utility
		return Utility.getMethodImports(method);
	}

	private static boolean isValidImport(final String importStatement) {
		return !(importStatement.startsWith("${") || importStatement
				.startsWith("de.uni_kassel.features"));
	}

	/**
	 * CodeGen2 sometimes has problems resolving template variables and these
	 * $... imports have to be removed. Imports that start with
	 * "de.uni_kassel..." are also unnecessary.
	 * 
	 * @param anImport
	 * @return true if import is legal
	 */
	private static void addImport(final GenModel genModel, String anImport) {
		if (isValidImport(anImport)) {

			// Import is legal so perform transformations if necessary
			if (anImport.startsWith("ecore")) {
				anImport = anImport.replaceFirst("ecore",
						"org.eclipse.emf.ecore");
			}

			// Correct with mappings from user
			for (String old : importMappings.keySet()) {
				if (anImport.startsWith(old)) {
					anImport = anImport.replaceFirst(old,
							importMappings.get(old));
				}
			}

			genModel.addImport(anImport);
		}
	}

	private void generateCodeForSDMs(
			final HashMap<EOperation, UMLMethod> eOperationToUMLMethod)
			throws Exception {
		// 0. Initialize collection for later (bookkeeping used by
		// EMFCodegenerator)
		CodeGen2Adapter.eOperationToUMLMethod = eOperationToUMLMethod;

		// 1. Pass current project to CodeGen2 for codegeneration
		String codeForProject = generateCodeForSDM(project);
		generatedCodeRaw = codeForProject;

		MethodVisitor methodVisitor = new MethodVisitor(codeForProject);
		for (EOperation eOperation : eOperationToUMLMethod.keySet()) {
			// 2. Extract only relevant code for method
			String codeForMethod = methodVisitor.extractCodeForMethod(
					eOperation, ecoreProject);

			// 3. Save generated code to be retrieved by EMF codegenerator
			generatedCode.put(MethodVisitor.signatureFor(eOperation),
					codeForMethod);
		}
	}

	private String generateCodeForSDM(final FElement element)
			throws MalformedURLException {
		// Use codegen to generate code for project
		return de.fujaba.codegen.CodeGeneration.get().generateFElement(element)
				.toString();
	}
}

/*
 * Copyright (c) 2010-2012 Gergely Varro
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Gergely Varro - Initial API and implementation
 */
package org.gervarro.emoflon;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.emf.codegen.GenModelBuilder;
import org.moflon.emf.codegen.dependency.SDMEnhancedEcoreResource;

import SDMLanguage.activities.Activity;

/**
 * This code generator serves to test the code generation engine in isolation
 */
public class EMoflonCodeGenerationApp extends GenModelBuilder {
	static final String TAG_PACKAGE = "package";

	static final String ATT_URI = "uri";

	static final String ATT_CLASS = "class";

	static final String ATT_GEN_MODEL = "genModel";

	public EMoflonCodeGenerationApp(final ResourceSet resourceSet) {
		super(resourceSet);
	}

	public void manuallyAddedCode(final Activity activity) {
		// Manually added code
		final Resource actRes = activity.eResource();
		final URI uri = actRes.getURI();
		final Resource res = new ResourceImpl(uri.trimSegments(1).appendSegment("graph"));
		actRes.getResourceSet().getResources().add(res);
	}

	public static Map<String, URI> readPluginRegistry() {
		final Map<String, URI> ePackageNsURIToGenModelLocationMap = new HashMap<String, URI>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final String pluginID = EcorePlugin.getPlugin().getBundle().getSymbolicName();
		final String extensionPointID = EcorePlugin.GENERATED_PACKAGE_PPID;
		final IExtensionPoint point = registry.getExtensionPoint(pluginID, extensionPointID);
		if (point != null) {
			final IConfigurationElement[] elements = point.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				final IConfigurationElement element = elements[i];
				if (element.getName().equals(TAG_PACKAGE)) {
					final String packageURI = element.getAttribute(ATT_URI);
					final String classAttribute = element.getAttribute(ATT_CLASS);
					if (packageURI != null && classAttribute != null) {
						final String genModel = element.getAttribute(ATT_GEN_MODEL);
						if (genModel != null) {
							URI genModelURI = URI.createURI(genModel);
							if (genModelURI.isRelative()) {
								genModelURI = URI.createPlatformPluginURI(
										element.getDeclaringExtension().getContributor().getName() + "/" + genModel,
										true);
								ePackageNsURIToGenModelLocationMap.put(packageURI, genModelURI);
							}
						}
					}
				}
			}
		}
		return ePackageNsURIToGenModelLocationMap;
	}

	public GenModel buildGenModel(final URI genModelURI, final boolean saveGenModel) throws IOException {
		// Create new GenModel
		final Resource genModelResource = resourceSet.createResource(genModelURI);
		final GenModel genModel = GenModelPackage.eINSTANCE.getGenModelFactory().createGenModel();
		genModelResource.getContents().add(genModel);

		loadDefaultGenModelContent(genModel);
		genModel.setModelDirectory("/Sequencer/src");

		// Load Ecore genmodel
		final URI ecorePlatformPluginURI = URI.createPlatformPluginURI("/org.eclipse.emf.ecore/", true);
		final URI ecoreJarURI = URI.createURI(
				"file:/C:/Download/eclipse-juno/plugins/org.eclipse.emf.ecore_2.8.3.v20130125-0546.jar", true);
		resourceSet.getURIConverter().getURIMap().put(ecorePlatformPluginURI,
				GenModelBuilder.createArchiveURI(ecoreJarURI));
		resourceSet.getResource(URI.createURI("model/Ecore.genmodel").resolve(ecorePlatformPluginURI), true);

		// Initialize SDMLanguage URIs
		final URI sdmLanguageJarURI = URI.createURI(
				"file:/C:/Download/eclipse-juno/plugins/org.moflon.SDMLanguage_1.0.0.201305161247.jar", true);
		final URI sdmLanguagePlatformResourceURI = URI.createPlatformResourceURI("/SDMLanguage/", true);
		resourceSet.getURIConverter().getURIMap().put(sdmLanguagePlatformResourceURI,
				GenModelBuilder.createArchiveURI(sdmLanguageJarURI));
		final URI sdmLanguageGenModelURI = URI.createURI("model/SDMLanguage.genmodel")
				.resolve(sdmLanguagePlatformResourceURI);

		// Load SDMLanguage ecore
		final SDMEnhancedEcoreResource sdmEcoreResource = (SDMEnhancedEcoreResource) resourceSet
				.getResource(sdmLanguageGenModelURI.trimFileExtension().appendFileExtension("ecore"), true);
		sdmEcoreResource.setUseGeneratedEPackageResource(true);
		sdmEcoreResource.setHandleGeneratedEPackageURIs(true);
		// Load SDMLanguage genmodel
		final Resource sdmLanguageGenModelResource = resourceSet.getResource(sdmLanguageGenModelURI, true);
		final List<GenPackage> sdmGenPackages = ((GenModel) sdmLanguageGenModelResource.getContents().get(0))
				.getGenPackages();
		genModel.getUsedGenPackages().addAll(sdmGenPackages);

		// Initialize Democles URIs
		final URI democlesJarURI = URI
				.createPlatformResourceURI("/ControlFlow/lib/org.gervarro.democles.specification.emf.jar", true);
		final URI democlesArchiveURI = GenModelBuilder.createArchiveURI(democlesJarURI);
		final URI democlesGenModelURI = URI.createURI("model/specification.genmodel", true).resolve(democlesArchiveURI);

		// Load Democles ecore
		final SDMEnhancedEcoreResource democlesEcoreResource = (SDMEnhancedEcoreResource) resourceSet
				.getResource(democlesGenModelURI.trimFileExtension().appendFileExtension("ecore"), true);
		democlesEcoreResource.setUseGeneratedEPackageResource(true);
		democlesEcoreResource.setHandleGeneratedEPackageURIs(true);
		// Load Democles genmodel
		final Resource democlesLanguageGenModelResource = resourceSet.getResource(democlesGenModelURI, true);
		final List<GenPackage> democlesGenPackages = ((GenModel) democlesLanguageGenModelResource.getContents().get(0))
				.getGenPackages();
		genModel.getUsedGenPackages().addAll(democlesGenPackages);

		// Initialize ControlFlow URIs
		final URI cfGenModelURI = URI.createPlatformResourceURI("/ControlFlow/model/ControlFlow.genmodel", true);

		// Load ControlFlow Language ecore
		final SDMEnhancedEcoreResource cfEcoreResource = (SDMEnhancedEcoreResource) resourceSet
				.getResource(cfGenModelURI.trimFileExtension().appendFileExtension("ecore"), true);
		cfEcoreResource.setUseGeneratedEPackageResource(true);
		cfEcoreResource.setHandleGeneratedEPackageURIs(true);
		// Load ControlFlow genmodel
		final Resource cfLanguageGenModelResource = resourceSet.getResource(cfGenModelURI, true);
		final List<GenPackage> cfGenPackages = ((GenModel) cfLanguageGenModelResource.getContents().get(0))
				.getGenPackages();
		genModel.getUsedGenPackages().addAll(cfGenPackages);

		// Use Ecore model to create new GenModel
		final URI ecoreURI = genModelURI.trimFileExtension().appendFileExtension("ecore");
		final Resource ecoreResource = resourceSet.getResource(ecoreURI, true);

		// Add GenModel content
		final LinkedList<EPackage> ePackages = new LinkedList<EPackage>();
		for (final EObject eObject : ecoreResource.getContents()) {
			if (EcorePackage.eINSTANCE.getEPackage().isInstance(eObject)) {
				ePackages.add((EPackage) eObject);
			}
		}
		genModel.initialize(ePackages);
		if (saveGenModel && genModel.validate().isOK()) {
			// Save to file (with no options)
			final Map<String, String> saveOptions = new HashMap<>();
			saveOptions.put(Resource.OPTION_LINE_DELIMITER, WorkspaceHelper.DEFAULT_RESOURCE_LINE_DELIMITER);
			genModelResource.save(saveOptions);
		}
		return genModel;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		// final DemoclesMethodBodyHandler methodBodyHandler = new
		// DemoclesMethodBodyHandler(set);
		// final EMoflonCodeGenerationApp genModelBuilder = new
		// EMoflonCodeGenerationApp(set);
		// final CodeGenerator generator = new CodeGenerator(methodBodyHandler);
		//
		// set.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
		//
		// set.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/",
		// true), URI.createFileURI(args[0] + "\\"));
		//
		// genModelBuilder.loadDefaultSettings();
		//
		// PatternsPackage.eINSTANCE.eClass();
		// SequencerPackage.eINSTANCE.eClass();
		//
		// final URI sequencerGenModelURI =
		// URI.createPlatformResourceURI("/Sequencer/model/Sequencer.genmodel", true);
		// final GenModel genModel = genModelBuilder.buildGenModel(sequencerGenModelURI,
		// false);
		// methodBodyHandler.processGenModel(genModel);
		// generator.generateCode(genModel);
	}
}

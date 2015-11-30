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
package org.moflon.compiler.sdm.democles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.codegen.eclipse.NoOperationTask;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.compiler.sdm.democles.eclipse.DemoclesValidatorTask;
import org.moflon.compiler.sdm.democles.eclipse.MethodBodyResourceFactory;
import org.moflon.compiler.sdm.democles.eclipse.PatternResourceFactory;
import org.moflon.core.dfs.DFSGraph;
import org.moflon.core.dfs.DfsFactory;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.InefficientBootstrappingBuilder;
import org.moflon.sdm.compiler.democles.validation.controlflow.SDMActivityGraphBuilder;
import org.moflon.sdm.compiler.democles.validation.controlflow.Validator;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;

import SDMLanguage.activities.ActivitiesPackage;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.MoflonEOperation;

public class DemoclesMethodBodyHandler implements MethodBodyHandler {
   
	public static final String SDM_FILE_EXTENSION = "sdm";

	public static final String DFS_FILE_EXTENSION = "dfs";

	public static final String CONTROL_FLOW_FILE_EXTENSION = "cf";

	public static final String BINDING_AND_BLACK_FILE_EXTENSION = "bindingAndBlack";

	public static final String BLACK_FILE_EXTENSION = "black";

	public static final String RED_FILE_EXTENSION = "red";

	public static final String GREEN_FILE_EXTENSION = "green";

	public static final String BINDING_FILE_EXTENSION = "binding";

	public static final String EXPRESSION_FILE_EXTENSION = "expression";

	private final ResourceSet resourceSet;
	
	private final ScopeValidationConfigurator scopeValidatorConfiguration;
	
	public DemoclesMethodBodyHandler(final ResourceSet resourceSet,
			final ScopeValidationConfigurator scopeValidatorConfiguration) {
		this.resourceSet = resourceSet;
		this.scopeValidatorConfiguration = scopeValidatorConfiguration;
		initResourceSetForDemocles(this.resourceSet);
	}
		
	public static final void initResourceSetForDemocles(final ResourceSet resourceSet) {
		final MethodBodyResourceFactory sdmFactory = new MethodBodyResourceFactory(SDM_FILE_EXTENSION);
		final EList<AdapterFactory> adapterFactories = resourceSet.getAdapterFactories();
		final Map<String, Object> extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		
		adapterFactories.add(sdmFactory);
		extensionToFactoryMap.put(SDM_FILE_EXTENSION, sdmFactory);
		final MethodBodyResourceFactory dfsFactory = new MethodBodyResourceFactory(DFS_FILE_EXTENSION);
		adapterFactories.add(dfsFactory);
		extensionToFactoryMap.put(DFS_FILE_EXTENSION, dfsFactory);
		final MethodBodyResourceFactory cfFactory = new MethodBodyResourceFactory(CONTROL_FLOW_FILE_EXTENSION);
		adapterFactories.add(cfFactory);
		extensionToFactoryMap.put(CONTROL_FLOW_FILE_EXTENSION, cfFactory);

		final PatternResourceFactory bindingAndBlackFactory = new PatternResourceFactory(BINDING_AND_BLACK_FILE_EXTENSION);
		adapterFactories.add(bindingAndBlackFactory);
		extensionToFactoryMap.put(BINDING_AND_BLACK_FILE_EXTENSION, bindingAndBlackFactory);
		final PatternResourceFactory blackFactory = new PatternResourceFactory(BLACK_FILE_EXTENSION);
		adapterFactories.add(blackFactory);
		extensionToFactoryMap.put(BLACK_FILE_EXTENSION, blackFactory);
		final PatternResourceFactory redFactory = new PatternResourceFactory(RED_FILE_EXTENSION);
		adapterFactories.add(redFactory);
		extensionToFactoryMap.put(RED_FILE_EXTENSION, redFactory);
		final PatternResourceFactory greenFactory = new PatternResourceFactory(GREEN_FILE_EXTENSION);
		adapterFactories.add(greenFactory);
		extensionToFactoryMap.put(GREEN_FILE_EXTENSION, greenFactory);
		final PatternResourceFactory bindingFactory = new PatternResourceFactory(BINDING_FILE_EXTENSION);
		adapterFactories.add(bindingFactory);
		extensionToFactoryMap.put(BINDING_FILE_EXTENSION, bindingFactory);
		final PatternResourceFactory expressionFactory = new PatternResourceFactory(EXPRESSION_FILE_EXTENSION);
		adapterFactories.add(expressionFactory);
		extensionToFactoryMap.put(EXPRESSION_FILE_EXTENSION, expressionFactory);
	}

	@Override
   public IMonitoredJob createValidator(final EPackage ePackage) {
		return new DemoclesValidatorTask(scopeValidatorConfiguration.createScopeValidator(), ePackage);
	}
	
	/**
	 * Default null implementation 
	 */
	@Override
	public IMonitoredJob createGenModelProcessor(final MoflonCodeGenerator codeGenerator, final Resource resource) {
		return new NoOperationTask("GenModel processing");
	}

	/**
	 * Creates, configures and returns a {@link DemoclesGeneratorAdapterFactory}
	 */
	@Override
	public Descriptor createCodeGenerationEngine(final MoflonCodeGenerator codeGenerator, final Resource resource) {
		final TemplateConfigurationProvider templateConfig =
				scopeValidatorConfiguration.createTemplateConfiguration(codeGenerator.getGenModel());
		return new DemoclesGeneratorAdapterFactory(templateConfig, codeGenerator.getInjectorManager());
	}
	
	public static final Activity lookupActivity(final ResourceSet resourceSet, final EOperation eOperation) {
		if (ActivitiesPackage.eINSTANCE.getMoflonEOperation().isInstance(eOperation)) {
			final Activity activity = ((MoflonEOperation) eOperation).getActivity();
			if (activity != null) {
				activity.setOwningOperation(eOperation);
				return activity;
			}
		}
		final EAnnotation sdmAnnotation = eOperation.getEAnnotation("SDM");
		if (sdmAnnotation != null) {
			final AdapterResource sdmResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, SDM_FILE_EXTENSION);
			try {
				// Load SDM resource and handle activity
				final String sdmXMISpec = sdmAnnotation.getDetails().get("XMI");
				load(sdmResource, sdmXMISpec);
				// Handle activity
				final Activity activity = (Activity) sdmResource.getContents().get(0);
				activity.setOwningOperation(eOperation);
			} catch (final IOException exception) {
				// Do nothing
			}

			if (resourceSet != null && sdmResource != null && sdmResource.getResourceSet() == null) {
				resourceSet.getResources().add(sdmResource);
			}
			return (Activity) sdmResource.getContents().get(0);
		} else {
			return null;
		}
	}
		
	public static final ValidationReport performControlFlowValidation(final ResourceSet resourceSet,
			final EOperation eOperation, final Activity activity) {
		// Initialize control flow validator
		final Validator validator = ControlflowFactory.eINSTANCE.createValidator();
		validator.setStopNodeInForEachComponentSeverity(Severity.WARNING);
		final InefficientBootstrappingBuilder inefficientBuilder = ControlflowFactory.eINSTANCE.createInefficientBootstrappingBuilder();
		final SDMActivityGraphBuilder builder = ControlflowFactory.eINSTANCE.createSDMActivityGraphBuilder();
		final DFSGraph graph = DfsFactory.eINSTANCE.createDFSGraph();
		validator.setGraph(graph);
		inefficientBuilder.setGraph(graph);
		builder.setGraph(graph);
		validator.getBuilders().add(builder);
		validator.getBuilders().add(inefficientBuilder);
		inefficientBuilder.setDelegate(builder);

		// Add the DFS graph to the resource set
		final AdapterResource dfsGraphResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DFS_FILE_EXTENSION);
		if (resourceSet != null && dfsGraphResource.getResourceSet() == null) {
			resourceSet.getResources().add(dfsGraphResource);
		}
		dfsGraphResource.getContents().add(graph);
		validator.validate(activity, inefficientBuilder);
		return validator.getValidationReport();
	}
	
	// SDM loader methods
	private static final void load(final Resource resource, final String string) throws IOException {
		final InputStream in = new URIConverter.ReadableInputStream(string);
		resource.load(in, Collections.EMPTY_MAP);
		in.close();
	}
}

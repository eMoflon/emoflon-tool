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
package org.moflon.codegen;

import java.util.Map;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;

public interface MethodBodyHandler {

	/**
	 * Creates a validator job for the given package
	 * 
	 * The purpose of the validator is to turn an SDM-based specification of a
	 * method into a proper control flow
	 */
	public ITask createValidator(EPackage ePackage);

	/**
	 * Creates a job for processing the associated gen model of the given resource.
	 */
	public ITask createGenModelProcessor(MoflonCodeGenerator codeGenerator, Resource resource);

	/**
	 * Creates a descriptor for code generator of the method bodies.
	 */
	public Descriptor createCodeGenerationEngine(MoflonCodeGenerator codeGenerator, Resource resource);

	public Map<String, PatternMatcher> getPatternMatcherConfiguration();
}

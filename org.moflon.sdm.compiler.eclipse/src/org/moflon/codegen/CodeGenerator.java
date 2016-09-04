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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.eclipse.genmodel.GeneratorAdapterFactory;

public class CodeGenerator
{
   private final GeneratorAdapterFactory.Descriptor descriptor;

   public CodeGenerator(final Descriptor descriptor) {
	   this.descriptor = descriptor;
   }
   
   public boolean hasGeneratedMethodBody(final EOperation eOperation) {
	   return eOperation != null && EcoreUtil.getExistingAdapter(eOperation, "cf") != null;
   }

   public final IStatus generateCode(final GenModel genModel)
   {
      return generateCode(genModel, new BasicMonitor());
   }

   public final IStatus generateCode(final GenModel genModel, final Monitor monitor)
   {
      IStatus status = new Status(IStatus.OK, "org.eclipse.emf.common", "Code generation succeeded");
      if (status.isOK())
      {
         final Generator generator = new Generator();
         generator.getAdapterFactoryDescriptorRegistry().addDescriptor(GenModelPackage.eNS_URI, descriptor);
         generator.setInput(genModel);
         genModel.setCanGenerate(true);

         final Diagnostic diagnostic = generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, "EMoflon Code Generation", monitor);
         final int severity = diagnostic.getSeverity();
         if (Diagnostic.OK != severity)
         {
            status = new Status(severity, "org.eclipse.emf.common", severity, diagnostic.getMessage(), diagnostic.getException());
         }
      }
      return status;
   }

   public static final URI getClassURI(final EClass eClass)
   {
      return eClass.eResource().getURI().trimSegments(1).appendSegment(MoflonUtil.getFQN(eClass));
   }

   public static final URI getOperationURI(final EOperation eOperation)
   {
      return eOperation.eResource().getURI().trimSegments(1).appendSegment(getOperationID(eOperation));
   }

   public static final String getOperationID(final EOperation eOperation)
   {
      String signature = MoflonUtil.getFQN(eOperation.getEContainingClass()) + "_" + eOperation.getName();
      for (final EParameter param : eOperation.getEParameters())
      {
         signature += "_" + param.getName() + "_" + getNameOfClassifier(param.getEType());
      }
      return signature;
   }

   /**
    * Returns the name of the associated Java class of the given type.
    */
   private static final String getNameOfClassifier(final EClassifier type)
   {
      if (type == null)
         throw new IllegalArgumentException("type may not be null");
      return type.getInstanceTypeName() != null ? type.getInstanceTypeName() : type.getName();
   }
}

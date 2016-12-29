package org.moflon.eclipse.genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;

/**
 * Instances of this class provide code to the code generation process
 */
public interface MoflonClassGeneratorCodeContributor
{

   String getPreGetGenFeatureCode(GenFeature genFeature);

   String getPreSetGenFeatureCode(GenFeature genFeature);

   String getConstructorInjectionCode(GenClass genClass);

}

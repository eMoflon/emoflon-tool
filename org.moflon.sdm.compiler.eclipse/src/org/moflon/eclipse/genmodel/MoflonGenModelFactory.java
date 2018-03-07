package org.moflon.eclipse.genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenModelFactoryImpl;

public class MoflonGenModelFactory extends GenModelFactoryImpl {

	public GenPackage createGenPackage() {
		return new MoflonGenPackage();
	}

	public GenClass createGenClass() {
		return new MoflonGenClass();
	}
}

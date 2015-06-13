package org.moflon.backend.ecore2fujaba;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.eclipse.genmodel.MoflonClassGeneratorAdapter;

class FujabaClassGeneratorAdapter extends MoflonClassGeneratorAdapter {

	FujabaClassGeneratorAdapter(
			FujabaMethodBodyHandler generatorAdapterFactory) {
		super(generatorAdapterFactory);
	}

	public final FujabaMethodBodyHandler getAdapterFactory() {
		return (FujabaMethodBodyHandler) adapterFactory;
	}

	@Override
	public String getGeneratedMethodBody(final EOperation eOperation) {
		return getAdapterFactory().getGeneratedMethodBody(eOperation);
	}

	@Override
	public void handleImports(final boolean isImplementation) {
		final GenClass genClass = (GenClass) generatingObject;
		getAdapterFactory().handleImports(genClass.getEcoreClass(), isImplementation);
		super.handleImports(isImplementation);
	}
}

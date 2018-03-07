package org.moflon.compiler.sdm.democles;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.gervarro.democles.codegen.ImportManager;

public final class GenModelToDemoclesImportManager implements ImportManager {
	private final GenModel genModel;

	public GenModelToDemoclesImportManager(final GenModel genModel) {
		this.genModel = genModel;
	}

	@Override
	public final List<String> getImportList() {
		return new ArrayList<String>(genModel.getImportManager().getImports());
	}

	@Override
	public final String getImportedName(final String fullyQualifiedName) {
		return genModel.getImportedName(fullyQualifiedName);
	}

	@Override
	public final void upload(final String fullyQualifiedName) {
		// Do nothing
	}
}

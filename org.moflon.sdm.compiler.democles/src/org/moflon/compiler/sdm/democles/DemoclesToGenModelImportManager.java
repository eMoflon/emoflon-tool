package org.moflon.compiler.sdm.democles;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.gervarro.democles.codegen.ImportManager;

public final class DemoclesToGenModelImportManager implements ImportManager {
	private final GenModel genModel;

	public DemoclesToGenModelImportManager(final GenModel genModel) {
		this.genModel = genModel;
	}

	@Override
	public final List<String> getImportList() {
		return Collections.emptyList();
	}

	@Override
	public final String getImportedName(final String fullyQualifiedName) {
		upload(fullyQualifiedName);
		return fullyQualifiedName;
	}

	@Override
	public final void upload(final String fullyQualifiedName) {
		genModel.addImport(fullyQualifiedName);
	}
}

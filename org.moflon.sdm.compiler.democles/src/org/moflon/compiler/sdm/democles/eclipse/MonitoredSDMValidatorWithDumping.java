package org.moflon.compiler.sdm.democles.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.preferences.EMoflonPreferencesStorage;

public class MonitoredSDMValidatorWithDumping extends MonitoredSDMValidator {

	public MonitoredSDMValidatorWithDumping(final IFile ecoreFile, final EMoflonPreferencesStorage preferencesStorage) {
		super(ecoreFile, preferencesStorage);
	}

	@Override
	protected DemoclesValidationProcess createValidationProcess(ResourceSet resourceSet) {
		return new DemoclesValidationProcess(getEcoreFile(), resourceSet, true, this.getPreferencesStorage());
	}
}

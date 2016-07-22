package org.moflon.ide.visualization.dot.ecore.epackageviz;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;

public class EPackageDiagrammTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof EPackage;
	}

	@Override
	protected String getPluginId() {
		return EPackageVisualizationPlugin.getDefault().getPluginId();
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return EpackagevizPackage.eINSTANCE;
		
	}

}

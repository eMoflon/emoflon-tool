package org.moflon.ide.visualization.dot.ecore.eclassviz;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;

public class EClassDiagramTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof EClass;
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return EclassvizPackage.eINSTANCE;
	}

}

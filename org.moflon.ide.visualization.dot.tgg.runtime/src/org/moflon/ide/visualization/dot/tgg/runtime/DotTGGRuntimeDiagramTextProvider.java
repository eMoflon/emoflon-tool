package org.moflon.ide.visualization.dot.tgg.runtime;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.runtime.PrecedenceStructure;

public class DotTGGRuntimeDiagramTextProvider extends EMoflonDiagramTextProvider {
	@Override
	protected boolean directionIsForward() {
		return true;
	}

	@Override
	protected EPackage getPackage() {
		return RuntimePackage.eINSTANCE;
	}

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof PrecedenceStructure;
	}

	@Override
	public boolean supportsSelection(final ISelection selection) {
		return true;
	}
}

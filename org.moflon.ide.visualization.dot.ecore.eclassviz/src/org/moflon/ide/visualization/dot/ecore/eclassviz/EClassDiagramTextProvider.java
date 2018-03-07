package org.moflon.ide.visualization.dot.ecore.eclassviz;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.ecore.eclassviz.utils.EClassPostProcessor;
import org.moflon.tgg.runtime.CorrespondenceModel;

public class EClassDiagramTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof EClass;
	}

	@Override
	public boolean supportsSelection(final ISelection selection) {
		return true;
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return EclassvizPackage.eINSTANCE;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(corr -> new EClassPostProcessor().postProcess(corr));
	}
}

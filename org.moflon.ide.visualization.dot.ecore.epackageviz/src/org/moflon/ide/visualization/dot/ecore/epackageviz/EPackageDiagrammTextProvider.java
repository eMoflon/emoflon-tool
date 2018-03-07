package org.moflon.ide.visualization.dot.ecore.epackageviz;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.language.AbstractGraph;
import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;

public class EPackageDiagrammTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof EPackage;
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
		return EpackagevizPackage.eINSTANCE;
	}

	protected String unparse(EObject input, EObject selectedElement) {
		AbstractGraph graph = EcoreUtil.copy(modelToDot(input));
		EPackage ePackage = EPackage.class.cast(selectedElement);
		for (AbstractGraph subGraph : graph.getSubGraphs()) {
			if (subGraph.getName().compareTo(ePackage.getName()) == 0) {
				ClassGraph superGraph = ClassGraph.class.cast(graph);
				ClassGraph classGraph = ClassGraph.class.cast(subGraph);
				classGraph.getSkinparams().addAll(superGraph.getSkinparams());
				graph = classGraph;
				break;
			}
		}
		return unparse(graph);
	}

	@Override
	protected EObject getInput(EObject selectedElement) {
		EPackage selectedPackage = EPackage.class.cast(selectedElement);
		return getRootPackage(selectedPackage);
	}

	private EPackage getRootPackage(EPackage currentPackage) {
		EPackage superPackage = currentPackage.getESuperPackage();
		if (superPackage == null)
			return currentPackage;
		else
			return getRootPackage(superPackage);
	}

	@Override
	protected void registerConfigurator(SynchronizationHelper helper) {
		helper.setConfigurator(new EPackageVisualizationConfigurator());
	}
}

package org.moflon.ide.visualization.dot.tgg.delta;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.tgg.delta.utils.DeltaPostProcessingHelper;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.DeltaSpecification;

public class DeltaDiagrammTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof DeltaSpecification;
	}

	@Override
	protected String getPluginId() {
		return DeltaVisualizationPlugin.getDefault().getPluginId();
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return DeltaPackage.eINSTANCE;
	}
	
	@Override
	protected void postprocess(CorrespondenceModel corrs){
		corrs.getCorrespondences().forEach(DeltaPostProcessingHelper.getHelper()::postProcess);
		DeltaPostProcessingHelper.clear();
	}

}

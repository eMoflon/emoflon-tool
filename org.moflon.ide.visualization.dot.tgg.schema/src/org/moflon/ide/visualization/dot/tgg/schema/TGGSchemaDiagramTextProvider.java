package org.moflon.ide.visualization.dot.tgg.schema;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.language.TripleGraphGrammar;


public class TGGSchemaDiagramTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof TripleGraphGrammar;
	}

	@Override
	protected String getPluginId() {
		return SchemaVisualizationPlugin.getDefault().getPluginId();
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return SchemaPackage.eINSTANCE;
	}



}

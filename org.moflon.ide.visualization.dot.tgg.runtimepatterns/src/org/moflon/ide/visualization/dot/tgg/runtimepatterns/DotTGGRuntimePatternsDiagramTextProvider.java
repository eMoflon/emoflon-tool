package org.moflon.ide.visualization.dot.tgg.runtimepatterns;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.language.DotColor;
import org.moflon.ide.visualization.dot.language.EdgeCommand;
import org.moflon.ide.visualization.dot.language.Record;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.TripleMatch;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;

public class DotTGGRuntimePatternsDiagramTextProvider extends EMoflonDiagramTextProvider {
	@Override
	protected boolean directionIsForward() {
		return true;
	}

	@Override
	protected EPackage getPackage() {
		return RuntimepatternsPackage.eINSTANCE;
	}

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof TripleMatch;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(this::postprocess);
	}

	@Override
	public boolean supportsSelection(final ISelection selection) {
		return true;
	}

	private void postprocess(EObject corr) {
		if (corr instanceof TripleMatchNodeMappingToRecord) {
			postprocessTripleMatchNodeMappingToRecord(TripleMatchNodeMappingToRecord.class.cast(corr));
		} else if (corr instanceof EMoflonEdgeToEdgeCommand) {
			postprocessEMoflonEdgeToEdgeCommand(EMoflonEdgeToEdgeCommand.class.cast(corr));
		}
	}

	private void postprocessEMoflonEdgeToEdgeCommand(EMoflonEdgeToEdgeCommand corr) {
		EMoflonEdge edge = corr.getSource();
		EdgeCommand lv = corr.getTarget();
		TripleMatch tripleMatch = (TripleMatch) edge.eContainer();
		if (tripleMatch.getCreatedElements().contains(edge))
			lv.setColor(DotColor.GREEN);
	}

	private void postprocessTripleMatchNodeMappingToRecord(TripleMatchNodeMappingToRecord corr) {
		Record record = corr.getTarget();
		TripleMatchNodeMapping mapping = corr.getSource();
		TripleMatch tripleMatch = (TripleMatch) mapping.eContainer();
		record.setLabel(mapping.getNodeName() + " ==> " + eMoflonEMFUtil.getIdentifier(mapping.getNode()));
		if (tripleMatch.getCreatedElements().contains(mapping.getNode()))
			record.setColor(DotColor.GREEN);
	}
}

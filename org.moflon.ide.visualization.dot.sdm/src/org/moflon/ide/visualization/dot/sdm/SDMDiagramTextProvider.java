package org.moflon.ide.visualization.dot.sdm;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.language.NodeCommand;
import org.moflon.ide.visualization.dot.language.RecordEntry;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.util.eMoflonSDMUtil;

import SDMLanguage.activities.Activity;
import SDMLanguage.activities.StopNode;
import SDMLanguage.calls.callExpressions.MethodCallExpression;

public class SDMDiagramTextProvider extends EMoflonDiagramTextProvider {
	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return SdmPackage.eINSTANCE;
	}

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof Activity;
	}

	@Override
	public boolean supportsSelection(final ISelection selection) {
		return true;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(this::postprocess);
	}

	private void postprocess(EObject corr) {
		if (corr instanceof NodeCommandToStopNode)
			postProcessStopNode(NodeCommandToStopNode.class.cast(corr));

		if (corr instanceof RecordToMethodCall)
			postProcessMethodCall(RecordToMethodCall.class.cast(corr));
	}

	private void postProcessMethodCall(RecordToMethodCall corr) {
		RecordEntry entry = corr.getSource();
		MethodCallExpression methodCallExp = corr.getTarget();

		entry.setValue(eMoflonSDMUtil.extractMethodCall(methodCallExp, ""));
	}

	private void postProcessStopNode(NodeCommandToStopNode corr) {
		StopNode activityNode = (StopNode) corr.getTarget();
		NodeCommand node = corr.getSource();

		String stopNodeLabel = eMoflonSDMUtil.extractExpression(activityNode.getReturnValue(), "");
		if ("null".equals(stopNodeLabel))
			stopNodeLabel = "void";

		node.setLabel(stopNodeLabel);
	}
}

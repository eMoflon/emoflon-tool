package org.moflon.ide.visualization.dot.tgg.delta.utils;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.visualization.dot.language.AbstractNode;
import org.moflon.ide.visualization.dot.language.EdgeCommand;
import org.moflon.ide.visualization.dot.language.NodeCommand;
import org.moflon.ide.visualization.dot.language.RecordEntry;
import org.moflon.ide.visualization.dot.tgg.delta.EdgeCommandToEMoflonEdge;
import org.moflon.ide.visualization.dot.tgg.delta.NodeCommandToEMoflonEdge;
import org.moflon.ide.visualization.dot.tgg.delta.RecordEntryToAttributeDelta;
import org.moflon.ide.visualization.dot.tgg.delta.RecordToNode;
import org.moflon.tgg.runtime.AttributeDelta;
import org.moflon.tgg.runtime.EMoflonEdge;

public class DeltaPostProcessingHelper {

	public void postProcess(EObject corr) {
		if (corr instanceof RecordToNode) {
			postProcessNode(RecordToNode.class.cast(corr));
		}

		if (corr instanceof NodeCommandToEMoflonEdge) {
			createLabel(NodeCommandToEMoflonEdge.class.cast(corr));
		}

		if (corr instanceof EdgeCommandToEMoflonEdge) {
			createLabel(EdgeCommandToEMoflonEdge.class.cast(corr));
		}

		if (corr instanceof RecordEntryToAttributeDelta) {
			createEntry(RecordEntryToAttributeDelta.class.cast(corr));
		}
	}

	private void createEntry(RecordEntryToAttributeDelta re2ad) {
		RecordEntry entry = re2ad.getSource();
		AttributeDelta attributeDelta = re2ad.getTarget();
		String changement = attributeDelta.getOldValue() + " ==\\> " + attributeDelta.getNewValue();
		EAttribute attribute = attributeDelta.getAffectedAttribute();
		String attributeText = attribute.getName();

		entry.setValue(attributeText + ": " + changement);
	}

	private void createLabel(EdgeCommandToEMoflonEdge ec2ee) {
		EdgeCommand ec = ec2ee.getSource();
		AbstractNode srcNode = ec.getSource();
		AbstractNode trgNode = ec.getTarget();

		EMoflonEdge ee = ec2ee.getTarget();
		EObject srcEObject = ee.getSrc();
		EObject trgEObject = ee.getTrg();

		srcNode.setLabel(createGoodNameToIdentify(srcEObject));
		trgNode.setLabel(createGoodNameToIdentify(trgEObject));
	}

	private String createGoodNameToIdentify(EObject eObject) {
		return (eMoflonEMFUtil.getIdentifier(eObject) + "(" + eObject.hashCode() + ")").replace("\"", "'");
	}

	private void createLabel(NodeCommandToEMoflonEdge nc2ee) {
		NodeCommand node = nc2ee.getSource();
		EMoflonEdge ee = nc2ee.getTarget();
		EClass eClass = ee.getTrg().eClass();
		node.setLabel(ee.getName() + " : " + eClass.getName());
	}

	private void postProcessNode(RecordToNode n2N) {
		NodeCommand srcNode = n2N.getSource();
		EObject trgNode = n2N.getTarget();
		srcNode.setLabel(createGoodNameToIdentify(trgNode));
	}
}

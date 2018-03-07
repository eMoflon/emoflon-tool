package org.moflon.ide.visualization.dot.ecore.eclassviz.utils;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;
import org.moflon.ide.visualization.dot.ecore.eclassviz.PTypedObjectToETypedElememt;
import org.moflon.ide.visualization.dot.language.PTypedObject;
import org.moflon.ide.visualization.dot.language.PVisibilityObject;

public class EClassPostProcessor {
	public void postProcess(EObject corr) {
		if (corr instanceof PTypedObjectToETypedElememt) {
			handleTyping(PTypedObjectToETypedElememt.class.cast(corr));
		}
	}

	private void handleTyping(PTypedObjectToETypedElememt pte2ete) {
		PTypedObject pTypeElement = pte2ete.getSource();
		ETypedElement eTypeElement = pte2ete.getTarget();

		EClassifier eType = eTypeElement.getEType();

		if (eType == null) {
			pTypeElement.setType("void");
		} else if (eType != null && eTypeElement.getUpperBound() > 1) {
			pTypeElement.setType("EList<" + eType.getName() + ">");
		} else {
			pTypeElement.setType(eType.getName());
		}

		if (pTypeElement instanceof PVisibilityObject) {
			repairVisibility(PVisibilityObject.class.cast(pTypeElement));
		}
	}

	private void repairVisibility(PVisibilityObject pvo) {
		switch (pvo.getVisibilty()) {
		case PRIVATE_VIEW:
			pvo.setSign("-");
			break;
		case PROTECTED_VIEW:
			pvo.setSign("#");
			break;
		case PACKAGE_ONLY:
			pvo.setSign("~");
			break;
		case PUBLIC_VIEW:
			pvo.setSign("+");
			break;
		default:
			break;
		}
	}
}

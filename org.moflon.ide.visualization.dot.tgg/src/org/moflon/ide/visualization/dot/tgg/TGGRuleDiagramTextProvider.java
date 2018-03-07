package org.moflon.ide.visualization.dot.tgg;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.language.LabelEntry;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.csp.AttributeVariable;
import org.moflon.tgg.language.csp.Literal;
import org.moflon.tgg.language.csp.LocalVariable;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.runtime.CorrespondenceModel;

public class TGGRuleDiagramTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof TGGRule;
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
		return TggPackage.eINSTANCE;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(this::postprocess);
	}

	private void postprocess(EObject corr) {
		if (corr instanceof LabelEntryToTGGConstraint) {
			postprocessRecordToObjectVariable(LabelEntryToTGGConstraint.class.cast(corr));
		}
	}

	private void postprocessRecordToObjectVariable(LabelEntryToTGGConstraint corr) {
		TGGConstraint constraint = corr.getTarget();
		LabelEntry labelEntry = corr.getSource();

		String variableList = constraint.getVariables().stream().collect(StringBuilder::new, (result, elt) -> {
			if (!result.toString().isEmpty())
				result.append(",");
			result.append(getVariableName(elt));
		}, (str1, str2) -> {
			str1.append(",").append(str2);
		}).toString();

		labelEntry.setValue(constraint.getName() + "(" + variableList.replace("\"", "\\\"") + ")");
	}

	private String getVariableName(Variable elt) {
		if (elt instanceof AttributeVariable) {
			AttributeVariable attrVar = (AttributeVariable) elt;
			return attrVar.getName();
		} else if (elt instanceof Literal) {
			return elt.getValue().toString();
		} else {
			return ((LocalVariable) elt).getName();
		}
	}
}

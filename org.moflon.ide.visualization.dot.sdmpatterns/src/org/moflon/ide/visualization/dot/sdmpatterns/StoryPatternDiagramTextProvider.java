package org.moflon.ide.visualization.dot.sdmpatterns;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.Record;
import org.moflon.ide.visualization.dot.language.RecordEntry;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.util.eMoflonSDMUtil;

import SDMLanguage.patterns.AttributeAssignment;
import SDMLanguage.patterns.Constraint;
import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.StoryPattern;

public class StoryPatternDiagramTextProvider extends EMoflonDiagramTextProvider {
	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof StoryPattern && notATGGRule(selectedElement);
	}

	@Override
	public boolean supportsSelection(final ISelection selection) {
		return true;
	}

	private boolean notATGGRule(Object selection) {
		if (selection instanceof TGGRule) {
			TGGRule rule = (TGGRule) selection;
			return rule.getTripleGraphGrammar() == null;
		}

		return true;
	}

	@Override
	protected boolean directionIsForward() {
		return false;
	}

	@Override
	protected EPackage getPackage() {
		return SdmpatternsPackage.eINSTANCE;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(this::postprocess);
	}

	private void postprocess(EObject corr) {
		if (corr instanceof RecordToObjectVariable) {
			postprocessRecordToObjectVariable(RecordToObjectVariable.class.cast(corr));
		}
	}

	private void postprocessRecordToObjectVariable(RecordToObjectVariable corr) {
		ObjectVariable trg__ov = corr.getTarget();
		Record ov = corr.getSource();

		for (Constraint constraint : trg__ov.getConstraint()) {
			RecordEntry entry = LanguageFactory.eINSTANCE.createRecordEntry();
			entry.setValue(eMoflonSDMUtil.extractConstraint(constraint).replace("\"", "\\\""));
			ov.getEntries().add(entry);
		}

		for (AttributeAssignment attributeAssignment : trg__ov.getAttributeAssignment()) {
			RecordEntry entry = LanguageFactory.eINSTANCE.createRecordEntry();
			entry.setValue(eMoflonSDMUtil.extractAttributeAssignment(attributeAssignment).replace("\"", "\\\""));
			ov.getEntries().add(entry);
		}

		if (trg__ov.getBindingExpression() != null) {
			ov.setLabel(ov.getLabel() + " := "
					+ eMoflonSDMUtil.extractExpression(trg__ov.getBindingExpression(), "").replace("\"", "\\\""));
		}
	}
}

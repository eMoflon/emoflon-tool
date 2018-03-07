package org.moflon.ide.visualization.dot.tgg.delta;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.ide.visualization.dot.tgg.delta.utils.DeltaPostProcessingHelper;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.configuration.RuleResult;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.DeltaSpecification;

public class DeltaDiagrammTextProvider extends EMoflonDiagramTextProvider {

	@Override
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof DeltaSpecification;
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
		return DeltaPackage.eINSTANCE;
	}

	@Override
	protected void postprocess(CorrespondenceModel corrs) {
		corrs.getCorrespondences().forEach(corr -> new DeltaPostProcessingHelper().postProcess(corr));
	}

	@Override
	protected void registerConfigurator(SynchronizationHelper helper) {
		helper.setConfigurator(new Configurator() {
			@Override
			public RuleResult chooseOne(Collection<RuleResult> alternatives) {
				return alternatives.stream().filter(rr -> rr.getRule().contains("Existing")).findAny()
						.orElse(Configurator.super.chooseOne(alternatives));
			}
		});
	}

}

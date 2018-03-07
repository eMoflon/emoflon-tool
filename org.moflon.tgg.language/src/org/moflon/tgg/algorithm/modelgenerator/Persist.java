package org.moflon.tgg.algorithm.modelgenerator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;

public class Persist {

	public static void persist(DataContainer state) {

		List<EObject> src = new ArrayList<EObject>();
		src.addAll(state.getSrcTempOutputContainer().getPotentialRoots());

		List<EObject> trg = new ArrayList<EObject>();
		trg.addAll(state.getTrgTempOutputContainer().getPotentialRoots());

		String folderName = "instances/generatedModels_" + generateTimeStamp() + "/";

		if (src.size() > 1) {
			state.getCorrespondenceModel().setSource(state.getSrcTempOutputContainer());
			String fileName = folderName + state.getModelgenStats().getSrcElementCount() + "_"
					+ state.getModelgenStats().getSrcConnectorCount() + "_SrcRootContainer.xmi";
			eMoflonEMFUtil.saveModel(state.getResourceSet(), state.getSrcTempOutputContainer(), fileName);
		} else if (src.size() == 1) {
			EObject sourceRoot = src.get(0);
			state.getSrcTempOutputContainer().getPotentialRoots().remove(0);

			String fileName = folderName + state.getModelgenStats().getSrcElementCount() + "_"
					+ state.getModelgenStats().getSrcConnectorCount() + "_SourceModel.xmi";
			state.getResourceSet().createResource(eMoflonEMFUtil.createFileURI(fileName, false)).getContents()
					.add(sourceRoot);
			eMoflonEMFUtil.saveModel(state.getResourceSet(), sourceRoot, fileName);
		}

		if (trg.size() > 1) {
			state.getCorrespondenceModel().setTarget(state.getTrgTempOutputContainer());
			String fileName = folderName + state.getModelgenStats().getTrgElementCount() + "_"
					+ state.getModelgenStats().getTrgConnectorCount() + "_TrgRootContainer.xmi";
			eMoflonEMFUtil.saveModel(state.getResourceSet(), state.getTrgTempOutputContainer(), fileName);
		} else if (trg.size() == 1) {
			EObject targetRoot = trg.get(0);
			state.getTrgTempOutputContainer().getPotentialRoots().remove(0);
			String fileName = folderName + state.getModelgenStats().getTrgElementCount() + "_"
					+ state.getModelgenStats().getTrgConnectorCount() + "_TargetModel.xmi";
			state.getResourceSet().createResource(eMoflonEMFUtil.createFileURI(fileName, false)).getContents()
					.add(targetRoot);
			eMoflonEMFUtil.saveModel(state.getResourceSet(), targetRoot, fileName);

		}
	}

	private static String generateTimeStamp() {

		return "" + System.nanoTime();
	}

}

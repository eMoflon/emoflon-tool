package org.moflon.tie;

import java.io.IOException;
import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.moflon.moca.MocaMain;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;

import SokobanCodeAdapter.SokobanCodeAdapterPackage;

public class TGGMain extends SynchronizationHelper {

	/* Constructor */
	public TGGMain() throws IOException {
		super(SokobanCodeAdapterPackage.eINSTANCE, ".");
	}
	
	public TGGMain(String pathToProject){
		super(SokobanCodeAdapterPackage.eINSTANCE, pathToProject);
	}
	
	/**
	 * Executes if you run TGGmain on its own; Driver for a complete unidirectional
	 * transformation-
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		/* Set up logging */
        BasicConfigurator.configure();

        TGGMain helper = new TGGMain();
        helper.performParse("instances/in/myLevels/sample.sok","instances/tree.xmi");
        
        helper = new TGGMain();
		helper.performForward("instances/tree.xmi");
		 
		helper = new TGGMain();
		helper.performBackward("instances/tree.xmi_FWD.xmi");
		
 		helper.performUnParse("instances/out", helper, "output.sok");
	}

	/**
	 * Executes transformation from tree-to-model
	 * @param source MocaTree model
	 */
	public void performForward(String source) {
		try {
			loadSrc(source);
		} catch (IllegalArgumentException iae) {
			System.err.println("Unable to load " + source + ", " + iae.getMessage());
			return;
		}
		
		integrateForward();
		saveTrg(source + "_FWD.xmi");
		saveCorr("instances/corr_FWD.xmi");
		saveSynchronizationProtocol("instances/protocol_FWD.xmi");		
	}

	/**
	 * Executes transformation from model-to-tree
	 * @param target Sokoban model
	 */
	public void performBackward(String target) {
		try {
			loadTrg(target);
		} catch (IllegalArgumentException iae) {
			System.err.println("Unable to load " + target + ", " + iae.getMessage());
			return;
		}
		
		integrateBackward();
		saveSrc(target + "_BWD.xmi");
		saveCorr("instances/corr_BWD.xmi");
		saveSynchronizationProtocol("instances/protocol_BWD.xmi");
	}
	
	/**
	 * Translates textual instance into Sokoban model
	 * @param src '.sok' file
	 * @param target '.xmi' file name
	 */
	public void performParse(String src, String target){
		MocaTree.File file = MocaMain.getCodeAdapter().parseFile(new File(src), null);
        eMoflonEMFUtil.saveModel(file, target);        
	}
	
	/**
	 * Translates Sokoban model into textual instance
	 * @param target directory
	 * @param helper
	 * @param nameOfFile target '.sok' file name
	 */
	public void performUnParse(String target, TGGMain helper, String nameOfFile){
		MocaTree.File createdFile = (MocaTree.File) helper.getSrc();
		createdFile.setName(nameOfFile);
		
		MocaMain.getCodeAdapter().unparseFile(createdFile, new File(target));
	}
	
}
package de.fhg.iao.matrixbrowser.toolnet_demo;
import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class CMFunktion3 implements ICommand {

	private static final Logger log = Logger.getLogger(CMFunktion3.class);

	public CMFunktion3() {
	}

	public String getName() {
		return "Funktion3";
	}

	public void execute() {
		log.debug("Funktion3");
	}

}

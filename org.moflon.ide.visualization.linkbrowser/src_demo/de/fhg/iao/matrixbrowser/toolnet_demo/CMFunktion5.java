package de.fhg.iao.matrixbrowser.toolnet_demo;
import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class CMFunktion5 implements ICommand {

	private static final Logger log = Logger.getLogger(CMFunktion5.class);

	public CMFunktion5() {
	}

	public String getName() {
		return "Funktion5";
	}

	public void execute() {
		log.debug("Funktion5");
	}

}

package de.fhg.iao.matrixbrowser.toolnet_demo;
import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class CMFunktion4 implements ICommand {
	private static final Logger log = Logger.getLogger(CMFunktion4.class);

	public CMFunktion4() {
	}

	public String getName() {
		return "Funktion4";
	}

	public void execute() {
		log.debug("Funktion4");
	}

}

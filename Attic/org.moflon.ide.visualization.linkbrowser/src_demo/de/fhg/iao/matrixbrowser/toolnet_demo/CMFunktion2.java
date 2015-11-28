package de.fhg.iao.matrixbrowser.toolnet_demo;
import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 * 
 */
public class CMFunktion2 implements ICommand {
	private static final Logger log = Logger.getLogger(CMFunktion2.class);

	public CMFunktion2() {
	}

	public String getName() {
		return "Funktion2";
	}

	public void execute() {
		log.debug("Funktion2");
	}

}

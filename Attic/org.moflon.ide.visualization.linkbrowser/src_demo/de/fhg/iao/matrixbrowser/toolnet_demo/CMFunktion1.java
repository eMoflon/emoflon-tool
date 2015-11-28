package de.fhg.iao.matrixbrowser.toolnet_demo;
import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/*
 * Created on 28.04.2005
 */

/**
 * @author Mr.Marwan
 */
public class CMFunktion1 implements ICommand {

	private static final Logger log = Logger.getLogger(CMFunktion1.class);

	public CMFunktion1() {
	}

	public String getName() {
		return "Funktion1";
	}

	public void execute() {
		log.debug("Funktion1");
	}

}

package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class RepairCommand implements ICommand {

	private static final Logger log = Logger.getLogger(RepairCommand.class);

	public RepairCommand() {
	}

	public String getName() {
		return "Repair Link";
	}

	public void execute() {
		log.debug("Repair Link pressed");
	}

}

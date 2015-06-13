package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class MultiplicityCommand implements ICommand {

	private static final Logger log = Logger
			.getLogger(MultiplicityCommand.class);

	public MultiplicityCommand() {
	}

	public String getName() {
		return "Check Multiplicity";
	}

	public void execute() {
		log.debug("Check Multiplicity pressed");
	}

}

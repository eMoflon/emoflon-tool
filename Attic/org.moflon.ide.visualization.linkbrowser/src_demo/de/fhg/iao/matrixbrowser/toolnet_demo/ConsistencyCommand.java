package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class ConsistencyCommand implements ICommand {
	private static final Logger log = Logger
			.getLogger(ConsistencyCommand.class);

	public ConsistencyCommand() {
	}

	public String getName() {
		return "Check Consistency";
	}

	public void execute() {
		log.debug("Check Consistency pressed");
	}

}

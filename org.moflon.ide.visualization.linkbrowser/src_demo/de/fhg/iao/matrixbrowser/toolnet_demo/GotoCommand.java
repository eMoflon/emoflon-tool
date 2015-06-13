package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class GotoCommand implements ICommand {

	private static final Logger log = Logger.getLogger(GotoCommand.class);

	public GotoCommand() {
	}

	public String getName() {
		return "Goto Object";
	}

	public void execute() {
		log.debug("Goto Object pressed");
	}

}

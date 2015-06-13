package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class HighlightCommand implements ICommand {

	private static final Logger log = Logger.getLogger(HighlightCommand.class);

	public HighlightCommand() {
	}

	public String getName() {
		return "Highlight Object";
	}

	public void execute() {
		log.debug("Highlight Object pressed");
	}

}

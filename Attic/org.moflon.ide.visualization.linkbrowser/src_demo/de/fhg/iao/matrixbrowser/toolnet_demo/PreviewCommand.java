package de.fhg.iao.matrixbrowser.toolnet_demo;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;

/**
 * @author Mr.Marwan
 */
public class PreviewCommand implements ICommand {

	private static final Logger log = Logger.getLogger(PreviewCommand.class);

	public PreviewCommand() {
	}

	public String getName() {
		return "Preview Object";
	}

	public void execute() {
		log.debug("Preview Object pressed");
	}

}

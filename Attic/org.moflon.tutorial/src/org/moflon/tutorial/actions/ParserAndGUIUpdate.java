package org.moflon.tutorial.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.moflon.tutorial.Common;


public class ParserAndGUIUpdate extends Action implements ICheatSheetAction {
   private static final Logger logger = Logger.getLogger(ParserAndGUIUpdate.class);

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		try {
			new CopyResourceToProject().run(new String[] {Common.PARSER_RESOURCES_LOCATION, Common.GENERATED_TGG_PROJECT_NAME, "nobuild"}, manager);
			new UpdateGUIProject().run(null, manager);
		} catch (Exception e) {
			logger.error("An unexpected error occurred in action 'ParserAndGUIUpdate':\n" + e.getMessage());
			e.printStackTrace();
		}

	}

}

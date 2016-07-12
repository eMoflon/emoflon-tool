package org.moflon.ide.visualisation.dot.language;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ToggleRefinementHandler extends AbstractHandler {

	private static boolean showRefinements = true;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		showRefinements = !showRefinements;
		return null;
	}

	public static String getTGGFileWithRules(){
		return showRefinements? ".pre.tgg.xmi" : ".tgg.xmi";
	}

}

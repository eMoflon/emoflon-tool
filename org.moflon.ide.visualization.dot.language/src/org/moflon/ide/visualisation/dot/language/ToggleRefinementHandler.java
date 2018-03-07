package org.moflon.ide.visualisation.dot.language;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ToggleRefinementHandler extends AbstractHandler {

	private static boolean flattenRefinements = true;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		flattenRefinements = !flattenRefinements;
		return null;
	}

	public static boolean flattenRefinements() {
		return flattenRefinements;
	}
}

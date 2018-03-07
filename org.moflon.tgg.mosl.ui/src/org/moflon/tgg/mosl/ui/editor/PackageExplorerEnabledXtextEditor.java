package org.moflon.tgg.mosl.ui.editor;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Subclass of {@link XtextEditor} whose sole purpose is to allow navigating
 * from the shown file to the Package Explorer view
 * 
 * @author Roland Kluge - Initial implementation
 *
 */
public class PackageExplorerEnabledXtextEditor extends XtextEditor {
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter == IShowInTargetList.class) {
			return new IShowInTargetList() {
				@Override
				public String[] getShowInTargetIds() {
					return new String[] { JavaUI.ID_PACKAGES };
				}

			};
		}
		return super.getAdapter(adapter);
	}
}

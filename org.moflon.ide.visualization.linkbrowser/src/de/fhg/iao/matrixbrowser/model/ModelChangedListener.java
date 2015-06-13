/*
 * Created on 13.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.EventListener;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface ModelChangedListener extends EventListener {

	/**
	 * Acts on the given <code>ModelChangedEvent</code>. Implementing methods
	 * should update their model-derived values.
	 * 
	 * @param aEvent
	 *            the Event triggering this method
	 */
	void onModelChanged(ModelChangedEvent aEvent);
}

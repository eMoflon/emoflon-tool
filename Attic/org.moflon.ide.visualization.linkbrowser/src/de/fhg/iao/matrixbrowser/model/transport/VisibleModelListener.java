package de.fhg.iao.matrixbrowser.model.transport;

import java.util.EventObject;

public interface VisibleModelListener {

	/**
	 * Receives an EventObject.
	 * 
	 * @param e
	 *            the Event
	 */
	void update(final EventObject e);

}

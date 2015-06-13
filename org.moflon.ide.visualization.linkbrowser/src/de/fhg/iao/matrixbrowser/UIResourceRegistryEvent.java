/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * UIResourceRegistryEvent.java,v 1.1 2004/04/14 11:44:53 maxmonroe Exp $
 * Created on 14.04.04
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventObject;

/**
 * Event that occours if something happened with a
 * <code>UIResourceRegistryEvent</code>, e.g. some or all values have changed.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 * @see UIResourceRegistry
 */
public class UIResourceRegistryEvent extends EventObject {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** The key, which has changed */
	private Object myKey = null;

	/**
	 * The old value stored with the key. The new value is in the
	 * <code>UIResourceRegistryEvent</code>
	 */
	private Object myOldValue = null;

	/**
	 * Constructor of a <code>UIResourceRegistryEvent</code>.
	 * 
	 * @param aSource
	 *            the source object, where this event occured
	 * @see UIResourceRegistry
	 */
	public UIResourceRegistryEvent(Object aSource) {
		super(aSource);
	}

	/**
	 * Constructor of a <code>UIResourceRegistryEvent</code>.
	 * 
	 * @param aKey
	 *            the key, which value has changed
	 * @param aOldValue
	 *            the old value associated with the key
	 * @param aSource
	 *            the source object, where this event occured
	 * @see UIResourceRegistry
	 */
	public UIResourceRegistryEvent(Object aSource, Object aKey, Object aOldValue) {
		super(aSource);
		myKey = aKey;
		myOldValue = aOldValue;
	}

	/**
	 * @return the key, which value has changed
	 * @see UIResourceRegistry
	 */
	public Object getKey() {
		return myKey;
	}

	/**
	 * @return the old value associated with the key
	 * @see UIResourceRegistry
	 */
	public Object getOldValue() {
		return myOldValue;
	}

	/**
	 * @return true, if the whole <code>UIResourceRegistry</code> has changed
	 * @see UIResourceRegistry
	 */
	public boolean isCompleteRegistryChange() {
		return myKey == null;
	}
}
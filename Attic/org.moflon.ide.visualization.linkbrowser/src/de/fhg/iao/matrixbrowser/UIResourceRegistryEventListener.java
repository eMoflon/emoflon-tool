/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * UIResourceRegistryEventListener.java,v 1.2 2004/04/14 11:44:53 maxmonroe Exp $
 * Created on 15.03.04
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventListener;

/**
 * Interface that all components which want an on-the-fly resource update (i.e.
 * icons, colors, localizations) have to implement
 * 
 * @author Christoph Schaal
 */
public interface UIResourceRegistryEventListener extends EventListener {

	/**
	 * Load new resources from the singelton <code>UIResourceRegistry</code>
	 * 
	 * @param e
	 * @see UIResourceRegistry
	 */
	public void onUIResourceRegistryChanged(UIResourceRegistryEvent aEvent);
}
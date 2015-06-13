/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications.
 *  
 * Please read carefully the IAO_License.txt and/or contact the authors. 
 *   File : $Id: TreeModelChangedListener.java,v 1.2 2009-03-11 12:31:38 srose Exp $
 *  
 *   Created on 28.11.2003
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.EventListener;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface TreeModelChangedListener extends EventListener {

	public void onTreeModelChanged(TreeModelChangedEvent e);
}
/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TransferableNode.java,v 1.2 2004/04/07 13:52:31 roehrijn Exp $ Created on
 * 23.09.2003
 */
package de.fhg.iao.matrixbrowser.model.elements;

import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransferableNode {

	private ID id;

	private String name;

	/**
	 * @param <br>
	 * @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public TransferableNode(ID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public ID getID() {
		return id;
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public void setID(ID id) {
		this.id = id;
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public void setName(String string) {
		name = string;
	}
}
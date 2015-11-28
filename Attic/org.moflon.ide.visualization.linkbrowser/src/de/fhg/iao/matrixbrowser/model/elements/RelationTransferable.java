/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * RelationTransferable.java,v 1.2 2004/04/07 13:52:31 roehrijn Exp $ Created on
 * 08.10.2003
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RelationTransferable implements Transferable {

	public static DataFlavor transferableRelationFlavor = null;

	private ID relationID = null;

	static {
		transferableRelationFlavor = new DataFlavor(RelationTransferable.class,
				"de.fhg.iao.matrixbrowser.flexiblemodel.TransferableRelation");
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] result = { transferableRelationFlavor };

		return result;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(transferableRelationFlavor);
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(transferableRelationFlavor)) {
			return this.getRelationID();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	/**
	 * @param
	 * @return
	 */
	public ID getRelationID() {
		return relationID;
	}

	/**
	 * @param
	 * @return
	 */
	public void setRelationID(ID id) {
		relationID = id;
	}
}
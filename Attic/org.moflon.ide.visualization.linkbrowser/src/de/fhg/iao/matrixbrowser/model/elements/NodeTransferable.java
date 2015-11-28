/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * NodeTransferable.java,v 1.2 2004/04/07 13:52:31 roehrijn Exp $ Created on
 * 23.09.2003
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NodeTransferable implements Transferable {

	public static DataFlavor transferableNodeFlavor = null;

	private TransferableNode[] nodes = null;

	static {
		transferableNodeFlavor = new DataFlavor(TransferableNode.class,
				"de.fhg.iao.matrixbrowser.model.TransferableNode");
	}

	/**
	 * @param <br>
	 * @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public NodeTransferable(TransferableNode[] nodes) {
		this.nodes = nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] result = { transferableNodeFlavor };

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.
	 * datatransfer.DataFlavor)
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return transferableNodeFlavor.equals(flavor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer
	 * .DataFlavor)
	 */
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(transferableNodeFlavor)) {
			return this.getNodes();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public TransferableNode[] getNodes() {
		return nodes;
	}

	/**
	 * @param
	 * @return @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public void setNodes(TransferableNode[] nodes) {
		this.nodes = nodes;
	}
}
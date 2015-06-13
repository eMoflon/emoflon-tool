/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TreeTransferHandler.java,v 1.3 2004/04/13 12:36:35 kookaburra Exp $ Created
 * on 24.09.2003
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.elements.NodeTransferable;
import de.fhg.iao.matrixbrowser.model.elements.TransferableNode;

/**
 * Specialized <code>TransferHandler</code> for the <code>TreeView</code> s in a
 * <code>MatrixBrowserPanel</code> as well as the <code>TransportPanel</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.4 $
 * @see javax.swing.TransferHandler
 */
public class TreeTransferHandler extends TransferHandler {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Null constructor.
	 */
	public TreeTransferHandler() {
		super();
	}

	/**
	 * Checks if the given <code>DataFlavor</code> s can be imported into the
	 * given component.
	 * 
	 * @param aComponent
	 *            the component, which should import
	 * @param aTransferFlavors
	 *            the <code>DataFlavor</code> s of the import
	 * @return if the aComponent can import one of the aDataFlavors
	 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent,
	 *      java.awt.datatransfer.DataFlavor[])
	 */
	public boolean canImport(JComponent aComponent,
			DataFlavor[] aTransferFlavors) {
		if (aComponent instanceof JTree) {
			for (int i = 0; i < aTransferFlavors.length; i++) {
				if (aTransferFlavors[i]
						.equals(NodeTransferable.transferableNodeFlavor)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Imports the data of the given <code>Transferable</code> into the given
	 * component.
	 * 
	 * @param aComponent
	 *            the component which imports the data
	 * @param aTransferable
	 *            the data to be imported
	 * @return if the operation suceeded
	 * @see javax.swing.TransferHandler#importData(javax.swing.JComponent,
	 *      java.awt.datatransfer.Transferable)
	 */
	public boolean importData(JComponent aComponent, Transferable aTransferable) {
		return false;
	}

	/**
	 * Creates a <code>TreeTransferable</code> to be exported. It consist out of
	 * an array of NodeTransferable.
	 * 
	 * @param aComponent
	 *            the component for which to create the Transferable
	 * @return a <code>TreeTrasnferable</code> encoding th actual selection.
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 * @see de.fhg.iao.swt.util.dnd.datatransfer.TreeTransferable
	 */
	protected Transferable createTransferable(JComponent aComponent) {
		if (aComponent instanceof JTree) {
			JTree tree = (JTree) aComponent;
			TreePath[] selections = tree.getSelectionPaths();
			TransferableNode[] nodes = new TransferableNode[selections.length];

			for (int i = 0; i < selections.length; i++) {
				nodes[i] = ((VisibleNode) selections[i].getLastPathComponent())
						.getNestedNode().createTransferableNode();
			}

			NodeTransferable result = new NodeTransferable(nodes);

			return result;
		}

		return null;
	}

	/**
	 * Gets the source actions of this object. In case the source object is an
	 * <code>TreeView</code> this returns a <code>COPY</code> action. Outherwise
	 * <code>NONE</code>.
	 * 
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent aComponent) {
		if (aComponent instanceof JTree) {
			return COPY_OR_MOVE;
		}

		return NONE;
	}
}
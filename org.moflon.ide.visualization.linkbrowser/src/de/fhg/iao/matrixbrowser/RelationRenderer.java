/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * RelationRenderer.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 14.03.01
 */
package de.fhg.iao.matrixbrowser;

import javax.swing.JComponent;

/**
 * Interface for a component, which renders a <code>VisibleRelation</code> on
 * the <code>RelationPane</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.6 $
 */
public interface RelationRenderer {

	/**
	 * Gets an instance of a <code>RelationRenderer</code>.
	 * 
	 * @param aRelationPane
	 *            the <code>RelationPane</code> which renderes the relations
	 * @param aCenterX
	 *            the centered x coordinate of the relation to render
	 * @param aCenterY
	 *            the centered y coordinate of the relation to render
	 * @param aRelation
	 *            the <code>VisibleRelation</code> to render
	 * @return the actually configured component
	 * @see de.fhg.iao.matrixbrowser.RelationPane
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public JComponent getRelationRendererComponent(RelationPane aRelationPane,
			int aCenterX, int aCenterY, VisibleRelation aRelation);
}
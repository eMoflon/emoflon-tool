/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: AbstractModelConsistencyChecker.java,v 1.2 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on 12.01.2005
 */
package de.fhg.iao.matrixbrowser.model;

/**
 * A ConsistencyChecker is used to check the model's consistency. There are a
 * wide variety of consistency constraints imagineable. Some of them are:
 * <ul>
 * <li>The node which is considered as rootNode must be part of the model</li>
 * <li>There must be at least one RelationType which is considered as a
 * treespanning RelationType.</li>
 * <li>There must be at least one Relation of one of the treespanning
 * RelationTypes</li>
 * </ul>
 * 
 * Furthermore there are also some more constraints which call for a variety of
 * time consuming algorithms. Some of them are:
 * <ul>
 * <li>sourceNode and targetNode of each relation must be part of the model</li>
 * <li>each relations RelationType must be part of the model</li>
 * <li>There mustn't be an cycles in the graph in the context of the
 * treespanning relations</li>
 * </ul>
 * 
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich</a>
 * @version $Revision: 1.2 $
 */
public abstract class AbstractModelConsistencyChecker {
	/**
	 * The model which will be checked
	 */
	private MBModel myModel;

	/**
	 * Constructs a new ConsistencyChecker using <code>model</code> as model to
	 * be checked
	 * 
	 * @param model
	 *            Model which will be checked for consistency
	 */
	public AbstractModelConsistencyChecker(MBModel model) {
		setModel(model);
	}

	/**
	 * @return The model which will be checked for consistency
	 */
	public MBModel getModel() {
		return myModel;
	}

	/**
	 * @param model
	 *            The model which shall be checked for consistency Note: Invoke
	 *            <code>check()</code> for checking afterwards.
	 */
	public void setModel(MBModel model) {
		myModel = model;
	}

	/**
	 * Effectivly initiates the consistency checking process. The return value
	 * will be true if everything is consistent, otherwise false.
	 * 
	 * @return True if all checks passed with pasitive result, false otherwise.
	 */
	public abstract boolean check();
}

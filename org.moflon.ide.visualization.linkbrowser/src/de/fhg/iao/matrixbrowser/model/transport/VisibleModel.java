package de.fhg.iao.matrixbrowser.model.transport;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * 
 * simple interface for building a visible model in matrixbrowser without
 * getting in touch with it.
 * 
 * @author $Author: smueller $
 * @version $Revision: 1.10 $
 * 
 */

public interface VisibleModel {

	/* nodes */

	public void addNode(ID id);

	public void removeNode(ID id);
	
   public void updateNodeID(ID oldID, ID newID);

	public void setVerticalRootNode(ID id);

	public void setHorizontalRootNode(ID id);

	/* relation types */

	public void addRelationType(RelationType type);

	public void addTreeSpanningRelationType(RelationType type);

	public void removeRelationType(RelationType type);

	/* relations */
	public void addRelation(RelationType type, ID sourceId,
			ID targetId, RelationDirection direction, RelationClass rclass,
			ID relationID);

	public void addRelation(RelationType type, ID sourceId,
			ID targetId, RelationDirection direction, RelationClass rclass,
			ID relationID, Color color);


	public void removeRelation(ID id);

	/* getter */

	public Node getNode(ID id);

	public Collection<Node> getNodes();

	public Collection<Relation> getRelations();

	public Collection<Relation> getRelationsByType(ID id,
			RelationType type);

	public Collection<RelationType> getRelationTypes();

	public Relation getRelation(ID id);

	public Collection<RelationType> getTreeSpanningRelationTypes();

	public ID getVerticalRootNodeID(); // was void
												// getVerticalRootNode(ID id)

	public ID getRootNodeID();

	public ID getHorizontalRootNodeID();
	
   public void update(Object objectToUpdate);

	/* clear */
	public void clearRelations();
	
	public void clearDownwards(ID id);
	
	/**
	 * Sets the visible model to read mode, if the specified enabled is true.
	 * If the read mode is enabled, no listener events will will be fired by the visible model.
	 * This can be used to get a better performance. If the read mode is set to false, all listeners
	 * will be informed, that the model has changed.
	 * @param enabled true if the read mode is activated, false otherwise
	 */
	public void setReadMode(boolean enabled);
	
	/**
	 * Returns true if the visible model is in read mode, false otherwise.
	 * @see {@link VisibleModel#setReadMode(boolean)}
	 * @return true if read mode is activated, false otherwise
	 */
	public boolean isInReadMode();
	
	/**
	 * If the specified enabled is true, the model listeners will be activated,
	 * otherwise the model listeners will deactivated and no events propagate to
	 * the listeners.
	 * @param enabled true, if events should be propagate to the model listeners, false otherwise
	 */
	public void setModelListenersActivated(boolean enabled);
	
	/**
	 * Returns all nodes that are plotted horizontally
	 * @return
	 */
	public Map<ID, Node> getHorizontalNodes();
	
	/**
	 * Returns all nodes that are plotted vertically
	 * @return
	 */
	public Map<ID, Node> getVerticalNodes();
	
}

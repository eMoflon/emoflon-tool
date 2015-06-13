/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *  $Id: ExternalModelMatrixBrowser.java,v 1.21 2010-04-29 12:19:15 smueller Exp $
 * 
 * Created on Aug 12, 2008
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.ModelChangedEvent;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.StringID;

/**
 * An Implementation of the <code>VisibleModel</code> interface. It extends a
 * JPanel, thus it combines all functionality external users should need.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.21 $
 */
public class ExternalModelMatrixBrowser implements VisibleModel {

	private Logger log = Logger.getLogger(ExternalModelMatrixBrowser.class);

	/** Comment for <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;

	/**
	 * the <code>DIVIDER_LOCATION</code> for the split in the pane between
	 * matrixBrowserPane and TransportPane.
	 */
	private static final int DIVIDER_LOCATION = 160;

	/** The MatrixBrowserPanel used by this MatrixBrowser. */
	private MatrixBrowserPanel matrixBrowserPanel;

   /** The externalMatrixBrowserPanel used by this MatrixBrowser. */
	private JPanel externalMatrixBrowserPanel;

	/** The MBModel we're dealing with. */
	private MBModel model;
	/** our rootNode. */
	private Node rootNode = null;

	/** the Reference to this MatrixBrowser's horizontal root node. */
	private MBModelNode horizontalroot = null;
	/** a reference to this MatrixBrowser's vertical root node. */
	private MBModelNode verticalroot = null;
	
	/**
	 * The relation Type that shall be used for Relations from the topmost root
	 * Node to the vertical and horizontal root node.
	 * */
	private RelationType rootNodeRelationType = new RelationType(
			"Root Node Relation Type");
	/**
	 * A reference to the Relation connecting the horizontal root node to the
	 * topmost root node.
	 */
	private Relation horizontalRootRelation;
	/**
	 * A reference to the Relation connecting the vertical root node to the
	 * topmost root node.
	 */
	private Relation verticalRootRelation;

	/**
	 * A boolean flag, which is true if the the integrator is in read mode
	 */
	private boolean readMode = false;

	/** Constructs an ExternalModelMatrixBrowser. */
	public ExternalModelMatrixBrowser() {
		super();
		if (model == null) {
			model = new DefaultMBModel();
			preliminaryModelBuild(model);
		}
		initPanel();
	}
	
	private void initPanel(){
      externalMatrixBrowserPanel = new JPanel();
      TreeManager treeManager = new TreeManager(model);
      matrixBrowserPanel = new MatrixBrowserPanel();
      matrixBrowserPanel.setTreeManager(treeManager);
      TransportPanel transportPanel = new TransportPanel();
      transportPanel.setTreeManager(treeManager);

      BorderLayout borderLayout = new BorderLayout();
      externalMatrixBrowserPanel.setLayout(borderLayout);
      matrixBrowserPanel.setBackground(Color.white);

      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            transportPanel, matrixBrowserPanel);
      splitPane.setDividerLocation(DIVIDER_LOCATION);
      externalMatrixBrowserPanel.add(BorderLayout.CENTER, splitPane);
      matrixBrowserPanel.setEditable(true);
      matrixBrowserPanel.setEnabled(true);
      externalMatrixBrowserPanel.validate();
	}
	
	public JPanel getPanel(){
	      return externalMatrixBrowserPanel;
	}

	/**
	 * Builds a preliminary Model for initialization of the components. This
	 * model contains a root Node, a vertical and a horizontal root node with
	 * their respective tree relations.
	 * 
	 * @param model
	 *            the model to build
	 */
	private final void preliminaryModelBuild(final MBModel model) {
		MBModelBuilder build = new MBModelBuilder(model);
		rootNode = new MBModelNode("root");
		try {
			build.setRootNodeID(rootNode.getID());
			build.addNode(rootNode);
			verticalroot = new MBModelNode("vertical root");
			build.addNode(verticalroot);
			horizontalroot = new MBModelNode("horizontal root");
			build.addNode(horizontalroot);
			RelationType treeRel = new RelationType("Baumrelation");
			build.addRelationType(treeRel, true);
			verticalRootRelation = new Relation(rootNode.getID(), verticalroot
					.getID(), treeRel, RelationClass.REAL,
					RelationDirection.UNDIRECTED);
			build.addRelation(verticalRootRelation);
			horizontalRootRelation = new Relation(rootNode.getID(),
					horizontalroot.getID(), treeRel, RelationClass.REAL,
					RelationDirection.DIRECTED);
			build.addRelation(horizontalRootRelation);
			build.complete();
		} catch (TransportException e) {
		      e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#addNode
	 *      (de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void addNode(final ID id) {
		if(model.getNode(id)== null){
		    Node node = new MBModelNode(id);
		      if (id instanceof StringID) {
		         node.setName(id.toString());
		      }
		      model.addNode(node);
		}
	}

   public final void update(Object objectToUpdate) {
      ModelChangedEvent eventToFire;
      if(objectToUpdate instanceof Node){
         eventToFire = new ModelChangedEvent(this, model, (Node)objectToUpdate);
      }
      else if(objectToUpdate instanceof Relation){
         eventToFire = new ModelChangedEvent(this, model, (Relation) objectToUpdate);
      }
      else if(objectToUpdate instanceof MBModel){
         eventToFire = new ModelChangedEvent(this, model, (MBModel)objectToUpdate);
      }
      else{
         eventToFire = new ModelChangedEvent(this);
      }
      model.fireModelChangedEvent(eventToFire);
   }
	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#addRelation
	 *      (de.fhg.iao.matrixbrowser.model.elements.RelationType,
	 *      de.fhg.iao.swt.util.uniqueidentifier.ID,
	 *      de.fhg.iao.swt.util.uniqueidentifier.ID, int)
	 */
	public void addRelation(RelationType type, ID sourceID,
			ID targetID, RelationDirection direction,
			RelationClass rclass, ID relationID) {
	   this.addRelation(type, sourceID, targetID, direction, rclass, relationID, null);
	}

	public void addRelation(RelationType type, ID sourceId, ID targetId,
			RelationDirection direction, RelationClass rclass, ID relationID,
			Color color) {
		Relation relation = new Relation(sourceId, targetId, type, rclass,
				direction);
		relation.setColor(color);
		relation.setID(relationID);
	   relation.setDescription(relationID.toString());
		model.addRelation(relation);
		matrixBrowserPanel.getRelationPane().updateVisibleRelations();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#addRelationType
	 *      (de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final void addRelationType(final RelationType type) {
		model.addRelationType(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #addTreeSpanningRelationType
	 *      (de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final void addTreeSpanningRelationType(final RelationType type) {
		model.addTreeSpanningRelationType(type);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#getRootNodeID()
	 */
	public final ID getRootNodeID() {
		return model.getRootNode().getID();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getHorizontalRootNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final Node getHorizontalRootNode() {
		return (Node) this.horizontalroot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public Node getNode(ID id) {
	   Node modelNode = model.getNode(id);
		return modelNode;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#getNodes()
	 */
	public final Collection<Node> getNodes() {
		return this.model.getNodes().values();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#getRelation
	 *      (de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final Relation getRelation(final ID id) {
		return model.getRelation(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getRelationTypes()
	 */
	public final Collection<RelationType> getRelationTypes() {
		return model.getRelationTypes();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#getRelations()
	 */
	public final Collection<Relation> getRelations() {
      Set<Relation> result = new HashSet<Relation>();
      Map<RelationType, Collection<Relation>> orderedRelations = model
            .getRelations();
      Iterator<Collection<Relation>> relTypeIterator = orderedRelations
            .values().iterator();

      while (relTypeIterator.hasNext()) {
         result.addAll(relTypeIterator.next());
      }
      return result;
   }

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getRelationsByType(de.fhg.iao.swt.util.uniqueidentifier.ID,
	 *      de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final Collection<Relation> getRelationsByType(final ID id,
			final RelationType type) {
		return model.getRelationsByType(model.getNode(id), type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getTreeSpanningRelationTypes()
	 */
	public final Collection<RelationType> getTreeSpanningRelationTypes() {
		return model.getTreeSpanningRelationTypes();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getVerticalRootNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final ID getVerticalRootNode() {
		return this.verticalroot.getID();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getHorizontalRootNodeID()
	 */
	public final ID getHorizontalRootNodeID() {
		return this.horizontalroot.getID();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #removeRelationType
	 *      (de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final void removeRelationType(final RelationType type) {
		// remove Relations with given Relation type
		model.getRelations().remove(type);
		// now remove Relation from RelationTypes
		model.getRelationTypes().remove(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #removeNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void removeNode(final ID id) {
	   Node node = model.getNode(id);
	   if(node != null){
	      model.removeNode(node);
	   }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel#removeRelation
	 *      (de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void removeRelation(final ID id) {
		model.removeRelation(model.getRelation(id));
		matrixBrowserPanel.getRelationPane().updateVisibleRelations();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #setHorizontalRootNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void setHorizontalRootNode(final ID id) {
		Relation newHorizontalRootRelation = new Relation(rootNode.getID(), id,
				rootNodeRelationType, RelationClass.REAL,
				RelationDirection.DIRECTED);
		model.addRelation(newHorizontalRootRelation);
		model.removeRelation(horizontalRootRelation);
		horizontalRootRelation = newHorizontalRootRelation;
		matrixBrowserPanel.getHorizontalTreeView().getTreeSynthesizer()
				.setRootNode(model.getNode(id));
		horizontalroot = (MBModelNode) model.getNode(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #setVerticalRootNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void setVerticalRootNode(ID id) {
		Relation newVerticalRootRelation = new Relation(rootNode.getID(), id,
				rootNodeRelationType, RelationClass.REAL,
				RelationDirection.DIRECTED);
		model.addRelation(newVerticalRootRelation);
		model.removeRelation(verticalRootRelation);
		verticalRootRelation = newVerticalRootRelation;
		matrixBrowserPanel.getVerticalTreeView().getTreeSynthesizer()
				.setRootNode(model.getNode(id));
		verticalroot = (MBModelNode) model.getNode(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModel
	 *      #getVerticalRootNodeID()
	 */
	public final ID getVerticalRootNodeID() {
		return this.getVerticalRootNodeID();
	}

	public void clearDownwards(ID id) {
		Node node = this.model.getNode(id);
		if(node != null){
		   Collection<Relation> relations = this.model.getRelations(node);
	      for(Relation relation : relations){
	         ID targetNodeId = relation.getTargetNodeID();
	         if(!node.getID().equals(targetNodeId)){
	            this.clearDownwards(targetNodeId);
	         }
	      }
	      this.model.removeNode(node);
		}
	}

	public void clearRelations() {
		Map<RelationType, Collection<Relation>>  relationsMap = this.model.getRelations();
		Collection<Collection<Relation>> relationCollections = relationsMap.values();
		for(Collection<Relation> curRelations : relationCollections){
		   curRelations = new ArrayList<Relation>(curRelations);
		   for(Relation curRelation : curRelations){
	          this.model.removeRelation(curRelation);
		   }
		}
		relationsMap.clear();
	}

   public void updateNodeID(ID oldID, ID newID)
   {
      Map<ID, Node> nodes = this.model.getNodes();
      if(oldID != null && newID != null){
         Node node = this.model.getNode(oldID);
         if(node != null){
            Collection <Relation> relations = this.model.getRelations(node);       
            nodes.remove(oldID);
            node.setID(newID);
            nodes.put(newID, node);
            for(Relation relation : relations){
               if(relation.getSourceNodeID().equals(oldID)){
                  relation.setSourceNodeID(newID);
               }
               else if(relation.getTargetNodeID().equals(oldID)){
                  relation.setTargetNodeID(newID);
               }          
            }
            this.model.fireModelChangedEvent(new ModelChangedEvent(this.model, this.model, node));
         }
      }
   }

   public boolean isInReadMode()
   {
      return readMode;
   }

   public void setReadMode(boolean enabled)
   {
      this.readMode = enabled;
      setModelListenersActivated(!enabled);
   }

   public void setModelListenersActivated(boolean enabled)
   {
      this.model.setModelListenersActivated(enabled);
      if(enabled){
         update(this.model);
      }
   }

   @Override
   public Map<ID, Node> getHorizontalNodes()
   {
      return horizontalroot.getModel().getNodes();
   }

   @Override
   public Map<ID, Node> getVerticalNodes()
   {
      return verticalroot.getModel().getNodes();
   }
}

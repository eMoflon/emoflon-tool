package org.moflon.integrator.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.integrator.builder.emf_adapters.EObjectID;
import org.moflon.integrator.builder.emf_adapters.NodeEObjectAdapter;
import org.moflon.integrator.listeners.NodeClickedEventListener;
import org.moflon.integrator.listeners.RelationClickedEventListener;

import TGGRuntime.CorrespondenceModel;
import TGGRuntime.EMoflonEdge;
import TGGRuntime.EObjectContainer;
import TGGRuntime.impl.TGGRuntimeFactoryImpl;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;

public class IntegratorBuilder
{
   // Define colors
   public static final Color green = new Color(150, 255, 150);

   public static final Color red = new Color(255, 150, 150);

   // Collections for nodes and relations
   private final HashMap<RelationType, Boolean> relationTypes;

   private final List<Relation> relations;

   private final List<Node> nodes;

   private final List<EMoflonEdge> displayedEdges;

   private final HashMap<EObject, Node> eObjectsToNodes;

   // Default relations
   private final RelationType composite;

   private final RelationType correspondence;

   // MatrixBrowser internal builder
   private final MBModelBuilder builder;

   // Root nodes representing models
   private final Node rootNode;

   private final NodeEObjectAdapter sourceNode;

   private final NodeEObjectAdapter targetNode;

   // EMF models
   private EObject sourceModel;

   private EObject targetModel;

   private CorrespondenceModel corrModel;

   // Constructor loads models and sets up basic nodes
   public IntegratorBuilder(CorrespondenceModel corrModel) throws TransportException
   {
      this.corrModel = corrModel;
      loadModels();

      relationTypes = new HashMap<RelationType, Boolean>();
      relations = new ArrayList<Relation>();
      nodes = new ArrayList<Node>();
      eObjectsToNodes = new HashMap<EObject, Node>();
      displayedEdges = new ArrayList<EMoflonEdge>();

      builder = new MBModelBuilder(new DefaultMBModel());

      composite = createRelationType("contains", true);
      correspondence = createRelationType("relates to", false);

      rootNode = createNode("root");
      sourceNode = createNode(eMoflonEMFUtil.getIdentifier(sourceModel), sourceModel);
      targetNode = createNode(eMoflonEMFUtil.getIdentifier(targetModel), targetModel);

      makeChild(rootNode, sourceNode);
      makeChild(rootNode, targetNode);
   }

   private void loadModels()
   {
      sourceModel = corrModel.getSource();
      targetModel = corrModel.getTarget();
   }

   // Traverses models and builds up visualization layer
   public void populateMatrixView(final MatrixBrowserPanel browserPanel) throws TransportException
   {
      // Create Nodes
      createEdgeNodes(sourceNode);
      createEdgeNodes(targetNode);

      // Create Correspondences
      createCorrespondences(corrModel);

      // Complete Build
      completeBuild();
   }

   private void createCorrespondences(final EObject model)
   {
      // Get all AbstractCorrs in model (they lie flat in the model) and create relations
      final EStructuralFeature corrsFeature = eMoflonEMFUtil.getContainment(model, "correspondences");

//      model.e
//      model.eClass().eCrossReferences()
//      model.eClass().getEAllStructuralFeatures()
//      eMoflonEMFUtil.getContainment(model, "correspondences");
//      eMoflonEMFUtil.getReference(model, "correspondences")
      
      if (corrsFeature == null)
      {
         throw new IllegalStateException("Sorry - unable to open integrator as the transformation did not create any correspondence links.  "
               + "Please take a look at the protocol file instead.");
      }

      for (final Object obj : (Collection<?>) model.eGet(corrsFeature))
      {
         final EObject corr = (EObject) obj;

         // Determine source and target references according to naming convention
         final EStructuralFeature srcFeature = eMoflonEMFUtil.getReference(corr, "source");
         final EStructuralFeature trgFeature = eMoflonEMFUtil.getReference(corr, "target");

         final EObject srcObj = (EObject) corr.eGet(srcFeature, true);
         final EObject trgObj = (EObject) corr.eGet(trgFeature, true);
         if (eObjectsToNodes.containsKey(srcObj) && eObjectsToNodes.containsKey(trgObj))
         {
            createCorrespondence(corr, eObjectsToNodes.get(srcObj), eObjectsToNodes.get(trgObj));
         }
      }

   }

   private void createNodes(final NodeEObjectAdapter node) throws TransportException
   {

      final EMoflonEdge edge = (EMoflonEdge) node.getObject();
      final EObject child = edge.getTrg();
      if (child instanceof EObjectContainer)
      {
         for (final EObject singleChild : ((EObjectContainer) child).getContents())
         {
            final NodeEObjectAdapter childNode = createNode(eMoflonEMFUtil.getIdentifier(singleChild), singleChild);
            makeChild(node, childNode);

            createEdgeNodes(childNode);
         }
      } else
      {
         final NodeEObjectAdapter childNode = createNode(eMoflonEMFUtil.getIdentifier(child), child);
         makeChild(node, childNode);

         createEdgeNodes(childNode);
      }

   }

   private void createEdgeNodes(final NodeEObjectAdapter node) throws TransportException
   {
      final List<EMoflonEdge> referenceEdges = new ArrayList<EMoflonEdge>();
      final List<EMoflonEdge> containmentEdges = new ArrayList<EMoflonEdge>();
      final List<EMoflonEdge> allEdges = new ArrayList<EMoflonEdge>();
      final EObject eObject = node.getObject();

      for (final EStructuralFeature feature : eMoflonEMFUtil.getAllReferences(eObject))
      {
         if (isContainmentReference(feature))
            addEdgesToList(eObject, feature, containmentEdges, allEdges);
         else
            addEdgesToList(eObject, feature, referenceEdges, allEdges);
      }
      displayedEdges.addAll(referenceEdges);
      displayedEdges.addAll(containmentEdges);

      for (final EMoflonEdge edge : referenceEdges)
      {
         final NodeEObjectAdapter edgeNode = createNode(createReferenceNodeName(edge), edge);
         makeChild(node, edgeNode);
      }

      for (final EMoflonEdge edge : containmentEdges)
      {
         final NodeEObjectAdapter edgeNode = createNode(createContainmentNodeName(edge), edge);
         makeChild(node, edgeNode);
         createNodes(edgeNode);
      }
   }

   /**
    * Creates the edge determined by the source object and the structural feature and adds it to the given list of edges
    * 
    * @return The given list of edges with the new one added
    */
   private List<EMoflonEdge> addEdgesToList(final EObject source, final EStructuralFeature feature, final List<EMoflonEdge> edges,
         final List<EMoflonEdge> allEdges)
   {
      if (feature.getUpperBound() != 1)
      {
         final EMoflonEdge edge = TGGRuntimeFactoryImpl.eINSTANCE.createEMoflonEdge();
         final EObjectContainer container = TGGRuntimeFactoryImpl.eINSTANCE.createEObjectContainer();

         @SuppressWarnings("unchecked")
         final List<EObject> connectedObjects = (List<EObject>) source.eGet(feature, true);

         container.getContents().addAll(connectedObjects);
         edge.setName(feature.getName());
         edge.setSrc(source);
         edge.setTrg(container);
         edges.add(edge);
      } else
      {
         final EObject connectedObject = (EObject) source.eGet(feature, true);
         if (connectedObject != null)
         {
            EMoflonEdge edge = getEdgeFromList(source, connectedObject, feature.getName(), allEdges);
            if (edge == null)
            {
               edge = TGGRuntimeFactoryImpl.eINSTANCE.createEMoflonEdge();
               edge.setName(feature.getName());
               edge.setSrc(source);
               edge.setTrg(connectedObject);
            }
            edges.add(edge);
         }
      }

      return edges;
   }

   private EMoflonEdge getEdgeFromList(final EObject src, final EObject target, final String edgeName, final List<EMoflonEdge> allEdges)
   {
      for (final EMoflonEdge edge : allEdges)
      {
         if (edge.getSrc().equals(src) && edge.getTrg().equals(target) && edge.getName().equals(edgeName))
         {
            return edge;
         }
      }

      return null;
   }

   private String createReferenceNodeName(final EMoflonEdge edge)
   {
      final StringBuilder sb = new StringBuilder("-");
      sb.append(edge.getName());
      sb.append("-> ");
      sb.append(eMoflonEMFUtil.getIdentifier(edge.getTrg()));
      return sb.toString();
   }

   private String createContainmentNodeName(final EMoflonEdge edge)
   {
      final StringBuilder sb = new StringBuilder("-");
      sb.append(edge.getName());
      sb.append("-> ");
      return sb.toString();
   }

   private boolean isContainmentReference(final EStructuralFeature feature)
   {
      if (feature instanceof EReference)
      {
         final EReference reference = (EReference) feature;
         if (reference.isContainer() || reference.isContainment())
            return true;
      }

      return false;
   }

   private NodeEObjectAdapter createNode(final String name, final EObject eObject) throws TransportException
   {
      final NodeEObjectAdapter node = new NodeEObjectAdapter(name, eObject);
      nodes.add(node);

      eObjectsToNodes.put(eObject, node);

      return node;
   }

   private Node createNode(final String name) throws TransportException
   {
      final Node node = new MBModelNode(name);
      nodes.add(node);

      return node;
   }

   private RelationType createRelationType(final String name, final boolean isTreeSpanningRelationType) throws TransportException
   {
      final RelationType relationType = new RelationType(name);
      relationTypes.put(relationType, isTreeSpanningRelationType);

      return relationType;
   }

   private Relation makeChild(final Node parent, final Node child)
   {
      final Relation relation = new Relation(parent.getID(), child.getID(), composite, RelationClass.REAL, RelationDirection.UNDIRECTED);
      relations.add(relation);

      return relation;
   }

   private Relation createCorrespondence(final EObject corr, final Node src, final Node trg)
   {
      final Relation relation = new Relation(new EObjectID(corr), src.getID(), trg.getID(), correspondence, RelationClass.REAL, RelationDirection.DIRECTED);
      relations.add(relation);

      return relation;
   }

   private void completeBuild() throws TransportException
   {
      builder.setRootNodeID(rootNode.getID());

      // Add all nodes
      for (final Node node : nodes)
         builder.addNode(node);

      // Add relation types
      for (final RelationType relationType : relationTypes.keySet())
         builder.addRelationType(relationType, relationTypes.get(relationType));

      // Add relations
      for (final Relation relation : relations)
         builder.addRelation(relation);

      builder.complete();
   }

   public void registerEventListeners(final MatrixBrowserPanel browserPanel)
   {
      // Add event listeners
      final MBModel model = builder.getModel();
      final TreeManager treeManager = new TreeManager(model);
      browserPanel.setTreeManager(treeManager);

      final NodeClickedEventListener nodeClicked = new NodeClickedEventListener();
      browserPanel.addVisibleNodeEventListener(nodeClicked);

      final RelationClickedEventListener relationClicked = new RelationClickedEventListener();
      browserPanel.addVisibleRelationEventListener(relationClicked);
   }
}

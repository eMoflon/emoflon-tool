/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. 
 * File : 
 * 		$Id: DOM4JDirector.java,v 1.4 2009-03-11 12:31:38 srose Exp $ Created on
 * 17.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.UserID;

/**
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich</a>
 * 
 *         The DOM4JDirector imports MatrixBrowser data XML-Files by using dom4j
 *         for the streaming process. It awaits an instance of
 *         de.fhg.iao.matrixbrowser.model.transport.Builder to forward each
 *         created element. The file to import must be specified as an URL which
 *         may point to local resources as well. For using this module ther must
 *         be at least a version of dom4j, a dom4j compliant SAX parser and a
 *         dom4j compliant XPath processor in the classpath.
 * @see de.fhg.iao.matrixbrowser.model.transport.Builder
 */
public class DOM4JDirector extends AbstractDirector {
	protected SAXReader myReader = new SAXReader();
	protected Document myDocument;
	protected URL mySource;

	/**
	 * Builds a new DOM4JDirector using the given Builder and URL objects
	 * 
	 * @param builder
	 *            An instances of Builder used to forward each new element.
	 * @param xmlFileUrl
	 *            The Url which points to the resource to import.
	 */
	public DOM4JDirector(final Builder builder, final URL xmlFileUrl) {
		super(builder);
		this.mySource = xmlFileUrl;
	}

	/**
	 * Starts the importing process. After completion of this method, the newly
	 * built structure can be obtained from the previously given Builder
	 * instance.
	 */
	@Override
   public void startTransport() throws TransportException {
		try {
			myDocument = myReader.read(mySource);
		} catch (DocumentException ex) {
			throw new TransportException(
					"Error while creating DOM Document. See attached exception",
					ex);
		}
		final Map<String, ID> nodeMap = new HashMap<>();

		// read all graph-nodes
		Element tmp = (Element) myDocument.selectSingleNode("/model/nodes");
		for (int i = 0; i < tmp.nodeCount(); i++) {
			Element node = (Element) tmp.node(i);
			Node mbNode = new de.fhg.iao.matrixbrowser.model.elements.MBModelNode(
					node.attributeValue("name"), new UserID(node
							.attributeValue("id")));
			myBuilder.addNode(mbNode);
			nodeMap.put(mbNode.getID().toString(), mbNode.getID());
		}

		// add the rootNodeID
		tmp = (Element) myDocument.selectSingleNode("/model");
		ID rootNodeID = nodeMap.get(tmp.attributeValue("rootNodeID"));
		myBuilder.setRootNodeID(rootNodeID);

		// read the relation types and all relations in there
		tmp = (Element) myDocument.selectSingleNode("/model/relations");
		for (int i = 0; i < tmp.nodeCount(); i++) {
			Element relationType = (Element) tmp.node(i);
			RelationType type = new RelationType(relationType
					.attributeValue("name"));
			myBuilder.addRelationType(type, relationType.attributeValue(
					"isTreeRelation").equals("true"));
			for (int j = 0; j < relationType.nodeCount(); j++) {
				Element relation = (Element) relationType.node(j);
				ID sourceNodeID = nodeMap.get(relation
						.attributeValue("sourceNodeID"));
				ID targetNodeID = nodeMap.get(relation
						.attributeValue("targetNodeID"));
				String direction = relation.attributeValue("directionType");
				RelationDirection dirNum;
				if (direction.equals("DIRECTED")) {
					dirNum = RelationDirection.DIRECTED;
				} else if (direction.equals("UNDIRECTED")) {
					dirNum = RelationDirection.UNDIRECTED;
				} else if (direction.equals("BI")) {
					dirNum = RelationDirection.BIDIRECTIONAL;
				} else {
					// not a known direction
					throw new TransportException("Direction value of relation "
							+ sourceNodeID + " <-> " + targetNodeID
							+ " unknown");
				}

				String relationClass = relation.attributeValue("class");
				RelationClass classNum;
				if (relationClass.equals("INFERED")) {
					classNum = RelationClass.INFERRED;
				} else if (relationClass.equals("EXPLICIT")) {
					classNum = RelationClass.REAL;
				} else {
					// not a known direction
					throw new TransportException("Class value of relation "
							+ sourceNodeID + " <-> " + targetNodeID
							+ " unknown");
				}

				Relation mbRelation = new Relation(sourceNodeID, targetNodeID,
						type, classNum, dirNum);
				myBuilder.addRelation(mbRelation);
			}
		}
		myBuilder.complete();
	}
}

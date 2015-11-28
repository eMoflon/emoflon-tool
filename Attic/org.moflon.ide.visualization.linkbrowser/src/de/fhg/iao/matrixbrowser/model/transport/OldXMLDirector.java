/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * OldXMLDirector.java,v 1.4 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 17.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.IDPool;
import de.fhg.iao.swt.util.uniqueidentifier.UserID;

/**
 * @author roehrich The XMLDirector gets a stream ad extracts a model data out
 *         of it.
 */
public class OldXMLDirector extends AbstractDirector {

	/**
	 * The parser class used for default.
	 */
	private String vendorParserClass = "org.apache.xerces.parsers.SAXParser";

	/**
	 * The reader containing the XML data.
	 */
	InputStreamReader myReader = null;

	/**
	 * The handler for the SAX parser
	 */
	private ContentHandler myContentHandler = new DefaultHandler() {

		private RelationType actualRelationType = null;

		// private Node actualNode = null; //not used

		private Node rootNode = null;

		private Stack<Node> nodeStack = new Stack<Node>();

		private RelationType treeRelationType = new RelationType("is_child");

		private Collection<Relation> relations = new LinkedList<Relation>();

		public void startElement(String namespaceURI, String localName,
				String qname, Attributes atts) throws SAXException {
			try {
				if (localName.equals("matrixData")) {
					rootNode = (Node) new MBModelNode("root", IDPool
							.getIDPool().getID());
					myBuilder.setRootNodeID(rootNode.getID());
					myBuilder.addNode(rootNode);
					nodeStack.push(rootNode);
				} else if (localName.equals("branch")) {
					Node node = (Node) new MBModelNode(atts
							.getValue("description"), new UserID(atts
							.getValue("id")));
					myBuilder.addNode(node);

					Node lastNode = (Node) nodeStack.peek();
					Relation relation = new Relation(lastNode.getID(), node
							.getID(), treeRelationType, RelationClass.REAL,
							RelationDirection.DIRECTED);
					relations.add(relation);

					nodeStack.push(node);
				} else if (localName.equals("hierarchyRelation")) {
					actualRelationType = new RelationType(atts
							.getValue("description"));
					myBuilder.addRelationType(actualRelationType, false);
				} else if (localName.equals("relation")) {
					ID relID = new UserID(atts.getValue("id"));
					ID sourceID = new UserID(atts.getValue("source"));
					ID targetID = new UserID(atts.getValue("destination"));

					String strDirection = atts.getValue("direction");
					String strType = atts.getValue("type");

					RelationDirection direction = RelationDirection.UNDIRECTED;

					if (strDirection.equals("RIGHT")) {
						direction = RelationDirection.DIRECTED;
					} else if (strDirection.equals("LEFT")) {
						direction = RelationDirection.DIRECTED;

						ID tmpID = sourceID;
						sourceID = targetID;
						targetID = tmpID;
					} else if (strDirection.equals("BI")) {
						direction = RelationDirection.BIDIRECTIONAL;
					} else if (strDirection.equals("UNDIRECTED")) {
						direction = RelationDirection.UNDIRECTED;
					}

					RelationClass type = RelationClass.REAL;

					if (strType.equals("INFERED")) {
						type = RelationClass.INFERRED;
					}

					Relation relation = new Relation(relID, sourceID, targetID,
							actualRelationType, type, direction);
					myBuilder.addRelation(relation);
				}
			} catch (TransportException ex) {
				throw new SAXException("TransportException", ex);
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			try {
				if (localName.equals("branch")) {
					nodeStack.pop();
				} else if (localName.equals("matrixData")) {
					myBuilder.addRelationType(treeRelationType, true);

					Iterator<Relation> iter = relations.iterator();

					while (iter.hasNext()) {
						myBuilder.addRelation((Relation) iter.next());
					}
				}
			} catch (TransportException ex) {
				throw new SAXException(ex);
			}
		}
	};

	/**
	 * @param <br>
	 */
	public OldXMLDirector(Builder builder, InputStreamReader reader) {
		super(builder);
		this.setReader(reader);
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.AbstractDirector#startTransport()
	 */
	public void startTransport() throws TransportException {
		XMLReader reader = null;

		try {
			reader = XMLReaderFactory.createXMLReader(this.vendorParserClass);
		} catch (SAXException ex) {
			throw new TransportException(
					"Exception while creating XML parser.", ex);
		}

		try {
			reader.setFeature("http://xml.org/sax/features/validation", true);
		} catch (SAXNotSupportedException ex) {
			throw new TransportException(
					"No validation supported in this SAX parser. "
							+ "Please turn off validation or choose another implementation.",
					ex);
		} catch (SAXNotRecognizedException ex) {
			throw new TransportException(
					"Validation feature couldn't be recognized. ", ex);
		}

		// ContentHandler
		reader.setContentHandler(this.myContentHandler);

		// Initialize builder
		myBuilder.initialize();

		// Parsing
		InputSource inputSource = new InputSource(this.myReader);

		try {
			reader.parse(inputSource);
		} catch (SAXException ex) {
			if (ex.getException() instanceof TransportException) {
				throw (TransportException) ex.getException();
			}
		} catch (IOException ex) {
			throw new TransportException("Error while parsing document.", ex);
		}

		// complete builder
		myBuilder.complete();
	}

	/**
	 * @return The name of the parser class used for importing
	 */
	public String getVendorParserClass() {
		return vendorParserClass;
	}

	/**
	 * @param string
	 *            The name of the parser class used for importing
	 */
	public void setVendorParserClass(String string) {
		vendorParserClass = string;
	}

	/**
	 * @return
	 */
	public InputStreamReader getReader() {
		return myReader;
	}

	/**
	 * @param
	 */
	public void setReader(InputStreamReader reader) {
		myReader = reader;
	}
}
/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * XMLDirector.java,v 1.3 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 17.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.io.IOException;
import java.io.InputStreamReader;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.Base64;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.UserID;

/**
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich</a> The
 *         XMLDirector gets a stream ad extracts a model data out of it.
 */
public class XMLDirector extends AbstractDirector {

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

		private boolean isBase64Data = false;

		private Node actualNode = null;

		public void startElement(String namespaceURI, String localName,
				String qname, Attributes atts) throws SAXException {
			try {
				if (localName.equals("model")) {
					// parse <model> tag
					myBuilder.setRootNodeID(new UserID(atts
							.getValue("rootNodeID")));
				} else if (localName.equals("nodes")) {
					// parse <nodes> tag
				} else if (localName.equals("node")) {
					// parse <node> tag
					Node node = (Node) new MBModelNode(atts.getValue("name"),
							new UserID(atts.getValue("id")));
					actualNode = node;
				} else if (localName.equals("relations")) {
					// parse <relations> tag
				} else if (localName.equals("relationType")) {
					// parse <relationType> tag
					RelationType type = new RelationType(atts.getValue("name"));

					if (atts.getValue("isTreeRelation").equals("true")) {
						// the RelationType is a treeSpaningRelationType
						myBuilder.addRelationType(type, true);
					} else {
						myBuilder.addRelationType(type, false);
					}

					this.actualRelationType = type;
				} else if (localName.equals("relation")) {
					// parse <relation> tag
					String relID = atts.getValue("id");
					ID relationID = null;

					if (relID != null) {
						relationID = new UserID(relID);
					}

					ID sourceNodeID = new UserID(atts.getValue("sourceNodeID"));
					ID targetNodeID = new UserID(atts.getValue("targetNodeID"));
					RelationDirection direction = RelationDirection.UNDIRECTED;
					String directionIndicator = atts.getValue("directionType");

					if (directionIndicator.equals("BI")) {
						direction = RelationDirection.BIDIRECTIONAL;
					} else if (directionIndicator.equals("DIRECTED")) {
						direction = RelationDirection.DIRECTED;
					}
					RelationClass relationClass = RelationClass.REAL;
					String classIndicator = atts.getValue("class");

					if (classIndicator.equals("INFERED")) {
						relationClass = RelationClass.INFERRED;
					}

					Relation relation = new Relation(relationID, sourceNodeID,
							targetNodeID, this.actualRelationType,
							relationClass, direction);
					myBuilder.addRelation(relation);
				} else if (localName.equals("userData")) {
					// parse <userData> Tag
					if (atts.getValue("encoding").equals("BASE64")) {
						isBase64Data = true;
					}
				}
			} catch (TransportException ex) {
				throw new SAXException("TransportException", ex);
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			try {
				if (localName.equals("userData")) {
					isBase64Data = false;
				} else if (localName.equals("node")) {
					myBuilder.addNode(actualNode);
				}
			} catch (TransportException ex) {
				throw new SAXException("TransportException", ex);
			}
		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (isBase64Data) {
				String string = (new String(ch, start, length)).trim();
				actualNode.setUserData(Base64.decode(string));
			}
		}
	};

	/**
	 * @param <br>
	 */
	public XMLDirector(Builder builder, InputStreamReader reader) {
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
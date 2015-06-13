/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * XMLBuilder.java,v 1.4 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 11.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.Base64;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/*
 * <ul><li> konsistency of
 * of relation's relation types </li><li> root node id must exist </li><li>
 * source an target of relations must exist (maybe not possible here) </li>
 * </ul>
 */

/**
 * @author roehrich This class renders XML into an OutputStreamWriter. The
 *         encoding is extracted and the XML code is rendered using this
 *         encoding. The rendered XML code complies to
 *         <code>FlexModelData-v1.1.dtd</code>.
 */
public class XMLBuilder implements Builder {

	/**
	 * The OutputStream where the XML data is rendered in.
	 */
	OutputStreamWriter myWriter = null;

	/**
	 * The stack is used to keep track about previous opened XML tags. Every
	 * time a XML tag is added it is put on top of the stack. If the tag is
	 * closed it is removed from the stack. So every method knows all open tag
	 * and is able to close them as needed.
	 */
	Stack<String> myTagStack = new Stack<>();

	/**
	 * Constructs an empty XMLBuilder
	 * 
	 * @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 */
	public XMLBuilder() {
		super();
	}

	/**
	 * Constructs a <code>XMLBuilder</code> using the given
	 * <code>OutputStreamWriter</code>
	 * 
	 * @param writer
	 *            The <code>OutputStreamWriter</code> which will be used to
	 *            render XML
	 */
	public XMLBuilder(final OutputStreamWriter writer) {
		this();
		this.myWriter = writer;
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addNode(de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	@Override
	public void addNode(final Node node) throws TransportException {
		if (myTagStack.peek().equals("model")) {
			// first node -> <nodes> tag must be rendered
			this.write("<nodes>\n");
			myTagStack.push("nodes");
		}

		if (myTagStack.peek().equals("nodes")) {
			if (node.getUserData() == null) {
				this.write("<node id=\"" + node.getID() + "\" name=\""
						+ node.getName() + "\"/>\n");
			} else {
				String userString = Base64.encodeBytes(node.getUserData());
				this.write("<node id=\"" + node.getID() + "\" name=\""
						+ node.getName() + "\">\n");
				this.write("<userData encoding=\"BASE64\">\n");
				this.write(userString);
				this.write("</userData>\n");
				this.write("</node>\n");
			}
		} else {
			// wrong order
			throw new TransportException(
					"open tag <nodes> expected but not present");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addRelation(de.fhg.iao.matrixbrowser.model.elements.Relation)
	 */
	@Override
	public void addRelation(final Relation relation) throws TransportException {
		if (myTagStack.peek().equals("relationType")) {
			String direction = null;

			switch (relation.getDirection()) {
			case BIDIRECTIONAL:
				direction = "BI";

				break;

			case DIRECTED:
				direction = "DIRECTED";

				break;

			default:
				direction = "UNDIRECTED";

				break;
			}

			String relationClass = null;

			switch (relation.getRelationClass()) {
			case INFERRED:
				relationClass = "INFERED";

				break;

			default:
				relationClass = "EXPLICIT";

				break;
			}

			this.write("<relation id=\"" + relation.getID()
					+ "\" sourceNodeID=\"" + relation.getSourceNodeID()
					+ "\" targetNodeID=\"" + relation.getTargetNodeID()
					+ "\" directionType=\"" + direction + "\" class=\""
					+ relationClass + "\"/>\n");
		} else {
			// the data is delivered in wrong order
			throw new TransportException(
					"open tag <relationType> expected but not present");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addRelationType(de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	@Override
	public void addRelationType(final RelationType type,
			final boolean isTreeSpanningRelationType) throws TransportException {
		if (myTagStack.peek().equals("nodes")) {
			myTagStack.pop();
			this.write("</nodes>\n");
			this.write("<relations>");
			myTagStack.push("relations");
		} else if (myTagStack.peek().equals("relationType")) {
			myTagStack.pop();
			this.write("</relationType>");
		}

		String tree = "false";

		if (isTreeSpanningRelationType) {
			tree = "true";
		}

		this.write("<relationType name=\"" + type.getName()
				+ "\" isTreeRelation=\"" + tree + "\">\n");
		myTagStack.push("relationType");
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#initialize()
	 */
	@Override
	public void initialize() throws TransportException {
		this.write("<?xml version=\"1.0\" encoding=\""
				+ this.myWriter.getEncoding() + "\"?>\n");
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#complete()
	 */
	@Override
	public void complete() throws TransportException {
		if (myTagStack.pop().equals("relationType")) {
			this.write("</relationType>\n");
		} else {
			throw new TransportException(
					"open tag <relationType> expected but not present");
		}

		if (myTagStack.pop().equals("relations")) {
			this.write("</relations>\n");
		} else {
			throw new TransportException(
					"open tag <relations> expected but not present");
		}

		if (myTagStack.pop().equals("model")) {
			this.write("</model>\n");
		} else {
			throw new TransportException(
					"open tag <model> excepted but not present");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#setRootNodeID(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	@Override
	public void setRootNodeID(final ID id) throws TransportException {
		this.write("<model rootNodeID=\"" + id + "\">\n");
		myTagStack.push("model");
	}

	/**
	 * This method is a wrapper of <code>OutputStreamWriter.write()</code>. All
	 * IOExceptions are cateched and converted to TransportExceptions.
	 * 
	 * @param content
	 *            The string which will be written to the stream
	 */
	private void write(final String content) throws TransportException {
		try {
			this.myWriter.write(content);
		} catch (IOException ex) {
			throw new TransportException("IO Error while rendering XML", ex);
		}
	}
}
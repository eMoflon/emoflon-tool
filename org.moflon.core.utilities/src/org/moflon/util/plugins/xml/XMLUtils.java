package org.moflon.util.plugins.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Several high-level utility methods for processing XML files
 */
public class XMLUtils {

	public static String formatXmlString(final Document doc, final IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask("Formatting XML string", 1);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			String output = result.getWriter().toString();

			monitor.worked(1);
			return output;
		} catch (TransformerFactoryConfigurationError | TransformerException ex) {
			throw new CoreException(new Status(IStatus.ERROR, MoflonUtilitiesActivator.getDefault().getPluginId(),
					"Formatting XML failed", ex));
		} finally {
			monitor.done();
		}
	}

	public static Document parseXmlDocument(final String content) throws CoreException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(content.getBytes()));

			XMLUtils.dropWhitespaceNodesFromTree(doc);

			return doc;
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
			throw new CoreException(new Status(IStatus.ERROR, MoflonUtilitiesActivator.getDefault().getPluginId(),
					"Formatting XML failed", ex));
		}
	}

	public static void dropWhitespaceNodesFromTree(final Document doc) throws XPathExpressionException {
		XPath xp = XPathFactory.newInstance().newXPath();
		NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

		for (int i = 0; i < nl.getLength(); ++i) {
			Node node = nl.item(i);
			node.getParentNode().removeChild(node);
		}
	}

	/**
	 * Reads the contents of the given files into a {@link Document} instance.
	 * 
	 * @param file
	 *            the input file
	 * @return the parsed document
	 * @throws CoreException
	 * 
	 * @throws IOException
	 *             if a problem occurs while reading the file
	 */
	public static Document parseXmlDocument(File file) throws CoreException, IOException {
		return parseXmlDocument(FileUtils.readFileToString(file));
	}

}

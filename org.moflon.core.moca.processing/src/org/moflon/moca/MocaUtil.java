package org.moflon.moca;

import static org.moflon.moca.MocaTokenFactory.createAttributeTree;
import static org.moflon.moca.MocaTokenFactory.createNodeTree;
import static org.moflon.moca.MocaTokenFactory.createTextTree;

import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.tree.CommonTree;

import MocaTree.Attribute;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import MocaTree.Text;
import MocaTree.TreeElement;

/**
 * Class for transformation of MocaTree<->CommonTree
 * 
 * @author david
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class MocaUtil {

	public static final String NodeStartIndexAttribute = "startIndex";

	public static final String NodeStopIndexAttribute = "stopIndex";

	public static final String NodeStartLineIndexAttribute = "startLineIndex";

	public static final String NodeStopLineIndexAttribute = "stopLineIndex";

	/**
	 * Trims the current token in the given Lexer
	 * 
	 * @param lexer
	 * @param leftMargin
	 *            amount of characters that will be removed from the beginning of
	 *            the token
	 * @param rightMargin
	 *            amount of characters that will be removed from the end of the
	 *            token
	 */
	public static void trim(Lexer lexer, int leftMargin, int rightMargin) {
		String text = lexer.getText();
		text = text.substring(leftMargin, text.length() - rightMargin);
		lexer.setText(text);
	}

	/**
	 * Removes leading and trailing whitespace from current token
	 * 
	 * @param lexer
	 */
	public static void trimWS(Lexer lexer) {
		String text = lexer.getText();
		lexer.setText(text.trim());
	}

	/**
	 * Transforms a MocaTree to a CommonTree:
	 * 
	 * @param element
	 *            A text or node element of a MocaTree
	 * @param tokenNames
	 *            A list of tokens used in the tree walker.
	 * @return
	 */
	public static CommonTree mocaTreeToCommonTree(Text element, String[] tokenNames) {
		return mocaTreeToCommonTree(element, tokenNames, true);
	}

	/**
	 * Transforms a MocaTree to a CommonTree
	 * 
	 * @param element
	 *            A text or node element of a MocaTree
	 * @param tokenNames
	 *            A list of tokens used in the tree walker.
	 * @param sort
	 *            Decide if the tree is to be sorted first or not
	 * @return
	 */
	public static CommonTree mocaTreeToCommonTree(Text element, String[] tokenNames, boolean sort) {
		/*
		 * Node elements are transformed to a CommonTree ^(name children)
		 */
		if (element instanceof Node) {
			Node node = (Node) element;
			CommonTree result = createNodeTree(node, tokenNames);

			// apply indices (if proper indices are used)
			if (sort)
				new MocaTreeSorter().applyIndices(node);

			// transform attributes and child nodes
			transformTreeElements(result, node.getAttribute(), tokenNames, sort);
			transformTreeElements(result, node.getChildren(), tokenNames, sort);

			return result;
		}

		/*
		 * Text elements are transformed to a CommonTree ^(TEXT name) The root element
		 * of a MocaTree must be a node.
		 */
		else {
			if (element.getParentNode() == null) {
				throw new IllegalArgumentException(
						"The root element of the given MocaTree is a Text element. Only Node elements can be the root of a MocaTree!");
			} else {
				return createTextTree(element.getName(), tokenNames);
			}
		}
	}

	/**
	 * Adds corresponding subtrees for the given tree elements
	 * 
	 * @param result
	 * @param node
	 * @param tokenNames
	 */
	private static void transformTreeElements(CommonTree result, List<? extends TreeElement> elements,
			String[] tokenNames, boolean sort) {
		for (TreeElement element : elements) {
			CommonTree childTree = transformTreeElement(tokenNames, element, sort);
			result.addChild(childTree);
		}
	}

	private static CommonTree transformTreeElement(String[] tokenNames, TreeElement treeElement, boolean sort) {
		CommonTree childTree = null;
		if (treeElement instanceof Attribute) {
			childTree = createAttributeTree(tokenNames, (Attribute) treeElement);
		} else if (treeElement instanceof Node) {
			Node node = (Node) treeElement;
			// transform child node using mocaTreeToCommonTree
			childTree = mocaTreeToCommonTree(node, tokenNames, sort);
		} else if (treeElement instanceof Text) {
			childTree = createTextTree(treeElement.getName(), tokenNames);
		} else {
			throw new IllegalArgumentException(
					"The given tree element must be Attribute, Node or Text, given: " + treeElement);
		}
		return childTree;
	}

	/**
	 * Transforms CommonTree to MocaTree. The node label is set to the text entry of
	 * the corresponding CommonTree.
	 * 
	 * @param tree
	 * @param tokenNames
	 * @return
	 */
	public static Node commonTreeToMocaTree(CommonTree tree) {
		if (tree.token != null && tree.getText().equals(MocaTokenFactory.TEXT)) {
			throw new IllegalArgumentException("root of a common tree cannot be a text element ^(TEXT ...) !");
		} else {
			return transformRoot(tree);
		}

	}

	/**
	 * Transforms the root node (which must be a Node).
	 * 
	 * @param tree
	 * @return
	 */
	private static Node transformRoot(CommonTree tree) {
		Node result = createNodeFromCommonTree(tree);
		for (int i = 0; i < tree.getChildCount(); i++) {
			transformChild(tree, result, i);
		}
		return result;
	}

	/**
	 * Transforms other nodes in the CommonTree (which can be Node or Text).
	 * 
	 * @param tree
	 * @return
	 */
	private static Text transformChildTree(CommonTree tree) {
		if (tree.getText().equals("TEXT")) {
			Text result = createTextFromCommonTree(tree);
			return result;
		} else {
			return transformRoot(tree);
		}
	}

	/**
	 * Transforms a child of tree to MocaTree and adds it to node.
	 * 
	 * @param tree
	 * @param node
	 * @param index
	 */
	private static void transformChild(CommonTree tree, Node node, int index) {
		CommonTree child = (CommonTree) tree.getChild(index);
		if (child != null) {
			// create attribute
			if (MocaTokenFactory.ATTRIBUTE.equals(child.getText())) {
				Attribute attribute = MocaTreeFactory.eINSTANCE.createAttribute();
				if (child.getChildCount() > 1) {
					attribute.setName(child.getChild(0).getText());
					attribute.setValue(child.getChild(1).getText());
					attribute.setIndex(index);
					node.getAttribute().add(attribute);
				}
			}
			// transform child, add it to node and set the
			// child index
			else {
				Text childNode = transformChildTree((CommonTree) child);
				childNode.setIndex(index);
				node.getChildren().add(childNode);
			}
		}
	}

	/**
	 * Creates a text node from a CommonTree.
	 * 
	 * @param tree
	 * @return
	 */
	private static Text createTextFromCommonTree(CommonTree tree) {
		// valid text nodes have the form ^(TEXT content)
		if (tree.getChildCount() != 1 || tree.getChild(0).getChildCount() > 0) {
			throw new IllegalArgumentException("The TEXT tree " + tree.toStringTree()
					+ " is invalid. Valid text trees have the form ^(TEXT content)");
		}
		Text result = MocaTreeFactory.eINSTANCE.createText();
		// a text node is represented as (TEXT content), set
		// name of text to content
		String name = tree.getChild(0).getText();
		result.setName(name);
		return result;
	}

	/**
	 * Creates a node from a CommonTree.
	 * 
	 * @param tree
	 * @return
	 */
	private static Node createNodeFromCommonTree(CommonTree tree) {
		Node result = MocaTreeFactory.eINSTANCE.createNode();
		if (tree.token == null) {
			// a null token is represented as empty string
			// (a token with empty String cannot occur)
			result.setName("");
		} else {
			result.setName(tree.getText());
		}

		if (tree.getToken() instanceof CommonToken) {
			CommonToken commonToken = (CommonToken) tree.getToken();
			result.setStartIndex(commonToken.getStartIndex());
			result.setStopIndex(commonToken.getStopIndex());
			result.setStartLineIndex(tree.getTokenStartIndex());
			result.setStopLineIndex(tree.getTokenStopIndex());
		}
		return result;
	}

	public static void appendNewAttribute(Node node, String attributeName, String attributeValue) {
		Attribute attribute = MocaTreeFactory.eINSTANCE.createAttribute();
		attribute.setName(attributeName);
		attribute.setValue(attributeValue);
		attribute.setIndex(node.getAttribute().size());
		node.getAttribute().add(attribute);
	}

	public static Attribute getAttributeWithName(Node node, String name) {
		for (Attribute attribute : node.getAttribute()) {
			if (attribute.getName().equals(name))
				return attribute;
		}
		return null;
	}

	public static String firstToUpper(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}

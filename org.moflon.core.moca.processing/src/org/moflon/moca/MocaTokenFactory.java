package org.moflon.moca;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

/**
 * Utility class for creation of CommonTrees and CommonTokens
 *
 * @author david
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class MocaTokenFactory {

	public static final String ID = "ID";

	public static final String STRING = "STRING";

	public static final String ATTRIBUTE = "ATTRIBUTE";

	public static final String TEXT = "TEXT";

	public static CommonToken createToken(String text, String[] tokenNames) {
		if (text == null || text.equals("")) {
			// a node with name="" represents a null token.
			return null;
		} else {
			return new CommonToken(getTokenIndex(tokenNames, text), text);
		}
	}

	/**
	 * Creates a token for the given node.
	 * 
	 * @param child
	 * @param tokenNames
	 *            A list of token names
	 * @return
	 */
	public static CommonToken createToken(Text child, String[] tokenNames) {
		// an empty string represents a null token.
		if (child.getName() == "") {
			return null;
		} else {
			return createToken(child.getName(), tokenNames);
		}
	}

	public static CommonTree createCommonTree(String text, String[] tokenNames) {
		return new CommonTree(createToken(text, tokenNames));
	}

	public static Tree createCommonTree(String token, String text, String[] tokenNames) {
		return new CommonTree(new CommonToken(getTokenIndex(tokenNames, token), text));
	}

	/**
	 * Searches for the given token in tokenNames.
	 * 
	 * @param tokenNames
	 * @param token
	 * @return
	 */
	public static int getTokenIndex(String[] tokenNames, String token) {
		// first search for token in tokenNames
		List<String> tokenList = Arrays.asList(tokenNames);
		int result = tokenList.indexOf(token);
		if (result != -1) {
			return result;
		}
		// if no match: search for 'token' (if the ANTLR grammar contains ('+' ...)
		String escapedToken = "'" + token + "'";
		result = tokenList.indexOf(escapedToken);
		if (result != -1) {
			return result;
		}
		// return the type of the token STRING (default token)
		result = tokenList.indexOf("STRING");
		if (result != -1) {
			return result;
		}
		throw new IllegalArgumentException("Tree grammars must contain a STRING token!");
	}

	public static CommonTree createTextTree(String name, String[] tokenNames) {
		CommonTree result = new CommonTree(createToken(TEXT, tokenNames));
		result.addChild(createCommonTree(name, tokenNames));
		return result;
	}

	public static CommonTree createNodeTree(Node node, String[] tokenNames) {
		CommonTree result = new CommonTree();
		result.token = createToken(node.getName(), tokenNames);
		return result;
	}

	public static CommonTree createAttributeTree(String[] tokenNames, Attribute element) {
		CommonTree attributeTree = createCommonTree(ATTRIBUTE, tokenNames);
		attributeTree.addChild(createCommonTree(ID, element.getName(), tokenNames));
		attributeTree.addChild(createCommonTree(STRING, element.getValue(), tokenNames));
		return attributeTree;
	}

}

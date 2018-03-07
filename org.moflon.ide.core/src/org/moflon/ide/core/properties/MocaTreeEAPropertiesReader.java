package org.moflon.ide.core.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class MocaTreeEAPropertiesReader {
	private static final Logger logger = Logger.getLogger(MocaTreeEAPropertiesReader.class);

	private ResourceSet set;

	private Node mocaTree;

	public static final String METAMODEL_PROJECT_NAME_KEY = "metamodelProject";

	/**
	 * Extracts the specification metadata from the MOCA tree in the .temp folder
	 */
	public Map<String, PluginProperties> getProperties(final IProject metamodelProject) throws CoreException {
		IFile mocaFile = MetamodelProjectUtil.getExportedMocaTree(metamodelProject);

		if (mocaFile.exists()) {
			// Create and initialize resource set
			set = eMoflonEMFUtil.createDefaultResourceSet();
			eMoflonEMFUtil.installCrossReferencers(set);

			// Load Moca tree in read-only mode
			URI mocaFileURI = URI.createPlatformResourceURI(mocaFile.getFullPath().toString(), true);
			Resource mocaTreeResource = set.getResource(mocaFileURI, true);
			mocaTree = (Node) mocaTreeResource.getContents().get(0);
			Map<String, PluginProperties> properties = getProperties(mocaTree);
			properties.keySet()
					.forEach(p -> properties.get(p).put(METAMODEL_PROJECT_NAME_KEY, metamodelProject.getName()));
			return properties;
		} else {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Cannot extract project properties, since Moca tree is missing."));
		}
	}

	private Map<String, PluginProperties> getProperties(final Node rootNode) throws CoreException {
		this.mocaTree = rootNode;
		Map<String, PluginProperties> propertiesMap = new HashMap<>();
		Node exportedTree = (Node) rootNode.getChildren().get(0);

		assert "exportedTree".equals(exportedTree.getName());

		EList<Text> rootPackages = exportedTree.getChildren();
		for (final Text rootText : rootPackages) {
			final Node rootPackage = (Node) rootText;
			PluginProperties properties = getProjectProperties(rootPackage);
			propertiesMap.put(properties.get(PluginProperties.PLUGIN_ID_KEY), properties);
		}

		return propertiesMap;
	}

	private PluginProperties getProjectProperties(final Node rootNode) throws CoreException {
		final PluginProperties properties = new PluginProperties();

		properties.put(PluginProperties.NAME_KEY, getValueForProperty("Moflon::Name", rootNode));
		properties.put(PluginProperties.NAME_KEY, getValueForProperty("Moflon::NsPrefix", rootNode));
		properties.put(PluginProperties.NS_URI_KEY, getValueForProperty("Moflon::NsUri", rootNode));
		properties.put(PluginProperties.PLUGIN_ID_KEY, getValueForProperty("Moflon::PluginID", rootNode));
		properties.put(PluginPropertiesHelper.EXPORT_FLAG_KEY, getValueForProperty("Moflon::Export", rootNode));
		properties.put(PluginPropertiesHelper.VALIDATED_FLAG_KEY, getValueForProperty("Moflon::Validated", rootNode));
		properties.put(PluginProperties.WORKING_SET_KEY, getValueForProperty("Moflon::WorkingSet", rootNode));
		PluginPropertiesHelper.setHasAttributeConstraints(containsAttributeConstraintsNode(rootNode), properties);

		switch (rootNode.getName()) {
		case "EPackage":
			properties.put(PluginProperties.TYPE_KEY, PluginPropertiesHelper.REPOSITORY_PROJECT);
			break;
		case "TGG":
			properties.put(PluginProperties.TYPE_KEY, PluginPropertiesHelper.INTEGRATION_PROJECT);
			break;
		default:
			logger.warn("Unknown node type in Moca tree: " + rootNode.getName());
		}

		properties.setDependencies(extractDependencies(rootNode));

		return properties;
	}

	private boolean containsAttributeConstraintsNode(Node rootNode) {
		final Queue<Node> unvisitedNodes = new LinkedList<>();
		unvisitedNodes.add(rootNode);
		while (!unvisitedNodes.isEmpty()) {
			Node nextNode = unvisitedNodes.poll();
			if ("AttributeConstraints".equals(nextNode.getName()))
				return true;

			nextNode.getChildren().stream().filter(t -> t instanceof Node).map(t -> (Node) t)
					.forEach(n -> unvisitedNodes.add(n));
		}
		return false;
	}

	public List<String> extractDependencies(final Node rootPackage) throws CoreException {
		Collection<Text> dependenciesNodes = rootPackage.getChildren("dependencies");

		if (dependenciesNodes.size() != 1) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Missing dependencies nodes for project " + rootPackage.getName()));
		}

		final List<String> dependencies = new ArrayList<>();
		final Node dependenciesNode = (Node) dependenciesNodes.iterator().next();

		for (final Text text : dependenciesNode.getChildren()) {
			final String dependency = ((Node) text).getName();
			dependencies.add(dependency);
		}

		return dependencies;
	}

	public String getValueForProperty(final String property, final Node rootPackage) throws CoreException {
		String value;
		Collection<Attribute> attributes = rootPackage.getAttribute(property);
		if (!attributes.isEmpty()) {
			value = attributes.iterator().next().getValue();
		} else {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					"Missing property " + property + " for project " + rootPackage.getName()));
		}
		return value;
	}

	public ResourceSet getResourceSet() {
		return set;
	}

	public Node getMocaTree() {
		return mocaTree;
	}

	public Resource getMocaTreeResource() {
		return mocaTree.eResource();
	}

}

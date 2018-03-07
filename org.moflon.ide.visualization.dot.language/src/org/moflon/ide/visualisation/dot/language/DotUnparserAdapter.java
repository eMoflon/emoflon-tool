package org.moflon.ide.visualisation.dot.language;

import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.visualization.dot.language.AbstractGraph;
import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class DotUnparserAdapter {
	static final private Logger log = Logger.getLogger(DotUnparserAdapter.class);

	public String unparse(AbstractGraph graph) {
		try {
			ST template = null;
			if (graph instanceof DirectedGraph) {
				template = getStringTemplateGroup().getInstanceOf("DirectedGraph");
				template.add("directGraph", graph);
			}
			if (graph instanceof ClassGraph) {
				template = getStringTemplateGroup().getInstanceOf("ClassGraph");
				template.add("classGraph", graph);
			}
			String plantUMLCode = template.render();
			return plantUMLCode;
		} catch (FileNotFoundException | NullPointerException e) {
			log.error("Unable to unparse the Graph", e);
		}

		return graph == null ? "Unable to unparse the Graph is null" : "Unable to unparse: " + graph.getName();
	}

	private STGroup getStringTemplateGroup() throws FileNotFoundException {
		URL templateFile = WorkspaceHelper.getPathRelToPlugIn("resources/templates/Dot.stg",
				WorkspaceHelper.getPluginId(getClass()));

		return new STGroupFile(templateFile, "UTF-8", '<', '>');
	}
}
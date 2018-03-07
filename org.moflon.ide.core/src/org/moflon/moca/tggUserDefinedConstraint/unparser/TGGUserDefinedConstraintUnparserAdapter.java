package org.moflon.moca.tggUserDefinedConstraint.unparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.MocaUtil;
import org.moflon.tgg.language.csp.Adornment;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.csp.Variable;

public class TGGUserDefinedConstraintUnparserAdapter {
	private static final Logger logger = Logger.getLogger(TGGUserDefinedConstraintUnparserAdapter.class);

	public String unparseCspConstraint(final String projectName, final TGGConstraint constraint) {
		String content = "";
		try {
			StringTemplateGroup group = getStringTemplateGroup();
			StringTemplate mainTemplate = group.getInstanceOf("main");
			// StringTemplate parameterNameTemplate = group.getInstanceOf("parameterName");
			// StringTemplate parameterTypeTemplate = group.getInstanceOf("parameterType");

			mainTemplate.setAttribute("projectName", projectName);
			mainTemplate.setAttribute("constraintName", MocaUtil.firstToUpper(constraint.getName()));

			List<StringTemplate> adornmentTemplates = computeAdornmentTemplates(constraint, group);
			mainTemplate.setAttribute("adornments", adornmentTemplates.toArray());

			List<String> parameters = new ArrayList<String>();

			for (@SuppressWarnings("unused")
			Variable variable : constraint.getVariables()) {
				parameters.add("");
			}
			mainTemplate.setAttribute("parameters", parameters);

			return mainTemplate.toString();
		} catch (FileNotFoundException e) {
			LogUtils.error(logger, e);
		}
		return content;

	}

	private List<StringTemplate> computeAdornmentTemplates(final TGGConstraint constraint,
			final StringTemplateGroup group) {
		List<StringTemplate> adornmentTemplates = new ArrayList<StringTemplate>();
		for (Adornment adornment : constraint.getAllowedAdornments()) {
			StringTemplate adornmentTemplate = group.getInstanceOf("adornmentCase");
			adornmentTemplate.setAttribute("adornment", adornment.getValue());
			adornmentTemplates.add(adornmentTemplate);
		}
		return adornmentTemplates;
	}

	protected StringTemplateGroup getStringTemplateGroup() throws FileNotFoundException {
		URL templateFile = WorkspaceHelper.getPathRelToPlugIn("resources/templates/TGGUserDefinedConstraint.stg",
				WorkspaceHelper.getPluginId(getClass()));

		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(templateFile.openStream());
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}

		return new StringTemplateGroup(reader);
	}

}
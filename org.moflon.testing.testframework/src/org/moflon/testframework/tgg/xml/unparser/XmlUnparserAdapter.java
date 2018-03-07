package org.moflon.testframework.tgg.xml.unparser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.moflon.core.moca.processing.unparser.impl.XMLUnparserImpl;
import org.moflon.core.utilities.LogUtils;

public class XmlUnparserAdapter extends XMLUnparserImpl {
	private static final Logger logger = Logger.getLogger(XmlUnparserAdapter.class);

	@Override
	public boolean canUnparseFile(final String fileName) {
		return true;
	}

	@Override
	protected StringTemplateGroup getStringTemplateGroup() throws FileNotFoundException {
		try {
			InputStream pathToTemplate = this.getClass().getClassLoader().getResourceAsStream("templates/XML.stg");

			// perhaps running on spawned eclipse
			if (pathToTemplate == null)
				pathToTemplate = this.getClass().getClassLoader().getResourceAsStream("XML.stg");

			InputStreamReader reader = new InputStreamReader(pathToTemplate);
			StringTemplateGroup stg = new StringTemplateGroup(reader, DefaultTemplateLexer.class);
			return stg;
		} catch (Exception e) {
			LogUtils.error(logger, e);
		}
		return null;
	}

}
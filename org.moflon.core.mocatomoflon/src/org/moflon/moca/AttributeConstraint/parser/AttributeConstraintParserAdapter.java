package org.moflon.moca.AttributeConstraint.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.parser.impl.ParserImpl;
import org.moflon.core.utilities.LogUtils;
import org.moflon.moca.MocaUtil;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;

import MocaTree.Node;
import SDMLanguage.patterns.StoryPattern;

public class AttributeConstraintParserAdapter extends ParserImpl {
	private static final Logger logger = Logger.getLogger(AttributeConstraintParserAdapter.class);

	private String inputString;

	private StoryPattern sourceStoryPattern;

	private ValidationReport parseReport;

	@Override
	public boolean canParseFile(final String fileName) {

		return false;
	}

	public ValidationReport parse(final String input) {
		inputString = input;
		parseReport = ResultFactory.eINSTANCE.createValidationReport();
		parseReport.setResult(parse(new StringReader(input)));
		return parseReport;
	}

	public ValidationReport parse(final String input, final StoryPattern storyPattern) {

		sourceStoryPattern = storyPattern;
		return this.parse(input);
	}

	@Override
	public Node parse(final Reader reader) {
		if (parseReport == null) {
			throw new IllegalStateException("Parse Report has not been initialized yet");
		}

		try {
			AttributeConstraintLexer lexer = new AttributeConstraintLexer(new ANTLRReaderStream(reader));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			AttributeConstraintParser parser = new AttributeConstraintParser(tokens);
			CommonTree result = (CommonTree) parser.main().tree;

			// Log Lexer Errors

			for (Problem problem : lexer.problems) {
				parseReport.getErrorMessages().add(createErrorMessage(problem));
			}

			// Log Parser Errors
			for (Problem problem : parser.problems) {
				parseReport.getErrorMessages().add(createErrorMessage(problem));
			}

			return MocaUtil.commonTreeToMocaTree(result);
		} catch (IOException | RecognitionException e) {
			LogUtils.error(logger, e);
		}
		return null;
	}

	private ErrorMessage createErrorMessage(final Problem problem) {
		StringBuilder msg = new StringBuilder();
		msg.append("Problems parsing attribute constraint expression:\n ");
		msg.append("\t" + inputString.replace("\n", "\n\t"));
		msg.append("\n at line ");
		msg.append(problem.getLine() + ":" + problem.getCharacterPositionStart());
		msg.append(" " + problem.getMessage());

		ErrorMessage errMsg = ResultFactory.eINSTANCE.createErrorMessage();
		errMsg.setId(msg.toString());
		errMsg.setSeverity(Severity.ERROR);
		if (sourceStoryPattern != null) {
			errMsg.getLocation().add(sourceStoryPattern);
		}
		return errMsg;
	}
}
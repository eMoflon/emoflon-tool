package org.moflon.compiler.sdm.democles.stringtemplate;

import org.apache.log4j.Logger;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.STMessage;

/**
 * An error listener to be used during the construction of {@link STGroup}s.
 * 
 * The error listener forwards error messages to a {@link Logger} instance that
 * is configured by the constructor.
 */
public class LoggingSTErrorListener implements STErrorListener {
	private Logger logger;

	public LoggingSTErrorListener(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void runTimeError(final STMessage msg) {
		// Suppress error reporting if the /democles/Constant template produces a
		// NO_SUCH_PROPERTY error
		// The above-mentioned template has to distinguish between Boolean.FALSE and
		// null values.
		// StringTemplate evaluates both to false, consequently their class property
		// (i.e., getClass() method)
		// has to be additionally invoked, which produces a NO_SUCH_PROPERTY error in
		// case of null values
		// See <if(constant.value || constant.value.class)> in the /democles/Constant
		// template
		if ("/democles/Constant".equals(msg.self.getName()) && msg.error == ErrorType.NO_SUCH_PROPERTY) {
			return;
		}
		logger.error("[ST runtime error] " + msg);
	}

	@Override
	public void internalError(final STMessage msg) {
		logger.error("[ST internal error] " + msg);
	}

	@Override
	public void compileTimeError(final STMessage msg) {
		logger.error("[ST compile time error] " + msg);
	}

	@Override
	public void IOError(final STMessage msg) {
		logger.error("[ST IO error] " + msg);
	}

}

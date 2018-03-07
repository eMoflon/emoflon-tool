package org.moflon.ide.core.runtime.builders;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.Tool;
import org.antlr.tool.ANTLRErrorListener;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Message;
import org.antlr.tool.ToolMessage;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.utilities.ExceptionUtil;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;

public class AntlrBuilder extends AbstractBuilder {
	private static final boolean DEBUG = false;

	private static final String EXTENSION = ".g";

	private static final Logger logger = Logger.getLogger(AntlrBuilder.class);

	private static final String MARKER = "org.moflon.ide.AntlrEditorProblem";

	private static final Pattern ANTLR_FILENAME_PATTERN = Pattern.compile("(.*)((?:Lexer)|(?:Parser)).g");

	private List<String> builtParsers = new ArrayList<String>();

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		builtParsers.clear();

		return super.build(kind, args, monitor);
	}

	@Override
	public boolean visit(final IResource resource) throws CoreException {
		processAntlrResource(resource);
		return true;
	}

	@Override
	public boolean visit(final IResourceDelta delta) throws CoreException {
		if (delta.getResource().exists())
			processAntlrResource(delta.getResource());

		return true;
	}

	public static String getId() {
		return "org.moflon.ide.core.runtime.builders.AntlrBuilder";
	}

	private boolean processAntlrResource(final IResource resource) throws CoreException {
		if (resource.getName().endsWith(EXTENSION)) {
			debug("AntlrBuilder: Processing Antlr Resource: " + resource.getName());
			if (resource.getFullPath().toString().contains("/bin/")) {
				debug("Skipping file because of bin location.");
				return true;
			}

			Matcher m = ANTLR_FILENAME_PATTERN.matcher(resource.getName());
			String prefix = null;
			String type = null;

			if (m.matches()) {
				prefix = m.group(1);
				type = m.group(2);
			}

			if (prefix != null) {
				if (builtParsers.contains(prefix)) {
					debug("Skipping file because we already built it.");
					return true;
				} else {
					builtParsers.add(prefix);
				}
			}

			try {
				// If parser then make sure lexer is built first
				if ("Parser".equals(type))
					compileAntlrResource(resource.getParent().findMember(prefix + "Lexer.g"));

				compileAntlrResource(resource);

				// If lexer then refresh parser
				if ("Lexer".equals(type))
					compileAntlrResource(resource.getParent().findMember(prefix + "Parser.g"));
			} catch (URISyntaxException e) {
				ExceptionUtil.throwCoreExceptionAsError(e.getMessage(), WorkspaceHelper.getPluginId(getClass()), e);
			}

		}
		return true;
	}

	private void compileAntlrResource(final IResource resource) throws CoreException, URISyntaxException {
		logger.debug(new Date().toString());
		logger.debug("Processing Antlr Resource: " + resource.getName());

		resource.deleteMarkers(MARKER, false, IResource.DEPTH_ZERO);

		String outputDirectory = resource.getParent().getRawLocation().toOSString();
		String inputFile = resource.getRawLocation().toOSString();
		try {
			String[] args = new String[] { "-o", outputDirectory, inputFile };
			Tool antlr = new Tool(args);
			ErrorManager.setErrorListener(new ANTLRErrorListener() {

				@Override
				public void warning(final Message msg) {
					logger.warn("[ANTLR warning] " + msg);
				}

				@Override
				public void info(final String msg) {
					logger.warn("[ANTLR info] " + msg);
				}

				@Override
				public void error(final ToolMessage msg) {
					logger.error("[ANTLR error] " + msg);
				}

				@Override
				public void error(final Message msg) {
					logger.error("[ANTLR error] " + msg);
				}
			});
			antlr.process();
		} catch (Exception e) {
			LogUtils.error(logger, e);
		}
		this.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	private void debug(final String message) {
		if (DEBUG)
			logger.debug("DEBUG: " + message);
	}

	@Override
	protected boolean processResource(final IProgressMonitor monitor) throws CoreException {
		logger.debug("Process resource.");

		final SubMonitor subMon = SubMonitor.convert(monitor, "Process resource", 1);
		getProject().accept(this);
		subMon.worked(1);

		return true;
	}

	@Override
	protected void cleanResource(final IProgressMonitor monitor) throws CoreException {
		cleanDirectory(getProject(), monitor);
	}

	private void cleanDirectory(final IContainer container, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Cleaning directory", 3 * container.members().length);
		Pattern antlrFilePattern = Pattern.compile("(.+)\\.g");
		for (IResource res : container.members()) {
			if (res.getType() == IResource.FILE) {
				Matcher m = antlrFilePattern.matcher(res.getName());
				if (m.matches()) {
					deleteResource(container, m.group(1) + ".tokens", subMon.split(1));
					deleteResource(container, m.group(1) + ".java", subMon.split(1));
				} else {
					subMon.worked(2);
				}
			}

			if (res.getType() == IResource.FOLDER) {
				cleanDirectory((IFolder) res, subMon.split(1));
			} else {
				subMon.worked(1);
			}
		}

	}

	private void deleteResource(final IContainer container, final String string, final IProgressMonitor monitor)
			throws CoreException {
		logger.debug("Removing file '" + string + "'");
		IResource res = container.findMember(string);
		final SubMonitor subMon = SubMonitor.convert(monitor, "Deleting", 1);
		if (res != null && res.exists()) {
			res.delete(true, subMon.split(1));
		}
	}

}

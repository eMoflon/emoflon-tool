package org.moflon.ide.visualisation.dot.language;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.moflon.core.utilities.ExceptionUtil;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.visualization.dot.language.AbstractGraph;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.runtime.CorrespondenceModel;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public abstract class EMoflonDiagramTextProvider implements DiagramTextProvider {
	private static final Logger logger = Logger.getLogger(EMoflonDiagramTextProvider.class);

	private Map<Object, String> diagramTextCache = new HashMap<>();

	private Map<Object, SynchronizationHelper> syncHelperCache = new HashMap<>();

	private Map<Object, Delta> deltaCache = new HashMap<>();

	private EcoreEditor currentEditor;

	private Statistics statisticsOfLastRun = null;

	/**
	 * Returns whether the given element can be translated by this particular class.
	 */
	public abstract boolean isElementValidInput(Object selectedElement);

	/**
	 * Returns the plugin ID of the plugin that provides the rules for visualizing
	 * the supported elements
	 * 
	 * By default, this method retrieves the plugin ID via
	 * {@link WorkspaceHelper#getPluginId(Class)} using the dynamic class of this
	 * object via {@link Object#getClass()}
	 * 
	 * @return
	 */
	protected String getPluginId() {
		return WorkspaceHelper.getPluginId(getClass());
	}

	/**
	 * Returns whether to invoke the TGG in forward (true) or backward (false)
	 * direction to obtain an instance of {@link DirectedGraph}.
	 */
	protected abstract boolean directionIsForward();

	/**
	 * Returns the {@link EPackage} containing the correspondence metamodel for the
	 * selected type of transation
	 */
	protected abstract EPackage getPackage();

	/**
	 * Returns the DOT visualization for the given selection
	 * 
	 * If the selection is null or empty, the result is an empty string.
	 */
	@Override
	public String getDiagramText(IEditorPart editorPart, ISelection selection) {
		EObject selectedElement = getSelectedObject(editorPart);
		if (selectedElement != null && isElementValidInput(selectedElement)) {
			// Extract input object
			EObject input = getInput(selectedElement);
			if (!diagramTextCache.containsKey(selectedElement) || selectionHasBeenChanged(selectedElement)) {
				String dotDiagram = unparse(input, selectedElement);
				if (dotDiagram == null)
					return "";
				diagramTextCache.put(selectedElement, dotDiagram);
			}

			String diagramText = diagramTextCache.get(selectedElement);
			return diagramText;
		}

		return "";
	}

	protected EObject getInput(EObject selectedElement) {
		return selectedElement;
	}

	protected String unparse(EObject input, EObject selectedElement) {
		return unparse(modelToDot(input));
	}

	protected String unparse(AbstractGraph graph) {
		return new DotUnparserAdapter().unparse(graph);
	}

	@Override
	public boolean supportsEditor(IEditorPart editorPart) {
		EObject selectedElement = getSelectedObject(editorPart);

		if (selectedElement == null || !isElementValidInput(selectedElement))
			return false;

		if (editorPart.equals(currentEditor))
			return true;

		if (editorPart instanceof EcoreEditor) {
			currentEditor = (EcoreEditor) editorPart;
			clearCache();

			return true;
		}

		return false;
	}

	/**
	 * Returns statistics of the previous visualization transformation. May be null.
	 * 
	 * @return
	 */
	public Statistics getStatisticsOfLastRun() {
		return this.statisticsOfLastRun;
	}

	public SynchronizationHelper getSynchronizationHelperForObject(final EObject input) {
		return this.syncHelperCache.get(input);
	}

	/**
	 * Removes all elements from the internal cache
	 */
	public void clearCache() {
		diagramTextCache.clear();
		deltaCache.clear();
		syncHelperCache.clear();
	}

	private boolean selectionHasBeenChanged(EObject input) {
		return deltaCache.containsKey(input) && deltaCache.get(input).isChangeDetected();
	}

	public AbstractGraph modelToDot(final EObject input) {
		final URL pathToPlugin = WorkspaceHelper.getPathRelToPlugIn("/", getPluginId());
		final ResourceSet resourceSet = input.eResource().getResourceSet();

		final long tic = System.nanoTime();
		try {
			if (hasSynchronizationInformationForObject(input))
				return runSync(input);
			else
				return runBatch(pathToPlugin, input, resourceSet);

		} catch (final Exception e) {
			LogUtils.error(logger, e, "Exception during visualization of " + eMoflonEMFUtil.getIdentifier(input) + ": "
					+ ExceptionUtil.displayExceptionAsString(e), e);
		} finally {
			final long toc = System.nanoTime();
			final double durationInMillis = (toc - tic) / 1e6;
			updateStatisticsOfLastRun(input, durationInMillis);

			logger.debug(formatStatistics(this.statisticsOfLastRun));
			resourceSet.getResources().removeIf(this::unwantedResource);
		}

		return null;
	}

	private boolean hasSynchronizationInformationForObject(final EObject input) {
		return syncHelperCache.containsKey(input);
	}

	private void updateStatisticsOfLastRun(final EObject input, final double durationInMillis) {
		final int edges = eMoflonEMFUtil.getEdgeCount(input);
		final int nodes = eMoflonEMFUtil.getNodeCount(input);
		this.statisticsOfLastRun = new Statistics(input, nodes, edges, durationInMillis);
	}

	private boolean unwantedResource(Resource r) {
		return r.getURI() != null && r.getURI().toString().endsWith("tempOutputContainer.xmi");
	}

	private static String formatStatistics(final Statistics statistics) {
		return String.format("Visualisation of [%s (E:%d + V:%d = %d] in: %.6fms",
				eMoflonEMFUtil.getIdentifier(statistics.input), statistics.edgeCount, statistics.nodeCount,
				statistics.edgeCount + statistics.nodeCount, statistics.durationInMillis);
	}

	private AbstractGraph runSync(final EObject input) {
		logger.debug("Running synchronization...");
		SynchronizationHelper helper = syncHelperCache.get(input);
		helper.setDelta(deltaCache.get(input));
		AbstractGraph result = runTrafo(helper);
		deltaCache.get(input).clear();
		return result;
	}

	private AbstractGraph runTrafo(SynchronizationHelper helper) {
		Object graph = null;
		// Remove for testing
		// helper.setMute(true);
		try {
			if (directionIsForward()) {
				helper.integrateForward();
				postprocess(helper.getCorr());
				graph = helper.getTrg();
			} else {
				helper.integrateBackward();
				postprocess(helper.getCorr());
				graph = helper.getSrc();
			}
			return (AbstractGraph) graph;
		} catch (ClassCastException cce) {
			if (graph instanceof TempOutputContainer) {
				logger.error("The graph has two or more roots");
			} else {
				logger.error(cce.getMessage(), cce);
			}
		}
		return null;
	}

	protected void postprocess(CorrespondenceModel corr) {
		// Per default no postprocessing
	}

	/**
	 * Performs a batch transformation and registers listeners for subsequent
	 * changes
	 * 
	 * @param pathToTggRulePlugin
	 *            path to the plugin that contains the generated TGG rules that
	 *            support the model-to-visualization transformation for the given
	 *            input. Used to create the appropriate
	 *            {@link SynchronizationHelper}
	 * @param input
	 *            the model to be visualized
	 * @param rs
	 *            the resource set to be used by the created
	 *            {@link SynchronizationHelper}
	 * @return
	 */
	private AbstractGraph runBatch(URL pathToTggRulePlugin, EObject input, ResourceSet rs) {
		logger.debug("Running batch...");

		final SynchronizationHelper helper = registerSynchronizationHelper(pathToTggRulePlugin, input, rs);
		registerConfigurator(helper);
		final AbstractGraph result = runTrafo(helper);
		this.registerChangeDetector(input);

		return result;
	}

	protected void registerConfigurator(SynchronizationHelper helper) {
		// per default standard configurator
	}

	private SynchronizationHelper registerSynchronizationHelper(URL pathToPlugin, EObject input, ResourceSet rs) {
		final SynchronizationHelper helper = new SynchronizationHelper(getPackage(), pathToPlugin.getFile(), rs);
		helper.setMute(true);
		syncHelperCache.put(input, helper);

		if (directionIsForward())
			helper.setSrc(input);
		else
			helper.setTrg(input);
		return helper;
	}

	private void registerChangeDetector(EObject input) {
		final Delta delta = new Delta();
		new OnlineChangeDetector(delta, input);
		deltaCache.put(input, delta);
	}

	private EObject getSelectedObject(IEditorPart editorPart) {
		ISelection selection = editorPart.getSite().getSelectionProvider().getSelection();

		if (selection != null && !selection.isEmpty() && selection instanceof TreeSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof EObject) {
				return (EObject) structuredSelection.getFirstElement();
			}
		}

		return null;
	}

	/**
	 * Collects information about a visualization transformation application
	 */
	public class Statistics {
		public EObject input;

		public int nodeCount;

		public int edgeCount;

		public double durationInMillis;

		private Statistics(EObject input, int nodeCount, int edgeCount, double durationInMillis) {
			this.input = input;
			this.nodeCount = nodeCount;
			this.edgeCount = edgeCount;
			this.durationInMillis = durationInMillis;
		}

	}
}

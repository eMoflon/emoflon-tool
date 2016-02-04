package org.moflon.ide.visualisation.dot.language;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.util.eMoflonSDMUtil;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public abstract class EMoflonDiagramTextProvider implements DiagramTextProvider
{
   private static final Logger logger = Logger.getLogger(EMoflonDiagramTextProvider.class);

   private Map<Object, String> diagramTextCache = new HashMap<>();

   private Map<Object, SynchronizationHelper> incrCache = new HashMap<>();

   private Map<Object, Delta> deltaCache = new HashMap<>();

   private EcoreEditor currentEditor;

   private Statistics statisticsOfLastRun = new Statistics(-1, -1, -1.0);

   /**
    * Returns whether the given element can be translated by this particular class.
    */
   public abstract boolean isElementValidInput(Object selectedElement);
   
   /**
    * Returns the plugin ID of the plugin that provides the rules for visualizing the supported elements
    * 
    * @return
    */
   protected abstract String getPluginId();

   /**
    * Returns whether to invoke the TGG in forward (true) or backward (false) direction to obtain an instance of
    * {@link DirectedGraph}.
    */
   protected abstract boolean directionIsForward();

   /**
    * Returns the {@link EPackage} containing the correspondence metamodel for the selected type of transation
    */
   protected abstract EPackage getPackage();


   /**
    * Returns the DOT visualization for the given selection
    * 
    * If the selection is null or empty, the result is an empty string.
    */
   @Override
   public String getDiagramText(IEditorPart editorPart, ISelection selection)
   {
      StructuredSelection structuredSelection = (StructuredSelection) selection;

      if (null == selection || selection.isEmpty())
         return "";

      EObject selectedElement = (EObject) structuredSelection.getFirstElement();

      // Extract input object
      EObject input = determineInputObjectFromResource(selectedElement.eResource(), selectedElement);
      if (!diagramTextCache.containsKey(input) || selectionHasBeenChanged(input))
      {
         String dotDiagram = new DotUnparserAdapter().unparse(modelToDot(input));
         if (dotDiagram == null)
            return "";
         diagramTextCache.put(input, dotDiagram);
      }

      return diagramTextCache.get(input);
   }

   @Override
   public boolean supportsEditor(IEditorPart editorPart)
   {
      if (editorPart.equals(currentEditor))
         return true;

      if (editorPart instanceof EcoreEditor)
      {
         currentEditor = (EcoreEditor) editorPart;
         clearCache();

         return true;
      }

      return false;
   }

   @Override
   public boolean supportsSelection(ISelection selection)
   {
      StructuredSelection structuredSelection = (StructuredSelection) selection;

      if (null == selection || selection.isEmpty())
         return false;
      else
         return isElementValidInput(structuredSelection.getFirstElement());
   }

   public Statistics getStatisticsOfLastRun()
   {
      return this.statisticsOfLastRun;
   }

   /**
    * Removes all elements from the internal cache
    */
   public void clearCache()
   {
      diagramTextCache.clear();
      deltaCache.clear();
      incrCache.clear();
   }

   private EObject determineInputObjectFromResource(Resource resourceForTrafo, EObject selectedElement)
   {
      final List<EObject> newSp = new ArrayList<>();
      resourceForTrafo.getContents().get(0).eAllContents().forEachRemaining(e -> {
         if (eMoflonSDMUtil.getFQN(e).equals(eMoflonSDMUtil.getFQN(selectedElement)))
            newSp.add(e);
      });

      return newSp.isEmpty() ? resourceForTrafo.getContents().get(0) : newSp.get(0);
   }

   private boolean selectionHasBeenChanged(EObject input)
   {
      return deltaCache.containsKey(input) && deltaCache.get(input).isChangeDetected();
   }

   public DirectedGraph modelToDot(final EObject input)
   {
      final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", getPluginId());
      ResourceSet rs = input.eResource().getResourceSet();

      long tic = System.nanoTime();
      try
      {
         if (incrCache.containsKey(input))
            return runSync(input);
         else
            return runBatch(pathToPlugin, input, rs);

      } catch (final Exception e)
      {
         e.printStackTrace();
      } finally
      {
         long toc = System.nanoTime();
         logger.debug(formatStatistics(input, tic, toc));
         rs.getResources().removeIf(this::unwantedResource);
      }

      return null;
   }

   private boolean unwantedResource(Resource r)
   {
      return r.getURI() != null && r.getURI().toString().endsWith("tempOutputContainer.xmi");
   }

   private String formatStatistics(final EObject input, long tic, long toc)
   {
      final int edges = eMoflonEMFUtil.getEdgeCount(input);
      final int nodes = eMoflonEMFUtil.getNodeCount(input);
      final double durationInSeconds = (toc - tic) / 1000000000.0;

      this.statisticsOfLastRun.edgeCount = edges;
      this.statisticsOfLastRun.nodeCount = nodes;
      this.statisticsOfLastRun.durationInSeconds = durationInSeconds;

      return String.format("Visualisation of [%s (E:%d + V:%d = %d] in: %.6fs", eMoflonEMFUtil.getIdentifier(input), edges, nodes, edges + nodes,
            durationInSeconds);
   }

   private DirectedGraph runSync(EObject input)
   {
      logger.debug("Running synchronization...");
      SynchronizationHelper helper = incrCache.get(input);
      helper.setDelta(deltaCache.get(input));
      DirectedGraph result = runTrafo(helper);
      deltaCache.get(input).clear();
      return result;
   }

   private DirectedGraph runTrafo(SynchronizationHelper helper)
   {
      if (directionIsForward())
      {
         helper.integrateForward();
         return (DirectedGraph) helper.getTrg();
      } else
      {
         helper.integrateBackward();
         return (DirectedGraph) helper.getSrc();
      }
   }

   private DirectedGraph runBatch(URL pathToPlugin, EObject input, ResourceSet rs)
   {
      logger.debug("Running batch...");

      SynchronizationHelper helper = new SynchronizationHelper(getPackage(), pathToPlugin.getFile(), rs);
      helper.setMute(true);
      incrCache.put(input, helper);

      if (directionIsForward())
         helper.setSrc(input);
      else
         helper.setTrg(input);

      DirectedGraph result = runTrafo(helper);

      Delta delta = new Delta();
      new OnlineChangeDetector(delta, input);
      deltaCache.put(input, delta);

      return result;
   }

   public class Statistics
   {
      public int nodeCount;

      public int edgeCount;

      public double durationInSeconds;

      private Statistics(int nodeCount, int edgeCount, double durationInSeconds)
      {
         this.nodeCount = nodeCount;
         this.edgeCount = edgeCount;
         this.durationInSeconds = durationInSeconds;
      }

   }
}

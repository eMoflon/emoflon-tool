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
   
   protected abstract String getPluginId();
   protected abstract boolean directionIsForward();
   protected abstract EPackage getPackage();
   protected abstract boolean isElementValidInput(Object selectedElement);

   private EObject determineInputObjectFromResource(Resource resourceForTrafo, EObject selectedElement)
   {
      final List<EObject> newSp = new ArrayList<>();
      resourceForTrafo.getContents().get(0).eAllContents().forEachRemaining(e -> {
         if(eMoflonSDMUtil.getFQN(e).equals(eMoflonSDMUtil.getFQN(selectedElement)))
            newSp.add(e);
      });
      
      return newSp.isEmpty()? resourceForTrafo.getContents().get(0) : newSp.get(0);
   }

   @Override
   public String getDiagramText(IEditorPart arg0, ISelection selection)
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
         if(dotDiagram == null) return "";
         diagramTextCache.put(input, dotDiagram);
      }

      return diagramTextCache.get(input);
   }
   private boolean selectionHasBeenChanged(EObject input)
   {
      return deltaCache.containsKey(input) && deltaCache.get(input).isChangeDetected();
   }

   private DirectedGraph modelToDot(final EObject input)
   {
      final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", getPluginId());   
      ResourceSet rs = input.eResource().getResourceSet();
      
      long tic = System.nanoTime();
      try
      {
         if(incrCache.containsKey(input))
            return runSync(input);
         else 
            return runBatch(pathToPlugin, input, rs);
         
      } catch (final Exception e)
      {
         e.printStackTrace();
      } finally {
         long toc = System.nanoTime();
         logger.info(outputStatistics(input, tic, toc));
         rs.getResources().removeIf(this::unwantedResource);
      }

      return null;
   }
   private boolean unwantedResource(Resource r)
   {
      return r.getURI() != null && r.getURI().toString().endsWith("tempOutputContainer.xmi");
   }
   private String outputStatistics(final EObject input, long tic, long toc)
   {
      int edges = eMoflonEMFUtil.getEdgeCount(input);
      int nodes = eMoflonEMFUtil.getNodeCount(input);
      
      return "Visualisation of [" + eMoflonEMFUtil.getIdentifier(input) 
         +  "(E:" + edges + "+ V:" + nodes + " = " + (edges + nodes) + ")] in: " 
         + (toc - tic) / 1000000000.0 + "s";
   }

   private DirectedGraph runSync(EObject input)
   {
      logger.info("Running synchronization...");
      SynchronizationHelper helper = incrCache.get(input);
      helper.setDelta(deltaCache.get(input));
      DirectedGraph result = runTrafo(helper);
      deltaCache.get(input).clear();
      return result;
   }
   
   private DirectedGraph runTrafo(SynchronizationHelper helper)
   {
      if(directionIsForward()){
         helper.integrateForward();
         return (DirectedGraph) helper.getTrg();
      } else {
         helper.integrateBackward();
         return (DirectedGraph) helper.getSrc();
      }
   }
   
   private DirectedGraph runBatch(URL pathToPlugin, EObject input, ResourceSet rs)
   {
      logger.info("Running batch...");

      SynchronizationHelper helper = new SynchronizationHelper(getPackage(), pathToPlugin.getFile(), rs);
      helper.setMute(true);
      incrCache.put(input, helper);
      
      if(directionIsForward()) 
         helper.setSrc(input); 
      else 
         helper.setTrg(input);
            
      DirectedGraph result = runTrafo(helper);
      
      Delta delta = new Delta();
      new OnlineChangeDetector(delta, input);
      deltaCache.put(input, delta);
      
      return result;
   }
   
   @Override
   public boolean supportsEditor(IEditorPart arg0)
   { 
      if(arg0.equals(currentEditor))
         return true;
      
      if(arg0 instanceof EcoreEditor){
         currentEditor = (EcoreEditor) arg0;
         diagramTextCache.clear();
         deltaCache.clear();
         incrCache.clear();
         
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

}

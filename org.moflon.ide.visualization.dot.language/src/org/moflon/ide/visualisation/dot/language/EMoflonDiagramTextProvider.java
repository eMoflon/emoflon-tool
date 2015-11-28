package org.moflon.ide.visualisation.dot.language;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.util.eMoflonSDMUtil;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public abstract class EMoflonDiagramTextProvider implements DiagramTextProvider
{
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

      EObject element = (EObject) structuredSelection.getFirstElement();

      return new DotUnparserAdapter().unparse(modelToDot(element));
   }

   private DirectedGraph modelToDot(final EObject selectedElement)
   {
      try
      {
         // Create resource with same URI as loaded resource in editor
         ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(resourceSet);
         Resource cloneResource = resourceSet.getResource(selectedElement.eResource().getURI(), true);
         
         // Create helper
         final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", getPluginId());
         SynchronizationHelper helper = new SynchronizationHelper(getPackage(), pathToPlugin.getFile(), resourceSet);
         
         // Extract selected object
         EObject input = determineInputObjectFromResource(cloneResource, selectedElement);
         
         // Run trafo
         if(directionIsForward()){
            helper.setSrc(input);            
            helper.integrateForward();
            return (DirectedGraph) helper.getTrg();
         } else {
            helper.setTrg(input);            
            helper.integrateBackward();
            return (DirectedGraph) helper.getSrc();
         }
      } catch (final Exception e)
      {
         e.printStackTrace();
      }

      return null;
   }

   @Override
   public boolean supportsEditor(IEditorPart arg0)
   {
      return arg0 instanceof EcoreEditor;
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

package org.moflon.ide.visualization.dot.sdm;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;

import SDMLanguage.activities.Activity;

public class SDMDiagramTextProvider extends EMoflonDiagramTextProvider
{
   @Override
   protected String getPluginId()
   {
      return SDMVisualizationPlugin.getDefault().getPluginId();
   }

   @Override
   protected boolean directionIsForward()
   {
      return false;
   }

   @Override
   protected EPackage getPackage()
   {
      return SdmPackage.eINSTANCE;
   }

   @Override
   public boolean isElementValidInput(Object selectedElement)
   {
      return selectedElement instanceof Activity;
   }
}

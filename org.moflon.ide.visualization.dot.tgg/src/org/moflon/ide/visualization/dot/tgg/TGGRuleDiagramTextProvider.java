package org.moflon.ide.visualization.dot.tgg;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.language.TGGRule;

public class TGGRuleDiagramTextProvider extends EMoflonDiagramTextProvider
{
   @Override
   protected String getPluginId()
   {
      return TGGVisualizationPlugin.getDefault().getPluginId();
   }

   @Override
   protected boolean directionIsForward()
   {
      return false;
   }

   @Override
   protected EPackage getPackage()
   {
      return TggPackage.eINSTANCE;
   }

   @Override
   protected boolean isElementValidInput(Object selectedElement)
   {
      return selectedElement instanceof TGGRule;
   }
}

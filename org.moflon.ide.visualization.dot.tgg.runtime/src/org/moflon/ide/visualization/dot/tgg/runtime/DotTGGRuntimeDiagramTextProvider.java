package org.moflon.ide.visualization.dot.tgg.runtime;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.runtime.PrecedenceStructure;

public class DotTGGRuntimeDiagramTextProvider extends EMoflonDiagramTextProvider
{
   @Override
   protected String getPluginId()
   {
      return DotTggRuntimePlugin.getDefault().getPluginId();
   }

   @Override
   protected boolean directionIsForward()
   {
      return true;
   }

   @Override
   protected EPackage getPackage()
   {
      return RuntimePackage.eINSTANCE;
   }

   @Override
   public boolean isElementValidInput(Object selectedElement)
   {
      return selectedElement instanceof PrecedenceStructure;
   }

}

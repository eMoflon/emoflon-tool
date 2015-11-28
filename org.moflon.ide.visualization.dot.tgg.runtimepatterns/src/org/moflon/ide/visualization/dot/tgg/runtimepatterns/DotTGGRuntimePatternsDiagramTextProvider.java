package org.moflon.ide.visualization.dot.tgg.runtimepatterns;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.runtime.TripleMatch;

public class DotTGGRuntimePatternsDiagramTextProvider extends EMoflonDiagramTextProvider
{   
   @Override
   protected String getPluginId()
   {
      return DotTGGRuntimePatternsPlugin.getDefault().getPluginId();
   }

   @Override
   protected boolean directionIsForward()
   {
      return true;
   }

   @Override
   protected EPackage getPackage()
   {
      return RuntimepatternsPackage.eINSTANCE;
   }

   @Override
   protected boolean isElementValidInput(Object selectedElement)
   {
      return selectedElement instanceof TripleMatch;
   }

}

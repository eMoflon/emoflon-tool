package org.moflon.ide.visualization.dot.sdmpatterns;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.visualisation.dot.language.EMoflonDiagramTextProvider;
import org.moflon.tgg.language.TGGRule;

import SDMLanguage.patterns.StoryPattern;

public class StoryPatternDiagramTextProvider extends EMoflonDiagramTextProvider
{
   private boolean notATGGRule(Object selection)
   {
      if(selection instanceof TGGRule){
         TGGRule rule = (TGGRule)selection;
         return rule.getTripleGraphGrammar() == null;
      }
      
      return true;
   }

   @Override
   protected String getPluginId()
   {
      return StoryPatternVisualizationPlugin.getDefault().getPluginId();
   }

   @Override
   protected boolean directionIsForward()
   {
      return false;
   }

   @Override
   protected EPackage getPackage()
   {
      return SdmpatternsPackage.eINSTANCE;
   }


   @Override
   protected boolean isElementValidInput(Object selectedElement)
   {
      return selectedElement instanceof StoryPattern && notATGGRule(selectedElement);
   }
}

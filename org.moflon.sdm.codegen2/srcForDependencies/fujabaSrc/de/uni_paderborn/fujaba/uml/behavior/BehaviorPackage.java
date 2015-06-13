/*Copyright*/
package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.factories.UMLGenericFactory;

/**
 * @author
 */
public class BehaviorPackage
{
   private BehaviorPackage()
   {
   }

   public static void registerFactories ( FProject project ) {
      //FIXME: specify interface classes as soon as they are available
      project.addToFactories( new UMLGenericFactory<UMLActivityDiagram>( project, UMLActivityDiagram.class, UMLActivityDiagram.class ) );
      project.addToFactories( new UMLGenericFactory<UMLAttrExprPair>( project, UMLAttrExprPair.class, UMLAttrExprPair.class ) );
      project.addToFactories( new UMLGenericFactory<UMLCollabStat>( project, UMLCollabStat.class, UMLCollabStat.class ) );
      project.addToFactories( new UMLGenericFactory<UMLComplexState>( project, UMLComplexState.class, UMLComplexState.class ) );
      project.addToFactories( new UMLGenericFactory<UMLLink>( project, UMLLink.class, UMLLink.class ) );
      project.addToFactories( new UMLGenericFactory<UMLLinkSet>( project, UMLLinkSet.class, UMLLinkSet.class ) );
      project.addToFactories( new UMLGenericFactory<UMLMultiLink>( project, UMLMultiLink.class, UMLMultiLink.class ) );
      project.addToFactories( new UMLGenericFactory<UMLNopActivity>( project, UMLNopActivity.class, UMLNopActivity.class ) );
      project.addToFactories( new UMLGenericFactory<UMLObject>( project, UMLObject.class, UMLObject.class ) );
      project.addToFactories( new UMLGenericFactory<UMLObjectDiagram>( project, UMLObjectDiagram.class, UMLObjectDiagram.class ) );
      project.addToFactories( new UMLGenericFactory<UMLPath>( project, UMLPath.class, UMLPath.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStartActivity>( project, UMLStartActivity.class, UMLStartActivity.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStatechart>( project, UMLStatechart.class, UMLStatechart.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStatement>( project, UMLStatement.class, UMLStatement.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStatementActivity>( project, UMLStatementActivity.class, UMLStatementActivity.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStopActivity>( project, UMLStopActivity.class, UMLStopActivity.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStoryActivity>( project, UMLStoryActivity.class, UMLStoryActivity.class ) );
      project.addToFactories( new UMLGenericFactory<UMLStoryPattern>( project, UMLStoryPattern.class, UMLStoryPattern.class ) );
      project.addToFactories( new UMLGenericFactory<UMLTransition>( project, UMLTransition.class, UMLTransition.class ) );
      project.addToFactories( new UMLGenericFactory<UMLTransitionGuard>( project, UMLTransitionGuard.class, UMLTransitionGuard.class ) );
      project.addToFactories( new UMLGenericFactory<UMLIteration>( project, UMLIteration.class, UMLIteration.class ) );
      project.addToFactories( new UMLGenericFactory<UMLIterContainer>( project, UMLIterContainer.class, UMLIterContainer.class ) );
      project.addToFactories( new UMLGenericFactory<UMLIterRunVariable>( project, UMLIterRunVariable.class, UMLIterRunVariable.class ) );
      project.addToFactories( new UMLGenericFactory<UMLIterTypeContainer>( project, UMLIterTypeContainer.class, UMLIterTypeContainer.class ) );
   }
}

/*
 * $log$
 */


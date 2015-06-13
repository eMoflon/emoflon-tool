/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) 1997-2003 Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact adress:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.fujaba.uml.utility;

import java.awt.Point;
import java.util.Iterator;

import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.asg.ASGDiagram;
import de.uni_paderborn.fujaba.fsa.unparse.UnparseManager;
import de.uni_paderborn.fujaba.metamodel.common.FModelRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.uml.behavior.*;
import de.uni_paderborn.fujaba.uml.structure.*;
import de.upb.tools.sdm.JavaSDM;

/**
 * @author    (last editor) $Author$
 * @version   $Revision$ $Date$
 */
public class DiagramCreationHelper
{
   /**
    * Creates a new class diagram. If a class diagram of the specified name already exists,
    * the existing class diagram will be returned.
    * @param name name of the new class diagram.
    * @return new or existing class diagram
    */
   public static UMLClassDiagram createClassDiagram(FProject project, String name)
   {
      if (name.trim().equals(""))
      {
         return null;
      }

      Iterator diagsEnum;
      ASGDiagram diagram;
      boolean found;
      UMLClassDiagram classDiag = null;

      try
      {
         // Check if class diagram exists.
         // Todo: this should be a functionality of UMLProject. AZ
         diagsEnum = project.iteratorOfModelRootNodes ();
         found = false;
         while (!found && diagsEnum.hasNext ())
         {
            diagram = (ASGDiagram) diagsEnum.next ();
            if ((diagram instanceof UMLClassDiagram) &&
                (diagram.getName ().equals (name)))
            {
               classDiag = (UMLClassDiagram) diagram;
               found = true;
            }
         }

         // Create class diagram.
         if (!found)
         {
            classDiag = project.createFromFactories(UMLClassDiagram.class);
            classDiag.setName(name);
            project.addToModelRootNodes(classDiag);
            // frameMain.createNewTreeItems ();
         }
      }
      finally
      {
      }
      return classDiag;
   }

   /**
    * Creates a new method for a given class. The method will be public void, without parameters.
    * @param source class where the new method is to be placed in.
    * @param name name of the new method
    * @return new method
    */
   public static UMLMethod createMethod(UMLClass source, String name)
   {
      if (source == null || !(source instanceof UMLClass) || name.trim().equals(""))
      {
         return null;
      }

      FProject project = source.getProject();
      
      // Create method.
      // Method is not static, public void.
      UMLBaseType resultType = null;
      FFactory<UMLBaseType> baseTypeFactory = project.getFromFactories(UMLBaseType.class);
      if (name.equals (source.getName()))
      {
         resultType = baseTypeFactory.getFromProducts(UMLBaseType.CONSTRUCTOR);
      }
      else
      {
         resultType = baseTypeFactory.getFromProducts(UMLBaseType.VOID);
      }
      if (resultType == null)
      {
            System.out.println("resultType == null");
      }
      
      
      UMLMethod method = project.createFromFactories(UMLMethod.class);
      method.setName(name);
      method.setStatic(false); 
      method.setVisibility(UMLDeclaration.PUBLIC); 
      method.setResultType(resultType); 


      // Add method to source.
      source.addToMethods(method);

      return method;
   }

   public static UMLActivityDiagram createStoryBoard(FProject project)
   {
		UMLActivityDiagram activityDiagram = project.createFromFactories(UMLActivityDiagram.class);

		activityDiagram.setIsStoryBoard (true);

		boolean done = false;
		String longName; 
		int noOfTries = 1;
		while (!done)
		{
			String name = "Scenario";

			longName = name;
			while (nameIsAlreadyUsed (project, activityDiagram, longName))
			{
				longName = name + " " + noOfTries;
				noOfTries++;
			}

			activityDiagram.setName (longName);

			project.addToModelRootNodes (activityDiagram);
			
			// FIXME !!! remove UI access
			FrameMain.get().selectTreeItem (activityDiagram);
			done = true;
		}
		
		return activityDiagram;
   }

   public static boolean nameIsAlreadyUsed (FProject project, UMLActivityDiagram activityDiagram, String name)
   {
      boolean found = false;
      Iterator iter = project.iteratorOfModelRootNodes();
      while (!found && iter.hasNext())
      {
         FModelRootNode modelRootNode = (FModelRootNode) iter.next();
         if (modelRootNode instanceof UMLActivityDiagram
            && JavaSDM.stringEquals (modelRootNode.getName(), name)
            && modelRootNode != activityDiagram)
         {
            found = true;
         }
      }
      return found;
   }

   
   /**
    * Creates a new activity diagram. If an activity diagram of the given name already
    * exists, the existing diagram will be returned.
    * @param name name of the new activity diagram.
    * @return new or existing activity diagram.
    */
   public static UMLActivityDiagram createActivityDiagram(UMLMethod testMethod)
   {
      FProject project = testMethod.getProject();
      UMLActivityDiagram activityDiag = null;

      try
      {
         activityDiag = (UMLActivityDiagram) testMethod.getStoryDiag();
         if (activityDiag == null)
         {
            activityDiag = project.createFromFactories(UMLActivityDiagram.class);
            activityDiag.setName(testMethod.getName());
            project.addToModelRootNodes(activityDiag);
            // frameMain.createNewTreeItems ();
         }
      }
      finally
      {
      }
      return activityDiag;
   }


   /**
    * Creates a start activity for an emtpy activity diagram.
    * @param diag the diagram where the start activity is to be placed in.
    * @param partner the method which this activity diagram defines.
    * @return the new start activity.
    */
   public static UMLStartActivity createStartActivity(UMLActivityDiagram diag, UMLMethod partner, String storyName)
   {
      if (diag == null)
      {
         return null;
      }
      
      FProject project = diag.getProject();

      UMLStartActivity activity = project.createFromFactories(UMLStartActivity.class);
      activity.setSpec(partner);
      activity.setStoryName (storyName);
      
      // FIXME !!! remove access to UnparseManager
      UnparseManager.get().addPointToUnparseInformation(activity, diag, "entry", new Point (30, 30));
		UnparseManager.get().saveFSAProperties(activity);

      diag.addToElements(activity);
      return activity;
   }

   /**
    * Create a stop activity for an activity diagram.
    * A reference from the predecessor to this activity will be created.
    * @param diag the diagram where this stop activity is to be placed in.
    * @param predecessor the predecessor of this activity.
    * @param xpos guess it 
    * @param ypos guess it
    * @return the stop activity.
    */
   public static UMLStopActivity createStopActivity(UMLActivityDiagram diag, UMLActivity predecessor, int xpos, int ypos)
   {
      return createStopActivity( diag, predecessor, UMLTransitionGuard.NONE, "NONE", xpos, ypos );
   }

   /**
    * Create a stop activity for an activity diagram.
    * A reference from the predecessor to this activity will be created.
    * The created {@link UMLTransition} gets the specified type (see 
    * {@link UMLTransitionGuard}) and the given expression, if any. 
    *
    * @param diag the diagram where this stop activity is to be placed in.
    * @param predecessor the predecessor of this activity.
    * @param type the type of the transition, see {@link UMLTransitionGuard}.
    * @param boolExpr the expression of the {@link UMLTransitionGuard}, if any.
    * @param xpos guess it 
    * @param ypos guess it
    * @return a new stop activity. 
    */
   public static UMLStopActivity createStopActivity( UMLActivityDiagram diag, UMLActivity predecessor, int type,
         String boolExpr, int xpos, int ypos )
   {
      if( diag == null || predecessor == null )
      {
         return null;
      }

      FProject project = diag.getProject();

      UMLStopActivity activity = project.createFromFactories( UMLStopActivity.class );

      // FIXME !!! remove access to UnparseManager
      UnparseManager.get().addPointToUnparseInformation(activity, diag, "entry", new Point( xpos, ypos ) );
      UnparseManager.get().saveFSAProperties(activity);

      diag.addToElements( activity );

      final UMLTransition transition = createTransition( diag, predecessor, activity );
      if( transition != null && UMLTransitionGuard.NONE != type )
      {
         final UMLTransitionGuard guard = project.getFromFactories( UMLTransitionGuard.class ).create( true );
         guard.setType( type );
         guard.setBoolExpr( boolExpr );
         transition.setGuard( guard );
      }

      return activity;
   }

	public static UMLTransition createTransition(UMLActivityDiagram diag, UMLActivity source, UMLActivity target)
	{
		if (diag == null || source == null || target == null)
		{
			return null;
		}
		
		FProject project = diag.getProject();
		
		UMLTransition trans = project.createFromFactories(UMLTransition.class);
      trans.setGuardExprText("NONE");
      trans.setRevExit(source);
      trans.setRevEntry(target);
      
      diag.addToElements(trans);
      
      return trans;
	}

   /**
    * Creates a new story activity containing the given comment.
    * A reference from the predecessor to this activity will be created.
    * @param diag the diagram where this activity is to be placed in.
    * @param predecessor the predecessor of this activity.
    * @param comment comment for the story activity.
    * @param ypos 
    * @param xpos 
    * @return new story activity.
    */
   public static UMLStoryActivity createStoryActivity(UMLActivityDiagram diag, UMLActivity predecessor, String comment, int xpos, int ypos)
   {
      return createStoryActivity( diag, predecessor, comment, UMLTransitionGuard.NONE, "NONE", xpos, ypos );
   }


   /**
    * Creates a new story activity containing the given comment.
    * A reference from the predecessor to this activity will be created.
    * The created {@link UMLTransition} gets the specified type (see 
    * {@link UMLTransitionGuard}) and the given expression, if any.
    *
    * @param activityDiagram the diagram where this activity is to be placed in.
    * @param predecessor the predecessor of this activity.
    * @param textComment comment for the story activity.
    * @param type  the type of the transition, see {@link UMLTransitionGuard}.
    * @param boolExpr the expression of the {@link UMLTransitionGuard}, if any.
    * @param xpos guess it
    * @param ypos guess it
    * @return a new story activity.
    */
   public static UMLStoryActivity createStoryActivity( UMLActivityDiagram activityDiagram, UMLActivity predecessor,
         String textComment, int type, String boolExpr, int xpos, int ypos )
   {
      if( activityDiagram == null || predecessor == null )
      {
         return null;
      }

      FProject project = activityDiagram.getProject();

      UMLStoryActivity activity = project.createFromFactories( UMLStoryActivity.class );
      activity.setTextComment( textComment );

      UMLStoryPattern story = project.createFromFactories( UMLStoryPattern.class );
      story.setRevStoryPattern( activity );

      // FIXME !!! remove access to UnparseManager
      UnparseManager.get().addPointToUnparseInformation(activity, activityDiagram, "entry", new Point( xpos, ypos ) );
      UnparseManager.get().saveFSAProperties(activity);

      activityDiagram.addToElements( activity );

      final UMLTransition transition = createTransition( activityDiagram, predecessor, activity );
      if( transition != null && UMLTransitionGuard.NONE != type )
      {
         final UMLTransitionGuard guard = project.getFromFactories( UMLTransitionGuard.class ).create( true );
         guard.setType( type );
         guard.setBoolExpr( boolExpr );
         transition.setGuard( guard );
      }

      return activity;
   }
   
   /**
    * Creates a new statement activity containing the given statement.
    * A reference from the predecessor to this activity will be created.
    * @param diag the diagram where this activity is to be placed in.
    * @param predecessor the predecessor of this activity.
    * @param statement statement as string for this activity.
    * @return new statement activity.
    */
   public static UMLStatementActivity createStatementActivity(UMLActivityDiagram diag, UMLActivity predecessor, String statement)
   {
      if (diag == null || predecessor == null)
      {
         return null;
      }

      FProject project = diag.getProject();
      
      UMLStatementActivity statAct = project.createFromFactories(UMLStatementActivity.class);
      statAct.setStatement(statement);
      diag.addToElements (statAct);
      createTransition(diag, predecessor, statAct);

      return statAct;
   }

	public static UMLStartActivity createStartActivity(UMLActivityDiagram activityDiagram, int xpos, int ypos)
	{
		if (activityDiagram == null)
		{
			return null;
		}
		
		FProject project = activityDiagram.getProject();
		
		UMLStartActivity act = project.createFromFactories(UMLStartActivity.class);
		act.setStoryName(activityDiagram.getName());
		
		// FIXME !!! remove access to UnparseManager
		UnparseManager.get().addPointToUnparseInformation(act, activityDiagram, "entry", new Point (xpos, ypos));
		UnparseManager.get().saveFSAProperties(act);
		
		activityDiagram.addToElements(act);
		
		return act;
	}
}

/*
 * $Log$
 * Revision 1.2  2007/04/05 13:38:42  fklar
 * some cleanup
 *
 */

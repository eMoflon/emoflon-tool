/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;

import java.util.Iterator;

import de.uni_kassel.fujaba.codegen.rules.Composite;
import de.uni_kassel.fujaba.codegen.rules.ExecuteStoryPatternOperation;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.upb.tools.sdm.JavaSDM;
import de.upb.tools.sdm.JavaSDMException; // requires Fujaba5/libs/RuntimeTools.jar in classpath


public class UnfoldTreeMutator extends PlanMutator
{


   public void mutate (ExecuteStoryPatternOperation plan )
   {
      boolean fujaba__Success = false;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         this.unfold (plan);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   public Token unfold (Token operation )
   {
      boolean fujaba__Success = false;
      Token prev = null;
      Token parent = null;
      Iterator fujaba__IterOperationToChild = null;
      Token child = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         prev = operation;

         // check object prev is really bound
         JavaSDM.ensure ( prev != null );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern Successor of successor
      try 
      {
         fujaba__Success = false; 

         // check object operation is really bound
         JavaSDM.ensure ( operation != null );
         // iterate to-many link children from operation to child
         fujaba__Success = false;
         fujaba__IterOperationToChild = operation.iteratorOfChildren ();

         while ( fujaba__IterOperationToChild.hasNext () )
         {
            try
            {
               child = (Token) fujaba__IterOperationToChild.next ();

               // check object child is really bound
               JavaSDM.ensure ( child != null );
               // search to-one link children from operation to parent
               parent = operation.getParent ();


               // check optional
               if (parent != null)
               {
                  // check isomorphic binding between objects parent and child
                  JavaSDM.ensure ( !parent.equals (child) );

                  // check isomorphic binding between objects parent and operation
                  JavaSDM.ensure ( !parent.equals (operation) );

               }
               // check isomorphic binding between objects operation and child
               JavaSDM.ensure ( !operation.equals (child) );

               // story pattern successor
               try 
               {
                  fujaba__Success = false; 

                  // check object child is really bound
                  JavaSDM.ensure ( child != null );
                  // check object operation is really bound
                  JavaSDM.ensure ( operation != null );
                  // check object parent is really bound
                  JavaSDM.ensure ( parent != null );
                  // check object prev is really bound
                  JavaSDM.ensure ( prev != null );
                  // check isomorphic binding between objects operation and child
                  JavaSDM.ensure ( !operation.equals (child) );

                  // check isomorphic binding between objects parent and child
                  JavaSDM.ensure ( !parent.equals (child) );

                  // check isomorphic binding between objects prev and child
                  JavaSDM.ensure ( !prev.equals (child) );

                  // check isomorphic binding between objects parent and operation
                  JavaSDM.ensure ( !parent.equals (operation) );

                  // check isomorphic binding between objects prev and parent
                  JavaSDM.ensure ( !prev.equals (parent) );

                  // check link children from child to operation
                  JavaSDM.ensure (operation.equals (child.getParent ()));

                  // check link children from parent to prev
                  JavaSDM.ensure (parent.hasInChildren (prev));

                  // constraint !(operation instanceof Composite)
                  JavaSDM.ensure ( !(operation instanceof Composite) );
                  // destroy link children from operation to child
                  operation.removeFromChildren (child);
                  // create link children from parent to child
                  parent.addAfterOfChildren (prev, child);

                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               if ( fujaba__Success )
               {
                  // story pattern successor
                  try 
                  {
                     fujaba__Success = false; 

                     // collabStat call
                     prev = this.unfold (child);
                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }


               }
               else
               {
                  // story pattern storypatternwiththis
                  try 
                  {
                     fujaba__Success = false; 

                     // collabStat call
                     this.unfold (child);
                     // collabStat call
                     prev = child;
                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }


               }

               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }
         }
         JavaSDM.ensure (fujaba__Success);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return prev;
   }

}



/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;


import java.util.Iterator;

import de.uni_kassel.fujaba.codegen.Utility;
import de.uni_kassel.fujaba.codegen.rules.CheckLinkOperation;
import de.uni_kassel.fujaba.codegen.rules.ExecuteStoryPatternOperation;
import de.uni_kassel.fujaba.codegen.rules.LinkLifecycleOperation;
import de.uni_kassel.fujaba.codegen.rules.LinkOperation;
import de.uni_kassel.fujaba.codegen.rules.SearchOperation;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.uni_kassel.fujaba.codegen.rules.UMLLinkRef;
import de.uni_kassel.fujaba.codegen.rules.UMLObjectRef;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLPath;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class UnusedOperationsMutator extends PlanMutator
{


   public void mutate (ExecuteStoryPatternOperation plan )
   {
      boolean fujaba__Success = false;
      ExecutionPlanEngine engine = null;
      Token parent = null;
      UMLObject target = null;
      UMLObjectRef targetRef = null;
      UMLLink umlLink = null;
      FRole role = null;
      UMLObjectRef sourceRef = null;
      FRole partner = null;
      Iterator fujaba__IterPlanToLinkOp = null;
      Object _TmpObject = null;
      LinkOperation linkOp = null;
      UMLLinkRef link = null;
      Iterator fujaba__IterPlanToSearchOp = null;
      SearchOperation searchOp = null;
      CheckLinkOperation revOp = null;
      double checkCost = 0.0;
      double revCost = 0.0;
      CheckLinkOperation cheapOp = null;
      Iterator fujaba__IterPlanToCheckOp = null;
      CheckLinkOperation checkOp = null;
      LinkLifecycleOperation swappedEditOp = null;
      Iterator fujaba__IterPlanToOther = null;
      LinkLifecycleOperation other = null;
      UMLLinkRef linkRef = null;
      Iterator fujaba__IterPlanToEditOp = null;
      LinkLifecycleOperation editOp = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // search to-one link mutators from this to engine
         engine = this.getEngine ();

         // check object engine is really bound
         JavaSDM.ensure ( engine != null );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object plan is really bound
         JavaSDM.ensure ( plan != null );
         // iterate to-many link operations from plan to searchOp
         fujaba__Success = false;
         fujaba__IterPlanToSearchOp = plan.iteratorOfOperations ();

         while ( fujaba__IterPlanToSearchOp.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterPlanToSearchOp.next ();

               // ensure correct type and really bound of object searchOp
               JavaSDM.ensure ( _TmpObject instanceof SearchOperation );
               searchOp = (SearchOperation) _TmpObject;

               // story pattern Successor of successor
               try 
               {
                  fujaba__Success = false; 

                  // check object plan is really bound
                  JavaSDM.ensure ( plan != null );
                  // check object searchOp is really bound
                  JavaSDM.ensure ( searchOp != null );
                  // check link operations from searchOp to plan
                  JavaSDM.ensure (plan.equals (searchOp.getStoryPatternOperation ()));

                  // check negative bindings
                  try
                  {
                     fujaba__Success = false;

                     // search to-one link children from searchOp to parent
                     parent = searchOp.getParent ();

                     // check object parent is really bound
                     JavaSDM.ensure ( parent != null );

                     // check isomorphic binding between objects parent and searchOp
                     JavaSDM.ensure ( !parent.equals (searchOp) );



                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }

                  fujaba__Success = !(fujaba__Success);

                  JavaSDM.ensure ( fujaba__Success );

                  // destroy link operations from plan to searchOp
                  plan.removeFromOperations (searchOp);
                  // delete object searchOp
                  searchOp.removeYou ();

                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               if ( !( fujaba__Success ) )
               {
                  // story pattern Successor of Successor of successor
                  try 
                  {
                     fujaba__Success = false; 

                     // check object plan is really bound
                     JavaSDM.ensure ( plan != null );
                     // check object searchOp is really bound
                     JavaSDM.ensure ( searchOp != null );
                     // search to-one link link from searchOp to link
                     link = searchOp.getLink ();

                     // check object link is really bound
                     JavaSDM.ensure ( link != null );

                     // iterate to-many link operations from plan to linkOp
                     fujaba__Success = false;
                     fujaba__IterPlanToLinkOp = plan.iteratorOfOperations ();

                     while ( fujaba__IterPlanToLinkOp.hasNext () )
                     {
                        try
                        {
                           _TmpObject =  fujaba__IterPlanToLinkOp.next ();

                           // ensure correct type and really bound of object linkOp
                           JavaSDM.ensure ( _TmpObject instanceof LinkOperation );
                           linkOp = (LinkOperation) _TmpObject;

                           // check isomorphic binding between objects searchOp and linkOp
                           JavaSDM.ensure ( !searchOp.equals (linkOp) );

                           // check link link from linkOp to link
                           JavaSDM.ensure (link.equals (linkOp.getLink ()));

                           // constraint linkOp instanceof SearchOperation || linkOp instanceof CheckLinkOperation
                           JavaSDM.ensure ( linkOp instanceof SearchOperation || linkOp instanceof CheckLinkOperation );
                           // story pattern Successor of Successor of Successor of successor
                           try 
                           {
                              fujaba__Success = false; 

                              // check object link is really bound
                              JavaSDM.ensure ( link != null );
                              // check object searchOp is really bound
                              JavaSDM.ensure ( searchOp != null );
                              // search to-one link ref from link to umlLink
                              umlLink = link.getRef ();

                              // check object umlLink is really bound
                              JavaSDM.ensure ( umlLink != null );

                              // attribute condition range != ""
                              JavaSDM.ensure ( JavaSDM.stringCompare ((String) umlLink.getRange (), "") != 0 );

                              // attribute condition range != null
                              JavaSDM.ensure ( JavaSDM.stringCompare ((String) umlLink.getRange (), null) != 0 );

                              // search to-one link subject from searchOp to targetRef
                              targetRef = searchOp.getSubject ();

                              // check object targetRef is really bound
                              JavaSDM.ensure ( targetRef != null );

                              // search to-one link ref from targetRef to target
                              target = targetRef.getRef ();

                              // check object target is really bound
                              JavaSDM.ensure ( target != null );

                              // constraint !(umlLink instanceof UMLPath)
                              JavaSDM.ensure ( !(umlLink instanceof UMLPath) );
                              // constraint linkOp instanceof CheckLinkOperation
                              JavaSDM.ensure ( linkOp instanceof CheckLinkOperation );



                              fujaba__Success = true;
                           }
                           catch ( JavaSDMException fujaba__InternalException )
                           {
                              fujaba__Success = false;
                           }

                           if ( fujaba__Success )
                           {
                              // story pattern storypatternwiththis
                              try 
                              {
                                 fujaba__Success = false; 

                                 role = umlLink.getCorrespondingRole(target);

                                 // check object role is really bound
                                 JavaSDM.ensure ( role != null );
                                 // check object linkOp is really bound
                                 JavaSDM.ensure ( linkOp != null );
                                 // check object target is really bound
                                 JavaSDM.ensure ( target != null );
                                 // search to-one link subject from linkOp to sourceRef
                                 sourceRef = linkOp.getSubject ();

                                 // check object sourceRef is really bound
                                 JavaSDM.ensure ( sourceRef != null );

                                 // check link ref from sourceRef to target
                                 JavaSDM.ensure (!(target.equals (sourceRef.getRef ())));


                                 // search to-one link partnerRole from role to partner
                                 partner = role.getPartnerRole ();

                                 // check object partner is really bound
                                 JavaSDM.ensure ( partner != null );

                                 // check isomorphic binding between objects role and partner
                                 JavaSDM.ensure ( !role.equals (partner) );

                                 // negative check for link qualifier from partner
                                 JavaSDM.ensure (partner.getQualifier () == null);

                                 fujaba__Success = true;
                              }
                              catch ( JavaSDMException fujaba__InternalException )
                              {
                                 fujaba__Success = false;
                              }

                              if ( !( fujaba__Success ) )
                              {
                                 // story pattern storypatternwiththis
                                 try 
                                 {
                                    fujaba__Success = false; 

                                    // check object linkOp is really bound
                                    JavaSDM.ensure ( linkOp != null );
                                    // check object plan is really bound
                                    JavaSDM.ensure ( plan != null );
                                    // check link operations from linkOp to plan
                                    JavaSDM.ensure (plan.equals (linkOp.getStoryPatternOperation ()));

                                    // destroy link operations from linkOp to plan
                                    linkOp.setStoryPatternOperation (null);
                                    // delete object linkOp
                                    linkOp.removeYou ();

                                    fujaba__Success = true;
                                 }
                                 catch ( JavaSDMException fujaba__InternalException )
                                 {
                                    fujaba__Success = false;
                                 }


                              }

                           }
                           else
                           {
                              // story pattern storypatternwiththis
                              try 
                              {
                                 fujaba__Success = false; 

                                 // check object linkOp is really bound
                                 JavaSDM.ensure ( linkOp != null );
                                 // check object plan is really bound
                                 JavaSDM.ensure ( plan != null );
                                 // check link operations from linkOp to plan
                                 JavaSDM.ensure (plan.equals (linkOp.getStoryPatternOperation ()));

                                 // destroy link operations from linkOp to plan
                                 linkOp.setStoryPatternOperation (null);
                                 // delete object linkOp
                                 linkOp.removeYou ();

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

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object plan is really bound
         JavaSDM.ensure ( plan != null );
         // iterate to-many link operations from plan to checkOp
         fujaba__Success = false;
         fujaba__IterPlanToCheckOp = plan.iteratorOfOperations ();

         while ( fujaba__IterPlanToCheckOp.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterPlanToCheckOp.next ();

               // ensure correct type and really bound of object checkOp
               JavaSDM.ensure ( _TmpObject instanceof CheckLinkOperation );
               checkOp = (CheckLinkOperation) _TmpObject;

               // story pattern successor
               try 
               {
                  fujaba__Success = false; 

                  // check object checkOp is really bound
                  JavaSDM.ensure ( checkOp != null );
                  // search to-one link reverseOperation from checkOp to revOp
                  _TmpObject = checkOp.getReverseOperation ();

                  // ensure correct type and really bound of object revOp
                  JavaSDM.ensure ( _TmpObject instanceof CheckLinkOperation );
                  revOp = (CheckLinkOperation) _TmpObject;


                  // check isomorphic binding between objects revOp and checkOp
                  JavaSDM.ensure ( !revOp.equals (checkOp) );


                  // collabStat call
                  checkCost = engine.getCost (checkOp);
                  // collabStat call
                  revCost = engine.getCost (revOp);
                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               if ( fujaba__Success )
               {
                  if ( checkCost <= revCost )
                  {
                     // story pattern successor
                     try 
                     {
                        fujaba__Success = false; 

                        cheapOp = checkOp;

                        // check object cheapOp is really bound
                        JavaSDM.ensure ( cheapOp != null );
                        fujaba__Success = true;
                     }
                     catch ( JavaSDMException fujaba__InternalException )
                     {
                        fujaba__Success = false;
                     }


                  }
                  else
                  {
                     // story pattern successor
                     try 
                     {
                        fujaba__Success = false; 

                        cheapOp = revOp;

                        // check object cheapOp is really bound
                        JavaSDM.ensure ( cheapOp != null );
                        fujaba__Success = true;
                     }
                     catch ( JavaSDMException fujaba__InternalException )
                     {
                        fujaba__Success = false;
                     }


                  }
                  // story pattern 
                  try 
                  {
                     fujaba__Success = false; 

                     _TmpObject = swapIfFirstLastOrQualified( cheapOp );

                     // ensure correct type and really bound of object cheapOp
                     JavaSDM.ensure ( _TmpObject instanceof CheckLinkOperation );
                     cheapOp = (CheckLinkOperation) _TmpObject;

                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }

                  // story pattern successor
                  try 
                  {
                     fujaba__Success = false; 

                     // check object cheapOp is really bound
                     JavaSDM.ensure ( cheapOp != null );
                     // check object plan is really bound
                     JavaSDM.ensure ( plan != null );
                     // search to-one link link from cheapOp to link
                     link = cheapOp.getLink ();

                     // check object link is really bound
                     JavaSDM.ensure ( link != null );

                     // iterate to-many link operations from plan to linkOp
                     fujaba__Success = false;
                     fujaba__IterPlanToLinkOp = plan.iteratorOfOperations ();

                     while ( fujaba__IterPlanToLinkOp.hasNext () )
                     {
                        try
                        {
                           _TmpObject =  fujaba__IterPlanToLinkOp.next ();

                           // ensure correct type and really bound of object linkOp
                           JavaSDM.ensure ( _TmpObject instanceof LinkOperation );
                           linkOp = (LinkOperation) _TmpObject;

                           // check isomorphic binding between objects linkOp and cheapOp
                           JavaSDM.ensure ( !linkOp.equals (cheapOp) );

                           // check link link from linkOp to link
                           JavaSDM.ensure (link.equals (linkOp.getLink ()));

                           // constraint linkOp instanceof SearchOperation || linkOp instanceof CheckLinkOperation
                           JavaSDM.ensure ( linkOp instanceof SearchOperation || linkOp instanceof CheckLinkOperation );
                           // destroy link operations from plan to linkOp
                           plan.removeFromOperations (linkOp);
                           // delete object linkOp
                           linkOp.removeYou ();


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

      // // delete double removal or creation of links
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object plan is really bound
         JavaSDM.ensure ( plan != null );
         // iterate to-many link operations from plan to editOp
         fujaba__Success = false;
         fujaba__IterPlanToEditOp = plan.iteratorOfOperations ();

         while ( fujaba__IterPlanToEditOp.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterPlanToEditOp.next ();

               // ensure correct type and really bound of object editOp
               JavaSDM.ensure ( _TmpObject instanceof LinkLifecycleOperation );
               editOp = (LinkLifecycleOperation) _TmpObject;

               // story pattern 
               try 
               {
                  fujaba__Success = false; 

                  _TmpObject = swapIfFirstLastOrQualified (editOp);

                  // ensure correct type and really bound of object swappedEditOp
                  JavaSDM.ensure ( _TmpObject instanceof LinkLifecycleOperation );
                  swappedEditOp = (LinkLifecycleOperation) _TmpObject;

                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               // story pattern Successor of 
               try 
               {
                  fujaba__Success = false; 

                  // check object plan is really bound
                  JavaSDM.ensure ( plan != null );
                  // check object swappedEditOp is really bound
                  JavaSDM.ensure ( swappedEditOp != null );
                  // check link operations from swappedEditOp to plan
                  JavaSDM.ensure (plan.equals (swappedEditOp.getStoryPatternOperation ()));

                  // search to-one link link from swappedEditOp to linkRef
                  linkRef = swappedEditOp.getLink ();

                  // check object linkRef is really bound
                  JavaSDM.ensure ( linkRef != null );

                  // iterate to-many link operations from plan to other
                  fujaba__Success = false;
                  fujaba__IterPlanToOther = plan.iteratorOfOperations ();

                  while ( !(fujaba__Success) && fujaba__IterPlanToOther.hasNext () )
                  {
                     try
                     {
                        _TmpObject =  fujaba__IterPlanToOther.next ();

                        // ensure correct type and really bound of object other
                        JavaSDM.ensure ( _TmpObject instanceof LinkLifecycleOperation );
                        other = (LinkLifecycleOperation) _TmpObject;

                        // check isomorphic binding between objects swappedEditOp and other
                        JavaSDM.ensure ( !swappedEditOp.equals (other) );

                        // check link link from other to linkRef
                        JavaSDM.ensure (linkRef.equals (other.getLink ()));


                        fujaba__Success = true;
                     }
                     catch ( JavaSDMException fujaba__InternalException )
                     {
                        fujaba__Success = false;
                     }
                  }
                  JavaSDM.ensure (fujaba__Success);

                  // destroy link operations from other to plan
                  other.setStoryPatternOperation (null);
                  // destroy link link from other to linkRef
                  other.setLink (null);
                  // delete object other
                  other.removeYou ();

                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
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

      return ;
   }

   public LinkOperation swapIfFirstLastOrQualified (LinkOperation cheapOp )
   {
      boolean fujaba__Success = false;
      UMLMultiLink multiLast = null;
      UMLMultiLink multiFirst = null;
      UMLLink umlLink = null;
      UMLLinkRef link = null;
      UMLObject target = null;
      UMLObjectRef targetRef = null;
      LinkOperation revOp = null;
      FRole role = null;
      FQualifier noQualifier = null;
      FQualifier qualifier = null;
      FCardinality card = null;
      FRole partner = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object cheapOp is really bound
         JavaSDM.ensure ( cheapOp != null );
         // search to-one link subject from cheapOp to targetRef
         targetRef = cheapOp.getSubject ();

         // check object targetRef is really bound
         JavaSDM.ensure ( targetRef != null );

         // search to-one link ref from targetRef to target
         target = targetRef.getRef ();

         // check object target is really bound
         JavaSDM.ensure ( target != null );

         // search to-one link link from cheapOp to link
         link = cheapOp.getLink ();

         // check object link is really bound
         JavaSDM.ensure ( link != null );

         // search to-one link ref from link to umlLink
         umlLink = link.getRef ();

         // check object umlLink is really bound
         JavaSDM.ensure ( umlLink != null );

         // check negative bindings
         try
         {
            fujaba__Success = false;

            // search to-one link sourceLink from umlLink to multiLast
            multiLast = umlLink.getRevSourceLink ();

            // check object multiLast is really bound
            JavaSDM.ensure ( multiLast != null );

            // check link containerObject from multiLast to target
            JavaSDM.ensure (target.equals (multiLast.getContainerObject ()));



            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         fujaba__Success = !(fujaba__Success);

         JavaSDM.ensure ( fujaba__Success );

         // check negative bindings
         try
         {
            fujaba__Success = false;

            // search to-one link targetLink from umlLink to multiFirst
            multiFirst = umlLink.getRevTargetLink ();

            // check object multiFirst is really bound
            JavaSDM.ensure ( multiFirst != null );

            // check link containerObject from multiFirst to target
            JavaSDM.ensure (target.equals (multiFirst.getContainerObject ()));



            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         fujaba__Success = !(fujaba__Success);

         JavaSDM.ensure ( fujaba__Success );





         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         // story pattern successor
         try 
         {
            fujaba__Success = false; 

            // check object cheapOp is really bound
            JavaSDM.ensure ( cheapOp != null );
            // search to-one link reverseOperation from cheapOp to revOp
            revOp = cheapOp.getReverseOperation ();

            // check object revOp is really bound
            JavaSDM.ensure ( revOp != null );

            // check isomorphic binding between objects revOp and cheapOp
            JavaSDM.ensure ( !revOp.equals (cheapOp) );


            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         return revOp;

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object umlLink is really bound
         JavaSDM.ensure ( umlLink != null );
         // attribute condition range != ""
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) umlLink.getRange (), "") != 0 );

         // attribute condition range != null
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) umlLink.getRange (), null) != 0 );

         // constraint !(umlLink instanceof UMLPath)
         JavaSDM.ensure ( !(umlLink instanceof UMLPath) );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {

         // story pattern 
         try 
         {
            fujaba__Success = false; 

            role = umlLink.getCorrespondingRole(target);

            // check object role is really bound
            JavaSDM.ensure ( role != null );
            // search to-one link partnerRole from role to partner
            partner = role.getPartnerRole ();

            // check object partner is really bound
            JavaSDM.ensure ( partner != null );

            // check isomorphic binding between objects role and partner
            JavaSDM.ensure ( !role.equals (partner) );

            // search to-one link card from partner to card
            card = partner.getCard ();

            // check object card is really bound
            JavaSDM.ensure ( card != null );

            // search to-one link qualifier from partner to qualifier
            qualifier = partner.getQualifier ();

            // check object qualifier is really bound
            JavaSDM.ensure ( qualifier != null );

            // check negative bindings
            try
            {
               fujaba__Success = false;

               // search to-one link qualifier from role to noQualifier
               noQualifier = role.getQualifier ();

               // check object noQualifier is really bound
               JavaSDM.ensure ( noQualifier != null );

               // check isomorphic binding between objects noQualifier and qualifier
               JavaSDM.ensure ( !noQualifier.equals (qualifier) );



               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            fujaba__Success = !(fujaba__Success);

            JavaSDM.ensure ( fujaba__Success );

            // constraint !(Utility.get().isOrdered (partner) && card.getUpperBound() > 1)
            JavaSDM.ensure ( !(Utility.get().isOrdered (partner) && card.getUpperBound() > 1) );



            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         if ( !( fujaba__Success ) )
         {
            // story pattern storypatternwiththis
            try 
            {
               fujaba__Success = false; 

               // check object cheapOp is really bound
               JavaSDM.ensure ( cheapOp != null );
               // search to-one link reverseOperation from cheapOp to revOp
               revOp = cheapOp.getReverseOperation ();

               // check object revOp is really bound
               JavaSDM.ensure ( revOp != null );

               // check isomorphic binding between objects revOp and cheapOp
               JavaSDM.ensure ( !revOp.equals (cheapOp) );


               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            return revOp;

         }

      }
      return cheapOp;
   }

}



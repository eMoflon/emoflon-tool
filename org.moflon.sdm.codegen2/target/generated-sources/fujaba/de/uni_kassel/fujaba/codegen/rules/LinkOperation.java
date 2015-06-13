/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules;
import java.util.Iterator;

import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public abstract class LinkOperation extends ObjectOperation
{


   public FElement getElement ()
   {
      boolean fujaba__Success = false;
      UMLLink link = null;
      UMLLinkRef linkRef = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // search to-one link link from this to linkRef
         linkRef = this.getLink ();

         // check object linkRef is really bound
         JavaSDM.ensure ( linkRef != null );

         // search to-one link ref from linkRef to link
         link = linkRef.getRef ();

         // check object link is really bound
         JavaSDM.ensure ( link != null );



         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return link;
   }

   public abstract LinkOperation getReverseOperation ();

   public boolean hasSingleResult ()
   {
      boolean fujaba__Success = false;
      UMLObject target = null;
      UMLObjectRef targetRef = null;
      UMLLink link = null;
      UMLLinkRef linkRef = null;
      UMLMultiLink multi = null;
      Object _TmpObject = null;
      SearchMultiLinkOperation multiSearch = null;
      FRole targetRole = null;
      FQualifier quali = null;
      FRole sourceRole = null;
      Iterator fujaba__IterTargetRoleToOrdered = null;
      FConstraint ordered = null;
      Iterator fujaba__IterAssocToOrdered = null;
      FAssoc assoc = null;
      FCardinality card = null;

      if ( isToOne() )
      {
         return true;

      }
      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link subject from this to targetRef
         targetRef = this.getSubject ();

         // check object targetRef is really bound
         JavaSDM.ensure ( targetRef != null );

         // search to-one link ref from targetRef to target
         target = targetRef.getRef ();

         // check object target is really bound
         JavaSDM.ensure ( target != null );



         // search to-one link link from this to linkRef
         linkRef = this.getLink ();

         // check object linkRef is really bound
         JavaSDM.ensure ( linkRef != null );

         // search to-one link ref from linkRef to link
         link = linkRef.getRef ();

         // check object link is really bound
         JavaSDM.ensure ( link != null );

         // attribute condition range != ""
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) link.getRange (), "") != 0 );

         // attribute condition range != null
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) link.getRange (), null) != 0 );



         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         // story pattern 
         try 
         {
            fujaba__Success = false; 

            // check object link is really bound
            JavaSDM.ensure ( link != null );
            // search to-one link targetLink from link to multi
            multi = link.getRevTargetLink ();

            // check object multi is really bound
            JavaSDM.ensure ( multi != null );

            // attribute condition type == UMLMultiLink.FIRST
            JavaSDM.ensure ( multi.getType () == UMLMultiLink.FIRST );


            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         if ( !( fujaba__Success ) )
         {
            // story pattern Successor of 
            try 
            {
               fujaba__Success = false; 

               // check object link is really bound
               JavaSDM.ensure ( link != null );
               // search to-one link sourceLink from link to multi
               multi = link.getRevSourceLink ();

               // check object multi is really bound
               JavaSDM.ensure ( multi != null );

               // attribute condition type == UMLMultiLink.LAST
               JavaSDM.ensure ( multi.getType () == UMLMultiLink.LAST );


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

                  _TmpObject = this;

                  // ensure correct type and really bound of object multiSearch
                  JavaSDM.ensure ( _TmpObject instanceof SearchMultiLinkOperation );
                  multiSearch = (SearchMultiLinkOperation) _TmpObject;

                  // search to-one link multiLink from multiSearch to multi
                  multi = multiSearch.getMultiLink ();

                  // check object multi is really bound
                  JavaSDM.ensure ( multi != null );

                  // attribute condition type != UMLMultiLink.INDIRECT
                  JavaSDM.ensure ( multi.getType () != UMLMultiLink.INDIRECT );


                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               if ( !( fujaba__Success ) )
               {
                  return false;

               }

            }

         }

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         targetRole = link.getCorrespondingRole(target);

         // check object targetRole is really bound
         JavaSDM.ensure ( targetRole != null );
         // search to-one link partnerRole from targetRole to sourceRole
         sourceRole = targetRole.getPartnerRole ();

         // check object sourceRole is really bound
         JavaSDM.ensure ( sourceRole != null );

         // check isomorphic binding between objects targetRole and sourceRole
         JavaSDM.ensure ( !targetRole.equals (sourceRole) );

         // search to-one link qualifier from sourceRole to quali
         quali = sourceRole.getQualifier ();

         // check object quali is really bound
         JavaSDM.ensure ( quali != null );



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

            // check object targetRole is really bound
            JavaSDM.ensure ( targetRole != null );
            // iterate to-many link constraints from targetRole to ordered
            fujaba__Success = false;
            fujaba__IterTargetRoleToOrdered = targetRole.iteratorOfConstraints ();

            while ( !(fujaba__Success) && fujaba__IterTargetRoleToOrdered.hasNext () )
            {
               try
               {
                  ordered = (FConstraint) fujaba__IterTargetRoleToOrdered.next ();

                  // check object ordered is really bound
                  JavaSDM.ensure ( ordered != null );
                  // attribute condition text == "ordered"
                  JavaSDM.ensure ( JavaSDM.stringCompare ((String) ordered.getText (), "ordered") == 0 );


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

         if ( fujaba__Success )
         {
            return true;

         }
         // story pattern Successor of successor
         try 
         {
            fujaba__Success = false; 

            // check object link is really bound
            JavaSDM.ensure ( link != null );
            // search to-one link instanceOf from link to assoc
            assoc = link.getInstanceOf ();

            // check object assoc is really bound
            JavaSDM.ensure ( assoc != null );

            // iterate to-many link constraints from assoc to ordered
            fujaba__Success = false;
            fujaba__IterAssocToOrdered = assoc.iteratorOfConstraints ();

            while ( !(fujaba__Success) && fujaba__IterAssocToOrdered.hasNext () )
            {
               try
               {
                  ordered = (FConstraint) fujaba__IterAssocToOrdered.next ();

                  // check object ordered is really bound
                  JavaSDM.ensure ( ordered != null );
                  // attribute condition text == "ordered"
                  JavaSDM.ensure ( JavaSDM.stringCompare ((String) ordered.getText (), "ordered") == 0 );


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

         if ( !( fujaba__Success ) )
         {
            return false;

         }
         return true;

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object targetRole is really bound
         JavaSDM.ensure ( targetRole != null );
         // search to-one link card from targetRole to card
         card = targetRole.getCard ();

         // check object card is really bound
         JavaSDM.ensure ( card != null );

         // attribute condition upperBound == 1
         JavaSDM.ensure ( card.getUpperBound () == 1 );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return true;

      }
      return false;
   }

   public boolean isToOne ()
   {
      boolean fujaba__Success = false;
      UMLObject target = null;
      UMLObjectRef targetRef = null;
      FAssoc assoc = null;
      UMLLink link = null;
      UMLLinkRef linkRef = null;
      FRole targetRole = null;
      FRole sourceRole = null;
      FCardinality card = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link subject from this to targetRef
         targetRef = this.getSubject ();

         // check object targetRef is really bound
         JavaSDM.ensure ( targetRef != null );

         // search to-one link ref from targetRef to target
         target = targetRef.getRef ();

         // check object target is really bound
         JavaSDM.ensure ( target != null );



         // search to-one link link from this to linkRef
         linkRef = this.getLink ();

         // check object linkRef is really bound
         JavaSDM.ensure ( linkRef != null );

         // search to-one link ref from linkRef to link
         link = linkRef.getRef ();

         // check object link is really bound
         JavaSDM.ensure ( link != null );

         // search to-one link instanceOf from link to assoc
         assoc = link.getInstanceOf ();

         // check object assoc is really bound
         JavaSDM.ensure ( assoc != null );




         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return false;

      }
      // story pattern Successor of storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         targetRole = link.getCorrespondingRole(target);

         // check object targetRole is really bound
         JavaSDM.ensure ( targetRole != null );
         // search to-one link partnerRole from targetRole to sourceRole
         sourceRole = targetRole.getPartnerRole ();

         // check object sourceRole is really bound
         JavaSDM.ensure ( sourceRole != null );

         // check isomorphic binding between objects targetRole and sourceRole
         JavaSDM.ensure ( !targetRole.equals (sourceRole) );

         // negative check for link qualifier from sourceRole
         JavaSDM.ensure (sourceRole.getQualifier () == null);

         // search to-one link card from targetRole to card
         card = targetRole.getCard ();

         // check object card is really bound
         JavaSDM.ensure ( card != null );

         // attribute condition upperBound == 1
         JavaSDM.ensure ( card.getUpperBound () == 1 );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return false;

      }
      return true;
   }

   /**
    * <pre>
    *           0..n     link     0..1
    * LinkOperation ------------------------- UMLLinkRef
    *           linkTo               link
    * </pre>
    */
   public static final String PROPERTY_LINK = "link";

   @Property( name = PROPERTY_LINK, partner = UMLLinkRef.PROPERTY_LINK_TO, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private UMLLinkRef link;

   @Property( name = PROPERTY_LINK )
   public boolean setLink (UMLLinkRef value)
   {
      boolean changed = false;

      if (this.link != value)
      {
      
         UMLLinkRef oldValue = this.link;
         LinkOperation source = this;
         if (this.link != null)
         {
            this.link = null;
            oldValue.removeFromLinkTo (this);
         }
         this.link = value;

         if (value != null)
         {
            value.addToLinkTo (this);
         }
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_LINK )
   public LinkOperation withLink (UMLLinkRef value)
   {
      setLink (value);
      return this;
   }

   public UMLLinkRef getLink ()
   {
      return this.link;
   }

   public void removeYou()
   {
      this.setLink (null);
      super.removeYou ();
   }
}



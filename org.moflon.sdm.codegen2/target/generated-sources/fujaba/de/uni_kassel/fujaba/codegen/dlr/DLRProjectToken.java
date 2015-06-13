/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.dlr;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.upb.tools.fca.FCollections;
import de.upb.tools.fca.FDuplicatedHashMap; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class DLRProjectToken extends DLRToken
{


   public DLRToken createDLRToken (Collection elements )
   {
      boolean fujaba__Success = false;
      Iterator fujaba__IterElementsToFile = null;
      Object _TmpObject = null;
      FFile file = null;
      DLRFileToken fileToken = null;
      DLRToken newToken = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object elements is really bound
         JavaSDM.ensure ( elements != null );
         // iterate to-many link contains from elements to file
         fujaba__Success = false;
         fujaba__IterElementsToFile = elements.iterator ();

         while ( !(fujaba__Success) && fujaba__IterElementsToFile.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterElementsToFile.next ();

               // ensure correct type and really bound of object file
               JavaSDM.ensure ( _TmpObject instanceof FFile );
               file = (FFile) _TmpObject;


               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }
         }
         JavaSDM.ensure (fujaba__Success);
         // create object fileToken
         fileToken = new DLRFileToken ( );

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return createDLRToken (fileToken, elements);

      }
      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         // create object newToken
         newToken = new DLRToken ( );

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return createDLRToken (newToken, elements);
   }

   public DLRToken createDLRToken (DLRToken token , Collection elements )
   {
      boolean fujaba__Success = false;
      ElementReference ref = null;
      Iterator fujaba__IterElementsToElem = null;
      Object _TmpObject = null;
      FElement elem = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // check object token is really bound
         JavaSDM.ensure ( token != null );
         // assign attribute token
         token.setId (getNextId());
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // check object token is really bound
         JavaSDM.ensure ( token != null );
         // assign attribute this
         this.setNextId (getNextId() + 1);
         // create link tokenByID from this to token
         this.addToTokenByID (token);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern storypatternwithparams
      try 
      {
         fujaba__Success = false; 

         // check object elements is really bound
         JavaSDM.ensure ( elements != null );
         // check object token is really bound
         JavaSDM.ensure ( token != null );
         // iterate to-many link contains from elements to elem
         fujaba__Success = false;
         fujaba__IterElementsToElem = elements.iterator ();

         while ( fujaba__IterElementsToElem.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterElementsToElem.next ();

               // ensure correct type and really bound of object elem
               JavaSDM.ensure ( _TmpObject instanceof FElement );
               elem = (FElement) _TmpObject;

               // create object ref
               ref = new ElementReference ( );

               // create link element from ref to elem
               ref.setElement (elem);

               // create link elements from ref to token
               ref.setToken (token);

               // story pattern storypatternwiththis
               try 
               {
                  fujaba__Success = false; 

                  // check object ref is really bound
                  JavaSDM.ensure ( ref != null );
                  // create link elementsRefs from this to ref
                  this.addToElementReference (ref);

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

      return token;
   }

   /**
    * <pre>
    *           0..1     elementsRefs     0..n
    * DLRProjectToken ------------------------> ElementReference
    *           project               elementReference
    * </pre>
    */
   public static final String PROPERTY_ELEMENT_REFERENCE = "elementReference";

   @Property( name = PROPERTY_ELEMENT_REFERENCE, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_MANY,
         adornment = ReferenceHandler.Adornment.NONE)
   private FDuplicatedHashMap<FElement, ElementReference> elementReference;

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public Map<FElement, ? extends ElementReference> getElementReference()
   {
      return ((this.elementReference == null)
              ? Collections.EMPTY_MAP
              : Collections.unmodifiableMap(this.elementReference));
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public boolean addToElementReference (ElementReference value)
   {
      return this.addToElementReference (getKeyForElementReference (value), value);
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public DLRProjectToken withElementReference (ElementReference value)
   {
      addToElementReference (value);
      return this;
   }

   public DLRProjectToken withoutElementReference (ElementReference value)
   {
      removeFromElementReference (value);
      return this;
   }


   public boolean removeFromElementReference (ElementReference value)
   {
      return removeFromElementReference (getKeyForElementReference (value), value);
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public void removeAllFromElementReference (){
      if (this.elementReference != null && this.elementReference.size () > 0)
      {
      
         this.elementReference.clear();
      
      }
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public boolean hasInElementReference (ElementReference value)
   {
      return (this.hasInElementReference (this.getKeyForElementReference (value), value));
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public Iterator<? extends ElementReference> iteratorOfElementReference ()
   {
      return ((this.elementReference == null)
              ? FEmptyIterator.<ElementReference>get ()
              : this.elementReference.values ().iterator ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public int sizeOfElementReference ()
   {
      return ((this.elementReference == null)
              ? 0
              : this.elementReference.size ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public boolean hasInElementReference (FElement key, ElementReference value)
   {
      return ((this.elementReference != null) &&
              this.elementReference.containsEntry (key, value));
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public boolean hasKeyInElementReference (FElement key)
   {
      return ((this.elementReference != null) &&
              this.elementReference.containsKey (key));
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public Iterator<FElement> keysOfElementReference ()
   {
      return ((this.elementReference == null)
              ? FEmptyIterator.<FElement>get ()
              : this.elementReference.keySet ().iterator ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public Iterator entriesOfElementReference ()
   {
      return ((this.elementReference == null)
              ? FEmptyIterator.get ()
              : this.elementReference.entrySet ().iterator ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   protected boolean addToElementReference (FElement key, ElementReference value)
   {
      boolean changed = false;

      if (this.elementReference == null)
      {
         this.elementReference = new FDuplicatedHashMap<FElement, ElementReference> ();
      }
   
      ElementReference oldValue = (ElementReference) this.elementReference.put (key, value);
      if (oldValue != value)
      {
         changed = true;
      }
   
      return changed;
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   protected boolean addToElementReference (Map.Entry entry)
   {
      return addToElementReference ((FElement) entry.getKey (), (ElementReference) entry.getValue ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   protected boolean removeFromElementReference (FElement key, ElementReference value)
   {
      boolean changed = false;

      if ((this.elementReference != null) &&
          (value != null || this.elementReference.containsEntry (key, value)))
      {
      
         ElementReference oldValue  = (ElementReference) this.elementReference.remove (key, value);
         if (oldValue == value)
         {
           changed = true;
         }
      
      }
      return changed;
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public boolean removeKeyFromElementReference (FElement key)
   {
      boolean changed = false;

      if (this.elementReference != null)
      {
      
         Collection tmpCol = (Collection) this.elementReference.values (key);
         if (tmpCol != null)
         {
            ElementReference tmpValue;
            Iterator iter = tmpCol.iterator ();
            while (iter.hasNext ())
            {
               tmpValue = (ElementReference) iter.next ();
               this.removeFromElementReference (key, tmpValue);
            }
            changed = true;
         }
      
      }
      return changed;
   }
   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public Iterator<? extends ElementReference> iteratorOfElementReference (FElement key)
   {
      return ((this.elementReference == null)
              ? FEmptyIterator.<ElementReference>get ()
              : FCollections.iterator (this.elementReference.values (key)));
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public int sizeOfElementReference (FElement key)
   {
      return ((this.elementReference == null)
              ? 0
              : this.elementReference.size (key));
   }
   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public FElement getKeyForElementReference (ElementReference value)
   {
      return (value == null ? null : value.getElement ());
   }

   @Property( name = PROPERTY_ELEMENT_REFERENCE )
   public void keyChangedInElementReference (FElement oldKey, ElementReference value)
   {
      if ((this.elementReference != null) && 
          (oldKey != getKeyForElementReference (value)))
      {
         ElementReference oldValue  = (ElementReference) this.elementReference.remove (oldKey, value);
         if (oldValue != null)
         {
            this.elementReference.put (getKeyForElementReference (value), value);
         }
      }
   }

   public static final String PROPERTY_NEXT_ID = "nextId";

   @Property( name = PROPERTY_NEXT_ID, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   private long nextId;

   @Property( name = PROPERTY_NEXT_ID )
   public void setNextId (long value)
   {
      this.nextId = value;
   }

   public DLRProjectToken withNextId (long value)
   {
      setNextId (value);
      return this;
   }

   @Property( name = PROPERTY_NEXT_ID )
   public long getNextId ()
   {
      return this.nextId;
   }

   /**
    * <pre>
    *           0..1     tokenByID     0..1
    * DLRProjectToken ------------------------> DLRToken
    *           project               tokenByID
    * </pre>
    */
   public static final String PROPERTY_TOKEN_BYID = "tokenByID";

   @Property( name = PROPERTY_TOKEN_BYID, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private FHashMap<Long, DLRToken> tokenByID;

   @Property( name = PROPERTY_TOKEN_BYID )
   public Map<Long, ? extends DLRToken> getTokenByID()
   {
      return ((this.tokenByID == null)
              ? Collections.EMPTY_MAP
              : Collections.unmodifiableMap(this.tokenByID));
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public boolean addToTokenByID (DLRToken value)
   {
      return this.addToTokenByID (getKeyForTokenByID (value), value);
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public DLRProjectToken withTokenByID (DLRToken value)
   {
      addToTokenByID (value);
      return this;
   }

   public DLRProjectToken withoutTokenByID (DLRToken value)
   {
      removeFromTokenByID (value);
      return this;
   }


   public boolean removeFromTokenByID (DLRToken value)
   {
      return removeFromTokenByID (getKeyForTokenByID (value), value);
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public void removeAllFromTokenByID (){
      if (this.tokenByID != null && this.tokenByID.size () > 0)
      {
      
         this.tokenByID.clear();
      
      }
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public boolean hasInTokenByID (DLRToken value)
   {
      return (this.hasInTokenByID (this.getKeyForTokenByID (value), value));
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public Iterator<? extends DLRToken> iteratorOfTokenByID ()
   {
      return ((this.tokenByID == null)
              ? FEmptyIterator.<DLRToken>get ()
              : this.tokenByID.values ().iterator ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public int sizeOfTokenByID ()
   {
      return ((this.tokenByID == null)
              ? 0
              : this.tokenByID.size ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public boolean hasInTokenByID (long key, DLRToken value)
   {
      return ((this.tokenByID != null) &&
              (value != null || this.tokenByID.containsKey (key)) && 
              (this.tokenByID.get (key) == value));
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public boolean hasKeyInTokenByID (long key)
   {
      return ((this.tokenByID != null) &&
              this.tokenByID.containsKey (key));
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public Iterator<Long> keysOfTokenByID ()
   {
      return ((this.tokenByID == null)
              ? FEmptyIterator.<Long>get ()
              : this.tokenByID.keySet ().iterator ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public Iterator entriesOfTokenByID ()
   {
      return ((this.tokenByID == null)
              ? FEmptyIterator.get ()
              : this.tokenByID.entrySet ().iterator ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   protected boolean addToTokenByID (long key, DLRToken value)
   {
      boolean changed = false;

      if (this.tokenByID == null)
      {
         this.tokenByID = new FHashMap<Long, DLRToken> ();
      }
   
      DLRToken oldValue = (DLRToken) this.tokenByID.put (key, value);
      if (oldValue != value)
      {
         changed = true;
      }
   
      return changed;
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   protected boolean addToTokenByID (Map.Entry entry)
   {
      return addToTokenByID ((Long) entry.getKey (), (DLRToken) entry.getValue ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   protected boolean removeFromTokenByID (long key, DLRToken value)
   {
      boolean changed = false;

      if (this.tokenByID != null)
      {
         DLRToken oldValue = (DLRToken) this.tokenByID.get (key);
         if (oldValue == value && 
             (oldValue != null || this.tokenByID.containsKey (key)))
         {
         
            this.tokenByID.remove (key);
            changed = true;
         
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public boolean removeKeyFromTokenByID (long key)
   {
      boolean changed = false;

      if (this.tokenByID != null)
      {
         changed = this.tokenByID.containsKey (key);
         if (changed)
         {
         
            DLRToken tmpValue = (DLRToken) this.tokenByID.remove (key);
         
         }
      }
      return changed;
   }
   @Property( name = PROPERTY_TOKEN_BYID )
   public DLRToken getFromTokenByID (long key)
   {
      return ((this.tokenByID == null)
              ? null
              : (DLRToken) this.tokenByID.get (key));
   }
   @Property( name = PROPERTY_TOKEN_BYID )
   public long getKeyForTokenByID (DLRToken value)
   {
      return (value == null ? null : value.getId ());
   }

   @Property( name = PROPERTY_TOKEN_BYID )
   public void keyChangedInTokenByID (long oldKey, DLRToken value)
   {
      if ((this.tokenByID != null) &&  
          (oldKey != getKeyForTokenByID (value)))
      {
         DLRToken oldValue = (DLRToken) this.tokenByID.get (oldKey);
         if (oldValue == value)
         {
            this.tokenByID.remove (oldKey);
            oldValue = (DLRToken)this.tokenByID.put (this.getKeyForTokenByID (value), value);
         }
      }
   }

   public void removeYou()
   {
      this.removeAllFromElementReference ();
      this.removeAllFromTokenByID ();
      super.removeYou ();
   }
}



/*
 * Copyright (c) 2004-2006 Software Engineering Kassel, various authors,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'CoObRA' nor 'CoObRA2' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.uni_kassel.coobra.identifiers;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.RepositorySetupException;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.io.BinaryInputStream;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.HashMapEx;
import de.uni_kassel.util.WeakMap;
import gnu.trove.TLongObjectHashMap;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 17.02.2005, 17:40:26
 */
public class IdentifierModule
{
   private final IDManager manager;
   private final boolean updateManager;

   /**
    * store value for field repository
    */
   private Repository repository;
   private static final int CACHED_ID_SUFFIXES = 100;
   private long nextCachedNr;
   private long maxChachedNr;
   private String cachedPrefix;
   private IDManagementHandler managementHandler;
   private String currentPrefix;

   private final HashMapEx<ID, Reference<Object>> idsSelfLookup = new HashMapEx<ID, Reference<Object>>();
   private final WeakMap<ID, Object> objects = new WeakMap<ID, Object>( idsSelfLookup );
   private final Map<String, TLongObjectHashMap<ID>> indexMaps = new HashMap<String, TLongObjectHashMap<ID>>();
   private final Map<Object, ID> ids;
   private final boolean allowUnknownIDs;

   public IDManager getManager()
   {
      return manager;
   }

   /**
    * Creata a new IdentifierModule.
    * @param repository the repository this module belongs (and send management changes) to
    * @param manager manager for repository cross references
    * @param prefix ID-prefix for this module
    * @param updateManager normally true, false to disable update of the manager when creating new ids
    * @param allowUnknownIDs true to allow reading unknown IDs (false to inherit manager.allowUnknownIDs)
    */
   public IdentifierModule( Repository repository, IDManager manager, String prefix, boolean updateManager,
                            boolean allowUnknownIDs )
   {
      this.allowUnknownIDs = allowUnknownIDs;
      if ( repository == null )
      {
         throw new NullPointerException( "Repository cannot be null!" );
      }
      if ( manager == null )
      {
         throw new NullPointerException( "IDManager cannot be null!" );
      }
      if ( prefix == null )
      {
         throw new NullPointerException( "IDPrefix cannot be null!" );
      }
      managementHandler = new IDManagementHandler();
      setRepository( repository );
      setCurrentPrefix( prefix );
      this.manager = manager;
      this.updateManager = updateManager;
      if ( !updateManager ) {
         ids = new HashMap<Object, ID>();
      } else {
         ids = null;
         manager.registerPrefix(prefix, this);
      }
   }
   /**
    * Creata a new IdentifierModule.
    * @param repository the repository this module belongs (and send management changes) to
    * @param manager manager for repository cross references
    * @param prefix ID-prefix for this module
    * @param updateManager normally true, false to disable update of the manager when creating new ids
    */
   public IdentifierModule( Repository repository, IDManager manager, String prefix, boolean updateManager )
   {
      this(repository, manager, prefix, updateManager, false);
   }

   protected void setCurrentPrefix( String prefix )
   {
      this.currentPrefix = prefix;
      nextCachedNr = -1;
      maxChachedNr = -1;
      cachedPrefix = null;
   }

   /**
    * @return current value of the field repository
    */
   public Repository getRepository()
   {
      return this.repository;
   }

   /**
    * @param value new value for field repository
    * @return true if repository was changed
    * @throws RepositorySetupException if repository already received changes
    */
   private boolean setRepository( Repository value ) throws RepositorySetupException
   {
      boolean changed = false;
      final Repository oldValue = this.repository;
      if ( oldValue != value )
      {
         if ( value != null && !value.isUntouched() )
         {
            throw new RepositorySetupException( "Identifier module must be set up right after creating the repository!" );
         }
         if ( oldValue != null && oldValue.getIdentifierModule() == this )
         {
            this.repository = null;
            oldValue.putManagementDataHandler( MANAGEMENT_KEY_ID_SUFFIX, null );
            oldValue.putManagementDataHandler( MANAGEMENT_KEY_ID_PREFIX, null );
            oldValue.putManagementDataHandler( MANAGEMENT_KEY_NAME, null );
            oldValue.setIdentifierModule(null);
         }
         this.repository = value;
         if ( value != null )
         {
            value.setIdentifierModule( this );
            value.putManagementDataHandler( MANAGEMENT_KEY_ID_SUFFIX, managementHandler );
            value.putManagementDataHandler( MANAGEMENT_KEY_ID_PREFIX, managementHandler );
            value.putManagementDataHandler( MANAGEMENT_KEY_NAME, managementHandler );
         }
         changed = true;
      }
      return changed;
   }

   public ID getID( Object object )
   {
      if ( object instanceof ID )
      {
         return ( ID ) object;
      }
      else
      {
         if ( !updateManager ) {
            ID id = ids.get( object );
            if ( id != null ) {
               return id;
            }
         }
         return manager.getID( object );
      }
   }

   public Object getObject( ID id )
   {
      if ( !updateManager ) {
         if ( objects != null ) {
            Object object = objects.get( id );
            if ( object != null ) {
               return object;
            }
         }
      }
      return manager.getObject( id );
   }

   public Object getObject( String idString )
   {
      if ( !updateManager ) {
         if ( objects != null ) {
            Object object = objects.get( idsSelfLookup.getKey(idString) );
            if ( object != null ) {
               return object;
            }
         }
      }
      return manager.getObject( idString );
   }

   Object lookupObject( String idString )
   {
      ID id = idsSelfLookup.getKey(idString);
      if (id != null)
      {
         return lookupObject(id);
      } else
      {
         return null;
      }
   }

   Object lookupObject( ID id )
   {
      Object object;
      if ( objects != null )
      {
         object = objects.get( id );
      }
      else
      {
         object = null;
      }
      return object;
   }

   public static final String MANAGEMENT_KEY_ID_PREFIX = "IDPREFIX";
   public static final String MANAGEMENT_KEY_ID_SUFFIX = "IDSUFFIX";
   public static final String MANAGEMENT_KEY_NAME = "NAME";

   /**
    * delete the module from memory
    */
   public void delete()
   {
      if ( manager != null )
      {
         if ( objects != null )
         {
            if ( updateManager ) {
               for ( Object object : objects.values() )
               {
                  manager.removeReferences( object );
               }
            }
            objects.clear();
            indexMaps.clear();
         }
         manager.removeFromModules( this );
         if ( !updateManager ) {
            ids.clear();
         }
      }
      setRepository( null );
   }

   /**
    * Send {@link Change.Kind#DESTROY_OBJECT}-changes for all objects that have been garbage collected.
    */
   public void gc()
   {
      if ( objects != null )
      {
         Set<ID> collected = objects.findCollected();
         for ( ID collectedID : collected )
         {
            if ( !deletedObjects.contains( collectedID ) )
            {
               if ( !getTransactionReferenceClassHandler().isAssignableFrom( collectedID.getClassHandler() )
                     && !getTransactionClassHandler().isAssignableFrom( collectedID.getClassHandler() ))
               {
                  Change change = getRepository().getChangeFactory().changeDestroyObject( collectedID );
                  change.setAutoResolving( false );
                  getRepository().acknowledgeChange( change );
               }
            }
            else
            {
               deletedObjects.remove( collectedID ); //object was garbage collected ID will not occur again
            }
         }
      }
   }

   private ClassHandler transactionReferenceClassHandler;

   private ClassHandler getTransactionReferenceClassHandler()
   {
      try
      {
         if ( transactionReferenceClassHandler == null )
         {
            transactionReferenceClassHandler = getRepository().getFeatureAccessModule()
                  .getClassHandler( TransactionReference.class.getName() );
         }
         return transactionReferenceClassHandler;
      } catch ( ClassNotFoundException e )
      {
         throw new RuntimeException( "Classloader problems?", e );
      }
   }

   private ClassHandler transactionClassHandler;

   private ClassHandler getTransactionClassHandler()
   {
      try
      {
         if ( transactionClassHandler == null )
         {
            transactionClassHandler = getRepository().getFeatureAccessModule()
                  .getClassHandler( Transaction.class.getName() );
         }
         return transactionClassHandler;
      } catch ( ClassNotFoundException e )
      {
         throw new RuntimeException( "Classloader problems?", e );
      }
   }

   public boolean isIDStringMatchingCurrentPrefix( String idString )
   {
      if ( idString.startsWith( getCurrentPrefix() ) )
      {
         final int idPrefixLength = getCurrentPrefix().length();
         if ( idString.length() > idPrefixLength && idString.charAt( idPrefixLength ) == ID.ID_SEPARATION_CHARACTER )
         {
            try
            {
               long index = ID.parseIndex( idString, idPrefixLength + 1 );
               if ( index >= 0 && index < nextCachedSuffix() ) //todo: this trashes one id
               {
                  return true;
               }
            } catch ( NumberFormatException e )
            {
               //no long after the # -> no valid id
            }
         }
      }
      return false;
   }

   /**
    * randomizer.
    */
   private static Random random = new Random();

   /**
    * Generates a random prefix.
    * todo: where this method is used try to use better prefixes instead
    *
    * @param bits number of bits randomness (2^bits possible combinations), length of prefix == ceil(bits / 6)
    * @return the new random prefix, with character 0-9, a-z, A-Z, - and _
    */
   public static String generateRandomPrefix( int bits )
   {
      StringBuffer prefix = new StringBuffer();
      while ( bits > 0 )
      {
         final int digits = '9' - '0' + 1;
         final int characters = 'z' - 'a' + 1;
         final int rand = random.nextInt( digits + characters * 2 + 2 );
         char c;
         if ( rand >= digits )
         {
            if ( rand >= characters + digits )
            {
               if ( rand >= digits + characters * 2 )
               {
                  if ( rand > digits + characters * 2 )
                  {
                     c = '-';
                  }
                  else
                  {
                     c = '_';
                  }
               }
               else
               {
                  c = ( char ) ( 'A' - digits - characters + rand );
               }
            }
            else
            {
               c = ( char ) ( 'a' - digits + rand );
            }
         }
         else
         {
            c = ( char ) ( '0' + rand );
         }
         prefix.append( c );
         bits -= 6;
      }
      return prefix.toString();
   }

   private Set<ID> deletedObjects = new HashSet<ID>();

   public void markObjectAsAlreadyDeleted( ID objectID )
   {
      deletedObjects.add( objectID );
   }

   /**
    * @return a collection containing id-object pairs currently registered with this module - 
    *         use with care: possibly not a complete list!
    */
   public Map<ID,Object> getIDs()
   {
      if ( objects != null )
      {
         return Collections.unmodifiableMap(objects);
      }
      else
      {
         return Collections.emptyMap();
      }
   }

   private class IDManagementHandler extends Repository.ManagementDataHandler
   {
      /**
       * Receives changes that should be handled (redone).
       *
       * @param change management change
       */
      public void handleRedo( Change change )
      {
         if ( MANAGEMENT_KEY_ID_SUFFIX.equals( change.getKey() ) )
         {
            nextIdSuffix = ( Long ) change.getNewValue();
         }
         else if ( MANAGEMENT_KEY_ID_PREFIX.equals( change.getKey() ) )
         {
            setCurrentPrefix( ( String ) change.getNewValue() );
         }
         else if ( MANAGEMENT_KEY_NAME.equals( change.getKey() ) )
         {
            putNamedObject_internal( ( String ) change.getOldValue(), change.getNewValue() );
         }
      }

      @Override
      public void handleRead(Change change)
      {
         //FIXME: id suffix change might have been undone - this is a hotfix for it:
         if (MANAGEMENT_KEY_ID_SUFFIX.equals(change.getKey()))
         {
            long newValue = (Long) change.getNewValue();
            if ( newValue > nextIdSuffix ) nextIdSuffix = newValue;
         }
      }
   }

   public ID newID( Object referencedObject )
   {
      if ( referencedObject instanceof ID )
      {
         return ( ID ) referencedObject;
      }

      synchronized( this ) {
          ID existingID = getID( referencedObject );
          if ( existingID != null )
          {
              return existingID;
          }
          final String prefix = getCurrentPrefix();
          //noinspection StringEquality
          if ( prefix != cachedPrefix ) //though String it must not be .equals() here!
          {
             cachedPrefix = prefix;
             nextCachedNr = -1;
          }
          try
          {
             ID newId = new ID( prefix, nextCachedSuffix(),
                   getRepository().getFeatureAccessModule().getClassHandler( referencedObject ) );

             registerID( newId, referencedObject );

             return newId;
          } catch ( ClassNotFoundException e )
          {
             throw new IllegalArgumentException( "Class of referencedObject was not found in FeatureAccessModule!" );
          }
      }
   }

   public void registerID( ID newId, Object changeAware )
   {
      objects.put( newId, changeAware );
      TLongObjectHashMap<ID> indexMap = indexMaps.get(newId.getPrefix());
      if ( indexMap == null )
      {
         indexMap = new TLongObjectHashMap<ID>();
         indexMaps.put(newId.getPrefix(), indexMap);
      }
      indexMap.put( newId.getIndex(), newId );
      if ( changeAware != null )
      {
         if ( changeAware instanceof TransactionReference || changeAware instanceof Transaction )
         {
            // don't generate DESTROY_OBJECT changes for transactions
            deletedObjects.add( newId );
         }
         if ( updateManager ) {
            manager.registerID( newId, changeAware, this );
         } else {
            ids.put( changeAware, newId );
         }
      }
   }

   /**
    * Lookup an existing ID to avoid dublicates.
    * @param id id object for identifying the existing ID
    * @param reading true while reading ID from String/Stream
    * @return an existing ID object, null if no such
    */
   protected ID lookupExistingID(Object id, boolean reading)
   {
      return idsSelfLookup != null ? idsSelfLookup.getKey( id ) : null;
   }

   /**
    * Lookup an existing ID to avoid dublicates.
    * @param prefix prefix
    * @param index index
    * @param reading true while reading ID from String/Stream
    * @return an existing ID object, null if no such
    */
   protected ID lookupExistingID(String prefix, long index, boolean reading)
   {
      TLongObjectHashMap<ID> indexMap = indexMaps.get(prefix);
      if ( indexMap != null )
      {
         return indexMap.get(index);
      }
      else
      {
         return null;
      }
   }

   private synchronized long nextCachedSuffix()
   {
      if ( nextCachedNr < 0 )
      {
         nextCachedNr = getNextIdSuffix();
         maxChachedNr = 0;
      }
      final long next = nextCachedNr;
      nextCachedNr = next + 1;
      if ( next >= maxChachedNr )
      {
         setNextIdSuffix( next + CACHED_ID_SUFFIXES );
         maxChachedNr = next + CACHED_ID_SUFFIXES;
      }
      return next;
   }

   public ID readID( String textualRepresentation, ClassHandler classHandler )
   {
      ID id = lookupExistingID( textualRepresentation, true);
      if ( id != null )
      {
         if( classHandler != null && id.getClassHandler() instanceof UnknownClassHandler )
         {
            id.setClassHandler( classHandler );
         }
         return id;
      }
      else
      {
         return manager.readID( textualRepresentation, classHandler, this, allowUnknownIDs);
      }
   }

   public ID readID( BinaryInputStream in, ClassHandler classHandler ) throws IOException
   {
      return manager.readID( in, classHandler, this, allowUnknownIDs);
   }


   /**
    * Queried whenever a new id is created. Thus should be fast. '#' characters are not allowed.
    *
    * @return an id prefix that makes the concatenation of id prefix and suffix unique (session id)
    */
   public String getCurrentPrefix()
   {
      return currentPrefix;
   }

   public void writePrefixToPersistencyModule() {
      getRepository().acknowledgeChange( getRepository().getChangeFactory().
            changeManagement( null, MANAGEMENT_KEY_ID_PREFIX, null, getCurrentPrefix(), true ) );
   }

   private long nextIdSuffix;

   /**
    * Called to indicate that all suffix numbers smaller than the parameter have been or will be used and are not
    * available any more.
    *
    * @param nextIdSuffix next unused suffix
    */
   private synchronized void setNextIdSuffix( long nextIdSuffix )
   {
      this.nextIdSuffix = nextIdSuffix;
      getRepository().acknowledgeChange( getRepository().getChangeFactory().
            changeManagement( null, MANAGEMENT_KEY_ID_SUFFIX, null, nextIdSuffix, true ) );
   }

   /**
    * Query the next unused number for the current id prefix.
    *
    * @return an id suffix that makes the concatenation of id prefix and suffix unique
    */
   private long getNextIdSuffix()
   {
      return nextIdSuffix;
   }

   private Map<String, Object> namedObjects;

   /**
    * Query the object that was given a specified name.
    *
    * @param name name of interest
    * @return the object that was assigned the name
    * @see #putNamedObject(String, Object)
    */
   public Object getNamedObject( String name )
   {
      return namedObjects != null ? namedObjects.get( name ) : null;
   }

   public Iterator<String> iteratorOfNames()
   {
      if ( namedObjects != null )
      {
         return namedObjects.keySet().iterator();
      }
      else
      {
         return EmptyIterator.get();
      }
   }

   /**
    * Mark an object with a name. This can be used to specify e.g. the object graph root for later idenfication,
    * for instance after loading. One object can have multiple names. Each name identifies exactly one object.
    *
    * @param name   name of the object
    * @param object object that is named
    * @throws IllegalArgumentException if name was already taken for another object
    */
   public void putNamedObject( String name, Object object ) throws IllegalArgumentException
   {
      Object oldValue = putNamedObject_internal( name, object );
      if ( oldValue != object )
      {
         getRepository().acknowledgeChange( getRepository().getChangeFactory().
               changeManagement( null, MANAGEMENT_KEY_NAME, name, object, false ) );
      }
   }

   /**
    * Put a named object without firing management change.
    *
    * @param name   key
    * @param object what is named
    * @return old value
    * @see #putNamedObject(String, Object)
    */
   private Object putNamedObject_internal( String name, Object object )
   {
      Object oldValue;
      if ( object != null )
      {
         if ( namedObjects == null )
         {
            namedObjects = new ConcurrentHashMap<String, Object>();
         }
         oldValue = namedObjects.put( name, object );
      }
      else if ( namedObjects != null )
      {
         oldValue = namedObjects.remove( name );
      }
      else
      {
         oldValue = null;
      }
      return oldValue;
   }

   /**
    * Remove the name from the list of named object.
    *
    * @param name which name to remove
    * @see #putNamedObject(String, Object)
    */
   public void removeNamedObject( String name )
   {
      putNamedObject( name, null );
   }
}

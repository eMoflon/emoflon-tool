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

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.errors.UnknownIdentifierException;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.io.BinaryInputStream;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.HashMapEx;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Manages IDs for a set of repositories.
 *
 * @author christian.schneider@uni-kassel.de
 * @created 16.02.2005, 11:12:22
 */
public class IDManager
{
   private HashMapEx<String, /*Reference<*/IdentifierModule/*>*/> prefixSelfLookup;
   private static final DependencyHandler DEFAULT_DEPENDENCY_HANDLER = new DependencyHandler()
   {
      public Result introduceDependency( Repository dependentRepository, Repository reqiredRepository )
      {
         return Result.ERROR;
      }
   };
   private final boolean allowUnknownIDs;

   /**
    * Create a manager.
    */
   public IDManager()
   {
      this( false );
   }

   /**
    * Create a manager.
    * @param allowUnknownIDs true to allow loading unknown IDs with a fake classhandler - use with care!
    */
   public IDManager(boolean allowUnknownIDs)
   {
      //todo: allow more specific ids, like set of prefixes?
      this.allowUnknownIDs = allowUnknownIDs;
   }

   private Map<Object, ID> ids;

   ID getID( Object object )
   {
      if ( ids != null )
      {
         return ids.get( object );
      }
      else
      {
         return null;
      }
   }

   Object getObject( ID id )
   {
      if ( prefixes != null )
      {
         IdentifierModule module = getIdentifierModule( id );
         if ( module != null )
         {
            return module.lookupObject( id );
         }
      }
      return null;
   }

   /**
    * Query the IdentifierModule in charge for a specified ID.
    *
    * @param id existing ID, managed by this IDManager
    * @return IdentifierModule that maintains the prefix of the given ID
    */
   public IdentifierModule getIdentifierModule( ID id )
   {
      return getIdentifierModuleForPrefix(id.getPrefix());
   }

   private IdentifierModule getIdentifierModuleForPrefix(final String prefix)
   {
      if ( prefixes != null )
      {
         return prefixes.get(prefix);
      }
      else
      {
         return null;
      }
   }

   void registerID( ID newId, Object changeAware, IdentifierModule module )
   {
      if ( ids == null )
      {
         ids = new WeakHashMap<Object, ID>();
      }
      ids.put( changeAware, newId );
      IdentifierModule oldModule = registerPrefix( newId.getPrefix(), module );
      if ( oldModule != null && oldModule != module )
      {
         module.getRepository().getErrorHandlerModule().error( module.getRepository(), ErrorHandlerModule.Level.WARNING,
               ErrorHandlerModule.ERROR_IDENTIFIERS_CHANGED_PREFIX_MODULE, "changed identifier module for prefix '"
               + newId.getPrefix() + "'!", null, newId.getPrefix() );
      }
   }

   ID getExistingID( ID id )
   {
      if ( prefixes != null )
      {
         IdentifierModule module = getIdentifierModule( id );
         if ( module != null )
         {
            return module.lookupExistingID( id, false);
         }
      }
      return null;
   }

   ID getExistingID(long index, String prefix, boolean reading)
   {
      if ( prefixes != null )
      {
         IdentifierModule module = prefixes.get( prefix );
         if ( module != null )
         {
            return module.lookupExistingID( prefix, index, reading );
         }
      }
      return null;
   }

   ID getExistingID(String id, String prefix, boolean reading)
   {
      if ( prefixes != null )
      {
         IdentifierModule module = prefixes.get( prefix );
         if ( module != null )
         {
            return module.lookupExistingID( id, reading );
         }
      }
      return null;
   }

    /**
     * Read an ID from String.
     * @param textualRepresentation string to read from
     * @param classHandler class handler if the instance that is referenced by the ID
     * @param module identifier module in charge
     * @param allowUnknownIDs true to allow to create IDs with dummy class handler
     * @return the read ID
     * @throws UnknownIdentifierException if ID was not registered previously and classHandler is null
     */
   ID readID(String textualRepresentation, ClassHandler classHandler, IdentifierModule module, final boolean allowUnknownIDs)
   {
      int indexOfSep = textualRepresentation.lastIndexOf( ID.ID_SEPARATION_CHARACTER );
      String prefix = indexOfSep > 0 ? textualRepresentation.substring( 0, indexOfSep ) : "";
      String existingPrefix = getExistingPrefix( prefix );
      if ( existingPrefix == null )
      {
         registerPrefix( prefix, module );
      }
      else
      {
         prefix = existingPrefix;
      }
      ID id = getExistingID( textualRepresentation, prefix, classHandler != null );
      if ( id == null )
      {
         if ( classHandler == null )
         {
            if (!allowUnknownIDs && !this.allowUnknownIDs)
            {
               throw new UnknownIdentifierException( textualRepresentation );
            }
            else
            {
               classHandler = new UnknownClassHandler();
            }
         }
         long index = ID.parseIndex( textualRepresentation, indexOfSep + 1 );
         id = new ID( prefix, index, classHandler );
         module.registerID( id, null );
      }
      return id;
   }

   /**
    * Read an ID from Stream.
    * @param in stream to read from
    * @param classHandler class handler if the instance that is referenced by the ID
    * @param module identifier module in charge
    * @param allowUnknownIDs true to allow to create IDs with dummy class handler
    * @return the read ID
    * @throws UnknownIdentifierException if ID was not registered previously and classHandler is null
    * @throws java.io.IOException on IO error
    */
   ID readID(BinaryInputStream in, ClassHandler classHandler, IdentifierModule module, final boolean allowUnknownIDs) throws IOException
   {
      String prefix = in.readRepeatingString();
      String existingPrefix = getExistingPrefix(prefix);
      if ( existingPrefix == null )
      {
         registerPrefix( prefix, module );
      }
      else
      {
         prefix = existingPrefix;
      }
      long index = in.readLong();
      ID id = getExistingID( index, prefix, classHandler != null );
      if ( id == null )
      {
         if ( classHandler == null )
         {
            if (!allowUnknownIDs && !this.allowUnknownIDs)
            {
               throw new UnknownIdentifierException( ID.asIDString(prefix, index) );
            }
            else
            {
               classHandler = new UnknownClassHandler();
            }
         }
         id = new ID( prefix, index, classHandler );
         module.registerID( id, null );
      }
      return id;
   }

   private Map<String, IdentifierModule> prefixes;

   private String getExistingPrefix( String prefix )
   {
      return prefixes != null ? prefixSelfLookup.getKey( prefix ) : null;
   }

   IdentifierModule registerPrefix( String prefix, IdentifierModule module )
   {
      if ( prefixes == null )
      {
         prefixSelfLookup = new HashMapEx<String, /*Reference<*/IdentifierModule/*>*/>();
         prefixes = prefixSelfLookup;//new WeakMap<String, IdentifierModule>( prefixSelfLookup );
      }
      return prefixes.put( prefix, module );
   }

   void removeReferences( Object object )
   {
      if ( ids != null )
      {
         ids.remove( object );
      }
   }

   public void removeFromModules( IdentifierModule module )
   {
      if ( prefixSelfLookup != null )
      {
         for ( Iterator<Map.Entry<String, /*Reference<*/IdentifierModule/*>*/>> iterator = prefixSelfLookup.entrySet().iterator(); iterator.hasNext(); )
         {
            Map.Entry<String,IdentifierModule> entry = iterator.next();
            if ( entry.getValue() == module )
            {
               iterator.remove();
            }
         }
      }
   }


   /**
    * @return current {@link DependencyHandler}
    */
   public DependencyHandler getDependencyHandler()
   {
      final DependencyHandler dependencyHandler = this.dependencyHandler;
      if ( dependencyHandler == null )
      {
         return DEFAULT_DEPENDENCY_HANDLER;
      }
      else
      {
         return dependencyHandler;
      }
   }

   /**
    * store the value for field dependencyHandler.
    */
   private DependencyHandler dependencyHandler;

   /**
    * @param value new {@link DependencyHandler}
    */
   public void setDependencyHandler( final DependencyHandler value )
   {
      final DependencyHandler oldValue = this.dependencyHandler;
      if ( oldValue != value )
      {
         this.dependencyHandler = value;
      }
   }

   /**
    * @return iterator through all IdentifierModules registered with this manager.
    */
   public Iterator<IdentifierModule> iteratorOfIdentifierModule()
   {
      if ( this.prefixes != null )
      {
         return this.prefixes.values().iterator();
      }
      else
      {
         return EmptyIterator.get();
      }
   }

   Object getObject(String idString)
   {
      if ( prefixes != null )
      {
         int indexOfSep = idString.lastIndexOf( ID.ID_SEPARATION_CHARACTER );
         String prefix = indexOfSep > 0 ? idString.substring( 0, indexOfSep ) : "";
         IdentifierModule module = getIdentifierModuleForPrefix( prefix );
         if ( module != null )
         {
            return module.lookupObject( idString );
         }
      }
      return null;
   }
}

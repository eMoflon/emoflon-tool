package de.uni_kassel.coobra.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.transactions.TransactionEntry;

public class PersistencyHelper
{
   public static void openRepository( Repository repository, String fileName )
   {
      openRepository( repository, new File( fileName ) );
   }

   public static void openRepository( Repository repository, File file )
   {
      // set the FilePersistencyModule
      repository.setPersistencyModule( new FilePersistencyModule( file ) );
      // open file
      repository.getPersistencyModule().open( true );
      // load stored model
      repository.restore();
   }

   public static Map<String, Object> getNamedObjects( Repository repository )
   {
      Map<String, Object> namedObjects = new HashMap<String, Object>();
      IdentifierModule identifierModule = repository.getIdentifierModule();
      for( Iterator<String> iter = identifierModule.iteratorOfNames(); iter.hasNext(); )
      {
         String name = iter.next();
         namedObjects.put(name, identifierModule.getNamedObject(name));
      }
      return namedObjects;
   }

   public static void storeRepository( final Repository repository, String fileName )
   {
      storeRepository( repository, new File( fileName ) );
   }

   public static void storeRepository( final Repository repository, File file )
   {
      // create copy-repository
      Repository copyRepository = new Repository();
      // set new PersistencyModule 
      copyRepository.setPersistencyModule( new FilePersistencyModule( file ) );
      copyRepository.getPersistencyModule().open( false );
      // set the old repository's IdentifierModule 
      copyRepository.setIdentifierModule( repository.getIdentifierModule() );
      // copy changes
      TransactionEntry entry = repository.getPersistencyModule().receiveFirst();
      while( entry != null )
      {
         boolean autoResolving = entry.isAutoResolving();
         entry.setAutoResolving( false );
         copyRepository.acknowledgeUpdate( entry );
         entry.setAutoResolving( autoResolving );
         entry = repository.getPersistencyModule().receiveNext( entry );
      }
      // close repository
      copyRepository.getPersistencyModule().close();
   }

   public static void putNamedObjects( Repository repository, Map<String, Object> namedObjects )
   {
      IdentifierModule identifierModule = repository.getIdentifierModule();
      for( Entry<String, Object> entry : namedObjects.entrySet() )
      {
         if (identifierModule.getID(entry.getValue()) != null)
         {
            identifierModule.putNamedObject(entry.getKey(), entry.getValue());
         }
      }
   }
}
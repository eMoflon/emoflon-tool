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
package de.uni_kassel.coobra.server.scm;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.RepositorySetupException;
import de.uni_kassel.coobra.persistency.AbstractStreamPersistencyModule;
import de.uni_kassel.coobra.persistency.ConflictMarker;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.server.DefaultServerModule;
import de.uni_kassel.coobra.server.ServerModule;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class SCMServerModule extends ServerModule<Void, Void> implements Repository.RepositoryListener
{
   private FilePersistencyModule persistencyModule;
   private long lastModified;
   private TransactionEntry lastEntry;

   public SCMServerModule(FilePersistencyModule persistencyModule)
   {
      this.persistencyModule = persistencyModule;
   }

   public FilePersistencyModule getPersistencyModule()
   {
      return persistencyModule;
   }

   @Override
   public boolean setRepository(final Repository value) throws RepositorySetupException
   {
      final Repository oldValue = getRepository();
      boolean changed = false;
      if (oldValue != value)
      {
         if (value != null && !value.isUntouched())
         {
            throw new RepositorySetupException("Server module must be set up right after creating the repository!");
         }
         if (oldValue != null)
         {
            oldValue.removeListener(this);
         }
         super.setRepository(value);
         if (value != null)
         {
            value.addListener(this);
         }
         changed = true;
      }
      return changed;
   }

   public void acknowledgeChange(TransactionEntry entry, EventType type)
   {
      if (  entry instanceof AbstractStreamPersistencyModule.StreamEntry &&
            ((AbstractStreamPersistencyModule.StreamEntry)entry).belongsTo( getPersistencyModule() ) )
      {
         setLastEntry(entry);
      }
   }

   public void setPersistencyModule(FilePersistencyModule persistencyModule)
   {
      this.persistencyModule = persistencyModule;
      this.setLastEntry(null);
   }

   /**
    * @throws UnexpectedFileContentException if file has been altered in an unexpected way - the application should
    * reload the repository entirely
    */
   public List<Change> update() throws PersistencyException, RemoteException
   {
      FilePersistencyModule filePersistencyModule = this.getPersistencyModule();
      long lastModified = filePersistencyModule.getFile().lastModified();
      PersistencyModule repositoryPersistencyModule = getRepository().getPersistencyModule();
      repositoryPersistencyModule.open(false);
      if ( filePersistencyModule != getRepository().getPersistencyModule() )
      {
         filePersistencyModule.open(false);
      }
      if (lastModified == this.lastModified)
      {
         return null;
      }
      TransactionEntry entry = repositoryPersistencyModule.receiveFirst();

      List<ConflictMarker> markers = new ArrayList<ConflictMarker>(3);

      try
      {
         boolean inRemoteChanges = false;
         String version = null;
         boolean hadConflict = false;
         List<Change> conflicts = null;
         while (entry != null)
         {
            if (entry instanceof ConflictMarker)
            {
               hadConflict = true;
               ConflictMarker change = (ConflictMarker) entry;
               markers.add( change );
               inRemoteChanges = AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_REMOTE
                     .equals(change.getKey());
               if (AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_END.equals(change.getKey()))
               {
                  version = (String) change.getNewValue();
               }
            } else
            {
               if (inRemoteChanges)
               {
                  if ( conflicts == null )
                  {
                     conflicts = new ArrayList<Change>();
                  }
                  DefaultServerModule.checkConflictWithCurrentModel( entry, conflicts );
                  getRepository().applyUpdate(entry);
               }
               if (version != null)
               {
                  throw new IllegalStateException("there should not be any changes after the conflict end marker!");
               }
            }
            entry = repositoryPersistencyModule.receiveNext(entry);
         }
         for (ConflictMarker marker : markers)
         {
            marker.markSolved();
         }

         if ( !hadConflict )
         {
            try
            {
               //maybe we got updates at the end of file
               entry = repositoryPersistencyModule.receiveNext(getLastEntry());
               while (entry != null)
               {
                  getRepository().applyUpdate(entry);
                  entry = repositoryPersistencyModule.receiveNext(entry);
               }
            } catch ( PersistencyException e )
            {
               // file seems to have changed in an unexpected way - application should reload repository completely
               throw new UnexpectedFileContentException();
            }
         }
         this.lastModified = lastModified;

         return conflicts;
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   public void connect(Void target) throws IOException, RemoteException
   {
      // nothing to do
   }

   public boolean isConnected()
   {
      return true;
   }

   public void checkin(String suggestedVersionName) throws RemoteException
   {
      if ( persistencyModule.isOpened() )
      {
         try
         {
            TransactionEntry entry = persistencyModule.receiveNext(getLastEntry());
            while (entry != null)
            {
               setLastEntry(entry);
               entry = persistencyModule.receiveNext(entry);
            }
         } catch (PersistencyException e)
         {
            e.printStackTrace();
         }
         persistencyModule.close();
      }
      lastModified = persistencyModule.getFile().lastModified();
   }

   public void useAuthentication(Void credentials)
   {
      throw new UnsupportedOperationException("Specify credentials in SCM client");
   }

   private TransactionEntry getLastEntry()
   {
      if ( lastEntry == null )
      {
         //todo: can this be avoided? maybe introcude a receiveLast?
         TransactionEntry entry = persistencyModule.receiveFirst();
         while ( entry != null )
         {
            this.lastEntry = entry;
            entry = persistencyModule.receiveNext(entry);
         }
      }
      return lastEntry;
   }

   private void setLastEntry(TransactionEntry lastEntry)
   {
      this.lastEntry = lastEntry;
   }
}

/*
 * $Log$
 * Revision 1.9  2008/10/23 14:38:33  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.8  2007/09/04 10:42:16  cschneid
 * fixed SCMServerModule bug with changed persistency module
 *
 * Revision 1.7  2006/12/19 11:46:16  cschneid
 * header updated
 *
 * Revision 1.6  2006/12/12 15:02:14  cschneid
 * paste returns list of pasted objects, copy visitor improved
 *
 * Revision 1.5  2006/06/28 13:56:16  cschneid
 * object/field level conflict detection
 *
 * Revision 1.4  2006/06/13 11:04:32  cschneid
 * handling of \r in change streams, newline before conflict markers possible, fix structural changes by reloading possible
 *
 * Revision 1.3  2006/06/12 12:17:31  cschneid
 * fixed test
 *
 * Revision 1.2  2006/06/12 12:14:08  cschneid
 * more work on scm integration, file locking, file headers
 *
 * Revision 1.1  2006/06/03 15:33:33  cschneid
 * first tests for CVS/SCN integration working
 *
 */


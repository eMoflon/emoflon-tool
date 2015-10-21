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
 *      Copyright (C) Fujaba Development Group
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
 * Contact address:
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
package de.uni_paderborn.fujaba.versioning;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IDManager;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.persistency.AbstractStreamPersistencyModule;
import de.uni_kassel.coobra.persistency.CachedPersistencyModule;
import de.uni_kassel.coobra.persistency.ConcatenatingPersistencyModule;
import de.uni_kassel.coobra.persistency.EntryFilter;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.FilterPersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.persistency.StreamPersistencyModule;
import de.uni_kassel.coobra.server.ServerModule;
import de.uni_kassel.coobra.server.handlers.NonResolvingClasshandlerFactory;
import de.uni_kassel.coobra.server.scm.SCMServerModule;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_paderborn.fujaba.app.Version;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.asg.ASGUnparseInformation;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProgressHandler;
import de.uni_paderborn.fujaba.project.ProjectLoader;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.uml.UMLPlugin;


/**
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 */
public class VersioningLoader extends ProjectLoader
{

   private final Repository importInto;
   private final boolean binary;

   public VersioningLoader()
   {
      this (false);
   }

   public VersioningLoader(boolean binary)
   {
      this.binary = binary;
      importInto = null;
   }

   private Map<ID, ID> importIDMap;

   public Map<ID, ID> getImportIDMap()
   {
      return Collections.unmodifiableMap(importIDMap);
   }

   public VersioningLoader(Repository importInto, boolean binary)
   {
      this.importInto = importInto;
      this.binary = binary;
      importIDMap = new HashMap<ID, ID>();
   }

   /**
    * Load a project from stream.
    *
    * @param stream   stream to read the project data
    * @param file     name of the file the stream stems from, used e.g. for the save dialog (but not
    *                 used to read any data from file)
    * @param progress progress handler to track loading progress
    * @return An instance of FProject that was found in the stream, null if no project could be read
    * @throws IOException if an IO error occures while loading
    */
   @SuppressWarnings({"unchecked"})
   @Override
   protected FProject load(InputStream stream, final File file,
                           final ProgressHandler progress) throws IOException
   {
      long bytes = estimateProjectLoadSize(file);
      long startTime = System.nanoTime();
      final FProject project;
      File originalFile = null;
      boolean workspaceFileEmpty = false;
      if (importInto == null)
      {
         final Repository repository = Versioning.get().setupRepository(true, getPersistencySupport());
         RepositoryNameListener listener = new RepositoryNameListener(repository);
         repository.addPropertyChangeListener("name", listener);
         if (!Versioning.get().isInWorkspace(file))
         {
            File workspaceFile = Versioning.get().createWorkspaceFile();
            //todo: this does not load from the stream given as parameter!
            ProgressFilePersistencyModule filePersistencyModule =
                  new ProgressFilePersistencyModule(file, progress, binary);
            filePersistencyModule.setRepository(repository);
            filePersistencyModule.open(true);
            readHeader(filePersistencyModule, repository);
            filePersistencyModule.close();
            ConcatenatingPersistencyModule module = new ConcatenatingPersistencyModule();
            module.append(filePersistencyModule);
            FilePersistencyModule workspaceFilePersistencyModule = new FilePersistencyModule(workspaceFile);
            PersistencyModule workspaceCFilePersistencyModule = new CachedPersistencyModule(
                  workspaceFilePersistencyModule, Versioning.UNDO_CACHE_LENGTH);
            module.append(workspaceCFilePersistencyModule);
            repository.setPersistencyModule(module);
            repository.getPersistencyModule().open(false);
            VersioningWriter.writeHeader( workspaceFilePersistencyModule, repository, file );
            try
            {
               repository.restore();
//               FIXME: repository.enableGarbageCollection();
            } finally
            {
               setLoadingProject( project = (FProject) repository.getIdentifierModule()
                     .getNamedObject(Versioning.PROJECT_ELEMENT_NAME) );
               if ( project == null )
               {
                  Versioning.get().close( repository, true );
               }
            }
         } else
         {
            ProgressFilePersistencyModule filePersistencyModule = new ProgressFilePersistencyModule(file, progress, binary);
            PersistencyModule cFilePersistencyModule = new CachedPersistencyModule(filePersistencyModule,
                  Versioning.UNDO_CACHE_LENGTH);
            cFilePersistencyModule.setRepository(repository);
            cFilePersistencyModule.open(true);
            try
            {
               Map<String, String> fileInfo = readHeader( filePersistencyModule, repository );
               String originalFileName = fileInfo.get(VersioningWriter.FILE_INFO_KEY_PROJECT_FILE);

               cFilePersistencyModule.close();
               if (originalFileName == null)
               {
                  // project resides in workspace only
                  repository.setPersistencyModule(cFilePersistencyModule);
               } else
               {
                  // start of file is somewhere in the filesystem
                  originalFile = new File(originalFileName);
                  if (!originalFile.isFile())
                  {
                     Versioning.get().renameWorkspaceFileToBackupFile(file);
                     throw new IOException(
                           "A project in workspace could not be restored: required file '"
                                 + originalFileName + "' could not be found!");
                  }
                  FilePersistencyModule originalFilePersistencyModule = new FilePersistencyModule(
                        originalFile, originalFileName.endsWith(UMLPlugin.CBR_FILE_FORMAT));
                  ServerModule serverModule = repository.getServerModule();
                  if ( serverModule instanceof SCMServerModule )
                  {
                     SCMServerModule scmServerModule = (SCMServerModule) serverModule;
                     scmServerModule.setPersistencyModule( originalFilePersistencyModule );
                  }

                  originalFilePersistencyModule.setRepository(repository);
                  originalFilePersistencyModule.open(true);
                  readHeader(originalFilePersistencyModule, repository);
                  originalFilePersistencyModule.close();
                  ConcatenatingPersistencyModule module = new ConcatenatingPersistencyModule();
                  module.append(originalFilePersistencyModule);
                  module.append(cFilePersistencyModule);
                  repository.setPersistencyModule(module);
               }
               final PersistencyModule persistencyModule = repository.getPersistencyModule();
               persistencyModule.open(false);
               //FIXME: filterModule is only for testing, remove when not needed any more:
               final FilterPersistencyModule filterModule = new FilterPersistencyModule(persistencyModule);
               filterModule.setFilter( new EntryFilter()
               {
                  /**
                   * 
                   */
                  private static final long serialVersionUID = -7987748381122280403L;

                  public boolean accept(TransactionEntry entry)
                  {
                     if (entry instanceof Change)
                     {
                        Change change = (Change) entry;
                        final ID id = change.getAffectedObjectID();

                        Transaction enclosingTransaction = change.getEnclosingTransaction();
                        if ( isBehaviour( null, id, enclosingTransaction) ) {
                           return false;
                        }
                        final boolean autoResolving = change.isAutoResolving();
                        change.setAutoResolving( false );
                        try
                        {
                           IdentifierModule identifierModule = repository.getIdentifierModule();
                           if ( isBehaviour( identifierModule, change.getNewValue(), null) ) {
                              return false;
                           }
                           if ( isBehaviour( identifierModule, change.getKey(), null) ) {
                              return false;
                           }
                           if ( isBehaviour( identifierModule, change.getOldValue(), null) ) {
                              return false;
                           }
                        } finally
                        {
                           change.setAutoResolving( autoResolving );
                        }
                     }
                     return true;
                  }

                  private boolean isBehaviour(IdentifierModule identifierModule, Object value, Transaction enclosingTransaction )
                  {
                     if (value instanceof ASGElement)
                     {
                        value = identifierModule.getID(value);
                     }
                     if (value instanceof ID)
                     {
                        ID id = (ID) value;
                        if (id.getClassHandler().getName().startsWith("de.uni_paderborn.fujaba.uml.behavior"))
                        {
                           return true;
                        }
                        if (id.getClassHandler().getName().equals(ASGUnparseInformation.class.getName()) ||
                              id.getClassHandler().getName().equals(ASGInformation.class.getName()))
                        {
                           // FIXME: read already filter - thus transaction is not complete 
                           Object parent = findParent(id, enclosingTransaction);
                           if ( parent != null && isBehaviour( identifierModule, parent, null ) ) {
                              return true;
                           }
                        }
                     }
                     return false;
                  }
               });
//               repository.setPersistencyModule( filterModule );
               repository.restore();
//               FIXME: repository.enableGarbageCollection();
               
               workspaceFileEmpty = cFilePersistencyModule.receiveFirst() == null;

               repository.setPersistencyModule( persistencyModule );
            }
            catch (RuntimeException re)
            {
               try
               {
                  cFilePersistencyModule.close();
               }
               catch (Exception e)
               {
                  // ignoring this let's us expose the actual exception
               }
               throw re;
            } finally {
               setLoadingProject( project = (FProject) repository.getIdentifierModule()
                     .getNamedObject(Versioning.PROJECT_ELEMENT_NAME) );
            }
         }
         repository.removePropertyChangeListener(listener);
         if (project == null)
         {
            repository.getPersistencyModule().close();
         }
      } else
      {
         if (Versioning.get().isInWorkspace(file))
         {
            throw new PersistencyException("Cannot import projects from workspace.");
         }
         Repository repository = Versioning.get().setupRepositoryForImport(importInto);
         RepositoryNameListener listener = new RepositoryNameListener(repository);
         repository.addPropertyChangeListener("name", listener);
         StreamPersistencyModule streamPersistencyModule = new StreamPersistencyModule(
               new FileInputStream(file), null, binary);
         repository.setPersistencyModule(streamPersistencyModule);
         streamPersistencyModule.open(true);
         readHeader(streamPersistencyModule, repository);
         try
         {
            repository.restore();
//            FIXME: repository.enableGarbageCollection();
            repository.getPersistencyModule().close();
            Map<ID, Object> ids = repository.getIdentifierModule().getIDs();
            for (ID id : ids.keySet())
            {
               Object object = ids.get(id);
               if ( object != null )
               {
                  importIDMap.put( id, importInto.getIdentifierModule().getID(object) );
               }
            }
         } finally
         {
            setLoadingProject( project = (FProject) importInto.getIdentifierModule()
                  .getNamedObject(Versioning.PROJECT_ELEMENT_NAME) );
            if (project == null)
            {
               Versioning.get().close(repository, true);
            }
         }
      }
      if (project != null)
      {
         project.setSaved( !Versioning.get().isInWorkspace(file) || workspaceFileEmpty );
         if (originalFile != null)
         {
            project.setFile(originalFile);
         }
         long duration = (System.nanoTime() - startTime)/1000000;
         long mbps = bytes > 0 && duration > 0 ? bytes / duration : 0;
         Logger.getLogger(VersioningLoader.class.getName()).info("Loading project \"" + project.getName() + "\"" +
               " (" +file+ ") took " +duration+ " ms ("+(bytes < 1000 ? "<1" : ""+bytes/1000)+" kb, "+mbps+" kb/s).");
         return project;
      } else
      {
         throw new IOException("Invalid file data: no project was found!");
      }
   }

   @Override
   public long estimateProjectLoadSize(File file)
   {
      if (Versioning.get().isInWorkspace(file))
      {
         try
         {
            FilePersistencyModule filePersistencyModule = new FilePersistencyModule(file);
            filePersistencyModule.setRepository(new Repository());
            filePersistencyModule.open(true);
            try
            {
               Map<String, String> fileInfo = readHeader(filePersistencyModule, null);
               String originalFileName = fileInfo.get(VersioningWriter.FILE_INFO_KEY_PROJECT_FILE);

               if (originalFileName == null)
               {
                  // project resides in workspace only
                  return file.length();
               } else
               {
                  // start of file is somewhere in the filesystem
                  File originalFile = new File(originalFileName);
                  return originalFile.length() + file.length();
               }
            } finally
            {
               filePersistencyModule.close();
            }
         } catch (Exception e)
         {
            // ignore it
            return -1;
         }
      }
      return file.length();
   }

   private Object findParent(ID id, Transaction enclosingTransaction)
   {
      Object parent = null;
      if ( enclosingTransaction != null ) {
         // look into the same transaction and try to find the parent of the unparse info
         //TODO: cache this
         for (Iterator<? extends TransactionEntry> it = enclosingTransaction.iterator(); it.hasNext();)
         {
            TransactionEntry entry = it.next();
            if ( entry instanceof Change) {
               Change change = (Change) entry;
               if ( change.getAffectedObjectID() == id && change.getField() != null ) {
                  String fieldName = change.getField().getName();
                  if ( ASGInformation.PARENT_PROPERTY.equals( fieldName ) ||
                        ASGUnparseInformation.ASGELEMENT_PROPERTY.equals( fieldName ) ) {
                     parent = change.getNewValue();
                     break;
                  }
               }
            }
         }
      }
      return parent;
   }

   private static Map<String, String> readHeader(AbstractStreamPersistencyModule filePersistencyModule, Repository repository)
         throws IOException
   {
      Map<String, String> fileInfo = filePersistencyModule.readHeader(Versioning.FUJABA_MODEL_NAME); // this must not be adapted to app name
      if (repository != null)
      {
         if (!isClient(fileInfo))
         {
            if ( filePersistencyModule instanceof FilePersistencyModule )
            {
               repository.setServerModule(new SCMServerModule((FilePersistencyModule) filePersistencyModule));
            }
         }
         if (!String.valueOf(true).equals(fileInfo.get(VersioningWriter.FILE_INFO_KEY_REUSE)))
         {
            repository.setFeatureAccessModule( Versioning.get().createFeatureAccessModule() );
         }
      }
      String fujabaVersion = fileInfo.get(VersioningWriter.FILE_INFO_KEY_FUJABA_VERSION);
      if (fujabaVersion != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(fujabaVersion, ".");
         try
         {
            int major = Integer.parseInt(tokenizer.nextToken());
            int minor = Integer.parseInt(tokenizer.nextToken());
            int revision = Integer.parseInt(tokenizer.nextToken());
            if (major != Version.get().getMajor())
            {
               //probably allow some conversions here lateron
               throw new UnsupportedOperationException("Reading versioned files from Fujaba " + major + " is not supported.");
            }
            else
            {
               if (minor > Version.get().getMinor())
               {
                  throw new UnsupportedOperationException("You are trying to open a file created with a Fujaba" +
                        " Version which is newer than yours.\nPlease update your Fujaba Version to "+
                        major+"."+minor+" or newer.");
               }
               else if (minor == Version.get().getMinor() && revision > Version.get().getRevision())
               {
                  throw new IllegalStateException("Your Fujaba Version (" + Version.get().toString() + ") is older than the Version the project file was " +
                        "created with ( " + fujabaVersion + " ) - consider updating your application.");
               }
            }
         } catch (NumberFormatException e)
         {
            throw new IllegalStateException("Error reading version number from file:", e);
         } catch (NoSuchElementException e)
         {
            throw new IllegalStateException("Error reading version number from file:", e);
         }
      }
      
      ProjectManager.get().setLastFileHeaderEntries(fileInfo);
      
      return fileInfo;
   }

   private static boolean isClient(Map<String, String> fileInfo)
   {
      return VersioningWriter.FILE_INFO_SERVER_DEFAULT.equals(fileInfo.get(VersioningWriter.FILE_INFO_KEY_SERVER));
   }

   public void convertImportedIDs(File source, File target) throws IOException
   {
      Repository repository = new Repository();
      repository.getFeatureAccessModule().setClassHandlerFactory(null,
            new NonResolvingClasshandlerFactory(repository.getFeatureAccessModule()));
      repository.setIdentifierModule(new IdentifierModule(repository, new IDManager(true),
            "INVALID", true));
      FilePersistencyModule sourceModule = new FilePersistencyModule(source);
      sourceModule.open(true);
      try
      {
         Map<String, String> fileInfo = sourceModule.readHeader(Versioning.FUJABA_MODEL_NAME);
         if (isClient(fileInfo))
         {
            throw new UnsupportedOperationException("Cannot convert projects that are hosted on a server, sorry.");
         }
         FilePersistencyModule targetModule = new FilePersistencyModule(target);
         repository.setPersistencyModule(targetModule);
         targetModule.open(false);
         try
         {
            sourceModule.setRepository(repository);

            targetModule.writeHeader(fileInfo, Versioning.FUJABA_MODEL_NAME);

            TransactionEntry entry = sourceModule.receiveFirst();
            Map<Transaction, Transaction> sentTransactions = new HashMap<Transaction, Transaction>();
            while (entry != null)
            {
               entry.setAutoResolving(false);
               if (entry instanceof Change)
               {
                  if (!(entry instanceof MutableChange))
                  {
                     entry = new MutableChange((Change) entry);
                  }
                  MutableChange change = (MutableChange) entry;
                  change.setAffectedObject(map(change.getAffectedObjectID()));
                  change.setKey(map(change.getKey()));
                  change.setOldValue(map(change.getOldValue()));
                  change.setNewValue(map(change.getNewValue()));
               }
               Transaction activeTransaction = sentTransactions.get(entry.getEnclosingTransaction());
               TransactionEntry sentEntry = targetModule.send(entry, activeTransaction);
               if (sentEntry instanceof Transaction)
               {
                  Transaction transaction = (Transaction) sentEntry;
                  sentTransactions.put((Transaction) entry, transaction);
               }
               entry = sourceModule.receiveNext(entry);
            }
         } finally
         {
            targetModule.close();
         }
      } finally
      {
         sourceModule.close();
      }
   }

   private Object map(Object id)
   {
      if ( id instanceof ID )
      {
         @SuppressWarnings({"SuspiciousMethodCalls"})
         ID targetId = getImportIDMap().get(id);
         return targetId != null ? targetId : id;
      }
      else
      {
         return id;
      }
   }

   private static class RepositoryNameListener implements PropertyChangeListener
   {
      private final Repository repository;


      public RepositoryNameListener(Repository repository)
      {
         this.repository = repository;
      }


      public void propertyChange(PropertyChangeEvent evt)
      {
         String name = repository.getName();
         if (name != null)
         {
            Iterator<? extends FProject> iter = ProjectManager.get().iteratorOfProjects();
            while (iter.hasNext())
            {
               FProject project = iter.next();
               Repository projectRepository = project.getRepository();
               if (projectRepository != null && projectRepository != repository
                     && name.equals(projectRepository.getName()))
               {
                  throw new ProjectAlreadyLoadedException(project);
               }
            }
         }
      }
   }

   @Override
   protected int getTotalWork()
   {
      return 100;
   }

   private static class ProgressFilePersistencyModule extends FilePersistencyModule
   {
      final long fileLength;
      private final ProgressHandler progress;

      public ProgressFilePersistencyModule(File file, ProgressHandler progress, boolean binary)
      {
         super(file.getAbsolutePath(), binary);
         this.progress = progress;
         fileLength = file.length();
      }

      long lastProgressUpdate;

      @Override
      public synchronized TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
      {
         final TransactionEntry read = super.receiveNext(entry, filter);
         if (lastProgressUpdate + 100 < System.currentTimeMillis())
         {
            lastProgressUpdate = System.currentTimeMillis();
            try
            {
               computeProgress(getInputPosition(), fileLength, progress);
            } catch (IOException e)
            {
               // whoops this should not happen
               e.printStackTrace();
            }
         }
         return read;
      }
   }

   static void computeProgress(long currentFilePos, long fileLength, ProgressHandler progress)
   {
      if (progress != null)
      {
         final long currentProgress = fileLength > 0 ? currentFilePos * 100 / fileLength : 100;
         final long workUnits = currentProgress - progress.getWorked();
         if (workUnits > 0)
         {
            progress.worked((int) workUnits);
         }
      }
   }

   /* currently not used - but still needed

   private static class ProgressStreamPersistencyModule extends StreamPersistencyModule
   {
      final long fileLength;
      private final ProgressHandler progress;

      public ProgressStreamPersistencyModule( InputStream stream, File file, ProgressHandler progress )
      {
         super( stream, null );
         this.progress = progress;
         fileLength = file.length();
      }

      @Override
      public synchronized TransactionEntry receiveNext( TransactionEntry entry, String versionName, Object affectedObject )
      {
         final TransactionEntry read = super.receiveNext( entry, versionName, affectedObject );
         try
         {
            computeProgress( getCharArrayWalker().readerPosition( 0 ), fileLength, progress );
         } catch ( IOException e )
         {
            // whoops this should not happen
            e.printStackTrace();
         }
         return read;
      }
   }*/
}

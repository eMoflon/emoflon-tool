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


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.persistency.CachedPersistencyModule;
import de.uni_kassel.coobra.persistency.CompactionModule;
import de.uni_kassel.coobra.persistency.ConcatenatingPersistencyModule;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.server.DefaultServerModule;
import de.uni_kassel.coobra.server.handlers.NonResolvingClassHandler;
import de.uni_kassel.coobra.server.handlers.NonResolvingFieldHandler;
import de.uni_kassel.coobra.server.scm.SCMServerModule;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_paderborn.fujaba.app.Version;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProgressHandler;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.project.ProjectWriter;


/**
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 */
public class VersioningWriter extends ProjectWriter
{
   private final boolean binary;

   public VersioningWriter()
   {
      this (false);
   }

   public VersioningWriter(boolean binary)
   {
      this.binary = binary;
   }

   /**
    * Indicates whether compaction should be used.<br/>
    * usually compact should be off as we could be using SCM versioning
    */
   private boolean compacting = false;
   
   static final String FILE_INFO_KEY_SERVER = "server";

   static final String FILE_INFO_SERVER_DEFAULT = "default";

   static final String FILE_INFO_KEY_REUSE = "reuseFAM";

   static final String FILE_INFO_KEY_PROJECT_FILE = "PROJECT_FILE";

   public static final String FILE_INFO_KEY_FUJABA_VERSION = "Fujaba Version";

   /**
    * @deprecated use VersioningWriter#FILE_INFO_KEY_FUJABA_VERSION (will be removed in Fujaba 5.2)
    */
   static final String FUJABA_VERSION = FILE_INFO_KEY_FUJABA_VERSION;

   public static final String FILE_INFO_KEY_APPLICATION_NAME = "Application Name";

   public static final String FILE_INFO_KEY_APPLICATION_VERSION = "Application Version";


   @Override
   protected void saveAs(FProject project, File file, ProgressHandler progress,
         boolean compressed) throws IOException
   {
      save(project, file, progress, compressed);
   }


   @Override
   protected void save(FProject project, File file, ProgressHandler progress,
         boolean compressed) throws IOException
   {
      final Repository repository = project.getRepository();

      // if the file is opened check that it is the same repository
      boolean currentlyOpenedFile = false;
      if (repository.getPersistencyModule() instanceof ConcatenatingPersistencyModule)
      {
         String canonicalPath = file.getCanonicalPath();
         for (Iterator<? extends FProject> it = ProjectManager.get().iteratorOfProjects(); it.hasNext();)
         {
            FProject aProject = it.next();
            try
            {
               if (aProject.getFile() != null
                     && aProject.getFile().getCanonicalPath().equals(canonicalPath))
               {
                  // ok, our file is opened check that it's the same project
                  if (aProject != project)
                  {
                     throw new IOException("The file is opened by Fujaba but contains another project"
                                 + " - cannot save to that file!");
                  }
                  currentlyOpenedFile = true;
               }
            }
            catch (IOException e)
            {
               // ok file cannot be resolved - skip that project
            }
         }
      }


      if (!currentlyOpenedFile)
      {
         final Repository copyRepository = Versioning.get().setupRepository(
               Versioning.get().usingDefaultModule(repository), null);
         if (file.exists())
         {
            if (!file.delete())
            {
               throw new IOException("failed to overwrite existing file: " + file);
            }
         }
         FilePersistencyModule filePersistencyModule = new FilePersistencyModule(file, binary);


         if (isCompacting())
         {
            CompactionModule compactionModule = new CompactionModule(filePersistencyModule, true);

            //debug: testing complete compact
//            compactionModule.setFlushingOnNewTransaction(false);

            copyRepository.setPersistencyModule(compactionModule);
         } else
         {
            copyRepository.setPersistencyModule(filePersistencyModule);
         }
         copyRepository.getPersistencyModule().open(false);
         copyRepository.setIdentifierModule(repository.getIdentifierModule());
         copyRepository.setFeatureAccessModule(repository.getFeatureAccessModule());
         writeHeader(filePersistencyModule, repository, null);

         PersistencyModule source = repository.getPersistencyModule();
         source.open(false);
         TransactionEntry entry = source.receiveFirst();
         while (entry != null)
         {
            final boolean autoResolving = entry.isAutoResolving();
            entry.setAutoResolving(false);
            
            // skip dummy entries
            if (isNoDummy(entry)) {
               copyRepository.acknowledgeUpdate(entry);
               entry.setAutoResolving(autoResolving);            
            }
            entry = source.receiveNext(entry);
         }

         File workspaceFile = Versioning.get().getWorkspaceFile(repository);
         copyRepository.getPersistencyModule().close();
         if (workspaceFile != null)
         {
            repository.getPersistencyModule().close();
            reopen(repository, filePersistencyModule, workspaceFile);
         }
      }
      else
      {
         ConcatenatingPersistencyModule persistencyModule = ((ConcatenatingPersistencyModule) repository
               .getPersistencyModule());
         FilePersistencyModule filePersistencyModule = (FilePersistencyModule) persistencyModule
               .iteratorOfPersistencyModules().next();
         CachedPersistencyModule workspaceCPersistencyModule = (CachedPersistencyModule) persistencyModule
               .getLastPersistencyModule();
         FilePersistencyModule workspacePersistencyModule =
               (FilePersistencyModule) workspaceCPersistencyModule.getDelegate();
         TransactionEntry entry = workspacePersistencyModule.receiveFirst();
         filePersistencyModule.open(false);
         while (entry != null)
         {
            entry.setAutoResolving(false);
            filePersistencyModule.send(entry, null);
            entry = workspacePersistencyModule.receiveNext(entry);
         }
         workspacePersistencyModule.close();
         persistencyModule.close();

         File workspaceFile = workspacePersistencyModule.getFile();

         reopen(repository, filePersistencyModule, workspaceFile);
      }
      ProjectManager.get().updateProjectLoadOrder();
   }


   private boolean isNoDummy(TransactionEntry entry) {
      if ( entry instanceof Change )
      {
         Change change = (Change) entry;
         if ( change.getAffectedObjectID() != null &&
              change.getAffectedObjectID().getClassHandler() instanceof NonResolvingClassHandler )
         {
            return false;
         }
         // NonResolvingFieldHandler may be used by some classhandler to filter deprecated fields
         if (change.getKind() == Change.Kind.ALTER_FIELD && change.getField() instanceof NonResolvingFieldHandler)
         {
            return false;
         }
      }
      return true;
   }


   static void writeHeader(FilePersistencyModule filePersistencyModule,
         Repository repository, File includedFile) throws IOException
   {
      TreeMap<String, String> fileInfo = new TreeMap<String, String>();
      if (repository.getServerModule().getClass().equals(
            DefaultServerModule.class))
      {
         fileInfo.put(FILE_INFO_KEY_SERVER, FILE_INFO_SERVER_DEFAULT);
      }
      fileInfo.put(FILE_INFO_KEY_REUSE, String.valueOf(Versioning.get()
            .usingDefaultModule(repository)));
      if (includedFile != null)
      {
         fileInfo.put(FILE_INFO_KEY_PROJECT_FILE, includedFile
               .getAbsolutePath());
      }
      fileInfo.put(FILE_INFO_KEY_APPLICATION_NAME, Version.get().getAppName());
      if ( Version.get().isAppPresent() )
      {
         fileInfo.put(FILE_INFO_KEY_APPLICATION_VERSION, Version.get().getAppVersion());
      }
      fileInfo.put(FILE_INFO_KEY_FUJABA_VERSION, Version.get().getVersion());
      filePersistencyModule.writeHeader(fileInfo, Versioning.FUJABA_MODEL_NAME); // this must not be adapted to app name!
   }


   private void reopen(Repository repository,
         FilePersistencyModule filePersistencyModule, File workspaceFile)
         throws IOException
   {
      ConcatenatingPersistencyModule newModule = new ConcatenatingPersistencyModule();
      newModule.append(filePersistencyModule);
      if ( repository.getServerModule() instanceof SCMServerModule )
      {
         SCMServerModule serverModule = (SCMServerModule) repository.getServerModule();
         serverModule.setPersistencyModule(filePersistencyModule);
      }
      Versioning.get().renameWorkspaceFileToBackupFile(workspaceFile);
      
      FilePersistencyModule workspaceFilePersistencyModule = new FilePersistencyModule(
            Versioning.get().createWorkspaceFile());
      PersistencyModule workspaceCFilePersistencyModule = new CachedPersistencyModule(workspaceFilePersistencyModule,
            Versioning.UNDO_CACHE_LENGTH);
      newModule.append(workspaceCFilePersistencyModule);
      repository.setPersistencyModule(newModule);
      newModule.open(false);
      VersioningWriter.writeHeader(workspaceFilePersistencyModule, repository,
            filePersistencyModule.getFile());
   }


   /**
    * @return whether compaction should be used or not.
    */
   public boolean isCompacting()
   {
      return this.compacting;
   }


   /**
    * @param compacting whether compaction should be used.
    */
   public void setCompacting( boolean compacting )
   {
      this.compacting = compacting;
   }

}

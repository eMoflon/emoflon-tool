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
package de.uni_kassel.coobra.util;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IDManager;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.persistency.StreamPersistencyModule;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.visitor.CopyGuide;
import de.uni_kassel.util.SingleValueIterator;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CopyManager extends ChangeProtocolGenerator
{
   private CopyGuide guide;
   private Collection<?> context;
   private CopyManager.GeneratorIdentifierModule generatorIdentifierModule;
   private static final String MANAGEMENT_KEY_TYPE = "COPY_EXTERNAL_REFERENCE_TYPE";

   protected CopyManager()
   {
      this(new Repository());
   }

   protected CopyManager( Repository repository )
   {
      super( repository );
      generatorIdentifierModule = new GeneratorIdentifierModule();
      getRepository().setIdentifierModule(generatorIdentifierModule);
   }

   /**
    * Called for each object that might be resolved to an external reference.
    * @param object object that is referenced
    * @param context context given to {@link #copy}
    * @return an identifier uniquely identifying the object in the given context
    */
   protected abstract String getIdentifier(Object object, Collection<?> context);

   /**
    * Called for each reference while pasting that was formerly returned by the {@link #getIdentifier} method.
    * @param identifier identifier returned by {@link #getIdentifier}
    * @param context context given to {@link #paste}
    * @param type classhandler for the object to be found
    * @return the object that is referenced by the identifier, not null
    */
   protected abstract Object getObject(String identifier, Collection<?> context, ClassHandler type);

   public void copy(Object root, Collection<?> context)
   {
      copy(new SingleValueIterator<Object>(root), context);
   }

   public void copy(Iterator<?> roots, Collection<?> context)
   {
      this.context = context;
      guide = new CopyGuide(getRepository().getFeatureAccessModule());
      guide.setVisitor(null, this);
      if (context != null)
      {
         for (Object element : context)
         {
            getRepository().getIdentifierModule().newID(element);
         }
      }
      guide.visit(roots);
   }

   protected boolean generateCreate(Object value)
   {
      boolean modelElement = isModelElement(value);
      boolean copied = guide.getCopiedObjects().contains(value);
      if (modelElement && !copied)
      {
         // is an external reference - generate an ID for it
         ID id = generatorIdentifierModule.newID(value);
         // and remember type in a management change
         try
         {
            Change change = getRepository().getChangeFactory().changeManagement(null,
                  MANAGEMENT_KEY_TYPE, id.toString(),
                  getRepository().getFeatureAccessModule().getClassHandler( value ), false );
            getRepository().acknowledgeChange(change);
         } catch (ClassNotFoundException e)
         {
            throw new RuntimeException("Classloader problems?", e);
         }
      }
      return copied && modelElement;
   }

   protected abstract boolean isModelElement(Object value);

   public Transferable getTransferable(String modelName)
   {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      StreamPersistencyModule out = new StreamPersistencyModule(null, outputStream);
      out.setRepository( getRepository() );
      try
      {
         out.writeHeader(null, modelName);
      } catch (IOException e)
      {
         // should not happen
         throw new RuntimeException(e);
      }

      PersistencyModule persistencyModule = getRepository().getPersistencyModule();
      TransactionEntry entry = persistencyModule.receiveFirst();
      while (entry != null)
      {
         TransactionEntry externalizedEntry = entry.externalize();
         out.send( externalizedEntry, null );
         entry = persistencyModule.receiveNext(entry);
      }
      out.close();
      String string = new String(outputStream.toByteArray());
      return new StringSelection( string );
   }

   public class PasteResult {
      private Repository repository;
      private List<Object> pastedObjects;

      public List<Object> getPastedObjects()
      {
         return pastedObjects;
      }

      public Repository getRepository()
      {
         return repository;
      }

      public PasteResult(List<Object> pastedObjects, Repository repository)
      {
         this.pastedObjects = pastedObjects;
         this.repository = repository;
      }
   }

   public PasteResult paste(Collection<?> context)
   {
      final List<Object> pastedObjects = new LinkedList<Object>();
      this.context = context;
      Repository repository = new Repository();
      repository.setFeatureAccessModule(repository.getFeatureAccessModule());
      repository.setIdentifierModule(new PasteIdentifierModule(repository));
      repository.putManagementDataHandler(MANAGEMENT_KEY_TYPE, new TypeManagementHandler());

      PersistencyModule persistencyModule = getRepository().getPersistencyModule();
      TransactionEntry entry = persistencyModule.receiveFirst();
      while (entry != null)
      {
         TransactionEntry externalizedEntry = entry.externalize();
         if (externalizedEntry instanceof Change)
         {
            if (!(externalizedEntry instanceof MutableChange))
            {
               throw new IllegalArgumentException("Changes for copy/paste must be mutable!");
            }
            ((MutableChange) externalizedEntry).setRepository(repository);
         }
         repository.acknowledgeUpdate(externalizedEntry);
         addCreatedObject(externalizedEntry, pastedObjects);
         entry = persistencyModule.receiveNext(entry);
      }
      return new PasteResult( pastedObjects, repository );
   }

   public PasteResult paste(Transferable data, Collection<?> context, String modelName) throws UnsupportedFlavorException, IOException
   {
      return paste( data, context, new Repository(), modelName);
   }

   public class NoDataException extends RuntimeException
   {

   }

   /**
    *
    * @param data
    * @param context
    * @param repository
    * @param modelName
    * @return
    * @throws UnsupportedFlavorException
    * @throws IOException
    * @throws StreamCorruptedException if transferable does not contain correct change list with header
    * @throws UnsupportedOperationException if header in transferable is not supported (version or model name)
    * @throws NoDataException if the Transferable is null or empty
    */
   protected PasteResult paste(Transferable data, Collection<?> context, Repository repository, String modelName)
         throws UnsupportedFlavorException, IOException, NoDataException
   {
      if ( data == null )
      {
         throw new NoDataException();
      }
      String text = (String) data.getTransferData(DataFlavor.stringFlavor);
      if ( text.length() == 0 )
      {
         throw new NoDataException();
      }
      InputStream input = new ByteArrayInputStream(text.getBytes());

      final List<Object> pastedObjects = new LinkedList<Object>();
      this.context = context;
      repository.setFeatureAccessModule(repository.getFeatureAccessModule());
      repository.setIdentifierModule(new PasteIdentifierModule(repository));
      repository.putManagementDataHandler(MANAGEMENT_KEY_TYPE, new TypeManagementHandler());

      StreamPersistencyModule persistencyModule = new StreamPersistencyModule( input, null );
      persistencyModule.setRepository( repository );
      persistencyModule.readHeader(modelName);
      TransactionEntry entry = persistencyModule.receiveFirst();
      while (entry != null)
      {
         repository.acknowledgeUpdate(entry);
         addCreatedObject(entry, pastedObjects);
         entry = persistencyModule.receiveNext(entry);
      }
      return new PasteResult( pastedObjects, repository );
   }

   private void addCreatedObject(TransactionEntry entry, Collection<Object> pastedObjects)
   {
      if ( entry instanceof Change) {
         Change change = (Change) entry;
         if ( change.getKind() == Change.Kind.CREATE_OBJECT )
         {
            pastedObjects.add( change.getAffectedObject() );
         }
      }
   }

   private class GeneratorIdentifierModule extends IdentifierModule
   {
      public GeneratorIdentifierModule()
      {
         super(CopyManager.this.getRepository(), new IDManager(), "", true);
      }

      @Override
      public ID newID(Object referencedObject)
      {
         if (!guide.getCopiedObjects().contains(referencedObject))
         {
            String name = getIdentifier(referencedObject, context);
            if (name != null && name.length() == 0)
            {
               throw new NullPointerException("identifier may not be empty!");
            }
            if ( name != null ) {
               // todo: check duplicate?
               setCurrentPrefix(name);
            } else {
               setCurrentPrefix("");
            }
         } else
         {
            setCurrentPrefix("");
         }
         return super.newID(referencedObject);
      }
   }

   private class PasteIdentifierModule extends IdentifierModule
   {
      public PasteIdentifierModule(Repository repository)
      {
         super(repository, new IDManager(), "", true);
      }

      @Override
      public Object getObject(ID id)
      {
         Object object = super.getObject(id);
         if (object == null)
         {
            if (id.getPrefix().length() > 0)
            {
               object = CopyManager.this.getObject(id.getPrefix(), context, idTypes.get( id.toString() ));
            }
         }
         return object;
      }


      @Override
      protected ID lookupExistingID(Object id, boolean reading)
      {
         ID existingID = super.lookupExistingID(id, reading);
         if (existingID == null && !reading)
         {
            if ( id instanceof ID ) {
               ID actualID = (ID) id;
               String prefix = actualID.getPrefix();
               if ( prefix.length() > 0 ) {
                  registerID( actualID, CopyManager.this.getObject(prefix, context, idTypes.get( actualID.toString() )) );
                  return actualID;
               }
            } else if ( id instanceof String ) {
               String idString = (String) id;
               int indexOfSep = idString.lastIndexOf( ID.ID_SEPARATION_CHARACTER );
               if ( indexOfSep > 0 ) {
                  String prefix = indexOfSep > 0 ? idString.substring( 0, indexOfSep ) : "";
                  ClassHandler type = idTypes.get(idString);
                  Object object = CopyManager.this.getObject(prefix, context, type);
                  if ( object == null ) throw new NullPointerException("getObject may not return null");
                  try
                  {
                     ID actualID = readID(idString, getRepository().getFeatureAccessModule().getClassHandler(object));
                     registerID( actualID, CopyManager.this.getObject(prefix, context, type) );
                     return actualID;
                  } catch (ClassNotFoundException e)
                  {
                     throw new RuntimeException( "Falied to load class. Please configure feature access module -> Classloader problems?", e );
                  }
               }
            }
         }
         return existingID;
      }
   }

   private Map<String, ClassHandler> idTypes = new HashMap<String, ClassHandler>();

   private class TypeManagementHandler extends Repository.ManagementDataHandler
   {
      public void handleRedo(Change change)
      {
         ClassHandler type = (ClassHandler) change.getNewValue();
         idTypes.put( (String) change.getOldValue(), type);
      }
   }
}

/*
 * $Log$
 * Revision 1.12  2008/10/23 14:38:42  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.11  2007/10/17 11:07:13  cschneid
 * use new features version
 *
 * Revision 1.10  2007/07/09 12:01:04  cschneid
 * throw exception if copy data is empty
 *
 * Revision 1.9  2007/06/28 15:29:23  cschneid
 * fixed auto resolve and rolledback changes for client/server mode, copy supports multiple context objects
 *
 * Revision 1.8  2007/03/14 10:27:04  cschneid
 * fixed possible bug
 *
 * Revision 1.7  2007/03/12 09:23:22  cschneid
 * provide class handler when searching for external reference in copy
 *
 * Revision 1.6  2007/01/09 09:49:52  cschneid
 * copying improved
 *
 * Revision 1.5  2006/12/19 17:28:07  cschneid
 * Copy-Paste works with clipboard (for inter-VM copy-paste)
 *
 * Revision 1.4  2006/12/19 11:46:10  cschneid
 * header updated
 *
 * Revision 1.3  2006/12/12 15:02:14  cschneid
 * paste returns list of pasted objects, copy visitor improved
 *
 * Revision 1.2  2006/12/06 11:35:39  cschneid
 * comments
 *
 * Revision 1.1  2006/12/05 09:05:25  cschneid
 * copy/paste test working
 *
 */


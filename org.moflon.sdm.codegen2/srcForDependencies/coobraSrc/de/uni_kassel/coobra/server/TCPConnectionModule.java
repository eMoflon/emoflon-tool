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
package de.uni_kassel.coobra.server;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.persistency.EntryFilter;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.SocketPersistencyModule;
import de.uni_kassel.coobra.server.DefaultServer.VersionInfo;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.messages.CheckinRequest;
import de.uni_kassel.coobra.server.messages.ContinuousUpdateRequest;
import de.uni_kassel.coobra.server.messages.Request;
import de.uni_kassel.coobra.server.messages.Response;
import de.uni_kassel.coobra.server.messages.UpdateRequest;
import de.uni_kassel.coobra.server.messages.ValidatePrefixRequest;
import de.uni_kassel.coobra.server.messages.VersionInfoRequest;
import de.uni_kassel.coobra.server.messages.VersionInfoResponse;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 29.03.2005, 17:14:32
 */
public class TCPConnectionModule extends DefaultServerModule.ConnectionModule
{
   private final TCPConnection tcpConnection;

   protected void open()
   {
   }

   public TCPConnectionModule( String host, int port ) throws UnknownHostException {
      tcpConnection = new MyTCPConnection( host, port );
   }

   public TCPConnectionModule( InetAddress address, int port )
   {
      tcpConnection = new MyTCPConnection( address, port );
   }

   /**
    * Connect the server module to the remote site/open a file etc.
    * May be called multiple times, but does not cascade.
    *
    * @param readonly if true the connection is opened for reading only
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      if ( !isOpened() )
      {
         autoOpen();
      }
   }

   private SocketPersistencyModule delegate = new SocketPersistencyModule();

   @Override
   public boolean isOpened()
   {
      return tcpConnection.getSocket() != null && tcpConnection.getSocket().isConnected();
   }

   private boolean inCheckin;
   private boolean inUpdate;

   @Override
   public void beginCheckin(String lastKnownServerVersion, String suggestedVersionName) throws RemoteException
   {
      try
      {
         checkBusy();
         autoOpen();
         checkSetup( true );
         inCheckin = true;
         delegate.open( false );
         send( new CheckinRequest( lastKnownServerVersion, suggestedVersionName ) );
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      }
   }

   private int autoOpened;

   private void autoOpen()
   {
      if ( !tcpConnection.isOpened() ) {
         autoOpened = 0;
      }
      autoOpened++;
      if ( autoOpened == 1 )
      {
         tcpConnection.open();
      }
   }

   @Override
   public void close() throws PersistencyException
   {
      if ( tcpConnection.getSocket() != null )
      {
         delegate.close();
         tcpConnection.close();
         autoOpened = 0;
      }
   }

   @Override
   public String finishCheckin() throws RemoteException
   {
      try
      {
         if ( inUpdate || !inCheckin )
         {
            throw new IllegalStateException( "Local checkin operation not in progress!" );
         }
         Response response = readResponse();
         //TODO: evaluate response
         System.out.println( response );
         autoClose();
         return response.getData();
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      } catch ( ClassNotFoundException e )
      {
         throw new PersistencyException( "unknown response from server", e );
      } finally {
         inCheckin = false;
      }
   }

   protected void send( Request request ) throws IOException
   {
      if ( getServerModule().getCredentials() != null ) {
         request.setUserName( getServerModule().getCredentials().getUserName() );
         request.setPassword( getServerModule().getCredentials().getPassword() );
      }
      boolean readOnly = delegate.isInReadOnlyMode();
      delegate.close();
      tcpConnection.send( request );
      delegate.open( readOnly );
   }

   protected Response readResponse() throws IOException, ClassNotFoundException, RemoteException
   {
      boolean readOnly = delegate.isInReadOnlyMode();
      delegate.close();
      try {
         Response response = tcpConnection.readResponse();
         return response;
      } finally {
         delegate.open( readOnly );
      }
   }

   private void autoClose()
   {
      if ( autoOpened > 0 )
      {
         autoOpened--;
         if ( autoOpened == 0 )
         {
            close();
         }
      }
   }

   /**
    * @param lastKnownServerVersion
    * @param compacting
    * @param continuous
    * @return null if no new changes are available, newest Version else
    */
   @Override
   public String update( String lastKnownServerVersion, boolean compacting, boolean continuous ) throws RemoteException
   {
      try
      {
         checkBusy();
         autoOpen();
         checkSetup( true );
         inUpdate = true;
         UpdateRequest request;
         if ( !continuous )
         {
            request = new UpdateRequest( lastKnownServerVersion );
         }
         else
         {
            request = new ContinuousUpdateRequest( lastKnownServerVersion );
         }
         request.setCompacting( compacting );
         send( request );
         Response response = readResponse();
         //todo: response
//         System.out.println( response );
         return response.getData();
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      } catch ( ClassNotFoundException e )
      {
         throw new PersistencyException( "unknown response from server", e );
      }
   }

   private void checkSetup( boolean autoCloseOnFailure ) throws RemoteException
   {
      final String currentPrefix = getServerModule().getRepository().getIdentifierModule().getCurrentPrefix();
      try {
         final String result = validateIDPrefix( currentPrefix );
         if ( result != null )
         {
            if ( autoCloseOnFailure )
            {
               autoClose();
            }
            throw new PersistencyException( "Invalid id prefix - use setupClient before" +
                  " server communication (suggested was " + result + ", current is " + currentPrefix + " )!" );
         }
      } catch ( PersistencyException e ) {
         try
         {
            close();
         } catch (PersistencyException e2)
         {
            // ignore failed closed
         }
         throw e;
      }
   }

   private void checkBusy()
   {
      if ( inUpdate || inCheckin )
      {
         throw new IllegalStateException( "Local update or checkin operation already in progress!" );
      }
   }

   protected String finishUpdate()
   {
      if ( !inUpdate || inCheckin )
      {
         throw new IllegalStateException( "Local update operation not in progress!" );
      }
      inUpdate = false;
      autoClose();
      return null;
   }

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the last change in this version or if entry occured after this version).
    *
    * @param entry          preceeding entry
    * @param filter
    * @return the next change
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      if ( !inUpdate )
      {
         throw new IllegalStateException( "Receiving changes is possible in update only!" );
      }
      TransactionEntry change;
      if ( entry == null )
      {
         delegate.reset();
         change = delegate.receiveFirst();
      } else
      {
         change = delegate.receiveNext( entry, filter);
      }
      return change;
   }

   /**
    * Read the change preceeding the specified TransactionEntry. If entry is a Transaction the change before the
    * first enclosed change is returned, if entry is a change the previous change is returned.
    * If no changes are before the specified entry, null is returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the first change in this version or if entry occured before this version).
    *
    * @param entry          succeeding entry
    * @param filter
    * @return the previous change
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public Change receivePrevious(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      throw new UnsupportedOperationException( "Receive previous is not supported by server connection module!" );
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      throw new UnsupportedOperationException("not implemented");
   }

   /**
    * Store info about a change at the end of the change list. The
    * eventually enclosing transaction is <b>not</b> stored. After the
    * change was stored the returned change object must be used to reference
    * the stored change in conjunction with this persistency module.
    *
    * @param change            change to be stored
    * @param activeTransaction currently active transaction
    * @return the change object that must be used from now on to reference
    *         the stored transaction in conjunction with this persistency module
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public Change send( Change change, Transaction activeTransaction ) throws PersistencyException
   {
      if ( !inCheckin )
      {
         throw new IllegalStateException( "Sending changes is possible in checkin only!" );
      }

      return delegate.send( change, activeTransaction );
   }

   /**
    * Store info about a transaction at the end of the transaction list. The
    * eventually enclosed transaction entries are <b>not</b> stored. After the
    * transaction was stored the returned transaction object must be used to reference
    * the stored transaction in conjunction with this persistency module.
    *
    * @param transaction       transaction to be stored
    * @param activeTransaction
    * @return the transaction object that must be used from now on to reference
    *         the stored transaction in this persistency module
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public Transaction send( Transaction transaction, Transaction activeTransaction ) throws PersistencyException
   {
      if ( !inCheckin )
      {
         throw new IllegalStateException( "Sending changes is possible in checkin only!" );
      }
      return delegate.send( transaction, activeTransaction );
   }

   /**
    * @param value new value for field serverModule
    * @return true if serverModule was changed
    */
   @Override
   public boolean setServerModule( DefaultServerModule value )
   {
      boolean changed = super.setServerModule( value );
      if ( changed )
      {
         if ( getServerModule() != null )
         {
            delegate.setRepository( getServerModule().getRepository() );
         } else
         {
            delegate.setRepository( null );
         }
      }
      return changed;
   }

   @Override
   public void sendEOF()
   {
      delegate.sendEOF();
   }

   @Override
   public String validateIDPrefix( String idPrefix ) throws RemoteException
   {
      checkBusy();
      autoOpen();
      try
      {
         send( new ValidatePrefixRequest( idPrefix ) );
         Response response = readResponse();
         return response.getData();
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      } catch ( ClassNotFoundException e )
      {
         throw new PersistencyException( "unknown response from server", e );
      } finally
      {
         autoClose();
      }
   }

   @Override
   public void stopContinuousUpdate() throws RemoteException
   {
      if ( inUpdate )
      {
         try
         {
            send( new TCPConnectionModule.NullRequest() ); //todo: make a seperate class?
            readResponse();
         } catch ( IOException e )
         {
            e.printStackTrace(); //todo: only info
            //ok either already stopped or read update data while expecting response
            close();
         } catch ( ClassNotFoundException e )
         {
            throw new PersistencyException( "unknown response from server", e );
         }
      }
   }

   /**
    * Requests a list of {@link VersionInfo} names from the {@link DefaultServer}.
    * 
    * @param fromVersion lower bound of the list: returns all version names that come after the
    *           fromVersion (exclusively). If null the list starts with the name of the first
    *           version.
    * @param toVersion upper bound of the list: returns all version names that come before the
    *           toVersion (exclusively). If null the list ends with the name of the last version.
    * @return a list of version names from the server.
    */
   public List<String> receiveVersionInfos( final String fromVersion, final String toVersion )
   {
      VersionInfoRequest versionInfoRequest = new VersionInfoRequest( fromVersion, toVersion );
      try
      {
         autoOpen();
         send( versionInfoRequest );
         Response response = readResponse();

         if( response instanceof VersionInfoResponse )
         {
            VersionInfoResponse versionInfoResponse = (VersionInfoResponse) response;
            return versionInfoResponse.getVersionInfos();
         }
         else
         {
            throw new PersistencyException( "unknown response from server" );
         }
      }
      catch( IOException ex )
      {
         throw new PersistencyException( ex );
      }
      catch( ClassNotFoundException ex )
      {
         throw new PersistencyException( "unknown response from server", ex );
      }
   }
   
   static class NullRequest extends Request {
      @Override
      protected void check( AbstractClientSession session ) throws Exception {

      }

      @Override
      protected void execute( AbstractClientSession session ) throws Exception {

      }
   }

   private class MyTCPConnection extends TCPConnection
   {
      public MyTCPConnection( String host, int port )
            throws UnknownHostException
      {
         super( host, port );
      }

      public MyTCPConnection( InetAddress address, int port )
      {
         super( address, port );
      }

      protected Socket getSocket()
      {
         return delegate.getSocket();
      }

      protected void setSocket( Socket socket )
      {
         delegate.setSocket( socket );
      }
   }
}

/*
 * $log$
 */


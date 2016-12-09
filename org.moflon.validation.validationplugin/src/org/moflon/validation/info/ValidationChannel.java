package org.moflon.validation.info;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Tobias Raffel
 * @version 1.0 30.3.2013
 */
public class ValidationChannel
{

   private static final int DEFAULT_TIMEOUT_IN_MS = 1000;

   private static final Logger logger = Logger.getLogger(ValidationChannel.class);

   private final static String URL = "localhost";

   public static final int DEFAULT_PORT = 3333;

   private final int port;

   public ValidationChannel(final int port)
   {
      this.port = port;
   }


   /**
    * Sends all messages to 'localhost' with a specified port number. <br>
    * NOTE: Must not be called, before calling Communication.recieve(String messagePattern)<br\>
    * 
    * @param messages
    *           The messages to be send.
    * @throws UnknownHostException
    * @throws IOException
    */
   public void send(final List<String> messages) throws UnknownHostException, IOException
   {
      logger.debug("Sending messages to EA: " + messages);
      Socket echoSocket = new Socket(URL, port);
      echoSocket.setSoTimeout(DEFAULT_TIMEOUT_IN_MS);
      PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), false);
      InputStreamReader in  = new InputStreamReader(echoSocket.getInputStream());
      
      for (String message : messages)
      {
         out.println(message);
         out.flush();
         //wait for a response from ea to avoid timing problems
         if(in.read() != -1) {
            
         }
      }
      out.close();
      echoSocket.close();
   }

   /**
    * Tries to connect to EA via the default port.
    * 
    * Returns true if the connection succeeded, and fails otherwise
    */
   public boolean checkConnection()
   {
      Socket socket = null;
      try
      {
         socket = new Socket(URL, port);
      } catch (IOException e)
      {
         return false;
      } finally
      {
         if (socket != null)
            try
            {
               socket.close();
            } catch (IOException e)
            {
               // Ignore
            }
      }
      return true;
   }
}

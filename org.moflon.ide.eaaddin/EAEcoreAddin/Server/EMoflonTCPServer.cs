using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.IO;
using System.Text.RegularExpressions;
using System.Net;
using System.Windows.Forms;
using System.Threading;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Rules;
using System.ComponentModel;
using EAEcoreAddin.Consistency.PropertyFile.Util;
using EAEcoreAddin.Consistency.PropertyFile;
using System.Net.NetworkInformation;
using EAEcoreAddin.ControlPanel;

namespace EAEcoreAddin.Server
{
    class EMoflonTCPServer
    {
        private SQLRepository sqlRep;
        private TCPServerUserControl serverGui;

        public static readonly int DefaultPort = 6423;

        public int SpecificPort { get; set; }
        private bool serverActive;

        private List<DataReceiveStrategy> dataReceiveStrategies;

        BackgroundWorker serverWorker;

        public EMoflonTCPServer(EA.Repository repository, TCPServerUserControl serverGui)
        {
            this.serverGui = serverGui;

            sqlRep = new SQLRepository(repository, false);
            dataReceiveStrategies = new List<DataReceiveStrategy>();

            computeSpecificPort(repository);
            startServer();
        }




        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            oldServer();
        
        }

        private void HandleClientComm(object client)
        {
            TcpClient tcpClient = (TcpClient)client;
            NetworkStream clientStream = tcpClient.GetStream();

            byte[] message = new byte[4096];

            while (true)
            {
                try
                {
                    int readBytes = clientStream.Read(message, 0, message.Length);
                    if (readBytes != 0)
                    {
                        foreach (DataReceiveStrategy strategy in dataReceiveStrategies)
                        {
                            strategy.dataReveiced(getReadLines(message));
                        }
                        message = new byte[4096];
                    }
                    else
                    {
                        clientStream.WriteByte((byte)0);
                        if (!tcpClient.Connected)
                        {
                            return;
                        }
                    }

                }
                catch (IOException)
                {
                    break;
                }
                catch (InvalidOperationException)
                {
                    break;
                }
            }
            clientStream.Close();
            tcpClient.Close();
        }

        private void oldServer()
        {
            while (serverActive)
            {
                TcpListener listener = new TcpListener(IPAddress.Loopback, SpecificPort);
                serverGui.changePortDesription(SpecificPort);
                listener.Start();

                while (serverActive)
                {
                    TcpClient client = listener.AcceptTcpClient();
                    resetStrategies(client.GetStream());
                    Thread clientThread = new Thread(new ParameterizedThreadStart(HandleClientComm));
                    clientThread.Start(client);

                }
            }
        }


        private void startServer()
        {
            serverWorker = new BackgroundWorker();
            serverWorker.DoWork += new DoWorkEventHandler(worker_DoWork);

            serverWorker.RunWorkerAsync();

            serverActive = true;

        }

        public void stopServer()
        {
            serverActive = false;
        }

        private void resetStrategies(NetworkStream stream)
        {
            dataReceiveStrategies.Clear();
            dataReceiveStrategies.Add(new ValidationReceiveStrategy(stream, sqlRep));
            dataReceiveStrategies.Add(new HighlightObjectReceiveStrategy(stream, sqlRep));

        }


        #region helpers

        private void computeSpecificPort(EA.Repository repository)
        {
            SpecificPort = DefaultPort;
            while (PortInUse(SpecificPort))
            {
                SpecificPort++;
            }
        }

        public static List<String> getReadLines(byte[] buffer)
        {
            String bufferData = EMoflonTCPServer.byteArrayToString(buffer);
            StringReader reader = new StringReader(bufferData);
            String line = "";

            List<String> receivedLines = new List<string>();

            while ((line = reader.ReadLine()) != null)
            {
                if (line != "")
                    receivedLines.Add(line);
            }
            return receivedLines;
        }

        public static byte[] stringToByteArray(string str)
        {
            System.Text.ASCIIEncoding enc = new System.Text.ASCIIEncoding();
            return enc.GetBytes(str);
        }

        public static string byteArrayToString(byte[] arr)
        {
            System.Text.ASCIIEncoding enc = new System.Text.ASCIIEncoding();
            String encoded = enc.GetString(arr);
            String[] spli = Regex.Split(encoded, "\0");
            return spli[0];
        }

        public static bool PortInUse(int port)
        {
            bool inUse = false;

            IPGlobalProperties ipProperties = IPGlobalProperties.GetIPGlobalProperties();
            IPEndPoint[] ipEndPoints = ipProperties.GetActiveTcpListeners();
            foreach (IPEndPoint endPoint in ipEndPoints)
            {
                if (endPoint.Port == port)
                {
                    inUse = true;
                    break;
                }
            }
            return inUse;
        }

        #endregion


    }
}

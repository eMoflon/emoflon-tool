using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using EAEcoreAddin.SQLWrapperClasses;
using System.Net.Sockets;

namespace EAEcoreAddin.Server
{
    class StrategyChooseStrategy :  AbstractDataReceiveStrategy
    {
       
        EMoflonTCPServer tcpServer;
        SQLRepository repository;

        NetworkStream stream;

        public StrategyChooseStrategy(EMoflonTCPServer tcpServer, SQLRepository repository)
        {
            
            this.tcpServer = tcpServer;
            this.repository = repository;
            this.stream = tcpServer.inOutStream;
        }

        public override void dataReveicedEvent()
        {
            List<String> receivedLines = EMoflonTCPServer.getReadLines(getBuffer());

            if (receivedLines.Count > 0)
            {

                if (receivedLines[0] == "ValidationResult")
                {
                    tcpServer.setReceiveDataStrategy(new ValidationReceiveStrategy());
                }
                else if (receivedLines[0] == "SETUP_DEBUG_SESSION")
                {
                    byte[] sendBuffer = EMoflonTCPServer.StringToByteArray(receivedLines[0] + EMoflonTCPServer.NEW_LINE);
                    stream.Write(sendBuffer, 0, sendBuffer.Length);
                    tcpServer.setReceiveDataStrategy(new TracingReceiveStrategy(tcpServer, repository));
                }
            }
        }

        
    }
}

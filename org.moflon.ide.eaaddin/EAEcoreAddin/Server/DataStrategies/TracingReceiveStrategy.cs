using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using EAEcoreAddin.Consistency.PropertyFile;
using EAEcoreAddin.Consistency.PropertyFile.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Server.Tracing;
using System.Windows.Forms;
using EAEcoreAddin.Util;
using System.ComponentModel;
using EAEcoreAddin.Server.DataStrategies;
using System.IO;

namespace EAEcoreAddin.Server
{
    class TracingReceiveStrategy : AbstractDataReceiveStrategy
    {
        private NetworkStream stream;
        private SQLRepository rep;

        public TracingReceiveStrategy(NetworkStream stream, SQLRepository rep)
        {
            this.rep = rep;
            this.stream = stream;
        }

        public override void dataReveiced(List<String> data)
        {
            List<String> receivedLines = data;

            try
            {
                if (receivedLines.Count > 0)
                {
                    if (receivedLines[0] == "SETUP_DEBUG_SESSION")
                    {
                        byte[] sendBuffer = EMoflonTCPServer.stringToByteArray(receivedLines[0] + Environment.NewLine);
                        stream.Write(sendBuffer, 0, sendBuffer.Length);
                        setIsActive(true);
                    }

                    else if (receivedLines[0].StartsWith("TEARDOWN_DEBUG_SESSION"))
                    {
                        byte[] sendBuffer = EMoflonTCPServer.stringToByteArray(receivedLines[0] + Environment.NewLine);
                        stream.Write(sendBuffer, 0, sendBuffer.Length);
                        setIsActive(false);
                    }
                    else if (isActive())
                    {
                        try
                        {
                            PropertyObject obj = ValidationPropertyUtil.computeObjectFromPath(rep, receivedLines[0]);
                            EAUtil.markObjectOnDiagram(obj.EAObject, rep);
                            EAUtil.markObjectInProjectBrowser(obj.EAObject, rep);
                        }
                        catch
                        {
                            setIsActive(false);
                        }
                    }
                }
            }
            catch (IOException)
            {

            }
        }
    }
}

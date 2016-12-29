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

namespace EAEcoreAddin.Server
{
    class TracingReceiveStrategy : AbstractDataReceiveStrategy
    {
        NetworkStream stream;
        EMoflonTCPServer server;
        
        SQLRepository repository;
        TracingDialog trDialog;
        Boolean shouldTrace;

        public BackgroundWorker LoadTracingFileWorker;

        public TracingReceiveStrategy(EMoflonTCPServer server, SQLRepository repository)
        {
            resetBuffer();
            this.repository = repository;
            this.stream = server.inOutStream;
            this.server = server;


            LoadTracingFileWorker = new BackgroundWorker();
            LoadTracingFileWorker.WorkerReportsProgress = true;
            LoadTracingFileWorker.WorkerSupportsCancellation = true;
            LoadTracingFileWorker.DoWork += new DoWorkEventHandler(worker_DoWork);
            LoadTracingFileWorker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
            LoadTracingFileWorker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);

            LoadTracingFileWorker.RunWorkerAsync();
            
        }

        private void resetBuffer()
        {
            buffersize = 65536;
            buffer = new byte[buffersize];
        }

        List<PropertyObject> objects = new List<PropertyObject>();

        public override void dataReveicedEvent()
        {
            List<String> receivedLines = EMoflonTCPServer.getReadLines(buffer);

            if(receivedLines.Count > 0)
            {
                if(receivedLines[0].StartsWith("TEARDOWN_DEBUG_SESSION"))
                {
                    byte[] sendBuffer = EMoflonTCPServer.StringToByteArray(receivedLines[0] + EMoflonTCPServer.NEW_LINE);
                    stream.Write(sendBuffer, 0, sendBuffer.Length);
                    resetBuffer();
                    server.setReceiveDataStrategy(new StrategyChooseStrategy(server, repository));
                }
                else
                {
                    shouldTrace = trDialog.Accepted;
                    foreach (String line in receivedLines)
                        objects.Add(ValidationPropertyUtil.computeObjectFromPath(repository, line));
                    if (shouldTrace)
                    {
                        EAUtil.markObjectOnDiagram(objects[objects.Count - 1].EAObject, repository);
                        EAUtil.markObjectInProjectBrowser(objects[objects.Count - 1].EAObject, repository);
                        resetBuffer();
                        server.setReceiveDataStrategy(this);
                    }
                    else
                    {
                        server.setReceiveDataStrategy(new StrategyChooseStrategy(server, repository));
                    }
                   
                }     
            }
            
        }



        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                trDialog = new TracingDialog();
                trDialog.ShowDialog();               
            }
            catch
            {
                MessageBox.Show("Tracing file seems to be invalid");
            }
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {

        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {

        }

        
    }
}

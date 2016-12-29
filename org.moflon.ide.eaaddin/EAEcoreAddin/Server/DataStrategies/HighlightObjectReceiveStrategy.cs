using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Server.DataStrategies;
using EAEcoreAddin.Consistency.PropertyFile;
using System.Windows.Forms;
using EAEcoreAddin.Consistency.PropertyFile.Util;
using EAEcoreAddin.SQLWrapperClasses;
using System.Net.Sockets;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Server
{
    class HighlightObjectReceiveStrategy : AbstractDataReceiveStrategy
    {

        SQLRepository sqlRep;


        Dictionary<int, PropertyEntry> errorCountToPropertyLine = new Dictionary<int, PropertyEntry>();
        private NetworkStream stream;

        public HighlightObjectReceiveStrategy(NetworkStream stream, SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.stream = stream;
        }
        
        public override void dataReveiced(List<string> receivedLines)
        {
            for (int i = 0; i < receivedLines.Count; i++ )
            {
                String line = receivedLines[i];

                if (line == "SETUP_HIGHLIGHT_OBJECT")
                {
                    setIsActive(true);
                }

                else if (line == "TEARDOWN_HIGHLIGHT_OBJECT")
                {
                    setIsActive(false);
                }

                else if (isActive())
                {
                    try
                    {

                        PropertyObject propertyObject = ValidationPropertyUtil.computeObjectFromPath(sqlRep, line);
                        EAUtil.markObjectInProjectBrowser(propertyObject.EAObject, sqlRep);
                        if (propertyObject.EAObject is SQLDiagram)
                        {
                            SQLDiagram sqlDiagram = propertyObject.EAObject as SQLDiagram;
                            sqlRep.OpenDiagram(sqlDiagram.DiagramID);
                            Main.tcpServerFunctions.addStatusMessage("Displayed TGG Rule diagram: " + line);
                        }
                        else
                        {
                            int onDiagrams = EAUtil.markObjectOnDiagram(propertyObject.EAObject, sqlRep);
                            Main.tcpServerFunctions.addStatusMessage("Displayed object on diagram: " + line);
                        }
                    }
                    catch (NullReferenceException)
                    {
                        //something went wrong while computing the path
                        Main.tcpServerFunctions.addStatusMessage("An error occured while parsing the highlight object path from Eclipse: " + line);
                    }
                }
                    
                
            }
            stream.WriteByte((byte) 10);
        }

        

    }
}

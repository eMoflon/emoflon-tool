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

namespace EAEcoreAddin.Server
{
    class ValidationReceiveStrategy : AbstractDataReceiveStrategy
    {

        SQLRepository sqlRep;


        Dictionary<int, PropertyEntry> errorCountToPropertyLine = new Dictionary<int, PropertyEntry>();
        private NetworkStream stream;

        public ValidationReceiveStrategy(NetworkStream stream, SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.stream = stream;
        }
        
        public override void dataReveiced(List<string> receivedLines)
        {
            for (int i = 0; i < receivedLines.Count; i++ )
            {
                String line = receivedLines[i];

                if (line == "SETUP_VALIDATION_SESSION")
                {
                    setIsActive(true);
                }

                else if (line == "TEARDOWN_VALIDATION_SESSION")
                {
                    setIsActive(false);

                    foreach (PropertyEntry entry in errorCountToPropertyLine.Values)
                    {
                        Main.consistencyModule.RuleControl.handleRuleResult(Consistency.ConsistencyModule.propertyEntryToRuleResult(entry), sqlRep);
                    }
                    Main.consistencyModule.refreshOutputs(sqlRep);
                }

                else if (isActive())
                {
                    PropertyLine propLine = ValidationPropertyUtil.readPropertyLine(receivedLines[i]);                
                    PropertyEntry propertyEntry = null;

                    if (!errorCountToPropertyLine.ContainsKey(propLine.Count))
                    {
                        propertyEntry = new PropertyEntry();
                        errorCountToPropertyLine.Add(propLine.Count, propertyEntry);
                    }
                    else
                    {
                        propertyEntry = errorCountToPropertyLine[propLine.Count];
                    }
                    if (propLine.Type == "message")
                    {
                        propertyEntry.MessageLine = propLine;
                    }
                    else
                    {
                        propertyEntry.PathLine = propLine;
                        if (propertyEntry.PathLine.Value != "")
                        {
                            try
                            {
                                propertyEntry.MoflonObject = ValidationPropertyUtil.computeObjectFromPath(sqlRep, propertyEntry.PathLine.Value);
                            }
                            catch (NullReferenceException)
                            {
                                //something went wrong while computing the path
                                errorCountToPropertyLine.Remove(propLine.Count);
                                Main.tcpServerFunctions.addStatusMessage("An error occured while parsing the validation results from Eclipse: " + propertyEntry.PathLine.Value);
                            }
                        }
                    }
                }
            }
            stream.WriteByte((byte) 10);
        }

        private void handleValidationResult()
        {

                PropertyEntry propertyEntry = new PropertyEntry();
                PropertyLine propLine = ValidationPropertyUtil.readPropertyLine("");
                if (propLine != null)
                {
                    propertyEntry.MessageLine = propLine;
                    if (propLine.Type == "message")
                    {
                      PropertyLine pathPropLine = ValidationPropertyUtil.readPropertyLine("");
                        propertyEntry.PathLine = pathPropLine;
                    }
                    
                }
            }
          /*  
            }*/
       
        

    }
}

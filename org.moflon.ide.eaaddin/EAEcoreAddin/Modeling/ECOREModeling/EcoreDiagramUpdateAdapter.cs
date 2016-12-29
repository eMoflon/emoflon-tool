using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Refactoring;
using EAEcoreAddin.SQLWrapperClasses;

// See http://www.newtonsoft.com/json
using Newtonsoft.Json;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace EAEcoreAddin
{

    // See EMoflonTCPServer for examples on how to implement a server
    // See http://www.codeplanet.eu/tutorials/csharp/4-tcp-ip-socket-programmierung-in-csharp.html
    class EcoreDiagramUpdateAdapter
    {
        internal static int serverPort = 30011;

        private bool initialConnectionAvailable;

        internal EcoreDiagramUpdateAdapter() {
            checkConnectionToServer();
        }

        private void checkConnectionToServer()
        {
            IPHostEntry ipHostInfo = Dns.GetHostEntry("localhost");
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint ipe = new IPEndPoint(ipAddress, serverPort);
            try
            {
                Socket socket = new Socket(ipe.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
                socket.Connect(ipe);
                initialConnectionAvailable = true;
            }
            catch (Exception)
            {
                initialConnectionAvailable = false;
            }
        }
        
        internal bool EA_OnPostNewElement(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Element element = Repository.GetElementByID(int.Parse((string)Info.Get(0).Value));
            CreateElementChange change = new CreateElementChange();
            change.elementType = ElementType.ELEMENT;
            change.internalID = element.ElementGUID;
            change.name = element.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info);
        }

        internal bool EA_OnPostNewPackage(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Package package = Repository.GetPackageByID(int.Parse((string)Info.Get(0).Value));
            CreateElementChange change = new CreateElementChange();
            change.elementType = ElementType.PACKAGE;
            change.internalID = package.PackageGUID;
            change.name = package.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }
        
        internal bool EA_OnPostNewMethod(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Method method = Repository.GetMethodByID(int.Parse((string)Info.Get(0).Value));
            CreateElementChange change = new CreateElementChange();
            change.elementType = ElementType.METHOD;
            change.internalID = method.MethodGUID;
            change.name = method.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }

        internal bool EA_OnPostNewAttribute(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Attribute attribute = Repository.GetAttributeByID(int.Parse((string)Info.Get(0).Value));
            CreateElementChange change = new CreateElementChange();
            change.elementType = ElementType.PACKAGE;
            change.internalID = attribute.AttributeGUID;
            change.name = attribute.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }

        internal bool EA_OnPostNewConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Connector connector = Repository.GetConnectorByID(int.Parse((string)Info.Get(0).Value));
            CreateElementChange change = new CreateElementChange();
            change.elementType = ElementType.CONNECTOR;
            change.internalID = connector.ConnectorGUID;
            change.name = connector.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }


        internal bool EA_OnPreDeletePackage(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Package package = Repository.GetPackageByID(int.Parse((string)Info.Get(0).Value));
            DeleteElementChange change = new DeleteElementChange();
            change.elementType = ElementType.PACKAGE;
            change.internalID = package.PackageGUID;
            change.name = package.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info);
        }

        internal bool EA_OnPreDeleteElement(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Element element = Repository.GetElementByID(int.Parse((string)Info.Get(0).Value));
            DeleteElementChange change = new DeleteElementChange();
            change.elementType = ElementType.ELEMENT;
            change.internalID = element.ElementGUID;
            change.name = element.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info);
        }

        internal bool EA_OnPreDeleteConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Connector connector = Repository.GetConnectorByID(int.Parse((string)Info.Get(0).Value));
            DeleteElementChange change = new DeleteElementChange();
            change.elementType = ElementType.CONNECTOR;
            change.internalID = connector.ConnectorGUID;
            change.name = connector.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }

        internal bool EA_OnPreDeleteMethod(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Method method = Repository.GetMethodByID(int.Parse((string)Info.Get(0).Value));
            DeleteElementChange change = new DeleteElementChange();
            change.elementType = ElementType.METHOD;
            change.internalID = method.MethodGUID;
            change.name = method.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info);
        }

        internal bool EA_OnPreDeleteAttribute(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!initialConnectionAvailable)
                return true;

            EA.Attribute attribute = Repository.GetAttributeByID(int.Parse((string)Info.Get(0).Value));
            DeleteElementChange change = new DeleteElementChange();
            change.elementType = ElementType.PACKAGE;
            change.internalID = attribute.AttributeGUID;
            change.name = attribute.Name;
            return sendObject(change);
            //return sendEvent(Repository, Info); 
        }



        internal void EA_OnNotifyContextItemModified(EA.Repository Repository, string GUID, EA.ObjectType ot)
        {
            if (!initialConnectionAvailable)
                return;

            //EA_OnNotifyContextItemModified notifies Add-Ins that the current context item has been modified.
            //This event occurs when a user has modified the context item. Add-Ins that require knowledge of when 
            // an item has been modified can subscribe to this broadcast function.
            // See also: http://www.sparxsystems.com/enterprise_architect_user_guide/9.3/automation/ea_onnotifycontextitemmodified.html

            ModificationChange change = new ModificationChange();
            change.internalID = GUID;
            
            switch (ot)
            {
                case EA.ObjectType.otPackage:
                     EA.Package eaPackage = Repository.GetPackageByGuid(GUID);
                    change.elementType = ElementType.PACKAGE;
                    change.name = eaPackage.Name;
                    break;
                case EA.ObjectType.otElement:
                    EA.Element eaElement = Repository.GetElementByGuid(GUID);
                    change.elementType = ElementType.ELEMENT;
                    change.name = eaElement.Name;
                    break;
                case EA.ObjectType.otConnector:
                    EA.Connector eaConnector = Repository.GetConnectorByGuid(GUID);
                    change.elementType = ElementType.CONNECTOR;
                    change.name = eaConnector.Name;
                    break;
                case EA.ObjectType.otAttribute:
                    EA.Attribute method = Repository.GetAttributeByGuid(GUID);
                    change.elementType = ElementType.ATTRIBUTE;
                    change.name = method.Name;
                    break;
                case EA.ObjectType.otMethod:
                    EA.Method attribute = Repository.GetMethodByGuid(GUID);
                    change.elementType = ElementType.METHOD;
                    change.name = attribute.Name;
                    break;
            }

            sendObject(change);
        }
        
        private Boolean sendEvent(EA.Repository Repository, EA.EventProperties Info)
        {
            string str = JsonConvert.SerializeObject(Info);
            sendString(str);
            return true;
        }

        private Boolean sendObject(Object obj)
        {
            string str = JsonConvert.SerializeObject(obj);
            sendString(str);
            return true;
        }
        
        private Boolean sendString(string message)
        {
            IPHostEntry ipHostInfo = Dns.GetHostEntry("localhost");
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint ipe = new IPEndPoint(ipAddress, serverPort);
            Socket socket = new Socket(ipe.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            try
            {
                socket.Connect(ipe);
                if (socket.Connected)
                {
                    Byte[] buffer = Encoding.ASCII.GetBytes(message);
                    socket.Send(buffer);
                }
            }
            catch (ArgumentNullException ae)
            {
                Console.WriteLine("ArgumentNullException : {0}", ae.ToString());
            }
            catch (SocketException se)
            {
                Console.WriteLine("SocketException : {0}", se.ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
            finally
            {
                socket.Close();
            }

            return true;
        }

        internal void EA_OnContextItemChanged(EA.Repository Repository, string GUID, EA.ObjectType ot)
        {
            // NOP

            //EA_OnContextItemChanged notifies Add-Ins that a different item is now in context.
            //This event occurs after a user has selected an item anywhere in the Enterprise Architect GUI. 
            // Add-Ins that require knowledge of the current item in context can subscribe to this broadcast function. 
            // If ot = otRepository, then this function behaves the same as EA_FileOpen.
            // See also: http://www.sparxsystems.com/enterprise_architect_user_guide/9.2/automation/ea_oncontextitemchanged.html
        }



    }
}

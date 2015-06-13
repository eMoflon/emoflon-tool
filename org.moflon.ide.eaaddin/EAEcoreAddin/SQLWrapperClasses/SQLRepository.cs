using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Collections;
using System.Collections.Specialized;
using System.Runtime.InteropServices;
using System.Diagnostics;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Threading;
using System.ComponentModel;
using System.Windows.Forms;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     * Facilitates access to the EA repository
     */
    public class SQLRepository
    {

        public readonly EA.Repository eaRepository;
        public BackgroundWorker worker { get; set; }
        public ExtractingDatabaseProgessBar extractDatabaseProgressBar { get; set; }
        public Boolean FullDatabaseCheckout { get; set; }
        public bool ShowStatusBar { get; set; }
        private AutoResetEvent resetEvent = new AutoResetEvent(false);

        #region tables for database checkout data

        public Dictionary<string, string> t_objectGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_objectObjectID = new Dictionary<string, string>();
        public NameValueCollection t_objectPackageID = new NameValueCollection();
        public NameValueCollection t_objectParentID = new NameValueCollection();
        public NameValueCollection t_objectPDATA1 = new NameValueCollection();
        public NameValueCollection t_objectObjectType = new NameValueCollection();


        public Dictionary<string, string> t_connectorGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_connectorConnectorID = new Dictionary<string, string>();
        public NameValueCollection t_connectorStart_Object_ID = new NameValueCollection();
        public NameValueCollection t_connectorEnd_Object_ID = new NameValueCollection();

        public NameValueCollection t_connectortagElementID = new NameValueCollection();

        public Dictionary<string, string> t_attributeGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_attributeAttributeID = new Dictionary<string, string>();
        public NameValueCollection t_attributeParentID = new NameValueCollection();

        public Dictionary<string, string> t_operationGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_operationOperationID = new Dictionary<string, string>();
        public NameValueCollection t_operationParentID = new NameValueCollection();

        public NameValueCollection t_operationparamsOperationID = new NameValueCollection();
        public Dictionary<string, string> t_operationParamsGUID = new Dictionary<string, string>();


        public NameValueCollection t_objectpropertiesObject_ID = new NameValueCollection();

        public Dictionary<string, string> t_packageGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_packagePackageID = new Dictionary<string, string>();
        public NameValueCollection t_packageParentID = new NameValueCollection();

        public NameValueCollection t_diagramParentID = new NameValueCollection();
        public NameValueCollection t_diagramPackageID = new NameValueCollection();
        public Dictionary<string, string> t_diagramGUID = new Dictionary<string, string>();
        public Dictionary<string, string> t_diagramID = new Dictionary<string, string>();

        public NameValueCollection t_xrefElementGUID = new NameValueCollection();

        public Dictionary<int, SQLElement> elementIdToEAElement = new Dictionary<int, SQLElement>();
        public Dictionary<string, SQLElement> elementGuidToEAElement = new Dictionary<string, SQLElement>();
        public Dictionary<int, SQLConnector> connectorIdToEAConnector = new Dictionary<int, SQLConnector>();
        public Dictionary<string, SQLConnector> connectorGuidToEAConnector = new Dictionary<string, SQLConnector>();
        public Dictionary<int, SQLPackage> packageIDToEAPackage = new Dictionary<int, SQLPackage>();
        public Dictionary<string, SQLPackage> packageGuidToEAPackage = new Dictionary<string, SQLPackage>();

        
        #endregion


        public SQLRepository(EA.Repository repository, Boolean fullDatabaseCheckout, Boolean showStatusBar)
        {
            this.eaRepository = repository;
            this.FullDatabaseCheckout = fullDatabaseCheckout;
            this.ShowStatusBar = showStatusBar;

            if (FullDatabaseCheckout)
            {
                worker = new BackgroundWorker();
                worker.WorkerReportsProgress = true;

                if (ShowStatusBar)
                {
                    this.extractDatabaseProgressBar = new ExtractingDatabaseProgessBar(10);
                    worker.DoWork += new DoWorkEventHandler(worker_DoWork);
                    worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
                    worker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);
                    worker.RunWorkerAsync();
                    
                    this.extractDatabaseProgressBar.ShowDialog();
                }
                else
                {
                    extractData();
                }
                

                
            }
        }

        public SQLRepository(EA.Repository repository, Boolean fullDatabaseCheckout) : this(repository, fullDatabaseCheckout, true) 
        {
           
        }
        
        #region backgroundworker methods

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            extractData();
        }

        private void extractData()
        {
            worker.ReportProgress(0);

            worker.ReportProgress(1);
            extract_t_object();

            worker.ReportProgress(2);
            extract_t_connector();

            worker.ReportProgress(3);
            extract_t_connectortag();

            worker.ReportProgress(4);
            extract_t_operation();

            worker.ReportProgress(5);
            extract_t_operationparams();

            worker.ReportProgress(6);
            extract_t_objectproperties();

            worker.ReportProgress(7);
            extract_t_attribute();

            worker.ReportProgress(8);
            extract_t_package();

            worker.ReportProgress(9);
            extract_t_diagram();

            worker.ReportProgress(10);
            extract_t_xref();
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            this.extractDatabaseProgressBar.performStep(e.ProgressPercentage);    
        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            this.extractDatabaseProgressBar.Close();
            resetEvent.Set();
        }

        #endregion 

        #region Database Checkout Methods

        public void extract_t_object()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_object");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String guid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                string objectID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Object_ID")[0];
                string parentID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ParentID")[0];
                string packageID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Package_ID")[0];
                String pdata1 = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "PDATA1")[0];
                String objectType = EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Object_Type")[0];
                t_objectGUID.Add(guid, actRow);
                t_objectObjectID.Add(objectID, actRow);
                t_objectParentID.Add(parentID.ToString(), actRow);
                t_objectPackageID.Add(packageID.ToString(), actRow);
                t_objectPDATA1.Add(pdata1, actRow);
                t_objectObjectType.Add(objectType, actRow);

                elementIdToEAElement.Add(int.Parse(objectID), new SQLElement(this, actRow));
                elementGuidToEAElement.Add(guid, new SQLElement(this, actRow));

            }
        }

        public void extract_t_connector()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_connector");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String guid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                string objectID = (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Connector_ID")[0]);
                String startObjectID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Start_Object_ID")[0];
                String endObjectID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "End_Object_ID")[0];
                t_connectorConnectorID.Add(objectID, actRow);
                t_connectorGUID.Add(guid, actRow);
                t_connectorStart_Object_ID.Add(startObjectID, actRow);
                t_connectorEnd_Object_ID.Add(endObjectID, actRow);
                connectorIdToEAConnector.Add(int.Parse(objectID), new SQLConnector(this, actRow));
                connectorGuidToEAConnector.Add(guid, new SQLConnector(this, actRow));

            }
        }

        public void extract_t_xref()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_xref");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String elementGUID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Client")[0];
                t_xrefElementGUID.Add(elementGUID, actRow);
            }
        }

        public void extract_t_operation()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_operation");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");
            foreach (String actRow in rows)
            {
                String guid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                string objectID = (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "OperationID")[0]);
                String parentID = (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Object_ID")[0]);

                t_operationGUID.Add(guid, actRow);
                t_operationOperationID.Add(objectID, actRow);
                t_operationParentID.Add(parentID.ToString(), actRow);
            }
        }

        public void extract_t_operationparams()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_operationparams");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String operationID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "OperationID")[0];
                String paramGUID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                t_operationparamsOperationID.Add(operationID, actRow);
                t_operationParamsGUID.Add(paramGUID, actRow);
            }
        }

        public void extract_t_attribute()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_attribute");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {

                string guid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                string attributeID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ID")[0];
                string parentID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Object_ID")[0];
                t_attributeGUID.Add(guid, actRow);
                t_attributeAttributeID.Add(attributeID, actRow);
                t_attributeParentID.Add(parentID, actRow);

            }
        }

        public void extract_t_objectproperties()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_objectproperties");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String objectID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Object_ID")[0];
                t_objectpropertiesObject_ID.Add(objectID, actRow);
            }
        }

        public void extract_t_package()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_package");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String pdata1 = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "PDATA1")[0];
                String guid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                String objectID = (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Package_ID")[0]);
                String parentID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Parent_ID")[0];

                t_packageParentID.Add(parentID, actRow);
                t_packagePackageID.Add(objectID, actRow);
                t_packageGUID.Add(guid, actRow);
                packageGuidToEAPackage.Add(guid, new SQLPackage(this, actRow));
                packageIDToEAPackage.Add(int.Parse(objectID), new SQLPackage(this, actRow));
            }
        }

        public void extract_t_connectortag()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_connectortag");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String elementID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ElementID")[0];
                t_connectortagElementID.Add(elementID, actRow);
            }
        }

        public void extract_t_diagram()
        {
            String sqlqry = eaRepository.SQLQuery("select * from t_diagram");
            List<String> rows = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");

            foreach (String actRow in rows)
            {
                String parentID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ParentID")[0];
                String packageID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Package_ID")[0];
                String diagramGUID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "ea_guid")[0];
                String diagramID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(actRow, "Diagram_ID")[0];
                t_diagramParentID.Add(parentID, actRow);
                t_diagramPackageID.Add(packageID, actRow);
                t_diagramGUID.Add(diagramGUID, actRow);
                t_diagramID.Add(diagramID, actRow);
            }
        }

        #endregion

        #region IDualRepository Members

        public void ActivateDiagram(int DiagramID)
        {
            eaRepository.ActivateDiagram(DiagramID);
        }

        public bool ActivatePerspective(string Perspective, int Options)
        {
            return eaRepository.ActivatePerspective(Perspective, Options);
        }

        public void ActivateTab(string Name)
        {
            eaRepository.ActivateTab(Name);
        }

        public bool ActivateTechnology(string ID)
        {
            return eaRepository.ActivateTechnology(ID);
        }

        public bool ActivateToolbox(string Toolbox, int Options)
        {
            return eaRepository.ActivateToolbox(Toolbox, Options);
        }

        public bool AddDefinedSearches(string sXML)
        {
            return eaRepository.AddDefinedSearches(sXML);
        }

        public bool AddPerspective(string Perspective, int Options)
        {
            return eaRepository.AddPerspective(Perspective, Options);
        }

        public object AddTab(string TabName, string ControlID)
        {
            return eaRepository.AddTab(TabName, ControlID);
        }

        public object AddWindow(string TabName, string ControlID)
        {
            return eaRepository.AddWindow(TabName, ControlID);
        }

        public void AdviseConnectorChange(int ConnectorID)
        {
            eaRepository.AdviseConnectorChange(ConnectorID);
        }

        public void AdviseElementChange(int ElementID)
        {
            eaRepository.AdviseElementChange(ElementID);
        }

        public EA.App App
        {
            get
            {
                return eaRepository.App;
            }
        }

        public EA.Collection Authors
        {
            get
            {
                return eaRepository.Authors;
            }
        }

        public bool BatchAppend
        {
            get
            {
                return eaRepository.BatchAppend;
            }
            set
            {
                eaRepository.BatchAppend = value;
            }
        }

        public bool ChangeLoginUser(string Name, string Password)
        {
            return eaRepository.ChangeLoginUser(Name, Password);
        }

        public bool ClearAuditLogs(object StateDateTime, object EndDateTime)
        {
            return eaRepository.ClearAuditLogs(StateDateTime, EndDateTime);
        }

        public void ClearOutput(string Name)
        {
            eaRepository.ClearOutput(Name);
        }

        public EA.Collection Clients
        {
            get
            {
                return eaRepository.Clients;
            }
        }

        public void CloseAddins()
        {
            eaRepository.CloseAddins();
        }

        public void CloseDiagram(int DiagramID)
        {
            eaRepository.CloseDiagram(DiagramID);
        }

        public void CloseFile()
        {
            eaRepository.CloseFile();
        }

        public string ConnectionString
        {
            get
            {
                return eaRepository.ConnectionString;
            }
        }

        public EA.DocumentGenerator CreateDocumentGenerator()
        {
            return eaRepository.CreateDocumentGenerator();
        }

        public bool CreateModel(EA.CreateModelType CreateType, string FilePath, int ParentWnd)
        {
            return eaRepository.CreateModel(CreateType, FilePath, ParentWnd);
        }

        public EA.ModelWatcher CreateModelWatcher()
        {
            return eaRepository.CreateModelWatcher();
        }

        public void CreateOutputTab(string Name)
        {
            eaRepository.CreateOutputTab(Name);
        }

        public string CustomCommand(string ClassName, string MethodName, string Parameters)
        {
            return eaRepository.CustomCommand(ClassName, MethodName, Parameters);
        }

        public EA.Collection Datatypes
        {
            get
            {
                return eaRepository.Datatypes;
            }
        }

        public bool DeletePerspective(string Perspective, int Options)
        {
            return eaRepository.DeletePerspective(Perspective, Options);
        }

        public bool DeleteTechnology(string ID)
        {
            return eaRepository.DeleteTechnology(ID);
        }

        public EA.EAEditionTypes EAEdition
        {
            get
            {
                return eaRepository.EAEdition;
            }
        }

        public EA.EAEditionTypes EAEditionEx
        {
            get
            {
                return eaRepository.EAEditionEx;
            }
        }

        public bool EnableCache
        {
            get
            {
                return eaRepository.EnableCache;
            }
            set
            {
                eaRepository.EnableCache = value;
            }
        }

        public int EnableEventFlags
        {
            get
            {
                return eaRepository.EnableEventFlags;
            }
            set
            {
                eaRepository.EnableEventFlags = value;
            }
        }

        public bool EnableUIUpdates
        {
            get
            {
                return eaRepository.EnableUIUpdates;
            }
            set
            {
                eaRepository.EnableUIUpdates = value;
            }
        }

        public void EnsureOutputVisible(string Name)
        {
            eaRepository.EnsureOutputVisible(Name);
        }

        public void Execute(string SQL)
        {
            eaRepository.Execute(SQL);
        }

        public void ExecutePackageBuildScript(int ScriptOptions, string PackageGUID)
        {
            eaRepository.ExecutePackageBuildScript(ScriptOptions, PackageGUID);
        }

        public void Exit()
        {
            eaRepository.Exit();
        }

        public bool FlagUpdate
        {
            get
            {
                return eaRepository.FlagUpdate;
            }
            set
            {
                eaRepository.FlagUpdate = value;
            }
        }

        public string GetActivePerspective()
        {
            return eaRepository.GetActivePerspective();
        }

        public SQLAttribute GetAttributeByGuid(string GUID)
        {
            String connectorPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_attributeGUID.ContainsKey(GUID))
                    connectorPropertyString = t_attributeGUID[GUID];
            }
            else
            {
                String attributeQry = this.SQLQuery("select * from t_attribute where ea_guid = '" + GUID + "'");
                connectorPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(attributeQry, "Row")[0];
            }
            if (connectorPropertyString != "")
            {
                SQLAttribute foundElement = new SQLAttribute(connectorPropertyString, this);
                return foundElement;
            }
            else
                return null;

        }

        public SQLAttribute GetAttributeByID(int AttributeID)
        {
            SQLAttribute foundElement = null;
            String attributePropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_attributeAttributeID.ContainsKey(AttributeID.ToString()))
                {
                    attributePropertyString = t_attributeAttributeID[AttributeID.ToString()];
                }
            }
            else
            {
                String attributeQry = this.SQLQuery("select * from t_attribute where ID = " + AttributeID);
                attributePropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(attributeQry, "Row")[0];
            }
            if (attributePropertyString != "")
                foundElement = new SQLAttribute(attributePropertyString, this);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public SQLConnector GetConnectorByGuid(string GUID)
        {
            SQLConnector foundElement = null;
            String connectorPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (connectorGuidToEAConnector.ContainsKey(GUID))
                {
                    return connectorGuidToEAConnector[GUID];
                }
                if (t_connectorGUID.ContainsKey(GUID))
                {
                    connectorPropertyString = t_connectorGUID[GUID];
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_connector where ea_guid = '" + GUID + "'");
                connectorPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }

            if (connectorPropertyString != "")
                foundElement = new SQLConnector(this, connectorPropertyString);
            return foundElement;
        }

        public SQLConnector GetConnectorByID(int ConnectorID)
        {
            SQLConnector foundElement = null;
            String connectorPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (connectorIdToEAConnector.ContainsKey(ConnectorID))
                {
                    return connectorIdToEAConnector[ConnectorID];
                }

                if (t_connectorConnectorID.ContainsKey(ConnectorID.ToString()))
                {
                    connectorPropertyString = t_connectorConnectorID[ConnectorID.ToString()];
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_connector where Connector_ID = " + ConnectorID);
                connectorPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }
            if (connectorPropertyString != "")
                foundElement = new SQLConnector(this, connectorPropertyString);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public EA.ObjectType GetContextItem(out object Item)
        {
            return eaRepository.GetContextItem(out Item);
        }

        public EA.ObjectType GetContextItemType()
        {
            return eaRepository.GetContextItemType();
        }

        public object GetContextObject()
        {
            return eaRepository.GetContextObject();
        }

        public string GetCounts()
        {
            return eaRepository.GetCounts();
        }

        public EA.Diagram GetCurrentDiagram()
        {
            return eaRepository.GetCurrentDiagram();
        }

        public string GetCurrentLoginUser(bool GetGuid)
        {
            return eaRepository.GetCurrentLoginUser(GetGuid);
        }

        public object GetDiagramByGuid(string GUID)
        {
            SQLDiagram foundElement = null;
            String diagramPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_diagramGUID.ContainsKey(GUID))
                {
                    diagramPropertyString = t_diagramGUID[GUID];
                }
            }
            else
            {
                String diagramQry = this.SQLQuery("select * from t_diagram where ea_guid = '" + GUID + "'");
                diagramPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(diagramQry, "Row")[0];
            }
            if (diagramPropertyString != "")
                foundElement = new SQLDiagram(this, diagramPropertyString);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public SQLDiagram GetDiagramByID(int DiagramID)
        {
            SQLDiagram foundElement = null;
            String diagramPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_diagramID.ContainsKey(DiagramID.ToString()))
                {
                    diagramPropertyString = t_diagramID[DiagramID.ToString()];
                }
            }
            else
            {
                String diagramQry = this.SQLQuery("select * from t_diagram where Diagram_ID = " + DiagramID);
                diagramPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(diagramQry, "Row")[0];
            }
            if (diagramPropertyString != "")
                foundElement = new SQLDiagram(this, diagramPropertyString);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public EA.Repository GetOriginalRepository()
        {
            return this.eaRepository;
        }

        public SQLElement GetElementByGuid(string GUID)
        {
            String elementString = "";
            if (FullDatabaseCheckout)
            {
                if (elementGuidToEAElement.ContainsKey(GUID))
                {
                    return elementGuidToEAElement[GUID];
                }

                if (t_objectGUID.ContainsKey(GUID))
                    elementString = t_objectGUID[GUID];
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_object where ea_guid = '" + GUID + "'");
                elementString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }

            if (elementString != "")
            {
                SQLElement element = new SQLElement(this, elementString);
                return element;
            }
            else
                return null;
        }

        [DebuggerHidden]        
        public SQLElement GetElementByID(int ElementID)
        {
            SQLElement element = GetElementByIDNullable(ElementID);

            if (element != null)
                return element;
            else
                throw new COMException("Can't find matching ID");

        }

        [DebuggerHidden]
        public SQLElement GetElementByIDNullable(int ElementID)
        {
            String elementString = "";
            if (FullDatabaseCheckout)
            {
                if (elementIdToEAElement.ContainsKey(ElementID))
                {
                    return elementIdToEAElement[ElementID];
                }

                if (t_objectObjectID.ContainsKey(ElementID.ToString()))
                    elementString = t_objectObjectID[ElementID.ToString()];
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_object where Object_ID = " + ElementID);
                elementString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }
            if (elementString != "")
            {
                SQLElement element = new SQLElement(this, elementString);
                return element;
            }
            else
            {
                return null;
            }
        }

        public EA.Collection GetElementSet(string IDList, int Unused)
        {
            return eaRepository.GetElementSet(IDList, Unused);
        }

        public EA.Collection GetElementsByQuery(string QueryName, string SearchTerm)
        {
            return eaRepository.GetElementsByQuery(QueryName, SearchTerm);
        }

        public List<SQLElement> getElementsByStereotype(string Stereotype)
        {
            List<SQLElement> retrievedElements = new List<SQLElement>();
            String ruleElements = this.SQLQuery("select * from t_object where Stereotype = '" + Stereotype + "'");
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(ruleElements, "Row"))
            {
                if (row != "")
                {
                    retrievedElements.Add(new SQLElement(this, row));
                }
            }
            return retrievedElements;
        }

        public string GetFieldFromFormat(string Format, string Text)
        {
            return eaRepository.GetFieldFromFormat(Format, Text);
        }

        public string GetFormatFromField(string Format, string Text)
        {
            return eaRepository.GetFormatFromField(Format, Text);
        }

        public string GetLastError()
        {
            return eaRepository.GetLastError();
        }


        public SQLMethod GetMethodByGuid(string GUID)
        {
            SQLMethod foundElement = null;
            String methodPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_operationGUID.ContainsKey(GUID))
                {
                    methodPropertyString = t_operationGUID[GUID];
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_operation where ea_guid = '" + GUID + "'");
                methodPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }

            if (methodPropertyString != "")
                foundElement = new SQLMethod(methodPropertyString, this);
            return foundElement;
        }

        public SQLMethod GetMethodByID(int MethodID)
        {
            SQLMethod foundElement = null;
            String methodPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_operationOperationID.ContainsKey(MethodID.ToString()))
                {
                    methodPropertyString = t_operationOperationID[MethodID.ToString()].ToString();
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_operation where OperationID = " + MethodID);
                methodPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }

            if (methodPropertyString != "")
                foundElement = new SQLMethod(methodPropertyString, this);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public SQLPackage GetPackageByGuid(string GUID)
        {
            SQLPackage foundElement = null;
            String packagePropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (packageGuidToEAPackage.ContainsKey(GUID))
                {
                    return packageGuidToEAPackage[GUID];
                }

                if (t_packageGUID.ContainsKey(GUID))
                {
                    packagePropertyString = t_packageGUID[GUID];
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_package where ea_guid = '" + GUID + "'");
                packagePropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }
            if (packagePropertyString != "")
                foundElement = new SQLPackage(this, packagePropertyString);
            return foundElement;
        }

        public SQLParameter GetParameterByGuid(string GUID)
        {
            SQLParameter foundElement = null;
            String parameterPropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (t_operationParamsGUID.ContainsKey(GUID))
                {
                    parameterPropertyString = t_operationParamsGUID[GUID];
                }
            }
            else
            {
                String parameterQry = this.SQLQuery("select * from t_operationparams where ea_guid = '" + GUID + "'");
                parameterPropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(parameterQry, "Row")[0];
            }
            if (parameterPropertyString != "")
                foundElement = new SQLParameter(this, parameterPropertyString);
            return foundElement;
        }

        [DebuggerHidden]
        public SQLPackage GetPackageByID(int PackageID)
        {
            SQLPackage foundElement = null;
            String packagePropertyString = "";
            if (FullDatabaseCheckout)
            {
                if (packageIDToEAPackage.ContainsKey(PackageID))
                {
                    return packageIDToEAPackage[PackageID];
                }

                if (t_packagePackageID.ContainsKey(PackageID.ToString()))
                {
                    packagePropertyString = t_packagePackageID[PackageID.ToString()];
                }
            }
            else
            {
                String connectorQry = this.SQLQuery("select * from t_package where Package_ID = " + PackageID);
                packagePropertyString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(connectorQry, "Row")[0];
            }

            if (packagePropertyString != "")
                foundElement = new SQLPackage(this, packagePropertyString);
            if (foundElement == null)
                throw new COMException("Can't find matching ID");
            return foundElement;
        }

        public EA.Project GetProjectInterface()
        {
            return eaRepository.GetProjectInterface();
        }

        public EA.Reference GetReferenceList(string ListName)
        {
            return eaRepository.GetReferenceList(ListName);
        }

        public string GetTechnologyVersion(string ID)
        {
            return eaRepository.GetTechnologyVersion(ID);
        }

        public EA.Collection GetTreeSelectedElements()
        {
            return eaRepository.GetTreeSelectedElements();
        }

        public EA.ObjectType GetTreeSelectedItem(out object Item)
        {
            return eaRepository.GetTreeSelectedItem(out Item);
        }

        public EA.ObjectType GetTreeSelectedItemType()
        {
            return eaRepository.GetTreeSelectedItemType();
        }

        public object GetTreeSelectedObject()
        {
            return eaRepository.GetTreeSelectedObject();
        }

        public SQLPackage GetTreeSelectedPackage()
        {
            return this.GetPackageByID(eaRepository.GetTreeSelectedPackage().PackageID);
        }

        public string HasPerspective(string Perspective)
        {
            return eaRepository.HasPerspective(Perspective);
        }

        public void ImportPackageBuildScripts(string PackageGUID, string BuildScriptXML)
        {
            eaRepository.ImportPackageBuildScripts(PackageGUID, BuildScriptXML);
        }

        public bool ImportTechnology(string Technology)
        {
            return eaRepository.ImportTechnology(Technology);
        }

        public string InstanceGUID
        {
            get
            {
                return eaRepository.InstanceGUID;
            }
        }

        public int InvokeConstructPicker(object ConstructType)
        {
            return eaRepository.InvokeConstructPicker(ConstructType);
        }

        public string InvokeFileDialog(object FilterString, int DefaultFilterIndex, int Flags)
        {
            return eaRepository.InvokeFileDialog(FilterString, DefaultFilterIndex, Flags);
        }

        public bool IsSecurityEnabled
        {
            get
            {
                return eaRepository.IsSecurityEnabled;
            }
        }

        public int IsTabOpen(string TabName)
        {
            return eaRepository.IsTabOpen(TabName);
        }

        public bool IsTechnologyEnabled(string ID)
        {
            return eaRepository.IsTechnologyEnabled(ID);
        }

        public bool IsTechnologyLoaded(string ID)
        {
            return eaRepository.IsTechnologyLoaded(ID);
        }

        public EA.Collection Issues
        {
            get
            {
                return eaRepository.Issues;
            }
        }

        public string LastUpdate
        {
            get
            {
                return eaRepository.LastUpdate;
            }
        }

        SQLCollection<SQLPackage> models;
        public SQLCollection<SQLPackage> Models
        {
            get
            {
                if (models != null)
                {
                    return models;
                }
                else
                {
                    models = new SQLCollection<SQLPackage>();
                    String[] mdls = null;
                    if (FullDatabaseCheckout)
                    {
                        mdls = t_packageParentID.GetValues("0");
                    }
                    else
                    {
                        String modelQry = this.SQLQuery("select * from t_package where Parent_ID = 0");
                        mdls = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(modelQry, "Row").ToArray();
                    }
                    if (mdls == null)
                        return models;

                    foreach (String model in mdls)
                    {
                        if (model != "")
                        {
                            SQLPackage childPackage = new SQLPackage(this, model);
                            models.AddNew(childPackage);

                        }
                    }
                    return models;
                }
            }
        }

        public EA.ObjectType ObjectType
        {
            get
            {
                return eaRepository.ObjectType;
            }
        }

        public void OpenDiagram(int DiagramID)
        {
            eaRepository.OpenDiagram(DiagramID);
        }

        public bool OpenFile(string FilePath)
        {
            return eaRepository.OpenFile(FilePath);
        }

        public bool OpenFile2(string FilePath, string Username, string Password)
        {
            return eaRepository.OpenFile2(FilePath, Username, Password);
        }

        public string ProjectGUID
        {
            get { return eaRepository.ProjectGUID; }
        }

        public EA.Collection ProjectRoles()
        {
            return eaRepository.ProjectRoles();
        }

        public EA.Collection PropertyTypes
        {
            get { return eaRepository.PropertyTypes; }
        }

        public void RefreshModelView(int PackageID)
        {
            eaRepository.RefreshModelView(PackageID);
        }

        public void RefreshOpenDiagrams(bool FullReload)
        {
            eaRepository.RefreshOpenDiagrams(FullReload);
        }

        public void ReloadDiagram(int DiagramID)
        {
            eaRepository.ReloadDiagram(DiagramID);
        }

        public void RemoveOutputTab(string Name)
        {
            eaRepository.RemoveOutputTab(Name);
        }

        public void RemoveTab(string Name)
        {
            eaRepository.RemoveTab(Name);
        }

        public EA.Collection Resources
        {
            get { return eaRepository.Resources; }
        }

        public void RunModelSearch(string QueryName, string SearchTerm, string SearchOptions, string SearchData)
        {
            eaRepository.RunModelSearch(QueryName, SearchTerm, SearchOptions, SearchData);
        }

        public string SQLQuery(string SQL)
        {
            return eaRepository.SQLQuery(SQL);
        }

        public void SaveAllDiagrams()
        {
            eaRepository.SaveAllDiagrams();
        }

        public bool SaveAuditLogs(string FilePath, object StateDateTime, object EndDateTime)
        {
            return eaRepository.SaveAuditLogs(FilePath, StateDateTime, EndDateTime);
        }

        public void SaveDiagram(int DiagramID)
        {
            eaRepository.SaveDiagram(DiagramID);
        }

        public bool ScanXMIAndReconcile()
        {
            return eaRepository.ScanXMIAndReconcile();
        }

        public void ShowBrowser(string TabName, string URL)
        {
            eaRepository.ShowBrowser(TabName, URL);
        }

        public void ShowDynamicHelp(string Topic)
        {
            eaRepository.ShowDynamicHelp(Topic);
        }

        public void ShowInProjectView(object Object)
        {
            eaRepository.ShowInProjectView(Object);
        }

        public void ShowProfileToolbox(string Technology, string Profile, bool Show)
        {
            eaRepository.ShowProfileToolbox(Technology, Profile, Show);
        }

        public void ShowWindow(int Show)
        {
            eaRepository.ShowWindow(Show);
        }

        public EA.Collection Stereotypes
        {
            get { return eaRepository.Stereotypes; }
        }

        public bool SuppressEADialogs
        {
            get
            {
                return eaRepository.SuppressEADialogs;
            }
            set
            {
                eaRepository.SuppressEADialogs = value;
            }
        }

        public bool SuppressSecurityDialog
        {
            get
            {
                return SuppressSecurityDialog;
            }
            set
            {
                eaRepository.SuppressSecurityDialog = value;
            }
        }

        public bool SynchProfile(object Profile, object Stereotype)
        {
            return eaRepository.SynchProfile(Profile, Stereotype);
        }

        public EA.Collection Tasks
        {
            get { return eaRepository.Tasks; }
        }

        public EA.Collection Terms
        {
            get
            {
                return eaRepository.Terms;
            }
        }
        #endregion

    }
}

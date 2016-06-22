using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.Reflection;
using System.IO;
using System.Linq;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency;
using EAEcoreAddin.Consistency.RuleHandling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Persistency;
using EAEcoreAddin.Persistency.Util;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.ControlPanel;
using EAEcoreAddin.Consistency.PropertyFile;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Util;
using EAEcoreAddin.Consistency.PropertyFile.Util;
using EAEcoreAddin.Server;
using EAEcoreAddin.ControlPanel.Navigation;
using EAEcoreAddin.Modeling.Util;
using EAEcoreAddin.ControlPanel.Global;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin
{
    public class Main
    {
        #region internal objects
        private List<EA.ObjectType> deletedDiagramObjectsObjectTypesQueue = new List<EA.ObjectType>();
        private List<String> deletedDiagramObjectGUIDQueue = new List<string>();
        private static Dictionary<String, MocaTaggableElement> SaveTreeQueue = new Dictionary<string, MocaTaggableElement>();
        private static List<String> DeleteQueue = new List<string>();
        #endregion

        #region eMoflon references
        private ECOREModelingMain ecoreModeling;
        private SDMModelingMain sdmModeling;
        private TGGModelingMain tggModeling;
        private EMoflonTCPServer propertyServer;
        public static EAPUpdater eapUpdater;
        public static ConsistencyModule consistencyModule;
        public static GlobalFunctions globalFunctions;
        public static TGGFunctions tggFunctions;
        public static SDMFunctions sdmFunctions;
        public static NewErrorOutput mainErrorOutput;
        public static TCPServerUserControl tcpServerFunctions;
        #endregion

        #region EA string values
        public static readonly String EAObjectType = "Object";
        public static readonly String EAClassType = "Class";
        public static readonly String EAActivityType = "Activity";
        public static readonly String GuidStringName = "guid";
        public static readonly String EAStateNodeType = "StateNode";
        public static readonly int EAStartNodeSubtype = 100;
        public static readonly int EAStopNodeSubtype = 101;
        public static readonly String EASourceTargetDirection = "Source -> Destination";
        public static readonly String EATargetSourceDirection = "Destination -> Source";
        public static readonly String EABidirectionalDirection = "Bi-Directional";
        public static readonly String EAControlFlowType = "ControlFlow";
        public static readonly String EADependencyType = "Dependency";
        public static readonly String EAGeneralizationConType = "Generalization";
        public static readonly String EAAssociationType = "Association";
        public static readonly String EAAssociationType_composite = "Aggregation";
        public static readonly String TrueValue = "true";
        public static readonly String FalseValue = "false";
        #endregion

        #region broadcast method id definitions
        private static int EALastBroadcastMethodId = -1;
        public static readonly int EAOnPostNewElement = 1;
        public static readonly int EAOnContextItemModified = 2;
        public static readonly int EAOnContextItemDoubleClicked = 3;
        #endregion

        #region eMoflon taggedvalue names
        public static readonly String MoflonVerboseTaggedValueName = "Moflon::Verbose";
        public static readonly String MoflonExtractedStoryPatternTaggedValueName = "Moflon::ExtractedStoryPattern";
        public static readonly String MoflonExportTreeTaggedValueName = "Moflon::ExportTree";
        public static readonly String MoflonChangesTreeTaggedValueName = "Moflon::ChangesTree";

        public static readonly String MoflonValidationFilterTaggedValueName = "Moflon::ValidationFilter";
        #endregion


        public Main()
        {
            sdmModeling = new SDMModelingMain();
            ecoreModeling = new ECOREModelingMain();

            tggModeling = new TGGModelingMain(ecoreModeling);

            incrementalUpdateAdapter = new EcoreDiagramUpdateAdapter();
        }
        
        #region Manage Addin

        /// <summary>
        /// Called Before EA starts to check Add-In exists
        /// </summary>
        /// <param name="Repository"></param>
        /// <returns></returns>
        public String EA_Connect(EA.Repository Repository)
        {
            //No special processing required.
            return "EAEcoreAddin";
        }

        public static Assembly StaticAssembly;

        /// <summary>
        /// Called afterwards to avoid hangups (Release References to EA Objects)
        /// </summary>
        public void EA_Disconnect()
        {
            GC.Collect();
            try
            {
                GC.WaitForPendingFinalizers();
            }
            catch
            {
            }
        }

        /// <summary>
        /// Called when user Click Add-Ins Menu item from within EA.
        /// Populates the Menu with our desired selections.
        /// </summary>
        /// <param name="Repository"></param>
        /// <param name="Location"></param>
        /// <param name="MenuName"></param>
        /// <returns></returns>
        public object EA_GetMenuItems(EA.Repository Repository, string Location, string MenuName)
        {
            switch (MenuName)
            {
                case "":
                    return "-&eMoflon::Ecore Addin";
                case "-&eMoflon::Ecore Addin":
                    ArrayList ar = new ArrayList();

                    ar.Add("&About....");                   
                    if (ar.Count == 0)
                    {
                        return null;
                    }
                    else
                        return ar.ToArray(typeof(string));
                default:
                    ArrayList ar_default = new ArrayList();
                    return ar_default.ToArray(typeof(string));
            }
        }



        /// <summary>
        /// Sets the state of the menu depending if there is an active project or not
        /// </summary>
        /// <param name="Repository"></param>
        /// <returns></returns>
        bool IsProjectOpen(EA.Repository Repository)
        {
            try
            {
                EA.Collection c = Repository.Models;
                return true;
            }
            catch
            {
                return false;
            }
        }

        /// <summary>
        /// Called once Menu has been opened to see what menu items are active.
        /// </summary>
        /// <param name="Repository"></param>
        /// <param name="Location"></param>
        /// <param name="MenuName"></param>
        /// <param name="ItemName"></param>
        /// <param name="IsEnabled"></param>
        /// <param name="IsChecked"></param>
        public void EA_GetMenuState(EA.Repository Repository, string Location, string MenuName, string ItemName, ref bool IsEnabled, ref bool IsChecked)
        {
            if (IsProjectOpen(Repository))
            {
                IsEnabled = true;
            }
            else
                // If no open project, disable all menu options
                IsEnabled = false;
        }


        /// <summary>
        /// Called when user makes a selection in the menu.
        /// This is your main exit point to the rest of your Add-in
        /// </summary>
        /// <param name="Repository"></param>
        /// <param name="Location"></param>
        /// <param name="MenuName"></param>
        /// <param name="ItemName"></param>
        public void EA_MenuClick(EA.Repository Repository, string Location, string MenuName, string ItemName)
        {
            if(ItemName == "&About....")
            {
                    About aboutBox = new About();
                    aboutBox.ShowDialog();
            }
        }

        public bool EA_OnContextItemDoubleClicked(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {
            EALastBroadcastMethodId = EAOnContextItemDoubleClicked;
            preventCascade = true;

            SQLRepository sqlRepository = new SQLRepository(Repository, false);

            EA.Element elem = Repository.GetElementByGuid(GUID);

            Boolean sdmContextItemDoubleClicked = sdmModeling.EA_OnContextItemDoubleClicked(Repository, GUID, ot);
            Boolean tggContextItemDoubleClicked = tggModeling.EA_OnContextItemDoubleClicked(Repository, GUID, ot);
            Boolean handledByAddin = sdmContextItemDoubleClicked || tggContextItemDoubleClicked;
            //run rulechecks if we handled the double clicked context item
            if (handledByAddin)
            {
                consistencyModule.dispatchSingleObject(Repository, GUID, ot);

                EA.Diagram currentDiagram = Repository.GetCurrentDiagram();
                if(currentDiagram != null)
                    Repository.ReloadDiagram(currentDiagram.DiagramID);
                
                preventCascade = false;



                return true;
            }

            //general double click handling
            handledByAddin = handleContextItemDoubleClick(Repository, ot, elem, handledByAddin);

            preventCascade = false;

            return handledByAddin;
        }

        private static Boolean handleContextItemDoubleClick(EA.Repository Repository, EA.ObjectType ot, EA.Element elem, Boolean handledByAddin)
        {
            if (ot == EA.ObjectType.otElement)
            {
                if (elem.Stereotype == SDMModelingMain.SDMAnchorStereotype)
                {
                    EA.Diagram currentDiagram = Repository.GetCurrentDiagram();
                    if (currentDiagram != null)
                    {
                        Navigator.jumpToAnchorPredecessor(Repository, currentDiagram.DiagramID);
                        handledByAddin = true;
                    }
                }
            }
            return handledByAddin;
        }

        public static String lastContextItemGUID;

        public static String currentContextItemGUID;

        public void EA_OnContextItemChanged(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {
            lastContextItemGUID = currentContextItemGUID;
            currentContextItemGUID = GUID;

            //run validation after diagram objects has been deleted
            validateDeletedDiagramObjects(Repository);

            //save all MocaTaggableElements in queue
            if (lastContextItemGUID != currentContextItemGUID)
            {
                saveMocaTaggableElementsInQueue();
            }
            //delete all queued Elements
            deleteElementsInQueue(Repository);

            //ecoreModeling.EA_OnContextItemChanged(Repository, GUID, ot);

            incrementalUpdateAdapter.EA_OnContextItemChanged(Repository, GUID, ot);

        }

        public bool EA_OnPreNewConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            bool connectorAllowedByEcore = true;
            bool connectorAllowedBySDM = sdmModeling.EA_OnPreNewConnector(Repository, Info);
            bool connectorAllowedByTGG = tggModeling.EA_OnPreNewConnector(Repository, Info);
            
            return connectorAllowedByEcore && connectorAllowedBySDM && connectorAllowedByTGG;
        }

        public bool EA_OnPostNewConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            preventCascade = true;
            SQLRepository sqlRepository = new SQLRepository(Repository, false);
            EA.Connector connector = Repository.GetConnectorByID(int.Parse((string)Info.Get(0).Value));
            EA.Diagram currentDiagram = sqlRepository.GetCurrentDiagram();
            sqlRepository.SaveDiagram(currentDiagram.DiagramID);

            bool modifiedBySDMModeling = sdmModeling.EA_OnPostNewConnector(sqlRepository, connector, currentDiagram);
            bool modifiedByTGGModeling = tggModeling.EA_OnPostNewConnector(sqlRepository, connector, currentDiagram);
            bool modifiedByECoreModeling = ecoreModeling.EA_OnPostNewConnector(sqlRepository, connector, currentDiagram);

            incrementalUpdateAdapter.EA_OnPostNewConnector(Repository, Info);

            preventCascade = false;
            return modifiedBySDMModeling || modifiedByTGGModeling || modifiedByECoreModeling;
        }

        public bool EA_OnPostNewPackage(EA.Repository Repository, EA.EventProperties Info)
        {
            SQLRepository sqlRep = new SQLRepository(Repository, false);
            EA.Package package = Repository.GetPackageByID(int.Parse((string)Info.Get(0).Value));
            if (package.IsModel)
            {
                EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRep, package, MetamodelHelper.MoflonExportTaggedValueName, Main.TrueValue);
            }

            incrementalUpdateAdapter.EA_OnPostNewPackage(Repository, Info);

            return false;
        }

        public void EA_OnPostOpenDiagram(EA.Repository repository, int diagramId)
        {
            EA.Diagram openedDiagram = repository.GetDiagramByID(diagramId);
            sdmModeling.EA_OnPostOpenDiagram(repository, openedDiagram);
            tggModeling.EA_OnPostOpenDiagram(repository, openedDiagram);
        }



        public bool EA_OnPostNewElement(EA.Repository Repository, EA.EventProperties Info)
        {
            EALastBroadcastMethodId = EAOnPostNewElement;

            preventCascade = true;

            SQLRepository sqlRepository = new SQLRepository(Repository, false);
            EA.Diagram currentDiagram = sqlRepository.GetCurrentDiagram();
            sqlRepository.SaveDiagram(currentDiagram.DiagramID);
            EA.Element element = Repository.GetElementByID(int.Parse((string)Info.Get(0).Value));

            Boolean sdmModeling_postNewElement = sdmModeling.EA_OnPostNewElement(sqlRepository, element, currentDiagram);
            Boolean tggModeling_postNewElement = tggModeling.EA_OnPostNewElement(sqlRepository, element, currentDiagram);
            Boolean ecorePost = ecoreModeling.EA_OnPostNewElement(sqlRepository, element, currentDiagram);
            consistencyModule.dispatchSingleObject(Repository, element.ElementGUID, element.ObjectType);

            preventCascade = false;

            incrementalUpdateAdapter.EA_OnPostNewElement(Repository, Info);

            return true;
        }

        public bool EA_OnPreDeleteMethod(EA.Repository Repository, EA.EventProperties Info)
        {
            bool allowed = true;
            EA.Method meth = Repository.GetMethodByID(int.Parse((string)Info.Get(0).Value));
            allowed = allowed && sdmModeling.EA_OnPreDeleteMethod(Repository, meth);
            allowed = allowed && tggModeling.EA_OnPreDeleteMethod(Repository, meth);

            incrementalUpdateAdapter.EA_OnPreDeleteMethod(Repository, Info);

            return allowed;
        }

        public bool EA_OnPreDeleteAttribute(EA.Repository Repository, EA.EventProperties Info)
        {
            incrementalUpdateAdapter.EA_OnPreDeleteAttribute(Repository, Info);
            return true;
        }

        public bool EA_OnPreDeleteConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            incrementalUpdateAdapter.EA_OnPreDeleteConnector(Repository, Info);
            return true;
        }

        public bool EA_OnPreDeletePackage(EA.Repository Repository, EA.EventProperties Info)
        {
            incrementalUpdateAdapter.EA_OnPreDeletePackage(Repository, Info);
            return true;
        }

        public bool EA_OnPreDeleteDiagramObject(EA.Repository Repository, EA.EventProperties Info)
        {
            EA.Element element = Repository.GetElementByID(int.Parse((string)Info.Get(0).Value));
            this.deletedDiagramObjectGUIDQueue.Add(element.ElementGUID);
            this.deletedDiagramObjectsObjectTypesQueue.Add(element.ObjectType);
            
            return true;
        }

        public bool EA_OnPreDeleteElement(EA.Repository Repository, EA.EventProperties Info)
        {
            incrementalUpdateAdapter.EA_OnPreDeleteElement(Repository, Info);

            return true;
        }
        
        public bool EA_OnPreNewElement(EA.Repository Repository, EA.EventProperties Info)
        {
            Boolean sdmModeling_preNewElement = sdmModeling.EA_OnPreNewElement(Repository, Info);
            Boolean tggModeling_preNewElement = tggModeling.EA_OnPreNewElement(Repository, Info);
                        
            return sdmModeling_preNewElement && tggModeling_preNewElement;
        }

        public void EA_OnPostNewDiagramObject(EA.Repository Repository, EA.EventProperties Info)
        {
            EA.Element element = Repository.GetElementByID(int.Parse((string)Info.Get(0).Value));
            tggModeling.EA_OnPostNewDiagramObject(Repository, Info);
           // consistencyModule.dispatchSingleObject(Repository, element.ElementGUID, element.ObjectType);
        }

        public void EA_FileClose(EA.Repository Repository)
        {
            if (propertyServer != null)
                propertyServer.stopServer();
        }

        public void EA_FileOpen(EA.Repository Repository)
        {
            SQLRepository sqlRep = new SQLRepository(Repository, false);

            eapUpdater = new EAPUpdater(Repository);

            StaticAssembly = this.GetType().Assembly;

            if (Repository.Models.Count > 0)
            {
                Main.eapUpdater.updateEAPIfNecessary(false);
            }

            try
            {
                initializeControlPanel(Repository);
                ValidationPropertyUtil.foundObjects.Clear();

                propertyServer = new EMoflonTCPServer(Repository, tcpServerFunctions);
            }
            catch
            {
            }
        }

        
        

        public void EA_OnPostNewDiagram(EA.Repository Repository, EA.EventProperties Info)
        {
            if (!preventCascade)
            {
                try
                {
                    Repository.GetDiagramByID(int.Parse(Info.Get(0).Value.ToString()));
                }
                catch (System.Exception)
                {
                    // Sometimes, the ID is not valid. Since it is not easily possible to check 
                    // whether a diagram ID is valid, we use try-catch here.
                    return;
                }
                preventCascade = true;
                tggModeling.EA_OnPostNewDiagram(Repository, Info);
                ecoreModeling.EA_OnPostNewDiagram(Repository, Info);
                preventCascade = false;

            }
        }

        public Boolean EA_OnPostNewMethod(EA.Repository Repository, EA.EventProperties Info)
        {
            EA.Method method = Repository.GetMethodByID(int.Parse((string)Info.Get(0).Value));
            consistencyModule.dispatchSingleObject(Repository, method.MethodGUID, method.ObjectType);

            incrementalUpdateAdapter.EA_OnPostNewMethod(Repository, Info);
            return true;
        }

        public Boolean EA_OnPostNewAttribute(EA.Repository Repository, EA.EventProperties Info)
        {
            incrementalUpdateAdapter.EA_OnPostNewAttribute(Repository, Info);
            return true;
        }
   
        #endregion

        #region Listener methods to deligate execution
        
        /// <summary>
        /// Technologies are added to EA instance.
        /// </summary>
        /// <param name="r"></param>
        /// <returns></returns>
        public String EA_OnInitializeTechnologies(EA.Repository Repository)
        {
            string technology = "";
            Assembly assem = this.GetType().Assembly;
            using (Stream stream = assem.GetManifestResourceStream("EAEcoreAddin.Resources.MOFLON2_Technology.xml"))
            {
                try
                {
                    using (StreamReader reader = new StreamReader(stream))
                    {
                        technology = reader.ReadToEnd();
                    }
                }
                catch
                {
                    System.Windows.Forms.MessageBox.Show("Error Initializing Technology");
                }
            }

            return technology;
        }

        
        #endregion

        #region Other methods

        public void EA_OnInitializeUserRules(EA.Repository Repository)
        {
            consistencyModule = new ConsistencyModule();
            consistencyModule.initializeRules();
        }

        public bool preventCascade = false;
        private EcoreDiagramUpdateAdapter incrementalUpdateAdapter;

        public void EA_OnNotifyContextItemModified(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {

            if (!preventCascade)
            {

                EALastBroadcastMethodId = EAOnContextItemModified;

                //updating package during this method can cause ea crashes
                if (!Import.MainImport.ImportBusy && !preventCascade && !Export.ExportRunning)
                {
                    preventCascade = true;
                    if (ot == EA.ObjectType.otConnector)
                    {
                        EA.Connector con = Repository.GetConnectorByGuid(GUID);
                        if (con.ClientID == 0 || con.SupplierID == 0)
                            return;
                    }

                    tggModeling.EA_OnNotifyContextItemModified(Repository, GUID, ot);
                    sdmModeling.EA_OnNotifyContextItemModified(Repository, GUID, ot);
                    ecoreModeling.EA_OnNotifyContextItemModified(Repository, GUID, ot);

                    if (ot != EA.ObjectType.otDiagram)
                        consistencyModule.dispatchSingleObject(Repository, GUID, ot);

                    incrementalUpdateAdapter.EA_OnNotifyContextItemModified(Repository, GUID, ot);

                    preventCascade = false;
                }
            }
        }



        public static void addToTreeQueue(String GUID,  MocaTaggableElement tagElement)
        {
            if (!SaveTreeQueue.ContainsKey(GUID))
            {
                SaveTreeQueue.Add(GUID, tagElement);
            }
        }

        public static void addToDeleteGUIDQueue(String guid)
        {
            DeleteQueue.Add(guid);
        }


        public static int getLastBroadcastMethod()
        {
            return EALastBroadcastMethodId;
        }

        #endregion

        #region helper methods

        private static void initializeControlPanel(EA.Repository Repository)
        {
            if (globalFunctions == null)
                globalFunctions = Repository.AddWindow("eMoflon Global Functions", "EAEcoreAddin.ControlPanel.Global.GlobalFunctions") as GlobalFunctions;
            globalFunctions.resetFunctions(Repository);

            if (sdmFunctions == null)
                sdmFunctions = Repository.AddWindow("eMoflon SDM Functions", "EAEcoreAddin.ControlPanel.SDMFunctions") as SDMFunctions;
            sdmFunctions.resetFunctions(Repository);

            if (tggFunctions == null)
                tggFunctions = Repository.AddWindow("eMoflon TGG Functions", "EAEcoreAddin.ControlPanel.TGGFunctions") as TGGFunctions;
            tggFunctions.resetFunctions(Repository);

            if (tcpServerFunctions == null)
                tcpServerFunctions = Repository.AddWindow("eMoflon TCP Server Functions", "EAEcoreAddin.ControlPanel.TCPServerUserControl") as TCPServerUserControl;
            tcpServerFunctions.resetFunctions(Repository);

            if (mainErrorOutput == null)
            {
                mainErrorOutput = Repository.AddWindow("eMoflon Validation Result", "EAEcoreAddin.Consistency.RuleHandling.NewErrorOutput") as NewErrorOutput;
                ConsistencyModule.RuleErrorOutputControls.Add(mainErrorOutput);
            }
            consistencyModule.resetConsistencyModule(Repository);
            
        }

        

        

        private static void deleteElementsInQueue(EA.Repository Repository)
        {
            while (DeleteQueue.Count > 0)
            {
                EAUtil.deleteElement(Repository.GetElementByGuid(DeleteQueue[0]), new SQLRepository(Repository, false));
                DeleteQueue.RemoveAt(0);
            }
        }

        private void saveMocaTaggableElementsInQueue()
        {
            if (!preventCascade)
            {
                while (SaveTreeQueue.Count > 0)
                {
                    preventCascade = true;
                    SaveTreeQueue[SaveTreeQueue.Keys.ElementAt(0)].saveTreeToEATaggedValue(true);
                    SaveTreeQueue.Remove(SaveTreeQueue.Keys.ElementAt(0));
                    preventCascade = false;
                }
            }
        }

        private void validateDeletedDiagramObjects(EA.Repository Repository)
        {
            while (deletedDiagramObjectGUIDQueue.Count > 0)
            {
                consistencyModule.dispatchSingleObject(Repository, deletedDiagramObjectGUIDQueue[0], deletedDiagramObjectsObjectTypesQueue[0]);
                deletedDiagramObjectGUIDQueue.RemoveAt(0);
                deletedDiagramObjectsObjectTypesQueue.RemoveAt(0);
            }

        }

        

        


        #endregion
    }
}

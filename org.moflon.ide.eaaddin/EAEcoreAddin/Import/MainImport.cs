using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using System.IO;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Import.Util;
using System.ComponentModel;
using EAEcoreAddin.Import.Gui;

namespace EAEcoreAddin.Import
{
    public class MainImport
    {
        public static Boolean hasGui = true;

        public static Boolean ImportBusy = false;

        //EA.Attribute EA.Element EA.Method and EA.Parameter objects stored with the GUID of the classifier Element from the MocaTree
        public Dictionary<Object, String> ObjectToTypeGuid { get; set; }

        //list of MocaTaggableElements which should be serialized and saved after they are created inside EA
        public List<MocaTaggableElement> MocaTaggableElements { get; set; }

        //old GUIDs from the MocaTree mapped to the new GUIDs which are create during the import in EA
        public Dictionary<String, String> OldGuidToNewGuid { get; set; }

        //all new ereference connector elements stored with guid from loaded mocaTree
        public Dictionary<String, EA.Connector> ReferenceGuidToReference { get; set; }

        //all new ereference connector elements stored with guid from loaded mocaTree
        public Dictionary<String, MocaNode> ReferenceGuidToClientTarget { get; set; }


        //new EClass elements with base class guids divided by empty char
        public Dictionary<EA.Element, String> ElementToBaseClassGuids { get; set; }

        //map of new created GUIDs with their corresponding EA.Elements
        public Dictionary<String, EA.Element> ElementGuidToElement { get; set; }

        public Dictionary<MocaNode, EA.Element> ConnectorNodeToParent { get; set; }

        public List<EA.Diagram> DiagramsToBeFilled{ get; set; }


        public List<EA.Package> importedPackages { get; set; }
        List<String> checkedMetamodelsToImport;


        public MocaNode MocaTree { get; set; }
        public SQLRepository sqlRep { get; set; }
        public EA.Repository repository { get; set; }


        public SDMImport SdmImport { get; set; }
        public EcoreImport EcoreImport { get; set; }
        public TGGImport TggImport { get; set; }

        public BackgroundWorker ImportWorker { get; set; }

        private static MainImport singleObject;

        public static MainImport getInstance()
        {
            if (singleObject != null)
            {
                return singleObject;
            }
            return null;
        }

        public static MainImport getInstance(SQLRepository sqlRep, BackgroundWorker importWorker)
        {
            
            if (singleObject == null)
                singleObject = new MainImport(sqlRep);
            if(importWorker != null)
                singleObject.ImportWorker = importWorker;
            return singleObject;
        }

        private MainImport(SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.repository = sqlRep.GetOriginalRepository();
            this.ConnectorNodeToParent = new Dictionary<MocaNode, EA.Element>();
            this.ElementGuidToElement = new Dictionary<string, EA.Element>();
            this.ElementToBaseClassGuids = new Dictionary<EA.Element, string>();
            this.MocaTaggableElements = new List<MocaTaggableElement>();
            this.ObjectToTypeGuid = new Dictionary<object, string>();
            this.OldGuidToNewGuid = new Dictionary<string, string>();
            this.ReferenceGuidToClientTarget = new Dictionary<string, MocaNode>();
            this.ReferenceGuidToReference = new Dictionary<string, EA.Connector>();
            this.importedPackages = new List<EA.Package>();
            this.checkedMetamodelsToImport = new List<string>();
            this.DiagramsToBeFilled = new List<EA.Diagram>();

            this.SdmImport = new SDMImport(sqlRep);
            this.EcoreImport = new EcoreImport(sqlRep);
            this.TggImport = new TGGImport(sqlRep);
        }

        


        public void startImport(MocaNode importNode, List<String> mmsToImport, Boolean gui)
        {
            this.MocaTree = importNode;
            hasGui = gui;
            checkedMetamodelsToImport = mmsToImport;
            try
            {
                doImport();
            }
            catch
            {
                int modelroot = 0;

                if (hasGui)
                    MessageBox.Show("Import was not successfull. The Moca XMI file seems to be invalid");
                else
                    Console.Out.WriteLine("ERROR:Import was not successfull. The Moca XMI file seems to be invalid#");
                repository.RefreshModelView(modelroot);
            }
            finally
            {
                singleObject = null;
            }
        }


        private void doImport()
        {
            repository.Models.Refresh();
            ImportBusy = true;
            repository.BatchAppend = true;

            if(hasGui)
                ImportWorker.ReportProgress(0, new ProgressObject(ProgressBarType.Complete, "Import raw Objects", 6));
            else
                Console.Out.WriteLine("INFO:Import Raw Objects#");
            importMetamodelNodes();

            if (hasGui)
                ImportWorker.ReportProgress(1, new ProgressObject(ProgressBarType.Complete, "Import raw Connectors"));
            else
                Console.Out.WriteLine("INFO:Import Raw Connector#");
            importConnectors();

            if (hasGui)
                ImportWorker.ReportProgress(2, new ProgressObject(ProgressBarType.Complete, "Import Inheritances"));
            else
                Console.Out.WriteLine("INFO:Import Inheritances#");
            EcoreImport.setInheritances();

            if (hasGui)
                ImportWorker.ReportProgress(3, new ProgressObject(ProgressBarType.Complete, "Set Classifiers"));
            else
                Console.Out.WriteLine("INFO:Import Set Classifiers#");
            setClassifierAttributes();

            if (hasGui)
                ImportWorker.ReportProgress(4, new ProgressObject(ProgressBarType.Complete, "Save Moca Fragment and refresh EA Gui"));
            else
                Console.Out.WriteLine("INFO:Save Moca Fragment and Refresh EA Gui#");
            saveMocaTagElements();

            if (hasGui)
                ImportWorker.ReportProgress(6, new ProgressObject(ProgressBarType.Complete, "Fill Diagrams"));
            else
                Console.Out.WriteLine("INFO:Fill Diagrams#");
            fillDiagrams();


            ImportBusy = false;
            repository.BatchAppend = false;

            repository.RefreshModelView((repository.Models.GetAt(0) as EA.Package).PackageID);


        }


        private void fillDiagrams()
        {
            int counter = 0;
            foreach(EA.Diagram diagram in DiagramsToBeFilled) 
            {
                if (hasGui)
                    ImportWorker.ReportProgress(7, new ProgressObject(ProgressBarType.Current, "Fill Diagram", DiagramsToBeFilled.Count));
                else
                {
                    Console.Out.WriteLine("SCALE:Fill Diagram '" + diagram.Name + "' %" + counter+ "/" + DiagramsToBeFilled.Count + "#");
                    counter++;
                }
                if (diagram.StyleEx == "MDGDgm=eMoflon Ecore Diagrams::Ecore Diagram;")
                {
                    EcoreImport.fillEcoreDiagram(diagram);
                }
            }

        }

        private void saveMocaTagElements()
        {
            int counter = 0;
            foreach (MocaTaggableElement taggableElement in this.MocaTaggableElements)
            {
                if (hasGui)
                    ImportWorker.ReportProgress(5, new ProgressObject(ProgressBarType.Current, "Save: " + taggableElement.GetType().Name, MocaTaggableElements.Count));
                else
                {
                    Console.Out.WriteLine("SCALE:Save '" + taggableElement.GetType().Name + "' %" + counter + "/" + MocaTaggableElements.Count + "#");
                    counter++;
                }
                if (!(taggableElement is EReference))
                {
                    taggableElement.saveTreeToEATaggedValue(true);
                }
                else
                {
                    taggableElement.saveTreeToEATaggedValue(false);
                }
            }
        }

        private void importConnectors()
        {
            int counter = 0;
            //first eReferences and ActivityEdges
            foreach (MocaNode refNode in ConnectorNodeToParent.Keys)
            {
                EA.Element parent = ConnectorNodeToParent[refNode];

                if (refNode.Name == EClass.ReferencesChildNodeName)
                {
                    if (hasGui)
                        ImportWorker.ReportProgress(1, new ProgressObject(ProgressBarType.Current, "Import EReference", ConnectorNodeToParent.Count));
                    else
                    {
                        Console.Out.WriteLine("SCALE:Import EReference for '" + parent.Name + "' %" + counter + "/" + ConnectorNodeToParent.Count + "#");
                        counter++;
                    }

                    EcoreImport.importERef(sqlRep.GetElementByID(parent.ElementID), refNode);
                }
          /*      else if (refNode.Name == "ActivityEdge")
                {
                    if (hasGui)
                        ImportWorker.ReportProgress(1, new ProgressObject(ProgressBarType.Current, "Import ActivityEdge", ConnectorNodeToParent.Count));

                    SdmImport.importActivityEdge(sqlRep.GetElementByID(parent.ElementID), refNode);
                }

                if (refNode.Name == SDMModelingMain.LinkVariableStereotype)
                {
                    if (hasGui)
                        ImportWorker.ReportProgress(1, new ProgressObject(ProgressBarType.Current, "Import LinkVariable", ReferenceGuidToClientTarget.Count));

                    SdmImport.importLinkVariable(sqlRep.GetElementByID(parent.ElementID), refNode);
                }
                else if (refNode.Name == TGGModelingMain.TggLinkVariableStereotype)
                {
                    if (hasGui)
                        ImportWorker.ReportProgress(1, new ProgressObject(ProgressBarType.Current, "Import TGGLinkVariable", ReferenceGuidToClientTarget.Count));

                    TggImport.importTGGLinkVariable(refNode, sqlRep.GetElementByID(parent.ElementID));
                }*/
            }
            counter = 0;
            foreach (String refGuid in ReferenceGuidToClientTarget.Keys)
            {
                if (hasGui)
                    ImportWorker.ReportProgress(2, new ProgressObject(ProgressBarType.Current, "Save EReference and refresh Gui", ReferenceGuidToClientTarget.Count));
                else
                {
                    Console.Out.WriteLine("SCALE:Save EReference and Refresh Gui for '"+refGuid+"' %" + counter + "/" + ReferenceGuidToClientTarget.Count + "#");
                    counter++;
                }
                if (ReferenceGuidToClientTarget.ContainsKey(refGuid) 
                    && ReferenceGuidToReference.ContainsKey(refGuid))
                {

                    EA.Connector con = ReferenceGuidToReference[refGuid];
                    con.Update();
                    MocaNode refsNode = ReferenceGuidToClientTarget[refGuid];

                    EReference eRef = new EReference(sqlRep.GetConnectorByID(con.ConnectorID), sqlRep);
                    eRef.deserializeFromMocaTree(refsNode);

                    if (con.Notes == "Switched")
                    {
                        cleanUpEA(con.ClientEnd, con.SupplierEnd, eRef);
                        con.Notes = "";
                        con.Update();
                        eRef.switchEnds();
                    }
                    else cleanUpEA(con.SupplierEnd, con.ClientEnd, eRef);

                    con.Update();

                    eRef.setRealConnector(con);

                    eRef.Update();

                    MocaTaggableElements.Add(eRef);
                   
                    
                }
            }
        }

        private void cleanUpEA(EA.ConnectorEnd eaSupEnd, EA.ConnectorEnd eaCliEnd, EReference eRef)
        {
            eaSupEnd.Role = eRef.SupplierEnd.Name;
            eaSupEnd.Update();
            eaCliEnd.Role = eRef.ClientEnd.Name;
            eaCliEnd.Update();

            // repair Suppliers End
            if (eaSupEnd.Role == null || eaSupEnd.Role == "")
                eaSupEnd.Cardinality = "";
            else
                eaSupEnd.Cardinality = eRef.SupplierEnd.lowerBound.Replace("-1", "*") + ".." + eRef.SupplierEnd.upperBound.Replace("-1", "*");
            eaSupEnd.Update();

            // repair Cliend End
            if (eaCliEnd.Role == null || eaCliEnd.Role == "")
                eaCliEnd.Cardinality = "";
            else
                eaCliEnd.Cardinality = eRef.ClientEnd.lowerBound.Replace("-1", "*") + ".." + eRef.ClientEnd.upperBound.Replace("-1", "*");
            eaCliEnd.Update();

        }


        private void setClassifierAttributes()
        {
            int counter = 0;
            foreach (Object objectToBeTyped in this.ObjectToTypeGuid.Keys)
            {
                String typeElementGuid = this.ObjectToTypeGuid[objectToBeTyped];
                if (hasGui)
                    ImportWorker.ReportProgress(4, new ProgressObject(ProgressBarType.Current, "Set Classifiers", ObjectToTypeGuid.Count));
                else
                {
                    Console.Out.WriteLine("SCALE:Set Classifiers for '"+ typeElementGuid +"' %" + counter + "/" + ObjectToTypeGuid.Count + "#");
                    counter++;
                }
                
                
                if (OldGuidToNewGuid.ContainsKey(typeElementGuid))
                {
                    String newTypeElementGuid = this.OldGuidToNewGuid[typeElementGuid];
                    EA.Element typeElement = ElementGuidToElement[newTypeElementGuid];
                    if (objectToBeTyped is EA.Element)
                    {
                        EA.Element elem = objectToBeTyped as EA.Element;
                        elem.ClassifierID = typeElement.ElementID;
                        elem.Update();
                    }
                    else if (objectToBeTyped is EA.Method)
                    {
                        EA.Method meth = objectToBeTyped as EA.Method;
                        meth.ReturnType = typeElement.Name;
                        meth.ClassifierID = typeElement.ElementID.ToString();
                        meth.Update();
                    }
                    else if (objectToBeTyped is EA.Attribute)
                    {
                        EA.Attribute attr = objectToBeTyped as EA.Attribute;
                        attr.Type = typeElement.Name;
                        attr.ClassifierID = typeElement.ElementID;
                        attr.Update();
                    }
                    else if (objectToBeTyped is EA.Parameter)
                    {
                        EA.Parameter param = objectToBeTyped as EA.Parameter;
                        param.Type = typeElement.Name;
                        param.ClassifierID = typeElement.ElementID.ToString();
                        param.Update();
                    }
                    else
                    {
                        throw new NotImplementedException();
                    }
                }
                else
                {
                }
            }
        }

        public static void fillDiagramFromCollections(EA.Diagram diagram, EA.Collection elementCollection, EA.Collection packageCollection)
        {
            if (elementCollection != null)
            {
                foreach (EA.Element element in elementCollection)
                {
                    EA.DiagramObject diagramObject = diagram.DiagramObjects.AddNew(element.Name, element.Type) as EA.DiagramObject;
                    diagramObject.ElementID = element.ElementID;
                    diagramObject.Update();
                }
            }
            if (packageCollection != null)
            {
                foreach (EA.Package pkg in packageCollection)
                {
                    EA.DiagramObject diagramObject = diagram.DiagramObjects.AddNew(pkg.Name, "") as EA.DiagramObject;
                    diagramObject.ElementID = pkg.Element.ElementID;
                    diagramObject.Update();
                }
            }
        }


        private void importMetamodelNodes()
        {
            int counter = 0;
            foreach (MocaNode modelPackageNode in this.MocaTree.Children)
            {
                if (!hasGui || checkedMetamodelsToImport.Contains(modelPackageNode.getAttributeOrCreate("Moflon::Name").Value))
                {
                    if (modelPackageNode.Name == ECOREModelingMain.EPackageStereotype)
                    {
                        if(hasGui)
                            ImportWorker.ReportProgress(0, new ProgressObject(ProgressBarType.Current, modelPackageNode.getAttributeOrCreate("Moflon::Name").Value, checkedMetamodelsToImport.Count));
                        else
                        {
                            Console.Out.WriteLine("SCALE:Import '" + modelPackageNode.getAttributeOrCreate("Moflon::Name").Value + "' %" + counter + "/" + checkedMetamodelsToImport.Count + "#");
                            counter++;
                        }
                        EcoreImport.importEPackageModel(modelPackageNode);
                    }
                    else if (modelPackageNode.Name == "TGG")
                    {
                        if (hasGui)
                            ImportWorker.ReportProgress(0, new ProgressObject(ProgressBarType.Current, modelPackageNode.getAttributeOrCreate("Moflon::Name").Value, checkedMetamodelsToImport.Count));
                        else
                        {
                            Console.Out.WriteLine("SCALE:Import '" + modelPackageNode.getAttributeOrCreate("Moflon::Name").Value + "' %" + counter + "/" + checkedMetamodelsToImport.Count + "#");
                            counter++;
                        }
                        TggImport.importTGGPackageModel(modelPackageNode);

                    }
                }
            }
        }

    }
}

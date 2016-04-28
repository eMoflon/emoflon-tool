using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;
using System.IO;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using System.Threading;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Persistency.Util;
using System.ComponentModel;

namespace EAEcoreAddin.Persistency
{
    public class EcoreExport
    {
        SQLRepository repository;
        public BackgroundWorker backgroundWorker { get; set; }
        Export Export { get; set; }
        //the current xmlDocument which is in creation
        public MocaNode currentNode { get; set; }

        public SDMExport sdmExport { get; set; }
        public static String outermostPackageName = "";

        public EcoreExport(SQLRepository repository, Export export)
        {
            // TODO: Complete member initialization
            this.repository = repository;
            this.Export = export;
            this.sdmExport = new SDMExport(repository, export);
            
        }

        public MocaNode processOutermostPackage(SQLPackage outermostPackage)
        {
            outermostPackageName = outermostPackage.Name;

            this.currentNode = new MocaNode();
            MocaNode outerMostPackageMocaNode = processEPackage(outermostPackage);

            return outerMostPackageMocaNode;
        }

        private MocaNode processEPackage(SQLPackage eaPackage)
        {
            backgroundWorker.ReportProgress(0, PersistencyUtil.computePackageUri(eaPackage, repository));
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonExportTreeTaggedValueName);


            if (mocaTreeTag != null)
            {
                EPackage ePackage = new EPackage(eaPackage, repository);


                MocaNode ePackageMocaNode = MocaTreeUtil.mocaNodeFromXmlString(mocaTreeTag.Notes);

                ePackage.addAttributesDuringExport(ePackageMocaNode);

                this.currentNode.appendChildNode(ePackageMocaNode);
                int counter = 0;
                foreach (SQLElement childClass in eaPackage.Elements)
                {
                    if (!Export.showStatusBar)
                    {
                        Console.Out.WriteLine("SCALE:Export Classifier '" + childClass.Name + "' %" + counter + "/" + eaPackage.Elements.Count + "#");
                    }
                    counter++;
                    this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName);
                    if (childClass.Stereotype.ToLower() == ECOREModelingMain.EClassStereotype.ToLower())
                    {
                        processEClass(childClass);
                    }
                    else if (childClass.Stereotype.ToLower() == ECOREModelingMain.EDatatypeStereotype.ToLower())
                    {
                        processEDatatype(childClass);
                    }
                    else if (childClass.Stereotype.ToLower() == ECOREModelingMain.EEnumStereotype.ToLower())
                    {
                        processEEnum(childClass);
                    }
                }

                foreach (SQLPackage childPackage in eaPackage.Packages)
                {
                    this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName);
                    processEPackage(childPackage);
                }

                return ePackageMocaNode;
            }
            return null;
        }

        private void processEDatatype(SQLElement eaEDatatype)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaEDatatype, Main.MoflonExportTreeTaggedValueName);
            if (mocaTreeTag != null)
            {

                MocaNode eDatatypeMocaNode = new MocaNode();
                eDatatypeMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
                this.currentNode.appendChildNode(eDatatypeMocaNode);
            }
        }

        public void processEEnum(SQLElement eaEnum)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaEnum, Main.MoflonExportTreeTaggedValueName);
            if (mocaTreeTag != null)
            {
                MocaNode eEnumMocaNode = new MocaNode();
                eEnumMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
                this.currentNode.appendChildNode(eEnumMocaNode);
            }  
        }

        public MocaNode processEClass(SQLElement eaClass)
        {
            //this.exportProgressBar.invokePerformNext("Exporting EClass: " + eaClass.Name);

            SQLTaggedValue changesTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaClass, Main.MoflonChangesTreeTaggedValueName);


            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaClass, Main.MoflonExportTreeTaggedValueName);
            if (mocaTreeTag != null)
            {
                EClass eClass = new EClass(eaClass, repository);
                eClass.loadTreeFromTaggedValue();
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(mocaTreeTag.Notes);

                eClass.addMocaAttributesDuringExport(eClassMocaNode);

                if (changesTreeTag != null)
                {
                    MocaNode eClassChangesNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);
                    eClass.addMocaAttributesDuringExport(eClassChangesNode);
                }
                //add baseclass dependencies
                foreach (var baseClass in eClassMocaNode.getAttributeOrCreate("baseClasses").Value.Split(" ".ToArray()))
                {
                    if (baseClass != "")
                    {
                        SQLElement baseclass = repository.GetElementByGuid(baseClass);
                        Export.computeAndAddToDependencies(repository, baseclass);
                    }
                }

                this.currentNode.appendChildNode(eClassMocaNode);

                //process SDM Container Objects
                int counter = 0;
                foreach (SQLElement possibleSDMContainer in eaClass.Elements)
                {
                    if (possibleSDMContainer.Stereotype == SDMModelingMain.SdmContainerStereotype || possibleSDMContainer.Stereotype == "SDM_Container")
                    {
                        String associatedMethodguid = EAEcoreAddin.Util.EAUtil.findTaggedValue(possibleSDMContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value;
                        MocaNode operationsNode = eClassMocaNode.getChildNodeWithName(EClass.OperationsChildNodeName);
                        MocaNode owningOperationNode = null;
                        if (!Export.showStatusBar)
                        {
                            Console.Out.WriteLine("SCALE:Export SDM '" + possibleSDMContainer.Name + "' %" + counter + "/" + eaClass.Elements.Count + "#");
                        }
                        counter++;
                        foreach (MocaNode EOperationNode in operationsNode.Children)
                        {
                            MocaAttribute guidAttribute = EOperationNode.getAttributeOrCreate(Main.GuidStringName);
                            if (guidAttribute != null && guidAttribute.Value == associatedMethodguid)
                                owningOperationNode = EOperationNode;
                        }

                        if (owningOperationNode != null)
                        {

                            MocaNode sdmActivityNode = this.sdmExport.processActivity(possibleSDMContainer);
                            owningOperationNode.appendChildNode(sdmActivityNode);
                        }
                    }
                }

                foreach (SQLAttribute element in eaClass.Attributes)
                {
                    string t = element.Type;
                    int cid = element.ClassifierID;
                    SQLElement typeElement = repository.GetElementByIDNullable(cid);
                    Export.computeAndAddToDependencies(repository, typeElement);
                }

                counter = 0;
                foreach (SQLConnector ereference in eaClass.Connectors)
                {
                    if (ereference.Type == Main.EAAssociationType)
                    {
                        if (!Export.showStatusBar)
                        {
                            Console.Out.WriteLine("SCALE:Export EReference '" + ereference.Name + "' %" + counter + "/" + eaClass.Connectors.Count + "#");
                        }
                        counter++;
                        MocaNode referencesNode = eClassMocaNode.getChildNodeWithName(EClass.ReferencesChildNodeName);

                        if (ereference.ClientID != eaClass.ElementID && ereference.ClientEnd.IsNavigable)
                        {
                            this.currentNode = referencesNode;
                            processEReference(ereference, ereference.ClientEnd.End);
                        }
                        else if (ereference.SupplierID != eaClass.ElementID && ereference.SupplierEnd.IsNavigable)
                        {
                            this.currentNode = referencesNode;
                            processEReference(ereference, ereference.SupplierEnd.End);
                        }

                        if (ereference.SupplierID == eaClass.ElementID && ereference.ClientID == eaClass.ElementID && ereference.SupplierEnd.IsNavigable)
                        {
                            this.currentNode = referencesNode;
                            processEReference(ereference, ereference.SupplierEnd.End);
                        }
                        if (ereference.SupplierID == eaClass.ElementID && ereference.ClientID == eaClass.ElementID && ereference.ClientEnd.IsNavigable)
                        {
                            this.currentNode = referencesNode;
                            processEReference(ereference, ereference.ClientEnd.End);
                        }

                    }
                }
                return eClassMocaNode;
            }
            return null;
        }


        public void processEReference(SQLConnector eaReference, String clientOrSupplier)
        {
            SQLConnectorTag mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaReference, Main.MoflonExportTreeTaggedValueName);
            EReference eReference = null;
            if (mocaTreeTag == null)
            {
                eReference = new EReference(eaReference, repository);
                eReference.saveTreeToEATaggedValue(true);
                mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaReference, Main.MoflonExportTreeTaggedValueName);
            }

            MocaNode referenceEndNode = new MocaNode();
            referenceEndNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            SQLElement eTypeElement = null;
            if (clientOrSupplier == "Client")
            {
                referenceEndNode = referenceEndNode.getChildNodeWithName(EReference.ClientReferenceChildNodeName).Children[0];
                eTypeElement = repository.GetElementByID(eaReference.ClientID);
            }
            else if (clientOrSupplier == "Supplier")
            {
                referenceEndNode = referenceEndNode.getChildNodeWithName(EReference.SupplierReferenceChildNodeName).Children[0];
                eTypeElement = repository.GetElementByID(eaReference.SupplierID);
            }

            Export.computeAndAddToDependencies(repository, eTypeElement);

            this.currentNode.appendChildNode(referenceEndNode);
        }        
    }
}

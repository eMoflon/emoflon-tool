using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Persistency.Util;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using System.ComponentModel;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Persistency
{
    public class TggExport
    {
        SQLRepository repository;
        MocaNode currentNode;
        Export ExportMain { get; set; }

        public BackgroundWorker backgroundWorker { get; set; }

        

        public TggExport(SQLRepository repository, Export export)
        {
            this.ExportMain = export;
            this.repository = repository;
        }


        private MocaNode processTggPackage(SQLPackage eaPackage)
        {
            backgroundWorker.ReportProgress(0, PersistencyUtil.computePackageUri(eaPackage, repository));

            EPackage ePackage = new EPackage(eaPackage, repository);

            if (eaPackage.Element.Stereotype == TGGModelingMain.TggRulePackageStereotype || eaPackage.Name == "Rules")
            {
                ePackage = new TGGRulePackage(eaPackage, repository);
            }

            ePackage.loadTreeFromTaggedValue();
            
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonExportTreeTaggedValueName);
            XmlDocument xmlDoc = MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes);

            MocaNode ePackageMocaNode = new MocaNode();
            ePackageMocaNode.deserializeFromXmlTree(xmlDoc.DocumentElement.FirstChild as XmlElement);

            ePackage.addAttributesDuringExport(ePackageMocaNode);

            this.currentNode.appendChildNode(ePackageMocaNode);

            foreach (SQLPackage childPackage in eaPackage.Packages)
            {
                this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName);
                processTggPackage(childPackage);
            }


            foreach (SQLElement childClass in eaPackage.Elements)
            {
                processTGGPackageFeatures(ePackageMocaNode, childClass);
            }


            return ePackageMocaNode;
        }


        private void processTGGPackageFeatures(MocaNode ePackageMocaNode, SQLElement childClass)
        {
            if (childClass.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
            {
                this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName);
                processTGGCorrespondenceType(childClass);
            }
            else if (childClass.Stereotype == TGGModelingMain.TggRuleSetStereotype)
            {
                foreach (SQLElement childElement in childClass.Elements)
                    processTGGPackageFeatures(ePackageMocaNode, childElement);
            }
            else if (childClass.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                MocaNode rulesNode = ePackageMocaNode.getChildNodeWithName(TGGRulePackage.RulesChildNodeName);
                if (rulesNode == null)
                    rulesNode = ePackageMocaNode.appendChildNode(TGGRulePackage.RulesChildNodeName);
                this.currentNode = rulesNode;
                processTggRule(childClass);
            }
            else if (childClass.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                ExportMain.ecoreExport.currentNode = new MocaNode();
                this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName);
                currentNode.appendChildNode(ExportMain.ecoreExport.processEClass(childClass));
            }
        }

        private void processTggRule(SQLElement ruleClass)
        {
            //this.exportProgressBar.invokePerformNext("exporting EClass: " + eaClass.Name);
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(ruleClass, Main.MoflonExportTreeTaggedValueName);
            TGGRule tggRule = new TGGRule(repository, ruleClass);
            tggRule.loadTreeFromTaggedValue();
            MocaNode tggRuleMocaNode = new MocaNode();
            tggRuleMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
            this.currentNode.appendChildNode(tggRuleMocaNode);

            tggRule.additionalAttributesDuringExport(tggRuleMocaNode);

            foreach (SQLElement element in ruleClass.Elements)
            {
                //handle tgg rule pattern

                if (element.Stereotype == TGGModelingMain.TggObjectVariableStereotype || element.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                {
                    this.currentNode = tggRuleMocaNode.getChildNodeWithName(StoryPattern.ObjectVariablesChildNodeName);
                    processTggObjectVariable(element);
                }
                //handle sdms of tgg rule class
                else if (element.Stereotype == SDMModelingMain.SdmContainerStereotype)
                {
                    String associatedMethodguid = EAEcoreAddin.Util.EAUtil.findTaggedValue(element, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value;
                    MocaNode operationsNode = tggRuleMocaNode.getChildNodeWithName(EClass.OperationsChildNodeName);
                    MocaNode owningOperationNode = null;

                    foreach (MocaNode EOperationNode in operationsNode.Children)
                    {
                        MocaAttribute guidAttribute = EOperationNode.getAttributeOrCreate(Main.GuidStringName);
                        if (guidAttribute != null && guidAttribute.Value == associatedMethodguid)
                            owningOperationNode = EOperationNode;
                    }

                    if (owningOperationNode != null)
                    {
                        MocaNode sdmActivityNode = this.ExportMain.ecoreExport.sdmExport.processActivity(element);
                        owningOperationNode.appendChildNode(sdmActivityNode);
                    }
                }
                else if (element.Stereotype == TGGModelingMain.CSPConstraintStereotype || element.Stereotype == "TGGCsp")
                {

                    MocaNode cspsNode = tggRuleMocaNode.getChildNodeWithName(TGGRule.CspsChildNodeName);
                    if (cspsNode == null)
                    {
                        cspsNode = tggRuleMocaNode.appendChildNode(TGGRule.CspsChildNodeName);
                    }
                    this.currentNode = cspsNode;
                    processTGGCSP(element);


                    tggRuleMocaNode.getAttributeOrCreate(TGGRule.CspSpecAttributeName).Value += element.Notes.Replace(Environment.NewLine, "");
                }
            }
        }

        private void processTGGCSP(SQLElement element)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(element, Main.MoflonExportTreeTaggedValueName);
            if (mocaTreeTag != null)
            {
                MocaNode tggRuleMocaNode = new MocaNode();
                tggRuleMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
                this.currentNode.appendChildNode(tggRuleMocaNode);
            }
        }

        public MocaNode processTggObjectVariable(SQLElement objectVariable)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(objectVariable, Main.MoflonExportTreeTaggedValueName);
            
            ObjectVariable ov = null;
            if (objectVariable.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                ov = new TGGObjectVariable(objectVariable, repository);
            else if (objectVariable.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                ov = new TGGCorrespondence(objectVariable, repository);
            SQLElement classifier = repository.GetElementByID(objectVariable.ClassifierID);
            ExportMain.computeAndAddToDependencies(repository, classifier);

            MocaNode ovMocaNode = new MocaNode();
            ovMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            ov.addAttributesDuringExport(ovMocaNode);
            
            this.currentNode.appendChildNode(ovMocaNode);

            foreach (SQLConnector linkVariable in objectVariable.Connectors)
            {
                try
                {
                    SQLElement source = repository.GetElementByID(linkVariable.ClientID);
                    SQLElement target = repository.GetElementByID(linkVariable.SupplierID);

                    if ((linkVariable.Stereotype == TGGModelingMain.TggLinkVariableStereotype || linkVariable.Type == Main.EAAssociationType) && linkVariable.ClientID == objectVariable.ElementID)
                    {
                        this.currentNode = ovMocaNode.getChildNodeWithName(ObjectVariable.OutgoingLinksNodeName);
                        processTggLinkVariable(linkVariable);
                    }

                }
                catch
                {
                }
            }
            return ovMocaNode;
        }

        public void processTggLinkVariable(SQLConnector linkVariable)
        {
            SQLConnectorTag mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(linkVariable, Main.MoflonExportTreeTaggedValueName);

            TGGLinkVariable lv = new TGGLinkVariable(linkVariable, repository);
            Boolean valid = lv.loadTreeFromTaggedValue();
            
            MocaNode linkVariableMocaNode = new MocaNode();
            linkVariableMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            lv.addAttributesDuringExport(linkVariableMocaNode);

            this.currentNode.appendChildNode(linkVariableMocaNode);
        }

        public void processTGGCorrespondenceType(SQLElement eaClass)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaClass, Main.MoflonExportTreeTaggedValueName);
            TGGCorrespondenceType tggCorrType = new TGGCorrespondenceType(eaClass, repository);
            tggCorrType.loadTreeFromTaggedValue();
            MocaNode eClassMocaNode = new MocaNode();
            eClassMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            tggCorrType.addMocaAttributesDuringExport(eClassMocaNode);

            this.currentNode.appendChildNode(eClassMocaNode);
                        
            foreach (SQLConnector ereference in eaClass.Connectors)
            {
                if (ereference.Type == Main.EAAssociationType)
                {
                    MocaNode referencesNode = eClassMocaNode.getChildNodeWithName(EClass.ReferencesChildNodeName);

                    if (ereference.ClientID != eaClass.ElementID && ereference.ClientEnd.IsNavigable)
                    {
                        this.currentNode = referencesNode;
                        processTggEReference(ereference, ereference.ClientEnd.End);
                    }
                    else if (ereference.SupplierID != eaClass.ElementID && ereference.SupplierEnd.IsNavigable)
                    {
                        this.currentNode = referencesNode;
                        processTggEReference(ereference, ereference.SupplierEnd.End);
                    }

                    if (ereference.SupplierID == eaClass.ElementID && ereference.ClientID == eaClass.ElementID && ereference.SupplierEnd.IsNavigable)
                    {
                        this.currentNode = referencesNode;
                        processTggEReference(ereference, ereference.SupplierEnd.End);
                    }
                    if (ereference.SupplierID == eaClass.ElementID && ereference.ClientID == eaClass.ElementID && ereference.ClientEnd.IsNavigable)
                    {
                        this.currentNode = referencesNode;
                        processTggEReference(ereference, ereference.ClientEnd.End);
                    }

                }
            }
        }

        public void processTggEReference(SQLConnector eaReference, String clientOrSupplier)
        {
            SQLConnectorTag mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaReference, Main.MoflonExportTreeTaggedValueName);

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

            ExportMain.computeAndAddToDependencies(repository, eTypeElement);

            this.currentNode.appendChildNode(referenceEndNode);
        }   


        public MocaNode processTggOutermostPackage(SQLPackage tggOutermostPackage)
        {
            backgroundWorker.ReportProgress(0, PersistencyUtil.computePackageUri(tggOutermostPackage, repository));


            this.currentNode = new MocaNode();

            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(tggOutermostPackage, Main.MoflonExportTreeTaggedValueName);

            TGG tgg = new TGG(repository, tggOutermostPackage);
            tgg.loadTreeFromTaggedValue();
            XmlDocument xmlDoc = MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes);

            MocaNode ePackageMocaNode = new MocaNode();
            ePackageMocaNode.deserializeFromXmlTree(xmlDoc.DocumentElement.FirstChild as XmlElement);

            tgg.addAttributesDuringExport(ePackageMocaNode);

            this.currentNode.appendChildNode(ePackageMocaNode);
            foreach (SQLPackage childPackage in tggOutermostPackage.Packages)
            {
                this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName);
                processTggPackage(childPackage);
            }
            foreach (SQLElement childClass in tggOutermostPackage.Elements)
            {
                processTGGPackageFeatures(ePackageMocaNode, childClass);
            }
            SQLTaggedValue moflonExportTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(tggOutermostPackage, MetamodelHelper.MoflonExportTaggedValueName);
            if (moflonExportTag != null)
                ePackageMocaNode.appendChildAttribute(MetamodelHelper.MoflonExportTaggedValueName, moflonExportTag.Value);
            else
                ePackageMocaNode.appendChildAttribute(MetamodelHelper.MoflonExportTaggedValueName, "false");

            return ePackageMocaNode;
        }

        
    }
}

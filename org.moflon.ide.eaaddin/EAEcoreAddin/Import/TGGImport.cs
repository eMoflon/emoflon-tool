using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using System.Web;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using System.Drawing;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Import
{
    public class TGGImport
    {
        
        EA.Repository repository;
        SQLRepository sqlRep;

        public TGGImport(SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.repository = sqlRep.GetOriginalRepository();
        }

        public void importTGGPackageModel(MocaNode tggPackageNode)
        {

            EA.Package rootPackage = MainImport.getInstance().EcoreImport.findOrCreateRoot(tggPackageNode);

            EA.Package modelPackage = MainImport.getInstance().EcoreImport.getOrCreatePackage(sqlRep.GetPackageByID(rootPackage.PackageID), tggPackageNode);

            TGG tggPkg = new TGG(sqlRep, sqlRep.GetPackageByID(modelPackage.PackageID));
            tggPkg.deserializeFromMocaTree(tggPackageNode);
            
            MainImport.getInstance().MocaTaggableElements.Add(tggPkg);
            MainImport.getInstance().OldGuidToNewGuid.Add(tggPkg.Guid, modelPackage.PackageGUID);
            MainImport.getInstance().importedPackages.Add(modelPackage);

            if (modelPackage.Diagrams.Count == 0)
            {
                EA.Diagram diag = modelPackage.Diagrams.AddNew(modelPackage.Name, TGGModelingMain.TggSchemaDiagramMetatype[0]) as EA.Diagram;
                diag.Update();
                MainImport.getInstance().DiagramsToBeFilled.Add(diag);
            }
            MainImport.getInstance().EcoreImport.importEPackageFeatures(tggPackageNode, sqlRep.GetPackageByID(modelPackage.PackageID));
            importTGGFeatures(tggPackageNode, sqlRep.GetPackageByID(modelPackage.PackageID));

        }

        public void importTGGFeatures(MocaNode ePackageNode, SQLPackage modelPackage)
        {
            foreach (MocaNode eclassNode in ePackageNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName).Children)
            {
                if (eclassNode.Name == TGGModelingMain.TggCorrespondenceTypeStereotype)
                {
                    importCorrespondenceType(modelPackage, eclassNode);
                }
            }

            foreach (MocaNode packageNode in ePackageNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName).Children)
            {

                if (packageNode.Name == TGGModelingMain.TggRulePackageStereotype)
                {
                    importRulePackage(modelPackage, packageNode);
                }
            }
        }

        private void importRulePackage(SQLPackage modelPackage, MocaNode packageNode)
        {
            EPackage ePackage = MainImport.getInstance().EcoreImport.importEPackage(packageNode, modelPackage);
            MainImport.getInstance().MocaTaggableElements.Remove(ePackage);
            ePackage = new TGGRulePackage(ePackage.EaPackage, sqlRep);
            MainImport.getInstance().MocaTaggableElements.Add(ePackage);

            foreach (MocaNode ruleNode in packageNode.getChildNodeWithName(TGGRulePackage.RulesChildNodeName).Children)
            {
                importRule(ePackage.EaPackage, ruleNode);
            }
        }

        private void importRule(SQLPackage modelPackage, MocaNode ruleNode)
        {
            EA.Element ruleElement = MainImport.getInstance().EcoreImport.getOrCreateElement(modelPackage, ruleNode, Main.EAClassType);
            
            if (ruleElement.Diagrams.Count == 0)
            {
                EA.Diagram ruleDiagram = ruleElement.Diagrams.AddNew(ruleElement.Name, TGGModelingMain.TggRuleDiagramMetatype[0]) as EA.Diagram;
                ruleDiagram.Update();
                MainImport.getInstance().DiagramsToBeFilled.Add(ruleDiagram);
            }

            EA.Method mainMethod = MainImport.getInstance().EcoreImport.getOrCreateMethod(sqlRep.GetElementByID(ruleElement.ElementID), ruleNode.getAttributeOrCreate("mainMethodGuid").Value, ruleElement.Name, "");
 
            MocaNode parametersNode = ruleNode.getChildNodeWithName(EOperation.ParametersChildNodeName);
            if(parametersNode != null) {
                foreach (MocaNode paramNode in parametersNode.Children)
                {
                    EA.Parameter parameter = MainImport.getInstance().EcoreImport.getOrCreateParameter(mainMethod, paramNode);
                
                    MainImport.getInstance().OldGuidToNewGuid.Add(paramNode.getAttributeOrCreate(Main.GuidStringName).Value, parameter.ParameterGUID);
                }
            }

            EClass eClass = MainImport.getInstance().EcoreImport.importEClassFeatures(ruleNode, sqlRep.GetElementByID(ruleElement.ElementID));
            MainImport.getInstance().MocaTaggableElements.Remove(eClass);
            TGGRule rule = new TGGRule(sqlRep, sqlRep.GetElementByID(ruleElement.ElementID));
            rule.deserializeFromMocaTree(ruleNode);

            MainImport.getInstance().MocaTaggableElements.Add(rule);

            foreach (MocaNode ovNode in ruleNode.getChildNodeWithName(StoryPattern.ObjectVariablesChildNodeName).Children)
            {
                if (ovNode.Name == TGGModelingMain.TggObjectVariableStereotype)
                {
                    ObjectVariable ov = MainImport.getInstance().SdmImport.importObjectVariable(sqlRep.GetElementByID(ruleElement.ElementID), ovNode);
                    MainImport.getInstance().MocaTaggableElements.Remove(ov);
                    ov = new TGGObjectVariable(ov.sqlElement, sqlRep);
                    ov.deserializeFromMocaTree(ovNode);
                    MainImport.getInstance().MocaTaggableElements.Add(ov);

                }
                else if (ovNode.Name == TGGCorrespondence.CorrespondenceNodeName)
                {
                    ObjectVariable ov = MainImport.getInstance().SdmImport.importObjectVariable(sqlRep.GetElementByID(ruleElement.ElementID), ovNode);
                    MainImport.getInstance().MocaTaggableElements.Remove(ov);
                    ov = new TGGCorrespondence(ov.sqlElement, sqlRep);
                    ov.deserializeFromMocaTree(ovNode);
                    MainImport.getInstance().MocaTaggableElements.Add(ov);
                }            
            }

            MocaNode cspsNode = ruleNode.getChildNodeWithName(TGGRule.CspsChildNodeName);

            if (cspsNode != null)
            {
                foreach (MocaNode cspInstanceNode in cspsNode.Children)
                {
                    importTGGCSP(sqlRep.GetElementByID(ruleElement.ElementID), cspInstanceNode);
                }
            }
            else
            {
                if (ruleNode.getAttributeOrCreate(TGGRule.CspSpecAttributeName).Value != "")
                {
                    EA.Element constraintElement = ruleElement.Elements.AddNew("", Main.EAClassType) as EA.Element;
                    constraintElement.Stereotype = TGGModelingMain.CSPConstraintStereotype;
                    constraintElement.Notes = ruleNode.getAttributeOrCreate(TGGRule.CspSpecAttributeName).Value;
                    constraintElement.Update();

                    CSPInstance cspInstance = new CSPInstance(sqlRep, sqlRep.GetElementByID(constraintElement.ElementID));
                    cspInstance.CspStringValueFromImport = ruleNode.getAttributeOrCreate(TGGRule.CspSpecAttributeName).Value;
                    MainImport.getInstance().MocaTaggableElements.Add(cspInstance);
                }
            }

        }

        private void importTGGCSP(SQLElement ruleElement, MocaNode cspInstanceNode)
        {
            EA.Element cspElement = MainImport.getInstance().EcoreImport.getOrCreateElement(ruleElement, cspInstanceNode, "Class");
            CSPInstance cspIstance = new CSPInstance(sqlRep, sqlRep.GetElementByID(cspElement.ElementID));
            cspIstance.deserializeFromMocaTree(cspInstanceNode);


            MainImport.getInstance().MocaTaggableElements.Add(cspIstance);
            MainImport.getInstance().ElementGuidToElement.Add(cspElement.ElementGUID, cspElement);

        }


        private void importCorrespondenceType(SQLPackage parentPackage, MocaNode eClassNode)
        {
            EClass eclass = MainImport.getInstance().EcoreImport.importEClass(parentPackage, eClassNode);
            MainImport.getInstance().MocaTaggableElements.Remove(eclass);
            eclass = new TGGCorrespondenceType(eclass.EaElement, sqlRep);
            eclass.deserializeFromMocaTree(eClassNode);
            MainImport.getInstance().MocaTaggableElements.Add(eclass);
        }

        public void importTGGLinkVariable(MocaNode refNode, SQLElement parent)
        {
            LinkVariable lv = MainImport.getInstance().SdmImport.importLinkVariable(parent, refNode);
            MainImport.getInstance().MocaTaggableElements.Remove(lv);
            lv = new TGGLinkVariable(lv.LinkVariableEA, sqlRep);
            lv.deserializeFromMocaTree(refNode);
            MainImport.getInstance().MocaTaggableElements.Add(lv);
        }




        internal void fillSchemaDiagram(EA.Diagram diagram)
        {
            EA.Package tggPackage = repository.GetPackageByID(diagram.PackageID);

            if(tggPackage != null) 
            {
                TGGModelingUtil.fillTGGSchemaDiagram(repository, tggPackage, diagram);
            }

        }

        internal void fillRulesDiagram(EA.Diagram diagram)
        {
        }

        internal void fillRuleDiagram(EA.Diagram diagram)
        {
            EA.Element ruleElement = repository.GetElementByID(diagram.ParentID);

            TGGModelingUtil.fillTGGRuleDiagram(repository, diagram, ruleElement);
        }
    }
}

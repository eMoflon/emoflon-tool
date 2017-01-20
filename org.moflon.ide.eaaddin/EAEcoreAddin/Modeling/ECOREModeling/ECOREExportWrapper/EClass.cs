using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    public class EClass : MocaTaggableElement
    {        
        public static readonly String AttributesChildNodeName = "attributes";
        public static readonly String OperationsChildNodeName = "operations";
        public static readonly String ReferencesChildNodeName = "references";
        public static readonly String ClassesChildNodeName = "classes";

        public List<EAttribute> EAttributes { get; set; }
        public List<EOperation> EOperations { get; set; }
        public Boolean isAbstract;
        public Boolean isInterface;
        public String Guid { get; set; }
        public SQLElement EaElement { get; set; }
        public string Alias { get; set; }

        public Boolean isInitialized = false;

        public EClass(SQLElement eaElement, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaElement = eaElement;
            this.Name = eaElement.Name;
            this.EOperations = new List<EOperation>();
            this.EAttributes = new List<EAttribute>();
            this.Alias = eaElement.Alias;
        }

        public virtual void addMocaAttributesDuringExport(MocaNode eclassNode)
        {
            eclassNode.appendChildAttribute("baseClasses", EClass.computeBaseClassesAttribute(this.EaElement));
        }

        public static String computeBaseClassesAttribute(SQLElement eaElement)
        {
            String val = "";
            foreach (SQLElement eSuperClass in eaElement.BaseClasses)
            {
                if (eSuperClass.ElementGUID != eaElement.ElementGUID)
                    val += eSuperClass.ElementGUID + " ";
            }
            return val;
        }

        public void computeFeatures()
        {
            this.EOperations = new List<EOperation>();
            this.EAttributes = new List<EAttribute>();
            if (EaElement.Abstract == "1")
            {
                isAbstract = true;
            }
            this.isInterface = EaElement.IsSpec;
            this.Alias = this.EaElement.Alias;

            foreach (SQLAttribute attribute in this.EaElement.Attributes)
            {
                EAttribute eAttribute = new EAttribute(attribute, Repository);
                eAttribute.computeAttributes();
                this.EAttributes.Add(eAttribute);
            }
            foreach (SQLMethod parameter in this.EaElement.Methods)
            {
                EOperation eOperation = new EOperation(parameter, Repository);
                eOperation.computeAttributes();
                this.EOperations.Add(eOperation);
            }

        }


        public override object getObjectToBeTagged()
        {
            return this.EaElement;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            if (!isInitialized)
            {
                computeFeatures();
            }
            MocaNode eclassNode = new MocaNode("EClass");
            eclassNode.appendChildAttribute("name",this.Name);
            eclassNode.appendChildAttribute("isAbstract", this.isAbstract.ToString().ToLower());
            eclassNode.appendChildAttribute("isInterface", this.isInterface.ToString().ToLower());
            eclassNode.appendChildAttribute(Main.GuidStringName, this.EaElement.ElementGUID);
            eclassNode.appendChildAttribute("alias", this.Alias);


            MocaNode referencesNode = eclassNode.appendChildNode(EClass.ReferencesChildNodeName);
            MocaNode attributesNode = eclassNode.appendChildNode(EClass.AttributesChildNodeName);
            MocaNode operationsNode = eclassNode.appendChildNode(EClass.OperationsChildNodeName);

            foreach (EAttribute eAttribute in this.EAttributes)
            { 
                MocaNode eAttributeNode = attributesNode.appendChildNode("EAttribute");
                eAttribute.serializeToMocaTree(eAttributeNode);
            }
            
            foreach (EOperation eOperation in this.EOperations)
            {
                MocaNode eOperationNode = operationsNode.appendChildNode("EOperation");
                eOperation.serializeToMocaTree(eOperationNode);
            }


            return eclassNode;
        }


        public SQLMethod findMethodFromMocaNode(SQLElement parent, MocaNode methodNode)
        {
            SQLMethod found = null;

            foreach (SQLMethod method in parent.Methods)
            {
                int validCount = 0;
                if (method.Name == methodNode.getAttributeOrCreate("name").Value)
                {                  
                    foreach (SQLParameter param in method.Parameters)
                    {
                        foreach (MocaNode paramNode in methodNode.getChildNodeWithName(EOperation.ParametersChildNodeName).Children)
                        {
                            if (paramNode.getAttributeOrCreate("name").Value == param.Name)
                                validCount++;
                        }
                    }
                    if (validCount == method.Parameters.Count && validCount == methodNode.getChildNodeWithName(EOperation.ParametersChildNodeName).Children.Count)
                        found = method;
                }               
            }
            return found;

        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaNode operationsNode = actNode.getChildNodeWithName(EClass.OperationsChildNodeName);
            MocaNode attributesNode = actNode.getChildNodeWithName(EClass.AttributesChildNodeName);
            MocaAttribute guidAttr = actNode.getAttributeOrCreate(Main.GuidStringName);
            MocaAttribute nameAttr = actNode.getAttributeOrCreate("name");
            MocaAttribute aliasAttr = actNode.getAttributeOrCreate("alias");
            MocaAttribute isAbstractAttr = actNode.getAttributeOrCreate("isAbstract");
            MocaAttribute isInterfaceAttr = actNode.getAttributeOrCreate("isInterface");

            if (nameAttr != null)
                this.Name = nameAttr.Value;
            if(guidAttr != null)
                this.Guid = guidAttr.Value;
            if (aliasAttr != null)
                this.Alias = aliasAttr.Value;
            if (isAbstractAttr.Value != "")
            {

                if (bool.TryParse(isAbstractAttr.Value, out this.isAbstract))
                {
                }
                else
                {
                    int value = 0;
                    if (int.TryParse(isAbstractAttr.Value, out value))
                    {
                        this.isAbstract = value == 1;
                    }
                }
            }
            if (isInterfaceAttr.Value != "")
            {
                this.isInterface = Boolean.Parse(isInterfaceAttr.Value);
            }
            
            foreach (MocaNode eOpNode in operationsNode.Children)
            {
                SQLMethod method = findMethodFromMocaNode(this.EaElement, eOpNode);
                if (method != null)
                {
                    EOperation actEOperation = new EOperation(method, Repository);
                    actEOperation.deserializeFromMocaTree(eOpNode);
                    this.EOperations.Add(actEOperation);
                }
            }
            foreach (MocaNode eAttrNode in attributesNode.Children)
            {
                foreach (SQLAttribute attr in this.EaElement.Attributes)
                {
                    if (attr.Name == eAttrNode.getAttributeOrCreate("name").Value)
                    {
                        EAttribute eAttr = new EAttribute(attr, Repository);
                        eAttr.deserializeFromMocaTree(eAttrNode);
                        this.EAttributes.Add(eAttr);
                    }
                }
            }
            isInitialized = true;
        }

        public override void doEaGuiStuff()
        {
            EA.Element realelement = EaElement.getRealElement();
            realelement.Visibility = "public";
            if (realelement.Stereotype != ECOREModelingMain.EClassStereotype)
            {
                realelement.StereotypeEx = ECOREModelingMain.EClassStereotype;
            }
            realelement.Update();

            if (Import.MainImport.ImportBusy)
            {
                foreach (EAttribute eAttr in this.EAttributes)
                {
                    eAttr.updateEAGui();
                }
                foreach (EOperation eOp in this.EOperations)
                {
                    eOp.updateEAGui();
                }
            }
        }

        public override void refreshSQLObject()
        {
            this.EaElement = Repository.GetElementByID(EaElement.ElementID);
        }
    }
}

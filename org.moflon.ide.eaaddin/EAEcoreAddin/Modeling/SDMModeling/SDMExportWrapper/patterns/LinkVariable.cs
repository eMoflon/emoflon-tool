using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns
{
    public class LinkVariable : Serialization.MocaTaggableElement
    {
        public BindingOperator BindingOperator { get; set; }
        public BindingSemantics BindingSemantics { get; set; }
        public int NacIndex { get; set; }
        public SQLConnector LinkVariableEA { get; private set; }
        public String TargetGuid = "";
        public String SourceGuid = "";
        public LinkDialogueEntry linkDialogueEntry {get;set;}
        public String Guid { get; set; }
        
        public LinkVariable(SQLConnector linkVariable, SQLRepository repository)
        {
            this.LinkVariableEA = linkVariable;
            this.Repository = repository;
            this.linkDialogueEntry = new LinkDialogueEntry();

            this.Name = linkVariable.SupplierEnd.Role;
            this.linkDialogueEntry.supplierRoleName = linkVariable.SupplierEnd.Role;
            this.linkDialogueEntry.clientRoleName = linkVariable.ClientEnd.Role;

            this.NacIndex = -1;
        }

        public override void refreshSQLObject()
        {
            this.LinkVariableEA = Repository.GetConnectorByID(LinkVariableEA.ConnectorID);
        }
        public static String getTextOfReference(SQLConnector con)
        {
            SQLConnectorTag textOfRefTag = EAUtil.findTaggedValue(con, "textOfReference");
            if (textOfRefTag != null)
                return textOfRefTag.Value;
            return "";
        }

        public static String getOppositeTextOfReference(SQLConnector con)
        {
            SQLConnectorTag oppositetextOfRefTag = EAUtil.findTaggedValue(con, "textOfReferenceOpposite");
            if (oppositetextOfRefTag != null)
                return oppositetextOfRefTag.Value;
            return "";
        }

        public static String getIdOfReference(SQLConnector con, SQLRepository rep)
        {
            SQLConnectorTag idOfRefTag = EAUtil.findTaggedValue(con, "idOfReference");
            if (idOfRefTag != null)
            {
                
                int possibleId = 0;
                if (int.TryParse(idOfRefTag.Value, out possibleId))
                {
                    try
                    {
                        SQLConnector refCon = rep.GetConnectorByID(possibleId);
                        return refCon.ConnectorGUID;
                    }
                    catch
                    {
                        return "";
                    }
                    
                }
                return idOfRefTag.Value;
            }
            return "";
        }


        public override void doEaGuiStuff()
        {
            EA.Connector realconnector = LinkVariableEA.getRealConnector();

            if (linkDialogueEntry != null)
            {
                if (linkDialogueEntry.direction == LinkDialogueEntryDirection.LEFT && !Persistency.Export.ExportRunning)
                {
                    int clientID = LinkVariableEA.ClientID;
                    realconnector.ClientID = LinkVariableEA.SupplierID;
                    realconnector.SupplierID = clientID;
                }
            }

            realconnector.Direction = "Unspecified";
            realconnector.StereotypeEx = SDMModelingMain.LinkVariableStereotype;
            realconnector.ClientEnd.Role = this.linkDialogueEntry.clientRoleName;
            realconnector.SupplierEnd.Role = this.linkDialogueEntry.supplierRoleName;
            this.Name = realconnector.SupplierEnd.Role;

            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realconnector, Main.MoflonVerboseTaggedValueName, Main.FalseValue);
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realconnector, ObjectVariable.BindingSemanticsTaggedValueName, this.BindingSemantics.ToString().ToLower());
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realconnector, ObjectVariable.BindingOperatorTaggedValueName, this.BindingOperator.ToString().ToLower());

            if (NacIndex == -1)
                EAUtil.deleteTaggedValue(realconnector, ObjectVariable.NacIndexTaggedValueName);
            else
                EAUtil.setTaggedValue(Repository, realconnector, ObjectVariable.NacIndexTaggedValueName, NacIndex.ToString());

            this.LinkVariableEA.getRealConnector().Update();
        }

        public override object getObjectToBeTagged()
        {
            return this.LinkVariableEA;
        }


        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode linkVariableNode = new MocaNode("LinkVariable");
            linkVariableNode.appendChildAttribute("name", this.linkDialogueEntry.supplierRoleName);
            linkVariableNode.appendChildAttribute("sourceGuid", SourceGuid);
            linkVariableNode.appendChildAttribute("targetGuid", TargetGuid);
            linkVariableNode.appendChildAttribute("bindingOperator", this.BindingOperator.ToString().ToLower());
            linkVariableNode.appendChildAttribute("bindingSemantics", this.BindingSemantics.ToString().ToLower());
            linkVariableNode.appendChildAttribute("sourceName", this.linkDialogueEntry.clientRoleName);
            linkVariableNode.appendChildAttribute(ObjectVariable.NacIndexTaggedValueName, NacIndex.ToString());

            if (this.linkDialogueEntry != null)
            {
                
                linkVariableNode.appendChildAttribute("textOfReference", this.linkDialogueEntry.output);
                linkVariableNode.appendChildAttribute("textOfReferenceOpposite", this.linkDialogueEntry.OutputOpposite);
                linkVariableNode.appendChildAttribute("idOfReference", this.linkDialogueEntry.CorrespondingConnectorGuid);
                linkVariableNode.appendChildAttribute("direction", this.linkDialogueEntry.direction.ToString().ToLower());
            }
            linkVariableNode.appendChildAttribute(Main.GuidStringName, LinkVariableEA.ConnectorGUID);
            return linkVariableNode;
        }

        public override void addAttributesDuringExport(MocaNode linkVariableMocaNode)
        {

            SQLElement target = Repository.GetElementByID(this.LinkVariableEA.SupplierID);
            SQLElement client = Repository.GetElementByID(this.LinkVariableEA.ClientID);

            linkVariableMocaNode.appendChildAttribute("sourceGuid", client.ElementGUID);
            linkVariableMocaNode.appendChildAttribute("targetGuid", target.ElementGUID);
            linkVariableMocaNode.appendChildAttribute("sourceName", LinkVariableEA.ClientEnd.Role);
            linkVariableMocaNode.appendChildAttribute("textOfReference", LinkVariable.getTextOfReference(LinkVariableEA));
            linkVariableMocaNode.appendChildAttribute("textOfReferenceOpposite", LinkVariable.getOppositeTextOfReference(LinkVariableEA));
            String oldRefId = LinkVariable.getIdOfReference(LinkVariableEA, Repository);
            if (oldRefId != "")
                linkVariableMocaNode.appendChildAttribute("idOfReference", oldRefId);

        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {

            this.linkDialogueEntry = new LinkDialogueEntry();
            if(actNode.getAttributeOrCreate(ObjectVariable.NacIndexTaggedValueName).Value != "")
                this.NacIndex = int.Parse( actNode.getAttributeOrCreate(ObjectVariable.NacIndexTaggedValueName).Value);
            this.BindingOperator = (BindingOperator)Enum.Parse(typeof(BindingOperator), actNode.getAttributeOrCreate("bindingOperator").Value.ToUpper());
            this.BindingSemantics = (BindingSemantics)Enum.Parse(typeof(BindingSemantics), actNode.getAttributeOrCreate("bindingSemantics").Value.ToUpper());
            if(actNode.getAttributeOrCreate("targetGuid") != null)
                this.TargetGuid = actNode.getAttributeOrCreate("targetGuid").Value;
            if(actNode.getAttributeOrCreate("sourceGuid") != null)
                this.SourceGuid = actNode.getAttributeOrCreate("sourceGuid").Value;
            this.linkDialogueEntry.supplierRoleName = actNode.getAttributeOrCreate("name").Value;
            if(actNode.getAttributeOrCreate("sourceName") != null)
                this.linkDialogueEntry.clientRoleName = actNode.getAttributeOrCreate("sourceName").Value;

            if (linkDialogueEntry.clientRoleName == "" && LinkVariableEA.ClientEnd.Role != "")
                linkDialogueEntry.clientRoleName = LinkVariableEA.ClientEnd.Role;

            Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;

            if (actNode.getAttributeOrCreate("direction").Value != "")
            {
                try
                {
                    this.linkDialogueEntry.direction = (LinkDialogueEntryDirection)Enum.Parse(typeof(LinkDialogueEntryDirection), actNode.getAttributeOrCreate("direction").Value.ToUpper());
                }
                catch
                {
                }
            }
            if (actNode.getAttributeOrCreate("textOfReference") != null)
                this.linkDialogueEntry.output = actNode.getAttributeOrCreate("textOfReference").Value;
            if (actNode.getAttributeOrCreate("textOfReferenceOpposite") != null)
                this.linkDialogueEntry.OutputOpposite = actNode.getAttributeOrCreate("textOfReferenceOpposite").Value;
            if (actNode.getAttributeOrCreate("idOfReference") != null)
                this.linkDialogueEntry.CorrespondingConnectorGuid = actNode.getAttributeOrCreate("idOfReference").Value;


        }
    }
}

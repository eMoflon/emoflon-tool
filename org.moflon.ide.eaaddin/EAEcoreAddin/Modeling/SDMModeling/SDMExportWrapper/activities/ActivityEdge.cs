using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
	public class ActivityEdge : MocaTaggableElement
	{

        public SQLConnector EaConnector { get; set; }
        String Guid { get; set; }
        public EdgeGuard GuardType { get; set; }
        public String sourceGuid;
        public String targetGuid;


        public SQLElement Source;
        public SQLElement Target;

        public static readonly String TargetGuidAttributeName = "targetGuid";
        public static readonly String SourceGuidAttributeName = "sourceGuid";
        public static readonly String EdgeGuardAttributeName = "edgeGuard";

        public static readonly String EdgeGuardEndGui = "End";
        public static readonly String EdgeGuardSuccessGui = "Success";
        public static readonly String EdgeGuardFailureGui = "Failure";
        public static readonly String EdgeGuardEachTimeGui = "Each Time";



        public ActivityEdge(SQLRepository sqlRepository,SQLConnector eaConnector) 
        {
            this.Repository = sqlRepository;
            this.EaConnector = eaConnector;
            this.Guid = this.EaConnector.ConnectorGUID;
            Source = Repository.GetElementByID(eaConnector.ClientID);
            Target = Repository.GetElementByID(eaConnector.SupplierID);
            this.sourceGuid = Source.ElementGUID;
            this.targetGuid = Target.ElementGUID;
        }

        
        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            String guardValue = "";
            if (GuardType == EdgeGuard.END)
                guardValue = EdgeGuardEndGui;
            else if (GuardType == EdgeGuard.NONE)
                guardValue = "";
            else if (GuardType == EdgeGuard.SUCCESS)
                guardValue = EdgeGuardSuccessGui;
            else if (GuardType == EdgeGuard.FAILURE)
                guardValue = EdgeGuardFailureGui;
            else if (GuardType == EdgeGuard.EACH_TIME)
                guardValue = EdgeGuardEachTimeGui;
            EaConnector.getRealConnector().StereotypeEx = "";
            EaConnector.getRealConnector().TransitionGuard = guardValue;
 
            
            EaConnector.getRealConnector().Update();
            EA.Diagram currentDiagram = Repository.GetCurrentDiagram();
            if (currentDiagram != null)
            {
                Repository.ReloadDiagram(currentDiagram.DiagramID);
            }
        }

        public override object getObjectToBeTagged()
        {
            return this.EaConnector;
        }

        public override void addAttributesDuringExport(MocaNode activityEdgeNode)
        {
            
            SQLElement target = Repository.GetElementByID(this.EaConnector.SupplierID);
            SQLElement client = Repository.GetElementByID(this.EaConnector.ClientID);
            activityEdgeNode.appendChildAttribute("sourceGuid", client.ElementGUID);
            activityEdgeNode.appendChildAttribute("targetGuid", target.ElementGUID);
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode activityEdgeNode = new MocaNode("ActivityEdge");
            activityEdgeNode.appendChildAttribute(SourceGuidAttributeName, this.sourceGuid);
            activityEdgeNode.appendChildAttribute(TargetGuidAttributeName, this.targetGuid);
            activityEdgeNode.appendChildAttribute(EdgeGuardAttributeName, this.GuardType.ToString().ToLower());
            activityEdgeNode.appendChildAttribute(Main.GuidStringName, EaConnector.ConnectorGUID);

            return activityEdgeNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.GuardType = (EdgeGuard)Enum.Parse(typeof(EdgeGuard), actNode.getAttributeOrCreate("edgeGuard").Value.ToUpper());
            Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
        }

        public override void refreshSQLObject()
        {
            this.EaConnector = Repository.GetConnectorByID(EaConnector.ConnectorID);
        }

    }
}

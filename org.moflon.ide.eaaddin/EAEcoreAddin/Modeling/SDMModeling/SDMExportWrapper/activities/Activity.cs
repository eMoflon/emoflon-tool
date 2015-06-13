using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using System.Diagnostics;

using System.Xml;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
   public class Activity : MocaTaggableElement
    {

        public ArrayList OwnedActivityEdge { get; set; }
        public SQLElement SdmContainer { get; set; }
        public ArrayList OwnedActivityNode { get; set; }
        public String OperationString { get; set; }
        public String ReturnType { get; set; }
        public String OperationGUID { get; set; }
        public String Guid { get; set; }

        public Activity(SQLElement sdmContainer,SQLRepository repository)
        {
            this.Repository = repository;
            this.SdmContainer = sdmContainer;
        }

        private EOperation owningOperation;
        public EOperation OwningOperation 
        {
            get
            {
                if (owningOperation == null)
                {
                    SQLTaggedValue methTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(this.SdmContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                    if(methTag != null)
                    {
                        String methodGUID = methTag.Value;
                        SQLMethod method = Repository.GetMethodByGuid(methodGUID);
                        owningOperation = new EOperation(method, Repository);
                        owningOperation.computeAttributes();
                    }
                    return owningOperation;
                }
                return owningOperation;
            }
            set
            {
                this.owningOperation = value;
            }
        }
        


        public override object getObjectToBeTagged()
        {
            return this.SdmContainer;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode activityNode = new MocaNode("Activity");
            activityNode.appendChildAttribute(Main.GuidStringName, SdmContainer.ElementGUID);
            return activityNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;    
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Element realElement = SdmContainer.getRealElement();
            if (realElement.Diagrams.Count > 0)
            {
                EA.Diagram sdmDiagram = realElement.Diagrams.GetAt(0) as EA.Diagram;
                String test = sdmDiagram.ExtendedStyle;
                sdmDiagram.ExtendedStyle = "HideRel=0;ShowTags=0;ShowReqs=0;ShowCons=0;OpParams=1;ShowSN=0;ScalePI=0;PPgs.cx=2;PPgs.cy=2;PSize=9;ShowIcons=1;SuppCN=0;HideProps=0;HideParents=0;UseAlias=0;HideAtts=1;HideOps=1;HideStereo=1;HideEStereo=1;FormName=;";
                sdmDiagram.StyleEx = sdmDiagram.StyleEx + "HideConnStereotype=0;";
                sdmDiagram.Update();
            }
            realElement.StereotypeEx = SDMModelingMain.SdmContainerStereotype;
            realElement.Name = EAUtil.getMethodSignature(OwningOperation.EaMethod);
            realElement.Update();

            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName, OwningOperation.EaMethod.MethodGUID);
        }

        public override void refreshSQLObject()
        {
            this.SdmContainer = Repository.GetElementByID(SdmContainer.ElementID);
        }

    }
}

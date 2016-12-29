using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    public class EOperation : MocaSerializableElement
    {

        public List<EParameter> EParameters { get; set; }
        public SQLMethod EaMethod { get; set; }
        public String typeGuid = "";
        public String guid = "";
        String returnType = "";

        public static readonly String ParametersChildNodeName = "parameters";

        public EOperation(SQLMethod eaMethod, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaMethod = eaMethod;
            this.Name = eaMethod.Name;
            this.EParameters = new List<EParameter>();        
        }

        public void computeAttributes()
        {
            this.EParameters = new List<EParameter>();
            this.returnType = EaMethod.ReturnType;
            foreach (SQLParameter parameter in EaMethod.Parameters)
            {
                EParameter eParameter = new EParameter(parameter, Repository);
                EParameters.Add(eParameter);
            }

            if (this.EaMethod.ClassifierID != "0" && this.EaMethod.ClassifierID != "")
            {
                try
                {
                    SQLElement classifier = Repository.GetElementByID(int.Parse(this.EaMethod.ClassifierID));
                    this.typeGuid = classifier.ElementGUID;
                }
                catch
                {
                }
            }
        }

        
         
        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            actNode.appendChildAttribute("name", this.Name);
            MocaNode parametersNode = actNode.appendChildNode(EOperation.ParametersChildNodeName);
            foreach (EParameter eParameter in this.EParameters)
            {
                MocaNode eParameterNode = parametersNode.appendChildNode("EParameter");
                eParameter.serializeToMocaTree(eParameterNode);
            }
            actNode.appendChildAttribute(Main.GuidStringName, this.EaMethod.MethodGUID);
            actNode.appendChildAttribute("typeGuid", this.typeGuid);
            actNode.appendChildAttribute("returnType", this.returnType);

            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaAttribute guidAttr = actNode.getAttributeOrCreate(Main.GuidStringName);
            MocaAttribute typeGuidAttr = actNode.getAttributeOrCreate("typeGuid");
            this.typeGuid = typeGuidAttr.Value;
            this.guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.returnType = actNode.getAttributeOrCreate("returnType").Value;
            this.Name = actNode.getAttributeOrCreate("name").Value;
            MocaNode parametersNode = actNode.getChildNodeWithName(EOperation.ParametersChildNodeName);
            foreach (MocaNode eParamNode in parametersNode.Children)
            {
                if (eParamNode.Name == "EParameter")
                {
                    foreach (SQLParameter neededParam in this.EaMethod.Parameters)
                    {
                        if (neededParam.Name == eParamNode.getAttributeOrCreate("name").Value)
                        {
                            EParameter eParam = new EParameter(neededParam, Repository);
                            eParam.deserializeFromMocaTree(eParamNode);
                            this.EParameters.Add(eParam);
                        }
                    }
                    

                }
            }

            //only for import purposes
            
        }

        public void updateEAGui()
        {
            this.EaMethod.getRealMethod().ReturnType = this.returnType;
            this.EaMethod.getRealMethod().Name = this.Name;
            this.EaMethod.getRealMethod().Update();

            foreach(EParameter eparam in this.EParameters)
            {
                eparam.updateEAGui();
            }
         }
    }
}

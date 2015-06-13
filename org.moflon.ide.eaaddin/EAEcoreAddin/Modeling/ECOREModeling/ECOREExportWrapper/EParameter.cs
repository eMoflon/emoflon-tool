using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;
using EAEcoreAddin.Import;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    public class EParameter : MocaSerializableElement
    {

        public SQLParameter EaParameter {get;set;}
        public String typeGuid = "";
        public String Guid = "";
        String parameterType = "";
        

        public EParameter(SQLParameter eaParameter, SQLRepository repository) 
        {
            this.Repository = repository;
            this.EaParameter = eaParameter;
            computeAttributes();
        }

    

        public void computeAttributes() 
        {
            this.Name = EaParameter.Name;
            this.parameterType = EaParameter.Type;

            if (this.EaParameter.ClassifierID != "0" && this.EaParameter.ClassifierID != "")
            {
                try
                {
                    SQLElement classifier = Repository.GetElementByID(int.Parse(this.EaParameter.ClassifierID));
                    this.typeGuid = classifier.ElementGUID;
                }
                catch
                {
                   
                }
            }
        }




        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            actNode.appendChildAttribute("type", this.parameterType);
            actNode.appendChildAttribute("name", this.Name);
            actNode.appendChildAttribute("typeGuid", this.typeGuid);
            actNode.appendChildAttribute(Main.GuidStringName, this.EaParameter.ParameterGUID);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.typeGuid = actNode.getAttributeOrCreate("typeGuid").Value;
            this.parameterType = actNode.getAttributeOrCreate("type").Value;
            this.Name = actNode.getAttributeOrCreate("name").Value;
            
        }

        public void updateEAGui()
        {
            this.EaParameter.getRealParameter().Type = this.parameterType;
            this.EaParameter.getRealParameter().Name = this.Name;
            this.EaParameter.getRealParameter().Update();
        }
    }
}

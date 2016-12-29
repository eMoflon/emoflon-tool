using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Persistency.Util;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    public class EAttribute : MocaSerializableElement
    {
        
        public SQLAttribute EaAttribute { get; set; }
        public String guid = "";
        public String typeGuid = "";
        String defaultString = "";
        String ordered = "false";
        String upperBound = "1";
        String lowerBound = "0";
        String isId = "false";
        String isDerived = "false";
        String attributeType = "";

        public EAttribute(SQLAttribute eaAttribute, SQLRepository repository)
        {
            this.EaAttribute = eaAttribute;
            this.Repository = repository;
            this.attributeType = eaAttribute.Type;
            this.Name = eaAttribute.Name;
        }


        public void computeAttributes()
        {

            SQLElement classifier = EAUtil.getClassifierElement(Repository, this.EaAttribute.ClassifierID);
            if (classifier != null)
            {
                this.typeGuid = classifier.ElementGUID;
            }
            else
            {
                String ecoreAdress = PersistencyUtil.getReferenceToBuiltInEcoreType(this.Type);
            }

            lowerBound = this.EaAttribute.LowerBound == "*" ? "-1" : this.EaAttribute.LowerBound;
            if (lowerBound == "")
                lowerBound = "0";

            upperBound = this.EaAttribute.UpperBound == "*" ? "-1" : this.EaAttribute.UpperBound;
            if (upperBound == "")
                upperBound = "1";

            if (this.EaAttribute.IsOrdered)
            { 
                this.ordered = "true";
            }

            if (this.EaAttribute.IsDerived)
            {
                this.isDerived = "true";
            }

            if (this.EaAttribute.getRealAttribute().IsID)
            {
                this.isId = "true";
            }
        }

        public override MocaNode serializeToMocaTree(MocaNode actNode)
        {
            actNode.appendChildAttribute("type", this.attributeType);
            actNode.appendChildAttribute("typeGuid", this.typeGuid); 
            actNode.appendChildAttribute("name", this.Name);
            actNode.appendChildAttribute(Main.GuidStringName, this.EaAttribute.AttributeGUID);
            if(this.EaAttribute.Default != "")
                actNode.appendChildAttribute("defaultValueLiteral", this.EaAttribute.Default);
            actNode.appendChildAttribute("ordered", this.ordered);
            actNode.appendChildAttribute("lowerBound", this.lowerBound);
            actNode.appendChildAttribute("upperBound", this.upperBound);
            actNode.appendChildAttribute("isId", this.isId);
            actNode.appendChildAttribute("isDerived", this.isDerived);

            return actNode;
        }

        public override void deserializeFromMocaTree(MocaNode actNode)
        {
            this.attributeType = actNode.getAttributeOrCreate("type").Value;
            this.typeGuid = actNode.getAttributeOrCreate("typeGuid").Value;
            this.Name = actNode.getAttributeOrCreate("name").Value;
            this.guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.lowerBound = actNode.getAttributeOrCreate("lowerBound").Value;
            this.upperBound = actNode.getAttributeOrCreate("upperBound").Value;
            this.ordered = actNode.getAttributeOrCreate("ordered").Value;
            this.isId = actNode.getAttributeOrCreate("isId").Value != "" ? actNode.getAttributeOrCreate("isId").Value : "false";
            this.isDerived = actNode.getAttributeOrCreate("isDerived").Value != "" ? actNode.getAttributeOrCreate("isDerived").Value : "false";
            MocaAttribute defAttr = actNode.getAttributeOrCreate("defaultLiteralValue");
            if (defAttr != null)
                this.defaultString = defAttr.Value;
        }

        public void updateEAGui()
        {
            this.EaAttribute.getRealAttribute().Name = this.Name;
            if (this.attributeType != "")
            {
                this.EaAttribute.getRealAttribute().Type = this.attributeType;
            } 
            this.EaAttribute.getRealAttribute().Update();
        }
    }
}

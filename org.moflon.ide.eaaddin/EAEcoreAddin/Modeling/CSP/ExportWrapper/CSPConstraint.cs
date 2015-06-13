using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.CSP.ExportWrapper
{
    public class CSPConstraint : MocaSerializableElement
    {
        public int Index { get; set; }
        public Boolean UserDefined { get; set; }
        private String informationText;
        public List<String> SignatureInformation { get; set; }
        public List<String> Signature { get; set; }
        public List<String> adornments = new List<string>();
        public List<String> modelgenAdornments = new List<string>();

        public static readonly string allowedAdornmentsNodeName = "ALLOWED_ADORNMENTS";
        public static readonly string modelgenAdornmentsNodeName = "MODELGEN_ADORNMENTS";
        public static readonly string signatureNodeName = "SIGNATURE";
        public static readonly string signatureInformationNodeName = "SIGNATURE_INFORMATION";
        public static readonly string informationTextNodeName = "INFORMATION_TEXT";
        public static readonly string userDefinedNodeName = "USER_DEFINED";
        public static readonly string indexNodeName = "INDEX";


        public CSPConstraint(String name, String[] parameterTypes, String[] adornments, String[] modelgenAdornments)
            : this(name,adornments, parameterTypes)
        {
            this.modelgenAdornments.AddRange(modelgenAdornments);
        }

        public CSPConstraint(String name, String[] adornments, String[] parameterTypes) : this()
        {
            this.Name = name;
            this.adornments.AddRange(adornments);
            this.Signature.AddRange(parameterTypes);
            
        }

        public CSPConstraint()
        {
            this.Signature = new List<string>();
            this.SignatureInformation = new List<string>();
            this.informationText = "";
            this.UserDefined = false;
            this.Name = "";
        }

        public String getConstraintInformationSummary()
        {
            return generateInformationSummary();
        }


      
        public void setConstraintInformation(String info)
        {
            this.informationText = info;
        }

        private String generateInformationSummary()
        {
            String adornmentsString = genAdornmentString(adornments);
            String modelgenAdornmentsString = genAdornmentString(modelgenAdornments);

            return "Allowed Adornments: " + adornmentsString + " \r\n" + 
                "Modelgen Adornments: " + modelgenAdornmentsString + " \r\n" +
                this.informationText;
        }

        private string genAdornmentString(List<String> adornments)
        {
            String adornmentsString = "";
            foreach (String adornment in adornments)
            {
                adornmentsString += adornment + ",";
            }
            if (adornmentsString.Length > 0)
            {
                adornmentsString = adornmentsString.Substring(0, adornmentsString.Length - 1);
            }
            return adornmentsString;
        }

        public override string ToString()
        {
            String adornments = "";
            foreach (String adornment in this.adornments)
            {
                adornments += adornment + ", ";
            }
            adornments = "[" + adornments.Substring(0, adornments.Length - 2) + "]";
            String types = "";
            foreach (String type in this.Signature)
            {
                types += type + ",";
            }
            if (types.Length > 0)
            {
                types = "(" + types.Substring(0, types.Length - 1) + ")";
            }
            return this.Name + adornments + types ;
        }


        

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(Serialization.MocaTree.MocaNode actNode)
        {

            actNode.Name = this.Name;

            MocaNode adornmentsNode = actNode.appendChildNode(allowedAdornmentsNodeName);
            MocaNode modelgenAdornmentsNode = actNode.appendChildNode(modelgenAdornmentsNodeName);
            MocaNode signatureNode = actNode.appendChildNode(signatureNodeName);
            MocaNode signatureInformationNode = actNode.appendChildNode(signatureInformationNodeName);

            addListEntriesToNode(adornments, adornmentsNode);
            addListEntriesToNode(modelgenAdornments, modelgenAdornmentsNode);
            addListEntriesToNode(Signature, signatureNode);
            addListEntriesToNode(SignatureInformation, signatureInformationNode);
            actNode.appendChildAttribute(informationTextNodeName, this.informationText);
            actNode.appendChildAttribute(userDefinedNodeName, this.UserDefined.ToString().ToLower());
            actNode.appendChildAttribute(indexNodeName, this.Index.ToString());
            return actNode;
        }

        

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            //for old moca trees
            tryOldDeserialize(actNode);

            MocaNode adornmentsNode = actNode.getChildNodeWithName(allowedAdornmentsNodeName);
            MocaNode modelgenAdornmentsNode = actNode.getChildNodeWithName(modelgenAdornmentsNodeName);
            MocaNode signatureNode = actNode.getChildNodeWithName(signatureNodeName);
            MocaNode signatureInformationNode = actNode.getChildNodeWithName(signatureInformationNodeName);

            addNodeChildrenToList(adornmentsNode, adornments);
            addNodeChildrenToList(modelgenAdornmentsNode, modelgenAdornments);
            addNodeChildrenToList(signatureNode, Signature);
            addNodeChildrenToList(signatureInformationNode, SignatureInformation);

            String infoValue = actNode.getAttributeOrCreate(informationTextNodeName).Value;
            if(infoValue != "") {
                this.informationText = infoValue;
            }

            String userDefinedValue = actNode.getAttributeOrCreate(userDefinedNodeName).Value;
            if (userDefinedValue != "")
            {
                this.UserDefined = userDefinedValue == "true";
            }
            if (actNode.Name != "CSPConstraint")
            {
                this.Name = actNode.Name;
            }
            MocaAttribute indexAttr = actNode.getAttributeOrCreate(indexNodeName);
            if (indexAttr.Value != "")
            {
                this.Index = int.Parse(indexAttr.Value);
            }

        }


        private void addNodeChildrenToList(MocaNode parentNode, List<String> listToAdd)
        {
            if (parentNode != null)
            {
                foreach (MocaNode childToAdd in parentNode.Children)
                {
                    listToAdd.Add(childToAdd.Name);
                }
            }
        }

        private void addListEntriesToNode(List<String> listEntries, MocaNode nodeToAdd)
        {
            foreach (string entry in listEntries)
            {
                nodeToAdd.appendChildNode(entry);
            }
        }


        private bool tryOldDeserialize(Serialization.MocaTree.MocaNode actNode)
        {

            MocaNode adornmentsNode = actNode.getChildNodeWithName("allowedAddornments");
            MocaNode modelgenAdornmentsNode = actNode.getChildNodeWithName("modelgenAdornments");
            MocaNode parameterInformationNode = actNode.getChildNodeWithName("parameterInformation");
            MocaNode parameterTypesNode = actNode.getChildNodeWithName("parameterTypes");

            addNodeChildrenToList(adornmentsNode, adornments);
            addNodeChildrenToList(modelgenAdornmentsNode, modelgenAdornments);
            addNodeChildrenToList(parameterInformationNode, SignatureInformation);
            addNodeChildrenToList(parameterTypesNode, Signature);


            this.informationText = actNode.getAttributeOrCreate("constraintInformation").Value;
            if (this.informationText.Contains("\r\n"))
            {
                String[] splitted = this.informationText.Split("\r\n".ToCharArray());
                this.informationText = splitted[splitted.Length - 1];
            }
            this.UserDefined = actNode.getAttributeOrCreate("userDefined").Value == "true";
            this.Name = actNode.getAttributeOrCreate("name").Value;
            MocaAttribute indexAttr = actNode.getAttributeOrCreate("index");
            if (indexAttr.Value != "")
            {
                this.Index = int.Parse(indexAttr.Value);
            }

            return true;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;

using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions
{
    public class MethodCallExpression : Expression
    {
        public const String DESCRIPTION = "MethodCallExpression";

        public List<ParameterBinding> OwnedParameterBinding { get; set; }

        public Expression Target { get; set; }        
        public SQLMethod Method { get; set; }
        public String MethodName { get; set; }
        public String MethodNameOld { get; set; }
        public String MethodGuid { get; set; }

        public String MethodReturnType { get; set; }

        public MethodCallExpression(SQLMethod calleeMethod, SQLRepository repository)
        {
            this.Repository = repository;
            this.Method = calleeMethod;
            this.MethodName = calleeMethod.Name;
            this.MethodGuid = calleeMethod.MethodGUID;
            this.MethodReturnType = calleeMethod.ReturnType;
            this.Type = DESCRIPTION;
            OwnedParameterBinding = new List<ParameterBinding>();
        }

        public MethodCallExpression(SQLRepository repository)
        {
            this.Type = DESCRIPTION;
            this.Repository = repository;
            OwnedParameterBinding = new List<ParameterBinding>();
        }


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            base.serializeToMocaTree(actNode);
            MocaNode targetNode = actNode.appendChildNode("target");
            actNode.appendChildAttribute("methodGuid", this.MethodGuid);
            actNode.appendChildAttribute("methodName", this.MethodName);
            actNode.appendChildAttribute("methodReturnType", this.MethodName);
            this.Target.serializeToMocaTree(targetNode);
            foreach(ParameterBinding parameterBinding in this.OwnedParameterBinding) 
            {
                MocaNode pbNode = actNode.appendChildNode("ownedParameterBinding");
                parameterBinding.serializeToMocaTree(pbNode);
            }

            return actNode;
           
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {

            this.MethodGuid = actNode.getAttributeOrCreate("methodGuid").Value;
            this.MethodNameOld = actNode.getAttributeOrCreate("methodName").Value;
            this.MethodReturnType = actNode.getAttributeOrCreate("methodReturnType").Value;

            SQLMethod mt = Repository.GetMethodByGuid(this.MethodGuid);
            if (mt != null)
                this.MethodName = mt.Name;
            else
                this.MethodName = MethodNameOld;
            MocaNode targetNode = actNode.getChildNodeWithName("target");
            this.Target = Expression.createExpression(targetNode.getAttributeOrCreate("type").Value, Repository);
            this.Target.deserializeFromMocaTree(targetNode);
            foreach (MocaNode childNode in actNode.Children)
            {
                if (childNode.Name == "ownedParameterBinding")
                {
                    ParameterBinding paramBinding = new ParameterBinding(Repository);
                    paramBinding.deserializeFromMocaTree(childNode);
                    this.OwnedParameterBinding.Add(paramBinding);
                }
            }
        }

        public override string ToString()
        {
            String parametersOutput = "";
            foreach (ParameterBinding parameterBinding in OwnedParameterBinding)
            {
                parametersOutput += parameterBinding.ValueExpression.ToString() + ",";
            }
            if (parametersOutput != "")
                parametersOutput = parametersOutput.Remove(parametersOutput.Length - 1, 1);
            return this.Target.ToString() + "." + this.MethodName + "(" + parametersOutput + ")";
        }
    }
}

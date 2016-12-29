using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
    public class StopNode : ActivityNode
    {

        public static readonly int DefaultWidth = 25;
        public static readonly int DefaultHeight = 25;


        public Expression ReturnValue { get; set; }
     
        public StopNode(SQLRepository repository, SQLElement activityNode)
            : base(repository, activityNode)
        {
            this.Repository = repository;
        }


        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Element realElement = ActivityNodeEAElement.getRealElement();
            realElement.StereotypeEx = SDMModelingMain.StopNodeStereotype;
            if (this.ReturnValue == null)
                realElement.Name = "";
            else
                realElement.Name = this.ReturnValue.ToString();
            realElement.Update();
        }



        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode stopNode = base.serializeToMocaTree(); 
            stopNode.appendChildAttribute("type", "stop");
            if (this.ReturnValue != null)
            {
                MocaNode ret = stopNode.appendChildNode("returnValue");
                this.ReturnValue.serializeToMocaTree(ret);
                Name = ReturnValue.ToString();
            }
            return stopNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);
            MocaNode returnValueNode = actNode.getChildNodeWithName("returnValue");
            if (returnValueNode != null)
            {
                MocaAttribute typeAttribute = returnValueNode.getAttributeOrCreate("type");
                Expression returnValueExpression = Expression.createExpression(typeAttribute.Value, this.Repository);
                if (returnValueExpression != null)
                {
                    returnValueExpression.deserializeFromMocaTree(returnValueNode);
                }
                else
                {
                
                }
                this.ReturnValue = returnValueExpression;
            }
        }
    }
}

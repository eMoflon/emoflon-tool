using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Xml;

using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns
{
    public class Constraint : MocaSerializableElement
    {

        public Constraint(SQLRepository repository)
        {
            this.Repository = repository;
        }

        public ComparisonExpression ConstraintExpression { get; set; }



        public override String ToString()
        {
            return this.ConstraintExpression.ToString();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode constExp = actNode.appendChildNode("constraintExpression");
            this.ConstraintExpression.serializeToMocaTree(constExp);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaNode constExpNode = actNode.getChildNodeWithName("constraintExpression");
            this.ConstraintExpression = Expression.createExpression(constExpNode.getAttributeOrCreate("type").Value, Repository) as ComparisonExpression;
            this.ConstraintExpression.deserializeFromMocaTree(constExpNode);
        }
    }

}
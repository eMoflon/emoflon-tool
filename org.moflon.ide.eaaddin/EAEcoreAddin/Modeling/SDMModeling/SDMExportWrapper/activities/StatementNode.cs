using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
    public class StatementNode : ActivityNode
    {
        public Expression StatementExpression { get; set; }

        public StatementNode(SQLRepository repository, SQLElement activity) : base (repository,activity)
        {
            this.Repository = repository;
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Element realElement = ActivityNodeEAElement.getRealElement();
            if (StatementExpression == null)
            {
                realElement.Notes = "";
                realElement.Update();
            }
            else
            {
                realElement.Notes = StatementExpression.ToString();
                //diagramObject.right = diagramObject.left + 20 + longestConstraint * 5;

                foreach (String id in EAUtil.getDiagramIDsOfObject(ActivityNodeEAElement, Repository))
                {
                    if (id != "")
                    {
                        EA.Diagram currentDiagram = Repository.GetOriginalRepository().GetDiagramByID(int.Parse(id));
                        if (currentDiagram != null)
                        {
                            EA.DiagramObject diagramObject = EAEcoreAddin.Util.EAUtil.findDiagramObject(Repository, realElement, currentDiagram);
                            if (diagramObject != null)
                            {
                                diagramObject.right = diagramObject.left + 20 + StatementExpression.ToString().Length * 5;
                                diagramObject.bottom = diagramObject.top - 80;
                                diagramObject.Update();
                            }
                        }
                    }
                }

            }
            realElement.Stereotype = SDMModelingMain.StatementNodeStereotype;
            realElement.Update();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode statementNode = base.serializeToMocaTree();
            statementNode.appendChildAttribute("type", "statement");
            if (this.StatementExpression != null)
            {
                MocaNode stat = statementNode.appendChildNode("statementExpression");
                this.StatementExpression.serializeToMocaTree(stat);
            }
            return statementNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);
            MocaNode statement = actNode.getChildNodeWithName("statementExpression");
            if (statement != null)
            {
                MocaAttribute typeAttribute = statement.getAttributeOrCreate("type");
                if (typeAttribute != null)
                {
                    this.StatementExpression = Expression.createExpression(typeAttribute.Value, this.Repository);
                    this.StatementExpression.deserializeFromMocaTree(statement);
                }
            }
        }

        public override void refreshSQLObject()
        {
            this.ActivityNodeEAElement = Repository.GetElementByID(ActivityNodeEAElement.ElementID);
        }

    }
}

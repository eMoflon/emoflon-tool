using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Common.TreeView
{
    public class ExpressionTreeNode : TreeNode
    {

        Expression expression;
        public ExpressionTreeNode(Expression exp, String text) : base(text)
        {
            this.expression = exp;
        }

        public Expression Expression
        {
            get
            {
                return expression;
            }
        }

    }
}

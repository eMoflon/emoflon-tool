using EAEcoreAddin.Modeling.SDMModeling.Gui.Common.TreeView;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Common
{
    class CommonUtil
    {


        public static TreeNode computeTreeNode(Expression expression)
        {
            if (expression != null)
            {
                TreeNode newNode = new ExpressionTreeNode(expression, expression.ToString());
                if (expression is MethodCallExpression)
                {
                    MethodCallExpression mCe = expression as MethodCallExpression;
                    if (mCe.MethodReturnType != "")
                    {
                        newNode.Text += ": " + mCe.MethodReturnType;
                    }
                    foreach (ParameterBinding pbinding in mCe.OwnedParameterBinding)
                    {
                        TreeNode pBindingTreeNode = computeTreeNode(pbinding.ValueExpression);
                        if (pbinding.ParameterType != "")
                        {
                            pBindingTreeNode.Text += ": " + pbinding.ParameterType;
                        } 
                        newNode.Nodes.Add(pBindingTreeNode);
                    }
                }
                return newNode;
            }
            return null;
        }

    }
}

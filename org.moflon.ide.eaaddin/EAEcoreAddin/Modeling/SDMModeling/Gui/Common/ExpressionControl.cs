using EAEcoreAddin.ControlPanel.Navigation;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common.TreeView;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using EAEcoreAddin.Modeling.SDMModeling.Gui.CreateSDMDialog;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui
{
    public partial class ExpressionControl : UserControl
    {
        IExpressionProvider expressionProvider;
        SQLRepository repository;


        private bool userChanged = true;
        private bool informationLossPossible = false;
        public ExpressionControl()
        {
            InitializeComponent();
        }

        public void initializeInformation(IExpressionProvider expressionProvider, Expression expression, SQLRepository repository)
        {
            this.expressionProvider = expressionProvider;
            this.repository = repository;
            
            userChanged = false;
            this.cmbExpressions.Items.Clear();
            this.cmbExpressions.Items.AddRange(expressionProvider.getExpressionStringList().ToArray());
            if (cmbExpressions.Items.Count > 0)
            {
                this.cmbExpressions.SelectedIndex = 0;
            }
            

            setVisualOutput(expressionProvider, expression);

            userChanged = true;
        
        }



        public void setVisualOutput(IExpressionProvider provider, Expression expression)
        {
            userChanged = false;
            this.expressionProvider = provider;
            //get default data from provider

            ExpressionControlData data = GUIUtil.getExpressionStringRepresentation(provider, expression, repository);
            if (data.Expression != "")
            {
                if (this.cmbExpressions.Text != data.Expression)
                {
                    this.cmbExpressions.Text = data.Expression;
                }
                else
                {
                    this.cmbExpressions_SelectedIndexChanged(null, null);
                }
            }
            if (data.First != "")
            {
                if (this.cmbFirstObjects.Text != data.First)
                {
                    this.cmbFirstObjects.Text = data.First;
                }
                else
                {
                    this.cmbFirstObjects_SelectedIndexChanged(null, null);
                }
            }
            if (data.Second != "")
            {
                if (this.cmbSecondObjects.Text != data.Second)
                {
                    this.cmbSecondObjects.Text = data.Second;
                }
                else
                {
                    cmbSecondObjects_SelectedIndexChanged(null, null);
                }
            }
            if (expression != null)
            {
                provider.setExpression(expression);
            }
            updateTreeView();
            userChanged = true;
        }

        LiteralExpressionForm literalExpressionForm;

        private void cmbExpressions_SelectedIndexChanged(object sender, EventArgs e)
        {

            setInformationLossBoolean();

            object previousFirstObject = this.cmbFirstObjects.SelectedItem;
            object previousSecondObject = this.cmbSecondObjects.SelectedItem;

            this.cmbFirstObjects.Items.Clear();
            //this.cmbSecondObjects.Items.Clear();
            this.cmbFirstObjects.Items.AddRange(expressionProvider.getFirstStringList(cmbExpressions.Text).ToArray());
            cmbFirstObjects.DropDownWidth = EAUtil.computeDropDownWidth(cmbFirstObjects);

            if (cmbFirstObjects.Items.Count > 0)
            {
                if (previousFirstObject != null && this.cmbFirstObjects.Items.Contains(previousFirstObject))
                {
                    this.cmbFirstObjects.SelectedItem = previousFirstObject;
                }
                else
                {
                    this.cmbFirstObjects.SelectedIndex = 0;
                }                
            }

            if (cmbExpressions.Text == "MethodCallExpression")
            {
                grpFirst.Enabled = true;
                grpSecond.Enabled = true;
                grpFirst.Text = "Target";
                grpSecond.Text = "Method";
                buttonToMethodCallExpression.Parent.Enabled = true;
            }
            else
            {
                buttonToMethodCallExpression.Parent.Enabled = false;
            }

            if (cmbExpressions.Text == "ObjectVariableExpression")
            {
                grpFirst.Enabled = true;
                grpSecond.Enabled = false;
                grpFirst.Text = "Object";
            }
            else if (cmbExpressions.Text == "ParameterExpression")
            {
                grpFirst.Enabled = true;
                grpSecond.Enabled = false;
                grpFirst.Text = "Parameter";
            }
            else if (cmbExpressions.Text == "LiteralExpression")
            {
                grpFirst.Enabled = false;
                grpSecond.Enabled = false;
            }
            else if (cmbExpressions.Text == "AttributeValueExpression")
            {
                grpFirst.Enabled = true;
                grpSecond.Enabled = true;
                grpFirst.Text = "Target";
                grpSecond.Text = "Attribute";

            }

            updateTreeView();


            

        }


        private void handleLiteralExpressionForm(Expression expression)
        {
            if (cmbExpressions.Text == "LiteralExpression")
            {
                literalExpressionForm = new LiteralExpressionForm((expression as LiteralExpression).Value, repository);
                if (literalExpressionForm.ShowDialog() == DialogResult.OK)
                {
                    expressionProvider.setExpression(new LiteralExpression(literalExpressionForm.getText()));
                    updateTreeView();
                }
            }
        }

        private void handleLiteralExpressionForm()
        {
           
        }

        private void cmbFirstObjects_SelectedIndexChanged(object sender, EventArgs e)
        {

            setInformationLossBoolean();

            object previouslySelectedSecondItem = this.cmbSecondObjects.SelectedItem;
            this.cmbSecondObjects.Items.Clear();
            this.cmbSecondObjects.Items.AddRange(expressionProvider.getSecondStringList(cmbFirstObjects.SelectedIndex).ToArray());

            if (this.cmbSecondObjects.Items.Count > 0)
            {
                if (previouslySelectedSecondItem != null && this.cmbSecondObjects.Items.Contains(previouslySelectedSecondItem)){
                    this.cmbSecondObjects.SelectedItem = previouslySelectedSecondItem;
                }
                else
                { 
                    this.cmbSecondObjects.SelectedIndex = 0;
                }
            }

            this.cmbSecondObjects.DropDownWidth = EAUtil.computeDropDownWidth(this.cmbSecondObjects);

            updateTreeView();
        }

        private void updateTreeView()
        {
            treeView1.Nodes.Clear();
            TreeNode expressionTreeNode = CommonUtil.computeTreeNode(expressionProvider.getExpression());
            if (expressionTreeNode != null)
            {
                treeView1.Nodes.Add(expressionTreeNode);
            }
            treeView1.ExpandAll();
        }

        private void cmbSecondObjects_SelectedIndexChanged(object sender, EventArgs e)
        {
            setInformationLossBoolean();

            expressionProvider.setSelectedSecondString(cmbSecondObjects.SelectedIndex);
            updateTreeView();
        }


        private void buttonToMethodCallExpression_Click(object sender, EventArgs e)
        {

            Object selectedSource = expressionProvider.getSelectedSecond();
            if (selectedSource is SDMExpressionHelper.SecondObject)
            {
                SDMExpressionHelper.SecondObject secondObject = selectedSource as SDMExpressionHelper.SecondObject;

                if (secondObject.eaObject is SQLMethod)
                {
                    SQLMethod selectedOperation = secondObject.eaObject as SQLMethod;

                    if (selectedOperation != null)
                    {

                        SQLMethod method = repository.GetMethodByID(selectedOperation.MethodID);
                        EA.Diagram currentDiagram = repository.GetCurrentDiagram();
                        int sdmDiagramId = SDMUtil.getSDMDiagramID(repository, method);
                        if (sdmDiagramId != SDMUtil.UNKNOWN_SDM_DIAGRAM_ID)
                        {
                            Navigator.addAnchorEntry(sdmDiagramId, currentDiagram.DiagramID);
                            repository.OpenDiagram(sdmDiagramId);
                            this.ParentForm.Close();
                        }
                        else
                        {
                            CreateSDMDialog.CreateSDMDialog createSdmDialog = new CreateSDMDialog.CreateSDMDialog();
                            if (createSdmDialog.ShowDialog() == DialogResult.OK)
                            {
                                SDMModelingMain.createStoryDiagram(method, repository.eaRepository, currentDiagram);
                                this.ParentForm.Close();
                            }
                        }
                    }
                }
            }
        }


        internal string getSelectedExpressionText()
        {
            return cmbExpressions.Text;
        }

        private void treeView1_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            setInformationLossBoolean();
            ExpressionTreeNode clickedNode = e.Node as ExpressionTreeNode;

            if (e.Node.Parent != null)
            {
                ExpressionReturnForm returnForm = new ExpressionReturnForm(expressionProvider.getMainProvider(), clickedNode.Expression, repository);
                if (returnForm.ShowDialog() == DialogResult.OK)
                {
                    Expression newExpression = returnForm.getUnderlyingExpression();
                    ((clickedNode.Parent as ExpressionTreeNode).Expression as MethodCallExpression).OwnedParameterBinding[clickedNode.Index].ValueExpression = newExpression;

                    updateTreeView();
                }
            }
            handleLiteralExpressionForm(clickedNode.Expression);
        }

        

        private void treeView1_BeforeCollapse(object sender, TreeViewCancelEventArgs e)
        {
            e.Cancel = true;
        }

        private void setInformationLossBoolean()
        {
            if (userChanged)
            {
                informationLossPossible = true;
            }
        }

        internal bool isInformationLossPossible()
        {
            return informationLossPossible;
        }


        internal void setInformationLossPossible(bool b)
        {
            this.informationLossPossible = b;
        }


    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    class CSPDefinitionDataGridView : DataGridView
    {

        SQLRepository repository;

        // public List<String> TypedValues = new List<string>();
        public DataTable DataTable = new DataTable();

        private List<Expression> typedExpressions;
        private CSPController controller;

        public List<Expression> getTypedExpressions()
        {
            //if there are no created Expressions create simple LiteralExpressions
            if (typedExpressions.Count < Rows.Count)
            {
                for (int i = 0; i < Rows.Count; i++)
                {
                    if (Rows[i].Cells[0].Value.ToString() != "")
                    {
                        typedExpressions.Add(new LiteralExpression(Rows[i].Cells[0].Value.ToString()));
                    }
                    else
                    {
                        typedExpressions.Add(null);
                    }
                }
            }
            return typedExpressions;
        }


        public List<String> getTypedInValues()
        {
            List<String> values = new List<string>();
            foreach (DataGridViewRow actRow in this.Rows)
            {
                values.Add(actRow.Cells[0].Value as String);
            }
            return values;
        }

        public void clearRows()
        {
            DataTable.Rows.Clear();
            typedExpressions.Clear();
        }

        public void setCellValue(String value, int rowIndex)
        {
            this.Rows[rowIndex].Cells[0].Value = value;
        
        }


        public void addRow(String value,String information)
        {

            DataTable.Rows.Add(value, information);
            //typedExpressions.Add(new LiteralExpression(value));
        }

        public void addTypedExpression(Expression typedExpression)
        {
            typedExpressions.Add(typedExpression);
        }

        public void resetTypedExpressions()
        {
            typedExpressions.Clear();
        }

        public void initializeComponent(SQLRepository repository, CSPController controller)
        {
            this.controller = controller;
            this.repository = repository;
            this.AllowUserToAddRows = false;
            DataGridViewRow templateRow = new DataGridViewRow();
            templateRow.Height = 15;
            this.RowTemplate = templateRow;

            this.typedExpressions = new List<Expression>();

            this.GridColor = System.Drawing.SystemColors.ControlLight;
            this.AllowUserToAddRows = false;
            this.AllowUserToDeleteRows = false;
            this.AllowUserToResizeRows = false;
            this.MultiSelect = false;
            this.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.BackgroundColor = System.Drawing.SystemColors.ControlLightLight;
            this.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.MultiSelect = false;
            this.RowHeadersVisible = false;
            this.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;

            this.CellDoubleClick += new DataGridViewCellEventHandler(ParametersDataGridView_CellDoubleClick);
            this.SelectionChanged += new EventHandler(ParametersDataGridView_SelectionChanged);
            this.CellEndEdit += new DataGridViewCellEventHandler(CSPConstraintDataGridView_CellEndEdit);
           
            this.TabIndex = 24;

            DataTable.Columns.Add("Value");
            DataTable.Columns.Add("Information");
            

            DataTable.AcceptChanges();
            this.DataSource = DataTable;
            this.Columns[0].FillWeight = 0.7F;
            this.Columns[0].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            this.Columns[0].SortMode = DataGridViewColumnSortMode.NotSortable;
            this.Columns[0].ReadOnly = false;

            this.Columns[1].FillWeight = 0.3F;
            this.Columns[1].SortMode = DataGridViewColumnSortMode.NotSortable;
            this.Columns[1].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            this.Columns[1].ReadOnly = true;


        }

        void CSPConstraintDataGridView_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            int newExpressionIndex = CurrentRow.Index;
            Expression lit = new LiteralExpression(Rows[newExpressionIndex].Cells[0].Value as String);
            if (typedExpressions.Count > newExpressionIndex)
            {
                Object typedExpression = typedExpressions[newExpressionIndex];
                if (typedExpression == null || typedExpression.ToString() != lit.ToString())
                {
                    typedExpressions[CurrentRow.Index] = lit;
                }
            }

        
        }



        private void ParametersDataGridView_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (this.CurrentRow != null)
            {
                this.CurrentCell = this.CurrentRow.Cells[0];

                //open expression form
                ExpressionReturnForm expressionForm = null;

                Expression selectedExpression = null;
                if (getTypedExpressions().Count > CurrentRow.Index)
                    selectedExpression = getTypedExpressions()[CurrentRow.Index];



                expressionForm = new ExpressionReturnForm(controller.getExpressionProvider(), selectedExpression, repository);
                
                if (expressionForm.ShowDialog() == DialogResult.OK)
                {
                    //replace old expression with newly created one
                    Expression createdExpression = expressionForm.getUnderlyingExpression();

                    DataTable.Rows[CurrentRow.Index][0] = createdExpression.ToString();
                    typedExpressions[CurrentRow.Index] = createdExpression; 
                }
            }
        }


        private void ParametersDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (this.CurrentRow != null)
            {
                if (this.CurrentCell != this.CurrentRow.Cells[0])
                {
                    this.CurrentCell = this.CurrentRow.Cells[0];
                }
            }
        }



    }
}

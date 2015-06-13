using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data;

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    class NewCSPDefDataGridView : DataGridView
    {
        // public List<String> TypedValues = new List<string>();

        String Type { get; set; }

        public DataTable DataTable = new DataTable();

        public void addValue(params String[] values) 
        {
            DataTable.Rows.Add(values);
            DataTable.AcceptChanges();
        }

        public List<String> getTypedInParameterInformation()
        {
            List<String> values = new List<string>();
            foreach (DataGridViewRow actRow in this.Rows)
            {
                if ((actRow.Cells[1].Value as String) != null)
                    values.Add(actRow.Cells[1].Value as String);
                else
                    values.Add("");
            }
            return values;
        }

        public List<String> getTypedInParameterTypes()
        {
            List<String> values = new List<string>();
            foreach (DataGridViewRow actRow in this.Rows)
            {
                if ((actRow.Cells[0].Value as String) != null)
                    values.Add(actRow.Cells[0].Value as String);
            }
            return values;
        }

        public List<String> getTypedInAddornments()
        {
            List<String> values = new List<string>();
            foreach (DataGridViewRow actRow in this.Rows)
            {
                if((actRow.Cells[0].Value as String) != null)
                    values.Add(actRow.Cells[0].Value as String);
            }
            return values;
        }

        public void clearRows()
        {
            DataTable.Rows.Clear();
        }

        public void setCellValue(String value, int rowIndex)
        {
            this.Rows[rowIndex].Cells[0].Value = value;
        }


        public void initializeComponent(String type)
        {
            this.Type = type;
            this.AllowUserToAddRows = false;
            DataGridViewRow templateRow = new DataGridViewRow();
            templateRow.Height = 15;
            this.RowTemplate = templateRow;

            this.GridColor = System.Drawing.SystemColors.ControlLight;
            this.AllowUserToAddRows = true;
            this.AllowUserToDeleteRows = true;
            this.AllowUserToResizeRows = false;
            this.MultiSelect = false;
            this.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.BackgroundColor = System.Drawing.SystemColors.ControlLightLight;
            this.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.MultiSelect = false;
            this.RowHeadersVisible = false;
            this.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;

          //  this.CellDoubleClick += new DataGridViewCellEventHandler(ParametersDataGridView_CellDoubleClick);
            //this.SelectionChanged += new EventHandler(ParametersDataGridView_SelectionChanged);
            this.TabIndex = 24;


            if (type == "adornment")
            {
                DataTable.Columns.Add("Value");
                DataTable.AcceptChanges();
                this.DataSource = DataTable;
                this.Columns[0].Width = this.Width - 3;
                this.Columns[0].SortMode = DataGridViewColumnSortMode.NotSortable;
                this.Columns[0].ReadOnly = false;
            }

            else if (type == "parameter")
            {
                DataTable.Columns.Add("Type");
                DataTable.Columns.Add("Information");


                DataTable.AcceptChanges();
                this.DataSource = DataTable;
                this.Columns[0].Width = this.Width / 4;
                this.Columns[0].SortMode = DataGridViewColumnSortMode.NotSortable;
                this.Columns[0].ReadOnly = false;

                this.Columns[1].Width = 3 * (this.Width / 4) - 3;
                this.Columns[1].SortMode = DataGridViewColumnSortMode.NotSortable;
                this.Columns[1].ReadOnly = false;
            }

            


        }


        private void ParametersDataGridView_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (this.CurrentRow != null)
            {
                this.CurrentCell = this.CurrentRow.Cells[0];
                BeginEdit(false);

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

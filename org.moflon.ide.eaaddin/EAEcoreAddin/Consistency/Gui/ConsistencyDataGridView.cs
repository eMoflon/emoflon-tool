using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Drawing;
using EAEcoreAddin.Modeling.SDMModeling.Util;

namespace EAEcoreAddin.Consistency.RuleHandling
{
    class ConsistencyDataGridView : DataGridView
    {
        public DataTable dataTable = new DataTable();
        NewErrorOutput parent;
        bool handled = false;


        public void addRow(String ruleId, String objectName, String message, String errorLevel)
        {
            handled = true;

            String ruleIdString = ruleId;
            if (ruleIdString == "")
            {
                String[] split = message.Split(' ');
                foreach (String splitEntry in split)
                {
                    ruleIdString += splitEntry.Substring(0, 1).ToUpper() + splitEntry.Substring(1, splitEntry.Length - 1);
                }
            
            }
            dataTable.Rows.Add(ruleIdString, objectName, message, errorLevel);
            handled = false;
        }


        public void setCellSizes()
        {
            if (this.InvokeRequired)
            {
                Invoke(new MethodInvoker(() => { setCellSizes(); }));
            }
            else
            {
            
                float sum = 0;
                for (int i = 0; i < this.dataTable.Columns.Count; i++)
                {
                    SizeF maxStringLenght = new SizeF();

                    foreach (DataRow row in this.dataTable.Rows)
                    {   
                        SizeF size = MeasureString(row[i] as String, this.Font);
                        if (size.Width > maxStringLenght.Width)
                            maxStringLenght = size;
                    }

                

                    if (maxStringLenght.Width > 2)
                    {
                        float weight = maxStringLenght.Width / parent.Width;
                        sum += weight;
                        this.Columns[i].FillWeight = weight;
                    }
                    if (sum > 1)
                    {
                        sum = 1;
                    }

                
                }
                this.Columns[0].FillWeight += (1 - sum) / 4;
                this.Columns[1].FillWeight += (1 - sum) / 4;
                this.Columns[2].FillWeight += (1 - sum) / 4;
                this.Columns[3].FillWeight += (1 - sum) / 4;

                int z = 0;

                foreach (DataGridViewRow row in this.Rows)
                {
                    Color color = Color.White;
                    if (z % 2 == 1)
                    {
                        color = Color.Gainsboro;
                    }

                    foreach (DataGridViewCell cell in row.Cells)
                    {
                        cell.Style.BackColor = color;
                    
                    }

                    row.Height = row.Height - 10;
                    z++;
                }
            }
        }

        public void clearEntries()
        {
            if(dataTable.Rows.Count > 0)
                dataTable.Rows.Clear();

            dataTable.AcceptChanges();
        }

        public virtual void disableDataGrid()
        {
            this.dataTable.Columns.Clear();
            this.dataTable.Rows.Clear();
            this.BackgroundColor = System.Drawing.SystemColors.ControlLight;
        }

        public void initializeComponent(NewErrorOutput parent)
        {
            this.parent = parent;
            this.AllowUserToAddRows = false;
            DataGridViewRow templateRow = new DataGridViewRow();
            templateRow.Height = 30;
            this.RowTemplate = templateRow;
            this.GridColor = System.Drawing.SystemColors.ControlLight;
            this.AllowUserToAddRows = false;
            this.AllowUserToDeleteRows = false;
            this.AllowUserToResizeRows = false;
            this.AllowUserToResizeColumns = true;
            this.MultiSelect = true;
            this.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.BackgroundColor = System.Drawing.SystemColors.ControlLightLight;
            this.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;

            this.Name = "parametersDataGridView1";
            this.RowHeadersVisible = false;
            this.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.Font = new Font("Microsoft Sans Serif", 8.25f);
            this.CellDoubleClick += new DataGridViewCellEventHandler(ParametersDataGridView_CellDoubleClick);

            this.CellClick +=new DataGridViewCellEventHandler(this_CellClick);

            //this.CurrentCellChanged +=new EventHandler(this_CellClick);
            //this.CellClick += new DataGridViewCellEventHandler(ParametersDataGridView_CellClick);
            this.SelectionChanged +=new EventHandler(this_SelectionChanged);
            this.DataBindingComplete +=new DataGridViewBindingCompleteEventHandler(this_DataBindingComplete);
            
            this.TabIndex = 24;

            dataTable.Columns.Add("RuleID");
            dataTable.Columns.Add("Object");
            dataTable.Columns.Add("Message");
            dataTable.Columns.Add("Level");
            
            dataTable.AcceptChanges();
            this.DataSource = dataTable;

            foreach(DataGridViewColumn column in this.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
                column.FillWeight = 1 / 4F;
                column.ReadOnly = true;
                column.SortMode = DataGridViewColumnSortMode.NotSortable;
            }

            this.ColumnHeadersDefaultCellStyle.Font = new Font("Microsoft Sans Serif", 8.25f, FontStyle.Bold);
        }


        private void ParametersDataGridView_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (this.CurrentRow != null && e.RowIndex != -1)
            {
                this.parent.errorDoubleClicked(e.RowIndex);
            }
        }

        

        private void this_CellClick(object sender, EventArgs e)
        {
            if (this.CurrentCell != null && !handled)
            {
                this.parent.errorSelected(this.CurrentCell.RowIndex);
            }
        }

        private void this_SelectionChanged(object sender, EventArgs e)
        {
            if (this.CurrentRow != null && !handled)
            {
                DataGridViewCellEventArgs newEvent = new DataGridViewCellEventArgs(this.CurrentRow.Index,0);
            }
        }

        private void this_DataBindingComplete(System.Object sender, System.Windows.Forms.DataGridViewBindingCompleteEventArgs e)
        {
            this.ClearSelection();
        }

        public static SizeF MeasureString(string s, Font font)
        {
            SizeF result;
            using (var image = new Bitmap(1, 1))
            {
                using (var g = Graphics.FromImage(image))
                {
                    result = g.MeasureString(s, font);
                }
            }

            return result;
        }

    }
}

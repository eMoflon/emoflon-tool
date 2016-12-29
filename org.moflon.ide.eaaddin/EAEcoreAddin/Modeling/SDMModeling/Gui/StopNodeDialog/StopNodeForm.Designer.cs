namespace EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog
{
    partial class StopNodeForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(StopNodeForm));
            this.bttCancel = new System.Windows.Forms.Button();
            this.bttOK = new System.Windows.Forms.Button();
            this.tableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.expressionControl1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.ExpressionControl();
            this.tableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.tableLayoutPanel1.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // bttCancel
            // 
            this.bttCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.bttCancel.Location = new System.Drawing.Point(207, 3);
            this.bttCancel.Name = "bttCancel";
            this.bttCancel.Size = new System.Drawing.Size(74, 23);
            this.bttCancel.TabIndex = 5;
            this.bttCancel.TabStop = false;
            this.bttCancel.Text = "&Cancel";
            this.bttCancel.UseVisualStyleBackColor = true;
            this.bttCancel.Click += new System.EventHandler(this.bttCancel_Click);
            // 
            // bttOK
            // 
            this.bttOK.Location = new System.Drawing.Point(127, 3);
            this.bttOK.Name = "bttOK";
            this.bttOK.Size = new System.Drawing.Size(74, 23);
            this.bttOK.TabIndex = 4;
            this.bttOK.TabStop = false;
            this.bttOK.Text = "&OK";
            this.bttOK.UseVisualStyleBackColor = true;
            this.bttOK.Click += new System.EventHandler(this.bttOK_Click);
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.expressionControl1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel2, 0, 1);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(284, 282);
            this.tableLayoutPanel1.TabIndex = 6;
            // 
            // expressionControl1
            // 
            this.expressionControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.expressionControl1.Location = new System.Drawing.Point(3, 3);
            this.expressionControl1.Name = "expressionControl1";
            this.expressionControl1.Size = new System.Drawing.Size(278, 246);
            this.expressionControl1.TabIndex = 0;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 3;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 80F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 80F));
            this.tableLayoutPanel2.Controls.Add(this.bttCancel, 2, 0);
            this.tableLayoutPanel2.Controls.Add(this.bttOK, 1, 0);
            this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel2.Location = new System.Drawing.Point(0, 252);
            this.tableLayoutPanel2.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(284, 30);
            this.tableLayoutPanel2.TabIndex = 1;
            // 
            // StopNodeForm
            // 
            this.AcceptButton = this.bttOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.bttCancel;
            this.ClientSize = new System.Drawing.Size(284, 282);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(264, 264);
            this.Name = "StopNodeForm";
            this.Text = "StopNode Expression";
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private ExpressionControl expressionControl1;
        private System.Windows.Forms.Button bttCancel;
        private System.Windows.Forms.Button bttOK;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel1;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel2;
    }
}
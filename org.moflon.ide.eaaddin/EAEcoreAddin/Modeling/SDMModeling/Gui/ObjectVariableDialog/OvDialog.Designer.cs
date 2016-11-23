namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    partial class OvDialog
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(OvDialog));
            this.btnOK = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.tableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.propertiesTab = new System.Windows.Forms.TabPage();
            this.ovPropertiesControl = new EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog.OvPropertiesUserControl();
            this.constraintTab = new System.Windows.Forms.TabPage();
            this.ovConstraintControl = new EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog.OvConstraintUserControl();
            this.bindingTab = new System.Windows.Forms.TabPage();
            this.bindingControl = new EAEcoreAddin.Modeling.SDMModeling.Gui.ExpressionControl();
            this.tableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.tableLayoutPanel1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.propertiesTab.SuspendLayout();
            this.constraintTab.SuspendLayout();
            this.bindingTab.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(242, 3);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(69, 23);
            this.btnOK.TabIndex = 1;
            this.btnOK.TabStop = false;
            this.btnOK.Text = "&OK";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOKClick);
            // 
            // btnCancel
            // 
            this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.btnCancel.Location = new System.Drawing.Point(317, 3);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(69, 23);
            this.btnCancel.TabIndex = 2;
            this.btnCancel.TabStop = false;
            this.btnCancel.Text = "&Cancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.tabControl, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel2, 0, 1);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 37F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(395, 540);
            this.tableLayoutPanel1.TabIndex = 3;
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.propertiesTab);
            this.tabControl.Controls.Add(this.constraintTab);
            this.tabControl.Controls.Add(this.bindingTab);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Location = new System.Drawing.Point(3, 3);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(389, 497);
            this.tabControl.TabIndex = 0;
            this.tabControl.TabStop = false;
            this.tabControl.SelectedIndexChanged += new System.EventHandler(this.tabIndexChanged);
            this.tabControl.TabIndexChanged += new System.EventHandler(this.tabIndexChanged);
            // 
            // propertiesTab
            // 
            this.propertiesTab.Controls.Add(this.ovPropertiesControl);
            this.propertiesTab.Location = new System.Drawing.Point(4, 22);
            this.propertiesTab.Name = "propertiesTab";
            this.propertiesTab.Padding = new System.Windows.Forms.Padding(3);
            this.propertiesTab.Size = new System.Drawing.Size(381, 471);
            this.propertiesTab.TabIndex = 0;
            this.propertiesTab.Text = "Object Properties";
            this.propertiesTab.UseVisualStyleBackColor = true;
            // 
            // ovPropertiesControl
            // 
            this.ovPropertiesControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ovPropertiesControl.Location = new System.Drawing.Point(3, 3);
            this.ovPropertiesControl.MinimumSize = new System.Drawing.Size(365, 211);
            this.ovPropertiesControl.Name = "ovPropertiesControl";
            this.ovPropertiesControl.Size = new System.Drawing.Size(375, 465);
            this.ovPropertiesControl.TabIndex = 0;
            // 
            // constraintTab
            // 
            this.constraintTab.Controls.Add(this.ovConstraintControl);
            this.constraintTab.Location = new System.Drawing.Point(4, 22);
            this.constraintTab.Name = "constraintTab";
            this.constraintTab.Padding = new System.Windows.Forms.Padding(3);
            this.constraintTab.Size = new System.Drawing.Size(381, 471);
            this.constraintTab.TabIndex = 1;
            this.constraintTab.Text = "Attribute Constraint";
            this.constraintTab.UseVisualStyleBackColor = true;
            // 
            // ovConstraintControl
            // 
            this.ovConstraintControl.ConstraintExpressionProvider = null;
            this.ovConstraintControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ovConstraintControl.Location = new System.Drawing.Point(3, 3);
            this.ovConstraintControl.Name = "ovConstraintControl";
            this.ovConstraintControl.OvExpressionProvider = null;
            this.ovConstraintControl.Size = new System.Drawing.Size(375, 465);
            this.ovConstraintControl.TabIndex = 0;
            // 
            // bindingTab
            // 
            this.bindingTab.Controls.Add(this.bindingControl);
            this.bindingTab.Location = new System.Drawing.Point(4, 22);
            this.bindingTab.Name = "bindingTab";
            this.bindingTab.Padding = new System.Windows.Forms.Padding(3);
            this.bindingTab.Size = new System.Drawing.Size(381, 471);
            this.bindingTab.TabIndex = 2;
            this.bindingTab.Text = "Binding";
            this.bindingTab.UseVisualStyleBackColor = true;
            // 
            // bindingControl
            // 
            this.bindingControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bindingControl.Location = new System.Drawing.Point(3, 3);
            this.bindingControl.Name = "bindingControl";
            this.bindingControl.Size = new System.Drawing.Size(375, 465);
            this.bindingControl.TabIndex = 0;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 3;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel2.Controls.Add(this.btnCancel, 2, 0);
            this.tableLayoutPanel2.Controls.Add(this.btnOK, 1, 0);
            this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel2.Location = new System.Drawing.Point(3, 506);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(389, 31);
            this.tableLayoutPanel2.TabIndex = 1;
            // 
            // OvDialog
            // 
            this.AcceptButton = this.btnOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.btnCancel;
            this.ClientSize = new System.Drawing.Size(395, 540);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(411, 495);
            this.Name = "OvDialog";
            this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
            this.Text = "Object Variable Properties";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.OvDialog_FormClosing);
            this.Load += new System.EventHandler(this.ObjectVariableTabsForm_Load);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.propertiesTab.ResumeLayout(false);
            this.constraintTab.ResumeLayout(false);
            this.bindingTab.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage propertiesTab;
        private System.Windows.Forms.TabPage constraintTab;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.TabPage bindingTab;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel1;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel2;
        private OvConstraintUserControl ovConstraintControl;
        public OvPropertiesUserControl ovPropertiesControl;
        public Gui.ExpressionControl bindingControl;
    }
}
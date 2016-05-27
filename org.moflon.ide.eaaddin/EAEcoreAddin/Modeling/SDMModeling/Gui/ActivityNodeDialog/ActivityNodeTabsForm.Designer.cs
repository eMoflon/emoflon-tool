namespace EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog
{
    partial class ActivityNodeTabsForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ActivityNodeTabsForm));
            this.checkBox2 = new System.Windows.Forms.CheckBox();
            this.checkBox1 = new System.Windows.Forms.CheckBox();
            this.label9 = new System.Windows.Forms.Label();
            this.cmbDatatypes = new System.Windows.Forms.ComboBox();
            this.cmbTarget = new System.Windows.Forms.ComboBox();
            this.comboBox2 = new System.Windows.Forms.ComboBox();
            this.btnOK = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.smoothTableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.activityPropertiesTab = new System.Windows.Forms.TabPage();
            this.activityNodePropertiesUserControl1 = new EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog.ActivityNodePropertiesUserControl();
            this.statementTabPage = new System.Windows.Forms.TabPage();
            this.statementExpressionControl = new EAEcoreAddin.Modeling.SDMModeling.Gui.ExpressionControl();
            this.smoothTableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.smoothTableLayoutPanel1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.activityPropertiesTab.SuspendLayout();
            this.statementTabPage.SuspendLayout();
            this.smoothTableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // checkBox2
            // 
            this.checkBox2.AutoSize = true;
            this.checkBox2.Location = new System.Drawing.Point(107, 83);
            this.checkBox2.Name = "checkBox2";
            this.checkBox2.Size = new System.Drawing.Size(69, 17);
            this.checkBox2.TabIndex = 13;
            this.checkBox2.Text = "reflective";
            this.checkBox2.UseVisualStyleBackColor = true;
            // 
            // checkBox1
            // 
            this.checkBox1.AutoSize = true;
            this.checkBox1.Location = new System.Drawing.Point(40, 60);
            this.checkBox1.Name = "checkBox1";
            this.checkBox1.Size = new System.Drawing.Size(184, 17);
            this.checkBox1.TabIndex = 12;
            this.checkBox1.Text = "call on each element of the target";
            this.checkBox1.UseVisualStyleBackColor = true;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(142, 31);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(16, 13);
            this.label9.TabIndex = 11;
            this.label9.Text = "=";
            // 
            // cmbDatatypes
            // 
            this.cmbDatatypes.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbDatatypes.FormattingEnabled = true;
            this.cmbDatatypes.Location = new System.Drawing.Point(6, 27);
            this.cmbDatatypes.Name = "cmbDatatypes";
            this.cmbDatatypes.Size = new System.Drawing.Size(69, 25);
            this.cmbDatatypes.TabIndex = 3;
            // 
            // cmbTarget
            // 
            this.cmbTarget.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbTarget.FormattingEnabled = true;
            this.cmbTarget.Location = new System.Drawing.Point(6, 23);
            this.cmbTarget.Name = "cmbTarget";
            this.cmbTarget.Size = new System.Drawing.Size(76, 25);
            this.cmbTarget.TabIndex = 1;
            // 
            // comboBox2
            // 
            this.comboBox2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox2.FormattingEnabled = true;
            this.comboBox2.Location = new System.Drawing.Point(6, 23);
            this.comboBox2.Name = "comboBox2";
            this.comboBox2.Size = new System.Drawing.Size(76, 25);
            this.comboBox2.TabIndex = 2;
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(4, 4);
            this.btnOK.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(93, 28);
            this.btnOK.TabIndex = 1;
            this.btnOK.Text = "OK";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.btnCancel.Location = new System.Drawing.Point(105, 4);
            this.btnCancel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(93, 28);
            this.btnCancel.TabIndex = 2;
            this.btnCancel.Text = "Cancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // smoothTableLayoutPanel1
            // 
            this.smoothTableLayoutPanel1.ColumnCount = 1;
            this.smoothTableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Controls.Add(this.tabControl, 0, 0);
            this.smoothTableLayoutPanel1.Controls.Add(this.smoothTableLayoutPanel2, 0, 1);
            this.smoothTableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.smoothTableLayoutPanel1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.smoothTableLayoutPanel1.Name = "smoothTableLayoutPanel1";
            this.smoothTableLayoutPanel1.RowCount = 2;
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 37F));
            this.smoothTableLayoutPanel1.Size = new System.Drawing.Size(452, 406);
            this.smoothTableLayoutPanel1.TabIndex = 3;
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.activityPropertiesTab);
            this.tabControl.Controls.Add(this.statementTabPage);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Location = new System.Drawing.Point(4, 4);
            this.tabControl.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(444, 361);
            this.tabControl.TabIndex = 0;
            this.tabControl.TabIndexChanged += new System.EventHandler(this.tabControl_TabIndexChanged);
            // 
            // activityPropertiesTab
            // 
            this.activityPropertiesTab.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.activityPropertiesTab.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.activityPropertiesTab.Controls.Add(this.activityNodePropertiesUserControl1);
            this.activityPropertiesTab.Location = new System.Drawing.Point(4, 25);
            this.activityPropertiesTab.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.activityPropertiesTab.Name = "activityPropertiesTab";
            this.activityPropertiesTab.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.activityPropertiesTab.Size = new System.Drawing.Size(436, 332);
            this.activityPropertiesTab.TabIndex = 0;
            this.activityPropertiesTab.Text = "Properties";
            // 
            // activityNodePropertiesUserControl1
            // 
            this.activityNodePropertiesUserControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.activityNodePropertiesUserControl1.Location = new System.Drawing.Point(4, 4);
            this.activityNodePropertiesUserControl1.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.activityNodePropertiesUserControl1.Name = "activityNodePropertiesUserControl1";
            this.activityNodePropertiesUserControl1.Size = new System.Drawing.Size(428, 324);
            this.activityNodePropertiesUserControl1.TabIndex = 0;
            // 
            // statementTabPage
            // 
            this.statementTabPage.Controls.Add(this.statementExpressionControl);
            this.statementTabPage.Location = new System.Drawing.Point(4, 25);
            this.statementTabPage.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.statementTabPage.Name = "statementTabPage";
            this.statementTabPage.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.statementTabPage.Size = new System.Drawing.Size(436, 333);
            this.statementTabPage.TabIndex = 1;
            this.statementTabPage.Text = "Statement";
            this.statementTabPage.UseVisualStyleBackColor = true;
            // 
            // statementExpressionControl
            // 
            this.statementExpressionControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.statementExpressionControl.Location = new System.Drawing.Point(4, 4);
            this.statementExpressionControl.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.statementExpressionControl.Name = "statementExpressionControl";
            this.statementExpressionControl.Size = new System.Drawing.Size(428, 325);
            this.statementExpressionControl.TabIndex = 0;
            // 
            // smoothTableLayoutPanel2
            // 
            this.smoothTableLayoutPanel2.ColumnCount = 2;
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 101F));
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 101F));
            this.smoothTableLayoutPanel2.Controls.Add(this.btnCancel, 1, 0);
            this.smoothTableLayoutPanel2.Controls.Add(this.btnOK, 0, 0);
            this.smoothTableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Right;
            this.smoothTableLayoutPanel2.Location = new System.Drawing.Point(249, 369);
            this.smoothTableLayoutPanel2.Margin = new System.Windows.Forms.Padding(0);
            this.smoothTableLayoutPanel2.Name = "smoothTableLayoutPanel2";
            this.smoothTableLayoutPanel2.RowCount = 1;
            this.smoothTableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel2.Size = new System.Drawing.Size(203, 37);
            this.smoothTableLayoutPanel2.TabIndex = 1;
            // 
            // ActivityNodeTabsForm
            // 
            this.AcceptButton = this.btnOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.btnCancel;
            this.ClientSize = new System.Drawing.Size(452, 406);
            this.Controls.Add(this.smoothTableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(313, 366);
            this.Name = "ActivityNodeTabsForm";
            this.Text = "Edit ActivityNode";
            this.smoothTableLayoutPanel1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.activityPropertiesTab.ResumeLayout(false);
            this.statementTabPage.ResumeLayout(false);
            this.smoothTableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.CheckBox checkBox2;
        private System.Windows.Forms.CheckBox checkBox1;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ComboBox cmbDatatypes;
        private System.Windows.Forms.ComboBox cmbTarget;
        private System.Windows.Forms.ComboBox comboBox2;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Button btnOK;
        public System.Windows.Forms.TabPage activityPropertiesTab;
        public System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage statementTabPage;
        private Gui.ExpressionControl statementExpressionControl;
        private ActivityNodePropertiesUserControl activityNodePropertiesUserControl1;
        private Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel1;
        private Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel2;
    }
}
namespace EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog
{
    partial class ActivityNodePropertiesUserControl
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.smoothTableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.buttonEvacuate = new System.Windows.Forms.Button();
            this.chkThis = new System.Windows.Forms.CheckBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.txtName = new System.Windows.Forms.TextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.comboTypes = new EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog.ActivityNodeTypeComboBox();
            this.smoothTableLayoutPanel1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // smoothTableLayoutPanel1
            // 
            this.smoothTableLayoutPanel1.ColumnCount = 1;
            this.smoothTableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox3, 0, 2);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox1, 0, 0);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox2, 0, 1);
            this.smoothTableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.smoothTableLayoutPanel1.Name = "smoothTableLayoutPanel1";
            this.smoothTableLayoutPanel1.RowCount = 3;
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Size = new System.Drawing.Size(331, 209);
            this.smoothTableLayoutPanel1.TabIndex = 26;
            // 
            // groupBox3
            // 
            this.groupBox3.AutoSize = true;
            this.groupBox3.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox3.Controls.Add(this.buttonEvacuate);
            this.groupBox3.Controls.Add(this.chkThis);
            this.groupBox3.Location = new System.Drawing.Point(3, 103);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(132, 84);
            this.groupBox3.TabIndex = 3;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Features";
            // 
            // buttonEvacuate
            // 
            this.buttonEvacuate.Location = new System.Drawing.Point(6, 42);
            this.buttonEvacuate.Name = "buttonEvacuate";
            this.buttonEvacuate.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.buttonEvacuate.Size = new System.Drawing.Size(120, 23);
            this.buttonEvacuate.TabIndex = 4;
            this.buttonEvacuate.Text = "Extract Story Pattern";
            this.buttonEvacuate.UseVisualStyleBackColor = true;
            this.buttonEvacuate.Click += new System.EventHandler(this.buttonExtractStoryPattern_Click);
            // 
            // chkThis
            // 
            this.chkThis.AutoSize = true;
            this.chkThis.Location = new System.Drawing.Point(6, 19);
            this.chkThis.Name = "chkThis";
            this.chkThis.Size = new System.Drawing.Size(110, 17);
            this.chkThis.TabIndex = 3;
            this.chkThis.Text = "Create this Object";
            this.chkThis.UseVisualStyleBackColor = true;
            // 
            // groupBox1
            // 
            this.groupBox1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox1.Controls.Add(this.txtName);
            this.groupBox1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox1.Location = new System.Drawing.Point(3, 3);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(325, 44);
            this.groupBox1.TabIndex = 1;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Name";
            // 
            // txtName
            // 
            this.txtName.Dock = System.Windows.Forms.DockStyle.Top;
            this.txtName.Location = new System.Drawing.Point(3, 16);
            this.txtName.Name = "txtName";
            this.txtName.Size = new System.Drawing.Size(319, 20);
            this.txtName.TabIndex = 1;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.comboTypes);
            this.groupBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox2.Location = new System.Drawing.Point(3, 53);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(325, 44);
            this.groupBox2.TabIndex = 2;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Type";
            // 
            // comboTypes
            // 
            this.comboTypes.Dock = System.Windows.Forms.DockStyle.Fill;
            this.comboTypes.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboTypes.FormattingEnabled = true;
            this.comboTypes.Items.AddRange(new object[] {
            "StoryNode",
            "ForEach",
            "StatementNode"});
            this.comboTypes.Location = new System.Drawing.Point(3, 16);
            this.comboTypes.Name = "comboTypes";
            this.comboTypes.Size = new System.Drawing.Size(319, 21);
            this.comboTypes.TabIndex = 2;
            this.comboTypes.SelectedIndexChanged += new System.EventHandler(this.comboTypes_TextChanged);
            this.comboTypes.TextChanged += new System.EventHandler(this.comboTypes_TextChanged);
            // 
            // ActivityNodePropertiesUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.smoothTableLayoutPanel1);
            this.Name = "ActivityNodePropertiesUserControl";
            this.Size = new System.Drawing.Size(331, 209);
            this.smoothTableLayoutPanel1.ResumeLayout(false);
            this.smoothTableLayoutPanel1.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.CheckBox chkThis;
        private Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel1;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button buttonEvacuate;
        private System.Windows.Forms.GroupBox groupBox1;
        public System.Windows.Forms.TextBox txtName;
        private System.Windows.Forms.GroupBox groupBox2;
        private ActivityNodeTypeComboBox comboTypes;

    }
}

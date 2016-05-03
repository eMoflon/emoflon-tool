namespace EAEcoreAddin.Modeling.TGGModeling.TGGActions
{
    partial class SelectCorrespondenceObject
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SelectCorrespondenceObject));
            this.comboBoxCorrClasses = new System.Windows.Forms.ComboBox();
            this.buttonOk = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.checkBoxCreateNew = new System.Windows.Forms.CheckBox();
            this.textBoxLinkName = new System.Windows.Forms.TextBox();
            this.groupBoxSelectLink = new System.Windows.Forms.GroupBox();
            this.groupBoxCreateNewLink = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxObjectName = new System.Windows.Forms.TextBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBoxSelectLink.SuspendLayout();
            this.groupBoxCreateNewLink.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // comboBoxCorrClasses
            // 
            this.comboBoxCorrClasses.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.comboBoxCorrClasses.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxCorrClasses.FormattingEnabled = true;
            this.comboBoxCorrClasses.Location = new System.Drawing.Point(6, 19);
            this.comboBoxCorrClasses.Name = "comboBoxCorrClasses";
            this.comboBoxCorrClasses.Size = new System.Drawing.Size(327, 21);
            this.comboBoxCorrClasses.TabIndex = 0;
            // 
            // buttonOk
            // 
            this.buttonOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOk.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.buttonOk.Location = new System.Drawing.Point(186, 210);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(78, 23);
            this.buttonOk.TabIndex = 1;
            this.buttonOk.Text = "OK";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // buttonCancel
            // 
            this.buttonCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(270, 210);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 2;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // checkBoxCreateNew
            // 
            this.checkBoxCreateNew.AutoSize = true;
            this.checkBoxCreateNew.Location = new System.Drawing.Point(12, 129);
            this.checkBoxCreateNew.Name = "checkBoxCreateNew";
            this.checkBoxCreateNew.Size = new System.Drawing.Size(190, 17);
            this.checkBoxCreateNew.TabIndex = 3;
            this.checkBoxCreateNew.Text = "Create New Correspondence Type";
            this.checkBoxCreateNew.UseVisualStyleBackColor = true;
            this.checkBoxCreateNew.CheckedChanged += new System.EventHandler(this.checkBox1_CheckedChanged);
            // 
            // textBoxLinkName
            // 
            this.textBoxLinkName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxLinkName.Location = new System.Drawing.Point(6, 16);
            this.textBoxLinkName.Name = "textBoxLinkName";
            this.textBoxLinkName.Size = new System.Drawing.Size(327, 20);
            this.textBoxLinkName.TabIndex = 4;
            // 
            // groupBoxSelectLink
            // 
            this.groupBoxSelectLink.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBoxSelectLink.Controls.Add(this.comboBoxCorrClasses);
            this.groupBoxSelectLink.Location = new System.Drawing.Point(6, 65);
            this.groupBoxSelectLink.Name = "groupBoxSelectLink";
            this.groupBoxSelectLink.Size = new System.Drawing.Size(339, 58);
            this.groupBoxSelectLink.TabIndex = 5;
            this.groupBoxSelectLink.TabStop = false;
            this.groupBoxSelectLink.Text = "Select Existing Correspondence Type";
            // 
            // groupBoxCreateNewLink
            // 
            this.groupBoxCreateNewLink.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBoxCreateNewLink.Controls.Add(this.textBoxLinkName);
            this.groupBoxCreateNewLink.Enabled = false;
            this.groupBoxCreateNewLink.Location = new System.Drawing.Point(6, 152);
            this.groupBoxCreateNewLink.Name = "groupBoxCreateNewLink";
            this.groupBoxCreateNewLink.Size = new System.Drawing.Size(339, 50);
            this.groupBoxCreateNewLink.TabIndex = 6;
            this.groupBoxCreateNewLink.TabStop = false;
            this.groupBoxCreateNewLink.Text = "Set Name of Correspondence Type";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(6, 17);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(179, 13);
            this.label2.TabIndex = 9;
            this.label2.Text = "Set name of Correspondence Object";
            // 
            // textBoxObjectName
            // 
            this.textBoxObjectName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxObjectName.Location = new System.Drawing.Point(194, 14);
            this.textBoxObjectName.Name = "textBoxObjectName";
            this.textBoxObjectName.Size = new System.Drawing.Size(139, 20);
            this.textBoxObjectName.TabIndex = 8;
            // 
            // groupBox3
            // 
            this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox3.Controls.Add(this.label2);
            this.groupBox3.Controls.Add(this.textBoxObjectName);
            this.groupBox3.Location = new System.Drawing.Point(6, 12);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(339, 47);
            this.groupBox3.TabIndex = 6;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Set Object Name";
            // 
            // SelectCorrespondenceObject
            // 
            this.AcceptButton = this.buttonOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(357, 245);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.checkBoxCreateNew);
            this.Controls.Add(this.groupBoxCreateNewLink);
            this.Controls.Add(this.groupBoxSelectLink);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.buttonOk);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximumSize = new System.Drawing.Size(1000, 284);
            this.MinimumSize = new System.Drawing.Size(373, 284);
            this.Name = "SelectCorrespondenceObject";
            this.Text = "Create TGG Correspondence Link";
            this.groupBoxSelectLink.ResumeLayout(false);
            this.groupBoxCreateNewLink.ResumeLayout(false);
            this.groupBoxCreateNewLink.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox comboBoxCorrClasses;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.CheckBox checkBoxCreateNew;
        private System.Windows.Forms.TextBox textBoxLinkName;
        private System.Windows.Forms.GroupBox groupBoxSelectLink;
        private System.Windows.Forms.GroupBox groupBoxCreateNewLink;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxObjectName;
        private System.Windows.Forms.GroupBox groupBox3;
    }
}
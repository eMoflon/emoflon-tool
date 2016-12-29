namespace EAEcoreAddin.Modeling.SDMModeling.Util
{
    partial class ExtractSDM
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ExtractSDM));
            this.textBoxNewSDM = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonOK = new System.Windows.Forms.Button();
            this.checkBoxBoundOv = new System.Windows.Forms.CheckBox();
            this.ovClassifiersComboBox1 = new EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog.OvClassifiersComboBox();
            this.checkBoxValidateAfter = new System.Windows.Forms.CheckBox();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // textBoxNewSDM
            // 
            this.textBoxNewSDM.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxNewSDM.Location = new System.Drawing.Point(6, 19);
            this.textBoxNewSDM.Name = "textBoxNewSDM";
            this.textBoxNewSDM.Size = new System.Drawing.Size(306, 20);
            this.textBoxNewSDM.TabIndex = 0;
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.textBoxNewSDM);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(318, 51);
            this.groupBox1.TabIndex = 1;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Name of new SDM";
            // 
            // buttonCancel
            // 
            this.buttonCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(255, 173);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 2;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonOK
            // 
            this.buttonOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOK.Location = new System.Drawing.Point(175, 173);
            this.buttonOK.Name = "buttonOK";
            this.buttonOK.Size = new System.Drawing.Size(75, 23);
            this.buttonOK.TabIndex = 3;
            this.buttonOK.Text = "OK";
            this.buttonOK.UseVisualStyleBackColor = true;
            this.buttonOK.Click += new System.EventHandler(this.buttonOK_Click);
            // 
            // checkBoxBoundOv
            // 
            this.checkBoxBoundOv.AutoSize = true;
            this.checkBoxBoundOv.Location = new System.Drawing.Point(18, 69);
            this.checkBoxBoundOv.Name = "checkBoxBoundOv";
            this.checkBoxBoundOv.Size = new System.Drawing.Size(191, 17);
            this.checkBoxBoundOv.TabIndex = 1;
            this.checkBoxBoundOv.Text = "Bound new SDM to ObjectVariable";
            this.checkBoxBoundOv.UseVisualStyleBackColor = true;
            this.checkBoxBoundOv.CheckedChanged += new System.EventHandler(this.checkBoxBoundOv_CheckedChanged);
            // 
            // ovClassifiersComboBox1
            // 
            this.ovClassifiersComboBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.ovClassifiersComboBox1.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.SuggestAppend;
            this.ovClassifiersComboBox1.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.ovClassifiersComboBox1.ClassifierIDs = null;
            this.ovClassifiersComboBox1.Classifiers = null;
            this.ovClassifiersComboBox1.Enabled = false;
            this.ovClassifiersComboBox1.FormattingEnabled = true;
            this.ovClassifiersComboBox1.Location = new System.Drawing.Point(215, 65);
            this.ovClassifiersComboBox1.Name = "ovClassifiersComboBox1";
            this.ovClassifiersComboBox1.selectedClassifier = null;
            this.ovClassifiersComboBox1.Size = new System.Drawing.Size(109, 21);
            this.ovClassifiersComboBox1.TabIndex = 4;
            // 
            // checkBoxValidateAfter
            // 
            this.checkBoxValidateAfter.AutoSize = true;
            this.checkBoxValidateAfter.Checked = true;
            this.checkBoxValidateAfter.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxValidateAfter.Location = new System.Drawing.Point(18, 92);
            this.checkBoxValidateAfter.Name = "checkBoxValidateAfter";
            this.checkBoxValidateAfter.Size = new System.Drawing.Size(124, 17);
            this.checkBoxValidateAfter.TabIndex = 5;
            this.checkBoxValidateAfter.Text = "Validate after Extract";
            this.checkBoxValidateAfter.UseVisualStyleBackColor = true;
            // 
            // ExtractSDM
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(342, 208);
            this.Controls.Add(this.checkBoxValidateAfter);
            this.Controls.Add(this.ovClassifiersComboBox1);
            this.Controls.Add(this.checkBoxBoundOv);
            this.Controls.Add(this.buttonOK);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "ExtractSDM";
            this.Text = "Extract SDM";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox textBoxNewSDM;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonOK;
        private System.Windows.Forms.CheckBox checkBoxBoundOv;
        private ObjectVariableDialog.OvClassifiersComboBox ovClassifiersComboBox1;
        private System.Windows.Forms.CheckBox checkBoxValidateAfter;
    }
}
namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    partial class DeriveNewTGGRule
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DeriveNewTGGRule));
            this.textBoxRuleName = new System.Windows.Forms.TextBox();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonOk = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.addInheritanceLinkCheckBox = new System.Windows.Forms.CheckBox();
            this.checkBoxExactCopy = new System.Windows.Forms.CheckBox();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // textBoxRuleName
            // 
            this.textBoxRuleName.Location = new System.Drawing.Point(6, 19);
            this.textBoxRuleName.Name = "textBoxRuleName";
            this.textBoxRuleName.Size = new System.Drawing.Size(205, 20);
            this.textBoxRuleName.TabIndex = 0;
            // 
            // buttonCancel
            // 
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(248, 105);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 1;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonOk
            // 
            this.buttonOk.Location = new System.Drawing.Point(166, 105);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(75, 23);
            this.buttonOk.TabIndex = 2;
            this.buttonOk.Text = "OK";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(217, 22);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(88, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "name of new rule";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.addInheritanceLinkCheckBox);
            this.groupBox1.Controls.Add(this.checkBoxExactCopy);
            this.groupBox1.Controls.Add(this.textBoxRuleName);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(311, 87);
            this.groupBox1.TabIndex = 4;
            this.groupBox1.TabStop = false;
            // 
            // addInheritanceLinkCheckBox
            // 
            this.addInheritanceLinkCheckBox.AutoSize = true;
            this.addInheritanceLinkCheckBox.Checked = true;
            this.addInheritanceLinkCheckBox.CheckState = System.Windows.Forms.CheckState.Checked;
            this.addInheritanceLinkCheckBox.Location = new System.Drawing.Point(6, 62);
            this.addInheritanceLinkCheckBox.Name = "addInheritanceLinkCheckBox";
            this.addInheritanceLinkCheckBox.Size = new System.Drawing.Size(183, 17);
            this.addInheritanceLinkCheckBox.TabIndex = 5;
            this.addInheritanceLinkCheckBox.Text = "add refinement link to original rule";
            this.addInheritanceLinkCheckBox.UseVisualStyleBackColor = true;
            // 
            // checkBoxExactCopy
            // 
            this.checkBoxExactCopy.AutoSize = true;
            this.checkBoxExactCopy.Location = new System.Drawing.Point(6, 45);
            this.checkBoxExactCopy.Name = "checkBoxExactCopy";
            this.checkBoxExactCopy.Size = new System.Drawing.Size(223, 17);
            this.checkBoxExactCopy.TabIndex = 4;
            this.checkBoxExactCopy.Text = "exact copy instead of context for new rule";
            this.checkBoxExactCopy.UseVisualStyleBackColor = true;
            // 
            // DeriveNewTGGRule
            // 
            this.AcceptButton = this.buttonOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(335, 138);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.buttonOk);
            this.Controls.Add(this.buttonCancel);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "DeriveNewTGGRule";
            this.Text = "Derive new TGG Rule";
            this.Load += new System.EventHandler(this.DeriveNewTGGRule_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TextBox textBoxRuleName;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.CheckBox checkBoxExactCopy;
        private System.Windows.Forms.CheckBox addInheritanceLinkCheckBox;

    }
}
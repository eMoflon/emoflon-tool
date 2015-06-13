namespace EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog
{
    partial class ComplexNAC
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
            this.complexNACControl = new System.Windows.Forms.GroupBox();
            this.comboBoxComplexNacIndex = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.complexNACControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // complexNACControl
            // 
            this.complexNACControl.Controls.Add(this.comboBoxComplexNacIndex);
            this.complexNACControl.Controls.Add(this.label1);
            this.complexNACControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.complexNACControl.Location = new System.Drawing.Point(0, 0);
            this.complexNACControl.Name = "complexNACControl";
            this.complexNACControl.Size = new System.Drawing.Size(155, 64);
            this.complexNACControl.TabIndex = 46;
            this.complexNACControl.TabStop = false;
            this.complexNACControl.Text = "Complex NAC";
            // 
            // comboBoxComplexNacIndex
            // 
            this.comboBoxComplexNacIndex.FormattingEnabled = true;
            this.comboBoxComplexNacIndex.Location = new System.Drawing.Point(79, 18);
            this.comboBoxComplexNacIndex.MaxLength = 3;
            this.comboBoxComplexNacIndex.Name = "comboBoxComplexNacIndex";
            this.comboBoxComplexNacIndex.Size = new System.Drawing.Size(61, 21);
            this.comboBoxComplexNacIndex.TabIndex = 2;
            this.comboBoxComplexNacIndex.TextChanged += new System.EventHandler(this.comboBoxComplexNacIndex_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(58, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "NAC Index";
            // 
            // ComplexNAC
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.complexNACControl);
            this.MinimumSize = new System.Drawing.Size(155, 64);
            this.Name = "ComplexNAC";
            this.Size = new System.Drawing.Size(155, 64);
            this.complexNACControl.ResumeLayout(false);
            this.complexNACControl.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox complexNACControl;
        private System.Windows.Forms.ComboBox comboBoxComplexNacIndex;
        private System.Windows.Forms.Label label1;
    }
}

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    partial class SDMCSPDialog
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SDMCSPDialog));
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonOk = new System.Windows.Forms.Button();
            this.textBoxCspDefinition = new System.Windows.Forms.RichTextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.showHelpButton = new System.Windows.Forms.Button();
            this.complexNAC1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog.ComplexNAC();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // buttonCancel
            // 
            this.buttonCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonCancel.Location = new System.Drawing.Point(672, 340);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 2;
            this.buttonCancel.Text = "&Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonOk
            // 
            this.buttonOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOk.Location = new System.Drawing.Point(591, 340);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(75, 23);
            this.buttonOk.TabIndex = 3;
            this.buttonOk.Text = "&OK";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // textBoxCspDefinition
            // 
            this.textBoxCspDefinition.Dock = System.Windows.Forms.DockStyle.Fill;
            this.textBoxCspDefinition.Font = new System.Drawing.Font("Courier New", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxCspDefinition.Location = new System.Drawing.Point(3, 16);
            this.textBoxCspDefinition.Name = "textBoxCspDefinition";
            this.textBoxCspDefinition.Size = new System.Drawing.Size(729, 233);
            this.textBoxCspDefinition.TabIndex = 4;
            this.textBoxCspDefinition.Text = "awdawdaw";
            this.textBoxCspDefinition.TextChanged += new System.EventHandler(this.textBoxCspDefinition_TextChanged);
            // 
            // groupBox2
            // 
            this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox2.Controls.Add(this.textBoxCspDefinition);
            this.groupBox2.Location = new System.Drawing.Point(12, 12);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(735, 252);
            this.groupBox2.TabIndex = 5;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "&Define CSP";
            // 
            // showHelpButton
            // 
            this.showHelpButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.showHelpButton.Location = new System.Drawing.Point(12, 340);
            this.showHelpButton.Name = "showHelpButton";
            this.showHelpButton.Size = new System.Drawing.Size(75, 23);
            this.showHelpButton.TabIndex = 7;
            this.showHelpButton.Text = "Show &Help";
            this.showHelpButton.UseVisualStyleBackColor = true;
            this.showHelpButton.Click += new System.EventHandler(this.showHelpButton_Click);
            // 
            // complexNAC1
            // 
            this.complexNAC1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.complexNAC1.Location = new System.Drawing.Point(12, 270);
            this.complexNAC1.MinimumSize = new System.Drawing.Size(155, 64);
            this.complexNAC1.Name = "complexNAC1";
            this.complexNAC1.Size = new System.Drawing.Size(735, 64);
            this.complexNAC1.TabIndex = 6;
            // 
            // SDMCSPDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(759, 375);
            this.Controls.Add(this.showHelpButton);
            this.Controls.Add(this.complexNAC1);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.buttonOk);
            this.Controls.Add(this.buttonCancel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(228, 220);
            this.Name = "SDMCSPDialog";
            this.Text = "Define CSP";
            this.groupBox2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.RichTextBox textBoxCspDefinition;
        private System.Windows.Forms.GroupBox groupBox2;
        private SDMModeling.Gui.ObjectVariableDialog.ComplexNAC complexNAC1;
        private System.Windows.Forms.Button showHelpButton;
    }
}
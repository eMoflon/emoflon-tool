namespace EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog
{
    partial class LinkVariablePropertiesForm
    {
        /// <summary>
        /// Erforderliche Designervariable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Verwendete Ressourcen bereinigen.
        /// </summary>
        /// <param name="disposing">True, wenn verwaltete Ressourcen gelöscht werden sollen; andernfalls False.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Vom Windows Form-Designer generierter Code

        /// <summary>
        /// Erforderliche Methode für die Designerunterstützung.
        /// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(LinkVariablePropertiesForm));
            this.btnOK = new System.Windows.Forms.Button();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.complexNAC1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog.ComplexNAC();
            this.gbxType = new System.Windows.Forms.GroupBox();
            this.radioButtonNegative = new System.Windows.Forms.RadioButton();
            this.radioButtonMandatory = new System.Windows.Forms.RadioButton();
            this.gbxModifier = new System.Windows.Forms.GroupBox();
            this.radioButtonCreate = new System.Windows.Forms.RadioButton();
            this.radioButtonDestroy = new System.Windows.Forms.RadioButton();
            this.radioButtonCheckOnly = new System.Windows.Forms.RadioButton();
            this.listboxLinks = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.panel1.SuspendLayout();
            this.gbxType.SuspendLayout();
            this.gbxModifier.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnOK
            // 
            this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOK.Location = new System.Drawing.Point(356, 345);
            this.btnOK.Margin = new System.Windows.Forms.Padding(4);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(100, 28);
            this.btnOK.TabIndex = 9;
            this.btnOK.Text = "OK";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "Desert.jpg");
            // 
            // panel1
            // 
            this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.panel1.Controls.Add(this.complexNAC1);
            this.panel1.Controls.Add(this.gbxType);
            this.panel1.Controls.Add(this.gbxModifier);
            this.panel1.Location = new System.Drawing.Point(0, 206);
            this.panel1.Margin = new System.Windows.Forms.Padding(4);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(611, 132);
            this.panel1.TabIndex = 21;
            // 
            // complexNAC1
            // 
            this.complexNAC1.Enabled = false;
            this.complexNAC1.Location = new System.Drawing.Point(357, 9);
            this.complexNAC1.Margin = new System.Windows.Forms.Padding(5);
            this.complexNAC1.MinimumSize = new System.Drawing.Size(207, 79);
            this.complexNAC1.Name = "complexNAC1";
            this.complexNAC1.Size = new System.Drawing.Size(207, 114);
            this.complexNAC1.TabIndex = 21;
            // 
            // gbxType
            // 
            this.gbxType.Controls.Add(this.radioButtonNegative);
            this.gbxType.Controls.Add(this.radioButtonMandatory);
            this.gbxType.Location = new System.Drawing.Point(187, 9);
            this.gbxType.Margin = new System.Windows.Forms.Padding(4);
            this.gbxType.Name = "gbxType";
            this.gbxType.Padding = new System.Windows.Forms.Padding(4);
            this.gbxType.Size = new System.Drawing.Size(163, 114);
            this.gbxType.TabIndex = 19;
            this.gbxType.TabStop = false;
            this.gbxType.Text = "Binding Semantics";
            // 
            // radioButtonNegative
            // 
            this.radioButtonNegative.AutoSize = true;
            this.radioButtonNegative.Location = new System.Drawing.Point(8, 52);
            this.radioButtonNegative.Margin = new System.Windows.Forms.Padding(4);
            this.radioButtonNegative.Name = "radioButtonNegative";
            this.radioButtonNegative.Size = new System.Drawing.Size(85, 21);
            this.radioButtonNegative.TabIndex = 3;
            this.radioButtonNegative.Text = "Negative";
            this.radioButtonNegative.UseVisualStyleBackColor = true;
            this.radioButtonNegative.CheckedChanged += new System.EventHandler(this.radioButtonNegative_CheckedChanged);
            // 
            // radioButtonMandatory
            // 
            this.radioButtonMandatory.AutoSize = true;
            this.radioButtonMandatory.Checked = true;
            this.radioButtonMandatory.Location = new System.Drawing.Point(8, 23);
            this.radioButtonMandatory.Margin = new System.Windows.Forms.Padding(4);
            this.radioButtonMandatory.Name = "radioButtonMandatory";
            this.radioButtonMandatory.Size = new System.Drawing.Size(96, 21);
            this.radioButtonMandatory.TabIndex = 2;
            this.radioButtonMandatory.TabStop = true;
            this.radioButtonMandatory.Text = "Mandatory";
            this.radioButtonMandatory.UseVisualStyleBackColor = true;
            // 
            // gbxModifier
            // 
            this.gbxModifier.Controls.Add(this.radioButtonCreate);
            this.gbxModifier.Controls.Add(this.radioButtonDestroy);
            this.gbxModifier.Controls.Add(this.radioButtonCheckOnly);
            this.gbxModifier.Location = new System.Drawing.Point(16, 9);
            this.gbxModifier.Margin = new System.Windows.Forms.Padding(4);
            this.gbxModifier.Name = "gbxModifier";
            this.gbxModifier.Padding = new System.Windows.Forms.Padding(4);
            this.gbxModifier.Size = new System.Drawing.Size(163, 114);
            this.gbxModifier.TabIndex = 20;
            this.gbxModifier.TabStop = false;
            this.gbxModifier.Text = "Binding Operator";
            // 
            // radioButtonCreate
            // 
            this.radioButtonCreate.AutoSize = true;
            this.radioButtonCreate.Location = new System.Drawing.Point(8, 52);
            this.radioButtonCreate.Margin = new System.Windows.Forms.Padding(4);
            this.radioButtonCreate.Name = "radioButtonCreate";
            this.radioButtonCreate.Size = new System.Drawing.Size(71, 21);
            this.radioButtonCreate.TabIndex = 7;
            this.radioButtonCreate.Text = "Create";
            this.radioButtonCreate.UseVisualStyleBackColor = true;
            this.radioButtonCreate.CheckedChanged += new System.EventHandler(this.radioButtonCreate_CheckedChanged);
            // 
            // radioButtonDestroy
            // 
            this.radioButtonDestroy.AutoSize = true;
            this.radioButtonDestroy.Location = new System.Drawing.Point(8, 80);
            this.radioButtonDestroy.Margin = new System.Windows.Forms.Padding(4);
            this.radioButtonDestroy.Name = "radioButtonDestroy";
            this.radioButtonDestroy.Size = new System.Drawing.Size(78, 21);
            this.radioButtonDestroy.TabIndex = 6;
            this.radioButtonDestroy.Text = "Destroy";
            this.radioButtonDestroy.UseVisualStyleBackColor = true;
            this.radioButtonDestroy.CheckedChanged += new System.EventHandler(this.radioButtonDestroy_CheckedChanged);
            // 
            // radioButtonCheckOnly
            // 
            this.radioButtonCheckOnly.AutoSize = true;
            this.radioButtonCheckOnly.Checked = true;
            this.radioButtonCheckOnly.Location = new System.Drawing.Point(8, 23);
            this.radioButtonCheckOnly.Margin = new System.Windows.Forms.Padding(4);
            this.radioButtonCheckOnly.Name = "radioButtonCheckOnly";
            this.radioButtonCheckOnly.Size = new System.Drawing.Size(101, 21);
            this.radioButtonCheckOnly.TabIndex = 5;
            this.radioButtonCheckOnly.TabStop = true;
            this.radioButtonCheckOnly.Text = "Check Only";
            this.radioButtonCheckOnly.UseVisualStyleBackColor = true;
            // 
            // listboxLinks
            // 
            this.listboxLinks.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.listboxLinks.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.listboxLinks.FormattingEnabled = true;
            this.listboxLinks.HorizontalExtent = 1;
            this.listboxLinks.HorizontalScrollbar = true;
            this.listboxLinks.ItemHeight = 16;
            this.listboxLinks.Location = new System.Drawing.Point(0, 0);
            this.listboxLinks.Margin = new System.Windows.Forms.Padding(4);
            this.listboxLinks.Name = "listboxLinks";
            this.listboxLinks.Size = new System.Drawing.Size(585, 194);
            this.listboxLinks.TabIndex = 1;
            this.listboxLinks.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.listboxLinks_MouseDoubleClick);
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.cancelButton.Location = new System.Drawing.Point(464, 345);
            this.cancelButton.Margin = new System.Windows.Forms.Padding(4);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(100, 28);
            this.cancelButton.TabIndex = 22;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // LinkVariablePropertiesForm
            // 
            this.AcceptButton = this.btnOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.cancelButton;
            this.ClientSize = new System.Drawing.Size(585, 414);
            this.ControlBox = false;
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.listboxLinks);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(601, 425);
            this.Name = "LinkVariablePropertiesForm";
            this.Text = "Link Variable Properties";
            this.panel1.ResumeLayout(false);
            this.gbxType.ResumeLayout(false);
            this.gbxType.PerformLayout();
            this.gbxModifier.ResumeLayout(false);
            this.gbxModifier.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox listboxLinks;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.GroupBox gbxType;
        private System.Windows.Forms.RadioButton radioButtonNegative;
        private System.Windows.Forms.RadioButton radioButtonMandatory;
        private System.Windows.Forms.GroupBox gbxModifier;
        private System.Windows.Forms.RadioButton radioButtonCreate;
        private System.Windows.Forms.RadioButton radioButtonDestroy;
        private System.Windows.Forms.RadioButton radioButtonCheckOnly;
        private Gui.ObjectVariableDialog.ComplexNAC complexNAC1;
        private System.Windows.Forms.Button cancelButton;
    }
}
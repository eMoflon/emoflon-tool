namespace EAEcoreAddin.ControlPanel.Global
{
    partial class GlobalFunctions
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
            this.components = new System.ComponentModel.Container();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.buttonExportAll = new System.Windows.Forms.Button();
            this.buttonExportMetamodel = new System.Windows.Forms.Button();
            this.buttonValidateAll = new System.Windows.Forms.Button();
            this.buttonValidateSelection = new System.Windows.Forms.Button();
            this.buttonImportFromMoca = new System.Windows.Forms.Button();
            this.buttonJump = new System.Windows.Forms.Button();
            this.buttonAnchor = new System.Windows.Forms.Button();
            this.buttonValidatePackage = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.button1 = new System.Windows.Forms.Button();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBoxValidation = new System.Windows.Forms.GroupBox();
            this.newErrorOutput1 = new EAEcoreAddin.Consistency.RuleHandling.NewErrorOutput();
            this.groupBox2.SuspendLayout();
            this.groupBox4.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBoxValidation.SuspendLayout();
            this.SuspendLayout();
            // 
            // buttonExportAll
            // 
            this.buttonExportAll.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonExportAll.Location = new System.Drawing.Point(225, 23);
            this.buttonExportAll.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonExportAll.Name = "buttonExportAll";
            this.buttonExportAll.Size = new System.Drawing.Size(100, 28);
            this.buttonExportAll.TabIndex = 3;
            this.buttonExportAll.Text = "All";
            this.toolTip.SetToolTip(this.buttonExportAll, "Export all metamodels to Workspace");
            this.buttonExportAll.UseVisualStyleBackColor = true;
            this.buttonExportAll.Click += new System.EventHandler(this.buttonExportAll_Click);
            // 
            // buttonExportMetamodel
            // 
            this.buttonExportMetamodel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonExportMetamodel.Location = new System.Drawing.Point(117, 23);
            this.buttonExportMetamodel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonExportMetamodel.Name = "buttonExportMetamodel";
            this.buttonExportMetamodel.Size = new System.Drawing.Size(100, 28);
            this.buttonExportMetamodel.TabIndex = 2;
            this.buttonExportMetamodel.Text = "Metamodel";
            this.toolTip.SetToolTip(this.buttonExportMetamodel, "Export current Metamodel to Workspace");
            this.buttonExportMetamodel.UseVisualStyleBackColor = true;
            this.buttonExportMetamodel.Click += new System.EventHandler(this.buttonExportSelection_Click);
            // 
            // buttonValidateAll
            // 
            this.buttonValidateAll.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonValidateAll.Location = new System.Drawing.Point(225, 23);
            this.buttonValidateAll.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonValidateAll.Name = "buttonValidateAll";
            this.buttonValidateAll.Size = new System.Drawing.Size(100, 28);
            this.buttonValidateAll.TabIndex = 3;
            this.buttonValidateAll.Text = "All";
            this.toolTip.SetToolTip(this.buttonValidateAll, "Validate and export all metamodels");
            this.buttonValidateAll.UseVisualStyleBackColor = true;
            this.buttonValidateAll.Click += new System.EventHandler(this.buttonValidateAll_Click);
            // 
            // buttonValidateSelection
            // 
            this.buttonValidateSelection.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonValidateSelection.Location = new System.Drawing.Point(9, 23);
            this.buttonValidateSelection.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonValidateSelection.Name = "buttonValidateSelection";
            this.buttonValidateSelection.Size = new System.Drawing.Size(100, 28);
            this.buttonValidateSelection.TabIndex = 2;
            this.buttonValidateSelection.Text = "Selection";
            this.toolTip.SetToolTip(this.buttonValidateSelection, "Validate current Selection ");
            this.buttonValidateSelection.UseVisualStyleBackColor = true;
            this.buttonValidateSelection.Click += new System.EventHandler(this.buttonValidateSelection_Click);
            // 
            // buttonImportFromMoca
            // 
            this.buttonImportFromMoca.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonImportFromMoca.Location = new System.Drawing.Point(9, 24);
            this.buttonImportFromMoca.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonImportFromMoca.Name = "buttonImportFromMoca";
            this.buttonImportFromMoca.Size = new System.Drawing.Size(100, 28);
            this.buttonImportFromMoca.TabIndex = 2;
            this.buttonImportFromMoca.Text = "Import";
            this.toolTip.SetToolTip(this.buttonImportFromMoca, "Import from Moca Tree");
            this.buttonImportFromMoca.UseVisualStyleBackColor = true;
            this.buttonImportFromMoca.Click += new System.EventHandler(this.button2_Click);
            // 
            // buttonJump
            // 
            this.buttonJump.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.buttonJump.BackgroundImage = global::EAEcoreAddin.Properties.Resources.arrowInside1;
            this.buttonJump.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.buttonJump.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonJump.Location = new System.Drawing.Point(47, 25);
            this.buttonJump.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonJump.Name = "buttonJump";
            this.buttonJump.Size = new System.Drawing.Size(31, 28);
            this.buttonJump.TabIndex = 3;
            this.toolTip.SetToolTip(this.buttonJump, "Jump to Called SDM");
            this.buttonJump.UseVisualStyleBackColor = false;
            this.buttonJump.Click += new System.EventHandler(this.buttonJump_Click);
            // 
            // buttonAnchor
            // 
            this.buttonAnchor.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.buttonAnchor.BackgroundImage = global::EAEcoreAddin.Properties.Resources.anchor_small;
            this.buttonAnchor.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.buttonAnchor.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonAnchor.Location = new System.Drawing.Point(8, 25);
            this.buttonAnchor.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonAnchor.Name = "buttonAnchor";
            this.buttonAnchor.Size = new System.Drawing.Size(31, 28);
            this.buttonAnchor.TabIndex = 2;
            this.toolTip.SetToolTip(this.buttonAnchor, "Use Anchor on Diagram");
            this.buttonAnchor.UseVisualStyleBackColor = false;
            this.buttonAnchor.Click += new System.EventHandler(this.buttonAnchor_Click);
            // 
            // buttonValidatePackage
            // 
            this.buttonValidatePackage.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonValidatePackage.Location = new System.Drawing.Point(117, 23);
            this.buttonValidatePackage.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonValidatePackage.Name = "buttonValidatePackage";
            this.buttonValidatePackage.Size = new System.Drawing.Size(100, 28);
            this.buttonValidatePackage.TabIndex = 4;
            this.buttonValidatePackage.Text = "Metamodel";
            this.toolTip.SetToolTip(this.buttonValidatePackage, " Validate and export current metamodel");
            this.buttonValidatePackage.UseVisualStyleBackColor = true;
            this.buttonValidatePackage.Click += new System.EventHandler(this.buttonValidatePackage_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.buttonExportAll);
            this.groupBox2.Controls.Add(this.buttonExportMetamodel);
            this.groupBox2.Controls.Add(this.button1);
            this.groupBox2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.Location = new System.Drawing.Point(0, 0);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(0);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox2.Size = new System.Drawing.Size(338, 60);
            this.groupBox2.TabIndex = 4;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Export";
            // 
            // button1
            // 
            this.button1.Enabled = false;
            this.button1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F);
            this.button1.Location = new System.Drawing.Point(9, 23);
            this.button1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(100, 28);
            this.button1.TabIndex = 8;
            this.button1.Text = "Selection";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.buttonValidatePackage);
            this.groupBox4.Controls.Add(this.buttonValidateAll);
            this.groupBox4.Controls.Add(this.buttonValidateSelection);
            this.groupBox4.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox4.Location = new System.Drawing.Point(0, 65);
            this.groupBox4.Margin = new System.Windows.Forms.Padding(0);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox4.Size = new System.Drawing.Size(338, 66);
            this.groupBox4.TabIndex = 5;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "Validate";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.buttonImportFromMoca);
            this.groupBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.Location = new System.Drawing.Point(0, 141);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(0);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Size = new System.Drawing.Size(196, 60);
            this.groupBox1.TabIndex = 6;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Import";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.buttonJump);
            this.groupBox3.Controls.Add(this.buttonAnchor);
            this.groupBox3.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox3.Location = new System.Drawing.Point(206, 141);
            this.groupBox3.Margin = new System.Windows.Forms.Padding(0);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox3.Size = new System.Drawing.Size(132, 60);
            this.groupBox3.TabIndex = 7;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Navigation";
            // 
            // groupBoxValidation
            // 
            this.groupBoxValidation.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBoxValidation.Controls.Add(this.newErrorOutput1);
            this.groupBoxValidation.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBoxValidation.Location = new System.Drawing.Point(338, 0);
            this.groupBoxValidation.Margin = new System.Windows.Forms.Padding(0);
            this.groupBoxValidation.Name = "groupBoxValidation";
            this.groupBoxValidation.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBoxValidation.Size = new System.Drawing.Size(731, 214);
            this.groupBoxValidation.TabIndex = 0;
            this.groupBoxValidation.TabStop = false;
            this.groupBoxValidation.Text = "Validation Results";
            // 
            // newErrorOutput1
            // 
            this.newErrorOutput1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.newErrorOutput1.ErrorsExisting = false;
            this.newErrorOutput1.ErrorSummary = null;
            this.newErrorOutput1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.newErrorOutput1.Location = new System.Drawing.Point(4, 21);
            this.newErrorOutput1.Margin = new System.Windows.Forms.Padding(0);
            this.newErrorOutput1.Name = "newErrorOutput1";
            this.newErrorOutput1.Size = new System.Drawing.Size(723, 189);
            this.newErrorOutput1.TabIndex = 0;
            // 
            // GlobalFunctions
            // 
            this.AllowDrop = true;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ButtonHighlight;
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBoxValidation);
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "GlobalFunctions";
            this.Size = new System.Drawing.Size(1069, 214);
            this.groupBox2.ResumeLayout(false);
            this.groupBox4.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox3.ResumeLayout(false);
            this.groupBoxValidation.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button buttonExportMetamodel;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button buttonExportAll;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.Button buttonValidateAll;
        private System.Windows.Forms.Button buttonValidateSelection;
        private System.Windows.Forms.GroupBox groupBoxValidation;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button buttonImportFromMoca;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button buttonAnchor;
        private System.Windows.Forms.Button buttonJump;
        private System.Windows.Forms.Button buttonValidatePackage;
        private System.Windows.Forms.Button button1;
        private Consistency.RuleHandling.NewErrorOutput newErrorOutput1;
    }
}

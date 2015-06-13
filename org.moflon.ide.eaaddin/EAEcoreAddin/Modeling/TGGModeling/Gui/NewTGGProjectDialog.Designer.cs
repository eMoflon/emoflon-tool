namespace EAEcoreAddin.Modeling.TGGModeling.TGGActions
{
    partial class NewTGGProjectDialog
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(NewTGGProjectDialog));
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.smoothTableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.smoothTableLayoutPanel3 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.targetDomainLabel = new System.Windows.Forms.Label();
            this.sourceDomainLabel = new System.Windows.Forms.Label();
            this.targetDomain = new System.Windows.Forms.ComboBox();
            this.sourceDomain = new System.Windows.Forms.ComboBox();
            this.smoothTableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.smoothTableLayoutPanel4 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.textBoxName = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.smoothTableLayoutPanel1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.smoothTableLayoutPanel3.SuspendLayout();
            this.smoothTableLayoutPanel2.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.smoothTableLayoutPanel4.SuspendLayout();
            this.SuspendLayout();
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(3, 3);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 6;
            this.okButton.TabStop = false;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Location = new System.Drawing.Point(84, 3);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 7;
            this.cancelButton.TabStop = false;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // smoothTableLayoutPanel1
            // 
            this.smoothTableLayoutPanel1.ColumnCount = 1;
            this.smoothTableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox1, 0, 1);
            this.smoothTableLayoutPanel1.Controls.Add(this.smoothTableLayoutPanel2, 0, 2);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox2, 0, 0);
            this.smoothTableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.smoothTableLayoutPanel1.Name = "smoothTableLayoutPanel1";
            this.smoothTableLayoutPanel1.RowCount = 3;
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.Size = new System.Drawing.Size(384, 190);
            this.smoothTableLayoutPanel1.TabIndex = 9;
            // 
            // groupBox1
            // 
            this.groupBox1.AutoSize = true;
            this.groupBox1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox1.Controls.Add(this.smoothTableLayoutPanel3);
            this.groupBox1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox1.Location = new System.Drawing.Point(3, 79);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(378, 73);
            this.groupBox1.TabIndex = 8;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Domain Definition";
            // 
            // smoothTableLayoutPanel3
            // 
            this.smoothTableLayoutPanel3.AutoSize = true;
            this.smoothTableLayoutPanel3.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.smoothTableLayoutPanel3.ColumnCount = 2;
            this.smoothTableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 95F));
            this.smoothTableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel3.Controls.Add(this.targetDomainLabel, 0, 1);
            this.smoothTableLayoutPanel3.Controls.Add(this.sourceDomainLabel, 0, 0);
            this.smoothTableLayoutPanel3.Controls.Add(this.targetDomain, 1, 1);
            this.smoothTableLayoutPanel3.Controls.Add(this.sourceDomain, 1, 0);
            this.smoothTableLayoutPanel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel3.Location = new System.Drawing.Point(3, 16);
            this.smoothTableLayoutPanel3.Name = "smoothTableLayoutPanel3";
            this.smoothTableLayoutPanel3.RowCount = 2;
            this.smoothTableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel3.Size = new System.Drawing.Size(372, 54);
            this.smoothTableLayoutPanel3.TabIndex = 10;
            // 
            // targetDomainLabel
            // 
            this.targetDomainLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.targetDomainLabel.AutoSize = true;
            this.targetDomainLabel.Location = new System.Drawing.Point(3, 34);
            this.targetDomainLabel.Name = "targetDomainLabel";
            this.targetDomainLabel.Size = new System.Drawing.Size(80, 13);
            this.targetDomainLabel.TabIndex = 4;
            this.targetDomainLabel.Text = "Target Project :";
            // 
            // sourceDomainLabel
            // 
            this.sourceDomainLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.sourceDomainLabel.AutoSize = true;
            this.sourceDomainLabel.Location = new System.Drawing.Point(3, 7);
            this.sourceDomainLabel.Name = "sourceDomainLabel";
            this.sourceDomainLabel.Size = new System.Drawing.Size(83, 13);
            this.sourceDomainLabel.TabIndex = 3;
            this.sourceDomainLabel.Text = "Source Project :";
            // 
            // targetDomain
            // 
            this.targetDomain.Dock = System.Windows.Forms.DockStyle.Top;
            this.targetDomain.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.targetDomain.FormattingEnabled = true;
            this.targetDomain.Location = new System.Drawing.Point(98, 30);
            this.targetDomain.Name = "targetDomain";
            this.targetDomain.Size = new System.Drawing.Size(271, 21);
            this.targetDomain.TabIndex = 1;
            this.targetDomain.SelectedIndexChanged += new System.EventHandler(this.targetDomain_SelectedIndexChanged);
            // 
            // sourceDomain
            // 
            this.sourceDomain.Dock = System.Windows.Forms.DockStyle.Top;
            this.sourceDomain.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.sourceDomain.FormattingEnabled = true;
            this.sourceDomain.Location = new System.Drawing.Point(98, 3);
            this.sourceDomain.Name = "sourceDomain";
            this.sourceDomain.Size = new System.Drawing.Size(271, 21);
            this.sourceDomain.TabIndex = 0;
            this.sourceDomain.SelectedIndexChanged += new System.EventHandler(this.sourceDomain_SelectedIndexChanged);
            // 
            // smoothTableLayoutPanel2
            // 
            this.smoothTableLayoutPanel2.AutoSize = true;
            this.smoothTableLayoutPanel2.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.smoothTableLayoutPanel2.ColumnCount = 2;
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel2.Controls.Add(this.okButton, 0, 0);
            this.smoothTableLayoutPanel2.Controls.Add(this.cancelButton, 1, 0);
            this.smoothTableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Right;
            this.smoothTableLayoutPanel2.Location = new System.Drawing.Point(219, 158);
            this.smoothTableLayoutPanel2.Name = "smoothTableLayoutPanel2";
            this.smoothTableLayoutPanel2.RowCount = 1;
            this.smoothTableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel2.Size = new System.Drawing.Size(162, 29);
            this.smoothTableLayoutPanel2.TabIndex = 9;
            // 
            // groupBox2
            // 
            this.groupBox2.AutoSize = true;
            this.groupBox2.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox2.Controls.Add(this.smoothTableLayoutPanel4);
            this.groupBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox2.Location = new System.Drawing.Point(3, 3);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(378, 70);
            this.groupBox2.TabIndex = 10;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Properties";
            this.groupBox2.Enter += new System.EventHandler(this.groupBox2_Enter);
            // 
            // smoothTableLayoutPanel4
            // 
            this.smoothTableLayoutPanel4.AutoSize = true;
            this.smoothTableLayoutPanel4.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.smoothTableLayoutPanel4.ColumnCount = 2;
            this.smoothTableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 55F));
            this.smoothTableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel4.Controls.Add(this.textBoxName, 1, 0);
            this.smoothTableLayoutPanel4.Controls.Add(this.label1, 0, 0);
            this.smoothTableLayoutPanel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel4.Location = new System.Drawing.Point(3, 16);
            this.smoothTableLayoutPanel4.Name = "smoothTableLayoutPanel4";
            this.smoothTableLayoutPanel4.RowCount = 2;
            this.smoothTableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel4.Size = new System.Drawing.Size(372, 51);
            this.smoothTableLayoutPanel4.TabIndex = 0;
            // 
            // textBoxName
            // 
            this.textBoxName.Dock = System.Windows.Forms.DockStyle.Top;
            this.textBoxName.Location = new System.Drawing.Point(58, 3);
            this.textBoxName.Name = "textBoxName";
            this.textBoxName.Size = new System.Drawing.Size(311, 20);
            this.textBoxName.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Name :";
            // 
            // NewTGGProjectDialog
            // 
            this.AcceptButton = this.okButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.cancelButton;
            this.ClientSize = new System.Drawing.Size(384, 190);
            this.Controls.Add(this.smoothTableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximumSize = new System.Drawing.Size(3000, 229);
            this.MinimumSize = new System.Drawing.Size(400, 229);
            this.Name = "NewTGGProjectDialog";
            this.Text = "TGG Project";
            this.smoothTableLayoutPanel1.ResumeLayout(false);
            this.smoothTableLayoutPanel1.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.smoothTableLayoutPanel3.ResumeLayout(false);
            this.smoothTableLayoutPanel3.PerformLayout();
            this.smoothTableLayoutPanel2.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.smoothTableLayoutPanel4.ResumeLayout(false);
            this.smoothTableLayoutPanel4.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ComboBox sourceDomain;
        private System.Windows.Forms.Label sourceDomainLabel;
        private System.Windows.Forms.Label targetDomainLabel;
        private System.Windows.Forms.ComboBox targetDomain;
        private System.Windows.Forms.GroupBox groupBox1;
        private SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel1;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel3;
        private SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel2;
        private System.Windows.Forms.GroupBox groupBox2;
        private SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel4;
        private System.Windows.Forms.TextBox textBoxName;
        private System.Windows.Forms.Label label1;
    }
}
namespace EAEcoreAddin.Consistency.Gui
{
    partial class RuleQuickFixControl
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
            this.groupBoxRadio = new System.Windows.Forms.GroupBox();
            this.groupBoxMessage = new System.Windows.Forms.GroupBox();
            this.labelMessage = new System.Windows.Forms.Label();
            this.smoothTableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.bttOk = new System.Windows.Forms.Button();
            this.smoothTableLayoutPanel1.SuspendLayout();
            this.groupBoxMessage.SuspendLayout();
            this.smoothTableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // smoothTableLayoutPanel1
            // 
            this.smoothTableLayoutPanel1.AutoSize = true;
            this.smoothTableLayoutPanel1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.smoothTableLayoutPanel1.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.smoothTableLayoutPanel1.ColumnCount = 1;
            this.smoothTableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBoxRadio, 0, 1);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBoxMessage, 0, 0);
            this.smoothTableLayoutPanel1.Controls.Add(this.smoothTableLayoutPanel2, 0, 2);
            this.smoothTableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.smoothTableLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.smoothTableLayoutPanel1.Name = "smoothTableLayoutPanel1";
            this.smoothTableLayoutPanel1.RowCount = 3;
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.smoothTableLayoutPanel1.Size = new System.Drawing.Size(459, 92);
            this.smoothTableLayoutPanel1.TabIndex = 9;
            // 
            // groupBoxRadio
            // 
            this.groupBoxRadio.AutoSize = true;
            this.groupBoxRadio.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBoxRadio.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.groupBoxRadio.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBoxRadio.Location = new System.Drawing.Point(3, 54);
            this.groupBoxRadio.Name = "groupBoxRadio";
            this.groupBoxRadio.Size = new System.Drawing.Size(453, 5);
            this.groupBoxRadio.TabIndex = 0;
            this.groupBoxRadio.TabStop = false;
            this.groupBoxRadio.Text = "Quickfix";
            // 
            // groupBoxMessage
            // 
            this.groupBoxMessage.AutoSize = true;
            this.groupBoxMessage.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBoxMessage.Controls.Add(this.labelMessage);
            this.groupBoxMessage.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBoxMessage.Location = new System.Drawing.Point(3, 3);
            this.groupBoxMessage.Name = "groupBoxMessage";
            this.groupBoxMessage.Size = new System.Drawing.Size(453, 45);
            this.groupBoxMessage.TabIndex = 8;
            this.groupBoxMessage.TabStop = false;
            this.groupBoxMessage.Text = "Error Message";
            // 
            // labelMessage
            // 
            this.labelMessage.AutoSize = true;
            this.labelMessage.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelMessage.Location = new System.Drawing.Point(6, 16);
            this.labelMessage.Name = "labelMessage";
            this.labelMessage.Size = new System.Drawing.Size(441, 13);
            this.labelMessage.TabIndex = 0;
            this.labelMessage.Text = "dawdawdwdawdawdwdawdawdwdawdawdwdawdawdwdawdawdwdawdawdw";
            // 
            // smoothTableLayoutPanel2
            // 
            this.smoothTableLayoutPanel2.AutoSize = true;
            this.smoothTableLayoutPanel2.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.smoothTableLayoutPanel2.ColumnCount = 2;
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel2.Controls.Add(this.buttonCancel, 1, 0);
            this.smoothTableLayoutPanel2.Controls.Add(this.bttOk, 0, 0);
            this.smoothTableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Right;
            this.smoothTableLayoutPanel2.Location = new System.Drawing.Point(297, 62);
            this.smoothTableLayoutPanel2.Margin = new System.Windows.Forms.Padding(0);
            this.smoothTableLayoutPanel2.Name = "smoothTableLayoutPanel2";
            this.smoothTableLayoutPanel2.RowCount = 1;
            this.smoothTableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel2.Size = new System.Drawing.Size(162, 30);
            this.smoothTableLayoutPanel2.TabIndex = 9;
            // 
            // buttonCancel
            // 
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(84, 3);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 5;
            this.buttonCancel.TabStop = false;
            this.buttonCancel.Text = "&Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            // 
            // bttOk
            // 
            this.bttOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bttOk.Location = new System.Drawing.Point(3, 3);
            this.bttOk.Name = "bttOk";
            this.bttOk.Size = new System.Drawing.Size(75, 23);
            this.bttOk.TabIndex = 4;
            this.bttOk.TabStop = false;
            this.bttOk.Text = "&OK";
            this.bttOk.UseVisualStyleBackColor = true;
            this.bttOk.Click += new System.EventHandler(this.OKButton_Click);
            // 
            // RuleQuickFixControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.Controls.Add(this.smoothTableLayoutPanel1);
            this.Name = "RuleQuickFixControl";
            this.Size = new System.Drawing.Size(459, 92);
            this.smoothTableLayoutPanel1.ResumeLayout(false);
            this.smoothTableLayoutPanel1.PerformLayout();
            this.groupBoxMessage.ResumeLayout(false);
            this.groupBoxMessage.PerformLayout();
            this.smoothTableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel1;
        private System.Windows.Forms.GroupBox groupBoxRadio;
        private System.Windows.Forms.GroupBox groupBoxMessage;
        private System.Windows.Forms.Label labelMessage;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel2;
        public System.Windows.Forms.Button buttonCancel;
        public System.Windows.Forms.Button bttOk;
    }
}

namespace EAEcoreAddin.Import.Gui
{
    partial class ImportDialog
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ImportDialog));
            this.smoothTableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.smoothTableLayoutPanel4 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonImport = new System.Windows.Forms.Button();
            this.labelTimer = new System.Windows.Forms.Label();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.labelCurrent = new System.Windows.Forms.Label();
            this.pBarCurrent = new System.Windows.Forms.ProgressBar();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.labelComplete = new System.Windows.Forms.Label();
            this.pBarComplete = new System.Windows.Forms.ProgressBar();
            this.groupBoxChooseFile = new System.Windows.Forms.GroupBox();
            this.smoothTableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.textBoxFile = new System.Windows.Forms.TextBox();
            this.buttonSelectFile = new System.Windows.Forms.Button();
            this.groupBoxCheckboxes = new System.Windows.Forms.GroupBox();
            this.smoothTableLayoutPanel1.SuspendLayout();
            this.smoothTableLayoutPanel4.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBoxChooseFile.SuspendLayout();
            this.smoothTableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // smoothTableLayoutPanel1
            // 
            this.smoothTableLayoutPanel1.ColumnCount = 1;
            this.smoothTableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.Controls.Add(this.smoothTableLayoutPanel4, 0, 4);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox3, 0, 3);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBox2, 0, 2);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBoxChooseFile, 0, 0);
            this.smoothTableLayoutPanel1.Controls.Add(this.groupBoxCheckboxes, 0, 1);
            this.smoothTableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.smoothTableLayoutPanel1.Name = "smoothTableLayoutPanel1";
            this.smoothTableLayoutPanel1.RowCount = 5;
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.smoothTableLayoutPanel1.Size = new System.Drawing.Size(476, 314);
            this.smoothTableLayoutPanel1.TabIndex = 4;
            // 
            // smoothTableLayoutPanel4
            // 
            this.smoothTableLayoutPanel4.ColumnCount = 3;
            this.smoothTableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.smoothTableLayoutPanel4.Controls.Add(this.buttonCancel, 2, 0);
            this.smoothTableLayoutPanel4.Controls.Add(this.buttonImport, 1, 0);
            this.smoothTableLayoutPanel4.Controls.Add(this.labelTimer, 0, 0);
            this.smoothTableLayoutPanel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel4.Location = new System.Drawing.Point(3, 282);
            this.smoothTableLayoutPanel4.Name = "smoothTableLayoutPanel4";
            this.smoothTableLayoutPanel4.RowCount = 1;
            this.smoothTableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel4.Size = new System.Drawing.Size(470, 29);
            this.smoothTableLayoutPanel4.TabIndex = 12;
            // 
            // buttonCancel
            // 
            this.buttonCancel.Location = new System.Drawing.Point(393, 3);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(74, 23);
            this.buttonCancel.TabIndex = 0;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonImport
            // 
            this.buttonImport.Location = new System.Drawing.Point(313, 3);
            this.buttonImport.Name = "buttonImport";
            this.buttonImport.Size = new System.Drawing.Size(74, 23);
            this.buttonImport.TabIndex = 1;
            this.buttonImport.Text = "Import";
            this.buttonImport.UseVisualStyleBackColor = true;
            this.buttonImport.Click += new System.EventHandler(this.buttonImport_Click);
            // 
            // labelTimer
            // 
            this.labelTimer.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.labelTimer.AutoSize = true;
            this.labelTimer.Location = new System.Drawing.Point(3, 8);
            this.labelTimer.Name = "labelTimer";
            this.labelTimer.Size = new System.Drawing.Size(35, 13);
            this.labelTimer.TabIndex = 2;
            this.labelTimer.Text = "label1";
            this.labelTimer.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // groupBox3
            // 
            this.groupBox3.AutoSize = true;
            this.groupBox3.Controls.Add(this.labelCurrent);
            this.groupBox3.Controls.Add(this.pBarCurrent);
            this.groupBox3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox3.Location = new System.Drawing.Point(3, 205);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(470, 71);
            this.groupBox3.TabIndex = 6;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Current";
            // 
            // labelCurrent
            // 
            this.labelCurrent.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.labelCurrent.AutoSize = true;
            this.labelCurrent.Location = new System.Drawing.Point(182, 42);
            this.labelCurrent.Name = "labelCurrent";
            this.labelCurrent.Size = new System.Drawing.Size(40, 13);
            this.labelCurrent.TabIndex = 8;
            this.labelCurrent.Text = "current";
            this.labelCurrent.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // pBarCurrent
            // 
            this.pBarCurrent.Dock = System.Windows.Forms.DockStyle.Top;
            this.pBarCurrent.Location = new System.Drawing.Point(3, 16);
            this.pBarCurrent.Name = "pBarCurrent";
            this.pBarCurrent.Size = new System.Drawing.Size(464, 23);
            this.pBarCurrent.TabIndex = 7;
            // 
            // groupBox2
            // 
            this.groupBox2.AutoSize = true;
            this.groupBox2.Controls.Add(this.labelComplete);
            this.groupBox2.Controls.Add(this.pBarComplete);
            this.groupBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox2.Location = new System.Drawing.Point(3, 128);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(470, 71);
            this.groupBox2.TabIndex = 5;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Complete";
            // 
            // labelComplete
            // 
            this.labelComplete.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.labelComplete.AutoSize = true;
            this.labelComplete.Location = new System.Drawing.Point(182, 42);
            this.labelComplete.Name = "labelComplete";
            this.labelComplete.Size = new System.Drawing.Size(50, 13);
            this.labelComplete.TabIndex = 7;
            this.labelComplete.Text = "complete";
            this.labelComplete.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // pBarComplete
            // 
            this.pBarComplete.Dock = System.Windows.Forms.DockStyle.Top;
            this.pBarComplete.Location = new System.Drawing.Point(3, 16);
            this.pBarComplete.Name = "pBarComplete";
            this.pBarComplete.Size = new System.Drawing.Size(464, 23);
            this.pBarComplete.TabIndex = 6;
            this.pBarComplete.Tag = "";
            // 
            // groupBoxChooseFile
            // 
            this.groupBoxChooseFile.AutoSize = true;
            this.groupBoxChooseFile.Controls.Add(this.smoothTableLayoutPanel2);
            this.groupBoxChooseFile.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupBoxChooseFile.Location = new System.Drawing.Point(3, 3);
            this.groupBoxChooseFile.Name = "groupBoxChooseFile";
            this.groupBoxChooseFile.Size = new System.Drawing.Size(470, 48);
            this.groupBoxChooseFile.TabIndex = 5;
            this.groupBoxChooseFile.TabStop = false;
            this.groupBoxChooseFile.Text = "Choose File to Import";
            // 
            // smoothTableLayoutPanel2
            // 
            this.smoothTableLayoutPanel2.AutoSize = true;
            this.smoothTableLayoutPanel2.ColumnCount = 2;
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 80F));
            this.smoothTableLayoutPanel2.Controls.Add(this.textBoxFile, 0, 0);
            this.smoothTableLayoutPanel2.Controls.Add(this.buttonSelectFile, 1, 0);
            this.smoothTableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.smoothTableLayoutPanel2.Location = new System.Drawing.Point(3, 16);
            this.smoothTableLayoutPanel2.Name = "smoothTableLayoutPanel2";
            this.smoothTableLayoutPanel2.RowCount = 1;
            this.smoothTableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.smoothTableLayoutPanel2.Size = new System.Drawing.Size(464, 29);
            this.smoothTableLayoutPanel2.TabIndex = 8;
            // 
            // textBoxFile
            // 
            this.textBoxFile.Dock = System.Windows.Forms.DockStyle.Top;
            this.textBoxFile.Location = new System.Drawing.Point(3, 3);
            this.textBoxFile.Name = "textBoxFile";
            this.textBoxFile.Size = new System.Drawing.Size(378, 20);
            this.textBoxFile.TabIndex = 3;
            this.textBoxFile.TextChanged += new System.EventHandler(this.textBoxFile_TextChanged);
            // 
            // buttonSelectFile
            // 
            this.buttonSelectFile.Dock = System.Windows.Forms.DockStyle.Top;
            this.buttonSelectFile.Location = new System.Drawing.Point(387, 3);
            this.buttonSelectFile.Name = "buttonSelectFile";
            this.buttonSelectFile.Size = new System.Drawing.Size(74, 23);
            this.buttonSelectFile.TabIndex = 2;
            this.buttonSelectFile.Text = "Select";
            this.buttonSelectFile.UseVisualStyleBackColor = true;
            this.buttonSelectFile.Click += new System.EventHandler(this.buttonSelectFile_Click_1);
            // 
            // groupBoxCheckboxes
            // 
            this.groupBoxCheckboxes.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBoxCheckboxes.Enabled = false;
            this.groupBoxCheckboxes.Location = new System.Drawing.Point(3, 57);
            this.groupBoxCheckboxes.Name = "groupBoxCheckboxes";
            this.groupBoxCheckboxes.Size = new System.Drawing.Size(470, 65);
            this.groupBoxCheckboxes.TabIndex = 9;
            this.groupBoxCheckboxes.TabStop = false;
            this.groupBoxCheckboxes.Text = "Possible Metamodels";
            // 
            // ImportDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(476, 314);
            this.Controls.Add(this.smoothTableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(492, 352);
            this.Name = "ImportDialog";
            this.Text = "Import";
            this.smoothTableLayoutPanel1.ResumeLayout(false);
            this.smoothTableLayoutPanel1.PerformLayout();
            this.smoothTableLayoutPanel4.ResumeLayout(false);
            this.smoothTableLayoutPanel4.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBoxChooseFile.ResumeLayout(false);
            this.groupBoxChooseFile.PerformLayout();
            this.smoothTableLayoutPanel2.ResumeLayout(false);
            this.smoothTableLayoutPanel2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel1;
        private System.Windows.Forms.GroupBox groupBoxChooseFile;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel2;
        private System.Windows.Forms.TextBox textBoxFile;
        private System.Windows.Forms.Button buttonSelectFile;
        private System.Windows.Forms.GroupBox groupBoxCheckboxes;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel smoothTableLayoutPanel4;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonImport;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Label labelCurrent;
        private System.Windows.Forms.ProgressBar pBarCurrent;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label labelComplete;
        private System.Windows.Forms.ProgressBar pBarComplete;
        private System.Windows.Forms.Label labelTimer;

    }
}
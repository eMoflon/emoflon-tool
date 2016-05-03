using EAEcoreAddin.Modeling.CSP.Gui;
namespace EAEcoreAddin.Modeling.CSP.Gui
{
    partial class CSPInstanceDialog
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CSPInstanceDialog));
            this.comboBoxConstraints = new System.Windows.Forms.ComboBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.CSPConstraintDataGridView1 = new EAEcoreAddin.Modeling.CSP.Gui.CSPDefinitionDataGridView();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.buttonEditUserDefinedConstraint = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.buttonDeleteConstraint = new System.Windows.Forms.Button();
            this.buttonAddConstraint = new System.Windows.Forms.Button();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.listBoxConstraints = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox();
            this.buttonModify = new System.Windows.Forms.Button();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.buttonRemove = new System.Windows.Forms.Button();
            this.labelInformation = new System.Windows.Forms.Label();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonOk = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.CSPConstraintDataGridView1)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox4.SuspendLayout();
            this.SuspendLayout();
            // 
            // comboBoxConstraints
            // 
            this.comboBoxConstraints.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxConstraints.FormattingEnabled = true;
            this.comboBoxConstraints.Location = new System.Drawing.Point(6, 19);
            this.comboBoxConstraints.Name = "comboBoxConstraints";
            this.comboBoxConstraints.Size = new System.Drawing.Size(247, 21);
            this.comboBoxConstraints.TabIndex = 1;
            this.comboBoxConstraints.SelectedIndexChanged += new System.EventHandler(this.comboBoxConstraints_SelectedIndexChanged);
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.CSPConstraintDataGridView1);
            this.groupBox1.Location = new System.Drawing.Point(12, 146);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(746, 191);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Specify Values";
            // 
            // CSPConstraintDataGridView1
            // 
            this.CSPConstraintDataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.CSPConstraintDataGridView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.CSPConstraintDataGridView1.Location = new System.Drawing.Point(3, 16);
            this.CSPConstraintDataGridView1.Name = "CSPConstraintDataGridView1";
            this.CSPConstraintDataGridView1.Size = new System.Drawing.Size(740, 172);
            this.CSPConstraintDataGridView1.TabIndex = 0;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.buttonEditUserDefinedConstraint);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Controls.Add(this.buttonDeleteConstraint);
            this.groupBox2.Controls.Add(this.buttonAddConstraint);
            this.groupBox2.Controls.Add(this.comboBoxConstraints);
            this.groupBox2.Location = new System.Drawing.Point(12, 12);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(259, 128);
            this.groupBox2.TabIndex = 3;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Constraints";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(67, 101);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(115, 13);
            this.label3.TabIndex = 12;
            this.label3.Text = "user defined Constraint";
            // 
            // buttonEditUserDefinedConstraint
            // 
            this.buttonEditUserDefinedConstraint.Location = new System.Drawing.Point(6, 96);
            this.buttonEditUserDefinedConstraint.Name = "buttonEditUserDefinedConstraint";
            this.buttonEditUserDefinedConstraint.Size = new System.Drawing.Size(55, 23);
            this.buttonEditUserDefinedConstraint.TabIndex = 11;
            this.buttonEditUserDefinedConstraint.Text = "Edit";
            this.buttonEditUserDefinedConstraint.UseVisualStyleBackColor = true;
            this.buttonEditUserDefinedConstraint.Click += new System.EventHandler(this.buttonEditUserDefinedConstraint_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(67, 76);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(115, 13);
            this.label2.TabIndex = 10;
            this.label2.Text = "user defined Constraint";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(67, 51);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 13);
            this.label1.TabIndex = 6;
            this.label1.Text = "new Constraint";
            // 
            // buttonDeleteConstraint
            // 
            this.buttonDeleteConstraint.Location = new System.Drawing.Point(6, 71);
            this.buttonDeleteConstraint.Name = "buttonDeleteConstraint";
            this.buttonDeleteConstraint.Size = new System.Drawing.Size(55, 23);
            this.buttonDeleteConstraint.TabIndex = 9;
            this.buttonDeleteConstraint.Text = "Delete";
            this.buttonDeleteConstraint.UseVisualStyleBackColor = true;
            this.buttonDeleteConstraint.Click += new System.EventHandler(this.buttonDeleteConstraint_Click);
            // 
            // buttonAddConstraint
            // 
            this.buttonAddConstraint.Location = new System.Drawing.Point(6, 46);
            this.buttonAddConstraint.Name = "buttonAddConstraint";
            this.buttonAddConstraint.Size = new System.Drawing.Size(55, 23);
            this.buttonAddConstraint.TabIndex = 9;
            this.buttonAddConstraint.Text = "Add";
            this.buttonAddConstraint.UseVisualStyleBackColor = true;
            this.buttonAddConstraint.Click += new System.EventHandler(this.buttonAddConstraint_Click);
            // 
            // groupBox3
            // 
            this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox3.Controls.Add(this.listBoxConstraints);
            this.groupBox3.Controls.Add(this.buttonModify);
            this.groupBox3.Controls.Add(this.buttonAdd);
            this.groupBox3.Controls.Add(this.buttonRemove);
            this.groupBox3.Location = new System.Drawing.Point(12, 343);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(746, 139);
            this.groupBox3.TabIndex = 4;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Constraints";
            // 
            // listBoxConstraints
            // 
            this.listBoxConstraints.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.listBoxConstraints.FormattingEnabled = true;
            this.listBoxConstraints.Location = new System.Drawing.Point(6, 19);
            this.listBoxConstraints.Name = "listBoxConstraints";
            this.listBoxConstraints.Size = new System.Drawing.Size(653, 108);
            this.listBoxConstraints.TabIndex = 0;
            this.listBoxConstraints.SelectedIndexChanged += new System.EventHandler(this.listBoxConstraints_SelectedIndexChanged);
            // 
            // buttonModify
            // 
            this.buttonModify.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonModify.Location = new System.Drawing.Point(665, 48);
            this.buttonModify.Name = "buttonModify";
            this.buttonModify.Size = new System.Drawing.Size(75, 23);
            this.buttonModify.TabIndex = 6;
            this.buttonModify.Text = "Modify";
            this.buttonModify.UseVisualStyleBackColor = true;
            this.buttonModify.Click += new System.EventHandler(this.buttonModify_Click);
            // 
            // buttonAdd
            // 
            this.buttonAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonAdd.Location = new System.Drawing.Point(665, 19);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(75, 23);
            this.buttonAdd.TabIndex = 7;
            this.buttonAdd.Text = "Add";
            this.buttonAdd.UseVisualStyleBackColor = true;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // buttonRemove
            // 
            this.buttonRemove.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonRemove.Location = new System.Drawing.Point(665, 77);
            this.buttonRemove.Name = "buttonRemove";
            this.buttonRemove.Size = new System.Drawing.Size(75, 23);
            this.buttonRemove.TabIndex = 8;
            this.buttonRemove.Text = "Remove";
            this.buttonRemove.UseVisualStyleBackColor = true;
            this.buttonRemove.Click += new System.EventHandler(this.buttonRemove_Click);
            // 
            // labelInformation
            // 
            this.labelInformation.Dock = System.Windows.Forms.DockStyle.Fill;
            this.labelInformation.Location = new System.Drawing.Point(3, 16);
            this.labelInformation.Name = "labelInformation";
            this.labelInformation.Size = new System.Drawing.Size(475, 109);
            this.labelInformation.TabIndex = 5;
            this.labelInformation.Text = "label1";
            // 
            // groupBox4
            // 
            this.groupBox4.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox4.Controls.Add(this.labelInformation);
            this.groupBox4.Location = new System.Drawing.Point(277, 12);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(481, 128);
            this.groupBox4.TabIndex = 5;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "Information";
            // 
            // buttonCancel
            // 
            this.buttonCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(683, 489);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 7;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonOk
            // 
            this.buttonOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOk.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.buttonOk.Location = new System.Drawing.Point(602, 489);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(75, 23);
            this.buttonOk.TabIndex = 8;
            this.buttonOk.Text = "OK";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // CSPInstanceDialog
            // 
            this.AcceptButton = this.buttonOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(770, 524);
            this.Controls.Add(this.buttonOk);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(414, 457);
            this.Name = "CSPInstanceDialog";
            this.Text = "Define CSP";
            this.groupBox1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.CSPConstraintDataGridView1)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox4.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private CSPDefinitionDataGridView CSPConstraintDataGridView1;
        private System.Windows.Forms.ComboBox comboBoxConstraints;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.GroupBox groupBox3;
        private EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox listBoxConstraints;
        private System.Windows.Forms.Label labelInformation;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.Button buttonModify;
        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Button buttonRemove;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button buttonDeleteConstraint;
        private System.Windows.Forms.Button buttonAddConstraint;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button buttonEditUserDefinedConstraint;
    }
}
using EAEcoreAddin.Modeling.SDMModeling.Util;
namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    partial class OvConstraintUserControl
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
            this.tableLayoutPanel1 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.tableLayoutPanel3 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.bttRemove = new System.Windows.Forms.Button();
            this.bttEdit = new System.Windows.Forms.Button();
            this.bttAdd = new System.Windows.Forms.Button();
            this.expressionControl = new EAEcoreAddin.Modeling.SDMModeling.Gui.ExpressionControl();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.listboxEntries = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox();
            this.tableLayoutPanel2 = new EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel();
            this.Attribute = new System.Windows.Forms.GroupBox();
            this.cmbAttributes = new System.Windows.Forms.ComboBox();
            this.groupboxOperation = new System.Windows.Forms.GroupBox();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.radioSet = new System.Windows.Forms.RadioButton();
            this.radioEqual = new System.Windows.Forms.RadioButton();
            this.radioNotEqual = new System.Windows.Forms.RadioButton();
            this.radioLessEqual = new System.Windows.Forms.RadioButton();
            this.radioGreaterEqual = new System.Windows.Forms.RadioButton();
            this.radioGreater = new System.Windows.Forms.RadioButton();
            this.radioLess = new System.Windows.Forms.RadioButton();
            this.tableLayoutPanel1.SuspendLayout();
            this.tableLayoutPanel3.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.Attribute.SuspendLayout();
            this.groupboxOperation.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel3, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.expressionControl, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.groupBox2, 0, 3);
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel2, 0, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 4;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 65F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 35F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(361, 402);
            this.tableLayoutPanel1.TabIndex = 37;
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.tableLayoutPanel3.ColumnCount = 4;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.tableLayoutPanel3.Controls.Add(this.bttRemove, 3, 0);
            this.tableLayoutPanel3.Controls.Add(this.bttEdit, 2, 0);
            this.tableLayoutPanel3.Controls.Add(this.bttAdd, 1, 0);
            this.tableLayoutPanel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel3.Location = new System.Drawing.Point(0, 268);
            this.tableLayoutPanel3.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.tableLayoutPanel3.RowCount = 1;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel3.Size = new System.Drawing.Size(361, 30);
            this.tableLayoutPanel3.TabIndex = 38;
            // 
            // bttRemove
            // 
            this.bttRemove.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bttRemove.Location = new System.Drawing.Point(289, 3);
            this.bttRemove.Name = "bttRemove";
            this.bttRemove.Size = new System.Drawing.Size(69, 23);
            this.bttRemove.TabIndex = 12;
            this.bttRemove.Text = "Remove";
            this.bttRemove.UseVisualStyleBackColor = true;
            this.bttRemove.Click += new System.EventHandler(this.bttRemove_Click);
            // 
            // bttEdit
            // 
            this.bttEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bttEdit.Location = new System.Drawing.Point(214, 3);
            this.bttEdit.Name = "bttEdit";
            this.bttEdit.Size = new System.Drawing.Size(69, 23);
            this.bttEdit.TabIndex = 11;
            this.bttEdit.Text = "Edit";
            this.bttEdit.UseVisualStyleBackColor = true;
            this.bttEdit.Click += new System.EventHandler(this.bttEdit_Click);
            // 
            // bttAdd
            // 
            this.bttAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bttAdd.Location = new System.Drawing.Point(139, 3);
            this.bttAdd.Name = "bttAdd";
            this.bttAdd.Size = new System.Drawing.Size(69, 23);
            this.bttAdd.TabIndex = 10;
            this.bttAdd.Text = "Add";
            this.bttAdd.UseVisualStyleBackColor = true;
            this.bttAdd.Click += new System.EventHandler(this.bttAdd_Click);
            // 
            // expressionControl
            // 
            this.expressionControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.expressionControl.Location = new System.Drawing.Point(3, 78);
            this.expressionControl.Name = "expressionControl";
            this.expressionControl.Size = new System.Drawing.Size(355, 187);
            this.expressionControl.TabIndex = 3;
            // 
            // groupBox2
            // 
            this.groupBox2.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox2.Controls.Add(this.listboxEntries);
            this.groupBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox2.Location = new System.Drawing.Point(3, 301);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(355, 98);
            this.groupBox2.TabIndex = 32;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Attribute Assignments / Constraints";
            // 
            // listboxEntries
            // 
            this.listboxEntries.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listboxEntries.FormattingEnabled = true;
            this.listboxEntries.HorizontalScrollbar = true;
            this.listboxEntries.Location = new System.Drawing.Point(3, 16);
            this.listboxEntries.Name = "listboxEntries";
            this.listboxEntries.Size = new System.Drawing.Size(349, 79);
            this.listboxEntries.TabIndex = 7;
            this.listboxEntries.SelectedIndexChanged += new System.EventHandler(this.listBoxSelectedIndexChangedNew);
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableLayoutPanel2.Controls.Add(this.Attribute, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.groupboxOperation, 1, 0);
            this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.tableLayoutPanel2.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel2.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel2.Size = new System.Drawing.Size(361, 69);
            this.tableLayoutPanel2.TabIndex = 37;
            // 
            // Attribute
            // 
            this.Attribute.AutoSize = true;
            this.Attribute.Controls.Add(this.cmbAttributes);
            this.Attribute.Dock = System.Windows.Forms.DockStyle.Top;
            this.Attribute.Location = new System.Drawing.Point(3, 3);
            this.Attribute.Name = "Attribute";
            this.Attribute.Size = new System.Drawing.Size(138, 40);
            this.Attribute.TabIndex = 35;
            this.Attribute.TabStop = false;
            this.Attribute.Text = "Attribute";
            // 
            // cmbAttributes
            // 
            this.cmbAttributes.Dock = System.Windows.Forms.DockStyle.Top;
            this.cmbAttributes.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbAttributes.FormattingEnabled = true;
            this.cmbAttributes.Location = new System.Drawing.Point(3, 16);
            this.cmbAttributes.Name = "cmbAttributes";
            this.cmbAttributes.Size = new System.Drawing.Size(132, 21);
            this.cmbAttributes.TabIndex = 1;
            this.cmbAttributes.SelectedIndexChanged += new System.EventHandler(this.cmbAttributes_SelectedIndexChanged);
            // 
            // groupboxOperation
            // 
            this.groupboxOperation.Controls.Add(this.flowLayoutPanel1);
            this.groupboxOperation.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupboxOperation.Location = new System.Drawing.Point(147, 3);
            this.groupboxOperation.Name = "groupboxOperation";
            this.groupboxOperation.Size = new System.Drawing.Size(211, 66);
            this.groupboxOperation.TabIndex = 34;
            this.groupboxOperation.TabStop = false;
            this.groupboxOperation.Text = "Operator";
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.AutoSize = true;
            this.flowLayoutPanel1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.flowLayoutPanel1.Controls.Add(this.radioSet);
            this.flowLayoutPanel1.Controls.Add(this.radioEqual);
            this.flowLayoutPanel1.Controls.Add(this.radioNotEqual);
            this.flowLayoutPanel1.Controls.Add(this.radioLessEqual);
            this.flowLayoutPanel1.Controls.Add(this.radioGreaterEqual);
            this.flowLayoutPanel1.Controls.Add(this.radioGreater);
            this.flowLayoutPanel1.Controls.Add(this.radioLess);
            this.flowLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(3, 16);
            this.flowLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(205, 50);
            this.flowLayoutPanel1.TabIndex = 2;
            // 
            // radioSet
            // 
            this.radioSet.AutoSize = true;
            this.radioSet.Location = new System.Drawing.Point(3, 3);
            this.radioSet.Name = "radioSet";
            this.radioSet.Size = new System.Drawing.Size(38, 19);
            this.radioSet.TabIndex = 2;
            this.radioSet.TabStop = true;
            this.radioSet.Text = ":=";
            this.radioSet.UseVisualStyleBackColor = true;
            // 
            // radioEqual
            // 
            this.radioEqual.AutoSize = true;
            this.radioEqual.Location = new System.Drawing.Point(47, 3);
            this.radioEqual.Name = "radioEqual";
            this.radioEqual.Size = new System.Drawing.Size(42, 19);
            this.radioEqual.TabIndex = 1;
            this.radioEqual.TabStop = true;
            this.radioEqual.Text = "==";
            this.radioEqual.UseVisualStyleBackColor = true;
            // 
            // radioNotEqual
            // 
            this.radioNotEqual.AutoSize = true;
            this.radioNotEqual.Location = new System.Drawing.Point(95, 3);
            this.radioNotEqual.Name = "radioNotEqual";
            this.radioNotEqual.Size = new System.Drawing.Size(38, 19);
            this.radioNotEqual.TabIndex = 2;
            this.radioNotEqual.TabStop = true;
            this.radioNotEqual.Text = "!=";
            this.radioNotEqual.UseVisualStyleBackColor = true;
            // 
            // radioLessEqual
            // 
            this.radioLessEqual.AutoSize = true;
            this.radioLessEqual.Location = new System.Drawing.Point(139, 3);
            this.radioLessEqual.Name = "radioLessEqual";
            this.radioLessEqual.Size = new System.Drawing.Size(42, 19);
            this.radioLessEqual.TabIndex = 3;
            this.radioLessEqual.TabStop = true;
            this.radioLessEqual.Text = "<=";
            this.radioLessEqual.UseVisualStyleBackColor = true;
            // 
            // radioGreaterEqual
            // 
            this.radioGreaterEqual.AutoSize = true;
            this.radioGreaterEqual.Location = new System.Drawing.Point(3, 28);
            this.radioGreaterEqual.Name = "radioGreaterEqual";
            this.radioGreaterEqual.Size = new System.Drawing.Size(42, 19);
            this.radioGreaterEqual.TabIndex = 3;
            this.radioGreaterEqual.TabStop = true;
            this.radioGreaterEqual.Text = ">=";
            this.radioGreaterEqual.UseVisualStyleBackColor = true;
            // 
            // radioGreater
            // 
            this.radioGreater.AutoSize = true;
            this.radioGreater.Location = new System.Drawing.Point(51, 28);
            this.radioGreater.Name = "radioGreater";
            this.radioGreater.Size = new System.Drawing.Size(35, 19);
            this.radioGreater.TabIndex = 3;
            this.radioGreater.TabStop = true;
            this.radioGreater.Text = ">";
            this.radioGreater.UseVisualStyleBackColor = true;
            // 
            // radioLess
            // 
            this.radioLess.AutoSize = true;
            this.radioLess.Location = new System.Drawing.Point(92, 28);
            this.radioLess.Name = "radioLess";
            this.radioLess.Size = new System.Drawing.Size(35, 19);
            this.radioLess.TabIndex = 3;
            this.radioLess.TabStop = true;
            this.radioLess.Text = "<";
            this.radioLess.UseVisualStyleBackColor = true;
            // 
            // OvConstraintUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.tableLayoutPanel1);
            this.Name = "OvConstraintUserControl";
            this.Size = new System.Drawing.Size(361, 402);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel3.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.tableLayoutPanel2.PerformLayout();
            this.Attribute.ResumeLayout(false);
            this.groupboxOperation.ResumeLayout(false);
            this.groupboxOperation.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            this.flowLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox2;
        private EAEcoreAddin.Modeling.SDMModeling.Gui.Util.SmoothListBox listboxEntries;
        private System.Windows.Forms.GroupBox groupboxOperation;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.RadioButton radioSet;
        private System.Windows.Forms.RadioButton radioEqual;
        private System.Windows.Forms.RadioButton radioNotEqual;
        private System.Windows.Forms.RadioButton radioLessEqual;
        private System.Windows.Forms.RadioButton radioGreaterEqual;
        private System.Windows.Forms.RadioButton radioGreater;
        private System.Windows.Forms.RadioButton radioLess;
        private System.Windows.Forms.GroupBox Attribute;
        private System.Windows.Forms.ComboBox cmbAttributes;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel1;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel2;
        private Modeling.SDMModeling.Gui.Util.SmoothTableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.Button bttRemove;
        private System.Windows.Forms.Button bttEdit;
        private System.Windows.Forms.Button bttAdd;
        public Gui.ExpressionControl expressionControl;

    }
}

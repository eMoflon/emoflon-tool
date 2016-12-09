using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;
namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    partial class OvPropertiesUserControl
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
            this.labelTypedIn = new System.Windows.Forms.Label();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.comboTypes = new EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog.OvClassifiersComboBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.comboNames = new EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog.OvNamesComboBox();
            this.gbxModifier = new System.Windows.Forms.GroupBox();
            this.radioButtonDestroy = new System.Windows.Forms.RadioButton();
            this.radioButtonCreate = new System.Windows.Forms.RadioButton();
            this.radioButtonCheckonly = new System.Windows.Forms.RadioButton();
            this.gbxConstraint = new System.Windows.Forms.GroupBox();
            this.radioButtonNegative = new System.Windows.Forms.RadioButton();
            this.radioButtonMandatory = new System.Windows.Forms.RadioButton();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.checkBoxBound = new System.Windows.Forms.CheckBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.complexNACControl = new EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog.ComplexNAC();
            this.groupBox4.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.gbxModifier.SuspendLayout();
            this.gbxConstraint.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelTypedIn
            // 
            this.labelTypedIn.AutoSize = true;
            this.labelTypedIn.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTypedIn.Location = new System.Drawing.Point(6, 108);
            this.labelTypedIn.Name = "labelTypedIn";
            this.labelTypedIn.Size = new System.Drawing.Size(0, 13);
            this.labelTypedIn.TabIndex = 9;
            // 
            // groupBox4
            // 
            this.groupBox4.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox4.Controls.Add(this.comboTypes);
            this.groupBox4.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupBox4.Location = new System.Drawing.Point(0, 45);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(363, 40);
            this.groupBox4.TabIndex = 39;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "Classi&fier";
            // 
            // comboTypes
            // 
            this.comboTypes.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.SuggestAppend;
            this.comboTypes.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.comboTypes.ClassifierIDs = null;
            this.comboTypes.Classifiers = null;
            this.comboTypes.Dock = System.Windows.Forms.DockStyle.Top;
            this.comboTypes.FormattingEnabled = true;
            this.comboTypes.Location = new System.Drawing.Point(3, 16);
            this.comboTypes.Name = "comboTypes";
            this.comboTypes.selectedClassifier = null;
            this.comboTypes.Size = new System.Drawing.Size(357, 21);
            this.comboTypes.TabIndex = 2;
            this.comboTypes.SelectedIndexChanged += new System.EventHandler(this.comboTypes_SelectedIndexChanged);
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.comboNames);
            this.groupBox3.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupBox3.Location = new System.Drawing.Point(0, 0);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(363, 45);
            this.groupBox3.TabIndex = 38;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "&Name";
            // 
            // comboNames
            // 
            this.comboNames.CbxBound = null;
            this.comboNames.ComboTypes = null;
            this.comboNames.Dock = System.Windows.Forms.DockStyle.Top;
            this.comboNames.ForeColor = System.Drawing.SystemColors.WindowText;
            this.comboNames.FormattingEnabled = true;
            this.comboNames.Location = new System.Drawing.Point(3, 16);
            this.comboNames.Name = "comboNames";
            this.comboNames.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.comboNames.Size = new System.Drawing.Size(357, 21);
            this.comboNames.TabIndex = 1;
            // 
            // gbxModifier
            // 
            this.gbxModifier.Controls.Add(this.radioButtonDestroy);
            this.gbxModifier.Controls.Add(this.radioButtonCreate);
            this.gbxModifier.Controls.Add(this.radioButtonCheckonly);
            this.gbxModifier.Location = new System.Drawing.Point(0, 2);
            this.gbxModifier.MinimumSize = new System.Drawing.Size(115, 96);
            this.gbxModifier.Name = "gbxModifier";
            this.gbxModifier.Size = new System.Drawing.Size(115, 96);
            this.gbxModifier.TabIndex = 40;
            this.gbxModifier.TabStop = false;
            this.gbxModifier.Text = "Binding Operator";
            // 
            // radioButtonDestroy
            // 
            this.radioButtonDestroy.AutoSize = true;
            this.radioButtonDestroy.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.radioButtonDestroy.Location = new System.Drawing.Point(18, 65);
            this.radioButtonDestroy.Name = "radioButtonDestroy";
            this.radioButtonDestroy.Size = new System.Drawing.Size(61, 17);
            this.radioButtonDestroy.TabIndex = 5;
            this.radioButtonDestroy.Text = "&Destroy";
            this.radioButtonDestroy.UseVisualStyleBackColor = true;
            // 
            // radioButtonCreate
            // 
            this.radioButtonCreate.AutoSize = true;
            this.radioButtonCreate.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.radioButtonCreate.Location = new System.Drawing.Point(18, 42);
            this.radioButtonCreate.Name = "radioButtonCreate";
            this.radioButtonCreate.Size = new System.Drawing.Size(56, 17);
            this.radioButtonCreate.TabIndex = 4;
            this.radioButtonCreate.Text = "C&reate";
            this.radioButtonCreate.UseVisualStyleBackColor = true;
            // 
            // radioButtonCheckonly
            // 
            this.radioButtonCheckonly.AutoSize = true;
            this.radioButtonCheckonly.Checked = true;
            this.radioButtonCheckonly.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.radioButtonCheckonly.Location = new System.Drawing.Point(18, 19);
            this.radioButtonCheckonly.Name = "radioButtonCheckonly";
            this.radioButtonCheckonly.Size = new System.Drawing.Size(80, 17);
            this.radioButtonCheckonly.TabIndex = 3;
            this.radioButtonCheckonly.TabStop = true;
            this.radioButtonCheckonly.Text = "Chec&k Only";
            this.radioButtonCheckonly.UseVisualStyleBackColor = true;
            // 
            // gbxConstraint
            // 
            this.gbxConstraint.Controls.Add(this.radioButtonNegative);
            this.gbxConstraint.Controls.Add(this.radioButtonMandatory);
            this.gbxConstraint.Location = new System.Drawing.Point(121, 2);
            this.gbxConstraint.MinimumSize = new System.Drawing.Size(115, 96);
            this.gbxConstraint.Name = "gbxConstraint";
            this.gbxConstraint.Size = new System.Drawing.Size(115, 96);
            this.gbxConstraint.TabIndex = 41;
            this.gbxConstraint.TabStop = false;
            this.gbxConstraint.Text = "Binding Semantics";
            // 
            // radioButtonNegative
            // 
            this.radioButtonNegative.AutoSize = true;
            this.radioButtonNegative.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.radioButtonNegative.Location = new System.Drawing.Point(17, 41);
            this.radioButtonNegative.Name = "radioButtonNegative";
            this.radioButtonNegative.Size = new System.Drawing.Size(68, 17);
            this.radioButtonNegative.TabIndex = 8;
            this.radioButtonNegative.Text = "Negati&ve";
            this.radioButtonNegative.UseVisualStyleBackColor = true;
            this.radioButtonNegative.CheckedChanged += new System.EventHandler(this.radioButtonNegative_CheckedChanged);
            // 
            // radioButtonMandatory
            // 
            this.radioButtonMandatory.AutoSize = true;
            this.radioButtonMandatory.Checked = true;
            this.radioButtonMandatory.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.radioButtonMandatory.Location = new System.Drawing.Point(17, 18);
            this.radioButtonMandatory.Name = "radioButtonMandatory";
            this.radioButtonMandatory.Size = new System.Drawing.Size(75, 17);
            this.radioButtonMandatory.TabIndex = 7;
            this.radioButtonMandatory.TabStop = true;
            this.radioButtonMandatory.Text = "&Mandatory";
            this.radioButtonMandatory.UseVisualStyleBackColor = true;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.checkBoxBound);
            this.groupBox2.Location = new System.Drawing.Point(242, 2);
            this.groupBox2.MinimumSize = new System.Drawing.Size(115, 96);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(115, 96);
            this.groupBox2.TabIndex = 42;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Binding State";
            // 
            // checkBoxBound
            // 
            this.checkBoxBound.AutoSize = true;
            this.checkBoxBound.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.checkBoxBound.Location = new System.Drawing.Point(15, 19);
            this.checkBoxBound.Name = "checkBoxBound";
            this.checkBoxBound.Size = new System.Drawing.Size(57, 17);
            this.checkBoxBound.TabIndex = 19;
            this.checkBoxBound.Text = "&Bound";
            this.checkBoxBound.UseVisualStyleBackColor = true;
            this.checkBoxBound.CheckStateChanged += new System.EventHandler(this.cbxBound_CheckedChanged);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.gbxModifier);
            this.panel1.Controls.Add(this.groupBox2);
            this.panel1.Controls.Add(this.gbxConstraint);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 85);
            this.panel1.Margin = new System.Windows.Forms.Padding(0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(363, 107);
            this.panel1.TabIndex = 43;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.complexNACControl);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 192);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(363, 103);
            this.panel2.TabIndex = 45;
            // 
            // complexNACControl
            // 
            this.complexNACControl.Location = new System.Drawing.Point(3, 3);
            this.complexNACControl.MinimumSize = new System.Drawing.Size(155, 64);
            this.complexNACControl.Name = "complexNACControl";
            this.complexNACControl.Size = new System.Drawing.Size(155, 64);
            this.complexNACControl.TabIndex = 0;
            // 
            // OvPropertiesUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.groupBox3);
            this.MinimumSize = new System.Drawing.Size(363, 298);
            this.Name = "OvPropertiesUserControl";
            this.Size = new System.Drawing.Size(363, 298);
            this.Load += new System.EventHandler(this.OvPropertiesUserControl_Load);
            this.groupBox4.ResumeLayout(false);
            this.groupBox3.ResumeLayout(false);
            this.gbxModifier.ResumeLayout(false);
            this.gbxModifier.PerformLayout();
            this.gbxConstraint.ResumeLayout(false);
            this.gbxConstraint.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label labelTypedIn;
        private System.Windows.Forms.GroupBox groupBox4;
        public OvClassifiersComboBox comboTypes;
        private System.Windows.Forms.GroupBox groupBox3;
        public OvNamesComboBox comboNames;
        private System.Windows.Forms.GroupBox gbxModifier;
        private System.Windows.Forms.RadioButton radioButtonDestroy;
        private System.Windows.Forms.RadioButton radioButtonCreate;
        private System.Windows.Forms.RadioButton radioButtonCheckonly;
        private System.Windows.Forms.GroupBox gbxConstraint;
        private System.Windows.Forms.RadioButton radioButtonNegative;
        private System.Windows.Forms.RadioButton radioButtonMandatory;
        private System.Windows.Forms.GroupBox groupBox2;
        public System.Windows.Forms.CheckBox checkBoxBound;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private ComplexNAC complexNACControl;

    }
}

namespace EAEcoreAddin.Modeling.SDMModeling.Util
{
    partial class EditMultipleObjects
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(EditMultipleObjects));
            this.buttonCancel = new System.Windows.Forms.Button();
            this.buttonOk = new System.Windows.Forms.Button();
            this.checkBoxLinksToo = new System.Windows.Forms.CheckBox();
            this.gbxModifier = new System.Windows.Forms.GroupBox();
            this.radioButtonCreate = new System.Windows.Forms.RadioButton();
            this.radioButtonDestroy = new System.Windows.Forms.RadioButton();
            this.radioButtonCheckOnly = new System.Windows.Forms.RadioButton();
            this.gbxType = new System.Windows.Forms.GroupBox();
            this.radioButtonNegative = new System.Windows.Forms.RadioButton();
            this.radioButtonMandatory = new System.Windows.Forms.RadioButton();
            this.gbxModifier.SuspendLayout();
            this.gbxType.SuspendLayout();
            this.SuspendLayout();
            // 
            // buttonCancel
            // 
            this.buttonCancel.Location = new System.Drawing.Point(186, 145);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 0;
            this.buttonCancel.Text = "Cancel";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // buttonOk
            // 
            this.buttonOk.Location = new System.Drawing.Point(105, 145);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(75, 23);
            this.buttonOk.TabIndex = 1;
            this.buttonOk.Text = "OK";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // checkBoxLinksToo
            // 
            this.checkBoxLinksToo.AutoSize = true;
            this.checkBoxLinksToo.Location = new System.Drawing.Point(12, 111);
            this.checkBoxLinksToo.Name = "checkBoxLinksToo";
            this.checkBoxLinksToo.Size = new System.Drawing.Size(121, 17);
            this.checkBoxLinksToo.TabIndex = 2;
            this.checkBoxLinksToo.Text = "change links as well";
            this.checkBoxLinksToo.UseVisualStyleBackColor = true;
            // 
            // gbxModifier
            // 
            this.gbxModifier.Controls.Add(this.radioButtonCreate);
            this.gbxModifier.Controls.Add(this.radioButtonDestroy);
            this.gbxModifier.Controls.Add(this.radioButtonCheckOnly);
            this.gbxModifier.Location = new System.Drawing.Point(11, 12);
            this.gbxModifier.Name = "gbxModifier";
            this.gbxModifier.Size = new System.Drawing.Size(122, 93);
            this.gbxModifier.TabIndex = 21;
            this.gbxModifier.TabStop = false;
            this.gbxModifier.Text = "Binding Operator";
            // 
            // radioButtonCreate
            // 
            this.radioButtonCreate.AutoSize = true;
            this.radioButtonCreate.Location = new System.Drawing.Point(6, 42);
            this.radioButtonCreate.Name = "radioButtonCreate";
            this.radioButtonCreate.Size = new System.Drawing.Size(56, 17);
            this.radioButtonCreate.TabIndex = 7;
            this.radioButtonCreate.Text = "Create";
            this.radioButtonCreate.UseVisualStyleBackColor = true;
            this.radioButtonCreate.CheckedChanged += new System.EventHandler(this.radioButtonCreate_CheckedChanged);
            // 
            // radioButtonDestroy
            // 
            this.radioButtonDestroy.AutoSize = true;
            this.radioButtonDestroy.Location = new System.Drawing.Point(6, 65);
            this.radioButtonDestroy.Name = "radioButtonDestroy";
            this.radioButtonDestroy.Size = new System.Drawing.Size(61, 17);
            this.radioButtonDestroy.TabIndex = 6;
            this.radioButtonDestroy.Text = "Destroy";
            this.radioButtonDestroy.UseVisualStyleBackColor = true;
            // 
            // radioButtonCheckOnly
            // 
            this.radioButtonCheckOnly.AutoSize = true;
            this.radioButtonCheckOnly.Checked = true;
            this.radioButtonCheckOnly.Location = new System.Drawing.Point(6, 19);
            this.radioButtonCheckOnly.Name = "radioButtonCheckOnly";
            this.radioButtonCheckOnly.Size = new System.Drawing.Size(80, 17);
            this.radioButtonCheckOnly.TabIndex = 5;
            this.radioButtonCheckOnly.TabStop = true;
            this.radioButtonCheckOnly.Text = "Check Only";
            this.radioButtonCheckOnly.UseVisualStyleBackColor = true;
            // 
            // gbxType
            // 
            this.gbxType.Controls.Add(this.radioButtonNegative);
            this.gbxType.Controls.Add(this.radioButtonMandatory);
            this.gbxType.Location = new System.Drawing.Point(139, 12);
            this.gbxType.Name = "gbxType";
            this.gbxType.Size = new System.Drawing.Size(122, 93);
            this.gbxType.TabIndex = 20;
            this.gbxType.TabStop = false;
            this.gbxType.Text = "Binding Semantics";
            // 
            // radioButtonNegative
            // 
            this.radioButtonNegative.AutoSize = true;
            this.radioButtonNegative.Location = new System.Drawing.Point(6, 42);
            this.radioButtonNegative.Name = "radioButtonNegative";
            this.radioButtonNegative.Size = new System.Drawing.Size(68, 17);
            this.radioButtonNegative.TabIndex = 3;
            this.radioButtonNegative.Text = "Negative";
            this.radioButtonNegative.UseVisualStyleBackColor = true;
            // 
            // radioButtonMandatory
            // 
            this.radioButtonMandatory.AutoSize = true;
            this.radioButtonMandatory.Checked = true;
            this.radioButtonMandatory.Location = new System.Drawing.Point(6, 19);
            this.radioButtonMandatory.Name = "radioButtonMandatory";
            this.radioButtonMandatory.Size = new System.Drawing.Size(75, 17);
            this.radioButtonMandatory.TabIndex = 2;
            this.radioButtonMandatory.TabStop = true;
            this.radioButtonMandatory.Text = "Mandatory";
            this.radioButtonMandatory.UseVisualStyleBackColor = true;
            // 
            // EditMultipleObjects
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(273, 180);
            this.Controls.Add(this.gbxType);
            this.Controls.Add(this.gbxModifier);
            this.Controls.Add(this.checkBoxLinksToo);
            this.Controls.Add(this.buttonOk);
            this.Controls.Add(this.buttonCancel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "EditMultipleObjects";
            this.Text = "Edit multiple Objects";
            this.gbxModifier.ResumeLayout(false);
            this.gbxModifier.PerformLayout();
            this.gbxType.ResumeLayout(false);
            this.gbxType.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.CheckBox checkBoxLinksToo;
        private System.Windows.Forms.GroupBox gbxModifier;
        private System.Windows.Forms.RadioButton radioButtonCreate;
        private System.Windows.Forms.RadioButton radioButtonDestroy;
        private System.Windows.Forms.RadioButton radioButtonCheckOnly;
        private System.Windows.Forms.GroupBox gbxType;
        private System.Windows.Forms.RadioButton radioButtonNegative;
        private System.Windows.Forms.RadioButton radioButtonMandatory;
    }
}
using EAEcoreAddin.Consistency.Gui;
namespace EAEcoreAddin.Consistency
{
    partial class RuleQuickfixDialogue
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(RuleQuickfixDialogue));
            this.quickfixControl = new EAEcoreAddin.Consistency.Gui.RuleQuickFixControl();
            this.SuspendLayout();
            // 
            // quickfixControl
            // 
            this.quickfixControl.AutoSize = true;
            this.quickfixControl.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.quickfixControl.Location = new System.Drawing.Point(0, 0);
            this.quickfixControl.Margin = new System.Windows.Forms.Padding(0);
            this.quickfixControl.Name = "quickfixControl";
            this.quickfixControl.Size = new System.Drawing.Size(459, 92);
            this.quickfixControl.TabIndex = 0;
            this.quickfixControl.Load += new System.EventHandler(this.quickfixControl_Load);
            // 
            // RuleQuickfixDialogue
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(459, 92);
            this.Controls.Add(this.quickfixControl);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "RuleQuickfixDialogue";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Show;
            this.Text = "QuickFix";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private RuleQuickFixControl quickfixControl;

    }
}
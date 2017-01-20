namespace EAEcoreAddin.ControlPanel
{
    partial class TCPServerUserControl
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
            this.labelPortDescr = new System.Windows.Forms.Label();
            this.textBoxPort = new System.Windows.Forms.TextBox();
            this.listBoxStatus = new System.Windows.Forms.ListBox();
            this.label1 = new System.Windows.Forms.Label();
            this.buttonClearStatusMessages = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // labelPortDescr
            // 
            this.labelPortDescr.AutoSize = true;
            this.labelPortDescr.Location = new System.Drawing.Point(109, 6);
            this.labelPortDescr.Name = "labelPortDescr";
            this.labelPortDescr.Size = new System.Drawing.Size(63, 13);
            this.labelPortDescr.TabIndex = 0;
            this.labelPortDescr.Text = "Default Port";
            // 
            // textBoxPort
            // 
            this.textBoxPort.Enabled = false;
            this.textBoxPort.Location = new System.Drawing.Point(3, 3);
            this.textBoxPort.Name = "textBoxPort";
            this.textBoxPort.Size = new System.Drawing.Size(100, 20);
            this.textBoxPort.TabIndex = 1;
            // 
            // listBoxStatus
            // 
            this.listBoxStatus.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.listBoxStatus.FormattingEnabled = true;
            this.listBoxStatus.HorizontalScrollbar = true;
            this.listBoxStatus.Location = new System.Drawing.Point(6, 61);
            this.listBoxStatus.Name = "listBoxStatus";
            this.listBoxStatus.Size = new System.Drawing.Size(257, 121);
            this.listBoxStatus.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 43);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(119, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "Status of received Data";
            // 
            // buttonClearStatusMessages
            // 
            this.buttonClearStatusMessages.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonClearStatusMessages.Location = new System.Drawing.Point(269, 61);
            this.buttonClearStatusMessages.Name = "buttonClearStatusMessages";
            this.buttonClearStatusMessages.Size = new System.Drawing.Size(75, 23);
            this.buttonClearStatusMessages.TabIndex = 4;
            this.buttonClearStatusMessages.Text = "Clear";
            this.buttonClearStatusMessages.UseVisualStyleBackColor = true;
            this.buttonClearStatusMessages.Click += new System.EventHandler(this.buttonClearStatusMessages_Click);
            // 
            // TCPServerUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.buttonClearStatusMessages);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.listBoxStatus);
            this.Controls.Add(this.textBoxPort);
            this.Controls.Add(this.labelPortDescr);
            this.Name = "TCPServerUserControl";
            this.Size = new System.Drawing.Size(507, 256);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelPortDescr;
        private System.Windows.Forms.TextBox textBoxPort;
        private System.Windows.Forms.ListBox listBoxStatus;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button buttonClearStatusMessages;
    }
}

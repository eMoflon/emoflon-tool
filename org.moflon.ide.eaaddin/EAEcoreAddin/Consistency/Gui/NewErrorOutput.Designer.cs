namespace EAEcoreAddin.Consistency.RuleHandling
{
    partial class NewErrorOutput
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
            this.labelSummary = new System.Windows.Forms.Label();
            this.buttonDeleteResults = new System.Windows.Forms.Button();
            this.consistencyDataGridView1 = new EAEcoreAddin.Consistency.RuleHandling.ConsistencyDataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.consistencyDataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // labelSummary
            // 
            this.labelSummary.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.labelSummary.AutoSize = true;
            this.labelSummary.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelSummary.Location = new System.Drawing.Point(3, 326);
            this.labelSummary.Name = "labelSummary";
            this.labelSummary.Size = new System.Drawing.Size(86, 13);
            this.labelSummary.TabIndex = 2;
            this.labelSummary.Text = "summaryLabel";
            // 
            // buttonDeleteResults
            // 
            this.buttonDeleteResults.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.buttonDeleteResults.Location = new System.Drawing.Point(3, 342);
            this.buttonDeleteResults.Name = "buttonDeleteResults";
            this.buttonDeleteResults.Size = new System.Drawing.Size(92, 24);
            this.buttonDeleteResults.TabIndex = 3;
            this.buttonDeleteResults.Text = "Delete Results";
            this.buttonDeleteResults.UseVisualStyleBackColor = true;
            this.buttonDeleteResults.Click += new System.EventHandler(this.buttonDeleteResults_Click);
            // 
            // consistencyDataGridView1
            // 
            this.consistencyDataGridView1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.consistencyDataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.consistencyDataGridView1.Location = new System.Drawing.Point(3, 0);
            this.consistencyDataGridView1.Name = "consistencyDataGridView1";
            this.consistencyDataGridView1.Size = new System.Drawing.Size(661, 323);
            this.consistencyDataGridView1.TabIndex = 4;
            // 
            // NewErrorOutput
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.consistencyDataGridView1);
            this.Controls.Add(this.buttonDeleteResults);
            this.Controls.Add(this.labelSummary);
            this.Name = "NewErrorOutput";
            this.Size = new System.Drawing.Size(667, 369);
            ((System.ComponentModel.ISupportInitialize)(this.consistencyDataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelSummary;
        private System.Windows.Forms.Button buttonDeleteResults;
        private ConsistencyDataGridView consistencyDataGridView1;
    }
}

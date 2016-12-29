using EAEcoreAddin.Modeling.SDMModeling.Util;
namespace EAEcoreAddin.Modeling.SDMModeling.Gui
{
    partial class ExpressionControl
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
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.cmbExpressions = new System.Windows.Forms.ComboBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.buttonToMethodCallExpression = new System.Windows.Forms.Button();
            this.grpSecond = new System.Windows.Forms.GroupBox();
            this.cmbSecondObjects = new System.Windows.Forms.ComboBox();
            this.grpFirst = new System.Windows.Forms.GroupBox();
            this.cmbFirstObjects = new System.Windows.Forms.ComboBox();
            this.grpCustomDataGridView = new System.Windows.Forms.GroupBox();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.groupBox3.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.grpSecond.SuspendLayout();
            this.grpFirst.SuspendLayout();
            this.grpCustomDataGridView.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox3
            // 
            this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox3.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.groupBox3.Controls.Add(this.cmbExpressions);
            this.groupBox3.Location = new System.Drawing.Point(3, 3);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(168, 44);
            this.groupBox3.TabIndex = 0;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Expression";
            // 
            // cmbExpressions
            // 
            this.cmbExpressions.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.cmbExpressions.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbExpressions.FormattingEnabled = true;
            this.cmbExpressions.Location = new System.Drawing.Point(6, 16);
            this.cmbExpressions.Name = "cmbExpressions";
            this.cmbExpressions.Size = new System.Drawing.Size(156, 21);
            this.cmbExpressions.TabIndex = 4;
            this.cmbExpressions.SelectedIndexChanged += new System.EventHandler(this.cmbExpressions_SelectedIndexChanged);
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.buttonToMethodCallExpression);
            this.groupBox1.Location = new System.Drawing.Point(177, 3);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(64, 44);
            this.groupBox1.TabIndex = 500;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Jump";
            // 
            // buttonToMethodCallExpression
            // 
            this.buttonToMethodCallExpression.Location = new System.Drawing.Point(3, 16);
            this.buttonToMethodCallExpression.Name = "buttonToMethodCallExpression";
            this.buttonToMethodCallExpression.Size = new System.Drawing.Size(58, 21);
            this.buttonToMethodCallExpression.TabIndex = 5;
            this.buttonToMethodCallExpression.Text = "to SDM";
            this.buttonToMethodCallExpression.UseVisualStyleBackColor = true;
            this.buttonToMethodCallExpression.Click += new System.EventHandler(this.buttonToMethodCallExpression_Click);
            // 
            // grpSecond
            // 
            this.grpSecond.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.grpSecond.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.grpSecond.Controls.Add(this.cmbSecondObjects);
            this.grpSecond.Location = new System.Drawing.Point(3, 103);
            this.grpSecond.Name = "grpSecond";
            this.grpSecond.Size = new System.Drawing.Size(238, 44);
            this.grpSecond.TabIndex = 1;
            this.grpSecond.TabStop = false;
            this.grpSecond.Text = "Second";
            // 
            // cmbSecondObjects
            // 
            this.cmbSecondObjects.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.cmbSecondObjects.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbSecondObjects.FormattingEnabled = true;
            this.cmbSecondObjects.Location = new System.Drawing.Point(6, 16);
            this.cmbSecondObjects.Name = "cmbSecondObjects";
            this.cmbSecondObjects.Size = new System.Drawing.Size(226, 21);
            this.cmbSecondObjects.TabIndex = 7;
            this.cmbSecondObjects.SelectedIndexChanged += new System.EventHandler(this.cmbSecondObjects_SelectedIndexChanged);
            // 
            // grpFirst
            // 
            this.grpFirst.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.grpFirst.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.grpFirst.Controls.Add(this.cmbFirstObjects);
            this.grpFirst.Location = new System.Drawing.Point(3, 53);
            this.grpFirst.Name = "grpFirst";
            this.grpFirst.Size = new System.Drawing.Size(238, 44);
            this.grpFirst.TabIndex = 0;
            this.grpFirst.TabStop = false;
            this.grpFirst.Text = "First";
            // 
            // cmbFirstObjects
            // 
            this.cmbFirstObjects.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.cmbFirstObjects.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbFirstObjects.FormattingEnabled = true;
            this.cmbFirstObjects.Location = new System.Drawing.Point(6, 16);
            this.cmbFirstObjects.Name = "cmbFirstObjects";
            this.cmbFirstObjects.Size = new System.Drawing.Size(226, 21);
            this.cmbFirstObjects.TabIndex = 6;
            this.cmbFirstObjects.SelectedIndexChanged += new System.EventHandler(this.cmbFirstObjects_SelectedIndexChanged);
            // 
            // grpCustomDataGridView
            // 
            this.grpCustomDataGridView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.grpCustomDataGridView.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.grpCustomDataGridView.Controls.Add(this.treeView1);
            this.grpCustomDataGridView.Location = new System.Drawing.Point(3, 153);
            this.grpCustomDataGridView.Name = "grpCustomDataGridView";
            this.grpCustomDataGridView.Size = new System.Drawing.Size(238, 143);
            this.grpCustomDataGridView.TabIndex = 2;
            this.grpCustomDataGridView.TabStop = false;
            // 
            // treeView1
            // 
            this.treeView1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.treeView1.Location = new System.Drawing.Point(6, 19);
            this.treeView1.Name = "treeView1";
            this.treeView1.Size = new System.Drawing.Size(226, 118);
            this.treeView1.TabIndex = 3;
            this.treeView1.BeforeCollapse += new System.Windows.Forms.TreeViewCancelEventHandler(this.treeView1_BeforeCollapse);
            this.treeView1.NodeMouseDoubleClick += new System.Windows.Forms.TreeNodeMouseClickEventHandler(this.treeView1_NodeMouseDoubleClick);
            // 
            // ExpressionControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.grpFirst);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.grpSecond);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.grpCustomDataGridView);
            this.Name = "ExpressionControl";
            this.Size = new System.Drawing.Size(249, 299);
            this.groupBox3.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.grpSecond.ResumeLayout(false);
            this.grpFirst.ResumeLayout(false);
            this.grpCustomDataGridView.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox grpCustomDataGridView;
        private System.Windows.Forms.GroupBox grpFirst;
        private System.Windows.Forms.ComboBox cmbFirstObjects;
        private System.Windows.Forms.GroupBox grpSecond;
        private System.Windows.Forms.ComboBox cmbSecondObjects;
        private System.Windows.Forms.GroupBox groupBox3;
        public System.Windows.Forms.ComboBox cmbExpressions;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button buttonToMethodCallExpression;
        private System.Windows.Forms.TreeView treeView1;
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Common
{
    public partial class ExpressionReturnForm : Form
    {
        IExpressionProvider provider;
        Expression underlyingExpression;
        SQLRepository repository;

        public ExpressionReturnForm(IExpressionProvider provider, Expression underlyingExpression, SQLRepository repository)
        {
            InitializeComponent();

            this.underlyingExpression = underlyingExpression;
            this.provider = provider;
            this.repository = repository;
            StartPosition = FormStartPosition.CenterScreen;

            initialize();

            
        }

        public void initialize()
        {
            expressionControl1.initializeInformation(provider, underlyingExpression, repository);
            expressionControl1.cmbExpressions.Select();
        }

        public Expression getUnderlyingExpression()
        {
            return provider.getExpression();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }
      
    }
}

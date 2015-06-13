using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Common
{
    public partial class LiteralExpressionForm : Form
    {
        AutoCompleteStringCollection source = new AutoCompleteStringCollection();
        private SQLWrapperClasses.SQLRepository repository;

        public LiteralExpressionForm(string textFieldContent, SQLWrapperClasses.SQLRepository repository)
        {
            InitializeComponent();
            StartPosition = FormStartPosition.CenterScreen;
            literalExpressionTextBox.Select();
            this.repository = repository;
            this.literalExpressionTextBox.Text = textFieldContent;
            this.literalExpressionTextBox.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.CustomSource;
            this.literalExpressionTextBox.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.SuggestAppend;
            this.literalExpressionTextBox.AutoCompleteCustomSource = this.source;
        }

        private void LiteralExpressionForm_Load(object sender, EventArgs e)
        {
            source.AddRange(new string[]
                    {
                        "true", "false","null"
                    });

            // Add all known enum constants
            IEnumerable<EA.Element> enums = EAUtil.findAllEEnums(repository.GetOriginalRepository());
            foreach (EA.Element ruleElement in enums)
            {
                foreach (EA.Attribute attribute in ruleElement.Attributes)
                {
                    if (isEnumConstant(attribute))
                    {
                        source.AddRange(new string[] { ruleElement.Name + "." + attribute.Name });
                    }
                }
            }
        }

        private static bool isEnumConstant(EA.Attribute attribute)
        {
            return ConsistencyUtil.isValidConstantName(attribute.Name);
        }

        public string getText()
        {
            return literalExpressionTextBox.Text;
        }
    }
}

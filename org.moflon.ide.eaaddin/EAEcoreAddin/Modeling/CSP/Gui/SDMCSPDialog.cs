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

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    public partial class SDMCSPDialog : Form
    {
        private EA.Element elem;
        private SQLRepository sqlRepository;
        private const string DEFAULT_CSP_CONTENT = "importPackage Ecore;";


        public SDMCSPDialog(SQLElement elem, SQLRepository sqlRepository)
        {
            this.InitializeComponent();
            this.elem = EAUtil.sqlEAObjectToOriginalObject(sqlRepository, elem) as EA.Element;
            this.sqlRepository = sqlRepository;

            if (elem.Notes.Trim() == "")
            {
                textBoxCspDefinition.Text = DEFAULT_CSP_CONTENT;
            }
            else
            {
                textBoxCspDefinition.Text = elem.Notes;
            }

            SQLTaggedValue nacIndexTag = EAUtil.findTaggedValue(elem, "nacIndex");
            if (nacIndexTag != null)
            {
                complexNAC1.setNacIndexValue(nacIndexTag.Value);
            }

            StartPosition = FormStartPosition.CenterScreen;
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            String nacIndexReal = complexNAC1.getNacIndexValue();
            
            EAUtil.setTaggedValue(sqlRepository, elem, "nacIndex", nacIndexReal != "" ? nacIndexReal : "-1" );
            elem.Notes = textBoxCspDefinition.Text;
            elem.Update();
            Close();

        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void textBoxCspDefinition_TextChanged(object sender, EventArgs e)
        {

        }

        private void showHelpButton_Click(object sender, EventArgs e)
        {
            new SDMCSPQuickReferenceDialog().Show();
        }


    }
}

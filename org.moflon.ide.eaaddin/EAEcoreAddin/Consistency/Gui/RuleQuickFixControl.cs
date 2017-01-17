using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency.Gui
{
    public partial class RuleQuickFixControl : UserControl
    {
        List<RadioButton> entryList;
        Rule rule;
        RuleResult ruleResult;
        Object eaObject;
        SQLRepository repository;

        private static readonly String NotAvailable = "There is no quickfix available";

        public RuleQuickFixControl()
        {
            InitializeComponent();
        }

        public void initializeControl(RuleResult ruleResult,SQLRepository repository)
        {
            this.entryList = new List<RadioButton>();
            this.rule = ruleResult.Rule;
            this.ruleResult = ruleResult;
            this.repository = repository;
            this.eaObject = ruleResult.EaObject;
            setTitle(ruleResult.getFullErrorMessage());

            try {

                foreach (String entry in rule.getQuickFixMessages(ruleResult.ErrorOutput))
                {
                    addEntry(entry);
                }
            }
            catch(Exception)
            {
            }
            if(entryList.Count == 0)
            {   addEntry(NotAvailable);              
            }

            if (entryList.Count > 0)
            {
                this.entryList[0].Checked = true;
            }
        }

        public void addEntry(String entryName)
        {
            RadioButton newEntry = new RadioButton();
            newEntry.Text = entryName;
            newEntry.AutoSize = true;
            newEntry.Name = "entry" + entryList.Count + 1;
            newEntry.Location = new Point(3, groupBoxRadio.Controls.Count * 26 + 18); 


            this.entryList.Add(newEntry);

            this.groupBoxRadio.Controls.Add(newEntry);
        }

        public void setTitle(String ruleTitle)
        {
            String splitTitle = "";
            int i = 0;
            Char[] title = ruleTitle.ToCharArray();

            foreach(Char actChar in title)
            {
                if (i > 50 && Char.IsWhiteSpace(actChar))
                {
                    i = 0;
                    splitTitle += "\n";
                } 
                splitTitle += actChar;
                i++;
            }


            this.labelMessage.Text = splitTitle;
        }

        private void OKButton_Click(object sender, EventArgs e)
        {
            
            int i = 0;
            foreach (RadioButton rb in entryList)
            {
                if (rb.Checked && rb.Text != NotAvailable)
                {
                    rule.doRuleQuickFix(eaObject, repository, i, ruleResult.ErrorOutput);
                }
                i++;
            }
            this.ParentForm.Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void bttOk_Click(object sender, EventArgs e)
        {

        }
    }
}

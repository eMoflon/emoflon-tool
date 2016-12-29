using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    public partial class NewCSPDefinitionDialog : Form
    {
        public CSPConstraint CreatedCSPConstraint { get; set; }

        public NewCSPDefinitionDialog() : this(new CSPConstraint())
        {
            
        }
        public NewCSPDefinitionDialog(CSPConstraint existingConstraint)
        {
            InitializeComponent();
            this.CreatedCSPConstraint = existingConstraint;
            this.StartPosition = FormStartPosition.CenterScreen;
            this.parameterDataGridView.initializeComponent("parameter");
            this.adornmentDataGridView.initializeComponent("adornment");
            this.modelgenAdornmentDataGridView.initializeComponent("adornment");

            initializeDialog(existingConstraint);
        }

        private void initializeDialog(CSPConstraint existingConstraint)
        {

            String[] splitted = existingConstraint.getConstraintInformationSummary().Split("\r\n".ToCharArray());           
            this.textBoxInformation.Text = splitted[splitted.Length - 1];
            
            this.textBoxName.Text = existingConstraint.Name;

            foreach (String adornment in existingConstraint.adornments)
            {
                adornmentDataGridView.addValue(adornment);
            }

            foreach (String modelgenAd in existingConstraint.modelgenAdornments)
            {
                modelgenAdornmentDataGridView.addValue(modelgenAd);
            }


            for (int i = 0; i < existingConstraint.Signature.Count; i++)
            {
                parameterDataGridView.addValue(new String[] { existingConstraint.Signature[i], existingConstraint.SignatureInformation[i] });
            }
        }

        public Boolean computeNewCSPConstraint()
        {

                CreatedCSPConstraint.Name = textBoxName.Text;
                CreatedCSPConstraint.adornments = adornmentDataGridView.getTypedInAddornments();
                CreatedCSPConstraint.modelgenAdornments = modelgenAdornmentDataGridView.getTypedInAddornments();
                CreatedCSPConstraint.Signature = parameterDataGridView.getTypedInParameterTypes();
                CreatedCSPConstraint.setConstraintInformation(this.textBoxInformation.Text);
                CreatedCSPConstraint.SignatureInformation = this.parameterDataGridView.getTypedInParameterInformation();
                CreatedCSPConstraint.UserDefined = true;
                return true;
         
        }

        public Boolean typedValuesAreValid()
        {
            Boolean valid = true;
            valid &= textBoxName.Text != "";
            valid &= parameterDataGridView.getTypedInParameterTypes().Count > 0;
            valid &= adornmentDataGridView.getTypedInAddornments().Count > 0;

            foreach(String adornment in adornmentDataGridView.getTypedInAddornments()) {
                valid &= adornment.Length == parameterDataGridView.getTypedInParameterTypes().Count;
                foreach (Char c in adornment.ToCharArray())
                {
                    valid &= (c == 'F' || c == 'B');
                }
            }

            foreach (String adornment in modelgenAdornmentDataGridView.getTypedInAddornments())
            {
                valid &= adornment.Length == parameterDataGridView.getTypedInParameterTypes().Count;
                foreach (Char c in adornment.ToCharArray())
                {
                    valid &= (c == 'F' || c == 'B');
                }
            }



            return valid;
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            if (typedValuesAreValid())
            {
                computeNewCSPConstraint();
                this.DialogResult = DialogResult.OK;
            }
            else
            {
                this.DialogResult = DialogResult.None;
                MessageBox.Show("Typed in Values are not valid. Allowed adornment values are F or B. The length of the adornment strings must be equal to the number of parameters");
            }
        }
       

    }
}

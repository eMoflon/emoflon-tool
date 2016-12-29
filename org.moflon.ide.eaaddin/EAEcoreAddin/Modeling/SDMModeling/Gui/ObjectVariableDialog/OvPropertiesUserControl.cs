using System;
using System.Linq;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using System.Collections.Specialized;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using NAMESPACE;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;

namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    public partial class OvPropertiesUserControl : UserControl
    {

        ObjectVariable objectVariable;
        SQLRepository repository;
        private IOVDialog ovDialog;

        /// <summary>
        /// Initialization of the Object Dialogue
        /// </summary>
        /// <param name="repository"></param>
        /// <param name="sdmObject"></param>
        /// <param name="editExistingLink"></param>
        public OvPropertiesUserControl()
        {
            InitializeComponent();
            complexNACControl.Enabled = false;
        }

        public void initializeUserControl(SQLRepository repository, ObjectVariable objectVariable, IOVDialog ovDialog)
        {

            //Set class variables
            this.repository = repository;
            this.objectVariable = objectVariable;
            this.ovDialog = ovDialog;

            this.comboNames.ComboTypes = this.comboTypes;
            this.comboNames.CbxBound = this.checkBoxBound;

            this.comboTypes.initializePossibleClassifiersComboBox(objectVariable.sqlElement.Stereotype, objectVariable.sqlElement.ClassifierID, repository);
            this.comboNames.initializePossibleNamesComboBox(objectVariable, repository);

            setAppearance();

            this.comboNames.Select();
        }

        private void setAppearance()
        {
            if (objectVariable.BindingOperator == BindingOperator.CHECK_ONLY)
                radioButtonCheckonly.Checked = true;
            else if (objectVariable.BindingOperator == BindingOperator.CREATE)
                radioButtonCreate.Checked = true;
            else if (objectVariable.BindingOperator == BindingOperator.DESTROY)
                radioButtonDestroy.Checked = true;

            if (objectVariable.BindingSemantics == BindingSemantics.MANDATORY)
                radioButtonMandatory.Checked = true;
            else if (objectVariable.BindingSemantics == BindingSemantics.NEGATIVE)
                radioButtonNegative.Checked = true;
            if (objectVariable.BindingState == BindingState.BOUND)
                checkBoxBound.Checked = true;
            else
            {
                checkBoxBound.Checked = false;
                (this.Parent.Parent as TabControl).TabPages[2].Controls[0].Enabled = false;
            }
                
            if (objectVariable.NacIndex != -1)
            {
                complexNACControl.setNacIndexValue(objectVariable.NacIndex.ToString());
            }
        }


        /// <summary>
        /// pressing the OK button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public Boolean btnOK_Click()
        {

            if (comboTypes.Text != "" && comboTypes.SelectedIndex != -1)
            {
                BindingSemantics bindingSemantics = BindingSemantics.MANDATORY;
                BindingOperator bindingOperator = BindingOperator.CHECK_ONLY;
                BindingState bindingState = BindingState.UNBOUND;

                if (radioButtonMandatory.Checked)
                    bindingSemantics = BindingSemantics.MANDATORY;
                else if (radioButtonNegative.Checked)
                    bindingSemantics = BindingSemantics.NEGATIVE;

                if (radioButtonCreate.Checked)
                    bindingOperator = BindingOperator.CREATE;
                else if (radioButtonCheckonly.Checked)
                    bindingOperator = BindingOperator.CHECK_ONLY;
                else if (radioButtonDestroy.Checked)
                    bindingOperator = BindingOperator.DESTROY;

                if (!checkBoxBound.Checked)
                    bindingState = BindingState.UNBOUND;
                if (checkBoxBound.Checked)
                    bindingState = BindingState.BOUND;


                objectVariable.Name = comboNames.Text;
                objectVariable.Classifier = comboTypes.Classifiers[comboTypes.SelectedIndex];
                objectVariable.BindingOperator = bindingOperator;
                objectVariable.BindingSemantics = bindingSemantics;
                objectVariable.BindingState = bindingState;
                if (complexNACControl.getNacIndexValue() != "" && radioButtonNegative.Checked)
                    objectVariable.NacIndex = int.Parse(complexNACControl.getNacIndexValue());
                else
                    objectVariable.NacIndex = -1;
                return true;
            }
            return false;
        }

        private void cbxBound_CheckedChanged(object sender, EventArgs e)
        {
            if(this.comboTypes.userChanged)
            {
                if (checkBoxBound.Checked)
                {
                    this.comboTypes.userSelectedBound = BindingState.BOUND;
                }
                else if (!checkBoxBound.Checked)
                {
                    this.comboTypes.userSelectedBound = BindingState.UNBOUND;
                }
            }


            if (checkBoxBound.Checked)
            {
                (this.Parent.Parent as TabControl).TabPages[2].Controls[0].Enabled = true;
            }
            else if (!checkBoxBound.Checked)
            {
                (this.Parent.Parent as TabControl).TabPages[2].Controls[0].Enabled = false;
            }
        }

        
        private void setRightClassifier()
        {
            int i = 0;
            foreach (String value in this.comboTypes.Items)
            {
                if(value.ToLower().StartsWith(this.labelTypedIn.Text.ToLower()))
                {
                    this.comboTypes.SelectedIndex = i;
                    break;
                }
                i++;
            }
        }


        private void comboTypes_KeyUp(object sender, KeyEventArgs e)
        {
            String typedString = ((Char)(e.KeyValue)).ToString().ToLower();
            if (typedString == "\b")
            {
                if (this.labelTypedIn.Text.Length > 0)
                {
                    this.labelTypedIn.Text = this.labelTypedIn.Text.Substring(0, this.labelTypedIn.Text.Length - 1);
                }
            }
            else
            {
                this.labelTypedIn.Text += typedString;
            }
            setRightClassifier();
        
        }

        private void tableLayoutPanel3_Paint(object sender, PaintEventArgs e)
        {

        }

        private void OvPropertiesUserControl_Load(object sender, EventArgs e)
        {

        }

        private void radioButtonNegative_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButtonNegative.Checked)
            {
                complexNACControl.Enabled = true;
            }
            else
            {
                complexNACControl.Enabled = false;
            }
        }

        private void comboTypes_SelectedIndexChanged(object sender, EventArgs e)
        {
            objectVariable.Classifier = comboTypes.Classifiers[comboTypes.SelectedIndex];
            this.ovDialog.initializeConstraintTab();


        }

        

       
    }
}
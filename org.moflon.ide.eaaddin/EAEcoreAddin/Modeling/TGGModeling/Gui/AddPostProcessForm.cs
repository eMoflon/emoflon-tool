using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.TGGModeling.Gui
{
    public partial class AddPostProcessForm : Form
    {
        SQLRepository repository;
        List<SQLElement> selectedObjects;
        Dictionary<CheckBox, SQLElement> checkBoxToElement;
        Dictionary<SQLElement, SQLElement> elementToClassifier;
        List<String> parameterNames;

        EA.Element tggRuleElement;

        public AddPostProcessForm(SQLRepository repository)
        {
            InitializeComponent();

            this.parameterNames = new List<string>();
            this.selectedObjects = new List<SQLElement>();
            this.checkBoxToElement = new Dictionary<CheckBox, SQLElement>();
            this.elementToClassifier = new Dictionary<SQLElement, SQLElement>();
            this.repository = repository;

            initialSettings();

            EA.Diagram currentDiagram = repository.GetCurrentDiagram();

            if (currentDiagram.ParentID != 0)
            {

                this.tggRuleElement = repository.GetOriginalRepository().GetElementByID(currentDiagram.ParentID);

                foreach (EA.DiagramObject diagObject in currentDiagram.SelectedObjects)
                {
                    SQLElement selectedElement = repository.GetElementByID(diagObject.ElementID);
                    if (selectedElement.MetaType == SDMModelingMain.ObjectVariableStereotype)
                    {
                        this.selectedObjects.Add(selectedElement);
                        SQLElement classifier = EAUtil.getClassifierElement(repository, selectedElement.ClassifierID); 
                        if (classifier != null)
                        {  
                            this.elementToClassifier.Add(selectedElement, classifier);
                            addEntry(classifier, selectedElement);
                        }
                    }
                }

                updateThisSize();

                this.ShowDialog();
            }
        }

        int rowIndex = 0;

        private void initialSettings()
        {
            this.StartPosition = FormStartPosition.CenterScreen;
            this.tableEntries.RowStyles.Clear();
            this.tableEntries.RowStyles.Add(new RowStyle(SizeType.AutoSize));
            this.rbFWD.Select();
        }

        private void addEntry(SQLElement selectedClassifier, SQLElement selectedObject)
        {
            String type = selectedClassifier.Name;
            String paramName = selectedObject.Name;
            //String paramName = type.Substring(0, 1).ToLower() + type.Substring(1);
            parameterNames.Add(paramName);
            CheckBox newCheckBox = new CheckBox();
            newCheckBox.Text = paramName + ": " + type;
            newCheckBox.Dock = DockStyle.Top;
            newCheckBox.Checked = true;

            this.checkBoxToElement.Add(newCheckBox, selectedClassifier);

            Label newTextBox = new Label();
            newTextBox.Text = "";
            newTextBox.Dock = DockStyle.Top;

            if (this.tableEntries.RowCount <= rowIndex)
            {
                this.tableEntries.RowStyles.Add(new RowStyle(SizeType.AutoSize));
                this.tableEntries.RowCount++;
            }
            this.tableEntries.Controls.Add(newCheckBox, 0, rowIndex);
            this.tableEntries.Controls.Add(newTextBox, 1, rowIndex);
           
            rowIndex++;
            
        }


        private void updateThisSize()
        {
            this.Height = tableMain.Height + 38;
            this.MinimumSize = new Size(this.Width, this.Height);
            this.MaximumSize = new Size(5000, this.Height);
        }

        private void buttonOK_Click(object sender, EventArgs e)
        {
            EA.Method newMethod = null;
            int i = 0;

            foreach (CheckBox cb in this.checkBoxToElement.Keys)
            {
                if (cb.Checked)
                {
                    if (newMethod == null)
                    {
                        String methodName = "postProcess";
                        if (rbBWD.Checked)
                        {
                            methodName += "BACKWARD";    
                        }
                        else if (rbFWD.Checked)
                        {
                            methodName += "FORWARD";
                        }
                        newMethod = this.tggRuleElement.Methods.AddNew(methodName, "void") as EA.Method;
                        newMethod.Update();
                    }

                    SQLElement elementToAdd = this.checkBoxToElement[cb];
                    String type = elementToAdd.Name;

                    EA.Parameter parameter =  newMethod.Parameters.AddNew(this.parameterNames[i], type) as EA.Parameter;
                    parameter.ClassifierID = elementToAdd.ElementID.ToString();
                    parameter.Update();
                    newMethod.Parameters.Refresh();


                }
                i++;
            }
            Close();
        }

    }
}

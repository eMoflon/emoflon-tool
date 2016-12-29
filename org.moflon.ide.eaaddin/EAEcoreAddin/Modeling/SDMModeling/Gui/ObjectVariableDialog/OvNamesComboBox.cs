using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System.Collections;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    public class OvNamesComboBox : ComboBox
    {

        ObjectVariable objectVariable;
        SQLRepository repository;
        ArrayList InternalList { get; set; }
        public OvClassifiersComboBox ComboTypes { get; set; }
        public CheckBox CbxBound { get; set; }

        public OvNamesComboBox()
        {
            this.TextChanged += new EventHandler(this.comboNames_TextChanged);

        }

        public void initializePossibleNamesComboBox(ObjectVariable objectVariable,SQLRepository repository) 
        {
            this.objectVariable = objectVariable;
            this.repository = repository;
            this.InternalList = new ArrayList();
            this.addPossibleNamesToComboBox();

            this.Text = objectVariable.Name;

            this.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.SuggestAppend;

        }

        private void addPossibleNamesToComboBox()
        {
            List<ObjectVariable> listOfOvs = new List<ObjectVariable>();
            
            List<MocaSerializableElement> existingNames = new List<MocaSerializableElement>();

            if (objectVariable is TGGObjectVariable)
            {
                TGGRule tggRule = objectVariable.StoryPattern as TGGRule;
                listOfOvs = SDMUtil.getTGGObjectVariablesInTggRule(tggRule, repository);         
            }
            else if (objectVariable is ObjectVariable)
            {
                Activity activity = objectVariable.StoryPattern.ParentStoryNode.OwningActivity;
                listOfOvs = SDMUtil.getSDMObjectVariablesInActivity(activity, repository);
                try
                {
                    foreach (EParameter eParameter in objectVariable.StoryPattern.ParentStoryNode.OwningActivity.OwningOperation.EParameters)
                    {
                        if(eParameter.EaParameter.ClassifierID != "0" && eParameter.EaParameter.ClassifierID != "")
                            existingNames.Add(eParameter);
                    }
                }
                catch
                {
                }
            }

            foreach (ObjectVariable ov in listOfOvs)
            {
                existingNames.Add(ov);
            }


            existingNames.Sort(new SDMExportWrapperComparator());

            foreach (Object possibleObject in existingNames)
            {
                if (possibleObject is MocaSerializableElement)
                {
                    MocaSerializableElement entry = possibleObject as MocaSerializableElement;
                    if (!this.Items.Contains(entry.Name))
                    {
                        this.Items.Add((possibleObject as MocaSerializableElement).Name);
                        InternalList.Add(possibleObject);
                    }
                }
            }

            /*

            ObjectVariable ovToDelete = null;
            foreach (ObjectVariable ov in listOfOvs)
            {
                if (ov.Name == this.objectVariable.ObjectVariableEA.Name)
                    ovToDelete = ov;
            }
            listOfOvs.Remove(ovToDelete);
            InternalList.AddRange(listOfOvs);
            InternalList.AddRange(listOfParameters);

            foreach (Object possibleObject in InternalList)
            {
                if (possibleObject is ObjectVariable)
                {
                    this.Items.Add((possibleObject as ObjectVariable).Name);
                }
                else if (possibleObject is EParameter)
                {
                    this.Items.Add((possibleObject as EParameter).Name);
                }
            }
            */

        }

        string oldName = "";

        public void comboNames_TextChanged(object sender, EventArgs e)
        {
            
            if (!OvDialog.DialogClosed && oldName != this.Text)
            {
                this.objectVariable.Name = this.Text;

                int selectedIndex = -1;
                int z = 0;
                foreach (String actEntry in this.Items)
                {
                    if (actEntry == this.Text)
                        selectedIndex = z;
                    z++;
                }
                if (selectedIndex != -1)
                {
                    Object selectedObject = InternalList[selectedIndex];
                    int selectedClassifierID = -1;
                    if (selectedObject is ObjectVariable)
                    {
                        selectedClassifierID = (selectedObject as ObjectVariable).sqlElement.ClassifierID;
                    }
                    else if (selectedObject is EParameter)
                    {
                        selectedClassifierID = int.Parse((selectedObject as EParameter).EaParameter.ClassifierID);
                    }
                    for (int i = 0; i < this.ComboTypes.Classifiers.Count; i++)
                    {
                        EClass classifier = this.ComboTypes.Classifiers[i];
                        if (classifier.EaElement.ElementID == selectedClassifierID)
                        {
                            this.ComboTypes.userChanged = false;
                            this.CbxBound.Checked = true;
                            this.ComboTypes.SelectedIndex = i;
                            this.ComboTypes.userChanged = true;
                            break;
                        }
                    }

                }

                else if (selectedIndex == -1 && this.Text != "this")
                {
                    this.ComboTypes.userChanged = false;
                    this.ComboTypes.SelectedIndex = this.ComboTypes.userSelectedIndex;
                    this.CbxBound.Checked = this.ComboTypes.userSelectedBound == BindingState.BOUND;
                    this.ComboTypes.userChanged = true;
                }

                else if (selectedIndex == -1 && this.Text == "this")
                {
                    String sqlString = "select d.Object_ID from t_object a, t_object b, t_object c, t_object d " +
                                       "where a.Object_ID = " + this.objectVariable.sqlElement.ElementID +
                                       " and a.ParentID = b.Object_ID and b.ParentID = c.Object_ID and c.ParentID = d.Object_ID " +
                                       "and ( d.Stereotype = '" + ECOREModelingMain.EClassStereotype + "' OR d.Stereotype = '" + TGGModelingMain.TggRuleStereotype + "')";
                    String sdmOvQuery = repository.SQLQuery(sqlString);
                    int classifierID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sdmOvQuery, "Object_ID")[0]);
                    int classifierIndex = -1;

                    int i = 0;
                    foreach (EClass classifier in this.ComboTypes.Classifiers)
                    {
                        if (classifier.EaElement.ElementID == classifierID)
                            classifierIndex = i;
                        i++;
                    }


                    if (classifierIndex != -1)
                    {
                        this.ComboTypes.userChanged = false;
                        this.ComboTypes.SelectedIndex = classifierIndex;
                        this.CbxBound.Checked = true;
                        this.ComboTypes.userChanged = true;
                    }
                }

                oldName = this.Text;
            }
        }

        


    }
}

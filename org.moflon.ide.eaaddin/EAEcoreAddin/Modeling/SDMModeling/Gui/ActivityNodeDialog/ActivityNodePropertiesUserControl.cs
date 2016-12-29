using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ActivityNodeDialog;
using EAEcoreAddin.ControlPanel.Navigation;

namespace EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog
{
    public partial class ActivityNodePropertiesUserControl : UserControl
    {

        ActivityNodeTabsForm activityTabsForm;
        SQLRepository repository;
        String evacuated;
        EA.Diagram currentSdmDiagram;

        public ActivityNodePropertiesUserControl()
        {
            InitializeComponent();
        }

        public void initializeUserControl(SQLRepository repository, ActivityNodeTabsForm activityTabsForm)
        {
            
            this.repository = repository;
            this.activityTabsForm = activityTabsForm;
            currentSdmDiagram = repository.GetCurrentDiagram();
            setDefaultAppearance();
            txtName.Select();
        }

        private void setDefaultAppearance()
        {
            txtName.Text = activityTabsForm.ActivityNode.Name;

            comboTypes.comboBoxFromActivityNodeType(activityTabsForm.ActivityNode);

            SQLTaggedValue extractedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(activityTabsForm.ActivityNode.ActivityNodeEAElement, Main.MoflonExtractedStoryPatternTaggedValueName);
            if (extractedTag == null)
            {
                buttonEvacuate.Text = "Extract Story Pattern";
                evacuated = Main.FalseValue;
            }
            else
            {
                evacuated = extractedTag.Value;

                if (evacuated == Main.FalseValue)
                {
                    buttonEvacuate.Text = "Extract Story Pattern";
                }
                if (evacuated == Main.TrueValue)
                {
                    buttonEvacuate.Text = "Embed Story Pattern";
                }
            }
            
        }

       
        
        /// <summary>
        /// Executes if the user clicks the OK button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void bttOK_Click(object sender, EventArgs e)
        {

            String activityType = comboTypes.ActivityNodeTypeFromComboBox();
     
            if (activityType != "call")
            {
                StoryNode newStoryNode = new StoryNode(repository, activityTabsForm.ActivityNode.ActivityNodeEAElement);
                this.activityTabsForm.ActivityNode = newStoryNode;
                if (activityType == "foreach")
                    newStoryNode.ForEach = true;
                else if(activityType == "activity")
                    newStoryNode.ForEach = false;

                //creates a new this object if the radio button is selected
                if (chkThis.Checked)
                {
                    createThisObject();    
                }
            }
            this.activityTabsForm.ActivityNode.Name = this.txtName.Text;
        }


        private void createThisObject()
        {
            EA.DiagramObject activityDiagramObject = EAEcoreAddin.Util.EAUtil.findDiagramObject(repository ,activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement(), currentSdmDiagram);
            SQLElement sdmContainer = repository.GetElementByID(activityTabsForm.ActivityNode.ActivityNodeEAElement.ParentID);
            SQLElement containingEClass = repository.GetElementByID(sdmContainer.ParentID);

            String b = "";
            String r = "";
            String l = "";
            String t = "";
            
            //compute the coordinates of the new diagramObject according to the StoryNode
            if (evacuated == Main.FalseValue)
            {
                b = "" + (activityDiagramObject.bottom + 20);
                r = "" + (activityDiagramObject.left + 110);
                l = "" + (activityDiagramObject.left + 20);
                t = "" + (activityDiagramObject.bottom + 70);
            }
            //compute the coordinates of the new diagramObject with static values
            else if (evacuated == Main.TrueValue)
            {
                b = "" + 60;
                r = "" + 100;
                l = "" + 10;
                t = "" + 10;
            }
            //create a new EA.Element which represents our new this ObjectVariable
            EA.Element obj = (EA.Element)activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement().Elements.AddNew("this", Main.EAObjectType);
            obj.Name = "this";
            obj.ParentID = activityTabsForm.ActivityNode.ActivityNodeEAElement.ElementID;
            obj.ClassifierID = containingEClass.ElementID;     
            obj.Update();

            //set the default values for the xml representation of the objectVariable
            ObjectVariable objectVariable = new ObjectVariable(repository.GetElementByID(obj.ElementID), repository);
            objectVariable.BindingState = BindingState.BOUND;
            objectVariable.BindingSemantics = BindingSemantics.MANDATORY;
            objectVariable.BindingOperator = BindingOperator.CHECK_ONLY;
            objectVariable.saveTreeToEATaggedValue(true);


            //create new diagramObject with the computed coordinates
            EA.DiagramObject diaObj = (EA.DiagramObject)currentSdmDiagram.DiagramObjects.AddNew("l=" + l + ";r=" + r + ";t=" + t + ";b=" + b, Main.EAObjectType);
            diaObj.ElementID = obj.ElementID;
            diaObj.Update();

            //this is required since EA 10.
            if (activityDiagramObject != null)
            {
                activityDiagramObject.Sequence = int.MaxValue;
                activityDiagramObject.Update();
            }
            repository.ReloadDiagram(currentSdmDiagram.DiagramID);
        }

        
        /// <summary>
        /// controls the radio button and the text field of the form
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>#
        int lastSelectedIndex = -1;
        private void comboTypes_TextChanged(object sender, EventArgs e)
        {
            
            if (lastSelectedIndex != comboTypes.SelectedIndex)
            {
                if (comboTypes.Text == "StoryNode" || comboTypes.Text == "ForEach")
                {
                    chkThis.Enabled = true;
                    this.buttonEvacuate.Enabled = true;
                    this.activityTabsForm.disableStatementTab();
                }

                else if (comboTypes.Text == "StatementNode")
                {
                    this.activityTabsForm.ActivityNode = new StatementNode(repository, activityTabsForm.ActivityNode.ActivityNodeEAElement);
                    this.activityTabsForm.ActivityNode.loadTreeFromTaggedValue();
                    if (this.activityTabsForm.provider == null)
                    {
                        this.activityTabsForm.provider = new StatementNodeExpressionProvider(this.activityTabsForm.ActivityNode as StatementNode, repository);
                        this.activityTabsForm.initializeStatementTab();
                    }
                    this.activityTabsForm.enableStatementTab();

                    chkThis.Enabled = false;
                    chkThis.Checked = false;
                    this.buttonEvacuate.Enabled = false;

                }        
                lastSelectedIndex = comboTypes.SelectedIndex;
            }
        }

        private void buttonExtractStoryPattern_Click(object sender, EventArgs e)
        {
            currentSdmDiagram.DiagramObjects.Refresh();
            EA.Element activityNodeElement = activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement();
            activityNodeElement.Name = txtName.Text;
            activityNodeElement.Update();
            //evacuates the diagram elements of the activity to a new diagram under the activity
            if (evacuated == Main.FalseValue)
            {
                extractStoryPattern();
            }
            if (evacuated == Main.TrueValue)
            {
                embedStoryPattern();          
            }
        }

        private void embedStoryPattern()
        {
            EA.Diagram evacuateDiagram = currentSdmDiagram;
            EA.Diagram embedDiagram = null;
            activityTabsForm.ActivityNode.ActivityNodeEAElement.Diagrams.Refresh();
            SQLElement sdmContainer = repository.GetElementByID(activityTabsForm.ActivityNode.ActivityNodeEAElement.ParentID);
            repository.SaveDiagram(currentSdmDiagram.DiagramID);
            EA.DiagramObject activityDiagObj = null;
            foreach (SQLDiagram diagrams in sdmContainer.Diagrams)
            {
                if (activityDiagObj == null)
                {
                    activityDiagObj = EAUtil.findDiagramObject(repository, activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement(), diagrams.getRealDiagram());
                    
                    embedDiagram = EAUtil.sqlEAObjectToOriginalObject(repository, diagrams) as EA.Diagram;
                }
            }
            try
            {
                int i = 30;
                foreach (SQLElement element in activityTabsForm.ActivityNode.ActivityNodeEAElement.Elements)
                {
                    if (element.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                    {
                        EAUtil.deleteDiagramObject(repository.GetOriginalRepository(), evacuateDiagram, element.getRealElement());
                        int l = activityDiagObj.left + i;
                        int r = l + 90;
                        int t = activityDiagObj.top - i;
                        int b = t - 50;
                        EA.DiagramObject elementDiagObj = (EA.DiagramObject)embedDiagram.DiagramObjects.AddNew(

                                "l=" + l +
                                ";r=" + r +
                                ";t=" + t +
                                ";b=" + b, element.Type);

                        i += 60;
                        elementDiagObj.ElementID = element.ElementID;
                        elementDiagObj.Sequence = activityDiagObj.Sequence + 1;
                        elementDiagObj.Update();
                    }
                }

                activityDiagObj.Sequence = int.MaxValue;
                activityDiagObj.Update();

                repository.ReloadDiagram(embedDiagram.DiagramID);

                EAEcoreAddin.Util.EAUtil.setTaggedValue(repository, activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement(), Main.MoflonExtractedStoryPatternTaggedValueName, "false");
                buttonEvacuate.Text = "Extract Story Pattern";
                repository.OpenDiagram(embedDiagram.DiagramID);
                this.ParentForm.Close();
            }
            catch
            {
                System.Windows.Forms.MessageBox.Show("The associated Activity is not on any diagrams");
            }       
        }

        private void extractStoryPattern()
        {
            Dictionary<EA.DiagramObject, EA.Element> diagramObjectsToElement = new Dictionary<EA.DiagramObject, EA.Element>();
            EA.Diagram newDiagram = null;
            //create new diagram if necessary
            if (activityTabsForm.ActivityNode.ActivityNodeEAElement.Diagrams.Count == 0)
            {
                newDiagram = (EA.Diagram)activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement().Diagrams.AddNew(activityTabsForm.ActivityNode.ActivityNodeEAElement.Name, SDMModelingMain.SdmDiagramMetatype[0]);
            }
            //or choose already existing diagram
            else
            {
                newDiagram = ( activityTabsForm.ActivityNode.ActivityNodeEAElement.Diagrams.GetAt(0) as SQLDiagram).getRealDiagram();
                newDiagram.Name = activityTabsForm.ActivityNode.ActivityNodeEAElement.Name;
                newDiagram.Update();
            }
            newDiagram.ParentID = activityTabsForm.ActivityNode.ActivityNodeEAElement.ElementID;
            repository.SaveDiagram(newDiagram.DiagramID);
            newDiagram.Update();

            int leftMin = int.MaxValue;
            int topMax = int.MinValue;

            //compute the minimal left value and the maximal top value of all object variables contained by current StoryNode
            foreach (SQLElement actElement in activityTabsForm.ActivityNode.ActivityNodeEAElement.Elements)
            {
                EA.DiagramObject actDiagObj = EAEcoreAddin.Util.EAUtil.findDiagramObject(repository, actElement.getRealElement(), currentSdmDiagram);
                if (actDiagObj != null)
                {
                    if (leftMin > actDiagObj.left)
                        leftMin = actDiagObj.left;
                    if (topMax < actDiagObj.top)
                        topMax = actDiagObj.top;
                    diagramObjectsToElement.Add(actDiagObj, actElement.getRealElement());
                }                  
            }
            //compute the difference for the new diagram objects so they are pinned on the left top corner
            int differenceLeftRight = leftMin - 40;
            if (differenceLeftRight < 0)
                differenceLeftRight = 40;
            int differenceTopBottom = topMax + 40;
            if (differenceTopBottom > 0)
                differenceTopBottom = -40;

            //delete all old diagramobjects and create new ones on the extracted diagram-
            foreach (EA.DiagramObject oldDiagramObject in diagramObjectsToElement.Keys)
            {
                EA.Element element = diagramObjectsToElement[oldDiagramObject];
                EAUtil.deleteDiagramObject(repository.GetOriginalRepository(), currentSdmDiagram, element);

                EA.DiagramObject newDiaObj = (EA.DiagramObject)newDiagram.DiagramObjects.AddNew("", element.Type);
                newDiaObj.left = oldDiagramObject.left - differenceLeftRight;
                newDiaObj.right = oldDiagramObject.right - differenceLeftRight;
                newDiaObj.top = oldDiagramObject.top - differenceTopBottom;
                newDiaObj.bottom = oldDiagramObject.bottom - differenceTopBottom;
                newDiaObj.ElementID = element.ElementID;
                newDiaObj.Update();
            }

            activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement().Update();
            repository.SaveDiagram(currentSdmDiagram.DiagramID);
            repository.ReloadDiagram(currentSdmDiagram.DiagramID);
            repository.ReloadDiagram(newDiagram.DiagramID);
            buttonEvacuate.Text = "Embed Story Pattern";

            Navigator.addAnchorEntry(newDiagram.DiagramID, currentSdmDiagram.DiagramID);

            repository.OpenDiagram(newDiagram.DiagramID);

            SDMModelingMain.setAnchorElementTags(repository, currentSdmDiagram, activityTabsForm.ActivityNode.ActivityNodeEAElement);

            this.ParentForm.Close();
            EAEcoreAddin.Util.EAUtil.setTaggedValue(repository, activityTabsForm.ActivityNode.ActivityNodeEAElement.getRealElement(), Main.MoflonExtractedStoryPatternTaggedValueName, "true");
            
        }

    }
}

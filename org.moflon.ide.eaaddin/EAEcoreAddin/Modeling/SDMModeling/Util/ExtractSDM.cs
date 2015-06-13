using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Web;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;

namespace EAEcoreAddin.Modeling.SDMModeling.Util
{
    public partial class ExtractSDM : Form
    {
        SQLRepository sqlRepository;
        EA.Repository repository;

        List<EA.DiagramObject> allOldDiagramObjects;
        List<EA.DiagramObject> selectedOldDiagramObjects;


        List<EA.Element> selectedActivityNodes;
        List<int> selectedElementIDs;

        List<EA.Element> allOldOvs;
        List<EA.Element> allNewOvs;


        EA.Element oldSDMContainer;
        EA.Element sdmCallPattern;
        EA.Element thisOvPattern;
        EA.Method newMethod;
        EA.Element newSdmContainer;


        EA.Diagram oldSDMDiagram;


        public ExtractSDM(SQLRepository sqlRepository)
        {


            InitializeComponent();
            this.sqlRepository = sqlRepository;
            repository = sqlRepository.GetOriginalRepository();
            //save selected diagramObjects;
            allOldDiagramObjects = new List<EA.DiagramObject>();
            selectedOldDiagramObjects = new List<EA.DiagramObject>();
            selectedActivityNodes = new List<EA.Element>();
            selectedElementIDs = new List<int>();
            allOldOvs = new List<EA.Element>();
            allNewOvs = new List<EA.Element>();

            oldSDMDiagram = sqlRepository.GetCurrentDiagram();
            oldSDMContainer = repository.GetElementByID(oldSDMDiagram.ParentID);

            gatherAllDiagramObjects();
            gatherSelectedActivityNodes();
            gatherUnselectedObjectVariables();

            ovClassifiersComboBox1.initializePossibleClassifiersComboBox(SDMModelingMain.ObjectVariableStereotype, 0, sqlRepository);
           

            StartPosition = FormStartPosition.CenterScreen;
            textBoxNewSDM.Select();
            ShowDialog();
        }


        private void buttonOK_Click(object sender, EventArgs e)
        {
            if (textBoxNewSDM.Text != "")
            {
                appendNewMethodAndSdm();
                Close();

                if (checkBoxValidateAfter.Checked)
                {
                    Main.consistencyModule.dispatchSingleObject(repository, newSdmContainer.ElementGUID, newSdmContainer.ObjectType, true);
                    Main.consistencyModule.dispatchSingleObject(repository, oldSDMContainer.ElementGUID, oldSDMContainer.ObjectType, true);
                }

             
            }
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }
        
        private void appendNewMethodAndSdm()
        {
            repository.SaveDiagram(oldSDMDiagram.DiagramID);
            

            EA.Element eClass = getContainingClass();
            this.newMethod = eClass.Methods.AddNew(textBoxNewSDM.Text, "void") as EA.Method;
            newMethod.Update();

            SDMModelingMain.createStoryDiagram(sqlRepository.GetMethodByID(newMethod.MethodID), sqlRepository.GetOriginalRepository(), oldSDMDiagram);
            newSdmContainer = sqlRepository.GetOriginalRepository().GetElementByID(sqlRepository.GetCurrentDiagram().ParentID);
            EA.Diagram newSdmDiagram = newSdmContainer.Diagrams.GetAt(0) as EA.Diagram;
            EA.Element newStartNode = findNewStartNode(newSdmContainer);
            createExtractedSDMStatementNode();

            int leftMax = 10000000;
            int topMax = -100000;
            computeLeftAndTopMin(ref leftMax, ref topMax);

            //copy elements
            foreach (EA.Element selectedElement in this.selectedActivityNodes)
            {
                selectedElement.ParentID = newSdmContainer.ElementID;
                EA.DiagramObject newPatternDiagramObject = null;
                copyDiagramObjects(leftMax - 100, topMax + 100, newSdmDiagram, selectedElement);
                EA.DiagramObject selectedDiagramObject = getDiagramObject(selectedElement.ElementID); 
                if (selectedDiagramObject != null)
                {
                    newPatternDiagramObject = copyDiagramObject(leftMax - 100, topMax + 100, newSdmDiagram, selectedDiagramObject, selectedElement, 2);
                    EAUtil.deleteDiagramObject(repository, oldSDMDiagram, selectedElement);
                }

                //deleteUnselectedConnectors(selectedElement);
                selectedElement.Update();

                arrangeUnselectedConnectors(newSdmContainer, newSdmDiagram, newStartNode, selectedElement, newPatternDiagramObject.bottom, newPatternDiagramObject.left);
                
            }



            repository.ReloadDiagram(newSdmDiagram.DiagramID);
            repository.ReloadDiagram(oldSDMDiagram.DiagramID);
            repository.OpenDiagram(oldSDMDiagram.DiagramID);
            
            
        }

        private void arrangeUnselectedConnectors(EA.Element newSdmContainer, EA.Diagram newSdmDiagram, EA.Element newStartNode, EA.Element selectedElement, int diagObjectBottom, int diagObjectLeft)
        {
            foreach (EA.Connector connector in selectedElement.Connectors)
            {

                if (!this.selectedElementIDs.Contains(connector.ClientID))
                {
                    ActivityEdge oldEdge = new ActivityEdge(sqlRepository, sqlRepository.GetConnectorByID(connector.ConnectorID));
                    oldEdge.loadTreeFromTaggedValue();
                    //rearrange connector target to new statement node
                    if (thisOvPattern != null)
                    {
                        connector.SupplierID = thisOvPattern.ElementID;                    
                    }
                    else
                    {
                        connector.SupplierID = sdmCallPattern.ElementID;                   
                    }
                    connector.Update();

                    //create new connection from new start node to selected Element
                    EA.Connector newConnection = newStartNode.Connectors.AddNew("", Main.EAControlFlowType) as EA.Connector;
                    newConnection.SupplierID = selectedElement.ElementID;
                    newConnection.Update();

                    ActivityEdge newEdge = new ActivityEdge(sqlRepository, sqlRepository.GetConnectorByID(newConnection.ConnectorID));
                    //newEdge.GuardType = oldEdge.GuardType;
                    newEdge.saveTreeToEATaggedValue(true);


                }
                else if (!this.selectedElementIDs.Contains(connector.SupplierID))
                {
                    ActivityEdge oldEdge = new ActivityEdge(sqlRepository, sqlRepository.GetConnectorByID(connector.ConnectorID));
                    oldEdge.loadTreeFromTaggedValue();
                    //rearrange target source to new statement node
                    connector.ClientID = sdmCallPattern.ElementID;
                    connector.Update();
                    EdgeGuard newGuard = oldEdge.GuardType;


                    oldEdge.GuardType = EdgeGuard.NONE;
                    oldEdge.saveTreeToEATaggedValue(true);

                    //create new stopnode for old outgoing edge from selected element
                    EA.Element newStopNode = newSdmContainer.Elements.AddNew("", Main.EAStateNodeType) as EA.Element;
                    newStopNode.Subtype = Main.EAStopNodeSubtype;
                    newStopNode.Update();

                    StopNode stopNode = new StopNode(sqlRepository, sqlRepository.GetElementByID(newStopNode.ElementID));
                    stopNode.saveTreeToEATaggedValue(true);

                    EA.DiagramObject stopNodeDiagramObject = newSdmDiagram.DiagramObjects.AddNew("", Main.EAStateNodeType) as EA.DiagramObject;
                    stopNodeDiagramObject.ElementID = newStopNode.ElementID;
                    stopNodeDiagramObject.top = diagObjectBottom - 20;
                    stopNodeDiagramObject.bottom = diagObjectBottom - 40;
                    stopNodeDiagramObject.left = diagObjectLeft + 20;
                    stopNodeDiagramObject.right = diagObjectLeft + 40;
                    stopNodeDiagramObject.Update();

                    EA.Connector newConnection = selectedElement.Connectors.AddNew("", Main.EAControlFlowType) as EA.Connector;
                    newConnection.SupplierID = newStopNode.ElementID;
                    newConnection.Update();

                    ActivityEdge newEdge = new ActivityEdge(sqlRepository, sqlRepository.GetConnectorByID(newConnection.ConnectorID));
                    newEdge.GuardType = newGuard;
                    newEdge.saveTreeToEATaggedValue(true);
                }

            }
        }

        private static EA.Element findNewStartNode(EA.Element newSdmContainer)
        {
            EA.Element newStartNode = null;
            foreach (EA.Element element in newSdmContainer.Elements)
            {
                if (element.Stereotype == SDMModelingMain.StartNodeStereotype)
                    newStartNode = element;
            }
            return newStartNode;
        }



        private void copyDiagramObjects(int leftMax, int topMax,EA.Diagram newSdmDiagram, EA.Element selectedElement)
        {
            //copy storypattern diagramObjects
            foreach (EA.Element ov in selectedElement.Elements)
            {
                EA.DiagramObject oldOVDiagramObject = getDiagramObject(ov.ElementID);
                if (oldOVDiagramObject != null)
                {
                    copyDiagramObject(leftMax, topMax, newSdmDiagram, oldOVDiagramObject, ov, 1);
                    EAUtil.deleteDiagramObject(repository, oldSDMDiagram, ov);
                }
            }
        }

        private EA.DiagramObject copyDiagramObject(int leftMax, int topMax, EA.Diagram newDiagram, EA.DiagramObject oldDiagramObject, EA.Element element, int sequence)
        {
            EA.DiagramObject newDiaObj2 = (EA.DiagramObject)newDiagram.DiagramObjects.AddNew(
                                            "l=" + (oldDiagramObject.left - leftMax) +
                                            ";r=" + (oldDiagramObject.right - leftMax) +
                                            ";t=" + (oldDiagramObject.top - topMax) +
                                            ";b=" + (oldDiagramObject.bottom - topMax), element.Type);
            newDiaObj2.Sequence = sequence;
            newDiaObj2.ElementID = element.ElementID;
            newDiaObj2.Update();
            return newDiaObj2;
        }

        private void createExtractedSDMStatementNode()
        {
            Boolean thisPatternCreated = false;
           
            int leftMax = 0;
            int topMax = -100000;
            computeLeftAndTopMax(ref leftMax, ref topMax);


            EA.Element thisObject = searchThisObject();
            //only create a statementNode if there exists a this object on the sdm
            //otherwise create new story node with an this variable

            if (thisObject == null)
            {
                thisOvPattern = createStoryPattern("this Activity");

                thisObject = createThisOv(thisOvPattern);

                createDiagramObject(leftMax + 50, topMax - 20, 50, 100, thisObject);

                createDiagramObject(leftMax, topMax, 100, 200, thisOvPattern);

                thisPatternCreated = true;

                leftMax += 300;
                
            }

            //create new story node with objectvariable bound to new sdm.
            if (checkBoxBoundOv.Checked)
            {
                EA.Element boundOvPattern = createStoryPattern("Bound new SDM");
                EA.Element boundObject2 = createThisOv(boundOvPattern);
                SQLElement boundObject = sqlRepository.GetElementByID(boundObject2.ElementID); 


                createDiagramObject(leftMax + 20, topMax - 20, 50, 100,boundObject2);
                createDiagramObject(leftMax, topMax, 100, 200, boundOvPattern);

                ObjectVariable ov = new ObjectVariable(boundObject, sqlRepository);
                ov.loadTreeFromTaggedValue();

                ov.BindingState = BindingState.BOUND;

                MethodCallExpression mcExp = new MethodCallExpression(sqlRepository.GetMethodByID(newMethod.MethodID), sqlRepository);

                ObjectVariableExpression ovExp = new ObjectVariableExpression(sqlRepository.GetElementByID(thisObject.ElementID), sqlRepository);
                mcExp.Target = ovExp;
                ov.BindingExpression  = mcExp;
                ov.Classifier = ovClassifiersComboBox1.Classifiers[ovClassifiersComboBox1.SelectedIndex];

                ov.saveTreeToEATaggedValue(true);

                sdmCallPattern = boundOvPattern;

                EA.Method sdmMethod = repository.GetMethodByGuid(EAUtil.findTaggedValue(sqlRepository.GetElementByID(newSdmContainer.ElementID), SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value);
                sdmMethod.ClassifierID = "" + ov.Classifier.EaElement.ElementID;
                sdmMethod.ReturnType = ov.Classifier.EaElement.Name;
                sdmMethod.Update();
            }
            //instead create statement node construct 
            else
            {


                EA.Element statementActivity = createStoryPattern("Call extracted method");
                StatementNode statementNode = new StatementNode(sqlRepository, sqlRepository.GetElementByID(statementActivity.ElementID));
                MethodCallExpression mcExp = new MethodCallExpression(sqlRepository.GetMethodByID(newMethod.MethodID), sqlRepository);

                ObjectVariableExpression ovExp = new ObjectVariableExpression(sqlRepository.GetElementByID(thisObject.ElementID), sqlRepository);
                mcExp.Target = ovExp;
                statementNode.StatementExpression = mcExp;
                statementActivity.Notes = thisObject.Name + "." + newMethod.Name + "()";
                EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, statementActivity, "activityType", "call");
                EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, statementActivity, "evacuated", "false");
                statementActivity.Update();

                createDiagramObject(leftMax, topMax, 200, 200, statementActivity);
                statementNode.saveTreeToEATaggedValue(true);

                sdmCallPattern = statementActivity;
            }

            if (thisPatternCreated)
            {
                //create edge between this pattern and statement pattern
                EA.Connector acEdge = thisOvPattern.Connectors.AddNew("", Main.EAControlFlowType) as EA.Connector;
                acEdge.SupplierID = sdmCallPattern.ElementID;
                acEdge.Update();
                ActivityEdge edge = new ActivityEdge(sqlRepository, sqlRepository.GetConnectorByID(acEdge.ConnectorID));
                edge.saveTreeToEATaggedValue(true);
            }

            
        }

        private void createDiagramObject(int leftMax, int topMax, int height, int width, EA.Element thisActivity)
        {
            EA.DiagramObject diagObject = oldSDMDiagram.DiagramObjects.AddNew("l=" + (leftMax) + ";r=" + (leftMax + width) + ";t=" + topMax + "+;b=" + (topMax  - height) + ";", thisActivity.Type) as EA.DiagramObject;
            diagObject.ElementID = thisActivity.ElementID;
            diagObject.Sequence = 1;
            diagObject.Update();
        }


        private EA.Element createBoundOv(EA.Element thisActivity)
        {
            EA.Element thisObject = thisActivity.Elements.AddNew("bound", "Object") as EA.Element;
            thisObject.Update();
            return thisObject;
        }

        private EA.Element createThisOv(EA.Element thisActivity)
        {
            EA.Element thisObject = thisActivity.Elements.AddNew("this", "Object") as EA.Element;
            thisObject.ClassifierID = this.oldSDMContainer.ParentID;
            thisObject.Update();
            ObjectVariable ov = new ObjectVariable(sqlRepository.GetElementByID(thisObject.ElementID), sqlRepository);
            ov.BindingState = BindingState.BOUND;
            ov.saveTreeToEATaggedValue(true);
            return thisObject;
        }

        private EA.Element createStoryPattern(String name)
        {
            EA.Element thisActivity = this.oldSDMContainer.Elements.AddNew(name, Main.EAActivityType) as EA.Element;
            thisActivity.Stereotype = SDMModelingMain.StoryNodeStereotype;
            thisActivity.Update();
            StoryNode storyNode = new StoryNode(sqlRepository, sqlRepository.GetElementByID(thisActivity.ElementID));
            EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, thisActivity, "evacuated", "false");
            thisActivity.Update();
            storyNode.saveTreeToEATaggedValue(true);
            return thisActivity;
        }

        private EA.Element searchThisObject()
        {
            EA.Element thisObject = null;
            foreach (EA.Element ov in this.allOldOvs)
            {
                if (ov.Name == "this")
                    thisObject = ov;
            }
            return thisObject;
        }

        private void computeLeftAndTopMax(ref int leftMax, ref int topMax)
        {
            foreach (EA.DiagramObject diagObject in this.selectedOldDiagramObjects)
            {
                if (diagObject.left > leftMax)
                    leftMax = diagObject.left;
                if (diagObject.top > topMax)
                    topMax = diagObject.top;
            }

            if (leftMax < 200)
                leftMax = 200;
        }

        private void computeLeftAndTopMin(ref int leftMin, ref int topMin)
        {
            foreach (EA.DiagramObject diagObject in this.selectedOldDiagramObjects)
            {
                if (diagObject.left < leftMin)
                    leftMin = diagObject.left;
                if (diagObject.top > topMin)
                    topMin = diagObject.top;
            }

        }
        


        private EA.DiagramObject getDiagramObject(int elementID)
        {
            foreach (EA.DiagramObject diagObject in this.allOldDiagramObjects)
            {
                if (diagObject.ElementID == elementID)
                    return diagObject;
            }
            return null;
        }

        


        private void gatherUnselectedObjectVariables()
        {
            //gather unselected object variables
            foreach (EA.Element activityNode in oldSDMContainer.Elements)
            {
                if (!selectedElementIDs.Contains(activityNode.ElementID))
                {
                    foreach (EA.Element ov in activityNode.Elements)
                    {
                        allOldOvs.Add(ov);
                    }
                }
            }
        }

        private void gatherSelectedActivityNodes()
        {
            //save all selected activity nodes
            foreach (EA.DiagramObject diagObject in oldSDMDiagram.SelectedObjects)
            {
                selectedOldDiagramObjects.Add(diagObject);
                EA.Element selectedElement = sqlRepository.GetOriginalRepository().GetElementByID(diagObject.ElementID);
                if (selectedElement.MetaType == SDMModelingMain.ActivityNodeMetatype)
                {
                    foreach (EA.Element possibleOv in selectedElement.Elements)
                    {
                        if (possibleOv.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                        {
                            allNewOvs.Add(possibleOv);
                        }
                    }
                    selectedActivityNodes.Add(selectedElement);
                    selectedElementIDs.Add(selectedElement.ElementID);
                }

            }
        }

        private EA.Element getContainingClass()
        {
            if (oldSDMDiagram != null)
            {
                EA.Element eClass = repository.GetElementByID(oldSDMContainer.ParentID);
                return eClass;
            }
            return null;
        }

        private void gatherAllDiagramObjects()
        {
            //gather all "old" diagram Objects
            foreach (EA.DiagramObject diagObject in oldSDMDiagram.DiagramObjects)
            {
                allOldDiagramObjects.Add(diagObject);
            }
        }

        private void checkBoxBoundOv_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBoxBoundOv.Checked)
            {
                ovClassifiersComboBox1.Enabled = true;
            }
            else
            {
                ovClassifiersComboBox1.Enabled = false;
            }
        }

        
    }
}

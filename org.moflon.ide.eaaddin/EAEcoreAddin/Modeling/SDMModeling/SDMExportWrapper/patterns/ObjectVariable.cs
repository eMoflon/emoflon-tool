using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Web;
using EAEcoreAddin.SQLWrapperClasses;

using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;

using System.Xml;

using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;



namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns
{

    public class ObjectVariable : MocaTaggableElement
    {

        #region taggedvalue names
        public static readonly String BindingSemanticsTaggedValueName = "bindingSemantics";
        public static readonly String BindingOperatorTaggedValueName = "bindingOperator";
        public static readonly String BindingStateTaggedValueName = "bindingState";
        public static readonly String BindingExpressionOutputTaggedValueName = "bindingOutput";
        public static readonly String MceVisualizationTaggedValueName = "mceVisualization";
        public static readonly String RunStateTaggedValueName = "runState";
        public static readonly String NacIndexTaggedValueName = "nacIndex";
        #endregion

        public static readonly String OutgoingLinksNodeName = "outgoingLinks";

       
        #region properties
        private StoryPattern storyPattern;
        public virtual StoryPattern StoryPattern
        {
            get
            {
                if (storyPattern == null)
                    storyPattern = new StoryPattern(Repository, Repository.GetElementByID(sqlElement.ParentID));
                return storyPattern;
            }
        }

        private EClass classifier;
        public EClass Classifier
        {
            get
            {
                if (classifier == null)
                {
                    SQLElement element = Repository.GetElementByIDNullable(this.sqlElement.ClassifierID);
                    if (element != null)
                        classifier = new EClass(element, Repository);
                }
                return classifier;
            }
            set
            {
                this.classifier = value;
            }
        }

        public BindingState BindingState { get; set; }
        public BindingSemantics BindingSemantics { get; set; }
        public BindingOperator BindingOperator { get; set; }

        public List<AttributeAssignment> AttributeAssignments { get; set; }
        public List<Constraint> Constraints { get; set; }
        public Expression BindingExpression { get; set; }

        public SQLElement sqlElement { get; set; }

        public string Guid { get; set; }
        public string TypeGuid { get; set; }
        public int NacIndex { get; set; }

        #endregion



        public ObjectVariable(SQLElement sdmObject, SQLRepository repository)
        {
            this.Name = sdmObject.Name;
            this.Repository = repository;
            this.sqlElement = sdmObject;
            this.AttributeAssignments = new List<patterns.AttributeAssignment>();
            this.Constraints = new List<Constraint>();
            this.Guid = sdmObject.ElementGUID;
            this.NacIndex = -1;
        }


        public override void refreshSQLObject()
        {
            this.sqlElement = Repository.GetElementByID(sqlElement.ElementID);
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();

            //compute real ea object to perfom updates
            EA.Element realElement = sqlElement.getRealElement();
            //taggedvalues for shapescript
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, ObjectVariable.BindingSemanticsTaggedValueName, this.BindingSemantics.ToString().ToLower());
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, ObjectVariable.BindingOperatorTaggedValueName, this.BindingOperator.ToString().ToLower());
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, ObjectVariable.BindingStateTaggedValueName, this.BindingState.ToString().ToLower());
            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, ObjectVariable.RunStateTaggedValueName, (Constraints.Count > 0 || AttributeAssignments.Count > 0).ToString());

            if (NacIndex == -1)
                EAUtil.deleteTaggedValue(realElement, NacIndexTaggedValueName);
            else
            {
                EAUtil.setTaggedValue(Repository, realElement, NacIndexTaggedValueName, NacIndex.ToString());
            }

            realElement.Name = this.Name;
            realElement.StereotypeEx = SDMModelingMain.ObjectVariableStereotype;
            if (this.Classifier != null)
            {
                realElement.ClassifierID = this.Classifier.EaElement.ElementID;
            }

            realElement.Notes = "";

            
            String bindingExprText = SDMUtil.setOvMethodCallExpressionGui(this);

            EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, "bindingOutput", bindingExprText);

            autoSetDiagramObjectSize(bindingExprText);

            realElement.Update();
        }

        

        private void autoSetDiagramObjectSize(String bindingExprText)
        {
            //three empty lines to ensure the attribute assignments output are on the correct position

            int longestOvStringLength = 0;

            foreach (AttributeAssignment attrAssignment in this.AttributeAssignments)
            {
                String assignmentString = attrAssignment.ToString();
                if (assignmentString.StartsWith(this.sqlElement.Name))
                    assignmentString = assignmentString.Remove(0, this.sqlElement.Name.Length + 1);
                if (assignmentString.Length > longestOvStringLength)
                    longestOvStringLength = assignmentString.Length;
                sqlElement.getRealElement().Notes += "\r\n" + assignmentString;
            }
            foreach (Constraint constraint in this.Constraints)
            {
                String constraintString = constraint.ToString();
                constraintString = constraintString.Remove(0, this.sqlElement.Name.Length + 1);
                if (constraintString.Length > longestOvStringLength)
                    longestOvStringLength = constraintString.Length;
                sqlElement.getRealElement().Notes += "\r\n" + constraintString;
            }
            if (bindingExprText.Length > longestOvStringLength)
                longestOvStringLength = bindingExprText.Length;
            if (this.sqlElement.Name.Length > longestOvStringLength)
                longestOvStringLength = this.sqlElement.Name.Length;
            if (this.Classifier != null && (sqlElement.Name + this.Classifier.EaElement.Name).Length > longestOvStringLength)
                longestOvStringLength = (sqlElement.Name + this.Classifier.EaElement.Name).Length;


            int widthMultiplier = 6;
            int widthOffset = 10;

            //set the size of all diagramObjects of the current objectvariable
            foreach (String id in EAUtil.getDiagramIDsOfObject(sqlElement, Repository))
            {
                if (id != "")
                {
                    EA.Diagram currentDiagram = Repository.GetOriginalRepository().GetDiagramByID(int.Parse(id));
                    if (currentDiagram != null)
                    {
                        EA.DiagramObject diagramObject = EAEcoreAddin.Util.EAUtil.findDiagramObject(Repository, sqlElement.getRealElement(), currentDiagram);
                        if (diagramObject != null)
                        {
                            //only auto update width right after OV creation
                            if (Main.getLastBroadcastMethod() == Main.EAOnPostNewElement)
                            {
                                diagramObject.right = diagramObject.left + widthOffset + longestOvStringLength * widthMultiplier;
                                //size shouldnt be less than 90
                                if (diagramObject.right - diagramObject.left < 90)
                                    diagramObject.right = diagramObject.left + 90;
                            }

                            int assignmentConstraintCount = this.AttributeAssignments.Count + this.Constraints.Count;
                            //ideal height if there is exactly one assignment or constraint
                            if (assignmentConstraintCount == 1)
                                diagramObject.bottom = diagramObject.top - 30 - assignmentConstraintCount * 25;
                            //ideal height for more than one
                            else if (assignmentConstraintCount > 1)
                                diagramObject.bottom = diagramObject.top - 50 - assignmentConstraintCount * 13;
                            //else leave height as it is

                            diagramObject.Update();
                        }
                    }
                }
            }
        }


        public override object getObjectToBeTagged()
        {
            return this.sqlElement;
        }

        public static ObjectVariable createCorrectOvType(SQLElement eaElement, SQLRepository repository)
        {
            ObjectVariable ov = null;
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                ov = new ObjectVariable(eaElement, repository);
            else if (eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                ov = new TGGObjectVariable(eaElement, repository);
            else if (eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                ov = new TGGCorrespondence(eaElement, repository);
            return ov;
        }

        public override void addAttributesDuringExport(MocaNode actNode)
        {
            actNode.appendChildAttribute("name", this.sqlElement.Name);
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode mocaNode = new MocaNode("ObjectVariable");
            mocaNode.appendChildAttribute(Main.GuidStringName, this.sqlElement.ElementGUID);
            if (this.Classifier != null)
            {
                mocaNode.appendChildAttribute("typeGuid", this.Classifier.EaElement.ElementGUID);
            }
            else
            {
                mocaNode.appendChildAttribute("typeGuid", "");
            }
            mocaNode.appendChildAttribute(BindingStateTaggedValueName, this.BindingState.ToString().ToLower());
            mocaNode.appendChildAttribute(BindingOperatorTaggedValueName, this.BindingOperator.ToString().ToLower());
            mocaNode.appendChildAttribute("name", this.Name);
            mocaNode.appendChildAttribute(BindingSemanticsTaggedValueName, this.BindingSemantics.ToString().ToLower());
            mocaNode.appendChildAttribute(NacIndexTaggedValueName, NacIndex.ToString());


            MocaNode constraintsNode = mocaNode.appendChildNode("constraints");
            MocaNode assignmentsNode = mocaNode.appendChildNode("attributeAssignments");
            mocaNode.appendChildNode(ObjectVariable.OutgoingLinksNodeName);

            foreach (Constraint constraint in this.Constraints)
            {
                MocaNode constNode = constraintsNode.appendChildNode("constraint");
                constraint.serializeToMocaTree(constNode);
            }
            foreach (AttributeAssignment attrAssignment in this.AttributeAssignments)
            {
                attrAssignment.OvName = this.Name;
                MocaNode attANode = assignmentsNode.appendChildNode("attributeAssignment");
                attrAssignment.serializeToMocaTree(attANode);
            }
            if (this.BindingExpression != null)
            {
                MocaNode bindNode = mocaNode.appendChildNode("bindingExpression");
                this.BindingExpression.serializeToMocaTree(bindNode);
            }


            return mocaNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Constraints.Clear();
            this.AttributeAssignments.Clear();

            this.BindingSemantics = SDMUtil.computeBindingSemantics(actNode.getAttributeOrCreate(ObjectVariable.BindingSemanticsTaggedValueName).Value.ToUpper());
            this.BindingOperator = SDMUtil.computeBindingOperator(actNode.getAttributeOrCreate(ObjectVariable.BindingOperatorTaggedValueName).Value.ToUpper());
            if (actNode.getAttributeOrCreate(NacIndexTaggedValueName).Value != "")
                NacIndex = int.Parse(actNode.getAttributeOrCreate(NacIndexTaggedValueName).Value);
            this.BindingState = SDMUtil.computeBindingState(actNode.getAttributeOrCreate(ObjectVariable.BindingStateTaggedValueName).Value.ToUpper());
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.TypeGuid = actNode.getAttributeOrCreate("typeGuid").Value;
            MocaNode assignmentsNode = actNode.getChildNodeWithName("attributeAssignments");
            MocaNode constraintsNode = actNode.getChildNodeWithName("constraints");
            MocaNode bindingExpressionNode = actNode.getChildNodeWithName("bindingExpression");
            if (bindingExpressionNode != null)
            {
                this.BindingExpression = Expression.createExpression(bindingExpressionNode.getAttributeOrCreate("type").Value, Repository);
                this.BindingExpression.deserializeFromMocaTree(bindingExpressionNode);
            }
            foreach (MocaNode constraints in constraintsNode.Children)
            {
                Constraint constraint = new Constraint(Repository);
                constraint.deserializeFromMocaTree(constraints);
                this.Constraints.Add(constraint);
            }
            foreach (MocaNode assignments in assignmentsNode.Children)
            {
                AttributeAssignment atAs = new AttributeAssignment(Repository);
                atAs.deserializeFromMocaTree(assignments);
                this.AttributeAssignments.Add(atAs);
            }


        }


    }
}

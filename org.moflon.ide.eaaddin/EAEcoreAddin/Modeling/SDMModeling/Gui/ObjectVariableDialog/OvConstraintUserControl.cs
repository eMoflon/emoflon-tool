using System;
using System.IO;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Xml.Serialization;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.SDMModeling.Gui;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;




namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    public partial class OvConstraintUserControl : UserControl
    {
        SQLRepository repository;
        List<SQLAttribute> leftAttributes;
        ObjectVariable objectVariable;
        ArrayList assignmentsAndConstraints;
        public IExpressionProvider ConstraintExpressionProvider { get; set; }
        public IExpressionProvider OvExpressionProvider { get; set; }


        public OvConstraintUserControl()
        {
            InitializeComponent();
        }

        public void initializeUserControl(ref ObjectVariable objectVariable, SQLRepository repository, IExpressionProvider parentProvider)
        {        
            this.repository = repository;
            this.objectVariable = objectVariable;
            this.leftAttributes = new List<SQLAttribute>();
            this.assignmentsAndConstraints = new ArrayList();
            this.OvExpressionProvider = parentProvider;
            this.ConstraintExpressionProvider = new AttributeConstraintExpressionProvider(null, objectVariable, repository, this.OvExpressionProvider);

            this.expressionControl.initializeInformation(this.ConstraintExpressionProvider, null, repository);

            addAssignmentsAndConstraintsToListboxNew();
            addLeftAttributes();
            this.radioSet.Checked = true;

            if (listboxEntries.Items.Count > 0)
                listboxEntries.SelectedIndex = 0;

            
        }

        private void addLeftAttributes()
        {
            cmbAttributes.Items.Clear();
            if (this.objectVariable.Classifier != null)
            {
                SQLElement classifier = this.objectVariable.Classifier.EaElement;
                List<SQLElement> classifierAndParents = EAUtil.getBaseClasses(classifier);
                foreach (SQLElement classif in classifierAndParents)
                {
                    foreach (SQLAttribute attribute in classif.Attributes)
                    {
                        cmbAttributes.Items.Add(attribute.Name);
                        this.leftAttributes.Add(attribute);
                    }
                }

                if (cmbAttributes.Items.Count > 0)
                    cmbAttributes.SelectedIndex = 0;
                cmbAttributes.DropDownWidth = EAUtil.computeDropDownWidth(cmbAttributes);
            }
        }



        public void listBoxSelectedIndexChangedNew(object sender, EventArgs e)
        {

            if (listboxEntries.SelectedIndex != -1)
            {
                Object assignmentOrConstraint = this.assignmentsAndConstraints[listboxEntries.SelectedIndex];

                if (assignmentOrConstraint is AttributeAssignment)
                {
                    AttributeAssignment attAss = assignmentOrConstraint as AttributeAssignment;
                    String leftAttributeName = attAss.AttributeName;
                    String expressionName = attAss.ValueExpression.GetType().Name;
                    cmbAttributes.Text = leftAttributeName;
                    radioSet.Checked = true;
                }
                else if (assignmentOrConstraint is EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint)
                {
                    EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint constraint = assignmentOrConstraint as EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint;
                    String leftAttributeName = ((constraint.ConstraintExpression as ComparisonExpression).LeftExpression as AttributeValueExpression).AttributeName;
                    String expressionName = (constraint.ConstraintExpression as ComparisonExpression).RightExpression.GetType().Name;
                    cmbAttributes.Text = leftAttributeName;

                    ComparisonExpression compExp = constraint.ConstraintExpression as ComparisonExpression;
                    if (compExp.Operator == ComparingOperator.EQUAL)
                        radioEqual.Checked = true;
                    else if (compExp.Operator == ComparingOperator.GREATER)
                        radioGreater.Checked = true;
                    else if (compExp.Operator == ComparingOperator.GREATER_OR_EQUAL)
                        radioGreaterEqual.Checked = true;
                    else if (compExp.Operator == ComparingOperator.LESS)
                        radioLess.Checked = true;
                    else if (compExp.Operator == ComparingOperator.LESS_OR_EQUAL)
                        radioLessEqual.Checked = true;
                    else if (compExp.Operator == ComparingOperator.UNEQUAL)
                        radioNotEqual.Checked = true;

                }

                this.ConstraintExpressionProvider = new AttributeConstraintExpressionProvider(assignmentOrConstraint, objectVariable, repository, this.OvExpressionProvider);
                expressionControl.setVisualOutput(this.ConstraintExpressionProvider, ConstraintExpressionProvider.getProviderExpression());
            }

        }

        public void addAssignmentsAndConstraintsToListboxNew()
        {
            listboxEntries.Items.Clear();
            this.assignmentsAndConstraints.Clear();
            foreach (AttributeAssignment attrAssignment in this.objectVariable.AttributeAssignments)
            {
                listboxEntries.Items.Add(attrAssignment.ToString());
                this.assignmentsAndConstraints.Add(attrAssignment);
            }

            foreach (EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint constraint in this.objectVariable.Constraints)
            {
                listboxEntries.Items.Add(constraint.ToString());
                this.assignmentsAndConstraints.Add(constraint);
            }
        }


        private void bttAdd_Click(object sender, EventArgs e)
        {
            String assignmentConstraintString = "";
            //attributeAssignment
            if (radioSet.Checked && cmbAttributes.Text != "")
            {
                SQLAttribute leftAttribute = this.leftAttributes[cmbAttributes.SelectedIndex] as SQLAttribute;
                AttributeAssignment attAssignment = new AttributeAssignment(repository,this.objectVariable, leftAttribute);
                Expression rightExpression = ConstraintExpressionProvider.getExpression();
                if (rightExpression != null)
                {
                    attAssignment.ValueExpression = rightExpression;
                    this.objectVariable.AttributeAssignments.Add(attAssignment);
                    String assignmentString = attAssignment.ToString();
                    listboxEntries.Items.Add(assignmentString);

                    assignmentConstraintString = assignmentString;

                }
            }
                
            //constraint
            else if(!radioSet.Checked && cmbAttributes.Text != "")
            {
                SQLAttribute leftAttribute = this.leftAttributes[cmbAttributes.SelectedIndex];
                EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint constraint = new EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint(repository);

                ComparisonExpression compExp = new ComparisonExpression(repository);
                ComparingOperator comparingOperator = ComparingOperator.EQUAL;
                if (radioEqual.Checked) comparingOperator = ComparingOperator.EQUAL;
                else if (radioGreater.Checked) comparingOperator = ComparingOperator.GREATER;
                else if (radioLess.Checked) comparingOperator = ComparingOperator.LESS;
                else if (radioLessEqual.Checked) comparingOperator = ComparingOperator.LESS_OR_EQUAL;
                else if (radioNotEqual.Checked) comparingOperator = ComparingOperator.UNEQUAL;
                else if (radioGreaterEqual.Checked) comparingOperator = ComparingOperator.GREATER_OR_EQUAL;
                compExp.Operator = comparingOperator;

                compExp.LeftExpression = new AttributeValueExpression(repository, leftAttribute, objectVariable.sqlElement);
                Expression rightExpression = ConstraintExpressionProvider.getExpression();
                if (rightExpression != null)
                {
                    compExp.RightExpression = rightExpression;
                    constraint.ConstraintExpression = compExp;
                    this.objectVariable.Constraints.Add(constraint);
                    String constraintString = constraint.ToString();
                    listboxEntries.Items.Add(constraintString);

                    assignmentConstraintString = constraintString;
                }
            }
            addAssignmentsAndConstraintsToListboxNew();

            if (assignmentConstraintString != "")
            {
                listboxEntries.SelectedItem = assignmentConstraintString;
                //listBoxSelectedIndexChangedNew(null, null);
            }
            this.expressionControl.setInformationLossPossible(false);

        }


        private void internalAssignmentConstraintListToOVLists()
        {
            this.objectVariable.AttributeAssignments.Clear();
            this.objectVariable.Constraints.Clear();
            foreach (Object assOrCon in this.assignmentsAndConstraints)
            {
                if (assOrCon is AttributeAssignment)
                {
                    this.objectVariable.AttributeAssignments.Add(assOrCon as AttributeAssignment);    
                }
                else if (assOrCon is EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint)
                {
                    this.objectVariable.Constraints.Add(assOrCon as EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint);
                }
            }
        }

        private void bttRemove_Click(object sender, EventArgs e)
        {
            int sIndex = listboxEntries.SelectedIndex;
            if(sIndex != -1) 
                this.assignmentsAndConstraints.RemoveAt(sIndex);
            internalAssignmentConstraintListToOVLists();
            addAssignmentsAndConstraintsToListboxNew();
            if(listboxEntries.Items.Count > 0)
                listboxEntries.SelectedIndex = 0;
            this.expressionControl.setInformationLossPossible(false);
        }

     

        public void bttEdit_Click(object sender, EventArgs e)
        {
            int selectedIndex = listboxEntries.SelectedIndex;
            String assignmentConstraintString = "";
            if (selectedIndex != -1)
            {  
                //attributeAssignment
                if (radioSet.Checked && cmbAttributes.Text != "")
                {                    
                    SQLAttribute leftAttribute = this.leftAttributes[cmbAttributes.SelectedIndex] as SQLAttribute;
                    AttributeAssignment attAssignment = new AttributeAssignment(repository, objectVariable, leftAttribute);
                    Expression rightExpression = ConstraintExpressionProvider.getExpression();
                    if (rightExpression != null)
                    {
                        attAssignment.ValueExpression = rightExpression;
                        this.assignmentsAndConstraints.RemoveAt(selectedIndex);
                        this.assignmentsAndConstraints.Insert(selectedIndex, attAssignment);
                        assignmentConstraintString = attAssignment.ToString();
                    }
                } 


                //constraint
                else if (!radioSet.Checked && cmbAttributes.Text != "")
                {
                    SQLAttribute leftAttribute = this.leftAttributes[cmbAttributes.SelectedIndex];
                    EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint constraint = new EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.Constraint(repository);

                    ComparisonExpression compExp = new ComparisonExpression(repository);
                    ComparingOperator comparingOperator = ComparingOperator.EQUAL;
                    if (radioEqual.Checked) comparingOperator = ComparingOperator.EQUAL;
                    else if (radioGreater.Checked) comparingOperator = ComparingOperator.GREATER;
                    else if (radioLess.Checked) comparingOperator = ComparingOperator.LESS;
                    else if (radioLessEqual.Checked) comparingOperator = ComparingOperator.LESS_OR_EQUAL;
                    else if (radioNotEqual.Checked) comparingOperator = ComparingOperator.UNEQUAL;
                    else if (radioGreaterEqual.Checked) comparingOperator = ComparingOperator.GREATER_OR_EQUAL;
                    compExp.Operator = comparingOperator;
                    compExp.LeftExpression = new AttributeValueExpression(repository, leftAttribute, objectVariable.sqlElement);
                    Expression rightExpression = ConstraintExpressionProvider.getExpression();
                    if (rightExpression != null)
                    {
                        compExp.RightExpression = rightExpression;
                        constraint.ConstraintExpression = compExp;
                        this.assignmentsAndConstraints.RemoveAt(selectedIndex);
                        this.assignmentsAndConstraints.Insert(selectedIndex, constraint);
                        assignmentConstraintString = constraint.ToString();
                    }
                }
                internalAssignmentConstraintListToOVLists();
                addAssignmentsAndConstraintsToListboxNew();

                listboxEntries.SelectedItem = assignmentConstraintString;
            }
            this.expressionControl.setInformationLossPossible(false);
        }

        private void cmbAttributes_SelectedIndexChanged(object sender, EventArgs e)
        {
        }





        internal bool handleCloseAction()
        {
            if (expressionControl.isInformationLossPossible())
            {
                AcceptInformationLoss dialog = new AcceptInformationLoss();
                DialogResult result = dialog.ShowDialog();
                if (result == DialogResult.Cancel)
                {
                    return false;
                }
                else if (result == DialogResult.OK)
                {
                    return true;
                }
            }
            return true;
        }
    }
}

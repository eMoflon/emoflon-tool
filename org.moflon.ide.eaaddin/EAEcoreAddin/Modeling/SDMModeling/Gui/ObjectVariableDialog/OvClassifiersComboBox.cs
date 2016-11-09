using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    public class OvClassifiersComboBox : ComboBox
    {

        SQLRepository sqlRepository;

        public EClass selectedClassifier { get; set; }

        public List<EClass> Classifiers { get; set; }
        public List<int> ClassifierIDs { get; set; }
        public List<String> foundData = new List<string>();

        public Boolean userChanged = true;
        public int userSelectedIndex = 0;
        public BindingState userSelectedBound = BindingState.UNBOUND;

        String ovType;
        int classifierId;

        public OvClassifiersComboBox()
        {
            this.SelectedIndexChanged += new EventHandler(OvClassifiersComboBox_SelectedIndexChanged);
            this.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.SuggestAppend;
        }

        public void initializePossibleClassifiersComboBox(String ovStereotype, int classifierId, SQLRepository repository)
        {
            ovType = ovStereotype;
            this.Items.Clear();
            this.classifierId = classifierId;
            this.sqlRepository = repository;
            this.Classifiers = new List<EClass>();
            this.ClassifierIDs = new List<int>();
            addPossibleEntriesToComboBoxSQL();

            DropDownWidth = EAUtil.computeDropDownWidth(this);
        }

        public void printClassifier(EClass classifier, String packageName)
        {
            this.Items.Add(classifier.Name + " : " + packageName);
        }

        public void printClassifiers()
        {
            foreach (EClass classifier in Classifiers)
            {
                SQLPackage pkg = sqlRepository.GetPackageByID(classifier.EaElement.PackageID);
                printClassifier(classifier, pkg.Name);

            }
            this.Text = this.Items[0] as String;
            
        }

        public void clearClassifiers()
        {
            ClassifierIDs.Clear();
            Classifiers.Clear();
            Items.Clear();
        }

        public void addClassifier(EClass classifier)
        {
            if (!ClassifierIDs.Contains(classifier.EaElement.ElementID))
            {
                ClassifierIDs.Add(classifier.EaElement.ElementID);
                Classifiers.Add(classifier);
            }
        }

        public void removeClassifier(EClass classifier)
        {
            EClass todelete = null;
            foreach (EClass eClass in Classifiers)
            {
                if (eClass.EaElement.ElementID == classifier.EaElement.ElementID)
                {
                    todelete = eClass;
                }
            }
            Classifiers.Remove(todelete);
            ClassifierIDs.Remove(classifier.EaElement.ElementID);
        }


        private void addPossibleEntriesToComboBoxSQL()
        {
            //use sql commands to search the repository for possible entries in the types combobox

                //get ids and names of all eclasses and the names of the packages the eclasses are contained sorted by eclass name
                String sqlResult = sqlRepository.SQLQuery("select a.Name AS Name, a.Object_ID, a.ea_guid, b.Name as PackageName, a.Stereotype from t_object a,t_package b where ( a.stereotype = '" + ECOREModelingMain.EClassStereotype + "' OR  a.stereotype = '" + TGGModelingMain.TggRuleStereotype + "' OR  a.stereotype = '" + TGGModelingMain.TggCorrespondenceTypeStereotype + "') and a.Package_ID = b.Package_ID ORDER BY a.Name");
                foundData.AddRange(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlResult, "Row"));
        
            foreach (String row in foundData)
            {
                EClass classifier = new EClass(new SQLElement(sqlRepository, row), sqlRepository);
                Boolean shouldAddClassifier = true;
                if (ovType == TGGModelingMain.TggCorrespondenceStereotype  && classifier.EaElement.Stereotype != TGGModelingMain.TggCorrespondenceTypeStereotype)
                {
                    shouldAddClassifier = false;
                }
                if (shouldAddClassifier)
                {
                    printClassifier(classifier, EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(row, "PackageName")[0]);
                    addClassifier(classifier);
                }
            }
            if (classifierId > 0)
            {
                this.Text = this.Items[this.ClassifierIDs.IndexOf(classifierId)] as String;
            }
            else
            {
                this.Text = this.Items[0] as String;
            }

        }

        public void OvClassifiersComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (userChanged)
            {
                this.userSelectedIndex = this.SelectedIndex;

                
            }

        
        }




        /// <summary>
        /// Verhindert das Einfügen von Text mithilfe von Strg + V.
        /// </summary>
        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            if (keyData == (Keys.Control | Keys.V))
                return true;
            else
                return base.ProcessCmdKey(ref msg, keyData);
        }

    }
          
}

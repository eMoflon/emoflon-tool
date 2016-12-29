using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Diagnostics;
using System.Reflection;
using System.IO;
using EAEcoreAddin.Consistency.Rules.ElementRules;
using System.ComponentModel;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;

namespace EAEcoreAddin.Modeling.Util
{
    public class EAPUpdater
    {

        public readonly String moflonVersionNumber = "2.39";
        private SQLRepository sqlRep;
        private EA.Repository repository;
        private UpdateDialog updateDialog;
        private bool showStatusBar;
        private bool ignoreVersionNumber;

        public BackgroundWorker worker { get; set; }


        public EAPUpdater(EA.Repository repository, Boolean showStatusBar)
        {
            this.showStatusBar = showStatusBar;
            this.repository = repository;
            sqlRep = new SQLRepository(repository, false);
            updateDialog = new UpdateDialog();


            worker = new BackgroundWorker();
            worker.WorkerReportsProgress = true;
            worker.DoWork += new DoWorkEventHandler(worker_DoWork);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
            worker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);
        }

        public EAPUpdater(EA.Repository repository) : this(repository, true)
        {            
        }


        public void updateEAPIfNecessary(Boolean ignoreVersionNumber)
        {
            if (showStatusBar)
            {
                this.ignoreVersionNumber = ignoreVersionNumber;
                worker.RunWorkerAsync();
                updateDialog.ShowDialog();
            }
            else
            {
                doUpdate();
            }
        }

        /*
         * All update methods should be implemented so that it is not problematic to invoke them even in cases where the update is no longer necessary.
         * This way very old EAPs and newer EAPs can be handled uniformly.
         */
        private void updateEap()
        {
            insertEcoreDatatypes();

            updateDiagramsMetatypes();

            OldCspsToNewCsps();

            deleteTGGRuleMainMethods();

            updateToStandardTaggedValues();

            updateMocaTreeFragments();
        }

        private void updateMocaTreeFragments()
        {
            // For all packages (recursively!) in TGG and Ecore projects
            foreach (EA.Package p in EAUtil.findAllMoflonPackages(repository))
            {
                SQLPackage p2 = sqlRep.GetPackageByID(p.PackageID);
                if (p.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype) 
                {
                    TGG tgg = new TGG(sqlRep, p2);
                    tgg.loadTreeFromTaggedValue();
                    tgg.saveTreeToEATaggedValue(false);

                }
                else if(p.Element.Stereotype == TGGModelingMain.TggRulePackageStereotype)
                {
                    TGGRulePackage package = new TGGRulePackage(p2, sqlRep);
                    package.loadTreeFromTaggedValue();
                    package.saveTreeToEATaggedValue(false);
                }
                else
                {
                    EPackage package = new EPackage(p2, sqlRep);
                    package.loadTreeFromTaggedValue();
                    package.saveTreeToEATaggedValue(false);
                }
            }

            foreach (EA.Element eClassElement in EAUtil.findAllEClasses(repository))
            {
                EClass eClass = new EClass(sqlRep.GetElementByID(eClassElement.ElementID), sqlRep);
                eClass.saveTreeToEATaggedValue(false);
            }

            foreach (EA.Element ruleElement in EAUtil.findAllStereotypedElements(repository, TGGModelingMain.TggRuleStereotype))
            {
                TGGRule rule = new TGGRule(sqlRep, sqlRep.GetElementByID(ruleElement.ElementID));
                rule.loadTreeFromTaggedValue();
                rule.saveTreeToEATaggedValue(false);
            }

            foreach (EA.Element corrTypeElement in EAUtil.findAllStereotypedElements(repository, TGGModelingMain.TggCorrespondenceTypeStereotype))
            {
                TGGCorrespondenceType corrType = new TGGCorrespondenceType(sqlRep.GetElementByID(corrTypeElement.ElementID), sqlRep);
                corrType.loadTreeFromTaggedValue();
                corrType.saveTreeToEATaggedValue(false);
            }

        }

        private void updateToStandardTaggedValues()
        {
            // For all packages (recursively!) in TGG and Ecore projects
            // Create new set of tagged values if not already present and set to default value
            foreach (EA.Package p in EAUtil.findAllMoflonPackages(repository))
            {
                SQLPackage p2 = sqlRep.GetPackageByID(p.PackageID);
                EPackage package = new EPackage(p2, sqlRep);
                package.updateToStandardTaggedValues();
            }


         /*   deleteOldOvTaggedValues(SDMModelingMain.ObjectVariableStereotype);
            deleteOldOvTaggedValues(TGGModelingMain.TggObjectVariableStereotype);
            deleteOldOvTaggedValues(TGGModelingMain.TggCorrespondenceStereotype);

            deleteOlvLvTaggedValues(SDMModelingMain.LinkVariableStereotype);
            deleteOlvLvTaggedValues(TGGModelingMain.TggLinkVariableStereotype);
            */


        }

        private void deleteOlvLvTaggedValues(string p)
        {
            foreach (EA.Connector c in EAUtil.findAllStereotypedConnectors(repository, p))
            {
                EAUtil.deleteTaggedValue(c, "assoModifier");
                EAUtil.deleteTaggedValue(c, "assoType");
                EAUtil.deleteTaggedValue(c, "textOfReference");
                EAUtil.deleteTaggedValue(c, "textOfReferenceOpposite");
                EAUtil.deleteTaggedValue(c, "swap");
                EAUtil.deleteTaggedValue(c, "idOfReference");
                EAUtil.deleteTaggedValue(c, "assoName");
                EAUtil.deleteTaggedValue(c, "sdmXMI");
                EAUtil.deleteTaggedValue(c, "bound");
            }
        }

        private void deleteOlvLvTaggedValues()
        {
           
        }

        private void deleteOldOvTaggedValues(String stereotype)
        {
            foreach (EA.Element e in EAUtil.findAllStereotypedElements(repository, stereotype))
            {
                deleteOldOVAssignmentTags(e, 0);
                deleteOldOVAssignmentTags(e, 1);
                deleteOldOVAssignmentTags(e, 2);
                deleteOldOVAssignmentTags(e, 3);
                deleteOldOVAssignmentTags(e, 4);

                EAUtil.deleteTaggedValue(e, "bindingAttributeID");
                EAUtil.deleteTaggedValue(e, "assignmentCount");
                EAUtil.deleteTaggedValue(e, "bindingExpression");
                EAUtil.deleteTaggedValue(e, "bindingMethodID");
                EAUtil.deleteTaggedValue(e, "bindingParameters");
                EAUtil.deleteTaggedValue(e, "bindingTargetID");
                EAUtil.deleteTaggedValue(e, "objConstraint");
                EAUtil.deleteTaggedValue(e, "objModifier");
                EAUtil.deleteTaggedValue(e, "sdmXMI");
                EAUtil.deleteTaggedValue(e, "bound");
            }
        }

        private static void deleteOldOVAssignmentTags(EA.Element e, int i)
        {
            EAUtil.deleteTaggedValue(e, "Assignment" + i);
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "LeftAttributeID");
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "RightAttributeID");
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "RightExpression");
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "RightMethodID");
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "RightParameters");
            EAUtil.deleteTaggedValue(e, "Assignment" + i + "RightTargetID");
        }


        private void deleteTGGRuleMainMethods()
        {
            String result = repository.SQLQuery(@"SELECT a.ea_guid FROM t_object a WHERE a.Stereotype = 'Rule' OR a.Stereotype = 'Multi-Rule' ");

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "ea_guid"))
            {
                if (row != "")
                {
                    EA.Element ruleElement = repository.GetElementByGuid(row);
                    if (ruleElement != null)
                    {
                        short i = 0;
                        foreach (EA.Method method in ruleElement.Methods)
                        {
                            if (method.Name == ruleElement.Name)
                            {
                                ruleElement.Methods.Delete(i);
                            }
                            i++;
                        }
                        EAUtil.deleteTaggedValue(ruleElement, "MainMethodGuid");
                    }
                }
            }
        }


        private String OldCspsToNewCsps()
        {
            String result = repository.SQLQuery("select a.* from t_object a, t_package b, t_diagramobjects c where a.Object_Type = 'Constraint' AND a.Package_ID = b.Package_ID and b.Name = 'Rules' AND c.Object_ID = a.Object_ID");
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLElement actElement = new SQLElement(sqlRep, row);
                    CSPConstraintInvalid ruleInstance = new CSPConstraintInvalid();
                    ruleInstance.doRuleQuickFix(actElement, sqlRep, 0, "Tgg CSP is outdated and cant be modified - quickfix is recommended");
                }
            }
            return result;
        }

        private void updateOldConstraints(ref EA.Element ruleElement, SQLRepository Repository)
        {
            bool timeToRefresh = false;
            if (ruleElement.Diagrams.Count > 0)
            {
                EA.Diagram ruleDiagram = ruleElement.Diagrams.GetAt(0) as EA.Diagram;
                ruleDiagram = Repository.GetOriginalRepository().GetDiagramByID(ruleDiagram.DiagramID);

                foreach (EA.DiagramObject diagObject in ruleDiagram.DiagramObjects)
                {
                    SQLElement actElement = Repository.GetElementByID(diagObject.ElementID);
                    if (actElement.Type == "Constraint")
                    {
                        
                    }
                }
            }
            if (timeToRefresh)
                ruleElement = EAUtil.sqlEAObjectToOriginalObject(Repository, ruleElement) as EA.Element;
        }


        private  void updateVersionNumber()
        {
            foreach (EA.Package model in repository.Models)
            {
                model.Notes = "Moflon::VersionNumber=" + moflonVersionNumber;
                model.Update();
            }
        }

        private  Boolean checkForVersionNumber(EA.Repository repository)
        {
            Boolean shouldUpdate = false;

            foreach (EA.Package model in repository.Models)
            {
                if (model.Notes != "Moflon::VersionNumber=" + moflonVersionNumber)
                {
                    return true;
                }
            }
            return shouldUpdate;
        }

        


        private  void updateDiagramMetaType(String oldMetaType, String newMetaType, EA.Repository repository)
        {
            SQLRepository sqlRep = new SQLRepository(repository, false);

            String sqlString = "select Diagram_ID from t_diagram where StyleEx LIKE '*" + oldMetaType + "*'";
            String result = repository.SQLQuery(sqlString);

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLDiagram diag = new SQLDiagram(sqlRep, row);
                    EA.Diagram rdiag = EAUtil.sqlEAObjectToOriginalObject(sqlRep, diag) as EA.Diagram;
                    String StyleEx = rdiag.StyleEx.Replace(oldMetaType, newMetaType);
                    sqlString = "UPDATE t_diagram SET StyleEx = '" + StyleEx + "' WHERE Diagram_ID = " + rdiag.DiagramID;
                    repository.Execute(sqlString);
                }
            }
        }


        private  void updateDiagramsMetatypes()
        {
            //update old diagram metatypes to new ones
            updateDiagramMetaType(ECOREModelingMain.EcoreDiagramMetatype[1], ECOREModelingMain.EcoreDiagramMetatype[0], repository);
            updateDiagramMetaType(SDMModelingMain.SdmDiagramMetatype[1], SDMModelingMain.SdmDiagramMetatype[0], repository);
            updateDiagramMetaType(TGGModelingMain.TggSchemaDiagramMetatype[1], TGGModelingMain.TggSchemaDiagramMetatype[0], repository);
            updateDiagramMetaType(TGGModelingMain.TggRuleDiagramMetatype[1], TGGModelingMain.TggRuleDiagramMetatype[0], repository);
            updateDiagramMetaType(TGGModelingMain.TggRulesDiagramMetatype[1], TGGModelingMain.TggRulesDiagramMetatype[0], repository);
            updateDiagramMetaType(TGGModelingMain.TggPatternsDiagramMetatype[1], TGGModelingMain.TggPatternsDiagramMetatype[0], repository);
        }

        private  void insertEcoreDatatypes()
        {
            //insert ecore datatypes file
            String refData = readEcoreDatatypesFile();
            repository.CustomCommand("Repository", "ImportRefData", refData);
        }


        private  String readEcoreDatatypesFile()
        {
            String refData = "";
            Assembly assem = Main.StaticAssembly;
            if (assem == null)
            {
                assem = GetType().Assembly;
            }
            using (Stream stream = assem.GetManifestResourceStream("EAEcoreAddin.Resources.ECORE_Datatypes.xml"))
            {
                try
                {
                    using (StreamReader reader = new StreamReader(stream))
                    {
                        refData = reader.ReadToEnd();
                    }
                }
                catch
                {
                    System.Windows.Forms.MessageBox.Show("Error Initializing Technology");
                }
            }
            return refData;
        }


        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            doUpdate();

        }

        private void doUpdate()
        {
            Boolean shouldUpdate = checkForVersionNumber(repository);
            if (shouldUpdate || ignoreVersionNumber)
            {
                updateEap();
                updateVersionNumber();
            }
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
  
        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            updateDialog.Close();
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGActions
{
    public partial class NewTGGProjectDialog : Form
    {

        private List<SQLPackage> possibleMetamodels;
        private SQLPackage newTGGProject;
        private SQLRepository repository;

        private SQLPackage selectedSourceMetamodel;
        private SQLPackage selectedTargetMetamodel;

        private TGG tgg;

        public NewTGGProjectDialog(SQLRepository repository, SQLPackage newTggProject)
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.repository = repository;
            this.newTGGProject = newTggProject;
            
            this.tgg = new TGG(repository, newTggProject);
            tgg.loadTreeFromTaggedValue();

            InitializeInformation();

        }

        private void InitializeInformation()
        {

            textBoxName.Text = tgg.Name;

            this.possibleMetamodels = new List<SQLPackage>();
            repository.Models.Refresh();
            foreach(SQLPackage outermostPackage in repository.Models)
            {
                outermostPackage.Packages.Refresh();
                foreach (SQLPackage model in outermostPackage.Packages)
                {
                    if (model.Element.Stereotype != TGGModelingMain.TggSchemaPackageStereotype)
                    {
                        this.possibleMetamodels.Add(model);
                        this.sourceDomain.Items.Add(model.Name);
                        this.targetDomain.Items.Add(model.Name);
                    }

                }
            }

            int i = 0;
            foreach(SQLPackage metamodelPackage in this.possibleMetamodels)
            {
                foreach(Domain domain in this.tgg.Domains)
                {
                    if(domain.DomainType == DomainType.SOURCE && metamodelPackage.PackageGUID == domain.MetamodelGuid)
                    {
                        this.sourceDomain.SelectedIndex = i;
                    }
                    else if(domain.DomainType == DomainType.TARGET && metamodelPackage.PackageGUID == domain.MetamodelGuid)
                    {
                        this.targetDomain.SelectedIndex = i;
                    }
                }
                i++;
            }

            if (this.sourceDomain.Text == "")
                this.sourceDomain.SelectedIndex = 0;
            if (this.targetDomain.Text == "")
                this.targetDomain.SelectedIndex = 0;
        }


        private void okButton_Click(object sender, EventArgs e)
        {
            if (this.selectedSourceMetamodel != null && this.selectedTargetMetamodel != null)
            {
                //create rule package
                if (newTGGProject.Packages.Count == 0)
                {
                    EA.Package rulesPackage = newTGGProject.getRealPackage().Packages.AddNew("Rules", TGGModelingMain.TggRulePackageStereotype) as EA.Package;
                    rulesPackage.Update();
                    TGGRulePackage rPackage = new TGGRulePackage(repository.GetPackageByID(rulesPackage.PackageID), repository);
                    rPackage.saveTreeToEATaggedValue(true);

                    EA.Diagram rulesDiagram = rulesPackage.Diagrams.AddNew("Rules", TGGModelingMain.TggRulesDiagramMetatype[0]) as EA.Diagram;
                    rulesDiagram.Update();
                    newTGGProject.Packages.Refresh();
                }

                Metamodel sourceMetamodel = new Metamodel(repository, this.selectedSourceMetamodel);
                Metamodel targetMetamodel = new Metamodel(repository, this.selectedTargetMetamodel);
                Metamodel correspondenceMetamodel = new Metamodel(repository, repository.GetPackageByID(newTGGProject.PackageID));

                Domain sourceDomain = new Domain(sourceMetamodel, DomainType.SOURCE);
                Domain targetDomain = new Domain(targetMetamodel, DomainType.TARGET);
                Domain correspondenceDomain = new Domain(correspondenceMetamodel, DomainType.CORRESPONDENCE);

                tgg.Metamodels.Clear();
                tgg.Domains.Clear();

                tgg.Metamodels.Add(sourceMetamodel);
                tgg.Metamodels.Add(targetMetamodel);
                tgg.Metamodels.Add(correspondenceMetamodel);

                tgg.Domains.Add(sourceDomain);
                tgg.Domains.Add(targetDomain);
                tgg.Domains.Add(correspondenceDomain);

                tgg.EaPackage.getRealPackage().Name = textBoxName.Text;

                tgg.saveTreeToEATaggedValue(true);
                Close();
            }

        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {

            targetDomain.Enabled = true;
            int oldIndex = targetDomain.SelectedIndex;
            targetDomain.SelectedIndex = 0;
            if(oldIndex != -1)
                targetDomain.SelectedIndex = oldIndex;
            
        }

        private void sourceDomain_SelectedIndexChanged(object sender, EventArgs e)
        {
            if(sourceDomain.SelectedIndex != -1)
                selectedSourceMetamodel = this.possibleMetamodels[sourceDomain.SelectedIndex];
        }

        private void targetDomain_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (targetDomain.SelectedIndex != -1)
                selectedTargetMetamodel = this.possibleMetamodels[targetDomain.SelectedIndex];
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {

        }

        private void groupBox2_Enter(object sender, EventArgs e)
        {

        }

    }
}

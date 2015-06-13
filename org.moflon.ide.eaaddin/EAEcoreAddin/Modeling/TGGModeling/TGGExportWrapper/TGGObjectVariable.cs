using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    class TGGObjectVariable : EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.ObjectVariable
    {

        public static readonly String RefinedTaggedValueName = "refined";

        String metamodelGuid;
        public DomainType domain;

        public TGGObjectVariable(SQLElement sdmObject, SQLRepository repository) :
            base(sdmObject, repository)
        {

            if (sdmObject.PackageID != 0)
            {
                SQLPackage mmPkg = repository.GetPackageByID(sdmObject.PackageID);
                while (!mmPkg.IsModel)
                    mmPkg = repository.GetPackageByID(mmPkg.ParentID);
                this.metamodelGuid = mmPkg.PackageGUID;               
            }
        }

        public override void addAttributesDuringExport(MocaNode actNode)
        {
            base.addAttributesDuringExport(actNode);
            actNode.appendChildAttribute("domain", TGGModelingUtil.getDomainOfObjectVariable(Repository, this).ToString().ToLower());
        }

        public override StoryPattern StoryPattern
        {
            get
            {
                StoryPattern storyPattern = null;
                SQLElement patternElement = Repository.GetElementByID(sqlElement.ParentID);
                if(patternElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                {
                    storyPattern = new TGGRule(Repository, patternElement);
                }
                
                return storyPattern;
            }
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();

            Boolean refined =  ovIsRefining();
          
            EA.Element realElement = sqlElement.getRealElement();
            EAUtil.setTaggedValue(Repository, realElement, RefinedTaggedValueName, refined.ToString().ToLower());

            realElement.StereotypeEx = TGGModelingMain.TggObjectVariableStereotype;
            realElement.Update();
        }

        private Boolean ovIsRefining()
        {
            String sqlStub = "";
            TGGRule rule = StoryPattern as TGGRule;
            List<SQLElement> allBaseRules = rule.getAllBaseRules();
            foreach (SQLElement ruleElement in allBaseRules)
            {
                sqlStub += "ParentID = " + ruleElement.ElementID + " OR ";
            }
            if (sqlStub != "")
            {
                sqlStub = sqlStub.Substring(0, sqlStub.Length - 4);

                String sqlString = "select Count(*) as count from t_object where (" + sqlStub + ") AND Name = '" + sqlElement.Name + "'";
                String sqlResult = Repository.SQLQuery(sqlString);
                if (EAUtil.getXMLNodeContentFromSQLQueryString(sqlResult, "count")[0] != "0")
                {
                    return true;
                }
            }
            return false;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            this.domain = TGGModelingUtil.getDomainOfObjectVariable(Repository, this);

            MocaNode ovNode = base.serializeToMocaTree();
            ovNode.appendChildAttribute("metamodelGuid", this.metamodelGuid);
            ovNode.appendChildAttribute("domain", this.domain.ToString().ToLower());
            ovNode.Name = "TGGObjectVariable";
            return ovNode;
        }

        public override void deserializeFromMocaTree(MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);
            this.domain = TGGModelingUtil.stringToDomainType(actNode.getAttributeOrCreate("domain").Value);
            this.metamodelGuid = actNode.getAttributeOrCreate("metamodelGuid").Value;
        }


        

    }
}

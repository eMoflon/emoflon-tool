using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Collections;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class PackageWithoutDiagram : PackageRule
    {
        public override List<string> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (eaPackage.Element.Stereotype == ECOREModelingMain.EPackageStereotype)
            {
                Boolean valid = false;
                foreach (SQLDiagram diagram in eaPackage.Diagrams)
                {
                    if (ECOREModelingMain.EcoreDiagramMetatype.Contains(diagram.MetaType))
                    {
                        valid = true;
                    }
                }
                if (!valid)
                    results.Add("Ecore Package must contain an Ecore diagram");
            }
            else if (eaPackage.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
            {
                Boolean valid = false;
                foreach (SQLDiagram diagram in eaPackage.Diagrams)
                {
                    if (TGGModelingMain.TggSchemaDiagramMetatype.Contains(diagram.MetaType))
                    {
                        valid = true;
                    }
                }
                if (!valid)
                    results.Add("TGG Package must contain a TGG diagram");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (errorMessage == "TGG Package must contain a TGG diagram")
            {
                EA.Package pkg = EAUtil.sqlEAObjectToOriginalObject(repository, eaPackage) as EA.Package;
                EA.Diagram newDiagram = pkg.Diagrams.AddNew(eaPackage.Name, TGGModelingMain.TggSchemaDiagramMetatype[0]) as EA.Diagram;
                newDiagram.Update();
            }
            else if (errorMessage == "Ecore Package must contain an Ecore diagram")
            {
                EA.Package pkg = EAUtil.sqlEAObjectToOriginalObject(repository, eaPackage) as EA.Package;
                EA.Diagram newDiagram = pkg.Diagrams.AddNew(eaPackage.Name, ECOREModelingMain.EcoreDiagramMetatype[0]) as EA.Diagram;
                newDiagram.Update();
            }
            
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            if (errorMessage == "TGG Package must contain a TGG diagram")
            {
                msgs.Add("Add TGG diagram");
            }
            else if (errorMessage == "Ecore Package must contain an Ecore diagram")
            {
                msgs.Add("Add Ecore diagram");
            }
            return msgs;
        }
    }
}

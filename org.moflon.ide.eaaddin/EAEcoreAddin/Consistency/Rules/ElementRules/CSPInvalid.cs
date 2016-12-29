using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Util;
using System.Collections;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class CSPConstraintInvalid : ElementRule
    {
        public override List<string> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (eaElement.Stereotype == TGGModelingMain.CSPConstraintStereotype ||  eaElement.Stereotype == "TGGCsp")
            {
                SQLElement parentElement = repository.GetElementByID(eaElement.ParentID);
                if (parentElement.Stereotype != SDMModelingMain.StoryNodeStereotype)
                {

                    CSPInstance csp = new CSPInstance(repository, eaElement);
                    if (csp.loadTreeFromTaggedValue())
                    {
                        foreach (CSPInstanceEntry instance in csp.createdEntries)
                        {
                            foreach (Expression exp in instance.typedInExpressions)
                            {
                                if (!ConsistencyUtil.checkExpression(eaElement, exp, repository))
                                {
                                    results.Add("Tgg CSP expression is invalid: " + exp);
                                }
                            }
                        }
                    }
                    else
                    {
                        results.Add("Tgg CSP is outdated and maybe erroneous - updating it manually is recommended");
                    }
                }
            }
            else if (eaElement.Type == "Constraint")
            {
                results.Add("Tgg CSP is outdated and cant be modified - quickfix is recommended");
            }

            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository sqlRep, int i, string errorMessage)
        {
            EA.Repository repository = sqlRep.GetOriginalRepository();
            if (errorMessage == "Tgg CSP is outdated and cant be modified - quickfix is recommended")
            {
                if (i == 0)
                {
                    transformConstraintToCSPElement(eaElement, sqlRep, repository);
                }
            }
        }

       

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            if (errorMessage == "Tgg CSP is outdated and maybe erroneous - updating it manually is recommended")
                return RuleErrorLevel.Warning;
            else if (errorMessage == "Tgg CSP is outdated and cant be modified - quickfix is recommended")
                return RuleErrorLevel.Warning;
            else
                return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList quickfixes = new ArrayList();

            if (errorMessage == "Tgg CSP is outdated and cant be modified - quickfix is recommended")
            {
                quickfixes.Add("Update CSP to current version");
            }
            return quickfixes;
        }



        private static void transformConstraintToCSPElement(SQLElement eaElement2, SQLWrapperClasses.SQLRepository sqlRep, EA.Repository repository)
        {
            EA.Element eaElement = EAUtil.sqlEAObjectToOriginalObject(sqlRep, eaElement2) as EA.Element;
            String result = repository.SQLQuery("select Diagram_ID from t_diagramobjects where Object_ID = " + eaElement.ElementID);
            string diagramId = EAUtil.getXMLNodeContentFromSQLQueryString(result, "Diagram_ID")[0];
            if (diagramId != "")
            {
                EA.Diagram diagram = repository.GetDiagramByID(int.Parse(diagramId));
                EA.DiagramObject oldDiagObject = EAUtil.findDiagramObject(sqlRep, eaElement, diagram);
                EA.Element ruleElement = repository.GetElementByID(diagram.ParentID);
                if (ruleElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                {
                    List<EA.Connector> outgoingCons = new List<EA.Connector>();

                    foreach (EA.Connector con in eaElement.Connectors)
                    {
                        if (con.Stereotype == "ConstraintLink" && con.ClientID == eaElement.ElementID)
                        {
                            outgoingCons.Add(con);
                        }
                    }

                    EA.Element newCsp = ruleElement.Elements.AddNew("", Main.EAClassType) as EA.Element;
                    newCsp.Notes = eaElement.Notes;
                    newCsp.Stereotype = TGGModelingMain.CSPConstraintStereotype;
                    newCsp.Update();

                    EA.TaggedValue oldTag = EAUtil.findTaggedValue(eaElement, Main.MoflonExportTreeTaggedValueName);
                    if (oldTag != null)
                    {
                        EAUtil.setTaggedValueNotes(sqlRep, newCsp, oldTag.Name, oldTag.Notes);
                    }


                    EA.DiagramObject newDiagObject = diagram.DiagramObjects.AddNew("", Main.EAClassType) as EA.DiagramObject;
                    newDiagObject.ElementID = newCsp.ElementID;
                    newDiagObject.left = oldDiagObject.left;
                    newDiagObject.right = oldDiagObject.right;
                    newDiagObject.bottom = oldDiagObject.bottom;
                    newDiagObject.top = oldDiagObject.top;
                    newDiagObject.Update();

                    foreach (EA.Connector con in outgoingCons)
                    {
                        con.ClientID = newCsp.ElementID;
                        con.Update();
                    }



                    EAUtil.deleteDiagramObject(repository, diagram, eaElement);
                    EAUtil.deleteElement(eaElement, sqlRep);
                }
            }
        }
    }
}

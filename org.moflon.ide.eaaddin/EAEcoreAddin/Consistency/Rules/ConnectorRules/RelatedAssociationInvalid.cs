using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System.Diagnostics;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class RelatedAssociationInvalid : ConnectorRule
    {
 
        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaConnector.Stereotype == SDMModelingMain.LinkVariableStereotype || eaConnector.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
            {
                LinkVariable lv = new LinkVariable(eaConnector, repository);
                lv.loadTreeFromTaggedValue();

                SQLConnector referencedReference = SDMUtil.getRelatedEReference(lv);

                
                //try to get the related EReference
                
                //related EReference cant be found
                if(referencedReference == null)
                    results.Add("There is no related EReference");


                //rolename of linkVariable not existing in the referenced EReference
                if (eaConnector.SupplierEnd.Role != "" && referencedReference != null)
                {
                    if ( !((eaConnector.SupplierEnd.Role == referencedReference.ClientEnd.Role || eaConnector.SupplierEnd.Role == referencedReference.SupplierEnd.Role)
                          && (eaConnector.ClientEnd.Role == referencedReference.ClientEnd.Role || eaConnector.ClientEnd.Role == referencedReference.SupplierEnd.Role)))                   
                    {
                        results.Add("The rolename(s) of the LinkVariable differs from the related EReference");
                    }
                }

                //for unidirectional EReferences the supplierEnd.Role of the linkvariable must be the "value" rolename
                if (eaConnector.ClientEnd.Role != "" && eaConnector.SupplierEnd.Role == "")
                    results.Add("The rolename(s) of the LinkVariable differs from the related EReference");

                //checks if the related reference is connecting the correct EClasses

                if (referencedReference != null)
                {
                    try
                    {

                        SQLElement lvSource = repository.GetElementByID(eaConnector.ClientID);
                        SQLElement lvTarget = repository.GetElementByID(eaConnector.SupplierID);

                        SQLElement sourceClassifier = EAUtil.getClassifierElement(repository, lvSource.ClassifierID);
                        SQLElement targetClassifier = EAUtil.getClassifierElement(repository, lvTarget.ClassifierID);


                        SQLElement refRefClient = repository.GetElementByID(referencedReference.ClientID);
                        SQLElement refRefSupplier = repository.GetElementByID(referencedReference.SupplierID);


                        if (sourceClassifier != null && targetClassifier != null)
                        {

                            List<SQLElement> sourceBases = EAUtil.getBaseClasses(sourceClassifier);
                            List<SQLElement> targetBases = EAUtil.getBaseClasses(targetClassifier);

                            Boolean valid = false;

                            foreach (SQLElement sourceBase in sourceBases)
                            {
                                foreach (SQLElement targetBase in targetBases)
                                {
                                    if (((referencedReference.ClientID == sourceBase.ElementID || referencedReference.ClientID == targetBase.ElementID)
                                       && (referencedReference.SupplierID == sourceBase.ElementID || referencedReference.SupplierID == targetBase.ElementID)))
                                    {
                                        valid = true;
                                    }
                                }
                            }

                            //maybe there is a direct connectio to EObject

                            if (!valid)
                            {
                                if (refRefClient.Name == "EObject" && (eaConnector.SupplierEnd.Role == referencedReference.SupplierEnd.Role || eaConnector.SupplierEnd.Role == referencedReference.ClientEnd.Role)
                                    || refRefSupplier.Name == "EObject" && (eaConnector.SupplierEnd.Role == referencedReference.SupplierEnd.Role || eaConnector.SupplierEnd.Role == referencedReference.ClientEnd.Role))
                                {
                                    valid = true;
                                }
                            }


                            if (!valid)
                            {
                                results.Add("The related EReference doesn't connect EClasses from the same type hiearchy as the LinkVariable");
                            }
                        }
                    }
                    catch
                    {
                        results.Add("Error during computing of related EReference. Please update your LinkVariable manually");
                    }
                }
            }


            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLRepository repository, int i, String errorMessage)
        {

            LinkVariable lv = new LinkVariable(eaConnector, repository);
            lv.loadTreeFromTaggedValue();


            if (errorMessage == "The rolename(s) of the LinkVariable differs from the related EReference")
            {
                if (i == 0)
                {
                    SQLConnector referencedReference = SDMUtil.getRelatedEReference(lv);
                    if (referencedReference != null)
                    {
                        EA.Diagram currentDiagram = repository.GetCurrentDiagram();
                        
                        lv.linkDialogueEntry.clientRoleName = referencedReference.ClientEnd.Role;
                        lv.linkDialogueEntry.supplierRoleName = referencedReference.SupplierEnd.Role;
                        lv.saveTreeToEATaggedValue(true);


                        if (currentDiagram != null)
                        {
                            repository.SaveDiagram(currentDiagram.DiagramID);
                            repository.ReloadDiagram(currentDiagram.DiagramID);
                        }

                    }
                }
            }
            else
            {
                if (i == 0)
                {
                    if (eaConnector.Stereotype == SDMModelingMain.LinkVariableStereotype)
                    {
                        lv = new LinkVariable(eaConnector, repository);
                    }
                    else if (eaConnector.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
                    {
                        lv = new TGGLinkVariable(eaConnector, repository);
                    }
                    LinkVariablePropertiesForm lvForm = new LinkVariablePropertiesForm(lv, repository, true);
                }
            }
        }

        
        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            if (errorMessage == "The rolename(s) of the LinkVariable differs from the related EReference")
            {
                messages.Add("Correct the rolename(s) of the LinkVariable");
            }
            messages.Add("Update linkVariable manually");
            return messages;
        }

        public override int getSortIndex()
        {
            return -100;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.Consistency.RuleHandling;
using System.Collections;
using System.Threading;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.PropertyFile;
using EAEcoreAddin.Persistency;
using System.Windows.Forms;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Consistency
{
    public class ConsistencyModule
    {
        public RuleControl RuleControl { get; set; }

        public Boolean LastCheckSuccessful { get; set; }
        public Boolean RunExportAfter { get; set; }

        public Boolean DoGlobalRules {get; set; }


        public Boolean Canceled
        {
            get
            {
                if (consistencyProgressbar != null)
                {
                    return consistencyProgressbar.Canceled;
                }
                return false;
            }
        }


        public static List<NewErrorOutput> RuleErrorOutputControls { get; set; }
        private Boolean fullValidation;

        private Boolean singleRuleCheck;

        static Thread exportBarProgessThread;
        static ConsistencyProgressBar consistencyProgressbar;

        public ConsistencyModule()
        {
            this.RuleControl = new RuleControl(this);
            RuleErrorOutputControls = new List<NewErrorOutput>();
        }
        public void initializeRules()
        {
            RuleControl.registerRules();
        }

        public static void progessBarThreadMethod()
        {
            consistencyProgressbar = new ConsistencyProgressBar();
            consistencyProgressbar.ShowDialog();
        }

        private void startValidation(EA.ObjectType ot, Object eaObject, SQLRepository repository, Boolean singleRulecheck, Boolean validateAll)
        {
            this.fullValidation = validateAll;
            this.singleRuleCheck = singleRulecheck;

            

            switch (ot)
            {
                //do rules for elements and their attributes/methods
                case EA.ObjectType.otElement:
                    validateEAElement(eaObject, repository, singleRulecheck);
                    break;
                //do rules for connectors
                case EA.ObjectType.otConnector:
                    SQLConnector connector = eaObject as SQLConnector;
                    RuleControl.deleteRuleResults(EA.ObjectType.otConnector, connector.ConnectorID);
                    RuleControl.doRules(connector, repository);
                    break;
                //do rules for attributes
                case EA.ObjectType.otAttribute:
                    SQLAttribute attribute = eaObject as SQLAttribute;
                    RuleControl.deleteRuleResults(EA.ObjectType.otAttribute, attribute.AttributeID);
                    RuleControl.doRules(attribute, repository);
                    break;
                //do rules for methods
                case EA.ObjectType.otMethod:
                    SQLMethod method = eaObject as SQLMethod;
                    RuleControl.deleteRuleResults(EA.ObjectType.otMethod, method.MethodID);
                    RuleControl.doRules(method, repository);
                    break;
                //do rules for packages  
                case EA.ObjectType.otPackage:
                    validatePackage(eaObject, repository, singleRulecheck, validateAll);                   
                    break;

                case EA.ObjectType.otDiagram:
                    SQLDiagram diagram = eaObject as SQLDiagram;
                    RuleControl.deleteRuleResults(EA.ObjectType.otDiagram, diagram.DiagramID);
                    RuleControl.doRules(diagram, repository);
                    break;
            }
            

            if(DoGlobalRules)
                RuleControl.doGlobalRules(repository);

            endValidation(repository);
        }

        private void validatePackage(Object eaObject, SQLRepository repository, Boolean singleRulecheck, Boolean validateAll)
        {
            SQLPackage package = eaObject as SQLPackage;

            //if a full rulecheck should be done
            if (!singleRulecheck)
            {
                List<SQLPackage> packagesToValidate = new List<SQLPackage>();
                //if package is a simple package check all elements within the package
                if (!validateAll)
                {
                    SQLPackage modelPackage = package;

                    //root node is selected
                    if (modelPackage.ParentID == 0)
                    {
                        foreach (SQLPackage pkg in modelPackage.Packages)
                        {
                            SQLTaggedValue validated = EAEcoreAddin.Util.EAUtil.findTaggedValue(pkg, MetamodelHelper.MoflonValidatedTaggedValueName);
                            if (validated == null || validated.Value != "true")
                                packagesToValidate.Add(pkg);
                        }
                    }
                    //simple package is selected
                    else
                    {
                        if (modelPackage.IsModel)
                        {
                            SQLTaggedValue validated = EAEcoreAddin.Util.EAUtil.findTaggedValue(modelPackage, MetamodelHelper.MoflonValidatedTaggedValueName);
                            if (validated == null || validated.Value != "true")
                                packagesToValidate.Add(modelPackage);
                        }
                        else
                        {
                            packagesToValidate.Add(modelPackage);
                        }
                    }
                }
                else
                {
                    foreach (SQLPackage root in repository.Models)
                    {
                        foreach (SQLPackage model in root.Packages)
                        {
                            SQLTaggedValue validated = EAEcoreAddin.Util.EAUtil.findTaggedValue(model, MetamodelHelper.MoflonValidatedTaggedValueName);
                            if (validated == null || !validated.Value.ToLower().Contains("true") || validated.Value == EPackageHelper.DEFAULT_VALUE_PLACEHOLDER)
                            {
                                packagesToValidate.Add(model);
                            }
                        }
                    }
                }
                doRulesForPackages(packagesToValidate, repository);
            }
            //single rulecheck for package
            else
            {
                RuleControl.deleteRuleResults(EA.ObjectType.otPackage, package.PackageID);
                RuleControl.doRules(package, repository);
            }
        }

        private void validateEAElement(Object eaObject, SQLRepository repository, Boolean singleRulecheck)
        {
            SQLElement element = eaObject as SQLElement;

            //only do rules for Element, Attributes and Methods
            if (singleRulecheck)
            {
                foreach (SQLAttribute actAttribute in element.Attributes)
                {
                    RuleControl.deleteRuleResults(EA.ObjectType.otAttribute, actAttribute.AttributeID);
                    RuleControl.doRules(actAttribute, repository);
                }
                foreach (SQLMethod actMethod in element.Methods)
                {
                    RuleControl.deleteRuleResults(EA.ObjectType.otMethod, actMethod.MethodID);
                    RuleControl.doRules(actMethod, repository);
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otElement, element.ElementID);
                RuleControl.doRules(element, repository);
            }
            else
            {
                List<SQLElement> listOfElements = new List<SQLElement>();
                List<SQLConnector> listOfConnectors = new List<SQLConnector>();
                List<SQLAttribute> listOfAttributes = new List<SQLAttribute>();
                List<SQLMethod> listofMethods = new List<SQLMethod>();
                List<SQLParameter> listofParameters = new List<SQLParameter>();
                List<SQLPackage> listOfPackages = new List<SQLPackage>();
                addElementChildFeatures(listOfElements, listOfConnectors, listOfAttributes, listofMethods, listofParameters, element);
                doRulesForFeatures(repository, listOfElements, listOfConnectors, listOfAttributes, listofMethods, listofParameters, listOfPackages);
            }
        }

        public void resetConsistencyModule(EA.Repository repository)
        {
            consistencyProgressbar = null;
            RuleControl.clearRuleResults();

            SQLRepository sqlRep = new SQLRepository(repository, false);

            foreach (NewErrorOutput rEo in RuleErrorOutputControls)
            {
                rEo.clearOutput();
                rEo.setClassMembers(new SQLRepository(repository, false), this.RuleControl);
            }
        }

        public void refreshOutputs(SQLRepository repo)
        {
            foreach (NewErrorOutput rEo in RuleErrorOutputControls)
            {
                rEo.clearOutput();
                rEo.setClassMembers(repo, this.RuleControl);
                rEo.writeOutput();
            }
        }

        

        public void endValidation(SQLRepository repository)
        {
            if (consistencyProgressbar != null)
            {
                while (!consistencyProgressbar.IsHandleCreated)
                {
                }
                consistencyProgressbar.invokeProgressBarClose();
            } 
            foreach (NewErrorOutput rEo in RuleErrorOutputControls)
            {
                rEo.setClassMembers(repository, this.RuleControl);
            }
            foreach (NewErrorOutput rEo in RuleErrorOutputControls)
            {
                rEo.writeOutput();
            }

            consistencyProgressbar = null;
        }


        #region Dispatcher Methods


        public void dispatchSingleObject(EA.Repository repository, String GUID, EA.ObjectType ot, Boolean includingChildren)
        {
            DoGlobalRules = false;

            SQLRepository sqlRepository = new SQLRepository(repository, false);
            Object eaObject = computeObject(GUID, ot, sqlRepository);

            if (eaObject != null)
            {
                if (includingChildren)
                {
                    RuleControl.ruleExecutionPoint = RuleExecutionPoint.OnRequest;
                    initializeProgressBar();
                    startValidation(ot, eaObject, sqlRepository, false, false);
                }
                else
                {
                    this.RuleControl.setRuleExecutionPoint(RuleExecutionPoint.Immediately);
                    startValidation(ot, eaObject, sqlRepository, true, false);
                }
            }

            RunExportAfter = false;
        }

        private static void initializeProgressBar()
        {
            exportBarProgessThread = new Thread(new ThreadStart(progessBarThreadMethod));
            exportBarProgessThread.Start();
        }

        private static Object computeObject(String GUID, EA.ObjectType ot, SQLRepository sqlRepository)
        {
            Object eaObject = null;
            switch (ot)
            {
                case EA.ObjectType.otElement:
                    eaObject = sqlRepository.GetElementByGuid(GUID);
                    break;
                case EA.ObjectType.otPackage:
                    eaObject = sqlRepository.GetPackageByGuid(GUID);
                    break;
                case EA.ObjectType.otMethod:
                    eaObject = sqlRepository.GetMethodByGuid(GUID);
                    break;
                case EA.ObjectType.otAttribute:
                    eaObject = sqlRepository.GetAttributeByGuid(GUID);
                    break;
                case EA.ObjectType.otDiagram:
                    eaObject = sqlRepository.GetDiagramByGuid(GUID);
                    break;
                case EA.ObjectType.otConnector:
                    eaObject = sqlRepository.GetConnectorByGuid(GUID);
                    break;
            }
            return eaObject;
        }

        public void dispatchSingleObject(EA.Repository repository, String GUID, EA.ObjectType ot)
        {
            dispatchSingleObject(repository, GUID, ot, false);
        }

        public void dispatchSelectionRulecheck(ref SQLRepository sqlRepository)
        {
            RuleControl.ruleExecutionPoint = RuleExecutionPoint.OnRequest;
            DoGlobalRules = false;

            EA.Diagram currentDiagram = sqlRepository.GetCurrentDiagram();
            SQLPackage selectedPackage = sqlRepository.GetTreeSelectedPackage();


            if (currentDiagram != null && currentDiagram.SelectedObjects.Count > 0)
            {
                foreach (EA.DiagramObject selectedDiagramObject in currentDiagram.SelectedObjects)
                {
                    initializeProgressBar();
                    SQLElement selectdElement = sqlRepository.GetElementByID(selectedDiagramObject.ElementID);
                    startValidation(EA.ObjectType.otElement, selectdElement, sqlRepository, false, false);
                }
                RunExportAfter = false;
                return;

            }
            if (sqlRepository.GetTreeSelectedElements().Count > 0)
            {
                foreach (EA.Element selectedElement in sqlRepository.GetTreeSelectedElements())
                {
                    initializeProgressBar();
                    startValidation(EA.ObjectType.otElement, sqlRepository.GetElementByID(selectedElement.ElementID), sqlRepository, false, false);
                }
                RunExportAfter = false;
                return;
            }
            if (selectedPackage != null)
            {               
                initializeProgressBar();
                startValidation(EA.ObjectType.otPackage, sqlRepository.GetPackageByID(selectedPackage.PackageID), sqlRepository, false, false);
            }

        }

        public void dispatchFullRulecheck(ref SQLRepository sqlRepository, Boolean validateEverything)
        {
            this.RuleControl.setRuleExecutionPoint(RuleExecutionPoint.OnRequest);

            DoGlobalRules = true;
            RunExportAfter = true;

            sqlRepository = new SQLRepository(sqlRepository.GetOriginalRepository(), true);


            resetConsistencyModule(sqlRepository.GetOriginalRepository());

            exportBarProgessThread = new Thread(new ThreadStart(progessBarThreadMethod));
            exportBarProgessThread.Start();

            SQLPackage selectedPackage = sqlRepository.GetTreeSelectedPackage();
            SQLPackage packageToCheck = sqlRepository.GetPackageByID(selectedPackage.PackageID);

            while (packageToCheck.ParentID != 0 && !packageToCheck.IsModel)
            {
                packageToCheck = sqlRepository.GetPackageByID(packageToCheck.ParentID);
            }
           

            startValidation(EA.ObjectType.otPackage, packageToCheck, sqlRepository, false, validateEverything);   
        }

        public static RuleResult propertyEntryToRuleResult(PropertyEntry actPropertyEntry)
        {
            RuleResult eclipseRuleResult = new RuleResult();
            eclipseRuleResult.EaObject = actPropertyEntry.MoflonObject.EAObject;
            eclipseRuleResult.NameOfObject = actPropertyEntry.MoflonObject.NameOfObject;
            eclipseRuleResult.TypeOfObject = actPropertyEntry.MoflonObject.TypeOfObject;
            eclipseRuleResult.ErrorLevel = RuleErrorLevel.Eclipse;
            eclipseRuleResult.ErrorOutput = actPropertyEntry.MessageLine.Value;
            return eclipseRuleResult;
        }

        #endregion

        

        public void doRulesForPackages(List<SQLPackage> packagesToValidate, SQLRepository repository) 
        {
            if (packagesToValidate.Count > 0)
            {

                List<SQLElement> listOfElements = new List<SQLElement>();
                List<SQLConnector> listOfConnectors = new List<SQLConnector>();
                List<SQLAttribute> listOfAttributes = new List<SQLAttribute>();
                List<SQLMethod> listofMethods = new List<SQLMethod>();
                List<SQLParameter> listofParameters = new List<SQLParameter>();
                List<SQLPackage> listOfPackages = new List<SQLPackage>();
                listOfPackages.AddRange(packagesToValidate);
                int z = listOfPackages.Count;

                //wait for progressbar to be initialized
                while (consistencyProgressbar == null)
                {
                }
                consistencyProgressbar.invokeProgressBarAndPrepare();
                for (int i = 0; i < z; i++)
                {
                    if (Canceled)
                    {
                        return;
                    }
                    SQLPackage actPackage = (SQLPackage)listOfPackages[i];
                    SQLPackage currentMetamodel = EAUtil.getOutermostPackage(actPackage, repository);

                    foreach (SQLElement actElement in actPackage.Elements)
                    {
                        if (Canceled)
                        {
                            return;
                        }
                        addElementChildFeatures(listOfElements, listOfConnectors, listOfAttributes, listofMethods, listofParameters, actElement);
                    }
                    foreach (SQLPackage actpackage in actPackage.Packages)
                    {
                        if (Canceled)
                        {
                            return;
                        }
                        listOfPackages.Add(actpackage);
                        z++;
                    }
                }
                doRulesForFeatures(repository, listOfElements, listOfConnectors, listOfAttributes, listofMethods, listofParameters, listOfPackages);
            }
        }

        private void doRulesForFeatures(SQLRepository repository, List<SQLElement> listOfElements, List<SQLConnector> listOfConnectors, List<SQLAttribute> listOfAttributes, List<SQLMethod> listofMethods, List<SQLParameter> listofParameters, List<SQLPackage> listOfPackages)
        {
            List<SQLElement> toDelete = new List<SQLElement>();

            foreach (SQLElement elem in listOfElements)
            {
                if (elem.Type == "Constraint")
                {
                    toDelete.Add(elem);
                }
            }

            foreach (SQLElement del in toDelete)
            {
                listOfElements.Remove(del);
            }


            String result = repository.SQLQuery("select a.* from t_object a, t_package b, t_diagramobjects c where a.Object_Type = 'Constraint' AND a.Package_ID = b.Package_ID and b.Name = 'Rules' AND c.Object_ID = a.Object_ID");
            //String result = repository.SQLQuery("select * from t_object where Object_Type = 'Constraint' AND Diagram_ID <> 0");
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    listOfElements.Add(new SQLElement(repository, row));
                }
            }

            consistencyProgressbar.invokeProgressBarSetBarSize(6);

            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otElement);
            foreach (SQLElement element in listOfElements)
            {
                if (Canceled)
                {
                    return;
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otElement, element.ElementID);
                RuleControl.doRules(element, repository);
            }
            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otConnector);
            foreach (SQLConnector connector in listOfConnectors)
            {
                if (Canceled)
                {
                    return;
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otConnector, connector.ConnectorID);
                RuleControl.doRules(connector, repository);
            }
            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otMethod);
            foreach (SQLMethod method in listofMethods)
            {
                if (Canceled)
                {
                    return;
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otMethod, method.MethodID);
                RuleControl.doRules(method, repository);
            }

            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otParameter);
            foreach (SQLParameter parameter in listofParameters)
            {

            }

            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otAttribute);
            foreach (SQLAttribute attribute in listOfAttributes)
            {
                if (Canceled)
                {
                    return;
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otAttribute, attribute.AttributeID);
                RuleControl.doRules(attribute, repository);
            }
            consistencyProgressbar.invokeProgressBarAndPerformNext(EA.ObjectType.otPackage);
            foreach (SQLPackage actpackage in listOfPackages)
            {
                if (Canceled)
                {
                    return;
                }
                RuleControl.deleteRuleResults(EA.ObjectType.otPackage, actpackage.PackageID);
                RuleControl.doRules(actpackage, repository);
            }
        }

        private static void addElementChildFeatures(List<SQLElement> listOfElements, List<SQLConnector> listOfConnectors, List<SQLAttribute> listOfAttributes, List<SQLMethod> listofMethods, List<SQLParameter> listofParameters, SQLElement elem)
        {

            if (elem != null)
            {
                foreach (SQLAttribute attribute in elem.Attributes)
                {
                    listOfAttributes.Add(attribute);
                }
                foreach (SQLMethod method in elem.Methods)
                {
                    listofMethods.Add(method);
                    foreach (SQLParameter param in method.Parameters)
                    {
                        listofParameters.Add(param);
                    }
                }
                foreach (SQLConnector connector in elem.Connectors)
                {
                    if (connector.ClientID == elem.ElementID)
                    {
                        listOfConnectors.Add(connector);
                    }
                }
                foreach (SQLElement sdmContainerOrTGGOv in elem.Elements)
                {

                    foreach (SQLConnector tggLv in sdmContainerOrTGGOv.Connectors)
                    {
                        if (tggLv.ClientID == sdmContainerOrTGGOv.ElementID)
                        {
                            listOfConnectors.Add(tggLv);
                        }
                    }
                    listOfElements.Add(sdmContainerOrTGGOv);
                    foreach (SQLElement activityNode in sdmContainerOrTGGOv.Elements)
                    {

                        listOfElements.Add(activityNode);
                        foreach (SQLConnector activityEdge in activityNode.Connectors)
                        {
                            if (activityEdge.ClientID == activityNode.ElementID)
                            {
                                listOfConnectors.Add(activityEdge);
                            }
                        }
                        foreach (SQLElement sdmOV in activityNode.Elements)
                        {
                            listOfElements.Add(sdmOV);
                            foreach (SQLConnector linkV in sdmOV.Connectors)
                            {
                                if (linkV.ClientID == sdmOV.ElementID)
                                {
                                    listOfConnectors.Add(linkV);
                                }
                            }
                        }
                    }
                }
                listOfElements.Add(elem);
            }            
        }



        #region Helper Methods

        
        

        

        

        #endregion

    }
}

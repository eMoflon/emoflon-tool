using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using System.Drawing;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    class TGGModelingUtil
    {
        public static DomainType stringToDomainType(String value)
        {
            try
            {
                DomainType domain = (DomainType)Enum.Parse(typeof(DomainType), value.ToUpper());
                return domain;
            }
            catch
            {
                return DomainType.CORRESPONDENCE;
            }
        }


        public static DomainType getDomainOfObjectVariable(SQLRepository repository, TGGObjectVariable tggOv)
        {

            return determineDomainUsingConnectivity(tggOv, repository, new HashSet<int>());
        }

        private static DomainType determineDomainSimple(SQLRepository repository, TGGObjectVariable tggOv)
        {
            if (tggOv.sqlElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                return DomainType.CORRESPONDENCE;

            EClass classifierElement = tggOv.Classifier;
            if (classifierElement != null)
            {

                SQLElement eClass = repository.GetElementByID(classifierElement.EaElement.ElementID);
                SQLPackage wantedDomainPkg = repository.GetPackageByID(eClass.PackageID);
                while (!wantedDomainPkg.IsModel)
                    wantedDomainPkg = repository.GetPackageByID(wantedDomainPkg.ParentID);

                SQLPackage tggPackage = repository.GetPackageByID(tggOv.sqlElement.PackageID);
                while (!tggPackage.IsModel)
                    tggPackage = repository.GetPackageByID(tggPackage.ParentID);

                TGG tgg = new TGG(repository, tggPackage);
                tgg.loadTreeFromTaggedValue();

                // source metamodel == target metamodel
                if (tgg.getDomain(DomainType.SOURCE).MetamodelGuid == tgg.getDomain(DomainType.TARGET).MetamodelGuid)
                {
                    return determineDomainUsingPrefixConvention(tggOv, repository);
                }

                if (wantedDomainPkg.PackageGUID == tgg.getDomain(DomainType.SOURCE).MetamodelGuid)
                {
                    return DomainType.SOURCE;
                }
                else if (wantedDomainPkg.PackageGUID == tgg.getDomain(DomainType.TARGET).MetamodelGuid)
                {
                    return DomainType.TARGET;
                }
            }

            return determineDomainUsingPrefixConvention(tggOv, repository);
        }

        private static DomainType determineDomainUsingPrefixConvention(TGGObjectVariable tggOv, SQLRepository repository)
        {

            if (tggOv.Name.StartsWith("src__"))
            {
                return DomainType.SOURCE;
            }
            else if (tggOv.Name.StartsWith("trg__"))
            {
                return DomainType.TARGET;
            }

            return DomainType.UNDEFINED;
        }

        private static DomainType determineDomainUsingConnectivity(TGGObjectVariable tggOv, SQLRepository repository, HashSet<int> alreadyVisited)
        {
            alreadyVisited.Add(tggOv.sqlElement.ElementID);

            DomainType result = DomainType.UNDEFINED;
            result = determineDomainSimple(repository, tggOv);
            if (result != DomainType.UNDEFINED)
                return result;

            result = determineDomainUsingDirectCorrespondence(tggOv, repository);
            if (result != DomainType.UNDEFINED)
                return result;

            int itsID = tggOv.sqlElement.ElementID;
            foreach (SQLConnector connector in tggOv.sqlElement.Connectors)
            {
                SQLElement client = repository.GetElementByID(connector.ClientID);
                SQLElement supplier = repository.GetElementByID(connector.SupplierID);
                SQLElement neighbour = client.ElementID == itsID ? supplier : client;

                if (!alreadyVisited.Contains(neighbour.ElementID) && neighbour.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                {
                    DomainType neigbourDomain = determineDomainUsingConnectivity(new TGGObjectVariable(neighbour, repository), repository, alreadyVisited);
                    if (neigbourDomain != DomainType.UNDEFINED)
                        return neigbourDomain;
                }

            }

            return DomainType.UNDEFINED;
        }

        private static DomainType determineDomainUsingDirectCorrespondence(TGGObjectVariable tggOv, SQLRepository repository)
        {
            int itsID = tggOv.sqlElement.ElementID;

            foreach (SQLConnector connector in tggOv.sqlElement.Connectors)
            {
                if (connector.ClientID == itsID && connector.ClientEnd.Role == "target")
                {
                    TGGObjectVariable potentialCorr = new TGGObjectVariable(repository.GetElementByID(connector.SupplierID), repository);
                    if (getDomainOfObjectVariable(repository, potentialCorr) == DomainType.CORRESPONDENCE)
                        return DomainType.TARGET;
                }

                else if (connector.SupplierID == itsID && connector.SupplierEnd.Role == "target")
                {
                    TGGObjectVariable potentialCorr = new TGGObjectVariable(repository.GetElementByID(connector.ClientID), repository);
                    if (getDomainOfObjectVariable(repository, potentialCorr) == DomainType.CORRESPONDENCE)
                        return DomainType.TARGET;
                }

                else if (connector.ClientID == itsID && connector.ClientEnd.Role == "source")
                {
                    TGGObjectVariable potentialCorr = new TGGObjectVariable(repository.GetElementByID(connector.SupplierID), repository);
                    if (getDomainOfObjectVariable(repository, potentialCorr) == DomainType.CORRESPONDENCE)
                        return DomainType.SOURCE;
                }

                else if (connector.SupplierID == itsID && connector.SupplierEnd.Role == "source")
                {
                    TGGObjectVariable potentialCorr = new TGGObjectVariable(repository.GetElementByID(connector.ClientID), repository);
                    if (getDomainOfObjectVariable(repository, potentialCorr) == DomainType.CORRESPONDENCE)
                        return DomainType.SOURCE;
                }
            }
            return DomainType.UNDEFINED;
        }

        public static DomainType getDomainOfLinkVariable(SQLRepository repository, SQLConnector tggLv)
        {
            TGGObjectVariable supplier = new TGGObjectVariable(repository.GetElementByID(tggLv.SupplierID), repository);
            TGGObjectVariable client = new TGGObjectVariable(repository.GetElementByID(tggLv.ClientID), repository);

            DomainType supplierDom = getDomainOfObjectVariable(repository, supplier);
            DomainType clientDom = getDomainOfObjectVariable(repository, client);

            if (!supplierDom.Equals(clientDom))
                return DomainType.CORRESPONDENCE;
            else
                return supplierDom;
        }


        public static DomainType getDomainOfEClass(SQLRepository repository, SQLElement eClass)
        {
            //Get the TGG Schema diagram
            EA.Diagram tggSchema = repository.GetCurrentDiagram();

            //Get the TGG Project
            SQLPackage tggProject = repository.GetPackageByID(tggSchema.PackageID);
            SQLPackage parentPackage = repository.GetPackageByID(tggProject.ParentID);
            while (parentPackage.ParentID != 0)
            {
                tggProject = parentPackage;
                parentPackage = repository.GetPackageByID(tggProject.ParentID);
            }

            TGG tgg = new TGG(repository, tggProject);
            tgg.loadTreeFromTaggedValue();

            String sourceDomainID = tgg.getDomain(DomainType.SOURCE).MetamodelGuid;
            String targetDomainID = tgg.getDomain(DomainType.TARGET).MetamodelGuid;

            SQLPackage currentPackage = repository.GetPackageByID(eClass.PackageID);
            SQLPackage parentOfCurrentPackage = repository.GetPackageByID(currentPackage.ParentID);

            while (parentOfCurrentPackage.ParentID != 0)
            {
                currentPackage = parentOfCurrentPackage;
                parentOfCurrentPackage = repository.GetPackageByID(parentOfCurrentPackage.ParentID);
            }

            SQLPackage modelOfTheEClass = currentPackage;

            Boolean isSource = sourceDomainID == modelOfTheEClass.PackageGUID;
            Boolean isTarget = targetDomainID == modelOfTheEClass.PackageGUID;

            if (isTarget && isSource)
                return DomainType.UNDEFINED;

            else if (isTarget)
                return DomainType.TARGET;

            else if (isSource)
                return DomainType.SOURCE;
            else
                return DomainType.UNDEFINED;

        }


        public static void assignmentsToConstraints(ObjectVariable ov, SQLRepository repository)
        {
            
            foreach (AttributeAssignment aAssignment in ov.AttributeAssignments)
            {
                Constraint constraint = new Constraint(repository);
                ComparisonExpression compExp = new ComparisonExpression(repository);
                compExp.Operator = ComparingOperator.EQUAL;
                compExp.LeftExpression = new AttributeValueExpression(repository, repository.GetAttributeByGuid(aAssignment.AttributeGUID), ov.sqlElement);
                compExp.RightExpression = aAssignment.ValueExpression;
                constraint.ConstraintExpression = compExp;
                ov.Constraints.Add(constraint);
            }
            ov.AttributeAssignments.Clear();

        }

        public static void constraintsToAssignments(ObjectVariable ov, SQLRepository repository)
        {

            foreach (Constraint constraint in ov.Constraints)
            {
                AttributeAssignment assignment = new AttributeAssignment(repository);
                assignment.ValueExpression = (constraint.ConstraintExpression as ComparisonExpression).RightExpression;

                AttributeValueExpression left = (constraint.ConstraintExpression as ComparisonExpression).LeftExpression as AttributeValueExpression;

                assignment.AttributeGUID = left.AttributeGUID;
                assignment.AttributeName = left.AttributeName;

                ov.AttributeAssignments.Add(assignment);
            }
            ov.Constraints.Clear();

        }


        public static void fillTGGSchemaDiagram(EA.Repository repository, EA.Package tggPackage, EA.Diagram tggDiagram)
        {
            int i = 2;
            int j = 1;

            List<int> validIds = new List<int>();
            List<int> addedIds = new List<int>();

            foreach (EA.Element element in tggPackage.Elements)
            {

                EA.DiagramObject diagramObject = tggDiagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
                diagramObject.ElementID = element.ElementID;
                diagramObject.left = i * 300;
                diagramObject.right = diagramObject.left + 250;
                diagramObject.top = j * -200;
                diagramObject.bottom = diagramObject.top - 100;
                diagramObject.Update();
                validIds.Add(element.ElementID);
                addedIds.Add(element.ElementID);


                int z = i - 1;
                foreach (EA.Connector con in element.Connectors)
                {
                    EA.Element otherEnd = null;
                    if (con.ClientID == element.ElementID)
                    {
                        otherEnd = repository.GetElementByID(con.SupplierID);
                    }
                    else
                    {
                        otherEnd = repository.GetElementByID(con.ClientID);
                    }
                    if (otherEnd.Stereotype == ECOREModeling.ECOREModelingMain.EClassStereotype )
                    {
                        if (!addedIds.Contains(otherEnd.ElementID))
                        {
                            diagramObject = tggDiagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
                            diagramObject.left = z * 300;
                            diagramObject.right = diagramObject.left + 200;
                            diagramObject.top = j * -200;
                            diagramObject.bottom = diagramObject.top - 100;
                            diagramObject.ElementID = otherEnd.ElementID;
                            diagramObject.Update();
                            addedIds.Add(otherEnd.ElementID);
                        }
                        z = z + 2;
                    }
                }

                j++;
            }
            tggDiagram.DiagramObjects.Refresh();
            tggDiagram.DiagramLinks.Refresh();

            repository.ReloadDiagram(tggDiagram.DiagramID);

            repository.Execute("UPDATE t_diagramlinks SET Hidden = true WHERE DiagramID = " + tggDiagram.DiagramID);

            foreach (EA.DiagramLink links in tggDiagram.DiagramLinks)
            {
                EA.Connector con = repository.GetConnectorByID(links.ConnectorID);
                if(!validIds.Contains(con.ClientID) && !validIds.Contains(con.SupplierID)) 
                {
                    links.IsHidden = true;
                    links.Update();
                }
            }
        }




        internal static void fillTGGRuleDiagram(EA.Repository repository, EA.Diagram diagram, EA.Element ruleElement)
        {
            SQLRepository sqlRep = new SQLRepository(repository, false);

            List<int> usedY = new List<int>();
            List<int> usedElementIds = new List<int>();
            List<Point> usedPoints = new List<Point>();
            foreach (EA.Element child in ruleElement.Elements)
            {
                if (child.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                {
                    printTggCorrespondenceObjectTree(child, diagram, repository, usedY, usedPoints, usedElementIds);
                }
                else if (child.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                {
                    printTGGOvObject(sqlRep.GetElementByID(child.ElementID), diagram, repository, usedPoints);
                }
                else if (child.Stereotype == TGGModelingMain.CSPConstraintStereotype)
                {
                    printCSPConstraintObject(child, diagram, repository, usedPoints);
                
                }
                

              
            }
        }

        private static void printCSPConstraintObject(EA.Element child, EA.Diagram diagram, EA.Repository repository, List<Point> usedPoints)
        {
            SQLRepository sqlRep = new SQLRepository(repository, false);
            TGGObjectVariable ov = new TGGObjectVariable( sqlRep.GetElementByID(child.ElementID), sqlRep);
            ov.loadTreeFromTaggedValue();

            int x = 4;
            int y = 1;


            while (usedPoints.Contains(new Point(x, y)))
            {
                y++;
            }

            EA.DiagramObject diagObject = diagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
            diagObject.ElementID = child.ElementID;
            diagObject.left = x * 200;
            diagObject.right = diagObject.left + 125;
            diagObject.top = y * -100;
            diagObject.bottom = diagObject.top - 50;
            diagObject.Update();
            usedPoints.Add(new Point(x, y));
        }

        private static void printTGGOvObject(SQLElement child, EA.Diagram diagram, EA.Repository repository, List<Point> usedPoints)
        {
            TGGObjectVariable ov = new TGGObjectVariable(child, new SQLRepository(repository, false));
            ov.loadTreeFromTaggedValue();

            int x = 1;
            int y = 1;
            if (ov.domain == DomainType.TARGET)
            {
                x = 3;
            }

            while (usedPoints.Contains(new Point(x, y)))
            {
                y++;
            }

            EA.DiagramObject diagObject = diagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
            diagObject.ElementID = child.ElementID;
            diagObject.left = x * 200;
            diagObject.right = diagObject.left + 125;
            diagObject.top = y * -100;
            diagObject.bottom = diagObject.top - 50;
            diagObject.Update();
            usedPoints.Add(new Point(x, y));
        }

        private static void printTggCorrespondenceObjectTree(EA.Element child, EA.Diagram diagram, EA.Repository repository, List<int> usedY, List<Point> usedPoints, List<int> usedElementIds)
        {
            int x = 2;
            int y = 1;
            while (usedY.Contains(y))
            {
                y++;
            }

            EA.DiagramObject diagObject = diagram.DiagramObjects.AddNew("","") as EA.DiagramObject;
            diagObject.ElementID = child.ElementID;
            diagObject.left = x * 200;
            diagObject.right = diagObject.left + 125;
            diagObject.top = y * -100;
            diagObject.bottom = diagObject.top - 50;
            diagObject.Update();
            usedY.Add(y);
            usedPoints.Add(new Point(x, y));
            


        }


        

        
    }
}

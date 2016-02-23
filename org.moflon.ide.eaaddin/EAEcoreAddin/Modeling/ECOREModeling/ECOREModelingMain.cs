using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Refactoring;

namespace EAEcoreAddin.Modeling.ECOREModeling
{
    class ECOREModelingMain
    {
        public static readonly String EClassStereotype = "EClass";
        public static readonly String EDatatypeStereotype = "EDatatype";
        public static readonly String EAttributeStereotype = "EAttribute";
        public static readonly String EOperationStereotype = "EOperation";
        public static readonly String EEnumStereotype = "EEnum";
        public static readonly String EPackageStereotype = "EPackage";
        public static readonly String EClassStereotypeOld = "eclass";
        public static readonly String EDatatypeStereotypeOld = "edatatype";
        public static readonly String EPackageStereotypeOld = "epackage";
        public static readonly String InheritanceConnectorType = "Generalization";
        public static readonly String EReferenceConnectorType = "Association";
        public static readonly String[] EcoreDiagramMetatype = { "eMoflon Ecore Diagrams::Ecore Diagram",
                                                                 "Ecore Diagram::Ecore Diagram" };

        public Boolean EA_OnPostNewConnector(SQLRepository sqlRepository, EA.Connector actCon, EA.Diagram currentDiagram)
        {

            if (ECOREModelingMain.EcoreDiagramMetatype.Contains(currentDiagram.MetaType) || TGGModelingMain.TggSchemaDiagramMetatype.Contains(currentDiagram.MetaType))
            {
                if (actCon.Type.Equals(ECOREModelingMain.EReferenceConnectorType))
                {
                    if (actCon.Stereotype == "bi")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.ClientEnd, actCon.ClientID, "0..1");
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..1");

                        configureBx(actCon);
                    }
                    if (actCon.Stereotype == "biComposite")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.ClientEnd, actCon.ClientID, "0..1");
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..*");

                        configureBx(actCon);
                        toComposite(actCon);
                    }
                    else if (actCon.Stereotype == "biCompositeSingle")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.ClientEnd, actCon.ClientID, "0..1");
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..1");

                        configureBx(actCon);
                        toComposite(actCon);
                    }
                    else if (actCon.Stereotype == "uniSingle")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..1");

                        configureUni(actCon);
                    }
                    else if (actCon.Stereotype == "uniMultiple")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..*");

                        configureUni(actCon);
                    }
                    else if (actCon.Stereotype == "uniComposite")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..*");

                        configureUni(actCon);
                        toComposite(actCon); 
                    }
                    else if (actCon.Stereotype == "uniCompositeSingle")
                    {
                        EAUtil.configureConnectorEnd(sqlRepository, actCon.SupplierEnd, actCon.SupplierID, "0..1");

                        configureUni(actCon);
                        toComposite(actCon);
                    }
                }
            }
            return true;
        }

        private static void configureUni(EA.Connector actCon)
        {
            actCon.StereotypeEx = "";
            actCon.Direction = "Source -> Destination";
            actCon.Update();
        }

        private static void configureBx(EA.Connector actCon)
        {
            actCon.StereotypeEx = "";
            actCon.Direction = "Bi-Directional";
            actCon.Update();
        }

        private static void toComposite(EA.Connector actCon)
        {
            actCon.ClientEnd.Aggregation = 2;
            actCon.ClientEnd.Update();
        }

        public void EA_OnPostNewDiagram(EA.Repository Repository, EA.EventProperties Info)
        {
            SQLRepository sqlRep = new SQLRepository(Repository, false);
            EA.Diagram diagram = Repository.GetDiagramByID(int.Parse((string)Info.Get(0).Value));
            if (ECOREModelingMain.EcoreDiagramMetatype.Contains(diagram.MetaType))
            {
                EA.Package package = Repository.GetPackageByID(diagram.PackageID);
                if (package.Diagrams.Count == 1 && (package.StereotypeEx == "" || package.StereotypeEx == "EPackage"))
                {
                    EPackage epackage = new EPackage(sqlRep.GetPackageByID(package.PackageID), sqlRep);
                    epackage.saveTreeToEATaggedValue(true);
                }
            }

        }

        public bool EA_OnPostNewElement(SQLRepository sqlRepository, EA.Element newElement2, EA.Diagram currentDiagram)
        {

            SQLElement newElement = sqlRepository.GetElementByID(newElement2.ElementID);

            if (ECOREModelingMain.EcoreDiagramMetatype.Contains(currentDiagram.MetaType) || TGGModelingMain.TggSchemaDiagramMetatype.Contains(currentDiagram.MetaType))
            {

                if (newElement.Stereotype.ToLower() == EClassStereotype.ToLower())
                {
                    EClass eClass = new EClass(newElement, sqlRepository);
                    eClass.saveTreeToEATaggedValue(false);
                    return true;
                }
                else if (newElement.Stereotype.ToLower() == EDatatypeStereotype.ToLower())
                {
                    EDatatype eDataType = new EDatatype(newElement, sqlRepository);
                    eDataType.saveTreeToEATaggedValue(false);
                    return true;
                }
                else if (newElement.Stereotype.ToLower() == EEnumStereotype.ToLower())
                {
                    EEnum eEnum = new EEnum(newElement, sqlRepository);
                    eEnum.saveTreeToEATaggedValue(false);
                    return true;
                }
            }
            return false;
            
        }

        public void EA_OnNotifyContextItemModified(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {

            SQLRepository sqlRepository = new SQLRepository(Repository, false);
            if (ot == EA.ObjectType.otPackage)
            {
                EA.Package eaPackage = Repository.GetPackageByGuid(GUID);
                if (eaPackage.Element.Stereotype.ToLower() == ECOREModelingMain.EPackageStereotype.ToLower())
                {
                    EPackage ePackage = new EPackage(sqlRepository.GetPackageByID(eaPackage.PackageID), sqlRepository);
                    Main.addToTreeQueue(GUID, ePackage);
                }

                // track changes for metamodelevolution
                savePackageChangesToEATaggedValue(sqlRepository, GUID);
            }
            if (ot == EA.ObjectType.otElement)
            {
                SQLElement eaElement = sqlRepository.GetElementByGuid(GUID);
                
                if (eaElement.Stereotype.ToLower() == EClassStereotype.ToLower() && eaElement.Type == Main.EAClassType)
                {
                    EClass eClass = new EClass(eaElement, sqlRepository);
                    eClass.saveTreeToEATaggedValue(false);

                    // track changes for metamodelevolution
                    saveElementChangesToEATaggedValue(eaElement, GUID, sqlRepository);
                }
                else if (eaElement.Stereotype.ToLower() == EDatatypeStereotype.ToLower())
                {
                    EDatatype eDataType = new EDatatype(eaElement, sqlRepository);
                    eDataType.saveTreeToEATaggedValue(false);
                }
                else if (eaElement.Stereotype.ToLower() == EEnumStereotype.ToLower())
                {
                    EEnum eEnum = new EEnum(eaElement, sqlRepository);
                    eEnum.saveTreeToEATaggedValue(false);
                }
            }

            if (ot == EA.ObjectType.otConnector)
            {
                SQLConnector eaConnector = sqlRepository.GetConnectorByGuid(GUID);
                if (eaConnector.Type == EReferenceConnectorType && (eaConnector.Stereotype == "" || eaConnector.Stereotype.ToLower() == "ereference"))
                {
                    EReference clientReference = new EReference(eaConnector, sqlRepository);
                    clientReference.saveTreeToEATaggedValue(false);
                }
                else if (eaConnector.Type == InheritanceConnectorType)
                {
                    SQLElement connectorSource = sqlRepository.GetElementByID(eaConnector.ClientID);
                    if (connectorSource.Stereotype == EClassStereotype)
                    {
                        EClass eClass = new EClass(connectorSource, sqlRepository);
                        eClass.saveTreeToEATaggedValue(false);
                    }
                }
            }
        }

        private String addPackageName(EPackage epackage, String path, SQLRepository repository)
        {
            if (path.Equals(""))
            {
                path = epackage.Name;
            }
            else {
                path = epackage.Name + "." + path;
            }
            int parentID = epackage.EaPackage.ParentID;
            SQLPackage parent = repository.GetPackageByID(parentID);  
         
            if (parent.IsModel)
            {
                EPackage parentEPackage = new EPackage(parent, repository);
                return addPackageName(parentEPackage, path, repository);
            }
            return path;
        }

        private void saveElementChangesToEATaggedValue(SQLElement eaElement,  String GUID, SQLRepository sqlRepository)
        {
            SQLPackage package = sqlRepository.GetPackageByID(eaElement.PackageID);
            EPackage epackage = new EPackage(package, sqlRepository);
           // String packages = addPackageName(epackage, "", sqlRepository);

            String previousName = eaElement.Name;

            SQLTaggedValue exportTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaElement, Main.MoflonExportTreeTaggedValueName);
            /*if (exportTreeTag != null)
            {
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(exportTreeTag.Notes);
                MocaNode eClassAttributes = eClassMocaNode.Children[1];
                eClassAttributes.getAttribute()

                previousName = eClassMocaNode.getAttribute("previousName").Value;
            }*/

            SQLTaggedValue changesTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaElement, Main.MoflonChangesTreeTaggedValueName);
            if (changesTreeTag != null)
            {
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);
                previousName = eClassMocaNode.getAttribute("previousName").Value;
            }
            CachedClass temp = new CachedClass();
            temp.getElement(GUID, sqlRepository);
            temp.name = eaElement.Name;
            temp.previousName = previousName;
            temp.packageName = addPackageName(epackage, "", sqlRepository);
            temp.projectName = getTopLevelPackageName(package, sqlRepository);
            temp.saveElementToEATaggedValue(true);
        }
        private void savePackageChangesToEATaggedValue(SQLRepository sqlRepository, String GUID) 
        {
            SQLPackage sqlPackage = sqlRepository.GetPackageByGuid(GUID);
            EPackage epackage = new EPackage(sqlPackage, sqlRepository);
            String packages = addPackageName(epackage, "", sqlRepository);

            String previousName = sqlPackage.Name;

            SQLTaggedValue changesTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sqlPackage, Main.MoflonChangesTreeTaggedValueName);
            if (changesTreeTag != null)
            {
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);
                previousName = eClassMocaNode.getAttribute("previousName").Value;
            }

            CachedPackage temp = new CachedPackage();
            temp.getPackage(GUID, sqlRepository);
            temp.name = sqlPackage.Name;
            temp.previousName = previousName;
            temp.packageName = packages;
            temp.projectName = getTopLevelPackageName(sqlPackage, sqlRepository);
            temp.savePackageToEATaggedValue(true);
        }

        private String getTopLevelPackageName(SQLPackage sqlPackage, SQLRepository repository)
        {
            int parentID = sqlPackage.ParentID;
            SQLPackage parent = repository.GetPackageByID(parentID);

            if (parent.IsModel)
            {
                return getTopLevelPackageName(parent, repository);
            }
            return sqlPackage.Name;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Import.Gui;

namespace EAEcoreAddin.Import
{
    public class EcoreImport
    {
        EA.Repository repository;
        SQLRepository sqlRep;

        public EcoreImport(SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.repository = sqlRep.GetOriginalRepository();
        }

        public void importEPackageModel(MocaNode ePackageNode)
        {
            EA.Package rootPackage = findOrCreateRoot(ePackageNode);

            EA.Package modelPackage = getOrCreatePackage( sqlRep.GetPackageByID(rootPackage.PackageID), ePackageNode);
            
            EPackage ePkg = new EPackage(sqlRep.GetPackageByID(modelPackage.PackageID), sqlRep);
            ePkg.deserializeFromMocaTree(ePackageNode);
            
            MainImport.getInstance().MocaTaggableElements.Add(ePkg);
            MainImport.getInstance().OldGuidToNewGuid.Add(ePkg.Guid, modelPackage.PackageGUID);
            MainImport.getInstance().importedPackages.Add(modelPackage);

            appendDiagram(modelPackage, "eMoflon Ecore Diagrams::Ecore Diagram", repository);//ECOREModelingMain.EcoreDiagramMetatype[0]);

            importEPackageFeatures(ePackageNode, sqlRep.GetPackageByID(modelPackage.PackageID));
        }

        
        
        public void importEPackageFeatures(MocaNode ePackageNode, SQLPackage modelPackage)
        {
            foreach (MocaNode eclassNode in ePackageNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName).Children)
            {
                if (eclassNode.Name == ECOREModelingMain.EClassStereotype)
                {
                    importEClass(modelPackage, eclassNode);
                }
                else if (eclassNode.Name == ECOREModelingMain.EDatatypeStereotype)
                {
                    importEDatatype(modelPackage, eclassNode);
                }
                else if (eclassNode.Name == ECOREModelingMain.EEnumStereotype)
                {
                    importEEnum(modelPackage, eclassNode);
                }
            }

            foreach (MocaNode packageNode in ePackageNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName).Children)
            {
                if (packageNode.Name == ECOREModelingMain.EPackageStereotype)
                {
                    importEPackage(packageNode, modelPackage);
                }
            }
        }

        public EPackage importEPackage(MocaNode ePackageNode, SQLPackage parentPackage)
        {
            EA.Package modelPackage = getOrCreatePackage(parentPackage, ePackageNode);

            EPackage ePkg = new EPackage(sqlRep.GetPackageByID(modelPackage.PackageID), sqlRep);
            ePkg.deserializeFromMocaTree(ePackageNode);
           
            MainImport.getInstance().MocaTaggableElements.Add(ePkg);
            MainImport.getInstance().OldGuidToNewGuid.Add(ePkg.Guid, modelPackage.PackageGUID);

            appendDiagram(modelPackage, "eMoflon Ecore Diagrams::Ecore Diagram", repository);// ECOREModelingMain.EcoreDiagramMetatype[0]);

            importEPackageFeatures(ePackageNode, sqlRep.GetPackageByID(modelPackage.PackageID));

            return ePkg;
        }

        public void importERef(SQLElement parent, MocaNode referencesNode)
        {

            foreach (MocaNode refNode in referencesNode.Children)
            {
                String refGuid = refNode.getAttributeOrCreate(Main.GuidStringName).Value;

                String oppositeGuid = refNode.getAttributeOrCreate("oppositeGuid").Value;

                bool containment = refNode.getAttributeOrCreate("containment").Value == "true";
                bool hasOpposite = oppositeGuid != "";

                SQLElement client = parent;
                String supGuid = refNode.getAttributeOrCreate("typeGuid").Value;
                
                EA.Connector con = null;
                if (MainImport.getInstance().ElementGuidToElement.ContainsKey(supGuid))
                {
                    EA.Element supplier = MainImport.getInstance().ElementGuidToElement[supGuid];
                    if (hasOpposite)
                    {
                        
                        if (!oppositeGuid.EndsWith("Client") && !oppositeGuid.EndsWith("Supplier"))
                        {
                            
                        }
                        if (MainImport.getInstance().ReferenceGuidToReference.ContainsKey(oppositeGuid))
                        {
                            con = MainImport.getInstance().ReferenceGuidToReference[oppositeGuid];
                            refNode.getAttributeOrCreate(Main.GuidStringName).Value = "Client";
                        }
                        else
                        {
                            int supplierAggregation = 0;
                            int clientAggregation = 0;
                            if (containment)
                            {
                                if (oppositeGuid.EndsWith("Client"))
                                    clientAggregation = 2;
                                else
                                    supplierAggregation = 2;
                            }

                            con = createConnector(client, sqlRep.GetElementByID(supplier.ElementID), refGuid, Main.EAAssociationType, supplierAggregation, clientAggregation, hasOpposite);
                            refNode.getAttributeOrCreate(Main.GuidStringName).Value = "Supplier";
                        }
                    }
                    else
                    {
                        con = createConnector(sqlRep.GetElementByID(client.ElementID), sqlRep.GetElementByID(supplier.ElementID), refGuid, Main.EAAssociationType,0,0, hasOpposite);
                        refNode.getAttributeOrCreate(Main.GuidStringName).Value = "Supplier";
                    }
                }
               if (con != null)
                {

                    if (!MainImport.getInstance().ReferenceGuidToReference.ContainsKey(refGuid))
                    {
                        MainImport.getInstance().ReferenceGuidToReference.Add(refGuid, con);
                    }

                    refGuid = refGuid.Replace("Client", "").Replace("Supplier", "");

                    if (!MainImport.getInstance().OldGuidToNewGuid.ContainsKey(refGuid))
                    {
                        MainImport.getInstance().OldGuidToNewGuid.Add(refGuid, con.ConnectorGUID);
                    }


                    
                    if (!MainImport.getInstance().ReferenceGuidToReference.ContainsKey(con.ConnectorGUID))
                    {
                        MainImport.getInstance().ReferenceGuidToReference.Add(con.ConnectorGUID, con);
                    }
                    
                    MocaNode referencesNodeSet = new MocaNode("references");


                    if (MainImport.getInstance().ReferenceGuidToClientTarget.ContainsKey(con.ConnectorGUID))
                    {
                        referencesNodeSet = MainImport.getInstance().ReferenceGuidToClientTarget[con.ConnectorGUID];
                    }
                    else
                    {
                        MainImport.getInstance().ReferenceGuidToClientTarget.Add(con.ConnectorGUID, referencesNodeSet);
                    }



                    referencesNodeSet.appendChildNode(refNode);
                }
                
            }
            
            
            

            
        }

        private void getSourceAndClient(EA.Element parent, MocaNode referencesNode, ref EA.Element client, ref EA.Element supp)
        {
            try
            {
                if (referencesNode.Children[0].getAttributeOrCreate(Main.GuidStringName).Value.Contains("Client"))
                    client = MainImport.getInstance().ElementGuidToElement[referencesNode.Children[0].getAttributeOrCreate("typeGuid").Value];
                else
                    client = MainImport.getInstance().ElementGuidToElement[referencesNode.Children[1].getAttributeOrCreate("typeGuid").Value];

            }
            catch 
            {
                client = parent;
            }

            try
            {
                if (referencesNode.Children[0].getAttributeOrCreate(Main.GuidStringName).Value.Contains("Supplier"))
                    supp = MainImport.getInstance().ElementGuidToElement[referencesNode.Children[0].getAttributeOrCreate("typeGuid").Value];
                else
                    supp = MainImport.getInstance().ElementGuidToElement[referencesNode.Children[1].getAttributeOrCreate("typeGuid").Value];
            }
            catch
            {
                supp = parent;
            }
        }

        /// <summary>
        /// create Inheritance connectors
        /// </summary>
        public void setInheritances()
        {
            foreach (EA.Element element in MainImport.getInstance().ElementToBaseClassGuids.Keys)
            {
                if(MainImport.hasGui)
                    MainImport.getInstance().ImportWorker.ReportProgress(3, new ProgressObject(ProgressBarType.Current, "Create Inheritance", MainImport.getInstance().ElementToBaseClassGuids.Count));

                String[] guids = MainImport.getInstance().ElementToBaseClassGuids[element].Split(" ".ToCharArray());
                foreach (String baseClassGuid in guids)
                {
                    if (baseClassGuid != "")
                    {
                        EA.Element baseClass = MainImport.getInstance().ElementGuidToElement[MainImport.getInstance().OldGuidToNewGuid[baseClassGuid]];

                        String result = repository.SQLQuery("select Connector_ID from t_connector where Connector_Type ='Generalization' AND Start_Object_ID = " + element.ElementID + " AND End_Object_ID = " + baseClass.ElementID);

                        if (EAUtil.getXMLNodeContentFromSQLQueryString(result, "Connector_ID")[0] == "")
                        {
                            EA.Connector inhCon = element.Connectors.AddNew("", "Generalization") as EA.Connector;
                            inhCon.SupplierID = baseClass.ElementID;
                            inhCon.Update();
                        }

                        
                    }
                }

            }
        }

        public EClass importEClass(SQLPackage parentPackage, MocaNode eClassNode)
        {
            

            EA.Element eclassElem = getOrCreateElement(parentPackage, eClassNode, "Class");
            MocaAttribute isAbstract = eClassNode.getAttributeOrCreate("isAbstract");
            if(isAbstract.Value == "true")
            {
                eclassElem.Abstract = "1";
            }
            else
            {
                eclassElem.Abstract = "0";
            }
            eclassElem.Update();

            return importEClassFeatures(eClassNode, sqlRep.GetElementByID(eclassElem.ElementID));
        }

        

        public EClass importEClassFeatures(MocaNode eClassNode, SQLElement eclassElem)
        {
            MocaNode operationsNode = eClassNode.getChildNodeWithName(EClass.OperationsChildNodeName);
            MocaNode attributesNode = eClassNode.getChildNodeWithName(EClass.AttributesChildNodeName);
            MocaNode refsNode = eClassNode.getChildNodeWithName(EClass.ReferencesChildNodeName);
            foreach (MocaNode eOpNode in operationsNode.Children)
            {
                EA.Method eaMethod = getOrCreateMethod(eclassElem, eOpNode);

                foreach (MocaNode eParamNode in eOpNode.getChildNodeWithName(EOperation.ParametersChildNodeName).Children)
                {
                    if (eParamNode.Name == "EParameter")
                    {
                        EA.Parameter eaParam = getOrCreateParameter(eaMethod, eParamNode);
                    }
                }
                eaMethod.Parameters.Refresh();

                MocaNode sdmActivityNode = eOpNode.getChildNodeWithName("Activity");
                if (sdmActivityNode != null)
                {
                    MainImport.getInstance().SdmImport.importSDMActivity(eclassElem, sqlRep.GetMethodByID(eaMethod.MethodID), sdmActivityNode);
                }

            }
            foreach (MocaNode eAttrNode in attributesNode.Children)
            {
                EA.Attribute eAttribute = getOrCreateAttribute(eclassElem, eAttrNode); ;
                eAttribute.Update();
            }

            if(refsNode.Children.Count != 0)
                MainImport.getInstance().ConnectorNodeToParent.Add(refsNode, eclassElem.getRealElement());
        
            eclassElem.Attributes.Refresh();
            eclassElem.Methods.Refresh();

            EClass eclass = new EClass(sqlRep.GetElementByID(eclassElem.ElementID), sqlRep);
            eclass.deserializeFromMocaTree(eClassNode);
            foreach (EAttribute eattr in eclass.EAttributes)
            {
                if (eattr.typeGuid != "")
                {
                    MainImport.getInstance().ObjectToTypeGuid.Add(eattr.EaAttribute.getRealAttribute(), eattr.typeGuid);
                }
                MainImport.getInstance().OldGuidToNewGuid.Add(eattr.guid, eattr.EaAttribute.AttributeGUID);
            }

            foreach (EOperation eOp in eclass.EOperations)
            {
                if (eOp.typeGuid != "")
                {
                    MainImport.getInstance().ObjectToTypeGuid.Add(eOp.EaMethod.getRealMethod(), eOp.typeGuid);

                }
                MainImport.getInstance().OldGuidToNewGuid.Add(eOp.guid, eOp.EaMethod.MethodGUID);
                foreach (EParameter eParam in eOp.EParameters)
                {
                    if (eParam.typeGuid != "")
                    {
                        MainImport.getInstance().ObjectToTypeGuid.Add(eParam.EaParameter.getRealParameter(), eParam.typeGuid);

                    }
                    MainImport.getInstance().OldGuidToNewGuid.Add(eParam.Guid, eParam.EaParameter.ParameterGUID);
                }
            }

            if (eClassNode.getAttributeOrCreate("baseClasses").Value != "")
            {
                MainImport.getInstance().ElementToBaseClassGuids.Add(eclassElem.getRealElement(), eClassNode.getAttributeOrCreate("baseClasses").Value);
            }
            MainImport.getInstance().ElementGuidToElement.Add(eclassElem.ElementGUID, eclassElem.getRealElement());
            MainImport.getInstance().MocaTaggableElements.Add(eclass);
            MainImport.getInstance().OldGuidToNewGuid.Add(eclass.Guid, eclassElem.ElementGUID);
            
            return eclass;
        }

        private void importEDatatype(SQLPackage parentPackage, MocaNode eDatatypeNode)
        {
            EA.Element edatatypeElement = getOrCreateElement(parentPackage, eDatatypeNode, "Class");

            EDatatype edatatype = new EDatatype( sqlRep.GetElementByID(edatatypeElement.ElementID), sqlRep);
            edatatype.deserializeFromMocaTree(eDatatypeNode);
           
            MainImport.getInstance().ElementGuidToElement.Add(edatatypeElement.ElementGUID, edatatypeElement);
            MainImport.getInstance().MocaTaggableElements.Add(edatatype);
            MainImport.getInstance().OldGuidToNewGuid.Add(edatatype.Guid, edatatypeElement.ElementGUID);
        }

        private void importEEnum(SQLPackage parentPackage, MocaNode eEnumNode)
        {
            EA.Element eenumElement = getOrCreateElement(parentPackage, eEnumNode, "Enumeration");

            foreach (MocaNode literalNode in eEnumNode.getChildNodeWithName("literals").Children)
            {
                String literalName = literalNode.getAttributeOrCreate("name").Value;
                if (literalName != "" && !literalExist(eenumElement, literalName))
                {
                    EA.Attribute literalAtt = eenumElement.Attributes.AddNew(literalName, "") as EA.Attribute;
                    literalAtt.Update();
                }
            }
            eenumElement.Attributes.Refresh();
            EEnum eenum = new EEnum(sqlRep.GetElementByID(eenumElement.ElementID), sqlRep);
            eenum.deserializeFromMocaTree(eEnumNode);

            MainImport.getInstance().ElementGuidToElement.Add(eenumElement.ElementGUID, eenumElement);
            MainImport.getInstance().MocaTaggableElements.Add(eenum);
            MainImport.getInstance().OldGuidToNewGuid.Add(eenum.Guid, eenumElement.ElementGUID);
        }

        private bool literalExist(EA.Element eenumElement, String name)
        {
            foreach (EA.Attribute attr in eenumElement.Attributes)
            {
                if (attr.Name == name)
                    return true;
            }

            return false;
        }

        #region getOrCreate methods
        public EA.Package getOrCreatePackage(SQLPackage rootPackage, MocaNode packageNode)
        {
            String oldGuid = packageNode.getAttributeOrCreate(Main.GuidStringName).Value;

            EA.Package modelPackage = repository.GetPackageByGuid(oldGuid);
            if (modelPackage != null)
            {
                DeleteWithGuid(rootPackage, oldGuid);
                EA.Package parent = repository.GetPackageByID(modelPackage.ParentID);
                DeleteWithGuid(parent, oldGuid);
            }

                EA.Package rootPackageOriginal = rootPackage.getRealPackage();
                String packageName = packageNode.getAttributeOrCreate(MetamodelHelper.MoflonCustomNameTaggedValueName).Value;
                if(packageName == "") {
                    packageName = packageNode.getAttributeOrCreate("name").Value;
                }

                modelPackage = rootPackageOriginal.Packages.AddNew(packageName, "") as EA.Package;
                if(rootPackageOriginal.ParentID == 0)
                    modelPackage.Flags = "isModel=1;VICON=3;";
                modelPackage.Update();

                // import nsUri
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonCustomNsUriTaggedValueName, packageNode, modelPackage);

                // import export
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonExportTaggedValueName, packageNode, modelPackage);

                // import pluginID
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonCustomPluginIDTaggedValueName, packageNode, modelPackage);

                // import prefix
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonCustomNsPrefixTaggedValueName, packageNode, modelPackage);

                // import validated
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonValidatedTaggedValueName, packageNode, modelPackage);

                // import name
                setTagValueIfPossibleForPackage(MetamodelHelper.MoflonCustomNameTaggedValueName, packageNode, modelPackage);

                repository.Execute("update t_package set ea_guid = '" + oldGuid + "' where ea_guid = '" + modelPackage.PackageGUID + "'");
                repository.Execute("update t_object set ea_guid = '" + oldGuid + "' where ea_guid = '" + modelPackage.PackageGUID + "'");
                modelPackage = repository.GetPackageByGuid(oldGuid);
            
            return modelPackage;
        }

        public void DeleteWithGuid(SQLPackage rootPackage, String guid)
        {
            foreach (SQLPackage sqlPackage in rootPackage.Packages)
            {
                if (rootPackage.PackageGUID == guid)
                {
                    rootPackage.Packages.Delete(sqlPackage);
                }

            }
        }

        public void DeleteWithGuid(EA.Package parent, String guid)
        {
            short max = parent.Packages.Count;
            for (short i = 0; i < max; i++)
            {
                EA.Package package = (EA.Package) parent.Packages.GetAt(i);
                if (package.PackageGUID == guid)
                {
                    parent.Packages.Delete(i);
                    return;
                }
            }
        }

        private void setTagValueIfPossibleForPackage(String tagNameInNode, MocaNode node, EA.Package eaPack)
        {
            MocaAttribute attribute = node.getAttribute(tagNameInNode); 
            
            if(attribute != null)
            {
                EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRep, eaPack, tagNameInNode, attribute.Value);
            }
            else
            {
                EAEcoreAddin.Util.EAUtil.deleteTaggedValue(eaPack.Element, tagNameInNode);
            }
            eaPack.Update();
        }


        public EA.Element getOrCreateElement(SQLPackage parentPackage, MocaNode elementNode, String elementType)
        {
            String oldGuid = elementNode.getAttributeOrCreate(Main.GuidStringName).Value;

            EA.Element eclassElem = repository.GetElementByGuid(oldGuid);
            if (eclassElem == null)
            {
                EA.Package parentPackageOrig = parentPackage.getRealPackage();
                eclassElem = parentPackageOrig.Elements.AddNew(elementNode.getAttributeOrCreate("name").Value, elementType) as EA.Element;
                eclassElem.Update();
                if (oldGuid != "")
                {
                    repository.Execute("update t_object set ea_guid = '" + oldGuid + "' where ea_guid = '" + eclassElem.ElementGUID + "'");
                    eclassElem = repository.GetElementByGuid(oldGuid);
                }
            }
            return eclassElem;
        }

        public EA.Element getOrCreateElement(SQLElement parentElement, MocaNode elementNode, String elementType)
        {
            return getOrCreateElement(parentElement, elementNode, elementType, int.MinValue);
        }


        public EA.Element getOrCreateElement(SQLElement parentElement, MocaNode elementNode, String elementType, int subtype)
        {
            String oldGuid = elementNode.getAttributeOrCreate(Main.GuidStringName).Value;

            EA.Element eclassElem = repository.GetElementByGuid(oldGuid);
            if (eclassElem == null)
            {
                eclassElem = parentElement.getRealElement().Elements.AddNew(elementNode.getAttributeOrCreate("name").Value, elementType) as EA.Element;
                if (subtype != int.MinValue)
                    eclassElem.Subtype = subtype;
                eclassElem.Update();
                if (oldGuid != "")
                {
                    repository.Execute("update t_object set ea_guid = '" + oldGuid + "' where ea_guid = '" + eclassElem.ElementGUID + "'");
                    eclassElem = repository.GetElementByGuid(oldGuid);
                }
            }
            return eclassElem;
        }

        public EA.Connector getOrCreateConnector(SQLElement client, SQLElement supplier, String guid, String connectorType)
        {
            String oldGuid = guid.Replace("Client", "").Replace("Supplier", "");
            EA.Connector connector = getConnector(oldGuid);
            if (connector == null)
            {
                connector = client.getRealElement().Connectors.AddNew("", connectorType) as EA.Connector;
                connector.SupplierID = supplier.ElementID;
                connector.Update();
                if (oldGuid != "")
                {
                    repository.Execute("update t_connector set ea_guid = '" + oldGuid + "' where ea_guid = '" + connector.ConnectorGUID + "'");
                    connector = repository.GetConnectorByGuid(oldGuid);
                }
            }
            connector.ClientID = client.ElementID;
            connector.SupplierID = supplier.ElementID;

            connector.Update();

            return connector;
        }

        public EA.Connector getConnector(String guid)
        {
            String oldGuid = guid.Replace("Client", "").Replace("Supplier", "");
            return repository.GetConnectorByGuid(oldGuid); 
        }

        public EA.Connector createConnector(SQLElement client, SQLElement supplier, String guid, String connectorType, int supplierAggregation, int clientAggregation, bool hasOpposite)
        {
            int locSupAgg = supplierAggregation;
            int locCliAgg = clientAggregation;

            String oldGuid = guid.Replace("Client", "").Replace("Supplier", "");

            EA.Connector connector = client.getRealElement().Connectors.AddNew("", connectorType) as EA.Connector;
            connector.SupplierID = supplier.ElementID;
            connector.Update();

            /*              
             * ClientEnds cannot be the Composite so switch Sides
             */
            if (clientAggregation == 2)
            {
                connector.ClientID = supplier.ElementID;
                connector.SupplierID = client.ElementID;
                connector.Update();
                locSupAgg = clientAggregation;
                locCliAgg = supplierAggregation;
                connector.Notes = "Switched";
            }

            if (hasOpposite)
            {
                connector.SupplierEnd.Navigable = "Navigable";
                connector.SupplierEnd.Update();
                connector.Update();
                connector.ClientEnd.Navigable = "Navigable";
                connector.ClientEnd.Update();
                connector.Update();
                connector.Direction = "Bi-Directional";
                connector.Update();
            }
            else
            {
                connector.ClientEnd.Navigable = "Navigable";
                connector.ClientEnd.Update();
                connector.Direction = "Source -> Destination";
                connector.Update();
            }

            connector.SupplierEnd.Aggregation = locSupAgg;
            connector.SupplierEnd.Update();
            connector.Update();

            connector.ClientEnd.Aggregation = locCliAgg;
            connector.ClientEnd.Update();
            connector.Update();



            client.Connectors.Refresh();
            supplier.Connectors.Refresh();

            if (oldGuid != "")
            {
                repository.Execute("update t_connector set ea_guid = '" + oldGuid + "' where ea_guid = '" + connector.ConnectorGUID + "'");
                connector = repository.GetConnectorByGuid(oldGuid);
            }

            connector.Update();

            return connector;
        }

        public EA.Parameter getOrCreateParameter(EA.Method parentMethod, MocaNode attributeNode)
        {
            String oldGuid = attributeNode.getAttributeOrCreate(Main.GuidStringName).Value;

            String result = repository.SQLQuery("select * from t_operationparams where ea_guid = '" + oldGuid + "'");

            EA.Parameter rattribute = null;
            SQLParameter attribute = new SQLParameter(sqlRep, result);
            if (attribute.ParameterGUID == "")
            {
                rattribute = parentMethod.Parameters.AddNew(attributeNode.getAttributeOrCreate("name").Value, "") as EA.Parameter;
                rattribute.Update();

                repository.Execute("update t_operationparams set ea_guid = '" + oldGuid + "' where ea_guid = '" + rattribute.ParameterGUID + "'");
                
                parentMethod.Parameters.Refresh();

                foreach (EA.Parameter parm in parentMethod.Parameters)
                {
                    if (parm.ParameterGUID == oldGuid)
                        return parm;
                }

            }
            return rattribute;
        }

        public EA.Attribute getOrCreateAttribute(SQLElement parentClass, MocaNode attributeNode)
        {
            String oldGuid = attributeNode.getAttributeOrCreate(Main.GuidStringName).Value;

            EA.Attribute attribute = repository.GetAttributeByGuid(oldGuid);
            if (attribute == null)
            {
                attribute = parentClass.getRealElement().Attributes.AddNew(attributeNode.getAttributeOrCreate("name").Value, "") as EA.Attribute;
                attribute.Update();
                repository.Execute("update t_attribute set ea_guid = '" + oldGuid + "' where ea_guid = '" + attribute.AttributeGUID + "'");
                attribute = repository.GetAttributeByGuid(oldGuid);
            }
            return attribute;
        }

        public EA.Method getOrCreateMethod(SQLElement parentClass, MocaNode methodNode)
        {
            String oldGuid = methodNode.getAttributeOrCreate(Main.GuidStringName).Value;
            String name = methodNode.getAttributeOrCreate("name").Value;
            String returnType = methodNode.getAttributeOrCreate("returnType").Value;

            return getOrCreateMethod(parentClass, oldGuid, name, returnType);
        }

        public EA.Method getOrCreateMethod(SQLElement parentClass, String oldGuid, String methodName, String returnType)
        {
            EA.Method method = repository.GetMethodByGuid(oldGuid);
            if (method == null)
            {
                method = parentClass.getRealElement().Methods.AddNew(methodName, "") as EA.Method;
                method.ReturnType = returnType;
                method.Update();
                repository.Execute("update t_operation set ea_guid = '" + oldGuid + "' where ea_guid = '" + method.MethodGUID + "'");
                method = repository.GetMethodByGuid(oldGuid);
            }
            return method;
        }

        public EA.Package findOrCreateRoot(MocaNode ePackageNode)
        {
            EA.Package rootPackage = null;
            String workingSetName = ePackageNode.getAttributeOrCreate(MetamodelHelper.MoflonWorkingSetTaggedValueName).Value;
             if (workingSetName == "")
             {
                 if (repository.Models.Count > 0)
                 {
                     rootPackage = repository.Models.GetAt(0) as EA.Package;
                 }
             }
             else
             {
                 foreach (EA.Package root in repository.Models)
                 {
                     if (root.Name == workingSetName)
                         rootPackage = root;
                 }
             }
            
            if (rootPackage == null)
            {
                String rootName = workingSetName;
                if(rootName == "")
                    rootName = "My Working Set";
                rootPackage = repository.Models.AddNew(rootName, "") as EA.Package;
                rootPackage.Update();
                repository.Models.Refresh();
            }


            return rootPackage;
        }

       // private static void appendTaggedValue()

        #endregion

        #region addDiagram methods

        private static void appendDiagram(EA.Package parentPackage, String diagramMetaType, EA.Repository repository)
        {
            if (parentPackage.Diagrams.Count == 0)
            {
                EA.Diagram diag = parentPackage.Diagrams.AddNew(parentPackage.Name, "") as EA.Diagram;
                diag.Update();
                diag.StyleEx = "MDGDgm=" + "eMoflon Ecore Diagrams::Ecore Diagram" + ";";
                diag.Update();
                repository.Execute("UPDATE t_diagram SET Diagram_Type='" + diagramMetaType + "' WHERE Diagram_ID=" + diag.DiagramID);
                MainImport.getInstance().DiagramsToBeFilled.Add(diag);
            }
        }

        #endregion

        internal void fillEcoreDiagram(EA.Diagram diagram)
        {
            EA.Collection elementCollection = null;
            EA.Collection packageCollection = null;
            if (diagram.PackageID != 0)
            {
                EA.Package pkg = (repository.GetPackageByID(diagram.PackageID) as EA.Package);
                elementCollection = pkg.Elements;
                packageCollection = pkg.Packages;
            }
            else if (diagram.ParentID != 0)
            {
                EA.Element ele = (repository.GetElementByID(diagram.ParentID) as EA.Element);
                elementCollection = ele.Elements;
            }

            MainImport.fillDiagramFromCollections(diagram, elementCollection, packageCollection);

            repository.GetProjectInterface().LayoutDiagramEx(diagram.DiagramGUID, EA.ConstLayoutStyles.lsDiagramDefault, 4, 20, 20, true);

            
        }

        
    }
}

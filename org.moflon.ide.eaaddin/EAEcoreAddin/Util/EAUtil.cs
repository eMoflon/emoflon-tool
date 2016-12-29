using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;
using System.Text.RegularExpressions;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using System.Drawing;
using EAEcoreAddin.Import;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.Util;

namespace EAEcoreAddin.Util
{
    class EAUtil
    {
        public static List<String> getXMLNodeContentFromSQLQueryString(String queryString, String rowName)
        {
            return getContentBetween(queryString, rowName, rowName);
        }

        public static List<String> getContentBetween(String queryString, String startTag, String endTag)
        {
            startTag = "<" + startTag + ">";
            endTag = "</" + endTag + ">";

            List<String> returnVal = new List<string>();

            
            string[] splitted = queryString.Split(new String[] { startTag }, StringSplitOptions.RemoveEmptyEntries);


            

            returnVal.AddRange(splitted);

            if (returnVal.Count > 0 && !queryString.StartsWith(startTag) )
            {
                returnVal.RemoveAt(0);           
            }

            for(int i = 0; i < returnVal.Count; i++)
            {
                returnVal[i] = returnVal[i].Split(new String[] { endTag }, StringSplitOptions.RemoveEmptyEntries)[0];
            }

            if (returnVal.Count == 0)
            {
                returnVal.Add("");
            } 
            return returnVal;
        }

        public static String setValueInXMLNodeContentFromSQLQueryString(string queryString, String rowName, string replaycement)
        {
            String startTag = "<" + rowName + ">";
            String endTag = "</" + rowName + ">";
            int startTagIndex = queryString.IndexOf(startTag);
            int endTagIndex = queryString.IndexOf(endTag);
            String origin = queryString.Substring(startTagIndex, endTagIndex - startTagIndex + endTag.Length);
            String replaycementString = startTag + replaycement + endTag;
            String returnValue = queryString.Replace(origin, replaycementString);
            return returnValue; 
        }


        private static Boolean stringEndsWith(string toCheck, string endsWith)
        {
            int z = endsWith.Length - 1;
            for (int i = 0; i< endsWith.Length; i++)
            {
                if (toCheck[toCheck.Length - i - 1] != endsWith[endsWith.Length - i - 1])
                {
                    return false;
                }
            }
            return true;
        }

        public static List<String> getDiagramIDsOfObject(SQLElement element, SQLRepository repository)
        {
            String result = repository.SQLQuery("select Diagram_ID from t_diagramobjects where Object_ID = " + element.ElementID);

            return getXMLNodeContentFromSQLQueryString(result, "Diagram_ID");
        }

        public static List<SQLElement> getBaseClasses(SQLElement classToGet)
        {
            return getBaseClasses(classToGet, new List<SQLElement>());
        }

        private static List<SQLElement> getBaseClasses(SQLElement classToGet, List<SQLElement> returnList)
        {
            SQLElement actClass = classToGet;
            returnList.Add(actClass);
            foreach (SQLElement actBaseClass in actClass.BaseClasses)
            {
                returnList = getBaseClasses(actBaseClass, returnList);
            }
            return returnList;
        }

        /// <summary>
        /// Searches for a TaggedValue with the given name on the given Element
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <returns>the found TaggedValue or null</returns>
        public static EA.TaggedValue findTaggedValue(EA.Element element, String name)
        {
            EA.TaggedValue aTag = null;
            if (element != null)
            {
                element.TaggedValues.Refresh();
                foreach (EA.TaggedValue aTaggi in element.TaggedValues)
                {
                    if (aTaggi.Name == name)
                        aTag = aTaggi;
                }
            }
            return aTag;
        }

        public static SQLTaggedValue findTaggedValue(SQLElement element, String name)
        {
            SQLTaggedValue aTag = null;
            if (element != null)
            {
                element.TaggedValues.Refresh();
                foreach (SQLTaggedValue aTaggi in element.TaggedValues)
                {
                    if (aTaggi.Name == name)
                        aTag = aTaggi;
                }
            }
            return aTag;
        }

        /// <summary>
        /// Searches for a MethodTag with the given name on the given Method
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <returns>the found MethodTag or null</returns>
        public static EA.MethodTag findTaggedValue(EA.Method method, String name)
        {
            method.TaggedValues.Refresh();
            EA.MethodTag aTag = null;

            foreach (EA.MethodTag aTaggi in method.TaggedValues)
            {
                if (aTaggi.Name == name) aTag = aTaggi;
            }

            return aTag;
        }

        

        public static EA.ConnectorTag findTaggedValue(EA.Connector element, String name)
        {
            element.TaggedValues.Refresh();
            foreach (EA.ConnectorTag tag in element.TaggedValues)
            {
                if (tag.Name == name)
                    return tag;
            }

            return null;
        }

        /// <summary>
        /// Searches for a ConnectorTag with the given name on the given Connector
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <returns>the found ConnectorTag or null</returns>
        public static SQLConnectorTag findTaggedValue(SQLConnector element, String name)
        {
            element.TaggedValues.Refresh();
            foreach (SQLConnectorTag tag in element.TaggedValues)
            {
                if (tag.Name == name)
                    return tag;
            }

            return null;
        }


        /// <summary>
        /// Searches for a TaggedValue in the packageElement with the given name
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <returns>the found TaggedValue or null</returns>
        public static SQLTaggedValue findTaggedValue(SQLPackage package, String name)
        {
            SQLTaggedValue aTag = null;
            SQLElement element = package.Element;
            if (element != null)
            {
                element.TaggedValues.Refresh();


                foreach (SQLTaggedValue aTaggi in element.TaggedValues)
                {
                    if (aTaggi.Name == name) aTag = aTaggi;
                }
            }

            return aTag;
        }

        public static string getPackageURI(SQLRepository repository, SQLPackage pkg)
        {
            EPackage epackage = new EPackage(pkg, repository);
            return epackage.NsURI;
        }

        public static string getPackageURI(SQLRepository repository, SQLElement targetElement)
        {
            return getPackageURI(repository, repository.GetPackageByID(targetElement.PackageID)) + "/";
        }

        public static SQLPackage getSelectionOutermostPackage(SQLRepository repository)
        {
            Object treeSelection = repository.GetTreeSelectedObject();

            if (!(treeSelection is EA.Package) && !(treeSelection is EA.Element))
                return getOutermostPackage(getContainingElement(treeSelection, repository), repository);
            else
                return getOutermostPackage(treeSelection, repository);
        }

        public static SQLElement getContainingElement(Object eaObject, SQLRepository repo)
        {
            try
            {
                if (eaObject is SQLElement)
                {
                    return eaObject as SQLElement;
                }
                else if (eaObject is SQLAttribute)
                {
                    SQLElement parent = repo.GetElementByID((eaObject as SQLAttribute).ParentID);
                    return parent;
                }
                else if (eaObject is SQLMethod)
                {
                    SQLElement parent = repo.GetElementByID((eaObject as SQLMethod).ParentID);
                    return parent;
                }
                else if (eaObject is SQLConnector)
                {
                    SQLElement parent = repo.GetElementByID((eaObject as SQLConnector).ClientID);
                    return parent;
                }
                else if (eaObject is SQLDiagram)
                {
                    SQLDiagram dia = eaObject as SQLDiagram;
                    if (dia.ParentID != 0)
                        return repo.GetElementByID((eaObject as SQLDiagram).ParentID);
                    else if (dia.getRealDiagram().PackageID != 0)
                        return repo.GetPackageByID(dia.getRealDiagram().PackageID).Element;
                    
                }
            }

            catch
            {
                return null;
            }
            return null;
        
        }

        public static Boolean packageIsModel(SQLPackage pkg, SQLRepository repository)
        {
            if (pkg != null && pkg.ParentID != 0)
            {
                SQLPackage parent = repository.GetPackageByID(pkg.ParentID);
                if (parent.ParentID == 0)
                    return true;
            }
            return false;
        }

        public static SQLPackage getOutermostPackage(Object eaObject, SQLRepository repository)
        {
            if (eaObject == null)
                return null;

            SQLPackage pkg = null;
            if (eaObject is SQLPackage)
            {
                pkg = eaObject as SQLPackage;
            }

            if (eaObject is EA.Package)
            {
                pkg = repository.GetPackageByID((eaObject as EA.Package).PackageID);
            }
            else if (eaObject is EA.Attribute)
            {
                return getOutermostPackage(repository.GetElementByID(((EA.Attribute)eaObject).ParentID), repository);
            }
            else if (eaObject is EA.Method)
            {
                return getOutermostPackage(repository.GetElementByID(((EA.Method)eaObject).ParentID), repository);
            }
            else if (eaObject is EA.Diagram)
            {
                EA.Diagram diagram = (EA.Diagram) eaObject;
                if(diagram.PackageID != 0) {
                    return getOutermostPackage(repository.GetPackageByID(((EA.Diagram)eaObject).PackageID), repository);
                }
                else if(diagram.ParentID != 0) {
                    return getOutermostPackage(repository.GetElementByID(((EA.Diagram)eaObject).ParentID), repository);
                }  
            }
            else if (eaObject is EA.Element)
            {
                return getOutermostPackage( repository.GetElementByID((eaObject as EA.Element).ElementID), repository);
            }

            else
            {

                SQLElement containigElement = getContainingElement(eaObject, repository);
                if (containigElement != null)
                {
                    if (containigElement.PackageID != 0)
                    {
                        pkg = repository.GetPackageByID((containigElement).PackageID);
                    }
                }
            }
            if (pkg != null)
            {
                if (pkg.ParentID == 0)
                    return null;

                while (!packageIsModel(pkg, repository))
                {
                    pkg = repository.GetPackageByID(pkg.ParentID);
                }
            }
            return pkg;
        }

        private static int markConnectorOnDiagram(SQLConnector connector, SQLRepository repository)
        {

            int count = 0;
            try
            {
                List<String> diagramIdsOfClient = EAUtil.getDiagramIDsOfObject(repository.GetElementByID(connector.ClientID), repository);
                List<String> diagramIdsOfSupplier = EAUtil.getDiagramIDsOfObject(repository.GetElementByID(connector.SupplierID), repository);
                List<int> diagramIds = new List<int>();
                foreach (String clientDiagId in diagramIdsOfClient)
                {
                    foreach (String suppDiagId in diagramIdsOfSupplier)
                    {
                        if (clientDiagId != "" && suppDiagId != "")
                        {
                            if (clientDiagId == suppDiagId)
                            {
                                diagramIds.Add(int.Parse(clientDiagId));
                                count++;
                            }
                        }
                    }
                }
                if (diagramIds.Count == 0)
                    return 0;

                foreach (int diagramId in diagramIds)
                {
                    EA.Diagram diagram = repository.GetOriginalRepository().GetDiagramByID(diagramId);
                    EA.DiagramLink diagramObject = null;
                    foreach (EA.DiagramLink diagLink in diagram.DiagramLinks)
                    {
                        if (diagLink.ConnectorID == connector.ConnectorID && diagLink.IsHidden == false)
                            diagramObject = diagLink;
                    }
                    if (diagramObject != null)
                    {
                        repository.OpenDiagram(diagramObject.DiagramID);
                        repository.GetCurrentDiagram().SelectedConnector = connector.getRealConnector();
                        repository.SaveDiagram(diagramObject.DiagramID);
                        break;
                        //repository.ReloadDiagram(diagramObject.DiagramID);
                    }
                }

            }
            catch
            {
            }
            
            return count;
        }

        public static EA.TaggedValue setTaggedValue(SQLRepository repository, EA.Package package, String name, String value)
        {
            EA.Package actPackage = package;
            if (actPackage is SQLPackage)
                actPackage = repository.GetOriginalRepository().GetPackageByID(package.PackageID);
            EA.Element element = actPackage.Element;
            if (element != null)
            {
                EA.TaggedValue aTag = findTaggedValue(element, name);

                if (aTag == null) aTag = (EA.TaggedValue)element.TaggedValues.AddNew(name, value);
                aTag.Value = value;
                aTag.Update();
                return aTag;
            }
            return null;
        }

        public static EA.TaggedValue setTaggedValueNotes(SQLRepository repository, EA.Element element, String name, String notes)
        {
            element.TaggedValues.Refresh();
            EA.Element actElement = element;
            if (actElement is SQLElement)
                actElement = repository.GetOriginalRepository().GetElementByID(element.ElementID);
            EA.TaggedValue aTag = setTaggedValue(repository, actElement, name, "<memo>");
            aTag.Notes = notes;
            aTag.Update();
            return aTag;
        }

        public static EA.TaggedValue setTaggedValueNotes(SQLRepository repository, EA.Package package, String name, String notes)
        {
            if (package.Element != null)
            {
                package.Element.TaggedValues.Refresh();
                EA.TaggedValue aTag = setTaggedValue(repository, package, name, "<memo>");
                aTag.Notes = notes;
                aTag.Update();
                return aTag;
            }
            return null;

        }

        public static Object setTaggedValueNotes(SQLRepository repository, Object eaObject, String name, String notes)
        {
            if (eaObject is SQLPackage)
                return setTaggedValueNotes(repository, (eaObject as SQLPackage).getRealPackage(), name, notes);
            else if (eaObject is SQLElement)
                return setTaggedValueNotes(repository, (eaObject as SQLElement).getRealElement(), name, notes);
            else if (eaObject is SQLConnector)
                return setTaggedValueNotes(repository, (eaObject as SQLConnector).getRealConnector(), name, notes);
            else if (eaObject is SQLConnectorEnd)
                return setTaggedValueNotes(repository, (eaObject as SQLConnectorEnd).getRealConnectorEnd(), name, notes);
            else throw new NotImplementedException();
            
        }

        public static EA.ConnectorTag setTaggedValueNotes(SQLRepository repository, EA.Connector connector, String name, String notes)
        {
            connector.TaggedValues.Refresh();
            EA.ConnectorTag aTag = setTaggedValue(repository, connector, name, "<memo>");
            aTag.Notes = notes;
            aTag.Update();
            return aTag;
        }

        public static EA.TaggedValue setTaggedValue(SQLRepository repository, EA.Element elem, String name, String value)
        {
            EA.Element element = elem;
            if (elem is SQLElement)
                element = repository.GetOriginalRepository().GetElementByID(elem.ElementID);
            element.TaggedValues.Refresh();
            EA.TaggedValue aTag = findTaggedValue(element, name);
            if (aTag == null) aTag = (EA.TaggedValue)element.TaggedValues.AddNew(name, value);
            aTag.Value = value;
            aTag.Update();
            return aTag;
        }
        /// <summary>
        /// Creates or editExistingLink the ConnectorTag with the given name and value of the given connector
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <param name="value"></param>
        /// <returns>the created or edited ConnectorTag</returns>
        public static EA.ConnectorTag setTaggedValue(SQLRepository repository, EA.Connector con, String name, String value)
        {
            EA.Connector connector = con;
            connector.TaggedValues.Refresh();
            EA.ConnectorTag aTag = findTaggedValue(connector, name);
            if (aTag == null)
            {
                aTag = (EA.ConnectorTag)connector.TaggedValues.AddNew(name, value);
            }
            aTag.Value = value;
            aTag.Update();
            return aTag;
        }
        /// <summary>
        /// Creates or editExistingLink the MethodTag with the given name and value of the given method
        /// </summary>
        /// <param name="element"></param>
        /// <param name="name"></param>
        /// <param name="value"></param>
        /// <returns>the created or edited MethodTag</returns>
        public static EA.MethodTag setTaggedValue(SQLRepository repository, EA.Method meth, String name, String value)
        {
            EA.Method method = meth;
            if (meth is SQLMethod)
                method = repository.GetOriginalRepository().GetMethodByID(meth.MethodID);
            method.TaggedValues.Refresh();
            EA.MethodTag aTag = findTaggedValue(method, name);
            if (aTag == null) aTag = (EA.MethodTag)method.TaggedValues.AddNew(name, value);
            aTag.Value = value;
            aTag.Update();
            return aTag;
        }


        public static bool deleteTaggedValue(EA.Connector con, String name)
        {
            con.TaggedValues.Refresh();
            short z = 0;
            foreach (EA.ConnectorTag aTag in con.TaggedValues)
            {
                if (aTag.Name == name)
                {
                    con.TaggedValues.Delete(z);
                    return true;
                }
                z++;
            }
            return false;
        }

        public static bool deleteTaggedValue(EA.Element element, String name)
        {
            element.TaggedValues.Refresh();
            short z = 0;
            foreach (EA.TaggedValue aTag in element.TaggedValues)
            {
                if (aTag.Name == name)
                {
                    element.TaggedValues.Delete(z);
                    return true;
                }
                z++;
            }
            return false;
        }

        public static Object sqlEAObjectToOriginalObject(SQLRepository sqlRepository, Object sqlEAObject)
        {
            EA.Repository repository = sqlRepository.GetOriginalRepository();
            Object realObject = sqlEAObject;
            if (sqlEAObject is SQLElement)
            {

                realObject = repository.GetElementByGuid((sqlEAObject as SQLElement).ElementGUID);
                if(realObject == null)
                    throw new COMException("Can't find matching ID");
                else
                    realObject = repository.GetElementByID((sqlEAObject as SQLElement).ElementID);
            }
            else if (sqlEAObject is SQLConnector)
            {
                realObject = repository.GetConnectorByID((sqlEAObject as SQLConnector).ConnectorID);
            }
            else if (sqlEAObject is SQLMethod)
            {
                realObject = repository.GetMethodByID((sqlEAObject as SQLMethod).MethodID);
            }
            else if (sqlEAObject is SQLAttribute)
            {
                realObject = repository.GetAttributeByID((sqlEAObject as SQLAttribute).AttributeID);
            }
            else if (sqlEAObject is SQLPackage)
            {
                realObject = repository.GetPackageByID((sqlEAObject as SQLPackage).PackageID);
            }
            else if (sqlEAObject is SQLDiagram)
            {
                realObject = repository.GetDiagramByID((sqlEAObject as SQLDiagram).DiagramID);
            }
            return realObject;
        }

        public static int markObjectOnDiagram(Object eaObject, SQLRepository rep)
        {
            clearSelectedDiagramObjects(rep);
            int count = 0;
            if (eaObject is SQLElement)
                count = markElementOnDiagram(eaObject as SQLElement, rep);
            else if (eaObject is SQLConnector)
                count = markConnectorOnDiagram(eaObject as SQLConnector, rep);
            else if (eaObject is SQLMethod)
                count = markMethodOnDiagram(eaObject as SQLMethod, rep);
            else if (eaObject is SQLAttribute)
                count = markAttributeOnDiagram(eaObject as SQLAttribute, rep);
            else if (eaObject is SQLPackage)
                count = markPackageOnDiagram(eaObject as SQLPackage, rep);


            /*
            EA.Diagram curDiag = rep.GetCurrentDiagram();

            if (curDiag != null && curDiag.SelectedObjects.Count > 0)
            {
                KeyboardSimulator kSim = new KeyboardSimulator();

                if (count == 1)
                {

                    kSim.KeyDown(VirtualKeyCode.LCONTROL);
                    kSim.KeyPress(VirtualKeyCode.VK_U);
                    kSim.KeyUp(VirtualKeyCode.LCONTROL);

                }
                if (count > 1)
                {
                    kSim.KeyDown(VirtualKeyCode.LCONTROL);
                    kSim.KeyPress(VirtualKeyCode.VK_U);
                    kSim.KeyUp(VirtualKeyCode.LCONTROL);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.NEXT);
                    kSim.KeyPress(VirtualKeyCode.RETURN);

                }
            }
             * */
            return count;
        }

        private static int markPackageOnDiagram(SQLPackage pkg, SQLRepository rep)
        {
            if (pkg.Element != null)
                return markElementOnDiagram(pkg.Element, rep);
            return 0;
        }

        public static void deleteDiagramObject(EA.Repository repository, EA.Diagram actDiagram, EA.Element actElement)
        {
            short i = 0;
            repository.SaveDiagram(actDiagram.DiagramID);
            foreach (EA.DiagramObject actDiagObject in actDiagram.DiagramObjects)
            {
                if (actDiagObject.ElementID == actElement.ElementID)
                {
                    actDiagram.DiagramObjects.Delete(i);
                    actDiagram.DiagramObjects.Refresh();
                    actDiagram.Update();
                    repository.ReloadDiagram(actDiagram.DiagramID);
                    break;
                }
                i++;
            }
        }

        /// <summary>
        /// Searches for a DiagramObject with the same ID as the element on the given diagram
        /// </summary>
        /// <param name="element"></param>
        /// <param name="diagram"></param>
        /// <returns>The found DiagramObject or null if there exists none</returns>
        public static EA.DiagramObject findDiagramObject(SQLRepository repository, EA.Element element, EA.Diagram diagram)
        {
            EA.Diagram currentDiagram = diagram;
            if (currentDiagram is SQLDiagram)
                currentDiagram = sqlEAObjectToOriginalObject(repository, currentDiagram) as EA.Diagram;
            EA.DiagramObject val = null;
            foreach (EA.DiagramObject diagObj in currentDiagram.DiagramObjects)
            {
                if (diagObj.ElementID == element.ElementID)
                {
                    val = diagObj;
                    break;
                }
            }
            return val;
        }

        /// <summary>
        /// Searches for a DiagramObject with the same ID as the connector on the given diagram
        /// </summary>
        /// <param name="element"></param>
        /// <param name="diagram"></param>
        /// <returns>The found DiagramObject or null if there exists none</returns>
        public static EA.DiagramLink findDiagramObject(EA.Connector element, EA.Diagram diagram)
        {
            EA.DiagramLink val = null;
            foreach (EA.DiagramLink diagObj in diagram.DiagramLinks)
            {
                if (diagObj.ConnectorID == element.ConnectorID) { val = diagObj; break; }
            }
            return val;
        }


        public static String getMethodSignature(SQLMethod method)
        {
            if (method != null)
            {
                String value = method.Name + "(";
                foreach (SQLParameter param in method.Parameters)
                {
                    value += param.Type + ",";
                }
                if (value[value.Length - 1] == ',')
                    value = value.Substring(0, value.Length - 1);
                value += ")";
                return value + ":" + method.ReturnType;
            }
            return "";
        }

        public static bool markObjectInProjectBrowser(Object eaObject, SQLRepository rep)
        {
            if (eaObject is SQLElement)
                return markElementInProjectBrowser(eaObject as SQLElement, rep);
            else if (eaObject is SQLConnector)
                return markConnectorInProjectBrowser(eaObject as SQLConnector, rep);
            else if (eaObject is SQLMethod)
                return markMethodInProjectBrowser(eaObject as SQLMethod, rep);
            else if (eaObject is SQLAttribute)
                return markAttributeInProjectBrowser(eaObject as SQLAttribute, rep);
            else if (eaObject is SQLPackage)
                return markPackageInProjectBrowser(eaObject as SQLPackage, rep);
            else
                return false;
        }

        private static Boolean markMethodInProjectBrowser(SQLMethod method, SQLRepository rep)
        {
            EA.Method realMethod = rep.GetOriginalRepository().GetMethodByID(method.MethodID);
            rep.ShowInProjectView(realMethod);
            return true;
        }

        private static Boolean markAttributeInProjectBrowser(SQLAttribute attribute, SQLRepository rep)
        {
            EA.Attribute realAttribute = rep.GetOriginalRepository().GetAttributeByID(attribute.AttributeID);
            rep.ShowInProjectView(realAttribute);
            return true;
        }

        private static Boolean markConnectorInProjectBrowser(SQLConnector con, SQLRepository rep)
        {
            SQLElement client = rep.GetElementByID(con.ClientID);

            return markElementInProjectBrowser(client, rep);
        }


        private static bool markElementInProjectBrowser(SQLElement element, SQLRepository repository)
        {
            EA.Element actElement = element.getRealElement();
            repository.ShowInProjectView(actElement);
            return true;
        }

        private static Boolean markPackageInProjectBrowser(SQLPackage package, SQLRepository repository)
        {
            EA.Package actPackage = package.getRealPackage();
            repository.ShowInProjectView(actPackage);
            return true;
        }

        private static int markAttributeOnDiagram(SQLAttribute attribute, SQLRepository repository)
        {
            SQLElement parentElement = repository.GetElementByID(attribute.ParentID);
            int count = markElementOnDiagram(parentElement, repository);
            return count;
        }

        private static int markMethodOnDiagram(SQLMethod method, SQLRepository repository)
        {
            SQLElement parentElement = repository.GetElementByID(method.ParentID);
            return markElementOnDiagram(parentElement, repository);
        }

        public static void clearSelectedDiagramObjects(SQLRepository rep)
        {
            EA.Diagram diagram = rep.GetCurrentDiagram();
            if (diagram != null)
            {
                for (short i = 0; i < diagram.SelectedObjects.Count; i++)
                {
                    diagram.SelectedObjects.Delete(i);
                }
            }
        }

        private static int markElementOnDiagram(SQLElement element, SQLRepository repository)
        {
            EA.Element realElement = element.getRealElement();
            EA.DiagramObject diagramObject = null;
            String diagramIdResult = repository.SQLQuery("select * from t_diagramobjects where t_diagramobjects.Object_ID = " + realElement.ElementID);
            int count = 0;
            String diagramID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(diagramIdResult, "Diagram_ID")[0];
            if (diagramID != "")
            {
                count = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(diagramIdResult, "Diagram_ID").Count;

                EA.Diagram diagram = repository.GetOriginalRepository().GetDiagramByID(int.Parse(diagramID));
                foreach (EA.DiagramObject diagObject in diagram.DiagramObjects)
                {
                    if (diagObject.ElementID == realElement.ElementID)
                        diagramObject = diagObject;
                }
                if (diagramObject != null)
                {
                    repository.OpenDiagram(diagram.DiagramID);

                    diagram.SelectedObjects.AddNew(diagramObject.ElementID.ToString(), "");

                    // repository.ReloadDiagram(diagram.DiagramID);
                }
            }
            return count;

        }

        /// <summary>
        /// Deletes the element
        /// </summary>
        /// <param name="Element"></param>
        /// <param name="sqlRep"></param>
        public static void deleteElement(EA.Element element, SQLRepository sqlRepository)
        {
            EA.Repository repository = sqlRepository.GetOriginalRepository();
            if (element != null)
            {

                EA.Element Element = sqlEAObjectToOriginalObject(sqlRepository, element) as EA.Element;
                if (Element.ParentID != 0)
                {
                    EA.Element actobject = null;
                    EA.Element eleParent = repository.GetElementByID(Element.ParentID);
                    for (short i = 0; i < eleParent.Elements.Count; i++)
                    {
                        actobject = (EA.Element)eleParent.Elements.GetAt(i);

                        if (actobject.ElementID == Element.ElementID)
                        {
                            eleParent.Elements.Delete(i);
                            eleParent.Elements.Refresh();
                            Element.Update();
                            return;
                        }
                    }

                }
                else
                {
                    EA.Element actobject = null;
                    EA.Package eleParent = repository.GetPackageByID(Element.PackageID);
                    for (short i = 0; i < eleParent.Elements.Count; i++)
                    {
                        actobject = (EA.Element)eleParent.Elements.GetAt(i);

                        if (actobject.ElementID == Element.ElementID)
                        {
                            eleParent.Elements.Delete(i);
                            eleParent.Elements.Refresh();
                            Element.Update();
                            return;
                        }
                    }
                }

            }

        }

        public static void deletePackage(EA.Package package, EA.Repository Repository)
        {
            if (package.ParentID != 0)
            {
                EA.Package eleParent = Repository.GetPackageByID(package.ParentID);
                EA.Package actobject = null;
                for (short i = 0; i < eleParent.Packages.Count; i++)
                {
                    actobject = (EA.Package)eleParent.Packages.GetAt(i);

                    if (actobject.PackageID == package.PackageID)
                    {
                        eleParent.Packages.Delete(i);
                        eleParent.Packages.Refresh();
                        return;
                    }
                }
            }
        }

        /// <summary>
        /// deletes the connector con
        /// </summary>
        /// <param name="con"></param>
        /// <param name="Repository"></param>
        public static void deleteConnector(EA.Connector con, EA.Repository Repository)
        {
            EA.Element client = Repository.GetElementByID(con.ClientID);
            EA.Diagram actDiag = Repository.GetCurrentDiagram();
            short i = 0;
            foreach (EA.Connector actLink in client.Connectors)
            {
                if (actLink.ConnectorID == con.ConnectorID)
                {
                    client.Connectors.Delete(i);
                }
                i++;
            }
            Repository.ReloadDiagram(actDiag.DiagramID);
        }

        public static void configureConnectorEnd(SQLRepository sqlRepository, EA.ConnectorEnd sourceOrTarget, int valueId, String cardinality)
        {
            EA.Element targetEClass = sqlRepository.GetElementByID(valueId).getRealElement();
            sourceOrTarget.Role = targetEClass.Name.Substring(0, 1).ToLower() + targetEClass.Name.Substring(1, targetEClass.Name.Length - 1);
            
            // If the association end has a cardinality larger than 1, add a 'plural s' to the role name.
            String lowerBound = "";
            String upperBound = "";
            EcoreUtil.computeLowerUpperBound(cardinality, ref lowerBound, ref upperBound);
            if (upperBound == EcoreUtil.ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER || int.Parse(upperBound) > 1)
                sourceOrTarget.Role = sourceOrTarget.Role + "s";

            sourceOrTarget.Cardinality = cardinality;
            sourceOrTarget.Update();
        }

        public static string computeRandomString(Int64 Length)
        {
            System.Random rnd = new System.Random();
            StringBuilder Temp = new StringBuilder();
            for (Int64 i = 0; i < Length; i++)
            {
                Temp.Append(Convert.ToChar(((byte)rnd.Next(254))).ToString());
            }
            return Temp.ToString();
        }

        public static void updateStereotype(EA.Repository repository, String newStereo, String oldStereo)
        {
            repository.Execute("UPDATE t_object SET Stereotype = '" + newStereo + "' WHERE Stereotype = '" + oldStereo + "'");
        }

        public static void updateStereotypeEx(EA.Repository repository, String newStereo, String oldStereo)
        {
            String result = repository.SQLQuery("select ea_guid from t_object where Stereotype = '" + oldStereo + "'");

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "ea_guid"))
            {
                if (row != "")
                {
                    EA.Element element = repository.GetElementByGuid(row);
                    if (element != null)
                    {
                        element.StereotypeEx = newStereo;
                        element.Update();
                    }
                }
            }
        }

        public static int computeDropDownWidth(ComboBox myCombo)
        {
            int maxWidth = 1, temp = 0;
            foreach (var obj in myCombo.Items)
            {
                temp = TextRenderer.MeasureText(obj.ToString(), myCombo.Font).Width;
                if (temp > maxWidth)
                {
                    maxWidth = temp;
                }
            }
            return maxWidth + 20;
        }

        internal static List<SQLElement> findSubclasses(SQLElement elementOnOtherSide, SQLRepository rep)
        {
            List<SQLElement> children = new List<SQLElement>();
            children.Add(elementOnOtherSide);
            for (int i = 0; i < children.Count; i++)
            {
                SQLElement currentElement = children[i];
                foreach (SQLConnector corrCon in currentElement.Connectors)
                {
                    //corrCon client is inheriting correspondence type
                    if (corrCon.Type == Main.EAGeneralizationConType && corrCon.SupplierID == currentElement.ElementID)
                    {
                        children.Add(rep.GetElementByID(corrCon.ClientID));
                    }
                }
            }
            return children;

        }

        internal static SQLElement getClassifierElement(SQLRepository repository, int classifierId)
        {
            if (classifierId != 0 && classifierId != -1)
            {
                try
                {
                    return repository.GetElementByID(classifierId);
                }
                catch (Exception)
                {
                    return null;
                }
            }
            return null;
        }


        #region SQL commands

        public static IEnumerable<EA.Package> findAllPackagesWithStereotype(EA.Repository repository, String stereotype)
        {
            String allPackageGuids = repository.SQLQuery(@"select o.ea_guid FROM t_object o , t_package p where o.Stereotype = '" + stereotype + "' AND o.Package_ID = p.Package_ID");

            return getXMLNodeContentFromSQLQueryString(allPackageGuids, "ea_guid").Select(id => repository.GetPackageByGuid(id)).Where(elt => elt != null);
        }

        public static IEnumerable<EA.Package> findAllEPackages(EA.Repository repository)
        {
            return findAllPackagesWithStereotype(repository, "EPackage");
        }

        public static IEnumerable<EA.Package> findAllTGGProjects(EA.Repository repository)
        {
            return findAllPackagesWithStereotype(repository, "TGGSchemaPackage");
        }

        public static IEnumerable<EA.Package> findAllTGGRulePackages(EA.Repository repository)
        {
            return findAllPackagesWithStereotype(repository, "RulePackage");
        }

        public static IEnumerable<EA.Package> findAllMoflonPackages(EA.Repository repository)
        {
            return findAllEPackages(repository)
                .Concat(findAllTGGProjects(repository))
                .Concat(findAllTGGRulePackages(repository));
        }

        #endregion

        internal static IEnumerable<EA.Element> findAllEClasses(EA.Repository repository)
        {
            List<SQLElement> eClasses = new List<SQLElement>();
            return findAllStereotypedElements(repository,ECOREModelingMain.EClassStereotype);
        }

        internal static IEnumerable<EA.Element> findAllEEnums(EA.Repository repository)
        {
            List<SQLElement> eClasses = new List<SQLElement>();
            return findAllStereotypedElements(repository, ECOREModelingMain.EEnumStereotype);
        }

        internal static IEnumerable<EA.Element> findAllStereotypedElements(EA.Repository repository, String stereotype)
        {
            String sqlResult = repository.SQLQuery("SELECT ea_guid FROM t_object where Stereotype ='"+ stereotype +"'");
            return getXMLNodeContentFromSQLQueryString(sqlResult, "ea_guid").Select(id => repository.GetElementByGuid(id)).Where(elt => elt != null);
        }

        internal static IEnumerable<EA.Connector> findAllStereotypedConnectors(EA.Repository repository, String stereotype)
        {
            String sqlResult = repository.SQLQuery("SELECT ea_guid FROM t_connector where Stereotype ='" + stereotype + "'");
            return getXMLNodeContentFromSQLQueryString(sqlResult, "ea_guid").Select(id => repository.GetConnectorByGuid(id)).Where(elt => elt != null);
        }


        internal static string formatErrorMessage(Exception e)
        {
            return "Something has gone wrong. Please check the validation messages and contact the eMoflon team if necessary (contact@emoflon.org).\n\nException: " + e.ToString();
        }
    }
}

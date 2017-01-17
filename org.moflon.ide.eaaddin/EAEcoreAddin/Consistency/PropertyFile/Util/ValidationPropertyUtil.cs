using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Consistency.PropertyFile.Util
{
    class ValidationPropertyUtil
    {

        public static Dictionary<String, Object> foundObjects = new Dictionary<string, object>();

        #region compute methods

        private static Object computeOperation(Object parentClass, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parentClass;
            foreach (SQLMethod method in (eaObject as SQLElement).Methods)
            {
                if (method.Name == getNameOfPathEntry(pathEntry))
                {
                    List<String> parameters = getParametersOfOperationPathEntry(pathEntry);
                    Boolean valid = true;
                    for (short i = 0; i < method.Parameters.Count; i++)
                    {
                        SQLParameter actParam = method.Parameters.GetAt(i) as SQLParameter;
                        String paramString = parameters[i];
                        if (paramString != actParam.Name)
                            valid = false;
                    }
                    if (valid)
                    {
                        eaObject = method;
                    }
                    else
                    {
                        throw new NullReferenceException();
                    }
                }
            }
            return eaObject;
        }

        private static Object computeAttribute(Object parentClass, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parentClass;
            foreach (SQLAttribute attribute in (eaObject as SQLElement).Attributes)
            {
                if (attribute.Name == getNameOfPathEntry(pathEntry))
                {
                    eaObject = attribute;
                    break;
                }
            }
            return eaObject;
        }

        private static Object computeActivity(Object owningOperation, SQLRepository repository, String pathEntry)
        {
            Object eaObject = owningOperation;
            SQLMethod actMethod = eaObject as SQLMethod;
            SQLElement methodParent = repository.GetElementByID(actMethod.ParentID);
            foreach (SQLElement actContainer in methodParent.Elements)
            {
                String guidOfAssociatedMethod = EAEcoreAddin.Util.EAUtil.findTaggedValue(actContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value;
                if (guidOfAssociatedMethod == actMethod.MethodGUID)
                {

                    eaObject = actContainer;
                    break;
                }
            }
            return eaObject;
        }

        private static Object computeChildElement(Object parentPackage, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parentPackage;
            foreach (SQLElement element in (eaObject as SQLPackage).Elements)
            {
                if (element.Name == getNameOfPathEntry(pathEntry))
                {
                    eaObject = element;
                    break;
                }
            }
            return eaObject;
        }

        private static Object computePackage(Object parentObject, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parentObject;
            //if pathEntry is outmost Package
            if (eaObject == null)
            {
                foreach (SQLPackage modelRoot in repository.Models)
                {
                    foreach (SQLPackage metamodelPackage in modelRoot.Packages)
                    {
                        if (metamodelPackage.Name == getNameOfPathEntry(pathEntry))
                        {

                            eaObject = metamodelPackage;
                            break;
                        }
                    }
                }
            }
            //if pathEntry is random package
            else
            {
                foreach (SQLPackage actPackage in (eaObject as SQLPackage).Packages)
                {
                    if (actPackage.Name == getNameOfPathEntry(pathEntry))
                    {
                        eaObject = actPackage;
                        break;
                    }
                }
            }
            return eaObject;
        }

        private static Object computeStoryNode(Object activity, SQLRepository repository, String pathEntry)
        {
            Object eaObject = activity;
            foreach (SQLElement activityNode in (eaObject as SQLElement).Elements)
            {
                if ((activityNode.Stereotype == SDMModelingMain.StoryNodeStereotype || activityNode.Stereotype == SDMModelingMain.StatementNodeStereotype) && activityNode.Name == getNameOfPathEntry(pathEntry))
                {
                    eaObject = activityNode;
                    break;
                }
            }
            return eaObject;
        }

        private static Object computeActivityEdge(Object parent, SQLRepository repository, String pathEntry)
        {
            object eaObject = parent;
            SQLElement sourceEdge = null;
            SQLElement targetEdge = null;
            foreach (SQLElement activityNode in (eaObject as SQLElement).Elements)
            {
                if (activityNode.Name == getNameOfConnectorEntry(pathEntry, "source"))
                    sourceEdge = activityNode;
                if (activityNode.Name == getNameOfConnectorEntry(pathEntry, "target"))
                    targetEdge = activityNode;
            }
            foreach (SQLConnector activityEdge in sourceEdge.Connectors)
            {
                if (activityEdge.ClientID == sourceEdge.ElementID && activityEdge.SupplierID == targetEdge.ElementID)
                {
                    eaObject = activityEdge;
                }
                if (activityEdge.ClientID == targetEdge.ElementID && activityEdge.SupplierID == sourceEdge.ElementID)
                {
                    eaObject = activityEdge;
                }
            }
            return eaObject;
        }

        private static Object computeLinkVariable(Object parent, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parent;
            SQLElement source = null;
            SQLElement target = null;
            foreach (SQLElement objectVariables in (eaObject as SQLElement).Elements)
            {
                if (objectVariables.Name == getNameOfConnectorEntry(pathEntry, "source"))
                    source = objectVariables;
                if (objectVariables.Name == getNameOfConnectorEntry(pathEntry, "target"))
                    target = objectVariables;
            }
            foreach (SQLConnector linkvariable in source.Connectors)
            {
                if (linkvariable.ClientID == source.ElementID && linkvariable.SupplierID == target.ElementID)
                {
                    eaObject = linkvariable;
                }
                if (linkvariable.ClientID == target.ElementID && linkvariable.SupplierID == source.ElementID)
                {
                    eaObject = linkvariable;

                }
            }
            return eaObject;
        }

        private static Object computeObjectVariable(Object parent, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parent;
            foreach (SQLElement objectVariables in (eaObject as SQLElement).Elements)
            {
                if (objectVariables.Name == getNameOfPathEntry(pathEntry))
                {

                    eaObject = objectVariables;
                }
            }
            return eaObject;
        }

        private static Object computeStartNode(object parent, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parent;
            foreach (SQLElement activityNode in (eaObject as SQLElement).Elements)
            {
                if (activityNode.Subtype == Main.EAStartNodeSubtype && activityNode.Name == getNameOfPathEntry(pathEntry))
                {
                    eaObject = activityNode;
                    break;
                }
            }
            return eaObject;
        }

        private static Object computeStopNode(Object parent, SQLRepository repository, String pathEntry)
        {
            Object eaObject = parent;
            foreach (SQLElement activityNode in (eaObject as SQLElement).Elements)
            {
                if (activityNode.Subtype == Main.EAStopNodeSubtype && activityNode.Name == getNameOfPathEntry(pathEntry))
                {
                    eaObject = activityNode;
                    break;
                }
            }
            return eaObject;
        }

        public static PropertyObject computeObjectFromPath(SQLRepository repository, String path)
        {
            PropertyObject moflonObject = new PropertyObject();
            Object eaObject = null;
            String[] splittedPath = path.Split('/');

            String lastType = "";
            String lastName = "";

            for (int i = 0; i < splittedPath.Length; i++)
            {
                String pathEntry = splittedPath[i];
                lastType = getTypeOfPathEntry(pathEntry);
                lastName = getNameOfPathEntry(pathEntry);

                String currentPath = "";
                for (int z = 0; z <= i; z++)
                {
                    currentPath += splittedPath[z];
                }

                if (foundObjects.Keys.Contains(currentPath))
                {
                    eaObject = foundObjects[currentPath];
                }

                else
                {

                    if (lastType == ECOREModelingMain.EPackageStereotype)
                        eaObject = computePackage(eaObject, repository, pathEntry);

                    else if (lastType == ECOREModelingMain.EClassStereotype)
                        eaObject = computeChildElement(eaObject, repository, pathEntry);

                    else if (lastType == ECOREModelingMain.EAttributeStereotype)
                        eaObject = computeAttribute(eaObject, repository, pathEntry);

                    else if (lastType.StartsWith(ECOREModelingMain.EOperationStereotype))
                        eaObject = computeOperation(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.ActivityType)
                        eaObject = computeActivity(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.StoryNodeStereotype)
                        eaObject = computeStoryNode(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.StopNodeStereotype)
                        eaObject = computeStopNode(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.StartNodeStereotype)
                        eaObject = computeStartNode(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.ObjectVariableStereotype)
                        eaObject = computeObjectVariable(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.LinkVariableStereotype)
                        eaObject = computeLinkVariable(eaObject, repository, pathEntry);

                    else if (lastType == SDMModelingMain.ActivityEdgeStereotype)
                        eaObject = computeActivityEdge(eaObject, repository, pathEntry);
                    else if (lastType == TGGModelingMain.TggSchemaPackageStereotype)
                    {
                        eaObject = computePackage(eaObject, repository, pathEntry);
                    }
                    else if (lastType == TGGModelingMain.TggRulePackageStereotype)
                    {
                        eaObject = computePackage(eaObject, repository, pathEntry);
                    }
                    else if (lastType == TGGModelingMain.TggRuleStereotype)
                    {
                        eaObject = computeChildElement(eaObject, repository, pathEntry);
                    }
                    else if (lastType == "Diagram")
                    {
                        eaObject = computeChildDiagram(eaObject, repository, pathEntry);
                    }

                    foundObjects.Add(currentPath, eaObject);

                }
            }

            moflonObject.EAObject = eaObject;
            moflonObject.TypeOfObject = lastType;
            moflonObject.NameOfObject = lastName;

            return moflonObject;
        }

        private static object computeChildDiagram(object eaObject, SQLRepository repository, string pathEntry)
        {
            if (eaObject is SQLElement)
            {
                SQLElement element = eaObject as SQLElement;
                if (element.Diagrams.Count > 0)
                {
                    return element.Diagrams.GetAt(0);
                }
            }
            else if (eaObject is SQLPackage)
            {
                SQLPackage package = eaObject as SQLPackage;
                if (package.Diagrams.Count > 0)
                {
                    return package.Diagrams.GetAt(0);
                }
            }

            return null;
        }

        private static object computeTGGSchemaPackage(object eaObject, SQLRepository repository, string pathEntry)
        {
            if (eaObject == null)
            {
                foreach (SQLPackage modelRoot in repository.Models)
                {
                    foreach (SQLPackage metamodelPackage in modelRoot.Packages)
                    {
                        if (metamodelPackage.Name == getNameOfPathEntry(pathEntry))
                        {

                            eaObject = metamodelPackage;
                            break;
                        }
                    }
                }
            }
            return null;
        }

        #endregion


        private static String getTypeOfPathEntry(String pathEntry)
        {
            String[] split = pathEntry.Split(':');
            if (split.Length > 0)
            {
                String type = split[split.Length - 1];
                return type;
            }
            return "";
        }

        private static List<String> getParametersOfOperationPathEntry(String operationPathEntry)
        {
            List<String> parameters = new List<string>();
            String[] split = operationPathEntry.Replace(")", String.Empty).Split('(');
            if (split.Length > 1)
            {
                String[] parameterSplit = split[1].Split(',');
                foreach (String param in parameterSplit)
                {
                    String[] splitType = param.Split(':');

                    parameters.Add(splitType[0]);
                }
            }
            return parameters;
        }

        public static PropertyLine readPropertyLine(String line)
        {
            String[] split = line.Split('=');
            String[] type = split[0].Split('.');
            
            if (type.Length > 1)
            {
                PropertyLine propLine = new PropertyLine();
                propLine.Type = type[1].Trim();
                propLine.Value = split[1].Trim();
                propLine.Count = ExtractIntFromString(type[0]);
                return propLine;
            }
            return null;
        }

        public static Int32 ExtractIntFromString(String s)
        {
            Int32 i = -1;
            String str;
            try
            {
                str = string.Join(null, System.Text.RegularExpressions.Regex.Split(s, "[^\\d]"));// convert type to int
                i = int.Parse(str);
                return i;
            }
            catch
            {
                return i;
            }
        } 

        private static String getNameOfPathEntry(String pathEntry)
        {
            String[] split = pathEntry.Split(':');
            String name = split[0];
            String[] split2 = name.Split('(');
            name = split2[0];
            return name;
        }
        private static String getNameOfConnectorEntry(String connectorEntry, String sourceOrTarget)
        {
            //source:Blabla_target:Blabla
            String[] split1 = connectorEntry.Split('_');
            if (sourceOrTarget == "source")
            {
                String[] split2 = split1[0].Split(':');
                return split2[1];
            }
            if (sourceOrTarget == "target")
            {
                String[] split2 = split1[1].Split(':');
                return split2[1];
            }
            return "";
        }

    }
}

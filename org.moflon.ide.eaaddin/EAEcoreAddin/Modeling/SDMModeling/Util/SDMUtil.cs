using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using System.Xml.Linq;
using System.IO;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System.Runtime.InteropServices;
using System.Threading;
using System.Diagnostics;
using EAEcoreAddin.Import;
using System.Drawing;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling
{
    class SDMUtil
    {
        public const int UNKNOWN_SDM_DIAGRAM_ID = 0;
        
        public static void setAllVerboseTags(Boolean toSet, SQLRepository repository,EA.Diagram currentDiagram)
        {
            EA.Diagram diagram = currentDiagram;
            if(diagram == null)
                diagram = repository.GetTreeSelectedObject() as EA.Diagram;
            repository.SaveDiagram(diagram.DiagramID);
            foreach (EA.DiagramObject diagObj in diagram.DiagramObjects)
            {
                SQLElement correspondingElement = repository.GetElementByID(diagObj.ElementID);
                if (correspondingElement.Stereotype == SDMModelingMain.ObjectVariableStereotype ||
                   correspondingElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype ||
                   correspondingElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                    EAEcoreAddin.Util.EAUtil.setTaggedValue(repository,correspondingElement.getRealElement(), Main.MoflonVerboseTaggedValueName, toSet.ToString());
            }
            foreach (EA.DiagramLink diagLink in diagram.DiagramLinks)
            {
                SQLConnector link = repository.GetConnectorByID(diagLink.ConnectorID);
                if(link.Stereotype == SDMModelingMain.LinkVariableStereotype ||
                   link.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
                    EAEcoreAddin.Util.EAUtil.setTaggedValue(repository,link.getRealConnector(), Main.MoflonVerboseTaggedValueName, toSet.ToString());
            }
            if (toSet)
            {
                diagram.Notes = diagram.Notes.Replace("Moflon::Verbose=false", "Moflon::Verbose=true");
            }
            else {
                diagram.Notes = diagram.Notes.Replace("Moflon::Verbose=true", "Moflon::Verbose=false");
            }
            if (!diagram.Notes.Contains("Moflon::Verbose"))
            {
                diagram.Notes += "Moflon::Verbose=true";
            }
            diagram.Update();
            repository.ReloadDiagram(diagram.DiagramID);
            
        }

        public static String extractNameFromURIString(String uriString)
        {
            String[] splittedBySlash = uriString.Split('/');
            return splittedBySlash[splittedBySlash.Length - 1];
        }

        

        public static List<ObjectVariable> getSDMObjectVariablesInActivity(Activity activity, SQLRepository repository)
        {
            List<ObjectVariable> ovs = new List<ObjectVariable>();
            String sdmOvQuery = repository.SQLQuery("select a.Name, max(a.Classifier) as Classifier from t_object a, t_object b, t_object c where ( a.Stereotype = '" + SDMModelingMain.ObjectVariableStereotype + "' ) and a.ParentID = b.Object_ID and b.ParentID = c.Object_ID and c.Object_ID = " + activity.SdmContainer.ElementID + "and (c.Stereotype = '" + SDMModelingMain.SdmContainerStereotype + "') group by a.Name");

            foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sdmOvQuery, "Row"))
            {
                SQLElement actOV = new SQLElement(repository, row);
                ObjectVariable ov = new ObjectVariable(actOV, repository);
                ovs.Add(ov);
            }
            return ovs;
        }

        public static List<ObjectVariable> getTGGObjectVariablesInTggRule(TGGRule tggRule, SQLRepository repository)
        {
            List<ObjectVariable> ovs = new List<ObjectVariable>();
            String tggOvQuery = repository.SQLQuery("select a.Name, max(a.Classifier) as Classifier from t_object a where a.Stereotype = '" + TGGModelingMain.TggObjectVariableStereotype + "' and a.ParentID = " + tggRule.rule.ElementID + " group by a.Name");

            foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(tggOvQuery, "Row"))
            {
                SQLElement actOV = new SQLElement(repository, row);
                TGGObjectVariable tggOv = new TGGObjectVariable(actOV, repository);
                ovs.Add(tggOv);
            }
            return ovs;
        }

        public static BindingSemantics computeBindingSemantics(String value)
        {
            try
            {
                BindingSemantics bS = (BindingSemantics)Enum.Parse(typeof(BindingSemantics), value.ToUpper());
                return bS;
            } 
            catch
            {
                return BindingSemantics.MANDATORY;
            }   
        }
        public static BindingState computeBindingState(String value)
        {
            try
            {
                BindingState bState = (BindingState)Enum.Parse(typeof(BindingState), value.ToUpper());
                return bState;
            }
            catch
            {
                return BindingState.UNBOUND;
            }
        }
        public static BindingOperator computeBindingOperator(String value)
        {
            try
            {
                BindingOperator bOp = (BindingOperator)Enum.Parse(typeof(BindingOperator), value.ToUpper());
                return bOp;
            } 
            catch
            {
                return BindingOperator.CHECK_ONLY;
            }
        }

        public static ActivityNode createCorrectActivityNode(SQLRepository rep, SQLElement eaElement)
        {
            if (eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
                return new StoryNode(rep, eaElement);
            else if (eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
                return new StatementNode(rep, eaElement);
            else if (eaElement.Stereotype == SDMModelingMain.StopNodeStereotype)
                return new StopNode(rep, eaElement);
            else if (eaElement.Stereotype == SDMModelingMain.StartNodeStereotype)
                return new StartNode(rep, eaElement);
            return null;
        }

        public static int getSDMDiagramID(SQLRepository repository, SQLMethod selectedOperation)
        {
            String sqlString = "select c.Diagram_ID from t_object a, t_objectproperties b, t_diagram c where a.Stereotype = '" + SDMModelingMain.SdmContainerStereotype + "' and b.Object_ID = a.Object_ID  and b.Property = 'associatedMethod' and b.Value = '" + selectedOperation.MethodGUID + "' and c.ParentID = a.Object_ID";
            String result = repository.SQLQuery(sqlString);
            String sdmDiagramID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Diagram_ID")[0];
            if (sdmDiagramID != "")
            {
                int diagramId = int.Parse(sdmDiagramID);
                return diagramId;
            }
            return UNKNOWN_SDM_DIAGRAM_ID;
        }

        public static void fillSDMActivityNodeDiagram(EA.Repository repository, EA.Diagram diagram, EA.Element parentElement)
        {
            fillSDMActivityNodeDiagram(repository, diagram, parentElement, null, null);

        }

        private static void fillSDMActivityNodeDiagram(EA.Repository repository, EA.Diagram diagram, EA.Element parentElement, List<Point> usedPoints, List<int> handledElementIds)
        {
            if (usedPoints == null)
            {
                usedPoints = new List<Point>();
            }
            if (handledElementIds == null)
            {
                handledElementIds = new List<int>();
            }

            EA.Element startNode = null;

            foreach (EA.Element element in parentElement.Elements)
            {
                if (element.Stereotype == SDMModelingMain.StartNodeStereotype)
                {
                    startNode = element;
                    break;
                }
            }
            if (startNode != null)
            {
                createSDMActivityNodeDiagramObject(repository, diagram, 0, 0, startNode, usedPoints, handledElementIds);
            }
        }

        private static void createSDMActivityNodeDiagramObject(EA.Repository repository, EA.Diagram diagram, int i, int j, EA.Element node, List<Point> usedPoints, List<int> handledElementIds)
        {
            if (node.MetaType == SDMModelingMain.ActivityNodeMetatype)
            {
                Point currentPoint = new Point(i, j);
                int width = StoryNode.DefaultWidth;
                int height = StoryNode.DefaultHeight;

                if (node.Stereotype == SDMModelingMain.StopNodeStereotype || node.Stereotype == SDMModelingMain.StartNodeStereotype)
                {
                    width = StopNode.DefaultWidth;
                    height = StopNode.DefaultHeight;
                }



                while (usedPoints.Contains(currentPoint))
                {
                    currentPoint.Y = currentPoint.Y + 1;
                }

                EA.DiagramObject dObj = diagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
                dObj.ElementID = node.ElementID;
                dObj.left = currentPoint.X * 200 + (100 - width / 2);
                dObj.right = dObj.left + width;
                dObj.top = currentPoint.Y * -200 + (-100 + height / 2);
                dObj.bottom = dObj.top - height;
                dObj.Update();

                String aa = " l = " + dObj.left + " r  =" + dObj.right + " t = " + dObj.top + " b = " + dObj.bottom;

                handledElementIds.Add(node.ElementID);
                usedPoints.Add(currentPoint);

                foreach (EA.Connector con in node.Connectors)
                {
                    if (con.SupplierID != node.ElementID && !handledElementIds.Contains(con.SupplierID))
                    {
                        EA.Element target = repository.GetElementByID(con.SupplierID);
                        createSDMActivityNodeDiagramObject(repository, diagram, currentPoint.X + 1, currentPoint.Y, target, usedPoints, handledElementIds);
                        currentPoint.Y = currentPoint.Y + 1;
                        currentPoint.X = currentPoint.X - 1;
                    }
                }
            }
        }

        public static void fillAllStoryNodeDiagrams(SQLRepository sqlRep, EA.Diagram diagram, EA.Element parentElement)
        {
            EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRep, parentElement, Main.MoflonExtractedStoryPatternTaggedValueName, "true");
            foreach (EA.Element child in parentElement.Elements)
            {
                fillSDMStoryNodeDiagram(sqlRep, diagram, child, new Point(1, 1), null, null, null);
            }
        }

        public static void fillSDMStoryNodeDiagram(SQLRepository sqlRepo, EA.Diagram diagram, EA.Element child, PointF p, List<int> handledElementIds, List<PointF> usedPoints, List<GraphPath> usedPaths)
        {
            handledElementIds = handledElementIds == null ? new List<int>() : handledElementIds;
            usedPoints = usedPoints == null ? new List<PointF>() : usedPoints;
            usedPaths = usedPaths == null ? new List<GraphPath>() : usedPaths;

            if (!handledElementIds.Contains(child.ElementID))
            {
                while (usedPoints.Contains(p))
                {
                    p.X = p.X + 2;
                    if (p.Y > 1)
                    {
                        p.Y = p.Y - 1;
                    }
                }

                try
                {

                    EA.DiagramObject dobject = diagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
                    dobject.left = (int)p.X * 125;
                    dobject.right = dobject.left + 100;
                    dobject.top = (int)p.Y * -125;
                    dobject.bottom = dobject.top - 50;
                    dobject.ElementID = child.ElementID;
                    dobject.Update();
                }
                catch (COMException e)
                {
                    String s = e.StackTrace;
                    int aa = child.ElementID;
                    int l = (int)p.X * 125;
                    int r = l + 100;
                    int t = (int)p.Y * -125;
                    int b = t - 50;

                }
                usedPoints.Add(p);
                handledElementIds.Add(child.ElementID);


                foreach (EA.Connector con in child.Connectors)
                {
                    EA.Element otherEnd = null;
                    if (con.SupplierID == child.ElementID)
                    {
                        otherEnd = sqlRepo.GetOriginalRepository().GetElementByID(con.ClientID);
                    }
                    else
                    {
                        otherEnd = sqlRepo.GetOriginalRepository().GetElementByID(con.SupplierID);
                    }
                    PointF p2 = new PointF();
                    p2.Y = p.Y % 2 == 0 ? p.Y + 1 : p.Y - 1;
                    p2.X = p.X % 2 == 0 ? p.X - 1 : p.X + 1;

                    checkPointPath(ref p2, usedPaths);

                    usedPaths.Add(new GraphPath(p, p2));

                    fillSDMStoryNodeDiagram(sqlRepo, diagram, otherEnd, p2, handledElementIds, usedPoints, usedPaths);
                }

            }
        }

        private static void checkPointPath(ref PointF p2, List<GraphPath> usedPaths)
        {
            foreach (GraphPath path in usedPaths)
            {
                float x = (p2.X - path.From.X) / (path.To.X - path.From.X);
                float y = (p2.Y - path.From.Y) / (path.To.Y - path.From.Y);
                if (y - x < 0.01 && y - x > -0.01)
                {
                    if (p2.Y > 100)
                    {
                        
                    }
                    p2.Y = p2.Y + 1;
                    p2.X = p2.X + 1;
                    checkPointPath(ref p2, usedPaths);
                }
            }
        }

        public static String computeStartNodeName(SQLMethod method, SQLElement containingEClass)
        {
            String result = "";

            String parameters = "";
            foreach (SQLParameter param in method.Parameters)
            {
                parameters += param.Name + ": " + param.Type + ", ";
            }
            if (parameters.Length > 0)
                parameters = parameters.Substring(0, parameters.Length - 2);
            result = containingEClass.Name + "::" + method.Name + " (" + parameters + "): " + method.ReturnType.ToString();
            return result;
        }

        internal static bool isActivityNodeMetatype(string p)
        {
            if (p == SDMModelingMain.StartNodeStereotype || p == SDMModelingMain.StopNodeStereotype || p == SDMModelingMain.StatementNodeStereotype || p == SDMModelingMain.StoryNodeStereotype)
            {
                return true;
            }
            return false;
        }


        public static void setSingleOvMceVisualizationTag(Boolean toSet,  EA.Element ov, SQLRepository rep)
        {
            if (toSet)
            {
                EAUtil.setTaggedValue(rep, ov, ObjectVariable.MceVisualizationTaggedValueName, "true");
            }
            else
            {
                EAUtil.setTaggedValue(rep, ov, ObjectVariable.MceVisualizationTaggedValueName, "false");

            }
        }


        public static string setOvMethodCallExpressionGui(ObjectVariable ov)
        {
            SQLTaggedValue mceVisTag = EAUtil.findTaggedValue(ov.sqlElement, ObjectVariable.MceVisualizationTaggedValueName);

            
            Expression BindingExpression = ov.BindingExpression;
            if (BindingExpression != null )

            {
                deletePossibleBindingConnectors(ov.sqlElement.getRealElement());

                if (BindingExpression is MethodCallExpression)
                {
                    var mcE = BindingExpression as MethodCallExpression;

                    ObjectVariableExpression firstOvExpression = null;

                    if (mcE.OwnedParameterBinding.Count == 1 && (mceVisTag == null || mceVisTag.Value == "true" ))
                    {

                        foreach (ParameterBinding paramBinding in mcE.OwnedParameterBinding)
                        {
                            if (paramBinding.ValueExpression is ObjectVariableExpression)
                            {
                                firstOvExpression = paramBinding.ValueExpression as ObjectVariableExpression;
                            }
                        }

                        if (firstOvExpression != null)
                        {

                            EA.Element firstOvEaElement = ov.Repository.GetOriginalRepository().GetElementByGuid(firstOvExpression.ObjectVariableGUID);

                            if (firstOvEaElement.ParentID == ov.sqlElement.ParentID)
                            {
                                EA.Connector bindingLink = firstOvEaElement.Connectors.AddNew("", Main.EADependencyType) as EA.Connector;
                                bindingLink.Stereotype = SDMModelingMain.BindingExpressionLinkStereotype;
                                bindingLink.Name = mcE.Target + "." + mcE.MethodName;
                                bindingLink.SupplierID = ov.sqlElement.ElementID;
                                bindingLink.Update();
                                EA.Element realElement = ov.sqlElement.getRealElement();
                                realElement.Connectors.Refresh();
                                realElement.Notes = "";
                                realElement.Update();

                                EAUtil.setTaggedValue(ov.Repository, realElement, ObjectVariable.BindingExpressionOutputTaggedValueName, "");
                              
                                return "";
                            }
                        }
                    }
                    
                              
                    
                }
                return BindingExpression.ToString();
            }
            return "";
        }

        internal static void setAllMceVisTags(bool p, SQLRepository repository, EA.Diagram currentDiagram)
        {
            EA.Diagram diagram = currentDiagram;
            if (diagram == null)
                diagram = repository.GetTreeSelectedObject() as EA.Diagram;
            repository.SaveDiagram(diagram.DiagramID);
            foreach (EA.DiagramObject diagObj in diagram.DiagramObjects)
            {
                EA.Element correspondingElement = repository.GetOriginalRepository().GetElementByID(diagObj.ElementID);
                if (correspondingElement.Stereotype == SDMModelingMain.ObjectVariableStereotype ||
                   correspondingElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype ||
                   correspondingElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                {
                    setSingleOvMceVisualizationTag(p, correspondingElement, repository);
                    ObjectVariable ov = new ObjectVariable(repository.GetElementByID(correspondingElement.ElementID), repository);
                    ov.loadTreeFromTaggedValue();
                    setOvMethodCallExpressionGui(ov);
                }

            }
        }

        private static void deletePossibleBindingConnectors(EA.Element ObjectVariableEA)
        {
            for (short i = 0; i < ObjectVariableEA.Connectors.Count; i++)
            {
                EA.Connector con = ObjectVariableEA.Connectors.GetAt(i) as EA.Connector;
                if (con.Stereotype == SDMModelingMain.BindingExpressionLinkStereotype && con.SupplierID == ObjectVariableEA.ElementID)
                {
                    ObjectVariableEA.Connectors.Delete(i);
                }
            }
            ObjectVariableEA.Connectors.Refresh();
        }

        public static SQLConnector getRelatedEReference(LinkVariable lv)
        {
            SQLRepository repository = lv.Repository;
            SQLConnector referencedReference = repository.GetConnectorByGuid(lv.linkDialogueEntry.CorrespondingConnectorGuid);
            if (referencedReference == null && lv.linkDialogueEntry.CorrespondingConnectorGuid != "")
            {
                try
                {
                    referencedReference = repository.GetConnectorByID(int.Parse(lv.linkDialogueEntry.CorrespondingConnectorGuid));
                }
                catch
                {
                }
            }
            if (referencedReference == null)
            {
                SQLConnectorTag guidOfRef = EAEcoreAddin.Util.EAUtil.findTaggedValue(lv.LinkVariableEA, "idOfReference");
                if (guidOfRef != null)
                {
                    referencedReference = repository.GetConnectorByGuid(guidOfRef.Value);
                    if (referencedReference == null)
                    {
                        try
                        {
                            referencedReference = repository.GetConnectorByID(int.Parse(guidOfRef.Value));
                        }
                        catch
                        {
                        }
                    }
                }
            }
            return referencedReference;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.ControlPanel.Navigation
{
    class Navigator
    {

        private static Dictionary<int, int> targetDiagramIdToSourceDiagramId = new Dictionary<int, int>();

        private static int lastAnchorSourceId = -1;

        public static int jumpToAnchorPredecessor(EA.Repository repository, int currentDiagramId)
        {
            SQLRepository sqlRepository = new SQLRepository(repository, false);

            if (targetDiagramIdToSourceDiagramId.ContainsKey(currentDiagramId))
            {
                int sourceDiagramId = targetDiagramIdToSourceDiagramId[currentDiagramId];
                repository.OpenDiagram(sourceDiagramId);
                lastAnchorSourceId = currentDiagramId;
                targetDiagramIdToSourceDiagramId.Remove(currentDiagramId);
                return currentDiagramId;
            }
            else
            {
                EA.Diagram diagramToOpen = null;
                EA.Diagram currentDiagram = repository.GetDiagramByID(currentDiagramId);


                if (currentDiagram != null)
                {
                    if (currentDiagram.ParentID != 0)
                    {
                        SQLElement parentElement = sqlRepository.GetElementByID(currentDiagram.ParentID);
                        List<String> diagramIds = EAUtil.getDiagramIDsOfObject(parentElement, sqlRepository);
                        while (diagramIds.Count == 1 && diagramIds[0] == "")
                        {
                            if(parentElement.ParentID != 0)
                                diagramIds = EAUtil.getDiagramIDsOfObject(sqlRepository.GetElementByID(parentElement.ParentID), sqlRepository);
                        }
                        diagramToOpen = repository.GetDiagramByID(int.Parse(diagramIds[0]));
                        
                    }
                    if(diagramToOpen == null)
                    {

                        EA.Package parentPackage = repository.GetPackageByID(currentDiagram.PackageID);
                        if (parentPackage.Diagrams.Count > 0)
                        {
                            EA.Diagram firstDiagram = parentPackage.Diagrams.GetAt(0) as EA.Diagram;
                            if (firstDiagram.DiagramID != currentDiagramId)
                            {
                                diagramToOpen = firstDiagram;
                            }
                            else
                            {
                                if (parentPackage.ParentID != 0)
                                {
                                    parentPackage = repository.GetPackageByID(parentPackage.ParentID);
                                    if (parentPackage.Diagrams.Count > 0)
                                    {
                                        EA.Diagram parentDiagram = parentPackage.Diagrams.GetAt(0) as EA.Diagram;
                                        if (parentDiagram.DiagramID != currentDiagramId)
                                        {
                                            diagramToOpen = parentDiagram;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (diagramToOpen != null)
                    {
                        lastAnchorSourceId = currentDiagramId;
                        repository.OpenDiagram(diagramToOpen.DiagramID);
                        return currentDiagramId;
                    }
                    
                }
            }
            return -1;
        }

        public static int jumpToAnchorSuccessor(EA.Repository repository, int currentDiagramId)
        {
            SQLRepository sqlRepository = new SQLRepository(repository, false);
            if (lastAnchorSourceId != -1)
            {
                repository.OpenDiagram(lastAnchorSourceId);
            }
            return -1;
        }

        public static void addAnchorEntry(int targetDiagramId, int sourceDiagramId)
        {
            setTargetSourceEntry(targetDiagramId, sourceDiagramId);
        }

        private static void setTargetSourceEntry(int targetDiagramId, int sourceDiagramId)
        {
            if (targetDiagramIdToSourceDiagramId.ContainsKey(targetDiagramId))
            {
                targetDiagramIdToSourceDiagramId[targetDiagramId] = sourceDiagramId;
            }
            else
            {
                targetDiagramIdToSourceDiagramId.Add(targetDiagramId, sourceDiagramId);
            }
        }


    }
}

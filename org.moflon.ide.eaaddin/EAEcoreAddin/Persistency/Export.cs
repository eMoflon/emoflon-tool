using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;
using System.IO;
using System.Threading;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Persistency.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Serialization.MocaTree;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Util;
using System.Text.RegularExpressions;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Refactoring;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Persistency
{
    public class Export
    {
        public SQLRepository Repository { get; set;}


        //.temp path of the current eap file
        String tempDirectoryPath;

        public MocaNode MocaTree { get; set; }
        public MocaNode ChangesTree { get; set; }

        public Boolean FullExport { get; set; }
        private Dictionary<String, EPackage> packageNameToEPackage = new Dictionary<string, EPackage>();
        
        private Dictionary<String, List<String>> dependencies = new Dictionary<string, List<string>>();
        public List<String> packageGuidsToExport = new List<string>();


        public static List<String> AutoUpdatePackages = new List<string>();

        public EcoreExport ecoreExport { get; set; }
        public TggExport tggExport { get; set; }
        public Changes changes { get; set; }

        public static Boolean ExportRunning = false;

       
        String fileName;
        public bool showStatusBar { get; set; }

        public Export(SQLRepository sqlrepository, Boolean fullExport, Boolean showStatusBar)
        {
            this.showStatusBar = showStatusBar;
            this.Repository = sqlrepository;
            this.FullExport = fullExport;
            this.ecoreExport = new EcoreExport(sqlrepository, this);
            this.tggExport = new TggExport(sqlrepository, this);
            this.changes = new Changes(sqlrepository, this);

            this.MocaTree = new MocaNode("exportedTree");
            this.ChangesTree = new MocaNode("changesTree");

            String rootDirectoryPath = Path.GetDirectoryName(sqlrepository.ConnectionString);
            this.tempDirectoryPath = Path.Combine(rootDirectoryPath, ".temp");

            String[] split = Path.GetDirectoryName(Repository.ConnectionString).Split("\\".ToCharArray());

            fileName = split[split.Length - 1];

            AutoUpdatePackages = new List<string>();

            SQLPackage selectedPackage = sqlrepository.GetPackageByID(sqlrepository.GetTreeSelectedPackage().PackageID);
            while (!selectedPackage.IsModel && selectedPackage.ParentID != 0)
            {
                selectedPackage = sqlrepository.GetPackageByID(selectedPackage.ParentID);
            }
            if (selectedPackage.ParentID == 0)
            {
                foreach (SQLPackage models in selectedPackage.Packages)
                {
                    this.packageGuidsToExport.Add(models.PackageGUID);
                }
            }
            else
            {
                this.packageGuidsToExport.Add(selectedPackage.PackageGUID);
            }

        }

        public Export(SQLRepository repository, Boolean fullExport)
            : this(repository, fullExport, true)
        {
        }

        private void writeDocumentToFile(XmlDocument docToWrite, String changesExtension)
        {
            String filePath = null;
            String currentValue = MocaTreeUtil.xmlDocumentToString(docToWrite);
            if (changesExtension != null)       
                filePath = Path.Combine(tempDirectoryPath, fileName + "." + changesExtension + ".moca.xmi");
            
            else
                filePath = Path.Combine(tempDirectoryPath, fileName + ".moca.xmi");

            String dirName = new FileInfo(filePath).DirectoryName;
            if (!(Directory.Exists(dirName)))
                Directory.CreateDirectory(dirName);

            if (File.Exists(filePath))
                File.Delete(filePath);
            
            File.AppendAllText(filePath, currentValue);
        }

        private void clearAndDeleteDirectory(String dirPath)
        {
            if(Directory.Exists(dirPath))
            {
                foreach (String filePath in Directory.GetFiles(dirPath))
                {
                    File.Delete(filePath);
                }
                Directory.Delete(dirPath);
            }
        }

        public void doExport()
        {
            ExportRunning = true;

            ExportProgressBar exportProgressBar = new ExportProgressBar();
            try
            {
                exportProgressBar.startExport(this);

                if (AutoUpdatePackages.Count > 0)
                {
                    String pkgStrings = String.Join("\n", AutoUpdatePackages.ToArray());
                    MessageBox.Show("Due to changes in the eMoflon plugin some projects have to be updated. Please retrieve the SCM locks for the following projects (and all subpackages) and retry the export: \n\n" + pkgStrings );
                }
            }
            catch (Exception e)
            {
                if(showStatusBar)
                    MessageBox.Show(EAUtil.formatErrorMessage(e));
                else
                {
                    Console.Out.WriteLine("EXCEPTION:" + e.StackTrace + "#");
                    Console.Out.WriteLine("ERROR:Something went wrong. Please check the validation messages and contact the eMoflon team if necessary (contact@emoflon.org)#");
                }
                ExportRunning = false;
            }
        }

        
        internal void enhanceMetamodelNodeWithDependencies(SQLPackage outermostPackage, MocaNode outermostPackageNode)
        {        
            var dependencyList = dependencies[outermostPackage.Name];

            MocaNode dependenciesNode = outermostPackageNode.appendChildNode("dependencies");
            
            if (dependencyList.Count != 0)
            {
                int counter = 0;
                foreach (String dependency in dependencyList)
                {

                    MetamodelHelper mmHelper = packageNameToEPackage[dependency].helper as MetamodelHelper;
                    if (!showStatusBar)
                    {
                        Console.Out.WriteLine("SCALE:Export Dependency '" + mmHelper.pluginID + "' %" + counter + "/" + dependencyList.Count + "#");
                    }
                    dependenciesNode.appendChildNode(mmHelper.pluginID);
                    counter++;
                }
            }
        }

        internal void exportPackage(SQLPackage projectPackage)
        {
            MocaNode outermostPackageNode = null;
            MocaNode outerMostPackageNodeChangesTree = null;

            foreach (SQLDiagram diagram in projectPackage.Diagrams)
            {

                //diagram is toplevel diagram
                if (diagram.ParentID == 0)
                {

                    if (ECOREModelingMain.EcoreDiagramMetatype.Contains(diagram.MetaType))
                    {
                        dependencies.Add(projectPackage.Name, new List<string>());
                        MocaNode ecoreTreeWithSDMs = ecoreExport.processOutermostPackage(projectPackage);
                        outermostPackageNode = ecoreTreeWithSDMs;
                        EPackage ePackage = new EPackage(projectPackage, Repository);
                        packageNameToEPackage.Add(projectPackage.Name, ePackage);

                        String workingSetName = computeRootNodeName(ePackage);
                        // export changes for metamodelevolution
                        if (!(projectPackage.Name.Equals("Ecore")) && !(projectPackage.Name.Equals("MocaTree")) && !(workingSetName.Equals("Dependencies"))) // we don't need the Ecore/MocaTree package in our changes tree
                        {
                            outerMostPackageNodeChangesTree = changes.processOutermostPackage(projectPackage);
                        }
                    
                        break;
                    }
                    else if (TGGModelingMain.TggSchemaDiagramMetatype.Contains(diagram.MetaType))
                    {
                        dependencies.Add(projectPackage.Name, new List<string>());
                        MocaNode tggTree = tggExport.processTggOutermostPackage(projectPackage);
                        outermostPackageNode = tggTree;
                        EPackage ePackage = new EPackage(projectPackage, Repository);
                        packageNameToEPackage.Add(projectPackage.Name, ePackage);
                        break;
                    }

                }
            }
            if (outermostPackageNode != null)
            {
                this.MocaTree.appendChildNode(outermostPackageNode);
                SQLTaggedValue moflonExportTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(projectPackage, MetamodelHelper.MoflonExportTaggedValueName);

                Boolean createProperyFile = true;

                if(moflonExportTag != null && moflonExportTag.Value == "false")
                    createProperyFile = false;

                if(!this.FullExport && !this.packageGuidsToExport.Contains(projectPackage.PackageGUID))
                    createProperyFile = false;

                if (createProperyFile)
                {
                    outermostPackageNode.appendChildAttribute(MetamodelHelper.MoflonExportTaggedValueName, "true");
                }
                else
                {
                    outermostPackageNode.appendChildAttribute(MetamodelHelper.MoflonExportTaggedValueName, "false");
                }
            }
            //track changes
            if (outerMostPackageNodeChangesTree != null)
            {
                this.ChangesTree.appendChildNode(outerMostPackageNodeChangesTree);
                SQLTaggedValue moflonChangesTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(projectPackage, MetamodelHelper.MoflonChangesTaggedValueName);

                Boolean createPropertyFile = true;

                if (moflonChangesTag != null && moflonChangesTag.Value == "false")
                    createPropertyFile = false;

                if (!this.FullExport && !this.packageGuidsToExport.Contains(projectPackage.PackageGUID))
                    createPropertyFile = false;

                if (createPropertyFile)
                {
                    outerMostPackageNodeChangesTree.appendChildAttribute(MetamodelHelper.MoflonChangesTaggedValueName, "true");
                }
                else
                {
                    outerMostPackageNodeChangesTree.appendChildAttribute(MetamodelHelper.MoflonChangesTaggedValueName, "false");
                }
            }
        }

        internal void finalize()
        {
            XmlDocument xmlDoc = MocaTreeUtil.createMocaXMLDoc();
            xmlDoc.DocumentElement.AppendChild(this.MocaTree.serializeToXmlTree(xmlDoc));
            writeDocumentToFile(xmlDoc, null);

            //track changes
            XmlDocument xmlRefactorDoc = MocaTreeUtil.createMocaXMLDoc();
            xmlRefactorDoc.DocumentElement.AppendChild(this.ChangesTree.serializeToXmlTree(xmlRefactorDoc));
            writeDocumentToFile(xmlRefactorDoc, "changes");

            ExportRunning = false;

        }


        internal void setWorker(System.ComponentModel.BackgroundWorker worker)
        {
            this.ecoreExport.backgroundWorker = worker;
            this.changes.backgroundWorker = worker;
            this.tggExport.backgroundWorker = worker;
        }

        public void addDependency(String metamodelName, String dependency)
        {
            if (dependency != "Ecore" && metamodelName != dependency)
                if (dependencies.ContainsKey(metamodelName) && dependency != "Ecore")
                {
                    var existingDepList = dependencies[metamodelName];
                    if (!existingDepList.Contains(dependency))
                        existingDepList.Add(dependency);
                }
                else
                {
                    var newList = new List<String>();
                    newList.Add(dependency);
                    dependencies.Add(metamodelName ,newList);
                }
        }

        public void computeAndAddToDependencies(SQLRepository repository, SQLElement element)
        {
            if (element != null)
            {
                SQLPackage pkg = repository.GetPackageByID(element.PackageID);
                while (!pkg.IsModel)
                {
                    pkg = repository.GetPackageByID(pkg.ParentID);
                }
                addDependency(CurrentMetamodelName, pkg.Name);
            }
        }

        public string CurrentMetamodelName { get; set; }

        internal void createDependencyTree()
        {
            for(int i = 0; i < dependencies.Keys.Count; i++)
            {
                String metamodelName = dependencies.Keys.ElementAt(i);
                dependencies[metamodelName] = computeSingleDependencyTree(metamodelName, new List<String>());
            }
        }

        private List<string> computeSingleDependencyTree(string metamodelName, List<string> alreadySeen)
        {
            var singleDependencyTree = new List<string>();

            if(!alreadySeen.Contains(metamodelName))
                alreadySeen.Add(metamodelName);

            foreach (String entry in dependencies[metamodelName])
            {
                if (!alreadySeen.Contains(entry))
                {
                    singleDependencyTree.Add(entry);
                    singleDependencyTree.AddRange(computeSingleDependencyTree(entry, alreadySeen));
                }
            }

            return singleDependencyTree;
        
        }

        private String computeRootNodeName(EPackage package)
        {
            SQLPackage curPackage = package.EaPackage;
            while (curPackage.ParentID != 0)
            {
                curPackage = package.Repository.GetPackageByID(curPackage.ParentID);
            }
            return curPackage.Name;
        }
    }
}

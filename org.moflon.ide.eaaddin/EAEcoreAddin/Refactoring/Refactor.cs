using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using System.ComponentModel;
using EAEcoreAddin.Persistency;
using EAEcoreAddin.Persistency.Util;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Refactoring
{
    public class Refactor
    {
        public MocaNode currentNode { get; set; }
        SQLRepository repository;
        public BackgroundWorker backgroundWorker { get; set; }

        public Refactor(SQLRepository repository, Export export)
        {
            this.repository = repository;
            //this.Export = export;            
        }

        public MocaNode processOutermostPackage(SQLPackage outermostPackage)
        {
            this.currentNode = new MocaNode();
            MocaNode outerMostPackageMocaNode = processEPackage(outermostPackage);

            return outerMostPackageMocaNode;
        }

        private MocaNode processEPackage(SQLPackage eaPackage)
        {
            //backgroundWorker.ReportProgress(0, PersistencyUtil.computePackageUri(eaPackage, repository));
            //SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonRefactorTreeTaggedValueName);

            //if (mocaTreeTag == null) 
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonExportTreeTaggedValueName);

            if (mocaTreeTag != null)
            {
                EPackage ePackage = new EPackage(eaPackage, repository);
                MocaNode ePackageMocaNode = MocaTreeUtil.mocaNodeFromXmlString(mocaTreeTag.Notes);
                ePackage.addAttributesDuringExport(ePackageMocaNode);

                this.currentNode.appendChildNode(ePackageMocaNode);

                foreach (SQLElement childClass in eaPackage.Elements)
                {
                    this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.ClassesChildNodeName);
                    if (childClass.Stereotype.ToLower() == ECOREModelingMain.EClassStereotype.ToLower())
                    {
                        processEClass(childClass);
                    }
                }

                foreach (SQLPackage childPackage in eaPackage.Packages)
                {
                    this.currentNode = ePackageMocaNode.getChildNodeWithName(EPackageHelper.PackagesChildNodeName);
                    processEPackage(childPackage);
                }

                return ePackageMocaNode;
            }
            return null;
        }

        public MocaNode processEClass(SQLElement eaClass)
        {
            SQLTaggedValue refactorTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaClass, Main.MoflonRefactorTreeTaggedValueName);

            if (refactorTreeTag != null)
            {
                EClass eClass = new EClass(eaClass, repository);
                eClass.loadTreeFromTaggedValue();
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(refactorTreeTag.Notes);

                eClass.addMocaAttributesDuringExport(eClassMocaNode);

                //add baseclass dependencies
                /*foreach (var baseClass in eClassMocaNode.getAttributeOrCreate("baseClasses").Value.Split(" ".ToArray()))
                {
                    if (baseClass != "")
                    {
                        SQLElement baseclass = repository.GetElementByGuid(baseClass);
                        Export.computeAndAddToDependencies(repository, baseclass);
                    }
                }*/

                this.currentNode.appendChildNode(eClassMocaNode);

                return eClassMocaNode;
            }
            return null;
        }
    }
}

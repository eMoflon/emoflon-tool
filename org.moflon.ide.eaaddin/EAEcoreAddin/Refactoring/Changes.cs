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
    public class Changes
    {
        public MocaNode currentNode { get; set; }
        SQLRepository repository;
        public BackgroundWorker backgroundWorker { get; set; }

        public Changes(SQLRepository repository, Export export)
        {
            this.repository = repository;
            //this.Export = export;            
        }

        public MocaNode processOutermostPackage(SQLPackage outermostPackage)
        {
            this.currentNode = new MocaNode();
            MocaNode outerMostPackageMocaNode = processEPackage(outermostPackage);
            if (outerMostPackageMocaNode != null)
                outerMostPackageMocaNode.appendChildAttribute("Changes::IsTLP", "true");

            return outerMostPackageMocaNode;
        }

        private MocaNode processEPackage(SQLPackage eaPackage)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonExportTreeTaggedValueName);
            if (mocaTreeTag != null)
            {

                EPackage ePackage = new EPackage(eaPackage, repository);
                MocaNode ePackageMocaNode = MocaTreeUtil.mocaNodeFromXmlString(mocaTreeTag.Notes);

                SQLTaggedValue changesTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaPackage, Main.MoflonChangesTreeTaggedValueName);
                if (changesTreeTag != null)
                {
                    MocaNode ePackageChangesMocaNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);
                    if (ePackageChangesMocaNode.hasAllAttributes(new List<string>(
                        new string[] { ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME, ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME })))
                    {
                        ePackage.addChangesAttributesDuringExport(ePackageMocaNode, changesTreeTag);
                    
                        CachedPackage temp = new CachedPackage();
                        temp.getPackage(eaPackage.PackageGUID, repository);
                        temp.name = eaPackage.Name;
                        temp.previousName = eaPackage.Name;
                        temp.packageName = ePackageChangesMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME).Value;
                        temp.projectName = ePackageChangesMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME).Value;
                        temp.savePackageToEATaggedValue(true);
                    }
                }

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
            SQLTaggedValue changesTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(eaClass, Main.MoflonChangesTreeTaggedValueName);

            if (changesTreeTag != null)
            {
                EClass eClass = new EClass(eaClass, repository);
                eClass.loadTreeFromTaggedValue();
                MocaNode eClassMocaNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);

                eClass.addMocaAttributesDuringExport(eClassMocaNode);

                this.currentNode.appendChildNode(eClassMocaNode);

                if (eClassMocaNode.hasAllAttributes(new List<string>(
                    new string[] { ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME, ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME })))
                {
                    String packageName = eClassMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME).Value;
                    String projectName = eClassMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME).Value;

                    CachedClass temp = new CachedClass();
                    temp.getElement(eaClass.ElementGUID, repository);
                    temp.name = eClass.Name;
                    temp.previousName = eClass.Name;
                    temp.packageName = packageName;
                    temp.projectName = projectName;
                    temp.saveElementToEATaggedValue(true);

                    return eClassMocaNode;
                }
            }
            return null;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Persistency.Util;

using EAEcoreAddin.Refactoring;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    public class EPackage : MocaTaggableElement
    {
        public EPackageHelper helper { get; set; }

        public SQLPackage EaPackage { get; set; }
        public String Guid { get; set; }

       

        public String NsURI
        {
            get { return helper.NsURI; }
        }

        public EPackage(SQLPackage eaPackage, SQLRepository repository)
        {
            this.EaPackage = eaPackage;
            this.Repository = repository;

            if (EAUtil.packageIsModel(EaPackage, Repository))
                helper = new MetamodelHelper(this);
            else
                helper = new SubPackageHelper(this);
        }

        public override void refreshSQLObject()
        {
            this.EaPackage = Repository.GetPackageByID(EaPackage.PackageID);
        }

        #region main methods

        public override void addAttributesDuringExport(MocaNode pkgNode)
        {
            helper.addAttributesDuringExport(pkgNode);
        }

        public void addChangesAttributesDuringExport(MocaNode pkgNode, SQLTaggedValue changesTreeTag)
        {
            MocaNode ePackageMocaNode = MocaTreeUtil.mocaNodeFromXmlString(changesTreeTag.Notes);

            string[] expectedAttributes = {ChangesTreeConstants.ATTRIBUTE_KEY_NAME, ChangesTreeConstants.ATTRIBUTE_KEY_PREVIOUS_NAME, ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME, ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME};
            if (ePackageMocaNode.hasAllAttributes(new List<string>(expectedAttributes)))
            {
                pkgNode.appendChildAttribute("Changes::Name", ePackageMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_NAME).Value);
                pkgNode.appendChildAttribute("Changes::PreviousName", ePackageMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PREVIOUS_NAME).Value);
                pkgNode.appendChildAttribute("Changes::PackageName", ePackageMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME).Value);
                pkgNode.appendChildAttribute("Changes::ProjectName", ePackageMocaNode.getAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME).Value);
                //pkgNode.appendChildAttribute("Changes::IsTLP", ePackageMocaNode.getAttribute("isTLP").Value);
            }
        }

        public override void doEaGuiStuff()
        {
            EA.Package realPackage = EaPackage.getRealPackage();
            if (realPackage.Element.Stereotype != ECOREModelingMain.EPackageStereotype)
            {
                realPackage.Element.StereotypeEx = ECOREModelingMain.EPackageStereotype;
                realPackage.Update();
            }
            updateToStandardTaggedValues();
        }

        public void updateToStandardTaggedValues()
        {
            helper.updateToStandardTaggedValues();
        }

        public override object getObjectToBeTagged()
        {
            return this.EaPackage;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode epackageNode = new MocaNode(ECOREModelingMain.EPackageStereotype);
            helper.serializeToMocaTree(epackageNode);
            return epackageNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            helper.deserializeFromMocaTree(actNode);
        }

        #endregion
    }

    public abstract class EPackageHelper
    {
        protected EPackage package;

        public static readonly String MoflonCustomNameTaggedValueName = "Moflon::Name";
        public static readonly String MoflonCustomNsUriTaggedValueName = "Moflon::NsUri";
        public static readonly String MoflonCustomNsPrefixTaggedValueName = "Moflon::NsPrefix";

        public static readonly String DEFAULT_VALUE_PLACEHOLDER = "__default__";

        public static readonly String PackagesChildNodeName = "packages";
        public static readonly String ClassesChildNodeName = "classes";

        public String Name { get; set; }
        public String NsURI { get; set; }
        public String NsPrefix { get; set; }

        public EPackageHelper(EPackage package)
        {
            this.package = package;
            updateToStandardTaggedValues();

            computeAttributes();
            package.Name = this.Name;
        }

        #region attribute handling

        internal virtual void computeAttributes()
        {
            this.Name = computeName();
            this.NsURI = computeNsUri();
            this.NsPrefix = computeNsPrefix();
        }

        protected String computeName()
        {
            SQLTaggedValue customProjectName = EAEcoreAddin.Util.EAUtil.findTaggedValue(package.EaPackage, MoflonCustomNameTaggedValueName);
            if (customProjectName != null)
            {
                String value = customProjectName.Value;
                //use default
                if (value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                {
                    return package.EaPackage.getRealPackage().Name;
                }
                //otherwise custom value
                return value;
            }
            else
            {
                return package.EaPackage.getRealPackage().Name;
            }
        }

        protected virtual String computeNsUri()
        {
            SQLTaggedValue customUri = EAEcoreAddin.Util.EAUtil.findTaggedValue(package.EaPackage, MoflonCustomNsUriTaggedValueName);
            if (customUri != null)
            {
                String value = customUri.Value;

                // use custom value 
                if (!value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                {
                    return value;
                }
            }
             
            // in all other cases calculate default
            String lastSegmentOfName = computeName().Split('.').Last();
            String capitalizedName = lastSegmentOfName.Substring(0, 1).ToUpper() + lastSegmentOfName.Substring(1);
            return "platform:/plugin/" + computeName() + "/model/" + capitalizedName + ".ecore";
        }

        private String computeNsPrefix()
        {
            SQLTaggedValue customNsPrefix = EAEcoreAddin.Util.EAUtil.findTaggedValue(package.EaPackage, MoflonCustomNsPrefixTaggedValueName);
            if (customNsPrefix != null)
            {
                String value = customNsPrefix.Value;

                //use default
                if (value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                {
                    return computeName();
                }
                //otherwise custom value
                return value;
            }
            else
            {
                return computeName();
            }
        }

        #endregion

        #region defaults

        internal virtual void updateToStandardTaggedValues()
        {
            // Create new set of tagged values if not already present and set to default value
            setDefaultValueIfNecessary(MoflonCustomNameTaggedValueName);
            setDefaultValueIfNecessary(MoflonCustomNsUriTaggedValueName);
            setDefaultValueIfNecessary(MoflonCustomNsPrefixTaggedValueName);
        }

        protected void setDefaultValueIfNecessary(String tagName)
        {
            SQLTaggedValue tag = EAUtil.findTaggedValue(package.EaPackage, tagName);
            if (tag == null)
                EAUtil.setTaggedValue(package.Repository, package.EaPackage.getRealPackage(), tagName, DEFAULT_VALUE_PLACEHOLDER);
        }

        #endregion

        #region persistence to moca tree

        internal virtual void addAttributesDuringExport(MocaNode pkgNode)
        {
            addAttributes(pkgNode);
        }

        private void addAttributes(MocaNode pkgNode)
        {
            pkgNode.appendChildAttribute(MoflonCustomNameTaggedValueName, computeName());
            pkgNode.appendChildAttribute(MoflonCustomNsPrefixTaggedValueName, computeNsPrefix());
            pkgNode.appendChildAttribute(MoflonCustomNsUriTaggedValueName, computeNsUri());

            removeObsoleteAttributes(pkgNode);
        }

        private void removeObsoleteAttributes(MocaNode pkgNode)
        {
            List<String> obsolete = new List<string> { 
                "name", 
                "nsURI", 
                "nsPrefix", 
                "validationFilter", 
                "validated", 
                "workingSet", 
                "export", 
                "mocaFragmentVersion"};
            pkgNode.Attributes.RemoveAll(attr => obsolete.Contains(attr.Name));
        }



        internal virtual void serializeToMocaTree(MocaNode pkgNode)
        {
            pkgNode.appendChildNode(ClassesChildNodeName);
            pkgNode.appendChildNode(PackagesChildNodeName);
            pkgNode.appendChildAttribute(Main.GuidStringName, package.EaPackage.PackageGUID);

            addAttributes(pkgNode);
        }

        internal virtual void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Name = actNode.getAttributeOrCreate(MoflonCustomNameTaggedValueName).Value;
            this.NsURI = actNode.getAttributeOrCreate(MoflonCustomNsUriTaggedValueName).Value;
            this.NsPrefix = actNode.getAttributeOrCreate(MoflonCustomNsPrefixTaggedValueName).Value;
            
            package.Name = this.Name;
        }

        #endregion

    }

    class SubPackageHelper : EPackageHelper
    {
        public SubPackageHelper(EPackage ePackage) : base(ePackage)
        {
            
        }
        override protected String computeNsUri()
        {
            SQLTaggedValue customUri = EAEcoreAddin.Util.EAUtil.findTaggedValue(package.EaPackage, MoflonCustomNsUriTaggedValueName);
            if (customUri != null)
            {
                String value = customUri.Value;

                //use default
                if (value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                {
                    return base.computeNsUri() + "#//" + PersistencyUtil.computePackageUri(package.EaPackage, package.Repository);
                }
                //otherwise custom value
                return value;
            }
            else
            {
                return base.computeNsUri() + "#//" + PersistencyUtil.computePackageUri(package.EaPackage, package.Repository);
            }
        }
    }

    class MetamodelHelper : EPackageHelper
    {
        public static readonly String MoflonCustomPluginIDTaggedValueName = "Moflon::PluginID";
        public static readonly String MoflonExportTaggedValueName = "Moflon::Export";
        public static readonly String MoflonChangesTaggedValueName = "Moflon::Changes";
        public static readonly String MoflonValidatedTaggedValueName = "Moflon::Validated";
        public static readonly String MoflonWorkingSetTaggedValueName = "Moflon::WorkingSet";

        
        

        public String pluginID { get; set; }
        public Boolean ExportPackage { get; set; }
        public Boolean Validated { get; set; }

        public String WorkingSetName { get; set; }

        public MetamodelHelper(EPackage package) : base(package)
        {
            
            
        }



        #region attribute handling

        override internal void computeAttributes()
        {
            base.computeAttributes();

            this.pluginID = computePluginID();
            this.ExportPackage = computeExportAttribute() == "true";
            this.Validated = computeValidatedAttribute() == "true";
            this.WorkingSetName = computeRootNodeName();
        }

        private string computeExportAttribute()
        {
            SQLTaggedValue exportTag = EAUtil.findTaggedValue(package.EaPackage, MoflonExportTaggedValueName);
            if (exportTag != null)
            {
                if (exportTag.Value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                    return "true";
                return exportTag.Value;
            }
            else
            {
                return "true";
            }
        }

        private string computeValidatedAttribute()
        {
            SQLTaggedValue validatedTag = EAUtil.findTaggedValue(package.EaPackage, MoflonValidatedTaggedValueName);
            if (validatedTag != null)
            {
                if (validatedTag.Value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                    return "false";
                return validatedTag.Value;
            }
            else
            {
                return "false";
            }
        }

        private String computeRootNodeName()
        {
            SQLPackage curPackage = package.EaPackage;
            while (curPackage.ParentID != 0)
            {
                curPackage = package.Repository.GetPackageByID(curPackage.ParentID);
            }
            return curPackage.Name;
        }

        private string computePluginID()
        {
            SQLTaggedValue customPluginID = EAEcoreAddin.Util.EAUtil.findTaggedValue(package.EaPackage, MoflonCustomPluginIDTaggedValueName);
            if (customPluginID != null)
            {
                String value = customPluginID.Value;

                //use default
                if (value.Equals(DEFAULT_VALUE_PLACEHOLDER))
                {
                    return computeName();
                }
                //otherwise custom value
                return value;
            }
            else
            {
                return computeName();
            }
        }

        #endregion

        #region defaults

        override internal void updateToStandardTaggedValues()
        {
            base.updateToStandardTaggedValues();

            setDefaultValueIfNecessary(MoflonCustomPluginIDTaggedValueName);
            setDefaultValueIfNecessary(MoflonExportTaggedValueName);
            setDefaultValueIfNecessary(MoflonValidatedTaggedValueName);
        }

        #endregion

        #region persistence to moca tree

        override internal void addAttributesDuringExport(MocaNode pkgNode)
        {
            base.addAttributesDuringExport(pkgNode);

            addAttributes(pkgNode);
        }

        private void addAttributes(MocaNode pkgNode){
            pkgNode.appendChildAttribute(MoflonCustomPluginIDTaggedValueName, computePluginID());
            pkgNode.appendChildAttribute(MoflonExportTaggedValueName, computeExportAttribute());
            pkgNode.appendChildAttribute(MoflonValidatedTaggedValueName, computeValidatedAttribute());
            pkgNode.appendChildAttribute(MoflonWorkingSetTaggedValueName, computeRootNodeName());
        }

        override internal void serializeToMocaTree(MocaNode epackageNode)
        {
            base.serializeToMocaTree(epackageNode);

            addAttributes(epackageNode);

        }
        
        override internal void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);

            this.Validated = actNode.getAttributeOrCreate(MoflonValidatedTaggedValueName).Value == "true";
            this.ExportPackage = actNode.getAttributeOrCreate(MoflonExportTaggedValueName).Value == "true";
            this.WorkingSetName = actNode.getAttributeOrCreate(MoflonWorkingSetTaggedValueName).Value;
            this.pluginID = actNode.getAttributeOrCreate(MoflonCustomPluginIDTaggedValueName).Value;


        }

        #endregion

    }
}

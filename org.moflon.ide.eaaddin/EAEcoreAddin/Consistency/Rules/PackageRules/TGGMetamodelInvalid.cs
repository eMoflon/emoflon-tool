using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGActions;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Collections;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class TGGMetamodelInvalid : PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository sqlRepository)
        {
            List<String> results = new List<string>();
            
            if (eaPackage.Element != null && eaPackage.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
            {
                TGG tggSchema = new TGG(sqlRepository, eaPackage);
                tggSchema.loadTreeFromTaggedValue();
                if(tggSchema.getDomain(DomainType.SOURCE) == null || tggSchema.getDomain(DomainType.TARGET) == null)
                {
                    results.Add("TGG source/target metamodel definition is invalid");
                }

                SQLPackage sourceMm = sqlRepository.GetPackageByGuid(tggSchema.getDomain(DomainType.SOURCE).MetamodelGuid);
                SQLPackage targetMm = sqlRepository.GetPackageByGuid(tggSchema.getDomain(DomainType.TARGET).MetamodelGuid);

                if (sourceMm == null || targetMm == null)
                    results.Add("TGG source/target metamodel definition is invalid");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                NewTGGProjectDialog dialog = new NewTGGProjectDialog(repository, eaPackage);
                dialog.ShowDialog();
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Open TGG Schema Package Dialog");
            return messages;
        }
    }
}

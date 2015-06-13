using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;

namespace EAEcoreAddin.Modeling.TGGModeling.Gui
{
    class TggOvExpressionProvider : OvExpressionProvider
    {
        public TggOvExpressionProvider(TGGObjectVariable ov, SQLRepository repository)
            : base(ov, repository)
        {
        }

        public override SQLElement getContainerElement()
        {
            SQLElement tggRuleElement = repository.GetElementByID(ov.sqlElement.ParentID);
            while (tggRuleElement.Stereotype != TGGModelingMain.TggRuleStereotype)
            {
                tggRuleElement = repository.GetElementByID(tggRuleElement.ParentID);
            }
            return tggRuleElement;
        }

        public override SDMModeling.Gui.IExpressionProvider getMainProvider()
        {
            return new TGGExpressionProvider(ov.StoryPattern as TGGRule, repository);
        }

    }
}

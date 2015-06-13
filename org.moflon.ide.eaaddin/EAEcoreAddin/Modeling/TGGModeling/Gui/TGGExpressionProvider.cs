using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.Gui;

namespace EAEcoreAddin.Modeling.TGGModeling.Gui
{
    class TGGExpressionProvider : CommonExpressionProvider
    {

        TGGRule tggRule;

        public TGGExpressionProvider(TGGRule tggRule, SQLRepository repository)
            : base(repository)
        {
            this.tggRule = tggRule;
        }

        public override SQLElement getContainerElement()
        {
            return tggRule.StoryPatternElement; 
        }

        public override List<string> getExpressionStringList()
        {
            List<String> baseList = base.getExpressionStringList();
            return baseList;
        }

        public override Expression getProviderExpression()
        {
            return null;   
        }


        public override IExpressionProvider getMainProvider()
        {
            return new TGGExpressionProvider(tggRule, repository);
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Common
{
    class SDMExpressionProvider : CommonExpressionProvider
    {

        Activity activity;

        public SDMExpressionProvider(Activity sdm, SQLRepository repository) : base (repository)
        {
            this.activity = sdm;
        }

        public override SQLElement getContainerElement()
        {
            return activity.SdmContainer;
        }

        public override SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            return null;   
        }


        public override IExpressionProvider getMainProvider()
        {
            return new SDMExpressionProvider(activity, repository);
        }
    }
}

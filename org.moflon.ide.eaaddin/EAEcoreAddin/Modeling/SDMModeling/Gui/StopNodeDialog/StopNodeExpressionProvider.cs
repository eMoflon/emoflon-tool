using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog
{
    class StopNodeExpressionProvider : CommonExpressionProvider
    {
        StopNode stopNode;

        public StopNodeExpressionProvider(StopNode stopNode, SQLRepository repository)
            : base(repository)
        {
            this.stopNode = stopNode;
            this.repository = repository;
        }

        public override SQLElement getContainerElement()
        {
            return this.stopNode.OwningActivity.SdmContainer;
        }


        public override SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            return this.stopNode.ReturnValue;
        }

        public IExpressionProvider getProviderCopy()
        {
            return new StopNodeExpressionProvider(stopNode, repository);
        }

        public override IExpressionProvider getMainProvider()
        {
            return new SDMExpressionProvider(new Activity(getContainerElement(), repository), repository);
        
        }
    }
}

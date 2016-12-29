using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.ActivityNodeDialog
{
    class StatementNodeExpressionProvider : CommonExpressionProvider
    {
        StatementNode sNode;

        public StatementNodeExpressionProvider(StatementNode sNode, SQLRepository repository)
            : base(repository)
        {
            this.sNode = sNode;
        }

        public override SQLElement getContainerElement()
        {
            return this.sNode.OwningActivity.SdmContainer;    
        }


        

        public override List<string> getExpressionStringList()
        {
            List<String> list = new List<string>();
            list.Add("MethodCallExpression");
            list.Add("Void");
            return list;
        }

        public override SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            return this.sNode.StatementExpression;
        }

        public  IExpressionProvider getProviderCopy()
        {
            return new StatementNodeExpressionProvider(sNode, repository);
        }

        public override IExpressionProvider getMainProvider()
        {
            return new SDMExpressionProvider(new Activity(getContainerElement(), repository), repository);
        
        }
    }
}

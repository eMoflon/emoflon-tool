using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using EAEcoreAddin.Modeling.CSP.Util;

namespace EAEcoreAddin.Modeling.CSP
{
    abstract class CSPExpressionProvider : CommonExpressionProvider
    {

        public CSPExpressionProvider(SQLRepository repository)
            : base(repository)
        {

        }

        public override List<string> getExpressionStringList()
        {
            List<String> list = base.getExpressionStringList();
            list.Remove("Void");
            list.Remove("MethodCallExpression");
            list.Remove("ObjectVariableExpression");
            list.Sort(new TggCspExpressionIComparer());
            return list;
        }

        

        public override SDMModeling.Gui.IExpressionProvider getMainProvider()
        {
            return null;
        }


        public override SDMModeling.SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            return null;
        }
    }
}

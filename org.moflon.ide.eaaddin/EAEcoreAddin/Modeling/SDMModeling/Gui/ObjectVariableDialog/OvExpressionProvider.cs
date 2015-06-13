using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog
{
    class OvExpressionProvider : CommonExpressionProvider
    {
        protected ObjectVariable ov;

        public OvExpressionProvider(ObjectVariable ov, SQLRepository repository)
            : base(repository)
        {
            this.ov = ov;
        }

        public override SQLElement getContainerElement()
        {
            SQLElement sdmContainer = repository.GetElementByID(this.ov.sqlElement.ParentID);
            while (sdmContainer.Stereotype != SDMModelingMain.SdmContainerStereotype)
            {
                sdmContainer = repository.GetElementByID(sdmContainer.ParentID);
            }
            return sdmContainer;
        }

        public override List<string> getExpressionStringList()
        {
            List<String> list = base.getExpressionStringList();
            list.Remove("Void");
            list.Insert(0, "None");
            return list;
        }


        public override SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            return this.ov.BindingExpression;
        }

        public  IExpressionProvider getProviderCopy()
        {
            return new OvExpressionProvider(ov, repository);
        }

        public override IExpressionProvider getMainProvider()
        {
            return new SDMExpressionProvider(new Activity(getContainerElement(), repository), repository);
        }
    }
}

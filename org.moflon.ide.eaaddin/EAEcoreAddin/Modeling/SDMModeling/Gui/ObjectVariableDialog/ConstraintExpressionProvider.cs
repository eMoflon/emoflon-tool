using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using EAEcoreAddin.Modeling.SDMModeling.Gui.Common;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog
{
    class AttributeConstraintExpressionProvider : CommonExpressionProvider
    {
        Object attributeConstraint;
        ObjectVariable ov;
        IExpressionProvider parentProvider;

        public AttributeConstraintExpressionProvider(Object constraint, ObjectVariable ov, SQLRepository repository, IExpressionProvider parentOvExpressionProvider) : base(repository)
        {
            this.ov = ov;
            this.parentProvider = parentOvExpressionProvider;
            this.attributeConstraint = constraint;
        }


        public override SDMExportWrapper.expressions.Expression getProviderExpression()
        {
            if (attributeConstraint is Constraint)
            {
                return ((attributeConstraint as Constraint).ConstraintExpression as ComparisonExpression).RightExpression;
            }
            else if (attributeConstraint is AttributeAssignment)
            {
                return (attributeConstraint as AttributeAssignment).ValueExpression;
            }
            return null;
        }

        public override SQLElement getContainerElement()
        {
            return this.parentProvider.getContainerElement();
        }


        public override IExpressionProvider getMainProvider()
        {
            return this.parentProvider.getMainProvider();
        }
    }
}

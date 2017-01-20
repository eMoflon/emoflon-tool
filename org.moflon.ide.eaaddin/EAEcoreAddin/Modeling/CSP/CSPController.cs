using EAEcoreAddin.Modeling.CSP.ExportWrapper;
using EAEcoreAddin.Modeling.CSP.Util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.CSP
{
    public abstract class CSPController
    {


        private List<CSPConstraint> cspConstraints;

        public CSPController()
        {
            this.cspConstraints = new List<CSPConstraint>();
        }



        internal List<CSPConstraint> getConstraints()
        {
            return cspConstraints;
        }

        internal void recomputeConstraintIndizes()
        {
            CSPUtil.recomputeConstraintIndizes(getConstraints());
        }

        internal void setCSPConstraints(List<CSPConstraint> cspConstraints) {
            this.cspConstraints = cspConstraints;

        }

        internal abstract SDMModeling.Gui.IExpressionProvider getExpressionProvider();

        internal abstract void saveConstraintContainer();
    }
}

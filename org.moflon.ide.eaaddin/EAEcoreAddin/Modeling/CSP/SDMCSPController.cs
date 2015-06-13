using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.CSP
{
    class SDMCSPController : CSPController
    {
        private EPackage package;

        public SDMCSPController(EPackage package)
        {
            this.package = package;
        }

        internal override SDMModeling.Gui.IExpressionProvider getExpressionProvider()
        {
            return new SDMCSPExpressionProvider(package.Repository);
        }

        internal override void saveConstraintContainer()
        {
            package.saveTreeToEATaggedValue(true);
        }
    }
}

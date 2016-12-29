using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.CSP
{
    class TGGCSPController : CSPController
    {
        private TGG tgg;        
    
        public TGGCSPController(TGG tgg) : base()
        {
            this.tgg = tgg;
            setCSPConstraints(tgg.Constraints);

        }

        internal override SDMModeling.Gui.IExpressionProvider getExpressionProvider()
        {
            return new TGGCSPExpressionProvider(tgg.Repository);
        }

        internal override void saveConstraintContainer()
        {
            tgg.saveTreeToEATaggedValue(true);
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Modeling.CSP
{
    class TGGCSPExpressionProvider : CSPExpressionProvider
    {


        public TGGCSPExpressionProvider(SQLRepository repository)
            : base(repository)
        {

        }

        public override SQLElement getContainerElement()
        {
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            SQLElement tggRuleElement = repository.GetElementByID(currentDiagram.ParentID);

            while (tggRuleElement.Stereotype != TGGModelingMain.TggRuleStereotype)
            {
                tggRuleElement = repository.GetElementByID(tggRuleElement.ParentID);
            }
            return tggRuleElement;
        }
    }
}

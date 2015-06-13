using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.Modeling.CSP
{
    class SDMCSPExpressionProvider : CSPExpressionProvider
    {

        public SDMCSPExpressionProvider(SQLRepository repository)
            : base(repository)
        {

        }

        public override SQLElement getContainerElement()
        {
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            SQLElement sdmContainerElement = repository.GetElementByID(currentDiagram.ParentID);

            while (sdmContainerElement.Stereotype != SDMModelingMain.SdmContainerStereotype)
            {
                sdmContainerElement = repository.GetElementByID(sdmContainerElement.ParentID);
            }
            return sdmContainerElement;
        }
    }
}

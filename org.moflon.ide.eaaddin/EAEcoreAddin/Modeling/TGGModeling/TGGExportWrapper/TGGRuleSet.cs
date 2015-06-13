using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Util;
using EAEcoreAddin.Serialization.MocaTree.Util;
using System.Xml;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    class TGGRuleSet
    {

        EA.Element eaRuleSet;
        SQLRepository Repository;


        public TGGRuleSet(EA.Element boundaryElement, SQLRepository repository)
        {
            this.Repository = repository;
            this.eaRuleSet = boundaryElement;
        }

    }
}

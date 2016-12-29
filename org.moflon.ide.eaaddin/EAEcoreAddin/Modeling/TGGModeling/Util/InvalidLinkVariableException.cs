using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    class InvalidLinkVariableException : Exception
    {
        EA.Connector ov;

        public InvalidLinkVariableException(EA.Connector ov)
        {
            this.ov = ov;
        }

        public override string Message
        {
            get
            {
                String baseMsg = base.Message;
                return "LinkVariable with ID '" + this.ov.Name + "' has to be updated manually. Use the EA search to find the connector. Doubleclick and accept with your desired properties.";
            }
        }

    }
}

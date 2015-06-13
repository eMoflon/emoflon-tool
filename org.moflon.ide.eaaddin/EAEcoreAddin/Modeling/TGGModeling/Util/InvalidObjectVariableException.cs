using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    class InvalidObjectVariableException : Exception
    {
        EA.Element ov;

        public InvalidObjectVariableException(EA.Element ov)
        {
            this.ov = ov;
        }

        public override string Message
        {
            get
            {
                String baseMsg = base.Message;
                return "ObjectVariable: '" + this.ov.Name + "' with ID: '" + ov.ElementID + "' has to be updated manually. Use the EA search to find the object. Doubleclick and accept with your desired properties.";
            }
        }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Server
{
    interface DataReceiveStrategy
    {
        void dataReveiced(List<String> receivedLines);
        Boolean isActive();
        void setIsActive(Boolean active);
    }
}

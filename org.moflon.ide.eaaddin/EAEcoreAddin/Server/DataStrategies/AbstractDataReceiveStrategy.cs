using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Server.DataStrategies
{
    abstract class AbstractDataReceiveStrategy : DataReceiveStrategy
    {
        private Boolean active;

        public abstract void dataReveiced(List<string> receivedLines);

        public bool isActive()
        {
            return active;
        }

        public void setIsActive(bool active)
        {
            this.active = active;
        }

    }
}

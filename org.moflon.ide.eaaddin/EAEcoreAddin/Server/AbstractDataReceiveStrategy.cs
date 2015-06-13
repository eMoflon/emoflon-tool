using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Server
{
    abstract class AbstractDataReceiveStrategy : DataReceiveStrategy
    {
        protected Byte[] buffer;
        protected int buffersize;

        public AbstractDataReceiveStrategy()
        {
            buffersize = 65536;
            buffer = new byte[buffersize];
        }


        public byte[] getBuffer()
        {
            return buffer;
        }

        abstract public void dataReveicedEvent();
    }
}

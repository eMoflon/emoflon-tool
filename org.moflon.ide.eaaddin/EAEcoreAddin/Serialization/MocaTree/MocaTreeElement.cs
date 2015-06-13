using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Serialization.MocaTree
{
    public abstract class MocaTreeElement : XmlTreeSerializableElement
    {
        public int Index { get; set; }
        public String Name { get; set; }   
    }

}

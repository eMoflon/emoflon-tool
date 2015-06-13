using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Util
{
    class SDMExportWrapperComparator : IComparer<MocaSerializableElement>
    {

        public int Compare(MocaSerializableElement x, MocaSerializableElement y)
        {
            return String.Compare(x.Name, y.Name);
        }
    }
}

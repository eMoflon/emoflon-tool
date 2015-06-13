using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Serialization
{
    /// <summary>
    /// Every implementing class has the possibility to serialize itself to a MocaTreeElement
    /// and deserialize itself from a MocaNode
    /// </summary>
    public abstract class MocaSerializableElement
    {

        public SQLRepository Repository { get; set; }
        public String Name { get; set; }
        public String Type { get; set; }

        /// <summary>
        /// Returns a new MocaTreeElement representing the current class
        /// </summary>
        /// <returns></returns>
        public abstract MocaNode serializeToMocaTree(MocaNode actNode);

        /// <summary>
        /// Fill the current class with the data from the given MocaNode
        /// </summary>
        /// <param name="actNode"></param>
        public abstract void deserializeFromMocaTree(MocaNode actNode);


        public virtual void addAttributesDuringExport(MocaNode actNode)
        {
        }

    }
}

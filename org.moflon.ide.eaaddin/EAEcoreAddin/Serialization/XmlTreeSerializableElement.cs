
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Serialization
{
    /// <summary>
    /// all classes implementing this class are able to serialize themselves to a xml tree
    /// </summary>
    public abstract class XmlTreeSerializableElement
    {

        /// <summary>
        /// Creates a new XmlElement according to the properties of the current class.
        /// </summary>
        /// <param name="xmlDocument">the XmlDocument the current xmlElement will be added to</param>
        /// <returns>the current object represented as XmlElement</returns>
        public abstract XmlElement serializeToXmlTree(XmlDocument xmlDocument);


        /// <summary>
        /// Reads the given XmlElement and sets the current properties
        /// </summary>
        /// <param name="mainNode">the XmlElement representing an instance of the current class</param>
        public abstract void deserializeFromXmlTree(XmlElement mainNode);

        
    }
}

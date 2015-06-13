using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Consistency.PropertyFile
{
    public class PropertyEntry
    {
        public PropertyObject MoflonObject { get; set; }
        public PropertyLine MessageLine { get; set; }
        public PropertyLine PathLine { get; set; }   
    }
}

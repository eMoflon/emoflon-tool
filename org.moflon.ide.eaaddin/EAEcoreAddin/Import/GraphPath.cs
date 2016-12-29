using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace EAEcoreAddin.Import
{
    class GraphPath
    {
        public PointF From { get; set; }
        public PointF To { get; set; }
        
        public GraphPath(PointF a, PointF b)
        {
            From = a;
            To = b;
        }

        
    }
}

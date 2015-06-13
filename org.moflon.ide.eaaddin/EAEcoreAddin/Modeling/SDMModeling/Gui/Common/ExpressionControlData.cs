using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui
{
    public class ExpressionControlData
    {

        public String Expression { get; set; }
        public String First { get; set; }
        public String Second { get; set; }
        public List<String> Parameters { get; set; }

        public ExpressionControlData(String Expression, String Object, String Source) : this()
        {
            this.Expression = Expression;
            this.First = Object;
            this.Second = Source;
        }

        public ExpressionControlData()
        {
            this.Parameters = new List<string>();
            this.Expression = "";
            this.First = "";
            this.Second = "";
        }

    }
}

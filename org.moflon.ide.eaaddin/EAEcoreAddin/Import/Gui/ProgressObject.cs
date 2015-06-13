using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Import.Gui
{
    class ProgressObject
    {
        public ProgressBarType Type { get; set; }
        public String Text { get; set; }
        public int Maximum { get; set; }


        public ProgressObject(ProgressBarType type, String text) : this(type, text, -1)
        {
        }

        public ProgressObject(ProgressBarType type, String text, int maximum)
        {
            this.Type = type;
            this.Text = text;
            this.Maximum = maximum;
        }

    }
}

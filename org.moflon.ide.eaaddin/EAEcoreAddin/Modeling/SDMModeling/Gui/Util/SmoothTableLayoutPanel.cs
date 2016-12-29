using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Util
{
    public class SmoothTableLayoutPanel : TableLayoutPanel
    {
        public SmoothTableLayoutPanel()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer | ControlStyles.UserPaint, true);
        }

    }
}

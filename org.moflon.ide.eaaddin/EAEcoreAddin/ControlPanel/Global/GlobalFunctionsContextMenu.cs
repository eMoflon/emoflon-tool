using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.Util;

namespace EAEcoreAddin.ControlPanel
{
    class GlobalFunctionsContextMenu :  ContextMenu
    {
        EA.Repository repository;
        
        public GlobalFunctionsContextMenu(EA.Repository repository)
        {
            this.repository = repository;

            MenuItem refreshEapItem = new MenuItem("Force Refresh EAP file");

            refreshEapItem.Click += new EventHandler(refreshEapItem_Click);

            MenuItems.Add(refreshEapItem);
        }

        private void refreshEapItem_Click(object sender, EventArgs e)
        {
            Main.eapUpdater.updateEAPIfNecessary(true);
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Server;

namespace EAEcoreAddin.ControlPanel
{
    public partial class TCPServerUserControl : UserControl
    {
        public TCPServerUserControl()
        {
            InitializeComponent();
        }

        internal void resetFunctions(EA.Repository Repository)
        {
            listBoxStatus.Items.Clear();
        }


        public void addStatusMessage(String message)
        {

            if (this.InvokeRequired)
            {
                this.Invoke(new Action(() => addStatusMessage(message)));
            }
            else
            {
                listBoxStatus.Items.Add(message);
            }
        }

        public void changePortDesription(int port)
        {
            if (InvokeRequired)
            {
                Invoke(new MethodInvoker(() => { changePortDesription(port); }));
            }
            else
            {
                textBoxPort.Text = "" + port;
                if (port != EMoflonTCPServer.DefaultPort)
                {
                    labelPortDescr.Text = @"This is not the default port since there are multiple opened instances 
                                        of EA. Use this port in Eclipse to be able to send debugging and validation information.";
                }
                else
                {
                    labelPortDescr.Text = "Default Port";
                }
            }
        }

        private void buttonClearStatusMessages_Click(object sender, EventArgs e)
        {
            listBoxStatus.Items.Clear();
        }
    }
}

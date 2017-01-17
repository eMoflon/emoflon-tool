using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Server.Tracing
{
    public partial class TracingDialog : Form
    {
        public Boolean Accepted { get; set; }

        public TracingDialog()
        {
            InitializeComponent();
            StartPosition = FormStartPosition.CenterScreen;
        }

        private void buttonYes_Click(object sender, EventArgs e)
        {
            Accepted = true;
            Close();
        }

        private void buttonNo_Click(object sender, EventArgs e)
        {
            Accepted = false;
            Close();
        }


    }
}

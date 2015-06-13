using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Persistency.Util
{
    public partial class UpdateEap : Form
    {
        public UpdateEap()
        {
            InitializeComponent();
            StartPosition = FormStartPosition.CenterScreen;
        }


        Boolean okPressed;

        public Boolean getOkPressed()
        {
            return okPressed;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.okPressed = true;
            Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.okPressed = false;
            Close();
        }
    }
}

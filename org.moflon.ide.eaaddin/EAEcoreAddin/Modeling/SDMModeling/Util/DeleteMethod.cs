using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling
{
    public partial class DeleteMethod : Form
    {
        private Boolean delete;
        public DeleteMethod(Boolean delete)
        {
            InitializeComponent();
            this.delete = delete;

        }
        public Boolean setVal()
        {
            return delete;
        }
        private void btOk_Click(object sender, EventArgs e)
        {
            delete = true;
            Close();
        }

        private void btCancel_Click(object sender, EventArgs e)
        {
            delete = false;
            Close();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}

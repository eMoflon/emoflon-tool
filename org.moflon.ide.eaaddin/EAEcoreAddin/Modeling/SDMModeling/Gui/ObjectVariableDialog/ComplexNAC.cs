using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog
{
    public partial class ComplexNAC : UserControl
    {
        public ComplexNAC()
        {
            InitializeComponent();
        }


        private void comboBoxComplexNacIndex_TextChanged(object sender, EventArgs e)
        {
            int value = 0;
            if (!(int.TryParse(comboBoxComplexNacIndex.Text, out value)))
            {
                if (comboBoxComplexNacIndex.Text.Length > 0)
                    comboBoxComplexNacIndex.Text = comboBoxComplexNacIndex.Text.Substring(0, comboBoxComplexNacIndex.Text.Length - 1);
                else
                    comboBoxComplexNacIndex.Text = "";
                comboBoxComplexNacIndex.Select(comboBoxComplexNacIndex.Text.Length, 0);
            }
        }

        internal void setNacIndexValue(string p)
        {
            comboBoxComplexNacIndex.Text = p;
        }

        internal string getNacIndexValue()
        {
            return comboBoxComplexNacIndex.Text;
        }
    }
}

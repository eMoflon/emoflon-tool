using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;

namespace EAEcoreAddin.Modeling.SDMModeling.ActivityEdgeDialog
{
    class GuardTypeComboBox : ComboBox
    {


        public GuardTypeComboBox()
        {
            //adding the options to the dropdownmenu
            /*
            this.Items.Add("None");
            this.Items.Add("Success");
            this.Items.Add("Failure");
            this.Items.Add("Each Time");
            this.Items.Add("End (for each)");*/
        }

        public void comboBoxFromEdgeGuard(EdgeGuard edgeGuard)
        {
            if (edgeGuard == EdgeGuard.NONE)
                this.Text = "None";
            else if (edgeGuard == EdgeGuard.SUCCESS)
                this.Text = "Success";
            else if (edgeGuard == EdgeGuard.FAILURE)
                this.Text = "Failure";
            else if (edgeGuard == EdgeGuard.END)
                this.Text = "End (for each)";
            else if (edgeGuard == EdgeGuard.EACH_TIME)
                this.Text = "Each Time";
        }

        public EdgeGuard edgeGuardFromComboBox()
        {
            EdgeGuard edgeGuard = EdgeGuard.NONE;
            if (this.Text == "End (for each)")
                edgeGuard = EdgeGuard.END;
            else if (this.Text == "None")
                edgeGuard = EdgeGuard.NONE;
            else if (this.Text == "Success")
                edgeGuard = EdgeGuard.SUCCESS;
            else if (this.Text == "Failure")
                edgeGuard = EdgeGuard.FAILURE;
            else if (this.Text == "Each Time")
                edgeGuard = EdgeGuard.EACH_TIME;
            return edgeGuard;
        }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace EAEcoreAddin.Import.Util
{
    class ImportUtil
    {



        public static String openMocaTreeChooseDialog(EA.Repository repository)
        {

            OpenFileDialog openMocaFileDialog = new OpenFileDialog();
            
            openMocaFileDialog.Filter = "eMoflon Export Tree|*.moca.xmi";
            String pathWithoutTemp = Path.GetDirectoryName(repository.ConnectionString);
            String pathWithTemp = pathWithoutTemp + "\\.temp";
            if (Directory.Exists(pathWithTemp))
                openMocaFileDialog.InitialDirectory = pathWithTemp;
            else
                openMocaFileDialog.InitialDirectory = pathWithoutTemp;
            openMocaFileDialog.AutoUpgradeEnabled = true;
            if (openMocaFileDialog.ShowDialog() == DialogResult.OK)
            {
                return openMocaFileDialog.FileName;
            }
            return "";
        }
    }
}

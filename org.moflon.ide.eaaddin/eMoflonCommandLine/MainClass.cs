using System;
using System.Collections.Generic;
using System.Linq;

namespace MOFLON2EAExportImportTest
{
    static class MainClass
    {
        [STAThread]
        static void Main(string[] args)
        {
            Moflon2Parser parser = new Moflon2Parser(args);
        }
    }
}

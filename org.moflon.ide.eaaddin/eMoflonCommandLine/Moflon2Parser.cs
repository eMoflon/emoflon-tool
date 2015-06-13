using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CommandLineParser.Arguments;
using System.IO;
using CommandLineParser.Exceptions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Persistency;
using EAEcoreAddin;
using EAEcoreAddin.Modeling.Util;
using System.Windows.Forms;
using EAEcoreAddin.Consistency;



namespace MOFLON2EAExportImportTest
{
    class Moflon2Parser
    {
        CommandLineParser.CommandLineParser parser = new CommandLineParser.CommandLineParser();

        ValueArgument<string> eapFile;
        ValueArgument<string> xmiFile;
        SwitchArgument export;
        SwitchArgument import;
        SwitchArgument codegen2compatibility;
        SwitchArgument validate;

        private const int EXPORT_EAP = 0;
        private const int IMPORT_EAP = 1;

        public Moflon2Parser(String[] args)
        {
            eapFile = new ValueArgument<string>('a', "eap","eap file");
            xmiFile = new ValueArgument<string>('x',"xmi","xmi file");
            export = new SwitchArgument('e', "export", false);
            import = new SwitchArgument('i', "import", false);
            codegen2compatibility = new SwitchArgument('c', "codegen2", false);
            validate = new SwitchArgument('v', "validate", false);

            parser.Arguments.Add(eapFile);
            parser.Arguments.Add(xmiFile);
            parser.Arguments.Add(export);
            parser.Arguments.Add(import);
            parser.Arguments.Add(codegen2compatibility);
            parser.Arguments.Add(validate);
            try
            {
                parser.ParseCommandLine(args);
            }
            catch (UnknownArgumentException e)
            {
                Console.Out.WriteLine(e.Message);  
            }
            doImport();
            doExport();

            if (validate.Value)
            {
                EA.Repository repository = null;
                String filename = eapFile.Value;
                repository = new EA.Repository();
                repository.OpenFile(filename);
                ConsistencyModule cM = new ConsistencyModule();
                cM.initializeRules();

                SQLRepository sqlRep = new SQLRepository(repository, false);

                cM.dispatchFullRulecheck(ref sqlRep, true);

            }
           
        }

        public void doExport()
        {
            if (export.Value)
            {    
                EA.Repository repository = null;
                try
                {
                    String filename = eapFile.Value; 
                    repository = new EA.Repository();
                    repository.OpenFile(filename);

                    EAPUpdater updater = new EAPUpdater(repository, false);
                    updater.updateEAPIfNecessary(false);

                    SQLRepository sqlRepository = new SQLRepository(repository, true, false);
                    Export exporter = new Export(sqlRepository, true, false);
                    exporter.doExport();

                    Console.Out.WriteLine("Export was successfull");

                    repository.Exit();

                }
                catch(Exception e)
                {
                    repository.Exit();
                    Console.Out.WriteLine("Export was not successfull " + "\n" + e.Message + "\n" + e.ToString() + "\n" + e.StackTrace);
                }   
            } 
        }

        public void doImport()
        {
            if (import.Value)
            {
                EA.Repository repository = null;
                try
                {
                    String xmiFilename = xmiFile.Value;
                    String eapFilename = eapFile.Value;
                    EAEcoreAddin.Main main = new EAEcoreAddin.Main();
                    repository = new EA.Repository();

                    if(File.Exists(eapFilename))
                        File.Delete(eapFilename);
                    repository.CreateModel(EA.CreateModelType.cmEAPFromBase, eapFilename, 0);
                    
                    ////open the empty eap
                    repository.OpenFile(eapFilename);

                    Console.Out.WriteLine("Import was successfull");
                }
                catch (Exception e)
                {
                    try
                    {
                        repository.Exit();
                    }
                    catch
                    {
                    }
                    Console.Out.Write("Import was not successfull. Reason: " + e.Message);
                }  
            }
        }


        



        private static String SelectEapFile(int flag)
        {
            OpenFileDialog dialog = new OpenFileDialog();
            dialog.Filter = "Enterprise Architect Model (*.EAP)|*.EAP|All files (*.*)|*.*";
            if(flag == EXPORT_EAP)
                dialog.Title = "Select .EAP File to Export";
            if(flag == IMPORT_EAP)
                dialog.Title = "Select .EAP File to Import";
            return (dialog.ShowDialog() == DialogResult.OK) ? dialog.FileName : null;
        }


    }
}

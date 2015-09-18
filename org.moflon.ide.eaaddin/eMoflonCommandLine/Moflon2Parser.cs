using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using CommandLineParser.Arguments;
using System.IO;
using System.Xml;
using CommandLineParser.Exceptions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Persistency;
using EAEcoreAddin;
using EAEcoreAddin.Import;
using EAEcoreAddin.Modeling.Util;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
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
        List<String> checkedMetamodelsToImport;

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
                Console.Out.WriteLine("EXCEPTION:"+e.Message);  
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

                    Console.Out.WriteLine("INFO:Export was successfull");

                    repository.Exit();

                }
                catch(Exception e)
                {
                    repository.Exit();
                    Console.Out.WriteLine("ERROR:Export was not successfull " + "\n" + e.Message + "\n" + e.ToString() + "\n" + e.StackTrace);
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
                    Console.Out.WriteLine("DEBUG:start import");
                    String xmiFilename = xmiFile.Value;
                    String eapFilename = eapFile.Value;
                    EAEcoreAddin.Main main = new EAEcoreAddin.Main();
                    repository = new EA.Repository();
                    repository.OpenFile(eapFilename);

                    

                    Console.Out.WriteLine("DEBUG:initialize importer");
                    SQLRepository sqlRepository = new SQLRepository(repository, false);
                    MainImport importer = MainImport.getInstance(sqlRepository, new BackgroundWorker());


                        Console.Out.WriteLine("DEBUG:getMocaTree Node");
                        MocaNode exportedTree = new MocaNode();
                        String readText = File.ReadAllText(xmiFilename);
                        XmlDocument mocaXmlDocument = XmlUtil.stringToXmlDocument(readText);
                        exportedTree.deserializeFromXmlTree(mocaXmlDocument.DocumentElement.FirstChild as XmlElement);

                        Console.Out.WriteLine("DEBUG:do import");
                        checkedMetamodelsToImport = new List<string>();
                        
                        MocaNode mocaTree = new MocaNode();
                        mocaTree.appendChildNode(exportedTree);
                        importer.startImport(exportedTree, checkedMetamodelsToImport, false);
                        ////open the empty eap
                        

                        Console.Out.WriteLine("INFO:Import was successfull");

                }
                catch (Exception e)
                {
                    Console.Out.Write("ERROR:Import was not successfull. Reason: " + e.Message);
                }
                finally
                {
                    try
                    {
                        repository.Exit();
                    }
                    catch
                    {
                    }
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

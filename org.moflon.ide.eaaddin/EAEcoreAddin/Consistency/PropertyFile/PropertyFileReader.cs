using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Consistency.RuleHandling;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Consistency.PropertyFile.Util;

namespace EAEcoreAddin.Consistency.PropertyFile
{
    class PropertyFileReader
    {
        

        public List<PropertyEntry> readPropertyFiles(SQLRepository repository)
        {
            List<PropertyEntry> propertyEntries = new List<PropertyEntry>();            
            foreach (EA.Package model in repository.Models)
            {
                foreach (EA.Package metamodel in model.Packages)
                {
                    propertyEntries.Add(readSinglePropertyFile(repository, metamodel));
                }
            }
            return propertyEntries;            
        }

        private PropertyEntry readSinglePropertyFile(SQLRepository repository, EA.Package metamodel)
        {
            String foldername = Path.GetDirectoryName(repository.ConnectionString);
            PropertyEntry newEntry = null;
            String fileName = metamodel.Name + ".validation.properties";
            String propertyFileName = Path.Combine(foldername, fileName);
            if (File.Exists(propertyFileName))
            {
                StreamReader streamReader = File.OpenText(propertyFileName);
                String line = "";
                while ((line = streamReader.ReadLine()) != null)
                {
                    if (line != "")
                    {
                        PropertyEntry propertyEntry = new PropertyEntry();
                        PropertyLine propLine = ValidationPropertyUtil.readPropertyLine(line);
                        propertyEntry.MessageLine = propLine;
                        if (propLine.Type == "message")
                        {
                            line = streamReader.ReadLine();
                            PropertyLine pathPropLine = ValidationPropertyUtil.readPropertyLine(line);
                            propertyEntry.PathLine = pathPropLine;
                        }
                        try
                        {
                            propertyEntry.MoflonObject = ValidationPropertyUtil.computeObjectFromPath(repository, propertyEntry.PathLine.Value);
                            newEntry = propertyEntry;
                        }
                        catch (NullReferenceException)
                        {
                            //something went wrong while computing the path
                            MessageBox.Show("Error occured while reading validation property file from eclipse. Maybe the .eap file has been changed lately - a new export build and validation should fix this problem");
                        }
                    }
                }
                streamReader.Close();
            }
            return newEntry;
        }

        

        
        

        

        
        
    }
}

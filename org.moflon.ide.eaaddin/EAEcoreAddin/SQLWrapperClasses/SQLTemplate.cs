using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.SQLWrapperClasses
{
    public class SQLTemplate
    {
        String xmlString;
        SQLRepository repository;
        public SQLTemplate(SQLRepository repository, String xmlString)
        {
            this.repository = repository;
            this.xmlString = xmlString;
        }


        private String[] Description
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlString, "Description")[0].Split(';');
            }
        }

        public String getDescriptionValue(String leftSide)
        {
            foreach (String entry in Description)
            {
                String[] splitted = entry.Split('=');
                if (splitted[0].ToLower() == leftSide.ToLower())
                    return splitted[1];
            }
            return "";
        }

        public String Name
        {
            get
            {
                return getDescriptionValue("Name");
            }
        }

        public String ActualName
        {
            get
            {
                return getDescriptionValue("ActualName");
            }
        }

        public String Type
        {
            get
            {
                return getDescriptionValue("Type");
            }
        }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLDiagram
    {
        String xmlDiagramString;
        SQLRepository repository;
        public SQLDiagram(SQLRepository repository, String xmlDiagramString)
        {
            this.repository = repository;
            this.xmlDiagramString = xmlDiagramString;
        }

        public int DiagramID
        {
            get 
            {
                return int.Parse( EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlDiagramString, "Diagram_ID")[0] );
            }
        }

        public string MetaType
        {
            get
            {
                String styleExString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlDiagramString, "StyleEx")[0];
                String[] splitStyleEx = styleExString.Split(';');
                String metatypeEntry = null;
                foreach (String entry in splitStyleEx)
                {
                    if (entry.Contains("MDGDgm="))
                    {
                        metatypeEntry = entry;
                    }
                }
                if (metatypeEntry != null && metatypeEntry.Contains("="))
                {
                    String[] metatypeSplit = metatypeEntry.Split('=');
                    String metaType = metatypeSplit[1];
                    return metaType;
                }
                return "";
            }
        }


        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlDiagramString, "Name")[0];
            }
        }


        public int ParentID
        {
            get
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlDiagramString, "ParentID")[0]);
            }
        }

        EA.Diagram realDiagram;
        internal EA.Diagram getRealDiagram()
        {
            if (realDiagram == null)
            {
                realDiagram = repository.GetOriginalRepository().GetDiagramByID(DiagramID);
            }
            return realDiagram;
        }
    }
}

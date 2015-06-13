using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Web;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLConnectorTag
    {

        SQLRepository repository;
        String xmlTaggedValue;

        public SQLConnectorTag(SQLRepository repository, String xmlTaggedValue)
        {
            this.repository = repository;
            this.xmlTaggedValue = xmlTaggedValue;
        }

        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlTaggedValue, "Property")[0];    
            }

        }

        public string Notes
        {
            get
            {
                String notes = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlTaggedValue, "NOTES")[0];
                notes = XmlUtil.XmlDecode(notes);
                return notes;
            }

        }


        public string Value
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlTaggedValue, "VALUE")[0];
            }
            set
            {
                throw new NotImplementedException();
            }
        }

    }
}

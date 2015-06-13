using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Web;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLAttribute
    {

        SQLRepository repository;
        String xmlAttributeEntry;
        EA.Attribute realAttribute;
        public SQLAttribute(String xmlAttributeEntry, SQLRepository repository)
        {
            this.repository = repository as SQLRepository;
            this.xmlAttributeEntry = xmlAttributeEntry;
        }

        public string AttributeGUID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "ea_guid")[0]; 
            }
        }

        public int AttributeID
        {
           
            get 
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "ID")[0]);
            }
        }

        public int ClassifierID
        {
            get
            {
                String id = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Classifier")[0];
                if (id == "")
                    return 0;
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Classifier")[0]);
            }
        }


        public string Containment
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Containment")[0]; 
            }
        }

        public string Default
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Default")[0]; 
            }
        }

        public bool IsDerived
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Derived")[0] == "1"; 
            }
        }

        public bool IsOrdered
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "IsOrdered")[0] == "1";  
            }
        }


        public string LowerBound
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "LowerBound")[0];   
            }
        }

        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Name")[0];
            }
        }

        public string Notes
        {
            get
            {
                return EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Notes")[0];
            }
        }



        public int ParentID
        {
            get 
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Object_ID")[0]);
            }
        }

        public string Stereotype
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Stereotype")[0];
            }
        }

        public string Type
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "Type")[0];
            }
        }


        public string UpperBound
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlAttributeEntry, "UpperBound")[0]; 
            }
        }


        public EA.Attribute getRealAttribute()
        {
            if (realAttribute == null)
            {
                realAttribute = repository.GetOriginalRepository().GetAttributeByID(AttributeID);
            }
            return realAttribute;
        }
    }
}

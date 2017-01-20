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
    public class SQLParameter
    {

        String xmlParameterString;
        SQLRepository repository;
        EA.Parameter parameter;

        public SQLParameter(SQLRepository repository, String xmlParameterString)
        {
            this.repository = repository;
            this.xmlParameterString = xmlParameterString;
        }

        public EA.Parameter getRealParameter()
        {
            if (parameter == null)
            {
                EA.Method parent = repository.GetOriginalRepository().GetMethodByID(this.OperationID);
                foreach(EA.Parameter actParam in parent.Parameters) {
                    if(actParam.Name == this.Name) {
                        this.parameter = actParam;
                    }
                }
            }
            return parameter;
        }



        public string ClassifierID
        {
            get
            {
                return (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlParameterString, "Classifier")[0]);
            }
        }


        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlParameterString, "Name")[0];
            }
        }


        public int OperationID
        {
            get 
            {
                String parent = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlParameterString, "OperationID")[0];
                return int.Parse(parent);
            
            }
        }

        public string ParameterGUID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlParameterString, "ea_guid")[0];
            }
        }


        public string Type
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlParameterString, "Type")[0];
            }
        }

    }
}

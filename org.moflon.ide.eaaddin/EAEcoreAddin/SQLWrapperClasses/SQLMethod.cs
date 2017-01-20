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
    public class SQLMethod
    {

        SQLRepository repository;
        String xmlMethodEntry;
        EA.Method realMethod;
        public SQLMethod(String xmlMethodEntry, SQLRepository repository)
        {
            this.repository = repository as SQLRepository;
            this.xmlMethodEntry = xmlMethodEntry;
        }

        public EA.Method getRealMethod()
        {
            if (realMethod == null)
            {
                realMethod = repository.GetOriginalRepository().GetMethodByID(MethodID);
            }
            return realMethod;
        }



        public string ClassifierID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "Classifier")[0];
            }
        }


        public string MethodGUID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "ea_guid")[0];
            }
        }

        public int MethodID
        {
            get 
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "OperationID")[0]); 
            }
        }

        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "Name")[0];
            }
        }

        public SQLCollection<SQLParameter> Parameters
        {
            get 
            {
                SQLCollection<SQLParameter> parameters = new SQLCollection<SQLParameter>();
                String[] paramss = null;
                if (repository.FullDatabaseCheckout)
                {
                    paramss = repository.t_operationparamsOperationID.GetValues(MethodID.ToString());
                }
                else
                {
                    String sqlqry = repository.SQLQuery("select * from t_operationparams where OperationID = " + MethodID);
                    List<String> paramets = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlqry, "Row");
                    if (!(paramets.Count  == 0) && !( paramets[0] == ""))
                    {
                        paramss = paramets.ToArray();    
                    }
                        
                }
                if (paramss == null)
                    return parameters;

                
                for (int i = 0; parameters.Count != paramss.Length; )
                {
                    foreach (String param in paramss)
                    {
                        if (param != "")
                        {
                            int pos = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(param, "Pos")[0]);
                            if (pos == i)
                            {
                                SQLParameter actParam = new SQLParameter(repository, param);
                                parameters.AddNew(actParam);

                            }
                        }
                    }
                    i++;
                }
                
                return parameters;
            }
        }

        public int ParentID
        {
            get 
            {
                if (xmlMethodEntry != "")
                    return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "Object_ID")[0]);
                else
                    return 0;
            }
        }






        public string ReturnType
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlMethodEntry, "Type")[0];
            }
        }
    }
}

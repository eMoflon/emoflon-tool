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
    public class SQLPackage
    {

        String xmlPackageString;
        SQLRepository repository;
        EA.Package originalPackage;
        public SQLPackage(SQLRepository repository, String xmlPackageString)
        {
            this.xmlPackageString = xmlPackageString;
            this.repository = repository;
        }


        public SQLCollection<SQLDiagram> Diagrams
        {
            get
            {
                SQLCollection<SQLDiagram> diagrams = new SQLCollection<SQLDiagram>();
                String[] diags = null;
                if (repository.FullDatabaseCheckout)
                {
                    diags = repository.t_diagramPackageID.GetValues(PackageID.ToString());
                }
                else
                {
                    String sqlString = repository.SQLQuery("select * from t_diagram where Package_ID = " + PackageID);
                    diags = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Row").ToArray(); ;
                }
                if (diags == null)
                    return diagrams;
                foreach (String diag in diags)
                {
                    if (diag != "")
                    {
                        SQLDiagram childDiag = new SQLDiagram(repository, diag);
                        diagrams.AddNew(childDiag);
                    }
                }
                return diagrams;
            }
        }

        private SQLElement element;

        public SQLElement Element
        {
            get
            {
                if (this.element != null)
                {
                    return this.element;
                }

                String[] element = null;
                if (ParentID != 0)
                {
                    if (repository.FullDatabaseCheckout)
                    {
                        element = repository.t_objectPDATA1.GetValues(PackageID.ToString());
                    }
                    else
                    {
                        String pkcElemQry = repository.SQLQuery("select * from t_object where PDATA1 = '" + PackageID + "'");
                        element = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(pkcElemQry, "Row").ToArray();
                    }
                    if (element != null)
                    {
                        if (element[0] != "")
                        {
                            SQLElement newElement = new SQLElement(repository, element[0]);
                            this.element = newElement;
                            return newElement;
                        }
                    }
                }
                return null;
            }
        }

        public SQLCollection<SQLElement> Elements
        {
            get
            {

                SQLCollection<SQLElement> elements = new SQLCollection<SQLElement>();

                String[] childs = null;
                if (repository.FullDatabaseCheckout)
                {
                    childs = repository.t_objectPackageID.GetValues(PackageID.ToString());
                }
                else
                {
                    String childQry = repository.SQLQuery("select * from t_object where Package_ID = " + PackageID);
                    childs = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(childQry, "Row").ToArray();
                }
                if (childs != null)
                {
                    foreach (String entry in childs)
                    {
                        if (entry != "")
                        {
                            String parentID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(entry, "ParentID")[0] as String;
                            if (parentID == "0")
                            {
                                SQLElement newElement = new SQLElement(repository, entry);
                                elements.AddNew(newElement);
                            }
                        }
                    }
                }
                return elements;

            }
        }



        public bool IsModel
        {
            get
            {
                if (this.ParentID != 0)
                {
                    SQLPackage parent = repository.GetPackageByID(this.ParentID);
                    if (parent.ParentID == 0)
                        return true;
                }
                return false;
            }
        }

        public bool IsNamespace
        {
            get
            {
                if (ParentID == 0)
                {
                    return false;
                }
                else
                {
                    SQLPackage parentPackage = repository.GetPackageByID(this.ParentID);
                    if (parentPackage.ParentID == 0)
                        return true;
                }

                return false;
            }
        }

        public string Name
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlPackageString, "Name")[0];
            }
        }





        public string PackageGUID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlPackageString, "ea_guid")[0];
            }

        }

        public int PackageID
        {
            get
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlPackageString, "Package_ID")[0]);

            }
        }
        SQLCollection<SQLPackage> packages;
        public SQLCollection<SQLPackage> Packages
        {
            get
            {
                if (packages != null)
                {
                    return packages;
                }
                else
                {
                    packages = new SQLCollection<SQLPackage>();
                    String[] pkgs = null;
                    if (repository.FullDatabaseCheckout)
                    {
                        pkgs = repository.t_packageParentID.GetValues(PackageID.ToString());
                    }
                    else
                    {
                        String modelQry = repository.SQLQuery("select * from t_package where Parent_ID = " + PackageID);
                        pkgs = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(modelQry, "Row").ToArray();
                    }

                    if (pkgs == null)
                        return packages;

                    foreach (String package in pkgs)
                    {
                        if (package != "")
                        {
                            SQLPackage childPackage = new SQLPackage(repository, package);
                            packages.AddNew(childPackage);
                        }
                    }
                    return packages;
                }
            }
        }


        private int parentID = -1;
        public int ParentID
        {
            get
            {
                if (parentID != -1)
                {
                    return parentID;
                }
                else
                {
                    parentID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlPackageString, "Parent_ID")[0]);
                    return parentID;
                }
            }
        }








        public void VersionControlAdd(string ConfigGuid, string XMLFile, string Comment, bool KeepCheckedOut)
        {
            throw new NotImplementedException();
        }

        public void VersionControlCheckin(string Comment)
        {
            throw new NotImplementedException();
        }

        public void VersionControlCheckinEx(string Comment, bool PreserveCrossPkgRefs)
        {
            throw new NotImplementedException();
        }

        public void VersionControlCheckout(string Comment)
        {
            throw new NotImplementedException();
        }

        public void VersionControlGetLatest(bool ForceImport)
        {
            throw new NotImplementedException();
        }

        public int VersionControlGetStatus()
        {
            throw new NotImplementedException();
        }

        public void VersionControlPutLatest(string Comment)
        {
            throw new NotImplementedException();
        }

        public void VersionControlRemove()
        {
            throw new NotImplementedException();
        }

        public void VersionControlResynchPkgStatus(bool ClearSettings)
        {
            throw new NotImplementedException();
        }

        public string XMLPath
        {
            get
            {
                throw new NotImplementedException();
            }
            set
            {
                throw new NotImplementedException();
            }
        }

        public void SetReadOnly(bool ReadOnly, bool IncludeSubPkgs)
        {
            throw new NotImplementedException();
        }

        public EA.Package getRealPackage()
        {
            if (originalPackage == null)
            {
                originalPackage = repository.GetOriginalRepository().GetPackageByID(PackageID);
            }
            return originalPackage;
        }
    }
}
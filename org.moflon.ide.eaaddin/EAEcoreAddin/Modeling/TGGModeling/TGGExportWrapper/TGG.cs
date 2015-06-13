using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;
using EAEcoreAddin.Modeling.CSP.Util;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    public class TGG : EPackage
    {
        public List<TGGRule> TGGRules { get; set; }
        public List<Metamodel> Metamodels { get; set; }
        public List<Domain> Domains { get; set; }


        public static readonly String ConstraintsChildNodeName = "constraints";
        public static readonly String DeclarationsChildNodeName = "DECLARATIONS";
        public static readonly String ConstraintInstancesChildNodeName = "CONSTRAINTS";

        internal virtual List<CSPConstraint> Constraints { get; set; }

        public TGG(SQLRepository repository, SQLPackage tggSchemaPackage) : base(tggSchemaPackage, repository)
        {
            this.Name = tggSchemaPackage.Name;
            TGGRules = new List<TGGRule>();
            Metamodels = new List<Metamodel>();
            Domains = new List<Domain>();

            Constraints = new List<CSPConstraint>();
            CSPUtil.addDefaultConstraints(Constraints);
        }


        public override void addAttributesDuringExport(MocaNode pkgNode)
        {
            base.addAttributesDuringExport(pkgNode);

            List<CSPConstraint> tempList = new List<CSPConstraint>();
            CSPUtil.addDefaultConstraints(tempList);

            MocaNode constraintRootNode = pkgNode.getChildNodeWithName(ConstraintsChildNodeName);
            if (constraintRootNode != null)
            {
                MocaNode declarationsRootNode = constraintRootNode.getChildNodeWithName(DeclarationsChildNodeName);
                if (declarationsRootNode != null)
                {
                    foreach (CSPConstraint constr in tempList)
                    {
                        MocaNode constraintNode = declarationsRootNode.appendChildNode("");
                        constr.serializeToMocaTree(constraintNode);
                    }
                }
            }



        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {

            MocaNode tggNode = base.serializeToMocaTree();
            tggNode.Name = "TGG";

            MocaNode domainsNode = tggNode.appendChildNode("domains");
            
            MocaNode metamodelsNode = tggNode.appendChildNode("metamodels");

            

            foreach (Metamodel metamodel in this.Metamodels)
            {
                MocaNode metamodelNode = metamodelsNode.appendChildNode("Metamodel");
                metamodel.serializeToMocaTree(metamodelNode);
            }
            foreach (Domain domain in this.Domains)
            {
                MocaNode domainNode = domainsNode.appendChildNode("Domain");
                domain.serializeToMocaTree(domainNode);
            }

            this.Constraints.Sort(new CSPConstraintComparer());


            MocaNode constraintsNode = tggNode.appendChildNode(ConstraintsChildNodeName);
            MocaNode declarationsNode = constraintsNode.appendChildNode(DeclarationsChildNodeName);
            MocaNode constraintInstancesNode = constraintsNode.appendChildNode(ConstraintInstancesChildNodeName); 

            //only persist user defined constraints
            foreach (CSPConstraint constr in this.Constraints)
            {
                if (constr.UserDefined)
                {
                    MocaNode constraintNode = declarationsNode.appendChildNode("");
                    constr.serializeToMocaTree(constraintNode);
                }
            }

            return tggNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);
            
            MocaNode domainsNode = actNode.getChildNodeWithName("domains");
            MocaNode metamodelsNode = actNode.getChildNodeWithName("metamodels");
            Name = EaPackage.Name;

            if (metamodelsNode != null)
            {
                foreach (MocaNode mmNode in metamodelsNode.Children)
                {
                    Metamodel mm = new Metamodel();
                    mm.deserializeFromMocaTree(mmNode);
                    this.Metamodels.Add(mm);
                }
            }

            if (domainsNode != null)
            {
                foreach (MocaNode dNode in domainsNode.Children)
                {
                    Domain mm = new Domain();
                    mm.deserializeFromMocaTree(dNode);
                    this.Domains.Add(mm);

                }
            }

            loadCSPConstraints(actNode);
        }


        #region csps


        private void loadCSPConstraints(MocaNode actNode)
        {
            MocaNode constraintsNode = actNode.getChildNodeWithName(ConstraintsChildNodeName);
            if (constraintsNode != null)
            {
                Constraints.Clear();
                foreach (MocaNode constraintNode in actNode.getChildNodeWithName(ConstraintsChildNodeName).Children)
                {
                    if (constraintNode.Name == DeclarationsChildNodeName)
                    {
                        foreach (MocaNode constraintNodeReal in constraintNode.Children)
                        {
                            CSPConstraint newConstr = new CSPConstraint();
                            newConstr.deserializeFromMocaTree(constraintNodeReal);
                            this.Constraints.Add(newConstr);
                        }
                    }
                    else if (constraintNode.Name == ConstraintInstancesChildNodeName)
                    {

                    }
                    else
                    {
                        CSPConstraint newConstr = new CSPConstraint();
                        newConstr.deserializeFromMocaTree(constraintNode);
                        this.Constraints.Add(newConstr);
                    }
                        
                }
                
            }
            CSPUtil.addDefaultConstraints(Constraints);
            this.Constraints.Sort(new CSPConstraintComparer());
            CSPUtil.recomputeConstraintIndizes(Constraints);
        }

        #endregion


        public Domain getDomain(DomainType type)
        {
            foreach (Domain domain in this.Domains)
            {
                if (domain.DomainType == type)
                    return domain;
            }
            return null;
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Package newTGGProject = EAUtil.sqlEAObjectToOriginalObject(Repository, this.EaPackage) as EA.Package;
            newTGGProject.Element.StereotypeEx = TGGModelingMain.TggSchemaPackageStereotype;
            newTGGProject.Update();
            newTGGProject.Element.Update();



        }

        

    }
}

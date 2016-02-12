﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    //TODO@settl: --> CachedNamedElement
    class CachedClass : CachedElement
    {
        public string previousName;
        public string name;
        public string packageName;

        /*public override void cache()
        {
            if (this.element != null)
            {
                if (!(this.element.Name.Equals(this.previousName)))
                {
                    this.previousName = this.element.Name;
                }
            }
        }*/

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode eclassNode = new MocaNode("EClass");     
            eclassNode.appendChildAttribute("name", this.name);
            eclassNode.appendChildAttribute("previousName", this.previousName);
            eclassNode.appendChildAttribute("packageName", this.packageName);
            return eclassNode;
        }

    }
}
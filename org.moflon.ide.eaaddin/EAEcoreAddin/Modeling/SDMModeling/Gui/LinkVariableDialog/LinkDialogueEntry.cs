using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog
{
    public class LinkDialogueEntry
    {
        public String CorrespondingConnectorGuid = "";
        public LinkDialogueEntryDirection direction = LinkDialogueEntryDirection.RIGHT;
        public String output = "";
        public String OutputOpposite = "";
        public String clientRoleName = "";
        public String supplierRoleName = "";
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Refactoring
{
    enum ElementType
    {
        PACKAGE = 0, 
        CLASS = 1,
        CONNECTOR = 2,
        ATTRIBUTE = 3,
        METHOD = 4
    }

    class RenameElementChange
    {
        public string changeType = "RENAMING";
        public string internalID;
        public ElementType elementType;
        public string name;
    }

    class CreateElementChange
    {
        public string changeType = "CREATION";
        public string internalID;
        public ElementType elementType;
        public string name;
    }

    class DeleteElementChange
    {
        public string changeType = "DELETION";
        public string internalID;
        public ElementType elementType;
        public string name;
    }
}

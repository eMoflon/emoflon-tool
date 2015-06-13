using EAEcoreAddin.SQLWrapperClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Consistency.Rules
{
    public class RuleResult
    {
        public Object EaObject  { get; set; }
        public String TypeOfObject { get; set; }
        public String RuleID { get; set; }
        public String NameOfObject { get; set; }
        public String ErrorOutput { get; set; }
        public RuleErrorLevel ErrorLevel { get; set; }
        public EA.ObjectType ObjectType { get; set; }
        public int ObjectID { get; set; }
        public Boolean Passed { get; set; }
        public Rule Rule { get; set; }

        public RuleResult()
        {
            TypeOfObject = "";
            RuleID = "";
            NameOfObject = "";
            ErrorOutput = "";

        }

        public string getFullErrorMessage()
        {
            //NavigableEndMustHaveName: - <Name of EReference> (EReference): Navigable end must have a role name - Fatal      
            String outputForSingleRule = RuleID + ": - " + NameOfObject + "(" + TypeOfObject + "): " + ErrorOutput + " - " + ErrorLevel.ToString();
            return outputForSingleRule;
        }

        public Boolean resultEqual(RuleResult otherResult)
        {
            Boolean val = true;
            val &= ObjectType == otherResult.ObjectType;
            val &= NameOfObject == otherResult.NameOfObject;
            val &= ErrorOutput == otherResult.ErrorOutput;
            val &= ErrorLevel == otherResult.ErrorLevel;
            val &= RuleID == otherResult.RuleID;
            val &= TypeOfObject == otherResult.TypeOfObject;
            val &= ObjectID == otherResult.ObjectID;
            val &= Passed == otherResult.Passed;
            val &= ObjectID == otherResult.ObjectID;
            return val;
        }

    }
}

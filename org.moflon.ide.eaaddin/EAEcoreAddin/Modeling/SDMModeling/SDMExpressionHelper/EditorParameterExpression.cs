using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public class EditorParameterExpression : EditorExpression
    {
        public override List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository)
        {
            List<FirstObject> targetParameters = new List<FirstObject>();
            SQLMethod associatedMethod = null;
            if (elementToSearch.Stereotype == SDMModelingMain.SdmContainerStereotype)
            {
                SQLTaggedValue assoMethodTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(elementToSearch, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                associatedMethod = repository.GetMethodByGuid(assoMethodTag.Value);
            }

            else if (elementToSearch.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                if (elementToSearch.Methods.Count > 0)
                    associatedMethod = elementToSearch.Methods.GetAt(0);
            }

            if (associatedMethod != null)
            {
                foreach (SQLParameter parameter in associatedMethod.Parameters)
                {
                    targetParameters.Add(new FirstObject(parameter));
                }
            }

            targetParameters.Sort(new EAObjectIComparer());
            return targetParameters;
        }

        public override List<SecondObject> getSecondObjects(Object targetObject, SQLRepository repository)
        {
            return new List<SecondObject>();
        }
    }
}

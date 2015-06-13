using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;
using System.Collections;

namespace EAEcoreAddin.Consistency.Util
{
    public class ConsistencyUtil
    {
        private static readonly String[] javaKeywords = {"abstract", "assert", "boolean", "break", "byte", "case",
                                                         "catch", "char", "class", "const", "continue", "default",
                                                         "do", "double", "else", "enum", "extends", "final",
                                                         "finally", "float", "for", "goto", "if", "implements",
                                                         "import", "instanceof", "int", "interface", "long", "native",
                                                         "new", "package", "private", "protected", "public", "return",
                                                         "short", "static", "strictfp", "super", "switch", "System", "synchronized",
                                                         "throw", "throws", "transient", "try", "void", "volatile",
                                                         "while" };


        public static Boolean nameIsKeyword(String name)
        {
            return javaKeywords.Contains(name);
        }

        public static Boolean isValidConstantName(String name)
        {
            string pattern = @"^[A-Z_][A-Z0-9_]*$";
            Regex regex = new Regex(pattern);
            return regex.IsMatch(name);
        }

        public static Boolean isValidTGGRuleName(String name)
        {
            string pattern = @"^[a-zA-Z_][a-zA-Z0-9_]*$";
            Regex regex = new Regex(pattern);
            return regex.IsMatch(name);
        }

        public static Boolean isValidActivityNodeName(String name)
        {
            if (name == null || name == "")
                return true;
            string pattern = @"^[\sa-zA-Z_-][\sa-zA-Z0-9_-]*$";
            Regex regex = new Regex(pattern);
            return regex.IsMatch(name);
        }

        public static Boolean checkAttributeAssignment(SQLElement ovElement, AttributeAssignment attrAssignment, SQLRepository repository)
        {
            SQLAttribute attribute = repository.GetAttributeByGuid(attrAssignment.AttributeGUID);
            //attribute not existing
            if (attribute == null)
                return false;


            Boolean attributeParentValid = checkIfClassifierContainsAttribute(repository.GetElementByGuid(attrAssignment.OvGuid), repository, attribute);
            if (!attributeParentValid)
                return attributeParentValid;

            return checkExpression(ovElement, attrAssignment.ValueExpression, repository);
        }

        private static bool checkIfClassifierContainsAttribute(SQLElement ovElement, SQLRepository repository, SQLAttribute attribute)
        {
            SQLElement ovClassifier = repository.GetElementByID(ovElement.ClassifierID);


            //assignment was created while ov had an other classifier
            Boolean attributeParentValid = false;
            foreach (SQLElement actBaseClass in EAUtil.getBaseClasses(ovClassifier))
            {
                if (attribute.ParentID == actBaseClass.ElementID)
                    attributeParentValid = true;
            }
            return attributeParentValid;
        }

        public static Boolean checkConstraint(SQLElement ovElement, Constraint constraint, SQLRepository repository)
        {
            return checkExpression(ovElement, constraint.ConstraintExpression, repository);
        }

        /// <summary>
        /// checks if the the parameter can be referenced from the object 
        /// parameter is in the same sdm as the object)
        /// </summary>
        /// <param name="referencedParameter"></param>
        /// <param name="containingElement"></param>
        /// <param name="repository"></param>
        /// <returns></returns>
        public static Boolean checkParameterCanBeReferenced(SQLParameter referencedParameter, SQLElement containingElement, SQLRepository repository)
        {
            SQLElement containerOfOwningObject = containingElement;
            while (containerOfOwningObject.Stereotype != SDMModelingMain.SdmContainerStereotype && containerOfOwningObject.Stereotype != TGGModelingMain.TggRuleStereotype)
                containerOfOwningObject = repository.GetElementByID(containerOfOwningObject.ParentID);

            if (containerOfOwningObject.Stereotype == SDMModelingMain.SdmContainerStereotype)
            {
                SQLMethod referencedMethodOfSDM = repository.GetMethodByGuid(EAEcoreAddin.Util.EAUtil.findTaggedValue(containerOfOwningObject, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value);
                if (referencedMethodOfSDM != null)
                {
                    if (referencedParameter.OperationID != referencedMethodOfSDM.MethodID)
                        return false;
                }
            }

            else if (containerOfOwningObject.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                SQLMethod method = repository.GetMethodByID(referencedParameter.OperationID);
                if (method.ParentID != containerOfOwningObject.ElementID)
                    return false;
            }
            return true;
        }

        public static Boolean compareSDMContainerOfSDMElements(SQLElement firstElement, SQLElement secondElement, SQLRepository repository)
        {
            SQLElement containerOfOwningObject = firstElement;



            while (containerOfOwningObject.Stereotype != SDMModelingMain.SdmContainerStereotype && containerOfOwningObject.Stereotype != TGGModelingMain.TggRuleStereotype)
                containerOfOwningObject = repository.GetElementByID(containerOfOwningObject.ParentID);

            SQLElement containerOfreferencedObject = secondElement;

            while (containerOfreferencedObject.Stereotype != SDMModelingMain.SdmContainerStereotype && containerOfreferencedObject.Stereotype != TGGModelingMain.TggRuleStereotype)
                containerOfreferencedObject = repository.GetElementByID(containerOfreferencedObject.ParentID);

            return containerOfOwningObject.ElementGUID == containerOfreferencedObject.ElementGUID;
        }

        public static Boolean checkExpression(SQLElement containingElement, Expression expression, SQLRepository sqlRepository)
        {
            if (expression is AttributeValueExpression)
            {
                AttributeValueExpression aVe = expression as AttributeValueExpression;
                return checkAttributeValueExpression(containingElement, sqlRepository, aVe);

            }
            else if (expression is MethodCallExpression)
            {
                MethodCallExpression mCe = expression as MethodCallExpression;
                return checkMethodCallExpression(containingElement, sqlRepository, mCe);

            }
            else if (expression is ObjectVariableExpression)
            {
                ObjectVariableExpression oVe = expression as ObjectVariableExpression;
                return checkObjectVariableExpression(containingElement, sqlRepository, oVe);
            }
            else if (expression is ParameterExpression)
            {
                return checkParameterExpression(containingElement, expression as ParameterExpression, sqlRepository);
            }
            else if (expression is ComparisonExpression)
            {
                ComparisonExpression compExp = expression as ComparisonExpression;
                return checkComparisonExpression(containingElement, sqlRepository, compExp);
            }

            else if (expression is LiteralExpression)
            {
                LiteralExpression litExpression = expression as LiteralExpression;
                return null == checkLiteralExpression(containingElement, litExpression, sqlRepository);
            }
            else if (expression is ComparisonExpression)
            {
                return checkComparisonExpression(containingElement, expression as ComparisonExpression, sqlRepository);
            }

            return true;
        }

        /**
         * Checks the given LiteralExpression
         * returns an error message if the literal expression is invalid or else null if the check was successful
         */
        public static String checkLiteralExpression(SQLElement containingElement, LiteralExpression litExpression, SQLRepository sqlRepository)
        {
            String value = litExpression.Value;

            if (!isConstantLiteral(value))
                return "Only constants are supported as literal expressions";

            return null;
        }

        public static bool isConstantLiteral(string value)
        {
            if (isCharacterLiteral(value))
                return true;

            if (isStringLiteral(value))
                return true;


            if (isNumberLiteral(value))
                return true;

            if (isEnumLiteral(value))
                return true;

            return false;
        }

        public static bool isEnumLiteral(string value)
        {
            string pattern = "^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*$";
            return new Regex(pattern).IsMatch(value);
        }

        public static bool isCharacterLiteral(string value)
        {
            string pattern = "^'.'$";
            return new Regex(pattern).IsMatch(value);
        }

        public static bool isStringLiteral(string value)
        {
            string pattern = "^\".*\"$";
            return new Regex(pattern).IsMatch(value);
        }

        public static bool isNumberLiteral(string value)
        {
            string numberPattern = @"^[-]?(0x)?[0-9]*([.][0-9]+)?([dfLl])?$";
            return new Regex(numberPattern).IsMatch(value);
        }

        private static bool checkComparisonExpression(SQLElement containingElement, ComparisonExpression expression, SQLRepository sqlRepository)
        {
            return checkExpression(containingElement, expression.LeftExpression, sqlRepository) &&
                   checkExpression(containingElement, expression.RightExpression, sqlRepository);
        }

        private static bool checkComparisonExpression(SQLElement containingElement, SQLRepository sqlRepository, ComparisonExpression compExp)
        {
            Boolean leftValid = checkExpression(containingElement, compExp.LeftExpression, sqlRepository);
            Boolean rightValid = checkExpression(containingElement, compExp.RightExpression, sqlRepository);
            if (!leftValid || !rightValid)
                return false;
            else
                return true;
        }

        private static bool checkParameterExpression(SQLElement containingElement, ParameterExpression pExp, SQLRepository sqlRepository)
        {
            SQLParameter referencedParameter = sqlRepository.GetParameterByGuid(pExp.ParameterGUID);
            if (referencedParameter == null)
                return false;
            if (pExp.ParameterNameOld != pExp.ParameterName)
                return false;
            return checkParameterCanBeReferenced(referencedParameter, containingElement, sqlRepository);
        }

        private static bool checkObjectVariableExpression(SQLElement containingElement, SQLRepository sqlRepository, ObjectVariableExpression oVe)
        {
            SQLElement referencedObject = sqlRepository.GetElementByGuid(oVe.ObjectVariableGUID);
            if (referencedObject == null)
                return false;
            if (oVe.ObjectVariableNameOld != oVe.ObjectVariableName)
                return false;

            return compareSDMContainerOfSDMElements(referencedObject, containingElement, sqlRepository);
        }

        private static bool checkMethodCallExpression(SQLElement containingElement, SQLRepository sqlRepository, MethodCallExpression mCe)
        {
            SQLMethod method = sqlRepository.GetMethodByGuid(mCe.MethodGuid);
            Boolean valid = checkExpression(containingElement, mCe.Target, sqlRepository);
            if (method == null)
                return false;

            if (mCe.MethodName != mCe.MethodNameOld || mCe.OwnedParameterBinding.Count != method.Parameters.Count)
                return false;

            foreach (ParameterBinding paramBinding in mCe.OwnedParameterBinding)
            {
                SQLParameter parameter = sqlRepository.GetParameterByGuid(paramBinding.ParameterGuid);
                if (parameter == null)
                    valid = false;
                if (!checkExpression(containingElement, paramBinding.ValueExpression, sqlRepository))
                    valid = false;
            }

            return valid;
        }

        private static bool checkAttributeValueExpression(SQLElement containingElement, SQLRepository sqlRepository, AttributeValueExpression aVe)
        {
            SQLAttribute referencedAttribute = sqlRepository.GetAttributeByGuid(aVe.AttributeGUID);
            SQLElement referencedObject = sqlRepository.GetElementByGuid(aVe.ObjectVariableGUID);

            if (referencedAttribute == null || referencedObject == null)
                return false;

            if (aVe.ObjectVariableName != aVe.ObjectVariableNameOld || aVe.AttributeName != aVe.AttributeNameOld)
                return false;

            if (!checkIfClassifierContainsAttribute(sqlRepository.GetElementByGuid(aVe.ObjectVariableGUID), sqlRepository, referencedAttribute))
            {
                return false;
            }



            return compareSDMContainerOfSDMElements(containingElement, referencedObject, sqlRepository);
        }

        public static String getExpressionOutput(Expression expression, SQLRepository repository)
        {
            String outputString = "";
            if (expression is AttributeValueExpression)
            {
                AttributeValueExpression aVe = expression as AttributeValueExpression;
                outputString = "AttributeValueExpression: " + aVe.ObjectVariableName + "." + aVe.AttributeName + "is corrupted";
            }
            else if (expression is ObjectVariableExpression)
            {
                ObjectVariableExpression oVe = expression as ObjectVariableExpression;
                outputString = "ObjectVariableExpression: " + oVe.ObjectVariableName + "is corrupted";
            }
            else if (expression is ParameterExpression)
            {
                ParameterExpression pExp = expression as ParameterExpression;
            }

            return outputString;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLElement eaObject, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            if (!passed)
            {
                ruleResult.RuleID = rule.getRuleID();
                ruleResult.Rule = rule;
                ruleResult.ObjectType = EA.ObjectType.otElement;
                ruleResult.ObjectID = (eaObject as SQLElement).ElementID;
                ruleResult.EaObject = eaObject;
                ruleResult.ErrorOutput = ruleOutput;
                ruleResult.ErrorLevel = rule.getErrorLevel(eaObject, ruleOutput);
                ruleResult.NameOfObject = (eaObject as SQLElement).Name;

                SQLElement element = eaObject as SQLElement;
                ruleResult.TypeOfObject = element.Stereotype;
            }
            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLParameter parameter, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            if (!passed)
            {
                ruleResult.RuleID = rule.getRuleID();
                ruleResult.Rule = rule;
                ruleResult.ObjectType = EA.ObjectType.otParameter;
                ruleResult.ObjectID = parameter.OperationID;
                ruleResult.EaObject = parameter;
                ruleResult.ErrorOutput = ruleOutput;
                ruleResult.ErrorLevel = rule.getErrorLevel(parameter, ruleOutput);
                ruleResult.NameOfObject = parameter.Name;
                ruleResult.TypeOfObject = "Parameter";
            }
            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLAttribute att, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            ruleResult.Rule = rule;
            ruleResult.RuleID = rule.getRuleID();
            ruleResult.EaObject = att;
            ruleResult.ObjectType = EA.ObjectType.otAttribute;
            ruleResult.ObjectID = att.AttributeID;
            ruleResult.NameOfObject = att.Name;
            ruleResult.ErrorOutput = ruleOutput;
            ruleResult.ErrorLevel = rule.getErrorLevel(att, ruleOutput);

            if (att.Stereotype == "enum")
                ruleResult.TypeOfObject = "EEnumLiteral";
            else
                ruleResult.TypeOfObject = "EAttribute";

            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLDiagram diag, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            ruleResult.Rule = rule;
            ruleResult.RuleID = rule.getRuleID();
            ruleResult.EaObject = diag;
            ruleResult.ObjectType = EA.ObjectType.otDiagram;
            ruleResult.ObjectID = diag.DiagramID;
            ruleResult.NameOfObject = diag.Name;
            ruleResult.ErrorOutput = ruleOutput;
            ruleResult.ErrorLevel = rule.getErrorLevel(diag, ruleOutput);
            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLPackage eaPkg, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            SQLPackage eaPackage = eaPkg;
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            ruleResult.Rule = rule;
            ruleResult.RuleID = rule.getRuleID();
            ruleResult.EaObject = eaPkg;
            ruleResult.ObjectType = EA.ObjectType.otPackage;
            ruleResult.ErrorOutput = ruleOutput;
            ruleResult.ErrorLevel = rule.getErrorLevel(eaPkg, ruleOutput);
            ruleResult.ObjectID = eaPkg.PackageID;
            ruleResult.NameOfObject = eaPkg.Name;

            if (eaPackage.Element != null)
            {
                ruleResult.TypeOfObject = eaPackage.Element.Stereotype;
            }
            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLMethod method, string ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            ruleResult.Rule = rule;
            ruleResult.RuleID = rule.getRuleID();
            ruleResult.EaObject = method;
            ruleResult.ObjectType = EA.ObjectType.otMethod;
            ruleResult.ObjectID = method.MethodID;
            ruleResult.ErrorOutput = ruleOutput;
            ruleResult.ErrorLevel = rule.getErrorLevel(method, ruleOutput);
            ruleResult.NameOfObject = method.Name;
            ruleResult.TypeOfObject = "Method";
            return ruleResult;
        }

        public static RuleResult computeRuleResult(Rule rule, SQLConnector eaObject, String ruleOutput)
        {
            Boolean passed = ruleOutput == "";
            RuleResult ruleResult = new RuleResult();
            ruleResult.Passed = passed;
            ruleResult.Rule = rule;
            ruleResult.RuleID = rule.getRuleID();
            ruleResult.EaObject = eaObject;
            ruleResult.ObjectType = EA.ObjectType.otConnector;
            ruleResult.ObjectID = eaObject.ConnectorID;
            ruleResult.NameOfObject = eaObject.Name;
            ruleResult.ErrorOutput = ruleOutput;
            ruleResult.ErrorLevel = rule.getErrorLevel(eaObject, ruleOutput);
            if (eaObject.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                if (eaObject.Stereotype == SDMModelingMain.LinkVariableStereotype)
                    ruleResult.TypeOfObject = SDMModelingMain.LinkVariableStereotype;
                else
                    ruleResult.TypeOfObject = "EReference";
            }
            else if (eaObject.Type == "Generalization")
                ruleResult.TypeOfObject = "EInheritance";
            return ruleResult;
        }



    }
}

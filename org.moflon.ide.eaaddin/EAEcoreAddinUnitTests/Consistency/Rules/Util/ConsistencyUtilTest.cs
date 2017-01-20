using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddinUnitTests
{
    [TestClass]
    public class ConsistencyUtilTest
    {
        [TestMethod]
        public void TestIsConventionalConstantName()
        {
            Assert.IsTrue(ConsistencyUtil.isValidConstantName("CONST"));
            Assert.IsTrue(ConsistencyUtil.isValidConstantName("CONST_NAME"));
            Assert.IsTrue(ConsistencyUtil.isValidConstantName("_CONST_NAME"));
            Assert.IsTrue(ConsistencyUtil.isValidConstantName("_CONST_NAME_123"));

            Assert.IsFalse(ConsistencyUtil.isValidConstantName(""));
            Assert.IsFalse(ConsistencyUtil.isValidConstantName("aMember"));
            Assert.IsFalse(ConsistencyUtil.isValidConstantName("1CONST"));
            Assert.IsFalse(ConsistencyUtil.isValidConstantName("1_CONST"));
            Assert.IsFalse(ConsistencyUtil.isValidConstantName("COnST"));
        }

        [TestMethod]
        public void testIsValidTGGRuleName()
        {
            Assert.IsTrue(ConsistencyUtil.isValidTGGRuleName("MyRule"));
            Assert.IsTrue(ConsistencyUtil.isValidTGGRuleName("myRule1"));
            Assert.IsTrue(ConsistencyUtil.isValidTGGRuleName("_withUnderscore"));
            Assert.IsTrue(ConsistencyUtil.isValidTGGRuleName("HouseToBungalowRule"));

            Assert.IsFalse(ConsistencyUtil.isValidTGGRuleName(""));
            Assert.IsFalse(ConsistencyUtil.isValidTGGRuleName("aQuote\""));
            Assert.IsFalse(ConsistencyUtil.isValidTGGRuleName("someWhitespaceX X"));
            
        }

        [TestMethod]
        public void testIsValidActivityNodeName()
        {
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("MyActivity"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("MyActivity1"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("myActivityNode1"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("_withUnderscore"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("someWhitespaceX X"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName("activity-activity"));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName(""));
            Assert.IsTrue(ConsistencyUtil.isValidActivityNodeName(null));

            Assert.IsFalse(ConsistencyUtil.isValidActivityNodeName("aQuote\""));

        }

        [TestMethod]
        public void isConstantLiteralNumbers()
        {
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1.0"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral(".234"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("0x123"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("123L"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1.2d"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1d"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1.2f"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("1f"));

            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1.0"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-.234"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-123L"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1.2d"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1d"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1.2f"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("-1f"));
        }

        [TestMethod]
        public void isConstantLiteralStrings()
        {
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("'A'"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("\"abc\""));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("\"\\\"abc\\\"\""));
        }

        [TestMethod]
        public void isConstantLiteralEnum()
        {
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("EnumConstant"));
            Assert.IsTrue(ConsistencyUtil.isConstantLiteral("EnumName.EnumConstant"));
            
        }

    }
}

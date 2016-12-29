using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using EAEcoreAddin.Modeling.ECOREModeling.Util;

namespace EAEcoreAddinUnitTests
{
    [TestClass]
    public class EcoreUtilTest
    {
        String lowerBound;
        String upperBound;

        [TestInitialize]
        public void setUp()
        {
            this.lowerBound = null;
            this.upperBound = null;
        }

        [TestMethod]
        public void TestComputeLowerUpperBound_OneOrTwo()
        {
            EcoreUtil.computeLowerUpperBound("1..2", ref lowerBound, ref upperBound);

            AreEqualLowerAndUpperBounds("1", lowerBound, "2", upperBound);
        }

        [TestMethod]
        public void TestComputeLowerUpperBound_ZeroOrMore()
        {
            EcoreUtil.computeLowerUpperBound("0..*", ref lowerBound, ref upperBound);

            AreEqualLowerAndUpperBounds("0", lowerBound, EcoreUtil.ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER, upperBound);
        }

        [TestMethod]
        public void TestComputeLowerUpperBound_SingleCardinality()
        {
            EcoreUtil.computeLowerUpperBound("1", ref lowerBound, ref upperBound);

            AreEqualLowerAndUpperBounds("1", lowerBound, "1", upperBound);
        }

        private static void AreEqualLowerAndUpperBounds(String expectedLowerBound, String actualLowerBound, String expectedUpperBound, String actualUpperBound)
        {
            Assert.AreEqual(expectedLowerBound, actualLowerBound);
            Assert.AreEqual(expectedUpperBound, actualUpperBound);
        }
    }
}

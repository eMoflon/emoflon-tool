package org.moflon.moca.AttributeConstraint.parser;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;

public class AttributeConstraintParserAdapterTest {
	private AttributeConstraintParserAdapter adapter;

	@Before
	public void setUp() {
		this.adapter = new AttributeConstraintParserAdapter();
	}

	@Test
	public void testImportPackage() throws Exception {

		ValidationReport validationReport = this.adapter.parse("importPackage Ecore;");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testImportPackage2() throws Exception {
		ValidationReport validationReport = this.adapter.parse("importPackage Ecore;importPackage x.y.z;");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testSimpleBinaryConstraint() throws Exception {
		ValidationReport validationReport = this.adapter.parse("=(this.k, 1::EInt);");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testSimpleTernaryConstraint() throws Exception {
		ValidationReport validationReport = this.adapter.parse("=(this.k, other.x, 1::EInt);");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testTemporaryVariable() throws Exception {
		ValidationReport validationReport = this.adapter.parse("=(x:EInt, 1::EInt);");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testEnumConstraint() throws Exception {
		ValidationReport validationReport = this.adapter.parse("=(this.x, ACTIVE::LinkState);");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testUnequal() throws Exception {
		ValidationReport validationReport = this.adapter.parse("!=(this.x, ACTIVE::LinkState);");
		assertHasNoErrorMessages(validationReport);
	}

	@Test
	public void testParamterExpression() throws Exception {
		ValidationReport validationReport = this.adapter.parse("!=(this.x, k);");
		assertHasNoErrorMessages(validationReport);
	}

	private static void assertHasNoErrorMessages(ValidationReport validationReport) {
		final EList<ErrorMessage> errorMessages = validationReport.getErrorMessages();
		Assert.assertEquals(new BasicEList<ErrorMessage>(), errorMessages);
	}
}

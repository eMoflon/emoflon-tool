package org.moflon.sdm.constraints.operationspecification;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Test;

import SDMLanguage.SDMLanguagePackage;

public class AttributeConstraintsCodeGenerationTest {

	@Test
	public void testGenerateCode() throws InvocationTargetException {

		final File pathToWorkspaceRoot = new File("").getAbsoluteFile().getParentFile();
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		// This is required if not running in plugin
		rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true),
				URI.createFileURI(pathToWorkspaceRoot.getAbsolutePath() + "\\"));
		// This is needed for loading using file based metamodel
		rs.getURIConverter().getURIMap().put(
				URI.createPlatformPluginURI("/org.moflon.sdm.constraints.operationspecification/", true),
				URI.createPlatformResourceURI("/org.moflon.sdm.constraints.operationspecification/", true));

		EcorePackage.eINSTANCE.eResource();
		SDMLanguagePackage.eINSTANCE.eResource();

		// This is for loading using generate metamodel
		OperationspecificationPackage.eINSTANCE.getClass();

		URI uri1 = URI.createPlatformResourceURI(
				String.format("/%s/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi",
						"org.moflon.sdm.constraints.operationspecification"),
				true);
		Resource r = rs.getResource(uri1, true);
		AttributeConstraintLibrary lib = (AttributeConstraintLibrary) r.getContents().get(0);
		@SuppressWarnings("unused")
		OperationSpecificationGroup group = lib.getOperationSpecifications().get(0);
	}

}

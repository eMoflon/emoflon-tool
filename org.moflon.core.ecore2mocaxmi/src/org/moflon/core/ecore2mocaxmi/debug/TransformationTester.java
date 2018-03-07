package org.moflon.core.ecore2mocaxmi.debug;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiFactory;
import org.moflon.core.utilities.eMoflonEMFUtil;

import MocaTree.Node;

public class TransformationTester {

	public static void main(String[] args) {
		Ecore2MocaXMIConverter converter = Ecore2mocaxmiFactory.eINSTANCE.createEcore2MocaXMIConverter();
		Node tree;
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		ResourceSet rs = new ResourceSetImpl();
		// enable extended metadata
		final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(rs.getPackageRegistry());
		rs.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		File file = new File("instances/in/java.ecore");
		Resource r = rs.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		EObject eObject = r.getContents().get(0);
		if (eObject instanceof EPackage) {
			EPackage p = (EPackage) eObject;
			rs.getPackageRegistry().put(p.getNsURI(), p);
			tree = converter.convert(p, "MoDisco", null);
			eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), tree, "instances/out/java.moca.xmi");
		}
	}

}

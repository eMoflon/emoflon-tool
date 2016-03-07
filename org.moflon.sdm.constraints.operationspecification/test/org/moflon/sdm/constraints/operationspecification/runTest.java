package org.moflon.sdm.constraints.operationspecification;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import SDMLanguage.SDMLanguagePackage;



public class runTest {

	public static void main2(final String[] args) throws InvocationTargetException {
		
	   ResourceSet rs=new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
//This is required if not running in plugin
		rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(args[0] + "\\"));
//This is needed for loading using file based metamodel
		rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/DemoclesAttributeConstraintSpecification/", true), URI.createPlatformResourceURI("/DemoclesAttributeConstraintSpecification/", true));
		

		EcorePackage.eINSTANCE.eResource();
		SDMLanguagePackage.eINSTANCE.eResource();

//This is for loading using generate metamodel		
		OperationspecificationPackage.eINSTANCE.getClass();

//This is for loading using file base metamodel		
//		URI mmuri =URI.createPlatformPluginURI("DemoclesAttributeConstraintSpecification/model/DemoclesAttributeConstraintSpecification.ecore",true);
//		Resource mmresource =rs.getResource(mmuri, true);
//		
//		rs.getPackageRegistry().put("platform:/plugin/DemoclesAttributeConstraintSpecification/model/DemoclesAttributeConstraintSpecification.ecore",
//				mmresource.getContents().get(0));

		
		
		URI uri1=URI.createPlatformResourceURI("/DemoclesAttributeConstraintSpecification/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi",true);
		Resource r=rs.getResource(uri1,true);
		AttributeConstraintLibrary lib = (AttributeConstraintLibrary) r.getContents().get(0);
		OperationSpecificationGroup group = lib.getOperationSpecifications().get(0);
		group.gernerateTemplate();
		System.out.println("---------------------------------------");
		System.out.println(group.getTemplateGroupString());
		
		

		
//		int x=0;
	}

}

package org.moflon.maave.tests.testsuite.testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResource;
import org.moflon.maave.tool.category.CategoryPackage;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;

import SDMLanguage.SDMLanguagePackage;


public class TestRunner {
	public static String workspaceLoc;



	public static void main(String[] args) {

		TestRunner.workspaceLoc=args[0];
		
		System.out.println(System.getenv("CurrentWSLoc"));
		Result result = JUnitCore.runClasses(AllTests.class);
		
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
			failure.getException().printStackTrace();
		}
		if(result.wasSuccessful()){
			System.out.println("All tests were successful");
		}else{
			System.out.println("NOT all tests were successful");
		}
	}

	public static EPackage loadTestMM(String pluginName, String projectName){
		ResourceSet rs =new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(workspaceLoc+ "\\"));
		rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/"+pluginName+"/", true), URI.createPlatformResourceURI("/"+projectName+"/", true));

		//		URI uri3=URI.createURI("platform:/plugin/org.moflon.SDMLanguage/model/SDMLanguage.ecore",true);
		//		PackageRemappingDependency pDependency3=new PackageRemappingDependency(uri3, true, true);
		//		pDependency3.getResource(rs, true);

		SDMLanguagePackage.eINSTANCE.getClass();

		URI uri1=URI.createPlatformResourceURI("/"+pluginName+"/model/"+projectName+".ecore", true);


		Resource resource=rs.getResource(uri1, true);
		EPackage pack=(EPackage)resource.getContents().get(0);

		return pack;

	}
	public static SymbGTRule loadTestModel(String name){
		ResourceSet rs=new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(workspaceLoc + "\\"));
		rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/TestGenerator/", true), URI.createPlatformResourceURI("/TestGenerator/", true));
		
		URI testGeneratorURI =URI.createURI("platform:/plugin/TestGenerator/model/TestGenerator.ecore",true);
		SDMLanguagePackage.eINSTANCE.getClass();
		PackageRemappingDependency d = new PackageRemappingDependency(testGeneratorURI, true, false);
	
		d.getResource(rs, true);
		//TestLanguagePackage.eINSTANCE.getClass();
		//TestGeneratorPackage.eINSTANCE.getClass();
		CategoryPackage.eINSTANCE.getClass();
		
		

		URI uri1=URI.createPlatformResourceURI("CathegoryLanguageTestSuite/instances/"+name+".xmi",false);
		Resource r=rs.getResource(uri1,true);
		EcoreUtil.resolveAll(r);
		SymbGTRule rule=(SymbGTRule) r.getContents().get(0);
		return rule;
		
	}
	public static void saveTestResult(EObject obj,String name) {
		ResourceSet rs =new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(workspaceLoc+ "\\"));
		//rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI(pluginName, true), URI.createPlatformResourceURI(projectName, true));

		PackageRemappingDependency d = new PackageRemappingDependency(URI.createPlatformResourceURI("/org.moflon.maave.tests.testsuite/instances/"+name+".xmi", true), false, false);
		Resource newRes=d.getResource(rs, false);
		newRes.getContents().add(obj);
		Map<String, Object> saveOptions = new HashMap<String, Object>();
		saveOptions.put(SDMEnhancedEcoreResource.SAVE_GENERATED_PACKAGE_CROSSREF_URIS, Boolean.TRUE);
		try {
			newRes.save(saveOptions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



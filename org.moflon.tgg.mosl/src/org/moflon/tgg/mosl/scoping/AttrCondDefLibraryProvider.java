package org.moflon.tgg.mosl.scoping;

import java.io.IOException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.tgg.mosl.tgg.Adornment;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.AttrCondDefLibrary;
import org.moflon.tgg.mosl.tgg.Param;
import org.moflon.tgg.mosl.tgg.TggFactory;
import org.moflon.tgg.mosl.tgg.TggPackage;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class AttrCondDefLibraryProvider {
	
	private static String[] names = {"eq", "addPrefix", "addSuffix", "concat", "setDefaultString", 
			"setDefaultNumber", "stringToDouble", "stringToInt", "multiply", "divide", 
			"add", "sub", "max", "smallerOrEqual"};

	private static String[][] paramTypes = {
			{"",""},							// 0. eq
			{"EString", "EString", "EString"},	// 1. addPrefix
			{"EString", "EString", "EString"},	// 2. addSuffix
			{"EString", "EString", "EString", "EString"},	// 3. concat
			{"EString", "EString"},				// 4. setDefaultString
			
			{"Number", "Number"},			// 5. setDefaultNumber
			{"EString", "EDouble"},			// 6. stringToDouble
			{"EString", "EInt"},			// 7. stringToInt
			{"Number", "Number", "Number"},	// 8. multiply
			{"Number", "Number", "Number"},	// 9. divide
			
			{"Number", "Number", "Number"},	// 10. add
			{"Number", "Number", "Number"},	// 11. sub
			{"Number", "Number", "Number"},	// 12. max
			{"Number", "Number"}			// 13. smallerOrEqual
	};
	
	private static String[][] syncAdornments = {
			{"BB", "BF", "FB"},					// 0. eq
			{"BBB", "BBF", "BFB", "FBB"},		// 1. addPrefix
			{"BBB", "BBF", "BFB", "FBB"},		// 2. addSuffix
			{"BBBB", "BBBF", "BBFB", "BFFB", "BFBB"},		// 3. concat
			{"BB", "FB"},						// 4. setDefaultString

			{"BB", "FB"},						// 5. setDefaultNumber
			{"BB", "BF", "FB"},					// 6. stringToDouble
			{"BB", "BF", "FB"},					// 7. stringToInt
			{"BBB", "BBF", "BFB", "FBB"},		// 8. multiply
			{"BBB", "BBF", "BFB", "FBB"},		// 9. divide
			
			{"BBB", "BBF", "BFB", "FBB"},		// 10. add
			{"BBB", "BBF", "BFB", "FBB"},		// 11. sub
			{"BBB", "BBF", "BFB", "FBB"},		// 12. max
			{"BB", "BF", "FB"}					// 13. smallerOrEqual
	};

	private static String[][] genAdornments = {
			{"BB", "BF", "FB", "FF"},							// 0. eq
			{"BBB", "BBF", "BFB", "FBB", "BFF", "FBF"},			// 1. addPrefix
			{"BBB", "BBF", "BFB", "FBB", "BFF", "FFF", "FBF"},	// 2. addSuffix
			{"BBBB", "BBBF", "BBFB", "BFFB", "BFBB", "BFFF", "BFBF", "BBFF"},	// 3. concat
			{"BB", "FB", "FF"},									// 4. setDefaultString

			{"BB", "FB", "FF"},									// 5. setDefaultNumber
			{"BB", "BF", "FB", "FF"},							// 6. stringToDouble
			{"BB", "BF", "FB", "FF"},							// 7. stringToInt
			{"BBB", "BBF", "BFB", "FBB"},						// 8. multiply
			{"BBB", "BBF", "BFB", "FBB"},						// 9. divide
			
			{"BBB", "BBF", "BFB", "FBB", "FFB", "FBF", "BFF"},			// 10. add
			{"BBB", "BBF", "BFB", "FBB", "FFB", "BFF", "FBF", "FFF"},	// 11. sub
			{"BBB", "BBF", "BFB", "FBB"},		// 12. max
			{"BB", "BF", "FB", "FF"}			// 13. smallerOrEqual
	};
	
			
	protected static EList<AttrCondDef> attrCondDefs;
	
	public static EList<AttrCondDef> getAttrCondDefs() {
		if(attrCondDefs == null){
			attrCondDefs = new BasicEList<AttrCondDef>();
			
			for (int i = 0; i < names.length; i++) {
				attrCondDefs.add(createAttrCondDef(names[i], paramTypes[i], syncAdornments[i], genAdornments[i]));
			}
		}
			
		return attrCondDefs;
	}
	

	public static AttrCondDefLibrary getAttrCondDefLibrary() {
		TggFactory tggFactory = TggPackage.eINSTANCE.getTggFactory();
		
		AttrCondDefLibrary attrCondDefLib = tggFactory.createAttrCondDefLibrary();
		attrCondDefLib.setName("AttrCondDefLibrary");
		attrCondDefLib.getAttributeCondDefs().addAll(getAttrCondDefs());
		
		return attrCondDefLib;
	}


	public static AttrCondDefLibrary syncAttrCondDefLibrary(XtextResourceSet resourceSet, String projectPath) throws IOException {
		TggFactory tggFactory = TggPackage.eINSTANCE.getTggFactory();
		
		TripleGraphGrammarFile tgg = tggFactory.createTripleGraphGrammarFile();
		tgg.setLibrary(getAttrCondDefLibrary());
		
		URI uri = URI.createPlatformResourceURI(projectPath + "/src/org/moflon/tgg/mosl/csp/lib/AttrCondDefLibrary.tgg", true);
		XtextResource resource = (XtextResource) resourceSet.createResource(uri);
		resource.getContents().add(tgg);
		resource.save(null);
		
		return tgg.getLibrary();
	}

	private static AttrCondDef createAttrCondDef(String name, String[] paramTypes, String[] syncAdornments, String[] genAdornments) {
		TggFactory tggFactory = TggPackage.eINSTANCE.getTggFactory();
		AttrCondDef attrCondDef = tggFactory.createAttrCondDef();
		attrCondDef.setName(name);
		
		Param param;
		for (int i = 0; i < paramTypes.length; i++) {
			param = tggFactory.createParam();
			param.setIndex(i);
			param.setType(paramTypes[i]);
			attrCondDef.getParams().add(param);
		}
		
		Adornment adornment;
		for (String adornmentValue : syncAdornments) {
			adornment = tggFactory.createAdornment();
			adornment.setValue(adornmentValue);
			attrCondDef.getAllowedSyncAdornments().add(adornment);
		}
		for (String adornmentValue : genAdornments) {
			adornment = tggFactory.createAdornment();
			adornment.setValue(adornmentValue);
			attrCondDef.getAllowedGenAdornments().add(adornment);
		}
		return attrCondDef;
	}
	
	
}

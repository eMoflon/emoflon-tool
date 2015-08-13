package org.moflon.mosl.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author szander
 *
 * This class provides reference from the EDatatypes of Ecore.
 *
 * Note: The Arrays of leType and eDatatypes must have the same size because leType will map to eDatatype.
 */

public class EcoreDatatypeProvider {
	
	
	private static final String[] leTypes = {"BooleanExpression", "StringExpression", "IntegerExpression", "DoubleExpression", "UndefinedExpression", "NullExpression"};
	
	private static final String[] eDatatypes ={"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBoolean", "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString", "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EInt", "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDouble", "",""};
	
	private static final EcoreDatatypeProvider singleton = create();
	
	private Map<String, String> datatypeProvider;
	
	private static EcoreDatatypeProvider create(){
		return new EcoreDatatypeProvider();
	}
	
	private EcoreDatatypeProvider(){
		datatypeProvider = new HashMap<>();
		
		if(leTypes.length == eDatatypes.length){
			for(int i=0; i< leTypes.length; i++){
				datatypeProvider.put(leTypes[i], eDatatypes[i]);
			}
		}
		else
			throw new RuntimeException("Somebody has manipulated the EcoreDatatypeProvider");
	}
	
	public static String getDatatype(String leType){
		String output=singleton.datatypeProvider.get(leType);
		if(output==null)
			throw new RuntimeException("LEType: " + leType+ " is unknown");
		return output;
	}
	
}

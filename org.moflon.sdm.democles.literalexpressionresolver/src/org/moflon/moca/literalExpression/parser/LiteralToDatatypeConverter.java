package org.moflon.moca.literalExpression.parser;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;

public class LiteralToDatatypeConverter {

	public static void main(String[] args) {
		LiteralToDatatypeConverter x=new LiteralToDatatypeConverter();
		String s="\"tes_t\"";
		System.out.println(s+":   "+x.determineEdatatype(s).getName());
		String s1="1231231231231";
		System.out.println(s1+":   "+x.determineEdatatype(s1).getName());
		String s2="123123123.123123";
		System.out.println(s2+":   "+x.determineEdatatype(s2).getName());
		String s3="-1231231231231";
		System.out.println(s3+":   "+x.determineEdatatype(s3).getName());
		String s4="-123123123.123123";
		System.out.println(s4+":   "+x.determineEdatatype(s4).getName());
		String s5="1231231123sdf23.123123";
		System.out.println(s5+":   "+x.determineEdatatype(s5).getName());

	}

	public EDataType determineEdatatype(String literalString) {
		if (literalString.matches("[-+]?[0-9]*")) {
			return EcorePackage.eINSTANCE.getEInt();			
		}else if(literalString.matches("[-+]?[0-9]*\\.[0-9]*")){
			return EcorePackage.eINSTANCE.getEDouble();
		}else if(literalString.matches("\"[a-zA-Z_0-9]*\"")){
			return EcorePackage.eINSTANCE.getEString();
		}else{
			throw new IllegalArgumentException("Type of in literal expression not supported: Could not determine EdataType of literal: "+literalString);
			
		}
		
	}
}

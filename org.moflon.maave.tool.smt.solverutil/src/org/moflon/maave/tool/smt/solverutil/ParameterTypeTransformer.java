package org.moflon.maave.tool.smt.solverutil;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;

public class ParameterTypeTransformer {


	public static String getSMTLibDatatypeString(EDataType eDataType){


		switch (eDataType.getClassifierID())
		{
		case EcorePackage.EBIG_DECIMAL:
			return "Real";
		case EcorePackage.EBIG_INTEGER:
			return "Int";
		case EcorePackage.EBOOLEAN:
			return "Bool";
		case EcorePackage.EBOOLEAN_OBJECT:
			return "Bool";
		case EcorePackage.EBYTE:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.EBYTE_ARRAY:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.EBYTE_OBJECT:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.ECHAR:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.ECHARACTER_OBJECT:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.EDATE:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.EDOUBLE:
			return "Real";
		case EcorePackage.EDOUBLE_OBJECT:
			return "Real";
		case EcorePackage.EFLOAT:
			return "Real";
		case EcorePackage.EFLOAT_OBJECT:
			return "Real";
		case EcorePackage.EINT:
			return "Int";
		case EcorePackage.EINTEGER_OBJECT:
			return "Int";
		case EcorePackage.EJAVA_CLASS:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.EJAVA_OBJECT:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		case EcorePackage.ELONG:
			return "Int";
		case EcorePackage.ELONG_OBJECT:
			return "Int";
		case EcorePackage.ESHORT:
			return "Int";
		case EcorePackage.ESHORT_OBJECT:
			return "Int";
		case EcorePackage.ESTRING:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not supported from the SMT Solver");
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}

	}
}

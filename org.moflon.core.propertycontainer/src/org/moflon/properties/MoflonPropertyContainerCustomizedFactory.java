package org.moflon.properties;

import org.eclipse.emf.ecore.EDataType;

import MoflonPropertyContainer.SDMCodeGeneratorIds;
import MoflonPropertyContainer.impl.MoflonPropertyContainerFactoryImpl;

public class MoflonPropertyContainerCustomizedFactory extends
		MoflonPropertyContainerFactoryImpl {
	
	public SDMCodeGeneratorIds createSDMCodeGeneratorIdsFromString(
			EDataType eDataType, String initialValue) {
		SDMCodeGeneratorIds result = SDMCodeGeneratorIds.getByName(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	public String convertSDMCodeGeneratorIdsToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : ((SDMCodeGeneratorIds) instanceValue).getName();
	}
}

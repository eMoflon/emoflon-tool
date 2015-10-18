package org.moflon.properties;

import org.eclipse.emf.ecore.EDataType;

import MoflonPropertyContainer.SDMCodeGeneratorIds;
import MoflonPropertyContainer.impl.MoflonPropertyContainerFactoryImpl;

public class MoflonPropertyContainerCustomizedFactory extends
		MoflonPropertyContainerFactoryImpl {
	
   @Override
	public SDMCodeGeneratorIds createSDMCodeGeneratorIdsFromString(
			final EDataType eDataType, final String initialValue) {
		final SDMCodeGeneratorIds result = SDMCodeGeneratorIds.getByName(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	@Override
   public String convertSDMCodeGeneratorIdsToString(final EDataType eDataType,
			final Object instanceValue) {
		return instanceValue == null ? null : ((SDMCodeGeneratorIds) instanceValue).getName();
	}
}

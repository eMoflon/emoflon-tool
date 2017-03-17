package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.moflon.gt.mosl.moslgt.AttributeConstraint;

public interface IAttributeConstraintClassGetter extends IAbstractAttributeClassGetter<AttributeConstraint>{
	default Class<AttributeConstraint> getAbstractAttributeClass(){
		return AttributeConstraint.class;
	}
}

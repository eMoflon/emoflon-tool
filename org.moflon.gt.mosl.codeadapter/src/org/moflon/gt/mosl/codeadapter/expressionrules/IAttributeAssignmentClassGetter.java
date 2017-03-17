package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.moflon.gt.mosl.moslgt.AttributeAssignment;

public interface IAttributeAssignmentClassGetter extends IAbstractAttributeClassGetter<AttributeAssignment>
{
   default Class<AttributeAssignment> getAbstractAttributeClass()
   {
      return AttributeAssignment.class;
   }
}

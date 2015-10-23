package DemoclesAttributeConstraintSpecification.constraint.util;

import org.eclipse.emf.ecore.util.Switch;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.VariableType;
import org.gervarro.democles.specification.emf.util.EMFTypeModule;

import DemoclesAttributeConstraintSpecification.constraint.AttributeVariableConstraintsTypeModule;
import DemoclesAttributeConstraints.AttributeVariableConstraint;
import DemoclesAttributeConstraints.util.DemoclesAttributeConstraintsSwitch;

public class AttributeVariableConstraintsModule extends EMFTypeModule
{

   public AttributeVariableConstraintsModule(final AttributeVariableConstraintsTypeModule typeModule)
   {
      super(typeModule);
   }

   @Override
   protected Switch<VariableType> createVariableTypeSwitch()
   {
      return null;
   }

   @Override
   protected Switch<ConstraintType> createConstraintTypeSwitch()
   {
      return new AttributeVariableConstraintsTypeSwitch();
   }

   private class AttributeVariableConstraintsTypeSwitch extends DemoclesAttributeConstraintsSwitch<ConstraintType>
   {

      @Override
      public ConstraintType caseAttributeVariableConstraint(final AttributeVariableConstraint object)
      {
         return ((AttributeVariableConstraintsTypeModule) typeModule).getConstraintType(object);
      }

   }
}

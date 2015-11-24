package DemoclesAttributeConstraintSpecification.constraint.util;

import org.eclipse.emf.ecore.util.Switch;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.VariableType;
import org.gervarro.democles.specification.emf.util.EMFTypeModule;
import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;
import org.moflon.sdm.constraints.democles.util.DemoclesSwitch;

import DemoclesAttributeConstraintSpecification.constraint.AttributeVariableConstraintsTypeModule;

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

   private class AttributeVariableConstraintsTypeSwitch extends DemoclesSwitch<ConstraintType>
   {

      @Override
      public ConstraintType caseAttributeVariableConstraint(final AttributeVariableConstraint object)
      {
         return ((AttributeVariableConstraintsTypeModule) typeModule).getConstraintType(object);
      }

   }
}

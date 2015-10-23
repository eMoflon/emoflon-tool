package DemoclesAttributeConstraintSpecification.constraint;

import java.util.List;

import org.gervarro.democles.common.TypeModule;
import org.gervarro.democles.specification.ConstraintType;

import DemoclesAttributeConstraintSpecification.AttributeConstraintLibrary;
import DemoclesAttributeConstraintSpecification.ConstraintSpecification;
import DemoclesAttributeConstraints.AttributeVariableConstraint;

public class AttributeVariableConstraintsTypeModule implements TypeModule
{

   public static AttributeVariableConstraintsTypeModule INSTANCE;

   private final List<AttributeConstraintLibrary> libraries;

   public AttributeVariableConstraintsTypeModule(final List<AttributeConstraintLibrary> libraries)
   {
      this.libraries = libraries;
      INSTANCE = this;
   }

   @Override
   public final String getName()
   {
      return "AttributeValueConstraintsTypeModule";
   }

   public final ConstraintType getConstraintType(final AttributeVariableConstraint constraint)
   {
      //
      ConstraintSpecification cSpecification = null;
      for (AttributeConstraintLibrary attributeConstraintLibrary : libraries)
      {
         cSpecification = attributeConstraintLibrary.lookupConstraintType(constraint);
         if (cSpecification != null)
         {
            break;
         }
      }
      return cSpecification;
   }

}

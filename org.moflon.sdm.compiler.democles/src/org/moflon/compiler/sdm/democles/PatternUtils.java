package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.common.util.EList;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.emf.Constraint;
import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.ConstraintVariable;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Attribute;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.gervarro.democles.specification.emf.constraint.relational.Equal;
import org.gervarro.democles.specification.emf.constraint.relational.Larger;
import org.gervarro.democles.specification.emf.constraint.relational.LargerOrEqual;
import org.gervarro.democles.specification.emf.constraint.relational.Smaller;
import org.gervarro.democles.specification.emf.constraint.relational.SmallerOrEqual;
import org.gervarro.democles.specification.emf.constraint.relational.Unequal;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

public final class PatternUtils {

	private PatternUtils() {
		throw new UtilityClassNotInstantiableException();
	}

	public static String describeSymbolicParameters(final Pattern pattern, final Adornment adornment) {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		final EList<Variable> parameters = pattern.getSymbolicParameters();
		for (int i = 0; i < parameters.size(); ++i) {
			final Variable parameter = parameters.get(i);
			if (parameter instanceof EMFVariable)
				sb.append(EMFVariable.class.cast(parameter).getName());
			else
				sb.append(parameter);
			sb.append("^");
			sb.append(describeAdornment(adornment.get(i)));

			if (i != parameters.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String describeAdornment(final int adornment) {
		switch (adornment) {
		case Adornment.BOUND:
			return "B";
		case Adornment.NOT_TYPECHECKED:
			return "U";
		case Adornment.FREE:
			return "F";
		default:
			return "?";
		}
	}

	public static String describeConstraint(final Constraint constraint) {
		final StringBuilder sb = new StringBuilder();
		sb.append(describeConstraintName(constraint));
		sb.append("(");
		for (final ConstraintParameter parameter : constraint.getParameters()) {
			final ConstraintVariable reference = parameter.getReference();
			if (reference instanceof EMFVariable)
				sb.append(EMFVariable.class.cast(reference).getName());
			else
				sb.append(parameter);
			sb.append("[").append(String.format("%x", reference.hashCode())).append("]");
			sb.append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		sb.append("    [class: ").append(constraint).append("]");
		return sb.toString();
	}

	public static String describeConstraintName(final Constraint constraint) {
		if (constraint instanceof Attribute) {
			final Attribute attribute = (Attribute) constraint;
			return attribute.getEModelElement().getName();
		} else if (constraint instanceof Reference) {
			final Reference reference = (Reference) constraint;
			return reference.getEModelElement().getName();
		} else if (constraint instanceof Smaller) {
			return "<";
		} else if (constraint instanceof SmallerOrEqual) {
			return "<=";
		} else if (constraint instanceof Equal) {
			return "==";
		} else if (constraint instanceof Unequal) {
			return "!=";
		} else if (constraint instanceof LargerOrEqual) {
			return ">=";
		} else if (constraint instanceof Larger) {
			return ">";
		}
		return constraint.toString();
	}

	public static Object describeOperation(final GeneratorOperation operation) {
		return operation.toString();
	}
}

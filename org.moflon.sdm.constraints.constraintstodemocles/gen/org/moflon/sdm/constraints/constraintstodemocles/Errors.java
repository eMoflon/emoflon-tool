/**
 */
package org.moflon.sdm.constraints.constraintstodemocles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Errors</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage#getErrors()
 * @model
 * @generated
 */
public enum Errors implements Enumerator {
	/**
	 * The '<em><b>UNKNOWN ATTRIBUTE CONSTRAINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_ATTRIBUTE_CONSTRAINT_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN_ATTRIBUTE_CONSTRAINT(0, "UNKNOWN_ATTRIBUTE_CONSTRAINT",
			"Found unknown attribute constraint with signature"),

	/**
	 * The '<em><b>MALFORMED ATTRIBUTE CONSTRAINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MALFORMED_ATTRIBUTE_CONSTRAINT_VALUE
	 * @generated
	 * @ordered
	 */
	MALFORMED_ATTRIBUTE_CONSTRAINT(1, "MALFORMED_ATTRIBUTE_CONSTRAINT", "MALFORMED_ATTRIBUTE_CONSTRAINT_SPECIFICATION");

	/**
	 * The '<em><b>UNKNOWN ATTRIBUTE CONSTRAINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNKNOWN ATTRIBUTE CONSTRAINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_ATTRIBUTE_CONSTRAINT
	 * @model literal="Found unknown attribute constraint with signature"
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_ATTRIBUTE_CONSTRAINT_VALUE = 0;

	/**
	 * The '<em><b>MALFORMED ATTRIBUTE CONSTRAINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MALFORMED ATTRIBUTE CONSTRAINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MALFORMED_ATTRIBUTE_CONSTRAINT
	 * @model literal="MALFORMED_ATTRIBUTE_CONSTRAINT_SPECIFICATION"
	 * @generated
	 * @ordered
	 */
	public static final int MALFORMED_ATTRIBUTE_CONSTRAINT_VALUE = 1;

	/**
	 * An array of all the '<em><b>Errors</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Errors[] VALUES_ARRAY = new Errors[] { UNKNOWN_ATTRIBUTE_CONSTRAINT,
			MALFORMED_ATTRIBUTE_CONSTRAINT, };

	/**
	 * A public read-only list of all the '<em><b>Errors</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Errors> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Errors</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Errors get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Errors result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Errors</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Errors getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Errors result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Errors</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Errors get(int value) {
		switch (value) {
		case UNKNOWN_ATTRIBUTE_CONSTRAINT_VALUE:
			return UNKNOWN_ATTRIBUTE_CONSTRAINT;
		case MALFORMED_ATTRIBUTE_CONSTRAINT_VALUE:
			return MALFORMED_ATTRIBUTE_CONSTRAINT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private Errors(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //Errors

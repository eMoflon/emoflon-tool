package de.uni_paderborn.fujaba.versioning.properties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Similar to the 'transient' modifier this annotation denotes that a (virtual) attribute should not be stored when
 * sending changes to a server - it is only stored locally.
 * @author cschneid
 * @see de.uni_kassel.coobra.Change#isLocal()
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = { ElementType.FIELD, ElementType.METHOD } )
public @interface Local {

}

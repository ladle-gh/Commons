package com.lcj.commons;

import com.lcj.commons.util.Lookup;
import com.lcj.commons.util.ObjectArray;

/**
 * Defines constants related to the Commons library. Such constants are
 * different from those defined within {@link Utility} in that they are only
 * useful in the context of the residing library.<br>
 * <br>
 * This class is a container for static fields and methods. Thus, it should not
 * be treated as an instantiable object.
 *
 * @since 1.0
 */
public final class Commons {
	/**
	 * Default size of an {@link ObjectArray}. Utilized internally when its
	 * {@link ObjectArray#ObjectArray() no-args
	 * constructor} is invoked.
	 *
	 * @since 1.0
	 */
	public static final int DEFAULT_ARRAY_SIZE = 10;
	/**
	 * Default size of a {@link Lookup}. Utilized internally when its
	 * {@link Lookup#Lookup() no-args constructor} is
	 * invoked.
	 *
	 * @since 1.0
	 */
	public static final int DEFAULT_LOOKUP_SIZE = 16;
	/**
	 * Ratio of populated cells to total cells within a {@code Lookup}. Utilitized
	 * internally by {@link Lookup#add()}
	 *
	 * @since 1.0
	 */
	public static final double LOOKUP_LOAD_FACTOR = 0.75;

	private Commons() {
	}
}

package com.lcj.commons;

import com.lcj.commons.checks.Container;

/**
 * Defines constants related to the Commons library.
 */
@Container
public final class Commons {
// public
	/**
	 * Default size of an ObjectArray. Utilized internally when its no-args constructor is invoked.
	 * 
	 * @see ObjectArray
	 * @see ObjectArray#ObjectArray()
	 * */
	public static final int DEFAULT_ARRAY_SIZE = 10;
	/**
	 * Default size of a Lookup. Utilized internally when its no-args constructor is invoked.
	 * 
	 * @see Lookup
	 * @see Lookup#Lookup()
	 */
	public static final int DEFAULT_LOOKUP_SIZE = 16;
	/**
	 * Default seek mode. Utilized internally by Utility.seek() when a seek mode is not explicitly specified.
	 * 
	 * @see Seek
	 * @see Utility#seek
	 */
	public static final byte DEFAULT_SEEK = Seek.FORWARD | Seek.INCLUSIVE;

// private
	private Commons() {
	}
}

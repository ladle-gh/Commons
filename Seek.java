package com.lcj.commons;

import com.lcj.commons.checks.Container;

@Container
public final class Seek {
// public
	public static final byte FORWARD = 0b0001;
	public static final byte REVERSE = 0b0010;
	public static final byte EXCLUSIVE = 0b0100;
	public static final byte INCLUSIVE = 0b1000;

// private
	private Seek() {
	}
}

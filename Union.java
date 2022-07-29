package com.lcj.commons.util;

import java.io.Serializable;


public final class Union<D, A> extends ImmutableUnion<D, A> implements Serializable {
	public static <D, A> Union<D, A> from(D dValue) {
		return (Union<D, A>) new Union<D, A>().setDefault(dValue);
	}
	public static <D, A> Union<D, A> fromAlt(A aValue) {
		return (Union<D, A>) new Union<D, A>().setAlternate(aValue);
	}

	public Union( ImmutableUnion<D, A> other) {
		super(other);
	}
	
	public Union<D, A> set(D obj) {
		return (Union<D, A>) super.setDefault(obj);
	}
	public Union<D, A> setAlt(A obj) {
		return (Union<D, A>) super.setAlternate(obj);
	}

	private Union() {
		super();
	}
	private static final long serialVersionUID = 1L;
}

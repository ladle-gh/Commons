package com.lcj.commons;

import java.io.Serializable;
import com.lcj.commons.checks.NonNull;

public final class Union<D, A> extends ImmutableUnion<D, A> implements Serializable {
// public
	public static <D, A> Union<D, A> from(D dValue) {
		return (Union<D, A>) new Union<D, A>().setDefault(dValue);
	}
	public static <D, A> Union<D, A> fromAlt(A aValue) {
		return (Union<D, A>) new Union<D, A>().setAlternate(aValue);
	}

	public Union(@NonNull ImmutableUnion<D, A> other) {
		super(other);
	}
	
	public Union<D, A> set(D obj) {
		return (Union<D, A>) super.setDefault(obj);
	}
	public Union<D, A> setAlt(A obj) {
		return (Union<D, A>) super.setAlternate(obj);
	}

// private
	private Union() {
		super();
	}
	private static final long serialVersionUID = 1L;
}

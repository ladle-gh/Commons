package com.lcj.commons.util;

import java.io.Serializable;


public final class Tuple<F, S> extends ImmutableTuple<F, S> implements Serializable {
	public Tuple() {
		super();
	}
	public Tuple(F first, S second) {
		super(first, second);
	}
	public Tuple( ImmutableTuple<F, S> other) {
		super(other);
	}

	public F setFirst(F obj) {
		return first = obj;
	}
	public S setSecond(S obj) {
		return second = obj;
	}

	@Override
	public Tuple<S, F> swap() {
		return new Tuple<>(second, first);
	}
	
	private static final long serialVersionUID = 1L;
}

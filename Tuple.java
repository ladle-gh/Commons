package com.lcj.commons;

import java.io.Serializable;
import com.lcj.commons.checks.NonNull;

public final class Tuple<F, S> extends ImmutableTuple<F, S> implements Serializable {
// public
	public Tuple() {
		super();
	}
	public Tuple(F first, S second) {
		super(first, second);
	}
	public Tuple(@NonNull ImmutableTuple<F, S> other) {
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
	
// private
	private static final long serialVersionUID = 1L;
}

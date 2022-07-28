package com.lcj.commons;

import java.io.Serializable;
import com.lcj.commons.checks.NonNull;

public sealed class ImmutableTuple<F, S> implements Serializable permits Tuple<F, S> {
// public
	public ImmutableTuple() {
		first = null;
		second = null;
	}
	public ImmutableTuple(F first, S second) {
		this.first = first;
		this.second = second;
	}
	public ImmutableTuple(@NonNull ImmutableTuple<F, S> other) {
		NonNull.Check.params(other);
		first = other.first;
		second = other.second;
	}

	public F getFirst() {
		return first;
	}
	public S getSecond() {
		return second;
	}
	@NonNull
	public ImmutableTuple<S, F> swap() {
		return new ImmutableTuple<>(second, first);
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof final ImmutableTuple<?, ?> tuple))
			return false;
		return first.equals(tuple.first) && second.equals(tuple.second);
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + Utility.toEqualsCompliant(first).hashCode();
		return prime * result + Utility.toEqualsCompliant(second).hashCode();
	}
	@Override
	public final String toString() {
		return "[" + first + ", " + second + "]";
	}

// protected
	protected F first;
	protected S second;

// private
	private static final long serialVersionUID = 1L;
}

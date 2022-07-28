package com.lcj.commons;

import java.io.Serializable;
import com.lcj.commons.checks.NonNull;

public sealed class ImmutableUnion<D, A> implements Serializable permits Union<D, A> {
// public
	public static <D, A> ImmutableUnion<D, A> from(D dValue) {
		return new ImmutableUnion<D, A>().setDefault(dValue);
	}
	public static <D, A> ImmutableUnion<D, A> fromAlt(A aValue) {
		return new ImmutableUnion<D, A>().setAlternate(aValue);
	}

	public ImmutableUnion(@NonNull ImmutableUnion<D, A> other) {
		NonNull.Check.params(other);
		if (other.type() == UnionType.DEFAULT)
			setDefault(other.get());
		else
			setAlternate(other.getAlt());
	}

	public final D get() {
		return def;
	}
	public final A getAlt() {
		return alt;
	}
	@SuppressWarnings("unchecked") @NonNull
	public final ImmutableUnion<A, D> swap() {
		return (ImmutableUnion<A, D>) ImmutableUnion.fromAlt(alt);
	}
	public final UnionType type() {
		return type;
	}

	// Test for equal member types, not equal value
	@Override
	public final boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof final ImmutableUnion<?,?> union))
			return false;
		return current().equals(union.current());
	}
	@Override
	public final int hashCode() {
		return Utility.toEqualsCompliant(current()).hashCode();
	}
	@NonNull
	@Override
	public final String toString() {
		final Class<?> curClass = current().getClass();
		final String name = curClass == Utility.NO_VALUE.getClass() ? "n/a" : curClass.getName();

		return "(Union" + name + " = ) " + current().toString();
	}

// protected
	protected ImmutableUnion() {
	}

	protected final ImmutableUnion<D, A> setDefault(D obj) {
		def = obj;
		alt = null;
		type = UnionType.DEFAULT;
		return this;
	}
	protected final ImmutableUnion<D, A> setAlternate(A obj) {
		def = null;
		alt = obj;
		type = UnionType.ALTERNATIVE;
		return this;
	}

// private
	private static final long serialVersionUID = 1L;
	private D def;
	private A alt;
	private UnionType type;

	private final Object current() {
		return type() == UnionType.DEFAULT ? def : alt;
	}
}

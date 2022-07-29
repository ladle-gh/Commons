package com.lcj.commons.util;

import java.io.Serializable;
import com.lcj.commons.Utility;


public sealed class ImmutableUnion<D, A> implements Serializable permits Union<D, A> {
	public static <D, A> ImmutableUnion<D, A> from(D dValue) {
		return new ImmutableUnion<D, A>().setDefault(dValue);
	}
	public static <D, A> ImmutableUnion<D, A> fromAlt(A aValue) {
		return new ImmutableUnion<D, A>().setAlternate(aValue);
	}

	public ImmutableUnion( ImmutableUnion<D, A> other) {
		
		if (other.type() == UnionMember.DEFAULT)
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
	@SuppressWarnings("unchecked") 
	public final ImmutableUnion<A, D> swap() {
		return (ImmutableUnion<A, D>) ImmutableUnion.fromAlt(alt);
	}
	public final UnionMember type() {
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
	
	@Override
	public final String toString() {
		final String name = current().getClass().getName();

		return "(Union" + name + " = ) " + current().toString();
	}

	protected ImmutableUnion() {
	}

	protected final ImmutableUnion<D, A> setDefault(D obj) {
		def = obj;
		alt = null;
		type = UnionMember.DEFAULT;
		return this;
	}
	protected final ImmutableUnion<D, A> setAlternate(A obj) {
		def = null;
		alt = obj;
		type = UnionMember.ALTERNATIVE;
		return this;
	}

	private static final long serialVersionUID = 1L;
	private D def;
	private A alt;
	private UnionMember type;

	private final Object current() {
		return type() == UnionMember.DEFAULT ? def : alt;
	}
}

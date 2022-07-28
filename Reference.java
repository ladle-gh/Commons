package com.lcj.commons;

import java.io.Serializable;
import com.lcj.commons.checks.NonNull;

public final class Reference<T> implements Serializable {
// public
	public T self;

	public Reference() {
		self = null;
	}
	public Reference(T self) {
		this.self = self;
	}
	public Reference(@NonNull Reference<T> other) {
		NonNull.Check.params(other);
		self = other.self;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Reference<?>))
			return false;
		return self.equals(((Reference<?>) obj).self);
	}
	@Override
	public int hashCode() {
		return Utility.toEqualsCompliant(self).hashCode();
	}
	@Override
	public String toString() {
		return "(" + self.getClass().getName() + " &) " + self; 
	}

// private
	private static final long serialVersionUID = 1L;
}

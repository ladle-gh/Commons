package com.lcj.commons.util;

import java.io.Serializable;
import com.lcj.commons.Utility;

public final class ObjectReference<T> implements Serializable {
	public T self;

	public ObjectReference() {
		self = null;
	}
	public ObjectReference(T self) {
		this.self = self;
	}
	public ObjectReference(ObjectReference<T> other) {

		self = other.self;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ObjectReference<?>))
			return false;
		return self.equals(((ObjectReference<?>) obj).self);
	}
	@Override
	public int hashCode() {
		return Utility.toEqualsCompliant(self).hashCode();
	}
	@Override
	public String toString() {
		return "(" + self.getClass().getName() + " &) " + self;
	}

	private static final long serialVersionUID = 1L;
}

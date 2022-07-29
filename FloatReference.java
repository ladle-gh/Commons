package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class FloatReference implements Serializable {
	public float self;

	public FloatReference() {
		self = 0.0F;
	}
	public FloatReference(float self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

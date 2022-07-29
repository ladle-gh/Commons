package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class DoubleReference implements Serializable {
	public double self;

	public DoubleReference() {
		self = 0.0;
	}
	public DoubleReference(double self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class BooleanReference implements Serializable {
	public boolean self;

	public BooleanReference() {
		self = false;
	}
	public BooleanReference(boolean self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

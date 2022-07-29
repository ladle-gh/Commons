package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class IntReference implements Serializable {
	public int self;

	public IntReference() {
		self = 0;
	}
	public IntReference(int self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

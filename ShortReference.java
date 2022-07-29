package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class ShortReference implements Serializable {
	public short self;

	public ShortReference() {
		self = (short) 0;
	}
	public ShortReference(short self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

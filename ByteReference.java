package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class ByteReference implements Serializable {
	public byte self;

	public ByteReference() {
		self = (byte) 0;
	}
	public ByteReference(byte self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

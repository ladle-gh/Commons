package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class LongReference implements Serializable {
	public long self;

	public LongReference() {
		self = 0L;
	}
	public LongReference(long self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

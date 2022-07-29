package com.lcj.commons.util.ref;

import java.io.Serializable;

public final class CharReference implements Serializable {
	public char self;

	public CharReference() {
		self = '\0';
	}
	public CharReference(char self) {
		this.self = self;
	}

	private static final long serialVersionUID = 1L;
}

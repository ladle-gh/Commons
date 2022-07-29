package com.lcj.commons;

public enum SeekMode {
	INCLUSIVE(SeekFlag.FORWARD | SeekFlag.INCLUSIVE),
	EXCLUSIVE(SeekFlag.FORWARD | SeekFlag.EXCLUSIVE),
	R_INCLUSIVE(SeekFlag.REVERSE | SeekFlag.INCLUSIVE),
	R_EXCLUSIVE(SeekFlag.REVERSE | SeekFlag.EXCLUSIVE);

	final int flags;

	private SeekMode(int flags) {
		this.flags = flags;
	}
}

final class SeekFlag {
	public static final int FORWARD   = 0b0001;
	public static final int REVERSE   = 0b0010;
	public static final int INCLUSIVE = 0b0100;
	public static final int EXCLUSIVE = 0b1000;
	
}
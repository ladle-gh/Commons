package com.lcj.commons.function;

@FunctionalInterface
public interface MemberSupplier<T> {
	public T get(int index);
}

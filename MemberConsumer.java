package com.lcj.commons.function;

@FunctionalInterface
public interface MemberConsumer<T> {
	void accept(T obj, int index);
}

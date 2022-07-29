package com.lcj.commons.function;

@FunctionalInterface
public interface MemberFunction<T, R>  {
	public R apply(T obj, int index);
}
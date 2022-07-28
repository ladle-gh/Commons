package com.lcj.commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import com.lcj.commons.checks.NonNull;

@SuppressWarnings("unchecked")
public final class ObjectArray<T> extends ImmutableArray<T> implements Serializable {
// public
	public ObjectArray() {
		super(Commons.DEFAULT_ARRAY_SIZE);
	}
	public ObjectArray(Supplier<? extends T> action) {
		super(Commons.DEFAULT_ARRAY_SIZE);
		applyToEach(action);
	}
	public ObjectArray(IndexedSupplier<? extends T> action) {
		super(Commons.DEFAULT_ARRAY_SIZE);
		applyToEach(action);
	}
	public ObjectArray(int capacity) {
		super(capacity);
	}
	public ObjectArray(Supplier<? extends T> action, int capacity) {
		super(capacity);
		applyToEach(action);
	}
	public ObjectArray(IndexedSupplier<? extends T> action, int capacity) {
		super(capacity);
		applyToEach(action);
	}
	@SafeVarargs
	public ObjectArray(T... objs) {
		super(objs);
	}
	public ObjectArray(Supplier<? extends T> action, T... objs) {
		super(objs);
		applyToEach(action);
	}
	public ObjectArray(IndexedSupplier<? extends T> action, T... objs) {
		super(objs);
		applyToEach(action);
	}
	public ObjectArray(@NonNull ImmutableArray<T> other) {
		super(other);
	}

	public ObjectArray<T> applyEach(Supplier<? extends T> action) {
		super.applyToEach(action);
		return this;
	}
	public ObjectArray<T> applyEach(IndexedSupplier<? extends T> action) {
		super.applyToEach(action);
		return this;
	}
	public ObjectArray<T> mutateEach(Function<T, ? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.apply((T) self[i]);
		return this;
	}
	public ObjectArray<T> mutateEach(IndexedFunction<T, ? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.apply((T) self[i], i);
		return this;
	}
	public T set(int index, T obj) {
		return (T) (self[index] = obj);
	}
	@NonNull
	public T[] asArray(Class<T[]> type) {
		if (!isTypeKnown)
			self = Arrays.copyOf(self, self.length, type);
		return (T[]) self;
	}

// private
	private static final long serialVersionUID = 1L;
}

package com.lcj.commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.lcj.commons.checks.NonNull;

/**
 * 
 *
 * @param <T> Type of array members
 */
@SuppressWarnings("unchecked")
public sealed class ImmutableArray<T> implements Iterable<T>, Serializable permits ObjectArray<T> {
// public
	public final int length;

	@FunctionalInterface
	public static interface IndexedConsumer<T> {
		public void accept(T obj, int index);
	}
	@FunctionalInterface
	public static interface IndexedFunction<T, R>  {
		public R apply(T obj, int index);
	}
	@FunctionalInterface
	public static interface IndexedSupplier<T> {
		public T get(int index);
	}

	@SafeVarargs
	public ImmutableArray(@NonNull T... objs) {
		NonNull.Check.params(objs);
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
	}
	public ImmutableArray(Supplier<T> defaultInitializer, @NonNull T... objs) {
		NonNull.Check.params(objs);
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
		applyToEach(defaultInitializer);
	}
	public ImmutableArray(IndexedSupplier<T> defaultInitializer, @NonNull T... objs) {
		NonNull.Check.params(objs);
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
		applyToEach(defaultInitializer);
	}
	public ImmutableArray(@NonNull ImmutableArray<T> other) {
		NonNull.Check.params(other);
		length = other.length;
		self = other.self.clone();
		isTypeKnown = other.isTypeKnown;
	}


	public final T get(int index) {
		return (T) self[index];
	}
	@NonNull
	public final T[] toArray(Class<T[]> type) {
		return isTypeKnown ? (T[]) self.clone() : Arrays.copyOf(self, self.length, type);
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ImmutableArray<?>))
			return false;
		return Arrays.equals(self, ((ImmutableArray<?>) obj).self);
	}
	@Override
	public final void forEach(Consumer<? super T> action) {
		for (int i = 0; i < self.length; ++i)
			action.accept((T) self[i]);
	}
	@Override
	public final Iterator<T> iterator() {
		return new Iterator<>() {
//		public
			public void forEachRemaining(IndexedConsumer<? super T> action) {
				for (int i = 0; i < self.length; ++i)
					action.accept((T) self[i], i);
			}

			@Override
			public void forEachRemaining(Consumer<? super T> action) {
				for (int j = i; j < self.length; ++j)
					action.accept((T) self[j]);
			}
			@Override
			public boolean hasNext() {
				return i < self.length;
			}
			@Override
			public T next() {
				return (T) self[i++];
			}

//		private
			private int i = 0;
		};
	}
	@Override
	public final int hashCode() {
		return Arrays.hashCode(self);
	}
	@Override
	public final String toString() {
		return Arrays.toString(self);
	}

// protected
	protected @NonNull Object[] self;
	protected boolean isTypeKnown;

	protected ImmutableArray() {
		self = new Object[length = Commons.DEFAULT_ARRAY_SIZE];
		isTypeKnown = false;
	}
	protected ImmutableArray(int capacity) {
		self = new Object[length = capacity];
		isTypeKnown = false;
	}
	protected ImmutableArray(int capacity, T... objs) {
		self = Arrays.copyOf(objs, length = objs.length + capacity);
		isTypeKnown = true;
	}

	protected final void applyToEach(Supplier<? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.get();
	}
	protected final void applyToEach(IndexedSupplier<? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.get(i);
	}

// private
	private static final long serialVersionUID = 1L;
}

package com.lcj.commons.util;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import com.lcj.commons.Commons;
import com.lcj.commons.function.MemberFunction;
import com.lcj.commons.function.MemberSupplier;

/**
 * Mutable implementation of ImmutableArray.
 *
 * @param <T> Type of array members
 * @see ImmutableArray
 */
@SuppressWarnings("unchecked")
public final class ObjectArray<T> extends ImmutableArray<T> {
	public static <T> ObjectArray<T> alias( T[] array) {
		return new ObjectArray<>((Object) array);
	}
	public static <T> ObjectArray<T>.Iterator iterator( T[] array) {
		return new ObjectArray<>(array).iterator();
	}

	public final class Iterator extends ImmutableArray<T>.Iterator {
		public ObjectArray<T> applyEachRemaining(Supplier<? extends T> action) {
			for (int j = i; j < self.length; ++j)
				self[j] = action.get();
			return ObjectArray.this;
		}
		public ObjectArray<T> applyEachRemaining(MemberSupplier<? extends T> action) {
			for (int j = i; j < self.length; ++j)
				self[j] = action.get(j);
			return ObjectArray.this;
		}
		public ObjectArray<T> mutateEachRemaining(Function<? super T, ? extends T> action) {
			for (int j = i; j < self.length; ++j)
				self[j] = action.apply((T) self[j]);
			return ObjectArray.this;
		}
		public ObjectArray<T> mutateEachRemaining(MemberFunction<? super T, ? extends T> action) {
			for (int j = i; j < self.length; ++j)
				self[j] = action.apply((T) self[j], j);
			return ObjectArray.this;
		}
	
		private Iterator() {
		}
	}

	public ObjectArray() {
		super(Commons.DEFAULT_ARRAY_SIZE);
	}
	public ObjectArray(int capacity) {
		super(capacity);
	}
	public ObjectArray(Supplier<? extends T> defaultInitializer) {
		super(Commons.DEFAULT_ARRAY_SIZE);
		applyToEach(defaultInitializer);
	}
	public ObjectArray(MemberSupplier<? extends T> defaultInitializer) {
		super(Commons.DEFAULT_ARRAY_SIZE);
		applyToEach(defaultInitializer);
	}
	public ObjectArray(Supplier<? extends T> defaultInitializer, int capacity) {
		super(capacity);
		applyToEach(defaultInitializer);
	}
	public ObjectArray(MemberSupplier<? extends T> defaultInitializer, int capacity) {
		super(capacity);
		applyToEach(defaultInitializer);
	}
	@SafeVarargs
	public ObjectArray( T... objs) {
		super(objs.clone());
	}
	public ObjectArray(Supplier<? extends T> defaultInitializer,  T... objs) {
		super(objs);
		applyToEach(defaultInitializer);
	}
	public ObjectArray( MemberSupplier<? extends T> defaultInitializer,  T... objs) {
		super(objs);
		applyToEach(defaultInitializer);
	}
	public ObjectArray( ObjectArray<T> other) {
		super(other);
	}

	
	public T[] asArray(Class<T[]> type) {
		if (!isTypeKnown)
			self = Arrays.copyOf(self, self.length, type);
		return (T[]) self;
	}
	public T set(int index, T obj) {
		return (T) (self[index] = obj);
	}

	@Override
	public Iterator iterator() {
		return new Iterator();
	}

	private static final long serialVersionUID = 1L;
	
	private ObjectArray(Object array) {
		super(( T[]) array);
	}
}

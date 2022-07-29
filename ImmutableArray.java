package com.lcj.commons.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.lcj.commons.function.MemberConsumer;
import com.lcj.commons.function.MemberSupplier;

/**
 * Represents an array of objects. Positions in the array may not be reassigned
 * the object they reference.<br>
 * <br>
 * This class assists in the creation of type-safe, expressive, and easy-use generic arrays within classes that require them. Additionally,  than what one could use by
 * simply populating an {@code Object[]} with objects of a generic type.<br>
 * <br>
 * For example, the following:
 * 
 * <pre>
 * {@code
 * 		class ClassicGenericArray<T> {
 * 			Object[] arrayOfTs;
 * 			T[] actualTs;	// = instance of T[]
 *
 * 			void initialize(T x, T y, T z) {
 * 				arrayOfTs = new Object[] {x, y, z};
 * 			}
 *			void initializeUsingSuppplier() {
 *				arrayOfTs = new Object[10];
 *				for (int i = 0; i < arrayOfTs.length; ++i)
 *					arrayOfTs[i] = actualTs[0];
 *			}
 *			void initializeUsingIndexedSuppplier() {
 *				arrayOfTs = new Object[10];
 *				for (int i = 0; i < arrayOfTs.length; ++i)
 *					arrayOfTs[i] = actualTs[i];
 *			}
 * 			T getMember {
 * 				return T
 * 			}
 * 			var makeTArrayCopy {
 * 				
 * 			}
 * 			void invokeForEach {
 * 				
 * 			}
 * 			void invokeForEachRemaining {
 * 				
 * 			}
 * 		}
 * }
 * </pre>
 * 
 * is equivalent to:
 * 
 * <pre>
 * {@code
 * 		class ImprovedGenericArray<T> {
 * 			ImmutableArray<T> arrayOfTs;
 * 			T[] actualTs;	// = instance of T[]
 * 
 * 			void initialize(T x, T y, T z) {
 * 				arrayOfTs = new ImmutableArray<>(x, y, z);
 * 			}
 * 			void initializeUsingSuppplier() {
 * 				arrayOfTs = new ImmutableArray<>(10, () -> actualTs[0]);
 * 			}
 *   		void initializeUsingIndexedSuppplier() {
 * 				arrayOfTs = new ImmutableArray<>(10, (i) -> actualTs[i]);
 * 			}
 * 			var getMember {
 * 				
 * 			}
 * 			var makeTArrayCopy {
 * 				
 * 			}
 * 			void invokeForEach {
 * 				
 * 			}
 * 			void invokeForEachRemaining {
 * 				
 * 			}
 * }
 * </pre>
 * 
 * Although similar results may be achieved by 
 * @param <T> Type of array members
 * @see ObjectArray
 * @apiNote
 *          ewd
 */
@SuppressWarnings("unchecked")
public sealed class ImmutableArray<T> implements Iterable<T>, Serializable permits ObjectArray<T> {
	public final int length;

	public sealed class Iterator implements java.util.Iterator<T>permits ObjectArray<T>.Iterator {
		public final void forEachRemaining(MemberConsumer<? super T> action) {
			for (int j = i; j < self.length; ++j)
				action.accept((T) self[j], j);
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

		protected int i = 0;

		protected Iterator() {
		}
	};

	public ImmutableArray( Supplier<T> defaultInitializer, int capacity) {
		
		self = new Object[length = capacity];
		isTypeKnown = false;
		applyToEach(defaultInitializer);
	}
	public ImmutableArray( MemberSupplier<T> defaultInitializer, int capacity) {
		
		self = new Object[length = capacity];
		isTypeKnown = false;
		applyToEach(defaultInitializer);
	}
	@SafeVarargs
	public ImmutableArray( T... objs) {
		
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
	}
	public ImmutableArray( Supplier<T> defaultInitializer,  T... objs) {
		
		
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
		applyToEach(defaultInitializer);
	}
	public ImmutableArray( MemberSupplier<T> defaultInitializer,  T... objs) {
		
		length = objs.length;
		self = objs.clone();
		isTypeKnown = objs.length != 0;
		applyToEach(defaultInitializer);
	}
	public ImmutableArray( ObjectArray<T> objArray) {
		
		length = objArray.length;
		self = objArray.self.clone();
		isTypeKnown = objArray.isTypeKnown;
	}
	public ImmutableArray( ImmutableArray<T> other) {
		
		length = other.length;
		self = other.self;
		isTypeKnown = other.isTypeKnown;
	}

	public final T get(int index) {
		return (T) self[index];
	}
	
	public final T[] toArray( Class<T[]> type) {
		
		if (isTypeKnown)
			return (T[]) self.clone();
		self = Arrays.copyOf(self, self.length, type);
		isTypeKnown = true;
		return (T[]) self;
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
	public Iterator iterator() {
		return new Iterator();
	}
	@Override
	public final int hashCode() {
		return Arrays.hashCode(self);
	}
	@Override
	public final String toString() {
		return Arrays.toString(self);
	}

	protected  Object[] self;
	protected boolean isTypeKnown;

	protected ImmutableArray(int capacity) {
		self = new Object[length = capacity];
		isTypeKnown = false;
	}

	protected final void applyToEach(Supplier<? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.get();
	}
	protected final void applyToEach(MemberSupplier<? extends T> action) {
		for (int i = 0; i < self.length; ++i)
			self[i] = action.get(i);
	}

	private static final long serialVersionUID = 1L;
}

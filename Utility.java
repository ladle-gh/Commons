package com.lcj.commons;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import com.lcj.commons.checks.Container;
import com.lcj.commons.checks.NonNull;

@Container
public final class Utility {
// public
	public static final int NO_INDEX = -1;
	public static final Object NO_VALUE = new Object() {
		@Override
		public boolean equals(Object obj) {
			return false;
		}
		@Override
		public int hashCode() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("(Utility.NO_VALUE) Attempted hashing of absent value");
		}
		@Override
		public String toString() {
			return "(none)";
		}
	};
	public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
	public static final double ACCEPTABLE_LOAD = 0.75;

	public static <T> void applyEach(@NonNull T[] array, UnaryOperator<T> operator) {
		for (int i = 0; i < array.length; ++i)
			array[i] = operator.apply(array[i]);
	}
	public static <T> void applyEach(@NonNull T[] array, BiFunction<T, Integer, T> operator) {
		for (int i = 0; i < array.length; ++i)
			array[i] = operator.apply(array[i], i);
	}
	public static <T> void forEach(@NonNull T[] array, Consumer<T> action) {
		for (final @NonNull T element : array)
			action.accept(element);
	}
	public static <T> void forEach(@NonNull T[] array, BiConsumer<T, Integer> action) {
		for (int i = 0; i < array.length; ++i)
			action.accept(array[i], i);
	}
	@NonNull
	public static String seek(@NonNull String in, @NonNull String is) {
		return doStringSeek(in, is, NO_INDEX, Commons.DEFAULT_SEEK);
	}
	@NonNull
	public static String seek(@NonNull String in, @NonNull String is, byte type) {
		return doStringSeek(in, is, NO_INDEX, type);
	}
	@NonNull
	public static String seek(@NonNull String in, @NonNull String is, int from) {
		return doStringSeek(in, is, from, Commons.DEFAULT_SEEK);
	}
	@NonNull
	public static String seek(@NonNull String in, @NonNull String is, int from, byte type) {
		return doStringSeek(in, is, from, type);
	}
	@NonNull
	public static <T> T[] seek(@NonNull T[] in, @NonNull T[] is) {
		return doSeek(in, is, NO_INDEX, Commons.DEFAULT_SEEK);
	}
	@NonNull
	public static <T> T[] seek(@NonNull T[] in, @NonNull T[] is, byte type) {
		return doSeek(in, is, NO_INDEX, type);
	}
	@NonNull
	public static <T> T[] seek(@NonNull T[] in, @NonNull T[] is, int from) {
		return doSeek(in, is, from, Commons.DEFAULT_SEEK);
	}
	@NonNull
	public static <T> T[] seek(@NonNull T[] in, @NonNull T[] is, int from, byte type) {
		return doSeek(in, is, from, type);
	}
	@SuppressWarnings("unchecked") @NonNull
	public static <T> String toDelimitedString(@NonNull Iterable<T> iterable) {
		return toDelimitedString(iterable, ", ", (Function<T, Object>) Function.identity());
	}
	@NonNull
	public static <T> String toDelimitedString(@NonNull Iterable<T> iterable, @NonNull Function<T, Object> forEach) {
		NonNull.Check.params(forEach);

		return toDelimitedString(iterable, ", ", forEach);
	}
	@NonNull
	public static <T> String toDelimitedString(@NonNull Iterable<T> iterable, @NonNull String delimiter,
	                                           Function<T, Object> forEach)
	{
		NonNull.Check.params(iterable, delimiter);

		final StringBuilder sb = new StringBuilder();
		final Iterator<T> i = iterable.iterator();

		if (i.hasNext()) {
			sb.append(forEach.apply(i.next()));
			while (i.hasNext())
				sb.append(delimiter).append(forEach.apply(i.next()));
		}
		return sb.toString();
	}
	@NonNull
	public static <T> String toDelimitedString(@NonNull @NonNull.Members T[] array) {
		return toDelimitedString(array, ", ", Function.identity());
	}
	@NonNull
	public static <T> String toDelimitedString(@NonNull @NonNull.Members T[] array,
	                                           @NonNull Function<T, Object> forEach)
	{
		NonNull.Check.params(forEach);

		return toDelimitedString(array, ", ", forEach);
	}
	@NonNull
	public static <T> String toDelimitedString(@NonNull @NonNull.Members T[] array, @NonNull String delimiter,
	                                           Function<T, Object> forEach)
	{
		NonNull.Check.params(array, delimiter);
		NonNull.Check.members(array);

		final StringBuilder sb = new StringBuilder();

		if (array.length > 0) {
			sb.append(forEach.apply(array[0]));
			for (int i = 1; i < array.length; ++i)
				sb.append(delimiter).append(forEach.apply(array[i]));
		}

		return sb.toString();
	}
	@NonNull
	public static Object toEqualsCompliant(@NonNull Object obj) {
		NonNull.Check.params(obj);
		try {
			return new Object() {
				// public
				@Override
				public boolean equals(Object o) {
					return obj.equals(o);
				}
				@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;

					result = prime * result + (obj == null ? 0 : obj.hashCode());
					return prime * result + equalityComparable.hashCode();

				}
				@Override
				public String toString() {
					return "(=" + equalityComparable.getName() + ") " + obj;
				}

				// private
				private final Class<?> equalityComparable = obj.getClass().getDeclaredMethod("clone")
				    .getDeclaringClass();
			};
		} catch (final NoSuchMethodException e) {
			return null; // Unreachable
		}
	}
	@NonNull
	public static Boolean[] toObjects(@NonNull boolean[] booleans) {
		NonNull.Check.params(booleans);

		final Boolean[] objs = new Boolean[booleans.length];

		for (int i = 0; i < booleans.length; ++i)
			objs[i] = booleans[i];
		return objs;
	}
	@NonNull
	public static Byte[] toObjects(@NonNull byte[] bytes) {
		NonNull.Check.params(bytes);

		final Byte[] objs = new Byte[bytes.length];

		for (int i = 0; i < bytes.length; ++i)
			objs[i] = bytes[i];
		return objs;
	}
	@NonNull
	public static Character[] toObjects(@NonNull char[] chars) {
		NonNull.Check.params(chars);

		final Character[] objs = new Character[chars.length];

		for (int i = 0; i < chars.length; ++i)
			objs[i] = chars[i];
		return objs;
	}
	@NonNull
	public static Double[] toObjects(@NonNull double[] doubles) {
		NonNull.Check.params(doubles);

		final Double[] objs = new Double[doubles.length];

		for (int i = 0; i < doubles.length; ++i)
			objs[i] = doubles[i];
		return objs;
	}
	@NonNull
	public static Float[] toObjects(@NonNull float[] floats) {
		NonNull.Check.params(floats);

		final Float[] objs = new Float[floats.length];

		for (int i = 0; i < floats.length; ++i)
			objs[i] = floats[i];
		return objs;
	}
	@NonNull
	public static Integer[] toObjects(@NonNull int[] ints) {
		NonNull.Check.params(ints);

		final Integer[] objs = new Integer[ints.length];

		for (int i = 0; i < ints.length; ++i)
			objs[i] = ints[i];
		return objs;
	}
	@NonNull
	public static Long[] toObjects(@NonNull long[] longs) {
		NonNull.Check.params(longs);

		final Long[] objs = new Long[longs.length];

		for (int i = 0; i < longs.length; ++i)
			objs[i] = longs[i];
		return objs;
	}
	@NonNull
	public static Short[] toObjects(@NonNull short[] shorts) {
		NonNull.Check.params(shorts);

		final Short[] objs = new Short[shorts.length];

		for (int i = 0; i < shorts.length; ++i)
			objs[i] = shorts[i];
		return objs;
	}
	@NonNull
	public static boolean[] toPrimitives(@NonNull Boolean[] objs) {
		NonNull.Check.params(objs);

		final boolean[] booleans = new boolean[objs.length];

		for (int i = 0; i < objs.length; ++i)
			booleans[i] = objs[i] == null ? false : objs[i];
		return booleans;
	}
	@NonNull
	public static byte[] toPrimitives(@NonNull Byte[] objs) {
		NonNull.Check.params(objs);

		final byte[] bytes = new byte[objs.length];

		for (int i = 0; i < objs.length; ++i)
			bytes[i] = objs[i] == null ? (byte) 0 : objs[i];
		return bytes;
	}
	@NonNull
	public static char[] toPrimitives(@NonNull Character[] objs) {
		NonNull.Check.params(objs);

		final char[] chars = new char[objs.length];

		for (int i = 0; i < objs.length; ++i)
			chars[i] = objs[i] == null ? '\0' : objs[i];
		return chars;
	}
	@NonNull
	public static double[] toPrimitives(@NonNull Double[] objs) {
		NonNull.Check.params(objs);

		final double[] doubles = new double[objs.length];

		for (int i = 0; i < objs.length; ++i)
			doubles[i] = objs[i] == null ? 0.0 : objs[i];
		return doubles;
	}
	@NonNull
	public static float[] toPrimitives(@NonNull Float[] objs) {
		NonNull.Check.params(objs);

		final float[] floats = new float[objs.length];

		for (int i = 0; i < objs.length; ++i)
			floats[i] = objs[i] == null ? 0.0F : objs[i];
		return floats;
	}
	@NonNull
	public static int[] toPrimitives(@NonNull Integer[] objs) {
		NonNull.Check.params(objs);

		final int[] ints = new int[objs.length];

		for (int i = 0; i < objs.length; ++i)
			ints[i] = objs[i] == null ? 0 : objs[i];
		return ints;
	}
	@NonNull
	public static long[] toPrimitives(@NonNull Long[] objs) {
		NonNull.Check.params(objs);

		final long[] longs = new long[objs.length];

		for (int i = 0; i < objs.length; ++i)
			longs[i] = objs[i] == null ? 0L : objs[i];
		return longs;
	}
	@NonNull
	public static short[] toPrimitives(@NonNull Short[] objs) {
		NonNull.Check.params(objs);

		final short[] shorts = new short[objs.length];

		for (int i = 0; i < objs.length; ++i)
			shorts[i] = objs[i] == null ? (short) 0 : objs[i];
		return shorts;
	}

// private
	private Utility() {
	}

	@NonNull
	private static <T> T[] doSeek(@NonNull T[] in, @NonNull T[] is, int from, byte type) {
		NonNull.Check.params(in, is);

		int beginSlice = NO_INDEX, endSlice = NO_INDEX;
		boolean negateMatch = false;
		final BiPredicate<Object,
		                  Object> foundMatch = (type & Seek.INCLUSIVE) != 0 ?
		                      (obj1, obj2) -> (obj1.equals(obj2) != negateMatch) :
		                      (obj1, obj2) -> (obj1.equals(obj2) == negateMatch);

		if (from == NO_INDEX)
			from = (type & Seek.FORWARD) != 0 ? 0 : in.length - 1;

		if ((type & Seek.FORWARD) != 0) {
			for (int i = from; i >= 0; --i)
				for (final Object obj : is)
					if (foundMatch.test(in[i], obj)) {
						if (negateMatch) {
							beginSlice = i + 1;
							break;
						}
						endSlice = i + 1;
					}
			if (endSlice == NO_INDEX)
				return null;
			if (beginSlice == NO_INDEX)
				beginSlice = 0;
			return Arrays.copyOfRange(in, beginSlice, endSlice);
		}
		for (int i = from; i < in.length; ++i)
			for (final Object obj : is)
				if (foundMatch.test(in[i], obj)) {
					if (negateMatch) {
						endSlice = i;
						break;
					}
					beginSlice = i;
				}
		if (beginSlice == NO_INDEX)
			return null;
		if (endSlice == NO_INDEX)
			endSlice = in.length - 1;
		return Arrays.copyOfRange(in, beginSlice, endSlice);
	}
	@NonNull
	private static String doStringSeek(@NonNull String in, @NonNull String is, int from, byte type) {
		return new String(Utility.toPrimitives(doSeek(toObjects(in.toCharArray()), toObjects(is.toCharArray()),
		                                              NO_INDEX, Commons.DEFAULT_SEEK)));
	}
}

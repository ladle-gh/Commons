package com.lcj.commons;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Contains
 *
 * @author eckar
 *
 */
public final class Utility {
	public static final int NO_INDEX = -1;
	public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

	public boolean isAsciiAlnum(char ch) {
		return isAsciiAlpha(ch) || isAsciiDigit(ch);
	}
	public boolean isAsciiAlpha(char ch) {
		return isAsciiUpper(ch) || isAsciiLower(ch);
	}
	public boolean isAsciiDigit(char ch) {
		return switch (ch) {
		case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> true;
		default -> false;
		};
	}
	public boolean isAsciiUpper(char ch) {
		return switch (ch) {
		case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' -> true;
		default -> false;
		};
	}
	public boolean isAsciiLower(char ch) {
		return switch (ch) {
		case 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> true;
		default -> false;
		};
	}
	public static String seek(String in, String is) {
		return seek(in, is, NO_INDEX, SeekMode.INCLUSIVE);
	}
	public static String seek(String in, String is, SeekMode mode) {
		return seek(in, is, NO_INDEX, mode);
	}		
	static String seek(String in, String is, int from) {
		return seek(in, is, from, SeekMode.INCLUSIVE);
	}
	public static String seek(String in, String is, int from, SeekMode mode) {
		@FunctionalInterface
		interface CharMatcher {
			public boolean matches(char ch1, char ch2, boolean modifier);
		}

		int beginSlice = NO_INDEX, endSlice = NO_INDEX;
		boolean negateMatch = false;
		final CharMatcher matcher = (ch1, ch2, mod) -> ((ch1 == ch2) == (mod && (mode.flags & SeekFlag.INCLUSIVE) == 0));

		if (from == NO_INDEX) {
			from = (mode.flags & SeekFlag.FORWARD) != 0 ? 0 : in.length() - 1;
		}
		if ((mode.flags & SeekFlag.FORWARD) != 0) {
			Forward:
			for (int i = from; i < in.length(); ++i) {
				for (int j = 0; j < is.length(); ++j) {
					if (matcher.matches(in.charAt(i), is.charAt(j), negateMatch)) {
						if (negateMatch) {
							endSlice = i;
							break Forward;
			
					}
						beginSlice = i;
						negateMatch = true;
					}
				}
			}
			if (beginSlice == NO_INDEX)
				return null;
			if (endSlice == NO_INDEX) {
				endSlice = in.length() - 1;
			}
		} else {
			Backward: for (int i = from; i >= 0; --i) {
				for (int j = 0; j < is.length(); ++j) {
					if (matcher.matches(in.charAt(i), is.charAt(j), negateMatch)) {
						if (negateMatch) {
							beginSlice = i + 1;
							break Backward;
					
			}
						endSlice = i + 1;
						negateMatch = true;
					}
				}
			}
			if (endSlice == NO_INDEX)
				return null;
			if (beginSlice == NO_INDEX) {
				beginSlice = 0;
			}
		}
		return in.substring(beginSlice, endSlice);
	}
	public static <T> T[] seek(T[] in, T[] is) {
		return seek(in, is, NO_INDEX, SeekMode.INCLUSIVE);
	}

	public static <T> T[] seek(T[] in, T[] is, SeekMode mode) {
		return seek(in, is, NO_INDEX, mode);
	}

	public static <T> T[] seek(T[] in, T[] is, int from) {
		return seek(in, is, from, SeekMode.INCLUSIVE);
	}
	public static <T> T[] seek(T[] in, T[] is, int from, SeekMode mode) {
		@FunctionalInterface
		interface ObjectMatcher {
			public boolean matches(Object obj1, Object obj2, boolean modifier);
		}

		int beginSlice = NO_INDEX, endSlice = NO_INDEX;
		boolean negateMatch = false;
		final ObjectMatcher matcher = (obj1, obj2, mod) -> obj1.equals(obj2) == (mod && (mode.flags & SeekFlag.INCLUSIVE) == 0);

		if (from == NO_INDEX) {
			from = (mode.flags & SeekFlag.FORWARD) != 0 ? 0 : in.length - 1;
		}
		if ((mode.flags & SeekFlag.FORWARD) != 0) {
			Forward:
			for (int i = from; i < in.length; ++i) {
				for (final Object obj : is) {
					if (matcher.matches(in[i], obj, negateMatch)) {
						if (negateMatch) {
							endSlice = i;
							break Forward;
						}
						beginSlice = i;
						negateMatch = true;
					}
				}
			}
			if (beginSlice == NO_INDEX)
				return null;
			if (endSlice == NO_INDEX) {
				endSlice = in.length - 1;
			}
		} else {
			Backward:
			for (int i = from; i >= 0; --i) {
				for (final Object obj : is) {
					if (matcher.matches(in[i], obj, negateMatch)) {
						if (negateMatch) {
							beginSlice = i + 1;
							break Backward;
						}
						endSlice = i + 1;
						negateMatch = true;
					}
				}
			}
			if (endSlice == NO_INDEX)
				return null;
			if (beginSlice == NO_INDEX) {
				beginSlice = 0;
			}
		}
		return Arrays.copyOfRange(in, beginSlice, endSlice);
	}
	@SuppressWarnings("unchecked")
	public static <T> String toDelimitedString(Iterable<T> iterable) {
		return toDelimitedString(iterable, ", ", (Function<T, Object>) Function.identity());
	}

	public static <T> String toDelimitedString(Iterable<T> iterable, Function<T, Object> forEach) {

		return toDelimitedString(iterable, ", ", forEach);
	}

	public static <T> String toDelimitedString(Iterable<T> iterable, String delimiter, Function<T, Object> forEach) {

		final StringBuilder sb = new StringBuilder();
		final Iterator<T> i = iterable.iterator();

		if (i.hasNext()) {
			sb.append(forEach.apply(i.next()));
			while (i.hasNext()) {
				sb.append(delimiter).append(forEach.apply(i.next()));
			}
		}
		return sb.toString();
	}

	public static <T> String toDelimitedString(T[] array) {
		return toDelimitedString(array, ", ", Function.identity());
	}

	public static <T> String toDelimitedString(T[] array, Function<T, Object> forEach) {

		return toDelimitedString(array, ", ", forEach);
	}

	public static <T> String toDelimitedString(T[] array, String delimiter, Function<T, Object> forEach) {

		final StringBuilder sb = new StringBuilder();

		if (array.length > 0) {
			sb.append(forEach.apply(array[0]));
			for (int i = 1; i < array.length; ++i) {
				sb.append(delimiter).append(forEach.apply(array[i]));
			}
		}

		return sb.toString();
	}

	public static Object toEqualsCompliant(Object obj) {

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

	public static Boolean[] toObjects(boolean[] booleans) {

		final Boolean[] objs = new Boolean[booleans.length];

		for (int i = 0; i < booleans.length; ++i) {
			objs[i] = booleans[i];
		}
		return objs;
	}

	public static Byte[] toObjects(byte[] bytes) {

		final Byte[] objs = new Byte[bytes.length];

		for (int i = 0; i < bytes.length; ++i) {
			objs[i] = bytes[i];
		}
		return objs;
	}

	public static Character[] toObjects(char[] chars) {

		final Character[] objs = new Character[chars.length];

		for (int i = 0; i < chars.length; ++i) {
			objs[i] = chars[i];
		}
		return objs;
	}

	public static Double[] toObjects(double[] doubles) {

		final Double[] objs = new Double[doubles.length];

		for (int i = 0; i < doubles.length; ++i) {
			objs[i] = doubles[i];
		}
		return objs;
	}

	public static Float[] toObjects(float[] floats) {

		final Float[] objs = new Float[floats.length];

		for (int i = 0; i < floats.length; ++i) {
			objs[i] = floats[i];
		}
		return objs;
	}

	public static Integer[] toObjects(int[] ints) {

		final Integer[] objs = new Integer[ints.length];

		for (int i = 0; i < ints.length; ++i) {
			objs[i] = ints[i];
		}
		return objs;
	}

	public static Long[] toObjects(long[] longs) {

		final Long[] objs = new Long[longs.length];

		for (int i = 0; i < longs.length; ++i) {
			objs[i] = longs[i];
		}
		return objs;
	}

	public static Short[] toObjects(short[] shorts) {

		final Short[] objs = new Short[shorts.length];

		for (int i = 0; i < shorts.length; ++i) {
			objs[i] = shorts[i];
		}
		return objs;
	}

	public static boolean[] toPrimitives(Boolean[] objs) {

		final boolean[] booleans = new boolean[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			booleans[i] = objs[i] == null ? false : objs[i];
		}
		return booleans;
	}

	public static byte[] toPrimitives(Byte[] objs) {

		final byte[] bytes = new byte[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			bytes[i] = objs[i] == null ? (byte) 0 : objs[i];
		}
		return bytes;
	}

	public static char[] toPrimitives(Character[] objs) {

		final char[] chars = new char[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			chars[i] = objs[i] == null ? '\0' : objs[i];
		}
		return chars;
	}

	public static double[] toPrimitives(Double[] objs) {

		final double[] doubles = new double[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			doubles[i] = objs[i] == null ? 0.0 : objs[i];
		}
		return doubles;
	}

	public static float[] toPrimitives(Float[] objs) {

		final float[] floats = new float[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			floats[i] = objs[i] == null ? 0.0F : objs[i];
		}
		return floats;
	}

	public static int[] toPrimitives(Integer[] objs) {

		final int[] ints = new int[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			ints[i] = objs[i] == null ? 0 : objs[i];
		}
		return ints;
	}

	public static long[] toPrimitives(Long[] objs) {

		final long[] longs = new long[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			longs[i] = objs[i] == null ? 0L : objs[i];
		}
		return longs;
	}

	public static short[] toPrimitives(Short[] objs) {

		final short[] shorts = new short[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			shorts[i] = objs[i] == null ? (short) 0 : objs[i];
		}
		return shorts;
	}

	private Utility() {
	}
}

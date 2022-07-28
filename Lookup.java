package com.lcj.commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;
import com.lcj.commons.checks.NonNull;

/**
 *
 *
 * @param <T> Type of item to be stored in lookup table
 */
public class Lookup<T> implements Serializable {
// public
	public Lookup() {
		this.capacity = Commons.DEFAULT_LOOKUP_SIZE;
		records = new ObjectArray<>();
		size = new Reference<>(0);
	}
	/* Lower capacity = optimize for memory usage Higher capacity = optimize for
	 * speed */
	public Lookup(int capacity) {
		records = new ObjectArray<>();
		size = new Reference<>(0);
	}
	@SafeVarargs
	public Lookup(@NonNull @NonNull.Members T... items) {
		NonNull.Check.params(items);
		NonNull.Check.members(items);
		records = new ObjectArray<>(this.capacity = items.length + Commons.DEFAULT_LOOKUP_SIZE);
		size = new Reference<>(0);
		Utility.forEach(items, item -> addHash(item.hashCode()));

	}
	public Lookup(@NonNull Lookup<T> other) {
		NonNull.Check.params(other);

		records = new ObjectArray<>(i -> {
			final var otherCell = other.records.get(i);

			if (otherCell == null)
				return null;
			if (otherCell.type() == UnionType.DEFAULT)
				return Union.from(otherCell.get());
			return Union.fromAlt(otherCell.getAlt());
		}, other.records.length);
		capacity = other.capacity;
		size = other.size;
	}

	/**
	 *
	 * @param item
	 * @return this
	 */
	@NonNull
	public final Lookup<T> add(@NonNull T item) {
		NonNull.Check.params(item);
		addHash(item.hashCode());
		return this;
	}
	@SafeVarargs @NonNull
	public final Lookup<T> add(@NonNull T item, @NonNull @NonNull.Members T... more) {
		modify(item, more, this::addHash);
		return this;
	}
	/**
	 *
	 * @param item
	 * @return
	 */
	public boolean has(@NonNull T item) {
		NonNull.Check.params(item);
		return hasHash(item.hashCode());
	}
	/**
	 *
	 * @param item
	 * @return this
	 */
	@NonNull
	public Lookup<T> remove(@NonNull T item) {
		NonNull.Check.params(item);
		removeHash(item.hashCode());
		return this;
	}
	@SafeVarargs @NonNull
	public final Lookup<T> remove(@NonNull T item, @NonNull @NonNull.Members T... more) {
		modify(item, more, this::removeHash);
		return this;
	}
	@NonNull @Override
	public String toString() {
		return Arrays.toString(getHashes());
	}
	@Override
	public int hashCode() {
		return 1; // TODO
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof final Lookup<?> lookup))
			return false;
		return records.equals(lookup.records);
	}

// private
	private final static long serialVersionUID = 1L;

	private @NonNull ObjectArray<Union<Integer, Lookup<T>>> records;
	private int capacity;
	private final Reference<Integer> size; // sized int, as presumed to be well under max memory when mul by 4 (size of
	                                       // Object); references parent size

	private Lookup(int capacity, Lookup<T> parent) {
		records = new ObjectArray<>(this.capacity = capacity);
		size = parent.size;
	}

	// Adds hash to lookup; returns this
	private void addHash(int hash) {
		final int index = indexOf(hash);
		final var cell = records.get(index); // Cell where hash is to be inserted into

		if (cell == null) { // Hash not recorded
			records.set(index, Union.from(hash));
			++size.self;
			return;
		}
		switch (cell.type()) {
		case DEFAULT: // (Integer) Convert to lookup, recording both hashes inside
			final int record = cell.get();

			/* Check for duplicates By generating a lookup with record table sized 1 up,
			 * collisions disappear */
			if (record != hash) {
				final Lookup<T> lookup = cell.setAlt(getNested(records.length + 1)).getAlt();

				lookup.addHash(record);
				lookup.addHash(hash);
				++size.self;
			}
			break;
		case ALTERNATIVE: // (lookup<T>) Record hash in nested lookup
			cell.getAlt().addHash(hash);
			break;
		}
	}
	private boolean hasHash(int hash) {
		final var cell = records.get(indexOf(hash));

		return cell == null ? false : switch (cell.type()) {
		case DEFAULT -> // (Integer) Compare to recorded hash
		     cell.get() == hash;
		case ALTERNATIVE -> // (Lookup<T>) Query hash in nested lookup
		     cell.getAlt().hasHash(hash);
		};
	}
	/* This function does three things */
	@NonNull
	private Lookup<T> getNested(int length) {
		if (size.self + 1.0 / (capacity + length) < Utility.ACCEPTABLE_LOAD)
			rehash(); // Rehash to reduce load factor
		capacity += length - 1;
		return new Lookup<>(length, this);
	}
	// Returns index in underlying ObjectArray wherein hash may be inserted
	private int indexOf(int hash) {
		return Math.abs(hash % records.length);
	}
	private void modify(@NonNull T item, @NonNull @NonNull.Members T[] more, Consumer<Integer> action) {
		NonNull.Check.params(item, more);
		NonNull.Check.members(more);
		action.accept(item.hashCode());
		for (final T obj : more)
			action.accept(obj.hashCode());
	}
	private Integer[] getHashes() {
		final Integer[] hashes = new Integer[size.self];

		getHashes(hashes, 0);
		return hashes;
	}
	private int getHashes(Integer[] storage, int index) {
		Union<Integer, Lookup<T>> cur;

		for (int i = 0; i < records.length; ++i) {
			cur = records.get(i);
			if (cur != null)
				// Cell is empty
				switch (cur.type()) {
				case DEFAULT: // (Integer)
					storage[index++] = cur.get();
					break;
				case ALTERNATIVE: // (Lookup<T>)
					index = cur.getAlt().getHashes(storage, index);
					break;
				}
		}
		return index;
	}
	// Increase size of array by x2, re-assign hashes
	private void rehash() {
		final Integer[] hashes = getHashes();

		records = new ObjectArray<>(records.length * 2);
		Utility.forEach(hashes, hash -> addHash(hash));
	}
	// Removes hash from lookup; return this
	private void removeHash(int hash) {
		final int index = indexOf(hash);
		final var cell = records.get(indexOf(hash));

		if (cell == null) // Hash not recorded
			return;
		switch (cell.type()) {
		case DEFAULT: // (Integer) If record = hash, remove record
			if (cell.get() == hash)
				records.set(index, null);
			--size.self;
			break;
		case ALTERNATIVE: // (lookup<T>) Remove hash from nested lookup
			cell.getAlt().removeHash(hash);
			break;
		}
	}
}

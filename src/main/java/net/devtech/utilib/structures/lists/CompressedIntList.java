package net.devtech.utilib.structures.lists;

import it.unimi.dsi.fastutil.ints.AbstractIntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.IntConsumer;

public class CompressedIntList extends AbstractIntList {
	private final LongList storage;
	private final int intSize;
	private final int maxValue;
	private int size;
	private int realMax;
	public CompressedIntList(LongList storage, int maxValue) {
		this.realMax = maxValue;
		this.intSize = ((int) Math.ceil(Math.log10(maxValue) / Math.log10(2))); // 1 extra state for zero
		this.maxValue = (1 << this.intSize) - 1;
		this.storage = storage;
	}

	public CompressedIntList(int maxValue, int initialSize) {
		this(new LongArrayList(initialSize), maxValue);
	}

	public CompressedIntList(int maxValue) {this(maxValue, 0);}


	@Override
	public void add(int index, int k) {
		// shift all values up
		for (int i = this.size - 1; i >= index; i--) {
			int current = this.getInt(i);
			this.set(i + 1, current);
		}
		// set
		this.set(index, k);
	}

	@Override
	public int set(int index, int value) {
		return this.set(index, value, false);
	}

	private int set(int index, int value, boolean remove) {
		if (index >= this.size - 1) {
			if (value == 0 && remove) {
				if (index == this.size - 1) {
					this.size--;
				}
			} else this.size = index + 1;
		}

		int bitIndex = index * this.intSize;
		int listIndex = bitIndex >> 6;
		int topIndex = (index + 1) * this.intSize - 1 >> 6;
		int bitOffset = bitIndex ^ listIndex << 6;
		int oldTop = (int) (this.getLong(listIndex) >>> bitOffset & this.maxValue);
		// set bottom bits
		long bottom = this.getLong(listIndex) & ~(this.maxValue << bitOffset) | ((long) value & this.maxValue) << bitOffset;
		if (listIndex < this.storage.size()) this.storage.set(listIndex, bottom);
		else this.storage.add(listIndex, bottom);
		if (listIndex != topIndex) { // if sliced, get top bits
			int bitInset = 64 - bitOffset;
			int shift = this.intSize - bitInset;
			oldTop |= (int) (this.getLong(topIndex) << bitInset & this.maxValue);
			// get top bits
			long top = this.getLong(topIndex) >>> shift << shift | ((long) value & this.maxValue) >> bitInset;
			if (topIndex < this.storage.size()) {
				this.storage.set(topIndex, top);
			} else {
				this.storage.add(topIndex, top);
			}
		}
		return oldTop;
	}

	@Override
	public int removeInt(int index) {
		if (index >= this.size) throw new IndexOutOfBoundsException(index + " is larger than" + this.size);
		int val = this.set(index, 0, true);
		for (int i = index + 1; i < this.size; i++) {
			int current = this.set(i, 0);
			this.set(i - 1, current, true);
		}
		return val;
	}

	private long getLong(int index) {
		return index < this.storage.size() ? this.storage.getLong(index) : 0;
	}

	@Override
	public int getInt(int index) {
		if (index >= this.size) throw new IndexOutOfBoundsException(index + " is larger than" + this.size);
		int bitIndex = index * this.intSize;
		int listIndex = bitIndex >> 6;
		int next = (index + 1) * this.intSize - 1 >> 6;
		int l = bitIndex ^ listIndex << 6;
		int val;
		if (listIndex == next) {
			val = (int) (this.getLong(listIndex) >>> l & this.maxValue);
		} else {
			int m = 64 - l;
			val = (int) ((this.getLong(listIndex) >>> l | this.getLong(listIndex) << m) & this.maxValue);
		}
		return val;
	}

	@Override
	public void forEach(IntConsumer consumer) {
		if (this.size != 0) {
			int current = 0;
			long value = this.getLong(0);
			long next = this.size > 1 ? this.getLong(1) : 0L;

			for (int i = 0; i < this.size; ++i) {
				int bitIndex = i * this.intSize;
				int listIndex = bitIndex >> 6;
				int topIndex = (i + 1) * this.intSize - 1 >> 6;
				int bitOffset = bitIndex ^ listIndex << 6;
				if (listIndex != current) {
					value = next;
					next = listIndex + 1 < this.size ? this.getLong(listIndex + 1) : 0L;
					current = listIndex;
				}

				int val;
				if (listIndex == topIndex) {
					val = (int) (value >>> bitOffset & this.maxValue);
				} else {
					int remaining = 64 - bitOffset;
					val = (int) ((value >>> bitOffset | next << remaining) & this.maxValue);
				}
				consumer.accept(val);
			}
		}
	}

	public int listSize() {
		return this.storage.size();
	}

	public int getRealMax() {
		return this.realMax;
	}

	public LongList getStorage() {
		return this.storage;
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * for serialization
	 */
	@Deprecated
	public void setSize(int size) {
		this.size = size;
	}


}

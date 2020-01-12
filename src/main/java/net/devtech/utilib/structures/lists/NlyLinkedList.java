package net.devtech.utilib.structures.lists;

import java.util.AbstractList;
import java.util.Arrays;

public class NlyLinkedList<T> extends AbstractList<T> {
	private int step;
	private NNode<T> root;
	private int size;

	public NlyLinkedList(int step) {
		this.step = step;
	}

	@Override
	public T get(int index) {
		NNode<T> next = this.root;
		while (next != null) {
			if(index-next.skip <= 0) {
				return next.get(index);
			} else {
				index -= next.skip;
				next = next.next;
			}
		}
		throw new IndexOutOfBoundsException(index+" is out of range " + this.size());
	}

	@Override
	public T set(int index, T element) {
		this.size++;
		if(this.root == null) {
			this.root = NNode.filled(this.step);
			return this.root.set(element, 0);
		}

		NNode<T> next = this.root;
		while (index-next.skip >= 0) {
			if(next.next == null)
				next.next = NNode.filled(this.step);
			index -= next.skip;
			next = next.next;
		}


		return next.set(element, index);
	}

	@Override
	public void add(int index, T element) {
		this.size++;
		if(this.root == null) {
			this.root = NNode.filled(this.step);
			this.root.add(element, 0);
		} else {

			NNode<T> next = this.root;
			while (index - next.skip >= 0) {
				if (next.next == null) next.next = NNode.filled(this.step);
				index -= next.skip;
				next = next.next;
			}
			next.add(element, index);
		}
	}

	@Override
	public int size() {
		return this.size;
	}


	static class NNode<T> {
		private static final Object FILLER_OBJECT = new Object();
		Object[] array;
		NNode<T> next;
		int skip;

		private NNode(int step) {
			this.array = new Object[step];
		}

		public static <T> NNode<T> filled(int size) {
			NNode<T> nNode = new NNode<>(size);
			Arrays.fill(nNode.array, FILLER_OBJECT);
			nNode.skip = size;
			return nNode;
		}


		public T get(int mod) {
			int counter = 0;
			for (Object o : this.array) {
				if(o != null)
					counter++;
				if(counter-1 == mod)
					return (T) o;
			}
			return null;
		}

		public T set(T obj, int mod) {
			T old = (T) this.array[mod];
			this.array[mod] = obj;
			if(old == null) // if new value
				this.skip++;
			return old;
		}

		public void add(T obj, int mod) {
			T old = (T) this.array[mod];
			this.array[mod] = obj;
			if(old == null) {
				this.skip++;
			}
		}

		@Override
		public String toString() {
			return "NNode{" + "array=" + Arrays.toString(this.array) + ", next=" + this.next + ", skip=" + this.skip + '}';
		}
	}

	@Override
	public String toString() {
		return "NlyLinkedList{" + "step=" + step + ", root=" + root + ", size=" + size + '}';
	}
}

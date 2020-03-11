package net.devtech.utilib.java;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class MoreArrays {
	/**
	 * adds the element into the array before the given index
	 * @param old the old array
	 * @param index
	 * @param object
	 * @return
	 */
	public static Object add(Object old, int index, Object object) {
		int len = Array.getLength(old);
		Object newArray = Array.newInstance(old.getClass().getComponentType(), len+Math.max(1, index-len+1));
		System.arraycopy(old, 0, newArray, 0, Math.min(index, len));
		if(index < len)
			System.arraycopy(old, index, newArray, index+1, len-index);
		System.out.println(Array.getLength(newArray));
		Array.set(newArray, index, object);
		return newArray;
	}

	@SafeVarargs
	public static <T> T[] vargs(T...args) {
		return args;
	}

	public static <A, B> A[] map(B[] array, Function<B, A> function, IntFunction<A[]> supplier) {
		A[] arr = supplier.apply(array.length);
		for (int i = 0; i < array.length; i++) {
			arr[i] = function.apply(array[i]);
		}
		return arr;
	}
}

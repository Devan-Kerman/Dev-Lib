package net.devtech.utilib.java;

import java.lang.reflect.Array;
import java.util.Arrays;

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
}

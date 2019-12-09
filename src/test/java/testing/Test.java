package testing;

import net.devtech.structures.NlyLinkedList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class Test {

	public static void main(String[] args) {
		testList("ArrayList", 10_000, 1, 100, ArrayList::new);
		// set .0263
		// get .013
		System.out.println("\n\n\n\n");
		testList("QuadLinkedList", 10_000, 1, 1000, () -> new NlyLinkedList<>(64));
		// set .0731
		// get .0205
		System.out.println("\n\n\n\n");
		testList("OctaLinkedList", 10_000, 1, 1000, () -> new NlyLinkedList<>(128));
		// set .0325
		// get .0102
		System.out.println("\n\n\n\n");
		testList("HexaLinkedList", 10_000, 1, 1000, () -> new NlyLinkedList<>(256));
		// set .0226
		// get .0086
		System.out.println("\n\n\n\n");
		testList("LinkedList", 10_000, 1, 1000, LinkedList::new);
		// set .1234
		// get .4062
	}

	public static void testList(String name, int warmup, int stages, int iters, Supplier<? extends List<Integer>> newList) {
		List<Integer> warmupList = newList.get();
		test(name + "set warmup", warmup, iters, warmupList::add);
		test(name + " get warmup", warmup, iters, warmupList::get);

		List<Integer> setList = newList.get();
		for (int i = 0; i < stages; i++)
			test(name + " set stage" + i, 0, iters, setList::add);

		for (int i = 0; i < stages; i++)
			test(name + " get stage" + i, 0, iters, setList::get);
	}

	public static double test(String test, int warmup, int iters, IntConsumer forI) {
		System.out.println("=========== TEST SRT " + test + " ===========");
		if (warmup != 0) {
			System.out.println("Warmup " + test);
			for (int i = 0; i < warmup; i++)
				forI.accept(i);
		}
		if (iters != 0) {
			System.out.println("Test " + test);
			long start = System.nanoTime();
			for (int i = 0; i < iters; i++)
				forI.accept(i);
			long time = System.nanoTime();
			double nsop = (time - start) / 1_000_000d;
			System.out.println("Nanoseconds per operation: " + nsop);
			System.out.println("=========== TEST END " + test + " ===========");
			return nsop;
		}
		return 0;
	}
}

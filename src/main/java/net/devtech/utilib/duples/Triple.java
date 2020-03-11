package net.devtech.utilib.duples;

public class Triple<A, B, C> {
	private final A a;
	private final B b;
	private final C c;

	public Triple(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public A getA() {
		return this.a;
	}

	public B getB() {
		return this.b;
	}

	public C getC() {
		return this.c;
	}
}

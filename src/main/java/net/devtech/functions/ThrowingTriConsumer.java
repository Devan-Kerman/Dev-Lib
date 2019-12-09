package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingTriConsumer<A, B, C> extends net.devtech.functions.TriConsumer<A, B, C> {
	void acceptThrow(A a, B b, C c) throws Throwable;
	@Override default void accept(A a, B b, C c) {
		try {
			acceptThrow(a, b, c);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
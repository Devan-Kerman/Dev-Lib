package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingQuadConsumer<A, B, C, D> extends net.devtech.functions.QuadConsumer<A, B, C, D> {
	void acceptThrow(A a, B b, C c, D d) throws Throwable;
	@Override default void accept(A a, B b, C c, D d) {
		try {
			acceptThrow(a, b, c, d);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
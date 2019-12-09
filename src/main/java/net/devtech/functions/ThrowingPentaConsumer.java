package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingPentaConsumer<A, B, C, D, E> extends net.devtech.functions.PentaConsumer<A, B, C, D, E> {
	void acceptThrow(A a, B b, C c, D d, E e) throws Throwable;
	@Override default void accept(A a, B b, C c, D d, E e) {
		try {
			acceptThrow(a, b, c, d, e);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
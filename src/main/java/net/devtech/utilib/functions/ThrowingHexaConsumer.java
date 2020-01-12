package net.devtech.utilib.functions;
/*Auto-generated*/
public interface ThrowingHexaConsumer<A, B, C, D, E, F> extends HexaConsumer<A, B, C, D, E, F> {
	void acceptThrow(A a, B b, C c, D d, E e, F f) throws Throwable;
	@Override default void accept(A a, B b, C c, D d, E e, F f) {
		try {
			acceptThrow(a, b, c, d, e, f);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
package net.devtech.utilib.functions;
/*Auto-generated*/
public interface ThrowingBiConsumer<A, B> extends java.util.function.BiConsumer<A, B> {
	void acceptThrow(A a, B b) throws Throwable;
	@Override default void accept(A a, B b) {
		try {
			acceptThrow(a, b);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
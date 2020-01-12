package net.devtech.utilib.functions;
/*Auto-generated*/
public interface ThrowingConsumer<A> extends java.util.function.Consumer<A> {
	void acceptThrow(A a) throws Throwable;
	@Override default void accept(A a) {
		try {
			acceptThrow(a);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
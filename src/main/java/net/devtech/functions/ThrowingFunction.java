package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingFunction<A, B> extends java.util.function.Function<A, B> {
	B applyThrow(A a) throws Throwable;
	@Override default B apply(A a) {
		try {
			return applyThrow(a);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
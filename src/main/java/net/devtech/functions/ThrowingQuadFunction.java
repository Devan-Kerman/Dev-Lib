package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingQuadFunction<A, B, C, D, E> extends net.devtech.functions.QuadFunction<A, B, C, D, E> {
	E applyThrow(A a, B b, C c, D d) throws Throwable;
	@Override default E apply(A a, B b, C c, D d) {
		try {
			return applyThrow(a, b, c, d);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
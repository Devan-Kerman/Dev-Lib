package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingPentaFunction<A, B, C, D, E, F> extends net.devtech.functions.PentaFunction<A, B, C, D, E, F> {
	F applyThrow(A a, B b, C c, D d, E e) throws Throwable;
	@Override default F apply(A a, B b, C c, D d, E e) {
		try {
			return applyThrow(a, b, c, d, e);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
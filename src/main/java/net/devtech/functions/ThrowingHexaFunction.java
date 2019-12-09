package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingHexaFunction<A, B, C, D, E, F, G> extends net.devtech.functions.HexaFunction<A, B, C, D, E, F, G> {
	G applyThrow(A a, B b, C c, D d, E e, F f) throws Throwable;
	@Override default G apply(A a, B b, C c, D d, E e, F f) {
		try {
			return applyThrow(a, b, c, d, e, f);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
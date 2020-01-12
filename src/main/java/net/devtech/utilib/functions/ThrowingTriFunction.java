package net.devtech.utilib.functions;
/*Auto-generated*/
public interface ThrowingTriFunction<A, B, C, D> extends TriFunction<A, B, C, D> {
	D applyThrow(A a, B b, C c) throws Throwable;
	@Override default D apply(A a, B b, C c) {
		try {
			return applyThrow(a, b, c);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
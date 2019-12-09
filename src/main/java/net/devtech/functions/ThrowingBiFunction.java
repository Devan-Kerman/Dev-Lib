package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingBiFunction<A, B, C> extends java.util.function.BiFunction<A, B, C> {
	C applyThrow(A a, B b) throws Throwable;
	@Override default C apply(A a, B b) {
		try {
			return applyThrow(a, b);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
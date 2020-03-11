package net.devtech.utilib.io;
/*Auto-generated*/
public interface ThrowingSupplier<A> extends java.util.function.Supplier<A> {
	A getThrow() throws Throwable;
	@Override default A get() {
		try {
			return getThrow();
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
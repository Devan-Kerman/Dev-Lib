package net.devtech.functions;
/*Auto-generated*/
public interface ThrowingRunnable extends Runnable {
	void runThrow() throws Throwable;
	@Override default void run() {
		try {
			runThrow();
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
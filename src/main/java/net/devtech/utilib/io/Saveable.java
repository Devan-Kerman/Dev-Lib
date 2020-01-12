package net.devtech.utilib.io;

/**
 * something that can be saved somewhere for persistent storage
 */
public interface Saveable<S> {
	void save(S storage);
}

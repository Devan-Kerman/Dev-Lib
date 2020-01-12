package net.devtech.utilib.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class HexadecimalOutputStream extends OutputStream {
	protected Writer writer;
	protected static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	public HexadecimalOutputStream(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void write(int b) throws IOException {
		this.writer.write(HEX_CHARS[(b >> 4) & 15]);
		this.writer.write(HEX_CHARS[b & 15]);
	}

	@Override
	public void flush() throws IOException {
		this.writer.flush();
	}

	@Override
	public void close() throws IOException {
		this.writer.flush();
	}
}

package net.devtech.utilib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class HexadecimalInputStream extends InputStream {
	private Reader reader;

	public HexadecimalInputStream(Reader reader) {
		this.reader = reader;
	}

	@Override
	public int read() throws IOException {
		char a = (char) this.reader.read();
		char b = (char) this.reader.read();
		return this.getNibble(a) << 4 | this.getNibble(b);
	}

	private int getNibble(char val) {
		if(val < 58) { // ascii numbers
			return val-48;
		} else if(val < 91) { // ascii uppercase
			return 10+val-65;
		} else if(val < 123) { // ascii lowercase
			return 10+val-97;
		}
		throw new IllegalArgumentException("Invalid hexadecimal character " + val);
	}

}

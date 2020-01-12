package testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Objects {
	public static void main(String[] args) throws IOException {
		File file = new File("build/classes/java/test/testing/Bytecode.class");
		try (FileInputStream in = new FileInputStream(file)) {
			int bite;
			int c = 0;
			while ((bite = in.read()) != -1) {
				if (c > 187) {
					if (bite > 127) //
						System.out.printf("%d-%s\n", c, bite);
					else //
						System.out.printf("%d-%s\n", c, bite);
				}
				c++;
			}
		}
	}
}

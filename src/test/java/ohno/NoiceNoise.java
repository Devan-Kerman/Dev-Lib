package ohno;

import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.sin;

/**
 * All rights reserved HalfOf2 2020
 * <p>
 * sine sine sine sine noise
 */
public class NoiceNoise {
	private static final int WAVELENGTH = 128;
	private static final int MOD = WAVELENGTH * 2 - 1;
	// range -16 to 16
	private static final int[] SINE = new int[WAVELENGTH * 2];

	static {
		for (int i = 0; i < SINE.length; i++) {
			SINE[i] = (int) (sin(i * Math.PI / WAVELENGTH) * 128);
		}
	}

	public static void main(String[] args) throws IOException {
		int dimensions = 1024;
		BufferedImage image = new BufferedImage(dimensions, dimensions, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < dimensions; x++) {
			for (int y = 0; y < dimensions; y++) {
				int val = sample(x, y);
				int rgb = (val << 16) + (val << 8) + val;
				image.setRGB(x, y, rgb);
			}
		}
		File temp = File.createTempFile("test", ".png");
		ImageIO.write(image, "png", temp);
		Desktop.getDesktop().open(temp);
	}

	private static int sample(int x, int y) {
		return sample((x & 0xffff) << 16 | (y & 0xffff));
	}

	private static int sample(int i) {
		return SINE[SINE[(i / 2) & MOD] + SINE[(i / 3) & MOD] + SINE[(i / 5) & MOD] + SINE[(i / 7) & MOD] + SINE[(i / 11) & MOD] + SINE[(i / 13) & MOD] + SINE[(i / 17) & MOD] & MOD];
	}
}

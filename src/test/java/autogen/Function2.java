package autogen;

import java.io.*;

/**
 * All rights reserved, c 2020, HalfOf2
 */
public class Function2 {
	private static final String PACKAGE_NAME = "net.devtech.utilib.functions";

	public static void main(String[] args) throws IOException {
		int toWrite = 7;
		for (int i = 0; i < toWrite; i++) {
			write(i);
		}
	}

	public static void write(int params) throws IOException {
		if (params > 2) {// java already covers these
			write(params, true, false);
			write(params, false, false);
		}
		write(params, true, true);
		write(params, false, true);
	}

	public static void write(int params, boolean ret, boolean throwing) throws IOException {
		String name = "";
		if (throwing) { name += "Throwing"; }
		name += prefix(params);
		name += ext(params, ret);
		File file = new File("src/main/java/" + PACKAGE_NAME.replace('.', '/') + '/', name + ".java");
		file.getParentFile().mkdirs();
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		write(new PrintStream(writer), params, ret, throwing);
		writer.close();
	}

	public static void write(PrintStream writer, int params, boolean ret, boolean throwing) throws IOException {
		System.out.println("Writing header");
		writer.print("package ");
		writer.print(PACKAGE_NAME);
		writer.println(";\n\n");
		writer.print("public interface ");
		if (throwing) { writer.print("Throwing"); }
		writer.print(prefix(params));
		writer.print(ext(params, ret));
		if (params > 0 || ret) {
			writer.write('<');
			writeSigs(writer, params, ret);
			writer.print(">");
		}
		if (throwing) {
			writer.print(" extends ");
			writer.print(extend(params, ret));
			if (params > 0 || ret) {
				writer.write('<');
				writeSigs(writer, params, ret);
				writer.print(">");
			}
		}

		writer.print(" {\n\t");
		// first method body
		if (ret) { writer.print(getAlphaUpper(params)); } else { writer.print("void"); }
		writer.print(' ');
		writer.print(getMethodName(params, ret));
		if (throwing) { writer.print("Throwing"); }
		writer.print('(');
		writeParams(writer, params);
		writer.print(')');
		if (throwing) { writer.print(" throws Throwable"); }
		writer.print(";\n");

		if (throwing) { // throwing method body
			writer.print("\t@Override default ");
			if (ret) { writer.print(getAlphaUpper(params)); } else { writer.print("void"); }
			writer.print(' ');
			writer.print(getMethodName(params, ret));
			writer.print('(');
			writeParams(writer, params);
			writer.print(") {\n\t\ttry {\n\t\t\t");
			if (ret) { writer.print("return "); }
			writer.print("this.");
			writer.print(getMethodName(params, ret));
			writer.print("Throwing(");
			writeArgs(writer, params);
			writer.print(");\n\t\t} catch(Throwable t) {\n\t\t\tthrow new RuntimeException(t);\n\t\t}\n\t}\n");
		}
		writer.print('}');
	}

	public static void writeArgs(PrintStream stream, int params) {
		for (int i = 0; i < params; i++) {
			String alpha = getAlphaLower(i);
			stream.print(alpha); // print type
			if (i + 1 != params) { stream.print(", "); }
		}
	}

	public static void writeParams(PrintStream stream, int params) {
		for (int i = 0; i < params; i++) {
			String alpha = getAlphaUpper(i);
			stream.print(alpha); // print type
			stream.print(' ');
			stream.print(alpha.toLowerCase()); // print param name
			if (i + 1 != params) { stream.print(", "); }
		}
	}

	public static void writeSigs(PrintStream writer, int i, boolean ret) {
		if (ret) { i++; }
		for (int index = 0; index < i; index++) {
			writer.print(getAlphaUpper(index));
			if (index + 1 != i) { writer.print(", "); }
		}
	}

	public static String getMethodName(int params, boolean ret) {
		if (params == 0) {
			return ret ? "get" : "run";
		}
		return ret ? "apply" : "accept";
	}

	public static final char[] ALPHABET_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static String getAlphaUpper(int number) {
		if (number == 0) { return "A"; }
		StringBuilder val = new StringBuilder();
		int exp = 0;
		while (true) {
			int div = (int) (number / Math.pow(26, exp++));
			if (div == 0) { return val.toString(); }
			int index = div % 26;
			val.append(ALPHABET_UPPER_CASE[index]);
		}
	}

	public static String getAlphaLower(int number) {
		return getAlphaUpper(number).toLowerCase();
	}

	public static final String[] PREFIXES = {"", "Bi", "Tri", "Quad", "Penta", "Hexa", "Hepta", "Octa", "Nona", "Deca",
	                                         "Undeca", "Duodeca", "Tredeca", "Quattuordeca", "Quindeca", "Sexdeca",
	                                         "Septendeca", "Octodeca", "Novemdeca", "Vigin", "Unvigin"
	};

	public static String prefix(int val) {
		if (val == 0) { return ""; }
		if (val == 1_000_000_000) { return "Giga"; }
		return PREFIXES[val - 1];
	}

	public static String ext(int params, boolean ret) {
		if (params == 0) { return ret ? "Supplier" : "Runnable"; }
		return ret ? "Function" : "Consumer";
	}

	public static String extend(int params, boolean ret) {
		switch (params) {
			case 0:
				return ret ? "java.util.function.Supplier" : "Runnable";
			case 1:
				return ret ? "java.util.function.Function" : "java.util.function.Consumer";
			case 2:
				return !ret ? "java.util.function.BiConsumer" : "java.util.function.BiFunction";
			default:
				return PACKAGE_NAME + '.' + prefix(params) + ext(params, ret);
		}
	}
}

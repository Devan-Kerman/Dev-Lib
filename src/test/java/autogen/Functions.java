package autogen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Functions {
	public static final String PACKAGE = "net.devtech.utilib";
	public static final String[] PREFIXES = {"", "Bi", "Tri", "Quad", "Penta", "Hexa", "Hepta", "Octa", "Nona", "Deca", "Undeca", "Duodeca", "Tredeca", "Quattuordeca", "Quindeca", "Sexdeca", "Septendeca", "Octodeca", "Novemdeca", "Vigin", "Unvigin"};

	// class name, extends, return type, methodname, typed parameters, return/noreturn, non typed parametrs
	public static final String THROWING = "package " + PACKAGE + ".functions;\n/*Auto-generated*/\npublic interface Throwing%s %s{\n\t%s %sThrow(%s) throws Throwable;\n\t@Override default %3$s %4$s(%5$s) {\n\t\ttry {\n\t\t\t%s%4$sThrow(%s);\n\t\t} catch(Throwable t) {\n\t\t\tthrow new RuntimeException(t);\n\t\t}\n\t}\n}";
	// class name, extends, return type, method name
	public static final String NORMAL = "package " + PACKAGE + ".functions;\n/*Auto-generated*/\npublic interface %s %s{\n\t%s %s(%s);\n}";


	public static void main(String[] args) throws IOException {
		int functions = 15 + 1;
		write(1_000_000_000, true);
		//writeNormal(1000000, true);
		/*for (int i = 0; i < functions; i++) {
			write(i, true);
			write(i, false);
			writeNormal(i, true);
			writeNormal(i, false);
		}*/
	}

	public static void write(int params, boolean ret) throws IOException {
		File file = new File("src/main/java/" + PACKAGE.replace('.', '/') + "/functions/Throwing" + getName(params, ret) + ".java");
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(file);
		writer.write(forParams(params, ret));
		writer.close();
	}

	public static void writeNormal(int params, boolean ret) throws IOException {
		if (params > 2) {
			File file = new File("src/main/java/" + PACKAGE.replace('.', '/') + "/functions/" + getName(params, ret) + ".java");
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			writer.write(String.format(NORMAL, getFullName(params, ret), getExtends(params, ret, true), getReturnType(params, ret), methodName(FunctionType.getType(params, ret)), typedParameters(params)));
			writer.close();
		}
	}

	public static String forParams(int params, boolean ret) {
		return String.format(THROWING, getFullName(params, ret), getExtends(params, ret, false), getReturnType(params, ret), methodName(FunctionType.getType(params, ret)), typedParameters(params), ret(ret), nonTypedParameters(params));
	}

	public static String getExtends(int params, boolean ret, boolean normal) {
		if (params == 0 && !ret) return "extends Runnable ";
		if (normal) {
			if (params <= 2) return "extends java.util.function." + getFullName(params, ret) + " ";
		} else {
			if (params <= 2) return "extends java.util.function." + getFullName(params, ret) + " ";
			else return "extends " + PACKAGE + ".functions." + getFullName(params, ret) + " ";
		}
		return "";
	}

	public static String methodName(FunctionType type) {
		switch (type) {
			case CONSUMER:
				return "accept";
			case FUNCTION:
				return "apply";
			case RUNNABLE:
				return "run";
			case SUPPLIER:
				return "get";
			default:
				return "error";
		}
	}


	public static String getFullName(int params, boolean ret) {
		return getName(params, ret) + getGenerics(params, ret);
	}

	public static String getName(int params, boolean ret) {
		if (params == 0)
			return ret ? "Supplier" : "Runnable";
		if (params == 1000) return "Milli" + (ret ? "Function" : "Consumer");
		if(params == 1_000_000)
			return "Mega" + (ret ? "Function" : "Consumer");
		if(params == 1_000_000_000)
			return "Giga" + (ret ? "Function" : "Consumer");
		return PREFIXES[params - 1] + (ret ? "Function" : "Consumer");
	}


	public static final char[] ALPHABET_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static String getAlphaUpper(int number) {
		if (number == 0) return "A";
		StringBuilder val = new StringBuilder();
		int exp = 0;
		while (true) {
			int div = (int) (number / Math.pow(26, exp++));
			if (div == 0) return val.toString();
			int index = div % 26;
			val.append(ALPHABET_UPPER_CASE[index]);
		}
	}

	public static String getAlphaLower(int number) {
		return getAlphaUpper(number).toLowerCase();
	}


	public static String getReturnType(int params, boolean ret) {
		if (!ret) return "void";
		else return getAlphaUpper(params);
	}

	public static String getGenerics(int params, boolean ret) {
		if ((ret ? ++params : params) != 0) {
			StringBuilder base = new StringBuilder("<");
			for (int i = 0; i < params; i++) {
				base.append(getAlphaUpper(i)).append(", ");
			}
			return base.substring(0, base.length() - 2) + ">";
		}
		return "";
	}

	public static String typedParameters(int params) {
		if (params == 0) return "";
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < params; i++) {
			current.append(getAlphaUpper(i)).append(" ").append(getAlphaLower(i)).append(", ");
		}
		return current.substring(0, current.length() - 2);
	}

	public static String nonTypedParameters(int params) {
		if (params == 0) return "";
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < params; i++) {
			current.append(getAlphaLower(i)).append(", ");
		}
		return current.substring(0, current.length() - 2);
	}

	public static String ret(boolean ret) {
		return ret ? "return " : "";
	}
}
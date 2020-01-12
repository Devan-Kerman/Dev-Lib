package autogen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class SuperFunction {
	private static final Class[] TYPES = {boolean.class, byte.class, char.class, short.class, int.class, float.class, long.class, double.class, Object.class};
	private static final Class[] RETS = {void.class, boolean.class, byte.class, char.class, short.class, int.class, float.class, long.class, double.class, Object.class};

	public static void main(String[] args) throws IOException {
		File file = new File("src/main/java/net/devtech/utilib/functions/GeneralFunction.java");
		PrintStream stream = new PrintStream(file);
		stream.println("package net.devtech.utilib.functions;");
		stream.println("/**");
		stream.println("* for LambdaMetaFactory things");
		stream.println("*/");
		stream.println("public interface GeneralFunction {");
		for (Class type : RETS) {
			genMethod(stream, type);
			for (Class aClass : TYPES) {
				genMethod(stream, type, aClass);
				for (Class type1 : TYPES) {
					genMethod(stream, type, aClass, type1);
				}
			}
		}
		stream.println("}");

	}

	public static void genMethod(PrintStream stream, Class<?> ret, Class<?>... params) {
		String simple = ret.getSimpleName();
		stream.print("\t" + simple + " invoke" + cap(simple)+"(");
		for (int i = 0; i < params.length; i++) {
			String smpl = params[i].getSimpleName();
			stream.print(smpl + " p" + i);
			if(i+1 < params.length)
				stream.print(',');
		}
		stream.println(");");
	}


	private static String cap(String string) {
		char[] chars = string.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}
}

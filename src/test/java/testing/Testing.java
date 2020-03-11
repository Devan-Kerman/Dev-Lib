package testing;

import net.devtech.utilib.duples.Triple;
import net.devtech.utilib.unsafe.UnsafeUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


public class Testing implements Opcodes {
	public static void main(String[] args) throws Throwable {
		Class<? extends TestSetter> bytecode = wrap(new TestLoader(), Triple.class, TestSetter.class);
	}

	public static class TestLoader extends ClassLoader implements Definer {
		@Override
		public Class<?> define(String type, byte[] bytecode) {
			return this.defineClass(type, bytecode, 0, bytecode.length);
		}
	}

	public interface Definer {
		Class<?> define(String type, byte[] bytecode);
	}

	public static <I> Class<? extends I> wrap(Definer definer, Class<?> type, Class<I> iface) {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		String name = Type.getInternalName(type);
		String impl = Type.getInternalName(iface);
		String owner = "org/github/fukkitmc/fukkit/synthetic/wrapper/" + name;
		writer.visit(V1_8, type.getModifiers() & 3, owner, null, name, new String[]{impl});
		Set<String> fields = new HashSet<>();
		for (Method method : iface.getMethods()) {
			String mtd = method.getName();
			String fname = mtd.substring(3).toLowerCase();
			MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, mtd, Type.getMethodDescriptor(method), null, null);
			visitor.visitVarInsn(ALOAD, 0); // load "this"
			Class<?> fieldType;
			if (method.getParameterCount() == 1) { // if setter
				fieldType = method.getParameterTypes()[0];
				int opcode;
				if (fieldType == int.class || fieldType == byte.class || fieldType == boolean.class || fieldType == short.class || fieldType == char.class) {
					opcode = ILOAD;
				} else if (fieldType == double.class) {
					opcode = DLOAD;
				} else if (fieldType == float.class) {
					opcode = FLOAD;
				} else if (fieldType == long.class) {
					opcode = LLOAD;
				} else {
					opcode = ALOAD;
				}
				visitor.visitVarInsn(opcode, 1); // load parameter
				visitor.visitFieldInsn(PUTFIELD, owner, fname, Type.getDescriptor(fieldType)); // set parameter

			} else { // getter
				fieldType = method.getReturnType();
				visitor.visitFieldInsn(GETFIELD, owner, fname, Type.getDescriptor(fieldType)); // get field
				int opcode;
				if (fieldType == int.class || fieldType == byte.class || fieldType == boolean.class || fieldType == short.class || fieldType == char.class) {
					opcode = IRETURN;
				} else if (fieldType == double.class) {
					opcode = DRETURN;
				} else if (fieldType == float.class) {
					opcode = FRETURN;
				} else if (fieldType == long.class) {
					opcode = LRETURN;
				} else {
					opcode = ARETURN;
				}
				visitor.visitVarInsn(opcode, 1); // load parameter
			}

			if (!fields.contains(fname)) {
				fields.add(fname); // lowercase field name
				writer.visitField(ACC_PUBLIC, fname, Type.getInternalName(fieldType), null, null).visitEnd();
			}

			// done writing method
			visitor.visitEnd();
		}

		for (Method method : type.getMethods()) {

		}
		writer.visitEnd();
		byte[] bytecode = writer.toByteArray();
		return (Class<? extends I>) definer.define(owner, bytecode);
	}


}

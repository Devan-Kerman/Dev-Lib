package testing;

import net.devtech.utilib.functions.GeneralFunction;
import net.devtech.utilib.unsafe.ReflectionUtil;

public class Testing {
	private int test;

	public Testing(int test) {
		this.test = test;
	}

	public static void main(String[] args) throws Throwable {
		GeneralFunction func = ReflectionUtil.getConstructor(Testing.class.getDeclaredConstructor(int.class));
		Testing object = (Testing) func.invokeObject(3);
		System.out.println(ReflectionUtil.getSpecial(Object.class.getDeclaredMethod("toString")).invokeObject(object));
		System.out.println(ReflectionUtil.getMethod(Object.class.getDeclaredMethod("toString")).invokeObject(object));
	}

	@Override
	public String toString() {
		return "Testing{" + "test=" + this.test + '}';
	}
}

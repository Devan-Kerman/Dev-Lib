package testing;

public class Bytecode {
	private static int b;
	private int i;
	public static void main(String[] args) {
		label:
		for (;;) { // GOTO
			for (;;) {
				if(b == b++)
					break label;
			}
		}

		Object[] arg = new Object[4]; // newarray

		arg[0] = "a"; // aastore
		int i = arg.length; // arraylength & astore
		if(arg[0] instanceof Integer && ((Boolean)arg[0])) // intance of checkcast
			throw new NullPointerException(); // athrow
		Bytecode bytecode = new Bytecode(); // create obj
		bytecode.test(bytecode); // invoke virtual
		bytecode.i = bytecode.i + 3; // get field
		b = b + 4; // get static
		// todo GOTO
		// todo invokedynamic
		arg[0] = Object.class; // ldc
		arg[3] = new Object[3][4];


	}

	public double test(Object a) {
		return this.oh(a.hashCode());
	}

	private int oh(double ok) {
		return (int) (ok+3);
	}
}

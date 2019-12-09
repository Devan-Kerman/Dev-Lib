package autogen;

public enum FunctionType {
	RUNNABLE,
	CONSUMER,
	SUPPLIER,
	FUNCTION;
	public static FunctionType getType(int params, boolean ret) {
		if(params == 0)
			if(ret)
				return SUPPLIER;
			else
				return RUNNABLE;
		else
			if(ret)
				return FUNCTION;
			else
				return CONSUMER;
	}
}

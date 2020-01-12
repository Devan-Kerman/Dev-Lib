package net.devtech.utilib.java;

import net.devtech.utilib.structures.inheritance.InheritedMap;
import java.lang.reflect.Method;

/**
 * extend methods easier, now with 100% less super.method()!
 */
public class ExtenderMethodFactory {
	private static final InheritedMap<Object, Method> METHODS = InheritedMap.getMethods(Object.class);
	/**
	 * Get all the methods annotated with the given annotation that has the given return type and parameters
	 * @param annotation the annotation type
	 * @param toGet the class to scan
	 * @param functionCombiner combines the lambda with the function type, the boolean is if the lambda is of a static method
	 * @param returnType the return type of the method
	 * @param params the parameter classes
	 * @param <T> the function
	 * @return a function that calls all the methods annotated with the annotation
	 */
	/*public static <T> T extend(Class<? extends Annotation> annotation, Class<?> toGet, TriFunction<T, Boolean, Lambda, T> functionCombiner, Class<?> returnType, Class<?>...params) {
		try {
			T current = null;
			for (Map.Entry<Method, Lambda> entry : getAnnotatedAsLambda(annotation, toGet).entrySet()) {
				Method method = entry.getKey();
				if(method.getReturnType() == returnType && Arrays.equals(method.getParameterTypes(), params))
					current = functionCombiner.apply(current, Modifier.isStatic(method.getModifiers()), entry.getValue());
				else
					throw new IllegalAccessException("Invalid return/param type(s)!");
			}
			return current;
		} catch (Throwable throwable) {
			throw new RuntimeException("Error in creating extender lambda", throwable);
		}
	}

	private static Map<Method, Lambda> getAnnotatedAsLambda(Class<? extends Annotation> annotation, Class<?> toGet) throws Throwable {
		Map<Method, Lambda> methods = new HashMap<>();
		for (Method method : METHODS.getAttributes(toGet)) {
			int mods = method.getModifiers();
			if (!Modifier.isAbstract(mods)) {
				Annotation ann = method.getAnnotation(annotation);
				if (ann != null)
					methods.put(method, LambdaFactory.create(method));
			}
		}
		return methods;
	}*/
	// TODO fix

}

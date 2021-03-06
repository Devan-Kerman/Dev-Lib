package net.devtech.utilib.structures.inheritance;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.devtech.utilib.structures.lists.NodedList;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * represents a map that stores a relation between classes, and items that they can inherit, like methods (mostly a reflection util)
 *
 * @param <P> the parent class type
 * @param <O> the object/attribute type
 */
public class InheritedMap<P, O> {
	// a map of the classes to their attributes
	private final Map<Class<?>, List<O>> attributes = new HashMap<>();
	// a map of classes to their and inherited attributes
	private final Map<Class<?>, NodedList<O>> cache = new HashMap<>();
	// the parent type
	private final Class<P> parentType;
	// a class to attributes converter
	private final Function<Class<?>, List<O>> attributeSupplier;

	/**
	 * creates a new inherited map with the given parent class and attribute supplier
	 *
	 * @param parentClass the parent object
	 * @param attributeSupplier the converter
	 */
	public InheritedMap(Class<P> parentClass, Function<Class<?>, List<O>> attributeSupplier) {
		this.parentType = parentClass;
		this.attributeSupplier = attributeSupplier;
	}

	public static <P, O extends AccessibleObject> InheritedMap<P, O> getAccessables(Class<P> parent, Function<Class<?>, O[]> getter) {
		return getAccessables(parent, getter, a -> true);
	}

	public static <P, O extends AccessibleObject> InheritedMap<P, O> getAccessables(Class<P> parent, Function<Class<?>, O[]> getter, Predicate<O> valid) {
		return new InheritedMap<>(parent, c -> {
			O[] fields = getter.apply(c);
			ArrayList<O> good = new ArrayList<>();
			for (O field : fields) {
				if(valid.test(field)) {
					field.setAccessible(true);
					good.add(field);
				}
			}

			return good;
		});
	}

	public static <A, B, C> InheritedMap<A, B> getRemapped(InheritedMap<A, C> original, Function<C, B> converter) {
		return new InheritedMap<>(original.parentType, c -> original.attributeSupplier.apply(c).stream().map(converter).collect(Collectors.toList()));
	}

	public static <P> InheritedMap<P, Field> getFields(Class<P> parent) {
		return getAccessables(parent, Class::getDeclaredFields);
	}

	public static <P> InheritedMap<P, Method> getMethods(Class<P> parent) {
		return getAccessables(parent, Class::getDeclaredMethods);
	}

	public static <P> InheritedMap<P, Method> getMethodsAnnotated(Class<P> parent, Class<? extends Annotation> annotation) {
		return getAccessables(parent, Class::getDeclaredMethods, m -> m.isAnnotationPresent(annotation));
	}

	public static <P> InheritedMap<P, Field> getFieldsAnnotated(Class<P> parent, Class<? extends Annotation> annotation) {
		return getAccessables(parent, Class::getFields, f -> f.isAnnotationPresent(annotation));
	}

	/**
	 * gets all of the classes attributes, inherited and owned, this is cached
	 *
	 * @param type the class
	 * @return an immutable list of all the attributes
	 */
	public NodedList<O> getAttributes(Class<? extends P> type) {
		NodedList<O> nodedList = cache.get(type);
		if (nodedList == null) {
			List<List<O>> lists = new ObjectArrayList<>();
			while (type != null && parentType.isAssignableFrom(type)) {
				List<O> ok = map(type);
				for (Class<?> anInterface : type.getInterfaces()) {
					ok.addAll(map(anInterface));
				}
				if (ok.size() > 0) lists.add(map(type));
				type = (Class) type.getSuperclass();
			}
			nodedList = new NodedList<>(lists);
			cache.put(type, nodedList);
		}
		return nodedList;
	}

	private List<O> map(Class<?> type) {
		List<O> types = attributes.get(type);
		if (types == null) {
			types = attributeSupplier.apply(type);
			attributes.put(type, types);
		}
		return types;
	}
}
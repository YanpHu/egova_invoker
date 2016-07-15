package com.egova.rpc.support.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


public class ClassUtils {
	
	public static String classNamesToString(Class<?>... classes) {
		return classNamesToString(Arrays.asList(classes));
	}

	
	public static String classNamesToString(Collection<Class<?>> classes) {
		if (classes.isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		for (Iterator<Class<?>> it = classes.iterator(); it.hasNext(); ) {
			Class<?> clazz = it.next();
			sb.append(clazz.getName());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}

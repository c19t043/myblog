package com.blog.ssh.utils;

import java.lang.reflect.ParameterizedType;

public class TUtil {

	public static Class getActualType(Class entityClass) {
		ParameterizedType type = (ParameterizedType) entityClass.getGenericSuperclass();
		return (Class) type.getActualTypeArguments()[0];

	}
	
}
